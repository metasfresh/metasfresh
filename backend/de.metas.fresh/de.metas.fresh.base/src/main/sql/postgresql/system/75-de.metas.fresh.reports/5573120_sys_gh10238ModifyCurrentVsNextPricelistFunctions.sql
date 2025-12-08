DROP FUNCTION IF EXISTS report.Current_Vs_Next_Pricelist_Comparison_Report(p_C_BPartner_ID numeric, p_C_BP_Group_ID numeric, p_IsSoTrx text, p_AD_Language text, p_show_product_price_pi_flag text)
;

CREATE OR REPLACE FUNCTION report.Current_Vs_Next_Pricelist_Comparison_Report(p_C_BPartner_ID              numeric = NULL,
                                                                              p_C_BP_Group_ID              numeric = NULL,
                                                                              p_IsSoTrx                    text = 'Y',
                                                                              p_AD_Language                TEXT = 'en_US',
                                                                              p_show_product_price_pi_flag text = 'Y')
    RETURNS TABLE
            (
                bp_value                   text,
                bp_name                    text,
                ProductCategory            text,
                M_Product_ID               integer,
                value                      text,
                CustomerProductNumber      text,
                ProductName                text,
                IsSeasonFixedPrice         text,
                ItemProductName            text,
                qtycuspertu                numeric,
                packingmaterialname        text,
                pricestd                   numeric,
                PricePattern1              text,
                altpricestd                numeric,
                PricePattern2              text,
                hasaltprice                integer,
                uomsymbol                  text,
                uom_x12de355               text,
                Attributes                 text,
                m_productprice_id          integer,
                m_attributesetinstance_id  integer,
                m_hu_pi_item_product_id    integer,
                currency                   text,
                currency2                  text,
                validFromPLV1              timestamp,
                validFromPLV2              timestamp,
                namePLV1                   text,
                namePLV2                   text,
                c_bpartner_location_id     numeric,
                AD_Org_ID                  numeric,
                show_product_price_pi_flag text
            )
AS
$$
WITH PriceListVersionsByValidFrom AS
         (
             SELECT t.*
             FROM ((SELECT --
                           plv.c_bpartner_id,
                           plv.m_pricelist_version_id,
                           plv.validfrom,
                           plv.name,
                           row_number() OVER (PARTITION BY plv.c_bpartner_id ORDER BY plv.validfrom DESC, plv.m_pricelist_version_id DESC) rank
                    FROM Report.Fresh_PriceList_Version_Val_Rule plv
                    WHERE TRUE
                      AND plv.validfrom <= now()
                      AND plv.issotrx = p_IsSoTrx
                      AND (p_C_BPartner_ID IS NULL OR plv.c_bpartner_id = p_C_BPartner_ID)
                      AND (p_C_BP_Group_ID IS NULL OR plv.c_bpartner_id IN (SELECT DISTINCT b.c_bpartner_id FROM c_bpartner b WHERE b.c_bp_group_id = p_C_BP_Group_ID))
                    order by
                             plv.validfrom DESC,
                             plv.m_pricelist_version_id DESC
                   )
                   UNION ALL
                   (SELECT --
                           plv.c_bpartner_id,
                           plv.m_pricelist_version_id,
                           plv.validfrom,
                           plv.name,
                           1001 - (row_number() OVER (PARTITION BY plv.c_bpartner_id ORDER BY plv.validfrom ASC, plv.m_pricelist_version_id ASC)) rank

                    FROM Report.Fresh_PriceList_Version_Val_Rule plv
                    WHERE TRUE
                      AND plv.validfrom > now()
                      AND plv.issotrx = p_IsSoTrx
                      AND (p_C_BPartner_ID IS NULL OR plv.c_bpartner_id = p_C_BPartner_ID)
                      AND (p_C_BP_Group_ID IS NULL OR plv.c_bpartner_id IN (SELECT DISTINCT b.c_bpartner_id FROM c_bpartner b WHERE b.c_bp_group_id = p_C_BP_Group_ID))
                    order by
                             plv.validfrom ASC,
                             plv.m_pricelist_version_id ASC
                   )
                  ) t

             WHERE t.rank = 1
                OR t.rank = 1000
         ),
     currentAndPreviousPLV AS
         (
             -- implementation detail: all these sub-selects would be better implemented with a pivot. Unfortunately i cant understand how pivots work.
             SELECT DISTINCT --
                             plvv.c_bpartner_id,
                             (SELECT plvv2.m_pricelist_version_id FROM PriceListVersionsByValidFrom plvv2 WHERE plvv2.rank = 1 AND plvv2.c_bpartner_id = plvv.c_bpartner_id)   PLV1_ID,
                             (SELECT plvv2.m_pricelist_version_id FROM PriceListVersionsByValidFrom plvv2 WHERE plvv2.rank = 1000 AND plvv2.c_bpartner_id = plvv.c_bpartner_id) PLV2_ID,
                             (SELECT plvv2.validfrom FROM PriceListVersionsByValidFrom plvv2 WHERE plvv2.rank = 1 AND plvv2.c_bpartner_id = plvv.c_bpartner_id)                validFromPLV1,
                             (SELECT plvv2.validfrom FROM PriceListVersionsByValidFrom plvv2 WHERE plvv2.rank = 1000 AND plvv2.c_bpartner_id = plvv.c_bpartner_id)              validFromPLV2,
                             (SELECT plvv2.name FROM PriceListVersionsByValidFrom plvv2 WHERE plvv2.rank = 1 AND plvv2.c_bpartner_id = plvv.c_bpartner_id)                     namePLV1,
                             (SELECT plvv2.name FROM PriceListVersionsByValidFrom plvv2 WHERE plvv2.rank = 1000 AND plvv2.c_bpartner_id = plvv.c_bpartner_id)                   namePLV2
             FROM PriceListVersionsByValidFrom plvv
             ORDER BY plvv.c_bpartner_id
         ),
     result AS
         (
             SELECT t.*,
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
                 ) AS t ON TRUE
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
order by
         r.bp_value,
         r.productCategory,
         r.value
