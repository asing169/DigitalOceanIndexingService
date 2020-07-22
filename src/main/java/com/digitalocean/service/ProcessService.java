package com.digitalocean.service;

import com.digitalocean.models.Commands;
import com.digitalocean.models.Input;
import com.digitalocean.models.Response;
import com.digitalocean.util.ParseUtil;

import static com.digitalocean.models.Status.ERROR;

public class ProcessService {

    private ParseUtil parseUtil = new ParseUtil();
    private CommandInterface commandService = CommandService.getInstance();

    public Response processCommand(String inputStr) {

        Input input = null;

        try {
            input = parseUtil.parse(inputStr);
            switch (Commands.valueOf(input.getCommand())) {
                case INDEX:
                    return commandService.indexPackage(input);

                case REMOVE:
                    return commandService.removePackage(input);

                case QUERY:
                    return commandService.queryPackage(input);

                default:
                    return new Response(ERROR.toString());

            }
        } catch (Exception e) {
            return new Response(ERROR.toString());
        }

    }


}
