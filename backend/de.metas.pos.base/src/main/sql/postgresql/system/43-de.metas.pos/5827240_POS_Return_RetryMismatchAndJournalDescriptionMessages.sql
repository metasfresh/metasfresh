-- AD_Message IDs allocated from idserver.metas.de:
--   AD_Message 545882 (de.metas.pos.Return.RetryContentMismatch)
--   AD_Message 545883 (de.metas.pos.Return.JournalDescription)

-- ############################################################
-- Message: de.metas.pos.Return.RetryContentMismatch
-- Rejects a POS-return retry whose externalId resolves an existing return document that no longer matches the
-- edited cart, so the OLD amount can never be refunded for an edited cart.
-- ############################################################
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545882 /*From ID Server*/,0,TO_TIMESTAMP('2026-09-30 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Der Warenkorb der Rücknahme wurde geändert. Bitte die Rücknahme neu starten.','E',TO_TIMESTAMP('2026-09-30 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos.Return.RetryContentMismatch')
;

UPDATE AD_Message SET ErrorCode='POS_RETURN_RETRY_CONTENT_MISMATCH', Updated=TO_TIMESTAMP('2026-09-30 10:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID=545882
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545882
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl SET MsgText='The return cart has changed. Please restart the return.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-30 10:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545882
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-30 10:00:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_DE' AND AD_Message_ID=545882
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-30 10:00:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_CH' AND AD_Message_ID=545882
;

-- ############################################################
-- Message: de.metas.pos.Return.JournalDescription
-- Cash-journal CASH_IN_OUT line description for a POS-return refund; {0} = the credit memo's document number.
-- ############################################################
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545883 /*From ID Server*/,0,TO_TIMESTAMP('2026-09-30 10:01:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Rücknahme {0}','I',TO_TIMESTAMP('2026-09-30 10:01:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos.Return.JournalDescription')
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545883
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl SET MsgText='Return {0}',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-30 10:01:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545883
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-30 10:01:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_DE' AND AD_Message_ID=545883
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-30 10:01:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_CH' AND AD_Message_ID=545883
;
