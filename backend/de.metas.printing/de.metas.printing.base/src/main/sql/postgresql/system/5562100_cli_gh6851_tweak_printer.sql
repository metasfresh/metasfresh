-- 2020-06-23T06:21:09.543Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (1000000,0,541325,'C',TO_TIMESTAMP('2020-06-23 08:21:09','YYYY-MM-DD HH24:MI:SS'),100,'Directory into which PDF data is stored for Pritners with output-type "Store".
If inactive or not set, then the temp-folder of the respective metasfresh-server will be used.','de.metas.printing','Y','de.metas.printing.StorePDFBaseDirectory',TO_TIMESTAMP('2020-06-23 08:21:09','YYYY-MM-DD HH24:MI:SS'),100,'/tmp/')
;

-- 2020-06-23T06:24:02.766Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (1000000,0,541326,'O',TO_TIMESTAMP('2020-06-23 08:24:02','YYYY-MM-DD HH24:MI:SS'),100,'If set to Y and metasfresh stores a file locally for a printer with output type "Store",
then the metasfresh server''s current time in milliseconds is prepended to the PDF-filename.
The goal of this is to avoid name collissions in case a printing queue item is printed more than once.','de.metas.printing','Y','de.metas.printing.IncludeSystemTimeMSInFileName',TO_TIMESTAMP('2020-06-23 08:24:02','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;

-- 2020-06-23T06:24:09.728Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET ConfigurationLevel='O',Updated=TO_TIMESTAMP('2020-06-23 08:24:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541325
;

-- 2020-06-23T06:25:24.587Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='Directory into which PDF data is stored for Pritners with output-type "Store".
If inactive or not set, then the temp-folder of the respective metasfresh-server will be used.
Note that this directory needs to exist.',Updated=TO_TIMESTAMP('2020-06-23 08:25:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541325
;

-- 2020-06-23T06:26:11.563Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='Directory into which PDF data is stored for printers with output-type "Store".
If inactive or not set, then the temp-folder of the respective metasfresh-server will be used.
Note that this directory needs to exist.',Updated=TO_TIMESTAMP('2020-06-23 08:26:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541325
;

-- 2020-06-23T06:32:09.700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET IsActive='N',Updated=TO_TIMESTAMP('2020-06-23 08:32:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541325
;

UPDATE AD_PrinterHW SET 
    Name='print-to-disk', 
    AD_PrinterHW_ID=540331,
    Description='Wenn mit diesem Drucker gedruckt wird, dann wird eine PDF-Datei innerhalb des Verzeichnisses "print-to-disk" (Name dieses Druckers) abgelegt. Siehe auch System-Konfig "de.metas.printing.StorePDFBaseDirectory"'
WHERE AD_PrinterHW_ID=1000006;


UPDATE AD_Printer_Config SET UpdatedBy=99, Updated='2020-06-23 17:06:39.646094+00', ConfigHostKey=TRIM(ConfigHostKey) WHERE TRIM(ConfigHostKey)!=ConfigHostKey;
UPDATE AD_Printer_Config SET UpdatedBy=99, Updated='2020-06-23 17:06:39.646094+00', ConfigHostKey=NULL WHERE TRIM(ConfigHostKey)='';

UPDATE AD_PrinterHW SET UpdatedBy=99, Updated='2020-06-23 17:06:39.646094+00', HostKey=TRIM(HostKey) WHERE TRIM(HostKey)!=HostKey;
UPDATE AD_PrinterHW SET UpdatedBy=99, Updated='2020-06-23 17:06:39.646094+00', HostKey=NULL WHERE TRIM(HostKey)='';
