

DROP INDEX IF EXISTS fact_acct_summary_key;

CREATE UNIQUE INDEX fact_acct_summary_key
  ON fact_acct_summary
  USING btree
  (ad_client_id, account_id, c_acctschema_id, postingtype COLLATE pg_catalog."default", c_period_id, dateacct, ad_org_id)
  WHERE pa_reportcube_id IS NULL;
