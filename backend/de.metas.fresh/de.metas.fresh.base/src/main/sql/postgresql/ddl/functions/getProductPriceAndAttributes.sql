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

DROP FUNCTION IF EXISTS report.getProductPriceAndAttributes(M_ProductPrice_ID numeric)
;

CREATE OR REPLACE FUNCTION report.getProductPriceAndAttributes(M_ProductPrice_ID numeric)
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

       (
           COALESCE(pp.isDefault || ' ', '') ||
           COALESCE(STRING_AGG(ai.M_Attribute_ID::text || ' ' || ai.M_Attributevalue_ID::text, ',' ORDER BY ai.M_Attribute_ID), '')
           )                                                                AS signature,
       pp.M_PriceList_Version_ID
FROM M_ProductPrice pp

         LEFT OUTER JOIN M_AttributeInstance ai ON pp.m_attributesetinstance_id = ai.m_attributesetinstance_id AND ai.isActive = 'Y'
         LEFT OUTER JOIN (SELECT av.isActive,
                                 av.M_Attributevalue_ID,
                                 CASE
                                     WHEN a.Value = '1000015' AND av.value = '01' THEN NULL -- ADR & Keine/Leer
                                     WHEN a.Value = '1000015'                     THEN 'AdR' -- ADR
                                     WHEN a.Value = '1000001'                     THEN av.value -- Herkunft
                                                                                  ELSE av.name
                                 END AS value
                          FROM M_Attributevalue av
                                   LEFT OUTER JOIN M_Attribute a ON av.M_Attribute_ID = a.M_Attribute_ID) av ON ai.M_Attributevalue_ID = av.M_Attributevalue_ID
WHERE pp.m_productprice_id = $1
  AND pp.IsActive = 'Y'
GROUP BY pp.m_productprice_id, ai.m_attributesetinstance_id, pp.m_attributesetinstance_id, pp.pricestd, pp.isactive, pp.m_hu_pi_item_product_id
$$
    LANGUAGE sql
    STABLE
;

