package hl.sc.demo.employee.controller;

import hl.sc.demo.employee.model.Employee;
import hl.sc.demo.employee.repository.EmployeeRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

/**
 * @author: hl
 * @created: 2019-08-27 14:32
 **/
@RunWith(SpringRunner.class)
@WebFluxTest(EmployeeController.class)
public class EmployeeControllerTest {
    @MockBean
    EmployeeRepository employeeRepository;
    /*@Autowired
    private MockMvc mockMvc;*/
    @Autowired
    private WebTestClient testClient;

    /*@Autowired
    ReactiveRedisConnectionFactory factory;

    @Autowired
    ReactiveRedisOperations<String, Employee> reactiveListOperations;

    //@Before
    public void setUp() {
        var employeeStream = Stream.of(
                new Employee(1L, 1L, "John Smith", 34, "Analyst"),
                new Employee(1L, 1L, "Darren Hamilton", 37, "Manager"),
                new Employee(1L, 1L, "Tom Scott", 26, "Developer"),
                new Employee(1L, 2L, "Anna London", 39, "Analyst"),
                new Employee(1L, 2L, "Patrick Dempsey", 27, "Developer"),
                new Employee(2L, 3L, "Kevin Price", 38, "Developer"),
                new Employee(2L, 3L, "Ian Scott", 34, "Developer"),
                new Employee(2L, 3L, "Andrew Campton", 30, "Manager"),
                new Employee(2L, 4L, "Steve Franklin", 25,
                        "Developer"),
                new Employee(2L, 4L, "Elisabeth Smith", 30, "Developer"));

        factory.getReactiveConnection()
                .serverCommands()
                .flushAll()
                .thenMany(
                        Flux.fromStream(employeeStream)
                                .flatMap(coffee -> reactiveListOperations.opsForList().rightPush(
                                        EmployeeRepository.EMPLOYEE, coffee)))
                .thenMany(reactiveListOperations.opsForList().range(EmployeeRepository.EMPLOYEE, 0, -1)
                        .map(Employee::getName))
                .subscribe(System.out::println);
    }*/

    @Test
    public void add() throws Exception {
        // prepare data and mock's behaviour
        Employee empStub = new Employee(5L, 5L, "tree", 50, "12000");
        /*StepVerifier.create(employeeRepository.add(empStub))
                .expectNext(empStub)
                .verifyComplete();*/
        /*
        when(employeeRepository.add(any(Employee.class)))
                .thenReturn(Mono.just(empStub));*/

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
        Assert.assertEquals(responseBody.getId(), empStub.getId());
        Assert.assertEquals(responseBody.getName(),
                empStub.getName());

        /*
        // execute
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(new Gson().toJson(empStub)).andReturn();

        // verify
        int status = result.getResponse().getStatus();
        assertEquals(HttpStatus.CREATED.value(), status, "Incorrect Response Status");

        // verify that service method was called once
        verify(employeeRepository).add(any(Employee.class));

        Employee resultEmployee = new Gson().fromJson(result.getResponse()
        .getContentAsString(), Employee.class);
        assertNotNull(resultEmployee);
        assertEquals(1l, resultEmployee.getId().longValue());*/
    }
}