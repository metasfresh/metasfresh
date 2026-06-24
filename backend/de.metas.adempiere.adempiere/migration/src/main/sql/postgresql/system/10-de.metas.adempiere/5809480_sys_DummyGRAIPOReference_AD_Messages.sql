-- 2026-06-24
-- AD_Messages for the dummy-GRAI PO-reference prerequisite validation.
-- Surfaced when a sales order's customer requires dummy GRAIs (GRAIRequired = YesWithDummyGRAIs) but the
-- order's PO reference cannot form a valid dummy-GRAI serial prefix (missing after trim, or longer than 10
-- characters). Used by the sales-order change/completion interceptors, the picking job-open check, and the
-- picking-completion backstop, so the same operator-facing text shows everywhere.

-- AD_Message: DummyGRAISerialPrefixTooLong
INSERT INTO AD_Message (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        MsgType, Value, MsgText, EntityType, AD_Message_ID)
VALUES (0, 0, 'Y', TO_TIMESTAMP('2026-06-24 12:00:00','YYYY-MM-DD HH24:MI:SS'), 0, TO_TIMESTAMP('2026-06-24 12:00:00','YYYY-MM-DD HH24:MI:SS'), 0,
        'E', 'de.metas.handlingunits.grai.DummyGRAISerialPrefixTooLong',
        'Die Bestellreferenz "{0}" ist zu lang für die GRAI-Erzeugung (max. 10 Zeichen). Bitte die Bestellreferenz im Auftrag entsprechend kürzen.',
        'de.metas.handlingunits', 545765 /*From ID Server*/);

UPDATE AD_Message SET ErrorCode = 'GRAI_POREFERENCE_TOO_LONG' WHERE AD_Message_ID = 545765;

INSERT INTO AD_Message_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Message_ID, MsgText, MsgTip, IsTranslated)
VALUES ('de_DE', 0, 0, 'Y', TO_TIMESTAMP('2026-06-24 12:00:01','YYYY-MM-DD HH24:MI:SS'), 0, TO_TIMESTAMP('2026-06-24 12:00:01','YYYY-MM-DD HH24:MI:SS'), 0,
        545765 /*From ID Server*/,
        'Die Bestellreferenz "{0}" ist zu lang für die GRAI-Erzeugung (max. 10 Zeichen). Bitte die Bestellreferenz im Auftrag entsprechend kürzen.', NULL, 'N');

INSERT INTO AD_Message_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Message_ID, MsgText, MsgTip, IsTranslated)
VALUES ('de_CH', 0, 0, 'Y', TO_TIMESTAMP('2026-06-24 12:00:02','YYYY-MM-DD HH24:MI:SS'), 0, TO_TIMESTAMP('2026-06-24 12:00:02','YYYY-MM-DD HH24:MI:SS'), 0,
        545765 /*From ID Server*/,
        'Die Bestellreferenz "{0}" ist zu lang für die GRAI-Erzeugung (max. 10 Zeichen). Bitte die Bestellreferenz im Auftrag entsprechend kürzen.', NULL, 'N');

INSERT INTO AD_Message_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Message_ID, MsgText, MsgTip, IsTranslated)
VALUES ('en_US', 0, 0, 'Y', TO_TIMESTAMP('2026-06-24 12:00:03','YYYY-MM-DD HH24:MI:SS'), 0, TO_TIMESTAMP('2026-06-24 12:00:03','YYYY-MM-DD HH24:MI:SS'), 0,
        545765 /*From ID Server*/,
        'The PO reference "{0}" is too long for GRAI generation (max. 10 characters). Please shorten the PO reference on the order.', NULL, 'Y');

-- AD_Message: DummyGRAIPOReferenceMissing
INSERT INTO AD_Message (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        MsgType, Value, MsgText, EntityType, AD_Message_ID)
VALUES (0, 0, 'Y', TO_TIMESTAMP('2026-06-24 12:00:04','YYYY-MM-DD HH24:MI:SS'), 0, TO_TIMESTAMP('2026-06-24 12:00:04','YYYY-MM-DD HH24:MI:SS'), 0,
        'E', 'de.metas.handlingunits.grai.DummyGRAIPOReferenceMissing',
        'Auftrag {0}: Es ist keine Bestellreferenz hinterlegt. Für die GRAI-Erzeugung wird eine Bestellreferenz (max. 10 Zeichen) benötigt.',
        'de.metas.handlingunits', 545766 /*From ID Server*/);

UPDATE AD_Message SET ErrorCode = 'GRAI_POREFERENCE_MISSING' WHERE AD_Message_ID = 545766;

INSERT INTO AD_Message_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Message_ID, MsgText, MsgTip, IsTranslated)
VALUES ('de_DE', 0, 0, 'Y', TO_TIMESTAMP('2026-06-24 12:00:05','YYYY-MM-DD HH24:MI:SS'), 0, TO_TIMESTAMP('2026-06-24 12:00:05','YYYY-MM-DD HH24:MI:SS'), 0,
        545766 /*From ID Server*/,
        'Auftrag {0}: Es ist keine Bestellreferenz hinterlegt. Für die GRAI-Erzeugung wird eine Bestellreferenz (max. 10 Zeichen) benötigt.', NULL, 'N');

INSERT INTO AD_Message_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Message_ID, MsgText, MsgTip, IsTranslated)
VALUES ('de_CH', 0, 0, 'Y', TO_TIMESTAMP('2026-06-24 12:00:06','YYYY-MM-DD HH24:MI:SS'), 0, TO_TIMESTAMP('2026-06-24 12:00:06','YYYY-MM-DD HH24:MI:SS'), 0,
        545766 /*From ID Server*/,
        'Auftrag {0}: Es ist keine Bestellreferenz hinterlegt. Für die GRAI-Erzeugung wird eine Bestellreferenz (max. 10 Zeichen) benötigt.', NULL, 'N');

INSERT INTO AD_Message_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Message_ID, MsgText, MsgTip, IsTranslated)
VALUES ('en_US', 0, 0, 'Y', TO_TIMESTAMP('2026-06-24 12:00:07','YYYY-MM-DD HH24:MI:SS'), 0, TO_TIMESTAMP('2026-06-24 12:00:07','YYYY-MM-DD HH24:MI:SS'), 0,
        545766 /*From ID Server*/,
        'Order {0}: no PO reference is set. GRAI generation requires a PO reference (max. 10 characters).', NULL, 'Y');
