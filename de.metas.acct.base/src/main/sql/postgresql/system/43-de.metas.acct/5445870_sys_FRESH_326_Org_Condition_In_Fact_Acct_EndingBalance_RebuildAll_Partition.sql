drop function if exists de_metas_acct.Fact_Acct_EndingBalance_RebuildAll();
create or replace function de_metas_acct.Fact_Acct_EndingBalance_RebuildAll()
returns text
AS
$BODY$
declare
	v_CountInserted integer;
begin
	truncate table Fact_Acct_EndingBalance;

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
	from Fact_Acct fa
	window facts_previous as (partition by fa.AD_Client_ID, fa.AD_Org_ID, fa.C_AcctSchema_ID, fa.PostingType, fa.Account_ID, fa.DateAcct order by Fact_Acct_ID)
	;
	
	GET DIAGNOSTICS v_CountInserted = ROW_COUNT;

	return ''||v_CountInserted||' rows inserted into Fact_Acct_EndingBalance';
end;
$BODY$
LANGUAGE plpgsql;

COMMENT ON FUNCTION de_metas_acct.Fact_Acct_EndingBalance_RebuildAll() IS 'Rebuilds Fact_Acct_EndingBalance.';
