

CREATE INDEX IF NOT EXISTS c_orderline_pmm_contract_asi_id
  ON public.c_orderline
  USING btree
  (pmm_contract_asi_id);

CREATE INDEX IF NOT EXISTS m_shipmentschedule_m_attributesetinstance_id
  ON public.m_shipmentschedule
  USING btree
  (m_attributesetinstance_id);

CREATE INDEX IF NOT EXISTS pp_mrp_m_attributesetinstance_id
  ON public.pp_mrp
  USING btree
  (m_attributesetinstance_id);

  
CREATE INDEX IF NOT EXISTS m_movementline_m_attributesetinstance_id
  ON public.m_movementline
  USING btree
  (m_attributesetinstance_id);
CREATE INDEX IF NOT EXISTS m_receiptschedule_m_attributesetinstance_id
  ON public.m_receiptschedule
  USING btree
  (m_attributesetinstance_id);
CREATE INDEX IF NOT EXISTS c_olcand_m_attributesetinstance_id
  ON public.c_olcand
  USING btree
  (m_attributesetinstance_id);
CREATE INDEX IF NOT EXISTS m_movementlinema_m_attributesetinstance_id
  ON public.m_movementlinema
  USING btree
  (m_attributesetinstance_id);
CREATE INDEX IF NOT EXISTS m_inoutlinema_m_attributesetinstance_id
  ON public.m_inoutlinema
  USING btree
  (m_attributesetinstance_id);
CREATE INDEX IF NOT EXISTS m_movementline_m_attributesetinstanceto_id
  ON public.m_movementline
  USING btree
  (m_attributesetinstanceto_id);
CREATE INDEX IF NOT EXISTS m_matchinv_m_attributesetinstance_id
  ON public.m_matchinv
  USING btree
  (m_attributesetinstance_id);
  