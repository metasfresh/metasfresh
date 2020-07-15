
UPDATE mktg_campaign set MKTG_Platform_ID=540000 where MKTG_Platform_ID is null; -- Cleverreach, i.e. the only platform we currently have in the system


-- 2018-05-11T08:25:28.844
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2018-05-11 08:25:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560098
;

-- 2018-05-11T08:25:29.871
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('mktg_campaign','MKTG_Platform_ID','NUMERIC(10)',null,null)
;

-- 2018-05-11T08:25:29.874
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('mktg_campaign','MKTG_Platform_ID',null,'NOT NULL',null)
;



-- right now there can be max. MKTG_Platform record inh the system
UPDATE mktg_contactperson set MKTG_Platform_ID=540000 where MKTG_Platform_ID is null; -- Cleverreach, i.e. the only platform we currently have in the system


-- 2018-05-11T08:30:14.652
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2018-05-11 08:30:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560093
;

-- 2018-05-11T08:30:15.755
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('mktg_contactperson','MKTG_Platform_ID','NUMERIC(10)',null,null)
;

-- 2018-05-11T08:30:15.760
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('mktg_contactperson','MKTG_Platform_ID',null,'NOT NULL',null)
;

