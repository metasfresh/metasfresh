/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

DROP FUNCTION IF EXISTS report.reportPriceListComparation(
    p_C_BPartner_ID            numeric, -- 1
    p_M_PriceList_Version_ID   numeric, -- 2
    p_Alt_PriceList_Version_ID numeric -- 3
)
;

CREATE OR REPLACE FUNCTION report.reportPriceListComparation(
    p_C_BPartner_ID            numeric, -- 1
    p_M_PriceList_Version_ID   numeric, -- 2
    p_Alt_PriceList_Version_ID numeric -- 3
)
    RETURNS TABLE
            (
                -- Displayed pricelist data
                ProductCategory           varchar,
                ProductCategoryValue      varchar,
                M_Product_ID              numeric,
                Value                     varchar,
                ProductName               varchar,
                IsSeasonFixedPrice        char(1),
                ItemProductName           varchar,
                PackingMaterialName       varchar,
                PriceStd                  numeric,
                PricePattern1             varchar,
                AltPriceStd               numeric,
                PricePattern2             varchar,
                HasAltPrice               integer,
                UOMSymbol                 varchar,
                attributes                text,
                SeqNo                     numeric,

                -- Filter Columns
                C_BPartner_ID             numeric,
                M_PriceList_Version_ID    numeric,
                Alt_PriceList_Version_ID  numeric,

                -- Additional internal infos to be used
                M_ProductPrice_ID         numeric,
                M_AttributeSetInstance_ID numeric,
                M_HU_PI_Item_Product_ID   numeric,
                UOM_X12DE355              varchar,
                QtyCUsPerTU               numeric,
                m_hu_pi_version_id        numeric,
                currency                  char(3),
                currency2                 char(3)
            )
AS
$$
    /*
     IMPORTANT: keep in sync with report.reportPriceListComparation_With_PP_PI
    */
SELECT -- Displayed pricelist data
       pc.Name                                                                                                                     AS ProductCategory,
       pc.value                                                                                                                    AS ProductCategoryValue,
       pp.M_Product_ID                                                                                                             AS M_Product_ID,
       p.Value                                                                                                                     AS Value,
       p.Name                                                                                                                      AS ProductName,
       pp.IsSeasonFixedPrice                                                                                                       AS IsSeasonFixedPrice,
       hupip.Name                                                                                                                  AS ItemProductName,
       pm.Name                                                                                                                     AS PackingMaterialName,
       COALESCE(ppa.PriceStd, pp.PriceStd)                                                                                         AS PriceStd,
       (CASE WHEN pl.priceprecision = 0 THEN '#,##0' ELSE SUBSTRING('#,##0.000' FROM 0 FOR 7 + pl.priceprecision :: integer) END)  AS PricePattern1,
       pp2.PriceStd                                                                                                                AS AltPriceStd,
       (CASE WHEN pl.priceprecision = 0 THEN '#,##0' ELSE SUBSTRING('#,##0.000' FROM 0 FOR 7 + pl2.priceprecision :: integer) END) AS PricePattern2,
       CASE WHEN pp2.PriceStd IS NULL THEN 0 ELSE 1 END                                                                            AS HasAltPrice,
       uom.UOMSymbol                                                                                                               AS UOMSymbol,
       COALESCE(ppa.Attributes, '')                                                                                                AS attributes,
       pp.seqNo                                                                                                                    AS SeqNo,

       -- Filter Columns
       p_C_BPartner_ID                                                                                                                          AS C_BPartner_ID,
       plv.M_Pricelist_Version_ID                                                                                                  AS M_Pricelist_Version_ID,
       plv2.M_Pricelist_Version_ID                                                                                                 AS Alt_PriceList_Version_ID,

       -- Additional internal infos to be used
       pp.M_ProductPrice_ID                                                                                                        AS M_ProductPrice_ID,
       ppa.m_attributesetinstance_ID                                                                                               AS m_attributesetinstance_ID,
       bpProductPackingMaterial.M_HU_PI_Item_Product_ID                                                                            AS M_HU_PI_Item_Product_ID,
       uom.X12DE355                                                                                                                AS UOM_X12DE355,
       hupip.Qty                                                                                                                   AS QtyCUsPerTU,
       it.m_hu_pi_version_id                                                                                                       AS m_hu_pi_version_id,
       c.iso_code                                                                                                                  AS currency,
       c2.iso_code                                                                                                                 AS currency2

