
ALTER TABLE public.ad_ui_section
   ALTER COLUMN value SET DEFAULT nextval('public.ad_ui_section_seq');
   
-- 2017-05-26T11:10:29.406
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_ui_section','Value','VARCHAR(40)',null,null)
;

-- 2017-05-26T11:10:29.416
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_ui_section','Value',null,'NOT NULL',null)
;

