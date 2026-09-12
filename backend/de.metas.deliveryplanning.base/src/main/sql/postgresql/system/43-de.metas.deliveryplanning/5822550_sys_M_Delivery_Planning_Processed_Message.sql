-- AD_Message 545828 (From ID Server): the rejection every RECEIVE action started from the
-- receipt-disposition delivery-planning window shares - refused because a selected row's delivery planning is already
-- Processed.
--
-- Deliberately NOT AD_Message 545812 ("Lieferplanung geschlossen"): Processed is true for a CLOSED
-- planning AND for one that already carries its single receipt or shipment, so saying "closed" would
-- be a false statement about half the rows it names. Both wordings therefore mention both states.
--
-- {0} carries a comma-separated list of the offending M_Delivery_Planning_IDs - the refusal is
-- all-or-nothing over the selection and names every row, so the planner can deselect exactly those.
-- Worded number-neutrally for that reason, and short, because it renders in the disabled-reason
-- tooltip as well as in the raised error.

INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545828 /*From ID Server*/,0,TO_TIMESTAMP('2026-09-04 08:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',
  'Lieferplanung bereits verarbeitet (geschlossen oder geliefert): {0}.','E',TO_TIMESTAMP('2026-09-04 08:00:00','YYYY-MM-DD HH24:MI:SS'),100,
  'de.metas.deliveryplanning.DeliveryPlanningService.Processed')
;

UPDATE AD_Message SET ErrorCode='DP_PLANNING_PROCESSED', Updated=TO_TIMESTAMP('2026-09-04 08:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545828;

-- seed AD_Message_Trl for every active system or base language, copying the German base text
INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText, t.MsgTip, 'N', t.AD_Client_ID, t.AD_Org_ID, 'Y', t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545828
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl SET MsgText='Delivery planning already processed (closed or delivered): {0}.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-04 08:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545828;

UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-04 08:00:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language IN ('de_DE', 'de_CH') AND AD_Message_ID=545828;

-- fr_CH per the convention stated once in
-- 5820520_sys_M_Delivery_Planning_GenerateDeliveryInstruction_IsComplete.sql: the en_US text,
-- IsTranslated='N'. Runs after the en_US override above, so it copies the English text -- without this the
-- seeded row keeps the German base text, which is unusable rather than merely untranslated for an fr_CH user.
UPDATE AD_Message_Trl trl
   SET MsgText      = en.MsgText,
       IsTranslated = 'N',
       Updated      = TO_TIMESTAMP('2026-09-04 08:00:04', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy    = 100
  FROM AD_Message_Trl en
 WHERE en.AD_Message_ID = trl.AD_Message_ID
   AND en.AD_Language = 'en_US'
   AND trl.AD_Language = 'fr_CH'
   AND trl.AD_Message_ID = 545828
;
