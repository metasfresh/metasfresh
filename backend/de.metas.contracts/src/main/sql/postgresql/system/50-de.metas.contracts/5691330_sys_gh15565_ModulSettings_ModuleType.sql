-- Column: C_ModuleSettings.Contract_Module_Type_ID
-- 2023-06-13T09:20:54.983934700Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586809,582395,0,19,541527,542340,'Contract_Module_Type_ID',TO_TIMESTAMP('2023-06-13 12:20:54.792','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Contract Module Type',0,0,TO_TIMESTAMP('2023-06-13 12:20:54.792','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-13T09:20:54.991934300Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586809 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-13T09:20:56.013197100Z
/* DDL */  select update_Column_Translation_From_AD_Element(582395) 
;

-- 2023-06-13T09:20:59.227791Z
/* DDL */ SELECT public.db_alter_table('C_ModuleSettings','ALTER TABLE public.C_ModuleSettings ADD COLUMN Contract_Module_Type_ID NUMERIC(10) NOT NULL')
;

-- 2023-06-13T09:20:59.274977700Z
ALTER TABLE C_ModuleSettings ADD CONSTRAINT ContractModuleType_CModuleSettings FOREIGN KEY (Contract_Module_Type_ID) REFERENCES public.Contract_Module_Type DEFERRABLE INITIALLY DEFERRED
;

-- Field: Einstellungen Vertragsbausteine(541712,de.metas.contracts) -> Bausteine(547014,de.metas.contracts) -> Contract Module Type
-- Column: C_ModuleSettings.Contract_Module_Type_ID
-- 2023-06-13T09:21:17.119294700Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586809,716356,0,547014,TO_TIMESTAMP('2023-06-13 12:21:16.946','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.contracts','Y','N','N','N','N','N','N','N','Contract Module Type',TO_TIMESTAMP('2023-06-13 12:21:16.946','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-13T09:21:17.133870800Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716356 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-13T09:21:17.140847100Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582395) 
;

-- 2023-06-13T09:21:17.146792600Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716356
;

-- 2023-06-13T09:21:17.146792600Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716356)
;

-- UI Element: Einstellungen Vertragsbausteine(541712,de.metas.contracts) -> Bausteine(547014,de.metas.contracts) -> main -> 10 -> default.Contract Module Type
-- Column: C_ModuleSettings.Contract_Module_Type_ID
-- 2023-06-13T09:21:45.749100300Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,716356,0,547014,617996,550783,'F',TO_TIMESTAMP('2023-06-13 12:21:45.565','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Contract Module Type',50,0,0,TO_TIMESTAMP('2023-06-13 12:21:45.565','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Einstellungen Vertragsbausteine(541712,de.metas.contracts) -> Bausteine(547014,de.metas.contracts) -> main -> 10 -> default.Contract Module Type
-- Column: C_ModuleSettings.Contract_Module_Type_ID
-- 2023-06-13T09:30:27.625526800Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-06-13 12:30:27.624','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617996
;

-- Element: Contract_Module_Type_ID
-- 2023-06-13T09:34:18.778810500Z
UPDATE AD_Element_Trl SET Name='Vertragsbaustein Typ', PrintName='Vertragsbaustein Typ',Updated=TO_TIMESTAMP('2023-06-13 12:34:18.778','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582395 AND AD_Language='de_CH'
;

-- 2023-06-13T09:34:18.781506700Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582395,'de_CH') 
;

-- Element: Contract_Module_Type_ID
-- 2023-06-13T09:34:21.377483300Z
UPDATE AD_Element_Trl SET Name='Vertragsbaustein Typ', PrintName='Vertragsbaustein Typ',Updated=TO_TIMESTAMP('2023-06-13 12:34:21.377','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582395 AND AD_Language='de_DE'
;

-- 2023-06-13T09:34:21.378508300Z
UPDATE AD_Element SET Name='Vertragsbaustein Typ', PrintName='Vertragsbaustein Typ' WHERE AD_Element_ID=582395
;

-- 2023-06-13T09:34:22.431273200Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582395,'de_DE') 
;

-- 2023-06-13T09:34:22.433442400Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582395,'de_DE') 
;

-- Element: Contract_Module_Type_ID
-- 2023-06-13T09:34:28.719473200Z
UPDATE AD_Element_Trl SET Name='Vertragsbaustein Typ', PrintName='Vertragsbaustein Typ',Updated=TO_TIMESTAMP('2023-06-13 12:34:28.718','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582395 AND AD_Language='fr_CH'
;

-- 2023-06-13T09:34:28.722103400Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582395,'fr_CH') 
;

