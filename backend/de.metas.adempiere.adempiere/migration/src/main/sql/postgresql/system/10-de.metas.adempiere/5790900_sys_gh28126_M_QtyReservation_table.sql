-- gh#28126: Create M_QtyReservation table for Qty-Based Reservation from Material Cockpit V2

-- ==========================================================================
-- 1. AD_Element for M_QtyReservation_ID
-- ==========================================================================
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, Name, PrintName, EntityType)
VALUES (584594, 0, 0, 'Y', TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), 0,
        'M_QtyReservation_ID', 'Qty Reservation', 'Qty Reservation', 'D');

INSERT INTO AD_Element_Trl (AD_Element_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, IsTranslated,
                            Name, PrintName)
SELECT 584594, AD_Language, 0, 0, 'Y', TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), 0, 'N',
       'Qty Reservation', 'Qty Reservation'
FROM AD_Language WHERE IsActive = 'Y' AND IsSystemLanguage = 'Y'
ON CONFLICT DO NOTHING;

UPDATE AD_Element_Trl SET Name = 'Mengenreservierung', PrintName = 'Mengenreservierung', IsTranslated = 'Y',
                          Updated = TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 0
WHERE AD_Element_ID = 584594 AND AD_Language = 'de_DE';

UPDATE AD_Element_Trl SET Name = 'Qty Reservation', PrintName = 'Qty Reservation', IsTranslated = 'Y',
                          Updated = TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 0
WHERE AD_Element_ID = 584594 AND AD_Language = 'de_CH';

UPDATE AD_Element_Trl SET Name = 'Qty Reservation', PrintName = 'Qty Reservation', IsTranslated = 'Y',
                          Updated = TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 0
WHERE AD_Element_ID = 584594 AND AD_Language = 'en_US';


-- ==========================================================================
-- 2. AD_Element for SupplyType — reuse existing AD_Element_ID=584567
-- ==========================================================================
UPDATE AD_Element_Trl SET Name = 'Versorgungsart', PrintName = 'Versorgungsart', IsTranslated = 'Y',
                          Updated = TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 0
WHERE AD_Element_ID = 584567 AND AD_Language = 'de_DE';

UPDATE AD_Element_Trl SET Name = 'Versorgungsart', PrintName = 'Versorgungsart', IsTranslated = 'Y',
                          Updated = TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 0
WHERE AD_Element_ID = 584567 AND AD_Language = 'de_CH';

UPDATE AD_Element_Trl SET Name = 'Supply Type', PrintName = 'Supply Type', IsTranslated = 'Y',
                          Updated = TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 0
WHERE AD_Element_ID = 584567 AND AD_Language = 'en_US';


-- ==========================================================================
-- 3. AD_Reference (List) for SupplyType
-- ==========================================================================
INSERT INTO AD_Reference (AD_Reference_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                          Name, ValidationType, EntityType, IsOrderByValue)
VALUES (542060, 0, 0, 'Y', TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), 0,
        'M_QtyReservation SupplyType', 'L', 'D', 'N');

-- OH = On Hand (stock)
INSERT INTO AD_Ref_List (AD_Ref_List_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                         AD_Reference_ID, Value, Name, EntityType)
VALUES (544133, 0, 0, 'Y', TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), 0,
        542060, 'OH', 'On Hand', 'D');

INSERT INTO AD_Ref_List_Trl (AD_Ref_List_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, IsTranslated,
                             Name)
SELECT 544133, AD_Language, 0, 0, 'Y', TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), 0, 'N',
       'On Hand'
FROM AD_Language WHERE IsActive = 'Y' AND IsSystemLanguage = 'Y'
ON CONFLICT DO NOTHING;

UPDATE AD_Ref_List_Trl SET Name = 'Bestand', IsTranslated = 'Y',
                           Updated = TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 0
WHERE AD_Ref_List_ID = 544133 AND AD_Language = 'de_DE';

UPDATE AD_Ref_List_Trl SET Name = 'Bestand', IsTranslated = 'Y',
                           Updated = TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 0
WHERE AD_Ref_List_ID = 544133 AND AD_Language = 'de_CH';

-- PS = Planned Supply
INSERT INTO AD_Ref_List (AD_Ref_List_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                         AD_Reference_ID, Value, Name, EntityType)
VALUES (544134, 0, 0, 'Y', TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), 0,
        542060, 'PS', 'Planned Supply', 'D');

INSERT INTO AD_Ref_List_Trl (AD_Ref_List_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, IsTranslated,
                             Name)
