-- 2018-07-13T13:22:57.597
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,CreatedBy,IsReadOnly,EntityType,AD_Field_ID,AD_Column_ID,AD_Org_ID,Name,UpdatedBy,DisplayLength,Created,Updated) VALUES (496,'Y','N','N','N','N',0,'Y',100,'N','D',565102,560677,0,'Passwort in Mengenmeldung App',100,255,TO_TIMESTAMP('2018-07-13 13:22:57','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-07-13 13:22:57','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-07-13T13:22:57.599
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=565102 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-07-13T13:24:42.173
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsMFProcurementUser/N@=Y', IsSameLine='Y', IsDisplayedGrid='N', DisplayLength=10, SeqNo=145,Updated=TO_TIMESTAMP('2018-07-13 13:24:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=565102
;

-- 2018-07-13T13:24:50.331
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET EntityType='de.metas.procurement',Updated=TO_TIMESTAMP('2018-07-13 13:24:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=565102
;

-- 2018-07-13T13:54:20.652
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsEncrypted='Y',Updated=TO_TIMESTAMP('2018-07-13 13:54:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=565102
;

