

-- 2018-03-13T18:57:43.332
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544959
;

-- 2018-03-13T18:57:47.873
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=549851
;

-- 2018-03-13T18:57:50.456
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=549852
;

-- 2018-03-13T18:57:56.955
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=561243
;

-- 2018-03-13T18:57:56.963
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=561243
;

-- 2018-03-13T18:58:07.536
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=3077
;

-- 2018-03-13T18:58:07.538
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=3077
;

-- 2018-03-13T18:58:07.605
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=561242
;

-- 2018-03-13T18:58:07.609
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=561242
;

-- 2018-03-13T18:59:08.507
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=558402
;

-- 2018-03-13T18:59:08.511
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=558402
;

-- 2018-03-13T18:59:11.228
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=558403
;

-- 2018-03-13T18:59:11.236
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=558403
;


SELECT public.db_alter_table('C_TaxCategory','ALTER TABLE public.C_TaxCategory DROP COLUMN IsReduced')
;

SELECT public.db_alter_table('C_TaxCategory','ALTER TABLE public.C_TaxCategory DROP COLUMN IsWithout')
;