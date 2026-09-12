-- AD_Message 545822 (From ID Server): the per-row report Cancel logs for a planning that was still
-- allocated to a delivery instruction when the cancel ran - its PlannedLoadedQuantity /
-- PlannedDischargeQuantity are committed cargo, so cancel leaves them untouched (voiding the instruction
-- and setting IsClosed/Processed/OrderStatus regardless), and names the planning here instead.
--
-- Worded number-neutrally, and short, like 5820800's MSG_M_Delivery_Planning_Closed - {0} carries one
-- planning id at some call sites and a comma-separated list at others.

INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545822 /*From ID Server*/,0,TO_TIMESTAMP('2026-09-02 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',
  'Lieferplanung war zugeordnet, Mengen unverändert belassen: {0}.','E',TO_TIMESTAMP('2026-09-02 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,
  'de.metas.deliveryplanning.DeliveryPlanningService.CancelAllocated')
;

UPDATE AD_Message SET ErrorCode='DP_CANCEL_ALLOCATED', Updated=TO_TIMESTAMP('2026-09-02 10:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545822;

-- seed AD_Message_Trl for every active system or base language, copying the German base text
INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText, t.MsgTip, 'N', t.AD_Client_ID, t.AD_Org_ID, 'Y', t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545822
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl SET MsgText='Delivery planning was allocated, figures left unchanged: {0}.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-02 10:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545822;

UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-02 10:00:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language IN ('de_DE', 'de_CH') AND AD_Message_ID=545822;

-- fr_CH per the convention stated once in
-- 5820520_sys_M_Delivery_Planning_GenerateDeliveryInstruction_IsComplete.sql: the en_US text,
-- IsTranslated='N'. Runs after the en_US override above, so it copies the English text -- without this the
-- seeded row keeps the German base text, which is unusable rather than merely untranslated for an fr_CH user.
UPDATE AD_Message_Trl trl
   SET MsgText      = en.MsgText,
       IsTranslated = 'N',
       Updated      = TO_TIMESTAMP('2026-09-02 10:00:04', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy    = 100
  FROM AD_Message_Trl en
 WHERE en.AD_Message_ID = trl.AD_Message_ID
   AND en.AD_Language = 'en_US'
   AND trl.AD_Language = 'fr_CH'
   AND trl.AD_Message_ID = 545822
;
