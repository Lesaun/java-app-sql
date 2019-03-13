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

    private CustomerManagerPane customerManagerPane;
    private Scene customerManagerScene;
    private CustomerCreateModifyPane currentCustomerPane;
    private Scene currentCustomerPaneScene;
    private AddressCreateModifyPane currentAddressPane;
    private Scene currentAddressPaneScene;
    private CityCreateModifyPane currentCityPane;
    private Scene currentCityPaneScene;
    private CountryCreateModifyPane currentCountryPane;
    private Scene currentCountryPaneScene;

    private String user = "Lesaun";

    @Override
    public void start(Stage primaryStage) {
        api = new SQLAPI();

        customerManagerPane = new CustomerManagerPane(api);
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
        currentCustomerPane = new CustomerCreateModifyPane();
        currentCustomerPaneScene = new Scene(currentCustomerPane, 480, 480);
        Customer customer = customerManagerPane.getSelectedCustomer();
        currentCustomerPane.setActive(customer.getActive());
        currentCustomerPane.setNameTextField(customer.getCustomerName());
        
        Address address = api.getAddressById(customer.getAddressId());
        City city = api.getCityById(address.getCityId());
        Country country = api.getCountryById(city.getCountryId());
        currentCustomerPane.setAddress(address, city, country);

        currentCustomerPane.setAddressAddEvent(event -> {
            customerPaneAddBtnEvent();
        });

        currentCustomerPane.setAddressEditEvent(event -> {
            customerPaneEditBtnEvent();
        });

        currentCustomerPane.setAddressSelectEvent(event -> {
            SelectAddressPane addressSelectPane = new SelectAddressPane(api);
            Scene addressSelectScene = new Scene(addressSelectPane, 580, 260);
            
            addressSelectPane.setSelectBtnEvent(event1 -> {
                Address address1 = addressSelectPane.getSelectedAddress();
                City city1 = api.getCityById(address1.getCityId());
                Country country1 = api.getCountryById(city1.getCountryId());
                currentCustomerPane.setAddress(address1, city1, country1);

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
        currentCustomerPane = new CustomerCreateModifyPane();
        currentCustomerPaneScene = new Scene(currentCustomerPane, 480, 480);

        currentCustomerPane.setAddressAddEvent(event -> {
            customerPaneAddBtnEvent();
        });

        currentCustomerPane.setAddressEditEvent(event -> {
            customerPaneEditBtnEvent();
        });

        currentCustomerPane.setAddressSelectEvent(event -> {
            SelectAddressPane addressSelectPane = new SelectAddressPane(api);
            Scene addressSelectScene = new Scene(addressSelectPane, 580, 260);
            
            addressSelectPane.setSelectBtnEvent(event1 -> {
                Address address = addressSelectPane.getSelectedAddress();
                
                City city = api.getCityById(address.getCityId());
                Country country = api.getCountryById(city.getCountryId());
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
        });

        
        currentCustomerPane.setSaveBtnEvent(event -> {
            Customer customer = api.insertCustomer(
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

    private void customerPaneAddBtnEvent() {
        currentAddressPane = new AddressCreateModifyPane();
        currentAddressPaneScene = new Scene(currentAddressPane, 480, 480);

        currentAddressPane.setCityAddEvent(event -> {
            addressPaneAddBtnEvent();
        });

        currentAddressPane.setCityEditEvent(event -> {
            if (currentAddressPane.isCitySelected())
                addressPaneEditBtnEvent(currentAddressPane.getSelectedCity(),
                        currentAddressPane.getSelectedCountry());
        });
        
        currentAddressPane.setCitySelectEvent(event -> {
            SelectCityPane citySelectPane = new SelectCityPane(api);
            Scene SelectCityScene = new Scene(citySelectPane, 580, 260);
            
            citySelectPane.setSelectBtnEvent(event1 -> {
                currentAddressPane.setSelectedCityCountry(citySelectPane.getSelectedCity(),
                    api.getCountryById(citySelectPane.getSelectedCity().getCountryId()));

                primaryStage.hide();
                primaryStage.setScene(currentAddressPaneScene);
                primaryStage.show();
            });
            
            citySelectPane.setCancelBtnEvent(event1 -> {
                primaryStage.hide();
                primaryStage.setScene(currentAddressPaneScene);
                primaryStage.show();
            });
            

            primaryStage.hide();
            primaryStage.setScene(SelectCityScene);
            primaryStage.show();
        });
        
        currentAddressPane.setSaveBtnEvent(event -> {
            Address address = api.insertAddress(
                currentAddressPane.getAddress1TextField(),
                currentAddressPane.getAddress2TextField(),
                currentAddressPane.getPostalTextField(),
                currentAddressPane.getPhoneTextField(),
                currentAddressPane.getSelectedCity(),
                user);
            
            City city = api.getCityById(address.getCityId());
            Country country = api.getCountryById(city.getCountryId());
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
        currentAddressPane = new AddressCreateModifyPane();
        currentAddressPaneScene = new Scene(currentAddressPane, 480, 480);
        Address addressToEdit = currentCustomerPane.getAddress();
        currentAddressPane.setAddress1TextField(addressToEdit.getAddress());
        currentAddressPane.setAddress2TextField(addressToEdit.getAddress2());
        currentAddressPane.setPostalTextField(addressToEdit.getPostalCode());
        currentAddressPane.setPhoneTextField(addressToEdit.getPhone());
        
        City currentCity = api.getCityById(addressToEdit.getCityId());
        Country currentCountry = api.getCountryById(currentCity.getCountryId());
        currentAddressPane.setSelectedCityCountry(currentCity, currentCountry);

        currentAddressPane.setCityAddEvent(event -> {
            addressPaneAddBtnEvent();
        });

        currentAddressPane.setCityEditEvent(event -> {
            if (currentAddressPane.isCitySelected())
                addressPaneEditBtnEvent(currentAddressPane.getSelectedCity(),
                        currentAddressPane.getSelectedCountry());
        });

        currentAddressPane.setCitySelectEvent(event -> {
            SelectCityPane citySelectPane = new SelectCityPane(api);
            Scene SelectCityScene = new Scene(citySelectPane, 580, 260);
            
            citySelectPane.setSelectBtnEvent(event1 -> {
                currentAddressPane.setSelectedCityCountry(citySelectPane.getSelectedCity(),
                    api.getCountryById(citySelectPane.getSelectedCity().getCountryId()));

                primaryStage.hide();
                primaryStage.setScene(currentAddressPaneScene);
                primaryStage.show();
            });
            
            citySelectPane.setCancelBtnEvent(event1 -> {
                primaryStage.hide();
                primaryStage.setScene(currentAddressPaneScene);
                primaryStage.show();
            });
            

            primaryStage.hide();
            primaryStage.setScene(SelectCityScene);
            primaryStage.show();
        });
        
        currentAddressPane.setSaveBtnEvent(event -> {
            api.updateAddress(
                currentAddressPane.getAddress1TextField(),
                currentAddressPane.getAddress2TextField(),
                currentAddressPane.getPostalTextField(),
                currentAddressPane.getPhoneTextField(),
                addressToEdit,
                currentAddressPane.getSelectedCity(),
                user);
            
            City newCity = api.getCityById(addressToEdit.getCityId());
            Country newCountry = api.getCountryById(newCity.getCountryId());
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

    private void addressPaneAddBtnEvent() {
        currentCityPane = new CityCreateModifyPane(api.getCountries());
        currentCityPaneScene = new Scene(currentCityPane, 480, 330);

        currentCityPane.setCountryAddEvent(event -> {
            cityPaneAddBtnEvent();
        });
        
        currentCityPane.setCountryEditEvent(event -> {
            if (currentCityPane.isCountrySelected())
                cityPaneEditBtnEvent(currentCityPane.getCountry());
        });
        
        currentCityPane.setSaveBtnEvent(event -> {
            if (currentCityPane.isCountrySelected()) {
                City city = api.insertCity(currentCityPane.getNameTextField(),
                        currentCityPane.getCountry(), user);
                currentAddressPane.setSelectedCityCountry(city, 
                        currentCityPane.getCountry());
                

                primaryStage.hide();
                primaryStage.setScene(currentAddressPaneScene);
                primaryStage.show();
            }
        });
        
        
        currentCityPane.setCancelBtnEvent(event -> {
            primaryStage.hide();
            primaryStage.setScene(currentAddressPaneScene);
            primaryStage.show();
        });

        primaryStage.hide();
        primaryStage.setScene(currentCityPaneScene);
        primaryStage.show();
    }

    private void addressPaneEditBtnEvent(City city, Country country) {
        currentCityPane = new CityCreateModifyPane(api.getCountries());
        currentCityPaneScene = new Scene(currentCityPane, 480, 330);
        currentCityPane.setIdTextField(Integer.toString(city.getCityId()));
        currentCityPane.setNameTextField(city.getCity());
        currentCityPane.updateCountries(api.getCountries());
        currentCityPane.setCountry(country);
        

        currentCityPane.setCountryAddEvent(event -> {
            cityPaneAddBtnEvent();
        });
        
        currentCityPane.setCountryEditEvent(event -> {
            if (currentCityPane.isCountrySelected())
                cityPaneEditBtnEvent(currentCityPane.getCountry());
        });
        
        currentCityPane.setSaveBtnEvent(event -> {
            if (currentCityPane.isCountrySelected()) {
                api.updateCity(currentCityPane.getNameTextField(),
                        city,
                        currentCityPane.getCountry(),
                        user);
                currentAddressPane.setSelectedCityCountry(city, 
                        currentCityPane.getCountry());
                

                primaryStage.hide();
                primaryStage.setScene(currentAddressPaneScene);
                primaryStage.show();
            }
        });
        
        currentCityPane.setCancelBtnEvent(event -> {
            primaryStage.hide();
            primaryStage.setScene(currentAddressPaneScene);
            primaryStage.show();
        });

        primaryStage.hide();
        primaryStage.setScene(currentCityPaneScene);
        primaryStage.show();
    }

    private void cityPaneAddBtnEvent() {
        currentCountryPane = new CountryCreateModifyPane();
        currentCountryPaneScene = new Scene(currentCountryPane, 480, 230);
        
        currentCountryPane.setSaveBtnEvent(event -> {
            Country country = api.insertCountry(currentCountryPane.getNameTextField(), user);
            currentCityPane.updateCountries(api.getCountries());
            currentCityPane.setCountry(country);

            primaryStage.hide();
            primaryStage.setScene(currentCityPaneScene);
            primaryStage.show();
        });
        
        currentCountryPane.setCancelBtnEvent(event -> {
            primaryStage.hide();
            primaryStage.setScene(currentCityPaneScene);
            primaryStage.show();
        });

        primaryStage.hide();
        primaryStage.setScene(currentCountryPaneScene);
        primaryStage.show();
    }

    private void cityPaneEditBtnEvent(Country country) {
        currentCountryPane = new CountryCreateModifyPane();
        currentCountryPaneScene = new Scene(currentCountryPane, 480, 230);
        currentCountryPane.setCountry(country);
        
        currentCountryPane.setSaveBtnEvent(event -> {
            api.updateCountry(currentCountryPane.getNameTextField(), country, user);
            currentCityPane.updateCountries(api.getCountries());
            currentCityPane.setCountry(country);

            primaryStage.hide();
            primaryStage.setScene(currentCityPaneScene);
            primaryStage.show();
        });
        
        currentCountryPane.setCancelBtnEvent(event -> {
            primaryStage.hide();
            primaryStage.setScene(currentCityPaneScene);
            primaryStage.show();
        });

        primaryStage.hide();
        primaryStage.setScene(currentCountryPaneScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }    
}
