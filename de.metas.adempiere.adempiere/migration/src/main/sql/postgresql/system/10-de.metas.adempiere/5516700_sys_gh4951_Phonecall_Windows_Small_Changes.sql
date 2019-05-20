-- 2019-03-20T17:38:38.971
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Phonecall Schema Version', PrintName='Phonecall Schema Version',Updated=TO_TIMESTAMP('2019-03-20 17:38:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576100 AND AD_Language='en_US'
;

-- 2019-03-20T17:38:39.052
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576100,'en_US') 
;

-- 2019-03-20T17:46:34.523
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2019-03-20 17:46:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=564474
;

-- 2019-03-20T17:56:33.806
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2019-03-20 17:56:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557980
;

-- 2019-03-20T17:56:33.816
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2019-03-20 17:56:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557982
;

-- 2019-03-20T17:56:33.816
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2019-03-20 17:56:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557951
;

-- 2019-03-20T17:56:33.826
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2019-03-20 17:56:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557946
;

-- 2019-03-20T17:56:33.826
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2019-03-20 17:56:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557986
;

-- 2019-03-20T17:56:33.836
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2019-03-20 17:56:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557987
;

-- 2019-03-20T17:56:33.836
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2019-03-20 17:56:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557945
;

-- 2019-03-20T18:31:05.548
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Erreichbar von', PrintName='Erreichbar von',Updated=TO_TIMESTAMP('2019-03-20 18:31:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576122 AND AD_Language='de_DE'
;

-- 2019-03-20T18:31:05.548
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576122,'de_DE') 
;

-- 2019-03-20T18:31:05.559
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576122,'de_DE') 
;

-- 2019-03-20T18:31:05.559
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PhonecallTimeMin', Name='Erreichbar von', Description=NULL, Help=NULL WHERE AD_Element_ID=576122
;

-- 2019-03-20T18:31:05.569
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PhonecallTimeMin', Name='Erreichbar von', Description=NULL, Help=NULL, AD_Element_ID=576122 WHERE UPPER(ColumnName)='PHONECALLTIMEMIN' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-03-20T18:31:05.569
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PhonecallTimeMin', Name='Erreichbar von', Description=NULL, Help=NULL WHERE AD_Element_ID=576122 AND IsCentrallyMaintained='Y'
;

-- 2019-03-20T18:31:05.569
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Erreichbar von', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576122) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576122)
;

-- 2019-03-20T18:31:05.619
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Erreichbar von', Name='Erreichbar von' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576122)
;

-- 2019-03-20T18:31:05.619
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Erreichbar von', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576122
;

-- 2019-03-20T18:31:05.619
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Erreichbar von', Description=NULL, Help=NULL WHERE AD_Element_ID = 576122
;

-- 2019-03-20T18:31:05.629
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Erreichbar von', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576122
;

-- 2019-03-20T18:31:30.980
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Minimum Call Time', PrintName='Minimum Call Time',Updated=TO_TIMESTAMP('2019-03-20 18:31:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576122 AND AD_Language='en_US'
;

-- 2019-03-20T18:31:30.980
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576122,'en_US') 
;

-- 2019-03-20T18:31:33.634
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-03-20 18:31:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576122 AND AD_Language='en_US'
;

-- 2019-03-20T18:31:33.644
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576122,'en_US') 
;

-- 2019-03-20T18:31:43.560
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Erreichbar von', PrintName='Erreichbar von',Updated=TO_TIMESTAMP('2019-03-20 18:31:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576122 AND AD_Language='nl_NL'
;

-- 2019-03-20T18:31:43.560
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576122,'nl_NL') 
;

-- 2019-03-20T18:31:49.322
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Erreichbar von', PrintName='Erreichbar von',Updated=TO_TIMESTAMP('2019-03-20 18:31:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576122 AND AD_Language='de_CH'
;

-- 2019-03-20T18:31:49.322
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576122,'de_CH') 
;

-- 2019-03-20T18:32:11.484
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Erreichbar bis', PrintName='Erreichbar bis',Updated=TO_TIMESTAMP('2019-03-20 18:32:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576123 AND AD_Language='de_CH'
;

-- 2019-03-20T18:32:11.494
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576123,'de_CH') 
;

-- 2019-03-20T18:32:15.537
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Erreichbar bis', PrintName='Erreichbar bis',Updated=TO_TIMESTAMP('2019-03-20 18:32:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576123 AND AD_Language='de_DE'
;

-- 2019-03-20T18:32:15.547
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576123,'de_DE') 
;

-- 2019-03-20T18:32:15.568
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576123,'de_DE') 
;

-- 2019-03-20T18:32:15.568
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PhonecallTimeMax', Name='Erreichbar bis', Description=NULL, Help=NULL WHERE AD_Element_ID=576123
;

-- 2019-03-20T18:32:15.568
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PhonecallTimeMax', Name='Erreichbar bis', Description=NULL, Help=NULL, AD_Element_ID=576123 WHERE UPPER(ColumnName)='PHONECALLTIMEMAX' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-03-20T18:32:15.568
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PhonecallTimeMax', Name='Erreichbar bis', Description=NULL, Help=NULL WHERE AD_Element_ID=576123 AND IsCentrallyMaintained='Y'
;

