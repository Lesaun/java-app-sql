/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwareiiassessment;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author lesaun
 */
public class Softwareiiassessment extends Application {
    private Stage primaryStage;
    private SQLAPI api;

    private PaneCustomerManager customerManagerPane;
    private Scene customerManagerScene;
    private PaneCustomerCreateModify currentCustomerPane;
    private Scene currentCustomerPaneScene;
    private PaneAddressCreateModify currentAddressPane;
    private Scene currentAddressPaneScene;

    private String user = "Lesaun";

    @Override
    public void start(Stage primaryStage) {
        api = new SQLAPI();

        customerManagerPane = new PaneCustomerManager(api);
        customerManagerScene = new Scene(customerManagerPane, 580, 260);

        customerManagerPane.setAddBtnEvent(event -> {
            customerManagerAddBtnEvent();
        });
        
        customerManagerPane.setModBtnEvent(event -> {
            customerManagerModBtnEvent();
        });
        
        customerManagerPane.setDelBtnEvent(event -> {
            api.deleteCustomer(customerManagerPane.getSelectedCustomer());
            customerManagerPane.updateCustomers(api.getCustomers());            
        });

        primaryStage.setResizable(false);
        primaryStage.setTitle("Appointment Manager");
        primaryStage.setScene(customerManagerScene);
        primaryStage.show();
        this.primaryStage = primaryStage;
    }

    private void customerManagerModBtnEvent() {
        currentCustomerPane = new PaneCustomerCreateModify();
        currentCustomerPaneScene = new Scene(currentCustomerPane, 480, 480);
        
        // Set the current values to the new pane
        ORMCustomer customer = customerManagerPane.getSelectedCustomer();
        currentCustomerPane.setActive(customer.getActive());
        currentCustomerPane.setNameTextField(customer.getCustomerName());
        ORMAddress address = api.getAddressById(customer.getAddressId());
        ORMCity city = api.getCityById(address.getCityId());
        ORMCountry country = api.getCountryById(city.getCountryId());
        currentCustomerPane.setAddress(address, city, country);

        currentCustomerPane.setAddressAddEvent(event -> {
            customerPaneAddBtnEvent();
        });

        currentCustomerPane.setAddressEditEvent(event -> {
            customerPaneEditBtnEvent();
        });

        currentCustomerPane.setAddressSelectEvent(event -> {
            customerPaneSelectBtnEvent();
        });

        
        currentCustomerPane.setSaveBtnEvent(event -> {
            api.updateCustomer(
                currentCustomerPane.getNameTextField(),
                currentCustomerPane.isActive(),
                currentCustomerPane.getAddress(),
                customer,
                user);
            
            customerManagerPane.updateCustomers(api.getCustomers());
            primaryStage.hide();
            primaryStage.setScene(customerManagerScene);
            primaryStage.show();
        });
        
        currentCustomerPane.setCancelBtnEvent(event -> {
            primaryStage.hide();
            primaryStage.setScene(customerManagerScene);
            primaryStage.show();
        });

        primaryStage.hide();
        primaryStage.setScene(currentCustomerPaneScene);
        primaryStage.show();
    }    

    private void customerManagerAddBtnEvent() {
        currentCustomerPane = new PaneCustomerCreateModify();
        currentCustomerPaneScene = new Scene(currentCustomerPane, 480, 480);

        currentCustomerPane.setAddressAddEvent(event -> {
            customerPaneAddBtnEvent();
        });

        currentCustomerPane.setAddressEditEvent(event -> {
            customerPaneEditBtnEvent();
        });

        currentCustomerPane.setAddressSelectEvent(event -> {
            customerPaneSelectBtnEvent();
        });

        currentCustomerPane.setSaveBtnEvent(event -> {
            api.insertCustomer(
                currentCustomerPane.getNameTextField(),
                currentCustomerPane.isActive(),
                currentCustomerPane.getAddress(),
                user);

            customerManagerPane.updateCustomers(api.getCustomers());
            primaryStage.hide();
            primaryStage.setScene(customerManagerScene);
            primaryStage.show();
        });
        
        currentCustomerPane.setCancelBtnEvent(event -> {
            primaryStage.hide();
            primaryStage.setScene(customerManagerScene);
            primaryStage.show();
        });

        primaryStage.hide();
        primaryStage.setScene(currentCustomerPaneScene);
        primaryStage.show();
    }
    
