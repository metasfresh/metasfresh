-- gh#28336: Create Intrastat window with detail view for debugging intra-EU trade data.
-- Window shows non-aggregated invoice/shipment line combinations so users can
-- zoom into shipments, invoices, products, partners to fix issues like zero amounts.

-- =====================================================================
-- 1. New AD_Elements
-- =====================================================================

-- 1a. Intrastat_Report_Detail_V_ID (PK element)
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    ColumnName, EntityType, Name, PrintName)
VALUES (584663 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    'Intrastat_Report_Detail_V_ID', 'D', 'Intrastat Detail', 'Intrastat Detail');

INSERT INTO AD_Element_Trl (AD_Element_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy, IsTranslated,
    Name, PrintName)
VALUES (584663 /*From ID Server*/, 'en_US', 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, 'Y',
    'Intrastat Detail', 'Intrastat Detail');

-- 1b. CommodityNumber (display column)
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    ColumnName, EntityType, Name, PrintName)
VALUES (584664 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    'CommodityNumber', 'D', 'Warennummer', 'Warennummer');

INSERT INTO AD_Element_Trl (AD_Element_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy, IsTranslated,
    Name, PrintName)
VALUES (584664 /*From ID Server*/, 'en_US', 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, 'Y',
    'Commodity Number', 'Commodity Number');

-- 1c. DeliveredFromCountry
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    ColumnName, EntityType, Name, PrintName)
VALUES (584665 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    'DeliveredFromCountry', 'D', 'Versandland', 'Versandland');

INSERT INTO AD_Element_Trl (AD_Element_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy, IsTranslated,
    Name, PrintName)
VALUES (584665 /*From ID Server*/, 'en_US', 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, 'Y',
    'Delivered From Country', 'Delivered From Country');

-- 1f. DeliveryCountry
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    ColumnName, EntityType, Name, PrintName)
VALUES (584666 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    'DeliveryCountry', 'D', 'Bestimmungsland', 'Bestimmungsland');

INSERT INTO AD_Element_Trl (AD_Element_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy, IsTranslated,
    Name, PrintName)
VALUES (584666 /*From ID Server*/, 'en_US', 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, 'Y',
    'Delivery Country', 'Delivery Country');

-- 1e. Intrastat (for AD_Window and AD_Tab — both require AD_Element_ID NOT NULL)
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    ColumnName, EntityType, Name, PrintName)
VALUES (584668 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    'Intrastat', 'D', 'Intrastat', 'Intrastat');

INSERT INTO AD_Element_Trl (AD_Element_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy, IsTranslated,
    Name, PrintName)
VALUES (584668 /*From ID Server*/, 'en_US', 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, 'Y',
    'Intrastat', 'Intrastat');

-- 1g. OriginCountry
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    ColumnName, EntityType, Name, PrintName)
VALUES (584667 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    'OriginCountry', 'D', 'Ursprungsland', 'Ursprungsland');

INSERT INTO AD_Element_Trl (AD_Element_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy, IsTranslated,
    Name, PrintName)
VALUES (584667 /*From ID Server*/, 'en_US', 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, 'Y',
    'Origin Country', 'Origin Country');

-- =====================================================================
-- 2. AD_Table: Intrastat_Report_Detail_V
-- =====================================================================
INSERT INTO AD_Table (AD_Table_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, TableName, EntityType, AccessLevel,
    IsView, IsHighVolume, IsChangeLog,
    IsDeleteable, IsSecurityEnabled,
    PersonalDataCategory)
VALUES (542587 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    'Intrastat_Report_Detail_V', 'Intrastat_Report_Detail_V', 'D', '3',
    'Y', 'Y', 'N',
    'N', 'N',
    'NP');

-- =====================================================================
-- 3. AD_Columns (27 columns)
-- =====================================================================

-- Col 1: Intrastat_Report_Detail_V_ID (PK)
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, AD_Table_ID, AD_Reference_ID,
    ColumnName, Name, EntityType,
    IsKey, IsMandatory, IsUpdateable, IsParent,
    IsSelectionColumn, IsIdentifier,
    FieldLength, Version, PersonalDataCategory)
VALUES (592215 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    584663, 542587, 13,
    'Intrastat_Report_Detail_V_ID', 'Intrastat Detail', 'D',
    'Y', 'Y', 'N', 'N',
    'N', 'N',
    10, 0, 'NP');

-- Col 2: AD_Client_ID
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, AD_Table_ID, AD_Reference_ID,
    ColumnName, Name, EntityType,
    IsKey, IsMandatory, IsUpdateable, IsParent,
    IsSelectionColumn, IsIdentifier,
    FieldLength, Version, PersonalDataCategory)
