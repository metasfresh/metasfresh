

-- 2018-03-13T14:42:08.522
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP INDEX IF EXISTS m_inventoryline_productlocattr
;

-- 2018-03-13T14:42:08.523
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX m_inventoryline_productlocattr ON M_InventoryLine (M_Inventory_ID,M_Locator_ID,M_Product_ID,M_AttributeSetInstance_ID,M_InOutLine_ID,COALESCE(M_HU_ID,0))
;

