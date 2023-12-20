-- Name: Fact_Acct_Transactions_View
-- avoid duplicate key ad_reference_id 540773 and 541718 on after_migration
UPDATE AD_Reference_Trl trl SET Name='Fact_Acct_Transactions_View' WHERE AD_Reference_ID=540773
;