VALUES (592216 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    102, 542587, 19,
    'AD_Client_ID', 'Mandant', 'D',
    'N', 'Y', 'N', 'N',
    'N', 'N',
    10, 0, 'NP');

-- Col 3: AD_Org_ID
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, AD_Table_ID, AD_Reference_ID,
    ColumnName, Name, EntityType,
    IsKey, IsMandatory, IsUpdateable, IsParent,
    IsSelectionColumn, IsIdentifier,
    FieldLength, Version, PersonalDataCategory)
VALUES (592217 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    113, 542587, 30,
    'AD_Org_ID', 'Sektion', 'D',
    'N', 'Y', 'N', 'N',
    'Y', 'N',
    10, 0, 'NP');

-- Col 4: IsActive
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, AD_Table_ID, AD_Reference_ID,
    ColumnName, Name, EntityType,
    IsKey, IsMandatory, IsUpdateable, IsParent,
    IsSelectionColumn, IsIdentifier,
    FieldLength, Version, PersonalDataCategory)
VALUES (592218 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    348, 542587, 20,
    'IsActive', 'Aktiv', 'D',
    'N', 'Y', 'N', 'N',
    'N', 'N',
    1, 0, 'NP');

-- Col 5: Created
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, AD_Table_ID, AD_Reference_ID,
    ColumnName, Name, EntityType,
    IsKey, IsMandatory, IsUpdateable, IsParent,
    IsSelectionColumn, IsIdentifier,
    FieldLength, Version, PersonalDataCategory)
VALUES (592219 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    245, 542587, 16,
    'Created', 'Erstellt', 'D',
    'N', 'Y', 'N', 'N',
    'N', 'N',
    29, 0, 'NP');

-- Col 6: CreatedBy
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, AD_Table_ID, AD_Reference_ID,
    ColumnName, Name, EntityType,
    IsKey, IsMandatory, IsUpdateable, IsParent,
    IsSelectionColumn, IsIdentifier,
    FieldLength, Version, PersonalDataCategory)
VALUES (592220 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    246, 542587, 18,
    'CreatedBy', 'Erstellt durch', 'D',
    'N', 'Y', 'N', 'N',
    'N', 'N',
    10, 0, 'NP');

-- Col 7: Updated
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, AD_Table_ID, AD_Reference_ID,
    ColumnName, Name, EntityType,
    IsKey, IsMandatory, IsUpdateable, IsParent,
    IsSelectionColumn, IsIdentifier,
    FieldLength, Version, PersonalDataCategory)
VALUES (592221 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    607, 542587, 16,
    'Updated', 'Aktualisiert', 'D',
    'N', 'Y', 'N', 'N',
    'N', 'N',
    29, 0, 'NP');

-- Col 8: UpdatedBy
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, AD_Table_ID, AD_Reference_ID,
    ColumnName, Name, EntityType,
    IsKey, IsMandatory, IsUpdateable, IsParent,
    IsSelectionColumn, IsIdentifier,
    FieldLength, Version, PersonalDataCategory)
VALUES (592222 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    608, 542587, 18,
    'UpdatedBy', 'Aktualisiert durch', 'D',
    'N', 'Y', 'N', 'N',
    'N', 'N',
    10, 0, 'NP');

-- Col 9: IsSOTrx
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, AD_Table_ID, AD_Reference_ID,
    ColumnName, Name, EntityType,
    IsKey, IsMandatory, IsUpdateable, IsParent,
    IsSelectionColumn, IsIdentifier,
    FieldLength, Version, PersonalDataCategory)
VALUES (592223 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    1106, 542587, 20,
    'IsSOTrx', 'Verkaufstransaktion', 'D',
    'N', 'N', 'N', 'N',
    'Y', 'N',
    1, 0, 'NP');

-- Col 10: M_InOut_ID
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, AD_Table_ID, AD_Reference_ID,
    ColumnName, Name, EntityType,
    IsKey, IsMandatory, IsUpdateable, IsParent,
    IsSelectionColumn, IsIdentifier,
    FieldLength, Version, PersonalDataCategory)
VALUES (592224 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    1025, 542587, 30,
    'M_InOut_ID', 'Lieferung/Wareneingang', 'D',
    'N', 'N', 'N', 'N',
    'N', 'N',
    10, 0, 'NP');

-- Col 11: C_Invoice_ID
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, AD_Table_ID, AD_Reference_ID,
    ColumnName, Name, EntityType,
    IsKey, IsMandatory, IsUpdateable, IsParent,
    IsSelectionColumn, IsIdentifier,
    FieldLength, Version, PersonalDataCategory)
VALUES (592225 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    1008, 542587, 30,
    'C_Invoice_ID', 'Rechnung', 'D',
    'N', 'N', 'N', 'N',
    'N', 'N',
    10, 0, 'NP');

