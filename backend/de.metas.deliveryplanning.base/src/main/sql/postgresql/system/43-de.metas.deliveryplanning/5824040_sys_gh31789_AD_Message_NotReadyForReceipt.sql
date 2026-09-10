-- The rejection reason for the receipt-readiness gate added alongside this.
--
-- Both windows now refuse a receive on a planning that is not yet on a COMPLETED delivery instruction:
--   * the receipt-disposition window through DeliveryPlanningService.getReceiveRejectionReason, which also
--     backs the runtime backstop assertNoneProcessed, so the check covers the precondition AND a process
--     invoked past it;
--   * the delivery-planning window through DeliveryPlanningGenerateProcessesHelper.checkEligibleToCreateReceipt.
--
-- Without this record the UI would show the raw key. Shape and MsgType copied from the sibling
-- .Processed message (AD_Message 545828): type 'E', EntityType 'D', one {0} placeholder taking the id list the
-- service passes.
--
-- Wording deliberately names the CAUSE, not just the state: the planning window has no
-- IsReadyForReceipt column on screen, so "not ready" alone would leave the user nothing to reconcile it
-- against, whereas the receipt-disposition window does show the flag.

INSERT INTO AD_Message (AD_Message_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,Value,MsgText,MsgType,EntityType)
SELECT 545834 /*From ID Server*/,0,0,'Y',TO_TIMESTAMP('2026-09-10 23:15:00','YYYY-MM-DD HH24:MI:SS'),100,
       TO_TIMESTAMP('2026-09-10 23:15:00','YYYY-MM-DD HH24:MI:SS'),100,
       'de.metas.deliveryplanning.DeliveryPlanningService.NotReadyForReceipt',
       'Wareneingang noch nicht möglich: die Lieferplanung ist keiner abgeschlossenen Auslieferungsanweisung zugeordnet: {0}.',
       'E','D'
WHERE NOT EXISTS (SELECT 1 FROM AD_Message WHERE AD_Message_ID=545834)
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
  FROM AD_Language l, AD_Message t
 WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545834
   AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl
   SET MsgText = 'Material receipt not possible yet: the delivery planning is not allocated to a completed delivery instruction: {0}.',
       IsTranslated = 'Y',
       Updated = TO_TIMESTAMP('2026-09-10 23:15:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
 WHERE AD_Message_ID = 545834 AND AD_Language IN ('en_US','fr_CH')
;
