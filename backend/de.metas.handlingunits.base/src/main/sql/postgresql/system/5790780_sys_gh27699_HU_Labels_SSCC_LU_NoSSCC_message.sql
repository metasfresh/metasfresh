-- gh#27699: Add AD_Message for SSCC label report when no HUs have SSCC18 attribute

-- AD_Message: HU_Labels_SSCC_LU.NoSSCC
INSERT INTO AD_Message (AD_Message_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Value, MsgText, MsgType, EntityType)
SELECT nextval('ad_message_seq'), 0, 0, 'Y', now(), 100, now(), 100,
       'HU_Labels_SSCC_LU.NoSSCC',
       'None of the selected Handling Units have an SSCC18 attribute. SSCC labels can only be printed for HUs that have been assigned an SSCC.',
       'E',
       'de.metas.handlingunits'
WHERE NOT EXISTS (SELECT 1 FROM AD_Message WHERE Value = 'HU_Labels_SSCC_LU.NoSSCC')
;

-- Translation: de_DE / de_CH
INSERT INTO AD_Message_Trl (AD_Message_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, IsTranslated, MsgText)
SELECT m.AD_Message_ID, 'de_DE', 0, 0, 'Y', now(), 100, now(), 100, 'Y',
       'Keine der ausgewählten Handling Units hat ein SSCC18-Merkmal. SSCC-Etiketten können nur für HUs gedruckt werden, denen eine SSCC zugewiesen wurde.'
FROM AD_Message m
WHERE m.Value = 'HU_Labels_SSCC_LU.NoSSCC'
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl t WHERE t.AD_Message_ID = m.AD_Message_ID AND t.AD_Language = 'de_DE')
;

INSERT INTO AD_Message_Trl (AD_Message_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, IsTranslated, MsgText)
SELECT m.AD_Message_ID, 'de_CH', 0, 0, 'Y', now(), 100, now(), 100, 'Y',
       'Keine der ausgewählten Handling Units hat ein SSCC18-Merkmal. SSCC-Etiketten können nur für HUs gedruckt werden, denen eine SSCC zugewiesen wurde.'
FROM AD_Message m
WHERE m.Value = 'HU_Labels_SSCC_LU.NoSSCC'
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl t WHERE t.AD_Message_ID = m.AD_Message_ID AND t.AD_Language = 'de_CH')
;

-- Update AD_Process description for HU_Labels_SSCC_LU (AD_Process_ID=540412)
-- English
UPDATE AD_Process_Trl
SET Description = 'Prints SSCC labels for selected LUs. HUs without an SSCC18 attribute are automatically skipped. If none of the selected HUs have an SSCC, an error is shown.',
    IsTranslated = 'Y',
    Updated = now(),
    UpdatedBy = 100
WHERE AD_Process_ID = 540412
  AND AD_Language = 'en_US'
;

UPDATE AD_Process base
SET Description = trl.Description, Updated = trl.Updated, UpdatedBy = trl.UpdatedBy
FROM AD_Process_Trl trl
WHERE trl.AD_Process_ID = base.AD_Process_ID
  AND trl.AD_Language = 'en_US'
  AND trl.AD_Language = getBaseLanguage()
  AND base.AD_Process_ID = 540412
;

-- German
UPDATE AD_Process_Trl
SET Description = 'Druckt SSCC-Etiketten für ausgewählte LUs. HUs ohne SSCC18-Merkmal werden automatisch übersprungen. Wenn keine der ausgewählten HUs eine SSCC hat, wird ein Fehler angezeigt.',
    IsTranslated = 'Y',
    Updated = now(),
    UpdatedBy = 100
WHERE AD_Process_ID = 540412
  AND AD_Language IN ('de_DE', 'de_CH')
;

UPDATE AD_Process base
SET Description = trl.Description, Updated = trl.Updated, UpdatedBy = trl.UpdatedBy
FROM AD_Process_Trl trl
WHERE trl.AD_Process_ID = base.AD_Process_ID
  AND trl.AD_Language IN ('de_DE', 'de_CH')
  AND trl.AD_Language = getBaseLanguage()
  AND base.AD_Process_ID = 540412
;
