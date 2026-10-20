-- C_CompensationGroup_CalibrationRule.GroupCompensationCalibrationFactor is now stored as a fraction (0.8 = 80 %):
-- a new calibration rule starts with factor 1 (= quantities unchanged) instead of 100.
-- The application-dictionary default drives the WebUI (new record in window "Kalibrierungsregeln");
-- the DB column default makes inserts that do not set the factor (REST, import, plain SQL) behave the same.
-- Only the default is changed, existing rows are not touched.

UPDATE AD_Column SET DefaultValue='1', Updated=TO_TIMESTAMP('2026-10-20 22:01:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Column_ID=593728
;

INSERT INTO t_alter_column values('c_compensationgroup_calibrationrule','GroupCompensationCalibrationFactor',null,null,'1')
;
