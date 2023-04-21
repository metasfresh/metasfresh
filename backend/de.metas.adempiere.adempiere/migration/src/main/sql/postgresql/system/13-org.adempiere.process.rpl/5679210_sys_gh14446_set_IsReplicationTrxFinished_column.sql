-- set `IsReplicationTrxFinished` true for all the past imports
Update EXP_ReplicationTrx
SET IsReplicationTrxFinished = 'Y',
    updated                  = '2023-02-16 12:26:00',
    updatedby= 99
where IsReplicationTrxFinished is null;