-- 2019-03-20T10:31:31.179
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Name='Anruf Planung',Updated=TO_TIMESTAMP('2019-03-20 10:31:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541173
;

-- 2019-03-20T10:31:57.705
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Anruf Planung', PrintName='Anruf Planung',Updated=TO_TIMESTAMP('2019-03-20 10:31:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576098 AND AD_Language='de_DE'
;

-- 2019-03-20T10:31:57.782
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576098,'de_DE') 
;

-- 2019-03-20T10:31:57.813
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576098,'de_DE') 
;

-- 2019-03-20T10:31:57.815
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_Phonecall_Schema_ID', Name='Anruf Planung', Description=NULL, Help=NULL WHERE AD_Element_ID=576098
;

-- 2019-03-20T10:31:57.818
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Phonecall_Schema_ID', Name='Anruf Planung', Description=NULL, Help=NULL, AD_Element_ID=576098 WHERE UPPER(ColumnName)='C_PHONECALL_SCHEMA_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-03-20T10:31:57.820
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Phonecall_Schema_ID', Name='Anruf Planung', Description=NULL, Help=NULL WHERE AD_Element_ID=576098 AND IsCentrallyMaintained='Y'
;

-- 2019-03-20T10:31:57.821
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Anruf Planung', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576098) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576098)
;

-- 2019-03-20T10:31:57.851
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Anruf Planung', Name='Anruf Planung' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576098)
;

-- 2019-03-20T10:31:57.853
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Anruf Planung', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576098
;

-- 2019-03-20T10:31:57.855
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Anruf Planung', Description=NULL, Help=NULL WHERE AD_Element_ID = 576098
;

-- 2019-03-20T10:31:57.857
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Anruf Planung', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576098
;

-- 2019-03-20T10:32:26.322
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Phonecall Schema', PrintName='Phonecall Schema',Updated=TO_TIMESTAMP('2019-03-20 10:32:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576098 AND AD_Language='en_US'
;

-- 2019-03-20T10:32:26.325
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576098,'en_US') 
;

-- 2019-03-20T10:32:39.017
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Anruf Planung', PrintName='Anruf Planung',Updated=TO_TIMESTAMP('2019-03-20 10:32:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576098 AND AD_Language='de_CH'
;

-- 2019-03-20T10:32:39.020
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576098,'de_CH') 
;

-- 2019-03-20T11:20:12.145
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Name='Anruf Planung Version',Updated=TO_TIMESTAMP('2019-03-20 11:20:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541175
;

-- 2019-03-20T11:20:26.752
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Trl SET Name='Anruf Planung Version',Updated=TO_TIMESTAMP('2019-03-20 11:20:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Table_ID=541175
;

-- 2019-03-20T11:20:37.153
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Trl SET Name='Anruf Planung',Updated=TO_TIMESTAMP('2019-03-20 11:20:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Table_ID=541173
;

-- 2019-03-20T11:21:04.913
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Anruf Planung Version', PrintName='Anruf Planung Version',Updated=TO_TIMESTAMP('2019-03-20 11:21:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576100 AND AD_Language='de_CH'
;

-- 2019-03-20T11:21:04.916
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576100,'de_CH') 
;

-- 2019-03-20T11:21:11.762
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Anruf Planung Version', PrintName='Anruf Planung Version',Updated=TO_TIMESTAMP('2019-03-20 11:21:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576100 AND AD_Language='de_DE'
;

-- 2019-03-20T11:21:11.765
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576100,'de_DE') 
;

-- 2019-03-20T11:21:11.787
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576100,'de_DE') 
;

-- 2019-03-20T11:21:11.788
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_Phonecall_Schema_Version_ID', Name='Anruf Planung Version', Description=NULL, Help=NULL WHERE AD_Element_ID=576100
;

-- 2019-03-20T11:21:11.789
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Phonecall_Schema_Version_ID', Name='Anruf Planung Version', Description=NULL, Help=NULL, AD_Element_ID=576100 WHERE UPPER(ColumnName)='C_PHONECALL_SCHEMA_VERSION_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-03-20T11:21:11.790
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Phonecall_Schema_Version_ID', Name='Anruf Planung Version', Description=NULL, Help=NULL WHERE AD_Element_ID=576100 AND IsCentrallyMaintained='Y'
;

-- 2019-03-20T11:21:11.791
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Anruf Planung Version', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576100) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576100)
;

-- 2019-03-20T11:21:11.803
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Anruf Planung Version', Name='Anruf Planung Version' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576100)
;

