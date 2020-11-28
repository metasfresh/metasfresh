

-- 2018-03-12T17:54:25.273
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX m_inventoryline_productlocattr ON M_InventoryLine (M_Inventory_ID,M_Locator_ID,M_Product_ID,M_AttributeSetInstance_ID,M_InOutLine_ID,M_HU_ID)
;

