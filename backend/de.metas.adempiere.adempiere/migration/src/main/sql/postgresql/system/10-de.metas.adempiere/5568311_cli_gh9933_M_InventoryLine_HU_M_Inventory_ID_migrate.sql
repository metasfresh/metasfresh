UPDATE M_InventoryLine_HU invl_hu
SET m_inventory_id=invl.m_inventory_id
FROM m_inventoryline invl
WHERE invl.m_inventoryline_id = invl_hu.m_inventoryline_id
  AND invl_hu.m_inventory_id IS NULL;