-- 2019-03-20T11:21:11.804
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Anruf Planung Version', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576100
;

-- 2019-03-20T11:21:11.807
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Anruf Planung Version', Description=NULL, Help=NULL WHERE AD_Element_ID = 576100
;

-- 2019-03-20T11:21:11.808
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Anruf Planung Version', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576100
;

-- 2019-03-20T11:21:50.215
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Trl SET Name='Anrufliste',Updated=TO_TIMESTAMP('2019-03-20 11:21:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Table_ID=541176
;

-- 2019-03-20T11:21:54.912
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Trl SET Name='Anrufliste',Updated=TO_TIMESTAMP('2019-03-20 11:21:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Table_ID=541176
;

-- 2019-03-20T11:22:01.337
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Name='Anrufliste',Updated=TO_TIMESTAMP('2019-03-20 11:22:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541176
;

-- 2019-03-20T11:22:18.474
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Anrufliste', PrintName='Anrufliste',Updated=TO_TIMESTAMP('2019-03-20 11:22:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576121 AND AD_Language='de_CH'
;

-- 2019-03-20T11:22:18.477
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576121,'de_CH') 
;

-- 2019-03-20T11:22:24.512
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Anrufliste', PrintName='Anrufliste',Updated=TO_TIMESTAMP('2019-03-20 11:22:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576121 AND AD_Language='de_DE'
;

-- 2019-03-20T11:22:24.516
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576121,'de_DE') 
;

-- 2019-03-20T11:22:24.544
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576121,'de_DE') 
;

-- 2019-03-20T11:22:24.546
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_Phonecall_Schedule_ID', Name='Anrufliste', Description=NULL, Help=NULL WHERE AD_Element_ID=576121
;

-- 2019-03-20T11:22:24.547
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Phonecall_Schedule_ID', Name='Anrufliste', Description=NULL, Help=NULL, AD_Element_ID=576121 WHERE UPPER(ColumnName)='C_PHONECALL_SCHEDULE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-03-20T11:22:24.548
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Phonecall_Schedule_ID', Name='Anrufliste', Description=NULL, Help=NULL WHERE AD_Element_ID=576121 AND IsCentrallyMaintained='Y'
;

-- 2019-03-20T11:22:24.549
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Anrufliste', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576121) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576121)
;

-- 2019-03-20T11:22:24.561
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Anrufliste', Name='Anrufliste' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576121)
;

-- 2019-03-20T11:22:24.562
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Anrufliste', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576121
;

-- 2019-03-20T11:22:24.563
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Anrufliste', Description=NULL, Help=NULL WHERE AD_Element_ID = 576121
;

-- 2019-03-20T11:22:24.564
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Anrufliste', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576121
;

-- 2019-03-20T11:26:31.671
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Anrufliste', PrintName='Anrufliste',Updated=TO_TIMESTAMP('2019-03-20 11:26:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576227 AND AD_Language='de_CH'
;

-- 2019-03-20T11:26:31.674
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576227,'de_CH') 
;

-- 2019-03-20T11:26:36.346
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Anrufliste', PrintName='Anrufliste',Updated=TO_TIMESTAMP('2019-03-20 11:26:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576227 AND AD_Language='de_DE'
;

-- 2019-03-20T11:26:36.348
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576227,'de_DE') 
;

-- 2019-03-20T11:26:36.370
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576227,'de_DE') 
;

-- 2019-03-20T11:26:36.373
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Anrufliste', Description=NULL, Help=NULL WHERE AD_Element_ID=576227
;

-- 2019-03-20T11:26:36.375
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Anrufliste', Description=NULL, Help=NULL WHERE AD_Element_ID=576227 AND IsCentrallyMaintained='Y'
;

-- 2019-03-20T11:26:36.377
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Anrufliste', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576227) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576227)
;

-- 2019-03-20T11:26:36.392
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Anrufliste', Name='Anrufliste' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576227)
;

-- 2019-03-20T11:26:36.394
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Anrufliste', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576227
;

-- 2019-03-20T11:26:36.397
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Anrufliste', Description=NULL, Help=NULL WHERE AD_Element_ID = 576227
;

-- 2019-03-20T11:26:36.403
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Anrufliste', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576227
;

