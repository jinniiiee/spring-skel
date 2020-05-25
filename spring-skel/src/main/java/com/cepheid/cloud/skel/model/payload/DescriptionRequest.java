package com.cepheid.cloud.skel.model.payload;

import com.cepheid.cloud.skel.model.Description;
import com.cepheid.cloud.skel.model.Type;
import com.cepheid.cloud.skel.validator.DescriptionValidator;

public class DescriptionRequest implements RequestBody<Description>{
    private String summary;

    private String type;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public Description getModel() {
        if(DescriptionValidator.isSummaryValid(summary) && DescriptionValidator.isTypeValid(type)) {
            return new Description(summary, Type.valueOf(type));
        }
        return null;
    }
}
