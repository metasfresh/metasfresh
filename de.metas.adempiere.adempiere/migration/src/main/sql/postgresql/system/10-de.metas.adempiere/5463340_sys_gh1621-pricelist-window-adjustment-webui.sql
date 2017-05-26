-- 2017-05-23T13:45:08.218
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Preislimit erzwingen', PrintName='Preislimit erzwingen',Updated=TO_TIMESTAMP('2017-05-23 13:45:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=882
;

-- 2017-05-23T13:45:08.248
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=882
;

-- 2017-05-23T13:45:08.252
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='EnforcePriceLimit', Name='Preislimit erzwingen', Description='Do not allow prices below the limit price', Help='The Enforce Price Limit check box indicates that prices cannot be below the limit price in Orders and Invoices.  Ths can be overwritten, if the role allows this.' WHERE AD_Element_ID=882
;

-- 2017-05-23T13:45:08.262
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='EnforcePriceLimit', Name='Preislimit erzwingen', Description='Do not allow prices below the limit price', Help='The Enforce Price Limit check box indicates that prices cannot be below the limit price in Orders and Invoices.  Ths can be overwritten, if the role allows this.', AD_Element_ID=882 WHERE UPPER(ColumnName)='ENFORCEPRICELIMIT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-05-23T13:45:08.263
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='EnforcePriceLimit', Name='Preislimit erzwingen', Description='Do not allow prices below the limit price', Help='The Enforce Price Limit check box indicates that prices cannot be below the limit price in Orders and Invoices.  Ths can be overwritten, if the role allows this.' WHERE AD_Element_ID=882 AND IsCentrallyMaintained='Y'
;

-- 2017-05-23T13:45:08.264
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Preislimit erzwingen', Description='Do not allow prices below the limit price', Help='The Enforce Price Limit check box indicates that prices cannot be below the limit price in Orders and Invoices.  Ths can be overwritten, if the role allows this.' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=882) AND IsCentrallyMaintained='Y'
;

-- 2017-05-23T13:45:08.278
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Preislimit erzwingen', Name='Preislimit erzwingen' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=882)
;

-- 2017-05-23T13:46:13.873
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Basis Preisliste', PrintName='Basis Preisliste',Updated=TO_TIMESTAMP('2017-05-23 13:46:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1259
;

-- 2017-05-23T13:46:13.875
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=1259
;

-- 2017-05-23T13:46:13.876
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='BasePriceList_ID', Name='Basis Preisliste', Description='Pricelist to be used, if product not found on this pricelist', Help='The Base Price List identifies the default price list to be used if a product is not found on the selected price list' WHERE AD_Element_ID=1259
;

-- 2017-05-23T13:46:13.885
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='BasePriceList_ID', Name='Basis Preisliste', Description='Pricelist to be used, if product not found on this pricelist', Help='The Base Price List identifies the default price list to be used if a product is not found on the selected price list', AD_Element_ID=1259 WHERE UPPER(ColumnName)='BASEPRICELIST_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-05-23T13:46:13.886
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='BasePriceList_ID', Name='Basis Preisliste', Description='Pricelist to be used, if product not found on this pricelist', Help='The Base Price List identifies the default price list to be used if a product is not found on the selected price list' WHERE AD_Element_ID=1259 AND IsCentrallyMaintained='Y'
;

-- 2017-05-23T13:46:13.886
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Basis Preisliste', Description='Pricelist to be used, if product not found on this pricelist', Help='The Base Price List identifies the default price list to be used if a product is not found on the selected price list' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=1259) AND IsCentrallyMaintained='Y'
;

-- 2017-05-23T13:46:13.896
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Basis Preisliste', Name='Basis Preisliste' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=1259)
;

-- 2017-05-23T13:46:57.251
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Preis Präzision', PrintName='Preis Präzision',Updated=TO_TIMESTAMP('2017-05-23 13:46:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2665
;

-- 2017-05-23T13:46:57.253
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=2665
;

-- 2017-05-23T13:46:57.254
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PricePrecision', Name='Preis Präzision', Description='Precision (number of decimals) for the Price', Help='The prices of the price list are rounded to the precision entered.  This allows to have prices with below currency precision, e.g. $0.005. Enter the number of decimals or -1 for no rounding.' WHERE AD_Element_ID=2665
;

-- 2017-05-23T13:46:57.262
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PricePrecision', Name='Preis Präzision', Description='Precision (number of decimals) for the Price', Help='The prices of the price list are rounded to the precision entered.  This allows to have prices with below currency precision, e.g. $0.005. Enter the number of decimals or -1 for no rounding.', AD_Element_ID=2665 WHERE UPPER(ColumnName)='PRICEPRECISION' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-05-23T13:46:57.263
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PricePrecision', Name='Preis Präzision', Description='Precision (number of decimals) for the Price', Help='The prices of the price list are rounded to the precision entered.  This allows to have prices with below currency precision, e.g. $0.005. Enter the number of decimals or -1 for no rounding.' WHERE AD_Element_ID=2665 AND IsCentrallyMaintained='Y'
;

