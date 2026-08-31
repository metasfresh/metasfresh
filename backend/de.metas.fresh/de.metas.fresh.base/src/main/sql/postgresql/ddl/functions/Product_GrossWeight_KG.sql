-- ============================================================================
-- SQL equivalent of ProductBL#getGrossWeight(I_M_Product) (private, ProductBL.java:220)
-- + getNetWeight(I_M_Product) (:238), NORMALIZED TO KILOGRAM.
--
-- Java returns Optional<Quantity> = (value, UOM). This function returns a single
-- numeric = the weight expressed in KG, or NULL (= Optional.empty()).
--
-- Branch logic (faithful to Java):
--   gross usable  = GrossWeight_UOM_ID IS NOT NULL
--                   AND GrossWeight IS NOT NULL
--                   AND GrossWeight > 0            (signum() > 0)
--     -> weight = GrossWeight, in UOM GrossWeight_UOM_ID
--   else (net fallback, getNetWeight):
--     Weight > 0 (signum() > 0)                    -> weight = Weight  (already KG*)
--     else                                         -> empty (NULL)
--
-- KG NORMALIZATION (the extra requirement over the Java private method):
--   * getNetWeightUOM() is hardcoded to KILOGRAM (X12DE355='KGM'), so the net
--     Weight column is already KG -> returned as-is (Java stores no other unit).
--   * The gross branch weight is in GrossWeight_UOM_ID, so it is converted to KG
--     via the standard uomConvert(product, from, to, qty) function
--     (product-specific rate first, then generic; rounds to KG StdPrecision).
--     If GrossWeight_UOM_ID IS already KG, uomConvert short-circuits (identity).
--     If NO C_UOM_Conversion rule links GrossWeight_UOM_ID to KG, uomConvert
--     returns NULL -> the gross weight cannot be expressed in KG (data gap;
--     surfaced as NULL rather than silently masked).
--
-- KG UOM lookup uses the canonical metasfresh pattern (cf. M_InOut_V):
--   select C_UOM_ID from C_UOM where x12de355='KGM' and isactive='Y'
--   order by isdefault desc limit 1
-- ============================================================================

CREATE OR REPLACE FUNCTION Product_GrossWeight_KG(p_M_Product_ID numeric)
    RETURNS numeric
    LANGUAGE plpgsql
    STABLE
AS
$BODY$
DECLARE
    v_GrossWeight        numeric;
    v_GrossWeight_UOM_ID numeric;
    v_Weight             numeric;
    v_KG_UOM_ID          numeric;
BEGIN
    SELECT p.GrossWeight, p.GrossWeight_UOM_ID, p.Weight
    INTO v_GrossWeight, v_GrossWeight_UOM_ID, v_Weight
    FROM M_Product p
    WHERE p.M_Product_ID = p_M_Product_ID;

    SELECT u.C_UOM_ID
    INTO v_KG_UOM_ID
    FROM C_UOM u
    WHERE u.X12DE355 = 'KGM'
      AND u.IsActive = 'Y'
    ORDER BY u.IsDefault DESC
    LIMIT 1;

    -- Gross branch: UOM set, value not null, value > 0 -> convert gross to KG
    IF v_GrossWeight_UOM_ID IS NOT NULL
        AND v_GrossWeight IS NOT NULL
        AND v_GrossWeight > 0
    THEN
        RETURN uomConvert(p_M_Product_ID, v_GrossWeight_UOM_ID, v_KG_UOM_ID, v_GrossWeight);
    END IF;

    -- Net fallback: Weight is already KG (hardcoded getNetWeightUOM)
    IF v_Weight IS NOT NULL AND v_Weight > 0 THEN
        RETURN v_Weight;
    END IF;

    -- Optional.empty()
    RETURN NULL;
END;
$BODY$
;

-- ---------------------------------------------------------------------------
-- Usage:
--   SELECT Product_GrossWeight_KG(1000000);   -- numeric KG, or NULL if empty
-- ---------------------------------------------------------------------------
