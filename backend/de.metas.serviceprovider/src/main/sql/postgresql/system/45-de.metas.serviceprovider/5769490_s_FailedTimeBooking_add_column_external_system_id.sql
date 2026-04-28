
-- Name: ExternalSystem
-- 2025-09-18T09:06:34.272Z
UPDATE AD_Reference SET EntityType='de.metas.externalsystem',Updated=TO_TIMESTAMP('2025-09-18 09:06:34.270000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Reference_ID=541988
;

-- Column: S_FailedTimeBooking.ExternalSystem_ID
-- 2025-09-18T09:06:43.665Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590940,583968,0,30,541988,541487,'XX','ExternalSystem_ID',TO_TIMESTAMP('2025-09-18 09:06:43.513000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.serviceprovider',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Externes System',0,0,TO_TIMESTAMP('2025-09-18 09:06:43.513000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-09-18T09:06:43.668Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590940 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-09-18T09:06:43.672Z
/* DDL */  select update_Column_Translation_From_AD_Element(583968)
;

-- 2025-09-18T09:06:47.107Z
/* DDL */ SELECT public.db_alter_table('S_FailedTimeBooking','ALTER TABLE public.S_FailedTimeBooking ADD COLUMN ExternalSystem_ID NUMERIC(10)')
;

-- 2025-09-18T09:06:47.114Z
ALTER TABLE S_FailedTimeBooking ADD CONSTRAINT ExternalSystem_SFailedTimeBooking FOREIGN KEY (ExternalSystem_ID) REFERENCES public.ExternalSystem DEFERRABLE INITIALLY DEFERRED
;

-- Field: Gescheiterte Zeitbuchung(540906,de.metas.serviceprovider) -> Gescheiterte Zeitbuchung(542444,de.metas.serviceprovider) -> Externes System
-- Column: S_FailedTimeBooking.ExternalSystem_ID
-- 2025-09-18T09:07:06.225Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590940,753783,0,542444,TO_TIMESTAMP('2025-09-18 09:07:06.083000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.serviceprovider','Y','N','N','N','N','N','N','N','Externes System',TO_TIMESTAMP('2025-09-18 09:07:06.083000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T09:07:06.229Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753783 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-18T09:07:06.232Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583968)
;

-- 2025-09-18T09:07:06.235Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753783
;

-- 2025-09-18T09:07:06.236Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753783)
;

-- UI Element: Gescheiterte Zeitbuchung(540906,de.metas.serviceprovider) -> Gescheiterte Zeitbuchung(542444,de.metas.serviceprovider) -> main -> 10 -> default.Externes System
-- Column: S_FailedTimeBooking.ExternalSystem_ID
-- 2025-09-18T09:08:13.524Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,753783,0,542444,543653,637133,'F',TO_TIMESTAMP('2025-09-18 09:08:13.228000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Externes System',31,0,0,TO_TIMESTAMP('2025-09-18 09:08:13.228000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Gescheiterte Zeitbuchung(540906,de.metas.serviceprovider) -> Gescheiterte Zeitbuchung(542444,de.metas.serviceprovider) -> main -> 10 -> default.Externes System
-- Column: S_FailedTimeBooking.ExternalSystem_ID
-- 2025-09-18T09:08:23.435Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2025-09-18 09:08:23.435000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637133
;

-- UI Element: Gescheiterte Zeitbuchung(540906,de.metas.serviceprovider) -> Gescheiterte Zeitbuchung(542444,de.metas.serviceprovider) -> main -> 10 -> default.External system
-- Column: S_FailedTimeBooking.ExternalSystem
-- 2025-09-18T09:08:23.444Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-09-18 09:08:23.444000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=567556
;

-- UI Element: Gescheiterte Zeitbuchung(540906,de.metas.serviceprovider) -> Gescheiterte Zeitbuchung(542444,de.metas.serviceprovider) -> main -> 10 -> default.External ID
-- Column: S_FailedTimeBooking.ExternalId
-- 2025-09-18T09:08:23.449Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-09-18 09:08:23.449000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=567555
;

-- UI Element: Gescheiterte Zeitbuchung(540906,de.metas.serviceprovider) -> Gescheiterte Zeitbuchung(542444,de.metas.serviceprovider) -> main -> 10 -> default.Import-Fehler
-- Column: S_FailedTimeBooking.ImportErrorMsg
-- 2025-09-18T09:08:23.455Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-09-18 09:08:23.455000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=567557
;
