DROP VIEW IF EXISTS de_metas_endcustomer_fresh_reports.M_InOut_V;

CREATE OR REPLACE VIEW de_metas_endcustomer_fresh_reports.M_InOut_V AS
select commoditynumber,
       productName,
       productDescription,
       deliveredFromCountry,
       deliveryCountry,
       OriginCountry,
       sum(movementqty) as movementqty,
       UOMSymbol,
       sum(grandtotal)  as grandtotal,
       cursymbol,
       C_Period_ID,
       Period,
       AD_Org_ID,
       OrgName
from (
         select cn.value        as commoditynumber,
                p.Name          as productName,
                p.description   as productDescription,
                wlc.countrycode as deliveredFromCountry,
                co.countrycode  as deliveryCountry,
                pco.countrycode as OriginCountry,

                coalesce((case
                              when qtydeliveredcatch is not null
                                  then qtydeliveredcatch
                              when uomConvert(iol.M_Product_ID, iol.C_UOM_ID, (select C_UOM_ID
                                                                               from C_UOM
                                                                               where x12de355 = 'KGM' and isactive = 'Y'
                                                                               order by isdefault desc
                                                                               limit 1), iol.qtyentered) is not null
                                  then uomConvert(iol.M_Product_ID, iol.C_UOM_ID, (select C_UOM_ID
                                                                                   from C_UOM
                                                                                   where x12de355 = 'KGM'
                                                                                     and isactive = 'Y'
                                                                                   order by isdefault desc
                                                                                   limit 1),
                                                  iol.qtyentered) -- hard coded UOM for KG
                              else iol.qtyentered
                    end), 0)    as movementqty,

                (case
                     when uomConvert(iol.M_Product_ID, iol.C_UOM_ID, (select C_UOM_ID
                                                                      from C_UOM
                                                                      where x12de355 = 'KGM' and isactive = 'Y'
                                                                      order by isdefault desc
                                                                      limit 1), iol.qtyentered) is not null
                         then uom.UOMSymbol
                     else uom_iol.UOMSymbol
                    end)
                                as UOMSymbol,

                il.linenetamt   as grandtotal,
                c.cursymbol,
                C_Period_ID,
                per.name        as Period,
                io.AD_Org_ID,
                o.Name          as OrgName
         from M_InOut io
                  join AD_Org o on io.ad_org_id = o.ad_org_id
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

                  LEFT OUTER JOIN C_UOM uom ON uom.C_UOM_ID = coalesce(iol.catch_uom_id, (select C_UOM_ID
                                                                                          from C_UOM
                                                                                          where x12de355 = 'KGM'
                                                                                            and isactive = 'Y'
                                                                                          order by isdefault desc
                                                                                          limit 1)) -- fallback to KG
                  LEFT OUTER JOIN C_UOM uom_iol ON uom_iol.C_UOM_ID = iol.C_UOM_ID


                  JOIN C_Period per on i.dateinvoiced >= per.startdate and i.dateinvoiced <= per.enddate

         where io.issotrx = 'Y'
           and io.isactive = 'Y'
           and iol.ispackagingmaterial = 'N'
           and wlc.countrycode = 'DE'
           and co.countrycode != 'DE'
     ) as v
group by commoditynumber,
         productName,
         productDescription,
         deliveredFromCountry,
         deliveryCountry,
         OriginCountry,
         UOMSymbol,
         cursymbol,
         C_Period_ID,
         Period,
         AD_Org_ID,
         OrgName
order by deliveryCountry, commoditynumber;


DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.IntraTradeShipments(numeric);


DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.IntraTradeShipments(numeric, numeric);

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.IntraTradeShipments(IN p_C_Period_ID numeric, IN p_AD_Org_ID numeric)

    RETURNS TABLE
            (
				"Org"                 varchar,
                "Pos"                 varchar,
                "Produkt"             text,
                "Warennumer"          varchar,
                "Land geliefert von"  char(2),
                "Land geliefert nach" char(2),
                "Ursprungsland"       char(2),
                "Menge"               numeric,
                "Einheit"             varchar,
                "RG. Betrag"          numeric,
                "Währung"             varchar,
                "Monat"               varchar
            )
AS
$$
select OrgName,
       (line::varchar) as pos,
       productName ||  E'\n' || productDescription as product,
       commoditynumber,
       deliveredFromCountry,
       deliveryCountry,
       OriginCountry,
       movementqty,
       UOMSymbol,
       grandtotal,
       cursymbol,
       Period
from (
         select row_number()
                over (order by deliveryCountry, commoditynumber, deliveredFromCountry, OriginCountry, UOMSymbol, cursymbol, C_Period_ID) as line,
                *
         from de_metas_endcustomer_fresh_reports.M_InOut_V i
         where C_Period_ID = p_C_Period_ID and AD_Org_ID = p_AD_Org_ID
     ) as v
order by line;
$$
    LANGUAGE sql
    STABLE;



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
	   de_metas_endcustomer_fresh_reports.hasFreeEUTaxes(i.C_Invoice_ID) as IsEuTax,
	   i.AD_Org_ID,
       o.Name          as OrgName
from C_invoice i
join AD_Org o on i.ad_org_id = o.ad_org_id
         join C_Bpartner bp on i.c_bpartner_id = bp.c_bpartner_id
         join c_bpartner_location bpl on bpl.c_bpartner_location_id = i.c_bpartner_location_id
         join c_location l on l.c_location_id = bpl.c_location_id
         join C_country co on co.c_country_id = l.c_country_id
         join c_currency c on i.c_currency_id = c.C_Currency_id
where i.issotrx = 'Y'
  and i.isactive = 'Y'
  and i.DocStatus in ('CO', 'CL')
  and de_metas_endcustomer_fresh_reports.isEUShippingFromInvoice(i.C_invoice_id)='Y';


