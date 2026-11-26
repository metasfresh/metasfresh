-- Quantity calibration: user-facing messages of the rule table C_CompensationGroup_CalibrationRule.
-- AD_Message IDs allocated from idserver.metas.de: 545907..545910.

-- C_CompensationGroup_CalibrationRule_BPartnerOrGroupRequired
INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Updated, UpdatedBy, Value)
VALUES (0, 545907 /*From ID Server*/, 0, TO_TIMESTAMP('2026-10-06 11:00:00','YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'Kunde oder Geschäftspartnergruppe muss gesetzt sein.', 'E', TO_TIMESTAMP('2026-10-06 11:00:00','YYYY-MM-DD HH24:MI:SS'), 100, 'C_CompensationGroup_CalibrationRule_BPartnerOrGroupRequired');

UPDATE AD_Message SET ErrorCode = 'CALIBRATION_BPARTNER_OR_GROUP_REQUIRED', Updated = TO_TIMESTAMP('2026-10-06 11:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Message_ID = 545907;

INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText, t.MsgTip, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Message_ID = 545907
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = t.AD_Message_ID);

UPDATE AD_Message_Trl SET MsgText = 'Customer or business partner group is required.', IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-06 11:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Language = 'en_US' AND AD_Message_ID = 545907;
UPDATE AD_Message_Trl SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-06 11:00:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Language = 'de_DE' AND AD_Message_ID = 545907;
UPDATE AD_Message_Trl SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-06 11:00:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Language = 'de_CH' AND AD_Message_ID = 545907;

-- C_CompensationGroup_CalibrationRule_NegativeFactor
INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Updated, UpdatedBy, Value)
VALUES (0, 545908 /*From ID Server*/, 0, TO_TIMESTAMP('2026-10-06 11:01:00','YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'Der Kalibrierfaktor darf nicht negativ sein.', 'E', TO_TIMESTAMP('2026-10-06 11:01:00','YYYY-MM-DD HH24:MI:SS'), 100, 'C_CompensationGroup_CalibrationRule_NegativeFactor');

UPDATE AD_Message SET ErrorCode = 'CALIBRATION_RULE_NEGATIVE_FACTOR', Updated = TO_TIMESTAMP('2026-10-06 11:01:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Message_ID = 545908;

INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText, t.MsgTip, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Message_ID = 545908
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = t.AD_Message_ID);

UPDATE AD_Message_Trl SET MsgText = 'The calibration factor must not be negative.', IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-06 11:01:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Language = 'en_US' AND AD_Message_ID = 545908;
UPDATE AD_Message_Trl SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-06 11:01:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Language = 'de_DE' AND AD_Message_ID = 545908;
UPDATE AD_Message_Trl SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-06 11:01:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Language = 'de_CH' AND AD_Message_ID = 545908;

-- C_CompensationGroup_CalibrationRule_UsedDeactivateInstead
INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Updated, UpdatedBy, Value)
VALUES (0, 545909 /*From ID Server*/, 0, TO_TIMESTAMP('2026-10-06 11:02:00','YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'Die Kalibrierungsregel wird in Auftragspositionen verwendet und kann nicht gelöscht werden. Bitte deaktivieren Sie sie stattdessen.', 'E', TO_TIMESTAMP('2026-10-06 11:02:00','YYYY-MM-DD HH24:MI:SS'), 100, 'C_CompensationGroup_CalibrationRule_UsedDeactivateInstead');

UPDATE AD_Message SET ErrorCode = 'CALIBRATION_RULE_USED_DEACTIVATE', Updated = TO_TIMESTAMP('2026-10-06 11:02:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Message_ID = 545909;

INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText, t.MsgTip, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Message_ID = 545909
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = t.AD_Message_ID);

UPDATE AD_Message_Trl SET MsgText = 'This calibration rule is used on order lines and cannot be deleted. Please deactivate it instead.', IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-06 11:02:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Language = 'en_US' AND AD_Message_ID = 545909;
UPDATE AD_Message_Trl SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-06 11:02:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Language = 'de_DE' AND AD_Message_ID = 545909;
UPDATE AD_Message_Trl SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-06 11:02:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Language = 'de_CH' AND AD_Message_ID = 545909;

-- C_CompensationGroup_CalibrationRule_AllComponentsLeftOut
INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Updated, UpdatedBy, Value)
VALUES (0, 545910 /*From ID Server*/, 0, TO_TIMESTAMP('2026-10-06 11:03:00','YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'Alle Komponenten des Menüs wurden durch Kalibrierungsregeln weggelassen.', 'E', TO_TIMESTAMP('2026-10-06 11:03:00','YYYY-MM-DD HH24:MI:SS'), 100, 'C_CompensationGroup_CalibrationRule_AllComponentsLeftOut');

UPDATE AD_Message SET ErrorCode = 'CALIBRATION_ALL_COMPONENTS_LEFT_OUT', Updated = TO_TIMESTAMP('2026-10-06 11:03:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Message_ID = 545910;

INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText, t.MsgTip, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Message_ID = 545910
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = t.AD_Message_ID);

UPDATE AD_Message_Trl SET MsgText = 'All components of the menu are left out by calibration rules.', IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-06 11:03:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Language = 'en_US' AND AD_Message_ID = 545910;
UPDATE AD_Message_Trl SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-06 11:03:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Language = 'de_DE' AND AD_Message_ID = 545910;
UPDATE AD_Message_Trl SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-06 11:03:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Language = 'de_CH' AND AD_Message_ID = 545910;
