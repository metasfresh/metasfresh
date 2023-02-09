DROP FUNCTION IF EXISTS report.fresh_pricelist_details_template_report_With_PP_PI(NUMERIC,
                                                                                  NUMERIC,
                                                                                  CHARACTER VARYING,
                                                                                  NUMERIC,
                                                                                  text,
                                                                                  numeric,
                                                                                  numeric)
;

CREATE OR REPLACE FUNCTION report.fresh_pricelist_details_template_report_With_PP_PI(IN p_c_bpartner_id              numeric,
                                                                                     IN p_m_pricelist_version_id     numeric,
                                                                                     IN p_ad_language                character varying,
                                                                                     IN p_c_bpartner_location_id     numeric,
                                                                                     IN p_show_product_price_pi_flag text,
                                                                                     IN p_dropship_partner_id        numeric DEFAULT NULL,
                                                                                     IN p_dropship_location_id       numeric DEFAULT NULL)
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
                show_product_price_pi_flag text,
                bill_bpartner_id           numeric,
                bill_location_id           numeric,
                handover_partner_id        numeric,
                handover_location_id       numeric,
                dropship_bpartner_id       numeric,
                dropship_location_id       numeric
            )

AS
$BODY$
    --

SELECT plc.value                                                                                                          AS prodvalue,
       plc.customerproductnumber                                                                                          AS customerproductnumber,
       plc.productcategory                                                                                                AS productcategory,
       plc.productname                                                                                                    AS productname,
       plc.attributes                                                                                                     AS attributes,
       COALESCE(REPLACE(plc.itemproductname, hupiv.name, pi.Name), stockingUOM.uomsymbol)                                 AS itemproductname,
       NULL::numeric                                                                                                      AS qty,
       plc.uomsymbol                                                                                                      AS priceUOMSymbol,
       TO_CHAR(plc.pricestd, getPricePattern(prl.priceprecision::integer))                                                AS pricestd,
       plc.M_ProductPrice_ID                                                                                              AS m_productprice_id,
       p_c_bpartner_id                                                                                                    AS c_bpartner_id,
       plc.M_HU_PI_Item_Product_ID                                                                                        AS m_hu_pi_item_product_id,
       stockingUOM.x12de355                                                                                               AS uom_x12de355,
       plc.uom_x12de355                                                                                                   AS priceUOM_x12de355,
       p_c_bpartner_location_id                                                                                           AS c_bpartner_location_id,
       plc.qtycuspertu /* internally used as a multiplier for the CU-qty if M_HU_PI_Item_Product_ID is given */           AS qtycuspertu,
       plc.m_product_id                                                                                                   AS m_product_id,
       plc.BP_Value                                                                                                       AS bp_value,
       plc.BP_Name                                                                                                        AS bp_name,
       CONCAT(bp_value, '_', bp_name, '_', CASE WHEN prlv.isactive = 'Y' THEN prlv.validfrom::date ELSE NULL END, '.xls') AS reportfilename,
       p_show_product_price_pi_flag                                                                                       AS show_product_price_pi_flag,
       p_c_bpartner_id                                                                                                    AS bill_bpartner_id,
       p_c_bpartner_location_id                                                                                           AS bill_location_id,
       p_dropship_partner_id                                                                                              AS handover_partner_id,
       p_dropship_location_id                                                                                             AS handover_location_id,
       p_dropship_partner_id                                                                                              AS dropship_bpartner_id,
       p_dropship_location_id                                                                                             AS dropship_location_id

FROM report.fresh_PriceList_Details_Report_With_PP_PI(p_c_bpartner_id, p_m_pricelist_version_id, NULL, p_ad_language, p_show_product_price_pi_flag) plc
         LEFT OUTER JOIN M_HU_PI_Item_Product hupip ON hupip.M_HU_PI_Item_Product_ID = plc.M_HU_PI_Item_Product_ID
            LEFT OUTER JOIN M_HU_PI_Item hupii ON hupii.M_HU_PI_Item_ID = hupip.M_HU_PI_Item_ID
                LEFT OUTER JOIN M_HU_PI_Version hupiv ON hupiv.M_HU_PI_Version_ID = hupii.M_HU_PI_Version_ID
                    LEFT OUTER JOIN M_HU_PI pi ON pi.M_HU_PI_ID = hupiv.M_HU_PI_ID

         LEFT OUTER JOIN M_Product p ON p.m_product_id = plc.m_product_id
            LEFT OUTER JOIN C_UOM stockingUOM ON stockingUOM.c_uom_id = p.c_uom_id

         LEFT OUTER JOIN M_Pricelist_Version prlv ON prlv.m_pricelist_version_id = p_m_pricelist_version_id
            LEFT OUTER JOIN M_Pricelist prl ON prlv.m_pricelist_id = prl.m_pricelist_id

$BODY$
    LANGUAGE sql STABLE
;

COMMENT ON FUNCTION report.fresh_pricelist_details_template_report_With_PP_PI(NUMERIC, NUMERIC, CHARACTER VARYING, NUMERIC, text, NUMERIC, NUMERIC) IS
    'Used in UNION with fresh_pricelist_details_template_report to export pricing data.
The pricing data is exported into the excel-template file jasperreports\de\metas\reports\pricelist\report.xls.
This is done by the AD_Process with Value=RV_Fresh_PriceList_Exporter'
;