-- 2019-03-20T18:32:15.568
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Erreichbar bis', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576123) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576123)
;

-- 2019-03-20T18:32:15.578
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Erreichbar bis', Name='Erreichbar bis' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576123)
;

-- 2019-03-20T18:32:15.578
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Erreichbar bis', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576123
;

-- 2019-03-20T18:32:15.588
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Erreichbar bis', Description=NULL, Help=NULL WHERE AD_Element_ID = 576123
;

-- 2019-03-20T18:32:15.588
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Erreichbar bis', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576123
;

-- 2019-03-20T18:32:28.500
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Maximum Call Time', PrintName='Maximum Call Time',Updated=TO_TIMESTAMP('2019-03-20 18:32:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576123 AND AD_Language='en_US'
;

-- 2019-03-20T18:32:28.510
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576123,'en_US') 
;

-- 2019-03-20T18:32:33.810
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Erreichbar bis', PrintName='Erreichbar bis',Updated=TO_TIMESTAMP('2019-03-20 18:32:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576123 AND AD_Language='nl_NL'
;

-- 2019-03-20T18:32:33.820
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576123,'nl_NL') 
;











-- 2019-03-21T11:50:45.370
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Process SET WEBUI_ViewQuickAction_Default='Y',Updated=TO_TIMESTAMP('2019-03-21 11:50:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_Process_ID=540686
;

-- 2019-03-21T11:51:14.918
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=40,Updated=TO_TIMESTAMP('2019-03-21 11:51:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=564166
;

-- 2019-03-21T11:51:14.948
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=50,Updated=TO_TIMESTAMP('2019-03-21 11:51:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=564555
;

-- 2019-03-21T11:51:14.958
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=60,Updated=TO_TIMESTAMP('2019-03-21 11:51:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=564123
;











-- 2019-03-21T13:43:16.678
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Jeden x Wochen', Name='Jede Woche', PrintName='Jede Woche',Updated=TO_TIMESTAMP('2019-03-21 13:43:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541371 AND AD_Language='de_DE'
;

-- 2019-03-21T13:43:16.718
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541371,'de_DE') 
;

-- 2019-03-21T13:43:16.758
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(541371,'de_DE') 
;

-- 2019-03-21T13:43:16.758
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='EveryWeek', Name='Jede Woche', Description='Jeden x Wochen', Help=NULL WHERE AD_Element_ID=541371
;

-- 2019-03-21T13:43:16.758
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='EveryWeek', Name='Jede Woche', Description='Jeden x Wochen', Help=NULL, AD_Element_ID=541371 WHERE UPPER(ColumnName)='EVERYWEEK' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-03-21T13:43:16.768
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='EveryWeek', Name='Jede Woche', Description='Jeden x Wochen', Help=NULL WHERE AD_Element_ID=541371 AND IsCentrallyMaintained='Y'
;

-- 2019-03-21T13:43:16.768
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Jede Woche', Description='Jeden x Wochen', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=541371) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 541371)
;

-- 2019-03-21T13:43:16.908
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Jede Woche', Name='Jede Woche' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=541371)
;

-- 2019-03-21T13:43:16.908
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Jede Woche', Description='Jeden x Wochen', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 541371
;

-- 2019-03-21T13:43:16.908
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Jede Woche', Description='Jeden x Wochen', Help=NULL WHERE AD_Element_ID = 541371
;

-- 2019-03-21T13:43:16.918
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Jede Woche', Description = 'Jeden x Wochen', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 541371
;

-- 2019-03-21T13:45:29.622
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Jeden x Monate', Name='Jeden Monat', PrintName='Jeden Monat',Updated=TO_TIMESTAMP('2019-03-21 13:45:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541373 AND AD_Language='fr_CH'
;

-- 2019-03-21T13:45:29.632
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541373,'fr_CH') 
;

-- 2019-03-21T13:45:36.508
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Jede x Wochen',Updated=TO_TIMESTAMP('2019-03-21 13:45:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541371 AND AD_Language='de_DE'
;

-- 2019-03-21T13:45:36.518
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541371,'de_DE') 
;

-- 2019-03-21T13:45:36.539
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(541371,'de_DE') 
;

-- 2019-03-21T13:45:36.539
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='EveryWeek', Name='Jede Woche', Description='Jede x Wochen', Help=NULL WHERE AD_Element_ID=541371
;

-- 2019-03-21T13:45:36.539
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='EveryWeek', Name='Jede Woche', Description='Jede x Wochen', Help=NULL, AD_Element_ID=541371 WHERE UPPER(ColumnName)='EVERYWEEK' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-03-21T13:45:36.539
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='EveryWeek', Name='Jede Woche', Description='Jede x Wochen', Help=NULL WHERE AD_Element_ID=541371 AND IsCentrallyMaintained='Y'
;

-- 2019-03-21T13:45:36.539
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Jede Woche', Description='Jede x Wochen', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=541371) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 541371)
;

-- 2019-03-21T13:45:36.549
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Jede Woche', Description='Jede x Wochen', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 541371
;

-- 2019-03-21T13:45:36.549
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Jede Woche', Description='Jede x Wochen', Help=NULL WHERE AD_Element_ID = 541371
;

-- 2019-03-21T13:45:36.549
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Jede Woche', Description = 'Jede x Wochen', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 541371
;

-- 2019-03-21T13:47:56.900
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Tag des Monats 1 to 28/29/30/31', Name='Tag des Monats',Updated=TO_TIMESTAMP('2019-03-21 13:47:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2454 AND AD_Language='de_DE'
;

-- 2019-03-21T13:47:56.900
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2454,'de_DE') 
;

