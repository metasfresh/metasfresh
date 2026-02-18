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
-- 2026-02-18T17:45:08.595Z
UPDATE AD_UI_Element SET IsAdvancedField='Y', IsDisplayed='Y',Updated=TO_TIMESTAMP('2026-02-18 17:45:08.595000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=626087
;

