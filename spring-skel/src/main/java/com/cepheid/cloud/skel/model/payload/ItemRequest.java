package com.cepheid.cloud.skel.model.payload;

import com.cepheid.cloud.skel.model.Item;
import com.cepheid.cloud.skel.model.State;
import com.cepheid.cloud.skel.validator.ItemValidator;

import java.util.Set;
import java.util.stream.Collectors;

public class ItemRequest implements RequestBody<Item> {

    private String name;
    private Set<DescriptionRequest> descriptions;
    private String state;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<DescriptionRequest> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(Set<DescriptionRequest> descriptions) {
        this.descriptions = descriptions;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public Item getModel() {
        if(ItemValidator.isNameValid(name) && ItemValidator.isStateValid(state)) {
            Item item = new Item(name, State.valueOf(state));
            if(descriptions != null) {
                item.setDescriptions(descriptions.stream().map(DescriptionRequest::getModel).collect(Collectors.toSet()));
            }
            return item;
        }
        return null;
    }
}
