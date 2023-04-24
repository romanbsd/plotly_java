package tech.tablesaw.plotly.traces;

import static tech.tablesaw.plotly.JsonMapper.JSON_MAPPER;
import static tech.tablesaw.plotly.Utils.dataAsString;
import static tech.tablesaw.plotly.Utils.toJSON;

import com.fasterxml.jackson.annotation.JsonValue;
import io.pebbletemplates.pebble.error.PebbleException;
import io.pebbletemplates.pebble.template.PebbleTemplate;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import tech.tablesaw.plotly.components.Marker;

public class Candlestick extends AbstractTrace {

  private final Object[] x;
  private final Marker marker;
  private final double[] open;
  private final double[] high;
  private final double[] low;
  private final double[] close;
  private final Map<String, Integer> line;

  private Candlestick(CandlestickBuilder builder) {
    super(builder);
    this.x = builder.x;
    this.open = builder.open;
    this.high = builder.high;
    this.low = builder.low;
    this.close = builder.close;
    this.marker = builder.marker;
    this.line = builder.line;
  }

  public static CandlestickBuilder builder(Object[] x, double[] open, double[] high, double[] low, double[] close) {
    return new CandlestickBuilder(x, open, high, low, close);
  }

  @Override
  public String asJavascript(int i) {
    Writer writer = new StringWriter();
    try {
      PebbleTemplate compiledTemplate = engine.getTemplate("trace_template.html");
      compiledTemplate.evaluate(writer, getContext(i));
    } catch (PebbleException e) {
      throw new IllegalStateException(e);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
    return writer.toString();
  }

  private Map<String, Object> getContext(int i) {
    Map<String, Object> context = super.getContext();
    context.put("variableName", "trace" + i);
    context.put("x", dataAsString(x));
    context.put("open", dataAsString(open));
    context.put("high", dataAsString(high));
    context.put("low", dataAsString(low));
    context.put("close", dataAsString(close));
    context.put("line", toJSON(line));

    if (marker != null) {
      context.put("marker", marker);
    }
    return context;
  }

  public static class CandlestickBuilder extends TraceBuilder {
    private final static String type = "candlestick";
    private final Object[] x;
    private final double[] open;
    private final double[] high;
    private final double[] low;
    private final double[] close;
    private final Map<String, Integer> line = new HashMap<>();
    private Marker marker;

    CandlestickBuilder(Object[] x, double[] open, double[] high, double[] low, double[] close) {
      this.x = x;
      this.open = open;
      this.high = high;
      this.low = low;
      this.close = close;
    }

    public Candlestick build() {
      return new Candlestick(this);
    }

    @Override
    public CandlestickBuilder opacity(double opacity) {
      super.opacity(opacity);
      return this;
    }

    @Override
    public CandlestickBuilder name(String name) {
      super.name(name);
      return this;
    }

    @Override
    public CandlestickBuilder showLegend(boolean b) {
      super.showLegend(b);
      return this;
    }

    public CandlestickBuilder marker(Marker marker) {
      this.marker = marker;
      return this;
    }

    @Override
    public CandlestickBuilder xAxis(String xAxis) {
      super.xAxis(xAxis);
      return this;
    }

    @Override
    public CandlestickBuilder yAxis(String yAxis) {
      super.yAxis(yAxis);
      return this;
    }

    public CandlestickBuilder lineWidth(int width) {
      line.put("width", width);
      return this;
    }

    @Override
    protected String getType() {
      return type;
    }
  }
}
