-- Carrier advise (manual): the GoodsType / Service validation rules must require the carrier-product
-- allocation in addition to the direct M_Shipper link, so only goods types / services that are actually
-- allocated to one of the shipper's carrier products are offered.
--
-- M_ShipmentSchedule_Advise_Manual lets the user pick a Carrier_Goods_Type and Carrier_Service(s) for a
-- shipper. So far the val rules offered EVERY goods type / service of the shipper
-- (Carrier_Goods_Type.M_Shipper_ID / Carrier_Service.M_Shipper_ID). But only those allocated to a carrier
-- product of the shipper are usable, so the rule must also match the allocation
-- (Carrier_Product_GoodsType_Alloc / Carrier_Product_Service_Alloc → Carrier_Product). Both the goods
-- type's / service's shipper AND the carrier product's shipper must match — an INNER JOIN (intersection),
-- not a UNION: the allocations are a subset of the direct-link set, so a union would not change anything.
--
-- Both rules are modified in place:
--   540750 Carrier_Goods_Type_ID_for_M_Shipper_ID — also used by AD_Column M_ShipmentSchedule.Carrier_Goods_Type_ID
--   540757 Carrier_Service_ID_for_M_Shipper_ID     — used only by this process' service params
-- Sharing rule 540750 with the M_ShipmentSchedule column is intentional: the allocation exists before a
-- goods type is put on the shipment schedule, so the column field should offer the same (allocated) set.

UPDATE AD_Val_Rule
SET Code='Carrier_Goods_Type_ID IN (SELECT cgt.Carrier_Goods_Type_ID FROM Carrier_Goods_Type cgt JOIN Carrier_Product_GoodsType_Alloc a ON a.Carrier_Goods_Type_ID = cgt.Carrier_Goods_Type_ID JOIN Carrier_Product cp ON cp.Carrier_Product_ID = a.Carrier_Product_ID WHERE cgt.M_Shipper_ID = @M_Shipper_ID/-1@ AND cp.M_Shipper_ID = @M_Shipper_ID/-1@)',
    Updated=TO_TIMESTAMP('2026-06-15 12:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100
WHERE AD_Val_Rule_ID=540750
;

UPDATE AD_Val_Rule
SET Code='Carrier_Service_ID IN (SELECT cs.Carrier_Service_ID FROM Carrier_Service cs JOIN Carrier_Product_Service_Alloc a ON a.Carrier_Service_ID = cs.Carrier_Service_ID JOIN Carrier_Product cp ON cp.Carrier_Product_ID = a.Carrier_Product_ID WHERE cs.M_Shipper_ID = @M_Shipper_ID/-1@ AND cp.M_Shipper_ID = @M_Shipper_ID/-1@)',
    Updated=TO_TIMESTAMP('2026-06-15 12:00:01','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100
WHERE AD_Val_Rule_ID=540757
;
