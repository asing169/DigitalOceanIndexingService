package com.digitalocean.service;

import com.digitalocean.models.Input;
import com.digitalocean.models.Response;

public interface CommandInterface {
    public Response indexPackage(Input input);
    public Response removePackage(Input input);
    public Response queryPackage(Input input);
}
