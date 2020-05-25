package com.cepheid.cloud.skel.model.payload;

import com.cepheid.cloud.skel.model.AbstractEntity;

public interface RequestBody<T extends AbstractEntity> {
    T getModel();

}
