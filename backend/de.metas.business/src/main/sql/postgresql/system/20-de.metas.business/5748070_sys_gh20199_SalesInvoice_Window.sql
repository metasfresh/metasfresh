-- Run mode: SWING_CLIENT

-- UI Element: Rechnung(167,D) -> Rechnung(263,D) -> main -> 10 -> rest.Beleg soll per EDI übermittelt werden
-- Column: C_Invoice.IsEdiEnabled
-- 2025-02-27T12:25:56.022Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540029, SeqNo=50,Updated=TO_TIMESTAMP('2025-02-27 12:25:56.022000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=626188
;

-- UI Element: Rechnung(167,D) -> Rechnung(263,D) -> main -> 10 -> rest.Beleg soll per EDI übermittelt werden
-- Column: C_Invoice.IsEdiEnabled
-- 2025-02-27T12:26:10.824Z
UPDATE AD_UI_Element SET IsAdvancedField='N',Updated=TO_TIMESTAMP('2025-02-27 12:26:10.824000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=626188
;

-- UI Element: Rechnung(167,D) -> Rechnung(263,D) -> advanced edit -> 10 -> advanced edit.EDI Status
-- Column: C_Invoice.EDI_ExportStatus
-- 2025-02-27T12:26:24.735Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2025-02-27 12:26:24.734000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=547015
;

