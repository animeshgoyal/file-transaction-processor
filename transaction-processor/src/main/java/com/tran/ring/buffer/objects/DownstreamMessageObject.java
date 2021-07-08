package com.tran.ring.buffer.objects;

import com.tran.model.Transaction;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DownstreamMessageObject {
  private final String downstreamSystemName;
  private final Transaction transaction;
}
