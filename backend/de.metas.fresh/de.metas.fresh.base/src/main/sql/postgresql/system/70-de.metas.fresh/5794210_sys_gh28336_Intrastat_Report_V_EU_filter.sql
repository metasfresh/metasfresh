-- gh#28336: Add EU membership filter to Intrastat_Report_V.
-- Only intra-EU trade is Intrastat-reportable. Non-EU partners (CN, HK, VN, etc.)
-- are customs declarations, not Intrastat. Uses C_CountryArea_Assign with date-based
-- validity (respects Brexit: GB excluded for invoices after 2020-12-31).

DROP VIEW IF EXISTS de_metas_endcustomer_fresh_reports.Intrastat_Report_V;

CREATE OR REPLACE VIEW de_metas_endcustomer_fresh_reports.Intrastat_Report_V AS
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
       OrgName,
       CustomsTariff,
       sum(weight)  as weight,
       vataxid,
       c_year_id,
       issotrx
from (
         select cn.value        as commoditynumber,
                p.Name          as productName,
                p.description   as productDescription,
                coalesce(wlc.countrycode, org_country.countrycode) as deliveredFromCountry,
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
                o.Name          as OrgName,
                ct.value           AS CustomsTariff,
                -- Per-line weight: catch weight first, then UOM conversion to KG, then product weight fallback
                coalesce(
                    coalesce(iol.qtydeliveredcatch,
                             uomConvert(iol.M_Product_ID, iol.C_UOM_ID,
                                        (select C_UOM_ID from C_UOM where x12de355 = 'KGM' and isactive = 'Y' order by isdefault desc limit 1),
                                        iol.qtyentered)),
                    iol.qtyentered * p.weight
                )                  AS weight,
                bp.vataxid,
                per.c_year_id,
                io.issotrx
         from M_InOut io
                  join AD_Org o on io.ad_org_id = o.ad_org_id
                  -- Org's country via AD_OrgInfo -> OrgBP_Location -> C_Location -> C_Country
                  join AD_OrgInfo org_info on io.ad_org_id = org_info.ad_org_id
                  left join C_BPartner_Location org_bpl on org_info.OrgBP_Location_ID = org_bpl.C_BPartner_Location_ID
                  left join C_Location org_loc on org_bpl.C_Location_ID = org_loc.C_Location_ID
                  left join C_Country org_country on org_loc.C_Country_ID = org_country.C_Country_ID
                  left join m_warehouse w on w.m_warehouse_id = io.m_warehouse_id
                  left join c_location wl on wl.c_location_id = w.c_location_id
                  left join C_country wlc on wlc.c_country_id = wl.c_country_id
                  join m_inoutline iol on iol.m_inout_id = io.m_inout_id
                  left join c_invoiceline il on il.m_inoutline_id = iol.m_inoutline_id
                  join C_invoice i on i.C_invoice_id = il.C_invoice_id and i.DocStatus IN ('CO', 'CL')
                  join m_product p on p.m_product_id = iol.m_product_id
                  left join m_commoditynumber cn on cn.m_commoditynumber_id = p.m_commoditynumber_id
                  left join C_country pco on pco.c_country_id = p.rawmaterialorigin_id
                  JOIN c_bpartner bp ON bp.c_bpartner_id = io.c_bpartner_id
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

                  LEFT OUTER JOIN M_CustomsTariff ct ON ct.M_CustomsTariff_ID = p.M_CustomsTariff_ID

                  -- Only intra-EU trade: partner country must be EU member at time of invoice
                  JOIN C_CountryArea_Assign eu_partner
                       ON eu_partner.C_Country_ID = co.C_Country_ID
                       AND eu_partner.C_CountryArea_ID = (SELECT ca.C_CountryArea_ID FROM C_CountryArea ca WHERE ca.value = 'EU' AND ca.isactive = 'Y')
                       AND eu_partner.isactive = 'Y'
                       AND i.dateinvoiced >= eu_partner.validfrom
                       AND (eu_partner.validto IS NULL OR i.dateinvoiced <= eu_partner.validto)

         where io.isactive = 'Y'
           and iol.ispackagingmaterial = 'N'
           -- Exclude lines with no customs tariff AND zero invoice value.
           and not (ct.M_CustomsTariff_ID is null and il.linenetamt = 0)
           -- Only international shipments: partner country differs from org/warehouse country.
           and coalesce(wlc.countrycode, org_country.countrycode) is not null
           and co.countrycode != coalesce(wlc.countrycode, org_country.countrycode)
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
         OrgName,
         CustomsTariff,
         vataxid,
         c_year_id,
         issotrx
order by deliveryCountry, commoditynumber;
