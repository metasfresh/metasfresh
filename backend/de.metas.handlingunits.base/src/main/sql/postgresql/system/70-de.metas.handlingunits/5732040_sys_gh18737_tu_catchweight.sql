-- 2024-08-29T10:27:48.667Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583234,0,'IsCatchWeightTUPickingEnabled',TO_TIMESTAMP('2024-08-29 13:27:48','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','Y','Allow picking TU with catch weight','Allow picking TU with catch weight',TO_TIMESTAMP('2024-08-29 13:27:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-08-29T10:27:48.695Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583234 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2024-08-29T10:28:49.848Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583235,0,'ConsiderSalesOrderCapacity',TO_TIMESTAMP('2024-08-29 13:28:49','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.salesorder','Y','Consider sales order capacity','Consider sales order capacity',TO_TIMESTAMP('2024-08-29 13:28:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-08-29T10:28:49.852Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583235 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2024-08-29T10:29:31.349Z
UPDATE AD_Element SET EntityType='de.metas.picking',Updated=TO_TIMESTAMP('2024-08-29 13:29:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583235
;

-- 2024-08-29T10:29:31.419Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583235,'de_DE') 
;

-- Column: MobileUI_UserProfile_Picking.IsCatchWeightTUPickingEnabled
-- Column: MobileUI_UserProfile_Picking.IsCatchWeightTUPickingEnabled
-- 2024-08-29T10:34:50.120Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588936,583234,0,20,542373,'XX','IsCatchWeightTUPickingEnabled',TO_TIMESTAMP('2024-08-29 13:34:49','YYYY-MM-DD HH24:MI:SS'),100,'N','N','de.metas.picking',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Allow picking TU with catch weight',0,0,TO_TIMESTAMP('2024-08-29 13:34:49','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-08-29T10:34:50.124Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588936 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-08-29T10:34:50.131Z
/* DDL */  select update_Column_Translation_From_AD_Element(583234) 
;

-- Column: MobileUI_UserProfile_Picking.IsCatchWeightTUPickingEnabled
-- Column: MobileUI_UserProfile_Picking.IsCatchWeightTUPickingEnabled
-- 2024-08-29T10:35:07.678Z
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2024-08-29 13:35:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=588936
;

-- 2024-08-29T10:35:51.969Z
UPDATE AD_Element SET ColumnName='IsConsiderSalesOrderCapacity',Updated=TO_TIMESTAMP('2024-08-29 13:35:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583235
;

-- 2024-08-29T10:35:51.970Z
UPDATE AD_Column SET ColumnName='IsConsiderSalesOrderCapacity' WHERE AD_Element_ID=583235
;

-- 2024-08-29T10:35:51.971Z
UPDATE AD_Process_Para SET ColumnName='IsConsiderSalesOrderCapacity' WHERE AD_Element_ID=583235
;

-- 2024-08-29T10:35:51.974Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583235,'de_DE') 
;

-- Column: MobileUI_UserProfile_Picking.IsConsiderSalesOrderCapacity
-- Column: MobileUI_UserProfile_Picking.IsConsiderSalesOrderCapacity
-- 2024-08-29T10:36:26.142Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588937,583235,0,20,542373,'XX','IsConsiderSalesOrderCapacity',TO_TIMESTAMP('2024-08-29 13:36:26','YYYY-MM-DD HH24:MI:SS'),100,'N','N','de.metas.picking',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Consider sales order capacity',0,0,TO_TIMESTAMP('2024-08-29 13:36:26','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-08-29T10:36:26.144Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588937 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-08-29T10:36:26.148Z
/* DDL */  select update_Column_Translation_From_AD_Element(583235) 
;

-- 2024-08-29T10:37:36.414Z
/* DDL */ SELECT public.db_alter_table('MobileUI_UserProfile_Picking','ALTER TABLE public.MobileUI_UserProfile_Picking ADD COLUMN IsConsiderSalesOrderCapacity CHAR(1) DEFAULT ''N'' CHECK (IsConsiderSalesOrderCapacity IN (''Y'',''N'')) NOT NULL')
;

-- 2024-08-29T10:37:46.882Z
/* DDL */ SELECT public.db_alter_table('MobileUI_UserProfile_Picking','ALTER TABLE public.MobileUI_UserProfile_Picking ADD COLUMN IsCatchWeightTUPickingEnabled CHAR(1) DEFAULT ''N'' CHECK (IsCatchWeightTUPickingEnabled IN (''Y'',''N'')) NOT NULL')
;

-- Column: MobileUI_UserProfile_Picking.IsConsiderSalesOrderCapacity
-- Column: MobileUI_UserProfile_Picking.IsConsiderSalesOrderCapacity
-- 2024-08-29T10:39:06.231Z
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2024-08-29 13:39:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=588937
;

-- 2024-08-29T10:39:08.590Z
INSERT INTO t_alter_column values('mobileui_userprofile_picking','IsConsiderSalesOrderCapacity','CHAR(1)',null,'N')
;

-- 2024-08-29T10:39:08.624Z
UPDATE MobileUI_UserProfile_Picking SET IsConsiderSalesOrderCapacity='N' WHERE IsConsiderSalesOrderCapacity IS NULL
;

