package com.github.narcoticfresh;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.narcoticfresh.model.Configuration;
import com.github.narcoticfresh.model.JobConfig;
import java.io.File;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobConfiguration {

  private final static Logger LOG = LoggerFactory
      .getLogger(JobConfiguration.class);

  private static Configuration configuration;

  public JobConfiguration(String configFilePath) {
    try {
      String config = FileUtils.readFileToString(new File(configFilePath), "UTF-8");

      ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
      configuration = mapper.readValue(config, Configuration.class);

    } catch (Exception e) {
      LOG.error("Could not read configuration from '{}'", configFilePath, e);
    }
  }

  public static Configuration getConfiguration()
  {
    return configuration;
  }

  public static JobConfig getJobConfiguration(String jobName)
  {
    return configuration.getJobs().get(jobName);
  }
}
