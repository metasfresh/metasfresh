-- 2021-05-19T20:23:35.583Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_bpartner_alberta','IsArchived','CHAR(1)',null,null)
;

-- 2021-05-19T20:23:45.394Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_bpartner_albertacaregiver','IsActive','CHAR(1)',null,null)
;

-- 2021-05-19T20:23:53.028Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_bpartner_albertapatient','C_BPartner_ID','NUMERIC(10)',null,null)
;

-- 2021-05-19T20:23:59.987Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_bpartner_albertarole','IsActive','CHAR(1)',null,null)
;

-- 2021-05-19T20:24:12.620Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_BPartner_AlbertaRole','ALTER TABLE public.C_BPartner_AlbertaRole ADD COLUMN C_BPartner_ID NUMERIC(10) NOT NULL')
;

-- 2021-05-19T20:24:12.627Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_BPartner_AlbertaRole ADD CONSTRAINT CBPartner_CBPartnerAlbertaRole FOREIGN KEY (C_BPartner_ID) REFERENCES public.C_BPartner DEFERRABLE INITIALLY DEFERRED
;

-- 2021-05-19T20:25:21.314Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_bpartner_alberta','Title','VARCHAR(255)',null,null)
;

