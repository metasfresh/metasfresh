-- AD_Messages for editing the Kalibrierfaktor (C_OrderLine.GroupCompensationCalibrationFactor) on an order line, see callout de.metas.order.compensationGroup.callout.C_OrderLine
-- IDs allocated from the ID server on 2026-10-20: AD_Message 545962, 545963, 545964

-- C_OrderLine_CalibrationFactorMandatory
INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Updated, UpdatedBy, Value)
VALUES (0, 545962 /*From ID Server*/, 0, TO_TIMESTAMP('2026-10-20 18:00:01','YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'Der Kalibrierfaktor darf nicht leer sein.', 'E', TO_TIMESTAMP('2026-10-20 18:00:02','YYYY-MM-DD HH24:MI:SS'), 100, 'C_OrderLine_CalibrationFactorMandatory');

UPDATE AD_Message SET ErrorCode = 'CALIBRATION_FACTOR_MANDATORY', Updated = TO_TIMESTAMP('2026-10-20 18:00:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Message_ID = 545962;

INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText, t.MsgTip, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Message_ID = 545962
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = t.AD_Message_ID);

UPDATE AD_Message_Trl SET MsgText = 'The calibration factor must not be empty.', IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-20 18:00:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Language = 'en_US' AND AD_Message_ID = 545962;
UPDATE AD_Message_Trl SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-20 18:00:05','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Language = 'de_DE' AND AD_Message_ID = 545962;
UPDATE AD_Message_Trl SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-20 18:00:06','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Language = 'de_CH' AND AD_Message_ID = 545962;

-- C_OrderLine_CalibrationFactorNotCalibrated
INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Updated, UpdatedBy, Value)
VALUES (0, 545963 /*From ID Server*/, 0, TO_TIMESTAMP('2026-10-20 18:00:07','YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'Diese Auftragsposition ist nicht kalibriert (keine Kalibrierungsregel, keine unkalibrierte Menge). Der Kalibrierfaktor kann hier nicht geändert werden; bitte die Menge direkt ändern.', 'E', TO_TIMESTAMP('2026-10-20 18:00:08','YYYY-MM-DD HH24:MI:SS'), 100, 'C_OrderLine_CalibrationFactorNotCalibrated');

UPDATE AD_Message SET ErrorCode = 'CALIBRATION_FACTOR_LINE_NOT_CALIBRATED', Updated = TO_TIMESTAMP('2026-10-20 18:00:09','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Message_ID = 545963;

INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText, t.MsgTip, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Message_ID = 545963
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = t.AD_Message_ID);

UPDATE AD_Message_Trl SET MsgText = 'This order line is not calibrated (no calibration rule, no uncalibrated quantity). The calibration factor cannot be changed here; please change the quantity directly.', IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-20 18:00:10','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Language = 'en_US' AND AD_Message_ID = 545963;
UPDATE AD_Message_Trl SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-20 18:00:11','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Language = 'de_DE' AND AD_Message_ID = 545963;
UPDATE AD_Message_Trl SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-20 18:00:12','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Language = 'de_CH' AND AD_Message_ID = 545963;

-- C_OrderLine_CalibrationFactorUOMChanged
INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Updated, UpdatedBy, Value)
VALUES (0, 545964 /*From ID Server*/, 0, TO_TIMESTAMP('2026-10-20 18:00:13','YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'Die Maßeinheit der Auftragsposition weicht von der Maßeinheit der Vorlagenzeile ab. Der Kalibrierfaktor kann nicht geändert werden; bitte die Menge direkt ändern.', 'E', TO_TIMESTAMP('2026-10-20 18:00:14','YYYY-MM-DD HH24:MI:SS'), 100, 'C_OrderLine_CalibrationFactorUOMChanged');

UPDATE AD_Message SET ErrorCode = 'CALIBRATION_FACTOR_UOM_CHANGED', Updated = TO_TIMESTAMP('2026-10-20 18:00:15','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Message_ID = 545964;

INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText, t.MsgTip, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Message_ID = 545964
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = t.AD_Message_ID);

UPDATE AD_Message_Trl SET MsgText = 'The order line''s unit of measure differs from the unit of its template line. The calibration factor cannot be changed; please change the quantity directly.', IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-20 18:00:16','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Language = 'en_US' AND AD_Message_ID = 545964;
UPDATE AD_Message_Trl SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-20 18:00:17','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Language = 'de_DE' AND AD_Message_ID = 545964;
UPDATE AD_Message_Trl SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-20 18:00:18','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Language = 'de_CH' AND AD_Message_ID = 545964;
