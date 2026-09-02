-- Run mode: SWING_CLIENT

-- Adds PickingProfile_PickingJobConfig.IsBlockLayout — a per-row switch on the MobileUI Picking
-- profile's "Feld" tab (window 541743, tab 547360). When enabled, the picking job-list caption
-- renders this item's values one per line (no separator) instead of on the entry's single
-- running line. Default 'N': no existing profile row changes behaviour.
--
-- A distinct AD_Element is used on purpose (not the existing 'IsMultiLine' element, which backs
-- AD_UI_Element.IsMultiLine — an unrelated multi-line TEXT INPUT rendering flag).
--
-- Also corrects the now-stale description of the PickingJobField_Options 'ProductNames' value
-- (AD_Ref_List 544344): it read unconditionally "comma separated", which is only true when the
-- item is not in block layout.
--
-- IDs allocated from idserver.metas.de on 2026-09-01:
--   AD_Element    585396 (IsBlockLayout)
--   AD_Column     593444 (PickingProfile_PickingJobConfig.IsBlockLayout)
--   AD_Field      783051 (tab 547360 — Picking Job Config / "Feld")
--   AD_UI_Element 653693 (group 551432 — flags, SeqNo 40)

-- AD_Element: IsBlockLayout
-- 2026-09-01T09:00:00Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy)
VALUES (0,585396 /*From ID Server*/,0,'IsBlockLayout',TO_TIMESTAMP('2026-09-01 09:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Blockdarstellung','Blockdarstellung',TO_TIMESTAMP('2026-09-01 09:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585396
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsBlockLayout — de_DE translation (base language) — 2026-09-01T09:00:01Z
UPDATE AD_Element_Trl SET Name='Blockdarstellung', PrintName='Blockdarstellung', Description='Zeigt die Werte dieses Eintrags im Job-Listeneintrag jeweils in einer eigenen Zeile an, ohne Trennzeichen, statt in einer laufenden, durch Komma getrennten Zeile.', Help='Zeigt die Werte dieses Eintrags im Job-Listeneintrag jeweils in einer eigenen Zeile an, ohne Trennzeichen, statt in einer laufenden, durch Komma getrennten Zeile.', IsTranslated='N', Updated=TO_TIMESTAMP('2026-09-01 09:00:01.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=585396 AND AD_Language='de_DE';
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Description=trl.Description, Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage();
/* DDL */  select update_ad_element_on_ad_element_trl_update(585396,'de_DE');
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(585396,'de_DE');

-- Element: IsBlockLayout — de_CH translation — 2026-09-01T09:00:02Z
UPDATE AD_Element_Trl SET Name='Blockdarstellung', PrintName='Blockdarstellung', Description='Zeigt die Werte dieses Eintrags im Job-Listeneintrag jeweils in einer eigenen Zeile an, ohne Trennzeichen, statt in einer laufenden, durch Komma getrennten Zeile.', Help='Zeigt die Werte dieses Eintrags im Job-Listeneintrag jeweils in einer eigenen Zeile an, ohne Trennzeichen, statt in einer laufenden, durch Komma getrennten Zeile.', IsTranslated='N', Updated=TO_TIMESTAMP('2026-09-01 09:00:02.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=585396 AND AD_Language='de_CH';
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Description=trl.Description, Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage();
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(585396,'de_CH');

-- Element: IsBlockLayout — en_US translation — 2026-09-01T09:00:03Z
UPDATE AD_Element_Trl SET Name='Block layout', PrintName='Block layout', Description='Shows the values of this entry on their own lines in the job-list entry, with no separator, instead of a single comma-separated line.', Help='Shows the values of this entry on their own lines in the job-list entry, with no separator, instead of a single comma-separated line.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-01 09:00:03.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=585396 AND AD_Language='en_US';
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Description=trl.Description, Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage();
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(585396,'en_US');

-- Column: PickingProfile_PickingJobConfig.IsBlockLayout
-- AD_Table_ID=542390
-- 2026-09-01T09:00:04Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,593444 /*From ID Server*/,585396 /*From ID Server*/,0,20,542390,'IsBlockLayout',TO_TIMESTAMP('2026-09-01 09:00:04.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Blockdarstellung','NP',0,0,TO_TIMESTAMP('2026-09-01 09:00:04.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593444
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

/* DDL */  select update_Column_Translation_From_AD_Element(585396);

-- DDL: add physical column — CHAR(1) NOT NULL DEFAULT 'N' (ADD COLUMN ... DEFAULT populates
-- existing rows, so every existing PickingProfile_PickingJobConfig row reads 'N').
/* DDL */ SELECT public.db_alter_table('PickingProfile_PickingJobConfig','ALTER TABLE public.PickingProfile_PickingJobConfig ADD COLUMN IF NOT EXISTS IsBlockLayout CHAR(1) DEFAULT ''N'' CHECK (IsBlockLayout IN (''Y'',''N'')) NOT NULL')
;

-- Field: Mobile UI Kommissionierprofil(541743,D) -> Picking Job Config / "Feld"(547360,D) -> Blockdarstellung
-- Column: PickingProfile_PickingJobConfig.IsBlockLayout
-- 2026-09-01T09:00:06Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,593444,783051 /*From ID Server*/,0,547360,TO_TIMESTAMP('2026-09-01 09:00:06.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Blockdarstellung',TO_TIMESTAMP('2026-09-01 09:00:06.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=783051
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

/* DDL */  select update_FieldTranslation_From_AD_Name_Element(585396);

DELETE FROM AD_Element_Link WHERE AD_Field_ID=783051;
/* DDL */ select AD_Element_Link_Create_Missing_Field(783051);

-- UI Element: Mobile UI Kommissionierprofil(541743,D) -> Picking Job Config / "Feld"(547360,D) -> main -> flags -> 40 -> Blockdarstellung
-- Placed beside IsDisplayInSummary (622147, group 551432 SeqNo 20/SeqNoGrid 30) and
-- IsDisplayInDetailed (622148, group 551432 SeqNo 30/SeqNoGrid 40) — same group, same display
-- config (shown in the form view and as a grid column).
-- Column: PickingProfile_PickingJobConfig.IsBlockLayout
-- 2026-09-01T09:00:07Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy)
VALUES (0,783051,0,547360,653693 /*From ID Server*/,551432,'F',TO_TIMESTAMP('2026-09-01 09:00:07.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','Y','N',0,'Blockdarstellung',40,0,50,TO_TIMESTAMP('2026-09-01 09:00:07.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Correct the now-stale PickingJobField_Options 'ProductNames' description (AD_Ref_List
-- 544344, introduced by 5819660_PickingJobField_Options_ProductNames.sql): it is comma-separated
-- only when the item is NOT in block layout; with the switch on, each name is on its own line.
-- 2026-09-01T09:00:08Z
UPDATE AD_Ref_List SET Description='Alle Produktnamen des Auftrags. Ohne Blockdarstellung durch Komma getrennt, mit Blockdarstellung je Name in einer eigenen Zeile.', Updated=TO_TIMESTAMP('2026-09-01 09:00:08.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Ref_List_ID=544344;
UPDATE AD_Ref_List_Trl SET Description='Alle Produktnamen des Auftrags. Ohne Blockdarstellung durch Komma getrennt, mit Blockdarstellung je Name in einer eigenen Zeile.', Updated=TO_TIMESTAMP('2026-09-01 09:00:09.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Ref_List_ID=544344 AND AD_Language='de_DE';
UPDATE AD_Ref_List_Trl SET Description='Alle Produktnamen des Auftrags. Ohne Blockdarstellung durch Komma getrennt, mit Blockdarstellung je Name in einer eigenen Zeile.', Updated=TO_TIMESTAMP('2026-09-01 09:00:10.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Ref_List_ID=544344 AND AD_Language='de_CH';
UPDATE AD_Ref_List_Trl SET Description='All product names of the job. Comma separated unless block layout is enabled, in which case each name is rendered on its own line.', Updated=TO_TIMESTAMP('2026-09-01 09:00:11.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Ref_List_ID=544344 AND AD_Language='en_US';
