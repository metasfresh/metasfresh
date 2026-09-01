-- M_Delivery_Planning: filterability for the planner's working-list window (AD_Window 541632).
--
-- Four new ColumnSQL virtual columns. Each duplicates a rule the delivery-planning service also
-- applies in Java, so the two must be kept in step:
--   IsAllocated           -- 'Y' iff an ACTIVE M_Delivery_Planning_Alloc row references this
--                             planning.
--   IsDelivered           -- 'Y' iff M_InOut_ID is set. FilterDefaultValue='N', so the working-list
--                             filter opens pre-set to "not yet delivered".
--   ShipFrom_Location_ID  -- Incoming or Dropship -> the receipt schedule's C_BPartner_Location_ID;
--                             Outgoing -> the warehouse's.
--   ShipTo_Location_ID    -- Outgoing -> the shipment schedule's C_BPartner_Location_ID;
--                             Incoming or Dropship -> the warehouse's.
-- Neither location column's name follows the "<TargetTable>_ID" convention, so
-- AD_Reference_Value_ID must be set explicitly (159 = C_BPartner_Location).
--
-- The four new columns get no AD_Field and no AD_UI_Element: AD_Tab 546674 leaves
-- IncludeFiltersStrategy blank, and under that an AD_Column.IsSelectionColumn='Y' becomes a WebUI
-- filter straight off AD_Column.
--
-- Selection flags also flip to 'Y' on six existing physical columns a planner needs to filter the
-- working list by but currently cannot: M_Shipper_ID, C_Incoterms_ID, IncotermLocation,
-- M_MeansOfTransportation_ID, AD_Org_ID, IsClosed. Three of those were filterable but invisible in
-- the result grid; section 10 makes them visible.
--
-- IsAllocated gets a fresh AD_Element: the existing 'IsAllocated' element (1508) carries
-- payment-domain Description/Help shared with unrelated columns, so reusing it would either mislead
-- this column's tooltip or rewrite text those columns depend on. AD_Element.ColumnName is UNIQUE and
-- 1508 already holds 'IsAllocated', hence 'M_Delivery_Planning_IsAllocated' on the element while the
-- AD_Column itself stays plain 'IsAllocated'. IsDelivered reuses the generic element 367 as-is.
--
-- IDs allocated from idserver.metas.de on 2026-08-28:
--   AD_MigrationScript 5821150 (this file)
--   AD_Element 585384 (IsAllocated), 585385 (ShipFrom_Location_ID), 585386 (ShipTo_Location_ID)
--   AD_Column 593412 (IsAllocated), 593413 (IsDelivered), 593414 (ShipFrom_Location_ID),
--             593415 (ShipTo_Location_ID)
--   AD_SQLColumn_SourceTableColumn 540225..540229

-- ============================================================================
-- 1) AD_Element: IsAllocated (new)
-- ============================================================================
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName, Created, CreatedBy, Description, EntityType, Help, IsActive, Name, PrintName, Updated, UpdatedBy)
VALUES (0, 585384 /*From ID Server*/, 0, 'M_Delivery_Planning_IsAllocated', TO_TIMESTAMP('2026-08-28 09:00:00','YYYY-MM-DD HH24:MI:SS'), 100, 'Zeigt an, ob die Lieferplanung bereits einer Auslieferungsanweisung zugeordnet ist.', 'D', 'Zeigt an, ob die Lieferplanung bereits einer Auslieferungsanweisung zugeordnet ist.', 'Y', 'Zugeordnet', 'Zugeordnet', TO_TIMESTAMP('2026-08-28 09:00:00','YYYY-MM-DD HH24:MI:SS'), 100)
;

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, CommitWarning, Description, Help, Name, PO_Description, PO_Help, PO_Name, PO_PrintName, PrintName, WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning, t.Description, t.Help, t.Name, t.PO_Description, t.PO_Help, t.PO_Name, t.PO_PrintName, t.PrintName, t.WEBUI_NameBrowse, t.WEBUI_NameNew, t.WEBUI_NameNewBreadcrumb, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585384
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

UPDATE AD_Element_Trl SET Name='Allocated', PrintName='Allocated', Description='Indicates whether the delivery planning is already allocated to a delivery instruction.', Help='Indicates whether the delivery planning is already allocated to a delivery instruction.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-28 09:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=585384 AND AD_Language='en_US'
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585384,'en_US')
;

UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-28 09:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=585384 AND AD_Language='de_DE'
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585384,'de_DE')
;

UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-28 09:00:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=585384 AND AD_Language='de_CH'
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585384,'de_CH')
;

-- fr_CH per the convention stated once in
-- 5820520_sys_M_Delivery_Planning_GenerateDeliveryInstruction_IsComplete.sql: the en_US text,
-- IsTranslated='N'.
UPDATE AD_Element_Trl SET Name='Allocated', PrintName='Allocated', Description='Indicates whether the delivery planning is already allocated to a delivery instruction.', Help='Indicates whether the delivery planning is already allocated to a delivery instruction.', IsTranslated='N', Updated=TO_TIMESTAMP('2026-08-28 09:00:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=585384 AND AD_Language='fr_CH'
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585384,'fr_CH')
;

-- ============================================================================
-- 2) AD_Element: ShipFrom_Location_ID (new)
-- ============================================================================
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName, Created, CreatedBy, Description, EntityType, Help, IsActive, Name, PrintName, Updated, UpdatedBy)
VALUES (0, 585385 /*From ID Server*/, 0, 'ShipFrom_Location_ID', TO_TIMESTAMP('2026-08-28 09:00:10','YYYY-MM-DD HH24:MI:SS'), 100, 'Adresse, an der die Ware für diese Lieferplanung verladen wird.', 'D', 'Adresse, an der die Ware für diese Lieferplanung verladen wird.', 'Y', 'Verladeadresse', 'Verladeadresse', TO_TIMESTAMP('2026-08-28 09:00:10','YYYY-MM-DD HH24:MI:SS'), 100)
;

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, CommitWarning, Description, Help, Name, PO_Description, PO_Help, PO_Name, PO_PrintName, PrintName, WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning, t.Description, t.Help, t.Name, t.PO_Description, t.PO_Help, t.PO_Name, t.PO_PrintName, t.PrintName, t.WEBUI_NameBrowse, t.WEBUI_NameNew, t.WEBUI_NameNewBreadcrumb, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585385
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

UPDATE AD_Element_Trl SET Name='Loading Address', PrintName='Loading Address', Description='The address where goods are loaded for this delivery planning.', Help='The address where goods are loaded for this delivery planning.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-28 09:00:11','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=585385 AND AD_Language='en_US'
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585385,'en_US')
;

UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-28 09:00:12','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=585385 AND AD_Language='de_DE'
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585385,'de_DE')
;

UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-28 09:00:13','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=585385 AND AD_Language='de_CH'
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585385,'de_CH')
;

UPDATE AD_Element_Trl SET Name='Loading Address', PrintName='Loading Address', Description='The address where goods are loaded for this delivery planning.', Help='The address where goods are loaded for this delivery planning.', IsTranslated='N', Updated=TO_TIMESTAMP('2026-08-28 09:00:14','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=585385 AND AD_Language='fr_CH'
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585385,'fr_CH')
;

-- ============================================================================
-- 3) AD_Element: ShipTo_Location_ID (new)
-- ============================================================================
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName, Created, CreatedBy, Description, EntityType, Help, IsActive, Name, PrintName, Updated, UpdatedBy)
VALUES (0, 585386 /*From ID Server*/, 0, 'ShipTo_Location_ID', TO_TIMESTAMP('2026-08-28 09:00:20','YYYY-MM-DD HH24:MI:SS'), 100, 'Adresse, an die die Ware für diese Lieferplanung geliefert wird.', 'D', 'Adresse, an die die Ware für diese Lieferplanung geliefert wird.', 'Y', 'Lieferadresse', 'Lieferadresse', TO_TIMESTAMP('2026-08-28 09:00:20','YYYY-MM-DD HH24:MI:SS'), 100)
;

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, CommitWarning, Description, Help, Name, PO_Description, PO_Help, PO_Name, PO_PrintName, PrintName, WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning, t.Description, t.Help, t.Name, t.PO_Description, t.PO_Help, t.PO_Name, t.PO_PrintName, t.PrintName, t.WEBUI_NameBrowse, t.WEBUI_NameNew, t.WEBUI_NameNewBreadcrumb, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585386
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

