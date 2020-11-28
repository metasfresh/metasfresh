drop function if exists de_metas_acct.Fact_Acct_EndingBalance_UpdateForTag(p_ProcessingTag varchar);
create or replace function de_metas_acct.Fact_Acct_EndingBalance_UpdateForTag(p_ProcessingTag varchar)
returns text
AS
$BODY$
declare
	v_CountDeleted integer;
	v_CountInserted integer;
begin
	delete from Fact_Acct_EndingBalance eb
	where exists (
		select 1 from Fact_Acct_Log log
		where true
		and log.ProcessingTag=p_ProcessingTag
		and eb.Account_ID=log.C_ElementValue_ID
		and eb.C_AcctSchema_ID=log.C_AcctSchema_ID
		and eb.PostingType=log.PostingType
		-- and AD_Client_ID=log.AD_Client_ID -- commented out because we don't have an index on this column
		and eb.DateAcct=log.DateAcct
		and eb.ad_org_id = log.AD_Org_ID
	);
	GET DIAGNOSTICS v_CountDeleted = ROW_COUNT;

	INSERT INTO Fact_Acct_EndingBalance
	(
		fact_acct_id
		, AmtAcctDr_DTD, AmtAcctCr_DTD
		, C_AcctSchema_ID, Account_ID, PostingType, DateAcct
		, ad_client_id, ad_org_id, created, createdby, isactive, updated, updatedby
	)
	select
		fa.fact_acct_id
		, sum(AmtAcctDr) over facts_previous as AmtAcctDr_DTD
		, sum(AmtAcctCr) over facts_previous as AmtAcctCr_DTD
		, fa.C_AcctSchema_ID, fa.Account_ID, fa.PostingType, fa.DateAcct
		, fa.ad_client_id, fa.ad_org_id, fa.created, fa.createdby, fa.isactive, fa.updated, fa.updatedby
		-- debug info:
		-- , fa.DateAcct, (select ev.Value from C_ElementValue ev where ev.C_ElementValue_ID=fa.Account_ID) as Account
	from (
		select distinct C_ElementValue_ID, C_AcctSchema_ID, PostingType, DateAcct, AD_Org_ID
		from Fact_Acct_Log
		where ProcessingTag=p_ProcessingTag
	) log
	inner join Fact_Acct fa on (
		fa.Account_ID=log.C_ElementValue_ID
		and fa.C_AcctSchema_ID=log.C_AcctSchema_ID
		and fa.PostingType=log.PostingType
		-- and fa.AD_Client_ID=log.AD_Client_ID -- commented out because we don't have an index on this column
		and fa.DateAcct=log.DateAcct
		and fa.ad_org_id = log.ad_org_id
	)
	window facts_previous as (partition by fa.AD_Client_ID, fa.ad_org_id, fa.C_AcctSchema_ID, fa.PostingType, fa.Account_ID, fa.DateAcct order by Fact_Acct_ID)
	;
	GET DIAGNOSTICS v_CountInserted = ROW_COUNT;

	return ''||v_CountDeleted||' rows deleted, '||v_CountInserted||' rows inserted into Fact_Acct_EndingBalance for tag='||p_ProcessingTag;
end;
$BODY$
LANGUAGE plpgsql;

COMMENT ON FUNCTION de_metas_acct.fact_acct_endingbalance_updatefortag(character varying) IS 'Checks Fact_Acct_Log for given tag and updates Fact_Acct_EndingBalance precomputed table.';


/*
delete from Fact_Acct_log where ProcessingTag='test';
update Fact_Acct_Log set ProcessingTag='test';
select * from Fact_Acct_Log
select de_metas_acct.Fact_Acct_EndingBalance_UpdateForTag('test');
*/
