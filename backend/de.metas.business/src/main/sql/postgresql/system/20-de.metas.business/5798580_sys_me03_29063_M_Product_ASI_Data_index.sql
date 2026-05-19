-- me03#29063: Performance index for M_Product_ASI_Data GTIN lookups
-- Narrows by product + bpartner; ASI matching happens via IsASIAttributesKeySubset function
CREATE INDEX IF NOT EXISTS m_product_asi_data_lookup
    ON m_product_asi_data (m_product_id, c_bpartner_id)
    WHERE isactive = 'Y';
