package com.cepheid.cloud.skel.controller;

import com.cepheid.cloud.skel.model.Description;
import com.cepheid.cloud.skel.model.Item;
import com.cepheid.cloud.skel.model.payload.DescriptionRequest;
import com.cepheid.cloud.skel.service.DomainService;
import com.cepheid.cloud.skel.service.ItemService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.Optional;

@Component
@Path("/api/1.0/items")
@Api()
public class DescriptionController {
    private final DomainService<Description> mDomainService;

    private final ItemService mItemService;


    @Autowired
    public DescriptionController(DomainService descriptionService, ItemService itemService) {
        mDomainService = descriptionService;
        mItemService = itemService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path(value = "/descriptions")
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Collection<Description> getAllDescriptions() {
        return mDomainService.getAll(Example.of(new Description()));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    @Path(value = "/descriptions/{id}")
    public Optional<Description> getDescriptionById(@PathParam(value = "id") Long id){
        return mDomainService.getById(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional(propagation = Propagation.REQUIRED)
    @Path(value = "/{itemId}/descriptions")
    public Response addDescription(@PathParam(value = "itemId") Long itemId, DescriptionRequest descriptionRequest){
        Description model = descriptionRequest.getModel();
        if(model == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        Optional<Item> itemReturned = mItemService.getById(itemId);
        if(itemReturned.isEmpty()){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        model.setItem(itemReturned.get());
        return mDomainService.save(model);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional(propagation = Propagation.REQUIRED)
    @Path(value = "/descriptions/{id}")
    public Response updateDescription(@PathParam(value = "id") Long id,
                                                   DescriptionRequest descriptionRequest){

        return mDomainService.update(id, descriptionRequest.getModel());
    }
}
