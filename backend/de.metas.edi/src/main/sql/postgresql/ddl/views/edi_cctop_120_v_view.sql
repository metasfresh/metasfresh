-- View: edi_cctop_120_v

-- DROP VIEW IF EXISTS edi_cctop_120_v;

CREATE OR REPLACE VIEW edi_cctop_120_v AS 
 SELECT i.c_invoice_id AS edi_cctop_120_v_id, 
        i.c_invoice_id, 
        i.c_invoice_id AS edi_cctop_invoic_v_id, 
        CASE
            WHEN tc.tax_count < 2 THEN max(t.rate)
            ELSE NULL::numeric
        END AS singlevat, 
        CASE
            WHEN max(t.rate) = 0 THEN 'Y'
            ELSE 'N'
        END AS taxfree, 
        c.iso_code, 
        pt.netdays, 
        CASE
            WHEN pt.netdays IS NOT NULL THEN i.dateinvoiced::timestamp with time zone + pt.netdays
            ELSE NULL::date
        END AS netdate, 
        i.ad_client_id, 
        i.ad_org_id, 
        i.created, 
        i.createdby, 
        i.updated, 
        i.updatedby, 
        i.isactive
 FROM c_invoicetax it
    LEFT JOIN c_invoice i ON it.c_invoice_id = i.c_invoice_id
    LEFT JOIN c_tax t ON t.c_tax_id = it.c_tax_id
    LEFT JOIN c_currency c ON c.c_currency_id = i.c_currency_id
    LEFT JOIN ( 
        SELECT sit.c_invoice_id, count(DISTINCT st.rate) AS tax_count
        FROM c_invoicetax sit
            LEFT JOIN c_tax st ON st.c_tax_id = sit.c_tax_id
        GROUP BY sit.c_invoice_id) tc ON tc.c_invoice_id = it.c_invoice_id
    LEFT JOIN c_paymentterm pt ON pt.c_paymentterm_id = i.c_paymentterm_id
GROUP BY 
    i.c_invoice_id, i.dateinvoiced, tc.tax_count, c.iso_code, pt.netdays, i.ad_client_id, i.ad_org_id, i.created, i.createdby, i.updated, i.updatedby, i.isactive;



