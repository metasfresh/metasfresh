
-- 2020-09-04T09:19:56.192Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('EDI_DesadvLine','ALTER TABLE public.EDI_DesadvLine ADD COLUMN C_UOM_Invoice_ID NUMERIC(10)')
;

-- 2020-09-04T09:19:56.296Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE EDI_DesadvLine ADD CONSTRAINT CUOMInvoice_EDIDesadvLine FOREIGN KEY (C_UOM_Invoice_ID) REFERENCES public.C_UOM DEFERRABLE INITIALLY DEFERRED
;


-- 2020-09-04T09:33:28.220Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('EDI_DesadvLine','ALTER TABLE public.EDI_DesadvLine ADD COLUMN QtyDeliveredInInvoiceUOM NUMERIC')
;


-- 2020-09-04T10:01:53.181Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('EDI_DesadvLine','ALTER TABLE public.EDI_DesadvLine ADD COLUMN OrderPOReference VARCHAR(255)')
;

-- 2020-09-04T10:06:46.177Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('exp_formatline','DateFormat','VARCHAR(128)',null,null)
;

-- 2020-09-04T10:09:21.947Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('EDI_DesadvLine','ALTER TABLE public.EDI_DesadvLine ADD COLUMN OrderLine NUMERIC(10)')
;


DROP VIEW IF EXISTS edi_cctop_000_v;

select db_alter_table('C_BPartner', 'ALTER TABLE C_BPartner RENAME COLUMN EdiRecipientGLN TO EdiDesadvRecipientGLN');


-- 2020-09-04T11:16:09.156Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE public.C_BPartner ADD COLUMN EdiInvoicRecipientGLN VARCHAR(255)')
;

CREATE OR REPLACE VIEW edi_cctop_000_v AS
SELECT
    l.c_bpartner_location_id AS edi_cctop_000_v_id,
    l.c_bpartner_location_id,
    bp.EdiInvoicRecipientGLN,
    l.ad_client_id,
    l.ad_org_id,
    l.created,
    l.createdby,
    l.updated,
    l.updatedby,
    l.isactive
FROM c_bpartner_location l
         INNER JOIN C_BPartner bp ON bp.C_BPartner_ID=l.C_BPartner_ID;
