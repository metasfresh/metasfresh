DROP FUNCTION IF EXISTS getLastCostPrice(numeric)
;

/*
 * Function: getLastCostPrice
 * Description: Calculates the cost price for a product by retrieving the most recent purchase price
 *              from completed purchase orders. If no purchase history exists, falls back to the
 *              product's Seed Cost value.
 * Parameters:
 *   p_Product_ID - The ID of the product to calculate cost price for
 * Returns: The calculated cost price as a numeric value, or NULL if the product doesn't exist
 */
CREATE OR REPLACE FUNCTION getLastCostPrice(IN p_Product_ID numeric)
    RETURNS numeric
    LANGUAGE sql
AS
$$
SELECT COALESCE(
               (SELECT ol.priceactual
                FROM C_OrderLine ol
                         INNER JOIN C_Order o ON ol.C_Order_ID = o.C_Order_ID
                WHERE o.issotrx = 'N'
                  AND o.docstatus = 'CO'
                  AND ol.m_product_id = p_Product_ID
                  AND ol.priceactual > 0
                ORDER BY o.DatePromised DESC, ol.C_OrderLine_ID DESC
                LIMIT 1),
               (SELECT p.SeedCost FROM M_Product p WHERE p.M_Product_ID = p_Product_ID)
       ) AS LastCostPrice
$$
;
