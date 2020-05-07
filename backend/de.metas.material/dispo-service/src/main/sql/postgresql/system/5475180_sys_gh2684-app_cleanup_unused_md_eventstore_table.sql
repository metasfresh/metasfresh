
--
-- delete MD_EventStore table :(
--

-- 2017-10-23T15:30:11.461
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=540814
;

-- 2017-10-23T15:30:11.467
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Table WHERE AD_Table_ID=540814
;

-- 2017-10-23T15:30:17.389
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=543326
;

-- 2017-10-23T15:30:17.399
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element WHERE AD_Element_ID=543326
;

DROP TABLE IF EXISTS public.MD_EventStore;

