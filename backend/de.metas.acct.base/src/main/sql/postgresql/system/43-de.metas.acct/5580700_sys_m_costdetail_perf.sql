
CREATE INDEX IF NOT EXISTS m_costdetail_m_inventoryline_id
    ON m_costdetail (m_inventoryline_id)
;

CREATE INDEX IF NOT EXISTS m_costdetail_c_invoiceline_id
    ON m_costdetail (c_invoiceline_id)
;

CREATE INDEX IF NOT EXISTS m_costdetail_c_orderline_id
    ON m_costdetail (c_orderline_id)
;

CREATE INDEX IF NOT EXISTS m_costdetail_m_movementline_id
    ON m_costdetail (m_movementline_id)
;
