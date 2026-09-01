-- Delivery Planning: refuse to COMPLETE a delivery instruction while any of its currently allocated
-- plannings is closed. Its own message, not the pre-write ClosedPlannings rejection (545797): that
-- one guards putting an already-closed planning ON an instruction, this one a planning closed AFTER
-- it was allocated -- a different moment and a different sentence.
-- No AD_Process / AD_Table_Process: this is a @DocValidate(TIMING_BEFORE_COMPLETE) guard on
-- M_ShipperTransportation.completeIt(), not a WebUI action with its own menu entry.
--
-- IDs allocated from idserver.metas.de on 2026-08-27:
--   AD_Message   545810 (ClosedAllocatedPlannings)
--
-- Any stack that applied this script BEFORE the _Trl seed covered a base language that is not
-- flagged as a system language needs this once -- the runner will not re-run an applied file.
-- A no-op wherever the base language is also flagged a system language, which is the usual setup:
--   INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
--   SELECT l.AD_Language, t.AD_Message_ID, t.MsgText, t.MsgTip, 'N', t.AD_Client_ID, t.AD_Org_ID, 'Y', t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
--     FROM AD_Language l, AD_Message t
--    WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545810
--      AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID);

INSERT INTO AD_Message (AD_Message_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Value, MsgText, MsgType, EntityType)
VALUES (545810 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-08-27 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-08-27 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'de.metas.deliveryplanning.CompleteDeliveryInstruction.ClosedAllocatedPlannings',
        'Die Lieferanweisung kann nicht abgeschlossen werden, solange geschlossene Lieferplanungen zugeordnet sind: {0}.', 'E', 'D')
;

UPDATE AD_Message SET ErrorCode='DP_COMPLETE_CLOSED_PLANNING', Updated=TO_TIMESTAMP('2026-08-27 12:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545810;

-- seed AD_Message_Trl for every active system or base language, copying the German base text
INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText, t.MsgTip, 'N', t.AD_Client_ID, t.AD_Org_ID, 'Y', t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545810
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl SET MsgText='The delivery instruction cannot be completed while closed delivery plannings are allocated to it: {0}.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-27 12:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545810;

UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-27 12:00:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language IN ('de_DE', 'de_CH') AND AD_Message_ID=545810;
