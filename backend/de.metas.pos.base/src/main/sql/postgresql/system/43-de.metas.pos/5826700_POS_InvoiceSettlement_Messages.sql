-- AD_Message IDs allocated from idserver.metas.de:
--   AD_Message 545865 (de.metas.pos.InvoiceSettlement.CurrencyMismatch)
--   AD_Message 545866 (de.metas.pos.InvoiceSettlement.NoLongerOpen)
--   AD_Message 545867 (de.metas.pos.InvoiceSettlement.WrongOrg)
--   AD_Message 545868 (de.metas.pos.InvoiceSettlement.TenderedTooLow)
--
-- Note: only these 4 AdMessageKeys are actually referenced by POSInvoiceSettlementService
-- (grep-verified). A 5th key, "de.metas.pos.InvoiceSettlement.NotFound", does not exist anywhere
-- in the codebase, so it is intentionally not created here.

-- ############################################################
-- Message 1: de.metas.pos.InvoiceSettlement.CurrencyMismatch
-- ############################################################
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545865 /*From ID Server*/,0,TO_TIMESTAMP('2026-09-28 15:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Rechnungswährung weicht von der Kassenwährung ab.','E',TO_TIMESTAMP('2026-09-28 15:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos.InvoiceSettlement.CurrencyMismatch')
;

UPDATE AD_Message SET ErrorCode='POS_INVOICE_SETTLEMENT_CURRENCY_MISMATCH', Updated=TO_TIMESTAMP('2026-09-28 15:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID=545865
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545865
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl SET MsgText='Invoice currency differs from the till currency.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-28 15:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545865
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-28 15:00:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_DE' AND AD_Message_ID=545865
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-28 15:00:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_CH' AND AD_Message_ID=545865
;

-- ############################################################
-- Message 2: de.metas.pos.InvoiceSettlement.NoLongerOpen
-- ############################################################
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545866 /*From ID Server*/,0,TO_TIMESTAMP('2026-09-28 15:00:05','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Die Rechnung ist nicht mehr offen.','E',TO_TIMESTAMP('2026-09-28 15:00:05','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos.InvoiceSettlement.NoLongerOpen')
;

UPDATE AD_Message SET ErrorCode='POS_INVOICE_SETTLEMENT_NO_LONGER_OPEN', Updated=TO_TIMESTAMP('2026-09-28 15:00:06','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID=545866
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545866
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl SET MsgText='The invoice is no longer open.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-28 15:00:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545866
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-28 15:00:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_DE' AND AD_Message_ID=545866
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-28 15:00:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_CH' AND AD_Message_ID=545866
;

-- ############################################################
-- Message 3: de.metas.pos.InvoiceSettlement.WrongOrg
-- ############################################################
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545867 /*From ID Server*/,0,TO_TIMESTAMP('2026-09-28 15:00:10','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Die Rechnung gehört zu einer anderen Organisation.','E',TO_TIMESTAMP('2026-09-28 15:00:10','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos.InvoiceSettlement.WrongOrg')
;

UPDATE AD_Message SET ErrorCode='POS_INVOICE_SETTLEMENT_WRONG_ORG', Updated=TO_TIMESTAMP('2026-09-28 15:00:11','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID=545867
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545867
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl SET MsgText='The invoice belongs to a different organization.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-28 15:00:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545867
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-28 15:00:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_DE' AND AD_Message_ID=545867
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-28 15:00:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_CH' AND AD_Message_ID=545867
;

-- ############################################################
-- Message 4: de.metas.pos.InvoiceSettlement.TenderedTooLow
-- ############################################################
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545868 /*From ID Server*/,0,TO_TIMESTAMP('2026-09-28 15:00:15','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Der erhaltene Betrag ist kleiner als der offene Betrag.','E',TO_TIMESTAMP('2026-09-28 15:00:15','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos.InvoiceSettlement.TenderedTooLow')
;

UPDATE AD_Message SET ErrorCode='POS_INVOICE_SETTLEMENT_TENDERED_TOO_LOW', Updated=TO_TIMESTAMP('2026-09-28 15:00:16','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID=545868
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545868
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl SET MsgText='The amount tendered is less than the open amount.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-28 15:00:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545868
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-28 15:00:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_DE' AND AD_Message_ID=545868
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-28 15:00:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_CH' AND AD_Message_ID=545868
;
