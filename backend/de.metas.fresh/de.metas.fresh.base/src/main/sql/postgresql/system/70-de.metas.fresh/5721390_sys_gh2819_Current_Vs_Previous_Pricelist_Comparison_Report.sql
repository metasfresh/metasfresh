DROP FUNCTION IF EXISTS report.Current_Vs_Previous_Pricelist_Comparison_Report(numeric,
                                                                               numeric,
                                                                               text,
                                                                               text,
                                                                               text)
;


CREATE FUNCTION report.Current_Vs_Previous_Pricelist_Comparison_Report(p_c_bpartner_id              numeric DEFAULT NULL::numeric,
                                                                       p_c_bp_group_id              numeric DEFAULT NULL::numeric,
                                                                       p_issotrx                    text DEFAULT 'Y'::text,
                                                                       p_ad_language                text DEFAULT 'en_US'::text,
                                                                       p_show_product_price_pi_flag text DEFAULT 'Y'::text)
    RETURNS TABLE
            (
                bp_value                   text,
                bp_name                    text,
                productcategory            text,
                m_product_id               integer,
                value                      text,
                customerproductnumber      text,
                productname                text,
                isseasonfixedprice         text,
                itemproductname            text,
                qtycuspertu                numeric,
                packingmaterialname        text,
                pricestd                   numeric,
                pricepattern1              text,
                altpricestd                numeric,
                pricepattern2              text,
                hasaltprice                integer,
                uomsymbol                  text,
                uom_x12de355               text,
                attributes                 text,
                m_productprice_id          integer,
                m_attributesetinstance_id  integer,
                m_hu_pi_item_product_id    integer,
                currency                   text,
                currency2                  text,
                validfromplv1              timestamp WITHOUT TIME ZONE,
                validfromplv2              timestamp WITHOUT TIME ZONE,
                nameplv1                   text,
                nameplv2                   text,
                c_bpartner_location_id     numeric,
                ad_org_id                  numeric,
                show_product_price_pi_flag text
            )
    STABLE
    LANGUAGE sql
AS
$$
WITH plvvr AS
         (SELECT plv.* FROM Report.Fresh_PriceList_Version_Val_Rule plv),
     bpg AS (SELECT DISTINCT b.c_bpartner_id FROM c_bpartner b WHERE b.c_bp_group_id = p_C_BP_Group_ID),
     PriceListVersionsByValidFrom AS
         (SELECT t.*
          FROM (SELECT --
                       plv.c_bpartner_id,
                       plv.m_pricelist_version_id,
                       plv.validfrom,
                       plv.name,
                       ROW_NUMBER() OVER (PARTITION BY plv.c_bpartner_id ORDER BY plv.validfrom DESC, plv.m_pricelist_version_id DESC) rank
                FROM plvvr plv
                WHERE TRUE
                  AND plv.validfrom <= NOW()
                  AND plv.issotrx = p_IsSoTrx
                  AND (p_C_BPartner_ID IS NULL OR plv.c_bpartner_id = p_C_BPartner_ID)
                  AND (p_C_BP_Group_ID IS NULL OR plv.c_bpartner_id IN (SELECT bpg.c_bpartner_id FROM bpg))
                ORDER BY plv.validfrom DESC,
                         plv.m_pricelist_version_id DESC) t
          WHERE t.rank <= 2),
     currentAndPreviousPLV AS
         (
             -- implementation detail: all these sub-selects would be better implemented with a pivot. Unfortunately i cant understand how pivots work.
             SELECT DISTINCT --
                             plvv.c_bpartner_id,
                             (SELECT plvv2.m_pricelist_version_id FROM PriceListVersionsByValidFrom plvv2 WHERE plvv2.rank = 1 AND plvv2.c_bpartner_id = plvv.c_bpartner_id) PLV1_ID,
                             (SELECT plvv2.m_pricelist_version_id FROM PriceListVersionsByValidFrom plvv2 WHERE plvv2.rank = 2 AND plvv2.c_bpartner_id = plvv.c_bpartner_id) PLV2_ID,
                             (SELECT plvv2.validfrom FROM PriceListVersionsByValidFrom plvv2 WHERE plvv2.rank = 1 AND plvv2.c_bpartner_id = plvv.c_bpartner_id)              validFromPLV1,
                             (SELECT plvv2.validfrom FROM PriceListVersionsByValidFrom plvv2 WHERE plvv2.rank = 2 AND plvv2.c_bpartner_id = plvv.c_bpartner_id)              validFromPLV2,
                             (SELECT plvv2.name FROM PriceListVersionsByValidFrom plvv2 WHERE plvv2.rank = 1 AND plvv2.c_bpartner_id = plvv.c_bpartner_id)                   namePLV1,
                             (SELECT plvv2.name FROM PriceListVersionsByValidFrom plvv2 WHERE plvv2.rank = 2 AND plvv2.c_bpartner_id = plvv.c_bpartner_id)                   namePLV2
             FROM PriceListVersionsByValidFrom plvv
             ORDER BY plvv.c_bpartner_id),
     result AS
         (SELECT t.*,
                 plv.validFromPLV1,
                 plv.validFromPLV2,
                 plv.namePLV1,
                 plv.namePLV2,
                 (SELECT bpl.c_bpartner_location_id FROM c_bpartner_location bpl WHERE bpl.c_bpartner_id = plv.c_bpartner_id ORDER BY bpl.isbilltodefault DESC LIMIT 1) c_bpartner_location_id,
                 (SELECT plv2.ad_org_id FROM m_pricelist_version plv2 WHERE plv2.m_pricelist_version_id = plv.PLV1_ID)                                                  AD_Org_ID
          FROM currentAndPreviousPLV plv
                   INNER JOIN LATERAL report.fresh_PriceList_Details_Report(
                  plv.c_bpartner_id,
                  plv.PLV1_ID,
                  plv.PLV2_ID,
                  p_AD_Language,
                  p_show_product_price_pi_flag
                                      ) AS t ON TRUE)
SELECT --
       r.bp_value,
       r.bp_name,
       r.productcategory,
       r.m_product_id,
       r.value,
       r.customerproductnumber,
       r.productname,
       r.isseasonfixedprice::text,
       r.itemproductname,
       r.qtycuspertu,
       r.packingmaterialname,
       r.pricestd,
       PricePattern1,
       r.altpricestd,
       PricePattern2,
       r.hasaltprice,
       r.uomsymbol,
       r.uom_x12de355,
       r.attributes,
       r.m_productprice_id,
       r.m_attributesetinstance_id,
       r.m_hu_pi_item_product_id,
       r.currency::text,
       r.currency2::text,
       r.validFromPLV1,
       r.validFromPLV2,
       r.namePLV1,
       r.namePLV2,
       r.c_bpartner_location_id,
       r.AD_Org_ID,
       p_show_product_price_pi_flag AS show_product_price_pi_flag
FROM result r
ORDER BY r.bp_value,
         r.productCategory,
         r.value
$$
;


