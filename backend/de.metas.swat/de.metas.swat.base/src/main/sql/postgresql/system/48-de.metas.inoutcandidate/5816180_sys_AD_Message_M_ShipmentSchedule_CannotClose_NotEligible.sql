-- AD_Message for the shipment-schedule Close guard: raised when the user runs the
-- Close-shipment-schedules action on a selection where NO schedule is eligible to
-- close -- there is no unfinished (Drafted) picking job, but every selected schedule
-- is either already processed or still has a picked-but-unshipped qty (QtyPickList > 0).
-- Replaces the misleading generic "@NoSelection@" for this all-ineligible case.

-- 1. the message (base text = German)
INSERT INTO AD_Message
(AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Updated, UpdatedBy, Value)
VALUES
(0, 545789 /*From ID Server*/, 0, TO_TIMESTAMP('2026-07-24 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'de.metas.inoutcandidate', 'Y',
 'Die ausgewählten Lieferdispositionen können nicht geschlossen werden: sie sind bereits verarbeitet oder haben noch eine kommissionierte, noch nicht versandte Menge.',
 'E', TO_TIMESTAMP('2026-07-24 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'M_ShipmentSchedule_CannotClose_NotEligible')
;

-- 2. short ErrorCode
UPDATE AD_Message
SET ErrorCode = 'ShipmentSchedule_NotEligibleToClose',
    Updated   = TO_TIMESTAMP('2026-07-24 00:00:01', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Message_ID = 545789 /*From ID Server*/
;

-- 3. seed AD_Message_Trl for ALL active system languages with the base (DE) text, IsTranslated='N'
INSERT INTO AD_Message_Trl
(AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText, t.MsgTip, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Message_ID = 545789 /*From ID Server*/
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = t.AD_Message_ID)
;

-- 4. en_US override (the real English text) + IsTranslated='Y'
UPDATE AD_Message_Trl
SET MsgText      = 'The selected shipment schedule(s) cannot be closed: they are already processed, or still have a picked quantity awaiting shipment.',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-07-24 00:00:02', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Language = 'en_US' AND AD_Message_ID = 545789 /*From ID Server*/
;

-- 5. flip de_DE + de_CH to IsTranslated='Y' (their text already equals the DE base)
UPDATE AD_Message_Trl
SET IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-07-24 00:00:03', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Language = 'de_DE' AND AD_Message_ID = 545789 /*From ID Server*/
;

UPDATE AD_Message_Trl
SET IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-07-24 00:00:04', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Language = 'de_CH' AND AD_Message_ID = 545789 /*From ID Server*/
;
