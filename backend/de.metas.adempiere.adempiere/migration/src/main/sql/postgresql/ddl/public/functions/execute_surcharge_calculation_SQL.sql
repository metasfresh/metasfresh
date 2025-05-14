DROP FUNCTION IF EXISTS execute_surcharge_calculation_SQL(numeric, numeric, numeric)
;

CREATE OR REPLACE FUNCTION execute_surcharge_calculation_SQL
(
    p_m_discountschema_id numeric,
    p_Target_PriceList_Version_ID numeric,
    p_Source_M_ProductPrice_ID numeric
)
    RETURNS numeric AS
$BODY$
DECLARE
    sql text;
    result numeric;
BEGIN

    sql := (
        SELECT dsc.surcharge_calc_sql
        FROM M_DiscountSchema ds
            LEFT JOIN M_DiscountSchema_Calculated_Surcharge dsc ON ds.M_DiscountSchema_Calculated_Surcharge_ID = dsc.M_DiscountSchema_Calculated_Surcharge_ID
        WHERE ds.m_discountschema_id = p_m_discountschema_id
    );

    if sql IS NULL then
        RETURN 0::numeric;
    end if;

    EXECUTE sql
        USING p_Target_PriceList_Version_ID, p_Source_M_ProductPrice_ID
        INTO result;

    RETURN COALESCE(result, 0::numeric);
END
$BODY$
    LANGUAGE plpgsql VOLATILE;
