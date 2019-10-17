-- 2019-10-17T08:08:50.548Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='@SQL=SELECT C_DocType_ID from C_DocType WHERE C_DocType.DocBaseType = ''MMI'' AND C_DocType. DocSubType = ''IUI''',Updated=TO_TIMESTAMP('2019-10-17 11:08:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540392
;

-- 2019-10-17T08:09:59.621Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DefaultValue='C_DocType.DocBaseType = ''MMI'' AND C_DocType. DocSubType = ''IUI''',Updated=TO_TIMESTAMP('2019-10-17 11:09:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=10969
;

-- 2019-10-17T08:11:09.992Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET AD_Column_ID=3563,Updated=TO_TIMESTAMP('2019-10-17 11:11:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=683
;

-- 2019-10-17T08:11:30.350Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsActive='N',Updated=TO_TIMESTAMP('2019-10-17 11:11:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541763
;

-- 2019-10-17T08:11:38.271Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsActive='N',Updated=TO_TIMESTAMP('2019-10-17 11:11:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=53134
;

-- 2019-10-17T08:12:16.885Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='C_DocType.DocBaseType = ''MMI'' AND C_DocType. DocSubType = ''IUI''',Updated=TO_TIMESTAMP('2019-10-17 11:12:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540392
;

-- 2019-10-17T08:12:34.574Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DefaultValue='@SQL=SELECT C_DocType_ID from C_DocType WHERE C_DocType.DocBaseType = ''MMI'' AND C_DocType. DocSubType = ''IUI''',Updated=TO_TIMESTAMP('2019-10-17 11:12:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=10969
;

-- 2019-10-17T10:11:26.082Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsActive='Y',Updated=TO_TIMESTAMP('2019-10-17 13:11:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=53134
;

-- 2019-10-17T10:12:27.875Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsActive='N',Updated=TO_TIMESTAMP('2019-10-17 13:12:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=53134
;

-- 2019-10-17T10:12:32.718Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsActive='Y',Updated=TO_TIMESTAMP('2019-10-17 13:12:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541763
;

-- 2019-10-17T10:29:53.156Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET DocNoSequence_ID=1000, IsDocNoControlled='Y',Updated=TO_TIMESTAMP('2019-10-17 13:29:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=540948
;

