-- Remove customer-specific AD_Columns from QtyDemand_QtySupply_V (AD_Table_ID=542218)
-- only if no AD_Fields reference them (safe for customer repos that use these columns).
-- These columns (SupplyType, C_BPartner_Vendor_ID, DatePromised, QtyOnHand, QtyTU, QtyLU, WeightNet)
-- belong only in customer-specific views, not in the base table metadata.
-- The base view m_materialcockpit_base_v does not have these columns,
-- so their presence in AD_Column breaks the window on instances using the base view.

-- AD_Column IDs: 592070 (SupplyType), 592071 (C_BPartner_Vendor_ID), 592072 (DatePromised),
--                592073 (QtyOnHand), 592074 (QtyTU), 592075 (QtyLU), 592076 (WeightNet)

SELECT backup_table('AD_Column');
SELECT backup_table('AD_Column_Trl');

DELETE FROM AD_Column_Trl
WHERE AD_Column_ID IN (592070, 592071, 592072, 592073, 592074, 592075, 592076)
  AND NOT EXISTS (SELECT 1 FROM AD_Field f WHERE f.AD_Column_ID = AD_Column_Trl.AD_Column_ID);

DELETE FROM AD_Column
WHERE AD_Column_ID IN (592070, 592071, 592072, 592073, 592074, 592075, 592076)
  AND NOT EXISTS (SELECT 1 FROM AD_Field f WHERE f.AD_Column_ID = AD_Column.AD_Column_ID);
