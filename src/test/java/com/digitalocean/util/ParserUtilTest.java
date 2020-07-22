package com.digitalocean.util;

import java.util.ArrayList;
import java.util.List;

import com.digitalocean.models.Input;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class ParserUtilTest {

    @Test
    void parse() {
        String inputStr = "INDEX|cloog|gmp,isl,pkg-config";
        ParseUtil parseUtil = new ParseUtil();
        Input input = new Input();

        List<String> dependencies = new ArrayList<>();
        dependencies.add("gmp");
        dependencies.add("isl");
        dependencies.add("pkg-config");


        input.setCommand("INDEX");
        input.setPackageName("cloog");
        input.setDependencies(dependencies);

        Input response = parseUtil.parse(inputStr);

        assertEquals(input.getCommand(), response.getCommand());
        assertEquals(input.getPackageName(), response.getPackageName());
        assertEquals(input.getDependencies(), response.getDependencies());
    }

    @Test
    void parseWrongInput(){
        String inputStr = "INDEX|cloog";
        ParseUtil parseUtil = new ParseUtil();
        assertThrows(IllegalArgumentException.class, () ->{
            parseUtil.parse(inputStr);
        });
    }
}
