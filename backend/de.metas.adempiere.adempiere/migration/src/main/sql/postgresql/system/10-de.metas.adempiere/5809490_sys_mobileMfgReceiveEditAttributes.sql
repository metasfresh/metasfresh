-- MobileUI Manufacturing — add IsBestBeforeDateEditable + IsLotNumberEditable config fields.
-- Two YesNo config fields gating whether the Best-Before-Date / Lot-Number inputs are editable
-- on the mobile Produktion receive dialog. Mirrors IsScanResourceRequired / IsAllowIssuingAnyHU.
--  - MobileUI_MFG_Config (542397): global config, YesNo (ref 20), NOT NULL DEFAULT 'Y'.
--  - MobileUI_UserProfile_MFG (542263): per-user override, three-state YesNo (ref 17 + 319), nullable (NULL = inherit global).
-- Shared AD_Element per attribute, referenced by both tables' columns.

-- ============================================================================
-- AD_Element: IsBestBeforeDateEditable (585046)
-- ============================================================================

-- 2026-06-24 08:00:00
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,585046 /*From ID Server*/,0,'IsBestBeforeDateEditable',TO_TIMESTAMP('2026-06-24 08:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','MHD bearbeitbar','MHD bearbeitbar',TO_TIMESTAMP('2026-06-24 08:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-06-24 08:00:00 (seed _Trl rows for all system/base languages — copies element time)
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585046 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2026-06-24 08:00:12 (en_US override — strictly later than the element INSERT)
UPDATE AD_Element_Trl SET Name='Best Before Date editable', PrintName='Best Before Date editable', Description='Allows editing the Best-Before-Date (MHD) when receiving finished goods in mobile manufacturing.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-06-24 08:00:12.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585046 AND AD_Language='en_US'
;

-- 2026-06-24 08:00:13 (de_DE override — set description + flip IsTranslated)
UPDATE AD_Element_Trl SET Name='MHD bearbeitbar', Description='Erlaubt das Bearbeiten des Mindesthaltbarkeitsdatums (MHD) beim Vereinnahmen von Fertigware in der mobilen Produktion.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-06-24 08:00:13.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585046 AND AD_Language='de_DE'
;

-- 2026-06-24 08:00:14 (de_CH override — same as de_DE)
UPDATE AD_Element_Trl SET Name='MHD bearbeitbar', Description='Erlaubt das Bearbeiten des Mindesthaltbarkeitsdatums (MHD) beim Vereinnahmen von Fertigware in der mobilen Produktion.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-06-24 08:00:14.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585046 AND AD_Language='de_CH'
;

-- ============================================================================
-- AD_Element: IsLotNumberEditable (585047)
-- ============================================================================

-- 2026-06-24 08:00:20
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,585047 /*From ID Server*/,0,'IsLotNumberEditable',TO_TIMESTAMP('2026-06-24 08:00:20.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Chargennr. bearbeitbar','Chargennr. bearbeitbar',TO_TIMESTAMP('2026-06-24 08:00:20.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-06-24 08:00:20 (seed _Trl rows for all system/base languages — copies element time)
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585047 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2026-06-24 08:00:32 (en_US override)
UPDATE AD_Element_Trl SET Name='Lot Number editable', PrintName='Lot Number editable', Description='Allows editing the Lot Number when receiving finished goods in mobile manufacturing.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-06-24 08:00:32.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585047 AND AD_Language='en_US'
;

-- 2026-06-24 08:00:33 (de_DE override)
UPDATE AD_Element_Trl SET Name='Chargennr. bearbeitbar', Description='Erlaubt das Bearbeiten der Chargennummer beim Vereinnahmen von Fertigware in der mobilen Produktion.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-06-24 08:00:33.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585047 AND AD_Language='de_DE'
;

-- 2026-06-24 08:00:34 (de_CH override — same as de_DE)
UPDATE AD_Element_Trl SET Name='Chargennr. bearbeitbar', Description='Erlaubt das Bearbeiten der Chargennummer beim Vereinnahmen von Fertigware in der mobilen Produktion.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-06-24 08:00:34.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585047 AND AD_Language='de_CH'
;

-- ============================================================================
-- AD_Column: MobileUI_MFG_Config (542397) — global config, YesNo (20), NOT NULL DEFAULT 'Y'
-- ============================================================================

-- Column: MobileUI_MFG_Config.IsBestBeforeDateEditable (592878)
-- 2026-06-24 08:01:00
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592878 /*From ID Server*/,585046,0,20,542397,'XX','IsBestBeforeDateEditable',TO_TIMESTAMP('2026-06-24 08:01:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Y','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'MHD bearbeitbar','NP',0,0,TO_TIMESTAMP('2026-06-24 08:01:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-06-24 08:01:00 (seed _Trl)
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592878 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Column: MobileUI_MFG_Config.IsLotNumberEditable (592879)
-- 2026-06-24 08:01:10
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592879 /*From ID Server*/,585047,0,20,542397,'XX','IsLotNumberEditable',TO_TIMESTAMP('2026-06-24 08:01:10.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Y','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Chargennr. bearbeitbar','NP',0,0,TO_TIMESTAMP('2026-06-24 08:01:10.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-06-24 08:01:10 (seed _Trl)
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592879 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- ============================================================================
-- AD_Column: MobileUI_UserProfile_MFG (542263) — per-user override, three-state YesNo (17 + 319), nullable
-- ============================================================================

-- Column: MobileUI_UserProfile_MFG.IsBestBeforeDateEditable (592880)
-- 2026-06-24 08:01:20
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592880 /*From ID Server*/,585046,0,17,319,542263,'XX','IsBestBeforeDateEditable',TO_TIMESTAMP('2026-06-24 08:01:20.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'MHD bearbeitbar','NP',0,0,TO_TIMESTAMP('2026-06-24 08:01:20.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-06-24 08:01:20 (seed _Trl)
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592880 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Column: MobileUI_UserProfile_MFG.IsLotNumberEditable (592881)
-- 2026-06-24 08:01:30
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592881 /*From ID Server*/,585047,0,17,319,542263,'XX','IsLotNumberEditable',TO_TIMESTAMP('2026-06-24 08:01:30.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Chargennr. bearbeitbar','NP',0,0,TO_TIMESTAMP('2026-06-24 08:01:30.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-06-24 08:01:30 (seed _Trl)
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592881 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- ============================================================================
-- Propagate element translations to the column _Trl rows
-- ============================================================================

-- 2026-06-24 08:01:40
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585046)
;

-- 2026-06-24 08:01:41
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585047)
;

-- ============================================================================
-- Physical DB columns
-- ============================================================================

-- 2026-06-24 08:02:00
/* DDL */ SELECT public.db_alter_table('MobileUI_MFG_Config','ALTER TABLE public.MobileUI_MFG_Config ADD COLUMN IsBestBeforeDateEditable CHAR(1) DEFAULT ''Y'' CHECK (IsBestBeforeDateEditable IN (''Y'',''N'')) NOT NULL')
;

-- 2026-06-24 08:02:01
/* DDL */ SELECT public.db_alter_table('MobileUI_MFG_Config','ALTER TABLE public.MobileUI_MFG_Config ADD COLUMN IsLotNumberEditable CHAR(1) DEFAULT ''Y'' CHECK (IsLotNumberEditable IN (''Y'',''N'')) NOT NULL')
;

-- 2026-06-24 08:02:02
/* DDL */ SELECT public.db_alter_table('MobileUI_UserProfile_MFG','ALTER TABLE public.MobileUI_UserProfile_MFG ADD COLUMN IsBestBeforeDateEditable CHAR(1) CHECK (IsBestBeforeDateEditable IN (''Y'',''N''))')
;

-- 2026-06-24 08:02:03
/* DDL */ SELECT public.db_alter_table('MobileUI_UserProfile_MFG','ALTER TABLE public.MobileUI_UserProfile_MFG ADD COLUMN IsLotNumberEditable CHAR(1) CHECK (IsLotNumberEditable IN (''Y'',''N''))')
;

-- ============================================================================
-- AD_Field: MobileUI Manufacturing Configuration tab (547483)
-- ============================================================================

-- Field: MobileUI Manufacturing Configuration(541788,D) -> MobileUI Manufacturing Configuration(547483,D) -> MHD bearbeitbar (781246)
-- Column: MobileUI_MFG_Config.IsBestBeforeDateEditable
-- 2026-06-24 08:03:00
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592878,781246 /*From ID Server*/,0,547483,TO_TIMESTAMP('2026-06-24 08:03:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','MHD bearbeitbar',TO_TIMESTAMP('2026-06-24 08:03:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-06-24 08:03:00 (seed _Trl)
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=781246 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Field: MobileUI Manufacturing Configuration(541788,D) -> MobileUI Manufacturing Configuration(547483,D) -> Chargennr. bearbeitbar (781247)
-- Column: MobileUI_MFG_Config.IsLotNumberEditable
-- 2026-06-24 08:03:10
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592879,781247 /*From ID Server*/,0,547483,TO_TIMESTAMP('2026-06-24 08:03:10.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Chargennr. bearbeitbar',TO_TIMESTAMP('2026-06-24 08:03:10.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-06-24 08:03:10 (seed _Trl)
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=781247 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- ============================================================================
-- AD_Field: MobileUI Nutzerprofil - Produktion tab (546679)
-- ============================================================================

-- Field: Nutzer(108,D) -> Mobile UI Nutzerprofil - Produktion(546679,D) -> MHD bearbeitbar (781248)
-- Column: MobileUI_UserProfile_MFG.IsBestBeforeDateEditable
-- 2026-06-24 08:03:20
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592880,781248 /*From ID Server*/,0,546679,TO_TIMESTAMP('2026-06-24 08:03:20.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','MHD bearbeitbar',TO_TIMESTAMP('2026-06-24 08:03:20.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-06-24 08:03:20 (seed _Trl)
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=781248 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Field: Nutzer(108,D) -> Mobile UI Nutzerprofil - Produktion(546679,D) -> Chargennr. bearbeitbar (781249)
-- Column: MobileUI_UserProfile_MFG.IsLotNumberEditable
-- 2026-06-24 08:03:30
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592881,781249 /*From ID Server*/,0,546679,TO_TIMESTAMP('2026-06-24 08:03:30.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Chargennr. bearbeitbar',TO_TIMESTAMP('2026-06-24 08:03:30.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-06-24 08:03:30 (seed _Trl)
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=781249 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- ============================================================================
-- Propagate element translations to field _Trl rows + rebuild element links
-- ============================================================================

-- 2026-06-24 08:03:40
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585046)
;

-- 2026-06-24 08:03:41
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585047)
;

-- 2026-06-24 08:03:42
DELETE FROM AD_Element_Link WHERE AD_Field_ID IN (781246,781247,781248,781249)
;

-- 2026-06-24 08:03:43
/* DDL */ select AD_Element_Link_Create_Missing_Field(781246)
;

-- 2026-06-24 08:03:44
/* DDL */ select AD_Element_Link_Create_Missing_Field(781247)
;

-- 2026-06-24 08:03:45
/* DDL */ select AD_Element_Link_Create_Missing_Field(781248)
;

-- 2026-06-24 08:03:46
/* DDL */ select AD_Element_Link_Create_Missing_Field(781249)
;

-- ============================================================================
-- AD_UI_Element — config tab group (551690) at seqno 40/50
-- ============================================================================

-- UI Element: MobileUI Manufacturing Configuration -> config group -> 40 -> MHD bearbeitbar (652366)
-- Column: MobileUI_MFG_Config.IsBestBeforeDateEditable
-- 2026-06-24 08:04:00
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,781246,0,547483,551690,652366 /*From ID Server*/,'F',TO_TIMESTAMP('2026-06-24 08:04:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','Y','N','MHD bearbeitbar',40,40,0,TO_TIMESTAMP('2026-06-24 08:04:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: MobileUI Manufacturing Configuration -> config group -> 50 -> Chargennr. bearbeitbar (652367)
-- Column: MobileUI_MFG_Config.IsLotNumberEditable
-- 2026-06-24 08:04:10
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,781247,0,547483,551690,652367 /*From ID Server*/,'F',TO_TIMESTAMP('2026-06-24 08:04:10.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','Y','N','Chargennr. bearbeitbar',50,50,0,TO_TIMESTAMP('2026-06-24 08:04:10.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- ============================================================================
-- AD_UI_Element — userprofile tab group (550042) at seqno 40/50
-- ============================================================================

-- UI Element: Nutzer -> Mobile UI Nutzerprofil - Produktion -> primary group -> 40 -> MHD bearbeitbar (652368)
-- Column: MobileUI_UserProfile_MFG.IsBestBeforeDateEditable
-- 2026-06-24 08:04:20
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,781248,0,546679,550042,652368 /*From ID Server*/,'F',TO_TIMESTAMP('2026-06-24 08:04:20.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','Y','N','MHD bearbeitbar',40,40,0,TO_TIMESTAMP('2026-06-24 08:04:20.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Nutzer -> Mobile UI Nutzerprofil - Produktion -> primary group -> 50 -> Chargennr. bearbeitbar (652369)
-- Column: MobileUI_UserProfile_MFG.IsLotNumberEditable
-- 2026-06-24 08:04:30
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,781249,0,546679,550042,652369 /*From ID Server*/,'F',TO_TIMESTAMP('2026-06-24 08:04:30.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','Y','N','Chargennr. bearbeitbar',50,50,0,TO_TIMESTAMP('2026-06-24 08:04:30.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
