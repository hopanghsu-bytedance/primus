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

package com.bytedance.primus.utils.concurrent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CallableExecutionUtilsTest {

  @Test(expected = TimeoutException.class)
  public void testExecuteCallableWithTimeout()
      throws InterruptedException, ExecutionException, TimeoutException {
    CallableExecutionUtilsBuilder builder = new CallableExecutionUtilsBuilder()
        .setCallable(() -> {
          TimeUnit.SECONDS.sleep(10);
          return true;
        })
        .setThreadName("testThread")
        .setTimeout(1)
        .setTimeUnit(TimeUnit.SECONDS);
    CallableExecutionUtils callableExecutionUtils = builder.createCallableExecutionUtils();
    callableExecutionUtils.doExecuteCallable(true);
  }

}
