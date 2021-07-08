package com.tran.ring.buffer.objects;

import com.tran.model.Transaction;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageObject {
  private final Transaction transaction;
}
