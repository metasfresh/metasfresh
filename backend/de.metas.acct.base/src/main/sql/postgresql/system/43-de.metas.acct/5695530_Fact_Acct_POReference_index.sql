drop index if exists fact_acct_poreference;
create index fact_acct_poreference on fact_acct(poreference);
