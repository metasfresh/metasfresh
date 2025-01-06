DROP INDEX IF EXISTS m_hu_trace_lotnumber_index
;

CREATE INDEX m_hu_trace_lotnumber_index
    ON m_hu_trace (lotnumber)
;



DROP INDEX IF EXISTS m_hu_trace_m_product_id_index
;

CREATE INDEX m_hu_trace_m_product_id_index
    ON m_hu_trace (m_product_id)
;