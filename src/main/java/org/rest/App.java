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

        User user = new User(3L, "James", "Brown", (byte) 25);
        communication.saveUser(user);

        User user2 = new User(3L, "Thomas", "Shelby", (byte) 29);

        communication.updateUser(user2);

        communication.deleteUser(3);
    }
}
