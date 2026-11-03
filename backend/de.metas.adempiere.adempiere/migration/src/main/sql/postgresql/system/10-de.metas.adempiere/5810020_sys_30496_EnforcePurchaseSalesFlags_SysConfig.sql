-- Add SysConfig gate for product IsPurchased/IsSold enforcement.
-- When Y, the system enforces IsPurchased/IsSold flags on order lines and related lookups.
-- When N (default), enforcement is skipped — preserving legacy behaviour for all tenants
-- that have not explicitly opted in.

INSERT INTO AD_SysConfig (AD_Client_ID, AD_Org_ID, AD_SysConfig_ID, ConfigurationLevel,
                          Created, CreatedBy, Updated, UpdatedBy,
                          EntityType, IsActive, Name, Value, Description)
VALUES (0, 0, 541826 /*From ID Server*/, 'O',
        TO_TIMESTAMP('2026-06-29 12:00', 'YYYY-MM-DD HH24:MI'), 100,
        TO_TIMESTAMP('2026-06-29 12:00', 'YYYY-MM-DD HH24:MI'), 100,
        'D', 'Y',
        'M_Product_EnforcePurchaseSalesFlags',
        'N',
        'When Y, IsPurchased and IsSold flags are enforced on order lines and product lookups. Default N preserves legacy behaviour for tenants that have not opted in.')
;
