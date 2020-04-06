
-- make the index not-unique, i.e. allow multiple schedules per order line
DROP INDEX public.m_shipmentschedule_c_orderline;
CREATE INDEX m_shipmentschedule_c_orderline
  ON public.m_shipmentschedule
  USING btree
  (c_orderline_id);
