DROP VIEW de_metas_endcustomer_fresh_reports.C_Invoice_V;

CREATE OR REPLACE VIEW de_metas_endcustomer_fresh_reports.C_Invoice_V AS
select bp.value,
	   bp.Name as BPName,
       bp.C_Bpartner_ID,	   
       i.bpartneraddress,
       bp.vataxid,
       i.dateinvoiced,
       i.documentno,
       c.cursymbol,
       i.grandtotal,
       co.name,
       co.countrycode,
       currencyconvert(i.grandtotal :: numeric, i.C_Currency_ID :: numeric,
                       (Select C_Currency_ID from c_Currency where iso_code = 'EUR') :: numeric,
                       i.DateInvoiced :: timestamp with time zone, 0 :: numeric, i.AD_Client_ID :: numeric,
                       i.AD_Org_ID :: numeric) as euro_amt,
	   de_metas_endcustomer_fresh_reports.hasFreeEUTaxes(i.C_Invoice_ID) as IsEuTax

from C_invoice i
         join C_Bpartner bp on i.c_bpartner_id = bp.c_bpartner_id
         join c_bpartner_location bpl on bpl.c_bpartner_location_id = i.c_bpartner_location_id
         join c_location l on l.c_location_id = bpl.c_location_id
         join C_country co on co.c_country_id = l.c_country_id
         join c_currency c on i.c_currency_id = c.C_Currency_id
where i.issotrx = 'Y'
  and i.isactive = 'Y'
  and i.DocStatus in ('CO', 'CL')
  and de_metas_endcustomer_fresh_reports.isEUShippingFromInvoice(i.C_invoice_id)='Y';