-- 2019-03-21T13:47:56.931
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(2454,'de_DE') 
;

-- 2019-03-21T13:47:56.931
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='MonthDay', Name='Tag des Monats', Description='Tag des Monats 1 to 28/29/30/31', Help=NULL WHERE AD_Element_ID=2454
;

-- 2019-03-21T13:47:56.941
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='MonthDay', Name='Tag des Monats', Description='Tag des Monats 1 to 28/29/30/31', Help=NULL, AD_Element_ID=2454 WHERE UPPER(ColumnName)='MONTHDAY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-03-21T13:47:56.941
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='MonthDay', Name='Tag des Monats', Description='Tag des Monats 1 to 28/29/30/31', Help=NULL WHERE AD_Element_ID=2454 AND IsCentrallyMaintained='Y'
;

-- 2019-03-21T13:47:56.941
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Tag des Monats', Description='Tag des Monats 1 to 28/29/30/31', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=2454) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 2454)
;

-- 2019-03-21T13:47:56.951
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Tag des Monats', Name='Tag des Monats' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=2454)
;

-- 2019-03-21T13:47:56.951
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Tag des Monats', Description='Tag des Monats 1 to 28/29/30/31', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 2454
;

-- 2019-03-21T13:47:56.951
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Tag des Monats', Description='Tag des Monats 1 to 28/29/30/31', Help=NULL WHERE AD_Element_ID = 2454
;

-- 2019-03-21T13:47:56.951
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Tag des Monats', Description = 'Tag des Monats 1 to 28/29/30/31', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 2454
;

-- 2019-03-21T13:48:25.533
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Wöchentlich', PrintName='Wöchentlich',Updated=TO_TIMESTAMP('2019-03-21 13:48:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541370 AND AD_Language='de_DE'
;

-- 2019-03-21T13:48:25.533
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541370,'de_DE') 
;

-- 2019-03-21T13:48:25.543
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(541370,'de_DE') 
;

-- 2019-03-21T13:48:25.543
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsWeekly', Name='Wöchentlich', Description=NULL, Help=NULL WHERE AD_Element_ID=541370
;

-- 2019-03-21T13:48:25.543
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsWeekly', Name='Wöchentlich', Description=NULL, Help=NULL, AD_Element_ID=541370 WHERE UPPER(ColumnName)='ISWEEKLY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-03-21T13:48:25.543
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsWeekly', Name='Wöchentlich', Description=NULL, Help=NULL WHERE AD_Element_ID=541370 AND IsCentrallyMaintained='Y'
;

-- 2019-03-21T13:48:25.543
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Wöchentlich', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=541370) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 541370)
;

-- 2019-03-21T13:48:25.554
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Wöchentlich', Name='Wöchentlich' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=541370)
;

-- 2019-03-21T13:48:25.554
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Wöchentlich', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 541370
;

-- 2019-03-21T13:48:25.554
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Wöchentlich', Description=NULL, Help=NULL WHERE AD_Element_ID = 541370
;

-- 2019-03-21T13:48:25.554
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Wöchentlich', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 541370
;

-- 2019-03-21T13:48:52.104
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Monatlich', PrintName='Monatlich',Updated=TO_TIMESTAMP('2019-03-21 13:48:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541372 AND AD_Language='de_DE'
;

-- 2019-03-21T13:48:52.104
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541372,'de_DE') 
;

-- 2019-03-21T13:48:52.135
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(541372,'de_DE') 
;

-- 2019-03-21T13:48:52.135
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsMonthly', Name='Monatlich', Description=NULL, Help=NULL WHERE AD_Element_ID=541372
;

-- 2019-03-21T13:48:52.145
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsMonthly', Name='Monatlich', Description=NULL, Help=NULL, AD_Element_ID=541372 WHERE UPPER(ColumnName)='ISMONTHLY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-03-21T13:48:52.145
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsMonthly', Name='Monatlich', Description=NULL, Help=NULL WHERE AD_Element_ID=541372 AND IsCentrallyMaintained='Y'
;

-- 2019-03-21T13:48:52.145
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Monatlich', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=541372) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 541372)
;

-- 2019-03-21T13:48:52.175
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Monatlich', Name='Monatlich' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=541372)
;

-- 2019-03-21T13:48:52.175
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Monatlich', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 541372
;

-- 2019-03-21T13:48:52.175
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Monatlich', Description=NULL, Help=NULL WHERE AD_Element_ID = 541372
;

-- 2019-03-21T13:48:52.175
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Monatlich', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 541372
;



