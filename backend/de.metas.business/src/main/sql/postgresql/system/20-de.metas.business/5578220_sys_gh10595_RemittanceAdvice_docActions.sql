-- 2021-02-03T00:27:54.328Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_remittanceadvice_line','IsInvoiceDateValid','CHAR(1)',null,null)
;

-- 2021-02-03T00:28:05.123Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_remittanceadvice_line','AD_Client_ID','NUMERIC(10)',null,null)
;

-- 2021-02-03T00:28:28.690Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_RemittanceAdvice_Line','ALTER TABLE public.C_RemittanceAdvice_Line ADD COLUMN IsInvoiceDocTypeValid CHAR(1) CHECK (IsInvoiceDocTypeValid IN (''Y'',''N''))')
;

-- 2021-02-03T00:28:35.750Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_remittanceadvice_line','IsAmountValid','CHAR(1)',null,null)
;

-- 2021-02-03T00:28:41.600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_remittanceadvice_line','IsBPartnerValid','CHAR(1)',null,null)
;

-- 2021-02-03T00:28:52.295Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_remittanceadvice_line','IsInvoiceResolved','CHAR(1)',null,null)
;

-- 2021-02-03T00:28:58.041Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_remittanceadvice_line','IsLineAcknowledged','CHAR(1)',null,null)
;

