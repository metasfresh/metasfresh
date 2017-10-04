-- 2017-10-03T16:16:18.556
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Help='', Name='Favoriten kopieren',Updated=TO_TIMESTAMP('2017-10-03 16:16:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540359
;

-- 2017-10-03T16:17:57.600
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Ausgewählte Benutzerkennworte ändern',Updated=TO_TIMESTAMP('2017-10-03 16:17:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540348
;

-- 2017-10-03T16:20:25.716
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='N',Updated=TO_TIMESTAMP('2017-10-03 16:20:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=302
;

-- 2017-10-03T16:20:49.056
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2017-10-03 16:20:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542483
;

-- 2017-10-03T16:22:14.211
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsActive='N',Updated=TO_TIMESTAMP('2017-10-03 16:22:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=302
;

-- 2017-10-03T16:22:58.224
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='',Updated=TO_TIMESTAMP('2017-10-03 16:22:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=417
;

-- 2017-10-03T16:45:04.479
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_user','Password','VARCHAR(96)',null,null)
;

-- 2017-10-03T16:53:13.484
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsActive='Y',Updated=TO_TIMESTAMP('2017-10-03 16:53:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=302
;

-- 2017-10-03T16:57:06.636
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-10-03 16:57:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=302
;

