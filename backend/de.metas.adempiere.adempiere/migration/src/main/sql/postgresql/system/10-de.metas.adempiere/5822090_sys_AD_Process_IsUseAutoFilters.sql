-- New AD_Process.IsUseAutoFilters (YesNo, default 'Y', mandatory) — controls whether opening the
-- target window of a Type='RelationTypeInOverlay' process also applies that window's own default
-- filters ('Y', today's behaviour, kept for every existing process) or shows exactly the rows the
-- relation resolved ('N').
--
-- IDs, all from the central ID server:
--   5822090 - this script's migration filename prefix
--    585420 - AD_Element (IsUseAutoFilters)
--    593467 - AD_Column  (AD_Process.IsUseAutoFilters)
--    784914 - AD_Field   (Bericht & Prozess tab, AD_Tab 245)
--    654684 - AD_UI_Element (element group 541397, SeqNo 100 — directly after OpenTarget at 90)
--
-- Modelled on 5804600_sys_gh29919_AD_Process_OpenTarget.sql (same table/tab/element group).

-- Element: IsUseAutoFilters
-- 2026-09-03T14:00:00.000Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,585420 /*From ID Server*/,0,'IsUseAutoFilters',TO_TIMESTAMP('2026-09-03 14:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Beim Sprung in das Zielfenster dessen Standardfilter anwenden. Ausgeschaltet zeigt der Sprung genau die verknüpften Zeilen.','D','Beim Sprung in das Zielfenster dessen Standardfilter anwenden. Ausgeschaltet zeigt der Sprung genau die verknüpften Zeilen.','Y','Standardfilter des Zielfensters anwenden','Standardfilter des Zielfensters anwenden',TO_TIMESTAMP('2026-09-03 14:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2026-09-03T14:00:00.000Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585420 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- DE translation: IsUseAutoFilters element (base language text, flip IsTranslated)
-- 2026-09-03T14:00:12.000Z
UPDATE AD_Element_Trl SET Name='Standardfilter des Zielfensters anwenden', PrintName='Standardfilter des Zielfensters anwenden', Description='Beim Sprung in das Zielfenster dessen Standardfilter anwenden. Ausgeschaltet zeigt der Sprung genau die verknüpften Zeilen.', Help='Beim Sprung in das Zielfenster dessen Standardfilter anwenden. Ausgeschaltet zeigt der Sprung genau die verknüpften Zeilen.', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-09-03 14:00:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Element_ID=585420 AND AD_Language='de_DE'
;

-- CH translation: same as DE (no ß, no Swiss term swap applies)
-- 2026-09-03T14:00:14.000Z
UPDATE AD_Element_Trl SET Name='Standardfilter des Zielfensters anwenden', PrintName='Standardfilter des Zielfensters anwenden', Description='Beim Sprung in das Zielfenster dessen Standardfilter anwenden. Ausgeschaltet zeigt der Sprung genau die verknüpften Zeilen.', Help='Beim Sprung in das Zielfenster dessen Standardfilter anwenden. Ausgeschaltet zeigt der Sprung genau die verknüpften Zeilen.', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-09-03 14:00:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Element_ID=585420 AND AD_Language='de_CH'
;

-- English translation (en_US)
-- 2026-09-03T14:00:16.000Z
UPDATE AD_Element_Trl SET Name='Apply target window''s default filters', PrintName='Apply target window''s default filters', Description='When jumping into the target window, also apply that window''s default filters. Switched off, the jump shows exactly the related rows.', Help='When jumping into the target window, also apply that window''s default filters. Switched off, the jump shows exactly the related rows.', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-09-03 14:00:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Element_ID=585420 AND AD_Language='en_US'
;

-- Column: AD_Process.IsUseAutoFilters — added NULLABLE first; backfilled and made mandatory below
-- (YesNo column, no AD_Reference_Value_ID needed for AD_Reference_ID=20)
-- 2026-09-03T14:01:00.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterInactiveValues,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593467 /*From ID Server*/,585420,0,20,284,'XX','IsUseAutoFilters',TO_TIMESTAMP('2026-09-03 14:01:00','YYYY-MM-DD HH24:MI:SS'),100,'N','Beim Sprung in das Zielfenster dessen Standardfilter anwenden. Ausgeschaltet zeigt der Sprung genau die verknüpften Zeilen.','D',0,1,'Beim Sprung in das Zielfenster dessen Standardfilter anwenden. Ausgeschaltet zeigt der Sprung genau die verknüpften Zeilen.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Y','N',0,'Standardfilter des Zielfensters anwenden','NP',0,0,TO_TIMESTAMP('2026-09-03 14:01:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2026-09-03T14:01:00.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593467 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-09-03T14:01:10.000Z
/* DDL */ select update_Column_Translation_From_AD_Element(585420)
;

-- 2026-09-03T14:01:20.000Z
/* DDL */ SELECT public.db_alter_table('AD_Process','ALTER TABLE public.AD_Process ADD COLUMN IsUseAutoFilters CHAR(1)')
;

-- 2026-09-03T14:01:21.000Z
INSERT INTO t_alter_column values('ad_process','IsUseAutoFilters','CHAR(1)',null,null)
;

-- Backfill: today's behaviour ('Y') on every pre-existing AD_Process row, before making the column mandatory
-- 2026-09-03T14:01:30.000Z
UPDATE AD_Process SET IsUseAutoFilters='Y', Updated=TO_TIMESTAMP('2026-09-03 14:01:30','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=99 WHERE IsUseAutoFilters IS NULL
;

-- Now make the column mandatory with default 'Y'
-- 2026-09-03T14:01:40.000Z
INSERT INTO t_alter_column values('ad_process','IsUseAutoFilters','CHAR(1)','NOT NULL','Y')
;

-- 2026-09-03T14:01:41.000Z
UPDATE AD_Column SET IsMandatory='Y', DefaultValue='Y', Updated=TO_TIMESTAMP('2026-09-03 14:01:41','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Column_ID=593467
;

-- Field: Bericht & Prozess(165,D) -> Bericht & Prozess(245,D) -> Standardfilter des Zielfensters anwenden
-- Column: AD_Process.IsUseAutoFilters
-- 2026-09-03T14:02:00.000Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,593467,784914 /*From ID Server*/,0,245,0,TO_TIMESTAMP('2026-09-03 14:02:00','YYYY-MM-DD HH24:MI:SS'),100,'Beim Sprung in das Zielfenster dessen Standardfilter anwenden. Ausgeschaltet zeigt der Sprung genau die verknüpften Zeilen.',0,'D',0,'Beim Sprung in das Zielfenster dessen Standardfilter anwenden. Ausgeschaltet zeigt der Sprung genau die verknüpften Zeilen.',0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Standardfilter des Zielfensters anwenden',0,401,380,0,1,1,TO_TIMESTAMP('2026-09-03 14:02:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2026-09-03T14:02:00.000Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=784914 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-09-03T14:02:10.000Z
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585420)
;

-- 2026-09-03T14:02:20.000Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=784914
;

-- 2026-09-03T14:02:21.000Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(784914)
;

-- Field: Bericht & Prozess(165,D) -> Bericht & Prozess(245,D) -> Standardfilter des Zielfensters anwenden
-- Column: AD_Process.IsUseAutoFilters
-- 2026-09-03T14:02:30.000Z
UPDATE AD_Field SET DisplayLogic='@Type@=''RelationTypeInOverlay''',Updated=TO_TIMESTAMP('2026-09-03 14:02:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=784914
;

-- UI Element: Bericht & Prozess(165,D) -> Bericht & Prozess(245,D) -> main -> 10 -> description.Standardfilter des Zielfensters anwenden
-- Column: AD_Process.IsUseAutoFilters
-- Placed directly after OpenTarget (AD_UI_Element 651843, SeqNo=90) in element group 541397
-- 2026-09-03T14:03:00.000Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,784914,0,245,541397,654684 /*From ID Server*/,'F',TO_TIMESTAMP('2026-09-03 14:03:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Standardfilter des Zielfensters anwenden',100,0,0,TO_TIMESTAMP('2026-09-03 14:03:00','YYYY-MM-DD HH24:MI:SS'),100)
;
