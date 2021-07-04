package com.cepheid.cloud.skel.controller;

import com.cepheid.cloud.skel.TestBase;
import com.cepheid.cloud.skel.dto.req.DescriptionRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
public class DescriptionControllerTest extends TestBase {

    @Test
    public void testAddDescription() {
        Invocation.Builder descriptionController = getBuilder("/app/api/1.0/descriptions");
        DescriptionRequest descriptionRequest = new DescriptionRequest();
        descriptionRequest.setDescriptionComment("Dummy Comment");
        Response response = descriptionController.buildPost(Entity.entity(descriptionRequest, MediaType.APPLICATION_JSON)).invoke();
        assertNotNull(response);
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    public void testAddDescriptionThrowsErrorWhenRequestHasNoDescriptionComment() {
        Invocation.Builder descriptionController = getBuilder("/app/api/1.0/descriptions");
        DescriptionRequest descriptionRequest = new DescriptionRequest();
        Response response = descriptionController.buildPost(Entity.entity(descriptionRequest, MediaType.APPLICATION_JSON)).invoke();

        assertNotNull(response);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetDescriptionById() {
        Invocation.Builder descriptionController = getBuilder("/app/api/1.0/descriptions/2");

        Response response = descriptionController.buildGet().invoke();
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetDescriptionByIdWhenIdIsNotPresent() {
        Invocation.Builder descriptionController = getBuilder("/app/api/1.0/descriptions/23");

        Response response = descriptionController.buildGet().invoke();
        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testUpdateDescriptionById() {
        Invocation.Builder descriptionController = getBuilder("/app/api/1.0/descriptions/2");
        DescriptionRequest descriptionRequest = new DescriptionRequest();
        descriptionRequest.setDescriptionComment("Updated Dummy Comment");
        Response response = descriptionController.buildPut(Entity.entity(descriptionRequest, MediaType.APPLICATION_JSON)).invoke();
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testUpdateDescriptionByIdWhenIdIsNotFound() {
        Invocation.Builder descriptionController = getBuilder("/app/api/1.0/descriptions/22");
        DescriptionRequest descriptionRequest = new DescriptionRequest();
        descriptionRequest.setDescriptionComment("Updated Dummy Comment");
        Response response = descriptionController.buildPut(Entity.entity(descriptionRequest, MediaType.APPLICATION_JSON)).invoke();
        assertNotNull(response);
        assertEquals(Response.Status.NOT_ACCEPTABLE.getStatusCode(), response.getStatus());
    }

}