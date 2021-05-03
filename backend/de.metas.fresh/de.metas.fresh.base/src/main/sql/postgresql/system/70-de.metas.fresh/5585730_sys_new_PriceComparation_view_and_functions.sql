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

DROP FUNCTION IF EXISTS report.getProductPriceAndAttributes(
    M_ProductPrice_ID       numeric,
    M_HU_PI_Item_Product_ID numeric)
;

CREATE OR REPLACE FUNCTION report.getProductPriceAndAttributes(
    M_ProductPrice_ID       numeric, -- 1
    M_HU_PI_Item_Product_ID numeric -- 2
)
    RETURNS TABLE
            (
                M_ProductPrice_ID         numeric,
                M_AttributeSetInstance_ID numeric,
                PriceStd                  numeric,
                IsActive                  char(1),
                M_HU_PI_Item_Product_ID   numeric,
                attributes                text,
                signature                 text,
                M_PriceList_Version_ID    numeric
            )
AS
$$
SELECT pp.M_ProductPrice_ID,
       COALESCE(ai.M_AttributeSetInstance_ID, pp.M_AttributeSetInstance_ID) AS m_attributesetinstance_id,
       pp.PriceStd,
       pp.IsActive,
       pp.M_HU_PI_Item_Product_ID,

       STRING_AGG(av.value, ', ' ORDER BY av.value)                         AS Attributes,

    /** Create a column, that allows us to identify Attribute prices with the same AttributeSet
     *  That column will be filled with an empty string if all relevant data is null. this is important to prevent
     *  attribute prices to be compared with regular prices */
       (
                   COALESCE(pp.M_HU_PI_Item_Product_ID || ' ', '') ||
                   COALESCE(pp.isDefault || ' ', '') ||
                   COALESCE(STRING_AGG(ai.M_Attribute_ID::text || ' ' || ai.M_Attributevalue_ID::text, ',' ORDER BY ai.M_Attribute_ID), '')
           )                                                                AS signature,
       pp.M_PriceList_Version_ID
FROM M_ProductPrice pp
         --LEFT OUTER JOIN M_ProductPrice_Attribute_Line ppal ON ppa.M_ProductPrice_Attribute_ID = ppal.M_ProductPrice_Attribute_ID AND ppal.isActive = 'Y'
         LEFT OUTER JOIN M_AttributeInstance ai ON pp.m_attributesetinstance_id = ai.m_attributesetinstance_id AND ai.isActive = 'Y'
         LEFT OUTER JOIN (
    SELECT av.isActive,
           av.M_Attributevalue_ID,
           CASE
               WHEN a.Value = '1000015' AND av.value = '01'      THEN NULL -- ADR & Keine/Leer
               WHEN a.Value = '1000015' AND av.value IS NOT NULL THEN 'AdR' -- ADR
               WHEN a.Value = '1000001'                          THEN av.value -- Herkunft
                                                                 ELSE av.name
           END AS value
    FROM M_Attributevalue av
             LEFT OUTER JOIN M_Attribute a ON av.M_Attribute_ID = a.M_Attribute_ID
) av ON ai.M_Attributevalue_ID = av.M_Attributevalue_ID AND av.IsActive = 'Y' AND av.value IS NOT NULL
WHERE pp.m_productprice_id = $1
  AND (pp.m_hu_pi_item_product_id = $2 OR pp.m_hu_pi_item_product_id IS NULL)
  AND pp.IsActive = 'Y'
GROUP BY pp.m_productprice_id, ai.m_attributesetinstance_id, pp.m_attributesetinstance_id, pp.pricestd, pp.isactive, pp.m_hu_pi_item_product_id
$$
    LANGUAGE sql
    STABLE
;















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
    C_BPartner_ID            numeric, -- 1
    M_PriceList_Version_ID   numeric, -- 2
    Alt_PriceList_Version_ID numeric -- 3
)
;

