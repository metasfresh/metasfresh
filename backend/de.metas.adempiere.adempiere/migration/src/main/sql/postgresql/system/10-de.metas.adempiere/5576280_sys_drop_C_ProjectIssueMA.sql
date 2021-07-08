-- 2021-01-13T07:01:31.236Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=761
;

-- 2021-01-13T07:01:31.241Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Table WHERE AD_Table_ID=761
;

-- 2021-01-13T07:01:44.040Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=543230
;

-- 2021-01-13T07:01:44.081Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element WHERE AD_Element_ID=543230
;

delete from ad_sequence where name='C_ProjectIssueMA' and istableid='Y';

drop table if exists c_projectissuema;
drop sequence if exists c_projectissuema_seq;

