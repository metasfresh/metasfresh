CREATE INDEX IF NOT EXISTS pp_product_planning_m_product_id
  ON public.pp_product_planning
  USING btree
  (m_product_id);
  
  
  
CREATE INDEX IF NOT EXISTS pp_product_planning_m_product_planningschema_id
  ON public.pp_product_planning
  USING btree
  (m_product_planningschema_id);