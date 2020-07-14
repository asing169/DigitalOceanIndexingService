package com.digitalocean.models;

public class Response {
    String status;

    public Response(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
