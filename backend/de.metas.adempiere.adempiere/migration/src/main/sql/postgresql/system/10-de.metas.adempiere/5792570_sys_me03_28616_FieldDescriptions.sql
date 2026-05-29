-- me03#28616: Add descriptive AD_Element for M_Product_ID fields affected by freight cost val rule
-- Since AD_Element_ID=454 (M_Product_ID) is global (used in 100+ places), we create a separate
-- element and use AD_Name_ID on the affected fields. This prevents element propagation from
-- overwriting the field-level descriptions.

-- 1. Create new AD_Element (base language = de_DE)
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, EntityType, Name, PrintName, Description, Help)
VALUES (584628, 0, 0, 'Y', '2026-03-06 12:30', 100, '2026-03-06 12:30', 100,
        'M_Product_NoFreight_ID', 'D',
        'Produkt',
        'Produkt',
        'Produkt (nur aktive Produkte mit Preisen in der Preisliste; aktive Frachtkostenprodukte werden ausgeschlossen)',
        'Zeigt nur Produkte an, die: aktiv sind, Preise in der zutreffenden Preislistenversion haben, nicht als aktive Frachtkostenprodukte verwendet werden und zur Organisation gehoeren.');

-- 2. Add translations
-- de_CH (same as base)
INSERT INTO AD_Element_Trl (AD_Element_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                            IsTranslated, Name, PrintName, Description, Help)
VALUES (584628, 'de_CH', 0, 0, 'Y', '2026-03-06 12:30', 100, '2026-03-06 12:30', 100,
        'Y',
        'Produkt',
        'Produkt',
        'Produkt (nur aktive Produkte mit Preisen in der Preisliste; aktive Frachtkostenprodukte werden ausgeschlossen)',
        'Zeigt nur Produkte an, die: aktiv sind, Preise in der zutreffenden Preislistenversion haben, nicht als aktive Frachtkostenprodukte verwendet werden und zur Organisation gehoeren.');

-- de_DE
INSERT INTO AD_Element_Trl (AD_Element_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                            IsTranslated, Name, PrintName, Description, Help)
VALUES (584628, 'de_DE', 0, 0, 'Y', '2026-03-06 12:30', 100, '2026-03-06 12:30', 100,
        'Y',
        'Produkt',
        'Produkt',
        'Produkt (nur aktive Produkte mit Preisen in der Preisliste; aktive Frachtkostenprodukte werden ausgeschlossen)',
        'Zeigt nur Produkte an, die: aktiv sind, Preise in der zutreffenden Preislistenversion haben, nicht als aktive Frachtkostenprodukte verwendet werden und zur Organisation gehoeren.');

-- en_US
INSERT INTO AD_Element_Trl (AD_Element_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                            IsTranslated, Name, PrintName, Description, Help)
VALUES (584628, 'en_US', 0, 0, 'Y', '2026-03-06 12:30', 100, '2026-03-06 12:30', 100,
        'Y',
        'Product',
        'Product',
        'Product (only active products with prices in the price list; active freight cost products are excluded)',
        'Only shows products that: are active, have prices in the applicable price list version, are not used as active freight cost products, and belong to the organization.');

-- 3. Set AD_Name_ID on affected fields (active windows only, skip _OLD windows)
-- This makes these fields use the new element for Name/Description/Help instead of the global element 454

-- Auftrag header (C_Order.M_Product_ID)
UPDATE AD_Field SET AD_Name_ID=584628, Updated='2026-03-06 12:29', UpdatedBy=100 WHERE AD_Field_ID=637303;
-- Auftrag line (C_OrderLine.M_Product_ID)
UPDATE AD_Field SET AD_Name_ID=584628, Updated='2026-03-06 12:29', UpdatedBy=100 WHERE AD_Field_ID=637380;
-- B2B Auftrag header
UPDATE AD_Field SET AD_Name_ID=584628, Updated='2026-03-06 12:29', UpdatedBy=100 WHERE AD_Field_ID=544901;
-- B2B Auftrag line
UPDATE AD_Field SET AD_Name_ID=584628, Updated='2026-03-06 12:29', UpdatedBy=100 WHERE AD_Field_ID=544967;
-- Bedarf line
UPDATE AD_Field SET AD_Name_ID=584628, Updated='2026-03-06 12:29', UpdatedBy=100 WHERE AD_Field_ID=12464;
-- Bestellung header
UPDATE AD_Field SET AD_Name_ID=584628, Updated='2026-03-06 12:29', UpdatedBy=100 WHERE AD_Field_ID=638088;
-- Bestellung line
UPDATE AD_Field SET AD_Name_ID=584628, Updated='2026-03-06 12:29', UpdatedBy=100 WHERE AD_Field_ID=638147;
-- Vertreterinfo line
UPDATE AD_Field SET AD_Name_ID=584628, Updated='2026-03-06 12:29', UpdatedBy=100 WHERE AD_Field_ID=8421;
