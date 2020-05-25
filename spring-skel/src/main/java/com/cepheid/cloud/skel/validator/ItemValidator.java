package com.cepheid.cloud.skel.validator;

import com.cepheid.cloud.skel.model.State;
import org.springframework.util.StringUtils;

public class ItemValidator {

    public static boolean isStateValid(String state){
        if(StringUtils.isEmpty(state)){
            return false;
        }
        try {
            State.valueOf(state);
        }catch (IllegalArgumentException e){
            return false;
        }
        return true;
    }

    public static boolean isNameValid(String name){
        return !StringUtils.isEmpty(name);
    }
}
