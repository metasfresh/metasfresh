-- 2017-11-22T17:04:03.335
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsReferenceTarget,Name,Updated,UpdatedBy) VALUES (0,0,540746,540195,TO_TIMESTAMP('2017-11-22 17:04:03','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','N','C_Invoice -> Order (SO)',TO_TIMESTAMP('2017-11-22 17:04:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-22T17:37:09.338
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='IsTableRecordIdTarget', Name='IsTableRecordIdTarget ', PrintName='IsTableRecordIdTarget ',Updated=TO_TIMESTAMP('2017-11-22 17:37:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543415
;

-- 2017-11-22T17:37:09.342
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsTableRecordIdTarget', Name='IsTableRecordIdTarget ', Description=NULL, Help=NULL WHERE AD_Element_ID=543415
;

-- 2017-11-22T17:37:09.352
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsTableRecordIdTarget', Name='IsTableRecordIdTarget ', Description=NULL, Help=NULL, AD_Element_ID=543415 WHERE UPPER(ColumnName)='ISTABLERECORDIDTARGET' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-11-22T17:37:09.355
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsTableRecordIdTarget', Name='IsTableRecordIdTarget ', Description=NULL, Help=NULL WHERE AD_Element_ID=543415 AND IsCentrallyMaintained='Y'
;

-- 2017-11-22T17:37:09.357
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='IsTableRecordIdTarget ', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543415) AND IsCentrallyMaintained='Y'
;

-- 2017-11-22T17:37:09.379
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='IsTableRecordIdTarget ', Name='IsTableRecordIdTarget ' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543415)
;

-- 2017-11-22T17:37:54.099
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@IsTableRecordIdTarget @=''Y''',Updated=TO_TIMESTAMP('2017-11-22 17:37:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=58582
;

-- 2017-11-22T17:38:04.836
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@IsTableRecordIdTarget @=''Y''',Updated=TO_TIMESTAMP('2017-11-22 17:38:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=58585
;

-- 2017-11-22T17:38:18.958
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@IsTableRecordIdTarget @=''Y''',Updated=TO_TIMESTAMP('2017-11-22 17:38:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=58584
;

-- 2017-11-22T17:40:07.988
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsTableRecordIdTarget @=''N''',Updated=TO_TIMESTAMP('2017-11-22 17:40:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=58067
;

-- 2017-11-22T17:40:11.514
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsTableRecordIdTarget @=''N''',Updated=TO_TIMESTAMP('2017-11-22 17:40:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=58071
;

-- 2017-11-22T17:40:14.898
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsTableRecordIdTarget @=''N''',Updated=TO_TIMESTAMP('2017-11-22 17:40:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=58072
;

-- 2017-11-22T17:40:48.884
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@IsTableRecordIdTarget @=''N''',Updated=TO_TIMESTAMP('2017-11-22 17:40:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=58582
;

