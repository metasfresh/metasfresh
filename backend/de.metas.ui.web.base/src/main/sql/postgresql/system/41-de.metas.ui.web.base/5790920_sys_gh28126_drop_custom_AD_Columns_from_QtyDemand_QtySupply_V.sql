-- Remove customer-specific AD_Columns from QtyDemand_QtySupply_V (AD_Table_ID=542218).
-- These columns (SupplyType, C_BPartner_Vendor_ID, DatePromised, QtyOnHand, QtyTU, QtyLU, WeightNet)
-- belong only in customer-specific views, not in the base table metadata.
-- The base view m_materialcockpit_base_v does not have these columns,
-- so their presence in AD_Column breaks the window on instances using the base view.

-- AD_Column IDs: 592070 (SupplyType), 592071 (C_BPartner_Vendor_ID), 592072 (DatePromised),
--                592073 (QtyOnHand), 592074 (QtyTU), 592075 (QtyLU), 592076 (WeightNet)

DELETE FROM AD_Column_Trl WHERE AD_Column_ID IN (592070, 592071, 592072, 592073, 592074, 592075, 592076);
DELETE FROM AD_Column WHERE AD_Column_ID IN (592070, 592071, 592072, 592073, 592074, 592075, 592076);
