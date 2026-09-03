-- 2019-02-19T12:04:58.924
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-19 12:04:58','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Lagerart',PrintName='Lagerart' WHERE AD_Element_ID=544253 AND AD_Language='de_CH'
;

-- 2019-02-19T12:04:59.381
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544253,'de_CH') 
;

-- 2019-02-19T12:05:04.707
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-19 12:05:04','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=544253 AND AD_Language='en_US'
;

-- 2019-02-19T12:05:04.714
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544253,'en_US') 
;

-- 2019-02-19T12:05:13.114
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-19 12:05:13','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Lagerart',PrintName='Lagerart' WHERE AD_Element_ID=544253 AND AD_Language='de_DE'
;

-- 2019-02-19T12:05:13.137
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544253,'de_DE') 
;

-- 2019-02-19T12:05:13.173
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(544253,'de_DE') 
;

-- 2019-02-19T12:05:13.179
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='M_Warehouse_Type_ID', Name='Lagerart', Description=NULL, Help=NULL WHERE AD_Element_ID=544253
;

-- 2019-02-19T12:05:13.185
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_Warehouse_Type_ID', Name='Lagerart', Description=NULL, Help=NULL, AD_Element_ID=544253 WHERE UPPER(ColumnName)='M_WAREHOUSE_TYPE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-02-19T12:05:13.191
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_Warehouse_Type_ID', Name='Lagerart', Description=NULL, Help=NULL WHERE AD_Element_ID=544253 AND IsCentrallyMaintained='Y'
;

-- 2019-02-19T12:05:13.196
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Lagerart', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544253) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544253)
;

-- 2019-02-19T12:05:13.216
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Lagerart', Name='Lagerart' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=544253)
;

-- 2019-02-19T12:05:13.222
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Lagerart', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 544253
;

-- 2019-02-19T12:05:13.228
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Lagerart', Description=NULL, Help=NULL WHERE AD_Element_ID = 544253
;

-- 2019-02-19T12:05:13.233
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name='Lagerart', Description=NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 544253
;

-- 2019-02-19T12:06:33.861
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-19 12:06:33','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Mitarbeiter',PrintName='Mitarbeiter' WHERE AD_Element_ID=575854 AND AD_Language='de_DE'
;

-- 2019-02-19T12:06:33.885
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575854,'de_DE') 
;

-- 2019-02-19T12:06:33.914
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(575854,'de_DE') 
;

-- 2019-02-19T12:06:33.926
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Picking_User_ID', Name='Mitarbeiter', Description=NULL, Help=NULL WHERE AD_Element_ID=575854
;

-- 2019-02-19T12:06:33.932
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Picking_User_ID', Name='Mitarbeiter', Description=NULL, Help=NULL, AD_Element_ID=575854 WHERE UPPER(ColumnName)='PICKING_USER_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-02-19T12:06:33.938
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Picking_User_ID', Name='Mitarbeiter', Description=NULL, Help=NULL WHERE AD_Element_ID=575854 AND IsCentrallyMaintained='Y'
;

-- 2019-02-19T12:06:33.943
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Mitarbeiter', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=575854) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 575854)
;

-- 2019-02-19T12:06:33.956
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Mitarbeiter', Name='Mitarbeiter' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=575854)
;

-- 2019-02-19T12:06:33.962
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Mitarbeiter', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 575854
;

-- 2019-02-19T12:06:33.968
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Mitarbeiter', Description=NULL, Help=NULL WHERE AD_Element_ID = 575854
;

-- 2019-02-19T12:06:33.974
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name='Mitarbeiter', Description=NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 575854
;

-- 2019-02-19T12:06:40.234
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-19 12:06:40','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=575854 AND AD_Language='en_US'
;

-- 2019-02-19T12:06:40.248
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575854,'en_US') 
;

-- 2019-02-19T12:06:45.410
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-19 12:06:45','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Mitarbeiter',PrintName='Mitarbeiter' WHERE AD_Element_ID=575854 AND AD_Language='de_CH'
;

-- 2019-02-19T12:06:45.425
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575854,'de_CH') 
;

-- 2019-02-19T12:12:59.549
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-19 12:12:59','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Pck. Gr.',PrintName='Pck. Gr.',Description='',Help='' WHERE AD_Element_ID=543503 AND AD_Language='de_CH'
;

