-- 2024-12-14
-- Create support schema and function to upsert C_UOM_Conversion from M_Product.Weight
-- For products with PCE as base UOM, creates conversion to KGM using Weight value

-- 1. Create schema
CREATE SCHEMA IF NOT EXISTS support;

DROP FUNCTION IF EXISTS support.m_product_upsert_all_uom_conversions_from_weight();
DROP FUNCTION IF EXISTS support.m_product_upsert_uom_conversion_from_weight(numeric);

-- 2. Single-product function

CREATE OR REPLACE FUNCTION support.m_product_upsert_uom_conversion_from_weight(
    p_M_Product_ID numeric
) RETURNS TEXT AS $$
DECLARE
    v_product RECORD;
    v_pce_uom_id numeric;
    v_kgm_uom_id numeric;
    v_existing_id numeric;
BEGIN
    -- Get product data
    SELECT p.M_Product_ID, p.C_UOM_ID, p.Weight, p.AD_Client_ID, p.AD_Org_ID
    INTO v_product
    FROM M_Product p
    WHERE p.M_Product_ID = p_M_Product_ID AND p.IsActive = 'Y';

    IF v_product IS NULL THEN
        RETURN 'SKIP: Product not found';
    END IF;

    -- Validate Weight
    IF v_product.Weight IS NULL OR v_product.Weight <= 0 THEN
        RETURN 'SKIP: Weight is NULL or <= 0';
    END IF;

    -- Get PCE UOM ID for product's client (check if product's UOM is PCE)
    SELECT u.C_UOM_ID INTO v_pce_uom_id
    FROM C_UOM u
    WHERE u.C_UOM_ID = v_product.C_UOM_ID
      AND u.X12DE355 = 'PCE'
      AND u.IsActive = 'Y';

    IF v_pce_uom_id IS NULL THEN
        RETURN 'SKIP: Product UOM is not PCE';
    END IF;

    -- Get KGM UOM ID (prefer client-specific, fallback to system)
    SELECT u.C_UOM_ID INTO v_kgm_uom_id
    FROM C_UOM u
    WHERE u.X12DE355 = 'KGM'
      AND u.IsActive = 'Y'
      AND (u.AD_Client_ID = v_product.AD_Client_ID OR u.AD_Client_ID = 0)
    ORDER BY u.AD_Client_ID DESC NULLS LAST
    LIMIT 1;

    IF v_kgm_uom_id IS NULL THEN
        RETURN 'SKIP: KGM UOM not found';
    END IF;

    -- Check if conversion already exists
    SELECT c.C_UOM_Conversion_ID INTO v_existing_id
    FROM C_UOM_Conversion c
    WHERE c.M_Product_ID = p_M_Product_ID
      AND c.C_UOM_ID = v_pce_uom_id
      AND c.C_UOM_To_ID = v_kgm_uom_id
      AND c.IsActive = 'Y';

    IF v_existing_id IS NOT NULL THEN
        -- UPDATE existing
        UPDATE C_UOM_Conversion
        SET MultiplyRate = v_product.Weight,
            DivideRate = 1.0 / v_product.Weight,
            Updated = now(),
            UpdatedBy = 99,
            IsCatchUOMForProduct='Y'
        WHERE C_UOM_Conversion_ID = v_existing_id;

        RETURN 'UPDATED: C_UOM_Conversion_ID=' || v_existing_id;
    ELSE
        -- INSERT new
        INSERT INTO C_UOM_Conversion (
            C_UOM_Conversion_ID, AD_Client_ID, AD_Org_ID,
            C_UOM_ID, C_UOM_To_ID, M_Product_ID,
            MultiplyRate, DivideRate,
            IsActive, IsCatchUOMForProduct,
            Created, CreatedBy, Updated, UpdatedBy
        ) VALUES (
            nextval('c_uom_conversion_seq'), v_product.AD_Client_ID, v_product.AD_Org_ID,
            v_pce_uom_id, v_kgm_uom_id, p_M_Product_ID,
            v_product.Weight, 1.0 / v_product.Weight,
            'Y', 'Y',
            now(), 99, now(), 99
        );

        RETURN 'INSERTED: New conversion for product ' || p_M_Product_ID;
    END IF;
END;
$$ LANGUAGE plpgsql;

COMMENT ON FUNCTION support.m_product_upsert_uom_conversion_from_weight(numeric) IS
'Creates or updates a C_UOM_Conversion record for a product based on its Weight value.
Only works for products where C_UOM_ID is a PCE (piece) UOM.
The Weight is interpreted as kg per piece, creating a PCE -> KGM conversion.
Returns a status string indicating what action was taken (INSERTED, UPDATED, or SKIP with reason).';

-- 3. Batch processing function

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
