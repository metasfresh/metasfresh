-- C_CompensationGroup_CalibrationRule.GroupCompensationCalibrationFactor: a new calibration rule starts with factor 100 (= 100 %, quantities unchanged) instead of 0.
-- The application-dictionary default drives the WebUI (new record in window "Kalibrierungsregeln");
-- the DB column default makes inserts that do not set the factor (REST, import, plain SQL) behave the same.
-- The column is already NOT NULL; only the default is set, existing rows are not touched.

UPDATE AD_Column SET DefaultValue='100', Updated=TO_TIMESTAMP('2026-10-08 10:30:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Column_ID=593728
;

INSERT INTO t_alter_column values('c_compensationgroup_calibrationrule','GroupCompensationCalibrationFactor',null,null,'100')
;
