








DELETE FROM ad_ui_element e WHERE exists ( select 1 from ad_field f where e.ad_field_id = f.ad_field_id and f.ad_column_id = 583863 );



DELETE FROM ad_field where AD_Column_ID=583863;





-- Column: C_Invoice_Candidate.IsInterimInvoice
-- 2024-04-02T12:46:19.033Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=583863
;

-- 2024-04-02T12:46:19.037Z
DELETE FROM AD_Column WHERE AD_Column_ID=583863
;





/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate','ALTER TABLE public.C_Invoice_Candidate DROP COLUMN IsInterimInvoice ')
;





-- 2024-04-02T12:48:49.243Z
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=581192
;

-- 2024-04-02T12:48:49.247Z
DELETE FROM AD_Element WHERE AD_Element_ID=581192
;





