
-- 2021-09-08T16:10:30.728Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,584804,542089,10,'OrderNo',TO_TIMESTAMP('2021-09-08 18:10:30','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem',255,'Y','N','Y','N','N','N','OrderNo',40,TO_TIMESTAMP('2021-09-08 18:10:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-08T16:10:30.732Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542089 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-09-08T16:12:25.345Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET Description='Wenn gesetzt, dann wird nur die Shopware6-Order mit der angegebenen OrderNo abgerufen und der "Seit" Parameter wird ignoriert.',Updated=TO_TIMESTAMP('2021-09-08 18:12:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542089
;

-- 2021-09-08T16:12:32.551Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET Description='Wenn gesetzt, dann wird nur die Shopware6-Order mit der angegebenen OrderNo abgerufen und der "Seit" Parameter wird ignoriert.', IsTranslated='Y',Updated=TO_TIMESTAMP('2021-09-08 18:12:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_Para_ID=542089
;

-- 2021-09-08T16:12:38.960Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET Description='Wenn gesetzt, dann wird nur die Shopware6-Order mit der angegebenen OrderNo abgerufen und der "Seit" Parameter wird ignoriert.', IsTranslated='Y',Updated=TO_TIMESTAMP('2021-09-08 18:12:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_Para_ID=542089
;

-- 2021-09-08T16:13:28.471Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET Description='If set, then only the Shopware6-Order with the respective OrderNo is retrieved and the "Since" parameter is ignored.', IsTranslated='Y',Updated=TO_TIMESTAMP('2021-09-08 18:13:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_Para_ID=542089
;

-- 2021-09-08T16:21:47.360Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Since',Updated=TO_TIMESTAMP('2021-09-08 18:21:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541941
;
