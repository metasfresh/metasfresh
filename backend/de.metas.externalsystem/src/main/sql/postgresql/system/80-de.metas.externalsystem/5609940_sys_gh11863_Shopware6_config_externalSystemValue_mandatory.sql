
UPDATE externalsystem_config_shopware6 childConfig
SET externalsystemvalue = upper(substr(md5(random()::text), 0, 14)),
    Updated=now(),
    UpdatedBy=99
WHERE childConfig.externalsystemvalue is null;

-- 2021-10-18T11:16:33.563Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2021-10-18 14:16:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573007
;

-- 2021-10-18T11:16:35.359Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('externalsystem_config_shopware6','ExternalSystemValue','VARCHAR(255)',null,null)
;

-- 2021-10-18T11:16:35.362Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('externalsystem_config_shopware6','ExternalSystemValue',null,'NOT NULL',null)
;

