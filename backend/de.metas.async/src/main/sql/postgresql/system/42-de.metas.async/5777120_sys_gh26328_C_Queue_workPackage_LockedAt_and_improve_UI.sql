-- Run mode: SWING_CLIENT

-- 2025-11-14T21:31:03.248Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584212,0,'LockedAt',TO_TIMESTAMP('2025-11-14 21:31:03.049000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zeitpunkt, zu dem der Datensatz zur Verarbeitung gesperrt wurde','D','Y','Gesperrt','Gesperrt',TO_TIMESTAMP('2025-11-14 21:31:03.049000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-14T21:31:03.269Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584212 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: LockedAt
-- 2025-11-14T21:31:08.908Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-11-14 21:31:08.907000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584212 AND AD_Language='de_CH'
;

-- 2025-11-14T21:31:08.960Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584212,'de_CH')
;

-- Element: LockedAt
-- 2025-11-14T21:31:12.828Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-11-14 21:31:12.828000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584212 AND AD_Language='de_DE'
;

-- 2025-11-14T21:31:12.832Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584212,'de_DE')
;

-- 2025-11-14T21:31:12.833Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584212,'de_DE')
;

-- Element: LockedAt
-- 2025-11-14T21:31:41.060Z
UPDATE AD_Element_Trl SET Description='Time at which the data record was blocked for processing', IsTranslated='Y', Name='Locked', PrintName='Locked',Updated=TO_TIMESTAMP('2025-11-14 21:31:41.059000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584212 AND AD_Language='en_US'
;

-- 2025-11-14T21:31:41.061Z
UPDATE AD_Element base SET Description=trl.Description, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-11-14T21:31:41.454Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584212,'en_US')
;

