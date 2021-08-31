drop view if exists de_metas_test.c_invoice_taxnote_v;

DROP FUNCTION if exists report.taxnote(p_c_invoice_id numeric);

CREATE OR REPLACE FUNCTION report.taxnote(IN p_c_invoice_id numeric) returns text
AS
$BODY$
DECLARE
    taxnotetext text;
BEGIN
    select String_agg(distinct report.textsnippet(t.ad_boilerplate_id), ''||E'\n')
    into taxnotetext
    from c_invoiceline il
             join C_Tax t on il.c_tax_id = t.c_tax_id
    where il.c_invoice_id = p_C_Invoice_ID
    group by il.c_invoice_id;

    IF taxnotetext IS NOT NUll THEN
        RETURN taxnotetext;
    ELSE
        RETURN '';
    END IF;
END;
$BODY$ LANGUAGE plpgsql;

create view de_metas_test.c_invoice_taxnote_v as
select distinct invoiceCase, Invoice, orgname, documentno, documenttype, taxnote, C_tax_ID,
                (select ad_boilerplate_id from ad_boilerplate where internalname=invoiceCase) as ad_boilerplate_id,
                report.taxnote(C_Invoice_id) as newTaxNote,
                C_Invoice_id
from (
         SELECT (case /*Belegart*/
                     when o.name = 'Bodymed AG' and (dt.name like 'Ausgangsrechnung%' or dt.name like 'Gutschrift%')
                                                                                               then
                         case
                             when t.istoeulocation = 'Y' and t.isTaxexempt = 'Y'
                                                     then 'Warenlieferung_EU_UStID'

                             when t.istoeulocation = 'Y' and t.isTaxexempt = 'N' and countrycode != 'AT'
                                                     then 'EU ohne UstID'

                             when t.istoeulocation = 'Y' and t.isTaxexempt = 'N' and countrycode = 'AT'
                                                     then 'Warenlieferung_AT_ohne_UStID'

                             when countrycode = 'CH' then 'Steuernummer_CH_BM'

                             when t.istoeulocation = 'N' and t.to_country_id is null
                                                     then 'Warenlieferung_Drittland'
                                                     else 'NO_Tax'
                         end
                     when o.name in ('Bodymed AG', 'Goldflug') and
                          (dt.name like 'Provisionsabrechnung%' or dt.name like 'Gutschrift%') then
                         case
                             when t.istoeulocation = 'Y' and bp.vataxid is not null and t.istaxexempt = 'Y'
                                 then 'Provisionsgutschriften_AT_EU'

                             when vat.c_vat_smallbusiness_id is not null and countrycode = 'DE'
                                 then 'Kleinunternehmer_DE_Provision'

                             when vat.c_vat_smallbusiness_id is not null and countrycode = 'AT'
                                 then 'Kleinunternehmer_AT_Provision'
                         end
                 end)                                                                    as invoiceCase,

                'https://bodymeduat.metasfresh.com/window/540713/' || i.c_invoice_id as Invoice,
                o.name                                                               as orgname,
                i.documentno,
                dt.name                                                              as documenttype,
                de_metas_endcustomer_bm115.taxnote(i.C_Invoice_ID)                   as taxnote,
                il.c_tax_ID,
                i.C_Invoice_id
         from C_Invoice i
                  join c_invoiceLine il on il.c_invoice_id = i.c_invoice_id
                  join c_doctype dt on i.c_doctype_id = dt.c_doctype_id
                  join ad_org o on i.ad_org_Id = o.ad_org_id
                  join C_Tax t on il.c_tax_id = t.c_tax_id
                  left join c_Bpartner bp on i.c_bpartner_id = bp.c_bpartner_id
                  join c_bpartner_location cbl on cbl.c_bpartner_location_id = i.c_bpartner_location_id
                  left join c_location l on l.c_location_id = cbl.c_location_id
                  left join c_country c on c.c_country_id = l.c_country_id
                  left join c_vat_smallbusiness vat on i.c_bpartner_id = vat.c_bpartner_id
         where i.issotrx = 'Y'
           and i.docstatus in ('CO', 'CL')
         order by i.c_invoice_id desc
     ) as invoices
where invoiceCase <>'NO_Tax' and invoiceCase <>'EU ohne UstID';