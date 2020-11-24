-- 2018-03-26T16:06:11.911
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP INDEX IF EXISTS ad_print_clients_hostkey
;

-- 2018-03-26T16:06:11.914
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX AD_Print_Clients_HostKey ON AD_Print_Clients (HostKey) WHERE IsActive='Y'
;

