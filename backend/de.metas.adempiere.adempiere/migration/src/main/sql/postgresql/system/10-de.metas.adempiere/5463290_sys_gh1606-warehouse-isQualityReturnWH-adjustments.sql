-- 2017-05-22T17:05:16.836
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557929,0,177,540174,544496,TO_TIMESTAMP('2017-05-22 17:05:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Sperrlager',70,0,0,TO_TIMESTAMP('2017-05-22 17:05:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T17:06:43.163
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Sperrlager', PrintName='Sperrlager',Updated=TO_TIMESTAMP('2017-05-22 17:06:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543295
;

-- 2017-05-22T17:06:43.175
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=543295
;

-- 2017-05-22T17:06:43.179
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsQualityReturnWarehouse', Name='Sperrlager', Description=NULL, Help=NULL WHERE AD_Element_ID=543295
;

-- 2017-05-22T17:06:43.216
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsQualityReturnWarehouse', Name='Sperrlager', Description=NULL, Help=NULL, AD_Element_ID=543295 WHERE UPPER(ColumnName)='ISQUALITYRETURNWAREHOUSE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-05-22T17:06:43.219
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsQualityReturnWarehouse', Name='Sperrlager', Description=NULL, Help=NULL WHERE AD_Element_ID=543295 AND IsCentrallyMaintained='Y'
;

-- 2017-05-22T17:06:43.220
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Sperrlager', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543295) AND IsCentrallyMaintained='Y'
;

-- 2017-05-22T17:06:43.232
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Sperrlager', Name='Sperrlager' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543295)
;

-- 2017-05-22T17:08:51.676
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='ohne HU Bestand',Updated=TO_TIMESTAMP('2017-05-22 17:08:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555189
;

-- 2017-05-22T17:08:51.681
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=555189
;

-- 2017-05-22T17:09:11.381
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Ohne HU Bestand', PrintName='Ohne HU Bestand',Updated=TO_TIMESTAMP('2017-05-22 17:09:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542645
;

-- 2017-05-22T17:09:11.389
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=542645
;

-- 2017-05-22T17:09:11.393
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsHUStorageDisabled', Name='Ohne HU Bestand', Description='If ticked, the HU storages will be excluded for this warehouse', Help=NULL WHERE AD_Element_ID=542645
;

-- 2017-05-22T17:09:11.422
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsHUStorageDisabled', Name='Ohne HU Bestand', Description='If ticked, the HU storages will be excluded for this warehouse', Help=NULL, AD_Element_ID=542645 WHERE UPPER(ColumnName)='ISHUSTORAGEDISABLED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-05-22T17:09:11.424
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsHUStorageDisabled', Name='Ohne HU Bestand', Description='If ticked, the HU storages will be excluded for this warehouse', Help=NULL WHERE AD_Element_ID=542645 AND IsCentrallyMaintained='Y'
;

-- 2017-05-22T17:09:11.426
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Ohne HU Bestand', Description='If ticked, the HU storages will be excluded for this warehouse', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542645) AND IsCentrallyMaintained='Y'
;

-- 2017-05-22T17:09:11.439
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Ohne HU Bestand', Name='Ohne HU Bestand' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542645)
;

-- 2017-05-22T17:10:22.048
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542140
;

-- 2017-05-22T17:10:31.817
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,996,0,177,540175,544497,TO_TIMESTAMP('2017-05-22 17:10:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2017-05-22 17:10:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T17:11:23.483
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2094,0,177,540173,544498,TO_TIMESTAMP('2017-05-22 17:11:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Suchschlüssel',5,0,0,TO_TIMESTAMP('2017-05-22 17:11:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T17:12:16.883
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET UIStyle='',Updated=TO_TIMESTAMP('2017-05-22 17:12:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542126
;

