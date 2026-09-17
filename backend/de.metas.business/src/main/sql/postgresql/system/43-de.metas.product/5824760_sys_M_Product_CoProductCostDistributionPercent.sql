-- Co-product valuation: add M_Product.CoProductCostDistributionPercent.
-- A manually maintained, overridable percentage share of production cost distribution (nullable)
-- read live at PP_Order cost creation; a co-product (ComponentType=CP) whose product carries a
-- non-blank value is relieved by that percentage share of the total cost instead of the
-- qty-distribution. Blank = zero-cost carve: the co-product is not opted in and receives no cost
-- at all, the main/finished product absorbs the entire cost pool. There is no fallback to the old
-- quantity-based distribution — that formula was removed by this change.
-- Rewrite-in-place of the branch-only, never-applied 5824340_sys_M_Product_CoProductFixedCostPrice.sql
-- scaffold: discards its fixed-price AD_Element 585459 / AD_Column 593554 and allocates fresh IDs
-- (see the id-reuse rule in CLAUDE.md "IDs and Sequence Numbers"). Field/UI-element placement on
-- window 344 / tab 700 is part of this same script (see Task 4 below).
--
-- IDs allocated from idserver.metas.de on 2026-09-16:
--   AD_Element 585471 (M_Product.CoProductCostDistributionPercent label)
--   AD_Column  593569 (M_Product.CoProductCostDistributionPercent)

-- AD_Element (base language = German)
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy)
VALUES (0,585471 /*From ID Server*/,0,'CoProductCostDistributionPercent',TO_TIMESTAMP('2026-09-16 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Manuell gepflegter, überschreibbarer Prozentsatz für die Kostenverteilung eines Co-Products. Leer = das Co-Product trägt keine Kosten (0%), das Hauptprodukt übernimmt die gesamten Kosten.','D','Der Wert wird bei der Erstellung der Produktionsauftragskosten live gelesen: Ein Co-Product mit gesetztem Kostenverteilungsanteil wird mit diesem Prozentsatz der Gesamtkosten bewertet, das Hauptprodukt trägt den Rest. Leer lassen, wenn das Co-Product keine Kosten tragen soll (0%) — es gibt keinen automatischen mengenbasierten Rückfall.','Y','Co-Product Kostenverteilungsanteil','Co-Product Kostenverteilungsanteil',TO_TIMESTAMP('2026-09-16 10:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Seed AD_Element_Trl for every active system language (copies base German text)
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585471
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- English override
UPDATE AD_Element_Trl SET Name='Co-Product Cost Distribution Percent', PrintName='Co-Product Cost Distribution Percent', Description='Manually maintained, overridable percentage share of cost distribution for a co-product. Blank = the co-product receives zero cost; the main product absorbs the entire cost pool.', Help='Read live at PP_Order cost creation: a co-product carrying a distribution percent is valued at that percentage share of the total cost, and the main product is relieved by the remainder. Leave blank if the co-product should carry no cost (0%) — there is no fallback to quantity-based distribution.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-16 10:00:12','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=585471 AND AD_Language='en_US'
;

-- de_DE/de_CH rows already carry the correct German base text (seeded from AD_Element above);
-- flip them to actively-translated (convention: see sibling 5824770_sys_AD_Message_..._OutOfRange.sql).
UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-16 10:00:13','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=585471 AND AD_Language='de_DE'
;
UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-16 10:00:14','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=585471 AND AD_Language='de_CH'
;

-- AD_Column (reference 22 = Number, the metasfresh convention for a percent value — see e.g.
-- GL_DistributionLine.Percent; there is no dedicated "Percent" AD_Reference. Nullable, not mandatory.)
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,593569 /*From ID Server*/,585471,0,22,208,'CoProductCostDistributionPercent',TO_TIMESTAMP('2026-09-16 10:01:00','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,22,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Co-Product Kostenverteilungsanteil','NP',0,0,TO_TIMESTAMP('2026-09-16 10:01:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- Seed AD_Column_Trl for every active system language
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593569
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

/* DDL */ SELECT update_Column_Translation_From_AD_Element(585471)
;

-- Physical column (nullable NUMERIC; view dependencies handled by db_alter_table)
/* DDL */ SELECT public.db_alter_table('M_Product','ALTER TABLE public.M_Product ADD COLUMN CoProductCostDistributionPercent NUMERIC')
;

-- ============================================================================
-- Task 4: place the field DIRECTLY on the core Product Costs window 344 / main
-- M_Product tab 700 — fresh AD_Field / AD_UI_Element / AD_UI_ElementGroup IDs,
-- discarding the old 784976 / 654744 / 555769. Never placed on window 140 / tab 180
-- (or any other window) — nothing to remove there.
--
-- Resolved via psql against the local intensive_care_uat stack (2026-09-16):
--   AD_Window 344 "Produktkosten" -> main tab AD_Tab 700 (M_Product, SeqNo 10, TabLevel 0)
--   Tab 700's main AD_UI_Section 540424 -> left AD_UI_Column 540567, which already holds
--   the primary group 541007 ("default": Name/Value/UOM/ProductType/Category) and the
--   secondary group 541008 ("description", SeqNo 20). The new group is placed as a further
--   secondary (non-primary) group in that same left column, after "description".
--
-- IDs allocated from idserver.metas.de on 2026-09-16:
--   AD_UI_ElementGroup 555771
--   AD_Field            784982
--   AD_UI_Element        654750
-- ============================================================================

-- New secondary element group in the left column (540567) of tab 700's main section.
-- 2026-09-16 10:02:00
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy)
VALUES (0,0,540567,555771 /*From ID Server*/,TO_TIMESTAMP('2026-09-16 10:02:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','costing',30,NULL,TO_TIMESTAMP('2026-09-16 10:02:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field on tab 700 for column 593569 / element 585471 (both added earlier in this script).
-- Not shown in grid/side-list (niche co-product-only setting); Description/Help left NULL so
-- AD_Field_v/vt fall back to the column's element-driven text.
-- 2026-09-16 10:02:01
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,Updated,UpdatedBy)
VALUES (0,593569,784982 /*From ID Server*/,0,700,TO_TIMESTAMP('2026-09-16 10:02:01','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Y','N','N','N','N','N','N','Co-Product Kostenverteilungsanteil',140,0,0,TO_TIMESTAMP('2026-09-16 10:02:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Seed AD_Field_Trl for every active system language
-- 2026-09-16 10:02:02
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Name,Description,Help, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Name,t.Description,t.Help, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=784982
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Propagate field translations (DE base + en_US) from the column's element 585471.
-- 2026-09-16 10:02:12
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585471)
;

-- Rebuild element link for the new field.
-- 2026-09-16 10:02:13
DELETE FROM AD_Element_Link WHERE AD_Field_ID=784982
;
-- 2026-09-16 10:02:14
/* DDL */ select AD_Element_Link_Create_Missing_Field(784982)
;

-- UI element placing the field in the new "costing" group (first and only field in it).
-- Not a side-list field (only the primary group's Name/Value/UOM + Sektion are), no advanced
-- edit, no grid column, WidgetSize left NULL (matches sibling secondary-group fields).
-- 2026-09-16 10:02:15
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy)
VALUES (0,784982,0,700,654750 /*From ID Server*/,555771,'F',TO_TIMESTAMP('2026-09-16 10:02:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','Co-Product Kostenverteilungsanteil',10,0,0,TO_TIMESTAMP('2026-09-16 10:02:15','YYYY-MM-DD HH24:MI:SS'),100)
;
