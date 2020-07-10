drop function if exists get_db_columns_string();
create or replace function get_db_columns_string()
returns TABLE (
	TableName varchar(2000),
	Record_ID integer,
	ColumnName varchar(2000),
	ColumnValue text
)
AS
$$
/**
 * Functions retrieves all columns from application dictionary which are of type varchar/string/text etc.
 * 
 * This function is used to search for a particular string in all tables in all columns.
 * e.g. select * from get_db_columns_string() where lower(columnValue) like '%mytext%'
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
					|| ', '||c.ColumnName||'::text AS ColumnVale'
					--
					|| ' FROM '||t.TableName
					|| '' AS sqltext
				from AD_Table t
				inner join AD_Column c on (c.AD_Table_ID=t.AD_Table_ID)
				where  true
				and t.IsView='N'
				and t.IsActive='Y' and c.IsActive='Y'
				and c.ColumnSQL is null
				and c.AD_Reference_ID in (
					10 -- String
					, 14 -- Text
					, 34 -- Memo
					, 36 -- Text long
					, 38 -- File path
					, 39 -- File name
					, 40 -- URL
					, 42 -- Printer Name
				)
				-- Skip views which were forgot to be marked as views
				and not (t.TableName like 'RV%')
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
$$ LANGUAGE plpgsql;

