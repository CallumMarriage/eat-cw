package com.coursework.users.service;

import com.coursework.users.model.Client;
import com.coursework.users.repository.LoginRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by callummarriage on 08/12/2019.
 */
@Service
@Slf4j
public class LoginService {

    private final LoginRepository loginRepository;

    @Autowired
    public LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }


    public Boolean validate(String username, String password){

        Optional<Client> client = loginRepository.findByUsername(username);

        return client.isPresent() && client.get().getPassword().equals(password);

    }


    public Boolean checkIfInUse(String username){
        Optional<Client> client = loginRepository.findByUsername(username);

        client.ifPresent(client1 -> {
            log.info(client.get().getUsername());
        });
        return client.isPresent();
    }

    public Integer getClientIdByUsername(String username){
        Optional<Client> client = loginRepository.findByUsername(username);

        return client.map(Client::getClientId).orElse(null);
    }

    public Client addNewUser(Client client){

        return loginRepository.save(client);
    }
}
