-- 2026-04-28
-- AD_Message — IsPartialInvoiceReadOnlyAfterComplete (iter-3 https://github.com/metasfresh/me03/issues/29369)

-- AD_Message
INSERT INTO AD_Message (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        MsgType, Value, MsgText, EntityType, AD_Message_ID)
VALUES (0, 0, 'Y',
        TO_TIMESTAMP('2026-04-28 00:00', 'YYYY-MM-DD HH24:MI'), 0,
        TO_TIMESTAMP('2026-04-28 00:00', 'YYYY-MM-DD HH24:MI'), 0,
        'E',
        'de.metas.invoice.IsPartialInvoiceReadOnlyAfterComplete',
        'IsPartialInvoice cannot be changed after invoice completion.',
        'D',
        545674 /*From ID Server*/);

-- Set ErrorCode (required for MsgType='E')
UPDATE AD_Message
SET ErrorCode = 'IsPartialInvoiceReadOnlyAfterComplete'
WHERE AD_Message_ID = 545674;

-- AD_Message_Trl: de_DE
INSERT INTO AD_Message_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                             AD_Message_ID, MsgText, MsgTip, IsTranslated)
VALUES ('de_DE', 0, 0, 'Y',
        TO_TIMESTAMP('2026-04-28 00:00', 'YYYY-MM-DD HH24:MI'), 0,
        TO_TIMESTAMP('2026-04-28 00:00', 'YYYY-MM-DD HH24:MI'), 0,
        545674 /*From ID Server*/,
        'Teilrechnung-Kennzeichen kann nach Abschluss der Rechnung nicht mehr geändert werden.',
        NULL, 'N');

-- AD_Message_Trl: de_CH
INSERT INTO AD_Message_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                             AD_Message_ID, MsgText, MsgTip, IsTranslated)
VALUES ('de_CH', 0, 0, 'Y',
        TO_TIMESTAMP('2026-04-28 00:00', 'YYYY-MM-DD HH24:MI'), 0,
        TO_TIMESTAMP('2026-04-28 00:00', 'YYYY-MM-DD HH24:MI'), 0,
        545674 /*From ID Server*/,
        'Teilrechnung-Kennzeichen kann nach Abschluss der Rechnung nicht mehr geändert werden.',
        NULL, 'N');

-- AD_Message_Trl: en_US
INSERT INTO AD_Message_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                             AD_Message_ID, MsgText, MsgTip, IsTranslated)
VALUES ('en_US', 0, 0, 'Y',
        TO_TIMESTAMP('2026-04-28 00:00', 'YYYY-MM-DD HH24:MI'), 0,
        TO_TIMESTAMP('2026-04-28 00:00', 'YYYY-MM-DD HH24:MI'), 0,
        545674 /*From ID Server*/,
        'IsPartialInvoice cannot be changed after invoice completion.',
        NULL, 'Y');
