-- Run mode: SWING_CLIENT

-- SysConfig Name: FallbackToBasePricelist
-- SysConfig Value: Y
-- 2026-03-31T15:09:25.593Z
INSERT INTO AD_SysConfig (AD_Client_ID, AD_Org_ID, AD_SysConfig_ID, ConfigurationLevel, Created, CreatedBy, Description, EntityType, IsActive, Name, Updated, UpdatedBy, Value)
VALUES (0, 0, 541802, 'O', TO_TIMESTAMP('2026-03-31 15:09:23.737000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 'If set to ''Y'', the order quick input will show products having prices in either the current price list version or the base price list.
If set to ''N'' the order quick input will show only products having prices in only the current price list version.', 'D', 'Y', 'FallbackToBasePricelist', TO_TIMESTAMP('2026-03-31 15:09:23.737000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 'Y')
;

