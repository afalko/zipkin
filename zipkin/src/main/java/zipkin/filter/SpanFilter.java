package zipkin.filter;

import zipkin.Span;
import zipkin.storage.Callback;

import java.util.List;

public interface SpanFilter {
  /**
   * Process filters given a set of v1 spans. The callback should return a FilterActivatedException if filter
   * implementor wants to produce custom return codes back to the user.
   * @param spans
   * @param callback
   */
  List<Span> processV1(List<Span> spans, Callback<Void> callback);

  /**
   * Process filters given a set of v2 spans. The callback should return a FilterActivatedException if filter
   * implementor wants to produce custom return codes back to the user.
   * @param spans
   * @param callback
   */
  List<zipkin2.Span> processV2(List<zipkin2.Span> spans, Callback<Void> callback);
}
