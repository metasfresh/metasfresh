-- 2019-11-26T16:02:13.662Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Rüstliste erfassen',Updated=TO_TIMESTAMP('2019-11-26 17:02:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=541179
;

-- 2019-11-26T16:04:28.255Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2019-11-26 17:04:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=562878
;

-- 2019-11-26T16:20:00.569Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@Action/''XX''@=''SU''',Updated=TO_TIMESTAMP('2019-11-26 17:20:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=540392
;

-- 2019-11-26T16:20:20.660Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@Action/''XX''@=''ST''', ReadOnlyLogic='@Action/''XX''@!''ST''',Updated=TO_TIMESTAMP('2019-11-26 17:20:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=540393
;

-- 2019-11-26T16:20:53.253Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Action/''XX''@=''ST''',Updated=TO_TIMESTAMP('2019-11-26 17:20:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=550547
;

-- 2019-11-26T16:21:00.141Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Action/''XX''@=''SU''',Updated=TO_TIMESTAMP('2019-11-26 17:21:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=550542
;

