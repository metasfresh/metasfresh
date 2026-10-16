-- Index for the relation type "calibration rule -> sales orders" (5828270) and for the delete guard of
-- the calibration rule: both look up C_OrderLine rows by the calibration rule FK. Partial, because only
-- calibrated lines carry the rule (the column is NULL on all others).
CREATE INDEX IF NOT EXISTS c_orderline_c_compensationgroup_calibrationrule_id
    ON C_OrderLine (C_CompensationGroup_CalibrationRule_ID)
    WHERE C_CompensationGroup_CalibrationRule_ID IS NOT NULL
;
