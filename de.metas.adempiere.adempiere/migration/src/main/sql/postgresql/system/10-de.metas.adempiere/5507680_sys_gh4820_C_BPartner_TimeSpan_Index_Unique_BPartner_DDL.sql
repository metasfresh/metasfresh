


-- 2018-12-07T12:02:04.138
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX C_BPartner_TimeSpan_Unique_BPartner ON C_BPartner_TimeSpan (C_BPartner_ID) WHERE IsActive = 'Y'
;