SELECT 544134, AD_Language, 0, 0, 'Y', TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), 0, 'N',
       'Planned Supply'
FROM AD_Language WHERE IsActive = 'Y' AND IsSystemLanguage = 'Y'
ON CONFLICT DO NOTHING;

UPDATE AD_Ref_List_Trl SET Name = 'Geplante Lieferung', IsTranslated = 'Y',
                           Updated = TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 0
WHERE AD_Ref_List_ID = 544134 AND AD_Language = 'de_DE';

UPDATE AD_Ref_List_Trl SET Name = 'Geplante Lieferung', IsTranslated = 'Y',
                           Updated = TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 0
WHERE AD_Ref_List_ID = 544134 AND AD_Language = 'de_CH';


-- ==========================================================================
-- 4. AD_Table for M_QtyReservation
-- ==========================================================================
INSERT INTO AD_Table (AD_Table_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      Name, TableName, IsView, AccessLevel, EntityType, IsHighVolume, IsChangeLog, IsDeleteable,
                      ReplicationType, CopyColumnsFromTable, ACTriggerLength)
VALUES (542583, 0, 0, 'Y', TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), 0,
        'Qty Reservation', 'M_QtyReservation', 'N', '3', 'D', 'N', 'Y', 'Y',
        'L', 'N', 0);


-- ==========================================================================
-- 5. AD_Columns for M_QtyReservation
-- ==========================================================================

-- M_QtyReservation_ID (PK)
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Table_ID, AD_Element_ID, AD_Reference_ID, ColumnName, Name,
                       IsKey, IsMandatory, IsUpdateable, IsIdentifier, SeqNo,
                       FieldLength, EntityType, Version, PersonalDataCategory)
VALUES (592094, 0, 0, 'Y', TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), 0,
        542583, 584594, 13, 'M_QtyReservation_ID', 'Qty Reservation',
        'Y', 'Y', 'N', 'N', 0,
        10, 'D', 0, 'NP');

-- AD_Client_ID
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Table_ID, AD_Element_ID, AD_Reference_ID, ColumnName, Name,
                       IsKey, IsMandatory, IsUpdateable, IsIdentifier, SeqNo,
                       FieldLength, EntityType, Version, PersonalDataCategory)
VALUES (592095, 0, 0, 'Y', TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), 0,
        542583, 102, 19, 'AD_Client_ID', 'Mandant',
        'N', 'Y', 'N', 'N', 0,
        10, 'D', 0, 'NP');

-- AD_Org_ID
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Table_ID, AD_Element_ID, AD_Reference_ID, ColumnName, Name,
                       IsKey, IsMandatory, IsUpdateable, IsIdentifier, SeqNo,
                       FieldLength, EntityType, Version, PersonalDataCategory)
VALUES (592096, 0, 0, 'Y', TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), 0,
        542583, 113, 19, 'AD_Org_ID', 'Sektion',
        'N', 'Y', 'N', 'N', 0,
        10, 'D', 0, 'NP');

-- IsActive
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Table_ID, AD_Element_ID, AD_Reference_ID, ColumnName, Name,
                       IsKey, IsMandatory, IsUpdateable, IsIdentifier, SeqNo,
                       FieldLength, DefaultValue, EntityType, Version, PersonalDataCategory)
VALUES (592097, 0, 0, 'Y', TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), 0,
        542583, 348, 20, 'IsActive', 'Aktiv',
        'N', 'Y', 'Y', 'N', 0,
        1, 'Y', 'D', 0, 'NP');

-- Created
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Table_ID, AD_Element_ID, AD_Reference_ID, ColumnName, Name,
                       IsKey, IsMandatory, IsUpdateable, IsIdentifier, SeqNo,
                       FieldLength, EntityType, Version, PersonalDataCategory)
VALUES (592098, 0, 0, 'Y', TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), 0,
        542583, 245, 16, 'Created', 'Erstellt',
        'N', 'Y', 'N', 'N', 0,
        29, 'D', 0, 'NP');

-- CreatedBy
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Table_ID, AD_Element_ID, AD_Reference_ID, ColumnName, Name,
                       IsKey, IsMandatory, IsUpdateable, IsIdentifier, SeqNo,
                       FieldLength, EntityType, Version, PersonalDataCategory)
VALUES (592099, 0, 0, 'Y', TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), 0,
        542583, 246, 18, 'CreatedBy', 'Erstellt durch',
        'N', 'Y', 'N', 'N', 0,
        10, 'D', 0, 'NP');

