-- AD_Message IDs allocated from idserver.metas.de:
--   AD_Message 545851 (de.metas.pos.CashJournalNotOpen)
--   AD_Message 545852 (de.metas.pos.CashWithdrawal.NoCategories)
--   AD_Message 545853 (de.metas.pos.CashWithdrawal.AmountMustBePositive)

-- ############################################################
-- Message 1: de.metas.pos.CashJournalNotOpen
-- ############################################################
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545851 /*From ID Server*/,0,TO_TIMESTAMP('2026-09-24 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Die Kasse ist nicht geöffnet.','E',TO_TIMESTAMP('2026-09-24 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos.CashJournalNotOpen')
;

UPDATE AD_Message SET ErrorCode='CASH_JOURNAL_NOT_OPEN', Updated=TO_TIMESTAMP('2026-09-24 10:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID=545851
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545851
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl SET MsgText='The cash journal is not open.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-24 10:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545851
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-24 10:00:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_DE' AND AD_Message_ID=545851
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-24 10:00:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_CH' AND AD_Message_ID=545851
;

-- ############################################################
-- Message 2: de.metas.pos.CashWithdrawal.NoCategories
-- ############################################################
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545852 /*From ID Server*/,0,TO_TIMESTAMP('2026-09-24 10:00:05','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Keine Entnahme-Kategorien konfiguriert.','E',TO_TIMESTAMP('2026-09-24 10:00:05','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos.CashWithdrawal.NoCategories')
;

UPDATE AD_Message SET ErrorCode='CASH_WITHDRAWAL_NO_CATEGORIES', Updated=TO_TIMESTAMP('2026-09-24 10:00:06','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID=545852
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545852
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl SET MsgText='No cash withdrawal categories configured.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-24 10:00:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545852
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-24 10:00:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_DE' AND AD_Message_ID=545852
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-24 10:00:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_CH' AND AD_Message_ID=545852
;

-- ############################################################
-- Message 3: de.metas.pos.CashWithdrawal.AmountMustBePositive
-- ############################################################
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545853 /*From ID Server*/,0,TO_TIMESTAMP('2026-09-24 10:00:10','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Der Betrag muss größer als 0 sein.','E',TO_TIMESTAMP('2026-09-24 10:00:10','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos.CashWithdrawal.AmountMustBePositive')
;

UPDATE AD_Message SET ErrorCode='CASH_WITHDRAWAL_AMOUNT_MUST_BE_POSITIVE', Updated=TO_TIMESTAMP('2026-09-24 10:00:11','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID=545853
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545853
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl SET MsgText='The amount must be greater than 0.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-24 10:00:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545853
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-24 10:00:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_DE' AND AD_Message_ID=545853
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-24 10:00:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_CH' AND AD_Message_ID=545853
;
