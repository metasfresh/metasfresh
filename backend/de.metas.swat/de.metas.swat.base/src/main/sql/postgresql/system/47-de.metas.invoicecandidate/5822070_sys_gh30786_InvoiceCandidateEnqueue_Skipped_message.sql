-- IDs allocated from idserver.metas.de on 2026-09-03:
--   AD_MigrationScript 5822070 (this script)
--   AD_Message         545824 (InvoiceCandidateEnqueue_Skipped)
--
-- Appended to the "Auswahl Fakturieren" process summary -- the string the user sees the moment the
-- run finishes -- when the ENQUEUER dropped part of the selection before any invoicing was attempted
-- (candidate already processed, in dispute, to-clear, simulation, manual rule, date not yet due, or
-- QtyOrdered <> 0 with QtyToInvoice = 0).
--
-- Reported in the SUMMARY rather than as a user notification on purpose: the summary is synchronous,
-- reaches whoever started the run, and does not depend on that user's AD_User.NotificationType.
--   {0} = how many of the selected candidates were skipped
--   {1} = how many candidates were selected
--   {2} = the skip reasons (first few; the process log has them all)

-- 2026-09-03T01:05:01
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545824 /*From ID Server*/,0,TO_TIMESTAMP('2026-09-03 01:05:01','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','{0} von {1} ausgewählten Rechnungskandidat(en) wurden nicht fakturiert: {2}','I',TO_TIMESTAMP('2026-09-03 01:05:01','YYYY-MM-DD HH24:MI:SS'),100,'InvoiceCandidateEnqueue_Skipped')
;

-- 2026-09-03T01:05:02
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545824
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2026-09-03T01:05:03
UPDATE AD_Message_Trl SET MsgText='{0} of {1} selected invoice candidate(s) were not invoiced: {2}',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-03 01:05:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545824
;

-- 2026-09-03T01:05:04
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-03 01:05:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545824
;

-- 2026-09-03T01:05:05
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-03 01:05:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545824
;
