-- 2023-08-09T09:59:17.549993Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582633,0,'InternalDescription',TO_TIMESTAMP('2023-08-09 12:59:17.326','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Internal Description','Internal Description',TO_TIMESTAMP('2023-08-09 12:59:17.326','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-09T09:59:17.566634800Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582633 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: InternalDescription
-- 2023-08-09T09:59:28.181946Z
UPDATE AD_Element_Trl SET Name='Bemerkung intern', PrintName='Bemerkung intern',Updated=TO_TIMESTAMP('2023-08-09 12:59:28.181','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582633 AND AD_Language='de_CH'
;

-- 2023-08-09T09:59:28.226412700Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582633,'de_CH') 
;

-- Element: InternalDescription
-- 2023-08-09T09:59:31.209238200Z
UPDATE AD_Element_Trl SET Name='Bemerkung intern', PrintName='Bemerkung intern',Updated=TO_TIMESTAMP('2023-08-09 12:59:31.209','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582633 AND AD_Language='de_DE'
;

-- 2023-08-09T09:59:31.214453300Z
UPDATE AD_Element SET Name='Bemerkung intern', PrintName='Bemerkung intern' WHERE AD_Element_ID=582633
;

-- 2023-08-09T09:59:31.726496500Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582633,'de_DE') 
;

-- 2023-08-09T09:59:31.730494800Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582633,'de_DE') 
;

-- Element: InternalDescription
-- 2023-08-09T09:59:41.827259800Z
UPDATE AD_Element_Trl SET Name='Bemerkung intern', PrintName='Bemerkung intern',Updated=TO_TIMESTAMP('2023-08-09 12:59:41.826','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582633 AND AD_Language='fr_CH'
;

-- 2023-08-09T09:59:41.829222100Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582633,'fr_CH') 
;

-- Element: InternalDescription
-- 2023-08-09T09:59:44.141333900Z
UPDATE AD_Element_Trl SET Name='Bemerkung intern', PrintName='Bemerkung intern',Updated=TO_TIMESTAMP('2023-08-09 12:59:44.14','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582633 AND AD_Language='it_IT'
;

-- 2023-08-09T09:59:44.142333400Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582633,'it_IT') 
;

-- Column: C_Order.InternalDescription
-- 2023-08-09T10:04:00.123277800Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587257,582633,0,36,259,'InternalDescription',TO_TIMESTAMP('2023-08-09 13:03:59.966','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,6000,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Bemerkung intern',0,0,TO_TIMESTAMP('2023-08-09 13:03:59.966','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-09T10:04:00.126277400Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587257 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-09T10:04:00.869432400Z
/* DDL */  select update_Column_Translation_From_AD_Element(582633) 
;

-- 2023-08-09T10:04:07.052008500Z
/* DDL */ SELECT public.db_alter_table('C_Order','ALTER TABLE public.C_Order ADD COLUMN InternalDescription TEXT')
;

