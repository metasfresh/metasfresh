-- 2017-08-18T15:08:45.582
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,53028,540414,TO_TIMESTAMP('2017-08-18 15:08:45','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-08-18 15:08:45','YYYY-MM-DD HH24:MI:SS'),100,'advanced edit')
;

-- 2017-08-18T15:08:45.588
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540414 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-08-18T15:08:47.803
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540554,540414,TO_TIMESTAMP('2017-08-18 15:08:47','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-08-18 15:08:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-18T15:09:06.480
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET AD_UI_Column_ID=540554, SeqNo=10,Updated=TO_TIMESTAMP('2017-08-18 15:09:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540442
;

-- 2017-08-18T15:09:24.685
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET UIStyle='primary',Updated=TO_TIMESTAMP('2017-08-18 15:09:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540442
;

-- 2017-08-18T15:11:55.304
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2017-08-18 15:11:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544411
;

-- 2017-08-18T15:11:55.312
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2017-08-18 15:11:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544401
;

-- 2017-08-18T15:12:22.412
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53484,0,53029,540444,547353,TO_TIMESTAMP('2017-08-18 15:12:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Sektion',210,0,0,TO_TIMESTAMP('2017-08-18 15:12:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-18T15:12:32.095
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53483,0,53029,540444,547354,TO_TIMESTAMP('2017-08-18 15:12:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mandant',220,0,0,TO_TIMESTAMP('2017-08-18 15:12:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-18T15:12:53.041
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2017-08-18 15:12:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544405
;

-- 2017-08-18T15:12:53.043
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2017-08-18 15:12:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544399
;

-- 2017-08-18T15:12:53.044
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2017-08-18 15:12:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547353
;

-- 2017-08-18T15:13:17.505
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2017-08-18 15:13:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544411
;

-- 2017-08-18T15:13:23.928
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2017-08-18 15:13:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544401
;

-- 2017-08-18T15:13:33.225
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=70,Updated=TO_TIMESTAMP('2017-08-18 15:13:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544399
;

-- 2017-08-18T15:13:39.895
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=80,Updated=TO_TIMESTAMP('2017-08-18 15:13:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544400
;

-- 2017-08-18T15:13:45.697
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=90,Updated=TO_TIMESTAMP('2017-08-18 15:13:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544412
;

-- 2017-08-18T15:13:49.620
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=100,Updated=TO_TIMESTAMP('2017-08-18 15:13:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544406
;

-- 2017-08-18T15:13:53.105
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=110,Updated=TO_TIMESTAMP('2017-08-18 15:13:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544416
;

-- 2017-08-18T15:13:56.647
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=120,Updated=TO_TIMESTAMP('2017-08-18 15:13:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544407
;

-- 2017-08-18T15:14:00.402
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=130,Updated=TO_TIMESTAMP('2017-08-18 15:14:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544414
;

-- 2017-08-18T15:14:06.072
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=140,Updated=TO_TIMESTAMP('2017-08-18 15:14:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544410
;

-- 2017-08-18T15:14:09.190
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=150,Updated=TO_TIMESTAMP('2017-08-18 15:14:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544409
;

-- 2017-08-18T15:14:12.359
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=160,Updated=TO_TIMESTAMP('2017-08-18 15:14:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544413
;

-- 2017-08-18T15:14:15.616
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=170,Updated=TO_TIMESTAMP('2017-08-18 15:14:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544415
;

-- 2017-08-18T15:14:19.578
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=190,Updated=TO_TIMESTAMP('2017-08-18 15:14:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544408
;

-- 2017-08-18T15:14:23.201
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=200,Updated=TO_TIMESTAMP('2017-08-18 15:14:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547353
;

-- 2017-08-18T15:14:36.296
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=220,Updated=TO_TIMESTAMP('2017-08-18 15:14:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547353
;

-- 2017-08-18T15:14:41.140
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=230,Updated=TO_TIMESTAMP('2017-08-18 15:14:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547354
;

-- 2017-08-18T15:14:58.344
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=210,Updated=TO_TIMESTAMP('2017-08-18 15:14:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544403
;

-- 2017-08-18T15:20:48.087
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Issue Methode', PrintName='Issue Methode',Updated=TO_TIMESTAMP('2017-08-18 15:20:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=53253
;

-- 2017-08-18T15:20:48.099
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IssueMethod', Name='Issue Methode', Description='There are two methods for issue the components to Manufacturing Order', Help='Method Issue: The component are delivered one for one and is necessary indicate the delivered quantity for each component.

Method BackFlush: The component are delivered based in BOM, The  delivered quantity for each component is based in BOM or Formula and Manufacturing Order Quantity.

Use the field Backflush Group for grouping the component in a Backflush Method.' WHERE AD_Element_ID=53253
;

-- 2017-08-18T15:20:48.117
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IssueMethod', Name='Issue Methode', Description='There are two methods for issue the components to Manufacturing Order', Help='Method Issue: The component are delivered one for one and is necessary indicate the delivered quantity for each component.

Method BackFlush: The component are delivered based in BOM, The  delivered quantity for each component is based in BOM or Formula and Manufacturing Order Quantity.

Use the field Backflush Group for grouping the component in a Backflush Method.', AD_Element_ID=53253 WHERE UPPER(ColumnName)='ISSUEMETHOD' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-08-18T15:20:48.119
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IssueMethod', Name='Issue Methode', Description='There are two methods for issue the components to Manufacturing Order', Help='Method Issue: The component are delivered one for one and is necessary indicate the delivered quantity for each component.

Method BackFlush: The component are delivered based in BOM, The  delivered quantity for each component is based in BOM or Formula and Manufacturing Order Quantity.

Use the field Backflush Group for grouping the component in a Backflush Method.' WHERE AD_Element_ID=53253 AND IsCentrallyMaintained='Y'
;

-- 2017-08-18T15:20:48.120
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Issue Methode', Description='There are two methods for issue the components to Manufacturing Order', Help='Method Issue: The component are delivered one for one and is necessary indicate the delivered quantity for each component.

Method BackFlush: The component are delivered based in BOM, The  delivered quantity for each component is based in BOM or Formula and Manufacturing Order Quantity.

Use the field Backflush Group for grouping the component in a Backflush Method.' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=53253) AND IsCentrallyMaintained='Y'
;

-- 2017-08-18T15:20:48.137
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Issue Methode', Name='Issue Methode' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=53253)
;

-- 2017-08-18T15:21:31.753
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zuteil Methode',Updated=TO_TIMESTAMP('2017-08-18 15:21:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53503
;

-- 2017-08-18T15:21:52.286
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Zuteil Methode', PrintName='Zuteil Methode',Updated=TO_TIMESTAMP('2017-08-18 15:21:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=53253
;

-- 2017-08-18T15:21:52.289
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IssueMethod', Name='Zuteil Methode', Description='There are two methods for issue the components to Manufacturing Order', Help='Method Issue: The component are delivered one for one and is necessary indicate the delivered quantity for each component.

Method BackFlush: The component are delivered based in BOM, The  delivered quantity for each component is based in BOM or Formula and Manufacturing Order Quantity.

Use the field Backflush Group for grouping the component in a Backflush Method.' WHERE AD_Element_ID=53253
;

-- 2017-08-18T15:21:52.302
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IssueMethod', Name='Zuteil Methode', Description='There are two methods for issue the components to Manufacturing Order', Help='Method Issue: The component are delivered one for one and is necessary indicate the delivered quantity for each component.

Method BackFlush: The component are delivered based in BOM, The  delivered quantity for each component is based in BOM or Formula and Manufacturing Order Quantity.

Use the field Backflush Group for grouping the component in a Backflush Method.', AD_Element_ID=53253 WHERE UPPER(ColumnName)='ISSUEMETHOD' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-08-18T15:21:52.304
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IssueMethod', Name='Zuteil Methode', Description='There are two methods for issue the components to Manufacturing Order', Help='Method Issue: The component are delivered one for one and is necessary indicate the delivered quantity for each component.

Method BackFlush: The component are delivered based in BOM, The  delivered quantity for each component is based in BOM or Formula and Manufacturing Order Quantity.

Use the field Backflush Group for grouping the component in a Backflush Method.' WHERE AD_Element_ID=53253 AND IsCentrallyMaintained='Y'
;

-- 2017-08-18T15:21:52.306
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zuteil Methode', Description='There are two methods for issue the components to Manufacturing Order', Help='Method Issue: The component are delivered one for one and is necessary indicate the delivered quantity for each component.

Method BackFlush: The component are delivered based in BOM, The  delivered quantity for each component is based in BOM or Formula and Manufacturing Order Quantity.

Use the field Backflush Group for grouping the component in a Backflush Method.' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=53253) AND IsCentrallyMaintained='Y'
;

-- 2017-08-18T15:21:52.327
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Zuteil Methode', Name='Zuteil Methode' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=53253)
;

-- 2017-08-18T15:22:58.265
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Retrograde Gruppe', PrintName='Retrograde Gruppe',Updated=TO_TIMESTAMP('2017-08-18 15:22:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=53248
;

-- 2017-08-18T15:22:58.267
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='BackflushGroup', Name='Retrograde Gruppe', Description='The Grouping Components to the Backflush', Help='When the components are deliver is possible to indicated The Backflush Group this way you only can deliver the components that are for this Backflush Group.' WHERE AD_Element_ID=53248
;

-- 2017-08-18T15:22:58.278
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='BackflushGroup', Name='Retrograde Gruppe', Description='The Grouping Components to the Backflush', Help='When the components are deliver is possible to indicated The Backflush Group this way you only can deliver the components that are for this Backflush Group.', AD_Element_ID=53248 WHERE UPPER(ColumnName)='BACKFLUSHGROUP' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-08-18T15:22:58.279
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='BackflushGroup', Name='Retrograde Gruppe', Description='The Grouping Components to the Backflush', Help='When the components are deliver is possible to indicated The Backflush Group this way you only can deliver the components that are for this Backflush Group.' WHERE AD_Element_ID=53248 AND IsCentrallyMaintained='Y'
;

-- 2017-08-18T15:22:58.280
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Retrograde Gruppe', Description='The Grouping Components to the Backflush', Help='When the components are deliver is possible to indicated The Backflush Group this way you only can deliver the components that are for this Backflush Group.' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=53248) AND IsCentrallyMaintained='Y'
;

-- 2017-08-18T15:22:58.291
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Retrograde Gruppe', Name='Retrograde Gruppe' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=53248)
;

-- 2017-08-18T15:24:03.085
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Mengen Probe',Updated=TO_TIMESTAMP('2017-08-18 15:24:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53501
;

-- 2017-08-18T15:24:17.540
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Mengen Probe', PrintName='Mengen Probe',Updated=TO_TIMESTAMP('2017-08-18 15:24:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=53247
;

-- 2017-08-18T15:24:17.541
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Assay', Name='Mengen Probe', Description='Indicated the Quantity Assay to use into Quality Order', Help='Indicated the Quantity Assay to use into Quality Order' WHERE AD_Element_ID=53247
;

-- 2017-08-18T15:24:17.550
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Assay', Name='Mengen Probe', Description='Indicated the Quantity Assay to use into Quality Order', Help='Indicated the Quantity Assay to use into Quality Order', AD_Element_ID=53247 WHERE UPPER(ColumnName)='ASSAY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-08-18T15:24:17.552
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Assay', Name='Mengen Probe', Description='Indicated the Quantity Assay to use into Quality Order', Help='Indicated the Quantity Assay to use into Quality Order' WHERE AD_Element_ID=53247 AND IsCentrallyMaintained='Y'
;

-- 2017-08-18T15:24:17.552
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Mengen Probe', Description='Indicated the Quantity Assay to use into Quality Order', Help='Indicated the Quantity Assay to use into Quality Order' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=53247) AND IsCentrallyMaintained='Y'
;

-- 2017-08-18T15:24:17.568
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Mengen Probe', Name='Mengen Probe' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=53247)
;

-- 2017-08-18T15:24:49.519
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='% Ausschuss (alt)',Updated=TO_TIMESTAMP('2017-08-18 15:24:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555024
;

-- 2017-08-18T15:25:07.056
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Erwartetes Ergebnis',Updated=TO_TIMESTAMP('2017-08-18 15:25:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555025
;

-- 2017-08-18T15:25:16.533
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Erwartetes Ergebnis', PrintName='Erwartetes Ergebnis',Updated=TO_TIMESTAMP('2017-08-18 15:25:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542596
;

-- 2017-08-18T15:25:16.537
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='expectedResult', Name='Erwartetes Ergebnis', Description=NULL, Help=NULL WHERE AD_Element_ID=542596
;

-- 2017-08-18T15:25:16.547
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='expectedResult', Name='Erwartetes Ergebnis', Description=NULL, Help=NULL, AD_Element_ID=542596 WHERE UPPER(ColumnName)='EXPECTEDRESULT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-08-18T15:25:16.548
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='expectedResult', Name='Erwartetes Ergebnis', Description=NULL, Help=NULL WHERE AD_Element_ID=542596 AND IsCentrallyMaintained='Y'
;

-- 2017-08-18T15:25:16.549
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Erwartetes Ergebnis', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542596) AND IsCentrallyMaintained='Y'
;

-- 2017-08-18T15:25:16.560
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Erwartetes Ergebnis', Name='Erwartetes Ergebnis' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542596)
;

-- 2017-08-18T15:25:38.623
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Varianten Gruppe',Updated=TO_TIMESTAMP('2017-08-18 15:25:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=553984
;

-- 2017-08-18T15:25:48.540
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Varianten Gruppe', PrintName='Varianten Gruppe',Updated=TO_TIMESTAMP('2017-08-18 15:25:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542377
;

-- 2017-08-18T15:25:48.545
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='VariantGroup', Name='Varianten Gruppe', Description=NULL, Help=NULL WHERE AD_Element_ID=542377
;

-- 2017-08-18T15:25:48.559
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='VariantGroup', Name='Varianten Gruppe', Description=NULL, Help=NULL, AD_Element_ID=542377 WHERE UPPER(ColumnName)='VARIANTGROUP' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-08-18T15:25:48.561
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='VariantGroup', Name='Varianten Gruppe', Description=NULL, Help=NULL WHERE AD_Element_ID=542377 AND IsCentrallyMaintained='Y'
;

-- 2017-08-18T15:25:48.562
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Varianten Gruppe', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542377) AND IsCentrallyMaintained='Y'
;

-- 2017-08-18T15:25:48.577
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Varianten Gruppe', Name='Varianten Gruppe' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542377)
;

-- 2017-08-18T15:27:06.031
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Kritische Komponente',Updated=TO_TIMESTAMP('2017-08-18 15:27:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53497
;

