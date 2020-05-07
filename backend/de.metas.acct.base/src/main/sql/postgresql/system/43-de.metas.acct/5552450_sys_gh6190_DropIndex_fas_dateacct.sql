DROP INDEX fas_dateacct;

/*
From my testing on our biggest instance available, it seems that `de_metas_acct.acctBalanceToDate` runs about 10-15% faster without this index.

---

For reference, the index looks like this:

CREATE INDEX fas_dateacct ON fact_acct_summary (dateacct);
 */
