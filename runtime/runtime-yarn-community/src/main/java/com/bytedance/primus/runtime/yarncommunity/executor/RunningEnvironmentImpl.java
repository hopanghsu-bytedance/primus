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

package com.bytedance.primus.runtime.yarncommunity.executor;

import com.bytedance.primus.common.model.ApplicationConstants;
import com.bytedance.primus.common.model.records.ContainerId;
import com.bytedance.primus.executor.environment.RunningEnvironment;
import java.util.Map;

public class RunningEnvironmentImpl implements RunningEnvironment {

  private static volatile boolean EXECUTOR_JVM_SHUTDOWN = false;

  public void setExecutorJvmShutdown(boolean value) {
    EXECUTOR_JVM_SHUTDOWN = value;
  }

  @Override
  public Boolean hasExecutorJvmShutdown() {
    return EXECUTOR_JVM_SHUTDOWN;
  }

  @Override
  public String getApplicationId() {
    Map<String, String> systemEnvs = System.getenv();
    ContainerId containerId = ContainerId
        .fromString(systemEnvs.get(ApplicationConstants.Environment.CONTAINER_ID.toString()));
    String applicationIdStr = containerId.getApplicationAttemptId().getApplicationId().toString();
    return applicationIdStr;
  }
}
