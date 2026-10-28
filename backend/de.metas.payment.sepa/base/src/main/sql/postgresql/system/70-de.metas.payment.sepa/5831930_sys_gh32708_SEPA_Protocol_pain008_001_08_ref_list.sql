-- Adds the pain.008.001.08 direct-debit version to the SEPA_Protocol list (AD_Reference 541110).
-- AD_Val_Rule 540805 (SEPA_Protocol_CreditTransfer, Code "AD_Ref_List.Value LIKE 'pain.001.%'") already excludes this value from the credit-transfer export parameter, so it is left unchanged.

INSERT INTO AD_Ref_List (AD_Client_ID, AD_Org_ID, AD_Ref_List_ID /*From ID Server*/, AD_Reference_ID, Created, CreatedBy, EntityType, IsActive, Name, Updated, UpdatedBy, Value, ValueName)
VALUES (0, 0, 544383 /*From ID Server*/, 541110, TO_TIMESTAMP('2026-10-28 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'de.metas.payment.sepa', 'Y', 'Direct Debit pain.008.001.08', TO_TIMESTAMP('2026-10-28 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'pain.008.001.08', 'Direct Debit pain.008.001.08')
;

INSERT INTO AD_Ref_List_Trl (AD_Language, AD_Ref_List_ID, Description, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Ref_List_ID = 544383
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Ref_List_ID = t.AD_Ref_List_ID)
;

-- The label is a technical name and is identical in de_CH, de_DE and en_US
UPDATE AD_Ref_List_Trl SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-28 10:00:10', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Ref_List_ID = 544383 AND AD_Language IN ('de_CH', 'de_DE', 'en_US')
;
