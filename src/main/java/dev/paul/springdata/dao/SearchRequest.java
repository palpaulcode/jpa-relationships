package dev.paul.springdata.dao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchRequest {
    private String firstname;
    private String lastname;
    private String email;
}
