--
-- Script: /tmp/webui_migration_scripts_2023-02-28_8563990830646092410/5679720_migration_2023-02-28_postgresql.sql
-- User: metasfresh
-- OS user: root
--


-- 2023-02-28T16:15:02.091Z
-- URL zum Konzept
INSERT INTO C_Queue_Processor (AD_Client_ID,AD_Org_ID,C_Queue_Processor_ID,Created,CreatedBy,Description,IsActive,KeepAliveTimeMillis,Name,PoolSize,Updated,UpdatedBy) VALUES (0,0,540072,TO_TIMESTAMP('2023-02-28 17:15:02','YYYY-MM-DD HH24:MI:SS'),100,'Contains the processors that work deal with shipment candidates; goal: prevent different processors from doing their thing to the same M_ShipmentSchedule concurrently','Y',0,'C_Invoice_CandidateQueue',10,TO_TIMESTAMP('2023-02-28 17:15:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-28T16:15:13.071Z
-- URL zum Konzept
UPDATE C_Queue_Processor SET Description='Contains the processors that work deal with invoice candidates; goal: prevent different processors from doing their thing to the same C_Invoice_Candidate concurrently',Updated=TO_TIMESTAMP('2023-02-28 17:15:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_Processor_ID=540072
;

-- 2023-02-28T16:15:20.529Z
-- URL zum Konzept
UPDATE C_Queue_Processor SET PoolSize=1,Updated=TO_TIMESTAMP('2023-02-28 17:15:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_Processor_ID=540072
;

-- 2023-02-28T16:16:22.145Z
-- URL zum Konzept
UPDATE C_Queue_Processor SET Description='Deactivated in favor of C_Invoice_CandidateQueue',Updated=TO_TIMESTAMP('2023-02-28 17:16:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_Processor_ID=540028
;

-- 2023-02-28T16:16:28.071Z
-- URL zum Konzept
UPDATE C_Queue_Processor SET IsActive='N',Updated=TO_TIMESTAMP('2023-02-28 17:16:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_Processor_ID=540028
;

-- 2023-02-28T16:16:46.683Z
-- URL zum Konzept
INSERT INTO C_Queue_Processor_Assign (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,C_Queue_Processor_Assign_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,0,540028,540113,540072,TO_TIMESTAMP('2023-02-28 17:16:46','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2023-02-28 17:16:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-28T16:17:05.550Z
-- URL zum Konzept
UPDATE C_Queue_Processor SET Description='Deactivated in favor of C_Invoice_CandidateQueue',Updated=TO_TIMESTAMP('2023-02-28 17:17:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_Processor_ID=540029
;

-- 2023-02-28T16:17:05.739Z
-- URL zum Konzept
UPDATE C_Queue_Processor SET IsActive='N',Updated=TO_TIMESTAMP('2023-02-28 17:17:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_Processor_ID=540029
;

-- 2023-02-28T16:17:14.821Z
-- URL zum Konzept
INSERT INTO C_Queue_Processor_Assign (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,C_Queue_Processor_Assign_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,0,540029,540114,540072,TO_TIMESTAMP('2023-02-28 17:17:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2023-02-28 17:17:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-28T16:17:36.739Z
-- URL zum Konzept
UPDATE C_Queue_Processor SET Description='Deactivated in favor of C_Invoice_CandidateQueue',Updated=TO_TIMESTAMP('2023-02-28 17:17:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_Processor_ID=540011
;

-- 2023-02-28T16:17:36.836Z
-- URL zum Konzept
UPDATE C_Queue_Processor SET IsActive='N',Updated=TO_TIMESTAMP('2023-02-28 17:17:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_Processor_ID=540011
;

-- 2023-02-28T16:18:03.605Z
-- URL zum Konzept
INSERT INTO C_Queue_Processor_Assign (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,C_Queue_Processor_Assign_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,0,540000,540115,540072,TO_TIMESTAMP('2023-02-28 17:18:03','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2023-02-28 17:18:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-28T16:21:20.504Z
-- URL zum Konzept
UPDATE C_Queue_Processor SET IsActive='Y',Updated=TO_TIMESTAMP('2023-02-28 17:21:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_Processor_ID=540029
;

-- 2023-02-28T16:21:24.516Z
-- URL zum Konzept
UPDATE C_Queue_Processor SET Description=NULL,Updated=TO_TIMESTAMP('2023-02-28 17:21:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_Processor_ID=540029
;

-- 2023-02-28T16:21:33.590Z
-- URL zum Konzept
DELETE FROM C_Queue_Processor_Assign WHERE C_Queue_Processor_Assign_ID=540114
;

-- 2023-02-28T16:21:42.524Z
-- URL zum Konzept
UPDATE C_Queue_Processor SET KeepAliveTimeMillis=1000,Updated=TO_TIMESTAMP('2023-02-28 17:21:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_Processor_ID=540072
;

-- 2023-02-28T16:21:57.146Z
-- URL zum Konzept
UPDATE C_Queue_Processor SET Description='Contains the processors that work deal with invoice candidates; goal: prevent different processors from doing their thing to the same C_Invoice_Candidate concurrently.',Updated=TO_TIMESTAMP('2023-02-28 17:21:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_Processor_ID=540072
;
