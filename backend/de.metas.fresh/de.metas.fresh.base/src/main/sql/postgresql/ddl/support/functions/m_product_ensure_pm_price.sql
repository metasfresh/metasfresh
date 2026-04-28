-- Function: support.m_product_ensure_pm_price(numeric, numeric)

-- DROP FUNCTION IF EXISTS support.m_product_ensure_pm_price(numeric, numeric);

CREATE OR REPLACE FUNCTION support.m_product_ensure_pm_price(
    p_M_Product_ID numeric,
    p_C_TaxCategory_ID numeric DEFAULT NULL
) RETURNS TABLE(
    pm_product_id numeric,
    pm_product_value varchar,
    pm_level varchar,
    m_pricelist_version_id numeric,
    status text
) AS $$
DECLARE
    v_tax_category_id numeric;
    v_rec RECORD;
    v_new_pp_id numeric;
BEGIN
    -- Get tax category (use parameter or default to "Normale MWSt")
    IF p_C_TaxCategory_ID IS NOT NULL THEN
        SELECT tc.C_TaxCategory_ID INTO v_tax_category_id
        FROM C_TaxCategory tc
        WHERE tc.C_TaxCategory_ID = p_C_TaxCategory_ID AND tc.IsActive = 'Y';

        IF v_tax_category_id IS NULL THEN
            RAISE EXCEPTION 'Tax category with ID % not found or inactive', p_C_TaxCategory_ID;
        END IF;
    ELSE
        SELECT tc.C_TaxCategory_ID INTO v_tax_category_id
        FROM C_TaxCategory tc
        WHERE tc.Name = 'Normale MWSt' AND tc.IsActive = 'Y';

        IF v_tax_category_id IS NULL THEN
            RAISE EXCEPTION 'Default tax category "Normale MWSt" not found or inactive';
        END IF;
    END IF;

    -- Process TU and LU level PM products
    FOR v_rec IN
        -- TU Level PM products
        SELECT DISTINCT
            'TU'::varchar as pm_level,
            pp.M_PriceList_Version_ID,
            pm.M_Product_ID as pm_product_id,
            pmp.Value::varchar as pm_product_value,
            pmp.C_UOM_ID,
            pp.AD_Client_ID,
            pp.AD_Org_ID
        FROM M_ProductPrice pp
        JOIN M_HU_PI_Item_Product hupip ON hupip.M_Product_ID = pp.M_Product_ID AND hupip.IsActive = 'Y'
        JOIN M_HU_PI_Item tu_item ON tu_item.M_HU_PI_Item_ID = hupip.M_HU_PI_Item_ID AND tu_item.IsActive = 'Y'
        JOIN M_HU_PI_Version tu_version ON tu_version.M_HU_PI_Version_ID = tu_item.M_HU_PI_Version_ID AND tu_version.IsActive = 'Y'
        JOIN M_HU_PI_Item tu_pm_item ON tu_pm_item.M_HU_PI_Version_ID = tu_version.M_HU_PI_Version_ID
            AND tu_pm_item.ItemType = 'PM' AND tu_pm_item.IsActive = 'Y'
        JOIN M_HU_PackingMaterial pm ON pm.M_HU_PackingMaterial_ID = tu_pm_item.M_HU_PackingMaterial_ID AND pm.IsActive = 'Y'
        JOIN M_Product pmp ON pmp.M_Product_ID = pm.M_Product_ID AND pmp.IsActive = 'Y'
        WHERE pp.M_Product_ID = p_M_Product_ID
          AND pp.IsActive = 'Y'
          AND NOT EXISTS (
              SELECT 1 FROM M_ProductPrice pp2
              WHERE pp2.M_Product_ID = pm.M_Product_ID
                AND pp2.M_PriceList_Version_ID = pp.M_PriceList_Version_ID
                AND pp2.IsActive = 'Y'
          )

        UNION

        -- LU Level PM products (parent packing instruction)
        SELECT DISTINCT
            'LU'::varchar as pm_level,
            pp.M_PriceList_Version_ID,
            lu_pm.M_Product_ID as pm_product_id,
            lu_pmp.Value::varchar as pm_product_value,
            lu_pmp.C_UOM_ID,
            pp.AD_Client_ID,
            pp.AD_Org_ID
        FROM M_ProductPrice pp
        JOIN M_HU_PI_Item_Product hupip ON hupip.M_Product_ID = pp.M_Product_ID AND hupip.IsActive = 'Y'
        JOIN M_HU_PI_Item tu_item ON tu_item.M_HU_PI_Item_ID = hupip.M_HU_PI_Item_ID AND tu_item.IsActive = 'Y'
        JOIN M_HU_PI_Version tu_version ON tu_version.M_HU_PI_Version_ID = tu_item.M_HU_PI_Version_ID AND tu_version.IsActive = 'Y'
        JOIN M_HU_PI tu_pi ON tu_pi.M_HU_PI_ID = tu_version.M_HU_PI_ID
        -- Find LU items that include this TU's PI
        JOIN M_HU_PI_Item lu_item ON lu_item.Included_HU_PI_ID = tu_pi.M_HU_PI_ID AND lu_item.IsActive = 'Y'
        JOIN M_HU_PI_Version lu_version ON lu_version.M_HU_PI_Version_ID = lu_item.M_HU_PI_Version_ID AND lu_version.IsActive = 'Y'
        -- Find PM item in LU version
        JOIN M_HU_PI_Item lu_pm_item ON lu_pm_item.M_HU_PI_Version_ID = lu_version.M_HU_PI_Version_ID
            AND lu_pm_item.ItemType = 'PM' AND lu_pm_item.IsActive = 'Y'
        JOIN M_HU_PackingMaterial lu_pm ON lu_pm.M_HU_PackingMaterial_ID = lu_pm_item.M_HU_PackingMaterial_ID AND lu_pm.IsActive = 'Y'
        JOIN M_Product lu_pmp ON lu_pmp.M_Product_ID = lu_pm.M_Product_ID AND lu_pmp.IsActive = 'Y'
        WHERE pp.M_Product_ID = p_M_Product_ID
          AND pp.IsActive = 'Y'
          AND NOT EXISTS (
              SELECT 1 FROM M_ProductPrice pp2
              WHERE pp2.M_Product_ID = lu_pm.M_Product_ID
                AND pp2.M_PriceList_Version_ID = pp.M_PriceList_Version_ID
                AND pp2.IsActive = 'Y'
          )
    LOOP
        -- Insert the new product price
        v_new_pp_id := nextval('m_productprice_seq');

        INSERT INTO M_ProductPrice (
            M_ProductPrice_ID, M_PriceList_Version_ID, M_Product_ID,
            AD_Client_ID, AD_Org_ID,
            PriceList, PriceStd, PriceLimit,
            C_TaxCategory_ID, C_UOM_ID,
            IsActive, UseScalePrice, IsAttributeDependant, IsSeasonFixedPrice,
            IsDefault, IsPriceEditable, IsDiscountEditable,
            InvoicableQtyBasedOn, IsInvalidPrice, SeqNo, MatchSeqNo, IsHUPrice,
            Created, CreatedBy, Updated, UpdatedBy
        ) VALUES (
            v_new_pp_id, v_rec.M_PriceList_Version_ID, v_rec.pm_product_id,
            v_rec.AD_Client_ID, v_rec.AD_Org_ID,
            0, 0, 0,
            v_tax_category_id, v_rec.C_UOM_ID,
            'Y', 'N', 'N', 'N',
            'N', 'Y', 'Y',
            'Nominal', 'N', 10, 10, 'N',
            now(), 99, now(), 99
        );

        -- Return the result
        pm_product_id := v_rec.pm_product_id;
        pm_product_value := v_rec.pm_product_value;
        pm_level := v_rec.pm_level;
        m_pricelist_version_id := v_rec.M_PriceList_Version_ID;
        status := 'INSERTED: M_ProductPrice_ID=' || v_new_pp_id;
        RETURN NEXT;
    END LOOP;

    RETURN;
END;
$$ LANGUAGE plpgsql;

COMMENT ON FUNCTION support.m_product_ensure_pm_price(numeric, numeric) IS
'Ensures packing material products have prices in the same price list versions as the source product.
Checks both TU (Transport Unit) and LU (Load Unit) level packing materials.
Parameters:
  - p_M_Product_ID: The source product ID to check
  - p_C_TaxCategory_ID: Optional tax category ID (defaults to "Normale MWSt")
Returns a table with pm_product_id, pm_product_value, pm_level (TU/LU), m_pricelist_version_id, and status.';