CREATE OR REPLACE FUNCTION report.reportPriceListComparation(
    C_BPartner_ID            numeric, -- 1
    M_PriceList_Version_ID   numeric, -- 2
    Alt_PriceList_Version_ID numeric -- 3
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
       $1                                                                                                                          AS C_BPartner_ID,
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
      AND CASE
              WHEN
                  EXISTS(SELECT 1 FROM report.Valid_PI_Item_Product_V v WHERE v.M_Product_ID = pp.M_Product_ID AND v.hasPartner IS TRUE AND v.C_BPartner_ID = $1)
                  THEN vip.C_BPartner_ID = $1
                  ELSE vip.C_BPartner_ID IS NULL
          END
    ) bpProductPackingMaterial ON TRUE

         LEFT OUTER JOIN LATERAL report.getProductPriceAndAttributes(M_ProductPrice_ID := pp.M_ProductPrice_ID, M_HU_PI_Item_Product_ID := bpProductPackingMaterial.m_hu_pi_item_product_id) ppa ON TRUE

         LEFT OUTER JOIN m_hu_pi_item_product hupip ON (CASE
                                                            WHEN pp.m_hu_pi_item_product_id IS NULL
                                                                THEN hupip.m_hu_pi_item_product_id = bpProductPackingMaterial.m_hu_pi_item_product_ID AND hupip.isActive = 'Y'
                                                                ELSE hupip.m_product_id = bpProductPackingMaterial.m_product_id AND hupip.isActive = 'Y'
                                                        END)
         LEFT OUTER JOIN m_hu_pi_item it ON it.M_HU_PI_Item_ID = hupip.M_HU_PI_Item_ID AND it.isActive = 'Y'
         LEFT OUTER JOIN m_hu_pi_item pmit ON pmit.m_hu_pi_version_id = it.m_hu_pi_version_id AND pmit.itemtype::TEXT = 'PM'::TEXT AND pmit.isActive = 'Y'
         LEFT OUTER JOIN m_hu_packingmaterial pm ON pm.m_hu_packingmaterial_id = pmit.m_hu_packingmaterial_id AND pm.isActive = 'Y'

         INNER JOIN M_PriceList_Version plv ON plv.M_PriceList_Version_ID = pp.M_PriceList_Version_ID AND plv.IsActive = 'Y'

    --
    -- Get Comparison Prices
    --

    -- Get all PriceList_Versions of the PriceList (we need all available PriceList_Version_IDs for outside filtering)
    -- limited to the same PriceList because the Parameter validation rule is enforcing this
         LEFT JOIN M_PriceList_Version plv2 ON plv2.M_PriceList_ID = plv.M_PriceList_ID AND plv2.IsActive = 'Y'
         LEFT OUTER JOIN LATERAL (
    SELECT COALESCE(ppa2.PriceStd, pp2.PriceStd) AS PriceStd, ppa2.signature
    FROM M_ProductPrice pp2
             INNER JOIN report.getProductPriceAndAttributes(M_ProductPrice_ID := pp2.M_ProductPrice_ID, M_HU_PI_Item_Product_ID := bpProductPackingMaterial.m_hu_pi_item_product_id) ppa2 ON TRUE

    WHERE pp2.M_Product_ID = pp.M_Product_ID
      AND pp2.M_Pricelist_Version_ID = plv2.M_Pricelist_Version_ID
      AND pp2.IsActive = 'Y'
      AND (pp2.m_hu_pi_item_product_ID = pp.m_hu_pi_item_product_ID OR
           (pp2.m_hu_pi_item_product_ID IS NULL AND pp.m_hu_pi_item_product_ID IS NULL))
      AND pp2.isAttributeDependant = pp.isAttributeDependant
      --avoid comparing different product prices in same pricelist
      AND (CASE WHEN pp2.M_PriceList_Version_ID = pp.M_PriceList_Version_ID THEN pp2.M_ProductPrice_ID = pp.M_ProductPrice_ID ELSE TRUE END)
        /* we have to make sure that only prices with the same attributes and packing instructions are compared. Note:
        * - If there is an Existing Attribute Price but no signature related columns are filled the signature will be ''
        * - If there are no Attribute Prices the signature will be null
        * This is important, because otherwise an empty attribute price will be compared to the regular price AND the alternate attribute price */
      AND (ppa.signature = ppa2.signature)
    ) pp2 ON TRUE

         INNER JOIN M_Pricelist pl ON plv.M_Pricelist_ID = pl.M_PriceList_ID AND pl.isActive = 'Y'
         LEFT JOIN M_Pricelist pl2 ON plv2.M_PriceList_ID = pl2.M_Pricelist_ID AND pl2.isActive = 'Y'
         INNER JOIN C_Currency c ON pl.C_Currency_ID = c.C_Currency_ID AND c.isActive = 'Y'
         LEFT JOIN C_Currency c2 ON pl2.C_Currency_ID = c2.C_CUrrency_ID AND c2.isActive = 'Y'

WHERE plv.M_Pricelist_Version_ID = $2
  AND plv2.M_Pricelist_Version_ID = COALESCE($3, plv.m_pricelist_version_id)
  AND CASE WHEN $3 IS NOT NULL THEN COALESCE(ppa.PriceStd, pp.PriceStd) != 0 ELSE COALESCE(ppa.PriceStd, pp.PriceStd) != 0 OR pp2.PriceStd != 0 END

  AND pp.isActive = 'Y'
  AND (pp.M_Attributesetinstance_ID = ppa.M_Attributesetinstance_ID OR pp.M_Attributesetinstance_ID IS NULL)
  AND (pp.M_HU_PI_Item_Product_ID = bpProductPackingMaterial.M_HU_PI_Item_Product_ID OR pp.M_HU_PI_Item_Product_ID IS NULL)
  AND (CASE WHEN plv2.M_PriceList_Version_ID = plv.M_PriceList_Version_ID THEN ppa.signature = pp2.signature ELSE TRUE END)
