package com.github.narcoticfresh.model;

import java.util.HashMap;

public class Configuration {

  private HashMap<String, JobConfig> jobs;

  public HashMap<String, JobConfig> getJobs() {
    return jobs;
  }

  public void setJobs(HashMap<String, JobConfig> jobs) {
    this.jobs = jobs;
  }
}
