-- Element: OI_OpenAmount
-- 2023-07-13T08:16:25.604Z
UPDATE AD_Element_Trl SET Description='OI Open amount in accounting currency',Updated=TO_TIMESTAMP('2023-07-13 11:16:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582539 AND AD_Language='de_CH'
;

-- 2023-07-13T08:16:25.634Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582539,'de_CH') 
;

-- Element: OI_OpenAmount
-- 2023-07-13T08:16:27.515Z
UPDATE AD_Element_Trl SET Description='OI Open amount in accounting currency',Updated=TO_TIMESTAMP('2023-07-13 11:16:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582539 AND AD_Language='de_DE'
;

-- 2023-07-13T08:16:27.517Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582539,'de_DE') 
;

-- Element: OI_OpenAmount
-- 2023-07-13T08:16:29.939Z
UPDATE AD_Element_Trl SET Description='OI Open amount in accounting currency', IsTranslated='Y',Updated=TO_TIMESTAMP('2023-07-13 11:16:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582539 AND AD_Language='en_US'
;

-- 2023-07-13T08:16:29.942Z
UPDATE AD_Element SET Description='OI Open amount in accounting currency', Updated=TO_TIMESTAMP('2023-07-13 11:16:29','YYYY-MM-DD HH24:MI:SS') WHERE AD_Element_ID=582539
;

-- 2023-07-13T08:16:30.368Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582539,'en_US') 
;

-- 2023-07-13T08:16:30.370Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582539,'en_US') 
;

-- Element: OI_OpenAmount
-- 2023-07-13T08:16:32.171Z
UPDATE AD_Element_Trl SET Description='OI Open amount in accounting currency',Updated=TO_TIMESTAMP('2023-07-13 11:16:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582539 AND AD_Language='fr_CH'
;

-- 2023-07-13T08:16:32.174Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582539,'fr_CH') 
;

-- Element: OI_OpenAmount
-- 2023-07-13T08:16:33.881Z
UPDATE AD_Element_Trl SET Description='OI Open amount in accounting currency',Updated=TO_TIMESTAMP('2023-07-13 11:16:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582539 AND AD_Language='nl_NL'
;

-- 2023-07-13T08:16:33.884Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582539,'nl_NL') 
;

-- 2023-07-13T08:17:05.602Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582550,0,'OI_OpenAmountSource',TO_TIMESTAMP('2023-07-13 11:17:05','YYYY-MM-DD HH24:MI:SS'),100,'OI Open amount in source currency','D','Y','OI Open Amount (source)','OI Open Amount (source)',TO_TIMESTAMP('2023-07-13 11:17:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-13T08:17:05.604Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582550 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: Fact_Acct.OI_OpenAmountSource
-- 2023-07-13T08:17:21.392Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587113,582550,0,12,270,'XX','OI_OpenAmountSource',TO_TIMESTAMP('2023-07-13 11:17:21','YYYY-MM-DD HH24:MI:SS'),100,'N','OI Open amount in source currency','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'OI Open Amount (source)',0,0,TO_TIMESTAMP('2023-07-13 11:17:21','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-07-13T08:17:21.393Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587113 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-07-13T08:17:22.180Z
/* DDL */  select update_Column_Translation_From_AD_Element(582550) 
;

-- 2023-07-13T08:17:23.301Z
/* DDL */ SELECT public.db_alter_table('Fact_Acct','ALTER TABLE public.Fact_Acct ADD COLUMN OI_OpenAmountSource NUMERIC')
;

