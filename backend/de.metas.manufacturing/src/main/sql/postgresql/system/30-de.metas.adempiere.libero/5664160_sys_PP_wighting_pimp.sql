-- Column: PP_Weighting_Spec.WeightChecksRequired
-- 2022-11-14T08:50:23.607Z
UPDATE AD_Column SET ValueMin='',Updated=TO_TIMESTAMP('2022-11-14 10:50:23.605','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=584928
;

-- Field: Wägelauf -> Gewichtskontrolle -> Maßeinheit
-- Column: PP_Order_Weighting_RunCheck.C_UOM_ID
-- 2022-11-14T08:54:26.882Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-11-14 10:54:26.882','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=708025
;

-- Field: Wägelauf -> Gewichtskontrolle -> Außerhalb der Wägetoleranz
-- Column: PP_Order_Weighting_RunCheck.IsToleranceExceeded
-- 2022-11-14T10:00:51.467Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-11-14 12:00:51.467','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=708027
;

-- Field: Wägelauf -> Gewichtskontrolle -> Wägelauf
-- Column: PP_Order_Weighting_RunCheck.PP_Order_Weighting_Run_ID
-- 2022-11-14T10:01:01.798Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-11-14 12:01:01.798','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=708022
;

-- Field: Wägelauf -> Gewichtskontrolle -> Gewichtskontrolle
-- Column: PP_Order_Weighting_RunCheck.PP_Order_Weighting_RunCheck_ID
-- 2022-11-14T10:01:04.720Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-11-14 12:01:04.719','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=708021
;

-- Field: Wägelauf -> Gewichtskontrolle -> Sektion
-- Column: PP_Order_Weighting_RunCheck.AD_Org_ID
-- 2022-11-14T10:01:11.416Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-11-14 12:01:11.416','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=708019
;

