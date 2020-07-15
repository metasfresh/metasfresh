-- make msv3_bestellunganteil.MSV3_Typ not-mandatory, because in the XSD, it is aparently also not mandatory (but logically it is)
-- 2018-09-12T14:32:02.018
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2018-09-12 14:32:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558749
;

-- 2018-09-12T14:32:04.389
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('msv3_bestellunganteil','MSV3_Typ','VARCHAR(39)',null,null)
;

-- 2018-09-12T14:32:04.393
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('msv3_bestellunganteil','MSV3_Typ',null,'NULL',null)
;

