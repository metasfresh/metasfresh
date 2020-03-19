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
                pricestd                numeric,
                m_productprice_id       integer,
                c_bpartner_id           numeric,
                m_hu_pi_item_product_id integer,
                uom_x12de355            text,
                c_bpartner_location_id  numeric,
                qtycuspertu             numeric,
                m_product_id            integer
            )
AS
$BODY$
--
SELECT plc.value                               AS prodvalue,
       plc.customerproductnumber               as customerproductnumber,
       plc.productcategory                     as productcategory,
       plc.productname                         as productname,
       plc.attributes                          as attributes,
       coalesce(hupiv.description, hupip.name) as itemproductname,
       -- if packing instructions exist, we will display the qty one as it refers to one TU (Transportation Unit).
--        CASE WHEN plc.itemproductname IS NOT NULL THEN 1 ELSE plc.qtycuspertu END AS qty,
       NULL::numeric                           as qty,
       plc.uomsymbol                           as uomsymbol,
       plc.pricestd                            as pricestd,
       plc.M_ProductPrice_ID                   as m_productprice_id,
       p_c_bpartner_id                         as c_bpartner_id,
       plc.M_HU_PI_Item_Product_ID             as m_hu_pi_item_product_id,
       plc.UOM_X12DE355                        as uom_x12de355,
       p_c_bpartner_location_id                as c_bpartner_location_id,
       plc.qtycuspertu                         as qtycuspertu,
       plc.bp_value							   as reportfilename

FROM report.fresh_PriceList_Details_Report(p_c_bpartner_id, p_m_pricelist_version_id, NULL, p_ad_language) plc
         LEFT OUTER JOIN M_HU_PI_Item_Product hupip on hupip.M_HU_PI_Item_Product_ID = plc.M_HU_PI_Item_Product_ID
         LEFT OUTER JOIN M_HU_PI_Item hupii on hupii.M_HU_PI_Item_ID = hupip.M_HU_PI_Item_ID
         LEFT OUTER JOIN M_HU_PI_Version hupiv on hupiv.M_HU_PI_Version_ID = hupii.M_HU_PI_Version_ID
--
$BODY$
    LANGUAGE sql STABLE
                 COST 100
                 ROWS 1000;
