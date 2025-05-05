INSERT INTO public.modcntr_module (ad_client_id, ad_org_id, created, createdby, invoicinggroup, isactive, modcntr_module_id, modcntr_settings_id, modcntr_type_id,
                                   m_product_id, name, seqno, updated, updatedby, processed)

SELECT 1000000,
       1000000,
       '2024-05-21 14:32:59.239000 +00:00',
       100,
       'Service',
       'Y',
       NEXTVAL('modcntr_module_seq'),
       s.modcntr_settings_id,
      ( CASE WHEN s.m_processed_product_id IS NOT NULL THEN 540010 ELSE  540009 END),
       ( CASE WHEN s.m_processed_product_id IS NOT NULL THEN s.m_processed_product_id  ELSE  s.m_raw_product_id  END),
       'Definitive Schlussabrechnung',
       0,
       '2024-05-21 14:32:59.239000 +00:00',
       100,
       'Y'
FROM modcntr_settings s
WHERE NOT EXISTS (SELECT 1 FROM modcntr_module m WHERE m.modcntr_settings_id = s.modcntr_settings_id AND (m.modcntr_type_id = 540009 OR m.modcntr_type_id = 540010 ))
;