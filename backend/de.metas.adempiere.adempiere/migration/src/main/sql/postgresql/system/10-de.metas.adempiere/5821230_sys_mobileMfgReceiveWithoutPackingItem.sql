-- MobileUI Manufacturing — add IsAllowReceiveWithoutPackingItem so the mobile production receipt can
-- offer the "No Packing Item" packing instruction as a receiving target, as the WebUI "Empfangen"
-- process already does (PackingInfoProcessParams -> includeVirtualItem). Without it a product in no
-- packing structure cannot be received in the mobile app at all: the receive query is pinned to
-- HU_UnitType='TU' while that packing instruction is 'V'. Default OFF.
-- No FinishedGoods qualifier, unlike the IsAllowFinishedGoods* / IsSkip* flags: those exempt a
-- co-/by-product line, which for an opt-in flag would force the target ON everywhere. Applies to
-- every receive line, like IsCaptureCatchWeightAtReceipt.
--  - MobileUI_MFG_Config (542397): global, YesNo (ref 20), NOT NULL DEFAULT 'N'.
--  - MobileUI_UserProfile_MFG (542263): per-user override, YesNo (ref 17 + 319), nullable = inherit.
-- Shared AD_Element for both columns. Mirrors 5819440.
--
-- IDs allocated from idserver.metas.de on 2026-08-29:
--   AD_Element    585389  (IsAllowReceiveWithoutPackingItem)
--   AD_Column     593434  (MobileUI_MFG_Config.IsAllowReceiveWithoutPackingItem)
--   AD_Column     593435  (MobileUI_UserProfile_MFG.IsAllowReceiveWithoutPackingItem)
--   AD_Field      783041  (config tab 547483) / 783042 (user-profile tab 546679)
--   AD_UI_Element 653685  (group 551690) / 653686 (group 550042)

-- ============================================================================
-- AD_Element: IsAllowReceiveWithoutPackingItem (585389)
-- ============================================================================

