DROP FUNCTION IF EXISTS get_posted_status_summary();
CREATE OR REPLACE FUNCTION get_posted_status_summary()
RETURNS TABLE(
	TableName character varying
	, Processed char(1)
	, DocStatus character varying
	, Posted char(1)
	, Count bigint
)
AS
$BODY$
/**
 * Function retrieves summary counters of document's Posted status
 */
DECLARE
	v_sql text := '';
	v_sql_all text := '';
	tbl record;
BEGIN
	--
	-- Create the v_sql_all which unions all table with "Posted" support
	for tbl in (select
				t.tableName
				, exists (select 1 from AD_Column c where c.AD_Table_ID=t.AD_Table_ID and c.ColumnName='Processed' and c.ColumnSQL is null) as Has_Processed
				, exists (select 1 from AD_Column c where c.AD_Table_ID=t.AD_Table_ID and c.ColumnName='DocStatus' and c.ColumnSQL is null) as Has_DocStatus
				from AD_Table t
				where true
					-- Not a view
					and t.IsView='N'
					-- "Posted" column shall exist and shall not be virtual
					and exists (select 1 from AD_Column c where c.AD_Table_ID=t.AD_Table_ID and c.ColumnName='Posted' and c.ColumnSQL is null)
				order by t.tableName)
	loop
		v_sql := 'select '''||tbl.TableName||'''::varchar as TableName'
			||', ' || (case when tbl.Has_Processed then 'Processed' else 'NULL::char(1)' end)||' as Processed'
			||', ' || (case when tbl.Has_DocStatus then 'DocStatus' else 'NULL' end)||'::varchar as DocStatus'
			||', Posted'
			||', count(1) as Count'
			||' from '||tbl.TableName
			||' group by Posted'
			||(case when tbl.Has_Processed then ', Processed' else '' end)
			||(case when tbl.Has_DocStatus then ', DocStatus' else '' end)
		;
		-- raise notice 'SQL: %', v_sql;
		
		if (v_sql_all <> '') then
			v_sql_all := v_sql_all || E'\n union all ';
		end if;
		v_sql_all := v_sql_all || v_sql;
	end loop;
	--
	v_sql_all := E'SELECT * FROM (\n'
		|| v_sql_all
		|| E'\n) t';
	
	--
	-- Return query execution
	return query execute v_sql_all;
END;
$BODY$
LANGUAGE plpgsql VOLATILE
COST 100
ROWS 1000;

--
-- Make sure the view functions is OK
do $$
declare
	v_cnt numeric;
begin
	select count(1) into v_cnt from get_posted_status_summary();
	raise notice 'View functions has % rows', v_cnt;
end $$;


