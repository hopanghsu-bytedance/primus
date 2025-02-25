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

package com.bytedance.primus.am.schedulerexecutor;

import com.bytedance.primus.common.model.records.Container;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class SchedulerExecutorManagerContainerCompletedEvent extends SchedulerExecutorManagerEvent {

  private Container container;
  private int exitCode;
  private String exitMsg;

  public SchedulerExecutorManagerContainerCompletedEvent(SchedulerExecutorManagerEventType type,
      Container container, int exitCode, String exitMsg) {
    super(type);
    this.container = container;
    this.exitCode = exitCode;
    this.exitMsg = exitMsg;
  }

  public Container getContainer() {
    return container;
  }

  public int getExitCode() {
    return exitCode;
  }

  public String getExitMsg() {
    return exitMsg;
  }

  @Override
  public String toJsonString() throws JSONException {
    JSONObject json = new JSONObject();
    json.put("containerId", container.getId());
    json.put("node", container.getNodeId().getHost());
    json.put("exitCode", getExitCode());
    json.put("exitMsg", getExitMsg());
    return json.toString();
  }
}
