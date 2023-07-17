-- 2022-11-28T10:41:59.125Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581751,0,'IsEnforceIssuingTolerance',TO_TIMESTAMP('2022-11-28 12:41:58','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Toleranz erzw.','Toleranz erzw.',TO_TIMESTAMP('2022-11-28 12:41:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T10:41:59.131Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581751 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsEnforceIssuingTolerance
-- 2022-11-28T10:42:25.713Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Enforce Issuing Tolerance', PrintName='Enforce Issuing Tolerance',Updated=TO_TIMESTAMP('2022-11-28 12:42:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581751 AND AD_Language='en_US'
;

-- 2022-11-28T10:42:25.745Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581751,'en_US') 
;

-- Element: IsEnforceIssuingTolerance
-- 2022-11-28T10:42:29.321Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-11-28 12:42:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581751 AND AD_Language='de_DE'
;

-- 2022-11-28T10:42:29.323Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581751,'de_DE') 
;

-- 2022-11-28T10:42:29.329Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581751,'de_DE') 
;

-- Element: IsEnforceIssuingTolerance
-- 2022-11-28T10:42:30.698Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-11-28 12:42:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581751 AND AD_Language='de_CH'
;

-- 2022-11-28T10:42:30.700Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581751,'de_CH') 
;

-- 2022-11-28T10:45:04.375Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581752,0,'ToleranceValueType',TO_TIMESTAMP('2022-11-28 12:45:04','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Tolerance Value Type','Tolerance Value Type',TO_TIMESTAMP('2022-11-28 12:45:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T10:45:04.377Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581752 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-11-28T10:55:20.111Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581753,0,'Tolerance_InKg',TO_TIMESTAMP('2022-11-28 12:55:19','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Tollerance (in Kilograms)','Tollerance (in Kilograms)',TO_TIMESTAMP('2022-11-28 12:55:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T10:55:20.112Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581753 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-11-28T10:55:29.951Z
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=581753
;

-- 2022-11-28T10:55:29.961Z
DELETE FROM AD_Element WHERE AD_Element_ID=581753
;

-- 2022-11-28T10:55:55.818Z
UPDATE AD_Element SET ColumnName='IssueToleranceValueType',Updated=TO_TIMESTAMP('2022-11-28 12:55:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581752
;

-- 2022-11-28T10:55:55.822Z
UPDATE AD_Column SET ColumnName='IssueToleranceValueType', Name='Tolerance Value Type', Description=NULL, Help=NULL WHERE AD_Element_ID=581752
;

-- 2022-11-28T10:55:55.823Z
UPDATE AD_Process_Para SET ColumnName='IssueToleranceValueType', Name='Tolerance Value Type', Description=NULL, Help=NULL, AD_Element_ID=581752 WHERE UPPER(ColumnName)='ISSUETOLERANCEVALUETYPE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-11-28T10:55:55.826Z
UPDATE AD_Process_Para SET ColumnName='IssueToleranceValueType', Name='Tolerance Value Type', Description=NULL, Help=NULL WHERE AD_Element_ID=581752 AND IsCentrallyMaintained='Y'
;

-- 2022-11-28T10:57:36.268Z
UPDATE AD_Element SET ColumnName='IssuingTolerance_ValueType',Updated=TO_TIMESTAMP('2022-11-28 12:57:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581752
;

-- 2022-11-28T10:57:36.271Z
UPDATE AD_Column SET ColumnName='IssuingTolerance_ValueType', Name='Tolerance Value Type', Description=NULL, Help=NULL WHERE AD_Element_ID=581752
;

-- 2022-11-28T10:57:36.272Z
UPDATE AD_Process_Para SET ColumnName='IssuingTolerance_ValueType', Name='Tolerance Value Type', Description=NULL, Help=NULL, AD_Element_ID=581752 WHERE UPPER(ColumnName)='ISSUINGTOLERANCE_VALUETYPE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-11-28T10:57:36.273Z
UPDATE AD_Process_Para SET ColumnName='IssuingTolerance_ValueType', Name='Tolerance Value Type', Description=NULL, Help=NULL WHERE AD_Element_ID=581752 AND IsCentrallyMaintained='Y'
;

-- 2022-11-28T10:58:27.522Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581754,0,'IssuingTolerance_Perc',TO_TIMESTAMP('2022-11-28 12:58:27','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Toleranz %','Toleranz %',TO_TIMESTAMP('2022-11-28 12:58:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T10:58:27.524Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581754 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IssuingTolerance_Perc
-- 2022-11-28T10:58:42.301Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Tolerance %', PrintName='Tolerance %',Updated=TO_TIMESTAMP('2022-11-28 12:58:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581754 AND AD_Language='en_US'
;

-- 2022-11-28T10:58:42.303Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581754,'en_US') 
;

-- Element: IssuingTolerance_Perc
-- 2022-11-28T10:58:47.344Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-11-28 12:58:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581754 AND AD_Language='de_DE'
;

-- 2022-11-28T10:58:47.346Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581754,'de_DE') 
;

-- 2022-11-28T10:58:47.357Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581754,'de_DE') 
;

-- Element: IssuingTolerance_Perc
-- 2022-11-28T10:58:48.958Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-11-28 12:58:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581754 AND AD_Language='de_CH'
;

-- 2022-11-28T10:58:48.960Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581754,'de_CH') 
;

-- 2022-11-28T11:03:39.499Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581756,0,'IssuingTolerance_Qty',TO_TIMESTAMP('2022-11-28 13:03:39','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Toleranz','Toleranz',TO_TIMESTAMP('2022-11-28 13:03:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T11:03:39.501Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581756 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IssuingTolerance_Qty
-- 2022-11-28T11:03:42.534Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-11-28 13:03:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581756 AND AD_Language='de_CH'
;

-- 2022-11-28T11:03:42.535Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581756,'de_CH') 
;

-- Element: IssuingTolerance_Qty
-- 2022-11-28T11:03:43.625Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-11-28 13:03:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581756 AND AD_Language='de_DE'
;

-- 2022-11-28T11:03:43.626Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581756,'de_DE') 
;

-- 2022-11-28T11:03:43.632Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581756,'de_DE') 
;

-- Element: IssuingTolerance_Qty
-- 2022-11-28T11:03:50.943Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Tolerance', PrintName='Tolerance',Updated=TO_TIMESTAMP('2022-11-28 13:03:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581756 AND AD_Language='en_US'
;

-- 2022-11-28T11:03:50.945Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581756,'en_US') 
;

