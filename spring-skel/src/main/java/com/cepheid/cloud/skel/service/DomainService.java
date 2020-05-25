package com.cepheid.cloud.skel.service;

import com.cepheid.cloud.skel.model.AbstractEntity;
import org.springframework.data.domain.Example;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;


public interface DomainService<T extends AbstractEntity> {

    Optional<T> getById(Long id);

    List<T> getAll(Example query);

    Response save(T model);

    Response update(Long id, T model);

    Response delete(Long id);


}
