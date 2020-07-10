-- 2019-10-23T11:06:35.836Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=563246
;

-- 2019-10-23T11:06:35.844Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589586
;

-- 2019-10-23T11:06:35.846Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=589586
;

-- 2019-10-23T11:06:35.851Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=589586
;

-- 2019-10-23T11:07:09.973Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=569213
;

-- 2019-10-23T11:07:09.979Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=569213
;

ALTER TABLE C_HierarchyCommissionSettings DROP COLUMN IF EXISTS LevelHierarchy;