-- Col 12: M_Product_ID
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, AD_Table_ID, AD_Reference_ID,
    ColumnName, Name, EntityType,
    IsKey, IsMandatory, IsUpdateable, IsParent,
    IsSelectionColumn, IsIdentifier,
    FieldLength, Version, PersonalDataCategory)
VALUES (592226 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    454, 542587, 30,
    'M_Product_ID', 'Produkt', 'D',
    'N', 'N', 'N', 'N',
    'Y', 'N',
    10, 0, 'NP');

-- Col 13: C_BPartner_ID
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, AD_Table_ID, AD_Reference_ID,
    ColumnName, Name, EntityType,
    IsKey, IsMandatory, IsUpdateable, IsParent,
    IsSelectionColumn, IsIdentifier,
    FieldLength, Version, PersonalDataCategory)
VALUES (592227 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    187, 542587, 30,
    'C_BPartner_ID', 'Geschaeftspartner', 'D',
    'N', 'N', 'N', 'N',
    'Y', 'N',
    10, 0, 'P');

-- Col 14: C_Period_ID
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, AD_Table_ID, AD_Reference_ID,
    ColumnName, Name, EntityType,
    IsKey, IsMandatory, IsUpdateable, IsParent,
    IsSelectionColumn, IsIdentifier,
    FieldLength, Version, PersonalDataCategory)
VALUES (592228 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    206, 542587, 19,
    'C_Period_ID', 'Periode', 'D',
    'N', 'N', 'N', 'N',
    'Y', 'N',
    10, 0, 'NP');

-- Col 15: C_Year_ID
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, AD_Table_ID, AD_Reference_ID,
    ColumnName, Name, EntityType,
    IsKey, IsMandatory, IsUpdateable, IsParent,
    IsSelectionColumn, IsIdentifier,
    FieldLength, Version, PersonalDataCategory)
VALUES (592229 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    223, 542587, 19,
    'C_Year_ID', 'Jahr', 'D',
    'N', 'N', 'N', 'N',
    'Y', 'N',
    10, 0, 'NP');

-- Col 16: DateInvoiced (selection column with Between filter for date-range filtering)
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, AD_Table_ID, AD_Reference_ID,
    ColumnName, Name, EntityType,
    IsKey, IsMandatory, IsUpdateable, IsParent,
    IsSelectionColumn, IsIdentifier,
    SelectionColumnSeqNo, FilterOperator,
    FieldLength, Version, PersonalDataCategory)
VALUES (592230 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    267, 542587, 15,
    'DateInvoiced', 'Rechnungsdatum', 'D',
    'N', 'N', 'N', 'N',
    'Y', 'N',
    25, 'B',
    29, 0, 'NP');

-- Col 17: CommodityNumber
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, AD_Table_ID, AD_Reference_ID,
    ColumnName, Name, EntityType,
    IsKey, IsMandatory, IsUpdateable, IsParent,
    IsSelectionColumn, IsIdentifier,
    FieldLength, Version, PersonalDataCategory)
VALUES (592231 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    584664, 542587, 10,
    'CommodityNumber', 'Warennummer', 'D',
    'N', 'N', 'N', 'N',
    'N', 'N',
    40, 0, 'NP');

-- Col 18: CustomsTariff
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, AD_Table_ID, AD_Reference_ID,
    ColumnName, Name, EntityType,
    IsKey, IsMandatory, IsUpdateable, IsParent,
    IsSelectionColumn, IsIdentifier,
    FieldLength, Version, PersonalDataCategory)
VALUES (592232 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    576870, 542587, 10,
    'CustomsTariff', 'Zolltarif', 'D',
    'N', 'N', 'N', 'N',
    'N', 'N',
    40, 0, 'NP');

-- Col 19: DeliveredFromCountry
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, AD_Table_ID, AD_Reference_ID,
    ColumnName, Name, EntityType,
    IsKey, IsMandatory, IsUpdateable, IsParent,
    IsSelectionColumn, IsIdentifier,
    FieldLength, Version, PersonalDataCategory)
VALUES (592233 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    584665, 542587, 10,
    'DeliveredFromCountry', 'Versandland', 'D',
    'N', 'N', 'N', 'N',
    'N', 'N',
    10, 0, 'NP');

-- Col 20: DeliveryCountry
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, AD_Table_ID, AD_Reference_ID,
    ColumnName, Name, EntityType,
    IsKey, IsMandatory, IsUpdateable, IsParent,
    IsSelectionColumn, IsIdentifier,
    FieldLength, Version, PersonalDataCategory)
VALUES (592234 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    584666, 542587, 10,
    'DeliveryCountry', 'Bestimmungsland', 'D',
    'N', 'N', 'N', 'N',
    'N', 'N',
    10, 0, 'NP');

