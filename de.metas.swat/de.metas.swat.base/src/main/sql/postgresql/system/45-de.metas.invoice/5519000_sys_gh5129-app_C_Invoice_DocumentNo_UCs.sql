
DROP INDEX public.c_invoice_documentno;
CREATE UNIQUE INDEX c_invoice_documentno
    ON public.c_invoice USING btree
    (documentno, c_doctype_id, c_bpartner_id, ad_org_id)
    TABLESPACE pg_default;
COMMENT ON INDEX c_invoice_documentno IS 'See https://github.com/metasfresh/metasfresh/issues/5129 on why we include AD_Org_ID';

DROP INDEX public.c_invoice_documentno_target;
CREATE UNIQUE INDEX c_invoice_documentno_target
    ON public.c_invoice USING btree
    (documentno, c_bpartner_id, c_doctypetarget_id, ad_org_id)
    TABLESPACE pg_default;
COMMENT ON INDEX c_invoice_documentno_target IS 'See https://github.com/metasfresh/metasfresh/issues/5129 on why we include AD_Org_ID';
