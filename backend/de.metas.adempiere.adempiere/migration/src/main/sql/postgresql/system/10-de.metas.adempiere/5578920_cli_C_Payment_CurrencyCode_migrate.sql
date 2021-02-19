DROP TABLE IF EXISTS tmp_payment_currencycode_to_migrate
;

CREATE TEMPORARY TABLE tmp_payment_currencycode_to_migrate AS
SELECT p.c_payment_id,
       bsl.currencyrate,
       acs.c_currency_id AS source_currency_id
FROM c_bankstatementline bsl
         INNER JOIN c_payment p ON p.c_payment_id = bsl.c_payment_id
         INNER JOIN ad_clientInfo ci ON ci.ad_client_id = bsl.ad_client_id
         INNER JOIN c_acctschema acs ON acs.c_acctschema_id = ci.c_acctschema1_id
WHERE bsl.currencyrate IS NOT NULL
  AND bsl.currencyrate <> 0
;

CREATE UNIQUE INDEX ON tmp_payment_currencycode_to_migrate (c_payment_id)
;

UPDATE c_payment p
SET currencyrate=t.currencyrate, source_currency_id=t.source_currency_id
FROM tmp_payment_currencycode_to_migrate t
WHERE t.c_payment_id = p.c_payment_id
;

SELECT "de_metas_acct".fact_acct_unpost('C_Payment', t.c_payment_id)
FROM tmp_payment_currencycode_to_migrate t
;

SELECT "de_metas_acct".fact_acct_unpost('C_AllocationHdr', al.c_allocationhdr_id)
FROM tmp_payment_currencycode_to_migrate t
         INNER JOIN c_allocationline al ON t.c_payment_id = al.c_payment_id
;

