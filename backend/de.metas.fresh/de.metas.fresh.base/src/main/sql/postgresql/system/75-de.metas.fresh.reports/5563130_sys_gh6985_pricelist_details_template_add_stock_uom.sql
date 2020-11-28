DROP FUNCTION IF EXISTS report.fresh_pricelist_details_template_report(NUMERIC, NUMERIC, CHARACTER VARYING, NUMERIC, text);
CREATE OR REPLACE FUNCTION report.fresh_pricelist_details_template_report(IN p_c_bpartner_id numeric, IN p_m_pricelist_version_id numeric, IN p_ad_language character varying,
                                                                          IN p_c_bpartner_location_id numeric, IN p_show_product_price_pi_flag text)
    RETURNS TABLE
            (
                prodvalue                  text,
                customerproductnumber      text,
                productcategory            text,
                productname                text,
                attributes                 text,
                itemproductname            text,
                qty                        numeric,
                priceUOMSymbol             text,
                pricestd                   text,
                m_productprice_id          integer,
                c_bpartner_id              numeric,
                m_hu_pi_item_product_id    integer,
                uom_x12de355               text,
                priceUOM_x12de355          text,
                c_bpartner_location_id     numeric,
                qtycuspertu                numeric,
                m_product_id               integer,
                bp_value                   text,
                bp_name                    text,
                reportfilename             text,
                show_product_price_pi_flag text
            )

AS
$BODY$
    --

SELECT plc.value                                                                                                          AS prodvalue,
       plc.customerproductnumber                                                                                          as customerproductnumber,
       plc.productcategory                                                                                                as productcategory,
       plc.productname                                                                                                    as productname,
       plc.attributes                                                                                                     as attributes,
       COALESCE(replace(plc.itemproductname, hupiv.name, pi.Name), stockingUOM.uomsymbol)                                 as itemproductname,
       NULL::numeric                                                                                                      as qty,
       plc.uomsymbol                                                                                                      as priceUOMSymbol,
       to_char(plc.pricestd, getPricePattern(prl.priceprecision::integer))                                       as pricestd,
       plc.M_ProductPrice_ID                                                                                              as m_productprice_id,
       p_c_bpartner_id                                                                                                    as c_bpartner_id,
       plc.M_HU_PI_Item_Product_ID                                                                                        as m_hu_pi_item_product_id,
       stockingUOM.x12de355                                                                                               as uom_x12de355,
       plc.UOM_x12de355                                                                                                   as priceUOM_x12de355,
       p_c_bpartner_location_id                                                                                           as c_bpartner_location_id,
       plc.qtycuspertu /* internally used as a multiplier for the CU-qty if M_HU_PI_Item_Product_ID is given */           as qtycuspertu,
       plc.m_product_id                                                                                                   as m_product_id,
       plc.BP_Value                                                                                                       as bp_value,
       plc.BP_Name                                                                                                        as bp_name,
       CONCAT(bp_value, '_', bp_name, '_', case when prlv.isactive = 'Y' then prlv.validfrom::date else null end, '.xls') as reportfilename,
       p_show_product_price_pi_flag                                                                                       as show_product_price_pi_flag

FROM report.fresh_PriceList_Details_Report(p_c_bpartner_id, p_m_pricelist_version_id, NULL, p_ad_language, p_show_product_price_pi_flag) plc
         LEFT OUTER JOIN M_HU_PI_Item_Product hupip on hupip.M_HU_PI_Item_Product_ID = plc.M_HU_PI_Item_Product_ID
         LEFT OUTER JOIN M_HU_PI_Item hupii on hupii.M_HU_PI_Item_ID = hupip.M_HU_PI_Item_ID
         LEFT OUTER JOIN M_HU_PI_Version hupiv on hupiv.M_HU_PI_Version_ID = hupii.M_HU_PI_Version_ID
         LEFT OUTER JOIN M_HU_PI pi on pi.M_HU_PI_ID = hupiv.M_HU_PI_ID

         LEFT OUTER JOIN M_Product p ON p.m_product_id = plc.m_product_id
         LEFT OUTER JOIN C_UOM stockingUOM ON stockingUOM.c_uom_id = p.c_uom_id

         LEFT OUTER JOIN M_Pricelist_Version prlv on prlv.m_pricelist_version_id = p_m_pricelist_version_id
         LEFT OUTER JOIN M_Pricelist prl on prlv.m_pricelist_id = prl.m_pricelist_id

$BODY$
    LANGUAGE sql STABLE;

COMMENT ON FUNCTION report.fresh_pricelist_details_template_report(NUMERIC, NUMERIC, CHARACTER VARYING, NUMERIC, text) IS
    'Used in UNION with fresh_pricelist_details_template_report_With_PP_PI to export pricing data.
    The pricing data is exported into the excel-template file jasperreports\de\metas\reports\pricelist\report.xls.
    This is done by the AD_Process with Value=RV_Fresh_PriceList_Exporter';

