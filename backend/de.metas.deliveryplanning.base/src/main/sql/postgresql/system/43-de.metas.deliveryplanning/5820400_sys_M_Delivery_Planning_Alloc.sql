-- M_Delivery_Planning_Alloc: links a delivery planning to the delivery instruction it travels on,
-- together with the shipping package that carries it. A pure link -- it holds NO quantity: a
-- planning is one load fully allocated to one instruction, so product / quantity / UOM stay on
-- M_ShippingPackage. The allocation references its M_ShippingPackage; neither M_ShippingPackage nor
-- M_Package carries a planning reference.
-- Removal retires the row (IsActive='N' plus a one-time DateRemoved stamp, never re-dated) instead
-- of deleting it: the retired row is what the instruction's read-only history tab renders.
-- LineNo orders the allocations of one instruction, assigned in tens (10/20/30 ...).
--
-- Any stack that applied this script BEFORE the de_DE/de_CH IsTranslated fix needs this once --
-- update_TRL_Tables_On_AD_Element_TRL_Update is guarded by c_trl.updated <> e_trl.updated, which
-- the first apply already equalised, so the flag alone does not propagate:
--   UPDATE AD_Element_Trl SET IsTranslated='Y'
--    WHERE AD_Element_ID=585382 AND AD_Language IN ('de_DE','de_CH');
--   UPDATE AD_Column_Trl t SET Updated = t.Updated - interval '1 second'
--     FROM AD_Column c WHERE c.AD_Column_ID=t.AD_Column_ID AND c.AD_Element_ID=585382
--       AND t.AD_Language IN ('de_DE','de_CH');
--   UPDATE AD_Field_Trl t SET Updated = t.Updated - interval '1 second'
--     FROM AD_Field f JOIN AD_Column c ON c.AD_Column_ID=f.AD_Column_ID
--    WHERE f.AD_Field_ID=t.AD_Field_ID AND c.AD_Element_ID=585382
--      AND t.AD_Language IN ('de_DE','de_CH');
--   SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585382,'de_DE');
--   SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585382,'de_CH');
--
-- IDs allocated from idserver.metas.de on 2026-08-26:
--   AD_Table   542641  (M_Delivery_Planning_Alloc)
--   AD_Element 585382  (M_Delivery_Planning_Alloc_ID -- PK element, new)
--   AD_Column  593396  (M_Delivery_Planning_Alloc.M_Delivery_Planning_Alloc_ID -- PK)
--   AD_Column  593397  (M_Delivery_Planning_Alloc.M_Delivery_Planning_ID      -- reuses element 581677)
--   AD_Column  593398  (M_Delivery_Planning_Alloc.M_ShipperTransportation_ID  -- reuses element 540089)
--   AD_Column  593399  (M_Delivery_Planning_Alloc.M_ShippingPackage_ID        -- reuses element 540097)
--   AD_Column  593400  (M_Delivery_Planning_Alloc.LineNo                      -- reuses element 2945)
--   AD_Column  593403  (M_Delivery_Planning_Alloc.AD_Client_ID                -- reuses element 102)
--   AD_Column  593404  (M_Delivery_Planning_Alloc.AD_Org_ID                   -- reuses element 113)
--   AD_Column  593405  (M_Delivery_Planning_Alloc.IsActive                    -- reuses element 348)
--   AD_Column  593406  (M_Delivery_Planning_Alloc.Created                     -- reuses element 245)
--   AD_Column  593407  (M_Delivery_Planning_Alloc.CreatedBy                   -- reuses element 246)
--   AD_Column  593408  (M_Delivery_Planning_Alloc.Updated                     -- reuses element 607)
--   AD_Column  593409  (M_Delivery_Planning_Alloc.UpdatedBy                   -- reuses element 608)

