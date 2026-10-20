-- Quantity calibration: the calibration factor is now stored as a fraction (0.8 = 80 %, 1 = unchanged) instead of a 100-based percent (80 = 80 %).
-- Convert the existing values on the calibration rules and on the order lines (all document statuses):
-- a value above 1 is a 100-based percent and is divided by 100, rounded to 2 decimals; values up to 1 are taken as already converted and stay as they are.
-- Empty factors (order lines that were never calibrated) stay empty.

SELECT backup_table('c_compensationgroup_calibrationrule', '_cf');

UPDATE C_CompensationGroup_CalibrationRule
SET GroupCompensationCalibrationFactor = ROUND(GroupCompensationCalibrationFactor / 100, 2),
    Updated = TO_TIMESTAMP('2026-10-20 22:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 99
WHERE GroupCompensationCalibrationFactor > 1
;

SELECT backup_table('c_orderline', '_CalibrationFactorToFraction');

UPDATE C_OrderLine
SET GroupCompensationCalibrationFactor = ROUND(GroupCompensationCalibrationFactor / 100, 2),
    Updated = TO_TIMESTAMP('2026-10-20 22:00:01', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 99
WHERE GroupCompensationCalibrationFactor > 1
;
