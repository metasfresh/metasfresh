

-- 2018-10-05T13:51:12.231
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_element','Description','VARCHAR(2000)',null,null)
;

select db_alter_table('ad_element_trl', 'ALTER TABLE public.ad_element_trl ALTER COLUMN description TYPE character varying (2000)');



-- 2018-10-05T11:05:10.812
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_column','Name','VARCHAR(60)',null,null)
;

-- 2018-10-05T11:05:10.820
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_column','Name',null,'NULL',null)
;
