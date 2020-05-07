delete from AD_PrintFormat where AD_Table_ID=479; -- RV_Product_Costing

-- 2018-01-18T11:06:07.790
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=314
;

-- 2018-01-18T11:06:07.804
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Menu WHERE AD_Menu_ID=314
;

-- 2018-01-18T11:06:07.807
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=314 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- 2018-01-18T11:06:16.645
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=183
;

-- 2018-01-18T11:06:16.648
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process WHERE AD_Process_ID=183
;

-- 2018-01-18T11:06:27.123
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_ReportView WHERE AD_ReportView_ID=125
;

delete from AD_ReportView where AD_Table_ID=479; -- RV_Product_Costing
















-- 2018-01-18T11:07:55.924
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=479
;

-- 2018-01-18T11:07:55.927
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Table WHERE AD_Table_ID=479
;

