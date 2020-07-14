package com.digitalocean.utilities;

import com.digitalocean.models.Input;

import java.util.ArrayList;
import java.util.logging.Logger;

public class ParseUtil {

    private Logger logger = Logger.getLogger(ParseUtil.class.getName());

    public Input parse(String inputStr) {
        StringBuilder stringBuilder = new StringBuilder();
        Input input = new Input();
        int counter = 0;

        //INDEX|cloog had|gmp,isl,pkg-config\n
        //INDEX|emacs elisp
        //INDEX|cloog had
        //INDEX|cloog|

        for (int i = 0; i < inputStr.length(); i++) {

            if (inputStr.charAt(i) == '|') {
                if (counter == 0) {
                    input.setCommand(stringBuilder.toString());
                    counter++;
                } else if (counter == 1) {
                    input.setPackageName(stringBuilder.toString());
                    counter++;
                }

                //Reset StringBuilder
                stringBuilder.setLength(0);

            } else {
                stringBuilder.append(inputStr.charAt(i));
            }

        }

        //dependcies list
        if (stringBuilder.length() > 0 && counter == 2) {
            //input.setDependencies(Arrays.asList(stringBuilder.toString().split(",")));
            input.setDependencies(new ArrayList<>());
        }

        if (counter < 2) {
            throw new IllegalArgumentException("Incorrect log format.");
        }

        return input;

    }
}
