--
-- Activate changelog for all MD_* tables, but not for Updated and UpdatedBy
-- fix insertable/deletable flags
--
-- 2017-05-17T08:06:09.288
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsInsertRecord='N',Updated=TO_TIMESTAMP('2017-05-17 08:06:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540802
;

-- 2017-05-17T08:06:54.202
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsChangeLog='Y',Updated=TO_TIMESTAMP('2017-05-17 08:06:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540815
;

-- 2017-05-17T08:07:00.468
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsAllowLogging='N',Updated=TO_TIMESTAMP('2017-05-17 08:07:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556579
;

-- 2017-05-17T08:07:04.025
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsAllowLogging='N',Updated=TO_TIMESTAMP('2017-05-17 08:07:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556578
;

-- 2017-05-17T08:07:15.413
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsChangeLog='Y',Updated=TO_TIMESTAMP('2017-05-17 08:07:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540821
;

-- 2017-05-17T08:07:21.027
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsAllowLogging='N',Updated=TO_TIMESTAMP('2017-05-17 08:07:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556796
;

-- 2017-05-17T08:07:24.067
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsAllowLogging='N',Updated=TO_TIMESTAMP('2017-05-17 08:07:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556797
;

-- 2017-05-17T08:07:30.760
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsChangeLog='Y',Updated=TO_TIMESTAMP('2017-05-17 08:07:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540810
;

-- 2017-05-17T08:07:33.634
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsAllowLogging='N',Updated=TO_TIMESTAMP('2017-05-17 08:07:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556521
;

-- 2017-05-17T08:07:37.443
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsAllowLogging='N',Updated=TO_TIMESTAMP('2017-05-17 08:07:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556522
;

-- 2017-05-17T08:07:48.678
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsDeleteable='N',Updated=TO_TIMESTAMP('2017-05-17 08:07:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540815
;

