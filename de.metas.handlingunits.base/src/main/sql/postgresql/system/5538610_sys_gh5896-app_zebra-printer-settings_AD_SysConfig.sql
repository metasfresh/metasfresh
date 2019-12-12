-- 2019-12-10T16:39:39.327Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_SysConfig WHERE AD_SysConfig_ID=541293
;

-- 2019-12-10T16:39:47.387Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_SysConfig WHERE AD_SysConfig_ID=541294
;

-- 2019-12-10T16:40:19.982Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET ConfigurationLevel='O', Name='de.metas.handlingunit.GS1ManufacturerCode',Updated=TO_TIMESTAMP('2019-12-10 17:40:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541296
;

-- 2019-12-10T16:51:41.641Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (1000000,1000000,541297,'O',TO_TIMESTAMP('2019-12-10 17:51:41','YYYY-MM-DD HH24:MI:SS'),100,'Layout file which will become a part of the bartender control line that we add to CSVs files to be send to zebra bar code printers.
','de.metas.handlingunits','Y','de.metas.handlingunit.sscc18Label.zebra.layoutFile',TO_TIMESTAMP('2019-12-10 17:51:41','YYYY-MM-DD HH24:MI:SS'),100,'\\V-APSRV01\PRAGMA\ETIKETTEN\LAYOUTS\SSCC.BTW')
;

-- 2019-12-10T16:53:02.701Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (1000000,0,541298,'O',TO_TIMESTAMP('2019-12-10 17:53:02','YYYY-MM-DD HH24:MI:SS'),100,'Layout file which will become a part of the bartender control line that we add to CSVs files to be send to zebra bar code printers.
Example \\windows-share\folder\layoutFile.BTW
','U','Y','de.metas.handlingunit.sscc18Label.zebra.layoutFile',TO_TIMESTAMP('2019-12-10 17:53:02','YYYY-MM-DD HH24:MI:SS'),100,'<specify-path to layout file>')
;

-- 2019-12-10T16:54:23.925Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET EntityType='de.metas.handlingunits', Value='<layout file>.BTW',Updated=TO_TIMESTAMP('2019-12-10 17:54:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541298
;

-- 2019-12-10T16:56:52.578Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (1000000,0,541299,'O',TO_TIMESTAMP('2019-12-10 17:56:52','YYYY-MM-DD HH24:MI:SS'),100,'Printer device name of the zebra printer that shall do the printing
Example "\\windows-server\Zebra110XiII','de.metas.handlingunits','Y','de.metas.handlingunit.sscc18Label.zebra.printer',TO_TIMESTAMP('2019-12-10 17:56:52','YYYY-MM-DD HH24:MI:SS'),100,'<printer service name>')
;

-- 2019-12-10T16:57:12.496Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='Layout file which will become a part of the bartender control line that we add to CSVs files to be send to zebra bar code printers. Example \\windows-share\folder\layoutFile.BTW ',Updated=TO_TIMESTAMP('2019-12-10 17:57:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541298
;

-- 2019-12-10T16:58:17.362Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (1000000,1000000,541300,'O',TO_TIMESTAMP('2019-12-10 17:58:17','YYYY-MM-DD HH24:MI:SS'),100,'Printer device name of the zebra printer that shall do the printing.','de.metas.handlingunits','Y','de.metas.handlingunit.sscc18Label.zebra.printer',TO_TIMESTAMP('2019-12-10 17:58:17','YYYY-MM-DD HH24:MI:SS'),100,'\\V-DCSRV02\ETIKETTEN01')
;

-- 2019-12-10T16:59:00.337Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET ConfigurationLevel='O', Description='Layout file which will become a part of the bartender control line that we add to CSVs files to be send to zebra bar code printers. ',Updated=TO_TIMESTAMP('2019-12-10 17:59:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541297
;

-- 2019-12-10T17:04:22.382Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (1000000,0,541301,'O',TO_TIMESTAMP('2019-12-10 18:04:22','YYYY-MM-DD HH24:MI:SS'),100,'SELECT database statement that retrieves the actual data for the SSCC18-Label(s)
Needs to have EDI_DesadvLine_Pack_ID as a column','de.metas.handlingunits','Y','de.metas.handlingunit.sscc18Label.zebra.sql-select',TO_TIMESTAMP('2019-12-10 18:04:22','YYYY-MM-DD HH24:MI:SS'),100,'<SELECT * FROM M_SSCC18_Label_Data_VIEW>')
;

