package com.mindhub.homebanking.RepositoriesTest;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class ClientsTest {
    @Autowired
    ClientRepository clientRepository;

    @Test
    public void existClients() {
        List<Client> clients = clientRepository.findAll();
        //assertThat(clients, is(not(empty())));
    }

    @Test
    public void clientExist() {
        List<Client> clients = clientRepository.findAll();
        //assertThat(clients, hasItem(hasProperty("firstName", is("Melba"))));
    }
}
