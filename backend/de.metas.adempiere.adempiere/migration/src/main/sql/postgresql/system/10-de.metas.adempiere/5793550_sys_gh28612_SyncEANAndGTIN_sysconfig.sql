-- gh#28612: Add sysconfig M_Product.SyncEANAndGTIN
-- When Y (default), GTIN/UPC/EAN_CU fields are synced automatically on M_Product and C_BPartner_Product.
-- When N, fields can be maintained independently.

INSERT INTO AD_SysConfig (AD_SysConfig_ID, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive,
                          Name, Value, Description, EntityType, ConfigurationLevel)
SELECT nextval('ad_sysconfig_seq'), 0, 0, now(), 100, now(), 100, 'Y',
       'M_Product.SyncEANAndGTIN', 'Y',
       'When Y, GTIN/UPC/EAN_CU fields are automatically synchronized on M_Product and C_BPartner_Product. When N, fields can be maintained independently.',
       'de.metas.product', 'O'
WHERE NOT EXISTS (SELECT 1 FROM AD_SysConfig WHERE Name = 'M_Product.SyncEANAndGTIN' AND AD_Client_ID = 0 AND AD_Org_ID = 0);
