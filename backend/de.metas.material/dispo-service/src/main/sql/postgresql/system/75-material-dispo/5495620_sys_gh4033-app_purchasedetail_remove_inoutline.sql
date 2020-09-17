-- 2018-06-11T10:29:35.566
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=564462
;

-- 2018-06-11T10:29:35.578
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=564462
;

-- MD_Candidate_Purchase_Detail.M_InOutLine_ID
SELECT db_alter_table('MD_Candidate_Purchase_Detail','ALTER TABLE MD_Candidate_Purchase_Detail DROP COLUMN M_InOutLine_ID;');

-- 2018-06-11T10:31:08.676
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=560249
;

-- 2018-06-11T10:31:08.681
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=560249
;

