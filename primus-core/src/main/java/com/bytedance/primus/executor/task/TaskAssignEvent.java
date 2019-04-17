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

package com.bytedance.primus.executor.task;

import com.bytedance.primus.api.records.ExecutorId;
import com.bytedance.primus.api.records.Task;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class TaskAssignEvent extends TaskRunnerManagerEvent {
  private Task task;

  public TaskAssignEvent(Task task, ExecutorId executorId) {
    super(TaskRunnerManagerEventType.TASK_ASSIGN, executorId);
    this.task = task;
  }

  public Task getTask() {
    return task;
  }

  @Override
  public String toJsonString() throws JSONException {
    JSONObject json = new JSONObject();
    json.put("taskId", task.getTaskId());
    json.put("type", task.getTaskType());
    json.put("attemptNum", task.getNumAttempt());
    return json.toString();
  }
}
