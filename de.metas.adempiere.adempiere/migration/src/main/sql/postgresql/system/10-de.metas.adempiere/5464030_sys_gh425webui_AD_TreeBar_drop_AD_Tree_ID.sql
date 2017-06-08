-- 2017-05-31T16:59:50.178
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=6212
;

-- 2017-05-31T16:59:50.210
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=6212
;

alter table AD_TreeBar drop column AD_Tree_ID;

