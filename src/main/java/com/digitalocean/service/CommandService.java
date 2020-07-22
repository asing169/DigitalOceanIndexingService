package com.digitalocean.service;

import com.digitalocean.models.Input;
import com.digitalocean.models.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import static com.digitalocean.models.Status.FAIL;
import static com.digitalocean.models.Status.OK;

public class CommandService implements CommandInterface {

    private Logger logger = Logger.getLogger(CommandService.class.getName());

    private ConcurrentHashMap<String, List<String>> map = new ConcurrentHashMap<>();
    private static CommandService commandService;

    private CommandService() {

    }

    public synchronized static CommandService getInstance() {
        if (commandService == null) {
            commandService = new CommandService();
        }
        return commandService;
    }

    public synchronized Response indexPackage(Input input) {
        logger.info("Indexing Package");

        if (dependenciesExist(input)) {
            map.put(input.getPackageName(), input.getDependencies() != null ? input.getDependencies() : new ArrayList<>());
            return new Response(OK.toString());
        } else {
            return new Response(FAIL.toString());
        }

    }

    public synchronized Response removePackage(Input input) {
        logger.info("Removing package");

        if (!map.containsKey(input.getPackageName())) {
            logger.info("Package doesn't exist nothing to remove!");
            return new Response(OK.name());
        } else if (!anyRemainingDependencies(input)) {

            logger.info("Package exists and no remaining dependencies starting removal!");
            map.remove(input.getPackageName());
            return new Response(OK.name());
        } else {
            //logger.info();
            return new Response(FAIL.toString());
        }
    }

    public synchronized Response queryPackage(Input input) {
        logger.info("Querying package");

        if (map.containsKey(input.getPackageName())) {
            return new Response(OK.toString());
        } else {
            return new Response(FAIL.toString());
        }
    }

    private boolean anyRemainingDependencies(Input input) {
        logger.info("Checking for dependent packages before removal.");


        //checks if list of dependencies includes this package as an dependency.
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            if (entry.getValue().stream().anyMatch(str -> str.equalsIgnoreCase(input.getPackageName()))) {
                logger.info("Remaining dependencies exist, cannot remove");
                return true;
            }
        }
        return false;
    }

    public boolean dependenciesExist(Input input) {
        logger.info("Checking if dependencies have already been installed.");

        if (input.getDependencies() != null) {
            for (String s : input.getDependencies()) {
                if (!map.containsKey(s)) {
                    return false;
                }
            }
        }
        return true;
    }

}