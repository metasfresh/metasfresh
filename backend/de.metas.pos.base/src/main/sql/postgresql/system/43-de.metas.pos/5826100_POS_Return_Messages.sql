-- AD_Message IDs allocated from idserver.metas.de:
--   AD_Message 545854 (de.metas.pos.Return.NoLines)
--   AD_Message 545855 (de.metas.pos.Return.InvoiceCandidateError)
--   AD_Message 545856 (de.metas.pos.Return.QtyMustBePositive)

-- ############################################################
-- Message 1: de.metas.pos.Return.NoLines
-- ############################################################
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545854 /*From ID Server*/,0,TO_TIMESTAMP('2026-09-24 11:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Keine Rücknahmepositionen.','E',TO_TIMESTAMP('2026-09-24 11:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos.Return.NoLines')
;

UPDATE AD_Message SET ErrorCode='POS_RETURN_NO_LINES', Updated=TO_TIMESTAMP('2026-09-24 11:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID=545854
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545854
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl SET MsgText='No return lines.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-24 11:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545854
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-24 11:00:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_DE' AND AD_Message_ID=545854
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-24 11:00:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_CH' AND AD_Message_ID=545854
;

-- ############################################################
-- Message 2: de.metas.pos.Return.InvoiceCandidateError
-- ############################################################
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545855 /*From ID Server*/,0,TO_TIMESTAMP('2026-09-24 11:00:05','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Rücknahme kann nicht gutgeschrieben werden: {0}','E',TO_TIMESTAMP('2026-09-24 11:00:05','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos.Return.InvoiceCandidateError')
;

UPDATE AD_Message SET ErrorCode='POS_RETURN_INVOICE_CANDIDATE_ERROR', Updated=TO_TIMESTAMP('2026-09-24 11:00:06','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID=545855
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545855
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl SET MsgText='The return cannot be credited: {0}',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-24 11:00:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545855
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-24 11:00:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_DE' AND AD_Message_ID=545855
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-24 11:00:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_CH' AND AD_Message_ID=545855
;

-- ############################################################
-- Message 3: de.metas.pos.Return.QtyMustBePositive
-- ############################################################
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545856 /*From ID Server*/,0,TO_TIMESTAMP('2026-09-24 11:00:10','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Die Rücknahmemenge muss größer als 0 sein.','E',TO_TIMESTAMP('2026-09-24 11:00:10','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos.Return.QtyMustBePositive')
;

UPDATE AD_Message SET ErrorCode='POS_RETURN_QTY_MUST_BE_POSITIVE', Updated=TO_TIMESTAMP('2026-09-24 11:00:11','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID=545856
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545856
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl SET MsgText='The returned quantity must be greater than 0.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-24 11:00:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545856
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-24 11:00:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_DE' AND AD_Message_ID=545856
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-24 11:00:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_CH' AND AD_Message_ID=545856
;
