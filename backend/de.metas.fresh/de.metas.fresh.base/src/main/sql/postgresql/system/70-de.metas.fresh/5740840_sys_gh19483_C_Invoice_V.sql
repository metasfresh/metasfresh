DROP VIEW de_metas_endcustomer_fresh_reports.C_Invoice_V
;

CREATE OR REPLACE VIEW de_metas_endcustomer_fresh_reports.C_Invoice_V AS
SELECT bp.value,
       bp.Name                                                           AS BPName,
       bp.C_Bpartner_ID,
       i.bpartneraddress,
       bp.vataxid,
       i.dateinvoiced,
       i.documentno,
       i.poreference,
       c.cursymbol,
       i.grandtotal,
       co.name,
       co.countrycode,
       currencyconvert(i.grandtotal :: numeric, i.C_Currency_ID :: numeric,
                       (SELECT C_Currency_ID FROM c_Currency WHERE iso_code = 'EUR') :: numeric,
                       i.DateInvoiced :: timestamp with time zone, 0 :: numeric, i.AD_Client_ID :: numeric,
                       i.AD_Org_ID :: numeric)                           AS euro_amt,
       de_metas_endcustomer_fresh_reports.hasFreeEUTaxes(i.C_Invoice_ID) AS IsEuTax,
       i.AD_Org_ID,
       o.Name                                                            AS OrgName
FROM C_invoice i
         JOIN AD_Org o ON i.ad_org_id = o.ad_org_id
         JOIN C_Bpartner bp ON i.c_bpartner_id = bp.c_bpartner_id
         JOIN c_bpartner_location bpl ON bpl.c_bpartner_location_id = i.c_bpartner_location_id
         JOIN c_location l ON l.c_location_id = bpl.c_location_id
         JOIN C_country co ON co.c_country_id = l.c_country_id
         JOIN c_currency c ON i.c_currency_id = c.C_Currency_id
WHERE i.issotrx = 'Y'
  AND i.DocStatus IN ('CO', 'CL')
  AND de_metas_endcustomer_fresh_reports.isEUShippingFromInvoice(i.C_invoice_id) = 'Y'
;

