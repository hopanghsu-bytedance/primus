/*
 * Copyright 2022 Bytedance Inc.
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bytedance.primus.io.messagebuilder;

import java.io.IOException;
import org.apache.hadoop.io.BytesWritable;

public class PbMessageBuilder extends MessageBuilder {

  public PbMessageBuilder(int bufferSize) {
    super(bufferSize);
  }

  @Override
  protected void writeKey(Object key) throws IOException {
    BytesWritable tmpKey = (BytesWritable) key;
    buffer.putLong(Long.reverseBytes(tmpKey.getLength()));
    buffer.put(tmpKey.getBytes(), 0, tmpKey.getLength());
  }

  @Override
  protected void writeValue(Object value) throws IOException {
    BytesWritable tmpValue = (BytesWritable) value;
    buffer.putLong(Long.reverseBytes(tmpValue.getLength()));
    buffer.put(tmpValue.getBytes(), 0, tmpValue.getLength());
  }
}
