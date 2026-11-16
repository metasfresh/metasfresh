-- Indexes on M_Delivery_Planning_Alloc, declared as AD_Index_Table + AD_Index_Column so a unique
-- violation surfaces the translated ErrorMsg instead of a raw duplicate-key error. The physical
-- index name MUST equal AD_Index_Table.Name -- that is the key the violation is mapped back on.
--
-- IDs allocated from idserver.metas.de on 2026-08-26:
--   AD_Index_Table  540869  (M_Delivery_Planning_Alloc_Planning_UQ)
--   AD_Index_Table  540870  (M_Delivery_Planning_Alloc_Instruction)
--   AD_Index_Table  540871  (M_Delivery_Planning_Alloc_Package_UQ)
--   AD_Index_Column 541538  (Planning_UQ    -> AD_Column 593397 M_Delivery_Planning_ID)
--   AD_Index_Column 541539  (Instruction    -> AD_Column 593398 M_ShipperTransportation_ID)
--   AD_Index_Column 541540  (Package_UQ     -> AD_Column 593399 M_ShippingPackage_ID)
-- Referenced existing IDs: AD_Table 542641 (M_Delivery_Planning_Alloc).

-- ===========================================================================
-- 1. M_Delivery_Planning_Alloc_Planning_UQ -- one active allocation per planning
-- ===========================================================================
INSERT INTO AD_Index_Table
    (AD_Index_Table_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Table_ID, IsUnique, WhereClause, Processing,
     EntityType, Description, ErrorMsg)
VALUES
    (540869 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-08-26 11:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-26 11:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'M_Delivery_Planning_Alloc_Planning_UQ',
     542641 /*existing: M_Delivery_Planning_Alloc*/, 'Y', 'IsActive=''Y''', 'N',
     'D',
     'One active allocation per delivery planning: a planning can only travel on one delivery instruction at a time.',
     'Diese Lieferplanung ist bereits einer Lieferanweisung zugeordnet.');

INSERT INTO AD_Index_Table_Trl
    (AD_Language, AD_Index_Table_ID, IsTranslated, ErrorMsg, Description,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Index_Table_ID, 'N', t.ErrorMsg, t.Description,
       t.AD_Client_ID, t.AD_Org_ID, 'Y',
       TO_TIMESTAMP('2026-08-26 11:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-08-26 11:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Index_Table t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Index_Table_ID = 540869
  AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Index_Table_ID = t.AD_Index_Table_ID);

UPDATE AD_Index_Table_Trl
SET ErrorMsg     = 'Diese Lieferplanung ist bereits einer Lieferanweisung zugeordnet.',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-08-26 11:00:02', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Index_Table_ID = 540869 AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Index_Table_Trl
SET ErrorMsg     = 'This delivery planning is already assigned to a delivery instruction.',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-08-26 11:00:03', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Index_Table_ID = 540869 AND AD_Language = 'en_US';

INSERT INTO AD_Index_Column
    (AD_Index_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     AD_Index_Table_ID, AD_Column_ID, SeqNo, EntityType)
VALUES
    (541538 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-08-26 11:00:04', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-26 11:00:04', 'YYYY-MM-DD HH24:MI:SS'), 100,
     540869 /*From ID Server*/, 593397 /*existing: M_Delivery_Planning_ID*/, 10, 'D');

CREATE UNIQUE INDEX M_Delivery_Planning_Alloc_Planning_UQ
    ON M_Delivery_Planning_Alloc (M_Delivery_Planning_ID)
    WHERE IsActive='Y';

-- ===========================================================================
-- 2. M_Delivery_Planning_Alloc_Instruction -- all allocations of one instruction
-- ===========================================================================
INSERT INTO AD_Index_Table
    (AD_Index_Table_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Table_ID, IsUnique, WhereClause, Processing,
     EntityType, Description, ErrorMsg)
