
drop index if exists fact_Acct_webui_perf;

create index fact_Acct_webui_perf
ON fact_Acct (DateAcct DESC NULLS LAST, Fact_Acct_ID ASC NULLS LAST);