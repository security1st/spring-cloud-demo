package hl.sc.demo.employee.controller;

import hl.sc.demo.employee.model.Employee;
import hl.sc.demo.employee.repository.EmployeeRedisRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @author: hl
 * @created: 2019-08-27 14:32
 **/
@RunWith(SpringRunner.class)
@WebFluxTest(EmployeeController.class)
@ImportAutoConfiguration(RefreshAutoConfiguration.class)
public class EmployeeControllerTest {
    @MockBean
    EmployeeRedisRepository employeeRedisRepository;

    @Autowired
    private WebTestClient testClient;

    @Test
    public void testAdd() {
        // prepare data and mock's behaviour
        Employee empStub = new Employee(5L, 5L, "tree", 50, "12000");
        when(employeeRedisRepository.add(any(Employee.class)))
                .thenReturn(Mono.just(empStub));

        var responseBody = testClient.post()
                                     .uri("/")
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .body(BodyInserters.fromObject(empStub))
                                     .exchange()
                                     .expectStatus()
                                     .isOk()
                                     .expectBody(Employee.class)
                                     .returnResult()
                                     .getResponseBody();

        Assert.assertNotNull(responseBody);
        Assert.assertEquals(responseBody.getName(),
                empStub.getName());
    }

    @Test
    public void testHi() {
        testClient.get().uri("/hi")
                  .exchange()
                  .expectStatus()
                  .isOk()
                  .expectBody().consumeWith(System.out::println);
    }
}