DROP INDEX IF EXISTS fact_acct_AccountConceptualName
;

CREATE INDEX fact_acct_AccountConceptualName ON Fact_Acct (AccountConceptualName)
;

