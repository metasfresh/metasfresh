-- Mar 6, 2017 8:11 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2017-03-06 20:11:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556344
;

-- Mar 6, 2017 8:11 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_bpartner_quickinput','Name','VARCHAR(60)',null,null)
;

-- Mar 6, 2017 8:11 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_bpartner_quickinput','Name',null,'NULL',null)
;

