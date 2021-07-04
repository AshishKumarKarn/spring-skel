package com.cepheid.cloud.skel.controller;

import com.cepheid.cloud.skel.dto.req.DescriptionRequest;
import com.cepheid.cloud.skel.dto.req.ItemRequest;
import com.cepheid.cloud.skel.dto.resp.ItemResponse;
import com.cepheid.cloud.skel.model.Description;
import com.cepheid.cloud.skel.model.Item;
import com.cepheid.cloud.skel.service.ItemService;
import com.cepheid.cloud.skel.util.Transformer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


// curl http:/localhost:9443/app/api/1.0/items

@Component
@Path("/api/1.0/items")
@Api
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }


    /**
     * HTTP Method GET
     * http://localhost:9443/app/api/1.0/items
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Get all Items",
            notes = "This method fetches all items present in database")
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Response getItems() {
        List<ItemResponse> allItem = itemService.findAll();
        if (allItem.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).entity(new ItemResponse(Boolean.TRUE, "No item data in database")).build();
        }
        return Response.status(Response.Status.OK).entity(allItem).build();
    }

    /**
     * HTTP Method GET
     * http://localhost:9443/app/api/1.0/items/3
     */
    @GET
    @Path("/{itemId}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Get Item by Id",
            notes = "This method fetches item if the given id is present in DB")
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Response getItemById(@ApiParam(
            name = "itemId",
            value = "Id of item to be fetched",
            required = true) @PathParam("itemId") Long itemId) {
        ItemResponse itemResponse = itemService.findItemResponseById(itemId);
        if (itemResponse.isInError()) {
            return Response.status(Response.Status.NO_CONTENT).entity(itemResponse).build();
        }
        return Response.status(Response.Status.OK).entity(itemResponse).build();
    }

    /**
     * http method POST
     * http://localhost:9443/app/api/1.0/items/
     * {"name":"New Data","state":"VALID","descriptions":[{"descriptionComment":"First description of movie New Data"},{"descriptionComment":"Second description of movie New Data"}]}
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    @ApiOperation(value = "add Item",
            notes = "This method adds new item to database")
    @ApiImplicitParams({@ApiImplicitParam(name = "item", value = "Item with name, state & descriptions", paramType = "body", dataType = "com.cepheid.cloud.skel.model.Item")})
    public Response addItem(@RequestBody ItemRequest itemRequest) {
        ItemResponse itemResponse;
        try {
            itemResponse = itemService.save(itemRequest);
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.OK).entity(new ItemResponse(Boolean.TRUE, e.getMessage())).build();
        }
        return Response.status(Response.Status.OK).entity(itemResponse).build();
    }

    /**
     * HTTP Method PUT
     * http://localhost:9443/app/api/1.0/items/3
     * {"descriptionComment":"Third description of movie Silmarillion"}
     */
    @PUT
    @Path("/{itemId}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "add new Item description",
            notes = "This method adds new item description to existing item to database")
    @ApiImplicitParams({@ApiImplicitParam(name = "description", value = "Description with itemId", paramType = "body", dataType = "com.cepheid.cloud.skel.model.Description")})
    public Response addItemDescription(@RequestBody DescriptionRequest descriptionRequest,
                                       @PathParam("itemId") Long itemId) {
        Item item = itemService.findItemById(itemId);
        ItemResponse itemResponse;
        if (item != null) {
            descriptionRequest.validate();
            Description description = Transformer.transformDescriptionRequestToDescription(descriptionRequest);
            item.addDescription(description);
            itemResponse = itemService.saveItem(item);
            return Response.status(Response.Status.CREATED).entity(itemResponse).build();
        }
        return Response.status(Response.Status.NOT_MODIFIED).entity("no item found with given id").build();
    }

    /**
     * HTTP Method DELETE
     * http://localhost:9443/app/api/1.0/items/3
     */
    @DELETE
    @Path("/{itemId}")
    @ApiOperation(value = "delete Item",
            notes = "This method deletes item from database when given id matches one in the database")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteItemByItemId(@PathParam("itemId") Long itemId) {
        ItemResponse itemResponse = itemService.deleteById(itemId);
        if (itemResponse.isInError()) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(itemResponse).build();
        }
        return Response.status(Response.Status.OK).entity(itemResponse).build();
    }

    /**
     * HTTP Method DELETE
     * http://localhost:9443/app/api/1.0/items
     */
    @DELETE
    @ApiOperation(value = "delete all Item",
            notes = "This method deletes all item from database")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAllItems() {
        itemService.deleteAll();
        return Response.status(Response.Status.OK).entity("All items deleted successfully").build();
    }
}

