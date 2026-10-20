-- Quantity calibration: the calibration factor is now stored as a fraction (0.8 = 80 %, 1 = unchanged) instead of a 100-based percent (80 = 80 %).
-- Convert the existing values on the calibration rules and on the order lines (all document statuses):
-- a value above 1 is a 100-based percent and is divided by 100, rounded to v_scale decimals; values up to 1 are taken as already converted and stay as they are.
-- Empty factors (order lines that were never calibrated) stay empty.
-- Re-run safe: both tables are backed up with the suffix '_cff' first; if that backup is already registered, the conversion already ran
-- and is skipped (a second run would divide again, e.g. 1.2 -> 0.01).

DO
$$
    DECLARE
        v_scale CONSTANT integer := 2; -- decimals of the converted fraction
    BEGIN
        IF EXISTS (SELECT 1
                   FROM backup.backup_tables
                   WHERE backup_table_name LIKE 'backup.c_compensationgroup_calibrationrule_bkp_%_cff')
        THEN
            RAISE NOTICE 'Calibration factors were already converted to fractions (backup _cff exists) - skipping';
            RETURN;
        END IF;

        PERFORM backup_table('c_compensationgroup_calibrationrule', '_cff');
        PERFORM backup_table('c_orderline', '_cff');

        UPDATE C_CompensationGroup_CalibrationRule
        SET GroupCompensationCalibrationFactor = ROUND(GroupCompensationCalibrationFactor / 100, v_scale),
            Updated                            = TO_TIMESTAMP('2026-10-20 22:00:00', 'YYYY-MM-DD HH24:MI:SS'),
            UpdatedBy                          = 99
        WHERE GroupCompensationCalibrationFactor > 1;

        UPDATE C_OrderLine
        SET GroupCompensationCalibrationFactor = ROUND(GroupCompensationCalibrationFactor / 100, v_scale),
            Updated                            = TO_TIMESTAMP('2026-10-20 22:00:01', 'YYYY-MM-DD HH24:MI:SS'),
            UpdatedBy                          = 99
        WHERE GroupCompensationCalibrationFactor > 1;
    END
$$
;
