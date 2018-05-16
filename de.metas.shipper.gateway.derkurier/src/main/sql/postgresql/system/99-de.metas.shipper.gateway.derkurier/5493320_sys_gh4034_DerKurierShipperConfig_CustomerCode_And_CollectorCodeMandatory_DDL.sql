

SELECT db_alter_table('derkurier_shipper_config', 'update derkurier_shipper_config set CollectorCode=''00'' where CollectorCode IS NULL');
SELECT db_alter_table('derkurier_shipper_config', 'update derkurier_shipper_config set CustomerCode=''00'' where CustomerCode IS NULL');

-- 2018-05-14T06:50:02.532
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='00', FieldLength=2,Updated=TO_TIMESTAMP('2018-05-14 06:50:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560055
;

-- 2018-05-14T06:50:23.283
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2018-05-14 06:50:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560055
;

-- 2018-05-14T06:50:28.996
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2018-05-14 06:50:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560055
;

-- 2018-05-14T06:54:12.452
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='', IsMandatory='N',Updated=TO_TIMESTAMP('2018-05-14 06:54:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560055
;

-- 2018-05-14T06:54:15.782
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=3,Updated=TO_TIMESTAMP('2018-05-14 06:54:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560055
;

-- 2018-05-14T06:54:33.565
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=3,Updated=TO_TIMESTAMP('2018-05-14 06:54:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560054
;

-- 2018-05-14T06:54:43.739
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2018-05-14 06:54:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560055
;

COMMIT;

-- 2018-05-14T06:54:17.557
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('derkurier_shipper_config','CollectorCode','VARCHAR(3)',null,null)
;

-- 2018-05-14T06:54:36.062
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('derkurier_shipper_config','CustomerCode','VARCHAR(3)',null,null)
;

-- 2018-05-14T06:54:36.083
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('derkurier_shipper_config','CustomerCode',null,'NOT NULL',null)
;

-- 2018-05-14T06:54:46.142
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('derkurier_shipper_config','CollectorCode','VARCHAR(3)',null,null)
;

-- 2018-05-14T06:54:46.145
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('derkurier_shipper_config','CollectorCode',null,'NOT NULL',null)
;

