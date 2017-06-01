-- 2017-05-26T11:10:24.545
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2017-05-26 11:10:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556820
;

-- 2017-05-26T11:10:29.406
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_ui_section','Value','VARCHAR(40)',null,null)
;

-- 2017-05-26T11:10:29.416
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_ui_section','Value',null,'NOT NULL',null)
;

