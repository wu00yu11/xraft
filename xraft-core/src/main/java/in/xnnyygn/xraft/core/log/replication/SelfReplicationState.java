package in.xnnyygn.xraft.core.log.replication;

import in.xnnyygn.xraft.core.log.Log;
import in.xnnyygn.xraft.core.node.NodeId;

public class SelfReplicationState extends AbstractReplicationState {

    private final NodeId selfNodeId;
    private final Log log;

    public SelfReplicationState(NodeId selfNodeId, Log log) {
        super(true);
        this.selfNodeId = selfNodeId;
        this.log = log;
    }

    @Override
    public NodeId getNodeId() {
        return this.selfNodeId;
    }

    @Override
    public int getNextIndex() {
        return this.log.getNextLogIndex();
    }

    @Override
    public int getMatchIndex() {
        return this.log.getNextLogIndex() - 1;
    }

    @Override
    public void backOffNextIndex() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean advance(int lastEntryIndex) {
        throw new UnsupportedOperationException();
    }

}