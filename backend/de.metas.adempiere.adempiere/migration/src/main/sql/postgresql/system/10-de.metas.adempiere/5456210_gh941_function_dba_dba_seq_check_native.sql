DROP FUNCTION IF EXISTS dba_seq_check_native();
DROP FUNCTION IF EXISTS dba_seq_check_native(varchar);


CREATE OR REPLACE FUNCTION dba_seq_check_native(IN p_tableName varchar DEFAULT NULL)
RETURNS void AS
$BODY$
DECLARE
	v_record_to_process record;
	v_firstId bigint := 1000000;
	v_nextid bigint;
	v_sequence_name varchar;
BEGIN
	FOR v_record_to_process in
	(
		SELECT 
			pt.Table_Name, -- physical table name
			seq.sequence_name,
			pc.column_name, -- physical column name
			
			-- we will create/update the native sequence named "tableName||'_seq'" 
			-- if there is *no* AD_Column or if there is an AD_Column that is both active and is flagged as a key column
			-- otherwise, this function will not do anything about that native sequence.
			(ac.AD_Column_ID IS NULL OR (ac.IsActive='Y' AND (ac.IsKey='Y' OR ac.IsParent='Y'))) AS CreateOrUpdateNativeSequence
	
		FROM public.AD_Table at
			INNER JOIN information_schema.tables pt ON pt.table_schema='public' AND lower(pt.Table_Name)=lower(at.TableName)
			LEFT OUTER JOIN information_schema.sequences seq ON seq.sequence_name=lower(at.tableName||'_seq') -- find out if there is an existing native sequence
			LEFT OUTER JOIN public.AD_Column ac ON ac.AD_Table_ID=at.AD_Table_ID AND lower(ac.ColumnName)=lower(at.TableName||'_ID')
			LEFT OUTER JOIN information_schema.columns pc ON pc.table_schema='public' AND pc.table_name=lower(at.tableName) and pc.column_name=lower(at.TableName||'_ID')
		WHERE true
			-- use the application dictionary's AD_Table and AD_Column to decide what tables the function chall deal with
			AND at.IsView='N' 
			AND at.IsActive='Y'
			AND (
				quote_literal(p_tableName) IS NULL AND at.TableName not like 'X!_%Template' escape '!'
				OR
				quote_literal(lower(p_tableName)) = lower(''''||at.TableName||'''')
			)
		ORDER BY at.TableName
	)
	LOOP
		BEGIN
			-- RAISE NOTICE 't=%',t; CONTINUE; -- uncomment for debugging
			
			IF v_record_to_process.column_name IS NULL
			THEN
				RAISE NOTICE 'Ignoring table % because it has no column %', v_record_to_process.Table_Name, v_record_to_process.Table_Name||'_ID';
				CONTINUE;
			END IF;
			IF NOT v_record_to_process.CreateOrUpdateNativeSequence
			THEN
				RAISE NOTICE 'Ignoring table % because of its AD_Column %', v_record_to_process.Table_Name, v_record_to_process.Table_Name||'_ID';
				CONTINUE;
			END IF;
		
			-- get the sequence's next value
			execute 'select max('
				||quote_ident(v_record_to_process.column_name)
				||') from '
				||quote_ident(v_record_to_process.Table_Name)
			into v_nextid;
				
			if (v_nextid is null)
			then
				v_nextid := v_firstId;
			else
				v_nextid := v_nextid + 1;
			end if;
	
			if (v_nextid < v_firstId)
			then
				v_nextid := v_firstId;
			end if;
	
			-- do the actual creating or updating of the native sequence
			if (v_record_to_process.sequence_name is null)
			then
				v_sequence_name := quote_ident(v_record_to_process.Table_Name||'_seq');
	
				execute 'CREATE SEQUENCE '||v_sequence_name
					||' INCREMENT 1'
					||' MINVALUE 0'
					||' MAXVALUE 2147483647' -- MAX java integer
					||' START '||v_nextid
				;
	
				raise notice 'Sequence %: created - v_nextid=%', v_sequence_name, v_nextid;
			else
				v_sequence_name := v_record_to_process.sequence_name;
	
				perform setval(v_sequence_name, v_nextid, false);
				
				raise notice 'Sequence %: updated - v_nextid=%', v_sequence_name, v_nextid;
			end if;
			
		exception when others then
			raise warning 'error while creating/updating native sequence %: % (sql-state=%)', v_sequence_name, SQLERRM, SQLSTATE;
		end;
	END LOOP;
end;
$BODY$
	LANGUAGE plpgsql VOLATILE
	COST 100;
COMMENT ON FUNCTION dba_seq_check_native(varchar) IS
'Creates or updates a native sequences. If a not-null p_tableName parameter is provided (case insensitive), then it works with just that table.
If no p_tableName parameter is provided, it does the job for each metasfresh AD_Table (whose name is not like X!_%Template) that is active and not flagged as a view.
In both cases, it ignores AD_Tables that have an AD_Column with
* ColumnName=TableName||''_ID''
* IsActive=''N'' or both IsParent=''N'' and IsKey=''N''.

In other words, you can use an AD_Column record to explicitly tell this function not to do anything about the respective table''s native sequence.
Also note that the function won''t do anything unless there is a physical column named like "<tablename>_id".

Otherwise, the sequence is named lower(tableName||''_seq'') and its next value is set from the maximum value of the key or parent column.'
;


--select dba_seq_check_native('M_HU_Trx_Line')
--select dba_seq_check_native();