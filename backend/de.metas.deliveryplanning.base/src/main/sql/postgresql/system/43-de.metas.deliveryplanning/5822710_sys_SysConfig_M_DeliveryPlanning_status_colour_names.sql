-- The two colour names the delivery planning's status colour is resolved from. Until now they existed
-- only as hardcoded fallbacks ('Gruen' / 'Rot') behind a sysconfig lookup that had no row to read, so
-- set_sysconfig_value could not be used to change them and the SQL side had nothing to read at all.
-- Seeded here with exactly those fallback values, so the resolved colour is unchanged on every
-- instance while becoming configurable through the standard sysconfig window.
--
-- Value is an AD_Color.Name; '-' means "no colour for that state".
--
-- ConfigurationLevel='S' (system) is deliberate rather than 'O': the lookup resolves the name by
-- sysconfig NAME alone, so a second row for the same name under another client/org would make the
-- resolution ambiguous.
--
-- CREATE-IF-ABSENT, and it must stay that way. The names were always meant to be operator-settable,
-- so an instance may already carry a hand-created row for one of them - and ad_sysconfig_unique
-- (a UNIQUE index on (AD_Client_ID, AD_Org_ID, Name) WHERE IsActive='Y') would abort the whole
-- migration run on such an instance. The guard therefore matches that index exactly, IsActive='Y'
-- included, and an existing row is left alone: it is the operator's own configuration, not ours to
-- overwrite.
--
-- IDs allocated from idserver.metas.de on 2026-09-04:
--   AD_SysConfig 541852, 541853

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
    TO_TIMESTAMP('2026-09-04 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-09-04 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100
WHERE NOT EXISTS (
    SELECT 1 FROM AD_SysConfig existing
    WHERE existing.Name = 'M_DeliveryPlanning.DeliveredColorName'
      AND existing.AD_Client_ID = 0
      AND existing.AD_Org_ID = 0
      AND existing.IsActive = 'Y')
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
    TO_TIMESTAMP('2026-09-04 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-09-04 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100
WHERE NOT EXISTS (
    SELECT 1 FROM AD_SysConfig existing
    WHERE existing.Name = 'M_DeliveryPlanning.NotDeliveredColorName'
      AND existing.AD_Client_ID = 0
      AND existing.AD_Org_ID = 0
      AND existing.IsActive = 'Y')
;
