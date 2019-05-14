package softwareiiassessment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Appointment Manager entry point
 *
 * @author Lesaun
 */
public class Softwareiiassessment extends Application {
    private Stage primaryStage;
    private SQLAPI api;

    private PaneLogin loginPane;
    private Scene loginScene;
    private PaneCalendar calendarPane;
    private Scene calendarScene;
    private PaneCustomerManager customerManagerPane;
    private Scene customerManagerScene;
    private PaneCustomerCreateModify customerPane;
    private Scene customerScene;
    private PaneAddressCreateModify addressPane;
    private Scene addressScene;
    private PaneAppointmentCreateModify appointmentPane;
    private Scene appointmentScene;
    private PaneReport reportPane;
    private Scene reportScene;

    private ORMUser user;

    @Override
    public void start(Stage primaryStage) {
        api = new SQLAPI();
        this.primaryStage = primaryStage;
        primaryStage.setResizable(false);
        primaryStage.setTitle("Appointment Manager");
        showLoginPane();
    }

    /**
     * Change active scene
     * 
     * @param newScene scene to change to
     */
    private void changeScene(Scene newScene) {
        primaryStage.hide();
        primaryStage.setScene(newScene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    /**
     * Show login point
     */
    private void showLoginPane() {
        loginPane = new PaneLogin();
        loginScene = new Scene(loginPane, 480, 220);
        loginPane.setLocale(Locale.getDefault());

        loginPane.setLoginBtnEvent(e -> {
            String userName = loginPane.getUsername();

            if (api.isUsernamePasswordValid(userName, loginPane.getPassword())) {
                user = api.getUserByName(userName);
                
                try {
                    String logPath = System.getProperty("user.home") +
                        File.separator + "Documents" 
                            + File.separator + "userlog.txt";
                    FileWriter fw = new FileWriter(logPath, true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write(userName + " logged in at " +
                            Long.toString(System.currentTimeMillis()) + "\n");
                    bw.close();
                } catch (IOException ex) {
                    System.out.println("failed to log login");
                }
                
                if (api.isAppointmentWithin15Min(LocalDateTime.now(ZoneId.of("UTC")))) {
                    PaneEventAlert apptAlert = new PaneEventAlert();
                    Scene apptAlertScene = new Scene(apptAlert, 480, 120);
                    apptAlert.setOkBtnEvent(event -> showCalendarPane());
                    changeScene(apptAlertScene);
                } else {
                    showCalendarPane();
                }
            } else {
                loginPane.showErrorMessage();
            }
        });

        loginPane.setExitBtnEvent(e -> primaryStage.close());
        changeScene(loginScene);
    }

    /**
     * Show calendar pane
     */
    private void showCalendarPane() {
        calendarPane = new PaneCalendar(api, LocalDateTime.now());
        calendarScene = new Scene(calendarPane, 745, 382);

        // justification of lambda: simplify's code for displaying panes
        calendarPane.setAddBtnEvent(event -> showAppointmentAddPane());

        calendarPane.setModBtnEvent(event -> showAppointmentEditPane());

        calendarPane.setDelBtnEvent(event -> {
            api.deleteAppointment(calendarPane.getSelectedAppointment());
            calendarPane.refreshCalendar();
        });

        calendarPane.setCustomerManagerBtnEvent(event ->
            showManageCustomerPane());
        
        calendarPane.setReportsBtnEvent(event ->
            showReportsPane());

        calendarPane.setLogoutBtnEvent(e -> showLoginPane());
        changeScene(calendarScene);
    }
    
    /**
     * Show report pane
     */
    private void showReportsPane() {
        reportPane = new PaneReport();
        reportScene = new Scene(reportPane, 280, 310);
        String docFolder = System.getProperty("user.home") +
                File.separator + "Documents";
        
        reportPane.setUserScheduleBtnEvent(e -> {
            try {
                BufferedWriter writer = new BufferedWriter(
                        new FileWriter(docFolder + File.separator
                        + "consultant_schedule.txt"));
                writer.write(api.consultantScheduleReport());
                writer.close();
            } catch (IOException ex) {
                System.out.println("failed to write user schedule report");
            }
        });

        reportPane.setNumApptByTypeMonBtnEvent(e -> {
            try {
                BufferedWriter writer = new BufferedWriter(
                        new FileWriter(docFolder + File.separator
                        + "number_appts_by_type_month.txt"));
                writer.write(api.appointmentTypesByMonthReport());
                writer.close();
            } catch (IOException ex) {
                System.out.println("failed to write num appts by type/month report");
            }
        });

        reportPane.setApptsByCustomerBtnEvent(e -> {
            try {
                BufferedWriter writer = new BufferedWriter(
                        new FileWriter(docFolder + File.separator
                        + "number_of_appts_per_customer.txt"));
                writer.write(api.numberOfAppointmentsByCustomer());
                writer.close();
            } catch (IOException ex) {
                System.out.println("failed to write number of appts by customer report");
            }
        });
        reportPane.setDoneBtnEvent(e -> changeScene(calendarScene));
        
        changeScene(reportScene);        
    }

    /**
     * Show manager customer pane
     */
    private void showManageCustomerPane() {
        customerManagerPane = new PaneCustomerManager(api);
        customerManagerScene = new Scene(customerManagerPane, 580, 260);

        customerManagerPane.setAddBtnEvent(event -> showCustomerAddPane());
        customerManagerPane.setModBtnEvent(event -> showCustomerEditPane());
        customerManagerPane.setDelBtnEvent(event -> {
            api.deleteCustomer(customerManagerPane.getSelectedCustomer());
            customerManagerPane.updateCustomers(api.getCustomers());
        });

        customerManagerPane.setCancelBtnEvent(event -> {
            changeScene(calendarScene);
        });

        changeScene(customerManagerScene);
    }

    /**
     * Show appointment add pane
     */
    private void showAppointmentAddPane() {
        appointmentPane = new PaneAppointmentCreateModify(api);
        appointmentScene = new Scene(appointmentPane, 480, 660);

        appointmentPane.setSelectBtnEvent(e -> {
            showCustomerSelectPane();
        });

        appointmentPane.setSaveBtnEvent(e -> {
            if (!api.doesAppointmentExistInWindow(appointmentPane.getStart(),
                appointmentPane.getEnd())) {
                api.insertAppointment(appointmentPane.getCustomer(),
                    appointmentPane.getUser(),
                    appointmentPane.getTitle(),
                    appointmentPane.getType(),
                    appointmentPane.getDescription(),
                    appointmentPane.getLocation(),
                    appointmentPane.getContact(),
                    appointmentPane.getUrl(),
                    appointmentPane.getStart(),
                    appointmentPane.getEnd(),
                    user);

                calendarPane.refreshCalendar();
                changeScene(calendarScene);
            } else {
                appointmentPane.setErrorText("Appointment Overlaps" +
                        " with existing");
            }
        });

        appointmentPane.setCancelBtnEvent(e -> {
            changeScene(calendarScene);
        });

        changeScene(appointmentScene);
    }

    /**
     * Show appointment edit pane
     */
    private void showAppointmentEditPane() {
        appointmentPane = new PaneAppointmentCreateModify(api);
        appointmentScene = new Scene(appointmentPane, 480, 660);

        ORMAppointment appointment = calendarPane.getSelectedAppointment();
        appointmentPane.setIdTextField(Integer.toString(appointment.getAppointmentId()));
        appointmentPane.setCustomer(api.getCustomerById(appointment.getCustomerId()));
        appointmentPane.setTitle(appointment.getTitle());
        appointmentPane.setUser(api.getUserById(appointment.getUserId()));
        appointmentPane.setType(appointment.getType());
        appointmentPane.setDescription(appointment.getDescription());
        appointmentPane.setLocation(appointment.getLocation());
        appointmentPane.setContact(appointment.getContact());
        appointmentPane.setUrl(appointment.getUrl());
        appointmentPane.setStart(appointment.getStart());
        appointmentPane.setEnd(appointment.getEnd());

        appointmentPane.setSelectBtnEvent(e -> {
            showCustomerSelectPane();
        });

        appointmentPane.setSaveBtnEvent(e -> {
            if (!api.doesAppointmentExistInWindow(appointmentPane.getStart(),
                appointmentPane.getEnd())) {
                api.updateAppointment(appointment,
                    appointmentPane.getCustomer(),
                    appointmentPane.getUser(),
                    appointmentPane.getTitle(),
                    appointmentPane.getType(),
                    appointmentPane.getDescription(),
                    appointmentPane.getLocation(),
                    appointmentPane.getContact(),
                    appointmentPane.getUrl(),
                    appointmentPane.getStart(),
                    appointmentPane.getEnd(),
                    user);

                calendarPane.refreshCalendar();

                changeScene(calendarScene);


            } else {
                appointmentPane.setErrorText("Appointment Overlaps" +
                        " with existing");
            }
        });

        appointmentPane.setCancelBtnEvent(e -> {
            changeScene(calendarScene);
        });

        changeScene(appointmentScene);
    }

    /**
     * Show customer select pane
     */
    private void showCustomerSelectPane() {
        PaneSelectCustomer customerSelectPane = new PaneSelectCustomer(api);
        Scene customerSelectScene = new Scene(customerSelectPane, 580, 260);

        customerSelectPane.setSelectBtnEvent(event1 -> {
            ORMCustomer customer = customerSelectPane.getSelectedCustomer();
            appointmentPane.setCustomer(customer);
            changeScene(appointmentScene);
        });

        customerSelectPane.setCancelBtnEvent(event1 -> {
            changeScene(appointmentScene);
        });

        changeScene(customerSelectScene);
    }

    /**
     * Show customer add pane
     */
    private void showCustomerAddPane() {
        customerPane = new PaneCustomerCreateModify();
        customerScene = new Scene(customerPane, 480, 430);

        customerPane.setAddressAddEvent(event -> showAddressAddPane());

        customerPane.setAddressEditEvent(event -> showAddressEditPane());

        customerPane.setAddressSelectEvent(event -> showAddressSelectPane());

        customerPane.setSaveBtnEvent(event -> {
            api.insertCustomer(customerPane.getNameTextField(),
                customerPane.isActive(),
                customerPane.getAddress(),
                user.getUserName());

            customerManagerPane.updateCustomers(api.getCustomers());
            changeScene(customerManagerScene);
        });

        customerPane.setCancelBtnEvent(event -> {
            changeScene(customerManagerScene);
        });

        changeScene(customerScene);
    }

    /**
     * Show customer edit pane
     */
    private void showCustomerEditPane() {
        customerPane = new PaneCustomerCreateModify();
        customerScene = new Scene(customerPane, 480, 430);

        // Set the current values to the new pane
        ORMCustomer customer = customerManagerPane.getSelectedCustomer();
        customerPane.setActive(customer.getActive());
        customerPane.setNameTextField(customer.getCustomerName());
        ORMAddress address = api.getAddressById(customer.getAddressId());
        ORMCity city = api.getCityById(address.getCityId());
        ORMCountry country = api.getCountryById(city.getCountryId());
        customerPane.setAddress(address, city, country);

        customerPane.setAddressAddEvent(event -> showAddressAddPane());
        customerPane.setAddressEditEvent(event -> showAddressEditPane());
        customerPane.setAddressSelectEvent(event -> showAddressSelectPane());

        customerPane.setSaveBtnEvent(event -> {
            api.updateCustomer(customerPane.getNameTextField(),
                customerPane.isActive(),
                customerPane.getAddress(),
                customer,
                user.getUserName());

            customerManagerPane.updateCustomers(api.getCustomers());
            changeScene(customerManagerScene);
        });

        customerPane.setCancelBtnEvent(event -> {
            changeScene(customerManagerScene);
        });

        changeScene(customerScene);
    }

    /**
     * Show address edit pane
     */
    private void showAddressEditPane() {
        addressPane = new PaneAddressCreateModify(api);
        addressScene = new Scene(addressPane, 480, 480);
        ORMAddress addressToEdit = customerPane.getAddress();
        addressPane.setAddress1TextField(addressToEdit.getAddress());
        addressPane.setAddress2TextField(addressToEdit.getAddress2());
        addressPane.setPostalTextField(addressToEdit.getPostalCode());
        addressPane.setPhoneTextField(addressToEdit.getPhone());

        ORMCity currentCity = api.getCityById(addressToEdit.getCityId());
        ORMCountry currentCountry = api.getCountryById(currentCity.getCountryId());

        addressPane.setCountry(currentCountry);
        addressPane.setCity(currentCity);

        addressPane.setSaveBtnEvent(event -> {
            api.updateAddress(addressPane.getAddress1TextField(),
                addressPane.getAddress2TextField(),
                addressPane.getPostalTextField(),
                addressPane.getPhoneTextField(),
                addressToEdit,
                addressPane.getSelectedCity(),
                user.getUserName());

            ORMCity newCity = api.getCityById(addressToEdit.getCityId());
            ORMCountry newCountry = api.getCountryById(newCity.getCountryId());
            customerPane.setAddress(addressToEdit, newCity, newCountry);
            changeScene(customerScene);
        });

        addressPane.setCancelBtnEvent(event -> {
            changeScene(customerScene);
        });

        changeScene(addressScene);
    }

    /**
     * Show address select pane
     */
    private void showAddressSelectPane() {
        PaneSelectAddress addressSelectPane = new PaneSelectAddress(api);
        Scene addressSelectScene = new Scene(addressSelectPane, 580, 260);

        addressSelectPane.setSelectBtnEvent(event1 -> {
            ORMAddress address = addressSelectPane.getSelectedAddress();

            ORMCity city = api.getCityById(address.getCityId());
            ORMCountry country = api.getCountryById(city.getCountryId());
            customerPane.setAddress(address, city, country);
            changeScene(customerScene);
        });

        addressSelectPane.setCancelBtnEvent(event1 -> {
            changeScene(customerScene);
        });

        changeScene(addressSelectScene);
    }

    /**
     * Show address add pane
     */
    private void showAddressAddPane() {
        addressPane = new PaneAddressCreateModify(api);
        addressScene = new Scene(addressPane, 480, 480);

        addressPane.setSaveBtnEvent(event -> {
            ORMAddress address = api.insertAddress(addressPane.getAddress1TextField(),
                addressPane.getAddress2TextField(),
                addressPane.getPostalTextField(),
                addressPane.getPhoneTextField(),
                addressPane.getSelectedCity(),
                user.getUserName());

            ORMCity city = api.getCityById(address.getCityId());
            ORMCountry country = api.getCountryById(city.getCountryId());
            customerPane.setAddress(address, city, country);
            changeScene(customerScene);
        });

        addressPane.setCancelBtnEvent(event -> {
            changeScene(customerScene);
        });

        changeScene(addressScene);
    }

    /**
     * Entry point - launch JavaFX application
     * 
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
