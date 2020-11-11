
--
-- change EDI_Desadv.EDI_ExportStatus from button to normal readonly list-field
-- 2019-05-27T11:21:14.875
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Process_ID=NULL, AD_Reference_ID=17, IsMandatory='N', ReadOnlyLogic='',Updated=TO_TIMESTAMP('2019-05-27 11:21:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551732
;

-- 2019-05-27T11:21:18.671
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('edi_desadv','EDI_ExportStatus','CHAR(1)',null,'P')
;

-- 2019-05-27T11:21:18.767
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('edi_desadv','EDI_ExportStatus',null,'NULL',null)
;

-- 2019-05-27T11:21:47.158
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-05-27 11:21:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555448
;

-- 2019-05-27T11:22:34.091
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=541997 AND AD_Language='it_CH'
;

-- 2019-05-27T11:22:34.133
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=541997 AND AD_Language='fr_CH'
;

-- 2019-05-27T11:22:34.182
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=541997 AND AD_Language='en_GB'
;

-- 2019-05-27T11:22:51.987
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='EDI-Exportstatus', PrintName='EDI-Exportstatus',Updated=TO_TIMESTAMP('2019-05-27 11:22:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541997 AND AD_Language='de_CH'
;

-- 2019-05-27T11:23:01.971
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='EDI-Exportstatus', PrintName='EDI-Exportstatus',Updated=TO_TIMESTAMP('2019-05-27 11:23:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541997 AND AD_Language='de_DE'
;

-- 2019-05-27T11:23:12.563
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='EDI export status', PrintName='EDI export status',Updated=TO_TIMESTAMP('2019-05-27 11:23:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541997 AND AD_Language='en_US'
;
