--
-- supposed to be run before 
-- /de.metas.acct.base/src/main/sql/postgresql/system/43-de.metas.acct/5436860_sys_09694_update_fact_acct_EndingBalance.sql 
--
DROP FUNCTION IF EXISTS public.acctbalancetodate(numeric, numeric, date);
DROP FUNCTION IF EXISTS de_metas_acct.acctbalancetodate(numeric, numeric, date);
create function de_metas_acct.acctBalanceToDate(p_Account_ID numeric, p_C_AcctSchema_ID numeric, p_DateAcct date, p_IncludePostingTypeStatistical char(1) = 'N')
RETURNS de_metas_acct.BalanceAmt AS
$BODY$
-- NOTE: we use COALESCE(SUM(..)) just to make sure we are not returning null
SELECT ROW(SUM((Balance).Balance), SUM((Balance).Debit), SUM((Balance).Credit))::de_metas_acct.BalanceAmt
FROM (
	(
		SELECT
			(case
				-- When the account is Expense/Revenue => we shall consider only the Year to Date amount
				when ev.AccountType in ('E', 'R') then ROW(fas.AmtAcctDr_YTD - fas.AmtAcctCr_YTD, fas.AmtAcctDr_YTD, fas.AmtAcctCr_YTD)::de_metas_acct.BalanceAmt
				-- For any other account => we consider from the beginning to Date amount
				else ROW(fas.AmtAcctDr - fas.AmtAcctCr, fas.AmtAcctDr, fas.AmtAcctCr)::de_metas_acct.BalanceAmt
			end) as Balance
		FROM Fact_Acct_Summary fas
		INNER JOIN C_ElementValue ev on (ev.C_ElementValue_ID=fas.Account_ID)
		WHERE true
			and fas.Account_ID=$1 -- p_Account_ID
			and fas.C_AcctSchema_ID=$2 -- p_C_AcctSchema_ID
			and fas.PostingType='A'
			and fas.PA_ReportCube_ID is null
			and fas.DateAcct <= $3 -- p_DateAcct
		ORDER BY fas.DateAcct DESC
		LIMIT 1
	)
	-- For PostingType=Statistical
	UNION ALL
	(
		SELECT
			(case
				-- When the account is Expense/Revenue => we shall consider only the Year to Date amount
				when ev.AccountType in ('E', 'R') then ROW(fas.AmtAcctDr_YTD - fas.AmtAcctCr_YTD, fas.AmtAcctDr_YTD, fas.AmtAcctCr_YTD)::de_metas_acct.BalanceAmt
				-- For any other account => we consider from the beginning to Date amount
				else ROW(fas.AmtAcctDr - fas.AmtAcctCr, fas.AmtAcctDr, fas.AmtAcctCr)::de_metas_acct.BalanceAmt
			end) as Balance
		FROM Fact_Acct_Summary fas
		INNER JOIN C_ElementValue ev on (ev.C_ElementValue_ID=fas.Account_ID)
		WHERE true
			and $4='Y' -- p_IncludePostingTypeStatistical
			and fas.Account_ID=$1 -- p_Account_ID
			and fas.C_AcctSchema_ID=$2 -- p_C_AcctSchema_ID
			and fas.PostingType='S'
			and fas.PA_ReportCube_ID is null
			and fas.DateAcct <= $3 -- p_DateAcct
		ORDER BY fas.DateAcct DESC
		LIMIT 1
	)
	-- Default value:
	UNION ALL
	(
		SELECT ROW(0, 0, 0)::de_metas_acct.BalanceAmt
	)
) t
$BODY$
LANGUAGE sql STABLE;
----------------------------------------------------


drop function if exists public.Fact_Acct_EndingBalance(factLine Fact_Acct);
drop function if exists de_metas_acct.Fact_Acct_EndingBalance(factLine Fact_Acct);
create or replace function de_metas_acct.Fact_Acct_EndingBalance(factLine Fact_Acct)
RETURNS numeric AS
$BODY$
	SELECT acctBalance(($1).Account_ID, COALESCE(SUM(AmtAcctDr), 0), COALESCE(SUM(AmtAcctCr), 0))
	FROM (
		(
			SELECT
				(case when ev.AccountType in ('E', 'R') then fa.AmtAcctDr_YTD else fa.AmtAcctDr end) as AmtAcctDr
				, (case when ev.AccountType in ('E', 'R') then fa.AmtAcctCr_YTD else fa.AmtAcctCr end) as AmtAcctCr
			FROM Fact_Acct_Summary fa
			INNER JOIN C_ElementValue ev on (ev.C_ElementValue_ID=fa.Account_ID)
			WHERE true
				and fa.Account_ID=($1).Account_ID
				and fa.C_AcctSchema_ID=($1).C_AcctSchema_ID
				and fa.PostingType=($1).PostingType
				and fa.DateAcct = ($1).DateAcct - interval '1 day'
			ORDER BY fa.DateAcct DESC
			LIMIT 1
		)
		UNION ALL
		(
			select
				fa.AmtAcctDr_DTD
				, fa.AmtAcctCr_DTD
			from Fact_Acct_EndingBalance fa
			where true
				and fa.Fact_Acct_ID = ($1).Fact_Acct_ID
		)
	) t
	;
$BODY$
LANGUAGE sql STABLE;

-------------------------------------------
drop function if exists public.Fact_Acct_EndingBalance_RebuildAll();
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
	window facts_previous as (partition by fa.AD_Client_ID, fa.C_AcctSchema_ID, fa.PostingType, fa.Account_ID, fa.DateAcct order by Fact_Acct_ID)
	;
	
	GET DIAGNOSTICS v_CountInserted = ROW_COUNT;

	return ''||v_CountInserted||' rows inserted into Fact_Acct_EndingBalance';
end;
$BODY$
LANGUAGE plpgsql;

COMMENT ON FUNCTION de_metas_acct.Fact_Acct_EndingBalance_RebuildAll() IS 'Rebuilds Fact_Acct_EndingBalance.';
---------------------------

drop function if exists public.Fact_Acct_EndingBalance_UpdateForTag(p_ProcessingTag varchar);
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
		select distinct C_ElementValue_ID, C_AcctSchema_ID, PostingType, DateAcct
		from Fact_Acct_Log
		where ProcessingTag=p_ProcessingTag
	) log
	inner join Fact_Acct fa on (
		fa.Account_ID=log.C_ElementValue_ID
		and fa.C_AcctSchema_ID=log.C_AcctSchema_ID
		and fa.PostingType=log.PostingType
		-- and fa.AD_Client_ID=log.AD_Client_ID -- commented out because we don't have an index on this column
		and fa.DateAcct=log.DateAcct
	)
	window facts_previous as (partition by fa.AD_Client_ID, fa.C_AcctSchema_ID, fa.PostingType, fa.Account_ID, fa.DateAcct order by Fact_Acct_ID)
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

