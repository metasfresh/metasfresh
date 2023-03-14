-- select migrationscript_ignore('30-de.metas.adempiere.libero/5611740_mobileUI_demo_workflow.sql');


-- 2021-11-03T14:32:32.720Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Workflow (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Workflow_ID,Author,Cost,Created,CreatedBy,DocumentNo,Duration,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsValid,Name,PublishStatus,QtyBatchSize,Updated,UpdatedBy,Value,Version,WaitingTime,WorkflowType,WorkingTime,Yield) VALUES ('3',1000000,1000000,540114,'metasfresh gmbH',0,TO_TIMESTAMP('2021-11-03 16:32:32','YYYY-MM-DD HH24:MI:SS'),100,'mobileUI_workflow_test',0,'U','Y','N','N','N','mobileUI_workflow_test','U',1,TO_TIMESTAMP('2021-11-03 16:32:32','YYYY-MM-DD HH24:MI:SS'),100,'mobileUI_workflow_test',0,0,'M',0,100)
;

-- 2021-11-03T14:32:32.728Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Workflow_Trl (AD_Language,AD_Workflow_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Workflow_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Workflow t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Workflow_ID=540114 AND NOT EXISTS (SELECT 1 FROM AD_Workflow_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Workflow_ID=t.AD_Workflow_ID)
;

-- 2021-11-03T14:32:32.969Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Workflow_Access (AD_Client_ID,AD_Org_ID,AD_Role_ID,AD_Workflow_Access_ID,AD_Workflow_ID,Created,CreatedBy,IsActive,IsReadWrite,Updated,UpdatedBy) VALUES (1000000,0,540024,540056,540114,TO_TIMESTAMP('2021-11-03 16:32:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y',TO_TIMESTAMP('2021-11-03 16:32:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-03T14:32:33.158Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Workflow_Access (AD_Client_ID,AD_Org_ID,AD_Role_ID,AD_Workflow_Access_ID,AD_Workflow_ID,Created,CreatedBy,IsActive,IsReadWrite,Updated,UpdatedBy) VALUES (0,0,0,540057,540114,TO_TIMESTAMP('2021-11-03 16:32:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y',TO_TIMESTAMP('2021-11-03 16:32:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-03T14:32:33.283Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Workflow_Access (AD_Client_ID,AD_Org_ID,AD_Role_ID,AD_Workflow_Access_ID,AD_Workflow_ID,Created,CreatedBy,IsActive,IsReadWrite,Updated,UpdatedBy) VALUES (1000000,0,1000000,540058,540114,TO_TIMESTAMP('2021-11-03 16:32:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y',TO_TIMESTAMP('2021-11-03 16:32:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-03T14:36:35.106Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_Sequence SET CurrentNext = CurrentNext + ? WHERE AD_Sequence_ID = ? RETURNING CurrentNext - ?
-- ;

-- 2021-11-03T14:36:35.108Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_Node (Action,AD_Client_ID,AD_Org_ID,AD_WF_Node_ID,AD_Workflow_ID,Cost,Created,CreatedBy,DocAction,Duration,DurationLimit,EntityType,IsActive,IsCentrallyMaintained,IsMilestone,IsSubcontracting,JoinElement,Name,PP_Activity_Type,SplitElement,UnitsCycles,Updated,UpdatedBy,Value,WaitingTime,XPosition,Yield,YPosition) VALUES ('Z',1000000,1000000,540257,540114,0,TO_TIMESTAMP('2021-11-03 16:36:35','YYYY-MM-DD HH24:MI:SS'),100,'CO',0,0,'U','Y','Y','N','N','X','clean the machine','AC','X',0,TO_TIMESTAMP('2021-11-03 16:36:35','YYYY-MM-DD HH24:MI:SS'),100,'1000004',0,0,100,0)
;

-- 2021-11-03T14:36:35.111Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_Node_Trl (AD_Language,AD_WF_Node_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_WF_Node_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_WF_Node t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_WF_Node_ID=540257 AND NOT EXISTS (SELECT 1 FROM AD_WF_Node_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_WF_Node_ID=t.AD_WF_Node_ID)
;

-- 2021-11-03T14:36:35.116Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Workflow SET AD_Table_ID=NULL, AD_WF_Node_ID=540257, IsValid='Y',Updated=TO_TIMESTAMP('2021-11-03 16:36:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Workflow_ID=540114
;

-- 2021-11-03T14:37:24.503Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Workflow SET AD_Table_ID=NULL, DurationUnit='h', IsValid='Y',Updated=TO_TIMESTAMP('2021-11-03 16:37:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Workflow_ID=540114
;

-- 2021-11-03T14:37:48.774Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WF_Node SET Action='Z', S_Resource_ID=540006,Updated=TO_TIMESTAMP('2021-11-03 16:37:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_WF_Node_ID=540257
;

-- 2021-11-03T14:37:59.014Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WF_Node SET Action='Z', S_Resource_ID=540011,Updated=TO_TIMESTAMP('2021-11-03 16:37:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_WF_Node_ID=540257
;

-- 2021-11-03T14:39:04.818Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_Sequence SET CurrentNext = CurrentNext + ? WHERE AD_Sequence_ID = ? RETURNING CurrentNext - ?
-- ;

-- 2021-11-03T14:39:04.820Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_Node (Action,AD_Client_ID,AD_Org_ID,AD_WF_Node_ID,AD_Workflow_ID,Cost,Created,CreatedBy,DocAction,Duration,DurationLimit,EntityType,IsActive,IsCentrallyMaintained,IsMilestone,IsSubcontracting,JoinElement,Name,SplitElement,UnitsCycles,Updated,UpdatedBy,Value,WaitingTime,XPosition,Yield,YPosition) VALUES ('Z',1000000,1000000,540258,540114,0,TO_TIMESTAMP('2021-11-03 16:39:04','YYYY-MM-DD HH24:MI:SS'),100,'CO',0,0,'U','Y','Y','N','N','X','issue raw materials','X',0,TO_TIMESTAMP('2021-11-03 16:39:04','YYYY-MM-DD HH24:MI:SS'),100,'1000005',0,0,100,0)
;

-- 2021-11-03T14:39:04.821Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_Node_Trl (AD_Language,AD_WF_Node_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_WF_Node_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_WF_Node t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_WF_Node_ID=540258 AND NOT EXISTS (SELECT 1 FROM AD_WF_Node_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_WF_Node_ID=t.AD_WF_Node_ID)
;

-- 2021-11-03T14:39:05.979Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WF_Node SET Action='Z', S_Resource_ID=540011,Updated=TO_TIMESTAMP('2021-11-03 16:39:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_WF_Node_ID=540258
;

-- 2021-11-03T14:39:11.288Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WF_Node SET Action='Z', PP_Activity_Type='MI',Updated=TO_TIMESTAMP('2021-11-03 16:39:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_WF_Node_ID=540258
;

-- 2021-11-03T14:39:38.552Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_Sequence SET CurrentNext = CurrentNext + ? WHERE AD_Sequence_ID = ? RETURNING CurrentNext - ?
-- ;

-- 2021-11-03T14:39:38.554Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_Node (Action,AD_Client_ID,AD_Org_ID,AD_WF_Node_ID,AD_Workflow_ID,Cost,Created,CreatedBy,DocAction,Duration,DurationLimit,EntityType,IsActive,IsCentrallyMaintained,IsMilestone,IsSubcontracting,JoinElement,Name,SplitElement,UnitsCycles,Updated,UpdatedBy,Value,WaitingTime,XPosition,Yield,YPosition) VALUES ('Z',1000000,1000000,540259,540114,0,TO_TIMESTAMP('2021-11-03 16:39:38','YYYY-MM-DD HH24:MI:SS'),100,'CO',0,0,'U','Y','Y','N','N','X','blend everything','X',0,TO_TIMESTAMP('2021-11-03 16:39:38','YYYY-MM-DD HH24:MI:SS'),100,'1000006',0,0,100,0)
;

-- 2021-11-03T14:39:38.555Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_Node_Trl (AD_Language,AD_WF_Node_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_WF_Node_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_WF_Node t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_WF_Node_ID=540259 AND NOT EXISTS (SELECT 1 FROM AD_WF_Node_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_WF_Node_ID=t.AD_WF_Node_ID)
;

-- 2021-11-03T14:39:40.033Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WF_Node SET Action='Z', PP_Activity_Type='AC',Updated=TO_TIMESTAMP('2021-11-03 16:39:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_WF_Node_ID=540259
;

-- 2021-11-03T14:39:43.897Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WF_Node SET Action='Z', S_Resource_ID=540011,Updated=TO_TIMESTAMP('2021-11-03 16:39:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_WF_Node_ID=540259
;

-- 2021-11-03T14:39:54.416Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WF_Node SET Action='Z', Value='10',Updated=TO_TIMESTAMP('2021-11-03 16:39:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_WF_Node_ID=540257
;

-- 2021-11-03T14:39:57.386Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WF_Node SET Action='Z', Value='20',Updated=TO_TIMESTAMP('2021-11-03 16:39:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_WF_Node_ID=540258
;

-- 2021-11-03T14:40:02.247Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WF_Node SET Action='Z', Value='30',Updated=TO_TIMESTAMP('2021-11-03 16:40:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_WF_Node_ID=540259
;

-- 2021-11-03T14:52:25.417Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_Sequence SET CurrentNext = CurrentNext + ? WHERE AD_Sequence_ID = ? RETURNING CurrentNext - ?
-- ;

-- 2021-11-03T14:52:25.419Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_Node (Action,AD_Client_ID,AD_Org_ID,AD_WF_Node_ID,AD_Workflow_ID,Cost,Created,CreatedBy,DocAction,Duration,DurationLimit,EntityType,IsActive,IsCentrallyMaintained,IsMilestone,IsSubcontracting,JoinElement,Name,SplitElement,UnitsCycles,Updated,UpdatedBy,Value,WaitingTime,XPosition,Yield,YPosition) VALUES ('Z',1000000,1000000,540260,540114,0,TO_TIMESTAMP('2021-11-03 16:52:25','YYYY-MM-DD HH24:MI:SS'),100,'CO',0,0,'U','Y','Y','N','N','X','receive finished goods','X',0,TO_TIMESTAMP('2021-11-03 16:52:25','YYYY-MM-DD HH24:MI:SS'),100,'1000007',0,0,100,0)
;

-- 2021-11-03T14:52:25.420Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_Node_Trl (AD_Language,AD_WF_Node_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_WF_Node_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_WF_Node t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_WF_Node_ID=540260 AND NOT EXISTS (SELECT 1 FROM AD_WF_Node_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_WF_Node_ID=t.AD_WF_Node_ID)
;

-- 2021-11-03T14:52:28.121Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WF_Node SET Action='Z', Value='40',Updated=TO_TIMESTAMP('2021-11-03 16:52:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_WF_Node_ID=540260
;

-- 2021-11-03T14:52:35.487Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WF_Node SET Action='Z', S_Resource_ID=540011,Updated=TO_TIMESTAMP('2021-11-03 16:52:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_WF_Node_ID=540260
;

-- 2021-11-03T14:52:40.332Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WF_Node SET Action='Z', PP_Activity_Type='MR',Updated=TO_TIMESTAMP('2021-11-03 16:52:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_WF_Node_ID=540260
;

-- 2021-11-03T14:53:24.221Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_Node (Action,AD_Client_ID,AD_Org_ID,AD_WF_Node_ID,AD_Workflow_ID,Cost,Created,CreatedBy,DocAction,Duration,DurationLimit,EntityType,IsActive,IsCentrallyMaintained,IsMilestone,IsSubcontracting,JoinElement,Name,SplitElement,UnitsCycles,Updated,UpdatedBy,Value,WaitingTime,XPosition,Yield,YPosition)
VALUES ('Z',1000000,1000000,540261,540114,0,TO_TIMESTAMP('2021-11-03 16:53:24','YYYY-MM-DD HH24:MI:SS'),100,'CO',0,0,'U','Y','Y','N','N','X','Complete manufacturing','X',0,TO_TIMESTAMP('2021-11-03 16:53:24','YYYY-MM-DD HH24:MI:SS'),100,'50',0,0,100,0)
;

-- 2021-11-03T14:53:24.222Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_Node_Trl (AD_Language,AD_WF_Node_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_WF_Node_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_WF_Node t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_WF_Node_ID=540261 AND NOT EXISTS (SELECT 1 FROM AD_WF_Node_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_WF_Node_ID=t.AD_WF_Node_ID)
;

-- 2021-11-03T14:53:26.408Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WF_Node SET Action='Z', PP_Activity_Type='AC',Updated=TO_TIMESTAMP('2021-11-03 16:53:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_WF_Node_ID=540261
;

-- 2021-11-03T14:53:32.660Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WF_Node SET Action='Z', S_Resource_ID=540011,Updated=TO_TIMESTAMP('2021-11-03 16:53:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_WF_Node_ID=540261
;










-- 2022-01-17T11:56:03.800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_Node (Action,AD_Client_ID,AD_Org_ID,AD_WF_Node_ID,AD_Workflow_ID,Cost,Created,CreatedBy,DocAction,Duration,DurationLimit,EntityType,IsActive,IsCentrallyMaintained,IsMilestone,IsSubcontracting,JoinElement,Name,SplitElement,UnitsCycles,Updated,UpdatedBy,Value,WaitingTime,XPosition,Yield,YPosition) VALUES ('Z',1000000,1000000,540262,540114,0,TO_TIMESTAMP('2022-01-17 13:56:03','YYYY-MM-DD HH24:MI:SS'),540116,'CO',0,0,'U','Y','Y','N','N','X','Print QR Labels','X',0,TO_TIMESTAMP('2022-01-17 13:56:03','YYYY-MM-DD HH24:MI:SS'),540116,'5',0,0,100,0)
;

-- 2022-01-17T11:56:03.811Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_Node_Trl (AD_Language,AD_WF_Node_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_WF_Node_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_WF_Node t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_WF_Node_ID=540262 AND NOT EXISTS (SELECT 1 FROM AD_WF_Node_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_WF_Node_ID=t.AD_WF_Node_ID)
;

-- 2022-01-17T11:56:06.145Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WF_Node SET Action='Z', PP_Activity_Type='GenerateHUQRCodes',Updated=TO_TIMESTAMP('2022-01-17 13:56:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=540116 WHERE AD_WF_Node_ID=540262
;

-- 2022-01-17T11:56:43.651Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WF_Node SET Action='Z', Value='05',Updated=TO_TIMESTAMP('2022-01-17 13:56:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=540116 WHERE AD_WF_Node_ID=540262
;

-- 2022-01-17T11:56:59.376Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WF_Node SET Action='Z', S_Resource_ID=540011,Updated=TO_TIMESTAMP('2022-01-17 13:56:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=540116 WHERE AD_WF_Node_ID=540262
;







