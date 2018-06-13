package zipkin2.collector.filter;

import zipkin2.Callback;
import zipkin2.Span;
import zipkin2.collector.CollectorMetrics;

import java.util.List;

public interface SpanFilter {
  /**
   * Process filters given a set of v1 spans. The callback should return a FilterActivatedException if filter
   * implementor wants to produce custom return codes back to the user.
   * @param spans
   * @param metrics
   * @param callback
   */
  List<Span> process(List<Span> spans, CollectorMetrics metrics, Callback<Void> callback);
}
