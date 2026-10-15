-- Boni refund engine: additional C_Flatrate_RefundConfig settings + packaging-option child table.
--
-- Additive only: the existing C_Flatrate_RefundConfig.M_Product_ID keeps its meaning.
--   Bonus_Product_ID                the product whose tax and accounts the refund (credit memo) line uses
--   M_Product_Category_ID           the base category: only lines of this category (incl. its sub-categories) are summed up
--   BonusRecipient                  who receives the bonus: invoice partner (default, existing behaviour) or shipment partner
--   IsPackingOptionFiltered         if 'Y', only lines whose packaging is listed in C_Flatrate_RefundConfig_PackingOption get the bonus
--   C_Flatrate_RefundConfig_PackingOption  the set of packing materials (packaging granularity: M_HU_PackingMaterial) of a refund config
-- No window / tab / field changes in this script.
--
-- IDs allocated from idserver.metas.de on 2026-10-05:
--   AD_Reference  542145  (BonusRecipient list)
--   AD_Ref_List   544374, 544375
--   AD_Element    585512 (Bonus_Product_ID), 585513 (BonusRecipient), 585514 (IsPackingOptionFiltered), 585515 (C_Flatrate_RefundConfig_PackingOption_ID)
--   AD_Table      542654  (C_Flatrate_RefundConfig_PackingOption)
--   AD_Column     593696..593709
-- Existing elements reused: M_Product_Category_ID, C_Flatrate_RefundConfig_ID, M_HU_PackingMaterial_ID

-- 1) Reference list BonusRecipient
INSERT INTO AD_Reference (AD_Reference_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                          Name, ValidationType, IsOrderByValue, EntityType)
VALUES (542145 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-05 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-05 10:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'BonusRecipient', 'L', 'N', 'de.metas.contracts')
;
INSERT INTO AD_Reference_Trl (AD_Language, AD_Reference_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Reference_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Reference t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Reference_ID = 542145
  AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Reference_ID = t.AD_Reference_ID)
;
UPDATE AD_Reference_Trl
SET Name = 'Bonus recipient', IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-05 10:00:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Reference_ID = 542145 AND AD_Language = 'en_US'
;
UPDATE AD_Reference_Trl
SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-05 10:00:04', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Reference_ID = 542145 AND AD_Language IN ('de_DE', 'de_CH')
;
INSERT INTO AD_Ref_List (AD_Ref_List_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                         AD_Reference_ID, EntityType, Value, ValueName, Name)
VALUES (544375 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-05 10:00:05', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-05 10:00:06', 'YYYY-MM-DD HH24:MI:SS'), 100,
        542145, 'de.metas.contracts', 'I', 'InvoicePartner', 'Rechnungspartner')
;
INSERT INTO AD_Ref_List_Trl (AD_Language, AD_Ref_List_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Ref_List_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Ref_List_ID = 544375
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Ref_List_ID = t.AD_Ref_List_ID)
;
UPDATE AD_Ref_List_Trl
SET Name = 'Invoice partner', IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-05 10:00:07', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Ref_List_ID = 544375 AND AD_Language = 'en_US'
;
UPDATE AD_Ref_List_Trl
SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-05 10:00:08', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Ref_List_ID = 544375 AND AD_Language IN ('de_DE', 'de_CH')
;
INSERT INTO AD_Ref_List (AD_Ref_List_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                         AD_Reference_ID, EntityType, Value, ValueName, Name)
VALUES (544374 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-05 10:00:09', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-05 10:00:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
        542145, 'de.metas.contracts', 'S', 'ShipmentPartner', 'Lieferpartner')
