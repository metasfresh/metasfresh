-- Column: M_Picking_Job.IsPickingReviewRequired
-- 2022-06-28T14:38:16.180Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583541,577151,0,20,541906,'IsPickingReviewRequired',TO_TIMESTAMP('2022-06-28 17:38:15','YYYY-MM-DD HH24:MI:SS'),100,'N','N','de.metas.handlingunits',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Kommissionierprüfung erforderlich',0,0,TO_TIMESTAMP('2022-06-28 17:38:15','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-06-28T14:38:16.182Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583541 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-06-28T14:38:16.208Z
/* DDL */  select update_Column_Translation_From_AD_Element(577151) 
;

-- 2022-06-28T14:38:17.782Z
/* DDL */ SELECT public.db_alter_table('M_Picking_Job','ALTER TABLE public.M_Picking_Job ADD COLUMN IsPickingReviewRequired CHAR(1) DEFAULT ''N'' CHECK (IsPickingReviewRequired IN (''Y'',''N'')) NOT NULL')
;

-- Column: M_Picking_Job.IsApproved
-- 2022-06-28T15:18:36.207Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583542,351,0,20,541906,'IsApproved',TO_TIMESTAMP('2022-06-28 18:18:36','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Zeigt an, ob dieser Beleg eine Freigabe braucht','de.metas.handlingunits',0,1,'Das Selektionsfeld "Freigabe" zeigt an, dass dieser Beleg eine Freigabe braucht, bevor er verarbeitet werden kann','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Freigegeben',0,0,TO_TIMESTAMP('2022-06-28 18:18:36','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-06-28T15:18:36.208Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583542 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-06-28T15:18:36.211Z
/* DDL */  select update_Column_Translation_From_AD_Element(351) 
;

-- 2022-06-28T15:18:37.535Z
/* DDL */ SELECT public.db_alter_table('M_Picking_Job','ALTER TABLE public.M_Picking_Job ADD COLUMN IsApproved CHAR(1) DEFAULT ''N'' CHECK (IsApproved IN (''Y'',''N'')) NOT NULL')
;

-- 2022-06-28T15:19:13.216Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581069,0,'IsReadyToReview',TO_TIMESTAMP('2022-06-28 18:19:13','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','Ready To Review','Ready To Review',TO_TIMESTAMP('2022-06-28 18:19:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-28T15:19:13.217Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581069 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-06-28T15:19:33.014Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Bereit zur Überprüfung', PrintName='Bereit zur Überprüfung',Updated=TO_TIMESTAMP('2022-06-28 18:19:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581069 AND AD_Language='de_CH'
;

-- 2022-06-28T15:19:33.020Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581069,'de_CH') 
;

-- 2022-06-28T15:19:36.424Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Bereit zur Überprüfung', PrintName='Bereit zur Überprüfung',Updated=TO_TIMESTAMP('2022-06-28 18:19:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581069 AND AD_Language='de_DE'
;

-- 2022-06-28T15:19:36.426Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581069,'de_DE') 
;

-- 2022-06-28T15:19:36.431Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581069,'de_DE') 
;

-- 2022-06-28T15:19:36.432Z
UPDATE AD_Column SET ColumnName='IsReadyToReview', Name='Bereit zur Überprüfung', Description=NULL, Help=NULL WHERE AD_Element_ID=581069
;

-- 2022-06-28T15:19:36.433Z
UPDATE AD_Process_Para SET ColumnName='IsReadyToReview', Name='Bereit zur Überprüfung', Description=NULL, Help=NULL, AD_Element_ID=581069 WHERE UPPER(ColumnName)='ISREADYTOREVIEW' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-06-28T15:19:36.434Z
UPDATE AD_Process_Para SET ColumnName='IsReadyToReview', Name='Bereit zur Überprüfung', Description=NULL, Help=NULL WHERE AD_Element_ID=581069 AND IsCentrallyMaintained='Y'
;

-- 2022-06-28T15:19:36.435Z
UPDATE AD_Field SET Name='Bereit zur Überprüfung', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581069) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581069)
;

