-- 2020-02-26T09:48:16.107Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Updated, UpdatedBy, Value)
VALUES (0, 544967, 0, TO_TIMESTAMP('2020-02-26 11:48:15', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'Confirm', 'I', TO_TIMESTAMP('2020-02-26 11:48:15', 'YYYY-MM-DD HH24:MI:SS'), 100, 'de.metas.popupinfo.popupTitle')
;

-- 2020-02-26T09:48:16.114Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language,
       t.AD_Message_ID,
       t.MsgText,
       t.MsgTip,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy
FROM AD_Language l,
     AD_Message t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N')
  AND t.AD_Message_ID = 544967
  AND NOT EXISTS(SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = t.AD_Message_ID)
;

-- 2020-02-26T09:54:41.297Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl
SET MsgText='Bestätigen',
    Updated=TO_TIMESTAMP('2020-02-26 11:54:41', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Language = 'de_CH'
  AND AD_Message_ID = 544967
;

-- 2020-02-26T09:54:44.479Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl
SET MsgText='Bestätigen',
    Updated=TO_TIMESTAMP('2020-02-26 11:54:44', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Language = 'nl_NL'
  AND AD_Message_ID = 544967
;

-- 2020-02-26T09:54:47.278Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl
SET IsTranslated='Y',
    Updated=TO_TIMESTAMP('2020-02-26 11:54:47', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Language = 'en_US'
  AND AD_Message_ID = 544967
;

-- 2020-02-26T09:54:53.814Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message
SET MsgText='Bestätigen',
    Updated=TO_TIMESTAMP('2020-02-26 11:54:53', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Message_ID = 544967
;
