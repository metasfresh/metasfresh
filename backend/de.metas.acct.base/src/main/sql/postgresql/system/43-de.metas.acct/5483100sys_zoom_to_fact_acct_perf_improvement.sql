
CREATE INDEX IF NOT EXISTS fact_acct_ad_table_id
  ON public.fact_acct(ad_table_id);	

CREATE INDEX IF NOT EXISTS fact_acct_record_id_ad_table_id
  ON public.fact_acct(record_id, ad_table_id);	
  
DROP INDEX IF EXISTS public.fact_acct_reference;