-- 2019-02-19T12:12:59.565
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543503,'de_CH') 
;

-- 2019-02-19T12:13:04.261
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-19 12:13:04','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=543503 AND AD_Language='en_US'
;

-- 2019-02-19T12:13:04.276
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543503,'en_US') 
;

-- 2019-02-19T12:13:12.953
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-19 12:13:12','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Pck. Gr.',PrintName='Pck. Gr.',Description='',Help='' WHERE AD_Element_ID=543503 AND AD_Language='de_DE'
;

-- 2019-02-19T12:13:12.974
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543503,'de_DE') 
;

-- 2019-02-19T12:13:13.011
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(543503,'de_DE') 
;

-- 2019-02-19T12:13:13.021
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PackageSize', Name='Pck. Gr.', Description='', Help='' WHERE AD_Element_ID=543503
;

-- 2019-02-19T12:13:13.030
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PackageSize', Name='Pck. Gr.', Description='', Help='', AD_Element_ID=543503 WHERE UPPER(ColumnName)='PACKAGESIZE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-02-19T12:13:13.038
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PackageSize', Name='Pck. Gr.', Description='', Help='' WHERE AD_Element_ID=543503 AND IsCentrallyMaintained='Y'
;

-- 2019-02-19T12:13:13.044
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Pck. Gr.', Description='', Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543503) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543503)
;

-- 2019-02-19T12:13:13.065
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Pck. Gr.', Name='Pck. Gr.' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543503)
;

-- 2019-02-19T12:13:13.073
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Pck. Gr.', Description='', Help='', CommitWarning = NULL WHERE AD_Element_ID = 543503
;

-- 2019-02-19T12:13:13.080
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Pck. Gr.', Description='', Help='' WHERE AD_Element_ID = 543503
;

-- 2019-02-19T12:13:13.087
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name='Pck. Gr.', Description='', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 543503
;

-- 2019-02-19T12:15:12.361
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-19 12:15:12','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Komm. ausstehend' WHERE AD_Ref_List_ID=541742 AND AD_Language='de_CH'
;

-- 2019-02-19T12:15:17.025
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-19 12:15:17','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Ref_List_ID=541742 AND AD_Language='en_US'
;

-- 2019-02-19T12:15:20.894
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-19 12:15:20','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Komm. ausstehend' WHERE AD_Ref_List_ID=541742 AND AD_Language='de_DE'
;

-- 2019-02-19T12:15:36.591
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Komm. ausstehend',Updated=TO_TIMESTAMP('2019-02-19 12:15:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541742
;

-- 2019-02-19T12:16:51.478
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-19 12:16:51','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Komm. Status',PrintName='Komm. Status' WHERE AD_Element_ID=544442 AND AD_Language='de_DE'
;

-- 2019-02-19T12:16:51.500
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544442,'de_DE') 
;

-- 2019-02-19T12:16:51.520
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(544442,'de_DE') 
;

-- 2019-02-19T12:16:51.526
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PickStatus', Name='Komm. Status', Description=NULL, Help=NULL WHERE AD_Element_ID=544442
;

-- 2019-02-19T12:16:51.534
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PickStatus', Name='Komm. Status', Description=NULL, Help=NULL, AD_Element_ID=544442 WHERE UPPER(ColumnName)='PICKSTATUS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-02-19T12:16:51.541
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PickStatus', Name='Komm. Status', Description=NULL, Help=NULL WHERE AD_Element_ID=544442 AND IsCentrallyMaintained='Y'
;

-- 2019-02-19T12:16:51.547
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Komm. Status', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544442) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544442)
;

-- 2019-02-19T12:16:51.562
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Komm. Status', Name='Komm. Status' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=544442)
;

-- 2019-02-19T12:16:51.570
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Komm. Status', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 544442
;

-- 2019-02-19T12:16:51.576
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Komm. Status', Description=NULL, Help=NULL WHERE AD_Element_ID = 544442
;

-- 2019-02-19T12:16:51.584
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name='Komm. Status', Description=NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 544442
;

-- 2019-02-19T12:16:58.601
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-19 12:16:58','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=544442 AND AD_Language='en_US'
;

-- 2019-02-19T12:16:58.619
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544442,'en_US') 
;

-- 2019-02-19T12:17:07.024
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-19 12:17:07','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Komm. Status',PrintName='Komm. Status' WHERE AD_Element_ID=544442 AND AD_Language='de_CH'
;

