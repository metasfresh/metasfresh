-- Add 3 DropshipWarehouse AD_Messages
-- Created: 2026-05-11 14:00

-- Message 1: DropshipWarehouse_MissingVendorOnLine (Error)
INSERT INTO AD_Message (AD_Message_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Value, MsgType, MsgText, ErrorCode)
VALUES (545679 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-05-11 14:04:00', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-05-11 14:04:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'DropshipWarehouse_MissingVendorOnLine', 'E', 'Dropship warehouse: vendor is missing on the following order line(s) — lines: {0}. Set a vendor on the line, or maintain a default vendor for the product.', 'DROPSHIP_MISSING_VENDOR_ON_LINE');

INSERT INTO AD_Message_Trl (AD_Message_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, MsgText)
VALUES (545679 /*From ID Server*/, 'de_DE', 0, 0, 'Y', TO_TIMESTAMP('2026-05-11 14:04:01', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-05-11 14:04:01', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Streckengeschäft-Lager: Auf folgenden Auftragspositionen fehlt ein Lieferant — Positionen: {0}. Bitte einen Lieferant pro Position setzen oder einen Standard-Lieferant für das Produkt im Bereich „Lieferant" pflegen.');

INSERT INTO AD_Message_Trl (AD_Message_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, MsgText)
VALUES (545679 /*From ID Server*/, 'de_CH', 0, 0, 'Y', TO_TIMESTAMP('2026-05-11 14:04:02', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-05-11 14:04:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Streckengeschäft-Lager: Auf folgenden Auftragspositionen fehlt ein Lieferant — Positionen: {0}. Bitte einen Lieferant pro Position setzen oder einen Standard-Lieferant für das Produkt im Bereich „Lieferant" pflegen.');

INSERT INTO AD_Message_Trl (AD_Message_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, MsgText)
VALUES (545679 /*From ID Server*/, 'en_US', 0, 0, 'Y', TO_TIMESTAMP('2026-05-11 14:04:03', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-05-11 14:04:03', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Dropship warehouse: vendor is missing on the following order line(s) — lines: {0}. Set a vendor on the line, or maintain a default vendor for the product.');

-- Message 2: DropshipWarehouse_NotAllowedDocType (Error)
INSERT INTO AD_Message (AD_Message_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Value, MsgType, MsgText, ErrorCode)
VALUES (545680 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-05-11 14:04:04', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-05-11 14:04:04', 'YYYY-MM-DD HH24:MI:SS'), 100, 'DropshipWarehouse_NotAllowedDocType', 'E', 'Dropship warehouse: this warehouse may only be used with the Standard Sales Order document type. Please pick a different warehouse or a different document type.', 'DROPSHIP_NOT_ALLOWED_DOC_TYPE');

INSERT INTO AD_Message_Trl (AD_Message_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, MsgText)
VALUES (545680 /*From ID Server*/, 'de_DE', 0, 0, 'Y', TO_TIMESTAMP('2026-05-11 14:04:05', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-05-11 14:04:05', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Streckengeschäft-Lager: Dieses Lager darf nur für Standard-Verkaufsaufträge verwendet werden. Bitte ein anderes Lager wählen oder einen anderen Belegtyp.');

INSERT INTO AD_Message_Trl (AD_Message_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, MsgText)
VALUES (545680 /*From ID Server*/, 'de_CH', 0, 0, 'Y', TO_TIMESTAMP('2026-05-11 14:04:06', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-05-11 14:04:06', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Streckengeschäft-Lager: Dieses Lager darf nur für Standard-Verkaufsaufträge verwendet werden. Bitte ein anderes Lager wählen oder einen anderen Belegtyp.');

INSERT INTO AD_Message_Trl (AD_Message_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, MsgText)
VALUES (545680 /*From ID Server*/, 'en_US', 0, 0, 'Y', TO_TIMESTAMP('2026-05-11 14:04:07', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-05-11 14:04:07', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Dropship warehouse: this warehouse may only be used with the Standard Sales Order document type. Please pick a different warehouse or a different document type.');

-- Message 3: DropshipWarehouse_POCreated (Information)
INSERT INTO AD_Message (AD_Message_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Value, MsgType, MsgText, ErrorCode)
VALUES (545681 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-05-11 14:04:08', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-05-11 14:04:08', 'YYYY-MM-DD HH24:MI:SS'), 100, 'DropshipWarehouse_POCreated', 'I', 'Dropship warehouse: purchase order {0} for vendor {1} has been created automatically.', NULL);

INSERT INTO AD_Message_Trl (AD_Message_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, MsgText)
VALUES (545681 /*From ID Server*/, 'de_DE', 0, 0, 'Y', TO_TIMESTAMP('2026-05-11 14:04:09', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-05-11 14:04:09', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Streckengeschäft-Lager: Bestellung {0} an Lieferant {1} wurde automatisch erzeugt.');

INSERT INTO AD_Message_Trl (AD_Message_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, MsgText)
VALUES (545681 /*From ID Server*/, 'de_CH', 0, 0, 'Y', TO_TIMESTAMP('2026-05-11 14:04:10', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-05-11 14:04:10', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Streckengeschäft-Lager: Bestellung {0} an Lieferant {1} wurde automatisch erzeugt.');

INSERT INTO AD_Message_Trl (AD_Message_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, MsgText)
VALUES (545681 /*From ID Server*/, 'en_US', 0, 0, 'Y', TO_TIMESTAMP('2026-05-11 14:04:11', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-05-11 14:04:11', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Dropship warehouse: purchase order {0} for vendor {1} has been created automatically.');
