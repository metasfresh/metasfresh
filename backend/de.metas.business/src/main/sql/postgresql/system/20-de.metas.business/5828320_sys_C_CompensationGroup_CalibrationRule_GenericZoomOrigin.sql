-- Calibration rules: make the rule a zoom origin, so the Related-Documents panel of a rule lists the
-- sales orders of AD_RelationType C_CompensationGroup_CalibrationRule_to_C_Order_SO.
-- A relation type whose source is a single-key record is only offered when the source key column
-- has IsGenericZoomOrigin='Y'; with 'N' the panel stays empty.
-- AD_Column 593714 = C_CompensationGroup_CalibrationRule.C_CompensationGroup_CalibrationRule_ID

UPDATE AD_Column SET IsGenericZoomOrigin='Y', Updated=TO_TIMESTAMP('2026-10-07 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Column_ID=593714
;