$$
    LANGUAGE sql STABLE
;


/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2020 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


/*
How to run

- All Bpartners

SELECT *
FROM report.Current_Vs_Next_Pricelist_Comparison_Report()
;

- Specific Bpartner

SELECT *
FROM report.Current_Vs_Next_Pricelist_Comparison_Report(2156515)
;
 */

DROP FUNCTION IF EXISTS report.Current_Vs_Next_Pricelist_Comparison_Report_With_PP_PI(p_C_BPartner_ID numeric, p_C_BP_Group_ID numeric, p_IsSoTrx text, p_AD_Language text, p_show_product_price_pi_flag text)
;

CREATE OR REPLACE FUNCTION report.Current_Vs_Next_Pricelist_Comparison_Report_With_PP_PI(p_C_BPartner_ID              numeric = NULL,
                                                                                         p_C_BP_Group_ID              numeric = NULL,
                                                                                         p_IsSoTrx                    text = 'Y',
                                                                                         p_AD_Language                TEXT = 'en_US',
                                                                                         p_show_product_price_pi_flag text = 'Y')
    RETURNS TABLE
            (
                bp_value                   text,
                bp_name                    text,
                ProductCategory            text,
                M_Product_ID               integer,
                value                      text,
                CustomerProductNumber      text,
                ProductName                text,
                IsSeasonFixedPrice         text,
                ItemProductName            text,
                qtycuspertu                numeric,
                packingmaterialname        text,
                pricestd                   numeric,
                PricePattern1              text,
                altpricestd                numeric,
                PricePattern2              text,
                hasaltprice                integer,
                uomsymbol                  text,
                uom_x12de355               text,
                Attributes                 text,
                m_productprice_id          integer,
                m_attributesetinstance_id  integer,
                m_hu_pi_item_product_id    integer,
                currency                   text,
                currency2                  text,
                validFromPLV1              timestamp,
                validFromPLV2              timestamp,
                namePLV1                   text,
                namePLV2                   text,
                c_bpartner_location_id     numeric,
                AD_Org_ID                  numeric,
                show_product_price_pi_flag text
            )