    private void customerPaneSelectBtnEvent() {
        PaneSelectAddress addressSelectPane = new PaneSelectAddress(api);
        Scene addressSelectScene = new Scene(addressSelectPane, 580, 260);

        addressSelectPane.setSelectBtnEvent(event1 -> {
            ORMAddress address = addressSelectPane.getSelectedAddress();

            ORMCity city = api.getCityById(address.getCityId());
            ORMCountry country = api.getCountryById(city.getCountryId());
            currentCustomerPane.setAddress(address, city, country);

            primaryStage.hide();
            primaryStage.setScene(currentCustomerPaneScene);
            primaryStage.show();
        });

        addressSelectPane.setCancelBtnEvent(event1 -> {
            primaryStage.hide();
            primaryStage.setScene(currentCustomerPaneScene);
            primaryStage.show();
        });


        primaryStage.hide();
        primaryStage.setScene(addressSelectScene);
        primaryStage.show();
    }

    private void customerPaneAddBtnEvent() {
        currentAddressPane = new PaneAddressCreateModify(api);
        currentAddressPaneScene = new Scene(currentAddressPane, 480, 480);
        
        currentAddressPane.setSaveBtnEvent(event -> {
            ORMAddress address = api.insertAddress(
                currentAddressPane.getAddress1TextField(),
                currentAddressPane.getAddress2TextField(),
                currentAddressPane.getPostalTextField(),
                currentAddressPane.getPhoneTextField(),
                currentAddressPane.getSelectedCity(),
                user);
            
            ORMCity city = api.getCityById(address.getCityId());
            ORMCountry country = api.getCountryById(city.getCountryId());
            currentCustomerPane.setAddress(address, city, country);

            primaryStage.hide();
            primaryStage.setScene(currentCustomerPaneScene);
            primaryStage.show();
        });

        currentAddressPane.setCancelBtnEvent(event -> {
            primaryStage.hide();
            primaryStage.setScene(currentCustomerPaneScene);
            primaryStage.show();
        });

        primaryStage.hide();
        primaryStage.setScene(currentAddressPaneScene);
        primaryStage.show();
    }

    private void customerPaneEditBtnEvent() {
        currentAddressPane = new PaneAddressCreateModify(api);
        currentAddressPaneScene = new Scene(currentAddressPane, 480, 480);
        ORMAddress addressToEdit = currentCustomerPane.getAddress();
        currentAddressPane.setAddress1TextField(addressToEdit.getAddress());
        currentAddressPane.setAddress2TextField(addressToEdit.getAddress2());
        currentAddressPane.setPostalTextField(addressToEdit.getPostalCode());
        currentAddressPane.setPhoneTextField(addressToEdit.getPhone());
        
        ORMCity currentCity = api.getCityById(addressToEdit.getCityId());
        ORMCountry currentCountry = api.getCountryById(currentCity.getCountryId());
        
        currentAddressPane.setCountry(currentCountry);
        currentAddressPane.setCity(currentCity);
      
        currentAddressPane.setSaveBtnEvent(event -> {
            api.updateAddress(
                currentAddressPane.getAddress1TextField(),
                currentAddressPane.getAddress2TextField(),
                currentAddressPane.getPostalTextField(),
                currentAddressPane.getPhoneTextField(),
                addressToEdit,
                currentAddressPane.getSelectedCity(),
                user);
            
            ORMCity newCity = api.getCityById(addressToEdit.getCityId());
            ORMCountry newCountry = api.getCountryById(newCity.getCountryId());
            currentCustomerPane.setAddress(addressToEdit, newCity, newCountry);

            primaryStage.hide();
            primaryStage.setScene(currentCustomerPaneScene);
            primaryStage.show();
        });

        currentAddressPane.setCancelBtnEvent(event -> {
            primaryStage.hide();
            primaryStage.setScene(currentCustomerPaneScene);
            primaryStage.show();
        });

        primaryStage.hide();
        primaryStage.setScene(currentAddressPaneScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }    
}
