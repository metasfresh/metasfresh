-- Run mode: SWING_CLIENT

-- SysConfig Name: de.metas.shipping.WeightSourceTypes
-- SysConfig Value: ProductWeight
-- 2025-07-30T10:03:48.579Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', Description='Specify how to calculate the package weight when creating the Shipping Package (M_Package).
Valid options are: ProductWeight, CatchWeight, HUWeightGross.

Product weight: Sum of CU product gross weight based on shipmentLine
HUWeightGross: HUWeightGross = HUWeightNet (Sum of CU product gross weight or net if not set) + HUWeightTara (Sum of packing material product gross weight)
CatchWeigh: qty delivered in catch weight based on shipmentLine',Updated=TO_TIMESTAMP('2025-07-30 10:03:48.577000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_SysConfig_ID=541757
;