-- 2019-02-19T12:17:07.045
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544442,'de_CH') 
;

-- 2019-02-19T12:17:54.614
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Gepackt',Updated=TO_TIMESTAMP('2019-02-19 12:17:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541749
;

-- 2019-02-19T12:18:02.711
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-19 12:18:02','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Gepackt' WHERE AD_Ref_List_ID=541749 AND AD_Language='de_DE'
;

-- 2019-02-19T12:18:06.959
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-19 12:18:06','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Ref_List_ID=541749 AND AD_Language='en_US'
;

-- 2019-02-19T12:18:13.200
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-19 12:18:13','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Gepackt' WHERE AD_Ref_List_ID=541749 AND AD_Language='de_CH'
;

-- 2019-02-19T12:20:27.696
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-19 12:20:27','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Klärungsfall' WHERE AD_Ref_List_ID=541744 AND AD_Language='de_CH'
;

-- 2019-02-19T12:20:31.251
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-19 12:20:31','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Ref_List_ID=541744 AND AD_Language='en_US'
;

-- 2019-02-19T12:20:36.497
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-19 12:20:36','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Klärungsfall' WHERE AD_Ref_List_ID=541744 AND AD_Language='de_DE'
;

-- 2019-02-19T12:21:04.086
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Klärungsfall',Updated=TO_TIMESTAMP('2019-02-19 12:21:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541744
;

-- 2019-02-19T12:24:11.281
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Komm. OK',Updated=TO_TIMESTAMP('2019-02-19 12:24:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541743
;

-- 2019-02-19T12:24:18.266
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-19 12:24:18','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Komm. OK' WHERE AD_Ref_List_ID=541743 AND AD_Language='de_CH'
;

-- 2019-02-19T12:24:21.217
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-19 12:24:21','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Komm. OK' WHERE AD_Ref_List_ID=541743 AND AD_Language='de_DE'
;

-- 2019-02-19T12:24:29.991
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-19 12:24:29','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Ref_List_ID=541743 AND AD_Language='en_US'
;

-- 2019-02-19T12:27:08.881
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Klärung',Updated=TO_TIMESTAMP('2019-02-19 12:27:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541017
;

-- 2019-02-19T12:27:24.513
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-19 12:27:24','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Klärung' WHERE AD_Process_ID=541017 AND AD_Language='de_CH'
;

-- 2019-02-19T12:27:25.514
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-19 12:27:25','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Process_ID=541017 AND AD_Language='en_US'
;

-- 2019-02-19T12:27:27.796
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-19 12:27:27','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Klärung' WHERE AD_Process_ID=541017 AND AD_Language='de_DE'
;

-- 2019-02-19T12:28:10.031
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='4 Augen starten',Updated=TO_TIMESTAMP('2019-02-19 12:28:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541012
;

-- 2019-02-19T12:28:20.181
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-19 12:28:20','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='4 Augen starten' WHERE AD_Process_ID=541012 AND AD_Language='de_CH'
;

-- 2019-02-19T12:28:25.060
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-19 12:28:25','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='4 Augen starten' WHERE AD_Process_ID=541012 AND AD_Language='de_DE'
;

-- 2019-02-19T12:28:26.065
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-19 12:28:26','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Process_ID=541012 AND AD_Language='en_US'
;

-- 2019-02-19T12:30:20.978
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-19 12:30:20','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Status',PrintName='Status' WHERE AD_Element_ID=544443 AND AD_Language='de_CH'
;

-- 2019-02-19T12:30:20.989
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544443,'de_CH') 
;

-- 2019-02-19T12:30:30.526
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-19 12:30:30','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=544443 AND AD_Language='en_US'
;

-- 2019-02-19T12:30:30.542
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544443,'en_US') 
;

-- 2019-02-19T12:30:35.734
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-19 12:30:35','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Status',PrintName='Status' WHERE AD_Element_ID=544443 AND AD_Language='de_DE'
;

-- 2019-02-19T12:30:35.752
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544443,'de_DE') 
;

-- 2019-02-19T12:30:35.782
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(544443,'de_DE') 
;

-- 2019-02-19T12:30:35.790
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ApprovalStatus', Name='Status', Description=NULL, Help=NULL WHERE AD_Element_ID=544443
;

