DROP INDEX IF EXISTS m_hu_reservation_c_orderlineso_id;
DROP INDEX IF EXISTS m_hu_reservation_c_projectline_id;

CREATE INDEX m_hu_reservation_c_orderlineso_id ON m_hu_reservation (c_orderlineso_id) 
	WHERE c_orderlineso_id is not null;
CREATE INDEX m_hu_reservation_c_projectline_id ON m_hu_reservation (c_projectline_id, c_project_id)
	WHERE c_projectline_id is not null and c_project_id is not null;

