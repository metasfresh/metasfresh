-- 2022-09-20T07:05:51.798Z
INSERT INTO AD_Workflow (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Workflow_ID,Author,Cost,Created,CreatedBy,DocumentNo,Duration,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsValid,Name,PublishStatus,QtyBatchSize,Updated,UpdatedBy,Value,Version,WaitingTime,WorkflowType,WorkingTime,Yield) VALUES ('3',1000000,1000000,540118,'metasfresh gmbH',0,TO_TIMESTAMP('2022-09-20 10:05:51.78','YYYY-MM-DD HH24:MI:SS.US'),540116,'Default Workflow for mobile UI Manufacturing',0,'U','Y','N','N','N','Default Workflow for mobile UI Manufacturing','U',1,TO_TIMESTAMP('2022-09-20 10:05:51.78','YYYY-MM-DD HH24:MI:SS.US'),540116,'Default Workflow for mobile UI Manufacturing',0,0,'M',0,100)
;

-- 2022-09-20T07:05:51.813Z
INSERT INTO AD_Workflow_Trl (AD_Language,AD_Workflow_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Workflow_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Workflow t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Workflow_ID=540118 AND NOT EXISTS (SELECT 1 FROM AD_Workflow_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Workflow_ID=t.AD_Workflow_ID)
;

-- 2022-09-20T07:05:52.038Z
INSERT INTO AD_Workflow_Access (AD_Client_ID,AD_Org_ID,AD_Role_ID,AD_Workflow_Access_ID,AD_Workflow_ID,Created,CreatedBy,IsActive,IsReadWrite,Updated,UpdatedBy) VALUES (1000000,0,540024,540095,540118,TO_TIMESTAMP('2022-09-20 10:05:51.898','YYYY-MM-DD HH24:MI:SS.US'),540116,'Y','Y',TO_TIMESTAMP('2022-09-20 10:05:51.898','YYYY-MM-DD HH24:MI:SS.US'),540116)
;

-- 2022-09-20T07:05:52.154Z
INSERT INTO AD_Workflow_Access (AD_Client_ID,AD_Org_ID,AD_Role_ID,AD_Workflow_Access_ID,AD_Workflow_ID,Created,CreatedBy,IsActive,IsReadWrite,Updated,UpdatedBy) VALUES (1000000,0,1000000,540096,540118,TO_TIMESTAMP('2022-09-20 10:05:52.046','YYYY-MM-DD HH24:MI:SS.US'),540116,'Y','Y',TO_TIMESTAMP('2022-09-20 10:05:52.046','YYYY-MM-DD HH24:MI:SS.US'),540116)
;

-- 2022-09-20T07:05:52.257Z
INSERT INTO AD_Workflow_Access (AD_Client_ID,AD_Org_ID,AD_Role_ID,AD_Workflow_Access_ID,AD_Workflow_ID,Created,CreatedBy,IsActive,IsReadWrite,Updated,UpdatedBy) VALUES (0,0,0,540097,540118,TO_TIMESTAMP('2022-09-20 10:05:52.157','YYYY-MM-DD HH24:MI:SS.US'),540116,'Y','Y',TO_TIMESTAMP('2022-09-20 10:05:52.157','YYYY-MM-DD HH24:MI:SS.US'),540116)
;

-- 2022-09-20T07:07:12.378Z
INSERT INTO AD_WF_Node (Action,AD_Client_ID,AD_Org_ID,AD_WF_Node_ID,AD_Workflow_ID,Cost,Created,CreatedBy,DocAction,Duration,DurationLimit,EntityType,IsActive,IsCentrallyMaintained,IsMilestone,IsSubcontracting,JoinElement,Name,SplitElement,UnitsCycles,Updated,UpdatedBy,Value,WaitingTime,XPosition,Yield,YPosition) VALUES ('Z',1000000,1000000,540275,540118,0,TO_TIMESTAMP('2022-09-20 10:07:12.375','YYYY-MM-DD HH24:MI:SS.US'),540116,'CO',0,0,'U','Y','Y','N','N','X','QR Label drucken','X',0,TO_TIMESTAMP('2022-09-20 10:07:12.375','YYYY-MM-DD HH24:MI:SS.US'),540116,'05',0,0,100,0)
;

-- 2022-09-20T07:07:12.381Z
INSERT INTO AD_WF_Node_Trl (AD_Language,AD_WF_Node_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_WF_Node_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_WF_Node t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_WF_Node_ID=540275 AND NOT EXISTS (SELECT 1 FROM AD_WF_Node_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_WF_Node_ID=t.AD_WF_Node_ID)
;

-- 2022-09-20T07:07:12.387Z
UPDATE AD_Workflow SET AD_Table_ID=NULL, AD_WF_Node_ID=540275, IsValid='Y',Updated=TO_TIMESTAMP('2022-09-20 10:07:12.387','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=540116 WHERE AD_Workflow_ID=540118
;

-- 2022-09-20T07:07:17.531Z
UPDATE AD_WF_Node SET Action='Z', S_Resource_ID=540011,Updated=TO_TIMESTAMP('2022-09-20 10:07:17.531','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=540116 WHERE AD_WF_Node_ID=540275
;

-- 2022-09-20T07:07:28.228Z
UPDATE AD_WF_Node SET Action='Z', PP_Activity_Type='GenerateHUQRCodes',Updated=TO_TIMESTAMP('2022-09-20 10:07:28.228','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=540116 WHERE AD_WF_Node_ID=540275
;

-- 2022-09-20T07:08:08.773Z
INSERT INTO AD_WF_Node (Action,AD_Client_ID,AD_Org_ID,AD_WF_Node_ID,AD_Workflow_ID,Cost,Created,CreatedBy,DocAction,Duration,DurationLimit,EntityType,IsActive,IsCentrallyMaintained,IsMilestone,IsSubcontracting,JoinElement,Name,SplitElement,UnitsCycles,Updated,UpdatedBy,Value,WaitingTime,XPosition,Yield,YPosition) VALUES ('Z',1000000,1000000,540276,540118,0,TO_TIMESTAMP('2022-09-20 10:08:08.77','YYYY-MM-DD HH24:MI:SS.US'),540116,'CO',0,0,'U','Y','Y','N','N','X','Waage scannen','X',0,TO_TIMESTAMP('2022-09-20 10:08:08.77','YYYY-MM-DD HH24:MI:SS.US'),540116,'15',0,0,100,0)
;

-- 2022-09-20T07:08:08.776Z
INSERT INTO AD_WF_Node_Trl (AD_Language,AD_WF_Node_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_WF_Node_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_WF_Node t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_WF_Node_ID=540276 AND NOT EXISTS (SELECT 1 FROM AD_WF_Node_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_WF_Node_ID=t.AD_WF_Node_ID)
;

-- 2022-09-20T07:08:14.942Z
UPDATE AD_WF_Node SET Action='Z', S_Resource_ID=540011,Updated=TO_TIMESTAMP('2022-09-20 10:08:14.942','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=540116 WHERE AD_WF_Node_ID=540276
;

-- 2022-09-20T07:08:20.311Z
UPDATE AD_WF_Node SET Action='Z', PP_Activity_Type='ScanScaleDevice',Updated=TO_TIMESTAMP('2022-09-20 10:08:20.311','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=540116 WHERE AD_WF_Node_ID=540276
;

-- 2022-09-20T07:08:49.401Z
INSERT INTO AD_WF_Node (Action,AD_Client_ID,AD_Org_ID,AD_WF_Node_ID,AD_Workflow_ID,Cost,Created,CreatedBy,DocAction,Duration,DurationLimit,EntityType,IsActive,IsCentrallyMaintained,IsMilestone,IsSubcontracting,JoinElement,Name,SplitElement,UnitsCycles,Updated,UpdatedBy,Value,WaitingTime,XPosition,Yield,YPosition) VALUES ('Z',1000000,1000000,540277,540118,0,TO_TIMESTAMP('2022-09-20 10:08:49.398','YYYY-MM-DD HH24:MI:SS.US'),540116,'CO',0,0,'U','Y','Y','N','N','X','Rohware einfüllen','X',0,TO_TIMESTAMP('2022-09-20 10:08:49.398','YYYY-MM-DD HH24:MI:SS.US'),540116,'20',0,0,100,0)
;

-- 2022-09-20T07:08:49.401Z
INSERT INTO AD_WF_Node_Trl (AD_Language,AD_WF_Node_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_WF_Node_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_WF_Node t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_WF_Node_ID=540277 AND NOT EXISTS (SELECT 1 FROM AD_WF_Node_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_WF_Node_ID=t.AD_WF_Node_ID)
;

-- 2022-09-20T07:09:19.864Z
UPDATE AD_WF_Node SET Action='Z', PP_Activity_Type='MI',Updated=TO_TIMESTAMP('2022-09-20 10:09:19.863','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=540116 WHERE AD_WF_Node_ID=540277
;

-- 2022-09-20T07:09:31.536Z
UPDATE AD_WF_Node SET Action='Z', S_Resource_ID=540011,Updated=TO_TIMESTAMP('2022-09-20 10:09:31.536','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=540116 WHERE AD_WF_Node_ID=540277
;

-- 2022-09-20T07:10:09.365Z
INSERT INTO AD_WF_Node (Action,AD_Client_ID,AD_Org_ID,AD_WF_Node_ID,AD_Workflow_ID,Cost,Created,CreatedBy,DocAction,Duration,DurationLimit,EntityType,IsActive,IsCentrallyMaintained,IsMilestone,IsSubcontracting,JoinElement,Name,SplitElement,UnitsCycles,Updated,UpdatedBy,Value,WaitingTime,XPosition,Yield,YPosition) VALUES ('Z',1000000,1000000,540278,540118,0,TO_TIMESTAMP('2022-09-20 10:10:09.362','YYYY-MM-DD HH24:MI:SS.US'),540116,'CO',0,0,'U','Y','Y','N','N','X','Fertigprodukt entnehmen','X',0,TO_TIMESTAMP('2022-09-20 10:10:09.362','YYYY-MM-DD HH24:MI:SS.US'),540116,'40',0,0,100,0)
;

-- 2022-09-20T07:10:09.367Z
INSERT INTO AD_WF_Node_Trl (AD_Language,AD_WF_Node_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_WF_Node_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_WF_Node t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_WF_Node_ID=540278 AND NOT EXISTS (SELECT 1 FROM AD_WF_Node_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_WF_Node_ID=t.AD_WF_Node_ID)
;

-- 2022-09-20T07:10:17.596Z
UPDATE AD_WF_Node SET Action='Z', PP_Activity_Type='MR',Updated=TO_TIMESTAMP('2022-09-20 10:10:17.596','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=540116 WHERE AD_WF_Node_ID=540278
;

-- 2022-09-20T07:10:23.127Z
UPDATE AD_WF_Node SET Action='Z', S_Resource_ID=540011,Updated=TO_TIMESTAMP('2022-09-20 10:10:23.127','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=540116 WHERE AD_WF_Node_ID=540278
;

-- 2022-09-20T07:10:52.714Z
INSERT INTO AD_WF_Node (Action,AD_Client_ID,AD_Org_ID,AD_WF_Node_ID,AD_Workflow_ID,Cost,Created,CreatedBy,DocAction,Duration,DurationLimit,EntityType,IsActive,IsCentrallyMaintained,IsMilestone,IsSubcontracting,JoinElement,Name,SplitElement,UnitsCycles,Updated,UpdatedBy,Value,WaitingTime,XPosition,Yield,YPosition) VALUES ('Z',1000000,1000000,540279,540118,0,TO_TIMESTAMP('2022-09-20 10:10:52.712','YYYY-MM-DD HH24:MI:SS.US'),540116,'CO',0,0,'U','Y','Y','N','N','X','Produktion abschliessen','X',0,TO_TIMESTAMP('2022-09-20 10:10:52.712','YYYY-MM-DD HH24:MI:SS.US'),540116,'50',0,0,100,0)
;

-- 2022-09-20T07:10:52.715Z
INSERT INTO AD_WF_Node_Trl (AD_Language,AD_WF_Node_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_WF_Node_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_WF_Node t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_WF_Node_ID=540279 AND NOT EXISTS (SELECT 1 FROM AD_WF_Node_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_WF_Node_ID=t.AD_WF_Node_ID)
;

-- 2022-09-20T07:10:55.780Z
UPDATE AD_WF_Node SET Action='Z', S_Resource_ID=540011,Updated=TO_TIMESTAMP('2022-09-20 10:10:55.78','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=540116 WHERE AD_WF_Node_ID=540279
;

-- 2022-09-20T07:11:01.212Z
UPDATE AD_WF_Node SET Action='Z', PP_Activity_Type='AC',Updated=TO_TIMESTAMP('2022-09-20 10:11:01.212','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=540116 WHERE AD_WF_Node_ID=540279
;

-- 2022-09-20T07:12:08.504Z
UPDATE AD_WF_Node SET Action='Z', Value='10',Updated=TO_TIMESTAMP('2022-09-20 10:12:08.504','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=540116 WHERE AD_WF_Node_ID=540275
;

-- 2022-09-20T07:12:13.149Z
UPDATE AD_WF_Node SET Action='Z', Value='30',Updated=TO_TIMESTAMP('2022-09-20 10:12:13.149','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=540116 WHERE AD_WF_Node_ID=540277
;

-- 2022-09-20T07:12:16.926Z
UPDATE AD_WF_Node SET Action='Z', Value='20',Updated=TO_TIMESTAMP('2022-09-20 10:12:16.925','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=540116 WHERE AD_WF_Node_ID=540276
;

-- 2022-09-20T07:18:10.173Z
UPDATE AD_Workflow SET AD_Table_ID=NULL, DurationUnit='h', IsValid='Y',Updated=TO_TIMESTAMP('2022-09-20 10:18:10.173','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=540116 WHERE AD_Workflow_ID=540118
;

