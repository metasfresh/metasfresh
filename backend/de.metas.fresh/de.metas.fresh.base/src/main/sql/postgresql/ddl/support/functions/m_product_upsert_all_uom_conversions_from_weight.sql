-- Function: support.m_product_upsert_all_uom_conversions_from_weight()

-- DROP FUNCTION IF EXISTS support.m_product_upsert_all_uom_conversions_from_weight();

CREATE OR REPLACE FUNCTION support.m_product_upsert_all_uom_conversions_from_weight()
RETURNS TABLE(m_product_id numeric, product_value varchar, status text) AS $$
BEGIN
    RETURN QUERY
    SELECT
        p.M_Product_ID,
        p.Value::varchar,
        support.m_product_upsert_uom_conversion_from_weight(p.M_Product_ID)
    FROM M_Product p
    JOIN C_UOM u ON u.C_UOM_ID = p.C_UOM_ID AND u.X12DE355 = 'PCE'
    WHERE p.IsActive = 'Y'
      AND p.Weight IS NOT NULL
      AND p.Weight > 0;
END;
$$ LANGUAGE plpgsql;

COMMENT ON FUNCTION support.m_product_upsert_all_uom_conversions_from_weight() IS
'Batch processes all active products with PCE as base UOM and a valid Weight (> 0).
Calls support.m_product_upsert_uom_conversion_from_weight() for each eligible product.
Returns a table with M_Product_ID, product Value, and status for each processed product.';
