-- Run mode: SWING_CLIENT

-- Column: C_Doc_Outbound_Config.AD_Archive_Storage_ID
-- 2024-11-12T07:21:43.457Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589385,583364,0,30,540434,'XX','AD_Archive_Storage_ID',TO_TIMESTAMP('2024-11-12 07:21:43.139000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Archive Storage',0,0,TO_TIMESTAMP('2024-11-12 07:21:43.139000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-11-12T07:21:43.468Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589385 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-11-12T07:21:43.501Z
/* DDL */  select update_Column_Translation_From_AD_Element(583364)
;

-- 2024-11-12T07:21:45.384Z
/* DDL */ SELECT public.db_alter_table('C_Doc_Outbound_Config','ALTER TABLE public.C_Doc_Outbound_Config ADD COLUMN AD_Archive_Storage_ID NUMERIC(10)')
;

-- 2024-11-12T07:21:45.394Z
ALTER TABLE C_Doc_Outbound_Config ADD CONSTRAINT ADArchiveStorage_CDocOutboundConfig FOREIGN KEY (AD_Archive_Storage_ID) REFERENCES public.AD_Archive_Storage DEFERRABLE INITIALLY DEFERRED
;

-- Field: Ausgehende Belege Konfig(540173,de.metas.document.archive) -> Ausgehende Belege Konfig(540477,de.metas.document.archive) -> Archive Storage
-- Column: C_Doc_Outbound_Config.AD_Archive_Storage_ID
-- 2024-11-12T07:22:08.208Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589385,733476,0,540477,TO_TIMESTAMP('2024-11-12 07:22:06.940000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.document.archive','Y','N','N','N','N','N','N','N','Archive Storage',TO_TIMESTAMP('2024-11-12 07:22:06.940000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-11-12T07:22:08.213Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=733476 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-11-12T07:22:08.217Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583364)
;

-- 2024-11-12T07:22:08.229Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=733476
;

-- 2024-11-12T07:22:08.231Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(733476)
;

-- Field: Ausgehende Belege Konfig(540173,de.metas.document.archive) -> Ausgehende Belege Konfig(540477,de.metas.document.archive) -> CC Pfad
-- Column: C_Doc_Outbound_Config.CCPath
-- 2024-11-12T07:22:23.231Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=90,Updated=TO_TIMESTAMP('2024-11-12 07:22:23.230000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=553967
;

-- Field: Ausgehende Belege Konfig(540173,de.metas.document.archive) -> Ausgehende Belege Konfig(540477,de.metas.document.archive) -> Archive Storage
-- Column: C_Doc_Outbound_Config.AD_Archive_Storage_ID
-- 2024-11-12T07:22:23.240Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=100,Updated=TO_TIMESTAMP('2024-11-12 07:22:23.240000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=733476
;

-- Field: Ausgehende Belege Konfig(540173,de.metas.document.archive) -> Ausgehende Belege Konfig(540477,de.metas.document.archive) -> CC Pfad
-- Column: C_Doc_Outbound_Config.CCPath
-- 2024-11-12T07:22:26.342Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2024-11-12 07:22:26.342000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=553967
;

-- Field: Ausgehende Belege Konfig(540173,de.metas.document.archive) -> Ausgehende Belege Konfig(540477,de.metas.document.archive) -> Archive Storage
-- Column: C_Doc_Outbound_Config.AD_Archive_Storage_ID
-- 2024-11-12T07:22:26.354Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2024-11-12 07:22:26.354000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=733476
;

-- UI Column: Ausgehende Belege Konfig(540173,de.metas.document.archive) -> Ausgehende Belege Konfig(540477,de.metas.document.archive) -> main -> 10
-- UI Element Group: storage
-- 2024-11-12T07:24:43.029Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540225,552149,TO_TIMESTAMP('2024-11-12 07:24:42.729000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','storage',30,TO_TIMESTAMP('2024-11-12 07:24:42.729000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Ausgehende Belege Konfig(540173,de.metas.document.archive) -> Ausgehende Belege Konfig(540477,de.metas.document.archive) -> main -> 10 -> storage.CC Pfad
-- Column: C_Doc_Outbound_Config.CCPath
-- 2024-11-12T07:25:03.999Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=552149, SeqNo=10,Updated=TO_TIMESTAMP('2024-11-12 07:25:03.998000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=543680
;

-- UI Element: Ausgehende Belege Konfig(540173,de.metas.document.archive) -> Ausgehende Belege Konfig(540477,de.metas.document.archive) -> main -> 10 -> storage.Archive Storage
-- Column: C_Doc_Outbound_Config.AD_Archive_Storage_ID
-- 2024-11-12T07:25:26.547Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,733476,0,540477,552149,627015,'F',TO_TIMESTAMP('2024-11-12 07:25:26.266000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Archive Storage',20,0,0,TO_TIMESTAMP('2024-11-12 07:25:26.266000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

