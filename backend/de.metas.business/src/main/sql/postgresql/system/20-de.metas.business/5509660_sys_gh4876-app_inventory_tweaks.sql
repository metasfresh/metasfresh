--
-- cleanup an obsolete refund related doctype that ended up in AD_client_ID=1000000
--
-- 2019-01-15T15:49:50.130
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  C_DocType_Trl WHERE C_DocType_ID=540956
;

-- 2019-01-15T15:49:50.141
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
delete from ad_document_action_access WHERE C_DocType_ID=540956
;
DELETE FROM C_DocType WHERE C_DocType_ID=540956
;

--
-- cleanup the legacy InventoryCountUpdate process which depends on M_Storage
--
-- 2019-01-15T17:30:25.482
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Table_Process WHERE AD_Process_ID=106 AND AD_Table_ID=321
;

-- 2019-01-15T17:30:32.286
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=106
;

-- 2019-01-15T17:30:32.293
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process WHERE AD_Process_ID=106
;

--
-- cleanup the legacy InventoryCountCreate process
--
-- 2019-01-15T17:34:22.593
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Table_Process WHERE AD_Process_ID=105 AND AD_Table_ID=321
;

-- 2019-01-15T17:34:26.882
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=105
;

-- 2019-01-15T17:34:26.890
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process WHERE AD_Process_ID=105
;

-- 2019-01-15T17:45:00.338
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,575952,0,TO_TIMESTAMP('2019-01-15 17:45:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Max. Anzahl von Lagerorten','Max. Anzahl von Lagerorten',TO_TIMESTAMP('2019-01-15 17:45:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-01-15T17:45:00.340
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=575952 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-01-15T17:45:10.613
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-15 17:45:10','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=575952 AND AD_Language='de_CH'
;

-- 2019-01-15T17:45:10.661
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575952,'de_CH') 
;

-- 2019-01-15T17:45:13.257
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-15 17:45:13','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=575952 AND AD_Language='de_DE'
;

-- 2019-01-15T17:45:13.267
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575952,'de_DE') 
;

-- 2019-01-15T17:45:13.288
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(575952,'de_DE') 
;

-- 2019-01-15T17:45:26.162
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-15 17:45:26','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Max. number of locators',PrintName='Max. number of locators' WHERE AD_Element_ID=575952 AND AD_Language='en_US'
;

-- 2019-01-15T17:45:26.171
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575952,'en_US') 
;

