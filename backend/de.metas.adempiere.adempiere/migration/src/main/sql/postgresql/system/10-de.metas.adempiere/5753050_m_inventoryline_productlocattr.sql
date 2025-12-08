-- Adding C_UOM_ID to the already existing unique index
drop index if exists m_inventoryline_productlocattr;
CREATE UNIQUE INDEX m_inventoryline_productlocattr
    ON m_inventoryline (m_inventory_id, m_locator_id, m_product_id, c_uom_id, m_attributesetinstance_id, m_inoutline_id, COALESCE(m_hu_id, 0::numeric))
;

