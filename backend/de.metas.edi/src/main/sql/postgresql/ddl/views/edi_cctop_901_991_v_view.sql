-- View: edi_cctop_901_991_v

DROP VIEW IF EXISTS edi_cctop_901_991_v;
CREATE OR REPLACE VIEW edi_cctop_901_991_v AS
SELECT i.c_invoice_id AS edi_cctop_901_991_v_id, i.c_invoice_id, i.c_invoice_id AS edi_cctop_invoic_v_id,
       it.taxamt + it.taxbaseamt AS totalamt,
       it.taxamt,
       it.taxbaseamt,
       t.rate, i.ad_client_id, i.ad_org_id, i.created, i.createdby, i.updated, i.updatedby, i.isactive,
       REGEXP_REPLACE(rn.referenceNo, '\s+$', '') AS ESRReferenceNumber
FROM c_invoice i
         LEFT JOIN c_invoicetax it ON it.c_invoice_id = i.c_invoice_id
         LEFT JOIN c_tax t ON t.c_tax_id = it.c_tax_id
         LEFT JOIN (
    SELECT sit.c_invoice_id, count(DISTINCT st.rate) AS tax_count
    FROM c_invoicetax sit
             LEFT JOIN c_tax st ON st.c_tax_id = sit.c_tax_id
    GROUP BY sit.c_invoice_id
) tc ON tc.c_invoice_id = it.c_invoice_id
         LEFT JOIN c_referenceno_doc rnd on rnd.record_id=i.c_invoice_id and rnd.ad_table_id=318 /* C_Invoice DocumentType */
         LEFT JOIN c_referenceno rn on rn.c_referenceno_id=rnd.c_referenceno_id

WHERE tc.tax_count > 0
  -- can either be an ESRReferenceNumber, or we may not have it at all. Regardless, both cases should work.
  AND (rn.c_referenceno_type_id = 540005 OR rnd.c_referenceno_doc_id is null) /* c_referenceno_type_id = 540005 (ESRReferenceNumber) */;

