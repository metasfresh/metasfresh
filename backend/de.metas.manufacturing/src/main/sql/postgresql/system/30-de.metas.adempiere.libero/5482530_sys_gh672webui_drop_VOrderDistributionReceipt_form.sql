-- 2018-01-12T19:30:15.651
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=53188
;

-- 2018-01-12T19:30:15.660
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Menu WHERE AD_Menu_ID=53188
;

-- 2018-01-12T19:30:15.664
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=53188 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- 2018-01-12T19:30:19.320
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Form_Trl WHERE AD_Form_ID=53012
;

-- 2018-01-12T19:30:19.322
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Form WHERE AD_Form_ID=53012
;