-- Col 21: OriginCountry
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, AD_Table_ID, AD_Reference_ID,
    ColumnName, Name, EntityType,
    IsKey, IsMandatory, IsUpdateable, IsParent,
    IsSelectionColumn, IsIdentifier,
    FieldLength, Version, PersonalDataCategory)
VALUES (592235 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    584667, 542587, 10,
    'OriginCountry', 'Ursprungsland', 'D',
    'N', 'N', 'N', 'N',
    'N', 'N',
    10, 0, 'NP');

-- Col 22: MovementQty
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, AD_Table_ID, AD_Reference_ID,
    ColumnName, Name, EntityType,
    IsKey, IsMandatory, IsUpdateable, IsParent,
    IsSelectionColumn, IsIdentifier,
    FieldLength, Version, PersonalDataCategory)
VALUES (592236 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    1038, 542587, 29,
    'MovementQty', 'Bewegungsmenge', 'D',
    'N', 'N', 'N', 'N',
    'N', 'N',
    10, 0, 'NP');

-- Col 23: UOMSymbol
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, AD_Table_ID, AD_Reference_ID,
    ColumnName, Name, EntityType,
    IsKey, IsMandatory, IsUpdateable, IsParent,
    IsSelectionColumn, IsIdentifier,
    FieldLength, Version, PersonalDataCategory)
VALUES (592237 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    602, 542587, 10,
    'UOMSymbol', 'Masseinheit', 'D',
    'N', 'N', 'N', 'N',
    'N', 'N',
    10, 0, 'NP');

-- Col 24: Weight
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, AD_Table_ID, AD_Reference_ID,
    ColumnName, Name, EntityType,
    IsKey, IsMandatory, IsUpdateable, IsParent,
    IsSelectionColumn, IsIdentifier,
    FieldLength, Version, PersonalDataCategory)
VALUES (592238 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    629, 542587, 22,
    'Weight', 'Gewicht', 'D',
    'N', 'N', 'N', 'N',
    'N', 'N',
    10, 0, 'NP');

-- Col 25: LineNetAmt
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, AD_Table_ID, AD_Reference_ID,
    ColumnName, Name, EntityType,
    IsKey, IsMandatory, IsUpdateable, IsParent,
    IsSelectionColumn, IsIdentifier,
    FieldLength, Version, PersonalDataCategory)
VALUES (592239 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    441, 542587, 12,
    'LineNetAmt', 'Zeilennetto', 'D',
    'N', 'N', 'N', 'N',
    'N', 'N',
    10, 0, 'NP');

-- Col 26: CurSymbol
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, AD_Table_ID, AD_Reference_ID,
    ColumnName, Name, EntityType,
    IsKey, IsMandatory, IsUpdateable, IsParent,
    IsSelectionColumn, IsIdentifier,
    FieldLength, Version, PersonalDataCategory)
VALUES (592240 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    250, 542587, 10,
    'CurSymbol', 'Waehrungssymbol', 'D',
    'N', 'N', 'N', 'N',
    'N', 'N',
    10, 0, 'NP');

-- Col 27: VATaxID
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, AD_Table_ID, AD_Reference_ID,
    ColumnName, Name, EntityType,
    IsKey, IsMandatory, IsUpdateable, IsParent,
    IsSelectionColumn, IsIdentifier,
    FieldLength, Version, PersonalDataCategory)
VALUES (592241 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    502388, 542587, 10,
    'VATaxID', 'USt-IdNr.', 'D',
    'N', 'N', 'N', 'N',
    'N', 'N',
    40, 0, 'P');

-- =====================================================================
-- 4. AD_Window
-- =====================================================================
INSERT INTO AD_Window (AD_Window_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, EntityType, WindowType, IsDefault, IsSOTrx,
    Processing, AD_Element_ID)
VALUES (542107 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    'Intrastat', 'D', 'M', 'N', 'N',
    'N', 584668);

-- Now link the table to its window (deferred from AD_Table INSERT to avoid FK order issue)
UPDATE AD_Table SET AD_Window_ID = 542107 WHERE AD_Table_ID = 542587;

-- =====================================================================
-- 5. AD_Tab
-- =====================================================================
INSERT INTO AD_Tab (AD_Tab_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, AD_Table_ID, AD_Window_ID, EntityType, AD_Element_ID,
    TabLevel, SeqNo, IsSingleRow, IsReadOnly,
    IsInsertRecord, IsAdvancedTab,
    IsSearchCollapsed, HasTree, IsInfoTab,
    IsTranslationTab, IsSortTab,
    AllowQuickInput, IncludedTabNewRecordInputMode,
    IsAutodetectDefaultDateFilter, IsGridModeOnly,
    IsRefreshAllOnActivate, IsRefreshViewOnChangeEvents)
