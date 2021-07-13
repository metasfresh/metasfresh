DROP FUNCTION IF EXISTS priceuomconvert(numeric, numeric, numeric, numeric, integer);

CREATE FUNCTION priceuomconvert(p_m_product_id   numeric,
                                p_price          numeric,
                                p_price_uom_id   numeric,
                                p_c_uom_to_id    numeric,
                                p_priceprecision integer DEFAULT 2)
    RETURNS numeric
    STABLE
    LANGUAGE plpgsql
AS
$$
DECLARE
    --v_Product_UOM_ID numeric;
    v_PriceConv numeric;
BEGIN
    -- Try converting using the direct conversion (if exists)
    v_PriceConv := uomConvertDirect(
            p_M_Product_ID,
            p_C_UOM_To_ID,
            p_Price_UOM_ID,
            p_Price);

    --
    -- Round to precision
    v_PriceConv = round(v_PriceConv, p_PricePrecision);

    RETURN v_PriceConv;
END;
$$;

ALTER FUNCTION priceuomconvert(numeric, numeric, numeric, numeric, integer) OWNER TO metasfresh;

