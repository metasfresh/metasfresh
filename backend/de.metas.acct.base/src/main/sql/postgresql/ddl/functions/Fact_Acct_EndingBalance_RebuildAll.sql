drop function if exists de_metas_acct.Fact_Acct_EndingBalance_RebuildAll();
create or replace function de_metas_acct.Fact_Acct_EndingBalance_RebuildAll()
returns text
AS
$BODY$
declare
	v_CountInserted integer;
begin
	
drop table if exists  backup.EndingBalance_bkp20162405;

create table backup.EndingBalance_bkp20162405 as select * from Fact_Acct_EndingBalance;

drop table if exists TMP_Fact_Acct;
create temporary table TMP_Fact_Acct as select * from Fact_Acct;
create index on TMP_Fact_Acct (AD_Client_ID, AD_Org_ID, C_Period_ID, DateAcct, C_AcctSchema_ID, PostingType, Account_ID);



drop table if exists TMP_Fact_Acct_EndingBalance;
create table TMP_Fact_Acct_EndingBalance as select * from Fact_Acct_EndingBalance limit 0;
insert into TMP_Fact_Acct_EndingBalance
(
	fact_acct_id
	, AmtAcctDr_DTD
	, AmtAcctCr_DTD
	, C_AcctSchema_ID
	, Account_ID
	, PostingType
	, DateAcct
	, ad_client_id
	, ad_org_id
	, created
	, createdby
	, isactive
	, updated
	, updatedby
)
select
	fa.fact_acct_id
	, sum(AmtAcctDr) over facts_previous as AmtAcctDr_DTD
	, sum(AmtAcctCr) over facts_previous as AmtAcctCr_DTD
	, fa.C_AcctSchema_ID
	, fa.Account_ID
	, fa.PostingType
	, fa.DateAcct
	, fa.ad_client_id
	, fa.ad_org_id
	, fa.created
	, fa.createdby
	, fa.isactive
	, fa.updated
	, fa.updatedby
	from TMP_Fact_Acct fa
	window facts_previous as (partition by fa.AD_Client_ID, fa.AD_Org_ID, fa.C_AcctSchema_ID, fa.PostingType, fa.Account_ID, fa.DateAcct order by Fact_Acct_ID)
	;


--
-- WARNING: Perform the actual change:
-- truncate table Fact_Acct_EndingBalance;
delete from Fact_Acct_EndingBalance ;
insert into Fact_Acct_EndingBalance select * from TMP_Fact_Acct_EndingBalance;
	
	GET DIAGNOSTICS v_CountInserted = ROW_COUNT;

	return ''||v_CountInserted||' rows inserted into Fact_Acct_EndingBalance';
end;
$BODY$
LANGUAGE plpgsql;

COMMENT ON FUNCTION de_metas_acct.Fact_Acct_EndingBalance_RebuildAll() IS 'Rebuilds Fact_Acct_EndingBalance.';


