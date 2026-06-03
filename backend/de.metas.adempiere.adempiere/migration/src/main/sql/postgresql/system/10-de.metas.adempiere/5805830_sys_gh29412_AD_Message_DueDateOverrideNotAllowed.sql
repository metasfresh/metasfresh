-- AD_Message for C_Invoice due date override validation.
-- Shown when attempting to override the due date of an invoice whose payment term forbids due date changes.
-- Referenced from Java constant DUE_DATE_OVERRIDE_NOT_ALLOWED_MESSAGE in the C_Invoice_OverrideDueDate process.
--
-- AD_Message base text is in German (DE). Rationale: most users are German-speakers; if a translation
-- is missing the fallback (AD_Message.MsgText) shows German. en_US translation is provided below.
-- {0} is the invoice number or list of invoice numbers (filled in by AdempiereException(MSG, invoiceNumbers)).

-- =============================================================================
-- DueDateOverrideNotAllowed  (AD_Message_ID 545745, ErrorCode DUE_DATE_OVERRIDE_NOT_ALLOWED)
-- =============================================================================

-- 2026-06-02T00:00:00.000Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545745 /*From ID Server*/,0,TO_TIMESTAMP('2026-06-02 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Das Überschreiben des Fälligkeitsdatums ist für Rechnung(en) {0} nicht zulässig: die Zahlungsbedingung erlaubt es nicht.','E',TO_TIMESTAMP('2026-06-02 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'DueDateOverrideNotAllowed')
;

-- AD_Message.ErrorCode is varchar(40); use a short form (the full key lives in AD_Message.Value).
-- 2026-06-02T00:00:01.000Z
UPDATE AD_Message SET ErrorCode='DUE_DATE_OVERRIDE_NOT_ALLOWED', Updated=TO_TIMESTAMP('2026-06-02 00:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545745 /*From ID Server*/
;

-- Seed AD_Message_Trl for every active system language using the base (DE) text.
-- 2026-06-02T00:00:02.000Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545745 /*From ID Server*/
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Override en_US with the English translation.
-- 2026-06-02T00:00:03.000Z
UPDATE AD_Message_Trl SET MsgText='Due date override is not allowed for invoice(s) {0}: the payment term forbids it.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-02 00:00:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545745 /*From ID Server*/
;

-- Mark de_DE and de_CH as actively translated (same text as base; flips IsTranslated to Y).
-- 2026-06-02T00:00:04.000Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-02 00:00:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545745 /*From ID Server*/
;

-- 2026-06-02T00:00:05.000Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-02 00:00:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545745 /*From ID Server*/
;
