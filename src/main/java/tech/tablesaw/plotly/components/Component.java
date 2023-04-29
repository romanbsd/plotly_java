package tech.tablesaw.plotly.components;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.pebbletemplates.pebble.PebbleEngine;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UncheckedIOException;
import java.util.Map;

import static tech.tablesaw.plotly.JsonMapper.JSON_MAPPER;

public abstract class Component {

  @JsonIgnore
  private final PebbleEngine engine = TemplateUtils.getNewEngine();

  protected PebbleEngine getEngine() {
    return engine;
  }

  protected abstract Map<String, Object> getJSONContext();

  public String asJSON() {
    try {
      return JSON_MAPPER.writeValueAsString(getJSONContext());
    } catch (IOException ioe) {
      throw new UncheckedIOException(ioe);
    }
  }

  @Override
  public String toString() {
    return asJSON();
  }
}
