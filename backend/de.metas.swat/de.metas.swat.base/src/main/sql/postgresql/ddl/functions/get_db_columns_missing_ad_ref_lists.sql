drop function if exists get_db_columns_missing_ad_ref_lists();
create or replace function get_db_columns_missing_ad_ref_lists()
returns TABLE (
	TableName varchar(2000),
	ColumnName varchar(2000),
	AD_Reference_Name varchar(2000),
	ColumnValue varchar(2000),
	AD_Reference_AvailableValues varchar(2000)
)
AS
$$
/**
 * Functions retrieves all table/columns with AD_Reference_ID=List/Button where their values does not match current AD_Ref_List values
 */
DECLARE
	col RECORD;
	v_sql TEXT := '';
BEGIN
	for col in (
		select
			t.TableName
			, c.ColumnName
			, c.AD_Reference_Value_ID
			, rv.Name as AD_Reference_Name
			, (select string_agg(rl.Value, ', ') from AD_Ref_List rl where rl.AD_Reference_ID=rv.AD_Reference_ID and rl.IsActive='Y') as AD_Reference_AvailableValues
		from AD_Column c
		inner join AD_Table t on (t.AD_Table_ID=c.AD_Table_ID)
		left outer join AD_Reference rv on (rv.AD_Reference_ID=c.AD_Reference_Value_ID)
		where true
		-- List and Button references
		and c.AD_Reference_ID in (17, 28)
		-- Only List validations (we set this condition in case of Buttons)
		and rv.ValidationType='L'
		-- Table and Column is active and not view
		and t.IsView='N'
		and t.IsActive='Y'
		and c.IsActive='Y'
		-- skip virtual columns
		and coalesce(c.ColumnSQL, '') = ''
		-- skip temporary tables
		and t.TableName not like 'T!_%' escape '!'
		and rv.AD_Reference_ID is not null
		-- make sure column exists in database
		and exists (select 1 from information_schema.columns dbc where lower(dbc.table_name)=lower(t.TableName) and lower(dbc.column_name)=lower(c.ColumnName))
		order by t.TableName, c.ColumnName
	)
	loop
		if (v_sql <> '') then
			v_sql := v_sql || ' union all ';
		end if;
		v_sql := v_sql
			||chr(13)
			||'SELECT DISTINCT '
			||''''||col.TableName||'''::varchar AS TableName'
			||', '''||col.ColumnName||'''::varchar AS ColumnName'
			||', '''||col.AD_Reference_Name||'''::varchar AS AD_Reference_Name'
			||', '||col.ColumnName||'::varchar as ColumnValue'
			||', '||quote_literal(col.AD_Reference_AvailableValues)||'::varchar AS AD_Reference_AvailableValues'
			||' FROM '||col.TableName||' t'
			||' WHERE '
			||' COALESCE('||col.ColumnName||'::varchar,'''')<>'''' '
			||' AND NOT EXISTS (SELECT 1 FROM AD_Ref_List rl WHERE rl.AD_Reference_ID='||col.AD_Reference_Value_ID||' AND rl.Value::varchar=t.'||col.ColumnName||'::varchar and rl.IsActive=''Y'')'
		;
	end loop;

	v_sql := 'SELECT * FROM ('
		||v_sql
		||chr(13)
		||') t';
	
	return query execute v_sql;
END;
$$ LANGUAGE plpgsql;

ALTER FUNCTION get_db_columns_missing_ad_ref_lists() OWNER TO adempiere;
