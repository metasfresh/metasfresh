DROP FUNCTION IF EXISTS get_C_ValidCombination_Records();

CREATE OR REPLACE FUNCTION get_C_ValidCombination_Records()
RETURNS TABLE(
	TableName character varying
	, KeyColumnName character varying
	, Record_ID numeric
	, C_AcctSchema_ID numeric
	, C_ValidCombination_ColumnName character varying
	, C_ValidCombination_ID numeric)
AS
$BODY$
/**
 * Functions retrieves all table column records which are pointing to C_ValidCombination.
 */
DECLARE
	v_sql		text := '';
	rec			record;
BEGIN
	for rec in (select
				t.TableName::character varying AS TableName
				, c.ColumnName::character varying AS Account_ColumnName
				--
				, COALESCE(
					(select max(k.ColumnName) from AD_Column k where k.AD_Table_ID=t.AD_Table_ID and k.IsKey='Y' and k.IsActive='Y' having count(1)=1)
					, (select max(k.ColumnName) from AD_Column k where k.AD_Table_ID=t.AD_Table_ID and k.IsParent='Y' and k.IsActive='Y' and k.ColumnName<>'C_AcctSchema_ID' having count(1)=1)
					, (select max(k.ColumnName) from AD_Column k where k.AD_Table_ID=t.AD_Table_ID and k.IsParent='Y' and k.IsActive='Y' and k.ColumnName='C_AcctSchema_ID' having count(1)=1)
					, null
				)::character varying as KeyColumnName
				--
				, (CASE WHEN EXISTS (select 1 from AD_Column k where k.AD_Table_ID=t.AD_Table_ID and k.ColumnName='C_AcctSchema_ID') THEN 'Y' ELSE 'N' END) as HasAcctSchema
				--
				from AD_Column c
				inner join AD_Table t on (t.AD_Table_ID=c.AD_Table_ID)
				where c.AD_Reference_ID=25 /* account */
				and c.ColumnSQL IS NULL -- not virtual
				and t.IsView='N' -- not a view
				order by t.TableName, c.ColumnName
	) loop
		if (v_sql <> '') then
			v_sql := v_sql || ' union all ';
		end if;
		
		v_sql := v_sql || 'SELECT ';
		
		-- TableName
		v_sql := v_sql || ''''||rec.TableName||'''::character varying AS TableName';
		
		-- KeyColumnName
		v_sql := v_sql || ', '''||rec.KeyColumnName||'''::character varying AS KeyColumnName';
		if (rec.KeyColumnName is not null) then
			v_sql := v_sql || ', '||rec.KeyColumnName || '::numeric AS Record_ID';
		else
			v_sql := v_sql || ', NULL::numeric AS Record_ID';
		end if;
		
		-- C_AcctSchema_ID
		if (rec.HasAcctSchema='Y') then
			v_sql := v_sql || ', C_AcctSchema_ID::numeric';
		else
			v_sql := v_sql || ', NULL::numeric AS C_AcctSchema_ID';
		end if;
		
		-- C_ValidCombination_ID
		v_sql := v_sql || ', '''||rec.Account_ColumnName||'''::character varying AS C_ValidCombination_ColumnName';
		v_sql := v_sql || ', '||rec.Account_ColumnName||'::numeric AS C_ValidCombination_ID';
		
		-- FROM...
		v_sql := v_sql || ' FROM '||rec.TableName;
	end loop;
	
	v_sql := 'SELECT t.TableName, t.KeyColumnName, t.Record_ID, t.C_AcctSchema_ID, t.C_ValidCombination_ColumnName, t.C_ValidCombination_ID FROM ('||v_sql||') t';
	
	return query execute v_sql;
END;
$BODY$
LANGUAGE plpgsql VOLATILE
COST 100
ROWS 1000;

ALTER FUNCTION get_C_ValidCombination_Records() OWNER TO adempiere;
