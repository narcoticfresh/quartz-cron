package com.github.narcoticfresh.model;

import java.util.ArrayList;

public class JobConfig {

  private String name;
  private String schedule;
  private ArrayList<String> command;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSchedule() {
    return schedule;
  }

  public void setSchedule(String schedule) {
    this.schedule = schedule;
  }

  public ArrayList<String> getCommand() {
    return command;
  }

  public void setCommand(ArrayList<String> command) {
    this.command = command;
  }
}