$$
    LANGUAGE sql
    STABLE
;












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

DROP FUNCTION IF EXISTS report.reportPriceListComparation_With_PP_PI(
    C_BPartner_ID            numeric, -- 1
    M_PriceList_Version_ID   numeric, -- 2
    Alt_PriceList_Version_ID numeric -- 3
)
;

CREATE OR REPLACE FUNCTION report.reportPriceListComparation_With_PP_PI(
    C_BPartner_ID            numeric, -- 1
    M_PriceList_Version_ID   numeric, -- 2
    Alt_PriceList_Version_ID numeric -- 3
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
     IMPORTANT: keep in sync with report.reportPriceListComparation
    */
SELECT
    -- Displayed pricelist data
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
    $1                                                                                                                          AS C_BPartner_ID,
    plv.M_Pricelist_Version_ID                                                                                                  AS M_Pricelist_Version_ID,
    plv2.M_Pricelist_Version_ID                                                                                                 AS Alt_PriceList_Version_ID,

    -- Additional internal infos to be used
    pp.M_ProductPrice_ID                                                                                                        AS M_ProductPrice_ID,
    ppa.m_attributesetinstance_ID                                                                                               AS m_attributesetinstance_ID,
    pp.M_HU_PI_Item_Product_ID                                                                                                  AS M_HU_PI_Item_Product_ID,
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
    SELECT vip.M_HU_PI_Item_Product_ID
    FROM report.Valid_PI_Item_Product_V vip
         -- WHERE isInfiniteCapacity = 'N' -- task 09045/09788: we can also export PiiPs with infinite capacity
    WHERE vip.M_Product_ID = pp.M_Product_ID

      AND CASE
              WHEN
                  EXISTS(SELECT 1 FROM report.Valid_PI_Item_Product_V v WHERE v.M_Product_ID = pp.M_Product_ID AND v.hasPartner IS TRUE AND v.C_BPartner_ID = $1)
                  THEN vip.C_BPartner_ID = $1
                  ELSE vip.C_BPartner_ID IS NULL
          END
    ) bpProductPackingMaterial ON TRUE


         LEFT OUTER JOIN LATERAL report.getProductPriceAndAttributes(M_ProductPrice_ID := pp.M_ProductPrice_ID, M_HU_PI_Item_Product_ID := bpProductPackingMaterial.m_hu_pi_item_product_id) ppa ON TRUE

         LEFT OUTER JOIN m_hu_pi_item_product hupip ON hupip.m_hu_pi_item_product_id = pp.m_hu_pi_item_product_ID AND hupip.isActive = 'Y'
         LEFT OUTER JOIN m_hu_pi_item it ON it.M_HU_PI_Item_ID = hupip.M_HU_PI_Item_ID AND it.isActive = 'Y'
         LEFT OUTER JOIN m_hu_pi_item pmit ON pmit.m_hu_pi_version_id = it.m_hu_pi_version_id AND pmit.itemtype::TEXT = 'PM'::TEXT AND pmit.isActive = 'Y'
         LEFT OUTER JOIN m_hu_packingmaterial pm ON pm.m_hu_packingmaterial_id = pmit.m_hu_packingmaterial_id AND pm.isActive = 'Y'

         INNER JOIN M_PriceList_Version plv ON plv.M_PriceList_Version_ID = pp.M_PriceList_Version_ID AND plv.IsActive = 'Y'


    --
    -- Get Comparison Prices
    --

    -- Get all PriceList_Versions of the PriceList (we need all available PriceList_Version_IDs for outside filtering)
    -- limited to the same PriceList because the Parameter validation rule is enforcing this
         LEFT JOIN M_PriceList_Version plv2 ON plv2.M_PriceList_ID = plv.M_PriceList_ID AND plv2.IsActive = 'Y'

         LEFT OUTER JOIN LATERAL (
    SELECT COALESCE(ppa2.PriceStd, pp2.PriceStd) AS PriceStd, ppa2.signature
    FROM M_ProductPrice pp2
             INNER JOIN report.getProductPriceAndAttributes(M_ProductPrice_ID := pp2.M_ProductPrice_ID, M_HU_PI_Item_Product_ID := bpProductPackingMaterial.m_hu_pi_item_product_id) ppa2 ON TRUE

    WHERE pp2.M_Product_ID = pp.M_Product_ID
      AND pp2.M_Pricelist_Version_ID = plv2.M_Pricelist_Version_ID
      AND pp2.IsActive = 'Y'
      AND (pp2.m_hu_pi_item_product_ID = pp.m_hu_pi_item_product_ID OR (pp2.m_hu_pi_item_product_ID IS NULL AND pp.m_hu_pi_item_product_ID IS NULL))
      AND pp2.isAttributeDependant = pp.isAttributeDependant
      --avoid comparing different product prices in same pricelist
      AND (CASE WHEN pp2.M_PriceList_Version_ID = pp.M_PriceList_Version_ID THEN pp2.M_ProductPrice_ID = pp.M_ProductPrice_ID ELSE TRUE END)
        /* we have to make sure that only prices with the same attributes and packing instructions are compared. Note:
        * - If there is an Existing Attribute Price but no signature related columns are filled the signature will be ''
        * - If there are no Attribute Prices the signature will be null
        * This is important, because otherwise an empty attribute price will be compared to the regular price AND the alternate attribute price */
      AND (ppa.signature = ppa2.signature)
    ) pp2 ON TRUE

         INNER JOIN M_Pricelist pl ON plv.M_Pricelist_ID = pl.M_PriceList_ID AND pl.isActive = 'Y'
         LEFT JOIN M_Pricelist pl2 ON plv2.M_PriceList_ID = pl2.M_Pricelist_ID AND pl2.isActive = 'Y'
         INNER JOIN C_Currency c ON pl.C_Currency_ID = c.C_Currency_ID AND c.isActive = 'Y'
         LEFT JOIN C_Currency c2 ON pl2.C_Currency_ID = c2.C_CUrrency_ID AND c2.isActive = 'Y'

