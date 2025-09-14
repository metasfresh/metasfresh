-- Run mode: SWING_CLIENT

-- Process: Import_GLJournal(org.compiere.process.ImportGLJournal)
-- ParameterName: AD_Client_ID
-- 2025-09-12T17:00:05.723Z
UPDATE AD_Process_Para SET DefaultValue='1000000',Updated=TO_TIMESTAMP('2025-09-12 17:00:05.723000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=328
;

-- Process: Import_GLJournal(org.compiere.process.ImportGLJournal)
-- ParameterName: AD_Org_ID
-- 2025-09-12T17:00:11.739Z
UPDATE AD_Process_Para SET DefaultValue='1000000',Updated=TO_TIMESTAMP('2025-09-12 17:00:11.738000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=329
;

-- Process: Import_GLJournal(org.compiere.process.ImportGLJournal)
-- ParameterName: C_AcctSchema_ID
-- 2025-09-12T17:00:16.723Z
UPDATE AD_Process_Para SET DefaultValue='1000000',Updated=TO_TIMESTAMP('2025-09-12 17:00:16.723000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=330
;

-- Process: Import_GLJournal(org.compiere.process.ImportGLJournal)
-- Table: I_GLJournal
-- EntityType: D
-- 2025-09-12T17:00:51.036Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,220,599,541570,TO_TIMESTAMP('2025-09-12 17:00:50.895000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y',TO_TIMESTAMP('2025-09-12 17:00:50.895000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N')
;

-- 2025-09-12T17:18:37.828Z
UPDATE AD_ImpFormat SET SkipFirstNRows=1,Updated=TO_TIMESTAMP('2025-09-12 17:18:37.825000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_ImpFormat_ID=540002
;

-- 2025-09-12T17:18:56.408Z
DELETE FROM AD_ImpFormat_Row WHERE AD_ImpFormat_Row_ID=540017
;

-- 2025-09-12T17:18:59.273Z
DELETE FROM AD_ImpFormat_Row WHERE AD_ImpFormat_Row_ID=540016
;

-- 2025-09-12T17:19:27.799Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,9253,540002,542131,0,TO_TIMESTAMP('2025-09-12 17:19:27.658000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','.','N',0,'Y','DateAcct',50,5,TO_TIMESTAMP('2025-09-12 17:19:27.658000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-12T17:19:40.699Z
UPDATE AD_ImpFormat_Row SET StartNo=4,Updated=TO_TIMESTAMP('2025-09-12 17:19:40.695000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=542131
;

-- 2025-09-12T17:19:43.438Z
UPDATE AD_ImpFormat_Row SET StartNo=5,Updated=TO_TIMESTAMP('2025-09-12 17:19:43.435000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=540018
;

-- 2025-09-12T17:19:44.777Z
UPDATE AD_ImpFormat_Row SET StartNo=6,Updated=TO_TIMESTAMP('2025-09-12 17:19:44.773000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=540019
;

-- 2025-09-12T17:19:46.044Z
UPDATE AD_ImpFormat_Row SET StartNo=7,Updated=TO_TIMESTAMP('2025-09-12 17:19:46.040000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=540020
;

-- 2025-09-12T17:19:48.079Z
UPDATE AD_ImpFormat_Row SET StartNo=8,Updated=TO_TIMESTAMP('2025-09-12 17:19:48.076000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=540021
;

-- 2025-09-12T17:19:50.167Z
UPDATE AD_ImpFormat_Row SET SeqNo=40,Updated=TO_TIMESTAMP('2025-09-12 17:19:50.164000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=542131
;

-- 2025-09-12T17:19:51.528Z
UPDATE AD_ImpFormat_Row SET SeqNo=50,Updated=TO_TIMESTAMP('2025-09-12 17:19:51.525000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=540018
;

-- 2025-09-12T17:19:52.827Z
UPDATE AD_ImpFormat_Row SET SeqNo=60,Updated=TO_TIMESTAMP('2025-09-12 17:19:52.823000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=540019
;

-- 2025-09-12T17:19:54.425Z
UPDATE AD_ImpFormat_Row SET SeqNo=70,Updated=TO_TIMESTAMP('2025-09-12 17:19:54.422000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=540020
;

-- 2025-09-12T17:19:58.863Z
UPDATE AD_ImpFormat_Row SET SeqNo=80,Updated=TO_TIMESTAMP('2025-09-12 17:19:58.860000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=540021
;

-- 2025-09-12T17:21:35.823Z
UPDATE AD_ImpFormat_Row SET DataFormat='dd.MM.yyyy',Updated=TO_TIMESTAMP('2025-09-12 17:21:35.820000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=542131
;

