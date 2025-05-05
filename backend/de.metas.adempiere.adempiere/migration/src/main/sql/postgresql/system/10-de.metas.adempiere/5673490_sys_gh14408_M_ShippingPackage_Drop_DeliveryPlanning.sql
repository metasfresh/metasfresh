-- Column: M_ShippingPackage.M_Delivery_Planning_ID
-- 2023-01-25T14:00:24.445Z
UPDATE AD_Column SET TechnicalNote='t',Updated=TO_TIMESTAMP('2023-01-25 16:00:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585600
;

-- Column: M_ShippingPackage.M_Delivery_Planning_ID
-- 2023-01-25T14:02:03.983Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585600
;

-- 2023-01-25T14:02:03.989Z
DELETE FROM AD_Column WHERE AD_Column_ID=585600
;






SELECT public.db_alter_table('M_ShippingPackage','ALTER TABLE public.M_ShippingPackage DROP COLUMN M_Delivery_Planning_ID')
;