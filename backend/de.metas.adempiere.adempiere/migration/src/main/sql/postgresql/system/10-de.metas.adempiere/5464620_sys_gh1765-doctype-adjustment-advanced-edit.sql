-- 2017-06-08T18:01:28.498
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,167,540273,TO_TIMESTAMP('2017-06-08 18:01:28','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-06-08 18:01:28','YYYY-MM-DD HH24:MI:SS'),100,'advanced edit')
;

-- 2017-06-08T18:01:28.515
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540273 AND NOT EXISTS (SELECT * FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-06-08T18:01:32.152
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540372,540273,TO_TIMESTAMP('2017-06-08 18:01:32','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-06-08 18:01:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-08T18:01:50.726
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540372,540636,TO_TIMESTAMP('2017-06-08 18:01:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','advanced edit',10,TO_TIMESTAMP('2017-06-08 18:01:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-08T18:02:18.379
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2581,0,167,540636,545564,TO_TIMESTAMP('2017-06-08 18:02:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Document Sub Type',10,0,0,TO_TIMESTAMP('2017-06-08 18:02:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-08T18:03:52.756
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET AD_UI_Column_ID=540372, SeqNo=20,Updated=TO_TIMESTAMP('2017-06-08 18:03:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540402
;

-- 2017-06-08T18:04:21.645
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545564
;

-- 2017-06-08T18:04:26.284
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=540636
;

-- 2017-06-08T18:04:30.252
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=10,Updated=TO_TIMESTAMP('2017-06-08 18:04:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540402
;

-- 2017-06-08T18:12:52.133
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET UIStyle='primary',Updated=TO_TIMESTAMP('2017-06-08 18:12:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540402
;

-- 2017-06-08T18:13:23.190
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Section SET Name='Erweitert',Updated=TO_TIMESTAMP('2017-06-08 18:13:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Section_ID=540273
;

-- 2017-06-08T18:13:23.191
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Section_Trl SET IsTranslated='N' WHERE AD_UI_Section_ID=540273
;

-- 2017-06-08T18:13:30.779
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Section_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-06-08 18:13:30','YYYY-MM-DD HH24:MI:SS'),Name='Advanced' WHERE AD_UI_Section_ID=540273 AND AD_Language='en_US'
;

-- 2017-06-08T18:13:35.036
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Section_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-06-08 18:13:35','YYYY-MM-DD HH24:MI:SS'),Name='Erweitert' WHERE AD_UI_Section_ID=540273 AND AD_Language='de_CH'
;

