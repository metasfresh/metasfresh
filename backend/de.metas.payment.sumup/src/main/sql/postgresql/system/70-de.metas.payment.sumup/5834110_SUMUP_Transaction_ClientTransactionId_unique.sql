-- One SumUp transaction per SumUp client_transaction_id: guards against two concurrent imports of the same accepted checkout.
-- SUMUP_ClientTransactionId is mandatory, so no partial index is needed.
CREATE UNIQUE INDEX sumup_transaction_clienttransactionid_uq ON SUMUP_Transaction (SUMUP_ClientTransactionId)
;
