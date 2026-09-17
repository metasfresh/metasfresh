-- 2026-05-13T18:00:00.000Z
-- InvoiceRule Manual — Dedicated skip-log AD_Message used by InvoiceCandBL.isSkipCandidateFromInvoicing when a Manual IC is skipped because the IsInvoiceManualRule flag is not set
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545684 /*From ID Server*/,0,TO_TIMESTAMP('2026-05-13 18:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Rechnungskandidat {0} wird übersprungen, da Rechnungsstellungsregel = Manuell und das Kennzeichen ''Manuelle Rechnungskandidaten fakturieren'' nicht gesetzt ist.','I',TO_TIMESTAMP('2026-05-13 18:00:00','YYYY-MM-DD HH24:MI:SS'),100,'InvoiceCandBL_Invoicing_Skipped_ManualRule')
;

-- 2026-05-13T18:00:01.000Z
-- InvoiceRule Manual — Seed AD_Message_Trl rows for all active system languages and the base language
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545684 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2026-05-13T18:00:02.000Z
-- InvoiceRule Manual — English (en_US) translation
UPDATE AD_Message_Trl SET MsgText='Invoice candidate {0} is skipped because the invoice rule is Manual and the ''Invoice Manual Candidates'' flag is not set.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-13 18:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545684
;

-- 2026-05-13T18:00:03.000Z
-- InvoiceRule Manual — Backfill translations for any other active languages (idempotent)
SELECT add_missing_translations()
;
