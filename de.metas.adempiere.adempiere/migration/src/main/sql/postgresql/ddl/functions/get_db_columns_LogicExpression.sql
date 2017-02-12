-- Function: get_db_columns_LogicExpression()

-- DROP FUNCTION IF EXISTS get_db_columns_LogicExpression();

CREATE OR REPLACE FUNCTION get_db_columns_LogicExpression()
  RETURNS TABLE(tablename character varying, record_id integer, columnname character varying, logicExpression character varying) AS
$BODY$
/**
 * Functions retrieves all columns from application dictionary which have columns containing logic expression snippets.
 */
DECLARE
	v_sql		text := '';
	rec			record;
BEGIN
	for rec in (select t.tableName, c.columnName
				, 'select '
					-- TableName
					|| ''''||t.TableName||'''::varchar AS TableName'
					-- Record_ID
					|| ', '|| COALESCE(
							(select max(k.ColumnName) from AD_Column k where k.AD_Table_ID=t.AD_Table_ID and k.IsKey='Y' and k.IsActive='Y' having count(1)=1)
							, (select max(k.ColumnName) from AD_Column k where k.AD_Table_ID=t.AD_Table_ID and k.IsParent='Y' and k.IsActive='Y' having count(1)=1)
							, 'NULL'
						)
						||'::integer AS Record_ID'
					-- ColumnName
					|| ', '''||c.ColumnName||'''::varchar AS ColumnName'
					-- Column Value
					|| ', '||c.ColumnName||' AS LogicExpression'
					--
					|| ' FROM '||t.TableName
					|| '' AS sqltext
				from AD_Table t
				inner join AD_Column c on (c.AD_Table_ID=t.AD_Table_ID)
				where 
				(
					c.ColumnName ilike '%logic%'
				)
				order by t.tableName, c.ColumnName
	) loop
		if (v_sql <> '') then
			v_sql := v_sql || ' union all ';
		end if;
		v_sql := v_sql || rec.sqltext;
	end loop;
	
	v_sql := 'SELECT * FROM ('||v_sql||') t';
	
	return query execute v_sql;
END;
$BODY$
LANGUAGE plpgsql VOLATILE
COST 100
ROWS 1000
;

