-- 2023-08-22T12:34:22.800556700Z
INSERT INTO C_Queue_PackageProcessor (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,Classname,Created,CreatedBy,EntityType,InternalName,IsActive,Updated,UpdatedBy) VALUES (0,0,540102,'de.metas.contracts.modular.workpackage.ModularLogsWorkPackageProcessor',TO_TIMESTAMP('2023-08-22 15:34:22.586','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','ModularLogsWorkPackageProcessor','Y',TO_TIMESTAMP('2023-08-22 15:34:22.586','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-22T12:36:27.831973700Z
INSERT INTO C_Queue_Processor (AD_Client_ID,AD_Org_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,KeepAliveTimeMillis,Name,PoolSize,Priority,Updated,UpdatedBy) VALUES (0,0,540073,TO_TIMESTAMP('2023-08-22 15:36:27.665','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',1000,'ModularLogsQueueProcessor',1,'',TO_TIMESTAMP('2023-08-22 15:36:27.665','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-22T12:36:34.392566300Z
UPDATE C_Queue_Processor SET Priority=NULL,Updated=TO_TIMESTAMP('2023-08-22 15:36:34.391','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_Queue_Processor_ID=540073
;

-- 2023-08-22T12:37:14.691434300Z
INSERT INTO C_Queue_Processor_Assign (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,C_Queue_Processor_Assign_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,0,540102,540116,540073,TO_TIMESTAMP('2023-08-22 15:37:14.529','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',TO_TIMESTAMP('2023-08-22 15:37:14.529','YYYY-MM-DD HH24:MI:SS.US'),100)
;

