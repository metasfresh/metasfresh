
CREATE INDEX IF NOT EXISTS md_cockpit_dategeneral
  ON public.md_cockpit
  USING btree
  (dategeneral);
  
CREATE UNIQUE INDEX IF NOT EXISTS md_cockpit_uc
  ON public.md_cockpit
  USING btree
  (dategeneral, m_product_id, attributeskey);