WHERE plv.M_Pricelist_Version_ID = $2
  AND plv2.M_Pricelist_Version_ID = COALESCE($3, plv.m_pricelist_version_id)
  AND CASE WHEN $3 IS NOT NULL THEN COALESCE(ppa.PriceStd, pp.PriceStd) != 0 ELSE COALESCE(ppa.PriceStd, pp.PriceStd) != 0 OR pp2.PriceStd != 0 END

  AND pp.isActive = 'Y'

  AND (pp.M_Attributesetinstance_ID = ppa.M_Attributesetinstance_ID OR pp.M_Attributesetinstance_ID IS NULL)
  AND (pp.M_HU_PI_Item_Product_ID = bpProductPackingMaterial.M_HU_PI_Item_Product_ID OR pp.M_HU_PI_Item_Product_ID IS NULL)

  AND (CASE WHEN plv2.M_PriceList_Version_ID = plv.M_PriceList_Version_ID THEN ppa.signature = pp2.signature ELSE TRUE END)

GROUP BY pp.M_ProductPrice_ID,
         pp.AD_Client_ID,
         pp.Created,
         pp.CreatedBy,
         pp.Updated,
         pp.UpdatedBy,
         pp.IsActive,
         pc.Name,
         pc.value,
         p.M_Product_ID,
         p.Value,
         p.Name,
         pp.IsSeasonFixedPrice,
         hupip.Name,
         pm.Name,
         COALESCE(ppa.PriceStd, pp.PriceStd),
         pl.priceprecision,
         pp2.PriceStd,
         pl2.priceprecision,
         CASE WHEN pp2.PriceStd IS NULL THEN 0 ELSE 1 END,
         uom.UOMSymbol,
         COALESCE(ppa.Attributes, ''),
         pp.seqNo,
         plv.M_Pricelist_Version_ID,
         plv2.M_Pricelist_Version_ID,
         pp.AD_Org_ID,
         ppa.m_attributesetinstance_ID,
         pp.M_HU_PI_Item_Product_ID,
         uom.X12DE355,
         hupip.Qty,
         it.m_hu_pi_version_id,
         c.iso_code,
         c2.iso_code
$$
    LANGUAGE sql
    STABLE
;



























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
             C_BPartner_ID := p_c_bpartner_id,
             M_PriceList_Version_ID := p_m_pricelist_version_id,
             Alt_PriceList_Version_ID := p_alt_pricelist_version_id
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







































DROP FUNCTION IF EXISTS report.fresh_PriceList_Details_Report_With_PP_PI(numeric,
                                                                         numeric,
                                                                         numeric,
                                                                         character varying,
                                                                         text)
;

CREATE OR REPLACE FUNCTION report.fresh_pricelist_details_report_With_PP_PI(IN p_c_bpartner_id              numeric,
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
      IMPORTANT: keep in sync with report.fresh_PriceList_Details_Report
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

FROM report.reportPriceListComparation_With_PP_PI(
             C_BPartner_ID := p_c_bpartner_id,
             M_PriceList_Version_ID := p_m_pricelist_version_id,
             Alt_PriceList_Version_ID := p_alt_pricelist_version_id
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

