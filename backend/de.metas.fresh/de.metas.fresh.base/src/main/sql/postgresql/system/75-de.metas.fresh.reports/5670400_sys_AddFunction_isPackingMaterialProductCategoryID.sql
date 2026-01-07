DROP FUNCTION IF EXISTS report.isPackingMaterialProductCategoryID(IN p_M_Product_Category_ID NUMERIC,
                                                                  IN p_AD_Client_ID          NUMERIC,
                                                                  IN p_AD_Org_ID             NUMERIC)
;

CREATE OR REPLACE FUNCTION report.isPackingMaterialProductCategoryID(IN p_M_Product_Category_ID numeric,
                                                                     IN p_AD_Client_ID          numeric,
                                                                     IN p_AD_Org_ID             numeric) RETURNS CHAR
AS
$$
DECLARE
    isPackingMaterialProductCategoryID CHAR DEFAULT 'N';
    v_PackingMaterialProductCategoryID numeric;
BEGIN

    SELECT getSysConfigAsNumeric('PackingMaterialProductCategoryID', p_AD_Client_ID, p_AD_Org_ID) INTO v_PackingMaterialProductCategoryID;

    SELECT CASE
               WHEN p_M_Product_Category_ID IN (SELECT m_product_category_id FROM m_product_category WHERE m_product_category_parent_id = v_PackingMaterialProductCategoryID OR m_product_category_id = v_PackingMaterialProductCategoryID)
                   THEN 'Y'
                   ELSE 'N'
           END
    INTO isPackingMaterialProductCategoryID;

    RETURN isPackingMaterialProductCategoryID;
END
$$
    LANGUAGE plpgsql
;