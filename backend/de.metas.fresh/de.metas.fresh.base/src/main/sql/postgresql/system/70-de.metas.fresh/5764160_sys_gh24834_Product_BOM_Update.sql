-- Run mode: SWING_CLIENT

-- UI Element: Stücklistenkonfiguration Version(53006,EE01) -> Stücklistenartikel(53028,EE01) -> main -> 20 -> doc.Belegstatus
-- Column: PP_Product_BOM.DocStatus
-- 2025-08-27T16:41:56.337Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,680053,0,53028,548109,636468,'F',TO_TIMESTAMP('2025-08-27 16:41:55.528000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'The current status of the document','The Document Status indicates the status of a document at this time.  If you want to change the document status, use the Document Action field','Y','N','N','Y','N','N','N',0,'Belegstatus',40,0,0,TO_TIMESTAMP('2025-08-27 16:41:55.528000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Stücklistenkonfiguration Version(53006,EE01) -> Stücklistenartikel(53028,EE01) -> main -> 20 -> doc.Belegstatus
-- Column: PP_Product_BOM.DocStatus
-- 2025-08-27T16:42:29.152Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-08-27 16:42:29.152000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636468
;

-- UI Element: Stücklistenkonfiguration Version(53006,EE01) -> Stücklistenartikel(53028,EE01) -> main -> 20 -> doc.Nr.
-- Column: PP_Product_BOM.DocumentNo
-- 2025-08-27T16:42:29.530Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-08-27 16:42:29.530000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=601117
;

-- UI Element: Stücklistenkonfiguration Version(53006,EE01) -> Stücklistenartikel(53028,EE01) -> main -> 10 -> default.Name
-- Column: PP_Product_BOM.Name
-- 2025-08-27T16:42:29.914Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-08-27 16:42:29.914000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=544419
;

-- UI Element: Stücklistenkonfiguration Version(53006,EE01) -> Stücklistenartikel(53028,EE01) -> main -> 10 -> default.Suchschlüssel
-- Column: PP_Product_BOM.Value
-- 2025-08-27T16:42:30.289Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2025-08-27 16:42:30.289000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=544418
;

-- UI Element: Stücklistenkonfiguration Version(53006,EE01) -> Stücklistenartikel(53028,EE01) -> main -> 10 -> default.Maßeinheit
-- Column: PP_Product_BOM.C_UOM_ID
-- 2025-08-27T16:42:30.670Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2025-08-27 16:42:30.670000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=544431
;

-- Column: PP_Product_BOM.ValidTo
-- 2025-08-27T16:45:17.391Z
UPDATE AD_Column SET IsAlwaysUpdateable='Y',Updated=TO_TIMESTAMP('2025-08-27 16:45:17.391000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=53339
;

-- Field: Stücklistenkonfiguration Version(53006,EE01) -> Stücklistenartikel(53028,EE01) -> Gültig bis
-- Column: PP_Product_BOM.ValidTo
-- 2025-08-27T16:45:34.360Z
UPDATE AD_Field SET IsAlwaysUpdateable='Y',Updated=TO_TIMESTAMP('2025-08-27 16:45:34.360000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=53473
;

-- Column: PP_Product_BOM.IsActive
-- 2025-08-27T16:46:39.218Z
UPDATE AD_Column SET FilterDefaultValue='',Updated=TO_TIMESTAMP('2025-08-27 16:46:39.218000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=53330
;

