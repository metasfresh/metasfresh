
DROP INDEX IF EXISTS public.c_doc_responsible_tablerecord;
CREATE UNIQUE INDEX IF NOT EXISTS c_doc_responsible_record_id_ad_table_id
    ON public.c_doc_responsible (record_id, ad_table_id)
    WHERE isactive = 'Y'::bpchar;

DROP INDEX IF EXISTS public.c_doc_responsible_name;
-- *not* unique, because there can be different records from different tables with the same Record_ID
CREATE INDEX IF NOT EXISTS c_doc_responsible_record_id_name
    ON public.c_doc_responsible (record_id, AD_WF_Responsible_Name);

DROP INDEX IF EXISTS public.c_invoiceline_c_invoice_id_m_product_id;
CREATE INDEX IF NOT EXISTS c_invoiceline_c_invoice_id_m_product_id
    ON public.c_invoiceline (c_invoice_id, m_product_id);
