
-- 2018-04-17T11:39:04.357
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('AD_Client','ALTER TABLE public.AD_Client ADD COLUMN SMTPPort NUMERIC DEFAULT 25 NOT NULL')
;

-- 2018-04-17T11:40:08.010
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('AD_Client','ALTER TABLE public.AD_Client ADD COLUMN IsStartTLS CHAR(1) DEFAULT ''N'' CHECK (IsStartTLS IN (''Y'',''N'')) NOT NULL')
;



-- 2018-04-17T13:19:00.592
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_client','SMTPPort','NUMERIC(10)',null,'25')
;


-- 2018-04-17T14:10:14.613
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_client','SMTPPort',null,'NULL',null)
;

