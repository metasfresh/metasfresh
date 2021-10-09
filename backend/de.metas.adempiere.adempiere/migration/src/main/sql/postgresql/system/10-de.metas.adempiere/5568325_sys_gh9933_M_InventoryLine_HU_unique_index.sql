DROP INDEX IF EXISTS M_InventoryLine_HU_UQ;
CREATE UNIQUE INDEX M_InventoryLine_HU_UQ
    ON m_inventoryline_hu (m_inventory_id, m_hu_id)
    WHERE m_hu_id IS NOT NULL;
