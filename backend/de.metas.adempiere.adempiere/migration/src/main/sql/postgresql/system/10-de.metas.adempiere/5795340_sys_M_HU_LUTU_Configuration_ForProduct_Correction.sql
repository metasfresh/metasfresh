-- Name: M_HU_LUTU_Configuration_ForProduct
-- 2026-03-23T13:12:57.489Z
UPDATE AD_Val_Rule SET Code='M_HU_LUTU_Configuration.M_Product_ID=@M_Product_ID/-1@
 AND 
COALESCE (M_HU_LUTU_Configuration.QtyCUsPerTU, 1) * COALESCE (M_HU_LUTU_Configuration.QtyTU, 1) * COALESCE(M_HU_LUTU_Configuration.QtyLU, 1) = @QtyEntered/0@
',Updated=TO_TIMESTAMP('2026-03-23 13:12:57.489000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Val_Rule_ID=540396
;


