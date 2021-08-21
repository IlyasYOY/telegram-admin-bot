package ru.ilyasyoy.telegram.admin;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.ilyasyoy.telegram.admin.domain.repository.DomainRepository;

@SpringBootTest
@ActiveProfiles("test")
public class BaseIntegrationTest {

    @Autowired private List<DomainRepository> domainRepositories;

    @BeforeEach
    void clear() {
        domainRepositories.forEach(repository -> repository.deleteAll());
    }
}
