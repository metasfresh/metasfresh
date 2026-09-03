-- AD_Messages for packaging licensing report column header translations.
-- ExportToSpreadsheetProcess uses IMsgBL.translatable() which checks AD_Message first,
-- then AD_Element. These messages ensure all report columns get clean German/English headers.

-- ProductValue
INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Value, Updated, UpdatedBy)
VALUES (0, 545650 /*From ID Server*/, 0, '2026-04-09 15:00', 0, 'D', 'Y', 'Produktnummer', 'I', 'ProductValue', '2026-04-09 15:00', 0);
INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, 545650, m.MsgText, m.MsgTip, 'N', 0, 0, m.Created, 0, m.Updated, 0, 'Y'
FROM AD_Language l, AD_Message m WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND m.AD_Message_ID=545650
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl t WHERE t.AD_Language=l.AD_Language AND t.AD_Message_ID=545650);
UPDATE AD_Message_Trl SET MsgText='Product No.', IsTranslated='Y', Updated='2026-04-09 15:00', UpdatedBy=0 WHERE AD_Message_ID=545650 AND AD_Language='en_US';

-- ProductName
INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Value, Updated, UpdatedBy)
VALUES (0, 545651 /*From ID Server*/, 0, '2026-04-09 15:00', 0, 'D', 'Y', 'Produktname', 'I', 'ProductName', '2026-04-09 15:00', 0);
INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, 545651, m.MsgText, m.MsgTip, 'N', 0, 0, m.Created, 0, m.Updated, 0, 'Y'
FROM AD_Language l, AD_Message m WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND m.AD_Message_ID=545651
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl t WHERE t.AD_Language=l.AD_Language AND t.AD_Message_ID=545651);
UPDATE AD_Message_Trl SET MsgText='Product Name', IsTranslated='Y', Updated='2026-04-09 15:00', UpdatedBy=0 WHERE AD_Message_ID=545651 AND AD_Language='en_US';

-- PurchaseQty
INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Value, Updated, UpdatedBy)
VALUES (0, 545652 /*From ID Server*/, 0, '2026-04-09 15:00', 0, 'D', 'Y', 'Einkaufsmenge', 'I', 'PurchaseQty', '2026-04-09 15:00', 0);
INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, 545652, m.MsgText, m.MsgTip, 'N', 0, 0, m.Created, 0, m.Updated, 0, 'Y'
FROM AD_Language l, AD_Message m WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND m.AD_Message_ID=545652
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl t WHERE t.AD_Language=l.AD_Language AND t.AD_Message_ID=545652);
UPDATE AD_Message_Trl SET MsgText='Purchase Qty', IsTranslated='Y', Updated='2026-04-09 15:00', UpdatedBy=0 WHERE AD_Message_ID=545652 AND AD_Language='en_US';

-- ForeignSalesQty
INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Value, Updated, UpdatedBy)
VALUES (0, 545653 /*From ID Server*/, 0, '2026-04-09 15:00', 0, 'D', 'Y', 'Auslandsverkaufsmenge', 'I', 'ForeignSalesQty', '2026-04-09 15:00', 0);
INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, 545653, m.MsgText, m.MsgTip, 'N', 0, 0, m.Created, 0, m.Updated, 0, 'Y'
FROM AD_Language l, AD_Message m WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND m.AD_Message_ID=545653
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl t WHERE t.AD_Language=l.AD_Language AND t.AD_Message_ID=545653);
UPDATE AD_Message_Trl SET MsgText='Foreign Sales Qty', IsTranslated='Y', Updated='2026-04-09 15:00', UpdatedBy=0 WHERE AD_Message_ID=545653 AND AD_Language='en_US';

