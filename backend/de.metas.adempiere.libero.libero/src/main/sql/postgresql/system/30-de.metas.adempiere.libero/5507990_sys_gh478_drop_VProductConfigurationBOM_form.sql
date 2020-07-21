-- 2018-12-11T14:43:51.295
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=53028
;

-- 2018-12-11T14:43:51.307
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Menu WHERE AD_Menu_ID=53028
;

-- 2018-12-11T14:43:51.314
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=53028 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- 2018-12-11T14:43:55.481
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Form_Trl WHERE AD_Form_ID=53002
;

-- 2018-12-11T14:43:55.487
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Form WHERE AD_Form_ID=53002
;

