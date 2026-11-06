-- AD_Val_Rule 540805 (SEPA_Protocol_CreditTransfer) was only used by the SEPA_Protocol parameter of the "SEPA Export" process,
-- which now uses 540812 (SEPA_Protocol_By_PaySelectionTrxType); no column, field or other parameter references it.

UPDATE AD_Val_Rule SET IsActive = 'N', Updated = TO_TIMESTAMP('2026-11-06 17:20:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Val_Rule_ID = 540805
;
