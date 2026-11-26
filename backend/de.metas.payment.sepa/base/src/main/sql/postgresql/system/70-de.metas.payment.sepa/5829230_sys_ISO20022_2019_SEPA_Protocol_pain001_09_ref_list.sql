-- Adds the pain.001.001.09.ch.03 credit-transfer version to the SEPA_Protocol list (AD_Reference 541110).

INSERT INTO AD_Ref_List (AD_Client_ID, AD_Org_ID, AD_Ref_List_ID /*From ID Server*/, AD_Reference_ID, Created, CreatedBy, EntityType, IsActive, Name, Updated, UpdatedBy, Value, ValueName)
VALUES (0, 0, 544377 /*From ID Server*/, 541110, TO_TIMESTAMP('2026-10-12 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'de.metas.payment.sepa', 'Y', 'Credit Transfer (pain.001.001.09.ch.03)', TO_TIMESTAMP('2026-10-12 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'pain.001.001.09.ch.03', 'Credit Transfer (pain.001.001.09.ch.03)')
;

INSERT INTO AD_Ref_List_Trl (AD_Language, AD_Ref_List_ID, Description, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Ref_List_ID = 544377
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Ref_List_ID = t.AD_Ref_List_ID)
;

-- The label is a technical name and is identical in de_CH, de_DE and en_US
UPDATE AD_Ref_List_Trl SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-12 10:00:10', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Ref_List_ID = 544377 AND AD_Language IN ('de_CH', 'de_DE', 'en_US')
;
