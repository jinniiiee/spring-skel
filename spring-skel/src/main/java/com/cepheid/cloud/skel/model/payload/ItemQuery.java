package com.cepheid.cloud.skel.model.payload;

import com.cepheid.cloud.skel.model.Item;
import com.cepheid.cloud.skel.model.State;
import com.cepheid.cloud.skel.validator.ItemValidator;

import org.springframework.data.domain.Example;

public class ItemQuery implements Query{

    private String name;

    private String state;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public ItemQuery(String name, String state) {
        this.name = name;
        this.state = state;
    }

    @Override
    public Example getExample() {
        Item itemExample = new Item();
        if(ItemValidator.isNameValid(name)){
            itemExample.setName(name);
        }
        if(ItemValidator.isStateValid(state)){
            itemExample.setState(State.valueOf(state));
        }
        return Example.of(itemExample);
    }

}