-- 2019-02-19T12:30:35.797
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ApprovalStatus', Name='Status', Description=NULL, Help=NULL, AD_Element_ID=544443 WHERE UPPER(ColumnName)='APPROVALSTATUS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-02-19T12:30:35.804
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ApprovalStatus', Name='Status', Description=NULL, Help=NULL WHERE AD_Element_ID=544443 AND IsCentrallyMaintained='Y'
;

-- 2019-02-19T12:30:35.810
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Status', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544443) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544443)
;

-- 2019-02-19T12:30:35.827
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Status', Name='Status' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=544443)
;

-- 2019-02-19T12:30:35.833
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Status', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 544443
;

-- 2019-02-19T12:30:35.840
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Status', Description=NULL, Help=NULL WHERE AD_Element_ID = 544443
;

-- 2019-02-19T12:30:35.845
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name='Status', Description=NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 544443
;

-- 2019-02-19T12:30:59.452
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='NOT OK',Updated=TO_TIMESTAMP('2019-02-19 12:30:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541747
;

-- 2019-02-19T12:31:05.161
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-19 12:31:05','YYYY-MM-DD HH24:MI:SS'),Name='NOT OK' WHERE AD_Ref_List_ID=541747 AND AD_Language='de_CH'
;

-- 2019-02-19T12:31:07.010
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-19 12:31:07','YYYY-MM-DD HH24:MI:SS'),Name='NOT OK' WHERE AD_Ref_List_ID=541747 AND AD_Language='nl_NL'
;

-- 2019-02-19T12:31:08.774
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-19 12:31:08','YYYY-MM-DD HH24:MI:SS'),Name='NOT OK' WHERE AD_Ref_List_ID=541747 AND AD_Language='en_US'
;

-- 2019-02-19T12:31:12.296
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-19 12:31:12','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='NOT OK' WHERE AD_Ref_List_ID=541747 AND AD_Language='de_DE'
;

-- 2019-02-19T12:31:13.505
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-19 12:31:13','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Ref_List_ID=541747 AND AD_Language='en_US'
;

-- 2019-02-19T12:31:14.497
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-19 12:31:14','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Ref_List_ID=541747 AND AD_Language='de_CH'
;

-- 2019-02-19T12:31:25.763
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='OK',Updated=TO_TIMESTAMP('2019-02-19 12:31:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541746
;

-- 2019-02-19T12:31:31.828
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-19 12:31:31','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='OK' WHERE AD_Ref_List_ID=541746 AND AD_Language='de_CH'
;

-- 2019-02-19T12:31:34.126
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-19 12:31:34','YYYY-MM-DD HH24:MI:SS'),Name='OK' WHERE AD_Ref_List_ID=541746 AND AD_Language='nl_NL'
;

-- 2019-02-19T12:31:37.381
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-19 12:31:37','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='OK' WHERE AD_Ref_List_ID=541746 AND AD_Language='en_US'
;

-- 2019-02-19T12:31:40.397
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-19 12:31:40','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='OK' WHERE AD_Ref_List_ID=541746 AND AD_Language='de_DE'
;

-- 2019-02-19T12:31:53.924
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='open',Updated=TO_TIMESTAMP('2019-02-19 12:31:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541745
;

-- 2019-02-19T12:31:58.638
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-19 12:31:58','YYYY-MM-DD HH24:MI:SS'),Name='open' WHERE AD_Ref_List_ID=541745 AND AD_Language='de_CH'
;

-- 2019-02-19T12:32:00.986
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-19 12:32:00','YYYY-MM-DD HH24:MI:SS'),Name='open' WHERE AD_Ref_List_ID=541745 AND AD_Language='nl_NL'
;

-- 2019-02-19T12:32:02.585
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-19 12:32:02','YYYY-MM-DD HH24:MI:SS'),Name='open' WHERE AD_Ref_List_ID=541745 AND AD_Language='en_US'
;

-- 2019-02-19T12:32:09.107
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-19 12:32:09','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='open' WHERE AD_Ref_List_ID=541745 AND AD_Language='de_DE'
;

-- 2019-02-19T12:32:10.242
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-19 12:32:10','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Ref_List_ID=541745 AND AD_Language='en_US'
;

-- 2019-02-19T12:32:19.430
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-19 12:32:19','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Ref_List_ID=541745 AND AD_Language='de_CH'
;

