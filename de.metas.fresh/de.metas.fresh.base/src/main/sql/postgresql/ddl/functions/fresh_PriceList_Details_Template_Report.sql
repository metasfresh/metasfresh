drop function if exists report.fresh_pricelist_details_template_report(numeric, numeric, character varying, numeric);
CREATE OR REPLACE FUNCTION report.fresh_pricelist_details_template_report(IN p_c_bpartner_id numeric, IN p_m_pricelist_version_id numeric, IN p_ad_language character varying,
                                                                          IN p_c_bpartner_location_id numeric)
    RETURNS TABLE
            (
                prodvalue               text,
                customerproductnumber   text,
                productcategory         text,
                productname             text,
                attributes              text,
                itemproductname         text,
                qty                     numeric,
                uomsymbol               text,
                pricestd                text,
                m_productprice_id       integer,
                c_bpartner_id           numeric,
                m_hu_pi_item_product_id integer,
                uom_x12de355            text,
                c_bpartner_location_id  numeric,
                qtycuspertu             numeric,
                bp_value                text,
                bp_name                 text,
                validfrom               timestamp without time zone,
                reportfilename          text
            )

AS
$BODY$
--

SELECT plc.value                                                                                                              AS prodvalue,
       plc.customerproductnumber                                                                                              as customerproductnumber,
       plc.productcategory                                                                                                    as productcategory,
       plc.productname                                                                                                        as productname,
       plc.attributes                                                                                                         as attributes,
       case
           when coalesce(hupiv.description, hupip.name) is null then plc.uomsymbol
           else
               coalesce(hupiv.description, hupip.name) end                                                                    as itemproductname,
       -- if packing instructions exist, we will display the qty one as it refers to one TU (Transportation Unit).
--        CASE WHEN plc.itemproductname IS NOT NULL THEN 1 ELSE plc.qtycuspertu END AS qty,
       NULL::numeric                                                                                                          as qty,
       plc.uomsymbol                                                                                                          as uomsymbol,
       cast(plc.pricestd as decimal(19, 2))::text                                                                             as pricestd,
       plc.M_ProductPrice_ID                                                                                                  as m_productprice_id,
       p_c_bpartner_id                                                                                                        as c_bpartner_id,
       plc.M_HU_PI_Item_Product_ID                                                                                            as m_hu_pi_item_product_id,
       case when plc.m_hu_pi_item_product_id is not null then 'COLI' else plc.uom_x12de355 end                                as uom_x12de355,
       p_c_bpartner_location_id                                                                                               as c_bpartner_location_id,
       plc.qtycuspertu                                                                                                        as qtycuspertu,
       plc.BP_Value                                                                                                           as bp_value,
       plc.BP_Name                                                                                                            as bp_name,
       (SELECT validfrom FROM M_Pricelist_Version WHERE M_Pricelist_Version_ID = p_m_pricelist_version_id AND isActive = 'Y') AS validfrom,
       CONCAT(bp_value, '_', bp_name, '_', validfrom, '.xls')                                                                 as reportfilename

FROM report.fresh_PriceList_Details_Report(p_c_bpartner_id, p_m_pricelist_version_id, NULL, p_ad_language) plc
         LEFT OUTER JOIN M_HU_PI_Item_Product hupip on hupip.M_HU_PI_Item_Product_ID = plc.M_HU_PI_Item_Product_ID
         LEFT OUTER JOIN M_HU_PI_Item hupii on hupii.M_HU_PI_Item_ID = hupip.M_HU_PI_Item_ID
         LEFT OUTER JOIN M_HU_PI_Version hupiv on hupiv.M_HU_PI_Version_ID = hupii.M_HU_PI_Version_ID
--


$BODY$
    LANGUAGE sql STABLE
                 COST 100
                 ROWS 1000;
