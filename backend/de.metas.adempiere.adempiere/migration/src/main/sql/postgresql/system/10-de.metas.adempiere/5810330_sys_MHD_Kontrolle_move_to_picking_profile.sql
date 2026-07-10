-- IDs allocated from idserver.metas.de on 2026-07-01:
--   AD_MigrationScript  5810330  (filename prefix, raw=581033)
--   AD_Column           592917   (MobileUI_UserProfile_Picking.IsWarnShelfLifeUndercut)
--   AD_Field            781318   (picking-profile window tab 547258)
--   AD_UI_Element       652426   (flags group 551252)
--
-- Reused:
--   AD_Element          585073   (IsWarnShelfLifeUndercut - already exists from migration 5809960)
--
-- ADD to MobileUI_UserProfile_Picking (table 542373, tab 547258, window 541743):
--   Column, Field, UI_Element for IsWarnShelfLifeUndercut
--
-- DROP from C_Workplace (table 542375, tab 547260, window 541744):
--   AD_UI_Element 652424, AD_Field 781317, AD_Column_Trl rows, AD_Column 592916,
--   physical column via db_alter_table
--
-- No customer override windows found for 541743 or 541744 (queried against intensive_care_uat_db).
-- No dependent views/functions/val-rules/virtual-columns/EXP_FormatLines found for
-- C_Workplace.IsWarnShelfLifeUndercut (dependency sweep result: empty).

-- =============================================================================
-- PART 1: ADD IsWarnShelfLifeUndercut to MobileUI_UserProfile_Picking
-- =============================================================================

-- Column: MobileUI_UserProfile_Picking.IsWarnShelfLifeUndercut
-- Reuses AD_Element 585073; no new element needed.
-- 2026-07-01T10:01:00.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592917 /*From ID Server*/,585073,0,20,542373,'XX','IsWarnShelfLifeUndercut',TO_TIMESTAMP('2026-07-01 10:01:00','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Warnung wenn Restlaufzeit Vorgabe unterschritten','NP',0,0,TO_TIMESTAMP('2026-07-01 10:01:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- Column_Trl seed rows
-- 2026-07-01T10:01:01.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592917
AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Propagate translations from AD_Element 585073
-- 2026-07-01T10:01:02.000Z
/* DDL */ SELECT update_Column_Translation_From_AD_Element(585073)
;

-- DDL: add physical column to MobileUI_UserProfile_Picking
-- 2026-07-01T10:01:10.000Z
/* DDL */ SELECT public.db_alter_table('MobileUI_UserProfile_Picking','ALTER TABLE public.MobileUI_UserProfile_Picking ADD COLUMN IF NOT EXISTS IsWarnShelfLifeUndercut CHAR(1) DEFAULT ''N'' NOT NULL')
;

-- Field: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> IsWarnShelfLifeUndercut
-- 2026-07-01T10:02:00.000Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,592917,781318 /*From ID Server*/,0,547258,TO_TIMESTAMP('2026-07-01 10:02:00','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Warnung wenn Restlaufzeit Vorgabe unterschritten',TO_TIMESTAMP('2026-07-01 10:02:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field_Trl seed rows
-- 2026-07-01T10:02:01.000Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=781318
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Propagate field translations from AD_Element 585073
-- 2026-07-01T10:02:02.000Z
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(585073)
;

-- 2026-07-01T10:02:03.000Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781318
;

-- 2026-07-01T10:02:04.000Z
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781318)
;

-- UI Element: Mobile UI Kommissionierprofil(547258) -> flags group(551252) -> IsWarnShelfLifeUndercut
-- Placement: right column, flags group (group 551252), SeqNo=170 (after IsAllowQuickPackAll at 160)
-- IsDisplayedGrid='N': boolean config flags are not shown in grid on this window (consistent with siblings)
-- 2026-07-01T10:02:30.000Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,781318,0,547258,551252,652426 /*From ID Server*/,'F',TO_TIMESTAMP('2026-07-01 10:02:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Warnung wenn Restlaufzeit Vorgabe unterschritten',170,0,0,TO_TIMESTAMP('2026-07-01 10:02:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- =============================================================================
-- PART 2: DROP IsWarnShelfLifeUndercut from C_Workplace (forward-drop)
-- Dependency sweep (2026-07-01): no views, functions, val-rules, virtual columns,
-- or EXP_FormatLines reference C_Workplace.IsWarnShelfLifeUndercut.
-- =============================================================================

-- Delete AD_UI_Element 652424 (C_Workplace tab 547260, flags group 551258)
-- 2026-07-01T10:03:00.000Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=652424
;

-- Delete AD_Field_Trl rows for AD_Field 781317
-- 2026-07-01T10:03:01.000Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID=781317
;

-- Delete AD_Element_Link rows for AD_Field 781317
-- 2026-07-01T10:03:02.000Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781317
;

-- Delete AD_Field 781317 (C_Workplace tab 547260)
-- 2026-07-01T10:03:03.000Z
DELETE FROM AD_Field WHERE AD_Field_ID=781317
;

-- Delete AD_Column_Trl rows for AD_Column 592916 (C_Workplace.IsWarnShelfLifeUndercut)
-- 2026-07-01T10:03:04.000Z
DELETE FROM AD_Column_Trl WHERE AD_Column_ID=592916
;

-- Delete AD_Column 592916 (C_Workplace.IsWarnShelfLifeUndercut)
-- 2026-07-01T10:03:05.000Z
DELETE FROM AD_Column WHERE AD_Column_ID=592916
;

-- No backup_table: the column was just added by the prior (unreleased) migration and is
-- unused (all rows 'N'), so there is no data worth preserving on drop.
-- DDL: drop physical column from C_Workplace
-- 2026-07-01T10:03:10.000Z
/* DDL */ SELECT public.db_alter_table('C_Workplace','ALTER TABLE public.C_Workplace DROP COLUMN IF EXISTS IsWarnShelfLifeUndercut')
;
