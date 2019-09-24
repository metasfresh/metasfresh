
-- 2019-09-24T11:21:17.430Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Workflow (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Table_ID,AD_Workflow_ID,Author,Cost,Created,CreatedBy,DocumentNo,Duration,DurationUnit,EntityType,
IsActive,IsBetaFunctionality,IsDefault,IsValid,Name,PublishStatus,Updated,UpdatedBy,Value,
Version,WaitingTime,WorkflowType,WorkingTime) VALUES ('1',0,0,392,540108,'metasfresh ERP',0,
TO_TIMESTAMP('2019-09-24 14:21:17','YYYY-MM-DD HH24:MI:SS'),100,'10000000',1,'D','D','Y','N','N','N','Process_C_BankStatement',
'R',TO_TIMESTAMP('2019-09-24 14:21:17','YYYY-MM-DD HH24:MI:SS'),100,'Process_C_BankStatement',0,0,'P',0)
;

-- 2019-09-24T11:21:17.435Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Workflow_Trl (AD_Language,AD_Workflow_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Workflow_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Workflow t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Workflow_ID=540108 AND NOT EXISTS (SELECT 1 FROM AD_Workflow_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Workflow_ID=t.AD_Workflow_ID)
;

-- 2019-09-24T11:21:17.653Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_Node (Action,AD_Client_ID,AD_Org_ID,AD_WF_Node_ID,AD_Workflow_ID,Cost,Created,CreatedBy,Description,Duration,DurationLimit,EntityType,IsActive,IsCentrallyMaintained,JoinElement,Name,SplitElement,Updated,UpdatedBy,Value,WaitingTime,XPosition,YPosition) VALUES ('Z',0,0,540236,540108,0,TO_TIMESTAMP('2019-09-24 14:21:17','YYYY-MM-DD HH24:MI:SS'),100,'(Standard Node)',0,0,'D','Y','Y','X','(Start)','X',TO_TIMESTAMP('2019-09-24 14:21:17','YYYY-MM-DD HH24:MI:SS'),100,'(Start)',0,0,0)
;

-- 2019-09-24T11:21:17.656Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_Node_Trl (AD_Language,AD_WF_Node_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_WF_Node_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_WF_Node t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_WF_Node_ID=540236 AND NOT EXISTS (SELECT 1 FROM AD_WF_Node_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_WF_Node_ID=t.AD_WF_Node_ID)
;

-- 2019-09-24T11:21:17.665Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Workflow SET AD_WF_Node_ID=540236, IsValid='Y',Updated=TO_TIMESTAMP('2019-09-24 14:21:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Workflow_ID=540108
;

-- 2019-09-24T11:21:17.782Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_Node (Action,AD_Client_ID,AD_Org_ID,AD_WF_Node_ID,AD_Workflow_ID,Cost,Created,CreatedBy,Description,DocAction,Duration,DurationLimit,EntityType,IsActive,IsCentrallyMaintained,JoinElement,Name,SplitElement,Updated,UpdatedBy,Value,WaitingTime,XPosition,YPosition) VALUES ('D',0,0,540237,540108,0,TO_TIMESTAMP('2019-09-24 14:21:17','YYYY-MM-DD HH24:MI:SS'),100,'(Standard Node)','--',0,0,'D','Y','Y','X','(DocAuto)','X',TO_TIMESTAMP('2019-09-24 14:21:17','YYYY-MM-DD HH24:MI:SS'),100,'(DocAuto)',0,0,0)
;

-- 2019-09-24T11:21:17.793Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_Node_Trl (AD_Language,AD_WF_Node_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_WF_Node_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_WF_Node t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_WF_Node_ID=540237 AND NOT EXISTS (SELECT 1 FROM AD_WF_Node_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_WF_Node_ID=t.AD_WF_Node_ID)
;

-- 2019-09-24T11:21:17.900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_Node (Action,AD_Client_ID,AD_Org_ID,AD_WF_Node_ID,AD_Workflow_ID,Cost,Created,CreatedBy,Description,DocAction,Duration,DurationLimit,EntityType,IsActive,IsCentrallyMaintained,JoinElement,Name,SplitElement,Updated,UpdatedBy,Value,WaitingTime,XPosition,YPosition) VALUES ('D',0,0,540238,540108,0,TO_TIMESTAMP('2019-09-24 14:21:17','YYYY-MM-DD HH24:MI:SS'),100,'(Standard Node)','PR',0,0,'D','Y','Y','X','(DocPrepare)','X',TO_TIMESTAMP('2019-09-24 14:21:17','YYYY-MM-DD HH24:MI:SS'),100,'(DocPrepare)',0,0,0)
;

-- 2019-09-24T11:21:17.901Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_Node_Trl (AD_Language,AD_WF_Node_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_WF_Node_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_WF_Node t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_WF_Node_ID=540238 AND NOT EXISTS (SELECT 1 FROM AD_WF_Node_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_WF_Node_ID=t.AD_WF_Node_ID)
;

-- 2019-09-24T11:21:17.997Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_Node (Action,AD_Client_ID,AD_Org_ID,AD_WF_Node_ID,AD_Workflow_ID,Cost,Created,CreatedBy,Description,DocAction,Duration,DurationLimit,EntityType,IsActive,IsCentrallyMaintained,JoinElement,Name,SplitElement,Updated,UpdatedBy,Value,WaitingTime,XPosition,YPosition) VALUES ('D',0,0,540239,540108,0,TO_TIMESTAMP('2019-09-24 14:21:17','YYYY-MM-DD HH24:MI:SS'),100,'(Standard Node)','CO',0,0,'D','Y','Y','X','(DocComplete)','X',TO_TIMESTAMP('2019-09-24 14:21:17','YYYY-MM-DD HH24:MI:SS'),100,'(DocComplete)',0,0,0)
;

-- 2019-09-24T11:21:17.998Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_Node_Trl (AD_Language,AD_WF_Node_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_WF_Node_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_WF_Node t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_WF_Node_ID=540239 AND NOT EXISTS (SELECT 1 FROM AD_WF_Node_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_WF_Node_ID=t.AD_WF_Node_ID)
;

-- 2019-09-24T11:21:18.119Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_NodeNext (AD_Client_ID,AD_Org_ID,AD_WF_Next_ID,AD_WF_Node_ID,AD_WF_NodeNext_ID,Created,CreatedBy,Description,EntityType,IsActive,IsStdUserWorkflow,SeqNo,Updated,UpdatedBy) VALUES (0,0,540238,540236,540166,TO_TIMESTAMP('2019-09-24 14:21:18','YYYY-MM-DD HH24:MI:SS'),100,'Standard Transition','D','Y','N',10,TO_TIMESTAMP('2019-09-24 14:21:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-24T11:21:18.127Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WF_NodeNext SET Description='(Standard Approval)', IsStdUserWorkflow='Y',Updated=TO_TIMESTAMP('2019-09-24 14:21:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_WF_NodeNext_ID=540166
;

-- 2019-09-24T11:21:18.223Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_NodeNext (AD_Client_ID,AD_Org_ID,AD_WF_Next_ID,AD_WF_Node_ID,AD_WF_NodeNext_ID,Created,CreatedBy,Description,EntityType,IsActive,IsStdUserWorkflow,SeqNo,Updated,UpdatedBy) VALUES (0,0,540237,540236,540167,TO_TIMESTAMP('2019-09-24 14:21:18','YYYY-MM-DD HH24:MI:SS'),100,'Standard Transition','D','Y','N',100,TO_TIMESTAMP('2019-09-24 14:21:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-24T11:21:18.323Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_NodeNext (AD_Client_ID,AD_Org_ID,AD_WF_Next_ID,AD_WF_Node_ID,AD_WF_NodeNext_ID,Created,CreatedBy,Description,EntityType,IsActive,IsStdUserWorkflow,SeqNo,Updated,UpdatedBy) VALUES (0,0,540239,540238,540168,TO_TIMESTAMP('2019-09-24 14:21:18','YYYY-MM-DD HH24:MI:SS'),100,'Standard Transition','D','Y','N',100,TO_TIMESTAMP('2019-09-24 14:21:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-24T11:21:18.429Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Workflow_ID,Created,CreatedBy,EntityType,IsActive,IsReport,IsUseBPartnerLanguage,Name,Type,Updated,UpdatedBy,Value) VALUES ('1',0,0,541197,540108,TO_TIMESTAMP('2019-09-24 14:21:18','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','Y','Process_C_BankStatement','Java',TO_TIMESTAMP('2019-09-24 14:21:18','YYYY-MM-DD HH24:MI:SS'),100,'Process_C_BankStatement')
;

-- 2019-09-24T11:21:18.431Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_ID=541197 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2019-09-24T11:21:18.481Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Process_ID=541197,Updated=TO_TIMESTAMP('2019-09-24 14:21:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=4923
;

-- 2019-09-24T11:21:18.491Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Process_ID=541197,Updated=TO_TIMESTAMP('2019-09-24 14:21:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=12461
;