-- Column: C_Queue_WorkPackage.LockedAt
-- 2025-11-14T21:34:00.438Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591515,584212,0,16,540425,'XX','LockedAt',TO_TIMESTAMP('2025-11-14 21:34:00.306000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Zeitpunkt, zu dem der Datensatz zur Verarbeitung gesperrt wurde','de.metas.async',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Gesperrt',0,0,TO_TIMESTAMP('2025-11-14 21:34:00.306000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-11-14T21:34:00.441Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591515 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-11-14T21:34:00.558Z
/* DDL */  select update_Column_Translation_From_AD_Element(584212)
;

-- 2025-11-14T21:34:02.493Z
/* DDL */ SELECT public.db_alter_table('C_Queue_WorkPackage','ALTER TABLE public.C_Queue_WorkPackage ADD COLUMN LockedAt TIMESTAMP WITH TIME ZONE')
;

COMMENT ON COLUMN C_Queue_WorkPackage.LockedAt IS
    'Timestamp when this workpackage was locked for processing. Used with SELECT FOR UPDATE SKIP LOCKED for optimized concurrent polling. Set when locked, cleared when processed/skipped/errored.';

-- Create partial index for workpackage polling queries
-- Column order: most selective equality filters first, then range, then ORDER BY
-- Partial index on unprocessed records only = smaller, faster
CREATE INDEX idx_queue_workpackage_polling
    ON C_Queue_WorkPackage (
                            C_Queue_PackageProcessor_ID,  -- Most selective equality filter (narrows to specific processor)
                            IsReadyForProcessing,          -- Equality filter
                            IsError,                       -- Equality filter
                            LockedAt,                      -- Equality filter (IS NULL)
                            Priority,                      -- Range filter + ORDER BY
                            C_Queue_WorkPackage_ID         -- ORDER BY
        )
    WHERE Processed = 'Y';

COMMENT ON INDEX idx_queue_workpackage_polling IS
    'Optimized index for workpackage polling with FOR UPDATE SKIP LOCKED. Column order: C_Queue_PackageProcessor_ID first (most selective), then ready/error/locked filters, then Priority/ID for sorting.';



-- UI Element: Asynchrone Verarbeitungswarteschlange(540163,de.metas.async) -> Arbeitspaket Warteschlange(540456,de.metas.async) -> main -> 20 -> flags.In Verarbeitung
-- Column: C_Queue_WorkPackage.Locked
-- 2025-11-14T21:34:32.934Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546471
;

-- 2025-11-14T21:34:46.344Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=553927
;

-- Field: Asynchrone Verarbeitungswarteschlange(540163,de.metas.async) -> Arbeitspaket Warteschlange(540456,de.metas.async) -> Gesperrt
-- Column: C_Queue_WorkPackage.Locked
-- 2025-11-14T21:34:46.351Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=553927
;

-- 2025-11-14T21:34:46.358Z
DELETE FROM AD_Field WHERE AD_Field_ID=553927
;

-- Column: C_Queue_WorkPackage.Locked
-- 2025-11-14T21:35:04.605Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=550200
;

-- 2025-11-14T21:35:04.611Z
DELETE FROM AD_Column WHERE AD_Column_ID=550200
;

-- Field: Asynchrone Verarbeitungswarteschlange(540163,de.metas.async) -> Arbeitspaket Warteschlange(540456,de.metas.async) -> Gesperrt
-- Column: C_Queue_WorkPackage.LockedAt
-- 2025-11-14T21:35:30.144Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591515,756152,0,540456,0,TO_TIMESTAMP('2025-11-14 21:35:30.023000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zeitpunkt, zu dem der Datensatz zur Verarbeitung gesperrt wurde',0,'de.metas.async',0,0,'Y','Y','Y','N','N','N','N','N','Y','N',0,'Gesperrt',0,0,180,0,1,1,TO_TIMESTAMP('2025-11-14 21:35:30.023000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-14T21:35:30.149Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756152 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-14T21:35:30.152Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584212)
;

-- 2025-11-14T21:35:30.158Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756152
;

-- 2025-11-14T21:35:30.159Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756152)
;

-- Field: Asynchrone Verarbeitungswarteschlange(540163,de.metas.async) -> Arbeitspaket Warteschlange(540456,de.metas.async) -> Ansprechpartner
-- Column: C_Queue_WorkPackage.AD_User_ID
-- 2025-11-14T21:35:47.494Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2025-11-14 21:35:47.494000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=556189
;

-- Field: Asynchrone Verarbeitungswarteschlange(540163,de.metas.async) -> Arbeitspaket Warteschlange(540456,de.metas.async) -> Rolle
-- Column: C_Queue_WorkPackage.AD_Role_ID
-- 2025-11-14T21:35:50.594Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2025-11-14 21:35:50.594000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=556188
;

-- Field: Asynchrone Verarbeitungswarteschlange(540163,de.metas.async) -> Arbeitspaket Warteschlange(540456,de.metas.async) -> Aktualisiert durch
-- Column: C_Queue_WorkPackage.UpdatedBy
-- 2025-11-14T21:35:51.755Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2025-11-14 21:35:51.755000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=551104
;

-- Field: Asynchrone Verarbeitungswarteschlange(540163,de.metas.async) -> Arbeitspaket Warteschlange(540456,de.metas.async) -> Erstellt durch
-- Column: C_Queue_WorkPackage.CreatedBy
-- 2025-11-14T21:35:52.940Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2025-11-14 21:35:52.940000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=551107
;

-- Field: Asynchrone Verarbeitungswarteschlange(540163,de.metas.async) -> Arbeitspaket Warteschlange(540456,de.metas.async) -> Prozess-Instanz
-- Column: C_Queue_WorkPackage.AD_PInstance_ID
-- 2025-11-14T21:35:53.834Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2025-11-14 21:35:53.833000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=551113
;

-- Field: Asynchrone Verarbeitungswarteschlange(540163,de.metas.async) -> Arbeitspaket Warteschlange(540456,de.metas.async) -> WorkPackage Processor
-- Column: C_Queue_WorkPackage.C_Queue_PackageProcessor_ID
-- 2025-11-14T21:35:55.130Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2025-11-14 21:35:55.129000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=554265
;

-- Field: Asynchrone Verarbeitungswarteschlange(540163,de.metas.async) -> Arbeitspaket Warteschlange(540456,de.metas.async) -> Aktualisiert
-- Column: C_Queue_WorkPackage.Updated
-- 2025-11-14T21:35:55.591Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2025-11-14 21:35:55.591000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=551103
;

-- Field: Asynchrone Verarbeitungswarteschlange(540163,de.metas.async) -> Arbeitspaket Warteschlange(540456,de.metas.async) -> Erstellt
-- Column: C_Queue_WorkPackage.Created
-- 2025-11-14T21:35:57.294Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2025-11-14 21:35:57.294000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=551106
;

-- UI Element: Asynchrone Verarbeitungswarteschlange(540163,de.metas.async) -> Arbeitspaket Warteschlange(540456,de.metas.async) -> main -> 20 -> error.Fehlermeldung
-- Column: C_Queue_WorkPackage.ErrorMsg
-- 2025-11-14T21:36:35.579Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546473
;

-- UI Element: Asynchrone Verarbeitungswarteschlange(540163,de.metas.async) -> Arbeitspaket Warteschlange(540456,de.metas.async) -> main -> 20 -> error.Probleme
-- Column: C_Queue_WorkPackage.AD_Issue_ID
-- 2025-11-14T21:36:52.002Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551893,0,540456,540810,638727,'F',TO_TIMESTAMP('2025-11-14 21:36:51.876000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Probleme',40,0,0,TO_TIMESTAMP('2025-11-14 21:36:51.876000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Asynchrone Verarbeitungswarteschlange(540163,de.metas.async) -> Arbeitspaket Warteschlange(540456,de.metas.async) -> main -> 20 -> flags.Gesperrt
-- Column: C_Queue_WorkPackage.LockedAt
-- 2025-11-14T21:37:31.548Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,756152,0,540456,540809,638728,'F',TO_TIMESTAMP('2025-11-14 21:37:31.417000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zeitpunkt, zu dem der Datensatz zur Verarbeitung gesperrt wurde','Y','N','N','Y','N','N','N',0,'Gesperrt',35,0,0,TO_TIMESTAMP('2025-11-14 21:37:31.417000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Asynchrone Verarbeitungswarteschlange(540163,de.metas.async) -> Arbeitspaket Warteschlange(540456,de.metas.async) -> main -> 20 -> flags.Gesperrt
-- Column: C_Queue_WorkPackage.LockedAt
-- 2025-11-14T21:37:45.503Z
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2025-11-14 21:37:45.503000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=638728
;

-- UI Element: Asynchrone Verarbeitungswarteschlange(540163,de.metas.async) -> Arbeitspaket Warteschlange(540456,de.metas.async) -> advanced edit -> 10 -> advanced edit.System-Problem
-- Column: C_Queue_WorkPackage.AD_Issue_ID
-- 2025-11-14T21:38:22.393Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546424
;

-- UI Element: Asynchrone Verarbeitungswarteschlange(540163,de.metas.async) -> Element(540457,de.metas.async) -> main -> 10 -> default.WorkPackage Queue
-- Column: C_Queue_Element.C_Queue_WorkPackage_ID
-- 2025-11-14T21:40:17.653Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2025-11-14 21:40:17.653000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=546438
;

-- UI Element: Asynchrone Verarbeitungswarteschlange(540163,de.metas.async) -> Element(540457,de.metas.async) -> main -> 10 -> default.Sektion
-- Column: C_Queue_Element.AD_Org_ID
-- 2025-11-14T21:40:17.661Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2025-11-14 21:40:17.661000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=546437
;

-- UI Element: Asynchrone Verarbeitungswarteschlange(540163,de.metas.async) -> Element(540457,de.metas.async) -> main -> 10 -> default.DB-Tabelle
-- Column: C_Queue_Element.AD_Table_ID
-- 2025-11-14T21:40:17.669Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2025-11-14 21:40:17.668000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=546439
;

-- UI Element: Asynchrone Verarbeitungswarteschlange(540163,de.metas.async) -> Element(540457,de.metas.async) -> main -> 10 -> default.Datensatz-ID
-- Column: C_Queue_Element.Record_ID
-- 2025-11-14T21:40:17.676Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-11-14 21:40:17.676000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=546440
;

-- UI Element: Asynchrone Verarbeitungswarteschlange(540163,de.metas.async) -> Element(540457,de.metas.async) -> main -> 10 -> default.Ältestes nicht verarb. Vorgänger-Paket
-- Column: C_Queue_Element.C_Queue_Workpackage_Preceeding_ID
-- 2025-11-14T21:40:17.683Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-11-14 21:40:17.683000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=546441
;

-- UI Element: Asynchrone Verarbeitungswarteschlange(540163,de.metas.async) -> Element(540457,de.metas.async) -> main -> 10 -> default.DB-Tabelle
-- Column: C_Queue_Element.AD_Table_ID
-- 2025-11-14T21:40:35.699Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2025-11-14 21:40:35.699000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=546439
;

-- UI Element: Asynchrone Verarbeitungswarteschlange(540163,de.metas.async) -> Element(540457,de.metas.async) -> main -> 10 -> default.Datensatz-ID
-- Column: C_Queue_Element.Record_ID
-- 2025-11-14T21:40:35.705Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2025-11-14 21:40:35.705000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=546440
;

-- UI Element: Asynchrone Verarbeitungswarteschlange(540163,de.metas.async) -> Element(540457,de.metas.async) -> main -> 10 -> default.Ältestes nicht verarb. Vorgänger-Paket
-- Column: C_Queue_Element.C_Queue_Workpackage_Preceeding_ID
-- 2025-11-14T21:40:35.713Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-11-14 21:40:35.712000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=546441
;

-- UI Element: Asynchrone Verarbeitungswarteschlange(540163,de.metas.async) -> Element(540457,de.metas.async) -> main -> 10 -> default.Sektion
-- Column: C_Queue_Element.AD_Org_ID
-- 2025-11-14T21:40:35.720Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-11-14 21:40:35.720000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=546437
;

-- UI Element: Asynchrone Verarbeitungswarteschlange(540163,de.metas.async) -> Log(540661,de.metas.async) -> main -> 10 -> default.WorkPackage Queue
-- Column: C_Queue_WorkPackage_Log.C_Queue_WorkPackage_ID
-- 2025-11-14T21:40:50.136Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2025-11-14 21:40:50.136000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=546442
;

-- UI Element: Asynchrone Verarbeitungswarteschlange(540163,de.metas.async) -> Log(540661,de.metas.async) -> main -> 10 -> default.Workpackage audit/log table
-- Column: C_Queue_WorkPackage_Log.C_Queue_WorkPackage_Log_ID
-- 2025-11-14T21:40:50.144Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2025-11-14 21:40:50.144000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=546443
;

-- UI Element: Asynchrone Verarbeitungswarteschlange(540163,de.metas.async) -> Log(540661,de.metas.async) -> main -> 10 -> default.Erstellt
-- Column: C_Queue_WorkPackage_Log.Created
-- 2025-11-14T21:40:50.150Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2025-11-14 21:40:50.150000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=546444
;

-- UI Element: Asynchrone Verarbeitungswarteschlange(540163,de.metas.async) -> Log(540661,de.metas.async) -> main -> 10 -> default.Message Text
-- Column: C_Queue_WorkPackage_Log.MsgText
-- 2025-11-14T21:40:50.157Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-11-14 21:40:50.157000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=546445
;

-- UI Element: Asynchrone Verarbeitungswarteschlange(540163,de.metas.async) -> Log(540661,de.metas.async) -> main -> 10 -> default.System-Problem
-- Column: C_Queue_WorkPackage_Log.AD_Issue_ID
-- 2025-11-14T21:40:50.173Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-11-14 21:40:50.173000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=566291
;

-- UI Column: Asynchrone Verarbeitungswarteschlange(540163,de.metas.async) -> Arbeitspaket Warteschlange(540456,de.metas.async) -> main -> 10
-- UI Element Group: skipped
-- 2025-11-14T21:41:42.396Z
UPDATE AD_UI_ElementGroup SET Name='skipped',Updated=TO_TIMESTAMP('2025-11-14 21:41:42.396000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540808
;

-- UI Element: Asynchrone Verarbeitungswarteschlange(540163,de.metas.async) -> Arbeitspaket Warteschlange(540456,de.metas.async) -> main -> 10 -> default.Async Batch
-- Column: C_Queue_WorkPackage.C_Async_Batch_ID
-- 2025-11-14T21:42:19.166Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555027,0,540456,540807,638729,'F',TO_TIMESTAMP('2025-11-14 21:42:19.038000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Async Batch',50,0,0,TO_TIMESTAMP('2025-11-14 21:42:19.038000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Asynchrone Verarbeitungswarteschlange(540163,de.metas.async) -> Arbeitspaket Warteschlange(540456,de.metas.async) -> main -> 10 -> default.Async Batch
-- Column: C_Queue_WorkPackage.C_Async_Batch_ID
-- 2025-11-14T21:42:30.935Z
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2025-11-14 21:42:30.935000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=638729
;

-- UI Element: Asynchrone Verarbeitungswarteschlange(540163,de.metas.async) -> Arbeitspaket Warteschlange(540456,de.metas.async) -> advanced edit -> 10 -> advanced edit.Async Batch
-- Column: C_Queue_WorkPackage.C_Async_Batch_ID
-- 2025-11-14T21:43:06.793Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546421
;

-- UI Element: Asynchrone Verarbeitungswarteschlange(540163,de.metas.async) -> Arbeitspaket Warteschlange(540456,de.metas.async) -> main -> 10 -> default.Async Batch
-- Column: C_Queue_WorkPackage.C_Async_Batch_ID
-- 2025-11-14T21:43:30.033Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-11-14 21:43:30.033000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=638729
;

-- UI Element: Asynchrone Verarbeitungswarteschlange(540163,de.metas.async) -> Arbeitspaket Warteschlange(540456,de.metas.async) -> main -> 20 -> error.Erstellt
-- Column: C_Queue_WorkPackage.Created
-- 2025-11-14T21:43:30.038Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-11-14 21:43:30.038000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=554500
;

-- UI Element: Asynchrone Verarbeitungswarteschlange(540163,de.metas.async) -> Arbeitspaket Warteschlange(540456,de.metas.async) -> main -> 20 -> error.Erstellt durch
-- Column: C_Queue_WorkPackage.CreatedBy
-- 2025-11-14T21:43:30.043Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-11-14 21:43:30.043000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=554501
;

-- UI Element: Asynchrone Verarbeitungswarteschlange(540163,de.metas.async) -> Arbeitspaket Warteschlange(540456,de.metas.async) -> main -> 10 -> default.Priorität
-- Column: C_Queue_WorkPackage.Priority
-- 2025-11-14T21:43:30.048Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2025-11-14 21:43:30.048000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=546465
;

-- UI Element: Asynchrone Verarbeitungswarteschlange(540163,de.metas.async) -> Arbeitspaket Warteschlange(540456,de.metas.async) -> main -> 20 -> flags.Verarbeitet
-- Column: C_Queue_WorkPackage.Processed
-- 2025-11-14T21:43:30.054Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2025-11-14 21:43:30.054000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=546469
;

-- UI Element: Asynchrone Verarbeitungswarteschlange(540163,de.metas.async) -> Arbeitspaket Warteschlange(540456,de.metas.async) -> main -> 20 -> flags.Bereit zur Verarbeitung
-- Column: C_Queue_WorkPackage.IsReadyForProcessing
-- 2025-11-14T21:43:30.058Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2025-11-14 21:43:30.058000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=546470
;

-- UI Element: Asynchrone Verarbeitungswarteschlange(540163,de.metas.async) -> Arbeitspaket Warteschlange(540456,de.metas.async) -> main -> 10 -> skipped.Zuerst übersprungen
-- Column: C_Queue_WorkPackage.Skipped_First_Time
-- 2025-11-14T21:43:30.063Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2025-11-14 21:43:30.063000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=546466
;

-- UI Element: Asynchrone Verarbeitungswarteschlange(540163,de.metas.async) -> Arbeitspaket Warteschlange(540456,de.metas.async) -> main -> 10 -> skipped.Zuletzt übersprungen
-- Column: C_Queue_WorkPackage.SkippedAt
-- 2025-11-14T21:43:30.067Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2025-11-14 21:43:30.067000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=546467
;

-- UI Element: Asynchrone Verarbeitungswarteschlange(540163,de.metas.async) -> Arbeitspaket Warteschlange(540456,de.metas.async) -> main -> 10 -> skipped.Übersprungen Grund
-- Column: C_Queue_WorkPackage.Skipped_Last_Reason
-- 2025-11-14T21:43:30.071Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2025-11-14 21:43:30.071000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=546468
;

-- UI Element: Asynchrone Verarbeitungswarteschlange(540163,de.metas.async) -> Arbeitspaket Warteschlange(540456,de.metas.async) -> main -> 20 -> org.Sektion
-- Column: C_Queue_WorkPackage.AD_Org_ID
-- 2025-11-14T21:43:30.077Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2025-11-14 21:43:30.077000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=546474
;

-- Run mode: SWING_CLIENT

-- Field: Asynchrone Verarbeitungswarteschlange(540163,de.metas.async) -> Arbeitspaket Warteschlange(540456,de.metas.async) -> Gesperrt
-- Column: C_Queue_WorkPackage.LockedAt
-- 2025-11-14T23:20:56.704Z
UPDATE AD_Field SET SeqNo=5,Updated=TO_TIMESTAMP('2025-11-14 23:20:56.704000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=756152
;

/*
 * #%L
 * de.metas.async
 * %%
 * Copyright (C) 2025 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

-- UI Element: Asynchrone Verarbeitungswarteschlange(540163,de.metas.async) -> Arbeitspaket Warteschlange(540456,de.metas.async) -> main -> 20 -> flags.Gesperrt
-- Column: C_Queue_WorkPackage.LockedAt
-- 2025-11-14T23:21:27.102Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2025-11-14 23:21:27.102000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=638728
;

-- UI Element: Asynchrone Verarbeitungswarteschlange(540163,de.metas.async) -> Arbeitspaket Warteschlange(540456,de.metas.async) -> main -> 10 -> skipped.Zuerst übersprungen
-- Column: C_Queue_WorkPackage.Skipped_First_Time
-- 2025-11-14T23:21:27.112Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2025-11-14 23:21:27.112000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=546466
;

-- UI Element: Asynchrone Verarbeitungswarteschlange(540163,de.metas.async) -> Arbeitspaket Warteschlange(540456,de.metas.async) -> main -> 10 -> skipped.Zuletzt übersprungen
-- Column: C_Queue_WorkPackage.SkippedAt
-- 2025-11-14T23:21:27.120Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2025-11-14 23:21:27.120000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=546467
;

-- UI Element: Asynchrone Verarbeitungswarteschlange(540163,de.metas.async) -> Arbeitspaket Warteschlange(540456,de.metas.async) -> main -> 10 -> skipped.Übersprungen Grund
-- Column: C_Queue_WorkPackage.Skipped_Last_Reason
-- 2025-11-14T23:21:27.126Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2025-11-14 23:21:27.126000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=546468
;

-- UI Element: Asynchrone Verarbeitungswarteschlange(540163,de.metas.async) -> Arbeitspaket Warteschlange(540456,de.metas.async) -> main -> 20 -> org.Sektion
-- Column: C_Queue_WorkPackage.AD_Org_ID
-- 2025-11-14T23:21:27.133Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2025-11-14 23:21:27.132000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=546474
;

-- Field: Asynchrone Verarbeitungswarteschlange(540163,de.metas.async) -> Arbeitspaket Warteschlange(540456,de.metas.async) -> Gesperrt
-- Column: C_Queue_WorkPackage.LockedAt
-- 2025-11-14T23:37:19.269Z
UPDATE AD_Field SET IsReadOnly='N',Updated=TO_TIMESTAMP('2025-11-14 23:37:19.269000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=756152
;

