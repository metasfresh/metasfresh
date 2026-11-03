-- Add AD_Process.FilenamePattern: per-process filename template used by ArchiveFileNameService.
-- Placeholders: {orgname}, {orgvalue}, {doctype}, {tablename}, {processname}, {processvalue}, {documentno}, {recordid}, {pinstanceid},
-- plus the date expression ${date:<DateTimeFormatter-pattern>} (e.g. ${date:yyyyMMdd_HHmmss}) formatted in the archive's org timezone.
-- Unresolvable placeholders are left as literal text. The extension '.pdf' is appended if missing.
-- For AD_Process 585548 (DD_Order_MaterialInTransitReport), seed the pattern so the Materialbegleitschein PDF
-- exports with a recognisable, unique filename built from the org name, process name, and AD_PInstance_ID.

--
-- AD_Element: FilenamePattern
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy)
VALUES (0,584851 /*From ID Server*/,0,'FilenamePattern',TO_TIMESTAMP('2026-05-11 14:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Filename Pattern','Filename Pattern',TO_TIMESTAMP('2026-05-11 14:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND t.AD_Element_ID=584851
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

UPDATE AD_Element_Trl SET Name='Dateinamensmuster', PrintName='Dateinamensmuster',
  Description='Muster für den PDF-Dateinamen dieses Prozesses. Platzhalter: {orgname}, {orgvalue}, {doctype}, {tablename}, {processname}, {processvalue}, {documentno}, {recordid}, {pinstanceid}, zusätzlich ${date:<DateTimeFormatter-Muster>} (z. B. ${date:yyyyMMdd_HHmmss}) in der Zeitzone der Organisation. Nicht aufgelöste Platzhalter bleiben unverändert. Die Endung ''.pdf'' wird automatisch ergänzt.',
  IsTranslated='Y', Updated=TO_TIMESTAMP('2026-05-11 14:00:12','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Element_ID=584851 AND AD_Language IN ('de_DE','de_CH')
;

UPDATE AD_Element_Trl SET Name='Filename Pattern', PrintName='Filename Pattern',
  Description='Pattern for the archive''s PDF filename for this process. Placeholders: {orgname}, {orgvalue}, {doctype}, {tablename}, {processname}, {processvalue}, {documentno}, {recordid}, {pinstanceid}, plus ${date:<DateTimeFormatter-pattern>} (e.g. ${date:yyyyMMdd_HHmmss}) in the org''s timezone. Unresolvable placeholders are left unchanged. The extension ''.pdf'' is appended automatically.',
  IsTranslated='Y', Updated=TO_TIMESTAMP('2026-05-11 14:00:18','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Element_ID=584851 AND AD_Language IN ('en_US','en_GB')
;

--
-- AD_Column: AD_Process.FilenamePattern
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592505 /*From ID Server*/,584851,0,10,284,'XX','FilenamePattern',TO_TIMESTAMP('2026-05-11 14:01:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Filename Pattern','NP',0,0,TO_TIMESTAMP('2026-05-11 14:01:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND t.AD_Column_ID=592505
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

/* DDL */ SELECT update_Column_Translation_From_AD_Element(584851)
;

--
-- DDL: physical column on AD_Process
/* DDL */ SELECT public.db_alter_table('AD_Process','ALTER TABLE public.AD_Process ADD COLUMN FilenamePattern VARCHAR(255)')
;

--
-- AD_Field on the AD_Process tab (Bericht & Prozess > Bericht & Prozess), placed in section/column 1/group "default" with SeqNo=200 (well after existing fields)
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,592505,779177 /*From ID Server*/,0,245,TO_TIMESTAMP('2026-05-11 14:02:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,60,'D','Y','Y','N','N','N','N','N','N','Filename Pattern',TO_TIMESTAMP('2026-05-11 14:02:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND t.AD_Field_ID=779177
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(584851)
;

DELETE FROM AD_Element_Link WHERE AD_Field_ID=779177
;

/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(779177)
;

--
-- UI placement: section 540608 / column 540814 (first column) / group 541398 (default) — SeqNo 50 (after existing fields)
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,779177,0,245,541398,651161 /*From ID Server*/,'F',TO_TIMESTAMP('2026-05-11 14:02:30','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Filename Pattern',50,0,0,TO_TIMESTAMP('2026-05-11 14:02:30','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

--
-- Seed the pattern on AD_Process 585548 (DD_Order_MaterialInTransitReport — Materialbegleitschein).
-- {processname} resolves to the translated AD_Process.Name (e.g. "Materialbegleitschein" in de_*).
UPDATE AD_Process
SET FilenamePattern='{orgname}-{processname}-{pinstanceid}',
    Updated=TO_TIMESTAMP('2026-05-11 14:03:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',
    UpdatedBy=100
WHERE AD_Process_ID=585548
;
