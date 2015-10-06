create or replace function fact_acct_unpost
(
	p_TableName character varying,
	p_Record_ID numeric
)
returns void
AS
$BODY$
DECLARE
	v_AD_Table_ID integer := -1;
BEGIN
	select AD_Table_ID
		into v_AD_Table_ID
		from AD_Table
		where TableName=p_TableName
	;
	
	delete from Fact_Acct fa
	where fa.AD_Table_ID=v_AD_Table_ID and fa.Record_ID=p_Record_ID
	;
	
	execute 'UPDATE '||p_TableName||' SET Posted=''N'', Processing=''N'''
		||' WHERE '||p_TableName||'_ID='||p_Record_ID;		
END;
$BODY$
LANGUAGE plpgsql VOLATILE COST 100;

ALTER FUNCTION fact_acct_unpost(character varying, numeric) OWNER TO adempiere;
