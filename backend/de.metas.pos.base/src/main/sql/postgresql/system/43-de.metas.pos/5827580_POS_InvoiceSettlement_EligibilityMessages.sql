-- AD_Message IDs allocated from idserver.metas.de:
--   AD_Message 545892 (de.metas.pos.InvoiceSettlement.NotEligibleForSettlement)
--   AD_Message 545893 (de.metas.pos.InvoiceSettlement.AllocationIncomplete)
--
-- Both guard the settle-by-id path (POSInvoiceSettlementService.settleInCashInTrx), whose client-supplied
-- invoiceId is NOT scoped by the org-scoped findOpenInvoices search:
--   NotEligibleForSettlement — the re-read invoice is not eligible for cash settlement (not a completed/closed,
--     still-open, non-credit-memo SALES invoice), so no valid allocation could ever be produced for it.
--   AllocationIncomplete — after auto-allocation the invoice is still not fully settled (e.g. a non-financial
--     invoice auto-allocation silently no-ops on), so booking the cash would leave money unmatched; throwing
--     rolls the whole transaction back.

-- ############################################################
-- Message 1: de.metas.pos.InvoiceSettlement.NotEligibleForSettlement
-- ############################################################
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545892 /*From ID Server*/,0,TO_TIMESTAMP('2026-10-01 16:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Die Rechnung kann an der Kasse nicht bar beglichen werden.','E',TO_TIMESTAMP('2026-10-01 16:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos.InvoiceSettlement.NotEligibleForSettlement')
;

UPDATE AD_Message SET ErrorCode='POS_INVOICE_SETTLEMENT_NOT_ELIGIBLE', Updated=TO_TIMESTAMP('2026-10-01 16:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID=545892
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545892
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl SET MsgText='The invoice cannot be settled in cash at the till.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-10-01 16:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545892
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-10-01 16:00:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_DE' AND AD_Message_ID=545892
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-10-01 16:00:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_CH' AND AD_Message_ID=545892
;

-- ############################################################
-- Message 2: de.metas.pos.InvoiceSettlement.AllocationIncomplete
-- ############################################################
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545893 /*From ID Server*/,0,TO_TIMESTAMP('2026-10-01 16:00:05','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Die Zahlung konnte der Rechnung nicht zugeordnet werden.','E',TO_TIMESTAMP('2026-10-01 16:00:05','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos.InvoiceSettlement.AllocationIncomplete')
;

UPDATE AD_Message SET ErrorCode='POS_INVOICE_SETTLEMENT_NOT_ALLOCATED', Updated=TO_TIMESTAMP('2026-10-01 16:00:06','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID=545893
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545893
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl SET MsgText='The payment could not be allocated to the invoice.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-10-01 16:00:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545893
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-10-01 16:00:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_DE' AND AD_Message_ID=545893
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-10-01 16:00:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_CH' AND AD_Message_ID=545893
;
