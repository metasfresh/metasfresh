INSERT INTO public.modcntr_module (ad_client_id, ad_org_id, created, createdby, invoicinggroup, isactive, modcntr_module_id, modcntr_settings_id, modcntr_type_id,
                                   m_product_id, name, seqno, updated, updatedby, processed)

SELECT 1000000,
       1000000,
       '2024-05-08 14:32:59.239000 +00:00',
       100,
       'Service',
       'Y',
       NEXTVAL('modcntr_module_seq'),
       s.modcntr_settings_id,
       540008,
       s.m_raw_product_id,
       'Informative Logs',
       0,
       '2024-05-08 14:32:59.239000 +00:00',
       100,
       'Y'
FROM modcntr_settings s
WHERE NOT EXISTS (SELECT 1 FROM modcntr_module m WHERE m.modcntr_settings_id = s.modcntr_settings_id AND m.modcntr_type_id = 540008)
;