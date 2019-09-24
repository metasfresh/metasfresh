
-- 2019-09-24T13:25:33.576Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2019-09-24 16:25:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543753
;





-- 2019-09-24T13:30:59.142Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='GlobalId',Updated=TO_TIMESTAMP('2019-09-24 16:30:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568818
;

-- 2019-09-24T13:31:25.169Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='GlobalId',Updated=TO_TIMESTAMP('2019-09-24 16:31:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543753
;

-- 2019-09-24T13:31:25.174Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='GlobalId', Name='Global ID', Description=NULL, Help=NULL WHERE AD_Element_ID=543753
;

-- 2019-09-24T13:31:25.175Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='GlobalId', Name='Global ID', Description=NULL, Help=NULL, AD_Element_ID=543753 WHERE UPPER(ColumnName)='GLOBALID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-09-24T13:31:25.177Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='GlobalId', Name='Global ID', Description=NULL, Help=NULL WHERE AD_Element_ID=543753 AND IsCentrallyMaintained='Y'
;




DO $$ 
    BEGIN
        BEGIN
            -- 2019-09-24T13:27:17.507Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('I_BPartner','ALTER TABLE public.I_BPartner ADD COLUMN GlobalId VARCHAR(255)')
;
        EXCEPTION
            WHEN duplicate_column THEN RAISE NOTICE 'column globalid already exists in I_BPartner.';
        END;
    END;
$$