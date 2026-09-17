-- Run mode: SWING_CLIENT

-- UI Element: Prognose(328,D) -> Prognose(653,D) -> main -> 20 -> flags.Belegstatus
-- Column: M_Forecast.DocStatus
-- 2026-06-22T12:00:52.978Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560389,0,653,540277,652351,'F',TO_TIMESTAMP('2026-06-22 12:00:51.517000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'The current status of the document','The Document Status indicates the status of a document at this time.  If you want to change the document status, use the Document Action field','Y','N','N','Y','N','N','N',0,'Belegstatus',20,0,0,TO_TIMESTAMP('2026-06-22 12:00:51.517000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Prognose(328,D) -> Prognose(653,D) -> main -> 20 -> flags.Aktiv
-- Column: M_Forecast.IsActive
-- 2026-06-22T12:02:35.058Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2026-06-22 12:02:35.058000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=543144
;

-- UI Element: Prognose(328,D) -> Prognose(653,D) -> main -> 20 -> flags.Belegstatus
-- Column: M_Forecast.DocStatus
-- 2026-06-22T12:02:35.357Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2026-06-22 12:02:35.357000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=652351
;

-- UI Element: Prognose(328,D) -> Prognose(653,D) -> advanced edit -> 10 -> description.Kommentar
-- Column: M_Forecast.Help
-- 2026-06-22T12:02:35.661Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2026-06-22 12:02:35.660000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=543150
;

-- UI Element: Prognose(328,D) -> Prognose(653,D) -> main -> 20 -> org.Sektion
-- Column: M_Forecast.AD_Org_ID
-- 2026-06-22T12:02:35.963Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2026-06-22 12:02:35.963000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=543148
;

-- UI Element: Prognose(328,D) -> Prognose(653,D) -> main -> 20 -> flags.Belegstatus
-- Column: M_Forecast.DocStatus
-- 2026-06-22T12:15:49.528Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2026-06-22 12:15:49.528000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=652351
;

-- UI Element: Prognose(328,D) -> Prognose(653,D) -> main -> 10 -> default.Zugesagter Termin
-- Column: M_Forecast.DatePromised
-- 2026-06-22T12:15:49.826Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2026-06-22 12:15:49.825000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=549293
;

-- Column: M_Forecast.DocStatus
-- 2026-06-22T12:25:04.279Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2026-06-22 12:25:04.279000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=557363
;

-- Column: M_Forecast.IsActive
-- 2026-06-22T12:25:51.715Z
UPDATE AD_Column SET IsSelectionColumn='N', SelectionColumnSeqNo=0,Updated=TO_TIMESTAMP('2026-06-22 12:25:51.715000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=11913
;

-- Column: M_Forecast.DocStatus
-- 2026-06-22T12:25:53.544Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=20,Updated=TO_TIMESTAMP('2026-06-22 12:25:53.544000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=557363
;

-- Column: M_Forecast.DatePromised
-- 2026-06-22T12:25:55.437Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=30,Updated=TO_TIMESTAMP('2026-06-22 12:25:55.437000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=557887
;

-- Column: M_Forecast.AD_Org_ID
-- 2026-06-22T12:25:56.623Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=40,Updated=TO_TIMESTAMP('2026-06-22 12:25:56.623000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=11917
;

