-- Run mode: SWING_CLIENT

-- UI Element: Eingangsrechnung(183,D) -> Eingangsrechnung(290,D) -> advanced edit -> 10 -> advanced edit.Summe Gesamt
-- Column: C_Invoice.GrandTotal
-- 2026-02-16T14:47:55.507Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2026-02-16 14:47:55.507000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=542655
;

-- UI Element: Eingangsrechnung(183,D) -> Eingangsrechnung(290,D) -> main -> 20 -> rest.Status
-- Column: C_Invoice.DocStatus
-- 2026-02-16T14:47:56.545Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2026-02-16 14:47:56.545000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=542729
;

-- UI Element: Eingangsrechnung(183,D) -> Eingangsrechnung(290,D) -> main -> 20 -> posted.Verbucht
-- Column: C_Invoice.Posted
-- 2026-02-16T14:47:57.758Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2026-02-16 14:47:57.758000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=542660
;

-- UI Element: Eingangsrechnung(183,D) -> Eingangsrechnung(290,D) -> advanced edit -> 10 -> advanced edit.Sales invoice count
-- Column: C_Invoice.Sales_Invoice_Count
-- 2026-02-16T14:47:59.661Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2026-02-16 14:47:59.661000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=594686
;

-- UI Element: Eingangsrechnung(183,D) -> Eingangsrechnung(290,D) -> main -> 20 -> org.Sektion
-- Column: C_Invoice.AD_Org_ID
-- 2026-02-16T14:48:00.027Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2026-02-16 14:48:00.027000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=542725
;

-- UI Element: Eingangsrechnung(183,D) -> Eingangsrechnung(290,D) -> main -> 20 -> rest.Summe Gesamt
-- Column: C_Invoice.GrandTotal
-- 2026-02-16T14:51:51.819Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3337,0,290,540228,647949,'F',TO_TIMESTAMP('2026-02-16 14:51:51.344000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Summe über Alles zu diesem Beleg','Die Summe Gesamt zeigt die Summe über Alles inklusive Steuern und Fracht in Belegwährung an.','Y','N','N','N','N','N','N',0,'Summe Gesamt',40,0,0,TO_TIMESTAMP('2026-02-16 14:51:51.344000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Eingangsrechnung(183,D) -> Eingangsrechnung(290,D) -> advanced edit -> 10 -> advanced edit.Summe Gesamt
-- Column: C_Invoice.GrandTotal
-- 2026-02-16T14:52:33.205Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2026-02-16 14:52:33.204000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=542655
;

-- UI Element: Eingangsrechnung(183,D) -> Eingangsrechnung(290,D) -> main -> 20 -> rest.Summe Gesamt
-- Column: C_Invoice.GrandTotal
-- 2026-02-16T14:52:33.545Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2026-02-16 14:52:33.544000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647949
;