VALUES (549082 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    'Detail', 542587, 542107, 'D', 584668,
    0, 10, 'N', 'Y',
    'N', 'N',
    'Y', 'N', 'N',
    'N', 'N',
    'N', 'A',
    'Y', 'N',
    'N', 'N');

-- =====================================================================
-- 6. AD_Fields (27 fields)
-- =====================================================================

-- Field 1: Intrastat_Report_Detail_V_ID
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, AD_Column_ID, AD_Tab_ID, EntityType,
    IsDisplayed, IsReadOnly, IsEncrypted)
VALUES (774897 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    'Intrastat Detail', 592215, 549082, 'D',
    'N', 'Y', 'N');

-- Field 2: AD_Client_ID
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, AD_Column_ID, AD_Tab_ID, EntityType,
    IsDisplayed, IsReadOnly, IsEncrypted)
VALUES (774898 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    'Mandant', 592216, 549082, 'D',
    'Y', 'Y', 'N');

-- Field 3: AD_Org_ID
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, AD_Column_ID, AD_Tab_ID, EntityType,
    IsDisplayed, IsReadOnly, IsEncrypted)
VALUES (774899 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    'Sektion', 592217, 549082, 'D',
    'Y', 'Y', 'N');

-- Field 4: IsActive
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, AD_Column_ID, AD_Tab_ID, EntityType,
    IsDisplayed, IsReadOnly, IsEncrypted)
VALUES (774900 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    'Aktiv', 592218, 549082, 'D',
    'N', 'Y', 'N');

-- Field 5: Created
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, AD_Column_ID, AD_Tab_ID, EntityType,
    IsDisplayed, IsReadOnly, IsEncrypted)
VALUES (774901 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    'Erstellt', 592219, 549082, 'D',
    'N', 'Y', 'N');

-- Field 6: CreatedBy
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, AD_Column_ID, AD_Tab_ID, EntityType,
    IsDisplayed, IsReadOnly, IsEncrypted)
VALUES (774902 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    'Erstellt durch', 592220, 549082, 'D',
    'N', 'Y', 'N');

-- Field 7: Updated
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, AD_Column_ID, AD_Tab_ID, EntityType,
    IsDisplayed, IsReadOnly, IsEncrypted)
VALUES (774903 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    'Aktualisiert', 592221, 549082, 'D',
    'N', 'Y', 'N');

-- Field 8: UpdatedBy
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, AD_Column_ID, AD_Tab_ID, EntityType,
    IsDisplayed, IsReadOnly, IsEncrypted)
VALUES (774904 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    'Aktualisiert durch', 592222, 549082, 'D',
    'N', 'Y', 'N');

-- Field 9: IsSOTrx
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, AD_Column_ID, AD_Tab_ID, EntityType,
    IsDisplayed, IsReadOnly, IsEncrypted)
VALUES (774905 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    'Verkaufstransaktion', 592223, 549082, 'D',
    'Y', 'Y', 'N');

-- Field 10: M_InOut_ID
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, AD_Column_ID, AD_Tab_ID, EntityType,
    IsDisplayed, IsReadOnly, IsEncrypted)
VALUES (774906 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    'Lieferung/Wareneingang', 592224, 549082, 'D',
    'Y', 'Y', 'N');

-- Field 11: C_Invoice_ID
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, AD_Column_ID, AD_Tab_ID, EntityType,
    IsDisplayed, IsReadOnly, IsEncrypted)
VALUES (774907 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    'Rechnung', 592225, 549082, 'D',
    'Y', 'Y', 'N');

-- Field 12: M_Product_ID
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, AD_Column_ID, AD_Tab_ID, EntityType,
    IsDisplayed, IsReadOnly, IsEncrypted)
VALUES (774908 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    'Produkt', 592226, 549082, 'D',
    'Y', 'Y', 'N');

-- Field 13: C_BPartner_ID
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, AD_Column_ID, AD_Tab_ID, EntityType,
    IsDisplayed, IsReadOnly, IsEncrypted)
VALUES (774909 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    'Geschaeftspartner', 592227, 549082, 'D',
    'Y', 'Y', 'N');

-- Field 14: C_Period_ID
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, AD_Column_ID, AD_Tab_ID, EntityType,
    IsDisplayed, IsReadOnly, IsEncrypted)
VALUES (774910 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    'Periode', 592228, 549082, 'D',
    'Y', 'Y', 'N');

-- Field 15: C_Year_ID
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, AD_Column_ID, AD_Tab_ID, EntityType,
    IsDisplayed, IsReadOnly, IsEncrypted)
VALUES (774911 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    'Jahr', 592229, 549082, 'D',
    'Y', 'Y', 'N');

-- Field 16: DateInvoiced
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, AD_Column_ID, AD_Tab_ID, EntityType,
    IsDisplayed, IsReadOnly, IsEncrypted)