VALUES
    (540870 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-08-26 11:00:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-26 11:00:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'M_Delivery_Planning_Alloc_Instruction',
     542641 /*existing: M_Delivery_Planning_Alloc*/, 'N', NULL, 'N',
     'D',
     'All allocations of one delivery instruction. Unfiltered on purpose: the void cascade must also see deactivated rows.',
     NULL);

INSERT INTO AD_Index_Table_Trl
    (AD_Language, AD_Index_Table_ID, IsTranslated, ErrorMsg, Description,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Index_Table_ID, 'N', t.ErrorMsg, t.Description,
       t.AD_Client_ID, t.AD_Org_ID, 'Y',
       TO_TIMESTAMP('2026-08-26 11:00:11', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-08-26 11:00:11', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Index_Table t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Index_Table_ID = 540870
  AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Index_Table_ID = t.AD_Index_Table_ID);

INSERT INTO AD_Index_Column
    (AD_Index_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     AD_Index_Table_ID, AD_Column_ID, SeqNo, EntityType)
VALUES
    (541539 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-08-26 11:00:12', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-26 11:00:12', 'YYYY-MM-DD HH24:MI:SS'), 100,
     540870 /*From ID Server*/, 593398 /*existing: M_ShipperTransportation_ID*/, 10, 'D');

CREATE INDEX M_Delivery_Planning_Alloc_Instruction
    ON M_Delivery_Planning_Alloc (M_ShipperTransportation_ID);

-- ===========================================================================
-- 3. M_Delivery_Planning_Alloc_Package_UQ -- one shipping package per allocation
-- ===========================================================================
INSERT INTO AD_Index_Table
    (AD_Index_Table_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Table_ID, IsUnique, WhereClause, Processing,
     EntityType, Description, ErrorMsg)
VALUES
    (540871 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-08-26 11:00:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-26 11:00:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'M_Delivery_Planning_Alloc_Package_UQ',
     542641 /*existing: M_Delivery_Planning_Alloc*/, 'Y', 'IsActive=''Y''', 'N',
     'D',
     'One shipping package per active allocation. Holds only while a package is exactly one LU/TU; drop this index once a package becomes a container shared by several plannings.',
     'Dieses Packstück ist bereits einer anderen Lieferplanung zugeordnet.');

INSERT INTO AD_Index_Table_Trl
    (AD_Language, AD_Index_Table_ID, IsTranslated, ErrorMsg, Description,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Index_Table_ID, 'N', t.ErrorMsg, t.Description,
       t.AD_Client_ID, t.AD_Org_ID, 'Y',
       TO_TIMESTAMP('2026-08-26 11:00:21', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-08-26 11:00:21', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Index_Table t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Index_Table_ID = 540871
  AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Index_Table_ID = t.AD_Index_Table_ID);

UPDATE AD_Index_Table_Trl
SET ErrorMsg     = 'Dieses Packstück ist bereits einer anderen Lieferplanung zugeordnet.',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-08-26 11:00:22', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Index_Table_ID = 540871 AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Index_Table_Trl
SET ErrorMsg     = 'This shipping package is already assigned to another delivery planning.',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-08-26 11:00:23', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Index_Table_ID = 540871 AND AD_Language = 'en_US';

INSERT INTO AD_Index_Column
    (AD_Index_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     AD_Index_Table_ID, AD_Column_ID, SeqNo, EntityType)
VALUES
    (541540 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-08-26 11:00:24', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-26 11:00:24', 'YYYY-MM-DD HH24:MI:SS'), 100,
     540871 /*From ID Server*/, 593399 /*existing: M_ShippingPackage_ID*/, 10, 'D');

CREATE UNIQUE INDEX M_Delivery_Planning_Alloc_Package_UQ
    ON M_Delivery_Planning_Alloc (M_ShippingPackage_ID)
    WHERE IsActive='Y';

-- ===========================================================================
-- 4. Backfill any missing translations for the new AD_Index_Table rows
-- ===========================================================================
SELECT add_missing_translations();
