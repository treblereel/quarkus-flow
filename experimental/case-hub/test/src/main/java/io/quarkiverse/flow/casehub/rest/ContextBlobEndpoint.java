package io.quarkiverse.flow.casehub.rest;

import java.util.List;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import io.quarkiverse.flow.casehub.model.ContextBlob;

@Path("/api/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ContextBlobEndpoint {

    @GET
    public List<ContextBlob> get() {
        return ContextBlob.listAll();
    }

}
