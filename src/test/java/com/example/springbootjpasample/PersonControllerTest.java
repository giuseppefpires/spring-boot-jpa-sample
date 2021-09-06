package com.example.springbootjpasample;

import com.example.springbootjpasample.model.PersonDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PersonControllerTest {
    @Autowired
    private TestRestTemplate template;

    @LocalServerPort
    private int port;

    @Test
    void shouldInsert() {
        PersonDTO personDTO = getPersonDTO();
        PersonDTO dto = this.template.postForObject(getUrl(), personDTO, PersonDTO.class);

        Assertions.assertNotNull(dto);
        Assertions.assertNotNull(dto.getId());
        Assertions.assertEquals(personDTO.getName(), dto.getName());
        Assertions.assertEquals(personDTO.getSurname(), dto.getSurname());
        Assertions.assertEquals(personDTO.getAge(), dto.getAge());
    }

    @Test
    void shouldFindById() {
        PersonDTO personDTO = this.template.postForObject(getUrl(), getPersonDTO(), PersonDTO.class);
        PersonDTO dto = this.template.getForObject(getUrl() + personDTO.getId(), PersonDTO.class);
        Assertions.assertNotNull(dto.getId());
        Assertions.assertEquals(personDTO.getName(), dto.getName());
        Assertions.assertEquals(personDTO.getSurname(), dto.getSurname());
        Assertions.assertEquals(personDTO.getAge(), dto.getAge());
    }

    @Test
    void shouldReturnNotFound() {
        ResponseEntity<PersonDTO> responseEntity = this.template.getForEntity(getUrl() + Long.MAX_VALUE,
                PersonDTO.class);
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), responseEntity.getStatusCodeValue());
    }

    @Test
    void shouldUpdate() {
        PersonDTO dto = this.template.postForObject(getUrl(), getPersonDTO(), PersonDTO.class);
        dto.setName("Update");
        ResponseEntity<PersonDTO> entity = this.template.exchange(getUrl() + dto.getId(),
                HttpMethod.PUT,
                new HttpEntity<>(dto), PersonDTO.class);

        PersonDTO response = entity.getBody();

        Assertions.assertEquals(dto.getName(), response.getName());
        Assertions.assertEquals(dto.getSurname(), response.getSurname());
        Assertions.assertEquals(dto.getAge(), response.getAge());
    }

    @Test
    void shouldRemove() {
        PersonDTO PersonDTO = this.template.postForObject(getUrl(), getPersonDTO(), PersonDTO.class);
        ResponseEntity<PersonDTO> response = this.template.getForEntity(getUrl() + PersonDTO.getId(),
                PersonDTO.class);

        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        this.template.delete(getUrl() + PersonDTO.getId());
        ResponseEntity<PersonDTO> responseB = this.template.getForEntity(getUrl() + PersonDTO.getId(),
                PersonDTO.class);
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), responseB.getStatusCodeValue());
    }

    @Test
    void shouldFindAllByDefaultValues() {
        for (int index = 0; index < 20; index++) {
            this.template.postForObject(getUrl(), getPersonDTO(), PersonDTO.class);
        }
        ResponseEntity<List<PersonDTO>> response = this.template.exchange(getUrl(), HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });

        List<PersonDTO> body = response.getBody();
        Assertions.assertEquals(10, body.size());
    }

    @Test
    void shouldFindAllOverWriteValues() {
        for (int index = 0; index < 20; index++) {
            this.template.postForObject(getUrl(), getPersonDTO(), PersonDTO.class);
        }

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getUrl())
                .queryParam("page", 2)
                .queryParam("size", 5);

        ResponseEntity<PersonDTO[]> response = this.template.exchange(builder.toUriString(), HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });

        PersonDTO[] body = response.getBody();
        Assertions.assertEquals(5, body.length);

    }

    private PersonDTO getPersonDTO() {
        PersonDTO PersonDTO = new PersonDTO();
        PersonDTO.setName("Maria");
        PersonDTO.setSurname("da Luz");
        PersonDTO.setAge(65);
        return PersonDTO;
    }

    private String getUrl() {
        return "http://localhost:" + port + "/person/";
    }
}
