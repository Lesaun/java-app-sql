package softwareiiassessment;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * API for SQL database
 *
 * @author Lesaun
 */
class SQLAPI {
    private Connection conn;
    //mysql -u U05nwl -p53688557025 -h 52.206.157.109 -D U05nwl
    private final String DRIVER = "com.mysql.jdbc.Driver";
    private final String DATABASE = "U05nwl";
    private final String URL = "jdbc:mysql://52.206.157.109/";
    private final String USER = "U05nwl";
    private final String PASSWORD = "53688557025";
    private boolean connected = false;

    private TemporalField woy = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear();
    private HashMap<Integer, ORMCountry> countries;
    private HashMap<Integer, ORMCity> cities;
    private HashMap<Integer, ORMAddress> addresses;
    private HashMap<Integer, ORMCustomer> customers;
    private HashMap<Integer, ORMAppointment> appointmentsById;
    private ObservableList<ORMUser> users; 
    private HashMap<SimpleImmutableEntry<Integer, Integer>,
        ArrayList<ORMAppointment>> appointmentsByYearWeek;

    SQLAPI() {
        initConnection();
        loadDBIntoMemory();
    }

    /**
     * Run sql query command
     * 
     * @param commandToExecute command to execute
     * @return command result set
     */
    public ResultSet runSQLQueryCommand(String commandToExecute) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(commandToExecute);
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(SQLAPI.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * Initiate with SQL connection
     */
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

    /**
     * Load database into memory
     */
    private void loadDBIntoMemory() {
        loadCountiesIntoMemory();
        loadCitiesIntoMemory();
        loadAddressesIntoMemory();
        loadCustomersIntoMemory();
        loadAppointmentsIntoMemory();
        loadUsersIntoMemory();
    }

    /**
     * Load cities into memory
     */
    private void loadCitiesIntoMemory() {
        ResultSet rs = runSQLQueryCommand("SELECT * FROM city;");
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

    /**
     * Load countries into memory
     */
    private void loadCountiesIntoMemory() {
        ResultSet rs = runSQLQueryCommand("select * from country;");
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

    /**
     * Load addresses into memory
     */
    private void loadAddressesIntoMemory() {
        ResultSet rs = runSQLQueryCommand("select * from address;");
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
    
    /**
     * Load customers into memory
     */
    private void loadCustomersIntoMemory() {
        ResultSet rs = runSQLQueryCommand("select * from customer;");
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

    /**
     * Load appointments into memory
     */
    private void loadAppointmentsIntoMemory() {
        ResultSet rs = runSQLQueryCommand("select * from appointment;");
        appointmentsById = new HashMap<>();
        appointmentsByYearWeek = new HashMap<>();

        try {
            while (rs.next()) {
                ORMAppointment appointment = new ORMAppointment(
                    rs.getInt("appointmentId"),
                    rs.getInt("customerId"),
                    rs.getInt("userId"),
                    rs.getString("title"),
                    rs.getString("type"),
                    rs.getString("description"),
                    rs.getString("location"),
                    rs.getString("contact"),
                    rs.getString("url"),
                    rs.getTimestamp("start").toLocalDateTime(),
                    rs.getTimestamp("end").toLocalDateTime(),
                    rs.getString("createDate"),
                    rs.getString("createdBy"),
                    (int) (rs.getTimestamp("lastUpdate").getTime() / 1000),
                    rs.getString("lastUpdateBy"));

                appointmentsById.put(appointment.getAppointmentId(), appointment);

                SimpleImmutableEntry yearWeekEntry = new SimpleImmutableEntry(
                    appointment.getStart().getYear(),
                    appointment.getStart().get(woy));

                if (!appointmentsByYearWeek.containsKey(yearWeekEntry)) {
                    appointmentsByYearWeek.put(yearWeekEntry, new ArrayList<>());
                }

                appointmentsByYearWeek.get(yearWeekEntry).add(appointment);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Load users into memory
     */
    private void loadUsersIntoMemory() {
        ResultSet rs = runSQLQueryCommand("select * from user;");
        users = FXCollections.observableArrayList();


        try {
            while (rs.next()) {
                ORMUser user = new ORMUser(
                    rs.getInt("userId"),
                    rs.getString("userName")
                );

                users.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Checks if username and password are valid
     * 
     * @param username username to check
     * @param password password to check
     * @return is username and password valid
     */
    public boolean isUsernamePasswordValid(String username, String password) {
        ResultSet rs = runSQLQueryCommand("select * from user where " +
                       "lower(username) = \"" + username.toLowerCase() + "\";");

        try {
            while (rs.next()) {
                if (rs.getString("password").equals(password)) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLAPI.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }
    
    /**
     * Returns list of users
     * 
     * @return list of users
     */
    public ObservableList<ORMUser> getUsers() {
        return users;
    }
    
    /**
     * Return user by username
     * 
     * @param username username to get user of
     * @return user object of username
     */
    public ORMUser getUserByName(String username) {
        ResultSet rs = runSQLQueryCommand("select * from user where " +
                       "lower(userName) = \"" + username.toLowerCase() + "\";");

        try {
            while (rs.next()) {
                return new ORMUser(rs.getInt("userId"), rs.getString("username"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLAPI.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     * Return appointments in week of year
     * 
     * @param weekOfYear week of year of appointment to return
     * @param year year of appointments
     * @return appointment in week of year
     */
    public ObservableList<ORMAppointment> getAppointmentsInWeek(int weekOfYear, int year) {
        SimpleImmutableEntry yearWeekEntry = new SimpleImmutableEntry(year, weekOfYear);

        if (appointmentsByYearWeek.containsKey(yearWeekEntry)) {
            return FXCollections.observableArrayList(appointmentsByYearWeek.get(yearWeekEntry));
        }
        return FXCollections.observableArrayList();
    }

    /**
     * Return appointments in month
     * 
     * @param monthOfYear month of appointments to return
     * @param year year of appointments to return
     * @return appointment in month
     */
    public ObservableList<ORMAppointment> getAppointmentsByMonth(int monthOfYear, int year) {
        ObservableList<ORMAppointment> appointmentsInMonth = FXCollections.observableArrayList();

        Calendar cal = new GregorianCalendar(year, monthOfYear - 1, 1);
        int minWeek = cal.get(Calendar.WEEK_OF_YEAR);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        int maxWeek = cal.get(Calendar.WEEK_OF_YEAR);

        for (int i = minWeek; i < maxWeek; i++) {
            SimpleImmutableEntry yearWeekEntry = new SimpleImmutableEntry(year, i);

            if (appointmentsByYearWeek.containsKey(yearWeekEntry)) {
                appointmentsInMonth.addAll(appointmentsByYearWeek.get(yearWeekEntry));
            }
        }

        return appointmentsInMonth;
    }

    /**
     * Return cities in country
     * 
     * @param countryId country to get cities of
     * @return cities in country
     */
    public ObservableList<ORMCity> getCountryCities(int countryId) {
        ObservableList<ORMCity> countryCities = FXCollections.observableArrayList();

        for (ORMCity city : cities.values()) {
            if (countryId == city.getCountryId()) {
                countryCities.add(city);
            }
        }

        return countryCities;
    }

    /**
     * Return countries
     * 
     * @return countries
     */
    public ObservableList<ORMCountry> getCountries() {
        return FXCollections.observableArrayList(countries.values());
    }

    /**
     * Return addresses
     * 
     * @return addresses
     */
    public ObservableList<ORMAddress> getAddresses() {
        return FXCollections.observableArrayList(addresses.values());
    }

    /** 
     * Return customers
     * 
     * @return customers
     */
    public ObservableList<ORMCustomer> getCustomers() {
        return FXCollections.observableArrayList(customers.values());
    }

    /**
     * Insert address into database
     * 
     * @param address1
     * @param address2
     * @param postalCode
     * @param phone
     * @param city
     * @param user
     * @return inserted address
     */
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

    /**
     * Insert customer into database
     * 
     * @param customerName
     * @param active
     * @param address
     * @param user
     * @return insert customer into database
     */
    public ORMCustomer insertCustomer(String customerName, boolean active,
            ORMAddress address, String user) {
        int maxId = Collections.max(customers.keySet());

        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO customer " +
                "(customerId, customerName, addressId, active, " +
                "createDate, createdBy, lastUpdateBy) " +
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

    /**
     * Update address in database
     * 
     * @param address1
     * @param address2
     * @param postalCode
     * @param phone
     * @param address
     * @param city
     * @param user 
     */
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

    /**
     * Update customer in database
     * 
     * @param customerName
     * @param active
     * @param address
     * @param customer
     * @param user 
     */
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
                    ", lastUpdateBy = \"" + user + "\" " +
                    " WHERE customerId = " + Integer.toString(customer.getCustomerId())
            );
        } catch (SQLException ex) {
            System.out.println("SQL update error: " + ex.getMessage());
        }
    }
    
/**
 * Update appointment in database
 * 
 * @param appointment
 * @param customer
 * @param user
 * @param title
 * @param type
 * @param description
 * @param location
 * @param contact
 * @param url
 * @param start
 * @param end
 * @param updateUser 
 */
    public void updateAppointment(ORMAppointment appointment,
            ORMCustomer customer, ORMUser user, String title, String type, String description,
            String location, String contact, String url, LocalDateTime start,
            LocalDateTime end, ORMUser updateUser) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        appointment.setCustomerId(customer.getCustomerId());
        appointment.setUserId(user.getUserId());
        appointment.setTitle(title);
        appointment.setType(type);
        appointment.setDescription(description);
        appointment.setLocation(location);
        appointment.setContact(contact);
        appointment.setUrl(url);
        appointment.setStart(start);
        appointment.setEnd(end);

        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("UPDATE appointment " +
                "SET customerId = " + Integer.toString(customer.getCustomerId()) +
                ", userId = " + user.getUserId() +
                ", title = \"" + title +
                "\", type = \"" + type +
                "\", description = \"" + description +
                "\", location = \"" + location +
                "\", contact = \"" + contact +
                "\", url = \"" + url +
                "\", start = \"" + start.format(formatter) +
                "\", end = \"" + end.format(formatter) +
                "\", lastUpdateBy = \"" + updateUser.getUserName() + "\" " +
                "WHERE appointmentId = " +
                Integer.toString(appointment.getAppointmentId()));
        } catch (SQLException ex) {
            System.out.println("SQL INSERT ERROR: " + ex.getMessage());
        }
    }

    /**
     * Returns the consultant schedule report
     * 
     * @return the consultant schedule report
     */
    public String consultantScheduleReport() {
        String retVal = "";
       
        try {   
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from appointment ORDER BY userId, start ASC");
            int lastUser = -1;
            
            while (rs.next()) {
                int userId =  rs.getInt("userId");
                
                if (lastUser != userId) {
                    retVal += "\n\nConsultant " + (
                            (userId == 0) ? "Unassigned" :
                            this.getUserById(userId).getUserName()) + ": \n";
                    lastUser = userId;
                }
                
                ZonedDateTime utcTime = rs.getTimestamp("start")
                        .toLocalDateTime().atZone(ZoneId.of("UTC"));
                ZonedDateTime localTime = utcTime.withZoneSameInstant(
                        ZoneId.systemDefault());
                String formattedTime = localTime.format(
                        DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
                
                retVal += formattedTime + " : " + rs.getString("title") + "\n";
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLAPI.class.getName()).log(Level.SEVERE, null, ex);
        }

        return retVal;
    }

    /**
     * Return appointment type by month report
     * 
     * @return appointment type by month report
     */
    public String appointmentTypesByMonthReport() {
        String retVal = "";
       
        try {        
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from appointment ORDER BY start ASC");            
            HashMap<String, Integer> monthsTypeCounts = new HashMap<>();
            Month lastMonth = null;
            
            while (rs.next()) {
                Month month =  rs.getTimestamp("start").toLocalDateTime().getMonth();
                
                if (!month.equals(lastMonth)) {
                    for (String type : monthsTypeCounts.keySet()) {
                        retVal += (type == null ? "None" : type) + ": " +
                                monthsTypeCounts.get(type) + "\n";
                    }
                    retVal += "\n\n" + month.toString() + " " + Integer.toString(
                        rs.getTimestamp("start").toLocalDateTime().getYear()) + "\n";
                    monthsTypeCounts = new HashMap<>();
                    lastMonth = month;
                }

                if (monthsTypeCounts.containsKey(rs.getString("type"))) {
                    monthsTypeCounts.put(rs.getString("type"),
                        monthsTypeCounts.get(rs.getString("type")) + 1);
                } else {
                    monthsTypeCounts.put(rs.getString("type"), 1);
                }
            }
            
            for (String type : monthsTypeCounts.keySet()) {
                retVal += (type == null ? "None" : type) + ": " +
                        monthsTypeCounts.get(type) + "\n";
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLAPI.class.getName()).log(Level.SEVERE, null, ex);
        }

        return retVal;
    }

    /**
     * Return number of appointments by customer report
     * 
     * @return number of appointments by customer report
     */
    public String numberOfAppointmentsByCustomer() {
        String retVal = "";
       
        try {        
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from appointment;");            
            HashMap<Integer, Integer> customerApptCounts = new HashMap<>();
            
            while (rs.next()) {                
                if (customerApptCounts.containsKey(rs.getInt("customerId"))) {
                    customerApptCounts.put(rs.getInt("customerId"),
                        customerApptCounts.get(rs.getInt("customerId")) + 1);
                } else {
                    customerApptCounts.put(rs.getInt("customerId"), 1);
                }
            }
            
            for (int customerId : customerApptCounts.keySet()) {
                retVal += Integer.toString(customerId) + " - " +
                        this.getCustomerById(customerId).getCustomerName() +
                        ": " + customerApptCounts.get(customerId) + "\n";
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLAPI.class.getName()).log(Level.SEVERE, null, ex);
        }

        return retVal;
    }
    
    /**
     * Insert appointment into database
     * 
     * @param customer
     * @param user
     * @param title
     * @param type
     * @param description
     * @param location
     * @param contact
     * @param url
     * @param start
     * @param end
     * @param updateUser
     * @return inserted appointment
     */
    public ORMAppointment insertAppointment(ORMCustomer customer, ORMUser user,
            String title,
        String type, String description, String location, String contact,
        String url,
            LocalDateTime start, LocalDateTime end, ORMUser updateUser) {
        int maxId = Collections.max(appointmentsById.keySet());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO appointment " +
                "(appointmentId, customerId, title, type, description, " +
                "location, contact, url, start, end, " +
                "createDate, createdBy, lastUpdateBy, userId) " +
                String.format("values (%1$d, %2$d, \"%3$s\", \"%4$s\", \"%5$s\"," +
                              " \"%6$s\", \"%7$s\", \"%8$s\",  \"%9$s\", " +
                              "\"%10$s\", NOW(), \"%11$s\", \"%11$s\", %12$d)",
                              maxId + 1, customer.getCustomerId(), title, type,
                              description, location, contact, url,
                              start.format(formatter), end.format(formatter),
                              updateUser.getUserName(), user.getUserId()));
        } catch (SQLException ex) {
            System.out.println("SQL INSERT ERROR: " + ex.getMessage());
        }

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from appointment" +
                    " where appointmentId=" + Integer.toString(maxId + 1));

            rs.next();
            ORMAppointment appointment = new ORMAppointment(
                rs.getInt("appointmentId"),
                rs.getInt("customerId"),
                rs.getInt("userId"),
                rs.getString("title"),
                rs.getString("type"),
                rs.getString("description"),
                rs.getString("location"),
                rs.getString("contact"),
                rs.getString("url"),
                rs.getTimestamp("start").toLocalDateTime(),
                rs.getTimestamp("end").toLocalDateTime(),
                rs.getString("createDate"),
                rs.getString("createdBy"),
                (int) (rs.getTimestamp("lastUpdate").getTime() / 1000),
                rs.getString("lastUpdateBy")
            );

            appointmentsById.put(appointment.getAppointmentId(), appointment);

            SimpleImmutableEntry yearWeekEntry = new SimpleImmutableEntry(
                appointment.getStart().getYear(),
                appointment.getStart().get(woy));

            if (!appointmentsByYearWeek.containsKey(yearWeekEntry)) {
                appointmentsByYearWeek.put(yearWeekEntry, new ArrayList<>());
            }

            appointmentsByYearWeek.get(yearWeekEntry).add(appointment);

            return appointment;
        } catch (SQLException ex) {
            System.out.println("SQL SELECT ERROR: " + ex.getMessage());
            return null;
        }
    }

    /**
     * Return country by id
     * 
     * @param countryId id of country to return
     * @return country of country id
     */
    public ORMCountry getCountryById(int countryId) {
        return countries.get(countryId);
    }

    /**
     * Return city by id
     * 
     * @param cityId id of city to return
     * @return city of city id
     */
    public ORMCity getCityById(int cityId) {
        return cities.get(cityId);
    }

    /**
     * Return customer by id
     * 
     * @param customerId id of customer to return
     * @return customer of customer id
     */
    public ORMCustomer getCustomerById(int customerId) {
        return customers.get(customerId);
    }

    /**
     * Return address by id
     * 
     * @param addressId id of address to return
     * @return address of address id
     */
    public ORMAddress getAddressById(int addressId) {
        return addresses.get(addressId);
    }
    
    /**
     * Return user by id
     * 
     * @param userId id of user to return
     * @return user of user id
     */
    public ORMUser getUserById(int userId) {
        for (ORMUser user : users) {
            if (user.getUserId() == userId) {
                return user;
            }
        }
        return null;
    }

    /**
     * Delete customer from database
     * 
     * @param selectedCustomer customer to delete
     */
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

    /**
     * Check if appointment overlaps
     * 
     * @param windowStart start of overlap check
     * @param windowEnd end of overlap check
     * @return does appointment exist
     */
    public boolean doesAppointmentExistInWindow(LocalDateTime windowStart,
            LocalDateTime windowEnd) {

        // check only one week for better Big-O
        SimpleImmutableEntry yearWeekEntry = new SimpleImmutableEntry(
            windowStart.getYear(),
            windowStart.get(woy));


        if (appointmentsByYearWeek.containsKey(yearWeekEntry)) {
            for (ORMAppointment a : appointmentsByYearWeek.get(yearWeekEntry)) {
                if ((a.getStart().isAfter(windowStart) &&
                     a.getStart().isBefore(windowEnd)) ||
                    (a.getEnd().isAfter(windowStart) &&
                     a.getEnd().isBefore(windowEnd)) ||
                    (a.getStart().isAfter(windowStart) &&
                     a.getEnd().isBefore(windowEnd))) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Is there an appointment within 15min
     * 
     * @param nowInUTC current time
     * @return is appointment in 15min
     */
    public boolean isAppointmentWithin15Min(LocalDateTime nowInUTC) {
        SimpleImmutableEntry yearWeekEntry = new SimpleImmutableEntry(
            nowInUTC.getYear(),
            nowInUTC.get(woy));

        if (appointmentsByYearWeek.containsKey(yearWeekEntry)) {
            for (ORMAppointment a : appointmentsByYearWeek.get(yearWeekEntry)) {
                if (a.getStart().minusMinutes(15).isBefore(nowInUTC) &&
                    a.getStart().isAfter(nowInUTC)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Delete appointment from db
     * 
     * @param appointment appointment to delete
     */
    public void deleteAppointment(ORMAppointment appointment) {
        appointmentsById.remove(appointment.getAppointmentId());

        SimpleImmutableEntry yearWeekEntry = new SimpleImmutableEntry(
            appointment.getStart().getYear(),
            appointment.getStart().get(woy));

        appointmentsByYearWeek.get(yearWeekEntry).remove(appointment);

        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM appointment " +
                "WHERE appointmentId = " +
                Integer.toString(appointment.getAppointmentId()));
        } catch (SQLException ex) {
            System.out.println("SQL update error: " + ex.getMessage());
        }
    }
}
