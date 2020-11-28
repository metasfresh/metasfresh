-- 2017-07-17T17:16:54.036
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='',Updated=TO_TIMESTAMP('2017-07-17 17:16:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545279
;

-- 2017-07-17T17:33:05.056
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2017-07-17 17:33:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544686
;

-- 2017-07-17T17:33:05.062
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2017-07-17 17:33:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544685
;

-- 2017-07-17T17:38:29.501
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SortNo=1.000000000000,Updated=TO_TIMESTAMP('2017-07-17 17:38:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=12718
;

-- 2017-07-17T17:39:36.388
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SortNo=NULL,Updated=TO_TIMESTAMP('2017-07-17 17:39:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=12716
;

-- 2017-07-17T17:46:40.495
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) 
SELECT 0,3276,0,223,1000032,546573,TO_TIMESTAMP('2017-07-17 17:46:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Rabatte drucken',150,0,0,TO_TIMESTAMP('2017-07-17 17:46:40','YYYY-MM-DD HH24:MI:SS'),100
WHERE NOT EXISTS (select 1 from AD_UI_Element where AD_UI_Element_ID=546573)
;

-- 2017-07-17T18:09:00.789
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET AD_UI_Column_ID=540035, SeqNo=20,Updated=TO_TIMESTAMP('2017-07-17 18:09:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540046
;

-- 2017-07-17T18:09:17.648
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2017-07-17 18:09:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540047
;

-- 2017-07-17T18:09:58.291
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5190,0,402,540031,546574,TO_TIMESTAMP('2017-07-17 18:09:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Notiz',50,0,0,TO_TIMESTAMP('2017-07-17 18:09:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-17T18:10:07.449
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540034,540838,TO_TIMESTAMP('2017-07-17 18:10:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','dates',20,TO_TIMESTAMP('2017-07-17 18:10:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-17T18:10:37.715
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,11463,0,402,540838,546575,TO_TIMESTAMP('2017-07-17 18:10:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Start Datum',10,0,0,TO_TIMESTAMP('2017-07-17 18:10:37','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2017-07-17T18:10:47.876
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,11451,0,402,540838,546576,TO_TIMESTAMP('2017-07-17 18:10:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','End Datum',20,0,0,TO_TIMESTAMP('2017-07-17 18:10:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-17T18:10:52.139
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2017-07-17 18:10:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546576
;

-- 2017-07-17T18:11:12.114
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=540926
;

-- 2017-07-17T18:11:14.923
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=540927
;

-- 2017-07-17T18:11:30.016
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Anfangsdatum',Updated=TO_TIMESTAMP('2017-07-17 18:11:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546575
;

-- 2017-07-17T18:11:34.969
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Enddatum',Updated=TO_TIMESTAMP('2017-07-17 18:11:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546576
;

-- 2017-07-17T18:12:11.545
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2017-07-17 18:12:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540910
;

-- 2017-07-17T18:12:11.551
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2017-07-17 18:12:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540912
;

-- 2017-07-17T18:12:11.556
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2017-07-17 18:12:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540911
;

-- 2017-07-17T18:12:11.560
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2017-07-17 18:12:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546575
;

-- 2017-07-17T18:12:11.566
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2017-07-17 18:12:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546576
;

-- 2017-07-17T18:13:25.741
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Enddatum',Updated=TO_TIMESTAMP('2017-07-17 18:13:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=11451
;

-- 2017-07-17T18:13:56.029
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Anfangsdatum',Updated=TO_TIMESTAMP('2017-07-17 18:13:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=11463
;

-- 2017-07-17T18:14:44.393
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='', Name='Startdatum', PrintName='Startdatum',Updated=TO_TIMESTAMP('2017-07-17 18:14:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2713
;

-- 2017-07-17T18:14:44.400
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='StartTime', Name='Startdatum', Description='', Help=NULL WHERE AD_Element_ID=2713
;

-- 2017-07-17T18:14:44.411
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='StartTime', Name='Startdatum', Description='', Help=NULL, AD_Element_ID=2713 WHERE UPPER(ColumnName)='STARTTIME' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-07-17T18:14:44.413
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='StartTime', Name='Startdatum', Description='', Help=NULL WHERE AD_Element_ID=2713 AND IsCentrallyMaintained='Y'
;

-- 2017-07-17T18:14:44.414
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Startdatum', Description='', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=2713) AND IsCentrallyMaintained='Y'
;

-- 2017-07-17T18:14:44.428
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Startdatum', Name='Startdatum' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=2713)
;

-- 2017-07-17T18:15:35.804
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Enddatum', PrintName='Enddatum',Updated=TO_TIMESTAMP('2017-07-17 18:15:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2714
;

-- 2017-07-17T18:15:35.814
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='EndTime', Name='Enddatum', Description='End of the time span', Help=NULL WHERE AD_Element_ID=2714
;

-- 2017-07-17T18:15:35.844
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='EndTime', Name='Enddatum', Description='End of the time span', Help=NULL, AD_Element_ID=2714 WHERE UPPER(ColumnName)='ENDTIME' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-07-17T18:15:35.846
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='EndTime', Name='Enddatum', Description='End of the time span', Help=NULL WHERE AD_Element_ID=2714 AND IsCentrallyMaintained='Y'
;

-- 2017-07-17T18:15:35.847
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Enddatum', Description='End of the time span', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=2714) AND IsCentrallyMaintained='Y'
;

-- 2017-07-17T18:15:35.862
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Enddatum', Name='Enddatum' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=2714)
;

-- 2017-07-17T18:18:15.765
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545385
;

-- 2017-07-17T18:20:19.682
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557542,0,402,540040,546577,TO_TIMESTAMP('2017-07-17 18:20:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Zulieferant',17,0,0,TO_TIMESTAMP('2017-07-17 18:20:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-17T18:20:37.096
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557235,0,402,540040,546578,TO_TIMESTAMP('2017-07-17 18:20:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Rücklieferung',27,0,0,TO_TIMESTAMP('2017-07-17 18:20:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-17T18:20:40.557
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2017-07-17 18:20:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545379
;

-- 2017-07-17T18:20:47.608
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2017-07-17 18:20:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545380
;

-- 2017-07-17T18:20:50.033
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2017-07-17 18:20:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546577
;

-- 2017-07-17T18:20:52.833
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2017-07-17 18:20:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546578
;

-- 2017-07-17T18:21:39.963
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2017-07-17 18:21:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546577
;

-- 2017-07-17T18:21:44.235
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2017-07-17 18:21:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546578
;

-- 2017-07-17T18:22:38.008
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557250,0,402,540031,546579,TO_TIMESTAMP('2017-07-17 18:22:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Performance Typ',60,0,0,TO_TIMESTAMP('2017-07-17 18:22:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-17T18:22:48.482
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557245,0,402,540031,546580,TO_TIMESTAMP('2017-07-17 18:22:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Beanstandung',70,0,0,TO_TIMESTAMP('2017-07-17 18:22:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-17T18:25:04.666
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=4,Updated=TO_TIMESTAMP('2017-07-17 18:25:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540910
;

-- 2017-07-17T18:25:25.631
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2017-07-17 18:25:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545387
;

-- 2017-07-17T18:25:28.707
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2017-07-17 18:25:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540910
;

-- 2017-07-17T18:25:37.128
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2017-07-17 18:25:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545386
;

-- 2017-07-17T18:25:48.548
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2017-07-17 18:25:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545388
;

-- 2017-07-17T18:25:52.441
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=32,Updated=TO_TIMESTAMP('2017-07-17 18:25:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540911
;

-- 2017-07-17T18:26:19.574
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2017-07-17 18:26:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540912
;

-- 2017-07-17T18:26:22.270
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2017-07-17 18:26:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545386
;

-- 2017-07-17T18:26:24.838
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2017-07-17 18:26:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540911
;

-- 2017-07-17T18:26:27.920
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2017-07-17 18:26:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545388
;