AS
$$
WITH PriceListVersionsByValidFrom AS
         (
             SELECT t.*
             FROM ((SELECT --
                           plv.c_bpartner_id,
                           plv.m_pricelist_version_id,
                           plv.validfrom,
                           plv.name,
                           row_number() OVER (PARTITION BY plv.c_bpartner_id ORDER BY plv.validfrom DESC, plv.m_pricelist_version_id DESC) rank
                    FROM Report.Fresh_PriceList_Version_Val_Rule plv
                    WHERE TRUE
                      AND plv.validfrom <= now()
                      AND plv.issotrx = p_IsSoTrx
                      AND (p_C_BPartner_ID IS NULL OR plv.c_bpartner_id = p_C_BPartner_ID)
                      AND (p_C_BP_Group_ID IS NULL OR plv.c_bpartner_id IN (SELECT DISTINCT b.c_bpartner_id FROM c_bpartner b WHERE b.c_bp_group_id = p_C_BP_Group_ID))
                    order by
                             plv.validfrom DESC,
                             plv.m_pricelist_version_id DESC
                   )
                   UNION ALL
                   (SELECT --
                           plv.c_bpartner_id,
                           plv.m_pricelist_version_id,
                           plv.validfrom,
                           plv.name,
                           1001 - (row_number() OVER (PARTITION BY plv.c_bpartner_id ORDER BY plv.validfrom ASC, plv.m_pricelist_version_id ASC)) rank

                    FROM Report.Fresh_PriceList_Version_Val_Rule plv
                    WHERE TRUE
                      AND plv.validfrom > now()
                      AND plv.issotrx = p_IsSoTrx
                      AND (p_C_BPartner_ID IS NULL OR plv.c_bpartner_id = p_C_BPartner_ID)
                      AND (p_C_BP_Group_ID IS NULL OR plv.c_bpartner_id IN (SELECT DISTINCT b.c_bpartner_id FROM c_bpartner b WHERE b.c_bp_group_id = p_C_BP_Group_ID))
                    order by
                             plv.validfrom ASC,
                             plv.m_pricelist_version_id ASC
                   )
                  ) t

             WHERE t.rank = 1
                OR t.rank = 1000
         ),
     currentAndPreviousPLV AS
         (
             -- implementation detail: all these sub-selects would be better implemented with a pivot. Unfortunately i cant understand how pivots work.
             SELECT DISTINCT --
                             plvv.c_bpartner_id,
                             (SELECT plvv2.m_pricelist_version_id FROM PriceListVersionsByValidFrom plvv2 WHERE plvv2.rank = 1 AND plvv2.c_bpartner_id = plvv.c_bpartner_id)   PLV1_ID,
                             (SELECT plvv2.m_pricelist_version_id FROM PriceListVersionsByValidFrom plvv2 WHERE plvv2.rank = 1000 AND plvv2.c_bpartner_id = plvv.c_bpartner_id) PLV2_ID,
                             (SELECT plvv2.validfrom FROM PriceListVersionsByValidFrom plvv2 WHERE plvv2.rank = 1 AND plvv2.c_bpartner_id = plvv.c_bpartner_id)                validFromPLV1,
                             (SELECT plvv2.validfrom FROM PriceListVersionsByValidFrom plvv2 WHERE plvv2.rank = 1000 AND plvv2.c_bpartner_id = plvv.c_bpartner_id)              validFromPLV2,
                             (SELECT plvv2.name FROM PriceListVersionsByValidFrom plvv2 WHERE plvv2.rank = 1 AND plvv2.c_bpartner_id = plvv.c_bpartner_id)                     namePLV1,
                             (SELECT plvv2.name FROM PriceListVersionsByValidFrom plvv2 WHERE plvv2.rank = 1000 AND plvv2.c_bpartner_id = plvv.c_bpartner_id)                   namePLV2
             FROM PriceListVersionsByValidFrom plvv
             ORDER BY plvv.c_bpartner_id
         ),
     result AS
         (
             SELECT t.*,
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
                 ) AS t ON TRUE
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
order by
         r.bp_value,
         r.productCategory,
         r.value
$$
    LANGUAGE sql STABLE
;


/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2020 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


/*
How to run

- All Bpartners

SELECT *
FROM report.Current_Vs_Next_Pricelist_Comparison_Report_With_PP_PI()
;

- Specific Bpartner

SELECT *
FROM report.Current_Vs_Next_Pricelist_Comparison_Report_With_PP_PI(2156515)
;
 */
