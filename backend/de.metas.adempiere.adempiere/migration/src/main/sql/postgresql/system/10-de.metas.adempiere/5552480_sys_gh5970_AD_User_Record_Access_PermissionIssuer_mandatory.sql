-- 2020-02-18T09:12:17.684Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2020-02-18 11:12:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570044
;

-- 2020-02-18T09:12:19.194Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_user_record_access','PermissionIssuer','VARCHAR(40)',null,null)
;

-- 2020-02-18T09:12:19.206Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_user_record_access','PermissionIssuer',null,'NOT NULL',null)
;

