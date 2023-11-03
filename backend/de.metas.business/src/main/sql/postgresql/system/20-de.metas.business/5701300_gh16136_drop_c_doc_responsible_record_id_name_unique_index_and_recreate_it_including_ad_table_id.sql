DROP INDEX IF EXISTS public.c_doc_responsible_record_id_name;

CREATE UNIQUE INDEX IF NOT EXISTS c_doc_responsible_table_record_ref_name
        ON c_doc_responsible (record_id, ad_table_id, ad_wf_responsible_name)
;