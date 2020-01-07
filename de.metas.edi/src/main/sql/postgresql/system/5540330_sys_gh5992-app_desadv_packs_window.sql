
-- 2020-01-06T08:02:17.386Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Name='C_Queue_Processor',Updated=TO_TIMESTAMP('2020-01-06 09:02:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540486
;

-- 2020-01-07T05:07:53.026Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Menge CU/TU', PrintName='Menge CU/TU',Updated=TO_TIMESTAMP('2020-01-07 06:07:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542492 AND AD_Language='fr_CH'
;

-- 2020-01-07T05:07:53.030Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542492,'fr_CH') 
;

-- 2020-01-07T05:08:12.712Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Menge CU/TU', PrintName='Menge CU/TU',Updated=TO_TIMESTAMP('2020-01-07 06:08:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542492 AND AD_Language='de_CH'
;

-- 2020-01-07T05:08:12.713Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542492,'de_CH') 
;

-- 2020-01-07T05:08:24.585Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Qty CU per TU', PrintName='Qty CU per TU',Updated=TO_TIMESTAMP('2020-01-07 06:08:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542492 AND AD_Language='en_US'
;

-- 2020-01-07T05:08:24.588Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542492,'en_US') 
;

-- 2020-01-07T05:08:35.793Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Menge CU/TU', PrintName='Menge CU/TU',Updated=TO_TIMESTAMP('2020-01-07 06:08:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542492 AND AD_Language='de_DE'
;

-- 2020-01-07T05:08:35.794Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542492,'de_DE') 
;

-- 2020-01-07T05:08:35.800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(542492,'de_DE') 
;

-- 2020-01-07T05:08:35.801Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyCU', Name='Menge CU/TU', Description='Menge der CUs pro Einzelgebinde (normalerweise TU)', Help=NULL WHERE AD_Element_ID=542492
;

-- 2020-01-07T05:08:35.802Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyCU', Name='Menge CU/TU', Description='Menge der CUs pro Einzelgebinde (normalerweise TU)', Help=NULL, AD_Element_ID=542492 WHERE UPPER(ColumnName)='QTYCU' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-01-07T05:08:35.803Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyCU', Name='Menge CU/TU', Description='Menge der CUs pro Einzelgebinde (normalerweise TU)', Help=NULL WHERE AD_Element_ID=542492 AND IsCentrallyMaintained='Y'
;

-- 2020-01-07T05:08:35.804Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Menge CU/TU', Description='Menge der CUs pro Einzelgebinde (normalerweise TU)', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542492) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 542492)
;

-- 2020-01-07T05:08:35.815Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Menge CU/TU', Name='Menge CU/TU' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542492)
;

-- 2020-01-07T05:08:35.816Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Menge CU/TU', Description='Menge der CUs pro Einzelgebinde (normalerweise TU)', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 542492
;

-- 2020-01-07T05:08:35.817Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Menge CU/TU', Description='Menge der CUs pro Einzelgebinde (normalerweise TU)', Help=NULL WHERE AD_Element_ID = 542492
;

-- 2020-01-07T05:08:35.819Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Menge CU/TU', Description = 'Menge der CUs pro Einzelgebinde (normalerweise TU)', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 542492
;

-- 2020-01-07T05:10:03.388Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2020-01-07 06:10:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564361
;

-- 2020-01-07T05:10:03.395Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2020-01-07 06:10:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564359
;

-- 2020-01-07T05:10:03.398Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2020-01-07 06:10:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564561
;

-- 2020-01-07T05:10:03.405Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2020-01-07 06:10:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564360
;

-- 2020-01-07T05:10:03.409Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2020-01-07 06:10:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564726
;

-- 2020-01-07T05:10:48.453Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2020-01-07 06:10:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564355
;

-- 2020-01-07T05:10:56.835Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2020-01-07 06:10:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564357
;

-- 2020-01-07T05:11:05.902Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=25,Updated=TO_TIMESTAMP('2020-01-07 06:11:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564354
;

-- 2020-01-07T05:11:12.082Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2020-01-07 06:11:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564356
;

-- 2020-01-07T05:11:24.035Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2020-01-07 06:11:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564354
;

-- 2020-01-07T05:11:40.328Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=150,Updated=TO_TIMESTAMP('2020-01-07 06:11:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564353
;

-- 2020-01-07T05:11:44.088Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=140,Updated=TO_TIMESTAMP('2020-01-07 06:11:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564362
;

-- 2020-01-07T05:11:47.916Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='N', SeqNo=130,Updated=TO_TIMESTAMP('2020-01-07 06:11:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564727
;

-- 2020-01-07T05:11:51.104Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=120,Updated=TO_TIMESTAMP('2020-01-07 06:11:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564726
;

-- 2020-01-07T05:11:54.325Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=110,Updated=TO_TIMESTAMP('2020-01-07 06:11:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564360
;

-- 2020-01-07T05:12:01.328Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=101,Updated=TO_TIMESTAMP('2020-01-07 06:12:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564561
;

-- 2020-01-07T05:12:04.983Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=90,Updated=TO_TIMESTAMP('2020-01-07 06:12:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564359
;

-- 2020-01-07T05:12:09.717Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=80,Updated=TO_TIMESTAMP('2020-01-07 06:12:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564361
;

-- 2020-01-07T05:12:17.038Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=100,Updated=TO_TIMESTAMP('2020-01-07 06:12:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564561
;

-- 2020-01-07T05:12:29.767Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2020-01-07 06:12:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564727
;

-- 2020-01-07T05:13:07.445Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,542132,543296,TO_TIMESTAMP('2020-01-07 06:13:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags and org',10,TO_TIMESTAMP('2020-01-07 06:13:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-07T05:13:25.854Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=543296, SeqNo=10,Updated=TO_TIMESTAMP('2020-01-07 06:13:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564362
;

-- 2020-01-07T05:13:35.088Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=543296, SeqNo=20,Updated=TO_TIMESTAMP('2020-01-07 06:13:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564353
;

-- 2020-01-07T05:14:29.227Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,542131,543297,TO_TIMESTAMP('2020-01-07 06:14:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','secondary',20,TO_TIMESTAMP('2020-01-07 06:14:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-07T05:14:50.546Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=543297, SeqNo=10,Updated=TO_TIMESTAMP('2020-01-07 06:14:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564356
;

-- 2020-01-07T05:14:58.059Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=543297, SeqNo=20,Updated=TO_TIMESTAMP('2020-01-07 06:14:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564358
;

-- 2020-01-07T05:15:07.957Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=543297, SeqNo=30,Updated=TO_TIMESTAMP('2020-01-07 06:15:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564724
;

-- 2020-01-07T05:15:19.155Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=543297, SeqNo=40,Updated=TO_TIMESTAMP('2020-01-07 06:15:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564560
;