FROM M_ProductPrice pp
         INNER JOIN M_Product p ON pp.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
         INNER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
         INNER JOIN C_UOM uom ON pp.C_UOM_ID = uom.C_UOM_ID AND uom.isActive = 'Y'


    /*
      * We know if there are packing instructions limited to the BPartner/product-combination. If so,
      * we will use only those. If not, we will use only the non limited ones
      */
         LEFT OUTER JOIN LATERAL
    (
    SELECT vip.M_HU_PI_Item_Product_ID, vip.M_Product_ID
    FROM report.Valid_PI_Item_Product_V vip
    -- WHERE isInfiniteCapacity = 'N' -- task 09045/09788: we can also export PiiPs with infinite capacity
    WHERE vip.M_Product_ID = pp.M_Product_ID
      AND (vip.c_bpartner_id IS NULL OR p_C_BPartner_ID IS NULL OR vip.c_bpartner_id = p_C_BPartner_ID)
    ) bpProductPackingMaterial ON
    (bpProductPackingMaterial.M_HU_PI_Item_Product_ID = pp.m_hu_pi_item_product_id OR
        -- if product price has no PIIPs then use it for all PIIPs with no valid product prices for the current PLV
     (pp.m_hu_pi_item_product_id IS NULL AND NOT EXISTS (SELECT 1
                                                         FROM m_productprice ppAlt
                                                         WHERE ppAlt.m_productprice_id != pp.m_productprice_id
                                                           AND ppAlt.m_pricelist_version_id = pp.m_pricelist_version_id
                                                           AND ppAlt.m_hu_pi_item_product_id = bpProductPackingMaterial.m_hu_pi_item_product_id)))

         LEFT OUTER JOIN LATERAL report.getProductPriceAndAttributes(M_ProductPrice_ID := pp.M_ProductPrice_ID) ppa ON TRUE

         INNER JOIN M_PriceList_Version plv ON plv.M_PriceList_Version_ID = pp.M_PriceList_Version_ID AND plv.IsActive = 'Y'

    --
    -- Get Comparison Prices
    --

    -- Get all PriceList_Versions of the PriceList (we need all available PriceList_Version_IDs for outside filtering)
    -- limited to the same PriceList because the Parameter validation rule is enforcing this
         LEFT JOIN M_PriceList_Version plv2 ON plv2.M_PriceList_ID = plv.M_PriceList_ID AND plv2.IsActive = 'Y'
         LEFT OUTER JOIN LATERAL (
    SELECT COALESCE(ppa2.PriceStd, pp2.PriceStd) AS PriceStd, ppa2.signature, pp2.m_hu_pi_item_product_id
    FROM M_ProductPrice pp2
             INNER JOIN report.getProductPriceAndAttributes(M_ProductPrice_ID := pp2.M_ProductPrice_ID) ppa2 ON TRUE

    WHERE pp2.M_Product_ID = pp.M_Product_ID
      AND (pp.m_hu_pi_item_product_id = pp2.m_hu_pi_item_product_id OR (pp.m_hu_pi_item_product_id IS NULL AND pp2.m_hu_pi_item_product_id IS NULL))
      AND pp2.M_Pricelist_Version_ID = plv2.M_Pricelist_Version_ID
      AND pp2.IsActive = 'Y'
      AND pp2.isAttributeDependant = pp.isAttributeDependant
      --avoid comparing different product prices in same pricelist
      AND (CASE WHEN pp2.M_PriceList_Version_ID = pp.M_PriceList_Version_ID THEN pp2.M_ProductPrice_ID = pp.M_ProductPrice_ID ELSE TRUE END)
        /* we have to make sure that only prices with the same attributes are compared. Note:
        * - If there is an Existing Attribute Price but no signature related columns are filled the signature will be ''
        * - If there are no Attribute Prices the signature will be null
        * This is important, because otherwise an empty attribute price will be compared to the regular price AND the alternate attribute price */
      AND (ppa.signature = ppa2.signature)
    ) pp2 ON TRUE

         INNER JOIN M_Pricelist pl ON plv.M_Pricelist_ID = pl.M_PriceList_ID AND pl.isActive = 'Y'
         LEFT JOIN M_Pricelist pl2 ON plv2.M_PriceList_ID = pl2.M_Pricelist_ID AND pl2.isActive = 'Y'
         INNER JOIN C_Currency c ON pl.C_Currency_ID = c.C_Currency_ID AND c.isActive = 'Y'
         LEFT JOIN C_Currency c2 ON pl2.C_Currency_ID = c2.C_CUrrency_ID AND c2.isActive = 'Y'

         LEFT OUTER JOIN m_hu_pi_item_product hupip ON (CASE
                                                            WHEN pp2.m_hu_pi_item_product_id IS NULL
                                                                THEN hupip.m_hu_pi_item_product_id = bpProductPackingMaterial.m_hu_pi_item_product_ID AND hupip.isActive = 'Y'
                                                                ELSE hupip.m_hu_pi_item_product_id = pp2.m_hu_pi_item_product_id AND pp.m_hu_pi_item_product_id = hupip.m_hu_pi_item_product_id AND hupip.isActive = 'Y'
                                                        END)
         LEFT OUTER JOIN m_hu_pi_item it ON it.M_HU_PI_Item_ID = hupip.M_HU_PI_Item_ID AND it.isActive = 'Y'
         LEFT OUTER JOIN m_hu_pi_item pmit ON pmit.m_hu_pi_version_id = it.m_hu_pi_version_id AND pmit.itemtype::TEXT = 'PM'::TEXT AND pmit.isActive = 'Y'
         LEFT OUTER JOIN m_hu_packingmaterial pm ON pm.m_hu_packingmaterial_id = pmit.m_hu_packingmaterial_id AND pm.isActive = 'Y'

