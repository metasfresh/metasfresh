DROP FUNCTION IF EXISTS fact_acct_unpost(character varying, numeric);

CREATE OR REPLACE FUNCTION fact_acct_unpost(p_tablename character varying, p_Record_ID numeric)
RETURNS text AS
$BODY$
DECLARE
	v_AD_Table_ID integer := -1;
	rowcount integer;
	v_result text := '';
BEGIN
	-- Do nothing if there was no record ID provided
	if (p_Record_ID is null or p_Record_ID <= 0) then
		return 'Nothing to do because Record_ID is null or <= 0';
	end if;
	--
	-- Get AD_Table_ID
	select AD_Table_ID
		into v_AD_Table_ID
		from AD_Table
		where UPPER(TableName)=UPPER(p_TableName)
	;
	--
	if (v_AD_Table_ID is null or v_AD_Table_ID <= 0) then
		raise exception 'No AD_Table_ID was found for TableName=%', p_TableName;
	end if;
	
	--
	-- Lock the record.
	-- Make sure it was not already locked.
	execute 'UPDATE '||p_TableName||' SET Processing=''Y'' WHERE '||p_TableName||'_ID='||p_Record_ID||' AND Processing=''N''';
	GET DIAGNOSTICS rowcount = ROW_COUNT;
	if (rowcount <> 1) then
		raise notice 'Failed to lock record %, ID=%  (affected rows count was %).', p_TableName, p_Record_ID, rowcount;
		raise notice 'HINT: Check the processing flag (select Processing from % where %_ID=%)', p_TableName, p_TableName, p_Record_ID;
		raise notice 'HINT: Set processing flag to No (update % set Processing=''N'' where %_ID=%)', p_TableName, p_TableName, p_Record_ID;
		raise exception 'Cannot lock record TableName=%, ID=%. Check previous notices for more info', p_TableName, p_Record_ID;
	end if;

	--
	-- Delete the Fact_Acct records
	delete from Fact_Acct fa where fa.AD_Table_ID=v_AD_Table_ID and fa.Record_ID=p_Record_ID;
	GET DIAGNOSTICS rowcount = ROW_COUNT;
	v_result := ''||rowcount||' Fact_Acct records deleted';
	
	--
	-- Mark the record as not posted, unlock it.
	execute 'UPDATE '||p_TableName||' SET Posted=''N'', Processing=''N'' WHERE '||p_TableName||'_ID='||p_Record_ID;
	
	return v_result;
END;
$BODY$
LANGUAGE plpgsql VOLATILE
COST 100;

