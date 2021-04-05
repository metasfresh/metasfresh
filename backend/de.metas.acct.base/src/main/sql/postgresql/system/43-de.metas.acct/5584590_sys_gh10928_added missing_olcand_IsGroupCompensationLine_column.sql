-- 2021-04-01T11:17:37.484Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column
VALUES ('c_olcand', 'IsGroupCompensationLine', 'CHAR(1)', NULL, 'N')
;

-- 2021-04-01T11:17:37.595Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_OLCand
SET IsGroupCompensationLine='N'
WHERE IsGroupCompensationLine IS NULL
;

-- 2021-04-01T11:24:47.058Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column
SET EntityType='de.metas.ordercandidate', Updated=TO_TIMESTAMP('2021-04-01 14:24:47', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID = 573256
;

