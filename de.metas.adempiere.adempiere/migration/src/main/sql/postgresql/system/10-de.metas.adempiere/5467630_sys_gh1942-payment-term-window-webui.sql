-- 2017-07-11T18:17:08.226
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET UIStyle='primary',Updated=TO_TIMESTAMP('2017-07-11 18:17:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540546
;

-- 2017-07-11T18:17:39.798
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Section SET Name='Beschreibung',Updated=TO_TIMESTAMP('2017-07-11 18:17:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Section_ID=540233
;

-- 2017-07-11T18:17:51.091
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Section_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-11 18:17:51','YYYY-MM-DD HH24:MI:SS'),Name='Description' WHERE AD_UI_Section_ID=540233 AND AD_Language='en_US'
;

-- 2017-07-11T18:18:39.524
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Section_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-11 18:18:39','YYYY-MM-DD HH24:MI:SS'),Name='' WHERE AD_UI_Section_ID=540233 AND AD_Language='en_US'
;

-- 2017-07-11T18:18:45.219
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Section SET Name='',Updated=TO_TIMESTAMP('2017-07-11 18:18:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Section_ID=540233
;

-- 2017-07-11T18:19:01.065
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,184,540351,TO_TIMESTAMP('2017-07-11 18:19:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','Beschreibung',30,TO_TIMESTAMP('2017-07-11 18:19:00','YYYY-MM-DD HH24:MI:SS'),100,'description')
;

-- 2017-07-11T18:19:01.067
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540351 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-07-11T18:19:18.411
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Section_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-11 18:19:18','YYYY-MM-DD HH24:MI:SS'),Name='Description',Description='',IsTranslated='Y' WHERE AD_UI_Section_ID=540351 AND AD_Language='en_US'
;

-- 2017-07-11T18:19:58.642
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545055
;

-- 2017-07-11T18:19:58.664
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545056
;

-- 2017-07-11T18:20:02.989
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=540547
;

-- 2017-07-11T18:20:11.314
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540477,540351,TO_TIMESTAMP('2017-07-11 18:20:11','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-07-11 18:20:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-11T18:20:20.486
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540477,540826,TO_TIMESTAMP('2017-07-11 18:20:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','description',10,TO_TIMESTAMP('2017-07-11 18:20:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-11T18:20:37.086
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1067,0,184,540826,546529,TO_TIMESTAMP('2017-07-11 18:20:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Beschreibung',10,0,0,TO_TIMESTAMP('2017-07-11 18:20:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-11T18:20:48.797
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3080,0,184,540826,546530,TO_TIMESTAMP('2017-07-11 18:20:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Notiz',20,0,0,TO_TIMESTAMP('2017-07-11 18:20:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-11T18:21:41.535
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-07-11 18:21:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546529
;

-- 2017-07-11T18:21:42.875
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-07-11 18:21:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546530
;

-- 2017-07-11T18:23:28.171
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Name auf Rechnung',Updated=TO_TIMESTAMP('2017-07-11 18:23:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=543389
;

-- 2017-07-11T18:23:49.197
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Name auf Rechnung', PrintName='Name auf Rechnung',Updated=TO_TIMESTAMP('2017-07-11 18:23:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=540650
;

-- 2017-07-11T18:23:49.201
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Name_Invoice', Name='Name auf Rechnung', Description='Alphanumeric identifier of the entity', Help='The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.' WHERE AD_Element_ID=540650
;

-- 2017-07-11T18:23:49.212
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Name_Invoice', Name='Name auf Rechnung', Description='Alphanumeric identifier of the entity', Help='The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.', AD_Element_ID=540650 WHERE UPPER(ColumnName)='NAME_INVOICE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-07-11T18:23:49.214
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Name_Invoice', Name='Name auf Rechnung', Description='Alphanumeric identifier of the entity', Help='The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.' WHERE AD_Element_ID=540650 AND IsCentrallyMaintained='Y'
;

-- 2017-07-11T18:23:49.215
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Name auf Rechnung', Description='Alphanumeric identifier of the entity', Help='The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=540650) AND IsCentrallyMaintained='Y'
;

-- 2017-07-11T18:23:49.227
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Name auf Rechnung', Name='Name auf Rechnung' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=540650)
;

-- 2017-07-11T18:23:57.683
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-11 18:23:57','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=540650 AND AD_Language='en_GB'
;

-- 2017-07-11T18:23:57.702
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(540650,'en_GB') 
;

