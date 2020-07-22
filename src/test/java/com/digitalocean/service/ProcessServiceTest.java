package com.digitalocean.service;

import com.digitalocean.models.Input;
import com.digitalocean.models.Response;
import com.digitalocean.models.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


class ProcessServiceTest {

    @InjectMocks
    @Spy
    private ProcessService processService;

    @Mock
    CommandService commandService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void processCommandIndex() {

        String inputStr = "INDEX|cloog|";

        Input input = new Input();

        Mockito.when(commandService.indexPackage(any())).thenReturn(new Response(Status.OK.toString()));

        Response response = processService.processCommand(inputStr);

        verify(commandService, times(1)).indexPackage(any());

        assertEquals(Status.OK.toString(), response.getStatus());

    }

    @Test
    void processCommandQuery(){

        String inputStr = "QUERY|cloog|";

        Mockito.when(commandService.queryPackage(any())).thenReturn(new Response(Status.OK.toString()));

        Response response = processService.processCommand(inputStr);

        verify(commandService, times(1)).queryPackage(any());

        assertEquals(Status.OK.toString(), response.getStatus());

    }

    @Test
    void processCommandRemove(){

        String inputStr = "REMOVE|cloog|";

        Mockito.when(commandService.removePackage(any())).thenReturn(new Response(Status.OK.toString()));

        Response response = processService.processCommand(inputStr);

        verify(commandService, times(1)).removePackage(any());

        assertEquals(Status.OK.toString(), response.getStatus());

    }


}