-- me03 #30509 ZUGFeRD e-invoicing: add AD_Process.IsPdfA3Output boolean flag.
-- When Y on a report's AD_Process, the Jasper exporter emits the report as PDF/A-3.
-- Admins toggle it per report on the Report & Process window (AD_Window_ID=165, AD_Tab_ID=245).
--
-- IDs allocated from idserver.metas.de on 2026-06-22:
--   AD_Element    585042  (IsPdfA3Output)
--   AD_Column     592875  (AD_Process.IsPdfA3Output)
--   AD_Field      781237  (tab 245, advanced-edit element group, Swing SeqNo=235)
--   AD_UI_Element 652355  (tab 245, advanced-edit element group, SeqNo=225)

-- ============================================================
-- 1. Physical column on AD_Process
-- ============================================================
/* DDL */ SELECT public.db_alter_table('AD_Process','ALTER TABLE public.AD_Process ADD COLUMN IsPdfA3Output CHAR(1) DEFAULT ''N'' NOT NULL');

-- ============================================================
-- 2. AD_Element (German base text; English via _Trl)
-- ============================================================
-- 2026-06-22T10:00:00.000Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Description,Updated,UpdatedBy)
VALUES (0,585042 /*From ID Server*/,0,'IsPdfA3Output',
  TO_TIMESTAMP('2026-06-22 10:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,
  'D','Y',
  'PDF/A-3 Ausgabe','PDF/A-3 Ausgabe',
  'Wenn aktiv, wird der Bericht als PDF/A-3 ausgegeben (für ZUGFeRD e-Rechnung).',
  TO_TIMESTAMP('2026-06-22 10:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-06-22T10:00:00.000Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585042
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- de_CH: same as de_DE, mark translated
-- 2026-06-22T10:00:12.000Z
UPDATE AD_Element_Trl SET IsTranslated='Y',
  Updated=TO_TIMESTAMP('2026-06-22 10:00:12.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
  UpdatedBy=100
WHERE AD_Element_ID=585042 AND AD_Language='de_CH'
;

-- 2026-06-22T10:00:12.000Z
/* DDL */ select update_ad_element_on_ad_element_trl_update(585042,'de_CH')
;

-- 2026-06-22T10:00:12.000Z
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585042,'de_CH')
;

-- de_DE: same as de_DE base, mark translated
-- 2026-06-22T10:00:14.000Z
UPDATE AD_Element_Trl SET IsTranslated='Y',
  Updated=TO_TIMESTAMP('2026-06-22 10:00:14.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
  UpdatedBy=100
WHERE AD_Element_ID=585042 AND AD_Language='de_DE'
;

-- 2026-06-22T10:00:14.000Z
/* DDL */ select update_ad_element_on_ad_element_trl_update(585042,'de_DE')
;

-- 2026-06-22T10:00:14.000Z
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585042,'de_DE')
;

-- en_US: English override
-- 2026-06-22T10:00:18.000Z
UPDATE AD_Element_Trl SET IsTranslated='Y',
  Name='PDF/A-3 output', PrintName='PDF/A-3 output',
  Description='If set, the report is emitted as PDF/A-3 (for ZUGFeRD e-invoicing).',
  Updated=TO_TIMESTAMP('2026-06-22 10:00:18.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
  UpdatedBy=100
WHERE AD_Element_ID=585042 AND AD_Language='en_US'
;

-- 2026-06-22T10:00:18.000Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy
FROM AD_Element_Trl trl
WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-06-22T10:00:18.000Z
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585042,'en_US')
;

-- ============================================================
-- 3. AD_Column
-- ============================================================
-- Column: AD_Process.IsPdfA3Output
-- 2026-06-22T10:01:00.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,
  ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,
  IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,
  IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,
  IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,
  IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,
  IsTranslated,IsUpdateable,IsUseDocSequence,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,
  Updated,UpdatedBy,Version)
VALUES (0,592875 /*From ID Server*/,585042 /*From ID Server*/,0,20 /*YesNo*/,284 /*AD_Process*/,
  'IsPdfA3Output',
  TO_TIMESTAMP('2026-06-22 10:01:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
  100,'N','D',1,'Y','N','Y',
  'N','N','N','N','N','N',
  'N','Y','N','N','N',
  'N','N','N','N','Y','N','N',
  'N','N','N','N','N',
  'N','Y','N',
  'PDF/A-3 Ausgabe','NP',0,0,
  TO_TIMESTAMP('2026-06-22 10:01:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
  100,0)
;

-- 2026-06-22T10:01:00.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592875
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-06-22T10:01:00.000Z
/* DDL */ select update_Column_Translation_From_AD_Element(585042 /*From ID Server*/)
;

-- ============================================================
-- 4. AD_Field on tab 245 (Report & Process, main tab)
--    AccessLevel=4 (System only) → Swing client window → must set SeqNo + IsDisplayed
-- ============================================================
-- Field: Bericht & Prozess(165) -> Bericht & Prozess(245) -> PDF/A-3 Ausgabe
-- Column: AD_Process.IsPdfA3Output
-- 2026-06-22T10:02:00.000Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,
  Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,
  IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,
  Updated,UpdatedBy)
VALUES (0,592875 /*From ID Server*/,781237 /*From ID Server*/,0,245,
  TO_TIMESTAMP('2026-06-22 10:02:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
  100,
  'Wenn aktiv, wird der Bericht als PDF/A-3 ausgegeben (für ZUGFeRD e-Rechnung).',
  1,'D','Y','Y','N',
  'N','N','N','N','N',
  'PDF/A-3 Ausgabe',235 /*Swing order: between AD_PrintFormat_ID (230) and ShowHelp (240)*/,
  TO_TIMESTAMP('2026-06-22 10:02:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
  100)
;

-- 2026-06-22T10:02:00.000Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=781237
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-06-22T10:02:00.000Z
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585042 /*From ID Server, element_id*/)
;

-- 2026-06-22T10:02:00.000Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781237 /*From ID Server*/
;

-- 2026-06-22T10:02:00.000Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(781237 /*From ID Server*/)
;

-- ============================================================
-- 5. AD_UI_Element in the 'advanced edit' element group (541388) on tab 245
--    (renders in WebUI; Swing uses AD_Field.SeqNo from above)
--    Placed with the other output-format controls (IsDirectPrint, AD_PrintFormat_ID),
--    NOT the 'excel' group — IsPdfA3Output is a PDF output flag, not an Excel option.
-- ============================================================
-- UI Element: Bericht & Prozess(165) -> Bericht & Prozess(245) -> section2 -> advanced edit -> PDF/A-3 Ausgabe
-- Column: AD_Process.IsPdfA3Output
-- 2026-06-22T10:02:30.000Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,
  Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,
  Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,781237 /*From ID Server*/,0,245,541388 /*advanced-edit group*/,652355 /*From ID Server*/,'F',
  TO_TIMESTAMP('2026-06-22 10:02:30.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
  100,'Y','Y' /*IsAdvancedField=Y: advanced-edit group, consistent with Direct print / Druck-Format*/,'Y','N','N',
  'PDF/A-3 Ausgabe',225 /*advanced-edit group: between Druck-Format (220) and Show Help (230)*/,0,0,
  TO_TIMESTAMP('2026-06-22 10:02:30.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
  100)
;

-- ============================================================
-- 6. Final translation propagation sweep
-- ============================================================
-- 2026-06-22T10:03:00.000Z
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585042 /*From ID Server*/)
;

-- Verify
SELECT count(*) AS column_count FROM ad_column WHERE columnname='IsPdfA3Output' AND ad_table_id=284;
