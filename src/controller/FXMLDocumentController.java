/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.Usermodel;

/**
 *
 * @author Owner
 */
public class FXMLDocumentController implements Initializable {

    
    //@FXML
    //private ResourceBundle resources;
    //@FXML
    //private URL location;
    
    @FXML
    private Label label;

    @FXML
    private Button createUser;

    @FXML
    private Button deleteUser;
    
    @FXML
    private Button readUser;

    @FXML
    private Button readUserByID;
    
    @FXML
    private Button readByName;
            
    @FXML
    private Button updateUser;
    
    @FXML
    private Button readByNameEmail;
    
    @FXML
    private Button searchUserButton;
    
    @FXML 
    private Button advancedSearch;

    @FXML
    private TableView<Usermodel> userTable;

    @FXML
    private TableColumn<Usermodel, Integer> tableUserID = new TableColumn<>("ID");

    @FXML
    private TableColumn<Usermodel, String> tableUserName = new TableColumn<>("Name");

    @FXML
    private TableColumn<Usermodel, String> tableUserEmail = new TableColumn<>("Email");

    @FXML
    private TableColumn<Usermodel, String> tableUserAddress = new TableColumn<>("Address");

    @FXML
    private TableColumn<Usermodel, Integer> tableUserPhone = new TableColumn<>("Phone Number");

    @FXML
    private TextField searchBar;

    @FXML
    private ObservableList<Usermodel> userData;
    
        // add the proper data to the observable list to be rendered in the table
    public void setTableData(List<Usermodel> userList) {

        // initialize the studentData variable
        userData = FXCollections.observableArrayList();

        // add the student objects to an observable list object for use with the GUI table
        userList.forEach(s -> {
            userData.add(s);
        });

        // set the the table items to the data in studentData; refresh the table
        userTable.setItems(userData);
        userTable.refresh();
    }
    
    @FXML
    void searchUserByNameAction(ActionEvent event){
        System.out.println("Clicked");
        
        String name = searchBar.getText();
        
        List<Usermodel> users = readByName(name);
        
        if(users == null || users.isEmpty()) {
            //show an alert
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog Box");// line 2
            alert.setHeaderText("This is header section to write heading");// line 3
            alert.setContentText("No user");// line 4
            alert.showAndWait(); // line 5
        }
        else{
            setTableData(users);
        }
    }
    
    @FXML
    void searchByNameAdvancedAction(ActionEvent event) {
        System.out.println("Clicked");
        
        String name = searchBar.getText();
        
        List<Usermodel> users = readByNameAdvanced(name);
        
        //setting table data
        if (users == null || users.isEmpty()){
            
            //show alert
                        // show an alert to inform user 
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog Box");// line 2
            alert.setHeaderText("This is header section to write heading");// line 3
            alert.setContentText("No user");// line 4
            alert.showAndWait(); // line 5
        }
        else {
            setTableData(users);
        }
    }
    
    @FXML
    void actionShowDetails(ActionEvent event) throws IOException{
        System.out.println("Clicked");
        
        //pass model currently selected
        Usermodel selectedUser = userTable.getSelectionModel().getSelectedItem();
        
        //fxml loader
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DetailedModelView.fxml"));
        
        //load ui elements
        Parent detailedModelView = loader.load();
        
        //load the scene
        Scene tableViewScene = new Scene(detailedModelView);
        
        //access detailedControlled and call method
        DetailedModelViewController detailedControlled = loader.getController();
        
        detailedControlled.initData(selectedUser);
        
        //create new state
        Stage stage = new Stage();
        stage.setScene(tableViewScene);
        stage.show();
    }    
    
