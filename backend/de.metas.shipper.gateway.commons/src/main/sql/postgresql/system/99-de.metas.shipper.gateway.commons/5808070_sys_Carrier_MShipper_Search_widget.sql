-- M_Shipper_ID lookup widget on the carrier master tables (Carrier_Product / Carrier_Goods_Type /
-- Carrier_Service): use Search (AD_Reference_ID=30) instead of TableDir (19), matching how M_Shipper_ID
-- is referenced elsewhere. The columns themselves are defined in the already-integrated 5773340.

UPDATE AD_Column SET AD_Reference_ID=30,
    Updated=TO_TIMESTAMP('2026-06-16 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID=591349 /*Carrier_Product.M_Shipper_ID*/;

UPDATE AD_Column SET AD_Reference_ID=30,
    Updated=TO_TIMESTAMP('2026-06-16 09:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID=591313 /*Carrier_Goods_Type.M_Shipper_ID*/;

UPDATE AD_Column SET AD_Reference_ID=30,
    Updated=TO_TIMESTAMP('2026-06-16 09:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID=591324 /*Carrier_Service.M_Shipper_ID*/;
