-- Run mode: SWING_CLIENT


-- 2025-05-14T18:54:30.915Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583635,0,'IsSkipPriceUOMValidation',TO_TIMESTAMP('2025-05-14 20:54:30.745','YYYY-MM-DD HH24:MI:SS.US'),100,'Erlaubt es eine abweichende Preiseinheit ohne Maßeinheitsumrechnung zu nutzen. Dies ist nur in Zusammenhang mit modularen Verträgen sinnvoll.','D','Y','Abw. Preiseinheit','Abw. Preiseinheit',TO_TIMESTAMP('2025-05-14 20:54:30.745','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-05-14T18:54:30.921Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583635 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsSkipPriceUOMValidation
-- 2025-05-14T18:56:59.807Z
UPDATE AD_Element_Trl SET Description='Allows different PriceUOM without UOMConversion. Should only be used for modular contract Products', IsTranslated='Y', Name='Diff. PriceUOM', PrintName='Diff. PriceUOM',Updated=TO_TIMESTAMP('2025-05-14 20:56:59.807','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583635 AND AD_Language='en_US'
;

-- 2025-05-14T18:56:59.827Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583635,'en_US')
;

-- Element: IsSkipPriceUOMValidation
-- 2025-05-14T18:57:00.488Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-05-14 20:57:00.488','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583635 AND AD_Language='de_CH'
;

-- 2025-05-14T18:57:00.491Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583635,'de_CH')
;

-- Element: IsSkipPriceUOMValidation
-- 2025-05-14T18:57:05.762Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-05-14 20:57:05.762','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583635 AND AD_Language='de_DE'
;

-- 2025-05-14T18:57:05.765Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583635,'de_DE')
;

-- 2025-05-14T18:57:05.766Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583635,'de_DE')
;

-- Column: M_Product.IsSkipPriceUOMValidation
-- 2025-05-14T19:15:42.611Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589986,583635,0,20,208,'IsSkipPriceUOMValidation',TO_TIMESTAMP('2025-05-14 21:15:42.121','YYYY-MM-DD HH24:MI:SS.US'),100,'N','N','Erlaubt es eine abweichende Preiseinheit ohne Maßeinheitsumrechnung zu nutzen. Dies ist nur in Zusammenhang mit modularen Verträgen sinnvoll.','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Abw. Preiseinheit',0,0,TO_TIMESTAMP('2025-05-14 21:15:42.121','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2025-05-14T19:15:42.617Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589986 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-05-14T19:15:42.630Z
/* DDL */  select update_Column_Translation_From_AD_Element(583635)
;

-- 2025-05-14T19:15:46.461Z
/* DDL */ SELECT public.db_alter_table('M_Product','ALTER TABLE public.M_Product ADD COLUMN IsSkipPriceUOMValidation CHAR(1) DEFAULT ''N'' CHECK (IsSkipPriceUOMValidation IN (''Y'',''N'')) NOT NULL')
;

-- Field: Produkt(140,D) -> Produkt(180,D) -> Abw. Preiseinheit
-- Column: M_Product.IsSkipPriceUOMValidation
-- 2025-05-14T19:17:15.116Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589986,743928,0,180,TO_TIMESTAMP('2025-05-14 21:17:15.001','YYYY-MM-DD HH24:MI:SS.US'),100,'Erlaubt es eine abweichende Preiseinheit ohne Maßeinheitsumrechnung zu nutzen. Dies ist nur in Zusammenhang mit modularen Verträgen sinnvoll.',1,'D','Y','N','N','N','N','N','N','N','Abw. Preiseinheit',TO_TIMESTAMP('2025-05-14 21:17:15.001','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-05-14T19:17:15.118Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=743928 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-05-14T19:17:15.120Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583635)
;

-- 2025-05-14T19:17:15.128Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=743928
;

-- 2025-05-14T19:17:15.129Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(743928)
;

-- UI Element: Produkt(140,D) -> Produkt(180,D) -> main -> 10 -> Produkt.Abw. Preiseinheit
-- Column: M_Product.IsSkipPriceUOMValidation
-- 2025-05-14T19:19:10.686Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,743928,0,180,1000014,632035,'F',TO_TIMESTAMP('2025-05-14 21:19:10.55','YYYY-MM-DD HH24:MI:SS.US'),100,'Erlaubt es eine abweichende Preiseinheit ohne Maßeinheitsumrechnung zu nutzen. Dies ist nur in Zusammenhang mit modularen Verträgen sinnvoll.','Y','Y','N','Y','N','N','N',0,'Abw. Preiseinheit',45,0,0,TO_TIMESTAMP('2025-05-14 21:19:10.55','YYYY-MM-DD HH24:MI:SS.US'),100)
;

