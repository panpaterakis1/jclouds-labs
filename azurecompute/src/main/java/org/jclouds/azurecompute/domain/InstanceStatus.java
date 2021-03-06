/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jclouds.azurecompute.domain;

import com.google.common.base.CaseFormat;

import static com.google.common.base.Preconditions.checkNotNull;

public enum InstanceStatus {

	/**
	 * The role state is currently unknown. The state should automatically be
	 * resolved once the role state is detected, so no action is required.
	 */
	ROLE_STATE_UNKNOWN,

	/**
	 * The host agent is currently creating resources for the Virtual Machine
	 * (VM).
	 */
	CREATING_V_M,

	/**
	 * The host agent is starting the Virtual Machine.
	 */
	STARTING_V_M,

	/**
	 * Windows Azure is creating resources for the role.
	 */
	CREATING_ROLE,

	/**
	 * Windows Azure is starting the role.
	 */
	STARTING_ROLE,

	/**
	 * The role instance has started and is ready to be used.
	 */
	READY_ROLE,

	/**
	 * The role instance is unavailable for requests. This state is usually
	 * generated while the role is being created or stopped.
	 */
	BUSY_ROLE,

	/**
	 * Windows Azure is stopping the role.
	 */
	STOPPING_ROLE,

	/**
	 * The host agent is stopping the Virtual Machine. This status also
	 * indicates that the role has already been stopped.
	 */
	STOPPING_V_M,

	/**
	 * The Virtual Machine is being deleted by the host agent.
	 */
	DELETING_V_M,

	/**
	 * The Virtual Machine is not running. This is the final state of the
	 * shutdown process, and no other status messages should be received after
	 * StoppedVM.
	 */
	STOPPED_V_M,

	/**
	 * The role has unexpectedly stopped or has failed to start. This status
	 * indicates that there is a problem with the role that is causing it to
	 * crash or preventing it from starting, and must be corrected before the
	 * role can be started. The InstanceStateDetails and InstanceErrorCode
	 * fields can hold information about the role error that caused this state,
	 * which may be useful for identifying and debugging the problem.
	 */
	RESTARTING_ROLE,

	/**
	 * The role has continually crashed after being started by Windows Azure.
	 * This status indicates that there is a problem with the role that prevents
	 * it from starting, and may be generated after the StartingRole even
	 * ReadyRole statuses are received. The problem in the role must be found
	 * and corrected before the role can be started. The InstanceStateDetails
	 * and InstanceErrorCode fields can hold information about the role error
	 * that caused this state, which may be useful for identifying and debugging
	 * the problem.
	 */
	CYCLING_ROLE,

	/**
	 * The role has continually failed to start. This status indicates that
	 * there is a problem with the role that prevents it from starting, and may
	 * be generated after the process returns StartingRole. The problem in the
	 * role must be found and corrected before the role can be started. The
	 * InstanceStateDetails and InstanceErrorCode fields can hold information
	 * about the role error that caused this state, which may be useful for
	 * identifying and debugging the problem.
	 */
	FAILED_STARTING_ROLE,

	/**
	 * A Windows Azure or container error is preventing the Virtual Machine from
	 * starting. This status is generated by Windows Azure, and does not
	 * indicate an error with the role. It may be generated after the
	 * StartingRole state.
	 */
	FAILED_STARTING_V_M,

	/**
	 * The role has timed out before receiving a status message and is not
	 * responding to requests.
	 */
	UNRESPONSIVE_ROLE,

	/**
	 * UNDOCUMENTED BY AZURE
	 */
	PROVISIONING;

	public String value() {
		return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, name());
	}

	@Override
	public String toString() {
		return value();
	}

	public static InstanceStatus fromValue(String type) {
		try {
			return valueOf(CaseFormat.UPPER_CAMEL.to(
					CaseFormat.UPPER_UNDERSCORE, checkNotNull(type, "type")));
		} catch (IllegalArgumentException e) {
			return ROLE_STATE_UNKNOWN;
		}
	}
}
