DROP INDEX IF EXISTS m_hu_qrcode_uniqueid
;

CREATE UNIQUE INDEX m_hu_qrcode_uniqueid ON m_hu_qrcode (uniqueid)
;


DROP INDEX IF EXISTS m_hu_qrcode_hu
;

CREATE UNIQUE INDEX m_hu_qrcode_hu ON m_hu_qrcode (m_hu_id) where m_hu_id is not null
;

DROP INDEX IF EXISTS m_hu_qrcode_aggregate_hu_item_id
;

CREATE UNIQUE INDEX m_hu_qrcode_aggregate_hu_item_id ON m_hu_qrcode (aggregate_hu_item_id) where aggregate_hu_item_id is not null
;
