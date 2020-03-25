DROP FUNCTION IF EXISTS report.Current_Vs_Previous_Pricelist_Comparison_Report(IN p_C_BPartner_ID numeric, IN p_AD_Language text)
;

-- todo add bpartner group as well
CREATE OR REPLACE FUNCTION report.Current_Vs_Previous_Pricelist_Comparison_Report(IN p_C_BPartner_ID numeric,
                                                                                  IN p_AD_Language   text = 'en_US')
    RETURNS TABLE
            (
                bp_value                  text,
                bp_name                   text,
                ProductCategory           text,
                M_Product_ID              integer,
                value                     text,
                CustomerProductNumber     text,
                ProductName               text,
                IsSeasonFixedPrice        text,
                ItemProductName           text,
                qtycuspertu               numeric,
                packingmaterialname       text,
                pricestd                  numeric,
                altpricestd               numeric,
                hasaltprice               integer,
                uomsymbol                 text,
                uom_x12de355              text,
                Attributes                text,
                m_productprice_id         integer,
                m_attributesetinstance_id integer,
                m_hu_pi_item_product_id   integer,
                currency                  text,
                currency2                 text
            )
AS
$$
WITH PriceListVersionsByValidFrom AS
         (
             SELECT --
                    plv.c_bpartner_id,
                    row_number() OVER (PARTITION BY plv.c_bpartner_id ORDER BY plv.validfrom DESC, plv.m_pricelist_version_id DESC) rank,
                    plv.m_pricelist_version_id,
                    plv.validfrom,
                    plv.name,
                    plv.issotrx
             FROM Report.Fresh_PriceList_Version_Val_Rule plv
             WHERE TRUE
               AND plv.issotrx = 'Y'
             ORDER BY TRUE,
                      plv.validfrom DESC,
                      plv.m_pricelist_version_id DESC
         ),
     currentAndPreviousPLV AS
         (
             SELECT DISTINCT --
                             plvv.c_bpartner_id,
                             (SELECT m_pricelist_version_id FROM PriceListVersionsByValidFrom plvv2 WHERE plvv2.rank = 1 AND plvv2.c_bpartner_id = plvv.c_bpartner_id) currentPlv_ID,
                             (SELECT m_pricelist_version_id FROM PriceListVersionsByValidFrom plvv2 WHERE plvv2.rank = 2 AND plvv2.c_bpartner_id = plvv.c_bpartner_id) previousPlv_ID
             FROM PriceListVersionsByValidFrom plvv
             WHERE TRUE
               AND (p_C_BPartner_ID IS NULL OR plvv.c_bpartner_id = p_C_BPartner_ID)
             ORDER BY plvv.c_bpartner_id
         ),
     result AS
         (
             SELECT data.*
             FROM currentAndPreviousPLV plv
                      INNER JOIN LATERAL report.fresh_PriceList_Details_Report(
                     plv.c_bpartner_id,
                     plv.currentPlv_ID,
                     plv.previousPlv_ID,
                     p_AD_Language
                 ) AS data ON TRUE
         )
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
       r.altpricestd,
       r.hasaltprice,
       r.uomsymbol,
       r.uom_x12de355,
       r.attributes,
       r.m_productprice_id,
       r.m_attributesetinstance_id,
       r.m_hu_pi_item_product_id,
       r.currency::text,
       r.currency2::text
FROM result r
$$
    LANGUAGE sql STABLE
;



---------------------------
---------------------------
---------------------------
---------------------------

-- for bpartners: AND plvv.c_bpartner_id IN (2157534, 2156515, 2157468) this takes 1m 30s
SELECT *
FROM report.Current_Vs_Previous_Pricelist_Comparison_Report(NULL)
;



-- speed test of view for the test bpartners AND plvv.c_bpartner_id IN (2157534, 2156515, 2157468)
-- this takes 2 seconds
SELECT *
FROM RV_fresh_PriceList_Comparison v
WHERE v.c_bpartner_id IN (2157534, 2156515, 2157468)
;


----

SELECT *
FROM report.fresh_PriceList_Details_Report(
        2157500,
        2003337,
        2002393,
        'en_US'
    )
;



SELECT (report.fresh_PriceList_Details_Report(
        2157500,
        2003337,
        2002393,
        'en_US'
    )).*
;
