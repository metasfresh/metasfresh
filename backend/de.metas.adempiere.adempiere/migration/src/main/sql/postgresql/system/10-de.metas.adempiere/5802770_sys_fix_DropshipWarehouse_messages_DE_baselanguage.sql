-- Fix AD_Message records 545679, 545680, 545681, 545687 to follow the metasfresh convention:
-- base AD_Message.MsgText holds German, AD_Message_Trl for en_US holds English.
-- Earlier scripts (5801760, 5802640, 5802720) had this inverted -- this UPDATE brings
-- already-applied dev DBs in sync with the amended scripts. Idempotent for fresh DBs.
-- Created: 2026-05-15

-- 545679 DropshipWarehouse_MissingVendorOnLine
UPDATE AD_Message
SET MsgText   = 'Streckengeschäft-Lager: Auf folgenden Auftragspositionen fehlt ein Lieferant — Positionen: {0}. Bitte einen Lieferant pro Position setzen oder einen Standard-Lieferant für das Produkt im Bereich „Lieferant" pflegen.',
    Updated   = TO_TIMESTAMP('2026-05-15 10:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Message_ID = 545679
;

UPDATE AD_Message_Trl
SET MsgText   = 'Dropship warehouse: vendor is missing on the following order line(s) — lines: {0}. Set a vendor on the line, or maintain a default vendor for the product.',
    Updated   = TO_TIMESTAMP('2026-05-15 10:00:01', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Message_ID = 545679 AND AD_Language = 'en_US'
;

-- 545680 DropshipWarehouse_NotAllowedDocType
UPDATE AD_Message
SET MsgText   = 'Streckengeschäft-Lager: Dieses Lager darf nur für Standard-Verkaufsaufträge verwendet werden. Bitte ein anderes Lager wählen oder einen anderen Belegtyp.',
    Updated   = TO_TIMESTAMP('2026-05-15 10:00:02', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Message_ID = 545680
;

UPDATE AD_Message_Trl
SET MsgText   = 'Dropship warehouse: this warehouse may only be used with the Standard Sales Order document type. Please pick a different warehouse or a different document type.',
    Updated   = TO_TIMESTAMP('2026-05-15 10:00:03', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Message_ID = 545680 AND AD_Language = 'en_US'
;

-- 545681 DropshipWarehouse_POCreated
UPDATE AD_Message
SET MsgText   = 'Streckengeschäft-Lager: Bestellung {0} an Lieferant {1} wurde automatisch erzeugt.',
    Updated   = TO_TIMESTAMP('2026-05-15 10:00:04', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Message_ID = 545681
;

UPDATE AD_Message_Trl
SET MsgText   = 'Dropship warehouse: purchase order {0} for vendor {1} has been created automatically.',
    Updated   = TO_TIMESTAMP('2026-05-15 10:00:05', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Message_ID = 545681 AND AD_Language = 'en_US'
;

-- 545687 DropShipWarehouse_NotFlagged
UPDATE AD_Message
SET MsgText   = 'Das gewählte Lager muss als ''Dropship-Lager'' markiert sein.',
    Updated   = TO_TIMESTAMP('2026-05-15 10:00:06', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Message_ID = 545687
;

UPDATE AD_Message_Trl
SET MsgText   = 'The selected warehouse must be marked as ''Dropship warehouse''.',
    Updated   = TO_TIMESTAMP('2026-05-15 10:00:07', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Message_ID = 545687 AND AD_Language = 'en_US'
;
