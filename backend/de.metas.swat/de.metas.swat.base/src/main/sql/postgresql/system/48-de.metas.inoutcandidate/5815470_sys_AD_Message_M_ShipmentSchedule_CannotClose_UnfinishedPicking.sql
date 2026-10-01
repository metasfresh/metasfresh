-- AD_Message for the shipment-schedule Close guard: blocks the user-initiated
-- Close-shipment-schedules action when a selected schedule still has an
-- unfinished (Drafted) picking job. {0} is filled with the offending
-- schedule/order identifier(s) by AdempiereException(MSG, ...).

-- 1. the message (base text = German)
INSERT INTO AD_Message
(AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Updated, UpdatedBy, Value)
VALUES
(0, 545779 /*From ID Server*/, 0, TO_TIMESTAMP('2026-07-22 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'de.metas.inoutcandidate', 'Y',
 'Die Lieferdisposition kann nicht geschlossen werden: für {0} besteht noch ein nicht abgeschlossener Kommissionierauftrag.',
 'E', TO_TIMESTAMP('2026-07-22 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'M_ShipmentSchedule_CannotClose_UnfinishedPicking')
;

-- 2. short ErrorCode
UPDATE AD_Message
SET ErrorCode = 'ShipmentSchedule_UnfinishedPicking',
    Updated   = TO_TIMESTAMP('2026-07-22 00:00:01', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Message_ID = 545779 /*From ID Server*/
;

-- 3. seed AD_Message_Trl for ALL active system languages with the base (DE) text, IsTranslated='N'
INSERT INTO AD_Message_Trl
(AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText, t.MsgTip, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Message_ID = 545779 /*From ID Server*/
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = t.AD_Message_ID)
;

-- 4. en_US override (the real English text) + IsTranslated='Y'
UPDATE AD_Message_Trl
SET MsgText      = 'Cannot close the shipment schedule: an unfinished picking job still exists for {0}.',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-07-22 00:00:02', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Language = 'en_US' AND AD_Message_ID = 545779 /*From ID Server*/
;

-- 5. flip de_DE + de_CH to IsTranslated='Y' (their text already equals the DE base)
UPDATE AD_Message_Trl
SET IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-07-22 00:00:03', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Language = 'de_DE' AND AD_Message_ID = 545779 /*From ID Server*/
;

UPDATE AD_Message_Trl
SET IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-07-22 00:00:04', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Language = 'de_CH' AND AD_Message_ID = 545779 /*From ID Server*/
;
