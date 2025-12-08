DROP FUNCTION IF EXISTS validate_surcharge_calculation_SQL(text, numeric, numeric)
;

CREATE OR REPLACE FUNCTION validate_surcharge_calculation_SQL
(
    p_sql text,
    p_Target_PriceList_Version_ID numeric,
    p_Source_M_ProductPrice_ID numeric
)
    RETURNS VOID AS
$BODY$
BEGIN
    SET TRANSACTION READ ONLY;

    EXECUTE p_sql
        USING p_Target_PriceList_Version_ID, p_Source_M_ProductPrice_ID;
END
$BODY$
    LANGUAGE plpgsql VOLATILE;

COMMENT ON FUNCTION validate_surcharge_calculation_SQL(text, numeric, numeric) IS 'Validate SQL later executed in execute_surcharge_calculation_SQL'
;
