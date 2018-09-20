-- 2018-09-19T15:16:57.523
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,DefaultValue,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Reference_Value_ID,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,Name,AD_Element_ID,EntityType) VALUES (28,'CO',2,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2018-09-19 15:16:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','N',TO_TIMESTAMP('2018-09-19 15:16:57','YYYY-MM-DD HH24:MI:SS'),100,540401,'You find the current status in the Document Status field. The options are listed in a popup',135,'DocAction',561010,'Y','Der zukünftige Status des Belegs',0,'Belegverarbeitung',287,'de.metas.dunning')
;

-- 2018-09-19T15:16:57.529
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=561010 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-09-19T15:16:57.622
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,DefaultValue,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Reference_Value_ID,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,Name,AD_Element_ID,EntityType) VALUES (17,'DR',2,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2018-09-19 15:16:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','N',TO_TIMESTAMP('2018-09-19 15:16:57','YYYY-MM-DD HH24:MI:SS'),100,540401,'The Document Status indicates the status of a document at this time.  If you want to change the document status, use the Document Action field',131,'DocStatus',561011,'Y','The current status of the document',0,'Belegstatus',289,'de.metas.dunning')
;

-- 2018-09-19T15:16:57.625
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=561011 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;


-- 2018-09-19T15:16:57.982
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Workflow (AD_Client_ID,Created,CreatedBy,IsActive,Author,AccessLevel,PublishStatus,DurationUnit,WorkingTime,Duration,WaitingTime,IsValid,WorkflowType,IsBetaFunctionality,Cost,IsDefault,Updated,UpdatedBy,Version,AD_Table_ID,AD_Workflow_ID,Value,DocumentNo,AD_Org_ID,Name,EntityType) VALUES (0,TO_TIMESTAMP('2018-09-19 15:16:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','metasfresh ERP','1','R','D',0,1,0,'N','P','N',0,'N',TO_TIMESTAMP('2018-09-19 15:16:57','YYYY-MM-DD HH24:MI:SS'),100,0,540401,540102,'Process_C_DunningDoc','10000000',0,'Process_C_DunningDoc','D')
;

-- 2018-09-19T15:16:57.985
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Workflow_Trl (AD_Language,AD_Workflow_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Workflow_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Workflow t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Workflow_ID=540102 AND NOT EXISTS (SELECT 1 FROM AD_Workflow_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Workflow_ID=t.AD_Workflow_ID)
;

-- 2018-09-19T15:16:58.215
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_Node (AD_Workflow_ID,IsActive,Created,CreatedBy,Updated,IsCentrallyMaintained,YPosition,XPosition,Duration,Cost,WaitingTime,JoinElement,SplitElement,AD_Client_ID,Action,UpdatedBy,AD_WF_Node_ID,Value,DurationLimit,Description,AD_Org_ID,Name,EntityType) VALUES (540102,'Y',TO_TIMESTAMP('2018-09-19 15:16:58','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2018-09-19 15:16:58','YYYY-MM-DD HH24:MI:SS'),'Y',0,0,0,0,0,'X','X',0,'Z',100,540219,'(Start)',0,'(Standard Node)',0,'(Start)','D')
;

-- 2018-09-19T15:16:58.219
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_Node_Trl (AD_Language,AD_WF_Node_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_WF_Node_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_WF_Node t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_WF_Node_ID=540219 AND NOT EXISTS (SELECT 1 FROM AD_WF_Node_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_WF_Node_ID=t.AD_WF_Node_ID)
;

-- 2018-09-19T15:16:58.225
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Workflow SET AD_WF_Node_ID=540219, IsValid='Y',Updated=TO_TIMESTAMP('2018-09-19 15:16:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Workflow_ID=540102
;

-- 2018-09-19T15:16:58.397
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_Node (AD_Workflow_ID,IsActive,Created,CreatedBy,Updated,IsCentrallyMaintained,YPosition,XPosition,Duration,Cost,WaitingTime,JoinElement,SplitElement,DocAction,AD_Client_ID,Action,UpdatedBy,AD_WF_Node_ID,Value,DurationLimit,Description,AD_Org_ID,Name,EntityType) VALUES (540102,'Y',TO_TIMESTAMP('2018-09-19 15:16:58','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2018-09-19 15:16:58','YYYY-MM-DD HH24:MI:SS'),'Y',0,0,0,0,0,'X','X','--',0,'D',100,540220,'(DocAuto)',0,'(Standard Node)',0,'(DocAuto)','D')
;

-- 2018-09-19T15:16:58.399
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_Node_Trl (AD_Language,AD_WF_Node_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_WF_Node_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_WF_Node t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_WF_Node_ID=540220 AND NOT EXISTS (SELECT 1 FROM AD_WF_Node_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_WF_Node_ID=t.AD_WF_Node_ID)
;

-- 2018-09-19T15:16:58.479
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_Node (AD_Workflow_ID,IsActive,Created,CreatedBy,Updated,IsCentrallyMaintained,YPosition,XPosition,Duration,Cost,WaitingTime,JoinElement,SplitElement,DocAction,AD_Client_ID,Action,UpdatedBy,AD_WF_Node_ID,Value,DurationLimit,Description,AD_Org_ID,Name,EntityType) VALUES (540102,'Y',TO_TIMESTAMP('2018-09-19 15:16:58','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2018-09-19 15:16:58','YYYY-MM-DD HH24:MI:SS'),'Y',0,0,0,0,0,'X','X','PR',0,'D',100,540221,'(DocPrepare)',0,'(Standard Node)',0,'(DocPrepare)','D')
;

-- 2018-09-19T15:16:58.480
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_Node_Trl (AD_Language,AD_WF_Node_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_WF_Node_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_WF_Node t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_WF_Node_ID=540221 AND NOT EXISTS (SELECT 1 FROM AD_WF_Node_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_WF_Node_ID=t.AD_WF_Node_ID)
;

-- 2018-09-19T15:16:58.555
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_Node (AD_Workflow_ID,IsActive,Created,CreatedBy,Updated,IsCentrallyMaintained,YPosition,XPosition,Duration,Cost,WaitingTime,JoinElement,SplitElement,DocAction,AD_Client_ID,Action,UpdatedBy,AD_WF_Node_ID,Value,DurationLimit,Description,AD_Org_ID,Name,EntityType) VALUES (540102,'Y',TO_TIMESTAMP('2018-09-19 15:16:58','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2018-09-19 15:16:58','YYYY-MM-DD HH24:MI:SS'),'Y',0,0,0,0,0,'X','X','CO',0,'D',100,540222,'(DocComplete)',0,'(Standard Node)',0,'(DocComplete)','D')
;

-- 2018-09-19T15:16:58.556
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_Node_Trl (AD_Language,AD_WF_Node_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_WF_Node_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_WF_Node t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_WF_Node_ID=540222 AND NOT EXISTS (SELECT 1 FROM AD_WF_Node_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_WF_Node_ID=t.AD_WF_Node_ID)
;

-- 2018-09-19T15:16:58.660
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_NodeNext (AD_WF_Node_ID,Created,IsActive,CreatedBy,AD_WF_Next_ID,SeqNo,IsStdUserWorkflow,Description,AD_Client_ID,Updated,UpdatedBy,AD_WF_NodeNext_ID,AD_Org_ID,EntityType) VALUES (540219,TO_TIMESTAMP('2018-09-19 15:16:58','YYYY-MM-DD HH24:MI:SS'),'Y',100,540221,10,'N','Standard Transition',0,TO_TIMESTAMP('2018-09-19 15:16:58','YYYY-MM-DD HH24:MI:SS'),100,540154,0,'D')
;

-- 2018-09-19T15:16:58.664
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WF_NodeNext SET IsStdUserWorkflow='Y', Description='(Standard Approval)',Updated=TO_TIMESTAMP('2018-09-19 15:16:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_WF_NodeNext_ID=540154
;

-- 2018-09-19T15:16:58.740
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_NodeNext (AD_WF_Node_ID,Created,IsActive,CreatedBy,AD_WF_Next_ID,SeqNo,IsStdUserWorkflow,Description,AD_Client_ID,Updated,UpdatedBy,AD_WF_NodeNext_ID,AD_Org_ID,EntityType) VALUES (540219,TO_TIMESTAMP('2018-09-19 15:16:58','YYYY-MM-DD HH24:MI:SS'),'Y',100,540220,100,'N','Standard Transition',0,TO_TIMESTAMP('2018-09-19 15:16:58','YYYY-MM-DD HH24:MI:SS'),100,540155,0,'D')
;

-- 2018-09-19T15:16:58.829
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_NodeNext (AD_WF_Node_ID,Created,IsActive,CreatedBy,AD_WF_Next_ID,SeqNo,IsStdUserWorkflow,Description,AD_Client_ID,Updated,UpdatedBy,AD_WF_NodeNext_ID,AD_Org_ID,EntityType) VALUES (540221,TO_TIMESTAMP('2018-09-19 15:16:58','YYYY-MM-DD HH24:MI:SS'),'Y',100,540222,100,'N','Standard Transition',0,TO_TIMESTAMP('2018-09-19 15:16:58','YYYY-MM-DD HH24:MI:SS'),100,540156,0,'D')
;

-- 2018-09-19T15:16:58.924
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AD_Client_ID,IsActive,Created,CreatedBy,Updated,IsReport,AccessLevel,AD_Workflow_ID,UpdatedBy,AD_Process_ID,Value,IsUseBPartnerLanguage,AD_Org_ID,Name,EntityType,Type) VALUES (0,'Y',TO_TIMESTAMP('2018-09-19 15:16:58','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2018-09-19 15:16:58','YYYY-MM-DD HH24:MI:SS'),'N','1',540102,100,541008,'Process_C_DunningDoc','Y',0,'Process_C_DunningDoc','D','Java')
;

-- 2018-09-19T15:16:58.927
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=541008 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2018-09-19T15:16:58.983
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Process_ID=541008,Updated=TO_TIMESTAMP('2018-09-19 15:16:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=547606
;

-- 2018-09-19T15:16:58.991
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Process_ID=541008,Updated=TO_TIMESTAMP('2018-09-19 15:16:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=561010
;


-- 2018-09-19T16:07:15.720
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,Help,ColumnName,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,Description,SelectionColumnSeqNo,AD_Org_ID,Name,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin) VALUES (19,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2018-09-19 16:07:15','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','N','N','Y','N',TO_TIMESTAMP('2018-09-19 16:07:15','YYYY-MM-DD HH24:MI:SS'),100,'N','N',540401,'N','Die Belegart bestimmt den Nummernkreis und die Vorgaben für die Belegverarbeitung.','C_DocType_ID',561012,'N','N','N','N','N','N','N','N','Belegart oder Verarbeitungsvorgaben',0,0,'Belegart',196,'de.metas.dunning','N','N')
;

-- 2018-09-19T16:07:15.723
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=561012 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-09-19T16:07:20.060
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_DunningDoc','ALTER TABLE public.C_DunningDoc ADD COLUMN C_DocType_ID NUMERIC(10)')
;

-- 2018-09-19T16:07:20.077
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_DunningDoc ADD CONSTRAINT CDocType_CDunningDoc FOREIGN KEY (C_DocType_ID) REFERENCES public.C_DocType DEFERRABLE INITIALLY DEFERRED
;

-- 2018-09-19T16:23:42.504
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Table_Process WHERE AD_Table_ID=540401 AND AD_Process_ID=540279
;

-- 2018-09-19T16:23:45.759
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=540279
;

-- 2018-09-19T16:23:45.765
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process WHERE AD_Process_ID=540279
;

