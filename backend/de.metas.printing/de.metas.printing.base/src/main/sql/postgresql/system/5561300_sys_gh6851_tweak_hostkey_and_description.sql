-- 2020-06-15T06:06:23.345Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N', TechnicalNote='Hostkey isn''t required unless OutputType=Queue',Updated=TO_TIMESTAMP('2020-06-15 08:06:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548299
;

-- 2020-06-15T06:06:25.631Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_printerhw','HostKey','VARCHAR(60)',null,null)
;

-- 2020-06-15T06:06:25.635Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_printerhw','HostKey',null,'NULL',null)
;

-- 2020-06-15T07:05:02.716Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='PDF als Job für Print-Client bereitstellen',Updated=TO_TIMESTAMP('2020-06-15 09:05:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542155
;

-- 2020-06-15T09:08:42.402Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=5000,Updated=TO_TIMESTAMP('2020-06-15 11:08:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548080
;

-- 2020-06-15T09:08:45.123Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_printerhw','Description','VARCHAR(5000)',null,null)
;

