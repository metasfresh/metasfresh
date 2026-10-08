-- AD_Message: BPartner_Value_NotUnique
-- Used when a BPartner Value lookup returns multiple results and no business context can disambiguate.
INSERT INTO AD_Message (AD_Message_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        Value, MsgText, MsgType, EntityType)
VALUES (545631, 0, 0, 'Y', '2026-02-21 10:00', 100, '2026-02-21 10:00', 100,
        'BPartner_Value_NotUnique',
        'More than one Business Partner found with search key "{0}" (found {1}). Please use a unique search key or resolve the ambiguity.',
        'E', 'D');

UPDATE AD_Message SET ErrorCode='BPARTNER_VALUE_NOT_UNIQUE' WHERE AD_Message_ID=545631;

-- DE translation
INSERT INTO AD_Message_Trl (AD_Message_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                            MsgText, IsTranslated)
VALUES (545631, 'de_CH', 0, 0, 'Y', '2026-02-21 10:00', 100, '2026-02-21 10:00', 100,
        'Es wurden mehrere Geschäftspartner mit dem Suchschlüssel "{0}" gefunden ({1} Treffer). Bitte verwenden Sie einen eindeutigen Suchschlüssel oder lösen Sie die Mehrdeutigkeit auf.',
        'Y');

INSERT INTO AD_Message_Trl (AD_Message_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                            MsgText, IsTranslated)
VALUES (545631, 'de_DE', 0, 0, 'Y', '2026-02-21 10:00', 100, '2026-02-21 10:00', 100,
        'Es wurden mehrere Geschäftspartner mit dem Suchschlüssel "{0}" gefunden ({1} Treffer). Bitte verwenden Sie einen eindeutigen Suchschlüssel oder lösen Sie die Mehrdeutigkeit auf.',
        'Y');


-- AD_Message: BPartner_Value_NotUnique_REST
-- Used when REST API val- prefix matches multiple BPartners.
INSERT INTO AD_Message (AD_Message_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        Value, MsgText, MsgType, EntityType)
VALUES (545632, 0, 0, 'Y', '2026-02-21 10:00', 100, '2026-02-21 10:00', 100,
        'BPartner_Value_NotUnique_REST',
        'The identifier "val-{0}" matched {1} Business Partners. The "val-" prefix requires a unique Value within the Org. Use "ext-" or a metasfresh-ID instead.',
        'E', 'D');

UPDATE AD_Message SET ErrorCode='BPARTNER_VALUE_NOT_UNIQUE_REST' WHERE AD_Message_ID=545632;

-- DE translation
INSERT INTO AD_Message_Trl (AD_Message_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                            MsgText, IsTranslated)
VALUES (545632, 'de_CH', 0, 0, 'Y', '2026-02-21 10:00', 100, '2026-02-21 10:00', 100,
        'Der Bezeichner "val-{0}" trifft auf {1} Geschäftspartner zu. Das Präfix "val-" erfordert einen eindeutigen Suchschlüssel innerhalb der Organisation. Verwenden Sie stattdessen "ext-" oder eine metasfresh-ID.',
        'Y');

INSERT INTO AD_Message_Trl (AD_Message_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                            MsgText, IsTranslated)
VALUES (545632, 'de_DE', 0, 0, 'Y', '2026-02-21 10:00', 100, '2026-02-21 10:00', 100,
        'Der Bezeichner "val-{0}" trifft auf {1} Geschäftspartner zu. Das Präfix "val-" erfordert einen eindeutigen Suchschlüssel innerhalb der Organisation. Verwenden Sie stattdessen "ext-" oder eine metasfresh-ID.',
        'Y');
