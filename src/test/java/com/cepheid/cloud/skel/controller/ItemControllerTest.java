package com.cepheid.cloud.skel.controller;

import com.cepheid.cloud.skel.TestBase;
import com.cepheid.cloud.skel.dto.req.DescriptionRequest;
import com.cepheid.cloud.skel.dto.req.ItemRequest;
import com.cepheid.cloud.skel.model.Item;
import org.assertj.core.util.Lists;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.junit4.SpringRunner;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
public class ItemControllerTest extends TestBase {


    @Test
    public void testGetItems() throws Exception {
        Invocation.Builder itemController = getBuilder("/app/api/1.0/items");

        Response response = itemController.get(new GenericType<Response>() {
        });
        assertNotNull(response);
        assertEquals(200, response.getStatus());
    }

    @Test
    public void testGetItemById() {
        Invocation.Builder itemController = getBuilder("/app/api/1.0/items/1");
        Response response = itemController.get(new GenericType<Response>() {
        });
        assertNotNull(response);
        assertEquals(200, response.getStatus());
    }

    @Test
    public void testAddItem() {
        Invocation.Builder itemController = getBuilder("/app/api/1.0/items");
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setState(Item.State.VALID);
        itemRequest.setName("Dummy name");
        itemRequest.setDescriptions(Lists.newArrayList(new DescriptionRequest("Dummy Desc1"), new DescriptionRequest("Dummy Desc2")));
        Response response = itemController.buildPost(Entity.entity(itemRequest, MediaType.APPLICATION_JSON)).invoke();

        assertNotNull(response);
        assertEquals(200, response.getStatus());
    }

    @Test
    public void testAddItemDescription() {
        Invocation.Builder itemController = getBuilder("/app/api/1.0/items/3");
        DescriptionRequest descriptionRequest = new DescriptionRequest();
        descriptionRequest.setDescriptionComment("Dummy Comment");
        Response response = itemController.buildPut(Entity.entity(descriptionRequest, MediaType.APPLICATION_JSON)).invoke();

        assertNotNull(response);
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    public void testAddItemDescriptionWhenItemIsNotPresent() {
        Invocation.Builder itemController = getBuilder("/app/api/1.0/items/19");
        DescriptionRequest descriptionRequest = new DescriptionRequest();
        descriptionRequest.setDescriptionComment("Dummy Comment");
        Response response = itemController.buildPut(Entity.entity(descriptionRequest, MediaType.APPLICATION_JSON)).invoke();

        assertNotNull(response);
        assertEquals(Response.Status.NOT_MODIFIED.getStatusCode(), response.getStatus());
    }

    @Test
    public void x_testDeleteItemByItemId() {
        Invocation.Builder itemController = getBuilder("/app/api/1.0/items/1");
        Response response = itemController.buildDelete().invoke();

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void y_testDeleteItemByItemIdWhenItemWithGivenIdIsNotPresent() {
        Invocation.Builder itemController = getBuilder("/app/api/1.0/items/17");
        Response response = itemController.buildDelete().invoke();

        assertNotNull(response);
        assertEquals(Response.Status.NOT_ACCEPTABLE.getStatusCode(), response.getStatus());
    }

    @Test
    public void z_testDeleteAllItems() {
        Invocation.Builder itemController = getBuilder("/app/api/1.0/items");
        Response response = itemController.buildDelete().invoke();

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }
}