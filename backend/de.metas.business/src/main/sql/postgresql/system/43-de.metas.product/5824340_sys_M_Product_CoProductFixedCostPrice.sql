-- Co-product valuation: add M_Product.CoProductFixedCostPrice.
-- A manually maintained, overridable cost price (nullable) read live at PP_Order cost creation;
-- a co-product (ComponentType=CP) whose product carries a non-blank value is relieved by
-- fixedPrice x qty instead of the qty-distribution. Blank = today's behaviour (opt-in, no seed).
-- Surfaced in a new "Product Costs" element group on the main Produkt tab (AD_Window 140 / AD_Tab 180).
--
-- IDs allocated from idserver.metas.de on 2026-09-14:
--   AD_Element         585459 (M_Product.CoProductFixedCostPrice label)
--   AD_Column          593554 (M_Product.CoProductFixedCostPrice)
--   AD_Field           784976 (Produkt tab 180 field)
--   AD_UI_ElementGroup 555769 (new "Product Costs" group, left column 1000004)
--   AD_UI_Element      654744 (field placement)

-- AD_Element (base language = German)
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy)
VALUES (0,585459 /*From ID Server*/,0,'CoProductFixedCostPrice',TO_TIMESTAMP('2026-09-14 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Manuell gepflegter, überschreibbarer fixer Kostenpreis für ein Co-Product (Rework-Ausschuss). Leer = bisheriges Verhalten.','D','Der Wert wird bei der Erstellung der Produktionsauftragskosten live gelesen: Ein Co-Product mit gesetztem Fixkostenpreis wird mit Fixkostenpreis x Menge bewertet, das Hauptprodukt trägt den Rest. Leer lassen, um das bisherige Verhalten beizubehalten.','Y','Co-Product Fixkostenpreis','Co-Product Fixkostenpreis',TO_TIMESTAMP('2026-09-14 10:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Seed AD_Element_Trl for every active system language (copies base German text)
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585459
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- English override
UPDATE AD_Element_Trl SET Name='Co-Product Fixed Cost Price', PrintName='Co-Product Fixed Cost Price', Description='Manually maintained, overridable fixed cost price for a co-product (rework output). Blank = today''s behaviour.', Help='Read live at PP_Order cost creation: a co-product carrying a fixed cost price is valued at fixedPrice x qty and the main product is relieved by the remainder. Leave blank to keep the existing behaviour.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-14 10:00:12','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=585459 AND AD_Language='en_US'
;

-- Mark the German rows as actively translated (text already German from the base)
UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-14 10:00:14','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=585459 AND AD_Language='de_DE'
;
UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-14 10:00:16','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=585459 AND AD_Language='de_CH'
;

-- AD_Column (reference 37 = Costs+Prices, nullable, not mandatory)
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,593554 /*From ID Server*/,585459,0,37,208,'CoProductFixedCostPrice',TO_TIMESTAMP('2026-09-14 10:01:00','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,22,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Co-Product Fixkostenpreis','NP',0,0,TO_TIMESTAMP('2026-09-14 10:01:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- Seed AD_Column_Trl for every active system language
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593554
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

/* DDL */ SELECT update_Column_Translation_From_AD_Element(585459)
;

-- Physical column (nullable NUMERIC; view dependencies handled by db_alter_table)
/* DDL */ SELECT public.db_alter_table('M_Product','ALTER TABLE public.M_Product ADD COLUMN CoProductFixedCostPrice NUMERIC')
;

-- AD_Field on the main Produkt tab (180)
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy)
VALUES (0,593554,784976 /*From ID Server*/,0,180,0,TO_TIMESTAMP('2026-09-14 10:02:00','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','N','N','N','N','N','N','Co-Product Fixkostenpreis',0,0,0,1,1,TO_TIMESTAMP('2026-09-14 10:02:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Seed AD_Field_Trl for every active system language
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=784976
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(585459)
;

DELETE FROM AD_Element_Link WHERE AD_Field_ID=784976
;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(784976)
;

-- New "Product Costs" element group in the left column (1000004) of the Produkt tab main section
INSERT INTO AD_UI_ElementGroup (AD_UI_ElementGroup_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_UI_Column_ID,SeqNo,UIStyle,Name)
VALUES (555769 /*From ID Server*/,0,0,'Y',TO_TIMESTAMP('2026-09-14 10:02:20','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2026-09-14 10:02:20','YYYY-MM-DD HH24:MI:SS'),100,1000004,60,NULL,'Product Costs')
;

-- Place the field in the new group (form only; not in grid)
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,784976,0,180,555769,654744 /*From ID Server*/,'F',TO_TIMESTAMP('2026-09-14 10:02:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Co-Product Fixkostenpreis',10,0,0,TO_TIMESTAMP('2026-09-14 10:02:30','YYYY-MM-DD HH24:MI:SS'),100)
;
