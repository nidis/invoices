package com.nidis.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.hateoas.core.Relation;

@Relation(value = "customer", collectionRelation = "customers")
@Getter
public class CustomerResource {
    private Long customerId;
    private String firstName;
    private String lastName;
    private Integer age;
    private String email;

    @JsonCreator
    public CustomerResource(@JsonProperty("customerId") Long customerId,
                            @JsonProperty("firstName") String firstName,
                            @JsonProperty("lastName") String lastName,
                            @JsonProperty("age") Integer age,
                            @JsonProperty("email") String email) {
        super();
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
    }
}
