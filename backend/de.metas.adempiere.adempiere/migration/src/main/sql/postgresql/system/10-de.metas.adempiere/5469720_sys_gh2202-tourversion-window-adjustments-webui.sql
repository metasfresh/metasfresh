-- 2017-08-21T12:46:17.520
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Bereitstellungszeit 1',Updated=TO_TIMESTAMP('2017-08-21 12:46:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542176
;

-- 2017-08-21T12:46:17.533
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PreparationTime_1', Name='Bereitstellungszeit 1', Description='Preparation time for monday', Help=NULL WHERE AD_Element_ID=542176
;

-- 2017-08-21T12:46:17.550
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PreparationTime_1', Name='Bereitstellungszeit 1', Description='Preparation time for monday', Help=NULL, AD_Element_ID=542176 WHERE UPPER(ColumnName)='PREPARATIONTIME_1' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-08-21T12:46:17.552
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PreparationTime_1', Name='Bereitstellungszeit 1', Description='Preparation time for monday', Help=NULL WHERE AD_Element_ID=542176 AND IsCentrallyMaintained='Y'
;

-- 2017-08-21T12:46:17.553
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Bereitstellungszeit 1', Description='Preparation time for monday', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542176) AND IsCentrallyMaintained='Y'
;

-- 2017-08-21T12:46:17.570
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Preparation Time', Name='Bereitstellungszeit 1' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542176)
;

-- 2017-08-21T12:46:33.856
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Bereitstellungszeit Di', PrintName='Bereitstellungszeit Di',Updated=TO_TIMESTAMP('2017-08-21 12:46:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542177
;

-- 2017-08-21T12:46:33.858
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PreparationTime_2', Name='Bereitstellungszeit Di', Description='Preparation time for tuesday', Help=NULL WHERE AD_Element_ID=542177
;

-- 2017-08-21T12:46:33.872
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PreparationTime_2', Name='Bereitstellungszeit Di', Description='Preparation time for tuesday', Help=NULL, AD_Element_ID=542177 WHERE UPPER(ColumnName)='PREPARATIONTIME_2' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-08-21T12:46:33.874
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PreparationTime_2', Name='Bereitstellungszeit Di', Description='Preparation time for tuesday', Help=NULL WHERE AD_Element_ID=542177 AND IsCentrallyMaintained='Y'
;

-- 2017-08-21T12:46:33.875
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Bereitstellungszeit Di', Description='Preparation time for tuesday', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542177) AND IsCentrallyMaintained='Y'
;

-- 2017-08-21T12:46:33.891
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Bereitstellungszeit Di', Name='Bereitstellungszeit Di' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542177)
;

-- 2017-08-21T12:46:47.976
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Bereitstellungszeit Mo', PrintName='Bereitstellungszeit Mo',Updated=TO_TIMESTAMP('2017-08-21 12:46:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542176
;

-- 2017-08-21T12:46:47.979
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PreparationTime_1', Name='Bereitstellungszeit Mo', Description='Preparation time for monday', Help=NULL WHERE AD_Element_ID=542176
;

-- 2017-08-21T12:46:47.988
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PreparationTime_1', Name='Bereitstellungszeit Mo', Description='Preparation time for monday', Help=NULL, AD_Element_ID=542176 WHERE UPPER(ColumnName)='PREPARATIONTIME_1' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-08-21T12:46:47.989
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PreparationTime_1', Name='Bereitstellungszeit Mo', Description='Preparation time for monday', Help=NULL WHERE AD_Element_ID=542176 AND IsCentrallyMaintained='Y'
;

-- 2017-08-21T12:46:47.990
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Bereitstellungszeit Mo', Description='Preparation time for monday', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542176) AND IsCentrallyMaintained='Y'
;

-- 2017-08-21T12:46:48
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Bereitstellungszeit Mo', Name='Bereitstellungszeit Mo' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542176)
;

-- 2017-08-21T12:47:02.604
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Bereitstellungszeit Mi', PrintName='Bereitstellungszeit Mi',Updated=TO_TIMESTAMP('2017-08-21 12:47:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542178
;