VALUES (774912 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    'Rechnungsdatum', 592230, 549082, 'D',
    'Y', 'Y', 'N');

-- Field 17: CommodityNumber
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, AD_Column_ID, AD_Tab_ID, EntityType,
    IsDisplayed, IsReadOnly, IsEncrypted)
VALUES (774913 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    'Warennummer', 592231, 549082, 'D',
    'Y', 'Y', 'N');

-- Field 18: CustomsTariff
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, AD_Column_ID, AD_Tab_ID, EntityType,
    IsDisplayed, IsReadOnly, IsEncrypted)
VALUES (774914 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    'Zolltarif', 592232, 549082, 'D',
    'Y', 'Y', 'N');

-- Field 19: DeliveredFromCountry
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, AD_Column_ID, AD_Tab_ID, EntityType,
    IsDisplayed, IsReadOnly, IsEncrypted)
VALUES (774915 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    'Versandland', 592233, 549082, 'D',
    'Y', 'Y', 'N');

-- Field 20: DeliveryCountry
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, AD_Column_ID, AD_Tab_ID, EntityType,
    IsDisplayed, IsReadOnly, IsEncrypted)
VALUES (774916 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    'Bestimmungsland', 592234, 549082, 'D',
    'Y', 'Y', 'N');

-- Field 21: OriginCountry
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, AD_Column_ID, AD_Tab_ID, EntityType,
    IsDisplayed, IsReadOnly, IsEncrypted)
VALUES (774917 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    'Ursprungsland', 592235, 549082, 'D',
    'Y', 'Y', 'N');

-- Field 22: MovementQty
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, AD_Column_ID, AD_Tab_ID, EntityType,
    IsDisplayed, IsReadOnly, IsEncrypted)
VALUES (774918 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    'Bewegungsmenge', 592236, 549082, 'D',
    'Y', 'Y', 'N');

-- Field 23: UOMSymbol
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, AD_Column_ID, AD_Tab_ID, EntityType,
    IsDisplayed, IsReadOnly, IsEncrypted)
VALUES (774919 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    'Masseinheit', 592237, 549082, 'D',
    'Y', 'Y', 'N');

-- Field 24: Weight
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, AD_Column_ID, AD_Tab_ID, EntityType,
    IsDisplayed, IsReadOnly, IsEncrypted)
VALUES (774920 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    'Gewicht', 592238, 549082, 'D',
    'Y', 'Y', 'N');

-- Field 25: LineNetAmt
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, AD_Column_ID, AD_Tab_ID, EntityType,
    IsDisplayed, IsReadOnly, IsEncrypted)
VALUES (774921 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    'Zeilennetto', 592239, 549082, 'D',
    'Y', 'Y', 'N');

-- Field 26: CurSymbol
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, AD_Column_ID, AD_Tab_ID, EntityType,
    IsDisplayed, IsReadOnly, IsEncrypted)
VALUES (774922 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    'Waehrungssymbol', 592240, 549082, 'D',
    'Y', 'Y', 'N');

-- Field 27: VATaxID
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, AD_Column_ID, AD_Tab_ID, EntityType,
    IsDisplayed, IsReadOnly, IsEncrypted)
VALUES (774923 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    'USt-IdNr.', 592241, 549082, 'D',
    'Y', 'Y', 'N');

-- =====================================================================
-- 7. AD_UI_Section, AD_UI_Columns, AD_UI_ElementGroups
-- =====================================================================

-- Section
INSERT INTO AD_UI_Section (AD_UI_Section_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Tab_ID, Name, SeqNo)
VALUES (547601 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    549082, 'main', 10);

-- Left Column
INSERT INTO AD_UI_Column (AD_UI_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_UI_Section_ID, SeqNo)
VALUES (549279 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    547601, 10);

-- Right Column
INSERT INTO AD_UI_Column (AD_UI_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_UI_Section_ID, SeqNo)
VALUES (549280 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    547601, 20);

-- ElementGroup: Core (left column) — IsSOTrx, Period, Year, BPartner, Product, InOut, Invoice
INSERT INTO AD_UI_ElementGroup (AD_UI_ElementGroup_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_UI_Column_ID, Name, SeqNo, UIStyle)
VALUES (554992 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    549279, 'core', 10, 'primary');

-- ElementGroup: Org (right column) — Organisation bottom-right
INSERT INTO AD_UI_ElementGroup (AD_UI_ElementGroup_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_UI_Column_ID, Name, SeqNo, UIStyle)
VALUES (554993 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    549280, 'org', 10, 'primary');

-- ElementGroup: Detail (right column) — countries, quantities, amounts
INSERT INTO AD_UI_ElementGroup (AD_UI_ElementGroup_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_UI_Column_ID, Name, SeqNo, UIStyle)
VALUES (554994 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    549280, 'detail', 20, 'primary');

