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