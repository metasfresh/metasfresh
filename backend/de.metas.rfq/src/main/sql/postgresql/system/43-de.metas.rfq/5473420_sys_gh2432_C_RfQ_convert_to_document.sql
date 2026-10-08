-- 2017-10-02T16:26:14.837
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557322,287,0,28,135,677,'DocAction',TO_TIMESTAMP('2017-10-02 16:26:13','YYYY-MM-DD HH24:MI:SS'),100,'CO','Der zukünftige Status des Belegs','de.metas.rfq',2,'You find the current status in the Document Status field. The options are listed in a popup','Y','N','N','N','N','Y','N','N','N','N','Y','Belegverarbeitung',0,TO_TIMESTAMP('2017-10-02 16:26:13','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-10-02T16:26:14.847
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557322 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-10-02T16:26:15.076
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_Sequence SET CurrentNext = CurrentNext + ? WHERE AD_Sequence_ID = ? RETURNING CurrentNext - ?
-- ;

-- 2017-10-02T16:26:15.081
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Workflow (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Table_ID,AD_Workflow_ID,Author,Cost,Created,CreatedBy,DocumentNo,Duration,DurationUnit,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsValid,Name,PublishStatus,Updated,UpdatedBy,Value,Version,WaitingTime,WorkflowType,WorkingTime) VALUES ('1',0,0,677,540090,'metasfresh ERP',0,TO_TIMESTAMP('2017-10-02 16:26:14','YYYY-MM-DD HH24:MI:SS'),100,'10000000',1,'D','D','Y','N','N','N','Process_C_RfQ','R',TO_TIMESTAMP('2017-10-02 16:26:14','YYYY-MM-DD HH24:MI:SS'),100,'Process_C_RfQ',0,0,'P',0)
;

-- 2017-10-02T16:26:15.084
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Workflow_Trl (AD_Language,AD_Workflow_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Workflow_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Workflow t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Workflow_ID=540090 AND NOT EXISTS (SELECT 1 FROM AD_Workflow_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Workflow_ID=t.AD_Workflow_ID)
;

-- 2017-10-02T16:26:15.224
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_Node (Action,AD_Client_ID,AD_Org_ID,AD_WF_Node_ID,AD_Workflow_ID,Cost,Created,CreatedBy,Description,Duration,DurationLimit,EntityType,IsActive,IsCentrallyMaintained,JoinElement,Name,SplitElement,Updated,UpdatedBy,Value,WaitingTime,XPosition,YPosition) VALUES ('Z',0,0,540171,540090,0,TO_TIMESTAMP('2017-10-02 16:26:15','YYYY-MM-DD HH24:MI:SS'),100,'(Standard Node)',0,0,'D','Y','Y','X','(Start)','X',TO_TIMESTAMP('2017-10-02 16:26:15','YYYY-MM-DD HH24:MI:SS'),100,'(Start)',0,0,0)
;

-- 2017-10-02T16:26:15.227
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_Node_Trl (AD_Language,AD_WF_Node_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_WF_Node_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_WF_Node t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_WF_Node_ID=540171 AND NOT EXISTS (SELECT 1 FROM AD_WF_Node_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_WF_Node_ID=t.AD_WF_Node_ID)
;

-- 2017-10-02T16:26:15.231
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Workflow SET AD_WF_Node_ID=540171, IsValid='Y',Updated=TO_TIMESTAMP('2017-10-02 16:26:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Workflow_ID=540090
;

-- 2017-10-02T16:26:15.322
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_Node (Action,AD_Client_ID,AD_Org_ID,AD_WF_Node_ID,AD_Workflow_ID,Cost,Created,CreatedBy,Description,DocAction,Duration,DurationLimit,EntityType,IsActive,IsCentrallyMaintained,JoinElement,Name,SplitElement,Updated,UpdatedBy,Value,WaitingTime,XPosition,YPosition) VALUES ('D',0,0,540172,540090,0,TO_TIMESTAMP('2017-10-02 16:26:15','YYYY-MM-DD HH24:MI:SS'),100,'(Standard Node)','--',0,0,'D','Y','Y','X','(DocAuto)','X',TO_TIMESTAMP('2017-10-02 16:26:15','YYYY-MM-DD HH24:MI:SS'),100,'(DocAuto)',0,0,0)
;

-- 2017-10-02T16:26:15.323
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_Node_Trl (AD_Language,AD_WF_Node_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_WF_Node_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_WF_Node t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_WF_Node_ID=540172 AND NOT EXISTS (SELECT 1 FROM AD_WF_Node_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_WF_Node_ID=t.AD_WF_Node_ID)
;

-- 2017-10-02T16:26:15.391
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_Node (Action,AD_Client_ID,AD_Org_ID,AD_WF_Node_ID,AD_Workflow_ID,Cost,Created,CreatedBy,Description,DocAction,Duration,DurationLimit,EntityType,IsActive,IsCentrallyMaintained,JoinElement,Name,SplitElement,Updated,UpdatedBy,Value,WaitingTime,XPosition,YPosition) VALUES ('D',0,0,540173,540090,0,TO_TIMESTAMP('2017-10-02 16:26:15','YYYY-MM-DD HH24:MI:SS'),100,'(Standard Node)','PR',0,0,'D','Y','Y','X','(DocPrepare)','X',TO_TIMESTAMP('2017-10-02 16:26:15','YYYY-MM-DD HH24:MI:SS'),100,'(DocPrepare)',0,0,0)
;

-- 2017-10-02T16:26:15.392
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_Node_Trl (AD_Language,AD_WF_Node_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_WF_Node_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_WF_Node t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_WF_Node_ID=540173 AND NOT EXISTS (SELECT 1 FROM AD_WF_Node_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_WF_Node_ID=t.AD_WF_Node_ID)
;

-- 2017-10-02T16:26:15.459
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_Node (Action,AD_Client_ID,AD_Org_ID,AD_WF_Node_ID,AD_Workflow_ID,Cost,Created,CreatedBy,Description,DocAction,Duration,DurationLimit,EntityType,IsActive,IsCentrallyMaintained,JoinElement,Name,SplitElement,Updated,UpdatedBy,Value,WaitingTime,XPosition,YPosition) VALUES ('D',0,0,540174,540090,0,TO_TIMESTAMP('2017-10-02 16:26:15','YYYY-MM-DD HH24:MI:SS'),100,'(Standard Node)','CO',0,0,'D','Y','Y','X','(DocComplete)','X',TO_TIMESTAMP('2017-10-02 16:26:15','YYYY-MM-DD HH24:MI:SS'),100,'(DocComplete)',0,0,0)
;

-- 2017-10-02T16:26:15.460
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_Node_Trl (AD_Language,AD_WF_Node_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_WF_Node_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_WF_Node t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_WF_Node_ID=540174 AND NOT EXISTS (SELECT 1 FROM AD_WF_Node_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_WF_Node_ID=t.AD_WF_Node_ID)
;

-- 2017-10-02T16:26:15.544
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_NodeNext (AD_Client_ID,AD_Org_ID,AD_WF_Next_ID,AD_WF_Node_ID,AD_WF_NodeNext_ID,Created,CreatedBy,Description,EntityType,IsActive,IsStdUserWorkflow,SeqNo,Updated,UpdatedBy) VALUES (0,0,540173,540171,540118,TO_TIMESTAMP('2017-10-02 16:26:15','YYYY-MM-DD HH24:MI:SS'),100,'Standard Transition','D','Y','N',10,TO_TIMESTAMP('2017-10-02 16:26:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-02T16:26:15.547
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WF_NodeNext SET Description='(Standard Approval)', IsStdUserWorkflow='Y',Updated=TO_TIMESTAMP('2017-10-02 16:26:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_WF_NodeNext_ID=540118
;

-- 2017-10-02T16:26:15.612
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_NodeNext (AD_Client_ID,AD_Org_ID,AD_WF_Next_ID,AD_WF_Node_ID,AD_WF_NodeNext_ID,Created,CreatedBy,Description,EntityType,IsActive,IsStdUserWorkflow,SeqNo,Updated,UpdatedBy) VALUES (0,0,540172,540171,540119,TO_TIMESTAMP('2017-10-02 16:26:15','YYYY-MM-DD HH24:MI:SS'),100,'Standard Transition','D','Y','N',100,TO_TIMESTAMP('2017-10-02 16:26:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-02T16:26:15.680
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_NodeNext (AD_Client_ID,AD_Org_ID,AD_WF_Next_ID,AD_WF_Node_ID,AD_WF_NodeNext_ID,Created,CreatedBy,Description,EntityType,IsActive,IsStdUserWorkflow,SeqNo,Updated,UpdatedBy) VALUES (0,0,540174,540173,540120,TO_TIMESTAMP('2017-10-02 16:26:15','YYYY-MM-DD HH24:MI:SS'),100,'Standard Transition','D','Y','N',100,TO_TIMESTAMP('2017-10-02 16:26:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-02T16:26:15.755
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Workflow_ID,Created,CreatedBy,EntityType,IsActive,IsUseBPartnerLanguage,Name,Type,Updated,UpdatedBy,Value) VALUES ('1',0,0,540856,540090,TO_TIMESTAMP('2017-10-02 16:26:14','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Y','Process_C_RfQ','Java',TO_TIMESTAMP('2017-10-02 16:26:14','YYYY-MM-DD HH24:MI:SS'),100,'Process_C_RfQ')
;

-- 2017-10-02T16:26:15.757
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540856 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2017-10-02T16:26:15.790
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Process_ID=540856,Updated=TO_TIMESTAMP('2017-10-02 16:26:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=11086
;

-- 2017-10-02T16:26:15.795
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Process_ID=540856,Updated=TO_TIMESTAMP('2017-10-02 16:26:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557322
;

-- 2017-10-02T16:32:31.026
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557322,560385,0,613,TO_TIMESTAMP('2017-10-02 16:32:30','YYYY-MM-DD HH24:MI:SS'),100,'Der zukünftige Status des Belegs',2,'de.metas.rfq','You find the current status in the Document Status field. The options are listed in a popup','Y','Y','Y','N','N','N','N','N','Belegverarbeitung',TO_TIMESTAMP('2017-10-02 16:32:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-02T16:32:31.028
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560385 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-10-02T16:33:08.501
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y', SeqNo=185, SeqNoGrid=185,Updated=TO_TIMESTAMP('2017-10-02 16:33:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560385
;

-- 2017-10-03T12:08:22.847
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET IsReport='N',Updated=TO_TIMESTAMP('2017-10-03 12:08:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540856
;

