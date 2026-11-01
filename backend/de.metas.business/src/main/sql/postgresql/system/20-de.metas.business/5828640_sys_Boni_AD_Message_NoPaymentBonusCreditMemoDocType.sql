-- Boni: the payment allocation cannot book the bonus that the customer deducted, because the client/organization has no document type for payment-bonus credit memos (base type ARC, sub type PB)
-- IDs allocated from the central ID server on 2026-10-08: AD_Message 545915

INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Updated, UpdatedBy, Value, ErrorCode)
VALUES (0, 545915 /*From ID Server*/, 0, TO_TIMESTAMP('2026-10-08 10:10:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y',
        'Für diesen Mandanten und diese Organisation gibt es keine Belegart für Zahlungsbonus-Gutschriften (Basisbelegart ARC, Unterart PB).', 'E',
        TO_TIMESTAMP('2026-10-08 10:10:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'de.metas.invoice.paymentbonus.NoPaymentBonusCreditMemoDocType', 'NO_PAYMENT_BONUS_CREDIT_MEMO_DOC_TYPE')
;

INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText, t.MsgTip, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l,
     AD_Message t
WHERE l.IsActive = 'Y'
  AND l.IsSystemLanguage = 'Y'
  AND t.AD_Message_ID = 545915
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = t.AD_Message_ID)
;

UPDATE AD_Message_Trl
SET MsgText='There is no document type for payment bonus credit memos (base type ARC, sub type PB) for this client and organization.', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-10-08 10:10:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'en_US' AND AD_Message_ID = 545915
;

UPDATE AD_Message_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-08 10:10:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language IN ('de_DE', 'de_CH') AND AD_Message_ID = 545915
;