-- 2019-01-15T17:46:08.858
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='MaxNumberOfLocators',Updated=TO_TIMESTAMP('2019-01-15 17:46:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=575952
;

-- 2019-01-15T17:46:08.865
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='MaxNumberOfLocators', Name='Max. Anzahl von Lagerorten', Description=NULL, Help=NULL WHERE AD_Element_ID=575952
;

-- 2019-01-15T17:46:08.866
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='MaxNumberOfLocators', Name='Max. Anzahl von Lagerorten', Description=NULL, Help=NULL, AD_Element_ID=575952 WHERE UPPER(ColumnName)='MAXNUMBEROFLOCATORS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-01-15T17:46:08.868
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='MaxNumberOfLocators', Name='Max. Anzahl von Lagerorten', Description=NULL, Help=NULL WHERE AD_Element_ID=575952 AND IsCentrallyMaintained='Y'
;

-- 2019-01-15T17:46:49.326
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Element_ID=NULL, ColumnName='MaxNumberOfLocators', Description=NULL, Help=NULL, Name='Max. Anzahl von Lagerorten',Updated=TO_TIMESTAMP('2019-01-15 17:46:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541331
;

-- 2019-01-15T17:47:25.784
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,575953,0,'ProductValueMin',TO_TIMESTAMP('2019-01-15 17:47:25','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Min. Warenwert','Min. Warenwert',TO_TIMESTAMP('2019-01-15 17:47:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-01-15T17:47:25.786
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=575953 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-01-15T17:47:29.387
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-15 17:47:29','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=575953 AND AD_Language='de_CH'
;

-- 2019-01-15T17:47:29.396
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575953,'de_CH') 
;

-- 2019-01-15T17:47:32.215
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-15 17:47:32','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=575953 AND AD_Language='de_DE'
;

-- 2019-01-15T17:47:32.225
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575953,'de_DE') 
;

-- 2019-01-15T17:47:32.247
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(575953,'de_DE') 
;

-- 2019-01-15T17:48:07.097
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-15 17:48:07','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Min. value of goods',PrintName='Min. value of goods' WHERE AD_Element_ID=575953 AND AD_Language='en_US'
;

-- 2019-01-15T17:48:07.108
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575953,'en_US') 
;

-- 2019-01-15T17:48:58.116
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='MinValueOfGoods',Updated=TO_TIMESTAMP('2019-01-15 17:48:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=575953
;

-- 2019-01-15T17:48:58.120
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='MinValueOfGoods', Name='Min. Warenwert', Description=NULL, Help=NULL WHERE AD_Element_ID=575953
;

-- 2019-01-15T17:48:58.122
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='MinValueOfGoods', Name='Min. Warenwert', Description=NULL, Help=NULL, AD_Element_ID=575953 WHERE UPPER(ColumnName)='MINVALUEOFGOODS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-01-15T17:48:58.123
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='MinValueOfGoods', Name='Min. Warenwert', Description=NULL, Help=NULL WHERE AD_Element_ID=575953 AND IsCentrallyMaintained='Y'
;

-- 2019-01-15T17:49:42.512
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Element_ID=575953, ColumnName='MinValueOfGoods', Description=NULL, Help=NULL, Name='Min. Warenwert',Updated=TO_TIMESTAMP('2019-01-15 17:49:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541332
;

-- 2019-01-15T17:53:10.372
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Zeilen erstellen nach Anz. Lagerorten & Warenenwert',Updated=TO_TIMESTAMP('2019-01-15 17:53:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540994
;

-- 2019-01-15T17:53:46.915
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Zeilen erstellen (Lagerort-Anz. & Mind.-Warenenwert)',Updated=TO_TIMESTAMP('2019-01-15 17:53:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540994
;

-- 2019-01-15T17:53:53.601
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-15 17:53:53','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Zeilen erstellen (Lagerort-Anz. & Mind.-Warenenwert)' WHERE AD_Process_ID=540994 AND AD_Language='de_CH'
;

-- 2019-01-15T17:53:58.830
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-15 17:53:58','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Zeilen erstellen (Lagerort-Anz. & Mind.-Warenenwert)' WHERE AD_Process_ID=540994 AND AD_Language='de_DE'
;

-- 2019-01-15T17:54:41.302
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-15 17:54:41','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Create lines by locator count & value of goods',Description='' WHERE AD_Process_ID=540994 AND AD_Language='en_US'
;

-- 2019-01-15T18:18:22.110
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Value='M_Inventory_CreateLines_By_Locators_And_Value',Updated=TO_TIMESTAMP('2019-01-15 18:18:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540994
;

-- 2019-01-15T18:18:30.647
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.handlingunits.inventory.process.M_Inventory_CreateLines_By_Locators_And_Value',Updated=TO_TIMESTAMP('2019-01-15 18:18:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540994
;

-- 2019-01-15T18:19:25.518
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.handlingunits.inventory.process.M_Inventory_CreateLines_RestrictBy_Locators_And_Value', Value='M_Inventory_CreateLines_RestrictBy_Locators_And_Value',Updated=TO_TIMESTAMP('2019-01-15 18:19:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540994
;

-- 2019-01-15T18:20:30.732
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.handlingunits.inventory.process.M_Inventory_CreateLines_ForLocatorAndProduct', Value='M_Inventory_CreateLines_ForLocatorAndProduct',Updated=TO_TIMESTAMP('2019-01-15 18:20:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540935
;

-- 2019-01-15T18:20:43.372
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.handlingunits.inventory.process.M_Inventory_CreateLines_RestrictBy_LocatorsAndValue', Value='M_Inventory_CreateLines_RestrictBy_LocatorsAndValue',Updated=TO_TIMESTAMP('2019-01-15 18:20:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540994
;

-- 2019-01-15T18:22:08.398
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsAutocomplete='Y',Updated=TO_TIMESTAMP('2019-01-15 18:22:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541272
;

-- 2019-01-15T18:27:51.928
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Zeilen erstellen (Lagerort & Produkt)',Updated=TO_TIMESTAMP('2019-01-15 18:27:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540935
;

-- 2019-01-15T18:27:59.809
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Zeilen erstellen (Lagerort-Anz. & Mind.-Warenwert)',Updated=TO_TIMESTAMP('2019-01-15 18:27:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540994
;

-- 2019-01-15T18:28:04.737
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-15 18:28:04','YYYY-MM-DD HH24:MI:SS'),Name='Zeilen erstellen (Lagerort-Anz. & Mind.-Warenwert)' WHERE AD_Process_ID=540994 AND AD_Language='de_CH'
;

-- 2019-01-15T18:28:12.043
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-15 18:28:12','YYYY-MM-DD HH24:MI:SS'),Name='Zeilen erstellen (Lagerort-Anz. & Mind.-Warenwert)' WHERE AD_Process_ID=540994 AND AD_Language='de_DE'
;

-- 2019-01-15T18:28:56.783
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-15 18:28:56','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Zeilen erstellen (Lagerort & Produkt)' WHERE AD_Process_ID=540935 AND AD_Language='de_CH'
;

-- 2019-01-15T18:29:00.584
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-15 18:29:00','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Zeilen erstellen (Lagerort & Produkt)' WHERE AD_Process_ID=540935 AND AD_Language='de_DE'
;

-- 2019-01-15T18:30:00.753
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-15 18:30:00','YYYY-MM-DD HH24:MI:SS'),Name='Create lines for locator & product',Description='' WHERE AD_Process_ID=540935 AND AD_Language='en_US'
;

-- 2019-01-15T18:30:32.492
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-15 18:30:32','YYYY-MM-DD HH24:MI:SS'),Name='Create lines restricted by locator count & value of goods' WHERE AD_Process_ID=540994 AND AD_Language='en_US'
;

-- 2019-01-15T18:36:13.747
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Zeilen erstellen (Lagerort-Anz. & Min.-Warenwert)',Updated=TO_TIMESTAMP('2019-01-15 18:36:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540994
;

-- 2019-01-15T18:36:17.105
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-15 18:36:17','YYYY-MM-DD HH24:MI:SS'),Name='Zeilen erstellen (Lagerort-Anz. & Min.-Warenwert)' WHERE AD_Process_ID=540994 AND AD_Language='de_CH'
;

-- 2019-01-15T18:36:19.793
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-15 18:36:19','YYYY-MM-DD HH24:MI:SS'),Name='Zeilen erstellen (Lagerort-Anz. & Min.-Warenwert)' WHERE AD_Process_ID=540994 AND AD_Language='de_DE'
;

-- 2019-01-16T08:02:08.750
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-16 08:02:08','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Max. Anzahl von Lagerorten',Description='',Help='' WHERE AD_Process_Para_ID=541331 AND AD_Language='de_CH'
;

-- 2019-01-16T08:02:15.053
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-16 08:02:15','YYYY-MM-DD HH24:MI:SS'),Name='Max. Anzahl von Lagerorten',Description='',Help='' WHERE AD_Process_Para_ID=541331 AND AD_Language='nl_NL'
;

-- 2019-01-16T08:02:34.239
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-16 08:02:34','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Max number of locators',Description='',Help='' WHERE AD_Process_Para_ID=541331 AND AD_Language='en_US'
;

-- 2019-01-16T08:02:46.419
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-16 08:02:46','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Max. Anzahl von Lagerorten',Description='',Help='' WHERE AD_Process_Para_ID=541331 AND AD_Language='de_DE'
;

-- 2019-01-16T08:03:44.177
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-16 08:03:44','YYYY-MM-DD HH24:MI:SS') WHERE AD_Process_Para_ID=541271 AND AD_Language='en_US'
;

-- 2019-01-16T08:04:31.772
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-16 08:04:31','YYYY-MM-DD HH24:MI:SS') WHERE AD_Process_Para_ID=541332 AND AD_Language='de_DE'
;

-- 2019-01-16T08:05:27.848
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsCentrallyMaintained='N',Updated=TO_TIMESTAMP('2019-01-16 08:05:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541332
;

-- 2019-01-16T08:05:31.013
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-16 08:05:31','YYYY-MM-DD HH24:MI:SS') WHERE AD_Process_Para_ID=541332 AND AD_Language='de_DE'
;

-- 2019-01-16T08:05:37.750
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-16 08:05:37','YYYY-MM-DD HH24:MI:SS') WHERE AD_Process_Para_ID=541332 AND AD_Language='de_DE'
;

-- 2019-01-16T08:11:04.922
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='Erstellt Inventurzeilen basierend auf dem aktuellen Lagerbestand.
Die zu erstellenden Zeilen können nach Lagerort und Produkt eingegrenzt werden.',Updated=TO_TIMESTAMP('2019-01-16 08:11:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540935
;

-- 2019-01-16T08:11:14.808
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-16 08:11:14','YYYY-MM-DD HH24:MI:SS'),Description='Erstellt Inventurzeilen basierend auf dem aktuellen Lagerbestand.
Die zu erstellenden Zeilen können nach Lagerort und Produkt eingegrenzt werden.' WHERE AD_Process_ID=540935 AND AD_Language='de_CH'
;

-- 2019-01-16T08:11:18.949
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-16 08:11:18','YYYY-MM-DD HH24:MI:SS'),Description='Erstellt Inventurzeilen basierend auf dem aktuellen Lagerbestand.
Die zu erstellenden Zeilen können nach Lagerort und Produkt eingegrenzt werden.' WHERE AD_Process_ID=540935 AND AD_Language='de_DE'
;

-- 2019-01-16T08:12:14.408
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-16 08:12:14','YYYY-MM-DD HH24:MI:SS'),Description='Creates inventory lines based on the current stock.
The lines to be created can be restricted by locator and product.' WHERE AD_Process_ID=540935 AND AD_Language='en_US'
;

-- 2019-01-16T08:15:08.748
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsCentrallyMaintained='Y',Updated=TO_TIMESTAMP('2019-01-16 08:15:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541332
;

-- 2019-01-16T08:15:11.757
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-16 08:15:11','YYYY-MM-DD HH24:MI:SS'),IsActive='N' WHERE AD_Process_Para_ID=541332 AND AD_Language='de_CH'
;

-- 2019-01-16T08:15:12.232
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-16 08:15:12','YYYY-MM-DD HH24:MI:SS'),IsActive='N' WHERE AD_Process_Para_ID=541332 AND AD_Language='nl_NL'
;

-- 2019-01-16T08:15:12.622
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-16 08:15:12','YYYY-MM-DD HH24:MI:SS'),IsActive='N' WHERE AD_Process_Para_ID=541332 AND AD_Language='en_US'
;

-- 2019-01-16T08:15:13.698
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-16 08:15:13','YYYY-MM-DD HH24:MI:SS'),IsActive='N' WHERE AD_Process_Para_ID=541332 AND AD_Language='de_DE'
;

-- 2019-01-16T08:15:19.861
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-16 08:15:19','YYYY-MM-DD HH24:MI:SS'),IsActive='N' WHERE AD_Process_Para_ID=541331 AND AD_Language='nl_NL'
;

-- 2019-01-16T08:15:20.395
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-16 08:15:20','YYYY-MM-DD HH24:MI:SS'),IsActive='N' WHERE AD_Process_Para_ID=541331 AND AD_Language='en_US'
;

-- 2019-01-16T08:15:21.052
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-16 08:15:21','YYYY-MM-DD HH24:MI:SS'),IsActive='N' WHERE AD_Process_Para_ID=541331 AND AD_Language='de_CH'
;

-- 2019-01-16T08:15:22.825
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-16 08:15:22','YYYY-MM-DD HH24:MI:SS'),IsActive='N' WHERE AD_Process_Para_ID=541331 AND AD_Language='de_DE'
;

-- 2019-01-16T08:15:45.393
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Element_ID=575952,Updated=TO_TIMESTAMP('2019-01-16 08:15:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541331
;

-- 2019-01-16T08:17:28.055
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='Beschrebung für Zeilen erstellen (Lagerort-Anz. & Min.-Warenwert)',Updated=TO_TIMESTAMP('2019-01-16 08:17:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540994
;

-- 2019-01-16T08:17:37.226
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='Beschreibung für Zeilen erstellen (Lagerort-Anz. & Min.-Warenwert)',Updated=TO_TIMESTAMP('2019-01-16 08:17:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540994
;

-- 2019-01-16T08:17:40.827
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-16 08:17:40','YYYY-MM-DD HH24:MI:SS'),Description='Beschreibung für Zeilen erstellen (Lagerort-Anz. & Min.-Warenwert)' WHERE AD_Process_ID=540994 AND AD_Language='de_CH'
;

-- 2019-01-16T08:17:43.020
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-16 08:17:43','YYYY-MM-DD HH24:MI:SS'),Description='Beschreibung für Zeilen erstellen (Lagerort-Anz. & Min.-Warenwert)' WHERE AD_Process_ID=540994 AND AD_Language='de_DE'
;

-- 2019-01-16T08:17:55.576
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-16 08:17:55','YYYY-MM-DD HH24:MI:SS'),Description='Description for Create lines restricted by locator count & value of goods' WHERE AD_Process_ID=540994 AND AD_Language='en_US'
;

-- 2019-01-16T08:22:14.894
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-16 08:22:14','YYYY-MM-DD HH24:MI:SS'),Description='Erlaubt es, nur Lagerorte zu berücksichtigen, die mindestens ein Produkt mit dem angegebenen Wert haben' WHERE AD_Element_ID=575953 AND AD_Language='de_CH'
;

-- 2019-01-16T08:22:14.905
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575953,'de_CH') 
;

-- 2019-01-16T08:22:20.107
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-16 08:22:20','YYYY-MM-DD HH24:MI:SS'),Description='Erlaubt es, nur Lagerorte zu berücksichtigen, die mindestens ein Produkt mit dem angegebenen Wert haben' WHERE AD_Element_ID=575953 AND AD_Language='de_DE'
;

-- 2019-01-16T08:22:20.118
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575953,'de_DE') 
;

-- 2019-01-16T08:22:20.142
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(575953,'de_DE') 
;

-- 2019-01-16T08:22:20.154
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='MinValueOfGoods', Name='Min. Warenwert', Description='Erlaubt es, nur Lagerorte zu berücksichtigen, die mindestens ein Produkt mit dem angegebenen Wert haben', Help=NULL WHERE AD_Element_ID=575953
;

-- 2019-01-16T08:22:20.162
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='MinValueOfGoods', Name='Min. Warenwert', Description='Erlaubt es, nur Lagerorte zu berücksichtigen, die mindestens ein Produkt mit dem angegebenen Wert haben', Help=NULL, AD_Element_ID=575953 WHERE UPPER(ColumnName)='MINVALUEOFGOODS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-01-16T08:22:20.169
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='MinValueOfGoods', Name='Min. Warenwert', Description='Erlaubt es, nur Lagerorte zu berücksichtigen, die mindestens ein Produkt mit dem angegebenen Wert haben', Help=NULL WHERE AD_Element_ID=575953 AND IsCentrallyMaintained='Y'
;

-- 2019-01-16T08:22:20.182
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Min. Warenwert', Description='Erlaubt es, nur Lagerorte zu berücksichtigen, die mindestens ein Produkt mit dem angegebenen Wert haben', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=575953) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 575953)
;

-- 2019-01-16T08:22:20.197
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Min. Warenwert', Description='Erlaubt es, nur Lagerorte zu berücksichtigen, die mindestens ein Produkt mit dem angegebenen Wert haben', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 575953
;

-- 2019-01-16T08:22:20.205
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Min. Warenwert', Description='Erlaubt es, nur Lagerorte zu berücksichtigen, die mindestens ein Produkt mit dem angegebenen Wert haben', Help=NULL WHERE AD_Element_ID = 575953
;

-- 2019-01-16T08:22:20.212
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name='Min. Warenwert', Description='Erlaubt es, nur Lagerorte zu berücksichtigen, die mindestens ein Produkt mit dem angegebenen Wert haben', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 575953
;

-- 2019-01-16T08:23:03.854
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-16 08:23:03','YYYY-MM-DD HH24:MI:SS'),Description='Allows it to ignore locatory that don''t have any product with at least the given value' WHERE AD_Element_ID=575953 AND AD_Language='en_US'
;

-- 2019-01-16T08:23:03.865
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575953,'en_US') 
;

-- 2019-01-16T10:45:15.587
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_ID=11, FieldLength=22, ValueMin='0',Updated=TO_TIMESTAMP('2019-01-16 10:45:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541331
;

-- 2019-01-16T10:45:29.681
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_ID=37,Updated=TO_TIMESTAMP('2019-01-16 10:45:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541332
;

-- 2019-01-16T12:21:02.725
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-16 12:21:02','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Inventur Zählliste',PrintName='Inventur Zählliste',Description='Abzuarbeitende Inventurpositionen' WHERE AD_Element_ID=573984 AND AD_Language='de_CH'
;

-- 2019-01-16T12:21:02.738
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(573984,'de_CH') 
;

-- 2019-01-16T12:21:21.671
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-16 12:21:21','YYYY-MM-DD HH24:MI:SS'),Description='Inventory lines that are to be counted' WHERE AD_Element_ID=573984 AND AD_Language='en_US'
;

-- 2019-01-16T12:21:21.680
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(573984,'en_US') 
;

-- 2019-01-16T12:21:36.080
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-16 12:21:36','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Inventur Zählliste',PrintName='Inventur Zählliste' WHERE AD_Element_ID=573984 AND AD_Language='de_DE'
;

-- 2019-01-16T12:21:36.091
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(573984,'de_DE') 
;

-- 2019-01-16T12:21:36.115
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(573984,'de_DE') 
;

-- 2019-01-16T12:21:36.126
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Inventur Zählliste', Description='M_InventoryLine_Counting', Help=NULL WHERE AD_Element_ID=573984
;

-- 2019-01-16T12:21:36.134
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Inventur Zählliste', Description='M_InventoryLine_Counting', Help=NULL WHERE AD_Element_ID=573984 AND IsCentrallyMaintained='Y'
;

-- 2019-01-16T12:21:36.141
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Inventur Zählliste', Description='M_InventoryLine_Counting', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=573984) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 573984)
;

-- 2019-01-16T12:21:36.158
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Inventur Zählliste', Name='Inventur Zählliste' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=573984)
;

-- 2019-01-16T12:21:36.171
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Inventur Zählliste', Description='M_InventoryLine_Counting', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 573984
;

-- 2019-01-16T12:21:36.179
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Inventur Zählliste', Description='M_InventoryLine_Counting', Help=NULL WHERE AD_Element_ID = 573984
;

-- 2019-01-16T12:21:36.204
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name='Inventur Zählliste', Description='M_InventoryLine_Counting', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 573984
;

-- 2019-01-16T12:21:42.706
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-16 12:21:42','YYYY-MM-DD HH24:MI:SS'),Description='Abzuarbeitende Inventurpositionen' WHERE AD_Element_ID=573984 AND AD_Language='de_DE'
;

-- 2019-01-16T12:21:42.719
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(573984,'de_DE') 
;

-- 2019-01-16T12:21:42.758
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(573984,'de_DE') 
;

-- 2019-01-16T12:21:42.776
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Inventur Zählliste', Description='Abzuarbeitende Inventurpositionen', Help=NULL WHERE AD_Element_ID=573984
;

-- 2019-01-16T12:21:42.788
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Inventur Zählliste', Description='Abzuarbeitende Inventurpositionen', Help=NULL WHERE AD_Element_ID=573984 AND IsCentrallyMaintained='Y'
;

-- 2019-01-16T12:21:42.797
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Inventur Zählliste', Description='Abzuarbeitende Inventurpositionen', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=573984) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 573984)
;

-- 2019-01-16T12:21:42.812
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Inventur Zählliste', Description='Abzuarbeitende Inventurpositionen', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 573984
;

-- 2019-01-16T12:21:42.820
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Inventur Zählliste', Description='Abzuarbeitende Inventurpositionen', Help=NULL WHERE AD_Element_ID = 573984
;

-- 2019-01-16T12:21:42.829
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name='Inventur Zählliste', Description='Abzuarbeitende Inventurpositionen', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 573984
;

-- 2019-01-16T12:22:22.131
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Name='M_InventoryLine',Updated=TO_TIMESTAMP('2019-01-16 12:22:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=322
;

-- 2019-01-16T12:25:22.384
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='N', SelectionColumnSeqNo=0,Updated=TO_TIMESTAMP('2019-01-16 12:25:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=3557
;

-- 2019-01-16T12:25:22.405
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='N', SelectionColumnSeqNo=0,Updated=TO_TIMESTAMP('2019-01-16 12:25:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=14101
;

-- 2019-01-16T12:25:22.414
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=10,Updated=TO_TIMESTAMP('2019-01-16 12:25:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560762
;

-- 2019-01-16T12:25:22.442
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=20,Updated=TO_TIMESTAMP('2019-01-16 12:25:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=3565
;

-- 2019-01-16T12:25:22.460
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=30,Updated=TO_TIMESTAMP('2019-01-16 12:25:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=3564
;

-- 2019-01-16T12:25:22.479
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', IsUpdateable='N', SelectionColumnSeqNo=40,Updated=TO_TIMESTAMP('2019-01-16 12:25:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=3563
;

-- 2019-01-16T12:28:32.465
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET MediaTypes='screen',Updated=TO_TIMESTAMP('2019-01-16 12:28:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552520
;

-- 2019-01-16T12:29:09.510
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2019-01-16 12:29:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552520
;

-- 2019-01-16T12:29:09.514
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2019-01-16 12:29:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552521
;

-- 2019-01-16T12:29:09.517
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2019-01-16 12:29:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552522
;

-- 2019-01-16T12:29:09.521
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2019-01-16 12:29:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552523
;

-- 2019-01-16T12:29:09.524
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2019-01-16 12:29:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552524
;

-- 2019-01-16T12:29:19.800
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET MediaTypes='',Updated=TO_TIMESTAMP('2019-01-16 12:29:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552520
;

-- 2019-01-16T12:30:03.917
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2019-01-16 12:30:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552518
;

-- 2019-01-16T12:30:06.208
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2019-01-16 12:30:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552519
;

-- 2019-01-16T12:30:08.042
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2019-01-16 12:30:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552525
;

-- 2019-01-16T12:30:10.029
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2019-01-16 12:30:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552520
;

-- 2019-01-16T12:30:12.057
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2019-01-16 12:30:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552521
;

-- 2019-01-16T12:30:13.760
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2019-01-16 12:30:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552522
;

-- 2019-01-16T12:30:16.210
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2019-01-16 12:30:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552524
;

-- 2019-01-16T12:30:19.324
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2019-01-16 12:30:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552523
;

-- 2019-01-16T12:36:37.040
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsEnableRemoteCacheInvalidation='Y', TechnicalNote='"Enable remote cache invalidation" is checked, because we want UI-updates to be propagated when users work with the M_InventoryLine_Counting window.',Updated=TO_TIMESTAMP('2019-01-16 12:36:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=322
;


-- enable remote cache invalidation for M_Inventory and M_InventoryLine
-- 2019-01-16T18:29:52.831
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsEnableRemoteCacheInvalidation='Y',Updated=TO_TIMESTAMP('2019-01-16 18:29:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=322
;

-- 2019-01-16T18:30:07.240
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsEnableRemoteCacheInvalidation='Y',Updated=TO_TIMESTAMP('2019-01-16 18:30:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=321
;

