
/*
Note that 5619590_sys_gh12207_invoicing_perf_indices.sql is also fixed meanwhile; 
but where the incorrect version  was already applied and just works incidentally, we need to have this SQL  
*/
DROP INDEX IF EXISTS public.c_doc_responsible_tablerecord;
CREATE UNIQUE INDEX IF NOT EXISTS c_doc_responsible_record_id_ad_table_id
    ON public.c_doc_responsible (record_id, ad_table_id)
    WHERE isactive = 'Y'::bpchar;

DROP INDEX IF EXISTS public.c_doc_responsible_name;
CREATE INDEX IF NOT EXISTS c_doc_responsible_record_id_name
    ON public.c_doc_responsible (record_id, AD_WF_Responsible_Name);

DROP INDEX IF EXISTS public.c_invoiceline_c_invoice_id_m_product_id;
CREATE INDEX IF NOT EXISTS c_invoiceline_c_invoice_id_m_product_id
    ON public.c_invoiceline (c_invoice_id, m_product_id);
