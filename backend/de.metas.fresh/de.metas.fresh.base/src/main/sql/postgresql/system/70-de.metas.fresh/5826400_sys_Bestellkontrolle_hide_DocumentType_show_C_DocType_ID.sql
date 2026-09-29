-- Run mode: SWING_CLIENT

-- Window Bestellkontrolle (540274) -> tab Bestellkontrolle (540703), section-backed.
-- DocumentType ("Belegart", ref-list) and C_DocType_ID ("Belegart", the real document type) were both shown
-- with the same caption. DocumentType is hidden everywhere -- single view, grid and filter -- and C_DocType_ID
-- takes over its grid slot (SeqNoGrid 10) and its filter slot (SelectionColumnSeqNo 20).
-- The DocumentType column itself stays: code and reports still read it.

-- UI Element: Bestellkontrolle(540274,D) -> Bestellkontrolle(540703,D) -> main -> 10 -> default.Belegart (DocumentType)
UPDATE AD_UI_Element SET IsDisplayed='N', IsDisplayedGrid='N', SeqNoGrid=0, Updated=TO_TIMESTAMP('2026-09-25 10:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_UI_Element_ID=545492
;

-- Field: Bestellkontrolle(540274,D) -> Bestellkontrolle(540703,D) -> Belegart (DocumentType)
-- Legacy Swing layer, mirrored for parity.
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N', SeqNo=0, SeqNoGrid=0, Updated=TO_TIMESTAMP('2026-09-25 10:00:01.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Field_ID=556321
;

-- Column: C_Order_MFGWarehouse_Report.DocumentType -- no longer offered as a filter
UPDATE AD_Column SET IsSelectionColumn='N', SelectionColumnSeqNo=0, Updated=TO_TIMESTAMP('2026-09-25 10:00:02.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Column_ID=552750
;

-- UI Element: Bestellkontrolle(540274,D) -> Bestellkontrolle(540703,D) -> main -> 10 -> default.Belegart (C_DocType_ID)
-- Takes DocumentType's grid slot: first grid column.
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10, Updated=TO_TIMESTAMP('2026-09-25 10:00:03.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_UI_Element_ID=654787
;

-- Field: Bestellkontrolle(540274,D) -> Bestellkontrolle(540703,D) -> Belegart (C_DocType_ID)
-- Legacy Swing layer, mirrored for parity (slot 10 is free on this layer too, DocumentType is 0 now).
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=10, Updated=TO_TIMESTAMP('2026-09-25 10:00:04.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Field_ID=785045
;

-- Column: C_Order_MFGWarehouse_Report.C_DocType_ID -- offered as a filter in DocumentType's former position
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=20, Updated=TO_TIMESTAMP('2026-09-25 10:00:05.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Column_ID=593637
;