-- Updated
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Table_ID, AD_Element_ID, AD_Reference_ID, ColumnName, Name,
                       IsKey, IsMandatory, IsUpdateable, IsIdentifier, SeqNo,
                       FieldLength, EntityType, Version, PersonalDataCategory)
VALUES (592100, 0, 0, 'Y', TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), 0,
        542583, 607, 16, 'Updated', 'Aktualisiert',
        'N', 'Y', 'N', 'N', 0,
        29, 'D', 0, 'NP');

-- UpdatedBy
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Table_ID, AD_Element_ID, AD_Reference_ID, ColumnName, Name,
                       IsKey, IsMandatory, IsUpdateable, IsIdentifier, SeqNo,
                       FieldLength, EntityType, Version, PersonalDataCategory)
VALUES (592101, 0, 0, 'Y', TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), 0,
        542583, 608, 18, 'UpdatedBy', 'Aktualisiert durch',
        'N', 'Y', 'N', 'N', 0,
        10, 'D', 0, 'NP');

-- C_OrderLine_ID
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Table_ID, AD_Element_ID, AD_Reference_ID, ColumnName, Name,
                       IsKey, IsMandatory, IsUpdateable, IsIdentifier, SeqNo,
                       FieldLength, EntityType, Version, PersonalDataCategory)
VALUES (592102, 0, 0, 'Y', TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), 0,
        542583, 561, 30, 'C_OrderLine_ID', 'Auftragsposition',
        'N', 'Y', 'Y', 'N', 0,
        10, 'D', 0, 'NP');

-- M_Product_ID
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Table_ID, AD_Element_ID, AD_Reference_ID, ColumnName, Name,
                       IsKey, IsMandatory, IsUpdateable, IsIdentifier, SeqNo,
                       FieldLength, EntityType, Version, PersonalDataCategory)
VALUES (592103, 0, 0, 'Y', TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), 0,
        542583, 454, 30, 'M_Product_ID', 'Produkt',
        'N', 'Y', 'Y', 'N', 0,
        10, 'D', 0, 'NP');

-- M_Warehouse_ID
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Table_ID, AD_Element_ID, AD_Reference_ID, ColumnName, Name,
                       IsKey, IsMandatory, IsUpdateable, IsIdentifier, SeqNo,
                       FieldLength, EntityType, Version, PersonalDataCategory)
VALUES (592104, 0, 0, 'Y', TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), 0,
        542583, 459, 30, 'M_Warehouse_ID', 'Lager',
        'N', 'Y', 'Y', 'N', 0,
        10, 'D', 0, 'NP');

-- C_BPartner_Vendor_ID
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Table_ID, AD_Element_ID, AD_Reference_ID, ColumnName, Name,
                       IsKey, IsMandatory, IsUpdateable, IsIdentifier, SeqNo,
                       FieldLength, EntityType, Version, PersonalDataCategory)
VALUES (592105, 0, 0, 'Y', TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), 0,
        542583, 542647, 30, 'C_BPartner_Vendor_ID', 'C_BPartner_Vendor_ID',
        'N', 'N', 'Y', 'N', 0,
        10, 'D', 0, 'NP');

-- SupplyType (List reference)
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Table_ID, AD_Element_ID, AD_Reference_ID, AD_Reference_Value_ID, ColumnName, Name,
                       IsKey, IsMandatory, IsUpdateable, IsIdentifier, SeqNo,
                       FieldLength, EntityType, Version, PersonalDataCategory)
VALUES (592106, 0, 0, 'Y', TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), 0,
        542583, 584567, 17, 542060, 'SupplyType', 'Supply Type',
        'N', 'Y', 'Y', 'N', 0,
        2, 'D', 0, 'NP');

-- DatePromised
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Table_ID, AD_Element_ID, AD_Reference_ID, ColumnName, Name,
                       IsKey, IsMandatory, IsUpdateable, IsIdentifier, SeqNo,
                       FieldLength, EntityType, Version, PersonalDataCategory)
VALUES (592107, 0, 0, 'Y', TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), 0,
        542583, 269, 15, 'DatePromised', 'Zugesagter Termin',
        'N', 'N', 'Y', 'N', 0,
        29, 'D', 0, 'NP');

-- C_UOM_ID
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Table_ID, AD_Element_ID, AD_Reference_ID, ColumnName, Name,
                       IsKey, IsMandatory, IsUpdateable, IsIdentifier, SeqNo,
                       FieldLength, EntityType, Version, PersonalDataCategory)
VALUES (592108, 0, 0, 'Y', TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), 0,
        542583, 215, 30, 'C_UOM_ID', 'Maßeinheit',
        'N', 'Y', 'Y', 'N', 0,
        10, 'D', 0, 'NP');

