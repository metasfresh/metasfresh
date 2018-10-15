
--
-- note: it's important to also take care of unknown AD_Tabs..
--
UPDATE AD_Tab SET AD_Column_ID=563235, AD_Table_ID=541144 WHERE AD_Column_ID=557035;
UPDATE AD_Tab SET WhereClause='AD_AttachmentEntry_ReferencedRecord_v.AD_Table_ID=291' WHERE WhereClause='AD_AttachmentEntry.AD_Table_ID=291';


DELETE FROM AD_Field_Trl WHERE AD_Field_ID IN (select AD_Field_ID from AD_Field where AD_Column_ID=557030);
DELETE FROM ad_ui_element WHERE AD_Field_ID IN (select AD_Field_ID from AD_Field where AD_Column_ID=557030);
DELETE FROM AD_Field WHERE AD_Column_ID=557030;

-- 2018-09-28T21:48:43.262
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=557030
;

-- 2018-09-28T21:48:43.267
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=557030
;

DELETE FROM AD_Field_Trl WHERE AD_Field_ID IN (select AD_Field_ID from AD_Field where AD_Column_ID=557035);
DELETE FROM ad_ui_element WHERE AD_Field_ID IN (select AD_Field_ID from AD_Field where AD_Column_ID=557035);
DELETE FROM AD_Field WHERE AD_Column_ID=557035;

-- 2018-09-28T21:48:50.769
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=557035
;

-- 2018-09-28T21:48:50.773
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=557035
;