-- 2019-03-20T11:28:06.274
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Description='Prospects list',Updated=TO_TIMESTAMP('2019-03-20 11:28:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541217
;

-- 2019-03-20T11:28:51.097
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET AD_Element_ID=576098, Description=NULL, Name='Anruf Planung',Updated=TO_TIMESTAMP('2019-03-20 11:28:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541217
;

-- 2019-03-20T11:28:51.103
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_menu_translation_from_ad_element(576098) 
;

-- 2019-03-20T11:29:25.233
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET AD_Element_ID=576100, Name='Anruf Planung Version',Updated=TO_TIMESTAMP('2019-03-20 11:29:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541218
;

-- 2019-03-20T11:29:25.239
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_menu_translation_from_ad_element(576100) 
;

-- 2019-03-20T11:29:52.525
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000023 AND AD_Tree_ID=10
;

-- 2019-03-20T11:29:52.527
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540815 AND AD_Tree_ID=10
;

-- 2019-03-20T11:29:52.528
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541211 AND AD_Tree_ID=10
;

-- 2019-03-20T11:29:52.529
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000042 AND AD_Tree_ID=10
;

-- 2019-03-20T11:29:52.529
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000024 AND AD_Tree_ID=10
;

-- 2019-03-20T11:29:52.530
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000025 AND AD_Tree_ID=10
;

-- 2019-03-20T11:29:52.530
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541200 AND AD_Tree_ID=10
;

-- 2019-03-20T11:29:52.531
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541178 AND AD_Tree_ID=10
;

-- 2019-03-20T11:29:52.532
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541217 AND AD_Tree_ID=10
;

-- 2019-03-20T11:29:52.553
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541210 AND AD_Tree_ID=10
;

-- 2019-03-20T11:29:55.802
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000023 AND AD_Tree_ID=10
;

-- 2019-03-20T11:29:55.803
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540815 AND AD_Tree_ID=10
;

-- 2019-03-20T11:29:55.804
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541211 AND AD_Tree_ID=10
;

-- 2019-03-20T11:29:55.805
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000042 AND AD_Tree_ID=10
;

-- 2019-03-20T11:29:55.805
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000024 AND AD_Tree_ID=10
;

-- 2019-03-20T11:29:55.806
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000025 AND AD_Tree_ID=10
;

-- 2019-03-20T11:29:55.807
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541200 AND AD_Tree_ID=10
;

-- 2019-03-20T11:29:55.807
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541178 AND AD_Tree_ID=10
;

-- 2019-03-20T11:29:55.808
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541217 AND AD_Tree_ID=10
;

-- 2019-03-20T11:29:55.809
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541218 AND AD_Tree_ID=10
;

-- 2019-03-20T11:29:55.809
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541210 AND AD_Tree_ID=10
;

-- 2019-03-20T11:29:57.838
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000023 AND AD_Tree_ID=10
;

-- 2019-03-20T11:29:57.838
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540815 AND AD_Tree_ID=10
;

-- 2019-03-20T11:29:57.839
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541211 AND AD_Tree_ID=10
;

-- 2019-03-20T11:29:57.840
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000042 AND AD_Tree_ID=10
;

-- 2019-03-20T11:29:57.841
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000024 AND AD_Tree_ID=10
;

-- 2019-03-20T11:29:57.841
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000025 AND AD_Tree_ID=10
;

-- 2019-03-20T11:29:57.842
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541200 AND AD_Tree_ID=10
;

-- 2019-03-20T11:29:57.843
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541178 AND AD_Tree_ID=10
;

-- 2019-03-20T11:29:57.844
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541217 AND AD_Tree_ID=10
;

-- 2019-03-20T11:29:57.844
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541218 AND AD_Tree_ID=10
;

-- 2019-03-20T11:29:57.845
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541219 AND AD_Tree_ID=10
;

-- 2019-03-20T11:29:57.846
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541210 AND AD_Tree_ID=10
;

-- 2019-03-20T11:30:00.397
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000023 AND AD_Tree_ID=10
;

-- 2019-03-20T11:30:00.398
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540815 AND AD_Tree_ID=10
;

-- 2019-03-20T11:30:00.399
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541211 AND AD_Tree_ID=10
;

-- 2019-03-20T11:30:00.400
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000042 AND AD_Tree_ID=10
;

-- 2019-03-20T11:30:00.402
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000024 AND AD_Tree_ID=10
;

-- 2019-03-20T11:30:00.403
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000025 AND AD_Tree_ID=10
;

-- 2019-03-20T11:30:00.404
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541200 AND AD_Tree_ID=10
;

-- 2019-03-20T11:30:00.405
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541178 AND AD_Tree_ID=10
;

-- 2019-03-20T11:30:00.406
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541210 AND AD_Tree_ID=10
;

-- 2019-03-20T11:30:00.408
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541217 AND AD_Tree_ID=10
;

-- 2019-03-20T11:30:00.409
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541218 AND AD_Tree_ID=10
;

-- 2019-03-20T11:30:00.410
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541219 AND AD_Tree_ID=10
;




-- 2019-03-20T11:32:38.608
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Anruflisten generieren',Updated=TO_TIMESTAMP('2019-03-20 11:32:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=541073
;

-- 2019-03-20T11:32:43.008
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Anruflisten generieren',Updated=TO_TIMESTAMP('2019-03-20 11:32:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=541073
;

-- 2019-03-20T11:32:57.599
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Create Phonecall Schedules',Updated=TO_TIMESTAMP('2019-03-20 11:32:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=541073
;

-- 2019-03-20T11:33:01.887
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Anruflisten generieren',Updated=TO_TIMESTAMP('2019-03-20 11:33:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=541073
;

-- 2019-03-20T11:33:39.025
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Auftrag erstellen',Updated=TO_TIMESTAMP('2019-03-20 11:33:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=541080
;

-- 2019-03-20T11:33:42.224
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Auftrag erstellen',Updated=TO_TIMESTAMP('2019-03-20 11:33:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=541080
;

-- 2019-03-20T11:33:49.569
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Create Sales Order',Updated=TO_TIMESTAMP('2019-03-20 11:33:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=541080
;

-- 2019-03-20T11:33:52.889
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Auftrag erstellen',Updated=TO_TIMESTAMP('2019-03-20 11:33:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=541080
;

-- 2019-03-20T11:35:15.568
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Name='Anruf Planung Version Position',Updated=TO_TIMESTAMP('2019-03-20 11:35:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541178
;

-- 2019-03-20T11:35:57.369
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Anruf Planung Version Position', PrintName='Anruf Planung Version Position',Updated=TO_TIMESTAMP('2019-03-20 11:35:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576119 AND AD_Language='de_CH'
;

-- 2019-03-20T11:35:57.372
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576119,'de_CH') 
;

-- 2019-03-20T11:35:59.009
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-03-20 11:35:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576119 AND AD_Language='de_CH'
;

-- 2019-03-20T11:35:59.012
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576119,'de_CH') 
;

-- 2019-03-20T11:36:02.945
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Anruf Planung Version Position', PrintName='Anruf Planung Version Position',Updated=TO_TIMESTAMP('2019-03-20 11:36:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576119 AND AD_Language='de_DE'
;

-- 2019-03-20T11:36:02.948
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576119,'de_DE') 
;

-- 2019-03-20T11:36:02.967
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576119,'de_DE') 
;

