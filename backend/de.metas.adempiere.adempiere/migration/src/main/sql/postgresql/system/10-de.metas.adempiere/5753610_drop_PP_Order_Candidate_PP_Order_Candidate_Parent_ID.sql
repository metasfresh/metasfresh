-- Run mode: SWING_CLIENT

-- UI Element: Produktionsdisposition(541316,EE01) -> Produktionsdisposition(544794,EE01) -> main -> 20 -> bp.PP_Order_Candidate_Parent_ID
-- Column: PP_Order_Candidate.PP_Order_Candidate_Parent_ID
-- 2025-05-06T06:27:15.478Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=625809
;

-- 2025-05-06T06:27:15.488Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730941
;

-- Field: Produktionsdisposition(541316,EE01) -> Produktionsdisposition(544794,EE01) -> Eltern-Kandidat
-- Column: PP_Order_Candidate.PP_Order_Candidate_Parent_ID
-- 2025-05-06T06:27:15.495Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=730941
;

-- 2025-05-06T06:27:15.499Z
DELETE FROM AD_Field WHERE AD_Field_ID=730941
;

-- 2025-05-06T06:27:15.527Z
/* DDL */ SELECT public.db_alter_table('PP_Order_Candidate','ALTER TABLE PP_Order_Candidate DROP COLUMN IF EXISTS PP_Order_Candidate_Parent_ID')
;

-- Column: PP_Order_Candidate.PP_Order_Candidate_Parent_ID
-- 2025-05-06T06:27:15.805Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=589156
;

-- 2025-05-06T06:27:15.810Z
DELETE FROM AD_Column WHERE AD_Column_ID=589156
;

