-- Column: AD_Client.AD_Archive_Storage_ID
-- 2024-11-11T13:06:28.994Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589383,583364,0,30,112,'XX','AD_Archive_Storage_ID',TO_TIMESTAMP('2024-11-11 13:06:28.726000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Archive Storage',0,0,TO_TIMESTAMP('2024-11-11 13:06:28.726000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-11-11T13:06:28.996Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589383 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-11-11T13:06:28.999Z
/* DDL */  select update_Column_Translation_From_AD_Element(583364)
;

-- 2024-11-11T13:06:32.003Z
/* DDL */ SELECT public.db_alter_table('AD_Client','ALTER TABLE public.AD_Client ADD COLUMN AD_Archive_Storage_ID NUMERIC(10)')
;

-- 2024-11-11T13:06:32.024Z
ALTER TABLE AD_Client ADD CONSTRAINT ADArchiveStorage_ADClient FOREIGN KEY (AD_Archive_Storage_ID) REFERENCES public.AD_Archive_Storage DEFERRABLE INITIALLY DEFERRED
;

-- Field: Mandant(109,D) -> Mandant(145,D) -> Archive Storage
-- Column: AD_Client.AD_Archive_Storage_ID
-- 2024-11-11T13:06:52.759Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589383,733472,0,145,TO_TIMESTAMP('2024-11-11 13:06:52.605000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Archive Storage',TO_TIMESTAMP('2024-11-11 13:06:52.605000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-11-11T13:06:52.762Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=733472 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-11-11T13:06:52.764Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583364)
;

-- 2024-11-11T13:06:52.767Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=733472
;

-- 2024-11-11T13:06:52.768Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(733472)
;

-- UI Element: Mandant(109,D) -> Mandant(145,D) -> advanced edit -> 10 -> advanced edit.Archive Storage
-- Column: AD_Client.AD_Archive_Storage_ID
-- 2024-11-11T13:07:37.742Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,733472,0,145,540476,627012,'F',TO_TIMESTAMP('2024-11-11 13:07:37.587000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Archive Storage',160,0,0,TO_TIMESTAMP('2024-11-11 13:07:37.587000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Mandant(109,D) -> Mandant(145,D) -> advanced edit -> 10 -> advanced edit.Archive Storage
-- Column: AD_Client.AD_Archive_Storage_ID
-- 2024-11-11T13:07:49.094Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2024-11-11 13:07:49.094000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627012
;

-- Run mode: SWING_CLIENT

-- Field: Archive Storage(541833,D) -> Archive Storage(547684,D) -> Art
-- Column: AD_Archive_Storage.Type
-- 2024-11-11T14:23:10.521Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=10,Updated=TO_TIMESTAMP('2024-11-11 14:23:10.521000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=733469
;

-- Field: Archive Storage(541833,D) -> Archive Storage(547684,D) -> Beschreibung
-- Column: AD_Archive_Storage.Description
-- 2024-11-11T14:23:10.535Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=20,Updated=TO_TIMESTAMP('2024-11-11 14:23:10.535000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=733470
;

-- Field: Archive Storage(541833,D) -> Archive Storage(547684,D) -> Pfad
-- Column: AD_Archive_Storage.Path
-- 2024-11-11T14:23:10.546Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=30,Updated=TO_TIMESTAMP('2024-11-11 14:23:10.546000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=733471
;

-- Field: Archive Storage(541833,D) -> Archive Storage(547684,D) -> Aktiv
-- Column: AD_Archive_Storage.IsActive
-- 2024-11-11T14:23:10.555Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=40,Updated=TO_TIMESTAMP('2024-11-11 14:23:10.555000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=733467
;

-- Field: Archive Storage(541833,D) -> Archive Storage(547684,D) -> Organisation
-- Column: AD_Archive_Storage.AD_Org_ID
-- 2024-11-11T14:23:10.562Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=50,Updated=TO_TIMESTAMP('2024-11-11 14:23:10.561000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=733466
;

-- Field: Archive Storage(541833,D) -> Archive Storage(547684,D) -> Mandant
-- Column: AD_Archive_Storage.AD_Client_ID
-- 2024-11-11T14:23:10.568Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=60,Updated=TO_TIMESTAMP('2024-11-11 14:23:10.568000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=733465
;

-- Field: Archive Storage(541833,D) -> Archive Storage(547684,D) -> Art
-- Column: AD_Archive_Storage.Type
-- 2024-11-11T14:23:21.330Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2024-11-11 14:23:21.330000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=733469
;

-- Field: Archive Storage(541833,D) -> Archive Storage(547684,D) -> Pfad
-- Column: AD_Archive_Storage.Path
-- 2024-11-11T14:23:21.339Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2024-11-11 14:23:21.339000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=733471
;

-- Field: Archive Storage(541833,D) -> Archive Storage(547684,D) -> Aktiv
-- Column: AD_Archive_Storage.IsActive
-- 2024-11-11T14:23:21.346Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2024-11-11 14:23:21.346000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=733467
;

-- Field: Archive Storage(541833,D) -> Archive Storage(547684,D) -> Organisation
-- Column: AD_Archive_Storage.AD_Org_ID
-- 2024-11-11T14:23:21.354Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2024-11-11 14:23:21.354000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=733466
;

-- Field: Archive Storage(541833,D) -> Archive Storage(547684,D) -> Mandant
-- Column: AD_Archive_Storage.AD_Client_ID
-- 2024-11-11T14:23:21.362Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2024-11-11 14:23:21.362000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=733465
;

-- Column: AD_Archive_Storage.Type
-- 2024-11-11T14:23:36.662Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-11-11 14:23:36.662000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589380
;

-- Column: AD_Archive_Storage.Path
-- 2024-11-11T14:23:47.365Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-11-11 14:23:47.365000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589382
;

-- UI Element: Archive Storage(541833,D) -> Archive Storage(547684,D) -> main -> 10 -> default.Art
-- Column: AD_Archive_Storage.Type
-- 2024-11-11T14:24:16.512Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2024-11-11 14:24:16.512000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627006
;

-- UI Element: Archive Storage(541833,D) -> Archive Storage(547684,D) -> main -> 10 -> file system.Pfad
-- Column: AD_Archive_Storage.Path
-- 2024-11-11T14:24:16.522Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2024-11-11 14:24:16.522000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627008
;

-- UI Element: Archive Storage(541833,D) -> Archive Storage(547684,D) -> main -> 20 -> flags.Aktiv
-- Column: AD_Archive_Storage.IsActive
-- 2024-11-11T14:24:16.533Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2024-11-11 14:24:16.532000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627009
;

-- UI Element: Archive Storage(541833,D) -> Archive Storage(547684,D) -> main -> 20 -> org&client.Organisation
-- Column: AD_Archive_Storage.AD_Org_ID
-- 2024-11-11T14:24:16.542Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2024-11-11 14:24:16.542000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627010
;

-- Field: Archive Storage(541833,D) -> Archive Storage(547684,D) -> Pfad
-- Column: AD_Archive_Storage.Path
-- 2024-11-11T14:25:43.473Z
UPDATE AD_Field SET DisplayLogic='@Type/x@=filesystem',Updated=TO_TIMESTAMP('2024-11-11 14:25:43.473000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=733471
;

-- Field: Archive Storage(541833,D) -> Archive Storage(547684,D) -> Pfad
-- Column: AD_Archive_Storage.Path
-- 2024-11-11T14:25:43.473Z
UPDATE AD_Field SET DisplayLogic='@Type/x@=filesystem',Updated=TO_TIMESTAMP('2024-11-11 14:25:43.473000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=733471
;