    @FXML
    void actionShowDetailsInPlace(ActionEvent event) throws IOException {
        System.out.println("Clicked");
        
        //pass selected model
        Usermodel selectedUser = userTable.getSelectionModel().getSelectedItem();
        
        //fxml loader
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DetailedModelView.fxml"));
        
        //load ui
        Parent detailedModelView = loader.load();
        
        //load scene
        Scene tableViewScene = new Scene(detailedModelView);
        
        //access detailedControlled and call method
        DetailedModelViewController detailedControlled = loader.getController();
    
        detailedControlled.initData(selectedUser);
        
        //pass scene to return
        Scene currentScene = ((Node) event.getSource()).getScene();
        detailedControlled.setPreviousScene(currentScene);
        
        //this line gets stage info
        Stage stage = (Stage) currentScene.getWindow();
        
        stage.setScene(tableViewScene);
        stage.show();
    }
    
        @FXML
    void createUser(ActionEvent event) {
        Scanner input = new Scanner(System.in);

        // read input from command line
        System.out.println("Enter ID:");
        int id = input.nextInt();

        System.out.println("Enter Name:");
        String name = input.next();

        System.out.println("Enter Email:");
        String email = input.next();

        System.out.println("Enter Address:");
        String address = input.next();
        
        System.out.println("Enter Phone Number:");
        int phoneNumber = input.nextInt();

        // create a user instance
        Usermodel user = new Usermodel();

        // set properties
        user.setId(id);
        user.setName(name);
        user.setEmail(email);
        user.setAddress(address);
        user.setPhonenumber(phoneNumber);

        // save this user to database by calling Create operation        
        create(user);
    }
    
        @FXML
    void deleteUser(ActionEvent event) {
        Scanner input = new Scanner(System.in);

        // read input from command line
        System.out.println("Enter ID:");
        int id = input.nextInt();

        Usermodel s = readUserById(id);
        System.out.println("we are deleting this user: " + s.toString());
        delete(s);

    }
    
        @FXML
    void readUserByID(ActionEvent event) {
        Scanner input = new Scanner(System.in);

        // read input from command line
        System.out.println("Enter ID:");
        int id = input.nextInt();

        Usermodel s = readUserById(id);
        System.out.println(s.toString());

    }
    
    
    public List<Usermodel> readByName(String name) {
        Query query = manager.createNamedQuery("Usermodel.findByName");
        
        //i was following this code but I've been getting the same error here for literally 3 days so i give up
        //i asked in the discussion board but I think no one else got the error so i never got help
        //this was my error
        //Caused by: java.lang.IllegalArgumentException: NamedQuery of name: Usermodel.findByName not found.

        // setting query parameter
        query.setParameter("name", name);

        // execute query
        List<Usermodel> users = query.getResultList();
        for (Usermodel user : users) {
            System.out.println(user.getId() + " " + user.getName() + " " + user.getEmail());
        }

        return users;
    }
    
        
    public List<Usermodel> readByNameAndEmail(String name, String email) {
        Query query = manager.createNamedQuery("Usermodel.findByNameAndEmail");

        // setting query parameter
        query.setParameter("email", email);
        query.setParameter("name", name);

        // execute query
        List<Usermodel> users = query.getResultList();
        for (Usermodel user : users) {
            System.out.println(user.getId() + " " + user.getName() + " " + user.getEmail());
        }

        return users;
    }
    
    public List<Usermodel> readByNameAdvanced(String name){
        Query query = manager.createNamedQuery("Usermodel.findByNameAdvanced");
        
        query.setParameter("name", name);
        
        List<Usermodel> users = query.getResultList();
        for (Usermodel user: users){
            System.out.println(user.getId() + " " + user.getName() + " " + user.getEmail() + "" + user.getAddress() + "" + user.getPhonenumber());      
        }
        return users;
    }