-- =====================================================================
-- 8. AD_UI_Elements (20 grid columns + form)
-- =====================================================================

-- Grid col 1: IsSOTrx (SeqNoGrid=10)
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Field_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_ElementType,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsAdvancedField)
VALUES (648545 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    774905, 549082, 554992, 'F',
    'IsSOTrx', 10, 10, 0,
    'Y', 'Y', 'N', 'N');

-- Grid col 2: C_Period_ID (SeqNoGrid=20)
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Field_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_ElementType,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsAdvancedField)
VALUES (648546 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    774910, 549082, 554992, 'F',
    'Periode', 20, 20, 0,
    'Y', 'Y', 'N', 'N');

-- Grid col 3: DateInvoiced (SeqNoGrid=30)
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Field_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_ElementType,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsAdvancedField)
VALUES (648547 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    774912, 549082, 554992, 'F',
    'Rechnungsdatum', 30, 30, 0,
    'Y', 'Y', 'N', 'N');

-- Grid col 4: M_Product_ID (SeqNoGrid=40)
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Field_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_ElementType,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsAdvancedField)
VALUES (648548 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    774908, 549082, 554992, 'F',
    'Produkt', 40, 40, 0,
    'Y', 'Y', 'N', 'N');

-- Grid col 5: C_BPartner_ID (SeqNoGrid=50)
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Field_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_ElementType,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsAdvancedField)
VALUES (648549 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    774909, 549082, 554992, 'F',
    'Geschaeftspartner', 50, 50, 0,
    'Y', 'Y', 'N', 'N');

-- Grid col 6: CommodityNumber (SeqNoGrid=60)
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Field_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_ElementType,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsAdvancedField)
VALUES (648550 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    774913, 549082, 554994, 'F',
    'Warennummer', 10, 60, 0,
    'Y', 'Y', 'N', 'N');

-- Grid col 7: CustomsTariff (SeqNoGrid=70)
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Field_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_ElementType,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsAdvancedField)
VALUES (648551 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    774914, 549082, 554994, 'F',
    'Zolltarif', 20, 70, 0,
    'Y', 'Y', 'N', 'N');

-- Grid col 8: DeliveryCountry (SeqNoGrid=80)
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Field_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_ElementType,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsAdvancedField)
VALUES (648552 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    774916, 549082, 554994, 'F',
    'Bestimmungsland', 30, 80, 0,
    'Y', 'Y', 'N', 'N');

-- Grid col 9: DeliveredFromCountry (SeqNoGrid=90)
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Field_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_ElementType,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsAdvancedField)
VALUES (648553 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    774915, 549082, 554994, 'F',
    'Versandland', 40, 90, 0,
    'Y', 'Y', 'N', 'N');

-- Grid col 10: OriginCountry (SeqNoGrid=100)
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Field_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_ElementType,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsAdvancedField)
VALUES (648554 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    774917, 549082, 554994, 'F',
    'Ursprungsland', 50, 100, 0,
    'Y', 'Y', 'N', 'N');

-- Grid col 11: MovementQty (SeqNoGrid=110)
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Field_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_ElementType,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsAdvancedField)
VALUES (648555 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    774918, 549082, 554994, 'F',
    'Bewegungsmenge', 60, 110, 0,
    'Y', 'Y', 'N', 'N');

-- Grid col 12: UOMSymbol (SeqNoGrid=120)
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Field_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_ElementType,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsAdvancedField)
VALUES (648556 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    774919, 549082, 554994, 'F',
    'Masseinheit', 70, 120, 0,
    'Y', 'Y', 'N', 'N');

-- Grid col 13: Weight (SeqNoGrid=130)
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Field_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_ElementType,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsAdvancedField)
VALUES (648557 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    774920, 549082, 554994, 'F',
    'Gewicht', 80, 130, 0,
    'Y', 'Y', 'N', 'N');

-- Grid col 14: LineNetAmt (SeqNoGrid=140)
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Field_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_ElementType,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsAdvancedField)
VALUES (648558 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    774921, 549082, 554994, 'F',
    'Zeilennetto', 90, 140, 0,
    'Y', 'Y', 'N', 'N');

-- Grid col 15: CurSymbol (SeqNoGrid=150)
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Field_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_ElementType,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsAdvancedField)
VALUES (648559 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    774922, 549082, 554994, 'F',
    'Waehrungssymbol', 100, 150, 0,
    'Y', 'Y', 'N', 'N');

-- Grid col 16: M_InOut_ID (SeqNoGrid=160)
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Field_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_ElementType,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsAdvancedField)
VALUES (648560 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    774906, 549082, 554992, 'F',
    'Lieferung', 60, 160, 0,
    'Y', 'Y', 'N', 'N');

