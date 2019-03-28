DROP INDEX IF EXISTS public.m_productprice_pricelistver_seqno;

CREATE INDEX m_productprice_pricelistver_seqno
  ON public.m_productprice
  USING btree
  (m_pricelist_version_id, seqno);