;
INSERT INTO AD_Ref_List_Trl (AD_Language, AD_Ref_List_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Ref_List_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Ref_List_ID = 544374
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Ref_List_ID = t.AD_Ref_List_ID)
;
UPDATE AD_Ref_List_Trl
SET Name = 'Shipment partner', IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-05 10:00:11', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Ref_List_ID = 544374 AND AD_Language = 'en_US'
;
UPDATE AD_Ref_List_Trl
SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-05 10:00:12', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Ref_List_ID = 544374 AND AD_Language IN ('de_DE', 'de_CH')
;
-- 2) AD_Elements
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, EntityType, Name, PrintName, Description)
VALUES (585512 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-05 10:00:13', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-05 10:00:14', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'Bonus_Product_ID', 'de.metas.contracts', 'Bonusprodukt', 'Bonusprodukt', 'Produkt der Gutschriftzeile. Steuer und Konten der Gutschrift richten sich nach diesem Produkt.')
;
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 585512
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;
UPDATE AD_Element_Trl
SET Name = 'Bonus product', PrintName = 'Bonus product', Description = 'Product of the credit memo line. The tax and accounts of the credit memo follow this product.', IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-05 10:00:15', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585512 AND AD_Language = 'en_US'
;
UPDATE AD_Element_Trl
SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-05 10:00:16', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585512 AND AD_Language IN ('de_DE', 'de_CH')
;
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, EntityType, Name, PrintName, Description)
VALUES (585513 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-05 10:00:17', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-05 10:00:18', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'BonusRecipient', 'de.metas.contracts', 'Bonusempfänger', 'Bonusempfänger', 'Legt fest, an welchen Partner der Bonus ausgestellt wird.')
;
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 585513
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;
UPDATE AD_Element_Trl
SET Name = 'Bonus recipient', PrintName = 'Bonus recipient', Description = 'Specifies the partner the bonus is issued to.', IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-05 10:00:19', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585513 AND AD_Language = 'en_US'
;
UPDATE AD_Element_Trl
SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-05 10:00:20', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585513 AND AD_Language IN ('de_DE', 'de_CH')
;
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, EntityType, Name, PrintName, Description)
VALUES (585514 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-05 10:00:21', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-05 10:00:22', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'IsPackingOptionFiltered', 'de.metas.contracts', 'Nach Verpackung filtern', 'Nach Verpackung filtern', 'Wenn aktiv, erhalten nur Zeilen mit einer der hinterlegten Verpackungen den Bonus.')
;
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 585514
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;
UPDATE AD_Element_Trl
SET Name = 'Filter by packaging', PrintName = 'Filter by packaging', Description = 'If enabled, only lines with one of the listed packaging options get the bonus.', IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-05 10:00:23', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585514 AND AD_Language = 'en_US'
;
UPDATE AD_Element_Trl
SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-05 10:00:24', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585514 AND AD_Language IN ('de_DE', 'de_CH')
;
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, EntityType, Name, PrintName, Description)
VALUES (585515 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-05 10:00:25', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-05 10:00:26', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'C_Flatrate_RefundConfig_PackingOption_ID', 'de.metas.contracts', 'Rückvergütung Verpackungsoption', 'Rückvergütung Verpackungsoption', 'Verpackung, für die eine Rückvergütungskondition gilt.')
;
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 585515
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;
UPDATE AD_Element_Trl
SET Name = 'Refund packaging option', PrintName = 'Refund packaging option', Description = 'Packaging a refund condition applies to.', IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-05 10:00:27', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585515 AND AD_Language = 'en_US'
;
UPDATE AD_Element_Trl
SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-05 10:00:28', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585515 AND AD_Language IN ('de_DE', 'de_CH')
;
-- 3) New table C_Flatrate_RefundConfig_PackingOption
INSERT INTO AD_Table (AD_Table_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      TableName, Name, AccessLevel, EntityType, IsView, ImportTable, IsChangeLog, ReplicationType,
                      IsSecurityEnabled, IsDeleteable, IsHighVolume, IsAutocomplete, IsDLM, CopyColumnsFromTable,
                      LoadSeq, ACTriggerLength)
VALUES (542654 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-05 10:00:29', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-05 10:00:30', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'C_Flatrate_RefundConfig_PackingOption', 'Rückvergütung Verpackungsoption', 3, 'de.metas.contracts', 'N', 'N', 'Y', 'L',
        'N', 'Y', 'N', 'N', 'N', 'N',
        0, 0)
;
INSERT INTO AD_Table_Trl (AD_Language, AD_Table_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Table t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Table_ID = 542654
  AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Table_ID = t.AD_Table_ID)
;
UPDATE AD_Table_Trl
SET Name = 'Refund packaging option', IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-05 10:00:31', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Table_ID = 542654 AND AD_Language = 'en_US'
;
UPDATE AD_Table_Trl
SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-05 10:00:32', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Table_ID = 542654 AND AD_Language IN ('de_DE', 'de_CH')
;
-- 4) New columns of C_Flatrate_RefundConfig
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID, AD_Reference_Value_ID,
                       FieldLength, Name, Description,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593706 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-05 10:00:33', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-05 10:00:34', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'Bonus_Product_ID', (SELECT AD_Table_ID FROM AD_Table WHERE TableName = 'C_Flatrate_RefundConfig'), (SELECT AD_Element_ID FROM AD_Element WHERE ColumnName = 'Bonus_Product_ID'), 30, 540272,
        10, (SELECT Name FROM AD_Element WHERE ColumnName = 'Bonus_Product_ID'), (SELECT Description FROM AD_Element WHERE ColumnName = 'Bonus_Product_ID'),
        'N', 'Y', 'N', NULL,
        'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT et.AD_Language, c.AD_Column_ID, et.Name, et.IsTranslated, c.AD_Client_ID, c.AD_Org_ID, c.Created, c.CreatedBy, c.Updated, c.UpdatedBy
