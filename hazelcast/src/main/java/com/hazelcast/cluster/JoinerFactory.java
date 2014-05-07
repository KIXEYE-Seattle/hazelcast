package com.hazelcast.cluster;

import com.hazelcast.config.JoinConfig;
import com.hazelcast.instance.Node;

/**
 * Creates a Joiner
 *
 * @author dturner@kixeye.com
 */
public interface JoinerFactory {

    /**
     * Creates a Joiner
     * @param node
     * @param joinConfig
     * @return
     */
    public Joiner createJoiner(Node node, JoinConfig joinConfig);
}
