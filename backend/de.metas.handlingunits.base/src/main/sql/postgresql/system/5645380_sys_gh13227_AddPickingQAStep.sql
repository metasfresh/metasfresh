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



