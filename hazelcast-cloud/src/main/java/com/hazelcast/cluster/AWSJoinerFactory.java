package com.hazelcast.cluster;

import com.hazelcast.config.JoinConfig;
import com.hazelcast.instance.Node;

/**
 * JoinerFactory for TcpIpJoinerOverAWS
 *
 * @author dturner@kixeye.com
 */
public class AWSJoinerFactory implements JoinerFactory{

    @Override
    public Joiner createJoiner(Node node, JoinConfig joinConfig) {
        return new TcpIpJoinerOverAWS(node);
    }

}