UPDATE AD_Element_Trl SET Name='Delivery Address', PrintName='Delivery Address', Description='The address goods are delivered to for this delivery planning.', Help='The address goods are delivered to for this delivery planning.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-28 09:00:21','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=585386 AND AD_Language='en_US'
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585386,'en_US')
;

UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-28 09:00:22','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=585386 AND AD_Language='de_DE'
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585386,'de_DE')
;

UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-28 09:00:23','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=585386 AND AD_Language='de_CH'
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585386,'de_CH')
;

UPDATE AD_Element_Trl SET Name='Delivery Address', PrintName='Delivery Address', Description='The address goods are delivered to for this delivery planning.', Help='The address goods are delivered to for this delivery planning.', IsTranslated='N', Updated=TO_TIMESTAMP('2026-08-28 09:00:24','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=585386 AND AD_Language='fr_CH'
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585386,'fr_CH')
;

-- ============================================================================
-- 4) AD_Column: M_Delivery_Planning.IsAllocated (ColumnSQL)
-- ============================================================================
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID, ColumnName, ColumnSQL, Created, CreatedBy, Description, EntityType, FieldLength, FilterOperator, Help, IsActive, IsLazyLoading, IsSelectionColumn, IsUpdateable, Name, PersonalDataCategory, SelectionColumnSeqNo, Updated, UpdatedBy, Version)
VALUES (0, 593412 /*From ID Server*/, 585384, 0, 20, 542259, 'IsAllocated',
        '(case when exists (select 1 from m_delivery_planning_alloc a where a.m_delivery_planning_id = M_Delivery_Planning.M_Delivery_Planning_ID and a.isactive = ''Y'') then ''Y'' else ''N'' end)',
        TO_TIMESTAMP('2026-08-28 09:01:00','YYYY-MM-DD HH24:MI:SS'), 100,
        'Zeigt an, ob die Lieferplanung bereits einer Auslieferungsanweisung zugeordnet ist.', 'D', 1, 'E',
        'Zeigt an, ob die Lieferplanung bereits einer Auslieferungsanweisung zugeordnet ist.', 'Y', 'Y', 'Y', 'N',
        'Zugeordnet', 'NP', 220, TO_TIMESTAMP('2026-08-28 09:01:00','YYYY-MM-DD HH24:MI:SS'), 100, 0)
;

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593412
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
/* DDL */ select update_Column_Translation_From_AD_Element(585384)
;

-- ============================================================================
-- 5) AD_Column: M_Delivery_Planning.IsDelivered (ColumnSQL) -- reuses element 367
-- ============================================================================
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID, ColumnName, ColumnSQL, Created, CreatedBy, EntityType, FieldLength, FilterDefaultValue, FilterOperator, IsActive, IsLazyLoading, IsSelectionColumn, IsUpdateable, Name, PersonalDataCategory, SelectionColumnSeqNo, Updated, UpdatedBy, Version)
VALUES (0, 593413 /*From ID Server*/, 367, 0, 20, 542259, 'IsDelivered',
        '(case when M_Delivery_Planning.M_InOut_ID is not null then ''Y'' else ''N'' end)',
        TO_TIMESTAMP('2026-08-28 09:01:10','YYYY-MM-DD HH24:MI:SS'), 100, 'D', 1, 'N', 'E',
        'Y', 'N', 'Y', 'N',
        'Zugestellt', 'NP', 230, TO_TIMESTAMP('2026-08-28 09:01:10','YYYY-MM-DD HH24:MI:SS'), 100, 0)
;

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593413
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
/* DDL */ select update_Column_Translation_From_AD_Element(367)
;

