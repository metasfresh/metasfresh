-- 2017-08-17T17:41:52.086
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET UIStyle='primary',Updated=TO_TIMESTAMP('2017-08-17 17:41:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=1000040
;

-- 2017-08-17T17:54:07.070
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Regalhöhe',Updated=TO_TIMESTAMP('2017-08-17 17:54:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000365
;

-- 2017-08-17T17:54:32.206
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555413,0,180,1000040,547341,TO_TIMESTAMP('2017-08-17 17:54:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Von ERP Berechnung ausschliessen',60,0,0,TO_TIMESTAMP('2017-08-17 17:54:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-17T17:55:37.174
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='', Name='MRP ausschliessen', PrintName='MRP ausschliessen',Updated=TO_TIMESTAMP('2017-08-17 17:55:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542697
;

-- 2017-08-17T17:55:37.179
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='MRP_Exclude', Name='MRP ausschliessen', Description='', Help=NULL WHERE AD_Element_ID=542697
;

-- 2017-08-17T17:55:37.191
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='MRP_Exclude', Name='MRP ausschliessen', Description='', Help=NULL, AD_Element_ID=542697 WHERE UPPER(ColumnName)='MRP_EXCLUDE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-08-17T17:55:37.192
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='MRP_Exclude', Name='MRP ausschliessen', Description='', Help=NULL WHERE AD_Element_ID=542697 AND IsCentrallyMaintained='Y'
;

-- 2017-08-17T17:55:37.194
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='MRP ausschliessen', Description='', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542697) AND IsCentrallyMaintained='Y'
;

-- 2017-08-17T17:55:37.220
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='MRP ausschliessen', Name='MRP ausschliessen' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542697)
;

-- 2017-08-17T17:56:20.031
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Maßeinheit',Updated=TO_TIMESTAMP('2017-08-17 17:56:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000091
;

-- 2017-08-17T17:56:30.766
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='MRP ausschliessen',Updated=TO_TIMESTAMP('2017-08-17 17:56:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547341
;

-- 2017-08-17T17:57:06.109
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3744,0,180,1000040,547342,TO_TIMESTAMP('2017-08-17 17:57:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Details auf Rechnung',70,0,0,TO_TIMESTAMP('2017-08-17 17:57:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-17T17:57:35.425
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3745,0,180,1000040,547343,TO_TIMESTAMP('2017-08-17 17:57:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Details auf Pickliste',80,0,0,TO_TIMESTAMP('2017-08-17 17:57:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-17T17:57:54.514
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2099,0,180,1000040,547344,TO_TIMESTAMP('2017-08-17 17:57:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Kommentar/ Hilfe',90,0,0,TO_TIMESTAMP('2017-08-17 17:57:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-17T17:57:54.949
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-08-17 17:57:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547341
;

-- 2017-08-17T17:57:55.356
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-08-17 17:57:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547342
;

-- 2017-08-17T17:57:55.688
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-08-17 17:57:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547343
;

-- 2017-08-17T17:57:56.952
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-08-17 17:57:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547344
;

-- 2017-08-17T17:58:25.452
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545300
;

-- 2017-08-17T17:58:25.485
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545301
;

-- 2017-08-17T17:58:25.514
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545302
;

-- 2017-08-17T17:58:25.526
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545303
;

-- 2017-08-17T17:58:29.748
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=540583
;

