-- gh#28126: Fixup — idempotent re-insert of M_QtyReservation AD metadata
--
-- Migration 5790900 runs in a single transaction. If ANY INSERT fails (e.g., AD_Element already
-- exists in the preloaded DB image), the entire script rolls back — including the AD_Column INSERTs.
-- The physical table (CREATE TABLE IF NOT EXISTS) and sequence succeed independently, but the
-- AD metadata is lost. This causes "No key columns defined for M_QtyReservation" in the health check.
--
-- This fixup re-inserts all AD metadata with ON CONFLICT DO NOTHING so it's safe to run on any DB state.

-- AD_Element
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, Name, PrintName, EntityType)
VALUES (584594, 0, 0, 'Y', TO_TIMESTAMP('2026-03-24 02:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-24 02:00', 'YYYY-MM-DD HH24:MI'), 0,
        'M_QtyReservation_ID', 'Qty Reservation', 'Qty Reservation', 'D')
ON CONFLICT (AD_Element_ID) DO NOTHING;

-- SupplyType element (may not exist on all DBs)
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, Name, PrintName, EntityType)
VALUES (584567, 0, 0, 'Y', TO_TIMESTAMP('2026-03-24 02:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-24 02:00', 'YYYY-MM-DD HH24:MI'), 0,
        'SupplyType', 'Supply Type', 'Supply Type', 'D')
ON CONFLICT (AD_Element_ID) DO NOTHING;

-- AD_Reference (List) for SupplyType
INSERT INTO AD_Reference (AD_Reference_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                          Name, ValidationType, EntityType, IsOrderByValue)
VALUES (542060, 0, 0, 'Y', TO_TIMESTAMP('2026-03-24 02:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-24 02:00', 'YYYY-MM-DD HH24:MI'), 0,
        'M_QtyReservation SupplyType', 'L', 'D', 'N')
ON CONFLICT (AD_Reference_ID) DO NOTHING;

INSERT INTO AD_Ref_List (AD_Ref_List_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                         AD_Reference_ID, Value, Name, EntityType)
VALUES (544133, 0, 0, 'Y', TO_TIMESTAMP('2026-03-24 02:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-24 02:00', 'YYYY-MM-DD HH24:MI'), 0,
        542060, 'OH', 'On Hand', 'D')
ON CONFLICT (AD_Ref_List_ID) DO NOTHING;

INSERT INTO AD_Ref_List (AD_Ref_List_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                         AD_Reference_ID, Value, Name, EntityType)
VALUES (544134, 0, 0, 'Y', TO_TIMESTAMP('2026-03-24 02:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-24 02:00', 'YYYY-MM-DD HH24:MI'), 0,
        542060, 'PS', 'Planned Supply', 'D')
ON CONFLICT (AD_Ref_List_ID) DO NOTHING;

-- AD_Table
INSERT INTO AD_Table (AD_Table_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      Name, TableName, IsView, AccessLevel, EntityType, IsHighVolume, IsChangeLog, IsDeleteable,
                      ReplicationType, CopyColumnsFromTable, ACTriggerLength)
VALUES (542583, 0, 0, 'Y', TO_TIMESTAMP('2026-03-24 02:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-24 02:00', 'YYYY-MM-DD HH24:MI'), 0,
        'Qty Reservation', 'M_QtyReservation', 'N', '3', 'D', 'N', 'Y', 'Y',
        'L', 'N', 0)
ON CONFLICT (AD_Table_ID) DO NOTHING;

-- AD_Columns (all 18 from 5790900)
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Table_ID, AD_Element_ID, AD_Reference_ID, ColumnName, Name, IsKey, IsMandatory, IsUpdateable, IsIdentifier, SeqNo, FieldLength, EntityType, Version, PersonalDataCategory)
VALUES (592094, 0, 0, 'Y', TO_TIMESTAMP('2026-03-24 02:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-24 02:00', 'YYYY-MM-DD HH24:MI'), 0, 542583, 584594, 13, 'M_QtyReservation_ID', 'Qty Reservation', 'Y', 'Y', 'N', 'N', 0, 10, 'D', 0, 'NP')
ON CONFLICT (AD_Column_ID) DO NOTHING;

INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Table_ID, AD_Element_ID, AD_Reference_ID, ColumnName, Name, IsKey, IsMandatory, IsUpdateable, IsIdentifier, SeqNo, FieldLength, EntityType, Version, PersonalDataCategory)
VALUES (592095, 0, 0, 'Y', TO_TIMESTAMP('2026-03-24 02:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-24 02:00', 'YYYY-MM-DD HH24:MI'), 0, 542583, 102, 19, 'AD_Client_ID', 'Mandant', 'N', 'Y', 'N', 'N', 0, 10, 'D', 0, 'NP')
ON CONFLICT (AD_Column_ID) DO NOTHING;

INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Table_ID, AD_Element_ID, AD_Reference_ID, ColumnName, Name, IsKey, IsMandatory, IsUpdateable, IsIdentifier, SeqNo, FieldLength, EntityType, Version, PersonalDataCategory)
VALUES (592096, 0, 0, 'Y', TO_TIMESTAMP('2026-03-24 02:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-24 02:00', 'YYYY-MM-DD HH24:MI'), 0, 542583, 113, 19, 'AD_Org_ID', 'Sektion', 'N', 'Y', 'N', 'N', 0, 10, 'D', 0, 'NP')
ON CONFLICT (AD_Column_ID) DO NOTHING;

INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Table_ID, AD_Element_ID, AD_Reference_ID, ColumnName, Name, IsKey, IsMandatory, IsUpdateable, IsIdentifier, SeqNo, FieldLength, DefaultValue, EntityType, Version, PersonalDataCategory)
VALUES (592097, 0, 0, 'Y', TO_TIMESTAMP('2026-03-24 02:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-24 02:00', 'YYYY-MM-DD HH24:MI'), 0, 542583, 348, 20, 'IsActive', 'Aktiv', 'N', 'Y', 'Y', 'N', 0, 1, 'Y', 'D', 0, 'NP')
ON CONFLICT (AD_Column_ID) DO NOTHING;

INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Table_ID, AD_Element_ID, AD_Reference_ID, ColumnName, Name, IsKey, IsMandatory, IsUpdateable, IsIdentifier, SeqNo, FieldLength, EntityType, Version, PersonalDataCategory)
VALUES (592098, 0, 0, 'Y', TO_TIMESTAMP('2026-03-24 02:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-24 02:00', 'YYYY-MM-DD HH24:MI'), 0, 542583, 245, 16, 'Created', 'Erstellt', 'N', 'Y', 'N', 'N', 0, 29, 'D', 0, 'NP')
ON CONFLICT (AD_Column_ID) DO NOTHING;

INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Table_ID, AD_Element_ID, AD_Reference_ID, ColumnName, Name, IsKey, IsMandatory, IsUpdateable, IsIdentifier, SeqNo, FieldLength, EntityType, Version, PersonalDataCategory)
VALUES (592099, 0, 0, 'Y', TO_TIMESTAMP('2026-03-24 02:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-24 02:00', 'YYYY-MM-DD HH24:MI'), 0, 542583, 246, 18, 'CreatedBy', 'Erstellt durch', 'N', 'Y', 'N', 'N', 0, 10, 'D', 0, 'NP')
ON CONFLICT (AD_Column_ID) DO NOTHING;

INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Table_ID, AD_Element_ID, AD_Reference_ID, ColumnName, Name, IsKey, IsMandatory, IsUpdateable, IsIdentifier, SeqNo, FieldLength, EntityType, Version, PersonalDataCategory)
VALUES (592100, 0, 0, 'Y', TO_TIMESTAMP('2026-03-24 02:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-24 02:00', 'YYYY-MM-DD HH24:MI'), 0, 542583, 607, 16, 'Updated', 'Aktualisiert', 'N', 'Y', 'N', 'N', 0, 29, 'D', 0, 'NP')
ON CONFLICT (AD_Column_ID) DO NOTHING;

INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Table_ID, AD_Element_ID, AD_Reference_ID, ColumnName, Name, IsKey, IsMandatory, IsUpdateable, IsIdentifier, SeqNo, FieldLength, EntityType, Version, PersonalDataCategory)
VALUES (592101, 0, 0, 'Y', TO_TIMESTAMP('2026-03-24 02:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-24 02:00', 'YYYY-MM-DD HH24:MI'), 0, 542583, 608, 18, 'UpdatedBy', 'Aktualisiert durch', 'N', 'Y', 'N', 'N', 0, 10, 'D', 0, 'NP')
ON CONFLICT (AD_Column_ID) DO NOTHING;

INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Table_ID, AD_Element_ID, AD_Reference_ID, ColumnName, Name, IsKey, IsMandatory, IsUpdateable, IsIdentifier, SeqNo, FieldLength, EntityType, Version, PersonalDataCategory)
VALUES (592102, 0, 0, 'Y', TO_TIMESTAMP('2026-03-24 02:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-24 02:00', 'YYYY-MM-DD HH24:MI'), 0, 542583, 561, 30, 'C_OrderLine_ID', 'Auftragsposition', 'N', 'Y', 'Y', 'N', 0, 10, 'D', 0, 'NP')
ON CONFLICT (AD_Column_ID) DO NOTHING;

INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Table_ID, AD_Element_ID, AD_Reference_ID, ColumnName, Name, IsKey, IsMandatory, IsUpdateable, IsIdentifier, SeqNo, FieldLength, EntityType, Version, PersonalDataCategory)
VALUES (592103, 0, 0, 'Y', TO_TIMESTAMP('2026-03-24 02:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-24 02:00', 'YYYY-MM-DD HH24:MI'), 0, 542583, 454, 30, 'M_Product_ID', 'Produkt', 'N', 'Y', 'Y', 'N', 0, 10, 'D', 0, 'NP')
ON CONFLICT (AD_Column_ID) DO NOTHING;

INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Table_ID, AD_Element_ID, AD_Reference_ID, ColumnName, Name, IsKey, IsMandatory, IsUpdateable, IsIdentifier, SeqNo, FieldLength, EntityType, Version, PersonalDataCategory)
VALUES (592104, 0, 0, 'Y', TO_TIMESTAMP('2026-03-24 02:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-24 02:00', 'YYYY-MM-DD HH24:MI'), 0, 542583, 459, 30, 'M_Warehouse_ID', 'Lager', 'N', 'Y', 'Y', 'N', 0, 10, 'D', 0, 'NP')
ON CONFLICT (AD_Column_ID) DO NOTHING;

INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Table_ID, AD_Element_ID, AD_Reference_ID, ColumnName, Name, IsKey, IsMandatory, IsUpdateable, IsIdentifier, SeqNo, FieldLength, EntityType, Version, PersonalDataCategory)
VALUES (592105, 0, 0, 'Y', TO_TIMESTAMP('2026-03-24 02:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-24 02:00', 'YYYY-MM-DD HH24:MI'), 0, 542583, 542647, 30, 'C_BPartner_Vendor_ID', 'C_BPartner_Vendor_ID', 'N', 'N', 'Y', 'N', 0, 10, 'D', 0, 'NP')
ON CONFLICT (AD_Column_ID) DO NOTHING;

INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Table_ID, AD_Element_ID, AD_Reference_ID, AD_Reference_Value_ID, ColumnName, Name, IsKey, IsMandatory, IsUpdateable, IsIdentifier, SeqNo, FieldLength, EntityType, Version, PersonalDataCategory)
VALUES (592106, 0, 0, 'Y', TO_TIMESTAMP('2026-03-24 02:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-24 02:00', 'YYYY-MM-DD HH24:MI'), 0, 542583, 584567, 17, 542060, 'SupplyType', 'Supply Type', 'N', 'Y', 'Y', 'N', 0, 2, 'D', 0, 'NP')
ON CONFLICT (AD_Column_ID) DO NOTHING;

INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Table_ID, AD_Element_ID, AD_Reference_ID, ColumnName, Name, IsKey, IsMandatory, IsUpdateable, IsIdentifier, SeqNo, FieldLength, EntityType, Version, PersonalDataCategory)
VALUES (592107, 0, 0, 'Y', TO_TIMESTAMP('2026-03-24 02:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-24 02:00', 'YYYY-MM-DD HH24:MI'), 0, 542583, 269, 15, 'DatePromised', 'Zugesagter Termin', 'N', 'N', 'Y', 'N', 0, 29, 'D', 0, 'NP')
ON CONFLICT (AD_Column_ID) DO NOTHING;

INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Table_ID, AD_Element_ID, AD_Reference_ID, ColumnName, Name, IsKey, IsMandatory, IsUpdateable, IsIdentifier, SeqNo, FieldLength, EntityType, Version, PersonalDataCategory)
VALUES (592108, 0, 0, 'Y', TO_TIMESTAMP('2026-03-24 02:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-24 02:00', 'YYYY-MM-DD HH24:MI'), 0, 542583, 215, 30, 'C_UOM_ID', 'Maßeinheit', 'N', 'Y', 'Y', 'N', 0, 10, 'D', 0, 'NP')
ON CONFLICT (AD_Column_ID) DO NOTHING;

INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Table_ID, AD_Element_ID, AD_Reference_ID, ColumnName, Name, IsKey, IsMandatory, IsUpdateable, IsIdentifier, SeqNo, FieldLength, DefaultValue, EntityType, Version, PersonalDataCategory)
VALUES (592109, 0, 0, 'Y', TO_TIMESTAMP('2026-03-24 02:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-24 02:00', 'YYYY-MM-DD HH24:MI'), 0, 542583, 542490, 29, 'QtyTU', 'TU Anzahl', 'N', 'Y', 'Y', 'N', 0, 14, '0', 'D', 0, 'NP')
ON CONFLICT (AD_Column_ID) DO NOTHING;

INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Table_ID, AD_Element_ID, AD_Reference_ID, ColumnName, Name, IsKey, IsMandatory, IsUpdateable, IsIdentifier, SeqNo, FieldLength, DefaultValue, EntityType, Version, PersonalDataCategory)
VALUES (592110, 0, 0, 'Y', TO_TIMESTAMP('2026-03-24 02:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-24 02:00', 'YYYY-MM-DD HH24:MI'), 0, 542583, 526, 29, 'Qty', 'Menge', 'N', 'Y', 'Y', 'N', 0, 14, '0', 'D', 0, 'NP')
ON CONFLICT (AD_Column_ID) DO NOTHING;

INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Table_ID, AD_Element_ID, AD_Reference_ID, ColumnName, Name, IsKey, IsMandatory, IsUpdateable, IsIdentifier, SeqNo, FieldLength, EntityType, Version, PersonalDataCategory)
VALUES (592111, 0, 0, 'Y', TO_TIMESTAMP('2026-03-24 02:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-24 02:00', 'YYYY-MM-DD HH24:MI'), 0, 542583, 543666, 10, 'AttributesKey', 'AttributesKey (technical)', 'N', 'N', 'Y', 'N', 0, 255, 'D', 0, 'NP')
ON CONFLICT (AD_Column_ID) DO NOTHING;

-- QtyDelivered column added by 5791000
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Table_ID, AD_Element_ID, AD_Reference_ID, ColumnName, Name, IsKey, IsMandatory, IsUpdateable, IsIdentifier, SeqNo, FieldLength, DefaultValue, EntityType, Version, PersonalDataCategory)
VALUES (592130, 0, 0, 'Y', TO_TIMESTAMP('2026-03-24 02:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-24 02:00', 'YYYY-MM-DD HH24:MI'), 0, 542583, 526, 29, 'QtyDelivered', 'Gelieferte Menge', 'N', 'Y', 'Y', 'N', 0, 14, '0', 'D', 0, 'NP')
ON CONFLICT (AD_Column_ID) DO NOTHING;

-- Processed column added by 5791810
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Table_ID, AD_Element_ID, AD_Reference_ID, ColumnName, Name, IsKey, IsMandatory, IsUpdateable, IsIdentifier, SeqNo, FieldLength, DefaultValue, EntityType, Version, PersonalDataCategory)
VALUES (592131, 0, 0, 'Y', TO_TIMESTAMP('2026-03-24 02:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-24 02:00', 'YYYY-MM-DD HH24:MI'), 0, 542583, 1047, 20, 'Processed', 'Verarbeitet', 'N', 'Y', 'Y', 'N', 0, 1, 'N', 'D', 0, 'NP')
ON CONFLICT (AD_Column_ID) DO NOTHING;

-- C_Order_ID column added by 5792870
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Table_ID, AD_Element_ID, AD_Reference_ID, ColumnName, Name, IsKey, IsMandatory, IsUpdateable, IsIdentifier, SeqNo, FieldLength, EntityType, Version, PersonalDataCategory)
VALUES (592205, 0, 0, 'Y', TO_TIMESTAMP('2026-03-24 02:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-24 02:00', 'YYYY-MM-DD HH24:MI'), 0, 542583, 558, 30, 'C_Order_ID', 'Auftrag', 'N', 'N', 'Y', 'N', 0, 10, 'D', 0, 'NP')
ON CONFLICT (AD_Column_ID) DO NOTHING;
