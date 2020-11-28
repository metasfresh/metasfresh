-- 2017-05-26T11:10:24.545
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2017-05-26 11:10:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556820
;

UPDATE ad_ui_section SET Value=ad_ui_section_ID where value IS NULL;
