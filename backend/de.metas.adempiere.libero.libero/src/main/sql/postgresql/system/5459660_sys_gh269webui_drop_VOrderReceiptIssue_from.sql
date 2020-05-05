-- 2017-04-05T13:56:19.660
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=53059
;

-- 2017-04-05T13:56:19.665
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Menu WHERE AD_Menu_ID=53059
;

-- 2017-04-05T13:56:19.668
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=53059 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- 2017-04-05T15:01:15.106
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WF_Node SET Action='Z', AD_Form_ID=NULL,Updated=TO_TIMESTAMP('2017-04-05 15:01:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_WF_Node_ID=50058
;

-- 2017-04-05T15:01:44.407
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Form_Trl WHERE AD_Form_ID=53007
;

-- 2017-04-05T15:01:44.410
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Form WHERE AD_Form_ID=53007
;

