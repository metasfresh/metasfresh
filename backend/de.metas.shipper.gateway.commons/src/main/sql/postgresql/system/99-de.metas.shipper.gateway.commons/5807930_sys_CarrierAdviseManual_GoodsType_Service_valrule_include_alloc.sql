-- Carrier advise (manual): broaden the GoodsType / Service validation rules to also include the
-- carrier products' goods-type / service allocations, in addition to the direct M_Shipper link.
--
-- M_ShipmentSchedule_Advise_Manual lets the user pick a Carrier_Goods_Type and Carrier_Service(s) for a
-- shipper. The val rules so far only offered the goods types / services directly linked to the shipper
-- (Carrier_Goods_Type.M_Shipper_ID / Carrier_Service.M_Shipper_ID). Goods types and services are also
-- linked to the shipper's carrier products via the allocation tables (Carrier_Product_GoodsType_Alloc /
-- Carrier_Product_Service_Alloc → Carrier_Product.M_Shipper_ID); those must be selectable too.
--
-- Both rules are modified in place (UNION the allocation source):
--   540750 Carrier_Goods_Type_ID_for_M_Shipper_ID — also used by AD_Column M_ShipmentSchedule.Carrier_Goods_Type_ID
--   540757 Carrier_Service_ID_for_M_Shipper_ID     — used only by this process' service params
-- Sharing rule 540750 with the M_ShipmentSchedule column is intentional: the allocation exists before a
-- goods type is put on the shipment schedule, so the column field should offer the same set.

UPDATE AD_Val_Rule
SET Code='Carrier_Goods_Type_ID IN (SELECT cgt.Carrier_Goods_Type_ID FROM Carrier_Goods_Type cgt WHERE cgt.M_Shipper_ID = @M_Shipper_ID/-1@ UNION SELECT a.Carrier_Goods_Type_ID FROM Carrier_Product_GoodsType_Alloc a JOIN Carrier_Product cp ON cp.Carrier_Product_ID = a.Carrier_Product_ID WHERE cp.M_Shipper_ID = @M_Shipper_ID/-1@)',
    Updated=TO_TIMESTAMP('2026-06-15 12:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100
WHERE AD_Val_Rule_ID=540750
;

UPDATE AD_Val_Rule
SET Code='Carrier_Service_ID IN (SELECT cs.Carrier_Service_ID FROM Carrier_Service cs WHERE cs.M_Shipper_ID = @M_Shipper_ID/-1@ UNION SELECT a.Carrier_Service_ID FROM Carrier_Product_Service_Alloc a JOIN Carrier_Product cp ON cp.Carrier_Product_ID = a.Carrier_Product_ID WHERE cp.M_Shipper_ID = @M_Shipper_ID/-1@)',
    Updated=TO_TIMESTAMP('2026-06-15 12:00:01','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100
WHERE AD_Val_Rule_ID=540757
;
