package com.cepheid.cloud.skel.service;

import com.cepheid.cloud.skel.model.Description;
import com.cepheid.cloud.skel.model.Item;
import com.cepheid.cloud.skel.repository.DescriptionRepository;
import com.cepheid.cloud.skel.repository.ItemRepository;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.*;

@Service
public class ItemService implements DomainService<Item> {

    private final ItemRepository mItemRepository;

    private final DescriptionService mDescriptionService;

    public ItemService(ItemRepository mItemRepository, DescriptionRepository mDescriptionRepository, DescriptionService mDescriptionService) {
        this.mItemRepository = mItemRepository;
        this.mDescriptionService = mDescriptionService;
    }

    @Override
    public Optional<Item> getById(Long itemId){
        return mItemRepository.findById(itemId);
    }

    @Override
    public List<Item> getAll(Example example) {
        return mItemRepository.findAll(example);
    }

    @Override
    public Response save(Item model) {
        if(model == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        Set<Description> descriptions = model.getDescriptions();
        if(descriptions == null || descriptions.isEmpty()){
            mItemRepository.save(model);
            return Response.status(Response.Status.CREATED.getStatusCode())
                    .header("Id", String.format("%s", model.getId())).build();
        }
        descriptions.forEach(description -> {
            description.setItem(model);
        });
        mItemRepository.save(model);
        return Response.status(Response.Status.CREATED.getStatusCode())
                .header("Id", String.format("%s", model.getId())).build();
    }

    @Override
    public Response update(Long itemId, Item model) {
        if(model == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        Optional<Item> itemReturned = mItemRepository.findById(itemId);
        if(itemReturned.isEmpty()){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Item item = itemReturned.get();
        item.setName(model.getName());
        item.setState(model.getState());
        // if desc requested to be added or removed
        Set<Description> descriptions = model.getDescriptions();
        if(descriptions == null){
            mItemRepository.save(item);
            return Response.status(Response.Status.OK.getStatusCode()).build();
        }
        // update description as well
        mDescriptionService.updateDescription(descriptions, item);
        mItemRepository.save(item);
        return Response.status(Response.Status.OK.getStatusCode()).build();

    }

    @Override
    public Response delete(Long itemId){
        mItemRepository.deleteById(itemId);
        return Response.status(Response.Status.OK.getStatusCode()).build();
    }

}