WHERE plv.M_Pricelist_Version_ID = p_M_PriceList_Version_ID
  AND plv2.M_Pricelist_Version_ID = COALESCE(p_Alt_PriceList_Version_ID, plv.m_pricelist_version_id)
  AND CASE WHEN p_Alt_PriceList_Version_ID IS NOT NULL THEN COALESCE(ppa.PriceStd, pp.PriceStd) != 0 ELSE COALESCE(ppa.PriceStd, pp.PriceStd) != 0 OR pp2.PriceStd != 0 END

  AND pp.isActive = 'Y'
  AND (pp.M_Attributesetinstance_ID = ppa.M_Attributesetinstance_ID OR pp.M_Attributesetinstance_ID IS NULL)
  AND (pp.M_HU_PI_Item_Product_ID = bpProductPackingMaterial.M_HU_PI_Item_Product_ID OR pp.M_HU_PI_Item_Product_ID IS NULL)
  AND (CASE WHEN plv2.M_PriceList_Version_ID = plv.M_PriceList_Version_ID THEN ppa.signature = pp2.signature ELSE TRUE END)
$$
    LANGUAGE sql
    STABLE
;

COMMENT ON FUNCTION report.reportPriceListComparation(numeric,numeric,numeric) IS 'Compares prices between a new and an old pricelist version. In addition to m_hu_pi_item_products for which a product price is defined, it will also show m_hu_pi_item_products for which a product price is not defined. In this case, the no PIIP price is used (if one is available).'
;