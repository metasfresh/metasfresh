-- 2023-12-21T10:55:36.122Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582876,0,'IsVoided',TO_TIMESTAMP('2023-12-21 11:55:35.923','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Storniert','Storniert',TO_TIMESTAMP('2023-12-21 11:55:35.923','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-12-21T10:55:36.133Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582876 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: PP_Order.IsVoided
-- Column SQL (old): null
-- 2023-12-21T11:00:28.044Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,FilterDefaultValue,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587764,582876,0,20,53027,'IsVoided','(CASE WHEN PP_Order.DocStatus = ''VO'' THEN ''Y'' ELSE ''N'' END)',TO_TIMESTAMP('2023-12-21 12:00:27.852','YYYY-MM-DD HH24:MI:SS.US'),100,'N','N','EE01',0,1,'N','E','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','N','N','Y','N','N','N','N','N','N','N',0,'Storniert',0,0,TO_TIMESTAMP('2023-12-21 12:00:27.852','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-12-21T11:00:28.046Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587764 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-21T11:00:28.063Z
/* DDL */  select update_Column_Translation_From_AD_Element(582876) 
;

-- 2023-12-21T11:01:19.086Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Voided', PrintName='Voided',Updated=TO_TIMESTAMP('2023-12-21 12:01:19.084','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582876 AND AD_Language='en_US'
;

-- 2023-12-21T11:01:19.090Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582876,'en_US') 
;

-- Column: PP_Order.IsVoided
-- 2023-12-21T11:45:42.443Z
UPDATE AD_Column SET FilterDefaultValue='N', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-12-21 12:45:42.441','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587764
;

-- Column: PP_Order.ExportStatus
-- 2023-12-21T11:02:40.704Z
UPDATE AD_Column SET FilterDefaultValue='',Updated=TO_TIMESTAMP('2023-12-21 12:02:40.702','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=571143
;

-- 2023-12-21T12:36:17.448Z
UPDATE AD_Element_Trl SET Description='Check if the status of the Manufacturing Order is voided on not',Updated=TO_TIMESTAMP('2023-12-21 13:36:17.248','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582876 AND AD_Language='en_US'
;

-- 2023-12-21T12:36:17.566Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582876,'en_US')
;

-- 2023-12-21T12:36:32.633Z
UPDATE AD_Element_Trl SET Description='Prüfen Sie, ob der Status des Produktionsauftrags storniert ist oder nicht',Updated=TO_TIMESTAMP('2023-12-21 13:36:32.431','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582876 AND AD_Language='de_DE'
;

-- 2023-12-21T12:36:32.705Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582876,'de_DE')
;

-- 2023-12-21T12:36:32.845Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582876,'de_DE')
;

-- 2023-12-21T12:36:32.916Z
UPDATE AD_Column SET ColumnName='IsVoided', Name='Storniert', Description='Prüfen Sie, ob der Status des Produktionsauftrags storniert ist oder nicht', Help=NULL WHERE AD_Element_ID=582876
;

-- 2023-12-21T12:46:12.945Z
UPDATE AD_Val_Rule SET Description='BOMs with Valid To < Production Date should no be displayed, Also If the PP_Order is (Manufactoring) only manufactoring BOMs with (BOM Type=Current Active) are displayed, if the PP_Order is (Service/Repair Order) only Repair BOMs are displayed',Updated=TO_TIMESTAMP('2023-12-21 13:46:12.745','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540667
;
