-- Column: C_OLCand.IsReplicationTrxFinished
-- Column SQL (old): (select EXP_ReplicationTrx.IsReplicationTrxFinished         from EXP_ReplicationTrx         where EXP_ReplicationTrx.EXP_ReplicationTrx_ID = C_OLCand.EXP_ReplicationTrx_ID)
-- 2023-05-24T14:35:05.082Z
UPDATE AD_Column SET ColumnSQL='(select coalesce(EXP_ReplicationTrx.IsReplicationTrxFinished,''N'')         from EXP_ReplicationTrx         where EXP_ReplicationTrx.EXP_ReplicationTrx_ID = C_OLCand.EXP_ReplicationTrx_ID)',Updated=TO_TIMESTAMP('2023-05-24 17:35:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585608
;

-- Column: C_OLCand.IsReplicationTrxError
-- Column SQL (old): (select EXP_ReplicationTrx.iserror         from EXP_ReplicationTrx         where EXP_ReplicationTrx.EXP_ReplicationTrx_ID = C_OLCand.EXP_ReplicationTrx_ID)
-- 2023-05-24T14:35:20.418Z
UPDATE AD_Column SET ColumnSQL='(select coalesce(EXP_ReplicationTrx.iserror,''N'')         from EXP_ReplicationTrx         where EXP_ReplicationTrx.EXP_ReplicationTrx_ID = C_OLCand.EXP_ReplicationTrx_ID)',Updated=TO_TIMESTAMP('2023-05-24 17:35:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585612
;

