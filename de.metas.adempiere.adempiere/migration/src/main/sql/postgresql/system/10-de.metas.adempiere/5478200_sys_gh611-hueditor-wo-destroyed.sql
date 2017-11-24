-- 2017-11-24T10:17:47.315
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET WhereClause='HUStatus != ''D''',Updated=TO_TIMESTAMP('2017-11-24 10:17:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540508
;

-- 2017-11-24T10:20:51.243
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=540302,Updated=TO_TIMESTAMP('2017-11-24 10:20:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=550218
;

-- 2017-11-24T10:21:08.663
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540381,'M_HU.HUStatus != ''D''',TO_TIMESTAMP('2017-11-24 10:21:08','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','PackingStatus wo detroyed','S',TO_TIMESTAMP('2017-11-24 10:21:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-24T10:21:26.475
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Name='PackingStatus wo destroyed',Updated=TO_TIMESTAMP('2017-11-24 10:21:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540381
;

-- 2017-11-24T10:22:09.310
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=540381,Updated=TO_TIMESTAMP('2017-11-24 10:22:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=550218
;

-- 2017-11-24T10:25:35.420
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='select value 
from AD_Ref_List
where AD_Reference_ID=540478
and value != ''D''',Updated=TO_TIMESTAMP('2017-11-24 10:25:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540381
;

-- 2017-11-24T10:26:36.038
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='M_HU.HUStatus in
(
select value 
from AD_Ref_List
where AD_Reference_ID=540478
and value != ''D''
)',Updated=TO_TIMESTAMP('2017-11-24 10:26:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540381
;

-- 2017-11-24T10:28:24.758
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='value != ''D''',Updated=TO_TIMESTAMP('2017-11-24 10:28:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540381
;

-- 2017-11-24T10:30:47.991
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=NULL,Updated=TO_TIMESTAMP('2017-11-24 10:30:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=550218
;

-- 2017-11-24T10:32:12.835
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Reference_ID=17, AD_Val_Rule_ID=540381,Updated=TO_TIMESTAMP('2017-11-24 10:32:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=553930
;

