-- Adds the "SEPA Protocol" parameter to the "SEPA Export" process (AD_Process 540461);
-- the user chooses between pain.001.001.09.ch.03 (default) and pain.001.001.03.ch.02.
-- Reuses AD_Element 577573 (SEPA_Protocol).

-- Validation rule: only the credit-transfer versions; direct debit (pain.008) is chosen automatically
INSERT INTO AD_Val_Rule (AD_Client_ID, AD_Org_ID, AD_Val_Rule_ID /*From ID Server*/, Code, Created, CreatedBy, EntityType, IsActive, Name, Type, Updated, UpdatedBy)
VALUES (0, 0, 540805 /*From ID Server*/, 'AD_Ref_List.Value LIKE ''pain.001.%''', TO_TIMESTAMP('2026-10-12 11:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'de.metas.payment.sepa', 'Y', 'SEPA_Protocol_CreditTransfer', 'S', TO_TIMESTAMP('2026-10-12 11:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- Description/Help of the element (translations first, then propagation)
UPDATE AD_Element_Trl SET Description = 'Version of the credit-transfer file. Direct debit always uses pain.008.003.02.', Help = 'Version of the credit-transfer file. Direct debit always uses pain.008.003.02.', IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-12 11:01:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 577573 AND AD_Language = 'en_US'
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(577573, 'en_US')
;

UPDATE AD_Element_Trl SET Description = 'Version der Überweisungsdatei. Lastschriften verwenden immer pain.008.003.02.', Help = 'Version der Überweisungsdatei. Lastschriften verwenden immer pain.008.003.02.', IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-12 11:01:10', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 577573 AND AD_Language = 'de_CH'
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(577573, 'de_CH')
;

UPDATE AD_Element_Trl SET Description = 'Version der Überweisungsdatei. Lastschriften verwenden immer pain.008.003.02.', Help = 'Version der Überweisungsdatei. Lastschriften verwenden immer pain.008.003.02.', IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-12 11:01:20', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 577573 AND AD_Language = 'de_DE'
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(577573, 'de_DE')
;
/* DDL */ select update_ad_element_on_ad_element_trl_update(577573, 'de_DE')
;

-- Process parameter, listed after ReferenceAsEndToEndId (SeqNo 10)
INSERT INTO AD_Process_Para (AD_Client_ID, AD_Element_ID, AD_Org_ID, AD_Process_ID, AD_Process_Para_ID /*From ID Server*/, AD_Reference_ID, AD_Reference_Value_ID, AD_Val_Rule_ID, ColumnName, Created, CreatedBy, DefaultValue, EntityType, FieldLength, IsActive, IsAutocomplete, IsCentrallyMaintained, IsEncrypted, IsMandatory, IsRange, Name, SeqNo, Updated, UpdatedBy)
VALUES (0, 577573, 0, 540461, 543343 /*From ID Server*/, 17, 541110, 540805, 'SEPA_Protocol', TO_TIMESTAMP('2026-10-12 11:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'pain.001.001.09.ch.03', 'de.metas.payment.sepa', 30, 'Y', 'N', 'Y', 'N', 'Y', 'N', 'SEPA Protocol', 20, TO_TIMESTAMP('2026-10-12 11:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

INSERT INTO AD_Process_Para_Trl (AD_Language, AD_Process_Para_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Process_Para_ID = 543343
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_Para_ID = t.AD_Process_Para_ID)
;

-- Fill Name/Description/Help of the parameter from the element
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(577573, 'en_US')
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(577573, 'de_CH')
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(577573, 'de_DE')
;
/* DDL */ select update_Process_Para_Translation_From_AD_Element(577573)
;
