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