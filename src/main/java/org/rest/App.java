package org.rest;

import org.rest.Entity.User;
import org.rest.configuration.MyConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class App 
{
    public static void main( String[] args )
    {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(MyConfig.class);

        Communication communication = context.getBean("communication", Communication.class);
        List<User> allUsers =  communication.getAllUser();

        System.out.println(allUsers);

        User user = new User((long) 3, "James", "Brown", (byte) 25);
        communication.saveUser(user);

        user.setName("Thomas");
        user.setLastName("Shelby");

        communication.saveUser(user);

        communication.deleteUser(3);
    }
}
