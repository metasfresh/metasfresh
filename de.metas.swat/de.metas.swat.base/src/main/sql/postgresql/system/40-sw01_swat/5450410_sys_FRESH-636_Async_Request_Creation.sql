-- 08.09.2016 18:28
-- URL zum Konzept
INSERT INTO C_Queue_PackageProcessor (AD_Client_ID,AD_Org_ID,Classname,C_Queue_PackageProcessor_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,Updated,UpdatedBy) VALUES (0,0,'de.metas.request.service.async.spi.impl.C_Request_CreateFromInout',540047,TO_TIMESTAMP('2016-09-08 18:28:01','YYYY-MM-DD HH24:MI:SS'),100,'Recommended to run in a 1-sized thread pool. Updates C_BPartner_Stats.TotalOpenBalance and SO_CreditUsed.','de.metas.swat','C_Request_CreateFromInout','Y',TO_TIMESTAMP('2016-09-08 18:28:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 08.09.2016 18:35
-- URL zum Konzept
UPDATE C_Queue_PackageProcessor SET EntityType='D',Updated=TO_TIMESTAMP('2016-09-08 18:35:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_PackageProcessor_ID=540047
;

-- 08.09.2016 18:37
-- URL zum Konzept
INSERT INTO C_Queue_Processor (AD_Client_ID,AD_Org_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,KeepAliveTimeMillis,Name,PoolSize,Updated,UpdatedBy) VALUES (0,0,540041,TO_TIMESTAMP('2016-09-08 18:37:51','YYYY-MM-DD HH24:MI:SS'),100,'Y',1000,'R_Request_Create',1,TO_TIMESTAMP('2016-09-08 18:37:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 08.09.2016 18:38
-- URL zum Konzept
INSERT INTO C_Queue_Processor_Assign (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,C_Queue_Processor_Assign_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,0,540047,540070,540041,TO_TIMESTAMP('2016-09-08 18:38:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2016-09-08 18:38:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 08.09.2016 19:16:56
-- URL zum Konzept
UPDATE C_Queue_PackageProcessor SET EntityType='de.metas.swat',Updated=TO_TIMESTAMP('2016-09-08 19:16:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_PackageProcessor_ID=540047
;

