drop table if exists TMP_Fact_Acct_PerDateAcct;
create temporary table TMP_Fact_Acct_PerDateAcct as
select
	fa.AD_Client_ID
	, fa.Account_ID
	, fa.C_AcctSchema_ID
	, fa.PostingType
	, p.C_Period_ID, p.C_Year_ID
	, fa.DateAcct
	-- Aggregated amounts
	, COALESCE(SUM(AmtAcctDr),0) as AmtAcctDr
	, COALESCE(SUM(AmtAcctCr),0) as AmtAcctCr
	, COALESCE(SUM(Qty), 0) as Qty
from Fact_Acct fa
left outer join C_Period p on (p.C_Period_ID=fa.C_Period_ID)
group by
fa.AD_Client_ID
, p.C_Period_ID
, fa.DateAcct
, fa.C_AcctSchema_ID
, fa.PostingType
, fa.Account_ID
;





-- truncate table Fact_Acct_Summary;
delete from Fact_Acct_Summary where PA_ReportCube_ID is null;

insert into Fact_Acct_Summary
(
	AD_Client_ID
	, AD_Org_ID
	, Account_ID
	, C_AcctSchema_ID
	, PostingType
	, C_Period_ID, C_Year_ID
	, DateAcct
	, PA_ReportCube_ID
	-- Aggregated amounts: (beginning) to Date
	, AmtAcctDr
	, AmtAcctCr
	, Qty
	-- Aggregated amounts: Year to Date
	, AmtAcctDr_YTD
	, AmtAcctCr_YTD
	-- Standard columns
	, Created
	, CreatedBy
	, Updated
	, UpdatedBy
	, IsActive
)
select
	fa.AD_Client_ID
	, 0 as AD_Org_ID
	, fa.Account_ID
	, fa.C_AcctSchema_ID
	, fa.PostingType
	, fa.C_Period_ID, fa.C_Year_ID
	, fa.DateAcct
	, null as PA_ReportCube_ID
	-- Aggregated amounts: (beginning) to Date
	, SUM(AmtAcctDr) over facts_ToDate as AmtAcctDr
	, SUM(AmtAcctCr) over facts_ToDate as AmtAcctCr
	, SUM(Qty) over facts_ToDate as Qty
	-- Aggregated amounts: Year to Date
	, SUM(AmtAcctDr) over facts_YearToDate as AmtAcctDr_YTD
	, SUM(AmtAcctCr) over facts_YearToDate as AmtAcctCr_YTD
	-- Standard columns
	, now() as Created
	, 0 as CreatedBy
	, now() as Updated
	, 0 as UpdatedBy
	, 'Y' as IsActive
from TMP_Fact_Acct_PerDateAcct fa
window
	facts_ToDate as (partition by fa.AD_Client_ID, fa.C_AcctSchema_ID, fa.PostingType, fa.Account_ID order by fa.DateAcct)
	, facts_YearToDate as (partition by fa.AD_Client_ID, fa.C_AcctSchema_ID, fa.PostingType, fa.Account_ID, fa.C_Year_ID order by fa.DateAcct)
;

