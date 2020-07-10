-- 2018-12-11T14:47:40.029
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=53025
;

-- 2018-12-11T14:47:40.034
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Menu WHERE AD_Menu_ID=53025
;

-- 2018-12-11T14:47:40.036
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=53025 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- 2018-12-11T14:49:44.327
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WF_Node SET Action='Z', AD_Form_ID=NULL,Updated=TO_TIMESTAMP('2018-12-11 14:49:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_WF_Node_ID=50050
;

-- 2018-12-11T14:49:53.138
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Form_Trl WHERE AD_Form_ID=53001
;

-- 2018-12-11T14:49:53.144
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Form WHERE AD_Form_ID=53001
;

