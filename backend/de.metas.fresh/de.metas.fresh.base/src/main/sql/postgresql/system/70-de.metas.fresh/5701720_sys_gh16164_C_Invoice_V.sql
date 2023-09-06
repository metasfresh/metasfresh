CREATE VIEW de_metas_endcustomer_fresh_reports.c_invoice_v
            (
             value,
             bpname,
             c_bpartner_id,
             bpartneraddress,
             vataxid,
             dateinvoiced,
             documentno,
             cursymbol,
             grandtotal,
             name,
             countrycode,
             euro_amt,
             iseutax,
             ad_org_id,
             orgname)
AS
SELECT bp.value,
       bp.name                                                                                                                                                                        AS bpname,
       bp.c_bpartner_id,
       i.bpartneraddress,
       bp.vataxid,
       i.dateinvoiced,
       i.documentno,
       c.cursymbol,
       i.grandtotal,
       co.name,
       co.countrycode,
       currencyconvert(i.grandtotal, i.c_currency_id, (SELECT c_currency.c_currency_id
                                                       FROM c_currency
                                                       WHERE c_currency.iso_code = 'EUR'::bpchar), i.dateinvoiced::timestamp WITH TIME ZONE, 0::numeric, i.ad_client_id, i.ad_org_id) AS euro_amt,
       de_metas_endcustomer_fresh_reports.hasfreeeutaxes(i.c_invoice_id)                                                                                                              AS iseutax,
       i.ad_org_id,
       o.name                                                                                                                                                                         AS orgname
FROM c_invoice i
         JOIN ad_org o ON i.ad_org_id = o.ad_org_id
         JOIN c_bpartner bp ON i.c_bpartner_id = bp.c_bpartner_id
         JOIN c_bpartner_location bpl ON bpl.c_bpartner_location_id = i.c_bpartner_location_id
         JOIN c_location l ON l.c_location_id = bpl.c_location_id
         JOIN c_country co ON co.c_country_id = l.c_country_id
         JOIN c_currency c ON i.c_currency_id = c.c_currency_id
WHERE i.issotrx = 'Y'::bpchar
  AND i.isactive = 'Y'::bpchar
  AND (i.docstatus = ANY (ARRAY ['CO'::bpchar, 'CL'::bpchar]))
  AND de_metas_endcustomer_fresh_reports.iseushippingfrominvoice(i.c_invoice_id) = 'Y'::bpchar
;