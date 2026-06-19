DROP INDEX IF EXISTS m_hu_reservation_c_orderlineso_id;
DROP INDEX IF EXISTS m_hu_reservation_c_projectline_id;

CREATE INDEX m_hu_reservation_c_orderlineso_id ON m_hu_reservation (c_orderlineso_id) ;
CREATE INDEX m_hu_reservation_c_projectline_id ON m_hu_reservation (c_projectline_id, c_project_id);