-- 2017-08-21T12:47:02.605
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PreparationTime_3', Name='Bereitstellungszeit Mi', Description='Preparation time for wednesday', Help=NULL WHERE AD_Element_ID=542178
;

-- 2017-08-21T12:47:02.618
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PreparationTime_3', Name='Bereitstellungszeit Mi', Description='Preparation time for wednesday', Help=NULL, AD_Element_ID=542178 WHERE UPPER(ColumnName)='PREPARATIONTIME_3' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-08-21T12:47:02.620
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PreparationTime_3', Name='Bereitstellungszeit Mi', Description='Preparation time for wednesday', Help=NULL WHERE AD_Element_ID=542178 AND IsCentrallyMaintained='Y'
;

-- 2017-08-21T12:47:02.621
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Bereitstellungszeit Mi', Description='Preparation time for wednesday', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542178) AND IsCentrallyMaintained='Y'
;

-- 2017-08-21T12:47:02.634
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Bereitstellungszeit Mi', Name='Bereitstellungszeit Mi' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542178)
;

-- 2017-08-21T12:47:16.074
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Preparation Time Do', PrintName='Preparation Time Do',Updated=TO_TIMESTAMP('2017-08-21 12:47:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542179
;

-- 2017-08-21T12:47:16.076
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PreparationTime_4', Name='Preparation Time Do', Description='Preparation time for thursday', Help=NULL WHERE AD_Element_ID=542179
;

-- 2017-08-21T12:47:16.089
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PreparationTime_4', Name='Preparation Time Do', Description='Preparation time for thursday', Help=NULL, AD_Element_ID=542179 WHERE UPPER(ColumnName)='PREPARATIONTIME_4' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-08-21T12:47:16.090
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PreparationTime_4', Name='Preparation Time Do', Description='Preparation time for thursday', Help=NULL WHERE AD_Element_ID=542179 AND IsCentrallyMaintained='Y'
;

-- 2017-08-21T12:47:16.092
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Preparation Time Do', Description='Preparation time for thursday', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542179) AND IsCentrallyMaintained='Y'
;

-- 2017-08-21T12:47:16.107
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Preparation Time Do', Name='Preparation Time Do' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542179)
;

-- 2017-08-21T12:47:26.248
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Preparation Time Fr', PrintName='Preparation Time Fr',Updated=TO_TIMESTAMP('2017-08-21 12:47:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542180
;

-- 2017-08-21T12:47:26.249
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PreparationTime_5', Name='Preparation Time Fr', Description='Preparation time for Friday', Help=NULL WHERE AD_Element_ID=542180
;

-- 2017-08-21T12:47:26.263
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PreparationTime_5', Name='Preparation Time Fr', Description='Preparation time for Friday', Help=NULL, AD_Element_ID=542180 WHERE UPPER(ColumnName)='PREPARATIONTIME_5' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-08-21T12:47:26.264
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PreparationTime_5', Name='Preparation Time Fr', Description='Preparation time for Friday', Help=NULL WHERE AD_Element_ID=542180 AND IsCentrallyMaintained='Y'
;

-- 2017-08-21T12:47:26.266
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Preparation Time Fr', Description='Preparation time for Friday', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542180) AND IsCentrallyMaintained='Y'
;

-- 2017-08-21T12:47:26.280
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Preparation Time Fr', Name='Preparation Time Fr' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542180)
;

-- 2017-08-21T12:47:39.542
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Bereitstellungszeit Sa', PrintName='Bereitstellungszeit Sa',Updated=TO_TIMESTAMP('2017-08-21 12:47:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542181
;

-- 2017-08-21T12:47:39.544
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PreparationTime_6', Name='Bereitstellungszeit Sa', Description='Preparation time for Saturday', Help=NULL WHERE AD_Element_ID=542181
;

-- 2017-08-21T12:47:39.557
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PreparationTime_6', Name='Bereitstellungszeit Sa', Description='Preparation time for Saturday', Help=NULL, AD_Element_ID=542181 WHERE UPPER(ColumnName)='PREPARATIONTIME_6' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-08-21T12:47:39.558
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PreparationTime_6', Name='Bereitstellungszeit Sa', Description='Preparation time for Saturday', Help=NULL WHERE AD_Element_ID=542181 AND IsCentrallyMaintained='Y'
;