    /*
    Implementing CRUD operations
     */
    // Create operation
    public void create(Usermodel user) {
        try {
            // begin transaction
            manager.getTransaction().begin();

            // sanity check
            if (user.getId() != null) {

                // create user
                manager.persist(user);

                // end transaction
                manager.getTransaction().commit();

                System.out.println(user.toString() + " is created");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    // Read Operations
    public List<Usermodel> readAll() {
        Query query = manager.createNamedQuery("Usermodel.findAll");
        List<Usermodel> users = query.getResultList();

        for (Usermodel s : users) {
            System.out.println(s.getId() + " " + s.getName() + " " + s.getEmail() + s.getAddress() + " " + s.getPhonenumber());
        }

        return users;
    }

    public Usermodel readUserById(int id) {
        Query query = manager.createNamedQuery("Usermodel.findUserById");

        // setting query parameter
        query.setParameter("id", id);

        // execute query
        Usermodel user = (Usermodel) query.getSingleResult();
        if (user != null) {
            System.out.println(user.getId() + " " + user.getName() + " " + user.getEmail());
        }

        return user;
    }

    // Update operation
    public void update(Usermodel model) {
        try {

            Usermodel existingUser = manager.find(Usermodel.class, model.getId());

            if (existingUser != null) {
                // begin transaction
                manager.getTransaction().begin();

                // update all atttributes
                existingUser.setName(model.getName());
                existingUser.setEmail(model.getEmail());

                // end transaction
                manager.getTransaction().commit();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    // Delete operation
    public void delete(Usermodel user) {
        try {
            Usermodel existingUser = manager.find(Usermodel.class, user.getId());

            // sanity check
            if (existingUser != null) {

                // begin transaction
                manager.getTransaction().begin();

                //remove user
                manager.remove(existingUser);

                // end transaction
                manager.getTransaction().commit();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

/////////// 

    @FXML
    void readByName(ActionEvent event) {
        
        System.out.println("clicked");
        
        Scanner input = new Scanner(System.in);

        // read input from command line
        System.out.println("Enter Name:");
        String name = input.next();

        List<Usermodel> s = readByName(name);
        System.out.println(s.toString());

    }

    @FXML
    void readByNameEmail(ActionEvent event) {
        // name and cpga

        Scanner input = new Scanner(System.in);

        // read input from command line
        System.out.println("Enter Name:");
        String name = input.next();

        System.out.println("Enter Email:");
        String email = input.next();

        // create a student instance      
        List<Usermodel> users = readByNameAndEmail(name, email);

    }

    @FXML
    void readUser(ActionEvent event) {
        readAll();

    }
     
    @FXML
    void updateUser(ActionEvent event) {
        Scanner input = new Scanner(System.in);

        // read input from command line
        System.out.println("Enter ID:");
        int id = input.nextInt();

        System.out.println("Enter Name:");
        String name = input.next();

        System.out.println("Enter Email:");
        String email = input.next();
        
        System.out.println("Enter Address:");
        String address = input.next();
        
        System.out.println("Enter Phone Number: ");
        int phoneNumber = input.nextInt();

        // create a student instance
        Usermodel user = new Usermodel();

        // set properties
        user.setId(id);
        user.setName(name);
        user.setEmail(email);
        user.setAddress(address);
        user.setPhonenumber(phoneNumber);

        // save this student to database by calling Create operation        
        update(user);
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");

        Query query = manager.createNamedQuery("Usermodel.findAll");
        List<Usermodel> data = query.getResultList();

        for (Usermodel s : data) {
            System.out.println(s.getId() + " " + s.getName() + " " + s.getEmail());
        }
    }
    
    
    // Database manager
    EntityManager manager;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // loading data from database
        //database reference: "IntroJavaFXPU"
        manager = (EntityManager) Persistence.createEntityManagerFactory("IntroJavaFXPU").createEntityManager();

            tableUserID.setCellValueFactory(new PropertyValueFactory<>("ID"));
            tableUserName.setCellValueFactory(new PropertyValueFactory<>("Name"));
            tableUserEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
            tableUserAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
            tableUserPhone.setCellValueFactory(new PropertyValueFactory<>("Phone Number"));
            
            //enable row selection
            //SelectionMode.MULTIPLE);
            
            userTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);     
    }

}
