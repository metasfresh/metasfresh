-- "SEPA Export" process (AD_Process 540461), parameter SEPA_Protocol (AD_Process_Para 543343):
-- offer only the formats that match the pay selection's transaction type:
--   credit transfer (CT) -> the pain.001 versions
--   direct debit (DD)    -> pain.008.001.08 only (pain.008.003.02 is rejected by German banks for new exports)
-- The default now depends on the transaction type as well; the process provides it (CT -> pain.001.001.09.ch.03, DD -> pain.008.001.08),
-- so the static credit-transfer default is removed from the parameter.
-- The previous rule 540805 (SEPA_Protocol_CreditTransfer) is credit-transfer only by name and content, so it is left as it is and a new rule is used.

INSERT INTO AD_Val_Rule (AD_Client_ID, AD_Org_ID, AD_Val_Rule_ID, Code, Created, CreatedBy, Description, EntityType, IsActive, Name, Type, Updated, UpdatedBy)
VALUES (0, 0, 540812 /*From ID Server*/,
        '(''@PaySelectionTrxType/X@''=''CT'' AND AD_Ref_List.Value LIKE ''pain.001.%'') OR (''@PaySelectionTrxType/X@''=''DD'' AND AD_Ref_List.Value=''pain.008.001.08'')',
        TO_TIMESTAMP('2026-11-06 17:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'SEPA formats that can be chosen for a new export of a pay selection with the given transaction type',
        'de.metas.payment.sepa', 'Y', 'SEPA_Protocol_By_PaySelectionTrxType', 'S',
        TO_TIMESTAMP('2026-11-06 17:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

UPDATE AD_Process_Para SET AD_Val_Rule_ID = 540812, DefaultValue = NULL, Updated = TO_TIMESTAMP('2026-11-06 17:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Process_Para_ID = 543343
;
