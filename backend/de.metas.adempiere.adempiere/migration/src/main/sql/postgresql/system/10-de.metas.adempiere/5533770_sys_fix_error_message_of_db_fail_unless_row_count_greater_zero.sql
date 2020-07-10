
DROP FUNCTION public.db_fail_unless_row_count_greater_zero(text);

CREATE OR REPLACE FUNCTION public.db_fail_unless_row_count_greater_zero(
	p_insertorupdatestmt text)
    RETURNS void
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE 
AS $BODY$
DECLARE
	v_Row_Count int; 
BEGIN
    EXECUTE p_insertorupdatestmt;
	GET DIAGNOSTICS v_Row_Count = ROW_COUNT;
	
    IF v_Row_Count <= 0
	THEN raise exception 'The given insert or update statement did not match any record; stmt=%', p_insertorupdatestmt;
    END IF;
END; 
$BODY$;

COMMENT ON FUNCTION public.db_fail_unless_row_count_greater_zero(text)
    IS 'Executes the given p_InserOrUpdateStmt and fails unless at least one row was changed.
Useful if you want to update something and are not 100% sure that this something is actually already exististing in your DB.';
