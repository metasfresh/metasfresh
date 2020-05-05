-- 2017-06-10T16:14:37.872
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=53294
;

-- 2017-06-10T16:14:37.887
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Menu WHERE AD_Menu_ID=53294
;

-- 2017-06-10T16:14:37.895
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=53294 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- 2017-06-10T16:15:03.583
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=53225
;

-- 2017-06-10T16:15:03.597
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process WHERE AD_Process_ID=53225
;

-- 2017-06-10T16:15:29.612
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=487
;

-- 2017-06-10T16:15:29.621
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Menu WHERE AD_Menu_ID=487
;

-- 2017-06-10T16:15:29.624
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=487 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- 2017-06-10T16:15:41.047
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=288
;

-- 2017-06-10T16:15:41.051
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process WHERE AD_Process_ID=288
;

-- 2017-06-10T16:16:09.497
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=540163
;

-- 2017-06-10T16:16:09.501
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Menu WHERE AD_Menu_ID=540163
;

-- 2017-06-10T16:16:09.503
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=540163 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- 2017-06-10T16:16:20.329
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=540126
;

-- 2017-06-10T16:16:20.335
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process WHERE AD_Process_ID=540126
;

-- 2017-06-10T16:17:51.147
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='Sends password reset emails to all users in current selection.', Name='Mass user password reset by email', Value='AD_User_ResetPassword_EnqueueForSelection',Updated=TO_TIMESTAMP('2017-06-10 16:17:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540348
;

-- 2017-06-10T16:17:51.150
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540348
;

