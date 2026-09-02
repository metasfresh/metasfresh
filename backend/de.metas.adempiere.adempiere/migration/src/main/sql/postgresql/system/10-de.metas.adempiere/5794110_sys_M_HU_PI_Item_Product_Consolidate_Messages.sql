-- 2026-03-12
-- AD_Messages for M_HU_PI_Item_Product consolidation report

-- Message 1: M_HU_PI_Item_Product_Conflict_DifferentPI
INSERT INTO AD_Message (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, MsgType, Value, MsgText, EntityType, AD_Message_ID)
VALUES (0, 0, 'Y', TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0,
        'I', 'M_HU_PI_Item_Product_Conflict_DifferentPI',
        'Konflikt: Gleiche GTIN, unterschiedliche Packvorschrift',
        'de.metas.handlingunits', 545644 /*From ID Server*/);

INSERT INTO AD_Message_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Message_ID, MsgText, MsgTip, IsTranslated)
VALUES ('de_DE', 0, 0, 'Y', TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0,
        545644 /*From ID Server*/,
        'Konflikt: Gleiche GTIN, unterschiedliche Packvorschrift', NULL, 'N');

INSERT INTO AD_Message_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Message_ID, MsgText, MsgTip, IsTranslated)
VALUES ('de_CH', 0, 0, 'Y', TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0,
        545644 /*From ID Server*/,
        'Konflikt: Gleiche GTIN, unterschiedliche Packvorschrift', NULL, 'N');

INSERT INTO AD_Message_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Message_ID, MsgText, MsgTip, IsTranslated)
VALUES ('en_US', 0, 0, 'Y', TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0,
        545644 /*From ID Server*/,
        'Conflict: Same GTIN, different packing instruction', NULL, 'Y');

-- Message 2: M_HU_PI_Item_Product_Conflict_DifferentProduct
INSERT INTO AD_Message (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, MsgType, Value, MsgText, EntityType, AD_Message_ID)
VALUES (0, 0, 'Y', TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0,
        'I', 'M_HU_PI_Item_Product_Conflict_DifferentProduct',
        'Konflikt: Gleiche GTIN, unterschiedliches Produkt',
        'de.metas.handlingunits', 545645 /*From ID Server*/);

INSERT INTO AD_Message_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Message_ID, MsgText, MsgTip, IsTranslated)
VALUES ('de_DE', 0, 0, 'Y', TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0,
        545645 /*From ID Server*/,
        'Konflikt: Gleiche GTIN, unterschiedliches Produkt', NULL, 'N');

INSERT INTO AD_Message_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Message_ID, MsgText, MsgTip, IsTranslated)
VALUES ('de_CH', 0, 0, 'Y', TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0,
        545645 /*From ID Server*/,
        'Konflikt: Gleiche GTIN, unterschiedliches Produkt', NULL, 'N');

INSERT INTO AD_Message_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Message_ID, MsgText, MsgTip, IsTranslated)
VALUES ('en_US', 0, 0, 'Y', TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0,
        545645 /*From ID Server*/,
        'Conflict: Same GTIN, different product', NULL, 'Y');

-- Message 3: M_HU_PI_Item_Product_Conflict_DifferentFields
INSERT INTO AD_Message (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, MsgType, Value, MsgText, EntityType, AD_Message_ID)
VALUES (0, 0, 'Y', TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0,
        'I', 'M_HU_PI_Item_Product_Conflict_DifferentFields',
        'Konflikt: Gleiche GTIN/Packvorschrift, unterschiedliche Felder',
        'de.metas.handlingunits', 545646 /*From ID Server*/);

INSERT INTO AD_Message_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Message_ID, MsgText, MsgTip, IsTranslated)
VALUES ('de_DE', 0, 0, 'Y', TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0,
        545646 /*From ID Server*/,
        'Konflikt: Gleiche GTIN/Packvorschrift, unterschiedliche Felder', NULL, 'N');

INSERT INTO AD_Message_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Message_ID, MsgText, MsgTip, IsTranslated)
VALUES ('de_CH', 0, 0, 'Y', TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0,
        545646 /*From ID Server*/,
        'Konflikt: Gleiche GTIN/Packvorschrift, unterschiedliche Felder', NULL, 'N');

INSERT INTO AD_Message_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Message_ID, MsgText, MsgTip, IsTranslated)
VALUES ('en_US', 0, 0, 'Y', TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0,
        545646 /*From ID Server*/,
        'Conflict: Same GTIN/packing instruction, different fields', NULL, 'Y');
