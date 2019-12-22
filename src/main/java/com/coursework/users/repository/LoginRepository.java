package com.coursework.users.repository;

import com.coursework.users.model.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by callummarriage on 08/12/2019.
 */
@Repository
public interface LoginRepository extends CrudRepository<Client, Integer> {

    Optional<Client> findByUsername(String username);
}