-- 2022-06-28T15:19:36.440Z
UPDATE AD_PrintFormatItem pi SET PrintName='Bereit zur Überprüfung', Name='Bereit zur Überprüfung' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581069)
;

-- 2022-06-28T15:19:36.441Z
UPDATE AD_Tab SET Name='Bereit zur Überprüfung', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581069
;

-- 2022-06-28T15:19:36.442Z
UPDATE AD_WINDOW SET Name='Bereit zur Überprüfung', Description=NULL, Help=NULL WHERE AD_Element_ID = 581069
;

-- 2022-06-28T15:19:36.443Z
UPDATE AD_Menu SET   Name = 'Bereit zur Überprüfung', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581069
;

-- 2022-06-28T15:19:41.518Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-06-28 18:19:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581069 AND AD_Language='en_US'
;

-- 2022-06-28T15:19:41.519Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581069,'en_US') 
;

-- Column: M_Picking_Job.IsReadyToReview
-- 2022-06-28T15:19:48.995Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583543,581069,0,20,541906,'IsReadyToReview',TO_TIMESTAMP('2022-06-28 18:19:48','YYYY-MM-DD HH24:MI:SS'),100,'N','N','de.metas.handlingunits',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Bereit zur Überprüfung',0,0,TO_TIMESTAMP('2022-06-28 18:19:48','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-06-28T15:19:48.996Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583543 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-06-28T15:19:48.998Z
/* DDL */  select update_Column_Translation_From_AD_Element(581069) 
;

-- 2022-06-28T15:21:45.805Z
/* DDL */ SELECT public.db_alter_table('M_Picking_Job','ALTER TABLE public.M_Picking_Job ADD COLUMN IsReadyToReview CHAR(1) DEFAULT ''N'' CHECK (IsReadyToReview IN (''Y'',''N'')) NOT NULL')
;

