/*
 * Copyright (c) 2008-2013, Hazelcast, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hazelcast.config;

import com.hazelcast.cluster.TcpIpJoinerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import static com.hazelcast.util.ValidationUtil.hasText;
import static com.hazelcast.util.ValidationUtil.isNotNull;

/**
 * Contains the configuration for the Tcp/Ip join mechanism.
 *
 * The Tcp/Ip join mechanism relies on one or more well known members. So when a new member wants to join
 * a cluster, it will try to connect to one of the well known members. If it is able to connect, it will now
 * about all members in the cluster and doesn't rely on these well known members anymore.
 */
public class TcpIpConfig {

    private int connectionTimeoutSeconds = 5;

    private boolean enabled = false;

    private List<String> members = new ArrayList<String>();

    private String requiredMember = null;

    private String joinerFactoryClass = TcpIpJoinerFactory.class.getName();

    /**
     * Getter for joinerFactoryClass
     * @return
     */
    public String getJoinerFactoryClass() {
        return joinerFactoryClass;
    }

    /**
     * Setter for joinerFactoryClass
     * @param joinerFactoryClass
     * @return
     */
    public TcpIpConfig setJoinerFactoryClass(String joinerFactoryClass) {
        this.joinerFactoryClass = hasText(joinerFactoryClass, "joinerFactoryClass");
        return this;
    }

    /**
     * Returns the connection timeout.
     *
     * @return the connectionTimeoutSeconds
     * @see #setConnectionTimeoutSeconds(int)
     */
    public int getConnectionTimeoutSeconds() {
        return connectionTimeoutSeconds;
    }

    /**
     * Sets the connection timeout. This is the maximum amount of time Hazelcast is going to to try to
     * connect to a well known member before giving up. Setting it to a too low value could mean that a
     * member is not able to connect to a cluster. Setting it too a too high value means that member startup
     * could slow down because of longer timeouts (e.g. when a well known member is not up).
     *
     * @param connectionTimeoutSeconds the connection timeout in seconds.
     * @return the updated TcpIpConfig
     * @see #getConnectionTimeoutSeconds()
     * @throws IllegalArgumentException if connectionTimeoutSeconds is smaller than 0.
     */
    public TcpIpConfig setConnectionTimeoutSeconds(final int connectionTimeoutSeconds) {
        if(connectionTimeoutSeconds<0){
            throw new IllegalArgumentException("connection timeout can't be smaller than 0");
        }
        this.connectionTimeoutSeconds = connectionTimeoutSeconds;
        return this;
    }

    /**
     * Checks if the Tcp/Ip join mechanism is enabled.
     *
     * @return the enabled
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Enables or disables the Tcp/Ip join mechanism.
     *
     * @param enabled the enabled to set
     * @return TcpIpConfig the updated TcpIpConfig config.
     */
    public TcpIpConfig setEnabled(final boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    /**
     * Gets a list of all the well known members. If there are no well known members, the list will
     * be empty.
     *
     * @return the lsMembers
     * @see #setMembers(java.util.List)
     */
    public List<String> getMembers() {
        if (members == null) {
            members = new ArrayList<String>();
        }
        return members;
    }

    /**
     * Sets the well known members.
     *
     * If members is empty, calling this method will have the same effect as calling {@link #clear()}.
     *
     * A member can be a comma separated string, e..g '10.11.12.1,10.11.12.2' which indicates multiple members
     * are going to be added.
     *
     * @param members the members to set
     * @return the updated TcpIpConfig.
     * @throws IllegalArgumentException if members is null.
     */
    public TcpIpConfig setMembers(final List<String> members) {
        isNotNull(members,"members");

        this.members.clear();

        for (String member : members) {
          addMember(member);
        }
        return this;
    }

    /**
     * Adds a 'well known' member.
     *
     * Each HazelcastInstance will try to connect to at least one of the members to find all other members
     * and create a cluster.
     *
     * A member can be a comma separated string, e..g '10.11.12.1,10.11.12.2' which indicates multiple members
     * are going to be added.
     *
     * @param member the member to add.
     * @return the updated configuration.
     * @throws IllegalArgumentException if member is null or empty.
     * @see #getMembers()
     */
    public TcpIpConfig addMember(String member) {
        member = hasText(member,"member");

        StringTokenizer tokenizer = new StringTokenizer(member, ",");
        while (tokenizer.hasMoreTokens()) {
            String s = tokenizer.nextToken();
            this.members.add(s.trim());
        }

        return this;
    }

    /**
     * Removes all members.
     *
     * Can safely be called when there are no members.
     *
     * @return the updated configuration.
     * @see #addMember(String)
     */
    public TcpIpConfig clear() {
        members.clear();
        return this;
    }

    /**
     * Gets the required member. If no required member is configured, null is returned.
     *
     * @return the requiredMember
     * @see #setRequiredMember(String)
     */
    public String getRequiredMember() {
        return requiredMember;
    }

    /**
     * Sets the required member. If a null value is passed, it means that there is no required member.
     *
     * With a required member configured, the cluster will only start up when this required member is up.
     * Setting the required member can be tricky, since if that member doesn't come up, the cluster won't start.
     *
     * @param requiredMember the requiredMember to set
     * @return TcpIpConfig the updated configuration.
     * @see #getRequiredMember()
     */
    public TcpIpConfig setRequiredMember(final String requiredMember) {
        this.requiredMember = requiredMember;
        return this;
    }

    @Override
    public String toString() {
        return "TcpIpConfig [enabled=" + enabled
                + ", connectionTimeoutSeconds=" + connectionTimeoutSeconds
                + ", members=" + members
                + ", requiredMember=" + requiredMember
                + "]";
    }
}