-- ===========================================================================
-- 1. Physical table
-- ===========================================================================
CREATE TABLE IF NOT EXISTS M_Delivery_Planning_Alloc
(
    M_Delivery_Planning_Alloc_ID NUMERIC(10)              NOT NULL,
    M_Delivery_Planning_ID       NUMERIC(10)              NOT NULL,
    M_ShipperTransportation_ID   NUMERIC(10)              NOT NULL,
    M_ShippingPackage_ID         NUMERIC(10)              NOT NULL,
    LineNo                       NUMERIC(10)              NOT NULL,
    AD_Client_ID                 NUMERIC(10)              NOT NULL,
    AD_Org_ID                    NUMERIC(10)              NOT NULL,
    IsActive                     CHAR(1)                  NOT NULL DEFAULT 'Y',
    Created                      TIMESTAMP WITH TIME ZONE NOT NULL,
    CreatedBy                    NUMERIC(10)              NOT NULL,
    Updated                      TIMESTAMP WITH TIME ZONE NOT NULL,
    UpdatedBy                    NUMERIC(10)              NOT NULL,
    CONSTRAINT M_Delivery_Planning_Alloc_Key PRIMARY KEY (M_Delivery_Planning_Alloc_ID),
    CONSTRAINT MDeliveryPlanning_MDeliveryPlanningAlloc
        FOREIGN KEY (M_Delivery_Planning_ID) REFERENCES public.M_Delivery_Planning DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT MShipperTransportation_MDeliveryPlanningAlloc
        FOREIGN KEY (M_ShipperTransportation_ID) REFERENCES public.M_ShipperTransportation DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT MShippingPackage_MDeliveryPlanningAlloc
        FOREIGN KEY (M_ShippingPackage_ID) REFERENCES public.M_ShippingPackage DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT M_Delivery_Planning_Alloc_IsActive_Check CHECK (IsActive IN ('Y', 'N'))
);

-- ===========================================================================
-- 2. AD_Table
-- ===========================================================================
INSERT INTO AD_Table
    (AD_Table_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, TableName, IsView, AccessLevel, EntityType,
     ImportTable, IsDeleteable, IsHighVolume, IsChangeLog,
     ReplicationType, IsSecurityEnabled, LoadSeq, PersonalDataCategory)
VALUES
    (542641 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-08-26 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-26 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Lieferplanung-Zuordnung', 'M_Delivery_Planning_Alloc', 'N', '3', 'D',
     'N', 'Y', 'N', 'Y',
     'L', 'N', 0, 'NP');

-- ===========================================================================
-- 3. AD_Element -- M_Delivery_Planning_Alloc_ID (PK, new)
-- ===========================================================================
INSERT INTO AD_Element
    (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     ColumnName, Name, PrintName, Description, Help, EntityType)
VALUES
    (585382 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-08-26 10:00:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-26 10:00:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'M_Delivery_Planning_Alloc_ID',
     'Lieferplanung-Zuordnung',
     'Lieferplanung-Zuordnung',
     'Zuordnung einer Lieferplanung zu einer Lieferanweisung.',
     NULL,
     'D');

INSERT INTO AD_Element_Trl
    (AD_Language, AD_Element_ID, IsTranslated,
     Name, PrintName, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 585382 /*From ID Server*/, 'N',
       e.Name, e.PrintName, e.Description, e.Help,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-08-26 10:00:11', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-08-26 10:00:11', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Element e
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y'
  AND e.AD_Element_ID = 585382
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = 585382);

