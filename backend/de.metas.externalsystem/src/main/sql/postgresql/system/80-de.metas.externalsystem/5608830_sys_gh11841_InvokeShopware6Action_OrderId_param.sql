
-- 2021-10-11T05:32:05.003Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,584804,542118,10,'OrderId',TO_TIMESTAMP('2021-10-11 08:32:04','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem',14,'','Y','N','Y','N','N','N','OrderId',50,TO_TIMESTAMP('2021-10-11 08:32:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-11T05:32:05.015Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542118 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-10-11T15:57:16.888Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET Description='If set, then only the Shopware6-Order with the respective OrderId is retrieved and the "Since" parameter is ignored.',Updated=TO_TIMESTAMP('2021-10-11 18:57:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_Para_ID=542118
;

-- 2021-10-11T15:57:24.967Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET Description='Wenn gesetzt, dann wird nur die Shopware6-Order mit der angegebenen OrderId abgerufen und der "Seit" Parameter wird ignoriert.',Updated=TO_TIMESTAMP('2021-10-11 18:57:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_Para_ID=542118
;

-- 2021-10-11T15:57:27.694Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET Description='Wenn gesetzt, dann wird nur die Shopware6-Order mit der angegebenen OrderId abgerufen und der "Seit" Parameter wird ignoriert.',Updated=TO_TIMESTAMP('2021-10-11 18:57:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_Para_ID=542118
;

-- 2021-10-11T15:57:31.962Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET Description='Wenn gesetzt, dann wird nur die Shopware6-Order mit der angegebenen OrderId abgerufen und der "Seit" Parameter wird ignoriert.',Updated=TO_TIMESTAMP('2021-10-11 18:57:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_Para_ID=542118
;

-- 2021-10-11T15:57:42.514Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET Description='Wenn gesetzt, dann wird nur die Shopware6-Order mit der angegebenen OrderId abgerufen und der "Seit" Parameter wird ignoriert.',Updated=TO_TIMESTAMP('2021-10-11 18:57:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542118
;

