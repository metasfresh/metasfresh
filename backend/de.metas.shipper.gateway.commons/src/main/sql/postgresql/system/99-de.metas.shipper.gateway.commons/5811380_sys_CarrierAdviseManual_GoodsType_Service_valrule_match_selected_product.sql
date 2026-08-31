-- Carrier advise (manual): the GoodsType / Service validation rules must cascade from the SELECTED
-- Carrier_Product, not only the shipper. 5807930 made them require an allocation to *some* carrier product
-- of the shipper (INNER JOIN to Carrier_Product_GoodsType_Alloc / Carrier_Product_Service_Alloc), but the
-- WHERE only matched @M_Shipper_ID@ — so a goods type / service allocated to a DIFFERENT product of the same
-- shipper was still offered. Add "a.Carrier_Product_ID = @Carrier_Product_ID/-1@" so only goods types /
-- services allocated to the carrier product the user actually picked are offered.
--
-- Both rules updated in place (same two rules 5807930 touched):
--   540750 Carrier_Goods_Type_ID_for_M_Shipper_ID — also used by AD_Column M_ShipmentSchedule.Carrier_Goods_Type_ID.
--     There @Carrier_Product_ID@ resolves to the schedule's own Carrier_Product_ID, so the field cascades to the
--     schedule's carrier product too (with /-1 → empty list until a product is set). Intended: goods type must match
--     the chosen product.
--   540757 Carrier_Service_ID_for_M_Shipper_ID     — used only by this process' service params.

UPDATE AD_Val_Rule
SET Code='Carrier_Goods_Type_ID IN (SELECT cgt.Carrier_Goods_Type_ID FROM Carrier_Goods_Type cgt JOIN Carrier_Product_GoodsType_Alloc a ON a.Carrier_Goods_Type_ID = cgt.Carrier_Goods_Type_ID JOIN Carrier_Product cp ON cp.Carrier_Product_ID = a.Carrier_Product_ID WHERE cgt.M_Shipper_ID = @M_Shipper_ID/-1@ AND cp.M_Shipper_ID = @M_Shipper_ID/-1@ AND a.Carrier_Product_ID = @Carrier_Product_ID/-1@)',
    Updated=TO_TIMESTAMP('2026-07-02 12:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100
WHERE AD_Val_Rule_ID=540750
;

UPDATE AD_Val_Rule
SET Code='Carrier_Service_ID IN (SELECT cs.Carrier_Service_ID FROM Carrier_Service cs JOIN Carrier_Product_Service_Alloc a ON a.Carrier_Service_ID = cs.Carrier_Service_ID JOIN Carrier_Product cp ON cp.Carrier_Product_ID = a.Carrier_Product_ID WHERE cs.M_Shipper_ID = @M_Shipper_ID/-1@ AND cp.M_Shipper_ID = @M_Shipper_ID/-1@ AND a.Carrier_Product_ID = @Carrier_Product_ID/-1@)',
    Updated=TO_TIMESTAMP('2026-07-02 12:00:01','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100
WHERE AD_Val_Rule_ID=540757
;
