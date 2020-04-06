-- 2018-12-17T16:44:56.269
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=312
;

-- 2018-12-17T16:44:56.275
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Menu WHERE AD_Menu_ID=312
;

-- 2018-12-17T16:44:56.278
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=312 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- 2018-12-17T16:45:09.298
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Form_Trl WHERE AD_Form_ID=108
;

-- 2018-12-17T16:45:09.312
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Form WHERE AD_Form_ID=108
;

