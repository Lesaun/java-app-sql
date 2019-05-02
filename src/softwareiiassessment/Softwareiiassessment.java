package softwareiiassessment;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


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

    private void changeScene(Scene newScene) {
        primaryStage.hide();
        primaryStage.setScene(newScene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    private void showLoginPane() {
        loginPane = new PaneLogin();
        loginScene = new Scene(loginPane, 480, 220);
        loginPane.setLocale(Locale.getDefault());

        loginPane.setLoginBtnEvent(e -> {
            String userName = loginPane.getUsername();

            if (api.isUsernamePasswordValid(userName, loginPane.getPassword())) {
                user = api.getUserByName(userName);
                if (api.isAppointmentWithin15Min(LocalDateTime.now(ZoneId.of("UTC")))) {
                    PaneEventAlert apptAlert = new PaneEventAlert();
                    Scene apptAlertScene = new Scene(apptAlert, 480, 120);
                    apptAlert.setOkBtnEvent(event -> showCalendarPane());
                    changeScene(apptAlertScene);
                } else {
                    showCalendarPane();
                }
            }
        });

        loginPane.setExitBtnEvent(e -> primaryStage.close());
        changeScene(loginScene);
    }

    private void showCalendarPane() {
        calendarPane = new PaneCalendar(api, LocalDateTime.now());
        calendarScene = new Scene(calendarPane, 745, 382);

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
    
    private void showReportsPane() {
        reportPane = new PaneReport();
        reportScene = new Scene(reportPane, 280, 310);
        
        reportPane.setUserScheduleBtnEvent(e -> {});
        reportPane.setNumApptByMonBtnEvent(e -> {});
        reportPane.setApptsByCustomerBtnEvent(e -> {
            System.out.println(api.appointmentTypesByMonthReport());
        });
        reportPane.setDoneBtnEvent(e -> changeScene(calendarScene));
        
        changeScene(reportScene);        
    }

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

    public static void main(String[] args) {
        launch(args);
    }
}
