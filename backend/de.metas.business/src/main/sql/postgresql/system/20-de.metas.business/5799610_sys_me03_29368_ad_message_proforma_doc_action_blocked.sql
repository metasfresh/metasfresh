-- 2026-04-25 https://github.com/metasfresh/me03/issues/29368
-- AD_Message for the proforma `C_Invoice` interceptor that blocks DocActions
-- (reverse / void / reactivate) while an active C_Proforma_Order_Alloc exists.
-- The interceptor previously reused 'NoLCBreakInOrder' as a stopgap; this
-- migration introduces a dedicated message with the correct semantics.

INSERT INTO AD_Message (AD_Message_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        Value, MsgText, MsgType, EntityType)
VALUES (545671 /*From ID Server*/, 0, 0, 'Y', '2026-04-25 12:00', 0, '2026-04-25 12:00', 0,
        'de.metas.invoice.proforma.DocActionBlockedByActiveAllocation',
        'This action is not allowed: the proforma invoice is allocated to an order. Please de-allocate it first via the Proforma Allocation tab.',
        'E', 'D');

UPDATE AD_Message
SET ErrorCode = 'DocActionBlockedByActiveAllocation',
    Updated   = '2026-04-25 12:00', UpdatedBy = 0
WHERE AD_Message_ID = 545671;

-- DE translation (umlauts proper UTF-8)
INSERT INTO AD_Message_Trl (AD_Message_ID, AD_Language, MsgText, IsTranslated, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
VALUES (545671, 'de_DE',
        'Aktion nicht zulässig: Die Proforma-Rechnung ist einer Bestellung zugeordnet. Bitte heben Sie zuerst die Zuordnung im Tab "Proforma-Zuordnung" auf.',
        'Y', 0, 0, 'Y', '2026-04-25 12:00', 0, '2026-04-25 12:00', 0);
