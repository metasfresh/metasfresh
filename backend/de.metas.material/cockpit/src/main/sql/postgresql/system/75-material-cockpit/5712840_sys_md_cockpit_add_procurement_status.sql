-- Name: Beschaffungsstatus
-- 2023-12-06T13:21:11.228Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541842,TO_TIMESTAMP('2023-12-06 14:21:10','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.cockpit','Y','N','Beschaffungsstatus',TO_TIMESTAMP('2023-12-06 14:21:10','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2023-12-06T13:21:11.246Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541842 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2023-12-06T13:21:21.583Z
UPDATE AD_Reference_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-12-06 14:21:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Reference_ID=541842
;

-- 2023-12-06T13:21:48.320Z
UPDATE AD_Reference_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-12-06 14:21:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Reference_ID=541842
;

-- 2023-12-06T13:22:18.990Z
UPDATE AD_Reference_Trl SET IsTranslated='Y', Name='Procurement Status',Updated=TO_TIMESTAMP('2023-12-06 14:22:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Reference_ID=541842
;

-- Reference: Beschaffungsstatus
-- Value: low
-- ValueName: low
-- 2023-12-06T13:25:35.637Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Color_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,1000000,0,541842,543598,TO_TIMESTAMP('2023-12-06 14:25:35','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.cockpit','Y','Niedrig',TO_TIMESTAMP('2023-12-06 14:25:35','YYYY-MM-DD HH24:MI:SS'),100,'low','low')
;

-- 2023-12-06T13:25:35.640Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543598 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: Beschaffungsstatus -> low_low
-- 2023-12-06T13:25:43.078Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-12-06 14:25:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543598
;

-- Reference Item: Beschaffungsstatus -> low_low
-- 2023-12-06T13:25:43.866Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-12-06 14:25:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543598
;

-- Reference Item: Beschaffungsstatus -> low_low
-- 2023-12-06T13:25:55.521Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Low',Updated=TO_TIMESTAMP('2023-12-06 14:25:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543598
;

-- Reference: Beschaffungsstatus
-- Value: medium
-- ValueName: medium
-- 2023-12-06T13:26:31.607Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Color_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,1000003,0,541842,543599,TO_TIMESTAMP('2023-12-06 14:26:31','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.cockpit','Y','Mittel',TO_TIMESTAMP('2023-12-06 14:26:31','YYYY-MM-DD HH24:MI:SS'),100,'medium','medium')
;

-- 2023-12-06T13:26:31.609Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543599 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: Beschaffungsstatus -> medium_medium
-- 2023-12-06T13:26:38.264Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-12-06 14:26:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543599
;

-- Reference Item: Beschaffungsstatus -> medium_medium
-- 2023-12-06T13:26:39.262Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-12-06 14:26:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543599
;

-- Reference Item: Beschaffungsstatus -> medium_medium
-- 2023-12-06T13:26:48.699Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Medium',Updated=TO_TIMESTAMP('2023-12-06 14:26:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543599
;

-- Reference: Beschaffungsstatus
-- Value: high
-- ValueName: high
-- 2023-12-06T13:29:08.888Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Color_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,1000001,0,541842,543600,TO_TIMESTAMP('2023-12-06 14:29:08','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.cockpit','Y','Hoch',TO_TIMESTAMP('2023-12-06 14:29:08','YYYY-MM-DD HH24:MI:SS'),100,'high','high')
;

-- 2023-12-06T13:29:08.889Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543600 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: Beschaffungsstatus -> high_high
-- 2023-12-06T13:29:13.633Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-12-06 14:29:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543600
;

-- Reference Item: Beschaffungsstatus -> high_high
-- 2023-12-06T13:29:14.269Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-12-06 14:29:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543600
;

-- Reference Item: Beschaffungsstatus -> high_high
-- 2023-12-06T13:29:24.192Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='High',Updated=TO_TIMESTAMP('2023-12-06 14:29:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543600
;

-- 2023-12-06T13:35:43.609Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582840,0,'ProcurementStatus',TO_TIMESTAMP('2023-12-06 14:35:43','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.cockpit','Y','Beschaffungsstatus','Beschaffungsstatus',TO_TIMESTAMP('2023-12-06 14:35:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-06T13:35:43.631Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582840 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: ProcurementStatus
-- 2023-12-06T13:35:49.826Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-12-06 14:35:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582840 AND AD_Language='de_DE'
;

-- 2023-12-06T13:35:49.868Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582840,'de_DE') 
;

-- 2023-12-06T13:35:49.910Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582840,'de_DE') 
;

-- Element: ProcurementStatus
-- 2023-12-06T13:35:50.608Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-12-06 14:35:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582840 AND AD_Language='de_CH'
;

-- 2023-12-06T13:35:50.610Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582840,'de_CH') 
;

-- Element: ProcurementStatus
-- 2023-12-06T13:36:46.473Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Procurement Status', PrintName='Procurement Status',Updated=TO_TIMESTAMP('2023-12-06 14:36:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582840 AND AD_Language='en_US'
;

-- 2023-12-06T13:36:46.475Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582840,'en_US') 
;
-- Column: MD_Cockpit.ProcurementStatus
-- 2023-12-06T13:43:54.565Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587689,582840,0,17,541842,540863,'ProcurementStatus',TO_TIMESTAMP('2023-12-06 14:43:54','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.material.cockpit',0,60,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Beschaffungsstatus',0,0,TO_TIMESTAMP('2023-12-06 14:43:54','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-12-06T13:43:54.572Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587689 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-06T13:43:54.577Z
/* DDL */  select update_Column_Translation_From_AD_Element(582840) 
;

-- 2023-12-06T13:43:56.985Z
/* DDL */ SELECT public.db_alter_table('MD_Cockpit','ALTER TABLE public.MD_Cockpit ADD COLUMN ProcurementStatus VARCHAR(60)')
;

-- Value: de.metas.ui.web.material.cockpit.process.MD_Cockpit_SetProcurementStatus
-- Classname: de.metas.ui.web.material.cockpit.process.MD_Cockpit_SetProcurementStatus
-- 2023-12-07T10:51:06.690Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585342,'Y','de.metas.ui.web.material.cockpit.process.MD_Cockpit_SetProcurementStatus','N',TO_TIMESTAMP('2023-12-07 11:51:06','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.cockpit','Y','N','N','N','Y','N','N','N','N','Y','Y',0,'Beschaffungsstatus setzen','json','N','N','xls','Java',TO_TIMESTAMP('2023-12-07 11:51:06','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web.material.cockpit.process.MD_Cockpit_SetProcurementStatus')
;

-- 2023-12-07T10:51:06.700Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585342 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: de.metas.ui.web.material.cockpit.process.MD_Cockpit_SetProcurementStatus(de.metas.ui.web.material.cockpit.process.MD_Cockpit_SetProcurementStatus)
-- 2023-12-07T10:51:13.348Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-12-07 11:51:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585342
;

-- Process: de.metas.ui.web.material.cockpit.process.MD_Cockpit_SetProcurementStatus(de.metas.ui.web.material.cockpit.process.MD_Cockpit_SetProcurementStatus)
-- 2023-12-07T10:51:14.119Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-12-07 11:51:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585342
;

-- Process: de.metas.ui.web.material.cockpit.process.MD_Cockpit_SetProcurementStatus(de.metas.ui.web.material.cockpit.process.MD_Cockpit_SetProcurementStatus)
-- 2023-12-07T10:51:27.081Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Set Procurement Status',Updated=TO_TIMESTAMP('2023-12-07 11:51:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585342
;

-- Process: de.metas.ui.web.material.cockpit.process.MD_Cockpit_SetProcurementStatus(de.metas.ui.web.material.cockpit.process.MD_Cockpit_SetProcurementStatus)
-- ParameterName: ProcurementStatus
-- 2023-12-07T11:30:32.756Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,582840,0,585342,542761,17,541842,'ProcurementStatus',TO_TIMESTAMP('2023-12-07 12:30:32','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.cockpit',0,'Y','N','Y','N','N','N','Beschaffungsstatus',10,TO_TIMESTAMP('2023-12-07 12:30:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-07T11:30:32.764Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542761 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

