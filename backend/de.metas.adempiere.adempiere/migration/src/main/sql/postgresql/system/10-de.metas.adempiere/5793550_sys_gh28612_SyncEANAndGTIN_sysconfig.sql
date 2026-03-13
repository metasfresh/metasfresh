-- gh#28612: Add sysconfig M_Product.SyncEANAndGTIN
-- When Y (default), GTIN/UPC/EAN_CU fields are synced automatically on M_Product and C_BPartner_Product.
-- When N, fields can be maintained independently.

INSERT INTO AD_SysConfig (AD_SysConfig_ID, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, Name, Value, Description, EntityType, ConfigurationLevel)
VALUES (541799, 0, 0, TO_TIMESTAMP('2026-03-10 11:11:11.000000', 'YYYY-MM-DD HH24:MI:SS.US'), 100, TO_TIMESTAMP('2026-03-10 11:11:11.000000', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'Y', 'M_Product.SyncEANAndGTIN', 'Y', 'When Y, GTIN/UPC/EAN_CU fields are automatically synchronized on M_Product and C_BPartner_Product. When N, fields can be maintained independently.', 'de.metas.product', 'O')
;
