-- The two colour names the delivery planning's status colour is resolved from. Until now they existed
-- only as hardcoded fallbacks ('Gruen' / 'Rot') behind a sysconfig lookup that had no row to read, so
-- set_sysconfig_value could not be used to change them and the SQL side had nothing to read at all.
-- Seeded here with exactly those fallback values.
--
-- Value is an AD_Color.Name; '-' means "no colour for that state".
--
-- ConfigurationLevel='S' (system) is deliberate rather than 'O': the lookup resolves the name by
-- sysconfig NAME alone, so a second row for the same name under another client/org would make the
-- resolution ambiguous.
--
-- GUARD ON Name ALONE - THE ONLY CORRECT WIDTH, and NOT the same criterion as the unique index.
-- ad_sysconfig_unique is a UNIQUE index on (AD_Client_ID, AD_Org_ID, Name) WHERE IsActive='Y'.
-- Matching that index is the criterion for "this INSERT will not abort the migration run"; it is NOT
-- the criterion for "the value resolves deterministically afterwards". The reader is
-- get_sysconfig_value(), whose body is
--     SELECT c.value, ad_sysconfig_id INTO v_value, v_sysconfig_id
--       FROM ad_sysconfig c WHERE c.name = get_sysconfig_value.name;
-- - no IsActive filter, no client/org predicate, no ORDER BY, no LIMIT. A plain PL/pgSQL SELECT INTO
-- matching several rows does not raise: it silently keeps an arbitrary one. So an index-shaped guard
-- lets exactly the rows through that break it - a same-name row that is INACTIVE at (0,0), or one at
-- any other client/org - the seed inserts a SECOND row beside it, and from then on the delivered
-- colour depends on heap order, plan choice, or the next VACUUM, with no error anywhere. Reproduced on
-- the delivery-planning stack inside a rolled-back transaction: with an inactive '-' row planted first,
-- the index-shaped guard produced two rows and every delivered planning rendered with no colour at all.
-- The guard below therefore covers every row the RESOLVER can see, which is strictly wider than the
-- index: the seed can never add a second row of a name that already exists anywhere.
--
-- RECLAIM instead of insert-beside, when the only row of that name is INACTIVE. Such a row is a
-- RETIRED configuration: the Java lookup this change replaces (SysConfigDAO.retrieveSysConfigEntryValues,
-- "WHERE IsActive='Y'") never saw it and fell back to the hardcoded 'Gruen' / 'Rot', so the colour that
-- instance actually shows today is the fallback - which is exactly what this script seeds. Adopting the
-- retired row (value reset, reactivated) keeps that resolved colour AND leaves exactly one row for the
-- resolver; leaving it in place would silently promote a retired setting to the live one. It fires only
-- when that name has exactly ONE row and it is inactive, so it can never collide with ad_sysconfig_unique.
--
-- An ACTIVE row is left alone in every case - it is the operator's live configuration, which the Java
-- lookup honoured too, and not ours to overwrite. An instance already carrying two or more rows of one
-- name was ambiguous before this script and stays that way; the guard only guarantees this script does
-- not add to it.
--
-- backup_table because AD_SysConfig holds operator-configurable data and the reclaim step UPDATEs it.
--
-- IDs allocated from idserver.metas.de on 2026-09-04:
--   AD_SysConfig 541852, 541853

SELECT backup_table('ad_sysconfig', '_gh31789_delivery_status_colour_names')
;

-- --------------------------------------------------------------------------------------------
-- M_DeliveryPlanning.DeliveredColorName
-- --------------------------------------------------------------------------------------------
UPDATE AD_SysConfig
SET Value              = 'Gruen',
    Description        = 'Name der AD_Color, mit der eine gelieferte Lieferplanung im Raster eingefärbt wird. "-" bedeutet keine Farbe.',
    EntityType         = 'D',
    ConfigurationLevel = 'S',
    IsActive           = 'Y',
    Updated            = TO_TIMESTAMP('2026-09-04 10:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy          = 100
WHERE Name = 'M_DeliveryPlanning.DeliveredColorName'
  AND IsActive = 'N'
  AND (SELECT count(*) FROM AD_SysConfig x WHERE x.Name = 'M_DeliveryPlanning.DeliveredColorName') = 1
;

INSERT INTO AD_SysConfig
    (AD_SysConfig_ID, AD_Client_ID, AD_Org_ID,
     Name, Value, Description,
     EntityType, ConfigurationLevel,
     IsActive,
     Created, CreatedBy, Updated, UpdatedBy)
SELECT
    541852 /*From ID Server*/, 0, 0,
    'M_DeliveryPlanning.DeliveredColorName',
    'Gruen',
    'Name der AD_Color, mit der eine gelieferte Lieferplanung im Raster eingefärbt wird. "-" bedeutet keine Farbe.',
    'D', 'S',
    'Y',
    TO_TIMESTAMP('2026-09-04 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-09-04 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100
WHERE NOT EXISTS (
    SELECT 1 FROM AD_SysConfig existing
    WHERE existing.Name = 'M_DeliveryPlanning.DeliveredColorName')
;

-- --------------------------------------------------------------------------------------------
-- M_DeliveryPlanning.NotDeliveredColorName
-- --------------------------------------------------------------------------------------------
UPDATE AD_SysConfig
SET Value              = 'Rot',
    Description        = 'Name der AD_Color, mit der eine noch nicht gelieferte Lieferplanung im Raster eingefärbt wird. "-" bedeutet keine Farbe.',
    EntityType         = 'D',
    ConfigurationLevel = 'S',
    IsActive           = 'Y',
    Updated            = TO_TIMESTAMP('2026-09-04 10:00:02', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy          = 100
WHERE Name = 'M_DeliveryPlanning.NotDeliveredColorName'
  AND IsActive = 'N'
  AND (SELECT count(*) FROM AD_SysConfig x WHERE x.Name = 'M_DeliveryPlanning.NotDeliveredColorName') = 1
;

INSERT INTO AD_SysConfig
    (AD_SysConfig_ID, AD_Client_ID, AD_Org_ID,
     Name, Value, Description,
     EntityType, ConfigurationLevel,
     IsActive,
     Created, CreatedBy, Updated, UpdatedBy)
SELECT
    541853 /*From ID Server*/, 0, 0,
    'M_DeliveryPlanning.NotDeliveredColorName',
    'Rot',
    'Name der AD_Color, mit der eine noch nicht gelieferte Lieferplanung im Raster eingefärbt wird. "-" bedeutet keine Farbe.',
    'D', 'S',
    'Y',
    TO_TIMESTAMP('2026-09-04 10:00:03', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-09-04 10:00:03', 'YYYY-MM-DD HH24:MI:SS'), 100
WHERE NOT EXISTS (
    SELECT 1 FROM AD_SysConfig existing
    WHERE existing.Name = 'M_DeliveryPlanning.NotDeliveredColorName')
;
