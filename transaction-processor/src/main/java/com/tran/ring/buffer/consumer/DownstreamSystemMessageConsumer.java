package com.tran.ring.buffer.consumer;

import com.tran.downstream.systems.DownStreamSystemConfig;
import com.tran.downstream.systems.DownstreamMessageHandler;
import com.tran.ring.buffer.objects.DownstreamMessageObject;
import io.micrometer.core.instrument.Timer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static com.tran.metrics.TPCustomerReportMetricsStore.timer;
import static com.tran.utils.MetricUtils.DS_MESSAGE_CONSUMER_TIMER_PREFIX;

@Component("downstreamSystemMessageConsumer")
@Slf4j
@AllArgsConstructor
public class DownstreamSystemMessageConsumer implements IObjectConsumer<DownstreamMessageObject> {

  @Override
  public void handle(DownstreamMessageObject downstreamMessageObject) {
    Timer timer = timer(downstreamMessageObject.getDownstreamSystemName() + DS_MESSAGE_CONSUMER_TIMER_PREFIX);
    long start = System.currentTimeMillis();
    DownstreamMessageHandler handler = DownStreamSystemConfig.getInstance().getDownStreamMessageHandler(downstreamMessageObject.getDownstreamSystemName());
    handler.send(downstreamMessageObject.getTransaction());
    timer.record(System.currentTimeMillis() - start, TimeUnit.MILLISECONDS);
  }
}
