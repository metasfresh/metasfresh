-- 2020-02-26T11:09:26.347Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540481,'AD_Element.AD_Element_ID < 1000000',TO_TIMESTAMP('2020-02-26 13:09:26','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','AD_Element_ID_Validation','S',TO_TIMESTAMP('2020-02-26 13:09:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-26T11:10:11.840Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=540481,Updated=TO_TIMESTAMP('2020-02-26 13:10:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=563571
;

-- 2020-02-26T11:10:35.458Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=540481, IsUpdateable='N',Updated=TO_TIMESTAMP('2020-02-26 13:10:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2637
;

-- 2020-02-26T11:11:14.402Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=540481,Updated=TO_TIMESTAMP('2020-02-26 13:11:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558125
;

-- 2020-02-26T11:11:43.133Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=540481,Updated=TO_TIMESTAMP('2020-02-26 13:11:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=563262
;

-- 2020-02-26T11:12:05.246Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=540481,Updated=TO_TIMESTAMP('2020-02-26 13:12:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=15790
;

-- 2020-02-26T11:12:25.817Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=540481,Updated=TO_TIMESTAMP('2020-02-26 13:12:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=563264
;

-- 2020-02-26T11:12:47.231Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=540481,Updated=TO_TIMESTAMP('2020-02-26 13:12:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=7729
;

-- 2020-02-26T11:13:03.777Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=540481,Updated=TO_TIMESTAMP('2020-02-26 13:13:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=563263
;

-- 2020-02-26T11:13:21.578Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=540481,Updated=TO_TIMESTAMP('2020-02-26 13:13:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556309
;

-- 2020-02-26T11:14:38.488Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540482,'AD_Element.ColumnName IS NOT NULL AND AD_Element.AD_Element_ID < 1000000',TO_TIMESTAMP('2020-02-26 13:14:38','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','AD_Element_WithColumnNameAndElementID','S',TO_TIMESTAMP('2020-02-26 13:14:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-26T11:19:34.324Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Val_Rule WHERE AD_Val_Rule_ID=540482
;

-- 2020-02-26T11:20:01.182Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540483,'AD_Element.ColumnName IS NOT NULL AND AD_Element.AD_Element_ID < 1000000',TO_TIMESTAMP('2020-02-26 13:20:01','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','AD_Element_WithColumnNameAndIdValidation','S',TO_TIMESTAMP('2020-02-26 13:20:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-26T11:22:03.845Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=540483,Updated=TO_TIMESTAMP('2020-02-26 13:22:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2608
;

