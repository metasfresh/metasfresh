
-- 2018-11-12T10:03:34.821
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE INDEX AD_Element_Link_AD_Window_ID ON AD_Element_Link (AD_Window_ID)
;

-- 2018-11-12T10:03:38.625
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE INDEX AD_Element_Link_AD_Tab_ID ON AD_Element_Link (AD_Tab_ID)
;

-- 2018-11-12T10:04:05.780
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE INDEX AD_Element_Link_AD_Field_ID ON AD_Element_Link (AD_Field_ID)
;


-- 2018-11-12T10:04:59.998
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX AD_Element_Link_UniqueField ON AD_Element_Link (AD_Field_ID) WHERE IsActive = 'Y'
;


-- 2018-11-12T10:05:22.882
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX AD_Element_Link_UniqueTab ON AD_Element_Link (AD_Tab_ID) WHERE IsActive = 'Y'
;



-- 2018-11-12T10:06:12.559
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX AD_Element_Link_UniqueWindow ON AD_Element_Link (AD_Window_ID) WHERE IsActive = 'Y' AND AD_Tab_ID IS NULL AND AD_Field_ID IS NULL
;
