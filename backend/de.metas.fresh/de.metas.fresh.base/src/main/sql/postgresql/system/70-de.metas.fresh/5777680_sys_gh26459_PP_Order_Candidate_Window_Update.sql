-- Run mode: SWING_CLIENT

-- Field: Produktionsdisposition_OLD(541316,EE01) -> Produktionsdisposition(544794,EE01) -> Geplante Menge
-- Column: PP_Order_Candidate.QtyProcessed
-- 2025-11-19T14:43:16.756Z
UPDATE AD_Field SET AD_Name_ID=1568, Description='', Help='', Name='Geplante Menge',Updated=TO_TIMESTAMP('2025-11-19 14:43:16.756000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=669111
;

-- 2025-11-19T14:43:17.228Z
UPDATE AD_Field_Trl trl SET Description='',Help='',Name='Geplante Menge' WHERE AD_Field_ID=669111 AND AD_Language='de_DE'
;

-- 2025-11-19T14:43:17.300Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1568)
;

-- 2025-11-19T14:43:17.372Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=669111
;

-- 2025-11-19T14:43:17.440Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(669111)
;

