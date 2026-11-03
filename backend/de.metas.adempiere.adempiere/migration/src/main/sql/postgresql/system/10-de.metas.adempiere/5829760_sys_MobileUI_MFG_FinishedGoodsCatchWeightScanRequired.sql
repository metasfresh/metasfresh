-- MobileUI Manufacturing — add IsFinishedGoodsCatchWeightScanRequired config field for the mobile production receipt flow.
-- When set, a catch-weight MAIN finished good can only be received by scanning a code: the scan pre-fills quantity 1, weight,
-- lot number and best-before date for review, the operator confirms, manual input is not offered, and a code without a lot number
-- or a valid best-before date is rejected. Unset (the default) keeps receive-on-scan with manual input available.
-- Co- and by-product lines are never affected; hence the FinishedGoods qualifier in column name and label.
-- Mirrors the sibling flags of 5819440:
--  - MobileUI_MFG_Config (542397): global config, YesNo (ref 20), NOT NULL DEFAULT 'N'.
--  - MobileUI_UserProfile_MFG (542263): per-user override, three-state YesNo (ref 17 + 319), nullable (NULL = inherit global).
-- Shared AD_Element, referenced by both tables' columns.
-- IDs from the central ID server (2026-10-19): AD_Element 585547; AD_Column 593753 (MobileUI_MFG_Config), 593754 (MobileUI_UserProfile_MFG);
-- AD_Field 785692 (tab 547483), 785693 (tab 546679); AD_UI_Element 655049 (group 551690), 655050 (group 550042).

-- ============================================================================
-- AD_Element: IsFinishedGoodsCatchWeightScanRequired (585547)
-- ============================================================================

