-- 2021-01-13T19:32:35.933Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=576295
;

-- 2021-01-13T19:32:35.942Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=628576
;

-- 2021-01-13T19:32:35.945Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=628576
;

-- 2021-01-13T19:32:35.948Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=628576
;

-- 2021-01-13T19:32:35.948Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_HU_Reservation','ALTER TABLE M_HU_Reservation DROP COLUMN IF EXISTS C_ProjectLine_ID')
;

-- 2021-01-13T19:32:35.984Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=572380
;

-- 2021-01-13T19:32:35.985Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=572380
;




DROP INDEX IF EXISTS m_hu_reservation_c_projectline_id;
DROP INDEX IF EXISTS m_hu_reservation_c_project_id;
CREATE INDEX m_hu_reservation_c_project_id ON m_hu_reservation (c_project_id);



