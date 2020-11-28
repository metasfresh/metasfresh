-- 2019-10-04T05:33:31.666Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N', TechnicalNote='Not mandatory; if not provided by the external party, then metasfresh falls back to use the M_Product''s C_UOM_ID',Updated=TO_TIMESTAMP('2019-10-04 07:33:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=544976
;

-- 2019-10-04T05:33:34.049Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_olcand','C_UOM_ID','NUMERIC(10)',null,null)
;

-- 2019-10-04T05:33:34.056Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_olcand','C_UOM_ID',null,'NULL',null)
;