-- Grid col 17: C_Invoice_ID (SeqNoGrid=170)
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Field_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_ElementType,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsAdvancedField)
VALUES (648561 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    774907, 549082, 554992, 'F',
    'Rechnung', 70, 170, 0,
    'Y', 'Y', 'N', 'N');

-- Grid col 18: VATaxID (SeqNoGrid=180)
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Field_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_ElementType,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsAdvancedField)
VALUES (648562 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    774923, 549082, 554994, 'F',
    'USt-IdNr.', 110, 180, 0,
    'Y', 'Y', 'N', 'N');

-- Grid col 19: C_Year_ID (SeqNoGrid=190)
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Field_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_ElementType,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsAdvancedField)
VALUES (648563 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    774911, 549082, 554992, 'F',
    'Jahr', 80, 190, 0,
    'Y', 'Y', 'N', 'N');

-- Grid col 20: AD_Org_ID (SeqNoGrid=200 — LAST per design guide)
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Field_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_ElementType,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsAdvancedField)
VALUES (648564 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    774899, 549082, 554993, 'F',
    'Sektion', 10, 200, 0,
    'Y', 'Y', 'N', 'N');

-- AD_Client_ID: form-only (advanced edit), NOT in grid per design guide
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Field_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_ElementType,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsAdvancedField)
VALUES (648565 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    774898, 549082, 554993, 'F',
    'Mandant', 20, 0, 0,
    'Y', 'N', 'N', 'Y');

-- =====================================================================
-- 9. AD_Menu + Tree placement
-- =====================================================================

-- Menu entry
INSERT INTO AD_Menu (AD_Menu_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, Action, AD_Window_ID, EntityType,
    IsSummary, IsSOTrx, IsReadOnly,
    AD_Element_ID, InternalName)
VALUES (542307 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    'Intrastat', 'W', 542107, 'D',
    'N', 'N', 'N',
    584668, 'intrastat');

-- Place in menu tree under "Auftraege" (Orders, Node_ID=457)
INSERT INTO AD_TreeNodeMM (AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Tree_ID, Node_ID, Parent_ID, SeqNo)
SELECT 0, 0, 'Y',
    TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-12 12:00','YYYY-MM-DD HH24:MI'), 100,
    t.AD_Tree_ID, 542307, 457,
    (SELECT COALESCE(MAX(tn.SeqNo), 0) + 10
     FROM AD_TreeNodeMM tn WHERE tn.Parent_ID = 457
       AND tn.AD_Tree_ID = t.AD_Tree_ID)
FROM AD_Tree t
WHERE t.AD_Client_ID = 0
  AND t.IsActive = 'Y'
  AND t.IsAllNodes = 'Y'
  AND t.AD_Table_ID = 116
ON CONFLICT (AD_Tree_ID, Node_ID) DO NOTHING;

-- Propagate menu translations from AD_Element
SELECT update_menu_translation_from_ad_element(542307);

-- =====================================================================
-- 10. Default grid sort: DateInvoiced DESC
-- =====================================================================
UPDATE AD_Tab SET OrderByClause = 'DateInvoiced DESC'
WHERE AD_Tab_ID = 549082;

-- =====================================================================
-- 11. Fix en_US translations: propagate from AD_Element_Trl to AD_Field_Trl
-- =====================================================================
-- The auto-generated AD_Field_Trl records copy base language (de_DE) text
-- with IsTranslated='N'. WebAPI uses AD_Field_Trl for view layout captions,
-- so without this the English view shows German column headers.

-- First fix CustomsTariff element translation (IsTranslated was 'N' in preloaded DB)
UPDATE AD_Element_Trl
SET IsTranslated = 'Y'
WHERE AD_Element_ID = (SELECT AD_Element_ID FROM AD_Element WHERE ColumnName = 'CustomsTariff')
  AND AD_Language = 'en_US'
  AND IsTranslated = 'N';

-- Propagate en_US translations from AD_Element_Trl to AD_Field_Trl for all Intrastat fields
UPDATE AD_Field_Trl
SET Name = et.Name, Description = et.Description, Help = et.Help, IsTranslated = 'Y'
FROM AD_Field f
JOIN AD_Column c ON c.AD_Column_ID = f.AD_Column_ID
JOIN AD_Element_Trl et ON et.AD_Element_ID = c.AD_Element_ID AND et.AD_Language = 'en_US'
WHERE AD_Field_Trl.AD_Field_ID = f.AD_Field_ID
  AND f.AD_Tab_ID = 549082
  AND AD_Field_Trl.AD_Language = 'en_US'
  AND AD_Field_Trl.IsTranslated = 'N'
  AND et.IsTranslated = 'Y';
