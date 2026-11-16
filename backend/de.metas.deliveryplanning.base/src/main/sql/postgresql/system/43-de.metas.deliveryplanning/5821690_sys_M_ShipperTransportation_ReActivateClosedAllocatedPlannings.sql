-- Delivery Planning: refuse to RE-ACTIVATE a delivery instruction while any of its currently allocated
-- plannings is closed. The sibling of ClosedAllocatedPlannings (545810), which refuses COMPLETE on exactly
-- the same condition -- same rule, other document action, and therefore its own sentence.
-- No AD_Process / AD_Table_Process: this is a @DocValidate(TIMING_BEFORE_REACTIVATE) guard on
-- M_ShipperTransportation.reActivateIt(), not a WebUI action with its own menu entry.
--
-- IDs allocated from idserver.metas.de on 2026-09-01:
--   AD_Message   545817 (ReActivateDeliveryInstruction.ClosedAllocatedPlannings)

INSERT INTO AD_Message (AD_Message_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Value, MsgText, MsgType, EntityType)
VALUES (545817 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-09-01 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-09-01 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'de.metas.deliveryplanning.ReActivateDeliveryInstruction.ClosedAllocatedPlannings',
        'Geschlossene Lieferplanung zugeordnet, Reaktivierung nicht möglich: {0}.', 'E', 'D')
;

UPDATE AD_Message SET ErrorCode='DP_REACTIVATE_CLOSED_PLANNING', Updated=TO_TIMESTAMP('2026-09-01 09:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545817;

-- seed AD_Message_Trl for every active system or base language, copying the German base text
INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText, t.MsgTip, 'N', t.AD_Client_ID, t.AD_Org_ID, 'Y', t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545817
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl SET MsgText='Closed delivery planning allocated, cannot be re-activated: {0}.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-01 09:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545817;

UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-01 09:00:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language IN ('de_DE', 'de_CH') AND AD_Message_ID=545817;

-- fr_CH per the convention stated once in
-- 5820520_sys_M_Delivery_Planning_GenerateDeliveryInstruction_IsComplete.sql: the en_US text,
-- IsTranslated='N'. Runs after the en_US override above, so it copies the English text -- without this the
-- seeded row keeps the German base text, which is unusable rather than merely untranslated for an fr_CH user.
UPDATE AD_Message_Trl trl
   SET MsgText      = en.MsgText,
       IsTranslated = 'N',
       Updated      = TO_TIMESTAMP('2026-09-01 09:00:04', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy    = 100
  FROM AD_Message_Trl en
 WHERE en.AD_Message_ID = trl.AD_Message_ID
   AND en.AD_Language = 'en_US'
   AND trl.AD_Language = 'fr_CH'
   AND trl.AD_Message_ID = 545817
;
