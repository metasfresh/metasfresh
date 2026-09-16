-- Co-product valuation: add M_Product.CoProductCostDistributionPercent.
-- A manually maintained, overridable percentage share of production cost distribution (nullable)
-- read live at PP_Order cost creation; a co-product (ComponentType=CP) whose product carries a
-- non-blank value is relieved by that percentage share of the total cost instead of the
-- qty-distribution. Blank = today's behaviour (opt-in, no seed).
-- Rewrite-in-place of the branch-only, never-applied 5824340_sys_M_Product_CoProductFixedCostPrice.sql
-- scaffold: discards its fixed-price AD_Element 585459 / AD_Column 593554 and allocates fresh IDs
-- (see the id-reuse rule in CLAUDE.md "IDs and Sequence Numbers"). Field/UI-element placement on
-- window 344 / tab 700 is defined separately (not part of this script).
--
-- IDs allocated from idserver.metas.de on 2026-09-16:
--   AD_Element 585471 (M_Product.CoProductCostDistributionPercent label)
--   AD_Column  593569 (M_Product.CoProductCostDistributionPercent)

-- AD_Element (base language = German)
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy)
VALUES (0,585471 /*From ID Server*/,0,'CoProductCostDistributionPercent',TO_TIMESTAMP('2026-09-16 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Manuell gepflegter, überschreibbarer Prozentsatz für die Kostenverteilung eines Co-Products. Leer = bisherige mengenbasierte Verteilung.','D','Der Wert wird bei der Erstellung der Produktionsauftragskosten live gelesen: Ein Co-Product mit gesetztem Kostenverteilungsanteil wird mit diesem Prozentsatz der Gesamtkosten bewertet, das Hauptprodukt trägt den Rest. Leer lassen, um die mengenbasierte Verteilung beizubehalten.','Y','Co-Product Kostenverteilungsanteil','Co-Product Kostenverteilungsanteil',TO_TIMESTAMP('2026-09-16 10:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Seed AD_Element_Trl for every active system language (copies base German text)
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585471
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- English override
UPDATE AD_Element_Trl SET Name='Co-Product Cost Distribution Percent', PrintName='Co-Product Cost Distribution Percent', Description='Manually maintained, overridable percentage share of cost distribution for a co-product. Blank = today''s quantity-based distribution.', Help='Read live at PP_Order cost creation: a co-product carrying a distribution percent is valued at that percentage share of the total cost, and the main product is relieved by the remainder. Leave blank to keep the quantity-based distribution.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-16 10:00:12','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=585471 AND AD_Language='en_US'
;

-- Mark the German rows as actively translated (text already German from the base)
UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-16 10:00:14','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=585471 AND AD_Language='de_DE'
;
UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-16 10:00:16','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
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
