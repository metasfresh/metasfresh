DROP INDEX if exists fact_acct_account;
-- old index: CREATE INDEX fact_acct_account ON fact_acct (ad_client_id , ad_org_id , c_acctschema_id , account_id );
CREATE INDEX fact_acct_account ON fact_acct (c_acctschema_id, account_id);