-- 2017-08-21T12:47:39.560
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Bereitstellungszeit Sa', Description='Preparation time for Saturday', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542181) AND IsCentrallyMaintained='Y'
;

-- 2017-08-21T12:47:39.575
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Bereitstellungszeit Sa', Name='Bereitstellungszeit Sa' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542181)
;

-- 2017-08-21T12:47:51.624
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Bereitstellungszeit So', PrintName='Bereitstellungszeit So',Updated=TO_TIMESTAMP('2017-08-21 12:47:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542182
;

-- 2017-08-21T12:47:51.625
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PreparationTime_7', Name='Bereitstellungszeit So', Description='Preparation time for Sunday', Help=NULL WHERE AD_Element_ID=542182
;

-- 2017-08-21T12:47:51.639
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PreparationTime_7', Name='Bereitstellungszeit So', Description='Preparation time for Sunday', Help=NULL, AD_Element_ID=542182 WHERE UPPER(ColumnName)='PREPARATIONTIME_7' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-08-21T12:47:51.641
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PreparationTime_7', Name='Bereitstellungszeit So', Description='Preparation time for Sunday', Help=NULL WHERE AD_Element_ID=542182 AND IsCentrallyMaintained='Y'
;

-- 2017-08-21T12:47:51.642
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Bereitstellungszeit So', Description='Preparation time for Sunday', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542182) AND IsCentrallyMaintained='Y'
;

-- 2017-08-21T12:47:51.658
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Bereitstellungszeit So', Name='Bereitstellungszeit So' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542182)
;

-- 2017-08-21T12:48:21.636
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Bereitstellungszeit Do', PrintName='Bereitstellungszeit Do',Updated=TO_TIMESTAMP('2017-08-21 12:48:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542179
;

-- 2017-08-21T12:48:21.637
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PreparationTime_4', Name='Bereitstellungszeit Do', Description='Preparation time for thursday', Help=NULL WHERE AD_Element_ID=542179
;

-- 2017-08-21T12:48:21.649
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PreparationTime_4', Name='Bereitstellungszeit Do', Description='Preparation time for thursday', Help=NULL, AD_Element_ID=542179 WHERE UPPER(ColumnName)='PREPARATIONTIME_4' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-08-21T12:48:21.651
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PreparationTime_4', Name='Bereitstellungszeit Do', Description='Preparation time for thursday', Help=NULL WHERE AD_Element_ID=542179 AND IsCentrallyMaintained='Y'
;

-- 2017-08-21T12:48:21.651
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Bereitstellungszeit Do', Description='Preparation time for thursday', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542179) AND IsCentrallyMaintained='Y'
;

-- 2017-08-21T12:48:21.666
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Bereitstellungszeit Do', Name='Bereitstellungszeit Do' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542179)
;

-- 2017-08-21T12:48:40.420
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Bereitstellungszeit Fr', PrintName='Bereitstellungszeit Fr',Updated=TO_TIMESTAMP('2017-08-21 12:48:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542180
;

-- 2017-08-21T12:48:40.426
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PreparationTime_5', Name='Bereitstellungszeit Fr', Description='Preparation time for Friday', Help=NULL WHERE AD_Element_ID=542180
;

-- 2017-08-21T12:48:40.440
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PreparationTime_5', Name='Bereitstellungszeit Fr', Description='Preparation time for Friday', Help=NULL, AD_Element_ID=542180 WHERE UPPER(ColumnName)='PREPARATIONTIME_5' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-08-21T12:48:40.441
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PreparationTime_5', Name='Bereitstellungszeit Fr', Description='Preparation time for Friday', Help=NULL WHERE AD_Element_ID=542180 AND IsCentrallyMaintained='Y'
;

-- 2017-08-21T12:48:40.442
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Bereitstellungszeit Fr', Description='Preparation time for Friday', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542180) AND IsCentrallyMaintained='Y'
;

