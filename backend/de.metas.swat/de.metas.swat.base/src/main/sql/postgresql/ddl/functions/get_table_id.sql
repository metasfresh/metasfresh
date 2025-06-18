
-- Function: get_table_id(character varying)

-- DROP FUNCTION get_table_id(character varying);

CREATE OR REPLACE FUNCTION get_table_id(tablename character varying)
    RETURNS numeric AS
$BODY$
SELECT t.AD_Table_ID FROM AD_Table t WHERE UPPER(t.TableName) = UPPER($1);
$BODY$
    LANGUAGE sql IMMUTABLE
                 COST 100;
COMMENT ON FUNCTION get_table_id(character varying) IS
    'Returns the Table_ID for the given name, case-insensitive.
    
    WARNING: This function is declared IMMUTABLE for performance reasons.
    If the contents of AD_Table.TableName are changed,
    you must restart the session or reconnect the client to ensure correct behavior.
    See PostgreSQL documentation on IMMUTABLE functions:
    https://www.postgresql.org/docs/current/xfunc-volatility.html';