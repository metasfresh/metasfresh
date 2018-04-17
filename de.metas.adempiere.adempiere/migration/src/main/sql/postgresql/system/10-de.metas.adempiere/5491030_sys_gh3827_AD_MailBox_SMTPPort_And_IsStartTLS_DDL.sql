
-- 2018-04-17T12:18:55.384
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('AD_MailBox','ALTER TABLE public.AD_MailBox ADD COLUMN SMTPPort NUMERIC DEFAULT 25 NOT NULL')
;



-- 2018-04-17T12:19:17.161
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('AD_MailBox','ALTER TABLE public.AD_MailBox ADD COLUMN IsStartTLS CHAR(1) DEFAULT ''N'' CHECK (IsStartTLS IN (''Y'',''N'')) NOT NULL')
;




-- 2018-04-17T13:19:24.692
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_mailbox','SMTPPort','NUMERIC(10)',null,'25')
;




-- 2018-04-17T14:07:51.531
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_mailbox','SMTPPort','NUMERIC(10)',null,'25')
;

-- 2018-04-17T14:07:51.538
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_mailbox','SMTPPort',null,'NULL',null)
;

