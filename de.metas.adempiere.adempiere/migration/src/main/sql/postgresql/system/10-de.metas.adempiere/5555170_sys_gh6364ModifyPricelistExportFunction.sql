DROP FUNCTION IF EXISTS report.fresh_pricelist_details_template_report(NUMERIC, NUMERIC, CHARACTER VARYING, NUMERIC);
CREATE OR REPLACE FUNCTION report.fresh_pricelist_details_template_report(IN p_c_bpartner_id NUMERIC, IN p_m_pricelist_version_id NUMERIC, IN p_ad_language CHARACTER VARYING,
                                                                          IN p_c_bpartner_location_id NUMERIC)
    RETURNS TABLE
            (
                prodvalue               TEXT,
                customerproductnumber   TEXT,
                productcategory         TEXT,
                productname             TEXT,
                attributes              TEXT,
                itemproductname         TEXT,
                qty                     NUMERIC,
                uomsymbol               TEXT,
                pricestd                NUMERIC,
                m_productprice_id       INTEGER,
                c_bpartner_id           NUMERIC,
                m_hu_pi_item_product_id INTEGER,
                uom_x12de355            TEXT,
                c_bpartner_location_id  NUMERIC,
                qtycuspertu             NUMERIC,
                m_product_id            INTEGER,
                bp_value                TEXT,
                bp_name                 TEXT,
                reportfilename          TEXT
            )

AS
$BODY$
--

SELECT plc.VALUE                                                                                                    AS prodvalue,
       plc.customerproductnumber                                                                                    AS customerproductnumber,
       plc.productcategory                                                                                          AS productcategory,
       plc.productname                                                                                              AS productname,
       plc.attributes                                                                                               AS attributes,
       COALESCE(hupiv.description, hupip.NAME, plc.uomsymbol)                                                       AS itemproductname,
       NULL::NUMERIC                                                                                                AS qty,
       plc.uomsymbol                                                                                                AS uomsymbol,
       ROUND(plc.pricestd, cur.stdprecision)                                                                        AS pricestd,
       plc.M_ProductPrice_ID                                                                                        AS m_productprice_id,
       p_c_bpartner_id                                                                                              AS c_bpartner_id,
       plc.M_HU_PI_Item_Product_ID                                                                                  AS m_hu_pi_item_product_id,
       CASE WHEN plc.m_hu_pi_item_product_id IS NOT NULL THEN 'COLI' ELSE plc.uom_x12de355 END                      AS uom_x12de355,
       p_c_bpartner_location_id                                                                                     AS c_bpartner_location_id,
       plc.qtycuspertu                                                                                              AS qtycuspertu,
       plc.m_product_id                                                                                             AS m_product_id,
       plc.BP_Value                                                                                                 AS bp_value,
       plc.BP_Name                                                                                                  AS bp_name,
       CONCAT(bp_value, '_', bp_name, '_', CASE WHEN prlv.isactive = 'Y' THEN prlv.validfrom ELSE NULL END, '.xls') AS reportfilename

FROM report.fresh_PriceList_Details_Report(p_c_bpartner_id, p_m_pricelist_version_id, NULL, p_ad_language) plc
         LEFT OUTER JOIN M_HU_PI_Item_Product hupip ON hupip.M_HU_PI_Item_Product_ID = plc.M_HU_PI_Item_Product_ID
         LEFT OUTER JOIN M_HU_PI_Item hupii ON hupii.M_HU_PI_Item_ID = hupip.M_HU_PI_Item_ID
         LEFT OUTER JOIN M_HU_PI_Version hupiv ON hupiv.M_HU_PI_Version_ID = hupii.M_HU_PI_Version_ID
         LEFT OUTER JOIN M_Pricelist_Version prlv ON prlv.m_pricelist_version_id = p_m_pricelist_version_id
         LEFT OUTER JOIN M_Pricelist prl ON prlv.m_pricelist_id = prl.m_pricelist_id
         LEFT OUTER JOIN C_Currency cur ON prl.c_currency_id = cur.c_currency_id
--


$BODY$
    LANGUAGE sql STABLE
                 COST 100
                 ROWS 1000;
