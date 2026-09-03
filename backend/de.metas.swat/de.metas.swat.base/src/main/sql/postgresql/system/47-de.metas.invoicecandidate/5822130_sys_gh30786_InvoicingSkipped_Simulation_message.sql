-- IDs allocated from idserver.metas.de on 2026-09-03:
--   AD_MigrationScript 5822130 (this script)
--   AD_Message         545825 (InvoiceCandBL_Invoicing_Skipped_Simulation)
--
-- The simulation branch of isSkipCandidateFromInvoicing was the only one of its six that had no
-- AD_Message: it logged a formatted template and built no reusable text. That was harmless while the
-- reason only reached the process log, but the reason is now surfaced in the "Auswahl Fakturieren"
-- summary, so an untranslated Java-built string would put English technical text into a German
-- sentence. Wording mirrors its five siblings (InvoiceCandBL_Invoicing_Skipped_*).
--   {0} = C_Invoice_Candidate_ID

-- 2026-09-03T07:40:01
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545825 /*From ID Server*/,0,TO_TIMESTAMP('2026-09-03 07:40:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoicecandidate','Y','Überspringe Rechnungskandidat {0}, da es sich um eine Simulation handelt.','I',TO_TIMESTAMP('2026-09-03 07:40:01','YYYY-MM-DD HH24:MI:SS'),100,'InvoiceCandBL_Invoicing_Skipped_Simulation')
;

-- 2026-09-03T07:40:02
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545825
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2026-09-03T07:40:03
UPDATE AD_Message_Trl SET MsgText='Skipping invoice candidate {0} because it is a simulation.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-03 07:40:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545825
;

-- 2026-09-03T07:40:04
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-03 07:40:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language IN ('de_DE','de_CH') AND AD_Message_ID=545825
;
