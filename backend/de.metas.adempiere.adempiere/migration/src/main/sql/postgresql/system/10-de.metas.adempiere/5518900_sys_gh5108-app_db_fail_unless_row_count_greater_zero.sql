
CREATE OR REPLACE FUNCTION db_fail_unless_row_count_greater_zero(IN p_InserOrUpdateStmt text)
returns void language plpgsql 
as $BODY$
DECLARE
	v_Row_Count int; 
BEGIN
    EXECUTE p_InserOrUpdateStmt;
	GET DIAGNOSTICS v_Row_Count = ROW_COUNT;
	
    IF v_Row_Count <= 0
	THEN raise exception 'Update statement did not match any record; UpdateStm=%', p_UpdateStmt;
    END IF;
END; 
$BODY$;    
COMMENT ON FUNCTION db_fail_unless_row_count_greater_zero(text) IS
'Executes the given p_InserOrUpdateStmt and fails unless at least one row was changed.
Useful if you want to update something and are not 100% sure that sonething is actually already exististing in your DB.'