-- 2017-08-21T12:48:40.461
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Bereitstellungszeit Fr', Name='Bereitstellungszeit Fr' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542180)
;

-- 2017-08-21T12:51:36.112
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Feiertage ausfallen',Updated=TO_TIMESTAMP('2017-08-21 12:51:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558083
;

-- 2017-08-21T12:51:49.332
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Lieferung verschieben',Updated=TO_TIMESTAMP('2017-08-21 12:51:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558084
;

-- 2017-08-21T12:52:54.621
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2017-08-21 12:52:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543019
;

-- 2017-08-21T12:52:54.624
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2017-08-21 12:52:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542992
;

-- 2017-08-21T12:53:50.802
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SortNo=-1.000000000000,Updated=TO_TIMESTAMP('2017-08-21 12:53:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558082
;

-- 2017-08-21T12:54:23.651
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SortNo=2.000000000000,Updated=TO_TIMESTAMP('2017-08-21 12:54:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558076
;

-- 2017-08-21T12:54:55.253
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SortNo=NULL,Updated=TO_TIMESTAMP('2017-08-21 12:54:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558088
;

-- 2017-08-21T12:54:57.343
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SortNo=NULL,Updated=TO_TIMESTAMP('2017-08-21 12:54:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558090
;

-- 2017-08-21T12:54:59.304
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SortNo=NULL,Updated=TO_TIMESTAMP('2017-08-21 12:54:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558092
;

-- 2017-08-21T12:55:00.824
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SortNo=NULL,Updated=TO_TIMESTAMP('2017-08-21 12:55:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558093
;

-- 2017-08-21T12:55:02.332
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SortNo=NULL,Updated=TO_TIMESTAMP('2017-08-21 12:55:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558094
;

-- 2017-08-21T12:55:03.672
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SortNo=NULL,Updated=TO_TIMESTAMP('2017-08-21 12:55:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558096
;

-- 2017-08-21T12:55:05.087
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SortNo=NULL,Updated=TO_TIMESTAMP('2017-08-21 12:55:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558098
;

-- 2017-08-21T12:55:10.528
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SortNo=NULL,Updated=TO_TIMESTAMP('2017-08-21 12:55:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558100
;

-- 2017-08-21T12:55:34.789
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2017-08-21 12:55:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543034
;

-- 2017-08-21T12:56:19.347
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558107,0,540801,540257,547367,TO_TIMESTAMP('2017-08-21 12:56:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Sektion',10,0,0,TO_TIMESTAMP('2017-08-21 12:56:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-21T12:56:30.117
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558106,0,540801,540257,547368,TO_TIMESTAMP('2017-08-21 12:56:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2017-08-21 12:56:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-21T12:56:40.789
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2017-08-21 12:56:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542987
;

-- 2017-08-21T12:56:45.505
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2017-08-21 12:56:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542988
;

-- 2017-08-21T12:56:47.855
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2017-08-21 12:56:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542989
;

-- 2017-08-21T12:56:49.837
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2017-08-21 12:56:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542990
;

-- 2017-08-21T12:56:52.132
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2017-08-21 12:56:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542991
;

-- 2017-08-21T12:56:54.653
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2017-08-21 12:56:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547367
;

-- 2017-08-21T12:56:57.167
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=70,Updated=TO_TIMESTAMP('2017-08-21 12:56:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547368
;

-- 2017-08-21T12:57:01.739
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2017-08-21 12:57:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547367
;

-- 2017-08-21T12:57:59.559
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558115,0,540801,540257,547369,TO_TIMESTAMP('2017-08-21 12:57:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','Standort',0,0,0,TO_TIMESTAMP('2017-08-21 12:57:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-21T12:58:17.375
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2017-08-21 12:58:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547369
;

-- 2017-08-21T12:58:17.377
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2017-08-21 12:58:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542989
;

-- 2017-08-21T12:58:17.378
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2017-08-21 12:58:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542990
;

-- 2017-08-21T12:58:17.379
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2017-08-21 12:58:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542991
;

-- 2017-08-21T12:58:17.380
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2017-08-21 12:58:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547367
;

