-- 2018-08-03T09:25:55.706
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column_Trl SET Name='Customer Label Name' WHERE AD_Column_ID=557915 AND AD_Language='en_US'
;

-- 2018-08-03T09:25:58.889
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column_Trl SET IsTranslated='Y' WHERE AD_Column_ID=557915 AND AD_Language='en_US'
;

-- 2018-08-03T09:59:49.713
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=14,Updated=TO_TIMESTAMP('2018-08-03 09:59:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560718
;

-- 2018-08-03T10:06:48.710
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsTranslated='Y',Updated=TO_TIMESTAMP('2018-08-03 10:06:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560718
;

-- 2018-08-03T10:07:15.061
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNo=10,Updated=TO_TIMESTAMP('2018-08-03 10:07:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563158
;

-- 2018-08-03T10:15:58.873
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y', SeqNo=20,Updated=TO_TIMESTAMP('2018-08-03 10:15:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563159
;

-- 2018-08-03T10:16:14.269
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNo=30,Updated=TO_TIMESTAMP('2018-08-03 10:16:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563162
;

-- 2018-08-03T10:16:21.487
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNo=40,Updated=TO_TIMESTAMP('2018-08-03 10:16:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563163
;

-- 2018-08-03T10:16:33.363
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y', SeqNo=50,Updated=TO_TIMESTAMP('2018-08-03 10:16:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563164
;

-- 2018-08-03T10:16:40.633
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNo=60,Updated=TO_TIMESTAMP('2018-08-03 10:16:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563161
;

-- 2018-08-03T10:16:44.343
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNo=70,Updated=TO_TIMESTAMP('2018-08-03 10:16:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563160
;

-- 2018-08-03T10:16:47.888
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNo=80,Updated=TO_TIMESTAMP('2018-08-03 10:16:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=565138
;

-- 2018-08-03T10:17:46.179
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SpanX=999,Updated=TO_TIMESTAMP('2018-08-03 10:17:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=565138
;

-- 2018-08-03T10:28:52.341
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET Name='Allergen',Updated=TO_TIMESTAMP('2018-08-03 10:28:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560700
;

-- 2018-08-03T10:28:52.366
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Allergen', Description=NULL, Help=NULL WHERE AD_Column_ID=560700
;

-- 2018-08-03T10:29:05.321
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Allergen', PrintName='Allergen',Updated=TO_TIMESTAMP('2018-08-03 10:29:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544183
;

-- 2018-08-03T10:29:05.323
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='M_Allergen_ID', Name='Allergen', Description=NULL, Help=NULL WHERE AD_Element_ID=544183
;

-- 2018-08-03T10:29:05.324
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_Allergen_ID', Name='Allergen', Description=NULL, Help=NULL, AD_Element_ID=544183 WHERE UPPER(ColumnName)='M_ALLERGEN_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-08-03T10:29:05.326
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_Allergen_ID', Name='Allergen', Description=NULL, Help=NULL WHERE AD_Element_ID=544183 AND IsCentrallyMaintained='Y'
;

-- 2018-08-03T10:29:05.327
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Allergen', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544183) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544183)
;

-- 2018-08-03T10:29:05.351
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Allergen', Name='Allergen' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=544183)
;

-- 2018-08-03T10:29:12.494
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Allergen',PrintName='Allergen' WHERE AD_Element_ID=544183 AND AD_Language='de_CH'
;


-- 2018-08-03T10:29:17.238
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Allergen',PrintName='Allergen' WHERE AD_Element_ID=544183 AND AD_Language='nl_NL'
;


-- 2018-08-03T10:29:22.005
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y' WHERE AD_Element_ID=544183 AND AD_Language='de_CH'
;

-- 2018-08-03T10:29:27.935
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Name='Allergen',PrintName='Allergen' WHERE AD_Element_ID=544183 AND AD_Language='en_US'
;


-- 2018-08-03T10:29:33.544
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y' WHERE AD_Element_ID=544183 AND AD_Language='nl_NL'
;




-- 2018-08-03T11:32:06.960
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Business Partner Product',PrintName='Business Partner Product' WHERE AD_Element_ID=543080 AND AD_Language='en_US'
;

-- 2018-08-03T11:33:09.850
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column_Trl SET Name='Language',IsTranslated='Y' WHERE AD_Column_ID=557940 AND AD_Language='en_US'
;

-- 2018-08-03T11:35:58.404
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsUpdateable='N', Name='Produkt Allergen',Updated=TO_TIMESTAMP('2018-08-03 11:35:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560698
;

-- 2018-08-03T11:35:58.420
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Produkt Allergen', Description=NULL, Help=NULL WHERE AD_Column_ID=560698
;

-- 2018-08-03T11:36:10.160
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column_Trl SET Name='Product Allergen',IsTranslated='Y' WHERE AD_Column_ID=560698 AND AD_Language='en_US'
;

-- 2018-08-03T11:36:18.528
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column_Trl SET Name='Product Allergen' WHERE AD_Column_ID=560698 AND AD_Language='de_CH'
;

-- 2018-08-03T11:36:20.711
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column_Trl SET IsTranslated='Y' WHERE AD_Column_ID=560698 AND AD_Language='de_CH'
;

-- 2018-08-03T11:36:28.880
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column_Trl SET Name='Product Allergen' WHERE AD_Column_ID=560698 AND AD_Language='nl_NL'
;

-- 2018-08-03T11:36:49.420
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Produkt Allergen', PrintName='Produkt Allergen',Updated=TO_TIMESTAMP('2018-08-03 11:36:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544184
;

-- 2018-08-03T11:36:49.422
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='M_Product_Allergen_ID', Name='Produkt Allergen', Description=NULL, Help=NULL WHERE AD_Element_ID=544184
;

-- 2018-08-03T11:36:49.423
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_Product_Allergen_ID', Name='Produkt Allergen', Description=NULL, Help=NULL, AD_Element_ID=544184 WHERE UPPER(ColumnName)='M_PRODUCT_ALLERGEN_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-08-03T11:36:49.426
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_Product_Allergen_ID', Name='Produkt Allergen', Description=NULL, Help=NULL WHERE AD_Element_ID=544184 AND IsCentrallyMaintained='Y'
;

-- 2018-08-03T11:36:49.427
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Produkt Allergen', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544184) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544184)
;

-- 2018-08-03T11:36:49.464
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Produkt Allergen', Name='Produkt Allergen' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=544184)
;

-- 2018-08-03T11:37:00.213
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Name='Produkt Allergen',PrintName='Produkt Allergen' WHERE AD_Element_ID=544184 AND AD_Language='de_CH'
;

-- 2018-08-03T11:37:05.901
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Produkt Allergen',PrintName='Produkt Allergen' WHERE AD_Element_ID=544184 AND AD_Language='nl_NL'
;

-- 2018-08-03T11:37:16.837
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Name='Product Allergen',PrintName='Product Allergen' WHERE AD_Element_ID=544184 AND AD_Language='en_US'
;

-- 2018-08-03T11:38:16.235
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column_Trl SET Name='Customer Label Name',IsTranslated='Y' WHERE AD_Column_ID=559491 AND AD_Language='en_US'
;


