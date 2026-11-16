-- "SEPA Export" process (AD_Process 540461), parameter SEPA_Protocol (AD_Process_Para 543343):
-- offer only the formats that match the pay selection's transaction type:
--   credit transfer (CT) -> the pain.001 versions
--   direct debit (DD)    -> pain.008.001.08 only (pain.008.003.02 is rejected by German banks for new exports)
-- AD_Val_Rule 540805 is used only by this parameter, so it is changed in place and renamed to match its new scope.
-- The default now depends on the transaction type as well; the process provides it (CT -> pain.001.001.09.ch.03, DD -> pain.008.001.08),
-- so the static credit-transfer default is removed from the parameter.

UPDATE AD_Val_Rule
SET Code        = '(''@PaySelectionTrxType/X@''=''CT'' AND AD_Ref_List.Value LIKE ''pain.001.%'') OR (''@PaySelectionTrxType/X@''=''DD'' AND AD_Ref_List.Value=''pain.008.001.08'')',
    Name        = 'SEPA_Protocol_By_PaySelectionTrxType',
    Description = 'SEPA formats that can be chosen for a new export of a pay selection with the given transaction type',
    Updated     = TO_TIMESTAMP('2026-11-06 17:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy   = 100
WHERE AD_Val_Rule_ID = 540805
;

UPDATE AD_Process_Para SET DefaultValue = NULL, Updated = TO_TIMESTAMP('2026-11-06 17:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Process_Para_ID = 543343
;
