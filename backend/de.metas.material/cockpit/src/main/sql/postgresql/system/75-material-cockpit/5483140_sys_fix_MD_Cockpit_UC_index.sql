--
-- ...it also need to contain the plant id
--
DROP INDEX public.md_cockpit_uc;

CREATE UNIQUE INDEX md_cockpit_uc
  ON public.md_cockpit
  USING btree
  (dategeneral, m_product_id, attributeskey, coalesce(pp_plant_id, 0));
