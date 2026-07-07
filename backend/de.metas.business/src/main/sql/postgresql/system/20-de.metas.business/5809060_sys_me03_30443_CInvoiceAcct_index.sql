-- F01010.4 — index on C_Invoice_Acct to avoid sequential scans.
-- Review (metas-ts) on Fact_Acct_Transactions_View.IsAccountOverridden: the correlated
-- EXISTS probes C_Invoice_Acct per Fact_Acct row on (C_Invoice_ID, C_AcctSchema_ID,
-- C_ElementValue_ID); C_Invoice_Acct previously had only its PK index -> seq scan per row.
-- This composite index lets the probe be an index lookup (most invoices have 0 override rows,
-- so the no-match case becomes a cheap index probe instead of a full scan).
-- Also benefits InvoiceAcctRuleMatcher / createOrUpdateLineOverride lookups by invoice.

CREATE INDEX IF NOT EXISTS C_Invoice_Acct_Invoice_Schema_Acct_idx
    ON C_Invoice_Acct (C_Invoice_ID, C_AcctSchema_ID, C_ElementValue_ID);