-- 2019-03-20T11:36:02.968
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_Phonecall_Schema_Version_Line_ID', Name='Anruf Planung Version Position', Description=NULL, Help=NULL WHERE AD_Element_ID=576119
;

-- 2019-03-20T11:36:02.969
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Phonecall_Schema_Version_Line_ID', Name='Anruf Planung Version Position', Description=NULL, Help=NULL, AD_Element_ID=576119 WHERE UPPER(ColumnName)='C_PHONECALL_SCHEMA_VERSION_LINE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-03-20T11:36:02.970
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Phonecall_Schema_Version_Line_ID', Name='Anruf Planung Version Position', Description=NULL, Help=NULL WHERE AD_Element_ID=576119 AND IsCentrallyMaintained='Y'
;

-- 2019-03-20T11:36:02.970
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Anruf Planung Version Position', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576119) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576119)
;

-- 2019-03-20T11:36:02.980
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Anruf Planung Version Position', Name='Anruf Planung Version Position' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576119)
;

-- 2019-03-20T11:36:02.981
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Anruf Planung Version Position', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576119
;

-- 2019-03-20T11:36:02.983
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Anruf Planung Version Position', Description=NULL, Help=NULL WHERE AD_Element_ID = 576119
;

-- 2019-03-20T11:36:02.983
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Anruf Planung Version Position', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576119
;

-- 2019-03-20T11:36:15.625
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Phonecall Schema Version Line', PrintName='Phonecall Schema Version Line',Updated=TO_TIMESTAMP('2019-03-20 11:36:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576119 AND AD_Language='en_US'
;

-- 2019-03-20T11:36:15.628
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576119,'en_US') 
;

-- 2019-03-20T11:36:21.169
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Phonecall Schema Version Line', PrintName='Phonecall Schema Version Line',Updated=TO_TIMESTAMP('2019-03-20 11:36:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576119 AND AD_Language='nl_NL'
;

-- 2019-03-20T11:36:21.171
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576119,'nl_NL') 
;



