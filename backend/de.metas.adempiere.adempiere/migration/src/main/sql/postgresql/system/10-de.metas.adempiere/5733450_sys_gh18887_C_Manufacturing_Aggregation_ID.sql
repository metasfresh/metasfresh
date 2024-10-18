-- 2024-09-16T09:57:05.131Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583254,0,'C_Manufacturing_Aggregation_ID',TO_TIMESTAMP('2024-09-16 12:57:04','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Manufacturing aggregation schema','Manufacturing aggregation schema',TO_TIMESTAMP('2024-09-16 12:57:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-16T09:57:05.155Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583254 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: C_Manufacturing_Aggregation_ID
-- 2024-09-16T09:57:33.608Z
UPDATE AD_Element_Trl SET Name='Produktionsaggregationsschema', PrintName='Produktionsaggregationsschema',Updated=TO_TIMESTAMP('2024-09-16 12:57:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583254 AND AD_Language='de_CH'
;

-- 2024-09-16T09:57:33.643Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583254,'de_CH') 
;

-- Element: C_Manufacturing_Aggregation_ID
-- 2024-09-16T09:57:36.596Z
UPDATE AD_Element_Trl SET Name='Produktionsaggregationsschema', PrintName='Produktionsaggregationsschema',Updated=TO_TIMESTAMP('2024-09-16 12:57:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583254 AND AD_Language='de_DE'
;

-- 2024-09-16T09:57:36.597Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583254,'de_DE') 
;

-- 2024-09-16T09:57:36.609Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583254,'de_DE') 
;

-- Element: C_Manufacturing_Aggregation_ID
-- 2024-09-16T09:57:45.228Z
UPDATE AD_Element_Trl SET Name='Produktionsaggregationsschema', PrintName='Produktionsaggregationsschema',Updated=TO_TIMESTAMP('2024-09-16 12:57:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583254 AND AD_Language='fr_CH'
;

-- 2024-09-16T09:57:45.229Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583254,'fr_CH') 
;

-- Column: PP_Product_Planning.C_Manufacturing_Aggregation_ID
-- Column: PP_Product_Planning.C_Manufacturing_Aggregation_ID
-- 2024-09-16T09:59:03.638Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588979,583254,0,18,540531,53020,'XX','C_Manufacturing_Aggregation_ID',TO_TIMESTAMP('2024-09-16 12:59:03','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Produktionsaggregationsschema',0,0,TO_TIMESTAMP('2024-09-16 12:59:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-16T09:59:03.643Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588979 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-16T09:59:03.649Z
/* DDL */  select update_Column_Translation_From_AD_Element(583254) 
;

-- 2024-09-16T09:59:34.908Z
/* DDL */ SELECT public.db_alter_table('PP_Product_Planning','ALTER TABLE public.PP_Product_Planning ADD COLUMN C_Manufacturing_Aggregation_ID NUMERIC(10)')
;

-- 2024-09-16T09:59:35.281Z
ALTER TABLE PP_Product_Planning ADD CONSTRAINT CManufacturingAggregation_PPProductPlanning FOREIGN KEY (C_Manufacturing_Aggregation_ID) REFERENCES public.C_Aggregation DEFERRABLE INITIALLY DEFERRED
;

-- Field: Produkt Plandaten -> Product Planning -> Produktionsaggregationsschema
-- Column: PP_Product_Planning.C_Manufacturing_Aggregation_ID
-- Field: Produkt Plandaten(540750,D) -> Product Planning(542102,D) -> Produktionsaggregationsschema
-- Column: PP_Product_Planning.C_Manufacturing_Aggregation_ID
-- 2024-09-16T10:02:26.398Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588979,729886,0,542102,TO_TIMESTAMP('2024-09-16 13:02:26','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Produktionsaggregationsschema',TO_TIMESTAMP('2024-09-16 13:02:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-16T10:02:26.405Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729886 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-16T10:02:26.411Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583254) 
;

-- 2024-09-16T10:02:26.433Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729886
;

-- 2024-09-16T10:02:26.443Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729886)
;

-- Field: Produkt Plandaten -> Product Planning -> Produktionsaggregationsschema
-- Column: PP_Product_Planning.C_Manufacturing_Aggregation_ID
-- Field: Produkt Plandaten(540750,D) -> Product Planning(542102,D) -> Produktionsaggregationsschema
-- Column: PP_Product_Planning.C_Manufacturing_Aggregation_ID
-- 2024-09-16T10:02:58.167Z
UPDATE AD_Field SET DisplayLogic='@IsManufactured/''N''@=''Y''',Updated=TO_TIMESTAMP('2024-09-16 13:02:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729886
;

-- UI Element: Produkt Plandaten -> Product Planning.Produktionsaggregationsschema
-- Column: PP_Product_Planning.C_Manufacturing_Aggregation_ID
-- UI Element: Produkt Plandaten(540750,D) -> Product Planning(542102,D) -> main -> 20 -> flags.Produktionsaggregationsschema
-- Column: PP_Product_Planning.C_Manufacturing_Aggregation_ID
-- 2024-09-16T10:05:19.882Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,729886,0,542102,625341,543143,'F',TO_TIMESTAMP('2024-09-16 13:05:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Produktionsaggregationsschema',140,0,0,TO_TIMESTAMP('2024-09-16 13:05:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Produkt Plandaten -> Product Planning.Produktionsaggregationsschema
-- Column: PP_Product_Planning.C_Manufacturing_Aggregation_ID
-- UI Element: Produkt Plandaten(540750,D) -> Product Planning(542102,D) -> main -> 20 -> flags.Produktionsaggregationsschema
-- Column: PP_Product_Planning.C_Manufacturing_Aggregation_ID
-- 2024-09-16T10:05:59.216Z
UPDATE AD_UI_Element SET SeqNo=95,Updated=TO_TIMESTAMP('2024-09-16 13:05:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625341
;

-- Column: M_Product_PlanningSchema.C_Manufacturing_Aggregation_ID
-- Column: M_Product_PlanningSchema.C_Manufacturing_Aggregation_ID
-- 2024-09-16T10:12:30.976Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588980,583254,0,18,540531,540928,'XX','C_Manufacturing_Aggregation_ID',TO_TIMESTAMP('2024-09-16 13:12:30','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.product',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Produktionsaggregationsschema',0,0,TO_TIMESTAMP('2024-09-16 13:12:30','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-16T10:12:30.977Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588980 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-16T10:12:30.980Z
/* DDL */  select update_Column_Translation_From_AD_Element(583254) 
;

-- 2024-09-16T10:12:33.130Z
/* DDL */ SELECT public.db_alter_table('M_Product_PlanningSchema','ALTER TABLE public.M_Product_PlanningSchema ADD COLUMN C_Manufacturing_Aggregation_ID NUMERIC(10)')
;

-- 2024-09-16T10:12:33.140Z
ALTER TABLE M_Product_PlanningSchema ADD CONSTRAINT CManufacturingAggregation_MProductPlanningSchema FOREIGN KEY (C_Manufacturing_Aggregation_ID) REFERENCES public.C_Aggregation DEFERRABLE INITIALLY DEFERRED
;

-- Field: Product Planning Schema -> Product Planning Schema -> Produktionsaggregationsschema
-- Column: M_Product_PlanningSchema.C_Manufacturing_Aggregation_ID
-- Field: Product Planning Schema(540398,de.metas.product) -> Product Planning Schema(540991,de.metas.product) -> Produktionsaggregationsschema
-- Column: M_Product_PlanningSchema.C_Manufacturing_Aggregation_ID
-- 2024-09-16T10:13:06.402Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588980,729887,0,540991,TO_TIMESTAMP('2024-09-16 13:13:06','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.product','Y','N','N','N','N','N','N','N','Produktionsaggregationsschema',TO_TIMESTAMP('2024-09-16 13:13:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-16T10:13:06.403Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729887 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-16T10:13:06.405Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583254) 
;

-- 2024-09-16T10:13:06.410Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729887
;

-- 2024-09-16T10:13:06.411Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729887)
;

-- Field: Product Planning Schema -> Product Planning Schema -> Produktionsaggregationsschema
-- Column: M_Product_PlanningSchema.C_Manufacturing_Aggregation_ID
-- Field: Product Planning Schema(540398,de.metas.product) -> Product Planning Schema(540991,de.metas.product) -> Produktionsaggregationsschema
-- Column: M_Product_PlanningSchema.C_Manufacturing_Aggregation_ID
-- 2024-09-16T10:13:40.155Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=180,Updated=TO_TIMESTAMP('2024-09-16 13:13:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729887
;

-- 2024-09-16T10:13:51.156Z
INSERT INTO t_alter_column values('m_product_planningschema','C_Manufacturing_Aggregation_ID','NUMERIC(10)',null,null)
;

