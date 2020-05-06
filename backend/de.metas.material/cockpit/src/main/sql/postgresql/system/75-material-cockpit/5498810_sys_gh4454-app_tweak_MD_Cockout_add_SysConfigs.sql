-- 2018-08-03T13:07:38.760
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541220,'S',TO_TIMESTAMP('2018-08-03 13:07:38','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.cockpit','Y','de.metas.ui.web.material.cockpit.field.qtyRequiredForProduction.IsDisplayed',TO_TIMESTAMP('2018-08-03 13:07:38','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;

-- 2018-08-03T13:21:20.516
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544197,0,'QtyAvailableToPromiseEstimate',TO_TIMESTAMP('2018-08-03 13:21:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','Zusagbare Menge (Zählbestand)','Zusagbare Menge (Zählbestand)',TO_TIMESTAMP('2018-08-03 13:21:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-08-03T13:21:20.522
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544197 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-08-03T13:21:37.349
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET Help=NULL, ColumnName='QtyAvailableToPromiseEstimate', Description=NULL, Name='Zusagbare Menge (Zählbestand)', AD_Element_ID=544197,Updated=TO_TIMESTAMP('2018-08-03 13:21:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558316
;

-- 2018-08-03T13:21:37.351
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zusagbare Menge (Zählbestand)', Description=NULL, Help=NULL WHERE AD_Column_ID=558316
;

SELECT db_alter_table('MD_Cockpit', 'ALTER TABLE MD_Cockpit RENAME COLUMN QtyAvailableToPromise TO QtyAvailableToPromiseEstimate;');

-- 2018-08-03T13:31:48.645
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Zusagbar Zählbestand', PrintName='Zusagbar Zählbestand',Updated=TO_TIMESTAMP('2018-08-03 13:31:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544197
;

-- 2018-08-03T13:31:48.647
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyAvailableToPromiseEstimate', Name='Zusagbar Zählbestand', Description=NULL, Help=NULL WHERE AD_Element_ID=544197
;

-- 2018-08-03T13:31:48.650
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyAvailableToPromiseEstimate', Name='Zusagbar Zählbestand', Description=NULL, Help=NULL, AD_Element_ID=544197 WHERE UPPER(ColumnName)='QTYAVAILABLETOPROMISEESTIMATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-08-03T13:31:48.652
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyAvailableToPromiseEstimate', Name='Zusagbar Zählbestand', Description=NULL, Help=NULL WHERE AD_Element_ID=544197 AND IsCentrallyMaintained='Y'
;

-- 2018-08-03T13:31:48.653
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zusagbar Zählbestand', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544197) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544197)
;

-- 2018-08-03T13:31:48.666
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Zusagbar Zählbestand', Name='Zusagbar Zählbestand' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=544197)
;

-- 2018-08-03T13:31:54.210
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Name='Zusagbar Zählbestand',PrintName='Zusagbar Zählbestand' WHERE AD_Element_ID=544197 AND AD_Language='de_CH'
;

-- 2018-08-03T13:31:54.213
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544197,'de_CH') 
;

-- 2018-08-03T13:32:14.659
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Name='ATP (Estimated)',PrintName='ATP (Estimated)' WHERE AD_Element_ID=544197 AND AD_Language='en_US'
;

-- 2018-08-03T13:32:14.662
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544197,'en_US') 
;

-- 2018-08-03T13:38:00.183
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541221,'S',TO_TIMESTAMP('2018-08-03 13:38:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.cockpit','Y','de.metas.ui.web.material.cockpit.field.QtyMaterialentnahme.IsDisplayed',TO_TIMESTAMP('2018-08-03 13:38:00','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

-- 2018-08-03T13:38:57.366
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541222,'S',TO_TIMESTAMP('2018-08-03 13:38:57','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.cockpit','Y','de.metas.ui.web.material.cockpit.field.PackageSize.IsDisplayed',TO_TIMESTAMP('2018-08-03 13:38:57','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

-- 2018-08-03T13:39:56.628
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541223,'S',TO_TIMESTAMP('2018-08-03 13:39:56','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.cockpit','Y','de.metas.ui.web.material.cockpit.field.Manufacturer_ID.IsDisplayed',TO_TIMESTAMP('2018-08-03 13:39:56','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

-- 2018-08-03T13:41:24.582
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541224,'S',TO_TIMESTAMP('2018-08-03 13:41:24','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.cockpit','Y','de.metas.ui.web.material.cockpit.field.C_UOM_ID.IsDisplayed',TO_TIMESTAMP('2018-08-03 13:41:24','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;

-- 2018-08-03T13:42:18.650
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541225,'S',TO_TIMESTAMP('2018-08-03 13:42:18','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.cockpit','Y','de.metas.ui.web.material.cockpit.field.QtyOnHandEstimate.IsDisplayed',TO_TIMESTAMP('2018-08-03 13:42:18','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

-- 2018-08-03T13:43:03.577
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541226,'S',TO_TIMESTAMP('2018-08-03 13:43:03','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.cockpit','Y','de.metas.ui.web.material.cockpit.field.pmmQtyPromised.IsDisplayed',TO_TIMESTAMP('2018-08-03 13:43:03','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

-- 2018-08-03T13:43:58.563
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541227,'S',TO_TIMESTAMP('2018-08-03 13:43:58','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.cockpit','Y','de.metas.ui.web.material.cockpit.field.QtyAvailableToPromiseEstimate.IsDisplayed',TO_TIMESTAMP('2018-08-03 13:43:58','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

