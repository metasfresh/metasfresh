-- 2017-10-23T15:50:38.757
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-23 15:50:38','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Scheduler',Description='' WHERE AD_Menu_ID=540969 AND AD_Language='en_US'
;

-- 2017-10-23T15:51:09.323
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=549019
;

-- 2017-10-23T15:51:12.313
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=549020
;

-- 2017-10-23T15:53:05.327
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541206, SeqNo=50,Updated=TO_TIMESTAMP('2017-10-23 15:53:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549017
;

-- 2017-10-23T15:53:09.466
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=541198
;

-- 2017-10-23T15:53:12.675
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=540691
;

-- 2017-10-23T15:53:15.752
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_UI_Section_Trl WHERE AD_UI_Section_ID=540517
;

-- 2017-10-23T15:53:15.757
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Section WHERE AD_UI_Section_ID=540517
;

-- 2017-10-23T15:56:46.981
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@ScheduleType@=C',Updated=TO_TIMESTAMP('2017-10-23 15:56:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=59029
;

-- 2017-10-23T15:57:00.987
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsMandatory='',Updated=TO_TIMESTAMP('2017-10-23 15:57:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=58774
;