-- Field: Picking Job -> Picking Job -> Kommissionierprüfung erforderlich
-- Column: M_Picking_Job.IsPickingReviewRequired
-- 2022-06-29T09:19:11.867Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583541,700829,0,544861,TO_TIMESTAMP('2022-06-29 12:19:11','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','Kommissionierprüfung erforderlich',TO_TIMESTAMP('2022-06-29 12:19:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-29T09:19:11.868Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=700829 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-06-29T09:19:11.871Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577151) 
;

-- 2022-06-29T09:19:11.884Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=700829
;

-- 2022-06-29T09:19:11.886Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(700829)
;

-- Field: Picking Job -> Picking Job -> Freigegeben
-- Column: M_Picking_Job.IsApproved
-- 2022-06-29T09:19:11.983Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583542,700830,0,544861,TO_TIMESTAMP('2022-06-29 12:19:11','YYYY-MM-DD HH24:MI:SS'),100,'Zeigt an, ob dieser Beleg eine Freigabe braucht',1,'de.metas.handlingunits','Das Selektionsfeld "Freigabe" zeigt an, dass dieser Beleg eine Freigabe braucht, bevor er verarbeitet werden kann','Y','N','N','N','N','N','N','N','Freigegeben',TO_TIMESTAMP('2022-06-29 12:19:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-29T09:19:11.984Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=700830 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-06-29T09:19:11.985Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(351) 
;

-- 2022-06-29T09:19:11.990Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=700830
;

-- 2022-06-29T09:19:11.991Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(700830)
;

-- Field: Picking Job -> Picking Job -> Bereit zur Überprüfung
-- Column: M_Picking_Job.IsReadyToReview
-- 2022-06-29T09:19:12.075Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583543,700831,0,544861,TO_TIMESTAMP('2022-06-29 12:19:11','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','Bereit zur Überprüfung',TO_TIMESTAMP('2022-06-29 12:19:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-29T09:19:12.076Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=700831 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-06-29T09:19:12.077Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581069) 
;

-- 2022-06-29T09:19:12.079Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=700831
;

-- 2022-06-29T09:19:12.079Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(700831)
;

-- 2022-06-29T09:37:47.692Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585070,'Y','de.metas.handlingunits.picking.job.process.M_Picking_Job_ApproveAndComplete','N',TO_TIMESTAMP('2022-06-29 12:37:46','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Approve and complete picking','json','N','N','xls','Java',TO_TIMESTAMP('2022-06-29 12:37:46','YYYY-MM-DD HH24:MI:SS'),100,'M_Picking_Job_ApproveAndComplete')
;

-- 2022-06-29T09:37:47.693Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585070 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2022-06-29T09:38:04.428Z
UPDATE AD_Process SET EntityType='de.metas.handlingunits',Updated=TO_TIMESTAMP('2022-06-29 12:38:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585070
;

-- 2022-06-29T09:38:16.562Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Kommissionierung genehmigen und abschließen',Updated=TO_TIMESTAMP('2022-06-29 12:38:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585070
;

-- 2022-06-29T09:38:19.442Z
UPDATE AD_Process SET Description=NULL, Help=NULL, Name='Kommissionierung genehmigen und abschließen',Updated=TO_TIMESTAMP('2022-06-29 12:38:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585070
;

-- 2022-06-29T09:38:19.438Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Kommissionierung genehmigen und abschließen',Updated=TO_TIMESTAMP('2022-06-29 12:38:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585070
;

-- 2022-06-29T09:38:55.414Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585070,541906,541117,TO_TIMESTAMP('2022-06-29 12:38:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y',TO_TIMESTAMP('2022-06-29 12:38:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- 2022-06-29T09:39:00.828Z
UPDATE AD_Table_Process SET WEBUI_ViewQuickAction='Y',Updated=TO_TIMESTAMP('2022-06-29 12:39:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_Process_ID=541117
;

-- 2022-06-29T09:58:19.044Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545135,0,TO_TIMESTAMP('2022-06-29 12:58:18','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','Request review for picking','I',TO_TIMESTAMP('2022-06-29 12:58:18','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking.workflow.handlers.activity_handlers.RequestReviewWFActivityHandler.caption')
;

-- 2022-06-29T09:58:19.045Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545135 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2022-06-29T09:58:30.903Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Überprüfung zur Kommissionierung anfordern',Updated=TO_TIMESTAMP('2022-06-29 12:58:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545135
;

-- 2022-06-29T09:58:38.323Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-06-29 12:58:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545135
;

-- 2022-06-29T09:58:47.146Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Überprüfung zur Kommissionierung anfordern',Updated=TO_TIMESTAMP('2022-06-29 12:58:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545135
;




-- 2022-06-29T13:35:45.343Z
UPDATE AD_Process SET Name='Kommissionierauftrag freigeben und fertigstellen',Updated=TO_TIMESTAMP('2022-06-29 16:35:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585070
;

-- 2022-06-29T13:35:50.027Z
UPDATE AD_Process_Trl SET Name='Kommissionierauftrag freigeben und fertigstellen',Updated=TO_TIMESTAMP('2022-06-29 16:35:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585070
;

-- 2022-06-29T13:35:58.226Z
UPDATE AD_Process_Trl SET Name='Kommissionierauftrag freigeben und fertigstellen',Updated=TO_TIMESTAMP('2022-06-29 16:35:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585070
;

-- 2022-06-29T13:36:50.802Z
UPDATE AD_Message_Trl SET MsgText='Freigabe für Kommissionierauftrag anfordern',Updated=TO_TIMESTAMP('2022-06-29 16:36:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545135
;

-- 2022-06-29T13:36:56.322Z
UPDATE AD_Message_Trl SET MsgText='Freigabe für Kommissionierauftrag anfordern',Updated=TO_TIMESTAMP('2022-06-29 16:36:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545135
;





--


-- 2022-07-06T14:15:09.025Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,544861,545067,TO_TIMESTAMP('2022-07-06 17:15:08','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-07-06 17:15:08','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2022-07-06T14:15:09.029Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=545067 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2022-07-06T14:15:09.165Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546133,545067,TO_TIMESTAMP('2022-07-06 17:15:09','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-07-06 17:15:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-06T14:15:09.269Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546134,545067,TO_TIMESTAMP('2022-07-06 17:15:09','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2022-07-06 17:15:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-06T14:15:09.405Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546133,549453,TO_TIMESTAMP('2022-07-06 17:15:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2022-07-06 17:15:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Picking Job -> Picking Job.Picking Job
-- Column: M_Picking_Job.M_Picking_Job_ID
-- 2022-07-06T14:15:09.548Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,669033,0,544861,549453,610030,'F',TO_TIMESTAMP('2022-07-06 17:15:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Picking Job',10,10,0,TO_TIMESTAMP('2022-07-06 17:15:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Picking Job -> Picking Job.Auftrag
-- Column: M_Picking_Job.C_Order_ID
-- 2022-07-06T14:15:09.641Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,669034,0,544861,549453,610031,'F',TO_TIMESTAMP('2022-07-06 17:15:09','YYYY-MM-DD HH24:MI:SS'),100,'Auftrag','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you cloase an order, unshipped (backordered) quantities are cancelled.','Y','N','Y','Y','N','Auftrag',20,20,0,TO_TIMESTAMP('2022-07-06 17:15:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Picking Job -> Picking Job.Geschäftspartner
-- Column: M_Picking_Job.C_BPartner_ID
-- 2022-07-06T14:15:09.737Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,669035,0,544861,549453,610032,'F',TO_TIMESTAMP('2022-07-06 17:15:09','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','Y','Y','N','Geschäftspartner',30,30,0,TO_TIMESTAMP('2022-07-06 17:15:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-06T14:15:09.865Z
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,669036,0,541544,610032,TO_TIMESTAMP('2022-07-06 17:15:09','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-07-06 17:15:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Picking Job -> Picking Job.Lieferadresse
-- Column: M_Picking_Job.DeliveryToAddress
-- 2022-07-06T14:15:09.966Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,669037,0,544861,549453,610033,'F',TO_TIMESTAMP('2022-07-06 17:15:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Lieferadresse',40,40,0,TO_TIMESTAMP('2022-07-06 17:15:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Picking Job -> Picking Job.Bereitstellungsdatum
-- Column: M_Picking_Job.PreparationDate
-- 2022-07-06T14:15:10.061Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,669038,0,544861,549453,610034,'F',TO_TIMESTAMP('2022-07-06 17:15:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Bereitstellungsdatum',50,50,0,TO_TIMESTAMP('2022-07-06 17:15:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Picking Job -> Picking Job.Mitarbeiter
-- Column: M_Picking_Job.Picking_User_ID
-- 2022-07-06T14:15:10.151Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,669039,0,544861,549453,610035,'F',TO_TIMESTAMP('2022-07-06 17:15:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Mitarbeiter',60,60,0,TO_TIMESTAMP('2022-07-06 17:15:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Picking Job -> Picking Job.Picking Slot
-- Column: M_Picking_Job.M_PickingSlot_ID
-- 2022-07-06T14:15:10.240Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,669042,0,544861,549453,610036,'F',TO_TIMESTAMP('2022-07-06 17:15:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Picking Slot',70,70,0,TO_TIMESTAMP('2022-07-06 17:15:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Picking Job -> Picking Job.Belegstatus
-- Column: M_Picking_Job.DocStatus
-- 2022-07-06T14:15:10.329Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,669040,0,544861,549453,610037,'F',TO_TIMESTAMP('2022-07-06 17:15:10','YYYY-MM-DD HH24:MI:SS'),100,'The current status of the document','The Document Status indicates the status of a document at this time.  If you want to change the document status, use the Document Action field','Y','N','Y','Y','N','Belegstatus',80,80,0,TO_TIMESTAMP('2022-07-06 17:15:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-06T14:15:10.416Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,544862,545068,TO_TIMESTAMP('2022-07-06 17:15:10','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-07-06 17:15:10','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2022-07-06T14:15:10.417Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=545068 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2022-07-06T14:15:10.516Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546135,545068,TO_TIMESTAMP('2022-07-06 17:15:10','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-07-06 17:15:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-06T14:15:10.618Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546135,549454,TO_TIMESTAMP('2022-07-06 17:15:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2022-07-06 17:15:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Picking Job -> Picking Job Line.Produkt
-- Column: M_Picking_Job_Line.M_Product_ID
-- 2022-07-06T14:15:10.713Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,669048,0,544862,549454,610038,'F',TO_TIMESTAMP('2022-07-06 17:15:10','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','Y','N','Produkt',0,10,0,TO_TIMESTAMP('2022-07-06 17:15:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-06T14:15:10.810Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,544864,545069,TO_TIMESTAMP('2022-07-06 17:15:10','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-07-06 17:15:10','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2022-07-06T14:15:10.811Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=545069 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2022-07-06T14:15:10.905Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546136,545069,TO_TIMESTAMP('2022-07-06 17:15:10','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-07-06 17:15:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-06T14:15:11.005Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546136,549455,TO_TIMESTAMP('2022-07-06 17:15:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2022-07-06 17:15:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Picking Job -> Picking Job HU Alternative.Produkt
-- Column: M_Picking_Job_HUAlternative.M_Product_ID
-- 2022-07-06T14:15:11.088Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,669079,0,544864,549455,610039,'F',TO_TIMESTAMP('2022-07-06 17:15:11','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','Y','N','Produkt',0,10,0,TO_TIMESTAMP('2022-07-06 17:15:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Picking Job -> Picking Job HU Alternative.Maßeinheit
-- Column: M_Picking_Job_HUAlternative.C_UOM_ID
-- 2022-07-06T14:15:11.185Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,669078,0,544864,549455,610040,'F',TO_TIMESTAMP('2022-07-06 17:15:11','YYYY-MM-DD HH24:MI:SS'),100,'Maßeinheit','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','N','Y','N','Maßeinheit',0,20,0,TO_TIMESTAMP('2022-07-06 17:15:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Picking Job -> Picking Job HU Alternative.Available Quantity
-- Column: M_Picking_Job_HUAlternative.QtyAvailable
-- 2022-07-06T14:15:11.267Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,669077,0,544864,549455,610041,'F',TO_TIMESTAMP('2022-07-06 17:15:11','YYYY-MM-DD HH24:MI:SS'),100,'Available Quantity (On Hand - Reserved)','Quantity available to promise = On Hand minus Reserved Quantity','Y','N','N','Y','N','Available Quantity',0,30,0,TO_TIMESTAMP('2022-07-06 17:15:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Picking Job -> Picking Job HU Alternative.Pick From HU
-- Column: M_Picking_Job_HUAlternative.PickFrom_HU_ID
-- 2022-07-06T14:15:11.358Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,669076,0,544864,549455,610042,'F',TO_TIMESTAMP('2022-07-06 17:15:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Pick From HU',0,40,0,TO_TIMESTAMP('2022-07-06 17:15:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Picking Job -> Picking Job HU Alternative.Pick From Locator
-- Column: M_Picking_Job_HUAlternative.PickFrom_Locator_ID
-- 2022-07-06T14:15:11.447Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,669081,0,544864,549455,610043,'F',TO_TIMESTAMP('2022-07-06 17:15:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Pick From Locator',0,50,0,TO_TIMESTAMP('2022-07-06 17:15:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-06T14:16:04.373Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546134,549456,TO_TIMESTAMP('2022-07-06 17:16:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2022-07-06 17:16:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Picking Job -> Picking Job.Aktiv
-- Column: M_Picking_Job.IsActive
-- 2022-07-06T14:16:20.677Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,669032,0,544861,549456,610044,'F',TO_TIMESTAMP('2022-07-06 17:16:20','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2022-07-06 17:16:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Picking Job -> Picking Job.Kommissionierprüfung erforderlich
-- Column: M_Picking_Job.IsPickingReviewRequired
-- 2022-07-06T14:16:39.417Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,700829,0,544861,549456,610045,'F',TO_TIMESTAMP('2022-07-06 17:16:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Kommissionierprüfung erforderlich',20,0,0,TO_TIMESTAMP('2022-07-06 17:16:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Picking Job -> Picking Job.Bereit zur Überprüfung
-- Column: M_Picking_Job.IsReadyToReview
-- 2022-07-06T14:16:54.740Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,700831,0,544861,549456,610046,'F',TO_TIMESTAMP('2022-07-06 17:16:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Bereit zur Überprüfung',30,0,0,TO_TIMESTAMP('2022-07-06 17:16:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Picking Job -> Picking Job.Freigegeben
-- Column: M_Picking_Job.IsApproved
-- 2022-07-06T14:17:06.368Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,700830,0,544861,549456,610047,'F',TO_TIMESTAMP('2022-07-06 17:17:06','YYYY-MM-DD HH24:MI:SS'),100,'Zeigt an, ob dieser Beleg eine Freigabe braucht','Das Selektionsfeld "Freigabe" zeigt an, dass dieser Beleg eine Freigabe braucht, bevor er verarbeitet werden kann','Y','N','N','Y','N','N','N',0,'Freigegeben',40,0,0,TO_TIMESTAMP('2022-07-06 17:17:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-06T14:17:14.386Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546134,549457,TO_TIMESTAMP('2022-07-06 17:17:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','orgs',20,TO_TIMESTAMP('2022-07-06 17:17:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Picking Job -> Picking Job.Sektion
-- Column: M_Picking_Job.AD_Org_ID
-- 2022-07-06T14:17:32.245Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,669031,0,544861,549457,610048,'F',TO_TIMESTAMP('2022-07-06 17:17:32','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',10,0,0,TO_TIMESTAMP('2022-07-06 17:17:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Picking Job -> Picking Job.Mandant
-- Column: M_Picking_Job.AD_Client_ID
-- 2022-07-06T14:17:43.054Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,669030,0,544861,549457,610049,'F',TO_TIMESTAMP('2022-07-06 17:17:42','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2022-07-06 17:17:42','YYYY-MM-DD HH24:MI:SS'),100)
;





-- 2022-07-08T08:14:33.717Z
UPDATE AD_Table_Process SET WEBUI_ViewQuickAction_Default='Y',Updated=TO_TIMESTAMP('2022-07-08 11:14:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_Process_ID=541117
;

-- UI Element: Picking Job -> Picking Job.Kommissionierprüfung erforderlich
-- Column: M_Picking_Job.IsPickingReviewRequired
-- 2022-07-08T08:16:18.847Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2022-07-08 11:16:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=610045
;

-- UI Element: Picking Job -> Picking Job.Bereit zur Überprüfung
-- Column: M_Picking_Job.IsReadyToReview
-- 2022-07-08T08:16:18.853Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2022-07-08 11:16:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=610046
;

-- UI Element: Picking Job -> Picking Job.Belegstatus
-- Column: M_Picking_Job.DocStatus
-- 2022-07-08T08:16:18.858Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2022-07-08 11:16:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=610037
;

-- UI Element: Picking Job -> Picking Job.Freigegeben
-- Column: M_Picking_Job.IsApproved
-- 2022-07-08T08:48:21.129Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2022-07-08 11:48:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=610047
;

-- UI Element: Picking Job -> Picking Job.Belegstatus
-- Column: M_Picking_Job.DocStatus
-- 2022-07-08T08:48:21.134Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2022-07-08 11:48:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=610037
;

