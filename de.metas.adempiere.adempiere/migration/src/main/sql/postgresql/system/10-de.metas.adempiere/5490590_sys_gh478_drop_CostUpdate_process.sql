-- 2018-04-12T21:48:59.660
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=313
;

-- 2018-04-12T21:48:59.677
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Menu WHERE AD_Menu_ID=313
;

-- 2018-04-12T21:48:59.682
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=313 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- 2018-04-12T21:49:09.729
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=182
;

-- 2018-04-12T21:49:09.733
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process WHERE AD_Process_ID=182
;

