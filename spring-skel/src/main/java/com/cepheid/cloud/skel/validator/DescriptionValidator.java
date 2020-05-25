package com.cepheid.cloud.skel.validator;

import com.cepheid.cloud.skel.model.Type;
import org.springframework.util.StringUtils;

public class DescriptionValidator {

    public static boolean isTypeValid(String type){
        if(StringUtils.isEmpty(type)){
            return false;
        }
        try {
            Type.valueOf(type);
        }catch (IllegalArgumentException e){
            return false;
        }
        return true;
    }

    public static boolean isSummaryValid(String summary){
        return !StringUtils.isEmpty(summary);
    }
}