-- 2021-02-03T00:31:14.003Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,572626,913,0,20,541573,'I_IsImported',TO_TIMESTAMP('2021-02-03 02:31:13','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Ist dieser Import verarbeitet worden?','D',0,1,'DasSelektionsfeld "Importiert" zeigt an, ob dieser Import verarbeitet worden ist.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Importiert',0,0,TO_TIMESTAMP('2021-02-03 02:31:13','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-02-03T00:31:14.012Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572626 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-02-03T00:31:14.041Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(913) 
;

-- 2021-02-03T00:31:15.282Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_RemittanceAdvice','ALTER TABLE public.C_RemittanceAdvice ADD COLUMN I_IsImported CHAR(1) DEFAULT ''N'' CHECK (I_IsImported IN (''Y'',''N'')) NOT NULL')
;

-- 2021-02-03T00:33:42.251Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,ReadOnlyLogic,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,572627,289,0,17,131,541573,'DocStatus',TO_TIMESTAMP('2021-02-03 02:33:42','YYYY-MM-DD HH24:MI:SS'),100,'N','DR','The current status of the document','D',0,2,'The Document Status indicates the status of a document at this time.  If you want to change the document status, use the Document Action field','Y','N','Y','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Belegstatus','',0,0,TO_TIMESTAMP('2021-02-03 02:33:42','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-02-03T00:33:42.253Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572627 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-02-03T00:33:42.258Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(289) 
;

-- 2021-02-03T00:33:43.841Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_RemittanceAdvice','ALTER TABLE public.C_RemittanceAdvice ADD COLUMN DocStatus VARCHAR(2) DEFAULT ''DR'' NOT NULL')
;

-- -- 2021-02-03T00:37:28.266Z
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_Sequence SET CurrentNext = CurrentNext + ? WHERE AD_Sequence_ID = ? RETURNING CurrentNext - ?
-- ;

-- 2021-02-03T00:37:28.268Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Workflow (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Table_ID,AD_Workflow_ID,Author,Cost,Created,CreatedBy,DocumentNo,Duration,DurationLimit,DurationUnit,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsValid,MovingTime,Name,OverlapUnits,Priority,PublishStatus,QtyBatchSize,QueuingTime,SetupTime,UnitsCycles,Updated,UpdatedBy,ValidateWorkflow,Value,Version,WaitingTime,WorkflowType,WorkingTime,Yield) VALUES ('3',0,0,541573,540111,'Metasfresh',0,TO_TIMESTAMP('2021-02-03 02:37:28','YYYY-MM-DD HH24:MI:SS'),100,'10000000',0,0,'D','D','Y','N','N','N',0,'Process Remittance Advice',0,0,'R',1,0,0,0,TO_TIMESTAMP('2021-02-03 02:37:28','YYYY-MM-DD HH24:MI:SS'),100,'N','Process_RemittanceAdvice',0,0,'P',0,100)
;

-- 2021-02-03T00:37:28.270Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Workflow_Trl (AD_Language,AD_Workflow_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Workflow_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Workflow t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Workflow_ID=540111 AND NOT EXISTS (SELECT 1 FROM AD_Workflow_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Workflow_ID=t.AD_Workflow_ID)
;

-- 2021-02-03T00:37:50.508Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Workflow SET IsBetaFunctionality='Y', IsValid='N',Updated=TO_TIMESTAMP('2021-02-03 02:37:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Workflow_ID=540111
;

-- 2021-02-03T00:39:56.315Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_Node (AD_Workflow_ID,IsActive,Created,CreatedBy,Updated,IsCentrallyMaintained,IsSubcontracting,MovingTime,YPosition,XPosition,Duration,Cost,WaitingTime,WorkingTime,Priority,JoinElement,SplitElement,WaitTime,IsMilestone,DynPriorityChange,SetupTime,QueuingTime,AD_Client_ID,UnitsCycles,OverlapUnits,Yield,UpdatedBy,AD_WF_Node_ID,Value,DurationLimit,Action,Name,AD_Org_ID,Description,DocAction,EntityType) VALUES (540111,'Y',TO_TIMESTAMP('2021-02-03 02:39:56','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-02-03 02:39:56','YYYY-MM-DD HH24:MI:SS'),'Y','N',0,0,0,0,0,0,0,0,'X','X',0,'N',0,0,0,0,0,0,100,100,540244,'(DocAuto)',0,'D','(DocAuto)',0,'(Standard Node)','--','D')
;

-- 2021-02-03T00:39:56.325Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_Node_Trl (AD_Language,AD_WF_Node_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_WF_Node_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_WF_Node t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_WF_Node_ID=540244 AND NOT EXISTS (SELECT 1 FROM AD_WF_Node_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_WF_Node_ID=t.AD_WF_Node_ID)
;

-- 2021-02-03T00:41:17.217Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_Node (AD_Workflow_ID,IsActive,Created,CreatedBy,Updated,IsCentrallyMaintained,IsSubcontracting,MovingTime,YPosition,XPosition,Duration,Cost,WaitingTime,WorkingTime,Priority,JoinElement,SplitElement,WaitTime,IsMilestone,DynPriorityChange,SetupTime,QueuingTime,AD_Client_ID,UnitsCycles,OverlapUnits,Yield,UpdatedBy,AD_WF_Node_ID,Value,DurationLimit,Action,Name,AD_Org_ID,Description,DocAction,EntityType) VALUES (540111,'Y',TO_TIMESTAMP('2021-02-03 02:41:17','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-02-03 02:41:17','YYYY-MM-DD HH24:MI:SS'),'Y','N',0,0,0,0,0,0,0,0,'X','X',0,'N',0,0,0,0,0,0,100,100,540245,'(DocComplete)',0,'D','(DocComplete)',0,'(Standard Node)','CO','D')
;

-- 2021-02-03T00:41:17.218Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_Node_Trl (AD_Language,AD_WF_Node_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_WF_Node_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_WF_Node t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_WF_Node_ID=540245 AND NOT EXISTS (SELECT 1 FROM AD_WF_Node_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_WF_Node_ID=t.AD_WF_Node_ID)
;

-- 2021-02-03T00:41:46.673Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_Node (AD_Workflow_ID,IsActive,Created,CreatedBy,Updated,IsCentrallyMaintained,IsSubcontracting,MovingTime,YPosition,XPosition,Duration,Cost,WaitingTime,WorkingTime,Priority,JoinElement,SplitElement,WaitTime,IsMilestone,DynPriorityChange,SetupTime,QueuingTime,AD_Client_ID,UnitsCycles,OverlapUnits,Yield,UpdatedBy,AD_WF_Node_ID,Value,DurationLimit,Action,Name,AD_Org_ID,Description,DocAction,EntityType) VALUES (540111,'Y',TO_TIMESTAMP('2021-02-03 02:41:46','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-02-03 02:41:46','YYYY-MM-DD HH24:MI:SS'),'Y','N',0,0,0,0,0,0,0,0,'X','X',0,'N',0,0,0,0,0,0,100,100,540246,'(DocPrepare)',0,'D','(DocPrepare)',0,'(Standard Node)','PR','D')
;

-- 2021-02-03T00:41:46.675Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_Node_Trl (AD_Language,AD_WF_Node_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_WF_Node_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_WF_Node t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_WF_Node_ID=540246 AND NOT EXISTS (SELECT 1 FROM AD_WF_Node_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_WF_Node_ID=t.AD_WF_Node_ID)
;

-- 2021-02-03T00:42:15.305Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_Node (AD_Workflow_ID,IsActive,Created,CreatedBy,Updated,IsCentrallyMaintained,IsSubcontracting,MovingTime,YPosition,XPosition,Duration,Cost,WaitingTime,WorkingTime,Priority,JoinElement,SplitElement,WaitTime,IsMilestone,DynPriorityChange,SetupTime,QueuingTime,AD_Client_ID,UnitsCycles,OverlapUnits,Yield,UpdatedBy,AD_WF_Node_ID,Value,DurationLimit,Action,Name,AD_Org_ID,Description,DocAction,EntityType) VALUES (540111,'Y',TO_TIMESTAMP('2021-02-03 02:42:15','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-02-03 02:42:15','YYYY-MM-DD HH24:MI:SS'),'Y','N',0,0,0,0,0,0,0,0,'X','X',0,'N',0,0,0,0,0,0,100,100,540247,'(Start)',0,'Z','(Start)',0,'(Standard Node)','CO','D')
;

-- 2021-02-03T00:42:15.309Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_Node_Trl (AD_Language,AD_WF_Node_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_WF_Node_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_WF_Node t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_WF_Node_ID=540247 AND NOT EXISTS (SELECT 1 FROM AD_WF_Node_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_WF_Node_ID=t.AD_WF_Node_ID)
;

-- 2021-02-03T00:43:17.500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Workflow SET AD_WF_Node_ID=540247, IsValid='Y',Updated=TO_TIMESTAMP('2021-02-03 02:43:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Workflow_ID=540111
;

-- 2021-02-03T00:45:15.225Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Workflow_ID,AllowProcessReRun,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsServerProcess,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,584796,540111,'N','N',TO_TIMESTAMP('2021-02-03 02:45:15','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','Y','N','N','N','N','N','Y','Y',0,'C_RemittanceAdvice Process','json','N','N','Java',TO_TIMESTAMP('2021-02-03 02:45:15','YYYY-MM-DD HH24:MI:SS'),100,'C_RemittanceAdvice Process')
;

-- 2021-02-03T00:45:15.228Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=584796 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2021-02-03T00:47:29.475Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,572628,287,0,584796,28,135,541573,'DocAction',TO_TIMESTAMP('2021-02-03 02:47:29','YYYY-MM-DD HH24:MI:SS'),100,'N','CO','Der zukünftige Status des Belegs','D',0,2,'You find the current status in the Document Status field. The options are listed in a popup','Y','N','Y','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Belegverarbeitung',0,0,TO_TIMESTAMP('2021-02-03 02:47:29','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-02-03T00:47:29.480Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572628 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-02-03T00:47:29.490Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(287) 
;

-- 2021-02-03T00:47:31.344Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_RemittanceAdvice','ALTER TABLE public.C_RemittanceAdvice ADD COLUMN DocAction CHAR(2) DEFAULT ''CO'' NOT NULL')
;

