-- AD_Sequence.DateFormat: optional date format of a date-based sequence (e.g. yy-DDD for a date-only lot number).
-- When set, the date-based sequence number implementation formats the date with it; when empty, the system-wide
-- sysconfig de.metas.document.seqNo.DateSequenceProvider.dateFormat applies as before.
-- The column reuses the existing AD_Element DateFormat (2286); since that element describes an import file's input format,
-- the field on the sequence window gets its own label/description via AD_Field.AD_Name_ID -> AD_Element 585553.
-- Field placement: window Nummernfolgen (112), tab Reihenfolge (146), group "default" right after the custom
-- sequence number implementation field; shown only for the date-based implementation (AD_JavaClass 540073, DateSequenceProvider).
-- IDs from the central ID server (2026-10-23): AD_Element 585553; AD_Column 593756; AD_Field 785695; AD_UI_Element 655053.

-- ============================================================================
-- AD_Element (field label/description): 585553
-- ============================================================================

-- 2026-10-23 11:00:00
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,585553 /*From ID Server*/,0,'AD_Sequence_DateFormat',TO_TIMESTAMP('2026-10-23 11:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datumsformat für Nummernfolgen mit datumsbasierter Implementierung, z.B. yy-DDD (zweistelliges Jahr und Tag des Jahres).','D','Wird von der datumsbasierten Implementierung verwendet, um das Datum der Nummer zu formatieren. Ist das Feld leer, gilt das systemweite Datumsformat aus der System-Konfiguration de.metas.document.seqNo.DateSequenceProvider.dateFormat. Für das Jahr yy verwenden (Kalenderjahr), nicht YY (Jahr der Kalenderwoche, Ende Dezember bereits das Folgejahr).','Y','Datumsformat','Datumsformat',TO_TIMESTAMP('2026-10-23 11:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-10-23 11:00:00 (seed _Trl rows for all system/base languages — copies element time)
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585553 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2026-10-23 11:00:12 (en_US override — strictly later than the element INSERT)
UPDATE AD_Element_Trl SET Name='Date format', PrintName='Date format', Description='Date format for sequences with a date-based implementation, e.g. yy-DDD (two-digit year and day of the year).', Help='Used by the date-based implementation to format the date of the number. If empty, the system-wide date format from the system configuration de.metas.document.seqNo.DateSequenceProvider.dateFormat applies. For the year use yy (calendar year), not YY (week-based year, already the next year at the end of December).', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-23 11:00:12.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585553 AND AD_Language='en_US'
;

-- 2026-10-23 11:00:13 (de_DE: same text as the base, flip IsTranslated)
UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-23 11:00:13.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585553 AND AD_Language='de_DE'
;

-- 2026-10-23 11:00:14 (de_CH: same text as the base, no ß in it; flip IsTranslated)
UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-23 11:00:14.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585553 AND AD_Language='de_CH'
;

-- ============================================================================
-- AD_Column AD_Sequence.DateFormat (593756), String(40), nullable, element DateFormat (2286)
-- ============================================================================

-- 2026-10-23 11:01:00
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) SELECT 0,593756 /*From ID Server*/,2286,0,10,t.AD_Table_ID,'XX','DateFormat',TO_TIMESTAMP('2026-10-23 11:01:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N',e.Description,'D',0,40,e.Help,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,e.Name,'NP',0,0,TO_TIMESTAMP('2026-10-23 11:01:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0 FROM AD_Table t, AD_Element e WHERE t.TableName='AD_Sequence' AND e.AD_Element_ID=2286
;

-- 2026-10-23 11:01:00 (seed _Trl)
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593756 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-10-23 11:01:30 (column translations from its element DateFormat)
/* DDL */ select update_Column_Translation_From_AD_Element(2286)
;

-- ============================================================================
-- Physical DB column
-- ============================================================================

-- 2026-10-23 11:02:00
/* DDL */ SELECT public.db_alter_table('AD_Sequence','ALTER TABLE public.AD_Sequence ADD COLUMN DateFormat VARCHAR(40)')
;

-- ============================================================================
-- AD_Field on Nummernfolgen (112) -> Reihenfolge (146), label/description from AD_Element 585553
-- ============================================================================

-- 2026-10-23 11:03:00
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,Updated,UpdatedBy) VALUES (0,593756,785695 /*From ID Server*/,585553,0,146,TO_TIMESTAMP('2026-10-23 11:03:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datumsformat für Nummernfolgen mit datumsbasierter Implementierung, z.B. yy-DDD (zweistelliges Jahr und Tag des Jahres).',40,'@IsAutoSequence@=Y & @IsTableID@=N & @CustomSequenceNoProvider_JavaClass_ID/0@=540073','D','Wird von der datumsbasierten Implementierung verwendet, um das Datum der Nummer zu formatieren. Ist das Feld leer, gilt das systemweite Datumsformat aus der System-Konfiguration de.metas.document.seqNo.DateSequenceProvider.dateFormat. Für das Jahr yy verwenden (Kalenderjahr), nicht YY (Jahr der Kalenderwoche, Ende Dezember bereits das Folgejahr).','Y','Y','N','N','N','N','N','N','Datumsformat',108,0,TO_TIMESTAMP('2026-10-23 11:03:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-10-23 11:03:00 (seed _Trl)
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=785695 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-10-23 11:04:00 (field translations from its AD_Name_ID element)
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585553)
;

-- 2026-10-23 11:04:01
DELETE FROM AD_Element_Link WHERE AD_Field_ID=785695
;

-- 2026-10-23 11:04:02
/* DDL */ select AD_Element_Link_Create_Missing_Field(785695)
;

-- ============================================================================
-- AD_UI_Element: group "default" (540411), right after "Abw. Sequenznummer-Implementierung" (SeqNo 30); not in the grid
-- ============================================================================

-- 2026-10-23 11:05:00
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,785695,0,146,540411,655053 /*From ID Server*/,'F',TO_TIMESTAMP('2026-10-23 11:05:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Datumsformat',35,0,0,TO_TIMESTAMP('2026-10-23 11:05:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
