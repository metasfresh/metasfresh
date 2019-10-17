
DROP INDEX IF EXISTS public.m_shipmentschedule_c_orderline; -- duplicate of also existing index m_shipmentschedule_orderline_id
CREATE INDEX IF NOT EXISTS m_shipmentschedule_orderline_id ON public.m_shipmentschedule (c_orderline_id); -- very probably exists!
CREATE INDEX IF NOT EXISTS m_shipmentschedule_order_id ON public.m_shipmentschedule (c_order_id);


CREATE INDEX IF NOT EXISTS r_request_r_requestrelated_id ON public.r_request (r_requestrelated_id);
CREATE INDEX IF NOT EXISTS r_requestaction_r_request_id ON public.r_requestaction (r_request_id);

DROP INDEX IF EXISTS r_requestupdate_r_request_id;
CREATE INDEX IF NOT EXISTS r_requestupdate_r_request_id ON public.r_requestupdate (r_request_id);