-- 2022-11-28T11:06:17.270Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581757,0,'IssuingTolerance_UOM_ID',TO_TIMESTAMP('2022-11-28 13:06:17','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Toleranz Einheiten','Toleranz Einheiten',TO_TIMESTAMP('2022-11-28 13:06:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T11:06:17.272Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581757 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IssuingTolerance_UOM_ID
-- 2022-11-28T11:06:22.025Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-11-28 13:06:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581757 AND AD_Language='de_CH'
;

-- 2022-11-28T11:06:22.026Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581757,'de_CH') 
;

-- Element: IssuingTolerance_UOM_ID
-- 2022-11-28T11:06:23.114Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-11-28 13:06:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581757 AND AD_Language='de_DE'
;

-- 2022-11-28T11:06:23.115Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581757,'de_DE') 
;

-- 2022-11-28T11:06:23.120Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581757,'de_DE') 
;

-- Element: IssuingTolerance_UOM_ID
-- 2022-11-28T11:06:31.695Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Tolerance UOM', PrintName='Tolerance UOM',Updated=TO_TIMESTAMP('2022-11-28 13:06:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581757 AND AD_Language='en_US'
;

-- 2022-11-28T11:06:31.696Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581757,'en_US') 
;

-- Column: M_Product.IsEnforceIssuingTolerance
-- 2022-11-28T11:08:35.991Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585143,581751,0,20,208,'IsEnforceIssuingTolerance',TO_TIMESTAMP('2022-11-28 13:08:35','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Toleranz erzw.',0,0,TO_TIMESTAMP('2022-11-28 13:08:35','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-28T11:08:35.993Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585143 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-28T11:08:36Z
/* DDL */  select update_Column_Translation_From_AD_Element(581751) 
;

-- 2022-11-28T11:08:36.871Z
/* DDL */ SELECT public.db_alter_table('M_Product','ALTER TABLE public.M_Product ADD COLUMN IsEnforceIssuingTolerance CHAR(1) DEFAULT ''N'' CHECK (IsEnforceIssuingTolerance IN (''Y'',''N'')) NOT NULL')
;

-- Name: IssuingTolerance_ValueType
-- 2022-11-28T11:09:14.106Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541693,TO_TIMESTAMP('2022-11-28 13:09:13','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','IssuingTolerance_ValueType',TO_TIMESTAMP('2022-11-28 13:09:13','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2022-11-28T11:09:14.109Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541693 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: IssuingTolerance_ValueType
-- Value: P
-- ValueName: Percentage
-- 2022-11-28T11:10:44.551Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541693,543353,TO_TIMESTAMP('2022-11-28 13:10:44','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Prozent',TO_TIMESTAMP('2022-11-28 13:10:44','YYYY-MM-DD HH24:MI:SS'),100,'P','Percentage')
;

-- 2022-11-28T11:10:44.554Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543353 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: IssuingTolerance_ValueType -> P_Percentage
-- 2022-11-28T11:10:48.122Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-11-28 13:10:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543353
;

-- Reference Item: IssuingTolerance_ValueType -> P_Percentage
-- 2022-11-28T11:10:49.126Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-11-28 13:10:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543353
;

-- Reference Item: IssuingTolerance_ValueType -> P_Percentage
-- 2022-11-28T11:10:55.083Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Percentage',Updated=TO_TIMESTAMP('2022-11-28 13:10:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543353
;

-- Reference: IssuingTolerance_ValueType
-- Value: Q
-- ValueName: Quantity
-- 2022-11-28T11:11:08.663Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541693,543354,TO_TIMESTAMP('2022-11-28 13:11:08','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Menge',TO_TIMESTAMP('2022-11-28 13:11:08','YYYY-MM-DD HH24:MI:SS'),100,'Q','Quantity')
;

-- 2022-11-28T11:11:08.665Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543354 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: IssuingTolerance_ValueType
-- Value: Q
-- ValueName: Quantity
-- 2022-11-28T11:11:10.416Z
UPDATE AD_Ref_List SET EntityType='D',Updated=TO_TIMESTAMP('2022-11-28 13:11:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543354
;

-- Reference Item: IssuingTolerance_ValueType -> Q_Quantity
-- 2022-11-28T11:11:16.155Z
UPDATE AD_Ref_List_Trl SET Name='Quantity',Updated=TO_TIMESTAMP('2022-11-28 13:11:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543354
;

-- Reference Item: IssuingTolerance_ValueType -> Q_Quantity
-- 2022-11-28T11:11:17.527Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-11-28 13:11:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543354
;

-- Reference Item: IssuingTolerance_ValueType -> Q_Quantity
-- 2022-11-28T11:11:18.550Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-11-28 13:11:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543354
;

-- Reference Item: IssuingTolerance_ValueType -> Q_Quantity
-- 2022-11-28T11:11:19.818Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-11-28 13:11:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543354
;

-- Column: M_Product.IssuingTolerance_ValueType
-- 2022-11-28T11:12:05.212Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585144,581752,0,17,541693,208,'IssuingTolerance_ValueType',TO_TIMESTAMP('2022-11-28 13:12:05','YYYY-MM-DD HH24:MI:SS'),100,'N','','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Tolerance Value Type',0,0,TO_TIMESTAMP('2022-11-28 13:12:05','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-28T11:12:05.214Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585144 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-28T11:12:05.219Z
/* DDL */  select update_Column_Translation_From_AD_Element(581752) 
;

-- 2022-11-28T11:12:05.789Z
/* DDL */ SELECT public.db_alter_table('M_Product','ALTER TABLE public.M_Product ADD COLUMN IssuingTolerance_ValueType CHAR(1)')
;

-- Column: M_Product.IssuingTolerance_Perc
-- 2022-11-28T11:12:34.326Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585145,581754,0,22,208,'IssuingTolerance_Perc',TO_TIMESTAMP('2022-11-28 13:12:34','YYYY-MM-DD HH24:MI:SS'),100,'N','','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Toleranz %',0,0,TO_TIMESTAMP('2022-11-28 13:12:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-28T11:12:34.328Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585145 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-28T11:12:34.331Z
/* DDL */  select update_Column_Translation_From_AD_Element(581754) 
;

-- 2022-11-28T11:12:36.901Z
/* DDL */ SELECT public.db_alter_table('M_Product','ALTER TABLE public.M_Product ADD COLUMN IssuingTolerance_Perc NUMERIC')
;

-- Column: M_Product.IssuingTolerance_Qty
-- 2022-11-28T11:41:39.214Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585146,581756,0,29,208,'IssuingTolerance_Qty',TO_TIMESTAMP('2022-11-28 13:41:39','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Toleranz',0,0,TO_TIMESTAMP('2022-11-28 13:41:39','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-28T11:41:39.218Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585146 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-28T11:41:39.225Z
/* DDL */  select update_Column_Translation_From_AD_Element(581756) 
;

-- 2022-11-28T11:41:39.968Z
/* DDL */ SELECT public.db_alter_table('M_Product','ALTER TABLE public.M_Product ADD COLUMN IssuingTolerance_Qty NUMERIC')
;

-- Column: M_Product.IssuingTolerance_UOM_ID
-- 2022-11-28T11:41:57.131Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585147,581757,0,30,208,'IssuingTolerance_UOM_ID',TO_TIMESTAMP('2022-11-28 13:41:56','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Toleranz Einheiten',0,0,TO_TIMESTAMP('2022-11-28 13:41:56','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-28T11:41:57.134Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585147 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-28T11:41:57.138Z
/* DDL */  select update_Column_Translation_From_AD_Element(581757) 
;

-- Column: M_Product.IssuingTolerance_UOM_ID
-- 2022-11-28T11:42:17.365Z
UPDATE AD_Column SET AD_Reference_Value_ID=114, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2022-11-28 13:42:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585147
;

-- 2022-11-28T11:42:18.950Z
/* DDL */ SELECT public.db_alter_table('M_Product','ALTER TABLE public.M_Product ADD COLUMN IssuingTolerance_UOM_ID NUMERIC(10)')
;

-- 2022-11-28T11:42:20.167Z
ALTER TABLE M_Product ADD CONSTRAINT IssuingToleranceUOM_MProduct FOREIGN KEY (IssuingTolerance_UOM_ID) REFERENCES public.C_UOM DEFERRABLE INITIALLY DEFERRED
;

-- UI Section: Produkt_OLD(140,D) -> Produkt(180,D) -> advanced edit
-- UI Column: 20
-- 2022-11-28T13:25:17.394Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546468,1000022,TO_TIMESTAMP('2022-11-28 15:25:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2022-11-28 15:25:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Produkt_OLD(140,D) -> Produkt(180,D) -> advanced edit -> 20
-- UI Element Group: Issuing tolerance
-- 2022-11-28T13:25:30.293Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546468,550055,TO_TIMESTAMP('2022-11-28 15:25:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','Issuing tolerance',10,TO_TIMESTAMP('2022-11-28 15:25:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Produkt_OLD(140,D) -> Produkt(180,D) -> Toleranz erzw.
-- Column: M_Product.IsEnforceIssuingTolerance
-- 2022-11-28T13:26:20.565Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585143,708220,0,180,TO_TIMESTAMP('2022-11-28 15:26:20','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Toleranz erzw.',TO_TIMESTAMP('2022-11-28 15:26:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T13:26:20.568Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708220 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-28T13:26:20.572Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581751) 
;

-- 2022-11-28T13:26:20.585Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708220
;

-- 2022-11-28T13:26:20.587Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708220)
;

-- Field: Produkt_OLD(140,D) -> Produkt(180,D) -> Tolerance Value Type
-- Column: M_Product.IssuingTolerance_ValueType
-- 2022-11-28T13:26:32.551Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585144,708221,0,180,TO_TIMESTAMP('2022-11-28 15:26:32','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Tolerance Value Type',TO_TIMESTAMP('2022-11-28 15:26:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T13:26:32.554Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708221 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-28T13:26:32.555Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581752) 
;

-- 2022-11-28T13:26:32.558Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708221
;

-- 2022-11-28T13:26:32.559Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708221)
;

-- Field: Produkt_OLD(140,D) -> Produkt(180,D) -> Toleranz %
-- Column: M_Product.IssuingTolerance_Perc
-- 2022-11-28T13:26:50.308Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585145,708222,0,180,TO_TIMESTAMP('2022-11-28 15:26:50','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Toleranz %',TO_TIMESTAMP('2022-11-28 15:26:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T13:26:50.309Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708222 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-28T13:26:50.311Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581754) 
;

-- 2022-11-28T13:26:50.313Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708222
;

-- 2022-11-28T13:26:50.314Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708222)
;

-- Field: Produkt_OLD(140,D) -> Produkt(180,D) -> Toleranz
-- Column: M_Product.IssuingTolerance_Qty
-- 2022-11-28T13:26:50.423Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585146,708223,0,180,TO_TIMESTAMP('2022-11-28 15:26:50','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Toleranz',TO_TIMESTAMP('2022-11-28 15:26:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T13:26:50.424Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708223 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-28T13:26:50.426Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581756) 
;

-- 2022-11-28T13:26:50.428Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708223
;

-- 2022-11-28T13:26:50.428Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708223)
;

-- Field: Produkt_OLD(140,D) -> Produkt(180,D) -> Toleranz Einheiten
-- Column: M_Product.IssuingTolerance_UOM_ID
-- 2022-11-28T13:26:50.543Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585147,708224,0,180,TO_TIMESTAMP('2022-11-28 15:26:50','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Toleranz Einheiten',TO_TIMESTAMP('2022-11-28 15:26:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T13:26:50.544Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708224 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-28T13:26:50.546Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581757) 
;

-- 2022-11-28T13:26:50.548Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708224
;

-- 2022-11-28T13:26:50.549Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708224)
;

-- UI Element: Produkt_OLD(140,D) -> Produkt(180,D) -> advanced edit -> 20 -> Issuing tolerance.Toleranz erzw.
-- Column: M_Product.IsEnforceIssuingTolerance
-- 2022-11-28T13:27:46.594Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708220,0,180,550055,613596,'F',TO_TIMESTAMP('2022-11-28 15:27:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Toleranz erzw.',10,0,0,TO_TIMESTAMP('2022-11-28 15:27:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Produkt_OLD(140,D) -> Produkt(180,D) -> advanced edit -> 20 -> Issuing tolerance.Tolerance Value Type
-- Column: M_Product.IssuingTolerance_ValueType
-- 2022-11-28T13:28:00.661Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708221,0,180,550055,613597,'F',TO_TIMESTAMP('2022-11-28 15:28:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Tolerance Value Type',20,0,0,TO_TIMESTAMP('2022-11-28 15:28:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Produkt_OLD(140,D) -> Produkt(180,D) -> advanced edit -> 20 -> Issuing tolerance.Toleranz %
-- Column: M_Product.IssuingTolerance_Perc
-- 2022-11-28T13:28:15.675Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708222,0,180,550055,613598,'F',TO_TIMESTAMP('2022-11-28 15:28:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Toleranz %',30,0,0,TO_TIMESTAMP('2022-11-28 15:28:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Produkt_OLD(140,D) -> Produkt(180,D) -> advanced edit -> 20 -> Issuing tolerance.Toleranz
-- Column: M_Product.IssuingTolerance_Qty
-- 2022-11-28T13:28:31.145Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708223,0,180,550055,613599,'F',TO_TIMESTAMP('2022-11-28 15:28:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Toleranz',40,0,0,TO_TIMESTAMP('2022-11-28 15:28:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Produkt_OLD(140,D) -> Produkt(180,D) -> advanced edit -> 20 -> Issuing tolerance.Toleranz Einheiten
-- Column: M_Product.IssuingTolerance_UOM_ID
-- 2022-11-28T13:28:43.467Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708224,0,180,550055,613600,'F',TO_TIMESTAMP('2022-11-28 15:28:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Toleranz Einheiten',50,0,0,TO_TIMESTAMP('2022-11-28 15:28:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Produkt_OLD(140,D) -> Produkt(180,D) -> advanced edit -> 20 -> Issuing tolerance.Toleranz erzw.
-- Column: M_Product.IsEnforceIssuingTolerance
-- 2022-11-28T13:28:54.992Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2022-11-28 15:28:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613596
;

-- UI Element: Produkt_OLD(140,D) -> Produkt(180,D) -> advanced edit -> 20 -> Issuing tolerance.Tolerance Value Type
-- Column: M_Product.IssuingTolerance_ValueType
-- 2022-11-28T13:28:55.896Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2022-11-28 15:28:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613597
;

-- UI Element: Produkt_OLD(140,D) -> Produkt(180,D) -> advanced edit -> 20 -> Issuing tolerance.Toleranz %
-- Column: M_Product.IssuingTolerance_Perc
-- 2022-11-28T13:28:56.900Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2022-11-28 15:28:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613598
;

-- UI Element: Produkt_OLD(140,D) -> Produkt(180,D) -> advanced edit -> 20 -> Issuing tolerance.Toleranz
-- Column: M_Product.IssuingTolerance_Qty
-- 2022-11-28T13:28:57.871Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2022-11-28 15:28:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613599
;

-- UI Element: Produkt_OLD(140,D) -> Produkt(180,D) -> advanced edit -> 20 -> Issuing tolerance.Toleranz Einheiten
-- Column: M_Product.IssuingTolerance_UOM_ID
-- 2022-11-28T13:29:00.350Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2022-11-28 15:29:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613600
;

-- Field: Produkt_OLD(140,D) -> Produkt(180,D) -> Tolerance Value Type
-- Column: M_Product.IssuingTolerance_ValueType
-- 2022-11-28T13:31:00.245Z
UPDATE AD_Field SET DisplayLogic='@IsEnforceIssuingTolerance/X@=Y',Updated=TO_TIMESTAMP('2022-11-28 15:31:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708221
;

-- Field: Produkt_OLD(140,D) -> Produkt(180,D) -> Toleranz %
-- Column: M_Product.IssuingTolerance_Perc
-- 2022-11-28T13:32:06.586Z
UPDATE AD_Field SET DisplayLogic='@IsEnforceIssuingTolerance/X@=Y & @IssuingTolerance_ValueType/X@=P',Updated=TO_TIMESTAMP('2022-11-28 15:32:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708222
;

-- Field: Produkt_OLD(140,D) -> Produkt(180,D) -> Toleranz
-- Column: M_Product.IssuingTolerance_Qty
-- 2022-11-28T13:32:13.686Z
UPDATE AD_Field SET DisplayLogic='@IsEnforceIssuingTolerance/X@=Y & @IssuingTolerance_ValueType/X@=Q',Updated=TO_TIMESTAMP('2022-11-28 15:32:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708223
;

-- Field: Produkt_OLD(140,D) -> Produkt(180,D) -> Toleranz Einheiten
-- Column: M_Product.IssuingTolerance_UOM_ID
-- 2022-11-28T13:32:22.819Z
UPDATE AD_Field SET DisplayLogic='@IsEnforceIssuingTolerance/X@=Y & @IssuingTolerance_ValueType/X@=P',Updated=TO_TIMESTAMP('2022-11-28 15:32:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708224
;

-- Field: Produkt_OLD(140,D) -> Produkt(180,D) -> Toleranz Einheiten
-- Column: M_Product.IssuingTolerance_UOM_ID
-- 2022-11-28T13:32:26.791Z
UPDATE AD_Field SET DisplayLogic='@IsEnforceIssuingTolerance/X@=Y & @IssuingTolerance_ValueType/X@=Q',Updated=TO_TIMESTAMP('2022-11-28 15:32:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708224
;

-- Column: M_Product.IssuingTolerance_Perc
-- 2022-11-28T13:32:42.124Z
UPDATE AD_Column SET MandatoryLogic='@IsEnforceIssuingTolerance/X@=Y & @IssuingTolerance_ValueType/X@=P',Updated=TO_TIMESTAMP('2022-11-28 15:32:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585145
;

-- Column: M_Product.IssuingTolerance_Qty
-- 2022-11-28T13:32:47.848Z
UPDATE AD_Column SET MandatoryLogic='@IsEnforceIssuingTolerance/X@=Y & @IssuingTolerance_ValueType/X@=Q',Updated=TO_TIMESTAMP('2022-11-28 15:32:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585146
;

-- Column: M_Product.IssuingTolerance_UOM_ID
-- 2022-11-28T13:32:52.744Z
UPDATE AD_Column SET MandatoryLogic='@IsEnforceIssuingTolerance/X@=Y & @IssuingTolerance_ValueType/X@=Q',Updated=TO_TIMESTAMP('2022-11-28 15:32:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585147
;

-- Column: M_Product.IssuingTolerance_ValueType
-- 2022-11-28T13:32:59.277Z
UPDATE AD_Column SET MandatoryLogic='@IsEnforceIssuingTolerance/X@=Y',Updated=TO_TIMESTAMP('2022-11-28 15:32:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585144
;

