package com.cepheid.cloud.skel.service;

import com.cepheid.cloud.skel.model.Description;
import com.cepheid.cloud.skel.model.Item;
import com.cepheid.cloud.skel.repository.DescriptionRepository;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class DescriptionService implements DomainService<Description>{

    private final DescriptionRepository mDescriptionRepository;

    public DescriptionService(DescriptionRepository mDescriptionRepository) {
        this.mDescriptionRepository = mDescriptionRepository;
    }

    @Override
    public List<Description> getAll(Example example) {
        return mDescriptionRepository.findAll(example);
    }

    @Override
    public Optional<Description> getById(Long id) {
        return mDescriptionRepository.findById(id);
    }

    @Override
    public Response save(Description description) {
        mDescriptionRepository.save(description);
        return Response.status(Response.Status.CREATED.getStatusCode())
                .header("Id", String.format("%s", description.getId())).build();
    }

    @Override
    public Response update(Long descriptionId, Description model) {
        if(model == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        Optional<Description> descReturned = mDescriptionRepository.findById(descriptionId);
        if(descReturned.isEmpty()){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Description description = descReturned.get();
        setDescription(model, description);
        mDescriptionRepository.save(description);
        return Response.status(Response.Status.OK.getStatusCode()).build();
    }

    @Override
    public Response delete(Long descriptionId) {
        mDescriptionRepository.deleteById(descriptionId);
        return Response.status(Response.Status.OK.getStatusCode()).build();
    }

    public void setDescription(Description model, Description description) {
        description.setSummary(model.getSummary());
        description.setType(model.getType());
    }

    public void updateDescription(Set<Description> model, Item item) {
        // delete existing description references
        Set<Description> descriptions = item.getDescriptions();
        if(descriptions != null){
            descriptions.forEach(mDescriptionRepository::delete);
        }
        // add new descriptions
        if(model !=null){
            model.forEach(description -> description.setItem(item));
        }
        item.setDescriptions(model);
    }


}
