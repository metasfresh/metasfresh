drop index fact_acct_period if exists;
drop index fact_acct_tax_account_info  if exists;
create index fact_acct_period on fact_acct (c_period_id);
create index fact_acct_tax_account_info on fact_acct (DateAcct, VatCode, C_Tax_ID);