FROM AD_Column c
         JOIN AD_Element_Trl et ON et.AD_Element_ID = c.AD_Element_ID
WHERE c.AD_Column_ID = 593706
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = et.AD_Language AND tt.AD_Column_ID = c.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID, AD_Reference_Value_ID,
                       FieldLength, Name, Description,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593707 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-05 10:00:35', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-05 10:00:36', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'M_Product_Category_ID', (SELECT AD_Table_ID FROM AD_Table WHERE TableName = 'C_Flatrate_RefundConfig'), (SELECT AD_Element_ID FROM AD_Element WHERE ColumnName = 'M_Product_Category_ID'), 19, NULL,
        10, (SELECT Name FROM AD_Element WHERE ColumnName = 'M_Product_Category_ID'), 'Produktkategorie, auf deren Zeilen sich der Bonus bezieht.',
        'N', 'Y', 'N', NULL,
        'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT et.AD_Language, c.AD_Column_ID, et.Name, et.IsTranslated, c.AD_Client_ID, c.AD_Org_ID, c.Created, c.CreatedBy, c.Updated, c.UpdatedBy
FROM AD_Column c
         JOIN AD_Element_Trl et ON et.AD_Element_ID = c.AD_Element_ID
WHERE c.AD_Column_ID = 593707
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = et.AD_Language AND tt.AD_Column_ID = c.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID, AD_Reference_Value_ID,
                       FieldLength, Name, Description,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593708 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-05 10:00:37', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-05 10:00:38', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'BonusRecipient', (SELECT AD_Table_ID FROM AD_Table WHERE TableName = 'C_Flatrate_RefundConfig'), (SELECT AD_Element_ID FROM AD_Element WHERE ColumnName = 'BonusRecipient'), 17, 542145,
        1, (SELECT Name FROM AD_Element WHERE ColumnName = 'BonusRecipient'), (SELECT Description FROM AD_Element WHERE ColumnName = 'BonusRecipient'),
        'Y', 'Y', 'N', 'I',
        'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT et.AD_Language, c.AD_Column_ID, et.Name, et.IsTranslated, c.AD_Client_ID, c.AD_Org_ID, c.Created, c.CreatedBy, c.Updated, c.UpdatedBy
FROM AD_Column c
         JOIN AD_Element_Trl et ON et.AD_Element_ID = c.AD_Element_ID
WHERE c.AD_Column_ID = 593708
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = et.AD_Language AND tt.AD_Column_ID = c.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID, AD_Reference_Value_ID,
                       FieldLength, Name, Description,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593709 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-05 10:00:39', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-05 10:00:40', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'IsPackingOptionFiltered', (SELECT AD_Table_ID FROM AD_Table WHERE TableName = 'C_Flatrate_RefundConfig'), (SELECT AD_Element_ID FROM AD_Element WHERE ColumnName = 'IsPackingOptionFiltered'), 20, NULL,
        1, (SELECT Name FROM AD_Element WHERE ColumnName = 'IsPackingOptionFiltered'), (SELECT Description FROM AD_Element WHERE ColumnName = 'IsPackingOptionFiltered'),
        'Y', 'Y', 'N', 'N',
        'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT et.AD_Language, c.AD_Column_ID, et.Name, et.IsTranslated, c.AD_Client_ID, c.AD_Org_ID, c.Created, c.CreatedBy, c.Updated, c.UpdatedBy
FROM AD_Column c
         JOIN AD_Element_Trl et ON et.AD_Element_ID = c.AD_Element_ID
