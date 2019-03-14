/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwareiiassessment;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

class SQLAPI {
    private Connection conn;
    //mysql -u U05nwl -p53688557025 -h 52.206.157.109 -D U05nwl
    private final String DRIVER = "com.mysql.jdbc.Driver";
    private final String DATABASE = "U05nwl";
    private final String URL = "jdbc:mysql://52.206.157.109/";
    private final String USER = "U05nwl";
    private final String PASSWORD = "53688557025";
    private boolean connected = false;
    
    private HashMap<Integer, ORMCountry> countries;
    private HashMap<Integer, ORMCity> cities;
    private HashMap<Integer, ORMAddress> addresses;
    private HashMap<Integer, ORMCustomer> customers;

    SQLAPI() {   
        initConnection();
        loadDBIntoMemory();
    }

    public ResultSet runSQLCommand(String commandToExecute) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(commandToExecute);
            return rs;
//            ResultSetMetaData rsmd = rs.getMetaData();
//            int columnsNumber = rsmd.getColumnCount();
//            while (rs.next()) {
//                for (int i = 1; i <= columnsNumber; i++) {
//                    if (i > 1) System.out.print(",  ");
//                    String columnValue = rs.getString(i);
//                    System.out.print(columnValue + " " + rsmd.getColumnName(i));
//                }
//                System.out.println("");
//            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLAPI.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private void initConnection() {
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL + DATABASE, USER, PASSWORD);
            System.out.println("Connected to database : " + DATABASE); 
            connected = true;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } catch (ClassNotFoundException ec) {
            System.out.println("Class not found exception: " + ec.getMessage());
        }
    }

    private void loadDBIntoMemory() {
        loadCountiesIntoMemory();
        loadCitiesIntoMemory();
        loadAddressesIntoMemory();
        loadCustomersIntoMemory();
    }

    private void loadCitiesIntoMemory() {
        ResultSet rs = runSQLCommand("SELECT * FROM city;");
        cities = new HashMap<>();

        try {
            while (rs.next()) {
                ORMCity city = new ORMCity(
                    rs.getInt("cityId"),
                    rs.getString("city"),
                    rs.getInt("countryId"),

                    rs.getString("createDate"),
                    rs.getString("createdBy"),
                    (int) (rs.getTimestamp("lastUpdate").getTime() / 1000),
                    rs.getString("lastUpdateBy")
                );

                cities.put(city.getCityId(), city);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void loadCountiesIntoMemory() {
        ResultSet rs = runSQLCommand("select * from country;");
        countries = new HashMap<>();

        try {
            while (rs.next()) {
                ORMCountry country = new ORMCountry(
                    rs.getInt("countryId"),
                    rs.getString("country"),
                    rs.getString("createDate"),
                    rs.getString("createdBy"),
                    (int) (rs.getTimestamp("lastUpdate").getTime() / 1000),
                    rs.getString("lastUpdateBy")
                );

                countries.put(country.getCountryId(), country);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void loadAddressesIntoMemory() {
        ResultSet rs = runSQLCommand("select * from address;");
        addresses = new HashMap<>();

        try {
            while (rs.next()) {
                ORMAddress address = new ORMAddress(
                    rs.getInt("addressId"),
                    rs.getString("address"),
                    rs.getString("address2"),
                    rs.getInt("cityId"),
                    rs.getString("postalCode"),
                    rs.getString("phone"),
                    rs.getString("createDate"),
                    rs.getString("createdBy"),
                    (int) (rs.getTimestamp("lastUpdate").getTime() / 1000),
                    rs.getString("lastUpdateBy")
                );

                addresses.put(address.getAddressId(), address);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void loadCustomersIntoMemory() {
        ResultSet rs = runSQLCommand("select * from customer;");
        customers = new HashMap<>();

        try {
            while (rs.next()) {
                ORMCustomer customer = new ORMCustomer(
                    rs.getInt("customerId"),
                    rs.getString("customerName"),
                    rs.getInt("addressId"),
                    rs.getBoolean("active"),
                    rs.getString("createDate"),
                    rs.getString("createdBy"),
                    (int) (rs.getTimestamp("lastUpdate").getTime() / 1000),
                    rs.getString("lastUpdateBy")
                );

                customers.put(customer.getCustomerId(), customer);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ObservableList<ORMCity> getCountryCities(int countryId) {
        ObservableList<ORMCity> countryCities = FXCollections.observableArrayList();

        for (ORMCity city : cities.values()) {
            if (countryId == city.getCountryId()) {
                countryCities.add(city);
            }
        }

        return countryCities;
    }

    public ObservableList<ORMCountry> getCountries() {
        return FXCollections.observableArrayList(countries.values());
    }
    
    public ObservableList<ORMAddress> getAddresses() {
        return FXCollections.observableArrayList(addresses.values());
    }
    
    public ObservableList<ORMCustomer> getCustomers() {
        return FXCollections.observableArrayList(customers.values());
    }
    
    public ORMAddress insertAddress(String address1, String address2,
            String postalCode, String phone, ORMCity city, String user) {
        int maxId = Collections.max(addresses.keySet());

        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO address " + 
                "(addressId, address, address2, cityId, postalCode, phone, " + 
                "createDate, createdBy, lastUpdateBy) " +
                String.format("VALUES (%1$d, \"%2$s\", \"%3$s\", %4$d, " + 
                    "\"%5$s\", \"%6$s\", NOW(), \"%7$s\", \"%7$s\")", 
                    maxId + 1, address1, address2, city.getCityId(), postalCode,
                    phone, user));
        } catch (SQLException ex) {
            System.out.println("SQL INSERT ERROR: " + ex.getMessage());
        }

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from address" + 
                    " where addressId=" + Integer.toString(maxId + 1));
            
            rs.next();
            ORMAddress address = new ORMAddress(
                rs.getInt("addressId"),
                rs.getString("address"),
                rs.getString("address2"),
                rs.getInt("cityId"),
                rs.getString("postalCode"),
                rs.getString("phone"),
                rs.getString("createDate"),
                rs.getString("createdBy"),
                (int) (rs.getTimestamp("lastUpdate").getTime() / 1000),
                rs.getString("lastUpdateBy")
            );

            addresses.put(address.getAddressId(), address);
            return address;
        } catch (SQLException ex) {
            System.out.println("SQL SELECT ERROR: " + ex.getMessage());
            return null;
        }
    }
    
    public ORMCustomer insertCustomer(String customerName, boolean active,
            ORMAddress address, String user) {
        int maxId = Collections.max(customers.keySet());

        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO customer " + 
                "(customerId, customerName, addressId, active, " +
                "createdate, createdBy, lastUpdateBy) " +
                String.format("values (%1$d, \"%2$s\", \"%3$d\", \"%4$d\"," +
                              " NOW(), \"%5$s\", \"%5$s\")", 
                              maxId + 1, customerName, address.getAddressId(),
                              active ? 1 : 0, user));
        } catch (SQLException ex) {
            System.out.println("SQL INSERT ERROR: " + ex.getMessage());
        }
        
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from customer" +
                    " where customerId=" + Integer.toString(maxId + 1));
            
            rs.next();
            ORMCustomer customer = new ORMCustomer(
                rs.getInt("customerId"),
                rs.getString("customerName"),
                rs.getInt("addressId"),
                rs.getBoolean("active"),
                rs.getString("createDate"),
                rs.getString("createdBy"),
                (int) (rs.getTimestamp("lastUpdate").getTime() / 1000),
                rs.getString("lastUpdateBy")
            );
            
            customers.put(customer.getCustomerId(), customer);
            return customer;
        } catch (SQLException ex) {
            System.out.println("SQL SELECT ERROR: " + ex.getMessage());
            return null;
        }
    }

    public void updateAddress(String address1, String address2,
            String postalCode, String phone, ORMAddress address, ORMCity city, String user) {
        try {
            Statement stmt = conn.createStatement();
            address.setAddress(address1);
            address.setAddress2(address2);
            address.setCityId(city.getCityId());
            address.setPostalCode(postalCode);
            address.setPhone(phone);

            stmt.executeUpdate("UPDATE address " +
                    "SET address = \"" + address1 +
                    "\", address2 = \"" + address2 +
                    "\", cityId = " + city.getCityId() +
                    ", postalCode = \"" + postalCode +
                    "\", phone = \"" + phone +
                    "\" WHERE addressId = " + Integer.toString(address.getAddressId())
            );
        } catch (SQLException ex) {
            System.out.println("SQL update error: " + ex.getMessage());
        }
    }

    public void updateCustomer(String customerName, boolean active,
            ORMAddress address, ORMCustomer customer, String user) {
        try {
            Statement stmt = conn.createStatement();
            customer.setCustomerName(customerName);
            customer.setActive(active);
            customer.setAddressId(address.getAddressId());
            
            stmt.executeUpdate("UPDATE customer " +
                    "SET customerName = \"" + customerName +
                    "\", addressId = \"" + address.getAddressId() +
                    "\", active = " + Integer.toString(active ? 1 : 0) +
                    " WHERE customerId = " + Integer.toString(customer.getCustomerId())
            );
        } catch (SQLException ex) {
            System.out.println("SQL update error: " + ex.getMessage());
        }
    }

    public ORMCountry getCountryById(int countryId) {
        return countries.get(countryId);
    }

    public ORMCity getCityById(int cityId) {
        return cities.get(cityId);
    }

    public ORMAddress getAddressById(int addressId) {
        return addresses.get(addressId);
    }

    public void deleteCustomer(ORMCustomer selectedCustomer) {
        customers.remove(selectedCustomer.getCustomerId());
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM customer " +
                    "WHERE customerId = " + Integer.toString(selectedCustomer.getCustomerId())
            );
        } catch (SQLException ex) {
            System.out.println("SQL update error: " + ex.getMessage());
        }
    }

    HashMap<Integer, ArrayList<ORMAppointment>> getAppontmentsByDayOfWeek(int weekOfYear, TimeZone timeZone) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}


