-- 2024-04-23T13:22:23.609Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583085,0,'Initial_Product_ID',TO_TIMESTAMP('2024-04-23 15:22:23.488','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Ursprüngliches Produkt','Ursprüngliches Produkt',TO_TIMESTAMP('2024-04-23 15:22:23.488','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-23T13:22:23.611Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583085 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: Initial_Product_ID
-- 2024-04-23T13:22:28.794Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-04-23 15:22:28.794','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583085 AND AD_Language='de_CH'
;

-- 2024-04-23T13:22:28.796Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583085,'de_CH')
;

-- Element: Initial_Product_ID
-- 2024-04-23T13:22:29.491Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-04-23 15:22:29.491','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583085 AND AD_Language='de_DE'
;

-- 2024-04-23T13:22:29.493Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583085,'de_DE')
;

-- 2024-04-23T13:22:29.495Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583085,'de_DE')
;

-- Element: Initial_Product_ID
-- 2024-04-23T13:22:57.726Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Initial Product', PrintName='Initial Product',Updated=TO_TIMESTAMP('2024-04-23 15:22:57.726','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583085 AND AD_Language='en_US'
;

-- 2024-04-23T13:22:57.728Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583085,'en_US')
;

-- Column: ModCntr_Log.Initial_Product_ID
-- 2024-04-23T13:24:16.317Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588192,583085,0,18,540272,542338,'Initial_Product_ID',TO_TIMESTAMP('2024-04-23 15:24:16.176','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Ursprüngliches Produkt',0,0,TO_TIMESTAMP('2024-04-23 15:24:16.176','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-04-23T13:24:16.318Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588192 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-23T13:24:16.320Z
/* DDL */  select update_Column_Translation_From_AD_Element(583085)
;

-- 2024-04-23T13:24:17.508Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Log','ALTER TABLE public.ModCntr_Log ADD COLUMN Initial_Product_ID NUMERIC(10)')
;

-- 2024-04-23T13:24:17.580Z
ALTER TABLE ModCntr_Log ADD CONSTRAINT InitialProduct_ModCntrLog FOREIGN KEY (Initial_Product_ID) REFERENCES public.M_Product DEFERRABLE INITIALLY DEFERRED
;

