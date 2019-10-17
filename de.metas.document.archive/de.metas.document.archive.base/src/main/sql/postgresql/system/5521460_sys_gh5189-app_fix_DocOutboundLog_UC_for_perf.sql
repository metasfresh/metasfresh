
drop index public.c_doc_outbound_log_uc;

CREATE UNIQUE INDEX c_doc_outbound_log_uc
    ON public.c_doc_outbound_log
    (record_id, ad_table_id)
WHERE isactive = 'Y';