-- QtyTU
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Table_ID, AD_Element_ID, AD_Reference_ID, ColumnName, Name,
                       IsKey, IsMandatory, IsUpdateable, IsIdentifier, SeqNo,
                       FieldLength, DefaultValue, EntityType, Version, PersonalDataCategory)
VALUES (592109, 0, 0, 'Y', TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), 0,
        542583, 542490, 29, 'QtyTU', 'TU Anzahl',
        'N', 'Y', 'Y', 'N', 0,
        14, '0', 'D', 0, 'NP');

-- Qty
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Table_ID, AD_Element_ID, AD_Reference_ID, ColumnName, Name,
                       IsKey, IsMandatory, IsUpdateable, IsIdentifier, SeqNo,
                       FieldLength, DefaultValue, EntityType, Version, PersonalDataCategory)
VALUES (592110, 0, 0, 'Y', TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), 0,
        542583, 526, 29, 'Qty', 'Menge',
        'N', 'Y', 'Y', 'N', 0,
        14, '0', 'D', 0, 'NP');

-- AttributesKey
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Table_ID, AD_Element_ID, AD_Reference_ID, ColumnName, Name,
                       IsKey, IsMandatory, IsUpdateable, IsIdentifier, SeqNo,
                       FieldLength, EntityType, Version, PersonalDataCategory)
VALUES (592111, 0, 0, 'Y', TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-01 10:00', 'YYYY-MM-DD HH24:MI'), 0,
        542583, 543666, 10, 'AttributesKey', 'AttributesKey (technical)',
        'N', 'N', 'Y', 'N', 0,
        255, 'D', 0, 'NP');


-- ==========================================================================
-- 6. Physical table DDL
-- ==========================================================================
CREATE TABLE IF NOT EXISTS M_QtyReservation
(
    M_QtyReservation_ID  NUMERIC(10)                 NOT NULL,
    AD_Client_ID         NUMERIC(10)                 NOT NULL,
    AD_Org_ID            NUMERIC(10)                 NOT NULL,
    IsActive             CHAR(1)       DEFAULT 'Y'   NOT NULL CHECK (IsActive IN ('Y', 'N')),
    Created              TIMESTAMP WITH TIME ZONE    NOT NULL,
    CreatedBy            NUMERIC(10)                 NOT NULL,
    Updated              TIMESTAMP WITH TIME ZONE    NOT NULL,
    UpdatedBy            NUMERIC(10)                 NOT NULL,
    --
    C_OrderLine_ID       NUMERIC(10)                 NOT NULL,
    M_Product_ID         NUMERIC(10)                 NOT NULL,
    M_Warehouse_ID       NUMERIC(10)                 NOT NULL,
    C_BPartner_Vendor_ID NUMERIC(10),
    SupplyType           VARCHAR(2)                  NOT NULL,
    DatePromised         TIMESTAMP WITHOUT TIME ZONE,
    C_UOM_ID             NUMERIC(10)                 NOT NULL,
    QtyTU                NUMERIC       DEFAULT 0     NOT NULL,
    Qty                  NUMERIC       DEFAULT 0     NOT NULL,
    AttributesKey        VARCHAR(255),
    --
    CONSTRAINT M_QtyReservation_Key PRIMARY KEY (M_QtyReservation_ID),
    CONSTRAINT COrderLine_MQtyReservation FOREIGN KEY (C_OrderLine_ID)
        REFERENCES C_OrderLine DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT MProduct_MQtyReservation FOREIGN KEY (M_Product_ID)
        REFERENCES M_Product DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT MWarehouse_MQtyReservation FOREIGN KEY (M_Warehouse_ID)
        REFERENCES M_Warehouse DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT CUOM_MQtyReservation FOREIGN KEY (C_UOM_ID)
        REFERENCES C_UOM DEFERRABLE INITIALLY DEFERRED
);

CREATE INDEX IF NOT EXISTS M_QtyReservation_OrderLine
    ON M_QtyReservation (C_OrderLine_ID)
    WHERE IsActive = 'Y';

-- Add check constraint for SupplyType reference list
ALTER TABLE M_QtyReservation ADD CONSTRAINT M_QtyReservation_SupplyType_check
    CHECK (SupplyType IN ('OH', 'PS'));


-- ==========================================================================
-- 7. Create DB sequence
-- ==========================================================================
CREATE SEQUENCE IF NOT EXISTS M_QtyReservation_Seq START WITH 1000000 INCREMENT BY 1;
