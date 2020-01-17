
DROP INDEX public.fki_m_shipmentschedule_recompute_m_shipmentsched;

CREATE INDEX m_shipmentschedule_recompute_m_shipmentschedule_id_ad_pinstance
    ON public.m_shipmentschedule_recompute (m_shipmentschedule_id, ad_pinstance_id NULLS FIRST);
COMMENT ON INDEX m_shipmentschedule_recompute_m_shipmentschedule_id_ad_pinstance IS 'Also contains ad_pinstance_id so we can avaid loading the table when we just want m_shipmentschedule_id and ad_pinstance_id';
