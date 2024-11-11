-- Run mode: SWING_CLIENT

-- Column: AD_Archive.AD_Archive_Storage_ID
-- 2024-11-11T16:17:40.735Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589384,583364,0,30,754,'XX','AD_Archive_Storage_ID',TO_TIMESTAMP('2024-11-11 16:17:40.302000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Archive Storage',0,0,TO_TIMESTAMP('2024-11-11 16:17:40.302000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-11-11T16:17:40.751Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589384 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-11-11T16:17:40.782Z
/* DDL */  select update_Column_Translation_From_AD_Element(583364)
;

-- 2024-11-11T16:17:42.710Z
/* DDL */ SELECT public.db_alter_table('AD_Archive','ALTER TABLE public.AD_Archive ADD COLUMN AD_Archive_Storage_ID NUMERIC(10)')
;

-- 2024-11-11T16:17:43.044Z
ALTER TABLE AD_Archive ADD CONSTRAINT ADArchiveStorage_ADArchive FOREIGN KEY (AD_Archive_Storage_ID) REFERENCES public.AD_Archive_Storage DEFERRABLE INITIALLY DEFERRED
;

-- Field: Archive(540176,de.metas.document.archive) -> Archiv(540483,de.metas.document.archive) -> Archive Storage
-- Column: AD_Archive.AD_Archive_Storage_ID
-- 2024-11-11T16:18:07.745Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589384,733475,0,540483,TO_TIMESTAMP('2024-11-11 16:18:06.531000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.document.archive','Y','N','N','N','N','N','N','N','Archive Storage',TO_TIMESTAMP('2024-11-11 16:18:06.531000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-11-11T16:18:07.750Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=733475 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-11-11T16:18:07.755Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583364)
;

-- 2024-11-11T16:18:07.769Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=733475
;

-- 2024-11-11T16:18:07.775Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(733475)
;

-- Field: Archive(540176,de.metas.document.archive) -> Archiv(540483,de.metas.document.archive) -> Nr.
-- Column: AD_Archive.DocumentNo
-- 2024-11-11T16:18:27.218Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=50,Updated=TO_TIMESTAMP('2024-11-11 16:18:27.218000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=649783
;

-- Field: Archive(540176,de.metas.document.archive) -> Archiv(540483,de.metas.document.archive) -> Geschäftspartner
-- Column: AD_Archive.C_BPartner_ID
-- 2024-11-11T16:18:27.227Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=60,Updated=TO_TIMESTAMP('2024-11-11 16:18:27.227000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=551548
;

-- Field: Archive(540176,de.metas.document.archive) -> Archiv(540483,de.metas.document.archive) -> DB-Tabelle
-- Column: AD_Archive.AD_Table_ID
-- 2024-11-11T16:18:27.235Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=70,Updated=TO_TIMESTAMP('2024-11-11 16:18:27.235000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=551544
;

-- Field: Archive(540176,de.metas.document.archive) -> Archiv(540483,de.metas.document.archive) -> Datensatz-ID
-- Column: AD_Archive.Record_ID
-- 2024-11-11T16:18:27.244Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=80,Updated=TO_TIMESTAMP('2024-11-11 16:18:27.244000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=551543
;

-- Field: Archive(540176,de.metas.document.archive) -> Archiv(540483,de.metas.document.archive) -> Prozess
-- Column: AD_Archive.AD_Process_ID
-- 2024-11-11T16:18:27.254Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=90,Updated=TO_TIMESTAMP('2024-11-11 16:18:27.254000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=551552
;

-- Field: Archive(540176,de.metas.document.archive) -> Archiv(540483,de.metas.document.archive) -> Binärwert
-- Column: AD_Archive.BinaryData
-- 2024-11-11T16:18:27.261Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=100,Updated=TO_TIMESTAMP('2024-11-11 16:18:27.261000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=551542
;

-- Field: Archive(540176,de.metas.document.archive) -> Archiv(540483,de.metas.document.archive) -> Aktiv
-- Column: AD_Archive.IsActive
-- 2024-11-11T16:18:27.270Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=110,Updated=TO_TIMESTAMP('2024-11-11 16:18:27.270000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=551536
;

-- Field: Archive(540176,de.metas.document.archive) -> Archiv(540483,de.metas.document.archive) -> Bericht
-- Column: AD_Archive.IsReport
-- 2024-11-11T16:18:27.278Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=120,Updated=TO_TIMESTAMP('2024-11-11 16:18:27.278000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=551540
;

-- Field: Archive(540176,de.metas.document.archive) -> Archiv(540483,de.metas.document.archive) -> In Druck-Warteschlange
-- Column: AD_Archive.IsDirectEnqueue
-- 2024-11-11T16:18:27.287Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=130,Updated=TO_TIMESTAMP('2024-11-11 16:18:27.286000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=551545
;

-- Field: Archive(540176,de.metas.document.archive) -> Archiv(540483,de.metas.document.archive) -> Warteschlangen-Eintrag sofort verarbeiten
-- Column: AD_Archive.IsDirectProcessQueueItem
-- 2024-11-11T16:18:27.295Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=140,Updated=TO_TIMESTAMP('2024-11-11 16:18:27.295000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=555208
;

-- Field: Archive(540176,de.metas.document.archive) -> Archiv(540483,de.metas.document.archive) -> Ausgehende Belege Konfig
-- Column: AD_Archive.C_Doc_Outbound_Config_ID
-- 2024-11-11T16:18:27.305Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=150,Updated=TO_TIMESTAMP('2024-11-11 16:18:27.305000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=556353
;

-- Field: Archive(540176,de.metas.document.archive) -> Archiv(540483,de.metas.document.archive) -> Beschreibung
-- Column: AD_Archive.Description
-- 2024-11-11T16:18:27.313Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=160,Updated=TO_TIMESTAMP('2024-11-11 16:18:27.313000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=551541
;

-- Field: Archive(540176,de.metas.document.archive) -> Archiv(540483,de.metas.document.archive) -> Kommentar/Hilfe
-- Column: AD_Archive.Help
-- 2024-11-11T16:18:27.320Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=170,Updated=TO_TIMESTAMP('2024-11-11 16:18:27.320000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=551549
;

-- Field: Archive(540176,de.metas.document.archive) -> Archiv(540483,de.metas.document.archive) -> IsFileSystem
-- Column: AD_Archive.IsFileSystem
-- 2024-11-11T16:18:27.330Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=180,Updated=TO_TIMESTAMP('2024-11-11 16:18:27.329000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=552887
;

-- Field: Archive(540176,de.metas.document.archive) -> Archiv(540483,de.metas.document.archive) -> Flavor
-- Column: AD_Archive.DocumentFlavor
-- 2024-11-11T16:18:27.337Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=190,Updated=TO_TIMESTAMP('2024-11-11 16:18:27.337000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=626428
;

-- Field: Archive(540176,de.metas.document.archive) -> Archiv(540483,de.metas.document.archive) -> Archive Storage
-- Column: AD_Archive.AD_Archive_Storage_ID
-- 2024-11-11T16:18:27.345Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=200,Updated=TO_TIMESTAMP('2024-11-11 16:18:27.345000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=733475
;

-- Field: Archive(540176,de.metas.document.archive) -> Archiv(540483,de.metas.document.archive) -> Archive Storage
-- Column: AD_Archive.AD_Archive_Storage_ID
-- 2024-11-11T16:18:30.986Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=200,Updated=TO_TIMESTAMP('2024-11-11 16:18:30.986000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=733475
;

