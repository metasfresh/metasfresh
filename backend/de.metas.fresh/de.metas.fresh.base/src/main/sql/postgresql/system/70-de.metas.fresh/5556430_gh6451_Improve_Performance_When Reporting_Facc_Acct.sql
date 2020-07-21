drop index if exists fact_acct_period ;
create index fact_acct_period on fact_acct (c_period_id);

drop index if exists fact_acct_tax_account_info  ;
create index fact_acct_tax_account_info on fact_acct (DateAcct, account_id, VatCode);