-- 2026-04-22
-- AD_Messages for Excel header translation of m_hu_pi_item_product_consolidate_report()
-- The JdbcExcelExporter translates each raw SQL column name via AD_Message lookup
-- (msgBL.translatable(columnName).translate(adLanguage)); without these entries,
-- the Excel headers show the raw SQL aliases (issue_type, product_name, etc.).

-- issue_type
INSERT INTO AD_Message (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, MsgType, Value, MsgText, EntityType, AD_Message_ID)
VALUES (0, 0, 'Y', TO_TIMESTAMP('2026-04-22 08:30', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-04-22 08:30', 'YYYY-MM-DD HH24:MI'), 0,
        'I', 'issue_type', 'Konflikttyp', 'de.metas.handlingunits', 545660 /*From ID Server*/);

INSERT INTO AD_Message_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Message_ID, MsgText, MsgTip, IsTranslated)
VALUES ('de_DE', 0, 0, 'Y', TO_TIMESTAMP('2026-04-22 08:30', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-04-22 08:30', 'YYYY-MM-DD HH24:MI'), 0,
        545660 /*From ID Server*/, 'Konflikttyp', NULL, 'N');

INSERT INTO AD_Message_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Message_ID, MsgText, MsgTip, IsTranslated)
VALUES ('de_CH', 0, 0, 'Y', TO_TIMESTAMP('2026-04-22 08:30', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-04-22 08:30', 'YYYY-MM-DD HH24:MI'), 0,
        545660 /*From ID Server*/, 'Konflikttyp', NULL, 'N');

INSERT INTO AD_Message_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Message_ID, MsgText, MsgTip, IsTranslated)
VALUES ('en_US', 0, 0, 'Y', TO_TIMESTAMP('2026-04-22 08:30', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-04-22 08:30', 'YYYY-MM-DD HH24:MI'), 0,
        545660 /*From ID Server*/, 'Issue Type', NULL, 'Y');

-- product_value
INSERT INTO AD_Message (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, MsgType, Value, MsgText, EntityType, AD_Message_ID)
VALUES (0, 0, 'Y', TO_TIMESTAMP('2026-04-22 08:30', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-04-22 08:30', 'YYYY-MM-DD HH24:MI'), 0,
        'I', 'product_value', 'Produkt-Suchschlüssel', 'de.metas.handlingunits', 545661 /*From ID Server*/);

INSERT INTO AD_Message_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Message_ID, MsgText, MsgTip, IsTranslated)
VALUES ('de_DE', 0, 0, 'Y', TO_TIMESTAMP('2026-04-22 08:30', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-04-22 08:30', 'YYYY-MM-DD HH24:MI'), 0,
        545661 /*From ID Server*/, 'Produkt-Suchschlüssel', NULL, 'N');

INSERT INTO AD_Message_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Message_ID, MsgText, MsgTip, IsTranslated)
VALUES ('de_CH', 0, 0, 'Y', TO_TIMESTAMP('2026-04-22 08:30', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-04-22 08:30', 'YYYY-MM-DD HH24:MI'), 0,
        545661 /*From ID Server*/, 'Produkt-Suchschlüssel', NULL, 'N');

INSERT INTO AD_Message_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Message_ID, MsgText, MsgTip, IsTranslated)
VALUES ('en_US', 0, 0, 'Y', TO_TIMESTAMP('2026-04-22 08:30', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-04-22 08:30', 'YYYY-MM-DD HH24:MI'), 0,
        545661 /*From ID Server*/, 'Product Value', NULL, 'Y');

-- product_name
INSERT INTO AD_Message (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, MsgType, Value, MsgText, EntityType, AD_Message_ID)
VALUES (0, 0, 'Y', TO_TIMESTAMP('2026-04-22 08:30', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-04-22 08:30', 'YYYY-MM-DD HH24:MI'), 0,
        'I', 'product_name', 'Produktname', 'de.metas.handlingunits', 545662 /*From ID Server*/);

INSERT INTO AD_Message_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Message_ID, MsgText, MsgTip, IsTranslated)
VALUES ('de_DE', 0, 0, 'Y', TO_TIMESTAMP('2026-04-22 08:30', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-04-22 08:30', 'YYYY-MM-DD HH24:MI'), 0,
        545662 /*From ID Server*/, 'Produktname', NULL, 'N');

INSERT INTO AD_Message_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Message_ID, MsgText, MsgTip, IsTranslated)
VALUES ('de_CH', 0, 0, 'Y', TO_TIMESTAMP('2026-04-22 08:30', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-04-22 08:30', 'YYYY-MM-DD HH24:MI'), 0,
        545662 /*From ID Server*/, 'Produktname', NULL, 'N');

INSERT INTO AD_Message_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Message_ID, MsgText, MsgTip, IsTranslated)
VALUES ('en_US', 0, 0, 'Y', TO_TIMESTAMP('2026-04-22 08:30', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-04-22 08:30', 'YYYY-MM-DD HH24:MI'), 0,
        545662 /*From ID Server*/, 'Product Name', NULL, 'Y');

-- pi_name
INSERT INTO AD_Message (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, MsgType, Value, MsgText, EntityType, AD_Message_ID)
VALUES (0, 0, 'Y', TO_TIMESTAMP('2026-04-22 08:30', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-04-22 08:30', 'YYYY-MM-DD HH24:MI'), 0,
        'I', 'pi_name', 'Packvorschrift', 'de.metas.handlingunits', 545663 /*From ID Server*/);

INSERT INTO AD_Message_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Message_ID, MsgText, MsgTip, IsTranslated)
VALUES ('de_DE', 0, 0, 'Y', TO_TIMESTAMP('2026-04-22 08:30', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-04-22 08:30', 'YYYY-MM-DD HH24:MI'), 0,
        545663 /*From ID Server*/, 'Packvorschrift', NULL, 'N');

INSERT INTO AD_Message_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Message_ID, MsgText, MsgTip, IsTranslated)
VALUES ('de_CH', 0, 0, 'Y', TO_TIMESTAMP('2026-04-22 08:30', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-04-22 08:30', 'YYYY-MM-DD HH24:MI'), 0,
        545663 /*From ID Server*/, 'Packvorschrift', NULL, 'N');

INSERT INTO AD_Message_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Message_ID, MsgText, MsgTip, IsTranslated)
VALUES ('en_US', 0, 0, 'Y', TO_TIMESTAMP('2026-04-22 08:30', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-04-22 08:30', 'YYYY-MM-DD HH24:MI'), 0,
        545663 /*From ID Server*/, 'Packing Instruction', NULL, 'Y');

-- bpartner_name
INSERT INTO AD_Message (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, MsgType, Value, MsgText, EntityType, AD_Message_ID)
VALUES (0, 0, 'Y', TO_TIMESTAMP('2026-04-22 08:30', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-04-22 08:30', 'YYYY-MM-DD HH24:MI'), 0,
        'I', 'bpartner_name', 'Geschäftspartner', 'de.metas.handlingunits', 545664 /*From ID Server*/);

INSERT INTO AD_Message_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Message_ID, MsgText, MsgTip, IsTranslated)
VALUES ('de_DE', 0, 0, 'Y', TO_TIMESTAMP('2026-04-22 08:30', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-04-22 08:30', 'YYYY-MM-DD HH24:MI'), 0,
        545664 /*From ID Server*/, 'Geschäftspartner', NULL, 'N');

INSERT INTO AD_Message_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Message_ID, MsgText, MsgTip, IsTranslated)
VALUES ('de_CH', 0, 0, 'Y', TO_TIMESTAMP('2026-04-22 08:30', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-04-22 08:30', 'YYYY-MM-DD HH24:MI'), 0,
        545664 /*From ID Server*/, 'Geschäftspartner', NULL, 'N');

INSERT INTO AD_Message_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Message_ID, MsgText, MsgTip, IsTranslated)
VALUES ('en_US', 0, 0, 'Y', TO_TIMESTAMP('2026-04-22 08:30', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-04-22 08:30', 'YYYY-MM-DD HH24:MI'), 0,
        545664 /*From ID Server*/, 'Business Partner', NULL, 'Y');

-- uom_symbol
INSERT INTO AD_Message (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, MsgType, Value, MsgText, EntityType, AD_Message_ID)
VALUES (0, 0, 'Y', TO_TIMESTAMP('2026-04-22 08:30', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-04-22 08:30', 'YYYY-MM-DD HH24:MI'), 0,
        'I', 'uom_symbol', 'Einheit', 'de.metas.handlingunits', 545665 /*From ID Server*/);

INSERT INTO AD_Message_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Message_ID, MsgText, MsgTip, IsTranslated)
VALUES ('de_DE', 0, 0, 'Y', TO_TIMESTAMP('2026-04-22 08:30', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-04-22 08:30', 'YYYY-MM-DD HH24:MI'), 0,
        545665 /*From ID Server*/, 'Einheit', NULL, 'N');

INSERT INTO AD_Message_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Message_ID, MsgText, MsgTip, IsTranslated)
VALUES ('de_CH', 0, 0, 'Y', TO_TIMESTAMP('2026-04-22 08:30', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-04-22 08:30', 'YYYY-MM-DD HH24:MI'), 0,
        545665 /*From ID Server*/, 'Einheit', NULL, 'N');

INSERT INTO AD_Message_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Message_ID, MsgText, MsgTip, IsTranslated)
VALUES ('en_US', 0, 0, 'Y', TO_TIMESTAMP('2026-04-22 08:30', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-04-22 08:30', 'YYYY-MM-DD HH24:MI'), 0,
        545665 /*From ID Server*/, 'UOM', NULL, 'Y');

-- conflicting_field
INSERT INTO AD_Message (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, MsgType, Value, MsgText, EntityType, AD_Message_ID)
VALUES (0, 0, 'Y', TO_TIMESTAMP('2026-04-22 08:30', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-04-22 08:30', 'YYYY-MM-DD HH24:MI'), 0,
        'I', 'conflicting_field', 'Konfliktfeld', 'de.metas.handlingunits', 545666 /*From ID Server*/);

INSERT INTO AD_Message_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Message_ID, MsgText, MsgTip, IsTranslated)
VALUES ('de_DE', 0, 0, 'Y', TO_TIMESTAMP('2026-04-22 08:30', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-04-22 08:30', 'YYYY-MM-DD HH24:MI'), 0,
        545666 /*From ID Server*/, 'Konfliktfeld', NULL, 'N');

INSERT INTO AD_Message_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Message_ID, MsgText, MsgTip, IsTranslated)
VALUES ('de_CH', 0, 0, 'Y', TO_TIMESTAMP('2026-04-22 08:30', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-04-22 08:30', 'YYYY-MM-DD HH24:MI'), 0,
        545666 /*From ID Server*/, 'Konfliktfeld', NULL, 'N');

INSERT INTO AD_Message_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Message_ID, MsgText, MsgTip, IsTranslated)
VALUES ('en_US', 0, 0, 'Y', TO_TIMESTAMP('2026-04-22 08:30', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-04-22 08:30', 'YYYY-MM-DD HH24:MI'), 0,
        545666 /*From ID Server*/, 'Conflicting Field', NULL, 'Y');