WHERE c.AD_Column_ID = 593709
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = et.AD_Language AND tt.AD_Column_ID = c.AD_Column_ID)
;
-- 5) Columns of C_Flatrate_RefundConfig_PackingOption
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID, AD_Reference_Value_ID,
                       FieldLength, Name, Description,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593696 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-05 10:00:41', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-05 10:00:42', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'AD_Client_ID', 542654, (SELECT AD_Element_ID FROM AD_Element WHERE ColumnName = 'AD_Client_ID'), 19, NULL,
        10, 'Mandant', 'Mandant für diese Installation.',
        'Y', 'N', 'N', NULL,
        'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT et.AD_Language, c.AD_Column_ID, et.Name, et.IsTranslated, c.AD_Client_ID, c.AD_Org_ID, c.Created, c.CreatedBy, c.Updated, c.UpdatedBy
FROM AD_Column c
         JOIN AD_Element_Trl et ON et.AD_Element_ID = c.AD_Element_ID
WHERE c.AD_Column_ID = 593696
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = et.AD_Language AND tt.AD_Column_ID = c.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID, AD_Reference_Value_ID,
                       FieldLength, Name, Description,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593697 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-05 10:00:43', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-05 10:00:44', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'AD_Org_ID', 542654, (SELECT AD_Element_ID FROM AD_Element WHERE ColumnName = 'AD_Org_ID'), 30, NULL,
        10, 'Sektion', 'Organisatorische Einheit des Mandanten',
        'Y', 'N', 'N', NULL,
        'N', 'N', 'N', 'N', 'N', 'Y',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT et.AD_Language, c.AD_Column_ID, et.Name, et.IsTranslated, c.AD_Client_ID, c.AD_Org_ID, c.Created, c.CreatedBy, c.Updated, c.UpdatedBy
FROM AD_Column c
         JOIN AD_Element_Trl et ON et.AD_Element_ID = c.AD_Element_ID
WHERE c.AD_Column_ID = 593697
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = et.AD_Language AND tt.AD_Column_ID = c.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID, AD_Reference_Value_ID,
                       FieldLength, Name, Description,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593698 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-05 10:00:45', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-05 10:00:46', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'Created', 542654, (SELECT AD_Element_ID FROM AD_Element WHERE ColumnName = 'Created'), 16, NULL,
        29, 'Erstellt', 'Datum, an dem dieser Eintrag erstellt wurde',
        'Y', 'N', 'N', NULL,
        'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT et.AD_Language, c.AD_Column_ID, et.Name, et.IsTranslated, c.AD_Client_ID, c.AD_Org_ID, c.Created, c.CreatedBy, c.Updated, c.UpdatedBy
FROM AD_Column c
         JOIN AD_Element_Trl et ON et.AD_Element_ID = c.AD_Element_ID
WHERE c.AD_Column_ID = 593698
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = et.AD_Language AND tt.AD_Column_ID = c.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID, AD_Reference_Value_ID,
                       FieldLength, Name, Description,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593699 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-05 10:00:47', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-05 10:00:48', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'CreatedBy', 542654, (SELECT AD_Element_ID FROM AD_Element WHERE ColumnName = 'CreatedBy'), 18, NULL,
        10, 'Erstellt durch', 'Nutzer, der diesen Eintrag erstellt hat',
        'Y', 'N', 'N', NULL,
        'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT et.AD_Language, c.AD_Column_ID, et.Name, et.IsTranslated, c.AD_Client_ID, c.AD_Org_ID, c.Created, c.CreatedBy, c.Updated, c.UpdatedBy
FROM AD_Column c
         JOIN AD_Element_Trl et ON et.AD_Element_ID = c.AD_Element_ID
WHERE c.AD_Column_ID = 593699
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = et.AD_Language AND tt.AD_Column_ID = c.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID, AD_Reference_Value_ID,
                       FieldLength, Name, Description,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593700 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-05 10:00:49', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-05 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'IsActive', 542654, (SELECT AD_Element_ID FROM AD_Element WHERE ColumnName = 'IsActive'), 20, NULL,
        1, 'Aktiv', 'Der Eintrag ist im System aktiv',
        'Y', 'Y', 'N', 'Y',
        'N', 'N', 'N', 'N', 'N', 'Y',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT et.AD_Language, c.AD_Column_ID, et.Name, et.IsTranslated, c.AD_Client_ID, c.AD_Org_ID, c.Created, c.CreatedBy, c.Updated, c.UpdatedBy
FROM AD_Column c
         JOIN AD_Element_Trl et ON et.AD_Element_ID = c.AD_Element_ID
