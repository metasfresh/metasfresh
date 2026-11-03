-- Column: DATEV_Export.C_Period_ID
-- 2026-05-11T07:05:28.852Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterInactiveValues,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592504,206,0,19,540934,'XX','C_Period_ID',TO_TIMESTAMP('2026-05-11 07:05:28.646000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Periode des Kalenders','de.metas.datev',0,10,'"Periode" bezeichnet einen eklusiven Datumsbereich eines Kalenders.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','Y','N',0,'Periode',0,0,TO_TIMESTAMP('2026-05-11 07:05:28.646000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-05-11T07:05:28.861Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592504 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-05-11T07:05:28.891Z
/* DDL */  select update_Column_Translation_From_AD_Element(206)
;

-- Column: DATEV_Export.C_Period_ID
-- 2026-05-11T07:30:56.683Z
UPDATE AD_Column SET DefaultValue='@C_Period_ID@', IsMandatory='N',Updated=TO_TIMESTAMP('2026-05-11 07:30:56.683000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=592504
;

-- 2026-05-11T07:30:57.339Z
/* DDL */ SELECT public.db_alter_table('DATEV_Export','ALTER TABLE public.DATEV_Export ADD COLUMN C_Period_ID NUMERIC(10)')
;

-- 2026-05-11T07:30:57.356Z
ALTER TABLE DATEV_Export ADD CONSTRAINT CPeriod_DATEVExport FOREIGN KEY (C_Period_ID) REFERENCES public.C_Period DEFERRABLE INITIALLY DEFERRED
;

-- Column: DATEV_Export.C_Period_ID
-- 2026-05-11T07:31:01.519Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2026-05-11 07:31:01.519000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=592504
;


-- Field: Buchungen Export(540413,de.metas.datev) -> Buchungen Export(541036,de.metas.datev) -> Periode
-- Column: DATEV_Export.C_Period_ID
-- 2026-05-11T07:56:25.123Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592504,779176,0,541036,TO_TIMESTAMP('2026-05-11 07:56:24.975000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Periode des Kalenders',10,'de.metas.datev','"Periode" bezeichnet einen eklusiven Datumsbereich eines Kalenders.','Y','N','N','N','N','N','N','N','Periode',TO_TIMESTAMP('2026-05-11 07:56:24.975000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-05-11T07:56:25.128Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=779176 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-05-11T07:56:25.132Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(206)
;

-- 2026-05-11T07:56:25.176Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=779176
;

-- 2026-05-11T07:56:25.182Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(779176)
;

-- Field: Buchungen Export(540413,de.metas.datev) -> Buchungen Export(541036,de.metas.datev) -> Buchungsdatum von
-- Column: DATEV_Export.DateAcctFrom
-- 2026-05-11T07:56:34.414Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2026-05-11 07:56:34.414000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=562734
;

-- Field: Buchungen Export(540413,de.metas.datev) -> Buchungen Export(541036,de.metas.datev) -> Buchungsdatum bis
-- Column: DATEV_Export.DateAcctTo
-- 2026-05-11T07:56:36.632Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2026-05-11 07:56:36.632000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=562735
;

-- UI Element: Buchungen Export(540413,de.metas.datev) -> Buchungen Export(541036,de.metas.datev) -> main -> 10 -> default.Periode
-- Column: DATEV_Export.C_Period_ID
-- 2026-05-11T07:56:56.905Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,779176,0,541036,541478,651160,'F',TO_TIMESTAMP('2026-05-11 07:56:56.755000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Periode des Kalenders','"Periode" bezeichnet einen eklusiven Datumsbereich eines Kalenders.','Y','N','N','Y','Y','N','N','Periode',5,10,0,TO_TIMESTAMP('2026-05-11 07:56:56.755000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Buchungen Export(540413,de.metas.datev) -> Buchungen Export(541036,de.metas.datev) -> main -> 10 -> default.Periode
-- Column: DATEV_Export.C_Period_ID
-- 2026-05-11T07:57:11.909Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2026-05-11 07:57:11.909000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=651160
;

-- UI Element: Buchungen Export(540413,de.metas.datev) -> Buchungen Export(541036,de.metas.datev) -> main -> 10 -> default.Buchungsdatum bis
-- Column: DATEV_Export.DateAcctTo
-- 2026-05-11T07:57:11.919Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2026-05-11 07:57:11.919000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=551035
;

-- UI Element: Buchungen Export(540413,de.metas.datev) -> Buchungen Export(541036,de.metas.datev) -> main -> 10 -> description.Beschreibung
-- Column: DATEV_Export.Description
-- 2026-05-11T07:57:11.927Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2026-05-11 07:57:11.927000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=551036
;

-- UI Element: Buchungen Export(540413,de.metas.datev) -> Buchungen Export(541036,de.metas.datev) -> main -> 20 -> flags.Verkaufstransaktion
-- Column: DATEV_Export.IsSOTrx
-- 2026-05-11T07:57:11.934Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2026-05-11 07:57:11.934000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=628432
;

-- UI Element: Buchungen Export(540413,de.metas.datev) -> Buchungen Export(541036,de.metas.datev) -> main -> 20 -> flags.Exclude already exported
-- Column: DATEV_Export.IsExcludeAlreadyExported
-- 2026-05-11T07:57:11.941Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2026-05-11 07:57:11.941000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=551037
;

-- UI Element: Buchungen Export(540413,de.metas.datev) -> Buchungen Export(541036,de.metas.datev) -> main -> 20 -> flags.Switch Credit Memo
-- Column: DATEV_Export.IsSwitchCreditMemo
-- 2026-05-11T07:57:11.946Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2026-05-11 07:57:11.946000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627390
;

-- UI Element: Buchungen Export(540413,de.metas.datev) -> Buchungen Export(541036,de.metas.datev) -> main -> 20 -> flags.Verarbeitet
-- Column: DATEV_Export.Processed
-- 2026-05-11T07:57:11.952Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2026-05-11 07:57:11.952000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=551038
;


-- Column: DATEV_Export.DateAcctTo
-- 2026-05-12T07:49:05.031Z
UPDATE AD_Column SET DefaultValue='@#Date@',Updated=TO_TIMESTAMP('2026-05-12 07:49:05.031000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=559134
;

