DROP FUNCTION IF EXISTS report.fresh_PriceList_Details_Report(numeric,
                                                              numeric,
                                                              numeric,
                                                              character varying,
                                                              text)
;

CREATE OR REPLACE FUNCTION report.fresh_pricelist_details_report(IN p_c_bpartner_id              numeric,
                                                                 IN p_m_pricelist_version_id     numeric,
                                                                 IN p_alt_pricelist_version_id   numeric,
                                                                 IN p_ad_language                character varying,
                                                                 IN p_show_product_price_pi_flag text)
    RETURNS TABLE
            (
                bp_value                   text,
                bp_name                    text,
                productcategory            text,
                m_product_id               integer,
                value                      text,
                customerproductnumber      text,
                productname                text,
                isseasonfixedprice         character,
                itemproductname            character varying,
                qtycuspertu                numeric,
                packingmaterialname        text,
                pricestd                   numeric,
                PricePattern1              text,
                altpricestd                numeric,
                PricePattern2              text,
                hasaltprice                integer,
                uomsymbol                  text,
                uom_x12de355               text,
                attributes                 text,
                m_productprice_id          integer,
                m_attributesetinstance_id  integer,
                m_hu_pi_item_product_id    integer,
                currency                   character(3),
                currency2                  character(3),
                show_product_price_pi_flag text
            )
AS
$BODY$
    /**
      IMPORTANT: keep in sync with report.fresh_PriceList_Details_Report_With_PP_PI
     */
SELECT --
       bp.value                                                                                             AS BP_Value,
       bp.name                                                                                              AS BP_Name,
       plc.ProductCategory,
       plc.M_Product_ID::integer,
       plc.Value,
       bpp.ProductNo                                                                                        AS CustomerProductNumber,
       COALESCE(p_trl.name, plc.ProductName)                                                                AS ProductName,
       plc.IsSeasonFixedPrice,
       CASE WHEN plc.m_hu_pi_version_id = 101 THEN NULL ELSE plc.ItemProductName END                        AS ItemProductName,
       plc.QtyCUsPerTU,
       plc.PackingMaterialName,
       plc.PriceStd,
       PricePattern1,
       plc.AltPriceStd,
       PricePattern2,
       plc.HasAltPrice,
       plc.UOMSymbol,
       plc.UOM_X12DE355::text,
       CASE WHEN p_ad_language = 'fr_CH' THEN REPLACE(plc.Attributes, 'AdR', 'DLR') ELSE plc.Attributes END AS Attributes,
       plc.M_ProductPrice_ID::integer,
       plc.M_AttributeSetInstance_ID::integer,
       plc.M_HU_PI_Item_Product_ID::integer,
       plc.currency                                                                                         AS currency,
       plc.currency2                                                                                        AS currency2,
       p_show_product_price_pi_flag                                                                         AS show_product_price_pi_flag

FROM report.reportPriceListComparation(
             p_C_BPartner_ID := p_c_bpartner_id,
             p_M_PriceList_Version_ID := p_m_pricelist_version_id,
             p_Alt_PriceList_Version_ID := p_alt_pricelist_version_id
         ) plc
         LEFT OUTER JOIN M_Product_Trl p_trl ON p_trl.M_Product_ID = plc.M_Product_ID AND p_trl.AD_Language = p_ad_language AND p_trl.isActive = 'Y'
         LEFT OUTER JOIN C_BPartner bp ON bp.C_BPartner_ID = plc.C_BPartner_ID AND bp.isActive = 'Y'
         LEFT OUTER JOIN C_BPartner_Product bpp ON bpp.C_BPartner_ID = plc.C_BPartner_ID AND bpp.M_Product_ID = plc.M_Product_ID AND bpp.isActive = 'Y'
ORDER BY plc.ProductCategoryValue,
         plc.ProductName,
         plc.seqNo,
         plc.attributes,
         plc.ItemProductName;
    --
$BODY$
    LANGUAGE sql STABLE
                 COST 100
                 ROWS 1000
;
