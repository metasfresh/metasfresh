-- AD_Message IDs allocated from idserver.metas.de:
--   AD_Message 545889 (de.metas.pos.InvoiceSettlement.JournalDescription)

-- ############################################################
-- Message: de.metas.pos.InvoiceSettlement.JournalDescription
-- Cash-journal CASH_IN_OUT line description for a POS cash invoice-settlement; {0} = the settled invoice's
-- document number. Mirrors de.metas.pos.Return.JournalDescription so both POS cash-journal lines are
-- message-resourced rather than hard-coded German (see migration 5827240).
-- ############################################################
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545889 /*From ID Server*/,0,TO_TIMESTAMP('2026-10-01 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Rechnung {0}','I',TO_TIMESTAMP('2026-10-01 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos.InvoiceSettlement.JournalDescription')
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545889
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl SET MsgText='Invoice {0}',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-10-01 10:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545889
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-10-01 10:00:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_DE' AND AD_Message_ID=545889
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-10-01 10:00:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_CH' AND AD_Message_ID=545889
;