WHERE c.AD_Column_ID = 593700
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = et.AD_Language AND tt.AD_Column_ID = c.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID, AD_Reference_Value_ID,
                       FieldLength, Name, Description,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593701 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-05 10:00:51', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-05 10:00:52', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'Updated', 542654, (SELECT AD_Element_ID FROM AD_Element WHERE ColumnName = 'Updated'), 16, NULL,
        29, 'Aktualisiert', 'Datum, an dem dieser Eintrag aktualisiert wurde',
        'Y', 'N', 'N', NULL,
        'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT et.AD_Language, c.AD_Column_ID, et.Name, et.IsTranslated, c.AD_Client_ID, c.AD_Org_ID, c.Created, c.CreatedBy, c.Updated, c.UpdatedBy
FROM AD_Column c
         JOIN AD_Element_Trl et ON et.AD_Element_ID = c.AD_Element_ID
WHERE c.AD_Column_ID = 593701
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = et.AD_Language AND tt.AD_Column_ID = c.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID, AD_Reference_Value_ID,
                       FieldLength, Name, Description,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593702 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-05 10:00:53', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-05 10:00:54', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'UpdatedBy', 542654, (SELECT AD_Element_ID FROM AD_Element WHERE ColumnName = 'UpdatedBy'), 18, NULL,
        10, 'Aktualisiert durch', 'Nutzer, der diesen Eintrag aktualisiert hat',
        'Y', 'N', 'N', NULL,
        'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT et.AD_Language, c.AD_Column_ID, et.Name, et.IsTranslated, c.AD_Client_ID, c.AD_Org_ID, c.Created, c.CreatedBy, c.Updated, c.UpdatedBy
FROM AD_Column c
         JOIN AD_Element_Trl et ON et.AD_Element_ID = c.AD_Element_ID
WHERE c.AD_Column_ID = 593702
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = et.AD_Language AND tt.AD_Column_ID = c.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID, AD_Reference_Value_ID,
                       FieldLength, Name, Description,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593703 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-05 10:00:55', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-05 10:00:56', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'C_Flatrate_RefundConfig_PackingOption_ID', 542654, 585515, 13, NULL,
        10, (SELECT Name FROM AD_Element WHERE ColumnName = 'C_Flatrate_RefundConfig_PackingOption_ID'), (SELECT Description FROM AD_Element WHERE ColumnName = 'C_Flatrate_RefundConfig_PackingOption_ID'),
        'Y', 'N', 'N', NULL,
        'Y', 'N', 'N', 'N', 'N', 'N',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT et.AD_Language, c.AD_Column_ID, et.Name, et.IsTranslated, c.AD_Client_ID, c.AD_Org_ID, c.Created, c.CreatedBy, c.Updated, c.UpdatedBy
FROM AD_Column c
         JOIN AD_Element_Trl et ON et.AD_Element_ID = c.AD_Element_ID
WHERE c.AD_Column_ID = 593703
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = et.AD_Language AND tt.AD_Column_ID = c.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID, AD_Reference_Value_ID,
                       FieldLength, Name, Description,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593704 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-05 10:00:57', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-05 10:00:58', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'C_Flatrate_RefundConfig_ID', 542654, (SELECT AD_Element_ID FROM AD_Element WHERE ColumnName = 'C_Flatrate_RefundConfig_ID'), 19, NULL,
        10, (SELECT Name FROM AD_Element WHERE ColumnName = 'C_Flatrate_RefundConfig_ID'), 'Rückvergütungskondition, zu der diese Verpackungsoption gehört.',
        'Y', 'N', 'N', NULL,
        'N', 'Y', 'N', 'N', 'N', 'N',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT et.AD_Language, c.AD_Column_ID, et.Name, et.IsTranslated, c.AD_Client_ID, c.AD_Org_ID, c.Created, c.CreatedBy, c.Updated, c.UpdatedBy
FROM AD_Column c
         JOIN AD_Element_Trl et ON et.AD_Element_ID = c.AD_Element_ID
WHERE c.AD_Column_ID = 593704
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = et.AD_Language AND tt.AD_Column_ID = c.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID, AD_Reference_Value_ID,
                       FieldLength, Name, Description,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593705 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-05 10:00:59', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-05 10:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'M_HU_PackingMaterial_ID', 542654, (SELECT AD_Element_ID FROM AD_Element WHERE ColumnName = 'M_HU_PackingMaterial_ID'), 19, NULL,
        10, (SELECT Name FROM AD_Element WHERE ColumnName = 'M_HU_PackingMaterial_ID'), 'Verpackungsmaterial (z. B. Karton, Kiste), für das der Bonus gilt.',
        'Y', 'Y', 'N', NULL,
        'N', 'N', 'N', 'Y', 'N', 'N',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT et.AD_Language, c.AD_Column_ID, et.Name, et.IsTranslated, c.AD_Client_ID, c.AD_Org_ID, c.Created, c.CreatedBy, c.Updated, c.UpdatedBy
