DROP INDEX IF EXISTS m_hu_qrcode_aggregate_hu_item_id
;

DROP INDEX IF EXISTS m_hu_qrcode_hu
;

CREATE INDEX m_hu_qrcode_hu
    ON m_hu_qrcode (m_hu_id)
    WHERE (m_hu_id IS NOT NULL)
;



