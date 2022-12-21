-- Name: SAP_GLJournalLine
-- 2022-12-16T12:52:53.013Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541700,TO_TIMESTAMP('2022-12-16 14:52:52','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.acct','Y','N','SAP_GLJournalLine',TO_TIMESTAMP('2022-12-16 14:52:52','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2022-12-16T12:52:53.017Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541700 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: SAP_GLJournalLine
-- Table: SAP_GLJournalLine
-- Key: SAP_GLJournalLine.SAP_GLJournalLine_ID
-- 2022-12-16T12:53:05.945Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,585390,0,541700,542276,TO_TIMESTAMP('2022-12-16 14:53:05','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.acct','Y','N','N',TO_TIMESTAMP('2022-12-16 14:53:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: SAP_GLJournal.DateAcct
-- 2022-12-16T12:54:26.476Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=20,Updated=TO_TIMESTAMP('2022-12-16 14:54:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585342
;

-- Column: SAP_GLJournal.Description
-- 2022-12-16T12:54:46.039Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=30,Updated=TO_TIMESTAMP('2022-12-16 14:54:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585344
;

-- Column: SAP_GLJournalLine.SAP_GLJournal_ID
-- 2022-12-16T12:55:25.346Z
UPDATE AD_Column SET IsIdentifier='Y', IsUpdateable='N', SeqNo=10,Updated=TO_TIMESTAMP('2022-12-16 14:55:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585391
;

-- Column: SAP_GLJournalLine.Line
-- 2022-12-16T12:55:47.774Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=20,Updated=TO_TIMESTAMP('2022-12-16 14:55:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585402
;

-- Column: SAP_GLJournalLine.Parent_ID
-- 2022-12-16T12:56:25.017Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585416,496,0,30,541700,542276,'Parent_ID',TO_TIMESTAMP('2022-12-16 14:56:24','YYYY-MM-DD HH24:MI:SS'),100,'N','Parent of Entity','de.metas.acct',0,10,'The Parent indicates the value used to represent the next level in a hierarchy or report to level for a record','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Parent',0,0,TO_TIMESTAMP('2022-12-16 14:56:24','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-16T12:56:25.019Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585416 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-16T12:56:25.048Z
/* DDL */  select update_Column_Translation_From_AD_Element(496) 
;

-- 2022-12-16T13:00:19.290Z
/* DDL */ SELECT public.db_alter_table('SAP_GLJournalLine','ALTER TABLE public.SAP_GLJournalLine ADD COLUMN Parent_ID NUMERIC(10)')
;

-- 2022-12-16T13:00:19.298Z
ALTER TABLE SAP_GLJournalLine ADD CONSTRAINT Parent_SAPGLJournalLine FOREIGN KEY (Parent_ID) REFERENCES public.SAP_GLJournalLine DEFERRABLE INITIALLY DEFERRED
;

-- Value: SAP_GLJournal_GenerateTaxLines
-- Classname: de.metas.acct.gljournal_sap.process.SAP_GLJournal_GenerateTaxLines
-- 2022-12-16T13:54:04.032Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585172,'Y','de.metas.acct.gljournal_sap.process.SAP_GLJournal_GenerateTaxLines','N',TO_TIMESTAMP('2022-12-16 15:54:03','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.acct','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Regenerate Tax Lines','json','N','N','xls','Java',TO_TIMESTAMP('2022-12-16 15:54:03','YYYY-MM-DD HH24:MI:SS'),100,'SAP_GLJournal_GenerateTaxLines')
;

-- 2022-12-16T13:54:04.034Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585172 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: SAP_GLJournal_GenerateTaxLines(de.metas.acct.gljournal_sap.process.SAP_GLJournal_GenerateTaxLines)
-- Table: SAP_GLJournal
-- EntityType: de.metas.acct
-- 2022-12-16T13:54:18.420Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585172,542275,541324,TO_TIMESTAMP('2022-12-16 15:54:18','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.acct','Y',TO_TIMESTAMP('2022-12-16 15:54:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N')
;

