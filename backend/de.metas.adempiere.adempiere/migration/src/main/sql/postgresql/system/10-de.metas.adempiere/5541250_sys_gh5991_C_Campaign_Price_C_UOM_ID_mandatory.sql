-- 2020-01-13T09:29:59.285Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2020-01-13 11:29:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569832
;

-- 2020-01-13T09:30:03.476Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_campaign_price','C_UOM_ID','NUMERIC(10)',null,null)
;

-- 2020-01-13T09:30:03.517Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_campaign_price','C_UOM_ID',null,'NOT NULL',null)
;

