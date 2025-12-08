drop index if exists fact_acct_openitemkey;
create index fact_acct_openitemkey on fact_acct(openitemkey);
