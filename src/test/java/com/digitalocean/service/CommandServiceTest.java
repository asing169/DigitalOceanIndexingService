package com.digitalocean.service;

import com.digitalocean.models.Input;
import com.digitalocean.models.Response;
import com.digitalocean.models.Status;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CommandServiceTest {

    @InjectMocks
    @Spy
    CommandService commandService;

    @BeforeEach
    public void setUp(){

        MockitoAnnotations.initMocks(this);

        Input input =  new Input();
        input.setCommand("INDEX");
        input.setPackageName("sidhu");

        Input input1 =  new Input();
        input1.setCommand("INDEX");
        input1.setPackageName("espn");

        Input input2 =  new Input();
        input2.setCommand("INDEX");
        input2.setPackageName("npm");

        commandService.indexPackage(input);
        commandService.indexPackage(input1);
        commandService.indexPackage(input2);
    }


    @Test
    void indexPackage() {
        Input input =  new Input();
        input.setCommand("INDEX");
        input.setPackageName("cloog");

        Input input2 =  new Input();
        input2.setCommand("INDEX");
        input2.setPackageName("cloog");
        input2.setDependencies(Arrays.asList("sidhu", "npm", "espn"));

        Response response = commandService.indexPackage(input);
        assertEquals("OK", response.getStatus());

        Response response1 = commandService.indexPackage(input2);
        assertEquals("OK", response1.getStatus());

    }

    @Test
    void queryPackage() {
        Input input =  new Input();
        input.setCommand("INDEX");
        input.setPackageName("espn");

        Response response = commandService.queryPackage(input);
        assertEquals("OK", response.getStatus());
    }


    @Test
    void removePackage() {
        Input input =  new Input();
        input.setCommand("INDEX");
        input.setPackageName("espn");

        Response response = commandService.removePackage(input);
        assertEquals("OK", response.getStatus());
    }
}