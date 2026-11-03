CREATE INDEX if NOT EXISTS idx_hu_trace_lot_prod_type
    ON M_HU_Trace(lotnumber, m_product_id, hutracetype);
