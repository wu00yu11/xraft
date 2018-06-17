package in.xnnyygn.xraft.serverstate;

import in.xnnyygn.xraft.scheduler.LogReplicationTask;
import in.xnnyygn.xraft.rpc.AppendEntriesResult;
import in.xnnyygn.xraft.rpc.AppendEntriesRpc;
import in.xnnyygn.xraft.rpc.RequestVoteResult;
import in.xnnyygn.xraft.rpc.RequestVoteRpc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LeaderServerState extends AbstractServerState {

    private static final Logger logger = LoggerFactory.getLogger(LeaderServerState.class);
    private final LogReplicationTask logReplicationTask;

    public LeaderServerState(int term, LogReplicationTask logReplicationTask) {
        super(NodeRole.LEADER, term);
        this.logReplicationTask = logReplicationTask;
    }

    @Override
    public ServerStateSnapshot takeSnapshot() {
        return new ServerStateSnapshot(this.role, this.term);
    }

    @Override
    protected void cancelTimeoutOrTask() {
        this.logReplicationTask.cancel();
    }

    @Override
    public void onReceiveRequestVoteResult(NodeStateContext context, RequestVoteResult result) {
        logger.debug("Node {}, current role is LEADER, ignore", context.getSelfNodeId());
    }

    @Override
    protected RequestVoteResult processRequestVoteRpc(NodeStateContext context, RequestVoteRpc rpc) {
        logger.debug("Node {}, current role is LEADER, ignore", context.getSelfNodeId());
        return new RequestVoteResult(this.term, false);
    }

    @Override
    protected AppendEntriesResult processAppendEntriesRpc(NodeStateContext context, AppendEntriesRpc rpc) {
        logger.warn("Node {}, receive AppendEntries RPC from another leader, node {}", context.getSelfNodeId(), rpc.getLeaderId());
        return new AppendEntriesResult(this.term, false);
    }

    @Override
    public String toString() {
        return "LeaderServerState{" +
                logReplicationTask +
                ", term=" + term +
                '}';
    }

}
