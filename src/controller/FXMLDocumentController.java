/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.Usermodel;

/**
 *
 * @author Owner
 */

public class FXMLDocumentController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button createUser;

    @FXML
    private Label label;

    @FXML
    private Button updateUser;

    @FXML
    private Button readUserByID;

    @FXML
    private Button deleteUser;
    
        @FXML
    private Button searchUserButton;
    
    
    //@FXML
    //private Button findByNameAndAddress;

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
    public List<Usermodel> readAll(){
        Query query = manager.createNamedQuery("Usermodel.findAll");
        List<Usermodel> users = query.getResultList();

        for (Usermodel s : users) {
            System.out.println(s.getId() + " " + s.getName() + " " + s.getEmail());
        }
        
        return users;
    }
    
    public Usermodel readUserById(int id){
        Query query = manager.createNamedQuery("Usermodel.findById");
        
        // setting query parameter
        query.setParameter("id", id);
        
        // execute query
        Usermodel user = (Usermodel) query.getSingleResult();
        if (user != null) {
            System.out.println(user.getId() + " " + user.getName() + " " + user.getEmail());
        }
        
        return user;
    }        
    
    public List<Usermodel> readByName(String name){
        Query query = manager.createNamedQuery("Usermodel.findByName");
        
        // setting query parameter
        query.setParameter("name", name);
        
        // execute query
        List<Usermodel> users =  query.getResultList();
        for (Usermodel user: users) {
            System.out.println(user.getId() + " " + user.getName() + " " + user.getEmail());
        }
        
        return users;
    }        
    
    public List<Usermodel> readByNameAndEmail(String name, String email){
        Query query = manager.createNamedQuery("Usermodel.findByNameAndEmail");
        
        // setting query parameter
        query.setParameter("email", email);
        query.setParameter("name", name);
        
        
        // execute query
        List<Usermodel> users =  query.getResultList();
        for (Usermodel user: users) {
            System.out.println(user.getId() + " " + user.getName() + " " + user.getEmail());
        }
        
        return users;
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
    void createUser(ActionEvent event) {
        Scanner input = new Scanner(System.in);
        
        // read input from command line
        System.out.println("Enter ID:");
        int id = input.nextInt();
        
        System.out.println("Enter Name:");
        String name = input.next();
        
        System.out.println("Enter Email:");
        String email = input.next();
        
        // create a user instance
        Usermodel user = new Usermodel();
        
        // set properties
        user.setId(id);
        user.setName(name);
        user.setEmail(email);
        
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
        System.out.println("we are deleting this student: "+ s.toString());
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

    @FXML
    void readByName(ActionEvent event) {
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
        
        System.out.println("Enter CPGA:");
        String email = input.next();
        
        // create a student instance      
        List<Usermodel> users =  readByNameAndEmail(name, email);

    }

    @FXML
    void readUser(ActionEvent event) {

    }
    
    //public List<Usermodel> findByNameAndAddress(String name, String address){
        //Query query = manager.createNamedQuery("Usermodel.findByNameAndAddress");
        
        // setting query parameter
        //query.setParameter("address", address);
        //query.setParameter("name", name);
        
        
        // execute query
        //List<Usermodel> users =  query.getResultList();
        //for (Usermodel user: users) {
            //System.out.println(user.getId() + " " + user.getName() + " " + user.getAddress());
        //}
        
        //return users;
    //}        
    

    @FXML
    void updateUser(ActionEvent event) {
        Scanner input = new Scanner(System.in);
        
        // read input from command line
        System.out.println("Enter ID:");
        int id = input.nextInt();
        
        System.out.println("Enter Name:");
        String name = input.next();
        
        System.out.println("Enter CPGA:");
        String email = input.next();
        
        // create a student instance
        Usermodel user = new Usermodel();
        
        // set properties
        user.setId(id);
        user.setName(name);
        user.setEmail(email);
        
        // save this student to database by calling Create operation        
        update(user);
    }

    

    //@FXML
    //void deleteUser(ActionEvent event) {

    //}

    //@FXML
    //void readByID(ActionEvent event) {

    //}

    //@FXML
    //void updateUser(ActionEvent event) {

    //}

    //@FXML
    //void initialize() {
        //assert createUser != null : "fx:id=\"buttonCreateUser\" was not injected: check your FXML file 'ElizabethFXMLSceneBuilder.fxml'.";
        //assert readUserID != null : "fx:id=\"buttonReadByID\" was not injected: check your FXML file 'ElizabethFXMLSceneBuilder.fxml'.";
        //assert updateUser != null : "fx:id=\"buttonUpdateUser\" was not injected: check your FXML file 'ElizabethFXMLSceneBuilder.fxml'.";
        //assert deleteUser != null : "fx:id=\"buttonDeleteUser\" was not injected: check your FXML file 'ElizabethFXMLSceneBuilder.fxml'.";

    //}


    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
                
        Query query = manager.createNamedQuery("Student.findAll");
        List<Usermodel> data = query.getResultList();
        
        for (Usermodel s : data) {            
            System.out.println(s.getId() + " " + s.getName()+ " " + s.getEmail());         
        }           
    }

     // Database manager
    EntityManager manager;

    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        // loading data from database
        //database reference: "IntroJavaFXPU"
        manager = (EntityManager) Persistence.createEntityManagerFactory("IntroJavaFXPU").createEntityManager();
    }    
    
     @FXML
    void searchUser(ActionEvent event) {
        System.out.println("Clicked");
    }

}