DROP FUNCTION IF EXISTS report.fresh_pricelist_details_template_report_With_PP_PI(NUMERIC, NUMERIC, CHARACTER VARYING, NUMERIC, text);
CREATE OR REPLACE FUNCTION report.fresh_pricelist_details_template_report_With_PP_PI(IN p_c_bpartner_id numeric, IN p_m_pricelist_version_id numeric, IN p_ad_language character varying,
                                                                                     IN p_c_bpartner_location_id numeric, IN p_show_product_price_pi_flag text)
    RETURNS TABLE
            (
                prodvalue                  text,
                customerproductnumber      text,
                productcategory            text,
                productname                text,
                attributes                 text,
                itemproductname            text,
                qty                        numeric,
                priceUOMSymbol             text,
                pricestd                   text,
                m_productprice_id          integer,
                c_bpartner_id              numeric,
                m_hu_pi_item_product_id    integer,
                uom_x12de355               text,
                priceUOM_x12de355          text,
                c_bpartner_location_id     numeric,
                qtycuspertu                numeric,
                m_product_id               integer,
                bp_value                   text,
                bp_name                    text,
                reportfilename             text,
                show_product_price_pi_flag text
            )

AS
$BODY$
    --

SELECT plc.value                                                                                                          AS prodvalue,
       plc.customerproductnumber                                                                                          as customerproductnumber,
       plc.productcategory                                                                                                as productcategory,
       plc.productname                                                                                                    as productname,
       plc.attributes                                                                                                     as attributes,
       COALESCE(replace(plc.itemproductname, hupiv.name, pi.Name), stockingUOM.uomsymbol)                                 as itemproductname,
       NULL::numeric                                                                                                      as qty,
       plc.uomsymbol                                                                                                      as priceUOMSymbol,
       to_char(plc.pricestd, getPricePattern(prl.priceprecision::integer))                                      as pricestd,
       plc.M_ProductPrice_ID                                                                                              as m_productprice_id,
       p_c_bpartner_id                                                                                                    as c_bpartner_id,
       plc.M_HU_PI_Item_Product_ID                                                                                        as m_hu_pi_item_product_id,
       stockingUOM.x12de355                                                                                               as uom_x12de355,
       plc.uom_x12de355                                                                                                   as priceUOM_x12de355,
       p_c_bpartner_location_id                                                                                           as c_bpartner_location_id,
       plc.qtycuspertu /* internally used as a multiplier for the CU-qty if M_HU_PI_Item_Product_ID is given */           as qtycuspertu,
       plc.m_product_id                                                                                                   as m_product_id,
       plc.BP_Value                                                                                                       as bp_value,
       plc.BP_Name                                                                                                        as bp_name,
       CONCAT(bp_value, '_', bp_name, '_', case when prlv.isactive = 'Y' then prlv.validfrom::date else null end, '.xls') as reportfilename,
       p_show_product_price_pi_flag                                                                                       as show_product_price_pi_flag

FROM report.fresh_PriceList_Details_Report_With_PP_PI(p_c_bpartner_id, p_m_pricelist_version_id, NULL, p_ad_language, p_show_product_price_pi_flag) plc
         LEFT OUTER JOIN M_HU_PI_Item_Product hupip on hupip.M_HU_PI_Item_Product_ID = plc.M_HU_PI_Item_Product_ID
         LEFT OUTER JOIN M_HU_PI_Item hupii on hupii.M_HU_PI_Item_ID = hupip.M_HU_PI_Item_ID
         LEFT OUTER JOIN M_HU_PI_Version hupiv on hupiv.M_HU_PI_Version_ID = hupii.M_HU_PI_Version_ID
         LEFT OUTER JOIN M_HU_PI pi on pi.M_HU_PI_ID = hupiv.M_HU_PI_ID

         LEFT OUTER JOIN M_Product p ON p.m_product_id = plc.m_product_id
         LEFT OUTER JOIN C_UOM stockingUOM ON stockingUOM.c_uom_id = p.c_uom_id

         LEFT OUTER JOIN M_Pricelist_Version prlv on prlv.m_pricelist_version_id = p_m_pricelist_version_id
         LEFT OUTER JOIN M_Pricelist prl on prlv.m_pricelist_id = prl.m_pricelist_id

$BODY$
    LANGUAGE sql STABLE;

COMMENT ON FUNCTION report.fresh_pricelist_details_template_report_With_PP_PI(NUMERIC, NUMERIC, CHARACTER VARYING, NUMERIC, text) IS
    'Used in UNION with fresh_pricelist_details_template_report to export pricing data.
The pricing data is exported into the excel-template file jasperreports\de\metas\reports\pricelist\report.xls.
This is done by the AD_Process with Value=RV_Fresh_PriceList_Exporter';
