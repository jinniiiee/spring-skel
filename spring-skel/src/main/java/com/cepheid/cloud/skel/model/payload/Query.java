package com.cepheid.cloud.skel.model.payload;

import org.springframework.data.domain.Example;

public interface Query<T extends Example> {
     T getExample();
}
