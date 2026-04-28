-- 2019-12-20T09:41:59.896Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='IsAnonymousHuPickedOnTheFly',Updated=TO_TIMESTAMP('2019-12-20 11:41:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577441
;

-- 2019-12-20T09:41:59.917Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsAnonymousHuPickedOnTheFly', Name='Anonymous HU Picked On the Fly', Description=NULL, Help=NULL WHERE AD_Element_ID=577441
;

-- 2019-12-20T09:41:59.918Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsAnonymousHuPickedOnTheFly', Name='Anonymous HU Picked On the Fly', Description=NULL, Help=NULL, AD_Element_ID=577441 WHERE UPPER(ColumnName)='ISANONYMOUSHUPICKEDONTHEFLY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-12-20T09:41:59.920Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsAnonymousHuPickedOnTheFly', Name='Anonymous HU Picked On the Fly', Description=NULL, Help=NULL WHERE AD_Element_ID=577441 AND IsCentrallyMaintained='Y'
;

-- 2019-12-20T09:42:13.684Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_shipmentschedule_qtypicked','IsAnonymousHuPickedOnTheFly','CHAR(1)',null,'N')
;

-- 2019-12-20T09:42:13.832Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_ShipmentSchedule_QtyPicked SET IsAnonymousHuPickedOnTheFly='N' WHERE IsAnonymousHuPickedOnTheFly IS NULL
;