-- 2017-05-23T13:46:57.264
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Preis Präzision', Description='Precision (number of decimals) for the Price', Help='The prices of the price list are rounded to the precision entered.  This allows to have prices with below currency precision, e.g. $0.005. Enter the number of decimals or -1 for no rounding.' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=2665) AND IsCentrallyMaintained='Y'
;

-- 2017-05-23T13:46:57.273
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Preis Präzision', Name='Preis Präzision' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=2665)
;

-- 2017-05-23T13:47:46.257
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Basis Preisliste',Updated=TO_TIMESTAMP('2017-05-23 13:47:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541397
;

-- 2017-05-23T13:47:56.310
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540776,540211,TO_TIMESTAMP('2017-05-23 13:47:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','advanced edit',20,TO_TIMESTAMP('2017-05-23 13:47:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T13:48:49.934
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=541373
;

-- 2017-05-23T13:48:53.291
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=541364
;

-- 2017-05-23T13:49:26.958
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557523,0,540776,540089,544709,TO_TIMESTAMP('2017-05-23 13:49:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Standard',15,0,0,TO_TIMESTAMP('2017-05-23 13:49:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T13:49:58.650
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2017-05-23 13:49:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541398
;

-- 2017-05-23T13:49:58.655
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2017-05-23 13:49:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544709
;

-- 2017-05-23T13:49:58.656
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2017-05-23 13:49:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541399
;

-- 2017-05-23T13:49:58.658
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2017-05-23 13:49:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541400
;

-- 2017-05-23T13:49:58.659
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2017-05-23 13:49:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541401
;

-- 2017-05-23T13:49:58.660
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2017-05-23 13:49:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541397
;

-- 2017-05-23T13:49:58.661
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2017-05-23 13:49:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541402
;

-- 2017-05-23T13:50:19.062
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557514,0,540776,540090,544710,TO_TIMESTAMP('2017-05-23 13:50:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2017-05-23 13:50:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T13:50:24.302
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2017-05-23 13:50:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544710
;

-- 2017-05-23T13:50:31.693
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=10,Updated=TO_TIMESTAMP('2017-05-23 13:50:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541391
;

-- 2017-05-23T13:50:31.694
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=20,Updated=TO_TIMESTAMP('2017-05-23 13:50:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541398
;

-- 2017-05-23T13:50:42.489
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=20,Updated=TO_TIMESTAMP('2017-05-23 13:50:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541393
;

-- 2017-05-23T13:50:42.495
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=30,Updated=TO_TIMESTAMP('2017-05-23 13:50:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541392
;

-- 2017-05-23T13:50:42.499
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=40,Updated=TO_TIMESTAMP('2017-05-23 13:50:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541398
;

-- 2017-05-23T13:53:15.388
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540295,540211,TO_TIMESTAMP('2017-05-23 13:53:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-05-23 13:53:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T13:53:22.824
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540295,540486,TO_TIMESTAMP('2017-05-23 13:53:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','advanced edit',10,TO_TIMESTAMP('2017-05-23 13:53:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T13:53:36.541
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557527,0,540776,540486,544711,TO_TIMESTAMP('2017-05-23 13:53:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Pflichtangabe',10,0,0,TO_TIMESTAMP('2017-05-23 13:53:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T13:56:14.043
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=541377
;

-- 2017-05-23T13:56:14.069
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=541376
;

-- 2017-05-23T13:56:18.480
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=540085
;

-- 2017-05-23T13:57:04.216
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557527,0,540776,540089,544712,TO_TIMESTAMP('2017-05-23 13:57:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Pflichtangabe',50,0,0,TO_TIMESTAMP('2017-05-23 13:57:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T13:57:18.459
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544711
;

-- 2017-05-23T13:57:21.562
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=540486
;

-- 2017-05-23T13:57:24.909
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=540295
;

-- 2017-05-23T13:57:29.077
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Section WHERE AD_UI_Section_ID=540211
;

-- 2017-05-23T13:59:39.776
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SortNo=1.000000000000,Updated=TO_TIMESTAMP('2017-05-23 13:59:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557537
;

-- 2017-05-23T14:00:11.626
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SortNo=-1.000000000000,Updated=TO_TIMESTAMP('2017-05-23 14:00:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557537
;

-- 2017-05-23T14:13:15.577
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-05-23 14:13:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541387
;

-- 2017-05-23T14:13:15.583
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2017-05-23 14:13:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541384
;

-- 2017-05-23T14:13:15.587
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2017-05-23 14:13:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541389
;

-- 2017-05-23T14:13:15.591
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2017-05-23 14:13:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541388
;

-- 2017-05-23T14:13:15.596
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2017-05-23 14:13:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541390
;

-- 2017-05-23T14:13:15.599
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2017-05-23 14:13:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541381
;

-- 2017-05-23T14:13:15.602
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2017-05-23 14:13:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541380
;

