package com.cepheid.cloud.skel.controller;

import com.cepheid.cloud.skel.dto.req.DescriptionRequest;
import com.cepheid.cloud.skel.dto.resp.DescriptionResponse;
import com.cepheid.cloud.skel.service.DescriptionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Path("/api/1.0/descriptions")
@Api
public class DescriptionController {

    private final DescriptionService descriptionService;


    @Autowired
    public DescriptionController(DescriptionService descriptionService) {
        this.descriptionService = descriptionService;
    }


    /**
     * HTTP Method POST
     * http://localhost:9443/app/api/1.0/descriptions
     * {"descriptionComment":"This is new description"}
     */
    @POST
    @ApiOperation(value = "add a description",
            notes = "This method add a new description un related to an item in database")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addDescription(@RequestBody DescriptionRequest descriptionRequest) {
        DescriptionResponse descriptionResponse = null;
        Throwable throwable = null;
        try {
            descriptionResponse = descriptionService.save(descriptionRequest);
        } catch (Exception e) {
            e.printStackTrace();
            throwable = e;
        }
        if (descriptionResponse == null) {
            assert throwable != null;
            return Response.status(Response.Status.BAD_REQUEST).entity(new DescriptionResponse(Boolean.TRUE, throwable.getMessage())).build();
        }
        return Response.status(Response.Status.CREATED).entity(descriptionResponse).build();
    }

    @GET
    @Path("/{id}")
    @ApiOperation(value = "get description by id",
            notes = "This method finds an existing description by it's id")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDescriptionById(@PathParam("id") Long id) {
        if (id == null) {
            return Response.status(Response.Status.OK).entity(new DescriptionResponse(Boolean.TRUE, "id can not be null")).build();
        }
        DescriptionResponse description = descriptionService.findById(id);
        if (description.isInError())
            return Response.status(Response.Status.NOT_FOUND).entity(description).build();

        return Response.status(Response.Status.OK).entity(description).build();
    }

    /**
     * HTTP Method PUT
     * http://localhost:9443/app/api/1.0/items/description/4
     * {"descriptionComment":"This is updated second description of movie Hobbit"}
     */
    @PUT
    @Path(value = "/{descriptionId}")
    @ApiOperation(value = "update a description by descriptionId",
            notes = "This method updates existing description related to an item in database")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateDescriptionById(@PathParam("descriptionId") Long descriptionId,
                                          @RequestBody DescriptionRequest descriptionRequest) {
        DescriptionResponse response = descriptionService.updateDescriptionById(descriptionId, descriptionRequest);
        if (response.isInError()) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(response).build();
        }
        return Response.status(Response.Status.OK).entity(response).build();
    }

    @GET
    @ApiOperation(value = "get all description",
            notes = "This method finds all descriptions present in database")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllDescription() {

        List<DescriptionResponse> descriptions = descriptionService.findAll();
        if (descriptions.get(0).isInError())
            return Response.status(Response.Status.NOT_FOUND).entity(descriptions).build();

        return Response.status(Response.Status.OK).entity(descriptions).build();
    }
}
