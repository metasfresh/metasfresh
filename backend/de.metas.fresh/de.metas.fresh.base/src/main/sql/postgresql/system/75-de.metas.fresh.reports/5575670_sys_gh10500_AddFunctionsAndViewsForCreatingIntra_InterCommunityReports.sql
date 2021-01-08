CREATE OR REPLACE FUNCTION isEUCountry(IN p_C_Country_ID numeric)
    RETURNS char(1) AS
$BODY$
DECLARE

    isEUCountry char(1);

BEGIN

    isEUCountry := (select case when (count(1) > 0) then 'Y' else 'N' end
                    from C_CountryArea_Assign ca
                    where ca.C_CountryArea_ID = 540000 -- European Union
                      and ca.C_Country_ID = p_C_Country_ID);

    IF isEUCountry IS NOT NUll THEN
        RETURN isEUCountry;
    ELSE
        RETURN 'N';
    END IF;
END;
$BODY$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.isEUShippingFromInvoice(IN p_C_Invoice_ID numeric)
    RETURNS char(1) AS
$BODY$
DECLARE

    isEUShippingFromInvoice char(1);

BEGIN

    isEUShippingFromInvoice := (
        select case when (count(1) > 0) then 'Y' else 'N' end
        from C_invoice i

                 ---- invoice warehouse country
                 left join m_warehouse wi on wi.m_warehouse_id = i.m_warehouse_id
                 left join c_location wli on wli.c_location_id = wi.c_location_id

            ---- shipment warehouse country
                 join c_invoiceline il on il.C_invoice_id = i.C_invoice_id
                 left join m_inoutline iol on iol.m_inoutline_id = il.m_inoutline_id
                 join m_inout io on io.m_inout_id = iol.m_inout_id
                 left join m_warehouse wio on wio.m_warehouse_id = io.m_warehouse_id
                 left join c_location wlio on wlio.c_location_id = wio.c_location_id

            ---- org warehouse country
                 left join AD_orgInfo oi on oi.ad_org_id = i.ad_org_id
                 left join m_warehouse wo on wo.m_warehouse_id = oi.m_warehouse_id
                 left join c_location wlo on wlo.c_location_id = wi.c_location_id

        where i.issotrx = 'Y'
          and i.isactive = 'Y'
          and i.DocStatus in ('CO', 'CL')
          and isEUCountry(coalesce(wlio.C_Country_ID, wli.C_Country_ID, wlo.c_country_id)) = 'Y'
          and i.C_Invoice_ID = p_C_Invoice_ID
    );

    IF isEUShippingFromInvoice IS NOT NUll THEN
        RETURN isEUShippingFromInvoice;
    ELSE
        RETURN 'N';
    END IF;
END;
$BODY$ LANGUAGE plpgsql;



CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.hasFreeEUTaxes(IN p_C_Invoice_ID numeric)
    RETURNS char(1) AS
$BODY$
DECLARE

    hasEUTax char(1);

BEGIN

    hasEUTax := (select case when (count(1) > 0) then 'Y' else 'N' end
                 from c_invoiceline il
                          join C_Tax t on il.c_tax_id = t.c_tax_id
                 where t.istoeulocation = 'Y' and t.isTaxexempt='Y'
                   and il.c_invoice_id = p_C_Invoice_ID);

    IF hasEUTax IS NOT NUll THEN
        RETURN hasEUTax;
    ELSE
        RETURN 'N';
    END IF;
END;
$BODY$ LANGUAGE plpgsql;



DROP VIEW IF EXISTS de_metas_endcustomer_fresh_reports.C_Invoice_V;

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




DROP VIEW IF EXISTS de_metas_endcustomer_fresh_reports.M_InOut_V;

CREATE OR REPLACE VIEW de_metas_endcustomer_fresh_reports.M_InOut_V AS
select
    commoditynumber,
    deliveredFromCountry,
    deliveryCountry,
    OriginCountry,
    sum(movementqty) as movementqty,
    UOMSymbol,
    sum(grandtotal) as grandtotal,
    cursymbol,
	C_Period_ID,
	Period
from (
         select cn.value                                    as commoditynumber,
                wlc.countrycode                             as deliveredFromCountry,
                co.countrycode                              as deliveryCountry,
                pco.countrycode                             as OriginCountry,

               coalesce((case
                             when qtydeliveredcatch is not null
                                 then qtydeliveredcatch
                             else uomConvert(iol.M_Product_ID, iol.C_UOM_ID, 540017, iol.qtyentered) -- hard coded UOM for KG
                   end), 0) as movementqty,
                 uom.UOMSymbol as UOMSymbol,

                il.linenetamt                               as grandtotal,
                c.cursymbol,
				C_Period_ID,
				per.name as Period
         from M_InOut io
                  left join m_warehouse w on w.m_warehouse_id = io.m_warehouse_id
                  left join c_location wl on wl.c_location_id = w.c_location_id
                  left join C_country wlc on wlc.c_country_id = wl.c_country_id
                  join m_inoutline iol on iol.m_inout_id = io.m_inout_id
                  left join c_invoiceline il on il.m_inoutline_id = iol.m_inoutline_id
                  join C_invoice i on i.C_invoice_id = il.C_invoice_id and i.DocStatus IN ('CO', 'CL')
                  join m_product p on p.m_product_id = iol.m_product_id
                  left join m_commoditynumber cn on cn.m_commoditynumber_id = p.m_commoditynumber_id
                  left join C_country pco on pco.c_country_id = p.rawmaterialorigin_id
                  join c_bpartner_location bpl on bpl.c_bpartner_location_id = io.c_bpartner_location_id
                  join c_location l on l.c_location_id = bpl.c_location_id
                  join C_country co on co.c_country_id = l.c_country_id
                  join c_currency c on i.c_currency_id = c.C_Currency_id

				  LEFT OUTER JOIN C_UOM uom ON uom.C_UOM_ID = coalesce(iol.catch_uom_id, 540017) -- fallback to KG

				  JOIN C_Period per on i.dateinvoiced >= per.startdate  and i.dateinvoiced <= per.enddate

         where io.issotrx = 'Y'
           and io.isactive = 'Y'
           and iol.ispackagingmaterial = 'N'
           and wlc.countrycode = 'DE'
           and co.countrycode != 'DE'
     ) as v
group by commoditynumber,
         deliveredFromCountry,
         deliveryCountry,
         OriginCountry,
         UOMSymbol,
         cursymbol,
		 C_Period_ID,
		 Period
order by deliveryCountry, commoditynumber;