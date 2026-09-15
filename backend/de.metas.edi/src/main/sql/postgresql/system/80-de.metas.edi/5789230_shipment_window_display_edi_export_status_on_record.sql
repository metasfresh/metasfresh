-- Run mode: SWING_CLIENT

-- Field: Lieferung(169,D) -> Lieferung(257,D) -> EDI Fehlermeldung
-- Column: M_InOut.EDIErrorMsg
-- 2026-02-18T17:07:08.735Z
UPDATE AD_Field SET IsActive='Y',Updated=TO_TIMESTAMP('2026-02-18 17:07:08.735000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=553215
;

-- Column: M_InOut.EDI_ExportStatus
-- 2026-02-18T17:43:58.516Z
UPDATE AD_Column SET AD_Process_ID=NULL, AD_Reference_ID=17, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2026-02-18 17:43:58.516000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=549871
;

-- UI Element: Lieferung(169,D) -> Lieferung(257,D) -> grid view -> 10 -> grid view.EDI-Sendestatus
-- Column: M_InOut.EDI_ExportStatus
-- 2026-02-19T14:06:51.529Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2026-02-19 14:06:51.529000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=626087
;

-- UI Element: Lieferung(169,D) -> Lieferung(257,D) -> main -> 10 -> delivery.DESADV
-- Column: M_InOut.EDI_Desadv_ID
-- 2026-02-19T14:06:51.551Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2026-02-19 14:06:51.551000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=547323
;

-- UI Element: Lieferung(169,D) -> Lieferung(257,D) -> grid view -> 10 -> grid view.Status
-- Column: M_InOut.DocStatus
-- 2026-02-19T14:06:51.561Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2026-02-19 14:06:51.561000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=547319
;

-- UI Element: Lieferung(169,D) -> Lieferung(257,D) -> main -> 20 -> posted.Posted
-- Column: M_InOut.Posted
-- 2026-02-19T14:06:51.573Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2026-02-19 14:06:51.573000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=564741
;

-- UI Element: Lieferung(169,D) -> Lieferung(257,D) -> grid view -> 10 -> grid view.Transport Auftrag
-- Column: M_InOut.M_ShipperTransportation_ID
-- 2026-02-19T14:06:51.585Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2026-02-19 14:06:51.585000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=564603
;

-- UI Element: Lieferung(169,D) -> Lieferung(257,D) -> main -> 20 -> dates.Tour
-- Column: M_InOut.M_Tour_ID
-- 2026-02-19T14:06:51.597Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2026-02-19 14:06:51.597000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=564599
;

-- UI Element: Lieferung(169,D) -> Lieferung(257,D) -> main -> 20 -> org.Sektion
-- Column: M_InOut.AD_Org_ID
-- 2026-02-19T14:06:51.609Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2026-02-19 14:06:51.609000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=540617
;

-- UI Element: Lieferung(169,D) -> Lieferung(257,D) -> grid view -> 10 -> grid view.EDI-Sendestatus
-- Column: M_InOut.EDI_ExportStatus
-- 2026-02-19T14:07:14.739Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=626087
;

-- UI Element: Lieferung(169,D) -> Lieferung(257,D) -> main -> 20 -> dates.EDI-Sendestatus
-- Column: M_InOut.EDI_ExportStatus
-- 2026-02-19T14:08:11.725Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,553214,0,257,1000029,648156,'F',TO_TIMESTAMP('2026-02-19 14:08:11.569000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'EDI-Sendestatus',80,0,0,TO_TIMESTAMP('2026-02-19 14:08:11.569000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Lieferung(169,D) -> Lieferung(257,D) -> main -> 20 -> dates.EDI-Sendestatus
-- Column: M_InOut.EDI_ExportStatus
-- 2026-02-19T14:08:22.895Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2026-02-19 14:08:22.895000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=648156
;

-- UI Element: Lieferung(169,D) -> Lieferung(257,D) -> grid view -> 10 -> grid view.Status
-- Column: M_InOut.DocStatus
-- 2026-02-19T14:08:22.904Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2026-02-19 14:08:22.904000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=547319
;

-- UI Element: Lieferung(169,D) -> Lieferung(257,D) -> main -> 20 -> posted.Posted
-- Column: M_InOut.Posted
-- 2026-02-19T14:08:22.912Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2026-02-19 14:08:22.912000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=564741
;

-- UI Element: Lieferung(169,D) -> Lieferung(257,D) -> grid view -> 10 -> grid view.Transport Auftrag
-- Column: M_InOut.M_ShipperTransportation_ID
-- 2026-02-19T14:08:22.919Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2026-02-19 14:08:22.919000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=564603
;

-- UI Element: Lieferung(169,D) -> Lieferung(257,D) -> main -> 20 -> dates.Tour
-- Column: M_InOut.M_Tour_ID
-- 2026-02-19T14:08:22.926Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2026-02-19 14:08:22.926000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=564599
;

-- UI Element: Lieferung(169,D) -> Lieferung(257,D) -> main -> 20 -> org.Sektion
-- Column: M_InOut.AD_Org_ID
-- 2026-02-19T14:08:22.934Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2026-02-19 14:08:22.934000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=540617
;

