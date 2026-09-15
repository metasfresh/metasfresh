-- https://github.com/metasfresh/me03/issues/29368
-- AD_Message for the C_Payment BEFORE_PREPARE guard (T48e — AC #16).
-- Proforma payments must be full payments — abs(PayAmt) must equal the proforma's GrandTotal.
-- Partial proforma payments are forbidden because there are no C_AllocationLine rows for
-- proforma payments (no accounting), so a residual balance cannot be reconciled.

INSERT INTO AD_Message (AD_Message_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        Value, MsgText, MsgType, EntityType)
VALUES (545672 /*From ID Server*/, 0, 0, 'Y', '2026-04-25 22:00', 0, '2026-04-25 22:00', 0,
        'de.metas.invoice.proforma.PaymentMustBeFull',
        'Proforma payments must be full payments: abs(PayAmt) must equal the proforma''s GrandTotal. Partial payments are not allowed because there is no allocation mechanism to reconcile residual balances on proforma invoices.',
        'E', 'D');

UPDATE AD_Message
SET ErrorCode = 'ProformaPaymentMustBeFull',
    Updated   = '2026-04-25 22:00', UpdatedBy = 0
WHERE AD_Message_ID = 545672;

-- DE translation (umlauts proper UTF-8)
INSERT INTO AD_Message_Trl (AD_Message_ID, AD_Language, MsgText, IsTranslated, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
VALUES (545672, 'de_DE',
        'Proforma-Zahlungen müssen Vollzahlungen sein: abs(PayAmt) muss dem GrandTotal der Proforma-Rechnung entsprechen. Teilzahlungen sind nicht zulässig, da es für Proforma-Rechnungen keinen Zuordnungsmechanismus zum Verrechnen verbleibender Beträge gibt.',
        'Y', 0, 0, 'Y', '2026-04-25 22:00', 0, '2026-04-25 22:00', 0);
