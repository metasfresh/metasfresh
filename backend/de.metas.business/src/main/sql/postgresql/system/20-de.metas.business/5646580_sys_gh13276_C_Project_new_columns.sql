-- 2022-07-12T13:34:35.453Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581121,0,'WOOwner',TO_TIMESTAMP('2022-07-12 15:34:35','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Ersteller Prüfauftrag','Ersteller Prüfauftrag',TO_TIMESTAMP('2022-07-12 15:34:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-12T13:34:35.460Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581121 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-07-12T13:34:39.508Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-07-12 15:34:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581121 AND AD_Language='de_CH'
;

-- 2022-07-12T13:34:39.551Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581121,'de_CH') 
;

-- 2022-07-12T13:34:41.966Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-07-12 15:34:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581121 AND AD_Language='de_DE'
;

-- 2022-07-12T13:34:41.968Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581121,'de_DE') 
;

-- 2022-07-12T13:34:41.977Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581121,'de_DE') 
;

-- 2022-07-12T13:35:15.482Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Creator of test order', PrintName='Creator of test order',Updated=TO_TIMESTAMP('2022-07-12 15:35:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581121 AND AD_Language='en_US'
;

-- 2022-07-12T13:35:15.484Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581121,'en_US') 
;

-- Column: C_Project.WOOwner
-- 2022-07-12T13:35:28.245Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583670,581121,0,10,203,'WOOwner',TO_TIMESTAMP('2022-07-12 15:35:28','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Ersteller Prüfauftrag',0,0,TO_TIMESTAMP('2022-07-12 15:35:28','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-12T13:35:28.249Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583670 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-12T13:35:28.255Z
/* DDL */  select update_Column_Translation_From_AD_Element(581121) 
;

-- 2022-07-12T13:35:29.241Z
/* DDL */ SELECT public.db_alter_table('C_Project','ALTER TABLE public.C_Project ADD COLUMN WOOwner VARCHAR(255)')
;

-----------------


-- Column: C_Project_WO_ObjectUnderTest.ExternalId
-- 2022-07-12T13:48:02.252Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583675,543939,0,10,542184,'ExternalId',TO_TIMESTAMP('2022-07-12 15:48:02','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,255,'Y','N','Y','N','N','N','Y','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Externe ID',0,0,TO_TIMESTAMP('2022-07-12 15:48:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-12T13:48:02.255Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583675 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-12T13:48:02.261Z
/* DDL */  select update_Column_Translation_From_AD_Element(543939)
;

-- 2022-07-12T13:48:03.069Z
/* DDL */ SELECT public.db_alter_table('C_Project_WO_ObjectUnderTest','ALTER TABLE public.C_Project_WO_ObjectUnderTest ADD COLUMN ExternalId VARCHAR(255)')
;