-- TotalSalesQty
INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Value, Updated, UpdatedBy)
VALUES (0, 545654 /*From ID Server*/, 0, '2026-04-09 15:00', 0, 'D', 'Y', 'Gesamtverkaufsmenge', 'I', 'TotalSalesQty', '2026-04-09 15:00', 0);
INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, 545654, m.MsgText, m.MsgTip, 'N', 0, 0, m.Created, 0, m.Updated, 0, 'Y'
FROM AD_Language l, AD_Message m WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND m.AD_Message_ID=545654
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl t WHERE t.AD_Language=l.AD_Language AND t.AD_Message_ID=545654);
UPDATE AD_Message_Trl SET MsgText='Total Sales Qty', IsTranslated='Y', Updated='2026-04-09 15:00', UpdatedBy=0 WHERE AD_Message_ID=545654 AND AD_Language='en_US';

-- MaterialType
INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Value, Updated, UpdatedBy)
VALUES (0, 545655 /*From ID Server*/, 0, '2026-04-09 15:00', 0, 'D', 'Y', 'Materialart', 'I', 'MaterialType', '2026-04-09 15:00', 0);
INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, 545655, m.MsgText, m.MsgTip, 'N', 0, 0, m.Created, 0, m.Updated, 0, 'Y'
FROM AD_Language l, AD_Message m WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND m.AD_Message_ID=545655
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl t WHERE t.AD_Language=l.AD_Language AND t.AD_Message_ID=545655);
UPDATE AD_Message_Trl SET MsgText='Material Type', IsTranslated='Y', Updated='2026-04-09 15:00', UpdatedBy=0 WHERE AD_Message_ID=545655 AND AD_Language='en_US';

-- VendorName
INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Value, Updated, UpdatedBy)
VALUES (0, 545656 /*From ID Server*/, 0, '2026-04-09 15:00', 0, 'D', 'Y', 'Lieferant', 'I', 'VendorName', '2026-04-09 15:00', 0);
INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, 545656, m.MsgText, m.MsgTip, 'N', 0, 0, m.Created, 0, m.Updated, 0, 'Y'
FROM AD_Language l, AD_Message m WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND m.AD_Message_ID=545656
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl t WHERE t.AD_Language=l.AD_Language AND t.AD_Message_ID=545656);
UPDATE AD_Message_Trl SET MsgText='Vendor', IsTranslated='Y', Updated='2026-04-09 15:00', UpdatedBy=0 WHERE AD_Message_ID=545656 AND AD_Language='en_US';

-- VendorCountryCode
INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Value, Updated, UpdatedBy)
VALUES (0, 545657 /*From ID Server*/, 0, '2026-04-09 15:00', 0, 'D', 'Y', 'Lieferantenland', 'I', 'VendorCountryCode', '2026-04-09 15:00', 0);
INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, 545657, m.MsgText, m.MsgTip, 'N', 0, 0, m.Created, 0, m.Updated, 0, 'Y'
FROM AD_Language l, AD_Message m WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND m.AD_Message_ID=545657
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl t WHERE t.AD_Language=l.AD_Language AND t.AD_Message_ID=545657);
UPDATE AD_Message_Trl SET MsgText='Vendor Country', IsTranslated='Y', Updated='2026-04-09 15:00', UpdatedBy=0 WHERE AD_Message_ID=545657 AND AD_Language='en_US';

-- IsVendorPackageLicensingExempt
INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Value, Updated, UpdatedBy)
VALUES (0, 545658 /*From ID Server — reusing next sequential*/, 0, '2026-04-09 15:00', 0, 'D', 'Y', 'Lieferant befreit', 'I', 'IsVendorPackageLicensingExempt', '2026-04-09 15:00', 0);
INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, 545658, m.MsgText, m.MsgTip, 'N', 0, 0, m.Created, 0, m.Updated, 0, 'Y'
FROM AD_Language l, AD_Message m WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND m.AD_Message_ID=545658
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl t WHERE t.AD_Language=l.AD_Language AND t.AD_Message_ID=545658);
UPDATE AD_Message_Trl SET MsgText='Vendor Exempt', IsTranslated='Y', Updated='2026-04-09 15:00', UpdatedBy=0 WHERE AD_Message_ID=545658 AND AD_Language='en_US';
