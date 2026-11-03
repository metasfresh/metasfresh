-- SysConfig Name: de.metas.shipping.WeightSourceTypes
-- Refresh the description so it matches the current HU weight semantics:
--   * HUWeightNet now holds the actual product net weight (no gross-as-net fallback);
--     when the product master has no net weight maintained, the listener does not derive WeightNet.
--   * HUWeightTara now also includes the per-CU product packaging delta
--     ((M_Product.GrossWeight - M_Product.Weight) * qty) on top of the packing-material weight.
-- Also fixes the "CatchWeigh" typo.
UPDATE AD_SysConfig
   SET Description = 'Specify how to calculate the package weight when creating the Shipping Package (M_Package).
Valid options are: ProductWeight, CatchWeight, HUWeightGross.

ProductWeight: Sum of CU product gross weight based on shipmentLine.
HUWeightGross: HUWeightGross = HUWeightNet (sum of CU product net weight; not derived when the product master has no net weight maintained) + HUWeightTara (sum of packing material product gross weight + per-CU product packaging delta ((GrossWeight - Weight) * qty)).
CatchWeight: Qty delivered in catch weight based on shipmentLine.',
       Updated = TO_TIMESTAMP('2026-04-29 10:00', 'YYYY-MM-DD HH24:MI'),
       UpdatedBy = 100
 WHERE AD_SysConfig_ID = 541757
;
