package softwareiiassessment;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author lesaun
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

    private String user;

    @Override
    public void start(Stage primaryStage) {
        api = new SQLAPI();
        this.primaryStage = primaryStage;
        primaryStage.setResizable(false);
        primaryStage.setTitle("Appointment Manager");

        showLoginPane();
    }

    private void showLoginPane() {
        loginPane = new PaneLogin();
        loginScene = new Scene(loginPane, 480, 210);
        loginPane.setLocale(Locale.getDefault());

        loginPane.setLoginBtnEvent(e -> {
            user = loginPane.getUsername();
            if (api.isUsernamePasswordValid(user, loginPane.getPassword())) {
                showCalendarPane();
            }
        });
        loginPane.setExitBtnEvent(e -> primaryStage.close());

        primaryStage.setScene(loginScene);
        primaryStage.sizeToScene();
        primaryStage.show();
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

        calendarPane.setLogoutBtnEvent(e -> showLoginPane());

        primaryStage.hide();
        primaryStage.setScene(calendarScene);
        primaryStage.sizeToScene();
        primaryStage.show();
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
            primaryStage.setScene(calendarScene);
            primaryStage.sizeToScene();
            primaryStage.show();
        });

        primaryStage.setScene(customerManagerScene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    private void showAppointmentAddPane() {
        appointmentPane = new PaneAppointmentCreateModify(api);
        appointmentScene = new Scene(appointmentPane, 480, 600);

        appointmentPane.setSelectBtnEvent(e -> {
            showCustomerSelectPane();
        });

        appointmentPane.setSaveBtnEvent(e -> {
            if (!api.doesAppointmentExistInWindow(appointmentPane.getStart(),
                appointmentPane.getEnd())) {
                api.insertAppointment(appointmentPane.getCustomer(),
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
                primaryStage.hide();
                primaryStage.setScene(calendarScene);
                primaryStage.sizeToScene();
                primaryStage.show();
            } else {
                appointmentPane.setErrorText("Appointment Overlaps" +
                        " with existing");
            }
        });

        appointmentPane.setCancelBtnEvent(e -> {
            primaryStage.hide();
            primaryStage.setScene(calendarScene);
            primaryStage.sizeToScene();
            primaryStage.show();
        });

        primaryStage.hide();
        primaryStage.setScene(appointmentScene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    private void showAppointmentEditPane() {
        appointmentPane = new PaneAppointmentCreateModify(api);
        appointmentScene = new Scene(appointmentPane, 480, 630);

        ORMAppointment appointment = calendarPane.getSelectedAppointment();
        appointmentPane.setIdTextField(Integer.toString(appointment.getAppointmentId()));
        appointmentPane.setCustomer(api.getCustomerById(appointment.getCustomerId()));
        appointmentPane.setTitle(appointment.getTitle());
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
                primaryStage.hide();
                primaryStage.setScene(calendarScene);
                primaryStage.sizeToScene();
                primaryStage.show();
            } else {
                appointmentPane.setErrorText("Appointment Overlaps" +
                        " with existing");
            }
        });

        appointmentPane.setCancelBtnEvent(e -> {
            primaryStage.hide();
            primaryStage.setScene(calendarScene);
            primaryStage.sizeToScene();
            primaryStage.show();
        });

        primaryStage.hide();
        primaryStage.setScene(appointmentScene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    private void showCustomerSelectPane() {
        PaneSelectCustomer customerSelectPane = new PaneSelectCustomer(api);
        Scene customerSelectScene = new Scene(customerSelectPane, 580, 260);

        customerSelectPane.setSelectBtnEvent(event1 -> {
            ORMCustomer customer = customerSelectPane.getSelectedCustomer();

            appointmentPane.setCustomer(customer);

            primaryStage.hide();
            primaryStage.setScene(appointmentScene);
            primaryStage.sizeToScene();
            primaryStage.show();
        });

        customerSelectPane.setCancelBtnEvent(event1 -> {
            primaryStage.hide();
            primaryStage.setScene(appointmentScene);
            primaryStage.sizeToScene();
            primaryStage.show();
        });


        primaryStage.hide();
        primaryStage.setScene(customerSelectScene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    private void showCustomerAddPane() {
        customerPane = new PaneCustomerCreateModify();
        customerScene = new Scene(customerPane, 480, 480);

        customerPane.setAddressAddEvent(event -> showAddressAddPane());

        customerPane.setAddressEditEvent(event -> showAddressEditPane());

        customerPane.setAddressSelectEvent(event -> showAddressSelectPane());

        customerPane.setSaveBtnEvent(event -> {
            api.insertCustomer(customerPane.getNameTextField(),
                customerPane.isActive(),
                customerPane.getAddress(),
                user);

            customerManagerPane.updateCustomers(api.getCustomers());
            primaryStage.hide();
            primaryStage.setScene(customerManagerScene);
            primaryStage.sizeToScene();
            primaryStage.show();
        });

        customerPane.setCancelBtnEvent(event -> {
            primaryStage.hide();
            primaryStage.setScene(customerManagerScene);
            primaryStage.sizeToScene();
            primaryStage.show();
        });

        primaryStage.hide();
        primaryStage.setScene(customerScene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    private void showCustomerEditPane() {
        customerPane = new PaneCustomerCreateModify();
        customerScene = new Scene(customerPane, 480, 480);

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
                user);

            customerManagerPane.updateCustomers(api.getCustomers());
            primaryStage.hide();
            primaryStage.setScene(customerManagerScene);
            primaryStage.sizeToScene();
            primaryStage.show();
        });

        customerPane.setCancelBtnEvent(event -> {
            primaryStage.hide();
            primaryStage.setScene(customerManagerScene);
            primaryStage.sizeToScene();
            primaryStage.show();
        });

        primaryStage.hide();
        primaryStage.setScene(customerScene);
        primaryStage.sizeToScene();
        primaryStage.show();
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
                user);

            ORMCity newCity = api.getCityById(addressToEdit.getCityId());
            ORMCountry newCountry = api.getCountryById(newCity.getCountryId());
            customerPane.setAddress(addressToEdit, newCity, newCountry);

            primaryStage.hide();
            primaryStage.setScene(customerScene);
            primaryStage.sizeToScene();
            primaryStage.show();
        });

        addressPane.setCancelBtnEvent(event -> {
            primaryStage.hide();
            primaryStage.setScene(customerScene);
            primaryStage.sizeToScene();
            primaryStage.show();
        });

        primaryStage.hide();
        primaryStage.setScene(addressScene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    private void showAddressSelectPane() {
        PaneSelectAddress addressSelectPane = new PaneSelectAddress(api);
        Scene addressSelectScene = new Scene(addressSelectPane, 580, 260);

        addressSelectPane.setSelectBtnEvent(event1 -> {
            ORMAddress address = addressSelectPane.getSelectedAddress();

            ORMCity city = api.getCityById(address.getCityId());
            ORMCountry country = api.getCountryById(city.getCountryId());
            customerPane.setAddress(address, city, country);

            primaryStage.hide();
            primaryStage.setScene(customerScene);
            primaryStage.sizeToScene();
            primaryStage.show();
        });

        addressSelectPane.setCancelBtnEvent(event1 -> {
            primaryStage.hide();
            primaryStage.setScene(customerScene);
            primaryStage.sizeToScene();
            primaryStage.show();
        });

        primaryStage.hide();
        primaryStage.setScene(addressSelectScene);
        primaryStage.sizeToScene();
        primaryStage.show();
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
                user);

            ORMCity city = api.getCityById(address.getCityId());
            ORMCountry country = api.getCountryById(city.getCountryId());
            customerPane.setAddress(address, city, country);

            primaryStage.hide();
            primaryStage.setScene(customerScene);
            primaryStage.sizeToScene();
            primaryStage.show();
        });

        addressPane.setCancelBtnEvent(event -> {
            primaryStage.hide();
            primaryStage.setScene(customerScene);
            primaryStage.sizeToScene();
            primaryStage.show();
        });

        primaryStage.hide();
        primaryStage.setScene(addressScene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
