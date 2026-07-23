-- Run mode: SWING_CLIENT

-- UI Element: Geschäftspartner_OLD(123,D) -> Geschäftspartner(220,D) -> advanced edit -> 10 -> advanced edit.Vertragsnummer
-- Column: C_BPartner.FactoringContractNo
-- 2026-07-23T14:40:23.582Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2026-07-23 14:40:23.581000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=652700
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Geschäftspartner(220,D) -> advanced edit -> 10 -> advanced edit.Kundenkontonummer
-- Column: C_BPartner.FactoringClientAccountId
-- 2026-07-23T14:41:03.884Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2026-07-23 14:41:03.883000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=652701
;

