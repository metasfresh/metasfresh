DROP FUNCTION IF EXISTS getCurrentStorageStock(p_M_Product_ID   numeric,
                                               p_C_UOM_ID       numeric,
                                               p_M_Attribute_ID numeric,
                                               p_AttrivuteValue character varying,
                                               p_AD_Client_ID   numeric,
                                               p_AD_Org_ID      numeric)
;

CREATE OR REPLACE FUNCTION getCurrentStorageStock(p_M_Product_ID   numeric,
                                                  p_C_UOM_ID       numeric,
                                                  p_M_Attribute_ID numeric,
                                                  p_AttrivuteValue character varying,
                                                  p_AD_Client_ID   numeric,
                                                  p_AD_Org_ID      numeric)
    RETURNS numeric
AS

$$

SELECT (COALESCE(SUM(s.qty), 0))

FROM m_hu_storage s
         JOIN M_HU hu ON s.m_hu_id = hu.m_hu_id

WHERE p_M_Product_ID = s.M_Product_ID
  AND s.c_uom_id = p_C_UOM_ID
  AND s.IsActive = 'Y'
  AND s.AD_Client_ID = p_AD_Client_ID
  AND s.AD_Org_ID = p_AD_Org_ID
  AND hu.hustatus IN ('A',
                      'S',
                      'I')
  AND hu.m_hu_item_parent_id IS NULL
  AND ((p_AttrivuteValue IS NULL AND
        (EXISTS (SELECT 1
                 FROM m_hu_attribute hua
                 WHERE s.m_hu_id = hua.m_hu_id
                   AND hua.m_attribute_id = p_M_Attribute_ID
                   AND hua.value IS NULL))
           )
    OR (EXISTS (SELECT 1
                FROM m_hu_attribute hua
                WHERE s.m_hu_id = hua.m_hu_id
                  AND hua.m_attribute_id = p_M_Attribute_ID
                  AND hua.value = p_AttrivuteValue)))

$$
    LANGUAGE SQL STABLE
;



COMMENT ON FUNCTION getCurrentStorageStock(p_M_Product_ID numeric,
    p_M_Attribute_ID numeric,
    p_AttrivuteValue character varying,
    p_AD_Client_ID numeric,
    p_AD_Org_ID numeric) IS
    ' -- TEST
    SELECT M_Product_ID,
       getCurrentStorageStock
           (
               p_M_Product_ID := M_Product_ID,
               p_C_UOM_ID := 540017 -- kg
               p_M_Attribute_ID := 1000017, -- Lot number
               p_AttrivuteValue := ''55555555'',
               p_AD_Client_ID := 1000000,
               p_AD_Org_ID := 1000000)
    FROM M_Product
;'
;


