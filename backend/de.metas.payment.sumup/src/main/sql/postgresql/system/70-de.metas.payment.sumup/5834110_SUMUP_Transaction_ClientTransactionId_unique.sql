-- One SumUp transaction per SumUp client_transaction_id: guards against two concurrent imports of the same accepted checkout.
-- SUMUP_ClientTransactionId is mandatory, so no "IS NOT NULL" partial index is needed. The index is also not partial on
-- IsActive: a SumUp transaction is one row regardless of IsActive, and the repository lookup by client transaction id has no
-- active filter.
CREATE UNIQUE INDEX sumup_transaction_clienttransactionid_uq ON SUMUP_Transaction (SUMUP_ClientTransactionId)
;
