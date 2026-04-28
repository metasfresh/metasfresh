
DROP INDEX IF EXISTS m_shipmentschedule_qtypicked_tus;
DROP INDEX IF EXISTS m_shipmentschedule_qtypicked_m_tu_hu_id;

CREATE INDEX IF NOT EXISTS m_shipmentschedule_qtypicked_m_tu_hu_id
  ON public.m_shipmentschedule_qtypicked
  USING btree
  (m_tu_hu_id);
  
CREATE INDEX IF NOT EXISTS m_shipmentschedule_qtypicked_vhu_id
  ON public.m_shipmentschedule_qtypicked
  USING btree
  (vhu_id);
