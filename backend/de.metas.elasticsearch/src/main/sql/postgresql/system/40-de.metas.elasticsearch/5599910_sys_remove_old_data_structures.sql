-- 2021-08-03T14:58:11.987Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET IsActive='Y',Updated=TO_TIMESTAMP('2021-08-03 17:58:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540725
;

-- 2021-08-03T14:58:18.552Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Table_Process WHERE AD_Table_Process_ID=540529
;

-- 2021-08-03T14:58:23.113Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=540725
;

-- 2021-08-03T14:58:23.116Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process WHERE AD_Process_ID=540725
;

-- 2021-08-03T14:58:30.032Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Table_Process WHERE AD_Table_Process_ID=540642
;

-- 2021-08-03T14:58:35.761Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=540981
;

-- 2021-08-03T14:58:35.768Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process WHERE AD_Process_ID=540981
;

-- 2021-08-03T15:13:09.433Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=541113
;

-- 2021-08-03T15:13:09.440Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Menu WHERE AD_Menu_ID=541113
;

-- 2021-08-03T15:13:09.452Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=541113 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- 2021-08-03T15:13:22.551Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=541112
;

-- 2021-08-03T15:13:22.554Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Menu WHERE AD_Menu_ID=541112
;

-- 2021-08-03T15:13:22.555Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=541112 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

delete from ad_pinstance where ad_window_id=540449;

-- 2021-08-03T15:14:38.306Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Window_ID=540449
;

-- 2021-08-03T15:14:38.307Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Window_Trl WHERE AD_Window_ID=540449
;

-- 2021-08-03T15:14:38.310Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Window WHERE AD_Window_ID=540449
;

-- 2021-08-03T15:14:43.966Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Window_ID=540448
;

-- 2021-08-03T15:14:43.967Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Window_Trl WHERE AD_Window_ID=540448
;

-- 2021-08-03T15:14:43.970Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Window WHERE AD_Window_ID=540448
;

-- 2021-08-03T15:16:32.188Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=540990
;

-- 2021-08-03T15:16:32.191Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Table WHERE AD_Table_ID=540990
;

-- 2021-08-03T15:16:42.722Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=540991
;

-- 2021-08-03T15:16:42.735Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Table WHERE AD_Table_ID=540991
;

-- 2021-08-03T15:17:00.578Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=540989
;

-- 2021-08-03T15:17:00.582Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Table WHERE AD_Table_ID=540989
;


delete from AD_Sequence where name in ('ES_FTS_Index', 'ES_FTS_IndexInclude', 'ES_FTS_Template');


-- 2021-08-03T15:18:32.818Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=544122
;

-- 2021-08-03T15:18:32.821Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element WHERE AD_Element_ID=544122
;

-- 2021-08-03T15:18:37.380Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=544121
;

-- 2021-08-03T15:18:37.384Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element WHERE AD_Element_ID=544121
;

-- 2021-08-03T15:19:04.798Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=543285
;

-- 2021-08-03T15:19:04.801Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element WHERE AD_Element_ID=543285
;

-- 2021-08-03T15:19:18.179Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=544123
;

-- 2021-08-03T15:19:18.184Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element WHERE AD_Element_ID=544123
;

-- 2021-08-03T15:19:25.966Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=544124
;

-- 2021-08-03T15:19:25.970Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element WHERE AD_Element_ID=544124
;