-- de_DE / de_CH carry the authored German verbatim, i.e. the row IS correct for that language,
-- so IsTranslated='Y'. The flag marks "this row's text is right for this language", NOT
-- "somebody translated it" -- setBaseLanguage() (de.metas.business ddl/functions/SetBaseLanguage.sql)
-- materialises the outgoing base language's rows with 'Y' for exactly that reason.
UPDATE AD_Element_Trl
SET    Name = 'Lieferplanung-Zuordnung', PrintName = 'Lieferplanung-Zuordnung',
       Description = 'Zuordnung einer Lieferplanung zu einer Lieferanweisung.',
       IsTranslated = 'Y',
       Updated = TO_TIMESTAMP('2026-08-26 10:00:12', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 585382 AND AD_Language = 'de_DE';

UPDATE AD_Element_Trl
SET    Name = 'Lieferplanung-Zuordnung', PrintName = 'Lieferplanung-Zuordnung',
       Description = 'Zuordnung einer Lieferplanung zu einer Lieferanweisung.',
       IsTranslated = 'Y',
       Updated = TO_TIMESTAMP('2026-08-26 10:00:13', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 585382 AND AD_Language = 'de_CH';

UPDATE AD_Element_Trl
SET    Name = 'Delivery Planning Allocation', PrintName = 'Delivery Planning Allocation',
       Description = 'Allocation of a delivery planning to a delivery instruction.',
       IsTranslated = 'Y',
       Updated = TO_TIMESTAMP('2026-08-26 10:00:14', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 585382 AND AD_Language = 'en_US';

-- ===========================================================================
-- 4. AD_Column -- M_Delivery_Planning_Alloc_ID (PK, AD_Reference_ID=13 = ID)
-- ===========================================================================
INSERT INTO AD_Column
    (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Table_ID, ColumnName, FieldLength,
     AD_Reference_ID, IsKey, IsParent, IsMandatory,
     IsTranslated, IsIdentifier, IsEncrypted, IsUpdateable, IsAlwaysUpdateable,
     IsSelectionColumn, IsSyncDatabase,
     EntityType, AD_Element_ID, Version, PersonalDataCategory)
VALUES
    (593396 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-08-26 10:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-26 10:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Lieferplanung-Zuordnung',
     542641 /*From ID Server*/, 'M_Delivery_Planning_Alloc_ID', 10,
     13, 'Y', 'N', 'Y',
     'N', 'N', 'N', 'N', 'N',
     'N', 'N',
     'D', 585382 /*From ID Server*/, 0, 'NP');

INSERT INTO AD_Column_Trl
    (AD_Language, AD_Column_ID, IsTranslated, Name,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 593396 /*From ID Server*/, 'N', t.Name,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-08-26 10:01:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-08-26 10:01:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Column_ID = 593396
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = 593396);

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585382 /*M_Delivery_Planning_Alloc_ID element*/);

-- ===========================================================================
-- 5. AD_Column -- M_Delivery_Planning_ID (FK -> M_Delivery_Planning, Search)
--    Reuses existing AD_Element 581677 (M_Delivery_Planning_ID / Lieferplanung).
--    IsParent='N': the allocation is reached from the instruction, not from the planning.
-- ===========================================================================
INSERT INTO AD_Column
    (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Table_ID, ColumnName, FieldLength,
     AD_Reference_ID, IsKey, IsParent, IsMandatory,
     IsTranslated, IsIdentifier, IsEncrypted, IsUpdateable, IsAlwaysUpdateable,
     IsSelectionColumn, IsSyncDatabase,
     EntityType, AD_Element_ID, Version, PersonalDataCategory)
VALUES
    (593397 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-08-26 10:01:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-26 10:01:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Lieferplanung',
     542641 /*From ID Server*/, 'M_Delivery_Planning_ID', 10,
     30, 'N', 'N', 'Y',
     'N', 'N', 'N', 'Y', 'N',
     'N', 'N',
     'D', 581677 /*existing: M_Delivery_Planning_ID element*/, 0, 'NP');

INSERT INTO AD_Column_Trl
    (AD_Language, AD_Column_ID, IsTranslated, Name,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 593397 /*From ID Server*/, 'N', t.Name,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-08-26 10:01:11', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-08-26 10:01:11', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Column_ID = 593397
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = 593397);

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(581677 /*M_Delivery_Planning_ID element*/);

-- ===========================================================================
-- 6. AD_Column -- M_ShipperTransportation_ID (FK -> M_ShipperTransportation, Search)
--    Reuses existing AD_Element 540089 (M_ShipperTransportation_ID / Transport Auftrag).
-- ===========================================================================
INSERT INTO AD_Column
    (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Table_ID, ColumnName, FieldLength,
     AD_Reference_ID, IsKey, IsParent, IsMandatory,
     IsTranslated, IsIdentifier, IsEncrypted, IsUpdateable, IsAlwaysUpdateable,
     IsSelectionColumn, IsSyncDatabase,
     EntityType, AD_Element_ID, Version, PersonalDataCategory)
VALUES
    (593398 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-08-26 10:01:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-26 10:01:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Transport Auftrag',
     542641 /*From ID Server*/, 'M_ShipperTransportation_ID', 10,
     30, 'N', 'N', 'Y',
     'N', 'N', 'N', 'Y', 'N',
     'N', 'N',
     'D', 540089 /*existing: M_ShipperTransportation_ID element*/, 0, 'NP');

INSERT INTO AD_Column_Trl
    (AD_Language, AD_Column_ID, IsTranslated, Name,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 593398 /*From ID Server*/, 'N', t.Name,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-08-26 10:01:21', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-08-26 10:01:21', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Column_ID = 593398
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = 593398);

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(540089 /*M_ShipperTransportation_ID element*/);

-- ===========================================================================
-- 7. AD_Column -- M_ShippingPackage_ID (FK -> M_ShippingPackage, Search)
--    Reuses existing AD_Element 540097 (M_ShippingPackage_ID / Versandpaket).
--    Mandatory: an allocated planning always has exactly one package on the instruction.
-- ===========================================================================
INSERT INTO AD_Column
    (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Table_ID, ColumnName, FieldLength,
     AD_Reference_ID, IsKey, IsParent, IsMandatory,
     IsTranslated, IsIdentifier, IsEncrypted, IsUpdateable, IsAlwaysUpdateable,
     IsSelectionColumn, IsSyncDatabase,
     EntityType, AD_Element_ID, Version, PersonalDataCategory)
VALUES
    (593399 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-08-26 10:01:30', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-26 10:01:30', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Versandpaket',
     542641 /*From ID Server*/, 'M_ShippingPackage_ID', 10,
     30, 'N', 'N', 'Y',
     'N', 'N', 'N', 'Y', 'N',
     'N', 'N',
     'D', 540097 /*existing: M_ShippingPackage_ID element*/, 0, 'NP');

INSERT INTO AD_Column_Trl
    (AD_Language, AD_Column_ID, IsTranslated, Name,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 593399 /*From ID Server*/, 'N', t.Name,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-08-26 10:01:31', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-08-26 10:01:31', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Column_ID = 593399
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = 593399);

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(540097 /*M_ShippingPackage_ID element*/);

-- ===========================================================================
-- 8. AD_Column -- LineNo (AD_Reference_ID=11 = Integer)
--    Reuses existing AD_Element 2945 (LineNo / Position). Assigned in tens per instruction,
--    mirroring the C_Customs_Invoice_Line.LineNo default.
-- ===========================================================================
INSERT INTO AD_Column
    (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Table_ID, ColumnName, FieldLength,
     AD_Reference_ID, IsKey, IsParent, IsMandatory, DefaultValue,
     IsTranslated, IsIdentifier, IsEncrypted, IsUpdateable, IsAlwaysUpdateable,
     IsSelectionColumn, IsSyncDatabase,
     EntityType, AD_Element_ID, Version, PersonalDataCategory)
VALUES
    (593400 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-08-26 10:01:40', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-26 10:01:40', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Position',
     542641 /*From ID Server*/, 'LineNo', 14,
     11, 'N', 'N', 'Y',
     '@SQL=SELECT COALESCE(MAX(LineNo),0)+10 AS DefaultValue FROM M_Delivery_Planning_Alloc WHERE M_ShipperTransportation_ID=@M_ShipperTransportation_ID@',
     'N', 'N', 'N', 'Y', 'N',
     'N', 'N',
     'D', 2945 /*existing: LineNo element*/, 0, 'NP');

INSERT INTO AD_Column_Trl
    (AD_Language, AD_Column_ID, IsTranslated, Name,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 593400 /*From ID Server*/, 'N', t.Name,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-08-26 10:01:41', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-08-26 10:01:41', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Column_ID = 593400
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = 593400);

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(2945 /*LineNo element*/);

-- ===========================================================================
-- 9. AD_Column -- AD_Client_ID (reuses element 102, AD_Reference_ID=19 = TableDir)
-- ===========================================================================
INSERT INTO AD_Column
    (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Table_ID, ColumnName, FieldLength,
     AD_Reference_ID, IsKey, IsParent, IsMandatory,
     IsTranslated, IsIdentifier, IsEncrypted, IsUpdateable, IsAlwaysUpdateable,
     IsSelectionColumn, IsSyncDatabase,
     EntityType, AD_Element_ID, Version, PersonalDataCategory)
VALUES
    (593403 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-08-26 10:02:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-26 10:02:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Mandant',
     542641 /*From ID Server*/, 'AD_Client_ID', 10,
     19, 'N', 'N', 'Y',
     'N', 'N', 'N', 'N', 'N',
     'N', 'N',
     'D', 102 /*existing: AD_Client_ID element*/, 0, 'NP');

INSERT INTO AD_Column_Trl
    (AD_Language, AD_Column_ID, IsTranslated, Name,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 593403 /*From ID Server*/, 'N', t.Name,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-08-26 10:02:11', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-08-26 10:02:11', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Column_ID = 593403
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = 593403);

-- ===========================================================================
-- 10. AD_Column -- AD_Org_ID (reuses element 113, AD_Reference_ID=30 = Search)
-- ===========================================================================
INSERT INTO AD_Column
    (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Table_ID, ColumnName, FieldLength,
     AD_Reference_ID, IsKey, IsParent, IsMandatory,
     IsTranslated, IsIdentifier, IsEncrypted, IsUpdateable, IsAlwaysUpdateable,
     IsSelectionColumn, IsSyncDatabase,
     EntityType, AD_Element_ID, Version, PersonalDataCategory)
VALUES
    (593404 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-08-26 10:02:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-26 10:02:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Sektion',
     542641 /*From ID Server*/, 'AD_Org_ID', 10,
     30, 'N', 'N', 'Y',
     'N', 'N', 'N', 'N', 'N',
     'N', 'N',
     'D', 113 /*existing: AD_Org_ID element*/, 0, 'NP');

INSERT INTO AD_Column_Trl
    (AD_Language, AD_Column_ID, IsTranslated, Name,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 593404 /*From ID Server*/, 'N', t.Name,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-08-26 10:02:21', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-08-26 10:02:21', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Column_ID = 593404
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = 593404);

-- ===========================================================================
-- 11. AD_Column -- IsActive (reuses element 348, AD_Reference_ID=20 = YesNo)
-- ===========================================================================
INSERT INTO AD_Column
    (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Table_ID, ColumnName, FieldLength,
     AD_Reference_ID, IsKey, IsParent, IsMandatory, DefaultValue,
     IsTranslated, IsIdentifier, IsEncrypted, IsUpdateable, IsAlwaysUpdateable,
     IsSelectionColumn, IsSyncDatabase,
     EntityType, AD_Element_ID, Version, PersonalDataCategory)
VALUES
    (593405 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-08-26 10:02:30', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-26 10:02:30', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Aktiv',
     542641 /*From ID Server*/, 'IsActive', 1,
     20, 'N', 'N', 'Y', 'Y',
     'N', 'N', 'N', 'Y', 'N',
     'N', 'N',
     'D', 348 /*existing: IsActive element*/, 0, 'NP');

INSERT INTO AD_Column_Trl
    (AD_Language, AD_Column_ID, IsTranslated, Name,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 593405 /*From ID Server*/, 'N', t.Name,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-08-26 10:02:31', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-08-26 10:02:31', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Column_ID = 593405
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = 593405);

-- ===========================================================================
-- 12. AD_Column -- Created (reuses element 245, AD_Reference_ID=16 = DateTime)
-- ===========================================================================
INSERT INTO AD_Column
    (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Table_ID, ColumnName, FieldLength,
     AD_Reference_ID, IsKey, IsParent, IsMandatory,
     IsTranslated, IsIdentifier, IsEncrypted, IsUpdateable, IsAlwaysUpdateable,
     IsSelectionColumn, IsSyncDatabase,
     EntityType, AD_Element_ID, Version, PersonalDataCategory)
VALUES
    (593406 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-08-26 10:02:40', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-26 10:02:40', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Erstellt',
     542641 /*From ID Server*/, 'Created', 29,
     16, 'N', 'N', 'Y',
     'N', 'N', 'N', 'N', 'N',
     'N', 'N',
     'D', 245 /*existing: Created element*/, 0, 'NP');

INSERT INTO AD_Column_Trl
    (AD_Language, AD_Column_ID, IsTranslated, Name,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 593406 /*From ID Server*/, 'N', t.Name,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-08-26 10:02:41', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-08-26 10:02:41', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Column_ID = 593406
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = 593406);

-- ===========================================================================
-- 13. AD_Column -- CreatedBy (reuses element 246, AD_Reference_ID=18, ref_value=110)
-- ===========================================================================
INSERT INTO AD_Column
    (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Table_ID, ColumnName, FieldLength,
     AD_Reference_ID, AD_Reference_Value_ID, IsKey, IsParent, IsMandatory,
     IsTranslated, IsIdentifier, IsEncrypted, IsUpdateable, IsAlwaysUpdateable,
     IsSelectionColumn, IsSyncDatabase,
     EntityType, AD_Element_ID, Version, PersonalDataCategory)
VALUES
    (593407 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-08-26 10:02:50', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-26 10:02:50', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Erstellt durch',
     542641 /*From ID Server*/, 'CreatedBy', 10,
     18, 110, 'N', 'N', 'Y',
     'N', 'N', 'N', 'N', 'N',
     'N', 'N',
     'D', 246 /*existing: CreatedBy element*/, 0, 'NP');

INSERT INTO AD_Column_Trl
    (AD_Language, AD_Column_ID, IsTranslated, Name,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 593407 /*From ID Server*/, 'N', t.Name,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-08-26 10:02:51', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-08-26 10:02:51', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Column_ID = 593407
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = 593407);

-- ===========================================================================
-- 14. AD_Column -- Updated (reuses element 607, AD_Reference_ID=16 = DateTime)
-- ===========================================================================
INSERT INTO AD_Column
    (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Table_ID, ColumnName, FieldLength,
     AD_Reference_ID, IsKey, IsParent, IsMandatory,
     IsTranslated, IsIdentifier, IsEncrypted, IsUpdateable, IsAlwaysUpdateable,
     IsSelectionColumn, IsSyncDatabase,
     EntityType, AD_Element_ID, Version, PersonalDataCategory)
VALUES
    (593408 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-08-26 10:03:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-26 10:03:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Aktualisiert',
     542641 /*From ID Server*/, 'Updated', 29,
     16, 'N', 'N', 'Y',
     'N', 'N', 'N', 'N', 'N',
     'N', 'N',
     'D', 607 /*existing: Updated element*/, 0, 'NP');

INSERT INTO AD_Column_Trl
    (AD_Language, AD_Column_ID, IsTranslated, Name,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 593408 /*From ID Server*/, 'N', t.Name,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-08-26 10:03:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-08-26 10:03:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Column_ID = 593408
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = 593408);

-- ===========================================================================
-- 15. AD_Column -- UpdatedBy (reuses element 608, AD_Reference_ID=18, ref_value=110)
-- ===========================================================================
INSERT INTO AD_Column
    (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Table_ID, ColumnName, FieldLength,
     AD_Reference_ID, AD_Reference_Value_ID, IsKey, IsParent, IsMandatory,
     IsTranslated, IsIdentifier, IsEncrypted, IsUpdateable, IsAlwaysUpdateable,
     IsSelectionColumn, IsSyncDatabase,
     EntityType, AD_Element_ID, Version, PersonalDataCategory)
VALUES
    (593409 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-08-26 10:03:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-26 10:03:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Aktualisiert durch',
     542641 /*From ID Server*/, 'UpdatedBy', 10,
     18, 110, 'N', 'N', 'Y',
     'N', 'N', 'N', 'N', 'N',
     'N', 'N',
     'D', 608 /*existing: UpdatedBy element*/, 0, 'NP');

INSERT INTO AD_Column_Trl
    (AD_Language, AD_Column_ID, IsTranslated, Name,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 593409 /*From ID Server*/, 'N', t.Name,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-08-26 10:03:11', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-08-26 10:03:11', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Column_ID = 593409
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = 593409);

-- ===========================================================================
-- 16. Backfill any missing translations for the new table rows
-- ===========================================================================
SELECT add_missing_translations();
