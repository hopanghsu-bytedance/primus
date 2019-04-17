/*
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
 *
 * This file may have been modified by Bytedance Inc.
 */

package com.bytedance.primus.common.model.records;

import com.bytedance.primus.common.model.records.impl.pb.ApplicationIdPBImpl;
import java.text.NumberFormat;

/**
 * <p><code>ApplicationId</code> represents the <em>globally unique</em>
 * identifier for an application.</p>
 *
 * <p>The globally unique nature of the identifier is achieved by using the
 * <em>cluster timestamp</em> i.e. start-time of the
 * <code>ResourceManager</code> along with a monotonically increasing counter
 * for the application.</p>
 */


public abstract class ApplicationId implements Comparable<ApplicationId> {


  public static final String appIdStrPrefix = "application_";


  public static ApplicationId newInstance(long clusterTimestamp, int id) {
    ApplicationId appId = new ApplicationIdPBImpl();
    appId.setClusterTimestamp(clusterTimestamp);
    appId.setId(id);
    appId.build();
    return appId;
  }

  /**
   * Get the short integer identifier of the <code>ApplicationId</code> which is unique for all
   * applications started by a particular instance of the <code>ResourceManager</code>.
   *
   * @return short integer identifier of the <code>ApplicationId</code>
   */


  public abstract int getId();


  protected abstract void setId(int id);

  /**
   * Get the <em>start time</em> of the <code>ResourceManager</code> which is used to generate
   * globally unique <code>ApplicationId</code>.
   *
   * @return <em>start time</em> of the <code>ResourceManager</code>
   */


  public abstract long getClusterTimestamp();


  protected abstract void setClusterTimestamp(long clusterTimestamp);

  protected abstract void build();

  static final ThreadLocal<NumberFormat> appIdFormat =
      new ThreadLocal<NumberFormat>() {
        @Override
        public NumberFormat initialValue() {
          NumberFormat fmt = NumberFormat.getInstance();
          fmt.setGroupingUsed(false);
          fmt.setMinimumIntegerDigits(4);
          return fmt;
        }
      };

  @Override
  public int compareTo(ApplicationId other) {
    if (this.getClusterTimestamp() - other.getClusterTimestamp() == 0) {
      return this.getId() - other.getId();
    } else {
      return this.getClusterTimestamp() > other.getClusterTimestamp() ? 1 :
          this.getClusterTimestamp() < other.getClusterTimestamp() ? -1 : 0;
    }
  }

  @Override
  public String toString() {
    return appIdStrPrefix + this.getClusterTimestamp() + "_"
        + appIdFormat.get().format(getId());
  }


  public static ApplicationId fromString(String appIdStr) {
    if (!appIdStr.startsWith(appIdStrPrefix)) {
      throw new IllegalArgumentException("Invalid ApplicationId prefix: "
          + appIdStr + ". The valid ApplicationId should start with prefix "
          + appIdStrPrefix);
    }
    try {
      int pos1 = appIdStrPrefix.length() - 1;
      int pos2 = appIdStr.indexOf('_', pos1 + 1);
      if (pos2 < 0) {
        throw new IllegalArgumentException("Invalid ApplicationId: "
            + appIdStr);
      }
      long rmId = Long.parseLong(appIdStr.substring(pos1 + 1, pos2));
      int appId = Integer.parseInt(appIdStr.substring(pos2 + 1));
      ApplicationId applicationId = ApplicationId.newInstance(rmId, appId);
      return applicationId;
    } catch (NumberFormatException n) {
      throw new IllegalArgumentException("Invalid ApplicationId: "
          + appIdStr, n);
    }
  }

  @Override
  public int hashCode() {
    // Generated by eclipse.
    final int prime = 371237;
    int result = 6521;
    long clusterTimestamp = getClusterTimestamp();
    result = prime * result
        + (int) (clusterTimestamp ^ (clusterTimestamp >>> 32));
    result = prime * result + getId();
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    ApplicationId other = (ApplicationId) obj;
    if (this.getClusterTimestamp() != other.getClusterTimestamp()) {
      return false;
    }
    if (this.getId() != other.getId()) {
      return false;
    }
    return true;
  }
}
