
-- Thx to https://blog.2ndquadrant.com/autovacuum-tuning-basics/
ALTER TABLE ad_archive SET (autovacuum_vacuum_scale_factor = 0);
ALTER TABLE ad_archive SET (autovacuum_vacuum_threshold = 10000);

ALTER TABLE c_referenceno_doc SET (autovacuum_vacuum_scale_factor = 0);
ALTER TABLE c_referenceno_doc SET (autovacuum_vacuum_threshold = 10000);

ALTER TABLE ad_attachment SET (autovacuum_vacuum_scale_factor = 0);
ALTER TABLE ad_attachment SET (autovacuum_vacuum_threshold = 10000);

ALTER TABLE ad_pinstance_para SET (autovacuum_vacuum_scale_factor = 0);
ALTER TABLE ad_pinstance_para SET (autovacuum_vacuum_threshold = 10000);

ALTER TABLE ad_pinstance SET (autovacuum_vacuum_scale_factor = 0);
ALTER TABLE ad_pinstance SET (autovacuum_vacuum_threshold = 10000);

ALTER TABLE ad_pinstance_log SET (autovacuum_vacuum_scale_factor = 0);
ALTER TABLE ad_pinstance_log SET (autovacuum_vacuum_threshold = 10000);

ALTER TABLE c_subscriptionprogress SET (autovacuum_vacuum_scale_factor = 0);
ALTER TABLE c_subscriptionprogress SET (autovacuum_vacuum_threshold = 10000);

ALTER TABLE c_invoice_line_alloc SET (autovacuum_vacuum_scale_factor = 0);
ALTER TABLE c_invoice_line_alloc SET (autovacuum_vacuum_threshold = 10000);

ALTER TABLE c_invoiceline SET (autovacuum_vacuum_scale_factor = 0);
ALTER TABLE c_invoiceline SET (autovacuum_vacuum_threshold = 10000);

ALTER TABLE c_doc_outbound_log_line SET (autovacuum_vacuum_scale_factor = 0);
ALTER TABLE c_doc_outbound_log_line SET (autovacuum_vacuum_threshold = 10000);

ALTER TABLE c_referenceno SET (autovacuum_vacuum_scale_factor = 0);
ALTER TABLE c_referenceno SET (autovacuum_vacuum_threshold = 10000);

ALTER TABLE c_doc_responsible SET (autovacuum_vacuum_scale_factor = 0);
ALTER TABLE c_doc_responsible SET (autovacuum_vacuum_threshold = 10000);

ALTER TABLE c_invoice SET (autovacuum_vacuum_scale_factor = 0);
ALTER TABLE c_invoice SET (autovacuum_vacuum_threshold = 10000);

ALTER TABLE c_printing_queue SET (autovacuum_vacuum_scale_factor = 0);
ALTER TABLE c_printing_queue SET (autovacuum_vacuum_threshold = 10000);

ALTER TABLE c_invoicetax SET (autovacuum_vacuum_scale_factor = 0);
ALTER TABLE c_invoicetax SET (autovacuum_vacuum_threshold = 10000);

ALTER TABLE c_doc_outbound_log SET (autovacuum_vacuum_scale_factor = 0);
ALTER TABLE c_doc_outbound_log SET (autovacuum_vacuum_threshold = 10000);

ALTER TABLE c_payment SET (autovacuum_vacuum_scale_factor = 0);
ALTER TABLE c_payment SET (autovacuum_vacuum_threshold = 10000);

ALTER TABLE c_allocationhdr SET (autovacuum_vacuum_scale_factor = 0);
ALTER TABLE c_allocationhdr SET (autovacuum_vacuum_threshold = 10000);

ALTER TABLE c_allocationline SET (autovacuum_vacuum_scale_factor = 0);
ALTER TABLE c_allocationline SET (autovacuum_vacuum_threshold = 10000);

ALTER TABLE c_contract_term_alloc SET (autovacuum_vacuum_scale_factor = 0);
ALTER TABLE c_contract_term_alloc SET (autovacuum_vacuum_threshold = 10000);

ALTER TABLE c_olcand SET (autovacuum_vacuum_scale_factor = 0);
ALTER TABLE c_olcand SET (autovacuum_vacuum_threshold = 10000);

-- not so big, but still need it, i think because a lot of changes are going on in there
ALTER TABLE public.t_query_selection SET (autovacuum_vacuum_scale_factor = 0);
ALTER TABLE public.t_query_selection SET (autovacuum_vacuum_threshold = 10000);

ALTER TABLE t_lock SET (autovacuum_vacuum_scale_factor = 0);
ALTER TABLE t_lock SET (autovacuum_vacuum_threshold = 10000);

ALTER TABLE m_shipmentschedule_recompute SET (autovacuum_vacuum_scale_factor = 0);
ALTER TABLE m_shipmentschedule_recompute SET (autovacuum_vacuum_threshold = 10000);

ALTER TABLE c_invoice_candidate_recompute SET (autovacuum_vacuum_scale_factor = 0);
ALTER TABLE c_invoice_candidate_recompute SET (autovacuum_vacuum_threshold = 10000);

ALTER TABLE ESR_ImportLine SET (autovacuum_vacuum_scale_factor = 0);
ALTER TABLE ESR_ImportLine SET (autovacuum_vacuum_threshold = 10000);

ALTER TABLE ESR_Import SET (autovacuum_vacuum_scale_factor = 0);
ALTER TABLE ESR_Import SET (autovacuum_vacuum_threshold = 10000);

ALTER TABLE c_flatrate_term SET (autovacuum_vacuum_scale_factor = 0);
ALTER TABLE c_flatrate_term SET (autovacuum_vacuum_threshold = 10000);

ALTER TABLE c_invoice_candidate SET (autovacuum_vacuum_scale_factor = 0);
ALTER TABLE c_invoice_candidate SET (autovacuum_vacuum_threshold = 10000); 


--
-- we can't run this via migration script: we get "ERROR:  ALTER SYSTEM cannot run inside a transaction block"
--
-- Thx to https://medium.com/contactually-engineering/postgres-at-scale-query-performance-and-autovacuuming-for-large-tables-d7e8ad40b16b
-- this ends up in postgresql.auto.conf. 
-- note "postgresql.auto.conf override those in postgresql.conf." (from https://www.postgresql.org/docs/9.5/config-setting.html)
-- but whenever we manually edited and postgresql.conf, we set these to settings to 0.05 there as well
--ALTER SYSTEM SET autovacuum_vacuum_scale_factor = 0.05;
--ALTER SYSTEM SET autovacuum_analyze_scale_factor = 0.05;
