-- 2020-12-07T14:37:32.236Z
-- #298 changing anz. stellen
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578599,0,'ContractStartDate',TO_TIMESTAMP('2020-12-07 16:37:30','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Contract Start Date','Contract Start Date',TO_TIMESTAMP('2020-12-07 16:37:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-12-07T14:37:32.302Z
-- #298 changing anz. stellen
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578599 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-12-07T14:38:02.938Z
-- #298 changing anz. stellen
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578600,0,'Contract End Date',TO_TIMESTAMP('2020-12-07 16:38:02','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Contract End Date','Contract End Date',TO_TIMESTAMP('2020-12-07 16:38:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-12-07T14:38:02.999Z
-- #298 changing anz. stellen
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578600 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-12-07T14:38:54.237Z
-- #298 changing anz. stellen
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,572303,578599,0,15,540320,'ContractStartDate',TO_TIMESTAMP('2020-12-07 16:38:53','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.contracts',0,7,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Contract Start Date',0,0,TO_TIMESTAMP('2020-12-07 16:38:53','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2020-12-07T14:38:54.301Z
-- #298 changing anz. stellen
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=572303 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-12-07T14:38:54.405Z
-- #298 changing anz. stellen
/* DDL */  select update_Column_Translation_From_AD_Element(578599) 
;

-- 2020-12-07T14:39:20.990Z
-- #298 changing anz. stellen
/* DDL */ SELECT public.db_alter_table('C_Flatrate_Term','ALTER TABLE public.C_Flatrate_Term ADD COLUMN ContractStartDate TIMESTAMP WITHOUT TIME ZONE')
;

-- 2020-12-07T14:41:06.984Z
-- #298 changing anz. stellen
UPDATE AD_Element SET ColumnName='ContractEndDate',Updated=TO_TIMESTAMP('2020-12-07 16:41:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578600
;

-- 2020-12-07T14:41:07.230Z
-- #298 changing anz. stellen
UPDATE AD_Column SET ColumnName='ContractEndDate', Name='Contract End Date', Description=NULL, Help=NULL WHERE AD_Element_ID=578600
;

-- 2020-12-07T14:41:07.306Z
-- #298 changing anz. stellen
UPDATE AD_Process_Para SET ColumnName='ContractEndDate', Name='Contract End Date', Description=NULL, Help=NULL, AD_Element_ID=578600 WHERE UPPER(ColumnName)='CONTRACTENDDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-12-07T14:41:07.370Z
-- #298 changing anz. stellen
UPDATE AD_Process_Para SET ColumnName='ContractEndDate', Name='Contract End Date', Description=NULL, Help=NULL WHERE AD_Element_ID=578600 AND IsCentrallyMaintained='Y'
;

-- 2020-12-07T14:41:24.137Z
-- #298 changing anz. stellen
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,572304,578600,0,15,540320,'ContractEndDate',TO_TIMESTAMP('2020-12-07 16:41:23','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.contracts',0,7,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Contract End Date',0,0,TO_TIMESTAMP('2020-12-07 16:41:23','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2020-12-07T14:41:24.197Z
-- #298 changing anz. stellen
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=572304 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-12-07T14:41:24.254Z
-- #298 changing anz. stellen
/* DDL */  select update_Column_Translation_From_AD_Element(578600) 
;

-- 2020-12-07T14:41:46.617Z
-- #298 changing anz. stellen
/* DDL */ SELECT public.db_alter_table('C_Flatrate_Term','ALTER TABLE public.C_Flatrate_Term ADD COLUMN ContractEndDate TIMESTAMP WITHOUT TIME ZONE')
;

