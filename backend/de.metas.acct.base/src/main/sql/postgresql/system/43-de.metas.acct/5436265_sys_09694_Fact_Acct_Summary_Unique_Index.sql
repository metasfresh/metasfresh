drop index if exists Fact_Acct_Summary_Key;
create unique index Fact_Acct_Summary_Key on Fact_Acct_Summary (
	AD_Client_ID
	, Account_ID
	, C_AcctSchema_ID
	, PostingType
	, C_Period_ID
	, DateAcct
)
where PA_ReportCube_ID is null;
