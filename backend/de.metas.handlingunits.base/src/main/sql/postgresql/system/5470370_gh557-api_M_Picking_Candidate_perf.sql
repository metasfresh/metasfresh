
CREATE INDEX IF NOT EXISTS m_picking_candidate_hu
   ON m_picking_candidate (m_hu_id ASC NULLS LAST);
CREATE INDEX IF NOT EXISTS m_picking_candidate_m_pickingslot
   ON m_picking_candidate (m_pickingslot_id ASC NULLS LAST);
CREATE INDEX IF NOT EXISTS m_picking_candidate_m_shipmentschedule
   ON m_picking_candidate (m_shipmentschedule_id ASC NULLS LAST);
