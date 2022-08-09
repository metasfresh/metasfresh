-- 2022-04-07T08:02:34.313Z
INSERT INTO C_Queue_Processor (AD_Client_ID,AD_Org_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,KeepAliveTimeMillis,Name,PoolSize,Updated,UpdatedBy) VALUES (0,0,540066,TO_TIMESTAMP('2022-04-07 11:02:34','YYYY-MM-DD HH24:MI:SS'),100,'Y',0,'C_InvoiceCandidateQueue',10,TO_TIMESTAMP('2022-04-07 11:02:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-07T08:02:42.981Z
UPDATE C_Queue_Processor SET Description='Contains the processors that work deal with invoice candidates; goal: prevent different processors from doing their thing to the same C_InvoiceCandidate concurrently',Updated=TO_TIMESTAMP('2022-04-07 11:02:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_Processor_ID=540066
;

-- 2022-04-07T08:03:01.300Z
UPDATE C_Queue_Processor SET PoolSize=1,Updated=TO_TIMESTAMP('2022-04-07 11:03:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_Processor_ID=540066
;

-- 2022-04-07T08:03:04.275Z
UPDATE C_Queue_Processor SET KeepAliveTimeMillis=1000,Updated=TO_TIMESTAMP('2022-04-07 11:03:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_Processor_ID=540066
;

-- 2022-04-07T08:03:49.014Z
INSERT INTO C_Queue_Processor_Assign (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,C_Queue_Processor_Assign_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,0,540029,540102,540066,TO_TIMESTAMP('2022-04-07 11:03:49','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2022-04-07 11:03:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-07T08:04:04.989Z
INSERT INTO C_Queue_Processor_Assign (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,C_Queue_Processor_Assign_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,0,540000,540103,540066,TO_TIMESTAMP('2022-04-07 11:04:04','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2022-04-07 11:04:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-07T08:04:17.935Z
INSERT INTO C_Queue_Processor_Assign (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,C_Queue_Processor_Assign_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,0,540069,540104,540066,TO_TIMESTAMP('2022-04-07 11:04:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2022-04-07 11:04:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-07T08:04:30.061Z
INSERT INTO C_Queue_Processor_Assign (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,C_Queue_Processor_Assign_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,0,540028,540105,540066,TO_TIMESTAMP('2022-04-07 11:04:30','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2022-04-07 11:04:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-07T08:04:38.681Z
INSERT INTO C_Queue_Processor_Assign (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,C_Queue_Processor_Assign_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,0,540062,540106,540066,TO_TIMESTAMP('2022-04-07 11:04:38','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2022-04-07 11:04:38','YYYY-MM-DD HH24:MI:SS'),100)
;



-- 2022-04-07T08:06:55.007Z
UPDATE C_Queue_Processor SET Description='Deactivated in favor of C_InvoiceCandidateQueue',Updated=TO_TIMESTAMP('2022-04-07 11:06:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_Processor_ID=540029
;

-- 2022-04-07T08:06:55.066Z
UPDATE C_Queue_Processor SET IsActive='N',Updated=TO_TIMESTAMP('2022-04-07 11:06:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_Processor_ID=540029
;

-- 2022-04-07T08:07:12.268Z
UPDATE C_Queue_Processor SET Description='Deactivated in favor of C_InvoiceCandidateQueue',Updated=TO_TIMESTAMP('2022-04-07 11:07:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_Processor_ID=540011
;

-- 2022-04-07T08:07:13.231Z
UPDATE C_Queue_Processor SET IsActive='N',Updated=TO_TIMESTAMP('2022-04-07 11:07:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_Processor_ID=540011
;

-- 2022-04-07T08:07:35.826Z
UPDATE C_Queue_Processor SET Description='Deactivated in favor of C_InvoiceCandidateQueue',Updated=TO_TIMESTAMP('2022-04-07 11:07:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_Processor_ID=540028
;

-- 2022-04-07T08:07:35.885Z
UPDATE C_Queue_Processor SET IsActive='N',Updated=TO_TIMESTAMP('2022-04-07 11:07:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_Processor_ID=540028
;

-- 2022-04-07T08:10:55.560Z
UPDATE C_Queue_Processor SET Description='Deactivated in favor of C_InvoiceCandidateQueue',Updated=TO_TIMESTAMP('2022-04-07 11:10:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_Processor_ID=540055
;

-- 2022-04-07T08:10:56.238Z
UPDATE C_Queue_Processor SET IsActive='N',Updated=TO_TIMESTAMP('2022-04-07 11:10:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_Processor_ID=540055
;

-- 2022-04-07T08:12:37.298Z
UPDATE C_Queue_Processor SET Description='Deactivated in favor of C_InvoiceCandidateQueue',Updated=TO_TIMESTAMP('2022-04-07 11:12:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_Processor_ID=540051
;

-- 2022-04-07T08:12:37.393Z
UPDATE C_Queue_Processor SET IsActive='N',Updated=TO_TIMESTAMP('2022-04-07 11:12:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_Processor_ID=540051
;

