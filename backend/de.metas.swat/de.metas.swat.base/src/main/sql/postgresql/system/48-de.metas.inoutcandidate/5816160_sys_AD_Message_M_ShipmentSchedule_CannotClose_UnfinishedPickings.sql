-- AD_Message for the shipment-schedule Close guard: the GENERIC rejection raised
-- when TWO OR MORE selected schedules still have an unfinished (Drafted) picking
-- job. Unlike M_ShipmentSchedule_CannotClose_UnfinishedPicking (singular, which
-- names the one offending order via {0}), this message does NOT enumerate the
-- offending schedules -- optimizing the user-Close over a huge selection.

-- 1. the message (base text = German)
INSERT INTO AD_Message
(AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Updated, UpdatedBy, Value)
VALUES
(0, 545788 /*From ID Server*/, 0, TO_TIMESTAMP('2026-07-24 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'de.metas.inoutcandidate', 'Y',
 'Die Lieferdispositionen können nicht geschlossen werden: es bestehen noch nicht abgeschlossene Kommissionieraufträge.',
 'E', TO_TIMESTAMP('2026-07-24 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'M_ShipmentSchedule_CannotClose_UnfinishedPickings')
;

-- 2. short ErrorCode
UPDATE AD_Message
SET ErrorCode = 'ShipmentSchedule_UnfinishedPickings',
    Updated   = TO_TIMESTAMP('2026-07-24 00:00:01', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Message_ID = 545788 /*From ID Server*/
;

-- 3. seed AD_Message_Trl for ALL active system languages with the base (DE) text, IsTranslated='N'
INSERT INTO AD_Message_Trl
(AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText, t.MsgTip, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Message_ID = 545788 /*From ID Server*/
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = t.AD_Message_ID)
;

-- 4. en_US override (the real English text) + IsTranslated='Y'
UPDATE AD_Message_Trl
SET MsgText      = 'Cannot close the shipment schedules: unfinished picking jobs still exist.',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-07-24 00:00:02', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Language = 'en_US' AND AD_Message_ID = 545788 /*From ID Server*/
;

-- 5. flip de_DE + de_CH to IsTranslated='Y' (their text already equals the DE base)
UPDATE AD_Message_Trl
SET IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-07-24 00:00:03', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Language = 'de_DE' AND AD_Message_ID = 545788 /*From ID Server*/
;

UPDATE AD_Message_Trl
SET IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-07-24 00:00:04', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Language = 'de_CH' AND AD_Message_ID = 545788 /*From ID Server*/
;
