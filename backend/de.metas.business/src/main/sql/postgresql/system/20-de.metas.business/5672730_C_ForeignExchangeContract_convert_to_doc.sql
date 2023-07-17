-- Column: C_ForeignExchangeContract.DocAction
-- 2023-01-20T00:24:12.982Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585595,287,0,28,135,542281,'DocAction',TO_TIMESTAMP('2023-01-20 02:24:12','YYYY-MM-DD HH24:MI:SS'),100,'N','CO','D',0,2,'Y','Y','N','N','Y','N','N','N','N','Y','N','N','N','N','N','N','Y','Process Batch',0,0,TO_TIMESTAMP('2023-01-20 02:24:12','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-20T00:24:12.985Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585595 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-20T00:24:13.017Z
/* DDL */  select update_Column_Translation_From_AD_Element(287) 
;

-- Column: C_ForeignExchangeContract.DocStatus
-- 2023-01-20T00:24:13.677Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585596,289,0,17,131,542281,'DocStatus',TO_TIMESTAMP('2023-01-20 02:24:13','YYYY-MM-DD HH24:MI:SS'),100,'N','DR','','D',0,2,'','Y','Y','N','N','Y','N','N','N','N','Y','N','N','N','N','N','N','Y','Status',0,0,TO_TIMESTAMP('2023-01-20 02:24:13','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-20T00:24:13.679Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585596 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-20T00:24:13.682Z
/* DDL */  select update_Column_Translation_From_AD_Element(289) 
;

-- Column: C_ForeignExchangeContract.Processing
-- 2023-01-20T00:24:14.307Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585597,524,0,28,542281,'Processing',TO_TIMESTAMP('2023-01-20 02:24:14','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,1,'Y','Y','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','Y','Process Now',0,0,TO_TIMESTAMP('2023-01-20 02:24:14','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-20T00:24:14.309Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585597 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-20T00:24:14.312Z
/* DDL */  select update_Column_Translation_From_AD_Element(524) 
;

-- 2023-01-20T00:24:15.161Z
--UPDATE AD_Sequence SET CurrentNext = CurrentNext + ? WHERE AD_Sequence_ID = ? RETURNING CurrentNext - ?
-- ;

-- 2023-01-20T00:24:15.170Z
INSERT INTO AD_Workflow (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Table_ID,AD_Workflow_ID,Author,Cost,Created,CreatedBy,DocumentNo,Duration,DurationUnit,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsValid,Name,PublishStatus,Updated,UpdatedBy,Value,Version,WaitingTime,WorkflowType,WorkingTime) VALUES ('1',0,0,542281,540122,'metasfresh ERP',0,TO_TIMESTAMP('2023-01-20 02:24:14','YYYY-MM-DD HH24:MI:SS'),100,'10000001',1,'D','D','Y','N','N','N','Process_C_ForeignExchangeContract','R',TO_TIMESTAMP('2023-01-20 02:24:14','YYYY-MM-DD HH24:MI:SS'),100,'Process_C_ForeignExchangeContract',0,0,'P',0)
;

-- 2023-01-20T00:24:15.172Z
INSERT INTO AD_Workflow_Trl (AD_Language,AD_Workflow_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Workflow_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Workflow t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Workflow_ID=540122 AND NOT EXISTS (SELECT 1 FROM AD_Workflow_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Workflow_ID=t.AD_Workflow_ID)
;

-- 2023-01-20T00:24:15.341Z
INSERT INTO AD_WF_Node (Action,AD_Client_ID,AD_Org_ID,AD_WF_Node_ID,AD_Workflow_ID,Cost,Created,CreatedBy,Description,Duration,DurationLimit,EntityType,IsActive,IsCentrallyMaintained,JoinElement,Name,SplitElement,Updated,UpdatedBy,Value,WaitingTime,XPosition,YPosition) VALUES ('Z',0,0,540300,540122,0,TO_TIMESTAMP('2023-01-20 02:24:15','YYYY-MM-DD HH24:MI:SS'),100,'(Standard Node)',0,0,'D','Y','Y','X','(Start)','X',TO_TIMESTAMP('2023-01-20 02:24:15','YYYY-MM-DD HH24:MI:SS'),100,'(Start)',0,0,0)
;

-- 2023-01-20T00:24:15.343Z
INSERT INTO AD_WF_Node_Trl (AD_Language,AD_WF_Node_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_WF_Node_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_WF_Node t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_WF_Node_ID=540300 AND NOT EXISTS (SELECT 1 FROM AD_WF_Node_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_WF_Node_ID=t.AD_WF_Node_ID)
;

-- 2023-01-20T00:24:15.348Z
UPDATE AD_Workflow SET AD_WF_Node_ID=540300, IsValid='Y',Updated=TO_TIMESTAMP('2023-01-20 02:24:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Workflow_ID=540122
;

-- 2023-01-20T00:24:15.358Z
UPDATE AD_Workflow SET AD_WF_Node_ID=540300, IsValid='Y',Updated=TO_TIMESTAMP('2023-01-20 02:24:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Workflow_ID=540122
;

-- 2023-01-20T00:24:15.461Z
INSERT INTO AD_WF_Node (Action,AD_Client_ID,AD_Org_ID,AD_WF_Node_ID,AD_Workflow_ID,Cost,Created,CreatedBy,Description,DocAction,Duration,DurationLimit,EntityType,IsActive,IsCentrallyMaintained,JoinElement,Name,SplitElement,Updated,UpdatedBy,Value,WaitingTime,XPosition,YPosition) VALUES ('D',0,0,540301,540122,0,TO_TIMESTAMP('2023-01-20 02:24:15','YYYY-MM-DD HH24:MI:SS'),100,'(Standard Node)','--',0,0,'D','Y','Y','X','(DocAuto)','X',TO_TIMESTAMP('2023-01-20 02:24:15','YYYY-MM-DD HH24:MI:SS'),100,'(DocAuto)',0,0,0)
;

-- 2023-01-20T00:24:15.463Z
INSERT INTO AD_WF_Node_Trl (AD_Language,AD_WF_Node_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_WF_Node_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_WF_Node t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_WF_Node_ID=540301 AND NOT EXISTS (SELECT 1 FROM AD_WF_Node_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_WF_Node_ID=t.AD_WF_Node_ID)
;

-- 2023-01-20T00:24:15.563Z
INSERT INTO AD_WF_Node (Action,AD_Client_ID,AD_Org_ID,AD_WF_Node_ID,AD_Workflow_ID,Cost,Created,CreatedBy,Description,DocAction,Duration,DurationLimit,EntityType,IsActive,IsCentrallyMaintained,JoinElement,Name,SplitElement,Updated,UpdatedBy,Value,WaitingTime,XPosition,YPosition) VALUES ('D',0,0,540302,540122,0,TO_TIMESTAMP('2023-01-20 02:24:15','YYYY-MM-DD HH24:MI:SS'),100,'(Standard Node)','PR',0,0,'D','Y','Y','X','(DocPrepare)','X',TO_TIMESTAMP('2023-01-20 02:24:15','YYYY-MM-DD HH24:MI:SS'),100,'(DocPrepare)',0,0,0)
;

-- 2023-01-20T00:24:15.565Z
INSERT INTO AD_WF_Node_Trl (AD_Language,AD_WF_Node_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_WF_Node_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_WF_Node t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_WF_Node_ID=540302 AND NOT EXISTS (SELECT 1 FROM AD_WF_Node_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_WF_Node_ID=t.AD_WF_Node_ID)
;

-- 2023-01-20T00:24:15.673Z
INSERT INTO AD_WF_Node (Action,AD_Client_ID,AD_Org_ID,AD_WF_Node_ID,AD_Workflow_ID,Cost,Created,CreatedBy,Description,DocAction,Duration,DurationLimit,EntityType,IsActive,IsCentrallyMaintained,JoinElement,Name,SplitElement,Updated,UpdatedBy,Value,WaitingTime,XPosition,YPosition) VALUES ('D',0,0,540303,540122,0,TO_TIMESTAMP('2023-01-20 02:24:15','YYYY-MM-DD HH24:MI:SS'),100,'(Standard Node)','CO',0,0,'D','Y','Y','X','(DocComplete)','X',TO_TIMESTAMP('2023-01-20 02:24:15','YYYY-MM-DD HH24:MI:SS'),100,'(DocComplete)',0,0,0)
;

-- 2023-01-20T00:24:15.674Z
INSERT INTO AD_WF_Node_Trl (AD_Language,AD_WF_Node_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_WF_Node_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_WF_Node t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_WF_Node_ID=540303 AND NOT EXISTS (SELECT 1 FROM AD_WF_Node_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_WF_Node_ID=t.AD_WF_Node_ID)
;

-- 2023-01-20T00:24:15.814Z
INSERT INTO AD_WF_NodeNext (AD_Client_ID,AD_Org_ID,AD_WF_Next_ID,AD_WF_Node_ID,AD_WF_NodeNext_ID,Created,CreatedBy,Description,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540302,540300,540188,TO_TIMESTAMP('2023-01-20 02:24:15','YYYY-MM-DD HH24:MI:SS'),100,'Standard Transition','D','Y',10,TO_TIMESTAMP('2023-01-20 02:24:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-20T00:24:15.821Z
UPDATE AD_WF_NodeNext SET Description='(Standard Approval)', IsStdUserWorkflow='Y',Updated=TO_TIMESTAMP('2023-01-20 02:24:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_WF_NodeNext_ID=540188
;

-- 2023-01-20T00:24:15.933Z
INSERT INTO AD_WF_NodeNext (AD_Client_ID,AD_Org_ID,AD_WF_Next_ID,AD_WF_Node_ID,AD_WF_NodeNext_ID,Created,CreatedBy,Description,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540301,540300,540189,TO_TIMESTAMP('2023-01-20 02:24:15','YYYY-MM-DD HH24:MI:SS'),100,'Standard Transition','D','Y',100,TO_TIMESTAMP('2023-01-20 02:24:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-20T00:24:16.029Z
INSERT INTO AD_WF_NodeNext (AD_Client_ID,AD_Org_ID,AD_WF_Next_ID,AD_WF_Node_ID,AD_WF_NodeNext_ID,Created,CreatedBy,Description,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540303,540302,540190,TO_TIMESTAMP('2023-01-20 02:24:15','YYYY-MM-DD HH24:MI:SS'),100,'Standard Transition','D','Y',100,TO_TIMESTAMP('2023-01-20 02:24:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Value: Process_C_ForeignExchangeContract
-- 2023-01-20T00:24:16.140Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Workflow_ID,Created,CreatedBy,EntityType,IsActive,IsReport,IsUseBPartnerLanguage,Name,Type,Updated,UpdatedBy,Value) VALUES ('1',0,0,585187,540122,TO_TIMESTAMP('2023-01-20 02:24:16','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','Y','Process_C_ForeignExchangeContract','Java',TO_TIMESTAMP('2023-01-20 02:24:16','YYYY-MM-DD HH24:MI:SS'),100,'Process_C_ForeignExchangeContract')
;

-- 2023-01-20T00:24:16.156Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585187 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Column: C_ForeignExchangeContract.Processing
-- 2023-01-20T00:24:16.231Z
UPDATE AD_Column SET AD_Process_ID=585187,Updated=TO_TIMESTAMP('2023-01-20 02:24:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585597
;

-- Column: C_ForeignExchangeContract.DocAction
-- 2023-01-20T00:24:16.576Z
UPDATE AD_Column SET AD_Process_ID=585187,Updated=TO_TIMESTAMP('2023-01-20 02:24:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585595
;

-- Table: C_ForeignExchangeContract
-- 2023-01-20T00:25:24.977Z
UPDATE AD_Table SET AD_Window_ID=541664,Updated=TO_TIMESTAMP('2023-01-20 02:25:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542281
;

-- Field: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract(546745,D) -> Process Batch
-- Column: C_ForeignExchangeContract.DocAction
-- 2023-01-20T00:25:47.268Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585595,710322,0,546745,TO_TIMESTAMP('2023-01-20 02:25:47','YYYY-MM-DD HH24:MI:SS'),100,2,'D','Y','N','N','N','N','N','N','N','Process Batch',TO_TIMESTAMP('2023-01-20 02:25:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-20T00:25:47.270Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710322 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-20T00:25:47.272Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(287) 
;

-- 2023-01-20T00:25:47.285Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710322
;

-- 2023-01-20T00:25:47.288Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710322)
;

-- Field: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract(546745,D) -> Status
-- Column: C_ForeignExchangeContract.DocStatus
-- 2023-01-20T00:25:47.394Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585596,710323,0,546745,TO_TIMESTAMP('2023-01-20 02:25:47','YYYY-MM-DD HH24:MI:SS'),100,'',2,'D','','Y','N','N','N','N','N','N','N','Status',TO_TIMESTAMP('2023-01-20 02:25:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-20T00:25:47.396Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710323 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-20T00:25:47.397Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(289) 
;

-- 2023-01-20T00:25:47.405Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710323
;

-- 2023-01-20T00:25:47.406Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710323)
;

-- Field: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract(546745,D) -> Process Now
-- Column: C_ForeignExchangeContract.Processing
-- 2023-01-20T00:25:47.523Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585597,710324,0,546745,TO_TIMESTAMP('2023-01-20 02:25:47','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Process Now',TO_TIMESTAMP('2023-01-20 02:25:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-20T00:25:47.525Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710324 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-20T00:25:47.526Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(524) 
;

-- 2023-01-20T00:25:47.548Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710324
;

-- 2023-01-20T00:25:47.549Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710324)
;

-- 2023-01-20T00:26:57.124Z
/* DDL */ SELECT public.db_alter_table('C_ForeignExchangeContract','ALTER TABLE public.C_ForeignExchangeContract ADD COLUMN DocAction CHAR(2) DEFAULT ''CO'' NOT NULL')
;

-- 2023-01-20T00:27:07.002Z
/* DDL */ SELECT public.db_alter_table('C_ForeignExchangeContract','ALTER TABLE public.C_ForeignExchangeContract ADD COLUMN DocStatus VARCHAR(2) DEFAULT ''DR'' NOT NULL')
;

-- 2023-01-20T00:27:21.894Z
/* DDL */ SELECT public.db_alter_table('C_ForeignExchangeContract','ALTER TABLE public.C_ForeignExchangeContract ADD COLUMN Processing CHAR(1)')
;

