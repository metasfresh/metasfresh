-- 2018-01-12T19:15:49.387
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=53070
;

-- 2018-01-12T19:15:49.432
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Menu WHERE AD_Menu_ID=53070
;

-- 2018-01-12T19:15:49.452
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=53070 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- 2018-01-12T19:18:20.695
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=53067
;

-- 2018-01-12T19:18:20.704
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Menu WHERE AD_Menu_ID=53067
;

-- 2018-01-12T19:18:20.708
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=53067 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- 2018-01-12T19:18:59.413
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Workflow SET AD_WF_Node_ID=NULL, IsValid='N',Updated=TO_TIMESTAMP('2018-01-12 19:18:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Workflow_ID=50010
;

-- 2018-01-12T19:19:07.645
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_WF_Node_Trl WHERE AD_WF_Node_ID=50063
;

-- 2018-01-12T19:19:07.662
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_WF_Node WHERE AD_WF_Node_ID=50063
;

-- 2018-01-12T19:19:07.946
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_WF_Node_Trl WHERE AD_WF_Node_ID=50034
;

-- 2018-01-12T19:19:07.958
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_WF_Node WHERE AD_WF_Node_ID=50034
;

-- 2018-01-12T19:19:08.125
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_WF_Node_Trl WHERE AD_WF_Node_ID=50061
;

-- 2018-01-12T19:19:08.133
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_WF_Node WHERE AD_WF_Node_ID=50061
;

-- 2018-01-12T19:19:08.359
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_WF_Node_Trl WHERE AD_WF_Node_ID=50062
;

-- 2018-01-12T19:19:08.362
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_WF_Node WHERE AD_WF_Node_ID=50062
;

-- 2018-01-12T19:19:08.421
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_WF_Node_Trl WHERE AD_WF_Node_ID=50035
;

-- 2018-01-12T19:19:08.425
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_WF_Node WHERE AD_WF_Node_ID=50035
;

-- 2018-01-12T19:19:08.493
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_WF_Node_Trl WHERE AD_WF_Node_ID=50036
;

-- 2018-01-12T19:19:08.498
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_WF_Node WHERE AD_WF_Node_ID=50036
;

-- 2018-01-12T19:25:14.279
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Workflow SET AD_WF_Node_ID=NULL, IsValid='N',Updated=TO_TIMESTAMP('2018-01-12 19:25:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Workflow_ID=50006
;

-- 2018-01-12T19:25:20.274
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_WF_Node_Trl WHERE AD_WF_Node_ID=50025
;

-- 2018-01-12T19:25:20.277
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_WF_Node WHERE AD_WF_Node_ID=50025
;

-- 2018-01-12T19:25:20.373
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_WF_Node_Trl WHERE AD_WF_Node_ID=50024
;

-- 2018-01-12T19:25:20.379
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_WF_Node WHERE AD_WF_Node_ID=50024
;

-- 2018-01-12T19:25:20.483
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_WF_Node_Trl WHERE AD_WF_Node_ID=50023
;

-- 2018-01-12T19:25:20.489
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_WF_Node WHERE AD_WF_Node_ID=50023
;

-- 2018-01-12T19:25:20.563
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_WF_Node_Trl WHERE AD_WF_Node_ID=50022
;

-- 2018-01-12T19:25:20.566
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_WF_Node WHERE AD_WF_Node_ID=50022
;

-- 2018-01-12T19:25:20.632
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_WF_Node_Trl WHERE AD_WF_Node_ID=50021
;

-- 2018-01-12T19:25:20.635
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_WF_Node WHERE AD_WF_Node_ID=50021
;

-- 2018-01-12T19:25:20.701
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_WF_Node_Trl WHERE AD_WF_Node_ID=50020
;

-- 2018-01-12T19:25:20.704
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_WF_Node WHERE AD_WF_Node_ID=50020
;

-- 2018-01-12T19:25:20.772
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_WF_Node_Trl WHERE AD_WF_Node_ID=50019
;

-- 2018-01-12T19:25:20.776
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_WF_Node WHERE AD_WF_Node_ID=50019
;

-- 2018-01-12T19:25:32.346
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=53050
;

-- 2018-01-12T19:25:32.349
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Menu WHERE AD_Menu_ID=53050
;

-- 2018-01-12T19:25:32.352
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=53050 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- 2018-01-12T19:25:36.993
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Workflow_Trl WHERE AD_Workflow_ID=50006
;

-- 2018-01-12T19:25:37.001
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Workflow WHERE AD_Workflow_ID=50006
;

-- 2018-01-12T19:25:44.281
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Workflow_Trl WHERE AD_Workflow_ID=50010
;

-- 2018-01-12T19:25:44.284
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Workflow WHERE AD_Workflow_ID=50010
;

-- 2018-01-12T19:25:55.751
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=53046
;

-- 2018-01-12T19:25:55.754
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process WHERE AD_Process_ID=53046
;

