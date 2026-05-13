-- 2026-05-13T10:00:00.000Z
-- me03#28882 — Add AD_Message used by the C_Order interceptor that forbids InvoiceRule=Manual together with IsAutoInvoice=Y
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545683 /*From ID Server*/,0,TO_TIMESTAMP('2026-05-13 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Die Kombination ''Rechnungsstellungsregel = Manuell'' und ''Auto-Faktura = Ja'' ist nicht zulässig. Bitte deaktivieren Sie ''Auto-Faktura'', wenn die Faktura manuell ausgelöst werden soll.','E',TO_TIMESTAMP('2026-05-13 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'ERR_INVOICERULE_MANUAL_AUTO_INVOICE')
;

-- 2026-05-13T10:00:01.000Z
-- me03#28882 — Seed AD_Message_Trl rows for all active system languages and the base language
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545683 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2026-05-13T10:00:02.000Z
-- me03#28882 — English (en_US) translation
UPDATE AD_Message_Trl SET MsgText='The combination ''Invoice Rule = Manual'' and ''Auto Invoice = Yes'' is not allowed. Disable ''Auto Invoice'' to trigger invoicing manually.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-13 10:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545683
;

-- 2026-05-13T10:00:03.000Z
-- me03#28882 — Set the ErrorCode for API consumers (per metasfresh-db skill rule on AD_Message error codes)
UPDATE AD_Message SET ErrorCode='INVOICERULE_MANUAL_AUTO_INVOICE',Updated=TO_TIMESTAMP('2026-05-13 10:00:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545683
;

-- 2026-05-13T10:00:04.000Z
-- me03#28882 — Backfill translations for any other active languages (idempotent)
SELECT add_missing_translations()
;
