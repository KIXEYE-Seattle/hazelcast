package com.hazelcast.cluster;

import com.hazelcast.config.JoinConfig;
import com.hazelcast.instance.Node;

/**
 * Creates a MulticastJoiner
 *
 * @author dturner@kixeye.com
 */
public class MulticastJoinerFactory implements JoinerFactory{

    @Override
    public Joiner createJoiner(Node node, JoinConfig joinConfig) {
        return new MulticastJoiner(node);
    }

}
