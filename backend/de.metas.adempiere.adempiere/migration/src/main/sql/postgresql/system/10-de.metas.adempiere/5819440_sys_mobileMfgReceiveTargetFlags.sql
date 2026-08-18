-- MobileUI Manufacturing — add IsAllowReceiveToLU / IsAllowReceiveToTU / IsSkipReceiveTargetStep /
-- IsCaptureCatchWeightAtReceipt config fields for the mobile production receipt flow.
-- Four YesNo config fields gating the mobile Produktion receive flow: which Gebinde targets (LU/TU)
-- are offered, whether the target-choice screen is skipped, and whether catch weight is captured at
-- receipt. Mirrors IsBestBeforeDateEditable / IsLotNumberEditable (5809490).
--  - MobileUI_MFG_Config (542397): global config, YesNo (ref 20), NOT NULL DEFAULT per flag (Y/Y/N/Y).
--  - MobileUI_UserProfile_MFG (542263): per-user override, three-state YesNo (ref 17 + 319), nullable (NULL = inherit global).
-- Shared AD_Element per flag, referenced by both tables' columns.

-- ============================================================================
-- AD_Element: IsAllowReceiveToLU (585305)
-- ============================================================================

-- 2026-08-17 09:00:00
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,585305 /*From ID Server*/,0,'IsAllowReceiveToLU',TO_TIMESTAMP('2026-08-17 09:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Wareneingang auf Ladeeinheit (LU) erlauben','Wareneingang auf Ladeeinheit (LU) erlauben',TO_TIMESTAMP('2026-08-17 09:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-08-17 09:00:00 (seed _Trl rows for all system/base languages — copies element time)
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585305 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2026-08-17 09:00:12 (en_US override — strictly later than the element INSERT)
UPDATE AD_Element_Trl SET Name='Allow receiving to LU', PrintName='Allow receiving to LU', Description='Offer load-unit (pallet/LU) targets on the production receipt.', Help='When set, the mobile manufacturing receipt offers LU (pallet) Gebinde as receiving targets for the main finished product. Unset to hide LU targets. Applies to the main finished-good line only — co- and by-products are not affected.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-17 09:00:12.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585305 AND AD_Language='en_US'
;

-- 2026-08-17 09:00:13 (de_DE override — set description/help + flip IsTranslated)
UPDATE AD_Element_Trl SET Name='Wareneingang auf Ladeeinheit (LU) erlauben', Description='Ladeeinheit (Palette/LU) als Ziel-Gebinde im Produktions-Wareneingang anbieten.', Help='Wenn gesetzt, bietet der mobile Produktions-Wareneingang Ladeeinheiten (Paletten/LU) als Ziel-Gebinde für das Hauptfertigprodukt an. Deaktivieren, um LU-Ziele auszublenden. Gilt nur für die Zeile des Hauptfertigprodukts – Kuppel- und Nebenprodukte sind nicht betroffen.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-17 09:00:13.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585305 AND AD_Language='de_DE'
;

-- 2026-08-17 09:00:14 (de_CH override — same as de_DE)
UPDATE AD_Element_Trl SET Name='Wareneingang auf Ladeeinheit (LU) erlauben', Description='Ladeeinheit (Palette/LU) als Ziel-Gebinde im Produktions-Wareneingang anbieten.', Help='Wenn gesetzt, bietet der mobile Produktions-Wareneingang Ladeeinheiten (Paletten/LU) als Ziel-Gebinde für das Hauptfertigprodukt an. Deaktivieren, um LU-Ziele auszublenden. Gilt nur für die Zeile des Hauptfertigprodukts – Kuppel- und Nebenprodukte sind nicht betroffen.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-17 09:00:14.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585305 AND AD_Language='de_CH'
;

-- ============================================================================
-- AD_Element: IsAllowReceiveToTU (585306)
-- ============================================================================

-- 2026-08-17 09:00:20
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,585306 /*From ID Server*/,0,'IsAllowReceiveToTU',TO_TIMESTAMP('2026-08-17 09:00:20.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Wareneingang auf Transporteinheit (TU) erlauben','Wareneingang auf Transporteinheit (TU) erlauben',TO_TIMESTAMP('2026-08-17 09:00:20.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-08-17 09:00:20 (seed _Trl rows for all system/base languages — copies element time)
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585306 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2026-08-17 09:00:32 (en_US override)
UPDATE AD_Element_Trl SET Name='Allow receiving to TU', PrintName='Allow receiving to TU', Description='Offer transport-unit (TU) targets on the production receipt.', Help='When set, the mobile manufacturing receipt offers TU Gebinde as receiving targets for the main finished product. Unset so that only LU (pallet) targets are offered. Applies to the main finished-good line only — co- and by-products keep their TU targets (including infinite-capacity Gebinde).', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-17 09:00:32.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585306 AND AD_Language='en_US'
;

-- 2026-08-17 09:00:33 (de_DE override)
UPDATE AD_Element_Trl SET Name='Wareneingang auf Transporteinheit (TU) erlauben', Description='Transporteinheit (TU) als Ziel-Gebinde im Produktions-Wareneingang anbieten.', Help='Wenn gesetzt, bietet der mobile Produktions-Wareneingang Transporteinheiten (TU) als Ziel-Gebinde für das Hauptfertigprodukt an. Deaktivieren, damit nur Ladeeinheiten (Paletten/LU) angeboten werden. Gilt nur für die Zeile des Hauptfertigprodukts – Kuppel- und Nebenprodukte behalten ihre TU-Ziele (auch Gebinde mit unbegrenzter Kapazität).', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-17 09:00:33.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585306 AND AD_Language='de_DE'
;

-- 2026-08-17 09:00:34 (de_CH override — same as de_DE)
UPDATE AD_Element_Trl SET Name='Wareneingang auf Transporteinheit (TU) erlauben', Description='Transporteinheit (TU) als Ziel-Gebinde im Produktions-Wareneingang anbieten.', Help='Wenn gesetzt, bietet der mobile Produktions-Wareneingang Transporteinheiten (TU) als Ziel-Gebinde für das Hauptfertigprodukt an. Deaktivieren, damit nur Ladeeinheiten (Paletten/LU) angeboten werden. Gilt nur für die Zeile des Hauptfertigprodukts – Kuppel- und Nebenprodukte behalten ihre TU-Ziele (auch Gebinde mit unbegrenzter Kapazität).', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-17 09:00:34.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585306 AND AD_Language='de_CH'
;

-- ============================================================================
-- AD_Element: IsSkipReceiveTargetStep (585307)
-- ============================================================================

-- 2026-08-17 09:00:40
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,585307 /*From ID Server*/,0,'IsSkipReceiveTargetStep',TO_TIMESTAMP('2026-08-17 09:00:40.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Schritt Gebinde-Auswahl überspringen','Schritt Gebinde-Auswahl überspringen',TO_TIMESTAMP('2026-08-17 09:00:40.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-08-17 09:00:40 (seed _Trl rows for all system/base languages — copies element time)
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585307 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2026-08-17 09:00:52 (en_US override)
UPDATE AD_Element_Trl SET Name='Skip receiving-target step', PrintName='Skip receiving-target step', Description='Skip the new-Gebinde / scan-existing screen and go straight to the packing instruction.', Help='When set, the mobile manufacturing receipt skips the intermediate target-choice screen (new Gebinde vs. scan an existing one) for the main finished product and goes directly to the packing-instruction (Packvorschrift) list. Use when finished goods are always received onto a new pallet. Applies to the main finished-good line only.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-17 09:00:52.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585307 AND AD_Language='en_US'
;

-- 2026-08-17 09:00:53 (de_DE override)
UPDATE AD_Element_Trl SET Name='Schritt Gebinde-Auswahl überspringen', Description='Bildschirm „Neues Gebinde / bestehendes scannen“ überspringen und direkt zur Packvorschrift springen.', Help='Wenn gesetzt, überspringt der mobile Produktions-Wareneingang für das Hauptfertigprodukt den Zwischenschritt zur Gebinde-Auswahl (neues Gebinde vs. bestehendes scannen) und geht direkt zur Packvorschrift-Liste. Verwenden, wenn Fertigprodukte immer auf eine neue Palette eingelagert werden. Gilt nur für die Zeile des Hauptfertigprodukts.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-17 09:00:53.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585307 AND AD_Language='de_DE'
;

-- 2026-08-17 09:00:54 (de_CH override — same as de_DE)
UPDATE AD_Element_Trl SET Name='Schritt Gebinde-Auswahl überspringen', Description='Bildschirm „Neues Gebinde / bestehendes scannen“ überspringen und direkt zur Packvorschrift springen.', Help='Wenn gesetzt, überspringt der mobile Produktions-Wareneingang für das Hauptfertigprodukt den Zwischenschritt zur Gebinde-Auswahl (neues Gebinde vs. bestehendes scannen) und geht direkt zur Packvorschrift-Liste. Verwenden, wenn Fertigprodukte immer auf eine neue Palette eingelagert werden. Gilt nur für die Zeile des Hauptfertigprodukts.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-17 09:00:54.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585307 AND AD_Language='de_CH'
;

-- ============================================================================
-- AD_Element: IsCaptureCatchWeightAtReceipt (585308)
-- ============================================================================

-- 2026-08-17 09:01:00
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,585308 /*From ID Server*/,0,'IsCaptureCatchWeightAtReceipt',TO_TIMESTAMP('2026-08-17 09:01:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Catch Weight beim Wareneingang erfassen','Catch Weight beim Wareneingang erfassen',TO_TIMESTAMP('2026-08-17 09:01:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-08-17 09:01:00 (seed _Trl rows for all system/base languages — copies element time)
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585308 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2026-08-17 09:01:12 (en_US override)
UPDATE AD_Element_Trl SET Name='Capture catch weight at receipt', PrintName='Capture catch weight at receipt', Description='Capture the catch weight of a catch-weight product at production receipt.', Help='When set (default), a catch-weight product prompts for its catch weight at production receipt. Unset so that no catch weight is captured at receipt — only the nominal quantity is entered — and the weight is captured later (e.g. at picking). Applies to the main finished-good line only; co- and by-products (e.g. into an infinite-capacity Gebinde, where the catch weight is the quantity) always keep catch-weight capture.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-17 09:01:12.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585308 AND AD_Language='en_US'
;

-- 2026-08-17 09:01:13 (de_DE override)
UPDATE AD_Element_Trl SET Name='Catch Weight beim Wareneingang erfassen', Description='Catch Weight eines Catch-Weight-Produkts beim Produktions-Wareneingang erfassen.', Help='Wenn gesetzt (Standard), wird bei einem Catch-Weight-Produkt das Catch Weight beim Produktions-Wareneingang abgefragt. Deaktivieren, damit beim Wareneingang kein Catch Weight erfasst wird – es wird nur die nominale Menge eingegeben – und das Gewicht später erfasst wird (z. B. bei der Kommissionierung). Gilt nur für die Zeile des Hauptfertigprodukts; Kuppel- und Nebenprodukte (z. B. in ein Gebinde mit unbegrenzter Kapazität, bei dem das Catch Weight die Menge ist) behalten die Catch-Weight-Erfassung immer.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-17 09:01:13.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585308 AND AD_Language='de_DE'
;

-- 2026-08-17 09:01:14 (de_CH override — same as de_DE)
UPDATE AD_Element_Trl SET Name='Catch Weight beim Wareneingang erfassen', Description='Catch Weight eines Catch-Weight-Produkts beim Produktions-Wareneingang erfassen.', Help='Wenn gesetzt (Standard), wird bei einem Catch-Weight-Produkt das Catch Weight beim Produktions-Wareneingang abgefragt. Deaktivieren, damit beim Wareneingang kein Catch Weight erfasst wird – es wird nur die nominale Menge eingegeben – und das Gewicht später erfasst wird (z. B. bei der Kommissionierung). Gilt nur für die Zeile des Hauptfertigprodukts; Kuppel- und Nebenprodukte (z. B. in ein Gebinde mit unbegrenzter Kapazität, bei dem das Catch Weight die Menge ist) behalten die Catch-Weight-Erfassung immer.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-17 09:01:14.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585308 AND AD_Language='de_CH'
;

-- ============================================================================
-- AD_Column: MobileUI_MFG_Config (542397) — global config, YesNo (20), NOT NULL DEFAULT per flag
-- ============================================================================

-- Column: MobileUI_MFG_Config.IsAllowReceiveToLU (593322) — DefaultValue='Y'
-- 2026-08-17 09:02:00
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593322 /*From ID Server*/,585305,0,20,542397,'XX','IsAllowReceiveToLU',TO_TIMESTAMP('2026-08-17 09:02:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Y','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Wareneingang auf Ladeeinheit (LU) erlauben','NP',0,0,TO_TIMESTAMP('2026-08-17 09:02:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-08-17 09:02:00 (seed _Trl)
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593322 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Column: MobileUI_MFG_Config.IsAllowReceiveToTU (593323) — DefaultValue='Y'
-- 2026-08-17 09:02:10
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593323 /*From ID Server*/,585306,0,20,542397,'XX','IsAllowReceiveToTU',TO_TIMESTAMP('2026-08-17 09:02:10.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Y','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Wareneingang auf Transporteinheit (TU) erlauben','NP',0,0,TO_TIMESTAMP('2026-08-17 09:02:10.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-08-17 09:02:10 (seed _Trl)
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593323 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Column: MobileUI_MFG_Config.IsSkipReceiveTargetStep (593324) — DefaultValue='N'
-- 2026-08-17 09:02:20
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593324 /*From ID Server*/,585307,0,20,542397,'XX','IsSkipReceiveTargetStep',TO_TIMESTAMP('2026-08-17 09:02:20.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Schritt Gebinde-Auswahl überspringen','NP',0,0,TO_TIMESTAMP('2026-08-17 09:02:20.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-08-17 09:02:20 (seed _Trl)
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593324 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Column: MobileUI_MFG_Config.IsCaptureCatchWeightAtReceipt (593325) — DefaultValue='Y'
-- 2026-08-17 09:02:30
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593325 /*From ID Server*/,585308,0,20,542397,'XX','IsCaptureCatchWeightAtReceipt',TO_TIMESTAMP('2026-08-17 09:02:30.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Y','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Catch Weight beim Wareneingang erfassen','NP',0,0,TO_TIMESTAMP('2026-08-17 09:02:30.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-08-17 09:02:30 (seed _Trl)
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593325 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- ============================================================================
-- AD_Column: MobileUI_UserProfile_MFG (542263) — per-user override, three-state YesNo (17 + 319), nullable, DefaultValue=''
-- ============================================================================

-- Column: MobileUI_UserProfile_MFG.IsAllowReceiveToLU (593326)
-- 2026-08-17 09:03:00
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593326 /*From ID Server*/,585305,0,17,319,542263,'XX','IsAllowReceiveToLU',TO_TIMESTAMP('2026-08-17 09:03:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Wareneingang auf Ladeeinheit (LU) erlauben','NP',0,0,TO_TIMESTAMP('2026-08-17 09:03:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-08-17 09:03:00 (seed _Trl)
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593326 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Column: MobileUI_UserProfile_MFG.IsAllowReceiveToTU (593327)
-- 2026-08-17 09:03:10
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593327 /*From ID Server*/,585306,0,17,319,542263,'XX','IsAllowReceiveToTU',TO_TIMESTAMP('2026-08-17 09:03:10.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Wareneingang auf Transporteinheit (TU) erlauben','NP',0,0,TO_TIMESTAMP('2026-08-17 09:03:10.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-08-17 09:03:10 (seed _Trl)
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593327 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Column: MobileUI_UserProfile_MFG.IsSkipReceiveTargetStep (593328)
-- 2026-08-17 09:03:20
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593328 /*From ID Server*/,585307,0,17,319,542263,'XX','IsSkipReceiveTargetStep',TO_TIMESTAMP('2026-08-17 09:03:20.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Schritt Gebinde-Auswahl überspringen','NP',0,0,TO_TIMESTAMP('2026-08-17 09:03:20.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-08-17 09:03:20 (seed _Trl)
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593328 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Column: MobileUI_UserProfile_MFG.IsCaptureCatchWeightAtReceipt (593329)
-- 2026-08-17 09:03:30
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593329 /*From ID Server*/,585308,0,17,319,542263,'XX','IsCaptureCatchWeightAtReceipt',TO_TIMESTAMP('2026-08-17 09:03:30.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Catch Weight beim Wareneingang erfassen','NP',0,0,TO_TIMESTAMP('2026-08-17 09:03:30.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-08-17 09:03:30 (seed _Trl)
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593329 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- ============================================================================
-- Propagate element translations to the column _Trl rows
-- ============================================================================

-- 2026-08-17 09:04:00
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585305)
;

-- 2026-08-17 09:04:01
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585306)
;

-- 2026-08-17 09:04:02
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585307)
;

-- 2026-08-17 09:04:03
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585308)
;

-- ============================================================================
-- Physical DB columns
-- ============================================================================

-- 2026-08-17 09:05:00
/* DDL */ SELECT public.db_alter_table('MobileUI_MFG_Config','ALTER TABLE public.MobileUI_MFG_Config ADD COLUMN IsAllowReceiveToLU CHAR(1) DEFAULT ''Y'' CHECK (IsAllowReceiveToLU IN (''Y'',''N'')) NOT NULL')
;

-- 2026-08-17 09:05:01
/* DDL */ SELECT public.db_alter_table('MobileUI_MFG_Config','ALTER TABLE public.MobileUI_MFG_Config ADD COLUMN IsAllowReceiveToTU CHAR(1) DEFAULT ''Y'' CHECK (IsAllowReceiveToTU IN (''Y'',''N'')) NOT NULL')
;

-- 2026-08-17 09:05:02
/* DDL */ SELECT public.db_alter_table('MobileUI_MFG_Config','ALTER TABLE public.MobileUI_MFG_Config ADD COLUMN IsSkipReceiveTargetStep CHAR(1) DEFAULT ''N'' CHECK (IsSkipReceiveTargetStep IN (''Y'',''N'')) NOT NULL')
;

-- 2026-08-17 09:05:03
/* DDL */ SELECT public.db_alter_table('MobileUI_MFG_Config','ALTER TABLE public.MobileUI_MFG_Config ADD COLUMN IsCaptureCatchWeightAtReceipt CHAR(1) DEFAULT ''Y'' CHECK (IsCaptureCatchWeightAtReceipt IN (''Y'',''N'')) NOT NULL')
;

-- 2026-08-17 09:05:04
/* DDL */ SELECT public.db_alter_table('MobileUI_UserProfile_MFG','ALTER TABLE public.MobileUI_UserProfile_MFG ADD COLUMN IsAllowReceiveToLU CHAR(1) CHECK (IsAllowReceiveToLU IN (''Y'',''N''))')
;

-- 2026-08-17 09:05:05
/* DDL */ SELECT public.db_alter_table('MobileUI_UserProfile_MFG','ALTER TABLE public.MobileUI_UserProfile_MFG ADD COLUMN IsAllowReceiveToTU CHAR(1) CHECK (IsAllowReceiveToTU IN (''Y'',''N''))')
;

-- 2026-08-17 09:05:06
/* DDL */ SELECT public.db_alter_table('MobileUI_UserProfile_MFG','ALTER TABLE public.MobileUI_UserProfile_MFG ADD COLUMN IsSkipReceiveTargetStep CHAR(1) CHECK (IsSkipReceiveTargetStep IN (''Y'',''N''))')
;

-- 2026-08-17 09:05:07
/* DDL */ SELECT public.db_alter_table('MobileUI_UserProfile_MFG','ALTER TABLE public.MobileUI_UserProfile_MFG ADD COLUMN IsCaptureCatchWeightAtReceipt CHAR(1) CHECK (IsCaptureCatchWeightAtReceipt IN (''Y'',''N''))')
;

-- ============================================================================
-- AD_Field: MobileUI Manufacturing Configuration tab (547483)
-- ============================================================================

-- Field: MobileUI Manufacturing Configuration(541788,D) -> MobileUI Manufacturing Configuration(547483,D) -> Wareneingang auf Ladeeinheit (LU) erlauben (782316)
-- Column: MobileUI_MFG_Config.IsAllowReceiveToLU
-- 2026-08-17 09:06:00
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,593322,782316 /*From ID Server*/,0,547483,TO_TIMESTAMP('2026-08-17 09:06:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Wareneingang auf Ladeeinheit (LU) erlauben',TO_TIMESTAMP('2026-08-17 09:06:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-08-17 09:06:00 (seed _Trl)
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=782316 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Field: MobileUI Manufacturing Configuration(541788,D) -> MobileUI Manufacturing Configuration(547483,D) -> Wareneingang auf Transporteinheit (TU) erlauben (782317)
-- Column: MobileUI_MFG_Config.IsAllowReceiveToTU
-- 2026-08-17 09:06:10
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,593323,782317 /*From ID Server*/,0,547483,TO_TIMESTAMP('2026-08-17 09:06:10.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Wareneingang auf Transporteinheit (TU) erlauben',TO_TIMESTAMP('2026-08-17 09:06:10.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-08-17 09:06:10 (seed _Trl)
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=782317 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Field: MobileUI Manufacturing Configuration(541788,D) -> MobileUI Manufacturing Configuration(547483,D) -> Schritt Gebinde-Auswahl überspringen (782318)
-- Column: MobileUI_MFG_Config.IsSkipReceiveTargetStep
-- 2026-08-17 09:06:20
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,593324,782318 /*From ID Server*/,0,547483,TO_TIMESTAMP('2026-08-17 09:06:20.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Schritt Gebinde-Auswahl überspringen',TO_TIMESTAMP('2026-08-17 09:06:20.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-08-17 09:06:20 (seed _Trl)
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=782318 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Field: MobileUI Manufacturing Configuration(541788,D) -> MobileUI Manufacturing Configuration(547483,D) -> Catch Weight beim Wareneingang erfassen (782319)
-- Column: MobileUI_MFG_Config.IsCaptureCatchWeightAtReceipt
-- 2026-08-17 09:06:30
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,593325,782319 /*From ID Server*/,0,547483,TO_TIMESTAMP('2026-08-17 09:06:30.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Catch Weight beim Wareneingang erfassen',TO_TIMESTAMP('2026-08-17 09:06:30.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-08-17 09:06:30 (seed _Trl)
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=782319 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- ============================================================================
-- AD_Field: MobileUI Nutzerprofil - Produktion tab (546679)
-- ============================================================================

-- Field: Nutzer(108,D) -> Mobile UI Nutzerprofil - Produktion(546679,D) -> Wareneingang auf Ladeeinheit (LU) erlauben (782320)
-- Column: MobileUI_UserProfile_MFG.IsAllowReceiveToLU
-- 2026-08-17 09:07:00
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,593326,782320 /*From ID Server*/,0,546679,TO_TIMESTAMP('2026-08-17 09:07:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Wareneingang auf Ladeeinheit (LU) erlauben',TO_TIMESTAMP('2026-08-17 09:07:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-08-17 09:07:00 (seed _Trl)
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=782320 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Field: Nutzer(108,D) -> Mobile UI Nutzerprofil - Produktion(546679,D) -> Wareneingang auf Transporteinheit (TU) erlauben (782321)
-- Column: MobileUI_UserProfile_MFG.IsAllowReceiveToTU
-- 2026-08-17 09:07:10
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,593327,782321 /*From ID Server*/,0,546679,TO_TIMESTAMP('2026-08-17 09:07:10.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Wareneingang auf Transporteinheit (TU) erlauben',TO_TIMESTAMP('2026-08-17 09:07:10.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-08-17 09:07:10 (seed _Trl)
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=782321 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Field: Nutzer(108,D) -> Mobile UI Nutzerprofil - Produktion(546679,D) -> Schritt Gebinde-Auswahl überspringen (782322)
-- Column: MobileUI_UserProfile_MFG.IsSkipReceiveTargetStep
-- 2026-08-17 09:07:20
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,593328,782322 /*From ID Server*/,0,546679,TO_TIMESTAMP('2026-08-17 09:07:20.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Schritt Gebinde-Auswahl überspringen',TO_TIMESTAMP('2026-08-17 09:07:20.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-08-17 09:07:20 (seed _Trl)
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=782322 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Field: Nutzer(108,D) -> Mobile UI Nutzerprofil - Produktion(546679,D) -> Catch Weight beim Wareneingang erfassen (782323)
-- Column: MobileUI_UserProfile_MFG.IsCaptureCatchWeightAtReceipt
-- 2026-08-17 09:07:30
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,593329,782323 /*From ID Server*/,0,546679,TO_TIMESTAMP('2026-08-17 09:07:30.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Catch Weight beim Wareneingang erfassen',TO_TIMESTAMP('2026-08-17 09:07:30.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-08-17 09:07:30 (seed _Trl)
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=782323 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- ============================================================================
-- Propagate element translations to field _Trl rows + rebuild element links
-- ============================================================================

-- 2026-08-17 09:08:00
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585305)
;

-- 2026-08-17 09:08:01
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585306)
;

-- 2026-08-17 09:08:02
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585307)
;

-- 2026-08-17 09:08:03
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585308)
;

-- 2026-08-17 09:08:04
DELETE FROM AD_Element_Link WHERE AD_Field_ID IN (782316,782317,782318,782319,782320,782321,782322,782323)
;

-- 2026-08-17 09:08:05
/* DDL */ select AD_Element_Link_Create_Missing_Field(782316)
;

-- 2026-08-17 09:08:06
/* DDL */ select AD_Element_Link_Create_Missing_Field(782317)
;

-- 2026-08-17 09:08:07
/* DDL */ select AD_Element_Link_Create_Missing_Field(782318)
;

-- 2026-08-17 09:08:08
/* DDL */ select AD_Element_Link_Create_Missing_Field(782319)
;

-- 2026-08-17 09:08:09
/* DDL */ select AD_Element_Link_Create_Missing_Field(782320)
;

-- 2026-08-17 09:08:10
/* DDL */ select AD_Element_Link_Create_Missing_Field(782321)
;

-- 2026-08-17 09:08:11
/* DDL */ select AD_Element_Link_Create_Missing_Field(782322)
;

-- 2026-08-17 09:08:12
/* DDL */ select AD_Element_Link_Create_Missing_Field(782323)
;

-- ============================================================================
-- AD_UI_Element — config tab group (551690) at seqno 60/70/80/90
-- ============================================================================

-- UI Element: MobileUI Manufacturing Configuration -> config group -> 60 -> Wareneingang auf Ladeeinheit (LU) erlauben (653169)
-- Column: MobileUI_MFG_Config.IsAllowReceiveToLU
-- 2026-08-17 09:09:00
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,782316,0,547483,551690,653169 /*From ID Server*/,'F',TO_TIMESTAMP('2026-08-17 09:09:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','Y','N','Wareneingang auf Ladeeinheit (LU) erlauben',60,60,0,TO_TIMESTAMP('2026-08-17 09:09:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: MobileUI Manufacturing Configuration -> config group -> 70 -> Wareneingang auf Transporteinheit (TU) erlauben (653170)
-- Column: MobileUI_MFG_Config.IsAllowReceiveToTU
-- 2026-08-17 09:09:10
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,782317,0,547483,551690,653170 /*From ID Server*/,'F',TO_TIMESTAMP('2026-08-17 09:09:10.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','Y','N','Wareneingang auf Transporteinheit (TU) erlauben',70,70,0,TO_TIMESTAMP('2026-08-17 09:09:10.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: MobileUI Manufacturing Configuration -> config group -> 80 -> Schritt Gebinde-Auswahl überspringen (653171)
-- Column: MobileUI_MFG_Config.IsSkipReceiveTargetStep
-- 2026-08-17 09:09:20
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,782318,0,547483,551690,653171 /*From ID Server*/,'F',TO_TIMESTAMP('2026-08-17 09:09:20.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','Y','N','Schritt Gebinde-Auswahl überspringen',80,80,0,TO_TIMESTAMP('2026-08-17 09:09:20.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: MobileUI Manufacturing Configuration -> config group -> 90 -> Catch Weight beim Wareneingang erfassen (653172)
-- Column: MobileUI_MFG_Config.IsCaptureCatchWeightAtReceipt
-- 2026-08-17 09:09:30
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,782319,0,547483,551690,653172 /*From ID Server*/,'F',TO_TIMESTAMP('2026-08-17 09:09:30.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','Y','N','Catch Weight beim Wareneingang erfassen',90,90,0,TO_TIMESTAMP('2026-08-17 09:09:30.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- ============================================================================
-- AD_UI_Element — userprofile tab group (550042) at seqno 60/70/80/90
-- ============================================================================

-- UI Element: Nutzer -> Mobile UI Nutzerprofil - Produktion -> primary group -> 60 -> Wareneingang auf Ladeeinheit (LU) erlauben (653173)
-- Column: MobileUI_UserProfile_MFG.IsAllowReceiveToLU
-- 2026-08-17 09:10:00
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,782320,0,546679,550042,653173 /*From ID Server*/,'F',TO_TIMESTAMP('2026-08-17 09:10:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','Y','N','Wareneingang auf Ladeeinheit (LU) erlauben',60,60,0,TO_TIMESTAMP('2026-08-17 09:10:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Nutzer -> Mobile UI Nutzerprofil - Produktion -> primary group -> 70 -> Wareneingang auf Transporteinheit (TU) erlauben (653174)
-- Column: MobileUI_UserProfile_MFG.IsAllowReceiveToTU
-- 2026-08-17 09:10:10
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,782321,0,546679,550042,653174 /*From ID Server*/,'F',TO_TIMESTAMP('2026-08-17 09:10:10.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','Y','N','Wareneingang auf Transporteinheit (TU) erlauben',70,70,0,TO_TIMESTAMP('2026-08-17 09:10:10.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Nutzer -> Mobile UI Nutzerprofil - Produktion -> primary group -> 80 -> Schritt Gebinde-Auswahl überspringen (653175)
-- Column: MobileUI_UserProfile_MFG.IsSkipReceiveTargetStep
-- 2026-08-17 09:10:20
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,782322,0,546679,550042,653175 /*From ID Server*/,'F',TO_TIMESTAMP('2026-08-17 09:10:20.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','Y','N','Schritt Gebinde-Auswahl überspringen',80,80,0,TO_TIMESTAMP('2026-08-17 09:10:20.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Nutzer -> Mobile UI Nutzerprofil - Produktion -> primary group -> 90 -> Catch Weight beim Wareneingang erfassen (653176)
-- Column: MobileUI_UserProfile_MFG.IsCaptureCatchWeightAtReceipt
-- 2026-08-17 09:10:30
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,782323,0,546679,550042,653176 /*From ID Server*/,'F',TO_TIMESTAMP('2026-08-17 09:10:30.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','Y','N','Catch Weight beim Wareneingang erfassen',90,90,0,TO_TIMESTAMP('2026-08-17 09:10:30.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
