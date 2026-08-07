-- Master on/off switch for automatic BPartner debtor/creditor number generation.
-- Default 'N' (off): the C_BPartner interceptor early-exits without any per-save sysconfig-branch
-- lookups unless an org/client turns this on. ConfigurationLevel 'O' so it can be enabled per org
-- (or client). Shipped OFF; activated where wanted.
INSERT INTO AD_SysConfig (AD_Client_ID, AD_Org_ID, AD_SysConfig_ID, ConfigurationLevel,
                          Created, CreatedBy, Updated, UpdatedBy,
                          EntityType, IsActive, Name, Value, Description)
SELECT 0, 0, 541847 /*From ID Server*/, 'O',
       TO_TIMESTAMP('2026-08-06 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-08-06 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
       'D', 'Y',
       'de.metas.bpartner.NumberGeneration_Enabled',
       'N',
       'Master on/off switch for automatic debtor/creditor number generation. Default N. Set Y (per org or client) to activate; the C_BPartner interceptor is a no-op while it is N.'
WHERE NOT EXISTS (
    SELECT 1 FROM AD_SysConfig WHERE Name = 'de.metas.bpartner.NumberGeneration_Enabled'
);
