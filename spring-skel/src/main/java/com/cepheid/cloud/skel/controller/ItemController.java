package com.cepheid.cloud.skel.controller;

import com.cepheid.cloud.skel.model.Item;
import com.cepheid.cloud.skel.model.payload.ItemQuery;
import com.cepheid.cloud.skel.model.payload.ItemRequest;
import com.cepheid.cloud.skel.service.DomainService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;


// curl http:/localhost:9443/app/api/1.0/items

@Component
@Path("/api/1.0/items")
@Api()
public class ItemController {

  private final DomainService<Item> mDomainService;

  @Autowired
  public ItemController(DomainService itemService) {
    mDomainService = itemService;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
  public Collection<Item> getItems(@QueryParam(value = "name") String name, @QueryParam(value = "state") String state) {
    if(name == null && state == null){
      return mDomainService.getAll(Example.of(new Item()));
    }
    return mDomainService.getAll(new ItemQuery(name, state).getExample());
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
  @Path(value = "/{id}")
  public Optional<Item> getItemById(@PathParam(value = "id") Long id){
    return mDomainService.getById(id);
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Transactional(propagation = Propagation.REQUIRED)
  public Response createItem(ItemRequest requestBody){
    return mDomainService.save(requestBody.getModel());
  }

  @PUT
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Path(value = "/{id}")
  @Transactional(propagation = Propagation.REQUIRED)
  public Response updateItem(@PathParam(value = "id") Long id, ItemRequest requestBody){
    return mDomainService.update(id, requestBody.getModel());
  }


  @DELETE
  @Produces(MediaType.APPLICATION_JSON)
  @Transactional(propagation = Propagation.REQUIRED)
  @Path(value = "/{id}")
  public Response deleteItem(@PathParam(value = "id") Long id){
    return mDomainService.delete(id);
  }

}
