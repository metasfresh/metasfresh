-- 2026-03-23
-- AD_Message for GRAICountMismatch error

-- AD_Message: GRAICountMismatch
INSERT INTO AD_Message (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        MsgType, Value, MsgText, EntityType, AD_Message_ID)
VALUES (0, 0, 'Y', TO_TIMESTAMP('2026-03-23 12:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-23 12:00', 'YYYY-MM-DD HH24:MI'), 0,
        'E', 'de.metas.handlingunits.picking.GRAICountMismatch',
        'LU {0}: {1} von {2} TUs haben einen GRAI. Bitte alle TUs scannen.',
        'de.metas.handlingunits', 545647 /*From ID Server*/);

-- Set ErrorCode (required for MsgType='E')
UPDATE AD_Message SET ErrorCode = 'GRAI_COUNT_MISMATCH' WHERE AD_Message_ID = 545647;

-- AD_Message_Trl: de_DE
INSERT INTO AD_Message_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Message_ID, MsgText, MsgTip, IsTranslated)
VALUES ('de_DE', 0, 0, 'Y', TO_TIMESTAMP('2026-03-23 12:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-23 12:00', 'YYYY-MM-DD HH24:MI'), 0,
        545647 /*From ID Server*/,
        'LU {0}: {1} von {2} TUs haben einen GRAI. Bitte alle TUs scannen.', NULL, 'N');

-- AD_Message_Trl: de_CH
INSERT INTO AD_Message_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Message_ID, MsgText, MsgTip, IsTranslated)
VALUES ('de_CH', 0, 0, 'Y', TO_TIMESTAMP('2026-03-23 12:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-23 12:00', 'YYYY-MM-DD HH24:MI'), 0,
        545647 /*From ID Server*/,
        'LU {0}: {1} von {2} TUs haben einen GRAI. Bitte alle TUs scannen.', NULL, 'N');

-- AD_Message_Trl: en_US
INSERT INTO AD_Message_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Message_ID, MsgText, MsgTip, IsTranslated)
VALUES ('en_US', 0, 0, 'Y', TO_TIMESTAMP('2026-03-23 12:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-23 12:00', 'YYYY-MM-DD HH24:MI'), 0,
        545647 /*From ID Server*/,
        'LU {0}: {1} of {2} TUs have a GRAI. Please scan all TUs.', NULL, 'Y');
