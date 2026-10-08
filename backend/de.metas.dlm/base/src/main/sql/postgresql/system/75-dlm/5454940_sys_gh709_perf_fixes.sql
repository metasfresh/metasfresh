

CREATE INDEX IF NOT EXISTS m_inoutlinema_m_inoutline_id
   ON public.m_inoutlinema (m_inoutline_id ASC NULLS LAST);

CREATE INDEX IF NOT EXISTS M_Material_Balance_Detail_m_inoutline_id
   ON public.M_Material_Balance_Detail (m_inoutline_id ASC NULLS LAST);

CREATE INDEX IF NOT EXISTS M_ShipmentSchedule_record_id_ad_Table_id
   ON public.M_ShipmentSchedule (record_id, ad_table_id);
COMMENT ON INDEX public.M_ShipmentSchedule_record_id_ad_Table_id
  IS 'putting record_id first because there are less records with the same record-id..hope that makes sense';
   
CREATE INDEX IF NOT EXISTS m_movementlinema_m_movementline_id
  ON public.m_movementlinema
  USING btree
  (m_movementline_id);

CREATE INDEX IF NOT EXISTS c_invoice_candidate_m_material_tracking_id
  ON public.c_invoice_candidate
  USING btree
  (m_material_tracking_id);  
  
CREATE INDEX IF NOT EXISTS m_costdetail_m_movementline_id
  ON public.m_costdetail
  USING btree
  (m_movementline_id);
  
CREATE INDEX IF NOT EXISTS m_transaction_pp_cost_collector_id
  ON public.m_transaction
  USING btree
  (pp_cost_collector_id);  
 
DROP INDEX IF EXISTS ad_changelog_speed;

CREATE INDEX IF NOT EXISTS ad_changelog_record_id_ad_table_id
  ON ad_changelog
  USING btree
  (record_id, ad_table_id);
COMMENT ON INDEX public.ad_changelog_record_id_ad_table_id
  IS 'putting record_id first because there are less records with the same record-id..hope that makes sense';
  
CREATE INDEX IF NOT EXISTS ad_changelog_ad_table_id
  ON ad_changelog
  USING btree
  (ad_table_id); 	