FROM AD_Column c
         JOIN AD_Element_Trl et ON et.AD_Element_ID = c.AD_Element_ID
WHERE c.AD_Column_ID = 593705
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = et.AD_Language AND tt.AD_Column_ID = c.AD_Column_ID)
;
-- 6) Physical DDL
SELECT public.db_alter_table('C_Flatrate_RefundConfig', 'ALTER TABLE public.C_Flatrate_RefundConfig ADD COLUMN Bonus_Product_ID NUMERIC(10)')
;
SELECT public.db_alter_table('C_Flatrate_RefundConfig', 'ALTER TABLE public.C_Flatrate_RefundConfig ADD COLUMN M_Product_Category_ID NUMERIC(10)')
;
SELECT public.db_alter_table('C_Flatrate_RefundConfig', 'ALTER TABLE public.C_Flatrate_RefundConfig ADD COLUMN BonusRecipient CHAR(1) DEFAULT ''I'' NOT NULL')
;
SELECT public.db_alter_table('C_Flatrate_RefundConfig', 'ALTER TABLE public.C_Flatrate_RefundConfig ADD COLUMN IsPackingOptionFiltered CHAR(1) DEFAULT ''N'' NOT NULL')
;
ALTER TABLE C_Flatrate_RefundConfig
    ADD CONSTRAINT BonusRecipient_Check CHECK (BonusRecipient IN ('I', 'S'))
;
ALTER TABLE C_Flatrate_RefundConfig
    ADD CONSTRAINT IsPackingOptionFiltered_Check CHECK (IsPackingOptionFiltered IN ('Y', 'N'))
;
ALTER TABLE C_Flatrate_RefundConfig
    ADD CONSTRAINT BonusProduct_CFlatrateRefundConfig FOREIGN KEY (Bonus_Product_ID) REFERENCES public.M_Product DEFERRABLE INITIALLY DEFERRED
;
ALTER TABLE C_Flatrate_RefundConfig
    ADD CONSTRAINT MProductCategory_CFlatrateRefundConfig FOREIGN KEY (M_Product_Category_ID) REFERENCES public.M_Product_Category DEFERRABLE INITIALLY DEFERRED
;
CREATE TABLE public.C_Flatrate_RefundConfig_PackingOption (
    AD_Client_ID NUMERIC(10) NOT NULL,
    AD_Org_ID NUMERIC(10) NOT NULL,
    C_Flatrate_RefundConfig_ID NUMERIC(10) NOT NULL,
    C_Flatrate_RefundConfig_PackingOption_ID NUMERIC(10) NOT NULL,
    Created TIMESTAMP WITH TIME ZONE NOT NULL,
    CreatedBy NUMERIC(10) NOT NULL,
    IsActive CHAR(1) DEFAULT 'Y' CHECK (IsActive IN ('Y','N')) NOT NULL,
    M_HU_PackingMaterial_ID NUMERIC(10) NOT NULL,
    Updated TIMESTAMP WITH TIME ZONE NOT NULL,
    UpdatedBy NUMERIC(10) NOT NULL,
    CONSTRAINT C_Flatrate_RefundConfig_PackingOption_Key PRIMARY KEY (C_Flatrate_RefundConfig_PackingOption_ID),
    CONSTRAINT CFlatrateRefundConfig_CFlatrateRefundConfigPackingOption FOREIGN KEY (C_Flatrate_RefundConfig_ID) REFERENCES public.C_Flatrate_RefundConfig DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT MHUPackingMaterial_CFlatrateRefundConfigPackingOption FOREIGN KEY (M_HU_PackingMaterial_ID) REFERENCES public.M_HU_PackingMaterial DEFERRABLE INITIALLY DEFERRED
)
;
CREATE UNIQUE INDEX c_flatrate_refundconfig_packingoption_active_uq
    ON public.C_Flatrate_RefundConfig_PackingOption (C_Flatrate_RefundConfig_ID, M_HU_PackingMaterial_ID)
    WHERE IsActive = 'Y'
;
-- 7) Propagate the new elements' translations to their columns
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585512)
;
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585513)
;
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585514)
;
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585515)
;
