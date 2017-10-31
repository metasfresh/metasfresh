

DROP VIEW public.c_payselection_remittance_v;

CREATE OR REPLACE VIEW public.c_payselection_remittance_v AS 
 SELECT psl.ad_client_id,
    psl.ad_org_id,
    'de_DE'::character varying AS ad_language,
    psl.c_payselection_id,
    psl.c_payselectionline_id,
    psl.paymentrule,
    psl.line,
    psl.openamt,
    psl.payamt,
    psl.discountamt,
    psl.differenceamt,
    i.c_bpartner_id,
    i.documentno,
    i.dateinvoiced,
    i.grandtotal,
    i.grandtotal AS amtinwords
   FROM c_payselectionline psl
     JOIN c_invoice i ON psl.c_invoice_id = i.c_invoice_id;

ALTER TABLE public.c_payselection_remittance_v
  OWNER TO metasfresh;
