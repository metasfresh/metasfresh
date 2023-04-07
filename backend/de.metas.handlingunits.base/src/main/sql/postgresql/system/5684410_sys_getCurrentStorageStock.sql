DROP FUNCTION IF EXISTS getCurrentStorageStock(p_M_Product_ID   numeric,
                                               p_M_Attribute_ID numeric,
                                               p_AttrivuteValue character varying,
                                               p_AD_Client_ID   numeric,
                                               p_AD_Org_ID      numeric)
;

CREATE OR REPLACE FUNCTION getCurrentStorageStock(p_M_Product_ID   numeric,
                                                  p_M_Attribute_ID numeric,
                                                  p_AttrivuteValue character varying,
                                                  p_AD_Client_ID   numeric,
                                                  p_AD_Org_ID      numeric)
    RETURNS numeric
AS

$$

SELECT SUM(s.qty)

FROM m_hu_storage s

WHERE p_M_Product_ID = s.M_Product_ID
  AND s.IsActive = 'Y'
  AND s.AD_Client_ID = p_AD_Client_ID
  AND s.AD_Org_ID = p_AD_Org_ID
  AND ((p_AttrivuteValue IS NULL) OR (EXISTS (SELECT 1
                                              FROM m_hu_attribute hua
                                                       JOIN M_HU hu ON hua.m_hu_id = hu.m_hu_id
                                              WHERE s.m_hu_id = hua.m_hu_id
                                                AND hua.m_attribute_id = p_M_Attribute_ID
                                                AND hua.value = p_AttrivuteValue
                                                AND hu.hustatus IN ('A',
                                                                    'S',
                                                                    'I')
                                                AND hu.m_hu_item_parent_id IS NULL)))

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
               p_M_Attribute_ID := 1000017, -- Lot number
               p_AttrivuteValue := ''55555555'',
               p_AD_Client_ID := 1000000,
               p_AD_Org_ID := 1000000)
    FROM M_Product
;'
;