-- ============================================================================
-- 6) AD_Column: M_Delivery_Planning.ShipFrom_Location_ID (ColumnSQL)
-- ============================================================================
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Reference_Value_ID, AD_Table_ID, ColumnName, ColumnSQL, Created, CreatedBy, Description, EntityType, FieldLength, FilterOperator, Help, IsActive, IsLazyLoading, IsSelectionColumn, IsUpdateable, Name, PersonalDataCategory, SelectionColumnSeqNo, Updated, UpdatedBy, Version)
VALUES (0, 593414 /*From ID Server*/, 585385, 0, 18, 159, 542259, 'ShipFrom_Location_ID',
        '(case when M_Delivery_Planning.TransportDirection in (''Incoming'',''Dropship'') then (select rs.c_bpartner_location_id from m_receiptschedule rs where rs.m_receiptschedule_id = M_Delivery_Planning.M_ReceiptSchedule_ID) else (select wh.c_bpartner_location_id from m_warehouse wh where wh.m_warehouse_id = M_Delivery_Planning.M_Warehouse_ID) end)',
        TO_TIMESTAMP('2026-08-28 09:01:20','YYYY-MM-DD HH24:MI:SS'), 100,
        'Adresse, an der die Ware für diese Lieferplanung verladen wird.', 'D', 10, 'E',
        'Adresse, an der die Ware für diese Lieferplanung verladen wird.', 'Y', 'Y', 'Y', 'N',
        'Verladeadresse', 'P', 240, TO_TIMESTAMP('2026-08-28 09:01:20','YYYY-MM-DD HH24:MI:SS'), 100, 0)
;

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593414
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
/* DDL */ select update_Column_Translation_From_AD_Element(585385)
;

-- ============================================================================
-- 7) AD_Column: M_Delivery_Planning.ShipTo_Location_ID (ColumnSQL)
-- ============================================================================
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Reference_Value_ID, AD_Table_ID, ColumnName, ColumnSQL, Created, CreatedBy, Description, EntityType, FieldLength, FilterOperator, Help, IsActive, IsLazyLoading, IsSelectionColumn, IsUpdateable, Name, PersonalDataCategory, SelectionColumnSeqNo, Updated, UpdatedBy, Version)
VALUES (0, 593415 /*From ID Server*/, 585386, 0, 18, 159, 542259, 'ShipTo_Location_ID',
        '(case when M_Delivery_Planning.TransportDirection = ''Outgoing'' then (select ss.c_bpartner_location_id from m_shipmentschedule ss where ss.m_shipmentschedule_id = M_Delivery_Planning.M_ShipmentSchedule_ID) else (select wh.c_bpartner_location_id from m_warehouse wh where wh.m_warehouse_id = M_Delivery_Planning.M_Warehouse_ID) end)',
        TO_TIMESTAMP('2026-08-28 09:01:30','YYYY-MM-DD HH24:MI:SS'), 100,
        'Adresse, an die die Ware für diese Lieferplanung geliefert wird.', 'D', 10, 'E',
        'Adresse, an die die Ware für diese Lieferplanung geliefert wird.', 'Y', 'Y', 'Y', 'N',
        'Lieferadresse', 'P', 250, TO_TIMESTAMP('2026-08-28 09:01:30','YYYY-MM-DD HH24:MI:SS'), 100, 0)
;

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593415
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
/* DDL */ select update_Column_Translation_From_AD_Element(585386)
;

-- ============================================================================
-- 8) AD_SQLColumn_SourceTableColumn -- cache-invalidation dependencies
-- ============================================================================

-- IsAllocated depends on M_Delivery_Planning_Alloc; the link column is the allocation's own FK.
INSERT INTO AD_SQLColumn_SourceTableColumn (AD_Client_ID, AD_SQLColumn_SourceTableColumn_ID, AD_Org_ID, AD_Table_ID, AD_Column_ID, Created, CreatedBy, FetchTargetRecordsMethod, IsActive, link_column_id, source_table_id, Updated, UpdatedBy)
VALUES (0, 540225 /*From ID Server*/, 0, 542259, 593412, TO_TIMESTAMP('2026-08-28 09:02:00','YYYY-MM-DD HH24:MI:SS'), 100, 'L', 'Y', 593397, 542641, TO_TIMESTAMP('2026-08-28 09:02:00','YYYY-MM-DD HH24:MI:SS'), 100)
;

