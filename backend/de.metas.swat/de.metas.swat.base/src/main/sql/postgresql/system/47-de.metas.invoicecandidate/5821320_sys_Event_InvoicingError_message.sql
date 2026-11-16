-- IDs allocated from idserver.metas.de on 2026-08-31:
--   AD_MigrationScript 5821320 (this script)
--   AD_Message         545816 (Event_InvoicingError)
--
-- Notification shown to the user who started a "Create Invoices" run when some or all of the
-- selected invoice candidates could not be invoiced.
--   {0} = number of invoice candidates that failed
--   {1} = the error text

-- 2026-08-31T15:11:41
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545816 /*From ID Server*/,0,TO_TIMESTAMP('2026-08-31 15:11:41','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','{0} Rechnungskandidat(en) konnten nicht fakturiert werden: {1}','E',TO_TIMESTAMP('2026-08-31 15:11:41','YYYY-MM-DD HH24:MI:SS'),100,'Event_InvoicingError')
;

-- 2026-08-31T15:11:42
UPDATE AD_Message SET ErrorCode='INVOICING_ERROR',Updated=TO_TIMESTAMP('2026-08-31 15:11:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545816
;

-- 2026-08-31T15:11:43
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545816
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2026-08-31T15:11:44
UPDATE AD_Message_Trl SET MsgText='{0} invoice candidate(s) could not be invoiced: {1}',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-08-31 15:11:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545816
;

-- 2026-08-31T15:11:45
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-08-31 15:11:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545816
;

-- 2026-08-31T15:11:46
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-08-31 15:11:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545816
;
