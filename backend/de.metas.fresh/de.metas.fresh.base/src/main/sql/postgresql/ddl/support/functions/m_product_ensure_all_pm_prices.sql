-- Function: support.m_product_ensure_all_pm_prices(numeric)

-- DROP FUNCTION IF EXISTS support.m_product_ensure_all_pm_prices(numeric);

CREATE OR REPLACE FUNCTION support.m_product_ensure_all_pm_prices(
    p_C_TaxCategory_ID numeric DEFAULT NULL
) RETURNS TABLE(
    source_product_id numeric,
    source_product_value varchar,
    pm_product_id numeric,
    pm_product_value varchar,
    pm_level varchar,
    m_pricelist_version_id numeric,
    status text
) AS $$
DECLARE
    v_source RECORD;
    v_result RECORD;
BEGIN
    -- Loop through all distinct source products that have prices and HU PI Item Products
    FOR v_source IN
        SELECT DISTINCT
            p.M_Product_ID,
            p.Value::varchar as product_value
        FROM M_Product p
        JOIN M_ProductPrice pp ON pp.M_Product_ID = p.M_Product_ID AND pp.IsActive = 'Y'
        JOIN M_HU_PI_Item_Product hupip ON hupip.M_Product_ID = p.M_Product_ID AND hupip.IsActive = 'Y'
        WHERE p.IsActive = 'Y'
    LOOP
        -- Call single-product function for each source product
        FOR v_result IN
            SELECT * FROM support.m_product_ensure_pm_price(v_source.M_Product_ID, p_C_TaxCategory_ID)
        LOOP
            source_product_id := v_source.M_Product_ID;
            source_product_value := v_source.product_value;
            pm_product_id := v_result.pm_product_id;
            pm_product_value := v_result.pm_product_value;
            pm_level := v_result.pm_level;
            m_pricelist_version_id := v_result.m_pricelist_version_id;
            status := v_result.status;
            RETURN NEXT;
        END LOOP;
    END LOOP;

    RETURN;
END;
$$ LANGUAGE plpgsql;

COMMENT ON FUNCTION support.m_product_ensure_all_pm_prices(numeric) IS
'Batch processes all products with prices and HU PI Item Products to ensure their packing material products have prices.
Parameters:
  - p_C_TaxCategory_ID: Optional tax category ID (defaults to "Normale MWSt")
Returns a table with source_product_id, source_product_value, pm_product_id, pm_product_value, pm_level (TU/LU), m_pricelist_version_id, and status.';
