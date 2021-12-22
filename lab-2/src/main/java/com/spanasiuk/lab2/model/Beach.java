package com.spanasiuk.lab2.model;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;

@Data
@AllArgsConstructor
public class Beach {

  @Default
  private List<Person> personList = new ArrayList<>();
  private Boolean boatPresent;
}
