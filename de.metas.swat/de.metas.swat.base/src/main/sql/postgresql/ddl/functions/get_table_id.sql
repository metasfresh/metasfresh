
-- Function: get_table_id(character varying)

-- DROP FUNCTION get_table_id(character varying);

CREATE OR REPLACE FUNCTION get_table_id(tablename character varying)
  RETURNS numeric AS
$BODY$SELECT t.AD_Table_ID FROM AD_Table t
WHERE t.TableName = $1;$BODY$
  LANGUAGE sql STABLE
  COST 100;
ALTER FUNCTION get_table_id(character varying) OWNER TO adempiere;
COMMENT ON FUNCTION get_table_id(character varying) IS 'Returns the Table_ID for the given name';