-- ShipFrom_Location_ID depends on M_ReceiptSchedule and M_Warehouse.
INSERT INTO AD_SQLColumn_SourceTableColumn (AD_Client_ID, AD_SQLColumn_SourceTableColumn_ID, AD_Org_ID, AD_Table_ID, AD_Column_ID, Created, CreatedBy, FetchTargetRecordsMethod, IsActive, link_column_id, source_table_id, Updated, UpdatedBy)
VALUES (0, 540226 /*From ID Server*/, 0, 542259, 593414, TO_TIMESTAMP('2026-08-28 09:02:01','YYYY-MM-DD HH24:MI:SS'), 100, 'L', 'Y', 549487, 540524, TO_TIMESTAMP('2026-08-28 09:02:01','YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_SQLColumn_SourceTableColumn (AD_Client_ID, AD_SQLColumn_SourceTableColumn_ID, AD_Org_ID, AD_Table_ID, AD_Column_ID, Created, CreatedBy, FetchTargetRecordsMethod, IsActive, link_column_id, source_table_id, Updated, UpdatedBy)
VALUES (0, 540227 /*From ID Server*/, 0, 542259, 593414, TO_TIMESTAMP('2026-08-28 09:02:02','YYYY-MM-DD HH24:MI:SS'), 100, 'L', 'Y', 1151, 190, TO_TIMESTAMP('2026-08-28 09:02:02','YYYY-MM-DD HH24:MI:SS'), 100)
;

-- ShipTo_Location_ID depends on M_ShipmentSchedule and M_Warehouse.
INSERT INTO AD_SQLColumn_SourceTableColumn (AD_Client_ID, AD_SQLColumn_SourceTableColumn_ID, AD_Org_ID, AD_Table_ID, AD_Column_ID, Created, CreatedBy, FetchTargetRecordsMethod, IsActive, link_column_id, source_table_id, Updated, UpdatedBy)
VALUES (0, 540228 /*From ID Server*/, 0, 542259, 593415, TO_TIMESTAMP('2026-08-28 09:02:03','YYYY-MM-DD HH24:MI:SS'), 100, 'L', 'Y', 500232, 500221, TO_TIMESTAMP('2026-08-28 09:02:03','YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_SQLColumn_SourceTableColumn (AD_Client_ID, AD_SQLColumn_SourceTableColumn_ID, AD_Org_ID, AD_Table_ID, AD_Column_ID, Created, CreatedBy, FetchTargetRecordsMethod, IsActive, link_column_id, source_table_id, Updated, UpdatedBy)
VALUES (0, 540229 /*From ID Server*/, 0, 542259, 593415, TO_TIMESTAMP('2026-08-28 09:02:04','YYYY-MM-DD HH24:MI:SS'), 100, 'L', 'Y', 1151, 190, TO_TIMESTAMP('2026-08-28 09:02:04','YYYY-MM-DD HH24:MI:SS'), 100)
;

-- ============================================================================
-- 9) Selection flags on six existing physical columns (already fielded on AD_Tab 546674)
-- ============================================================================
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=160, Updated=TO_TIMESTAMP('2026-08-28 09:03:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Column_ID=585490 /* M_Shipper_ID */
;
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=170, Updated=TO_TIMESTAMP('2026-08-28 09:03:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Column_ID=585008 /* C_Incoterms_ID */
;
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=180, Updated=TO_TIMESTAMP('2026-08-28 09:03:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Column_ID=585445 /* IncotermLocation */
;
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=190, Updated=TO_TIMESTAMP('2026-08-28 09:03:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Column_ID=585306 /* M_MeansOfTransportation_ID */
;
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=200, Updated=TO_TIMESTAMP('2026-08-28 09:03:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Column_ID=584993 /* AD_Org_ID */
;
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=210, Updated=TO_TIMESTAMP('2026-08-28 09:03:05','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Column_ID=585272 /* IsClosed */
;

-- ============================================================================
-- 10) Grid visibility for the three filterable columns that were filter-only (AD_Tab 546674).
--     SeqNoGrid: M_MeansOfTransportation_ID/40 and IsClosed/50 sit next to TransportDirection/30
--     and ahead of Incoterms/60; AD_Org_ID/390 goes last, as the window design rules require for
--     Organisation.
-- ============================================================================
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40, Updated=TO_TIMESTAMP('2026-08-28 09:04:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=613935 /* M_MeansOfTransportation_ID */
;
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50, Updated=TO_TIMESTAMP('2026-08-28 09:04:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=613916 /* IsClosed */
;
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=390, Updated=TO_TIMESTAMP('2026-08-28 09:04:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=613478 /* AD_Org_ID */
;
