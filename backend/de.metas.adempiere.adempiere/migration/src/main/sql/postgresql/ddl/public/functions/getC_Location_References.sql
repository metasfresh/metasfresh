DROP FUNCTION IF EXISTS getC_Location_References();

CREATE OR REPLACE FUNCTION getC_Location_References()
RETURNS TABLE(
	TableName character varying
	, C_Location_ColumnName character varying
	, C_Location_ID numeric
)
AS
$BODY$
/**
 * Functions retrieves all table column records which are pointing to C_ValidCombination.
 */
DECLARE
	v_sql		text := '';
	rec			record;
BEGIN
	for rec in (
				select fk.TableName, fk.ColumnName
				from db_columns_fk fk
				inner join AD_Table t on (t.TableName=fk.TableName)
				where ref_tableName='C_Location'
				and t.IsView='N' and t.IsActive='Y' and fk.IsVirtualColumn='N'
				order by t.TableName, fk.ColumnName
	) loop
		if (v_sql <> '') then
			v_sql := v_sql || ' union all ';
		end if;
		
		v_sql := v_sql || 'SELECT ';
		
		-- TableName
		v_sql := v_sql || ''''||rec.TableName||'''::character varying AS TableName';
		
		-- C_Location
		v_sql := v_sql || ', '''||rec.ColumnName||'''::character varying AS C_Location_ColumnName';
		v_sql := v_sql || ', '||rec.ColumnName||'::numeric AS C_Location_ID';
		
		-- FROM...
		v_sql := v_sql || ' FROM '||rec.TableName;

		-- WHERE...
		v_sql := v_sql || ' WHERE true ';
		v_sql := v_sql || ' and '||rec.ColumnName||' is not null';
	end loop;
	
	raise debug 'Using SQL: %', v_sql;

	return query execute v_sql;
END;
$BODY$
LANGUAGE plpgsql VOLATILE
COST 100
ROWS 1000;


-- select * from getC_Location_References()