-- 2026-08-29 10:00:00
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,585389 /*From ID Server*/,0,'IsAllowReceiveWithoutPackingItem',TO_TIMESTAMP('2026-08-29 10:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Vereinnahmen ohne Packvorschrift erlauben','Vereinnahmen ohne Packvorschrift erlauben',TO_TIMESTAMP('2026-08-29 10:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-08-29 10:00:00 (seed _Trl rows for all system/base languages — copies element time)
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585389 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2026-08-29 10:00:12 (en_US override — strictly later than the element INSERT)
UPDATE AD_Element_Trl SET Name='Allow receiving without a packing instruction', PrintName='Allow receiving without a packing instruction', Description='Offer the ''No Packing Item'' packing instruction as a receiving target for the production receipt.', Help='When set, the mobile manufacturing receipt additionally offers the ''No Packing Item'' packing instruction as a receiving target, so a product with no physical Gebinde can still be received — the way the WebUI ''Empfangen'' process already does. The result is a virtual HU. Off by default. Applies to every line of the production receipt, co- and by-products included. The target appears in the TU list and is therefore hidden together with ''Finished goods: allow receiving to TU''.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-29 10:00:12.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585389 AND AD_Language='en_US'
;

-- 2026-08-29 10:00:13 (de_DE override — set description/help + flip IsTranslated)
UPDATE AD_Element_Trl SET Name='Vereinnahmen ohne Packvorschrift erlauben', Description='Die Packvorschrift „No Packing Item“ als Ziel-Gebinde bei der Produktionsentnahme anbieten.', Help='Wenn gesetzt, bietet die mobile Produktionsentnahme zusätzlich die Packvorschrift „No Packing Item“ als Ziel-Gebinde an, sodass ein Produkt ohne physisches Gebinde vereinnahmt werden kann – so wie es die WebUI-Funktion „Empfangen“ bereits tut. Ergebnis ist eine virtuelle HU. Standardmäßig deaktiviert. Gilt für alle Zeilen der Produktionsentnahme, auch für Kuppel- und Nebenprodukte. Das Ziel erscheint in der TU-Liste und wird daher zusammen mit „Fertigprodukt: Entnahme auf TU erlauben“ ausgeblendet.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-29 10:00:13.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585389 AND AD_Language='de_DE'
;

-- 2026-08-29 10:00:14 (de_CH override — de_DE with the Swiss ss convention applied)
UPDATE AD_Element_Trl SET Name='Vereinnahmen ohne Packvorschrift erlauben', Description='Die Packvorschrift „No Packing Item“ als Ziel-Gebinde bei der Produktionsentnahme anbieten.', Help='Wenn gesetzt, bietet die mobile Produktionsentnahme zusätzlich die Packvorschrift „No Packing Item“ als Ziel-Gebinde an, sodass ein Produkt ohne physisches Gebinde vereinnahmt werden kann – so wie es die WebUI-Funktion „Empfangen“ bereits tut. Ergebnis ist eine virtuelle HU. Standardmässig deaktiviert. Gilt für alle Zeilen der Produktionsentnahme, auch für Kuppel- und Nebenprodukte. Das Ziel erscheint in der TU-Liste und wird daher zusammen mit „Fertigprodukt: Entnahme auf TU erlauben“ ausgeblendet.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-29 10:00:14.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585389 AND AD_Language='de_CH'
;

-- ============================================================================
-- AD_Column: MobileUI_MFG_Config (542397) — global config, YesNo (20), NOT NULL DEFAULT 'N'
-- ============================================================================

-- Column: MobileUI_MFG_Config.IsAllowReceiveWithoutPackingItem (593434) — DefaultValue='N'
-- 2026-08-29 10:02:00
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593434 /*From ID Server*/,585389,0,20,542397,'XX','IsAllowReceiveWithoutPackingItem',TO_TIMESTAMP('2026-08-29 10:02:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Vereinnahmen ohne Packvorschrift erlauben','NP',0,0,TO_TIMESTAMP('2026-08-29 10:02:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-08-29 10:02:00 (seed _Trl)
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593434 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- ============================================================================
-- AD_Column: MobileUI_UserProfile_MFG (542263) — per-user override, three-state YesNo (17 + 319), nullable
-- ============================================================================

-- Column: MobileUI_UserProfile_MFG.IsAllowReceiveWithoutPackingItem (593435)
-- 2026-08-29 10:03:00
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593435 /*From ID Server*/,585389,0,17,319,542263,'XX','IsAllowReceiveWithoutPackingItem',TO_TIMESTAMP('2026-08-29 10:03:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Vereinnahmen ohne Packvorschrift erlauben','NP',0,0,TO_TIMESTAMP('2026-08-29 10:03:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-08-29 10:03:00 (seed _Trl)
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593435 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- ============================================================================
-- Propagate element translations to the column _Trl rows
-- ============================================================================

-- 2026-08-29 10:04:00
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585389)
;

-- ============================================================================
-- Physical DB columns
-- ============================================================================

-- 2026-08-29 10:05:00
/* DDL */ SELECT public.db_alter_table('MobileUI_MFG_Config','ALTER TABLE public.MobileUI_MFG_Config ADD COLUMN IsAllowReceiveWithoutPackingItem CHAR(1) DEFAULT ''N'' CHECK (IsAllowReceiveWithoutPackingItem IN (''Y'',''N'')) NOT NULL')
;

-- 2026-08-29 10:05:01
/* DDL */ SELECT public.db_alter_table('MobileUI_UserProfile_MFG','ALTER TABLE public.MobileUI_UserProfile_MFG ADD COLUMN IsAllowReceiveWithoutPackingItem CHAR(1) CHECK (IsAllowReceiveWithoutPackingItem IN (''Y'',''N''))')
;

-- ============================================================================
-- AD_Field: MobileUI Manufacturing Configuration tab (547483)
-- ============================================================================

-- Field: MobileUI Manufacturing Configuration(541788,D) -> MobileUI Manufacturing Configuration(547483,D) -> Vereinnahmen ohne Packvorschrift erlauben (783041)
-- Column: MobileUI_MFG_Config.IsAllowReceiveWithoutPackingItem
-- 2026-08-29 10:06:00
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,593434,783041 /*From ID Server*/,0,547483,TO_TIMESTAMP('2026-08-29 10:06:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Vereinnahmen ohne Packvorschrift erlauben',TO_TIMESTAMP('2026-08-29 10:06:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-08-29 10:06:00 (seed _Trl)
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=783041 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- ============================================================================
-- AD_Field: MobileUI Nutzerprofil - Produktion tab (546679)
-- ============================================================================

-- Field: Nutzer(108,D) -> Mobile UI Nutzerprofil - Produktion(546679,D) -> Vereinnahmen ohne Packvorschrift erlauben (783042)
-- Column: MobileUI_UserProfile_MFG.IsAllowReceiveWithoutPackingItem
-- 2026-08-29 10:07:00
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,593435,783042 /*From ID Server*/,0,546679,TO_TIMESTAMP('2026-08-29 10:07:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Vereinnahmen ohne Packvorschrift erlauben',TO_TIMESTAMP('2026-08-29 10:07:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-08-29 10:07:00 (seed _Trl)
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=783042 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- ============================================================================
-- Propagate element translations to field _Trl rows + rebuild element links
-- ============================================================================

-- 2026-08-29 10:08:00
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585389)
;

-- 2026-08-29 10:08:04
DELETE FROM AD_Element_Link WHERE AD_Field_ID IN (783041,783042)
;

-- 2026-08-29 10:08:05
/* DDL */ select AD_Element_Link_Create_Missing_Field(783041)
;

-- 2026-08-29 10:08:06
/* DDL */ select AD_Element_Link_Create_Missing_Field(783042)
;

-- ============================================================================
-- AD_UI_Element — config tab group (551690) at seqno 100
-- ============================================================================

-- UI Element: MobileUI Manufacturing Configuration -> config group -> 100 -> Vereinnahmen ohne Packvorschrift erlauben (653685)
-- Column: MobileUI_MFG_Config.IsAllowReceiveWithoutPackingItem
-- 2026-08-29 10:09:00
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,783041,0,547483,551690,653685 /*From ID Server*/,'F',TO_TIMESTAMP('2026-08-29 10:09:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','Y','N','Vereinnahmen ohne Packvorschrift erlauben',100,100,0,TO_TIMESTAMP('2026-08-29 10:09:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- ============================================================================
-- AD_UI_Element — userprofile tab group (550042) at seqno 100
-- ============================================================================

-- UI Element: Nutzer -> Mobile UI Nutzerprofil - Produktion -> primary group -> 100 -> Vereinnahmen ohne Packvorschrift erlauben (653686)
-- Column: MobileUI_UserProfile_MFG.IsAllowReceiveWithoutPackingItem
-- 2026-08-29 10:10:00
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,783042,0,546679,550042,653686 /*From ID Server*/,'F',TO_TIMESTAMP('2026-08-29 10:10:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','Y','N','Vereinnahmen ohne Packvorschrift erlauben',100,100,0,TO_TIMESTAMP('2026-08-29 10:10:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

