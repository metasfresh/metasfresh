-- 2017-07-22T15:26:27.298
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2770,0,263,540029,547008,TO_TIMESTAMP('2017-07-22 15:26:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Währung',15,0,0,TO_TIMESTAMP('2017-07-22 15:26:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-22T15:27:35.406
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET UIStyle='',Updated=TO_TIMESTAMP('2017-07-22 15:27:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540029
;

-- 2017-07-22T15:28:02.007
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2761,0,263,540028,547009,TO_TIMESTAMP('2017-07-22 15:28:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mandant',15,0,0,TO_TIMESTAMP('2017-07-22 15:28:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-22T15:28:41.375
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2779,0,263,540027,547010,TO_TIMESTAMP('2017-07-22 15:28:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Beleg Nr.',15,0,0,TO_TIMESTAMP('2017-07-22 15:28:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-22T15:29:11.491
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=540797
;

-- 2017-07-22T15:29:15.603
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=540802
;

-- 2017-07-22T15:29:18.172
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=540801
;

-- 2017-07-22T15:29:20.551
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=540796
;

-- 2017-07-22T15:29:23.744
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=540030
;

-- 2017-07-22T15:30:13.373
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,263,540392,TO_TIMESTAMP('2017-07-22 15:30:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','Mahnung',30,TO_TIMESTAMP('2017-07-22 15:30:13','YYYY-MM-DD HH24:MI:SS'),100,'dunning')
;

-- 2017-07-22T15:30:13.378
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540392 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-07-22T15:30:18.763
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540529,540392,TO_TIMESTAMP('2017-07-22 15:30:18','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-07-22 15:30:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-22T15:30:28.179
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540529,540908,TO_TIMESTAMP('2017-07-22 15:30:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','dunning',10,TO_TIMESTAMP('2017-07-22 15:30:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-22T15:30:41.472
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53258,0,263,540908,547011,TO_TIMESTAMP('2017-07-22 15:30:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mahnstufe',10,0,0,TO_TIMESTAMP('2017-07-22 15:30:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-22T15:30:56.760
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53257,0,263,540908,547012,TO_TIMESTAMP('2017-07-22 15:30:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mahnkarenz',20,0,0,TO_TIMESTAMP('2017-07-22 15:30:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-22T15:31:16.947
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=540800
;

-- 2017-07-22T15:31:19.920
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=540799
;

-- 2017-07-22T15:31:56.800
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-07-22 15:31:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547011
;

-- 2017-07-22T15:31:58.044
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-07-22 15:31:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547012
;

