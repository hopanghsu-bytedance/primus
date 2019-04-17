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

package com.bytedance.primus.am.schedule;

import com.bytedance.primus.am.role.RoleInfo;
import com.bytedance.primus.am.role.RoleInfoManager;
import com.bytedance.primus.am.schedulerexecutor.SchedulerExecutorManager;
import com.bytedance.primus.api.records.ExecutorId;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DynamicSchedulePolicyImpl implements SchedulePolicy {

  private static final Logger LOG = LoggerFactory.getLogger(DynamicSchedulePolicyImpl.class);

  private SchedulerExecutorManager schedulerExecutorManager;
  private RoleInfoManager roleInfoManager;

  public DynamicSchedulePolicyImpl(
      SchedulerExecutorManager schedulerExecutorManager,
      RoleInfoManager roleManager) {
    this.schedulerExecutorManager = schedulerExecutorManager;
    this.roleInfoManager = roleManager;
  }

  @Override
  public boolean canSchedule(ExecutorId executorId) {
    RoleInfo roleInfo = roleInfoManager.getRoleNameRoleInfoMap().get(executorId.getRoleName());
    if (roleInfo.getRoleSpec().getMinReplicas() > 0) {
      int priority = roleInfoManager.getRoleNamePriorityMap().get(executorId.getRoleName());
      int registeredNum = schedulerExecutorManager.getRegisteredNum(priority);
      int completedNum = schedulerExecutorManager.getCompletedNum(priority);
      if (roleInfo.getRoleSpec().getMinReplicas() > completedNum + registeredNum) {
        return false;
      }
    }
    return true;
  }
}
