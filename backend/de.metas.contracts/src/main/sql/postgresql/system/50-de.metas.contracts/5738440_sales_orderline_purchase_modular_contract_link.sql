-- Run mode: SWING_CLIENT

-- 2024-11-05T12:16:20.606Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583360,0,'Purchase_Modular_Flatrate_Term_ID',TO_TIMESTAMP('2024-11-05 13:16:20.364','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Modularer Vertrag Einkauf','Modularer Vertrag Einkauf',TO_TIMESTAMP('2024-11-05 13:16:20.364','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-11-05T12:16:20.620Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583360 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: Purchase_Modular_Flatrate_Term_ID
-- 2024-11-05T12:17:01.765Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Modular Contract Purchase', PrintName='Modular Contract Purchase',Updated=TO_TIMESTAMP('2024-11-05 13:17:01.765','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583360 AND AD_Language='en_US'
;

-- 2024-11-05T12:17:01.785Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583360,'en_US')
;

-- Element: Purchase_Modular_Flatrate_Term_ID
-- 2024-11-05T12:17:06.890Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-11-05 13:17:06.89','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583360 AND AD_Language='de_DE'
;

-- 2024-11-05T12:17:06.894Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583360,'de_DE')
;

-- 2024-11-05T12:17:06.896Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583360,'de_DE')
;

-- Element: Purchase_Modular_Flatrate_Term_ID
-- 2024-11-05T12:17:20.299Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-11-05 13:17:20.299','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583360 AND AD_Language='de_CH'
;

-- 2024-11-05T12:17:20.303Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583360,'de_CH')
;

-- Name: Purchase_Modular_Flatrate_Term_ID
-- 2024-11-05T13:38:37.086Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540694,'C_Flatrate_Term.C_Flatrate_Term_ID IN (SELECT ft.C_Flatrate_Term_ID
 FROM C_Flatrate_Term ft
     INNER JOIN C_Flatrate_Conditions c ON ft.c_flatrate_conditions_id = c.c_flatrate_conditions_id
     INNER JOIN ModCntr_Settings mc ON @Harvesting_Year_ID/0@>0
     AND mc.ModCntr_Settings_ID = c.ModCntr_Settings_ID
     AND (mc.M_Raw_Product_ID = @M_Product_ID@ OR mc.M_Processed_Product_ID = @M_Product_ID@)
     AND mc.C_Year_ID = @Harvesting_Year_ID/0@ AND mc.isSOTrx = ''N''
 WHERE ft.type_conditions = ''ModularContract''AND ft.isSOTrx = ''N'' )',TO_TIMESTAMP('2024-11-05 14:38:36.877','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Purchase_Modular_Flatrate_Term_ID','S',TO_TIMESTAMP('2024-11-05 14:38:36.877','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Column: C_OrderLine.Purchase_Modular_Flatrate_Term_ID
-- 2024-11-05T13:43:59.335Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589369,583360,0,30,541780,260,540694,'Purchase_Modular_Flatrate_Term_ID',TO_TIMESTAMP('2024-11-05 14:43:59.123','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Modularer Vertrag Einkauf',0,0,TO_TIMESTAMP('2024-11-05 14:43:59.123','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-11-05T13:43:59.338Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589369 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-11-05T13:43:59.344Z
/* DDL */  select update_Column_Translation_From_AD_Element(583360)
;

-- 2024-11-05T14:06:08.831Z
/* DDL */ SELECT public.db_alter_table('C_OrderLine','ALTER TABLE public.C_OrderLine ADD COLUMN Purchase_Modular_Flatrate_Term_ID NUMERIC(10)')
;

-- 2024-11-05T14:06:09.790Z
ALTER TABLE C_OrderLine ADD CONSTRAINT PurchaseModularFlatrateTerm_COrderLine FOREIGN KEY (Purchase_Modular_Flatrate_Term_ID) REFERENCES public.C_Flatrate_Term DEFERRABLE INITIALLY DEFERRED
;

-- Field: Auftrag(143,D) -> Auftragsposition(187,D) -> Modularer Vertrag Einkauf
-- Column: C_OrderLine.Purchase_Modular_Flatrate_Term_ID
-- 2024-11-05T15:07:00.197Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,589369,733459,0,187,0,TO_TIMESTAMP('2024-11-05 16:06:59.977','YYYY-MM-DD HH24:MI:SS.US'),100,0,'@Harvesting_Year_ID/0@>0','D',0,'Y','Y','Y','N','N','N','N','N','N','Modularer Vertrag Einkauf',0,380,0,1,1,TO_TIMESTAMP('2024-11-05 16:06:59.977','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-11-05T15:07:00.217Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=733459 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-11-05T15:07:00.226Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583360)
;

-- 2024-11-05T15:07:00.241Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=733459
;

-- 2024-11-05T15:07:00.244Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(733459)
;

-- Reference: C_Flatrate_Term_Modular
-- Table: C_Flatrate_Term
-- Key: C_Flatrate_Term.C_Flatrate_Term_ID
-- 2024-11-06T07:20:32.599Z
UPDATE AD_Ref_Table SET AD_Display=NULL,Updated=TO_TIMESTAMP('2024-11-06 08:20:32.599','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Reference_ID=541780
;

-- UI Element: Auftrag_OLD(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Modularer Vertrag Einkauf
-- Column: C_OrderLine.Purchase_Modular_Flatrate_Term_ID
-- 2024-11-06T09:39:04.501Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,733459,0,187,1000005,626999,'F',TO_TIMESTAMP('2024-11-06 10:39:04.338','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','Y','N','Y','N','N','N',0,'Modularer Vertrag Einkauf',450,0,0,TO_TIMESTAMP('2024-11-06 10:39:04.338','YYYY-MM-DD HH24:MI:SS.US'),100)
;