-- 2026-10-19 10:00:00
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,585547 /*From ID Server*/,0,'IsFinishedGoodsCatchWeightScanRequired',TO_TIMESTAMP('2026-10-19 10:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Fertigprodukt: Catch-Weight-Scan erforderlich','Fertigprodukt: Catch-Weight-Scan erforderlich',TO_TIMESTAMP('2026-10-19 10:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-10-19 10:00:00 (seed _Trl rows for all system/base languages — copies element time)
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585547 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2026-10-19 10:00:12 (en_US override — strictly later than the element INSERT)
UPDATE AD_Element_Trl SET Name='Finished goods: require catch-weight scan', PrintName='Finished goods: require catch-weight scan', Description='Receiving a catch-weight finished good at production receipt requires a scan.', Help='When set, receiving a catch-weight finished good on the mobile production receipt requires scanning a code (L&M or QR code): the scan pre-fills quantity 1, weight, lot number and best-before date for review and the operator confirms. Manual input is not offered, and a code without a lot number or without a valid best-before date is rejected. Unset (default): the scan receives right away and manual input stays available. Applies to the main finished-good line only — co- and by-products are not affected.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-19 10:00:12.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585547 AND AD_Language='en_US'
;

-- 2026-10-19 10:00:13 (de_DE override — set description/help + flip IsTranslated)
UPDATE AD_Element_Trl SET Name='Fertigprodukt: Catch-Weight-Scan erforderlich', Description='Bei der Produktionsentnahme muss ein Catch-Weight-Fertigprodukt per Scan erfasst werden.', Help='Wenn gesetzt, muss bei der mobilen Produktionsentnahme eines Catch-Weight-Fertigprodukts ein Code (L&M- oder QR-Code) gescannt werden: der Scan füllt Menge 1, Gewicht, Chargennr. und MHD zur Prüfung vor, der Bediener bestätigt. Die manuelle Eingabe wird nicht angeboten, und ein Code ohne Chargennr. oder ohne gültiges MHD wird abgelehnt. Nicht gesetzt (Standard): der Scan bucht sofort, die manuelle Eingabe bleibt verfügbar. Gilt nur für die Zeile des Hauptfertigprodukts – Kuppel- und Nebenprodukte sind nicht betroffen.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-19 10:00:13.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585547 AND AD_Language='de_DE'
;

-- 2026-10-19 10:00:14 (de_CH override — set description/help + flip IsTranslated)
UPDATE AD_Element_Trl SET Name='Fertigprodukt: Catch-Weight-Scan erforderlich', Description='Bei der Produktionsentnahme muss ein Catch-Weight-Fertigprodukt per Scan erfasst werden.', Help='Wenn gesetzt, muss bei der mobilen Produktionsentnahme eines Catch-Weight-Fertigprodukts ein Code (L&M- oder QR-Code) gescannt werden: der Scan füllt Menge 1, Gewicht, Chargennr. und MHD zur Prüfung vor, der Bediener bestätigt. Die manuelle Eingabe wird nicht angeboten, und ein Code ohne Chargennr. oder ohne gültiges MHD wird abgelehnt. Nicht gesetzt (Standard): der Scan bucht sofort, die manuelle Eingabe bleibt verfügbar. Gilt nur für die Zeile des Hauptfertigprodukts – Kuppel- und Nebenprodukte sind nicht betroffen.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-19 10:00:14.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585547 AND AD_Language='de_CH'
;

-- ============================================================================
-- AD_Column
-- ============================================================================

-- Column: MobileUI_MFG_Config.IsFinishedGoodsCatchWeightScanRequired (593753) — global config, YesNo (20), NOT NULL, DefaultValue='N'
-- 2026-10-19 10:01:00
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593753 /*From ID Server*/,585547,0,20,542397,'XX','IsFinishedGoodsCatchWeightScanRequired',TO_TIMESTAMP('2026-10-19 10:01:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Fertigprodukt: Catch-Weight-Scan erforderlich','NP',0,0,TO_TIMESTAMP('2026-10-19 10:01:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-10-19 10:01:00 (seed _Trl)
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593753 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Column: MobileUI_UserProfile_MFG.IsFinishedGoodsCatchWeightScanRequired (593754) — per-user override, three-state YesNo (17 + 319), nullable
-- 2026-10-19 10:01:10
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593754 /*From ID Server*/,585547,0,17,319,542263,'XX','IsFinishedGoodsCatchWeightScanRequired',TO_TIMESTAMP('2026-10-19 10:01:10.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Fertigprodukt: Catch-Weight-Scan erforderlich','NP',0,0,TO_TIMESTAMP('2026-10-19 10:01:10.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-10-19 10:01:10 (seed _Trl)
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593754 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-10-19 10:02:00 (propagate element translations to the column _Trl rows)
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585547)
;

-- ============================================================================
-- Physical DB columns
-- ============================================================================

-- 2026-10-19 10:03:00
/* DDL */ SELECT public.db_alter_table('MobileUI_MFG_Config','ALTER TABLE public.MobileUI_MFG_Config ADD COLUMN IsFinishedGoodsCatchWeightScanRequired CHAR(1) DEFAULT ''N'' CHECK (IsFinishedGoodsCatchWeightScanRequired IN (''Y'',''N'')) NOT NULL')
;

-- 2026-10-19 10:03:01
/* DDL */ SELECT public.db_alter_table('MobileUI_UserProfile_MFG','ALTER TABLE public.MobileUI_UserProfile_MFG ADD COLUMN IsFinishedGoodsCatchWeightScanRequired CHAR(1) CHECK (IsFinishedGoodsCatchWeightScanRequired IN (''Y'',''N''))')
;

-- ============================================================================
-- AD_Field
-- ============================================================================

-- Field: MobileUI Manufacturing Configuration(541788,D) -> MobileUI Manufacturing Configuration(547483,D) -> Fertigprodukt: Catch-Weight-Scan erforderlich (785692)
-- Column: MobileUI_MFG_Config.IsFinishedGoodsCatchWeightScanRequired
-- 2026-10-19 10:04:00
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,593753,785692 /*From ID Server*/,0,547483,TO_TIMESTAMP('2026-10-19 10:04:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Fertigprodukt: Catch-Weight-Scan erforderlich',TO_TIMESTAMP('2026-10-19 10:04:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-10-19 10:04:00 (seed _Trl)
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=785692 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Field: Nutzer(108,D) -> Mobile UI Nutzerprofil - Produktion(546679,D) -> Fertigprodukt: Catch-Weight-Scan erforderlich (785693)
-- Column: MobileUI_UserProfile_MFG.IsFinishedGoodsCatchWeightScanRequired
-- 2026-10-19 10:04:10
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,593754,785693 /*From ID Server*/,0,546679,TO_TIMESTAMP('2026-10-19 10:04:10.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Fertigprodukt: Catch-Weight-Scan erforderlich',TO_TIMESTAMP('2026-10-19 10:04:10.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-10-19 10:04:10 (seed _Trl)
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=785693 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- ============================================================================
-- Propagate element translations to field _Trl rows + rebuild element links
-- ============================================================================

-- 2026-10-19 10:05:00
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585547)
;

-- 2026-10-19 10:05:01
DELETE FROM AD_Element_Link WHERE AD_Field_ID IN (785692,785693)
;

-- 2026-10-19 10:05:02
/* DDL */ select AD_Element_Link_Create_Missing_Field(785692)
;

-- 2026-10-19 10:05:03
/* DDL */ select AD_Element_Link_Create_Missing_Field(785693)
;

-- ============================================================================
-- AD_UI_Element — after the catch-weight flag (SeqNo 90) in both groups
-- ============================================================================

-- UI Element: MobileUI Manufacturing Configuration -> config group -> 100 -> Fertigprodukt: Catch-Weight-Scan erforderlich (655049)
-- Column: MobileUI_MFG_Config.IsFinishedGoodsCatchWeightScanRequired
-- 2026-10-19 10:06:00
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,785692,0,547483,551690,655049 /*From ID Server*/,'F',TO_TIMESTAMP('2026-10-19 10:06:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','Y','N','Fertigprodukt: Catch-Weight-Scan erforderlich',100,100,0,TO_TIMESTAMP('2026-10-19 10:06:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Nutzer -> Mobile UI Nutzerprofil - Produktion -> primary group -> 100 -> Fertigprodukt: Catch-Weight-Scan erforderlich (655050)
-- Column: MobileUI_UserProfile_MFG.IsFinishedGoodsCatchWeightScanRequired
-- 2026-10-19 10:06:10
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,785693,0,546679,550042,655050 /*From ID Server*/,'F',TO_TIMESTAMP('2026-10-19 10:06:10.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','Y','N','Fertigprodukt: Catch-Weight-Scan erforderlich',100,100,0,TO_TIMESTAMP('2026-10-19 10:06:10.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
