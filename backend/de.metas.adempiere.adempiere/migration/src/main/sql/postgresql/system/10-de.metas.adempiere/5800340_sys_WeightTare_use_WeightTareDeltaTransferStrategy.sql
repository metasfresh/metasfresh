-- Register the WeightTareDeltaTransferStrategy java class and switch every M_HU_PI_Attribute
-- record for the WeightTare attribute from the previous SkipHUAttributeTransferStrategy
-- (AD_JavaClass_ID=540026) to the new strategy (AD_JavaClass_ID=540099).
--
-- The new strategy moves only the per-CU packaging delta `(Product.GrossWeight - Product.NetWeight) * qty`
-- between source and destination on a VHU-to-VHU transfer. The packing-material portion of WeightTare
-- (Karton, Pallet, ...) is intentionally NOT touched: packing material is indivisible per physical
-- container and stays where the container is. BOTU sum on the source HU's children re-aggregates
-- packing-material weight automatically once the qty has moved.
--
-- Why not RedistributeQty: redistributing the source's full WeightTare proportionally to qty would
-- mathematically split the indivisible Karton (e.g. 0.5 of a 1 kg Karton ending up on each side of
-- a 5-of-10 PCE split). Per-CU delta-only avoids that.

INSERT INTO AD_JavaClass (
    AD_Client_ID, AD_Org_ID, AD_JavaClass_ID, AD_JavaClass_Type_ID,
    Classname, Name, EntityType, IsActive, IsInterface,
    Created, CreatedBy, Updated, UpdatedBy)
VALUES (
    0, 0, 540099 /*From ID Server*/, 540036,
    'de.metas.handlingunits.attribute.strategy.impl.WeightTareDeltaTransferStrategy',
    'WeightTareDeltaTransferStrategy', 'D', 'Y', 'N',
    TO_TIMESTAMP('2026-04-29 12:00','YYYY-MM-DD HH24:MI'), 100,
    TO_TIMESTAMP('2026-04-29 12:00','YYYY-MM-DD HH24:MI'), 100)
;

UPDATE M_HU_PI_Attribute
   SET HU_TansferStrategy_JavaClass_ID = 540099,
       Updated = TO_TIMESTAMP('2026-04-29 12:00','YYYY-MM-DD HH24:MI'),
       UpdatedBy = 99
 WHERE HU_TansferStrategy_JavaClass_ID = 540026
   AND M_Attribute_ID IN (SELECT M_Attribute_ID FROM M_Attribute WHERE Value = 'WeightTare')
;
