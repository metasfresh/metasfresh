-- 2024-08-05T07:45:46.344Z
INSERT INTO C_Queue_PackageProcessor (AD_Client_ID,AD_Org_ID,Classname,C_Queue_PackageProcessor_ID,Created,CreatedBy,EntityType,InternalName,IsActive,Updated,UpdatedBy) VALUES (0,0,'de.metas.distribution.ddordercandidate.async.GenerateDDOrderFromDDOrderCandidate',540109,TO_TIMESTAMP('2024-08-05 10:45:45','YYYY-MM-DD HH24:MI:SS'),100,'EE01','GenerateDDOrderFromDDOrderCandidate','Y',TO_TIMESTAMP('2024-08-05 10:45:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-08-05T07:46:50.390Z
INSERT INTO C_Queue_Processor (AD_Client_ID,AD_Org_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,KeepAliveTimeMillis,Name,PoolSize,Updated,UpdatedBy) VALUES (0,0,540079,TO_TIMESTAMP('2024-08-05 10:46:50','YYYY-MM-DD HH24:MI:SS'),100,'Y',1000,'GenerateDDOrderFromDDOrderCandidate',1,TO_TIMESTAMP('2024-08-05 10:46:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-08-05T07:47:06.580Z
INSERT INTO C_Queue_Processor_Assign (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,C_Queue_Processor_Assign_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,0,540105,540122,540079,TO_TIMESTAMP('2024-08-05 10:47:06','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2024-08-05 10:47:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-08-05T08:01:51.715Z
DELETE FROM C_Queue_Processor_Assign WHERE C_Queue_Processor_Assign_ID=540122
;

-- 2024-08-05T08:02:00.314Z
INSERT INTO C_Queue_Processor_Assign (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,C_Queue_Processor_Assign_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,0,540109,540123,540079,TO_TIMESTAMP('2024-08-05 11:02:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2024-08-05 11:02:00','YYYY-MM-DD HH24:MI:SS'),100)
;

