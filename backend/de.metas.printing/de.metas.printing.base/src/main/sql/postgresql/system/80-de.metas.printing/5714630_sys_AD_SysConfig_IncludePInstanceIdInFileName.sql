-- SysConfig Name: de.metas.printing.IncludePInstanceIdInFileName
-- SysConfig Value: N
-- 2023-12-22T12:43:33.853075600Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541680,'O',TO_TIMESTAMP('2023-12-22 13:43:33.682','YYYY-MM-DD HH24:MI:SS.US'),100,'If set to Y, and something is printed with an AD_PrinterHW that has OutputType=Store, then metasfresh prepends "<AD_PInstance_ID>_" to the PDF filename.
AD_PInstance_ID is the ID of the (jasper-)process-instance which created the respective PDF.
If *both* this systeconfig * and* de.metas.printing.IncludeSystemTimeMSInFileName are set to Y, then the filename contains first the AD_PInstance_ID, followed by the timestamp.','de.metas.printing','Y','de.metas.printing.IncludePInstanceIdInFileName',TO_TIMESTAMP('2023-12-22 13:43:33.682','YYYY-MM-DD HH24:MI:SS.US'),100,'N')
;

-- SysConfig Name: de.metas.printing.IncludeSystemTimeMSInFileName
-- SysConfig Value: Y
-- 2023-12-22T12:46:25.755804400Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541681,'S',TO_TIMESTAMP('2023-12-22 13:46:25.627','YYYY-MM-DD HH24:MI:SS.US'),100,'If *both* this system-config *and* de.metas.printing.IncludePInstanceIdInFileName are set to Y, then the filename contains first the AD_PInstance_ID, followed by the timestamp.','de.metas.printing','Y','de.metas.printing.IncludeSystemTimeMSInFileName',TO_TIMESTAMP('2023-12-22 13:46:25.627','YYYY-MM-DD HH24:MI:SS.US'),100,'Y')
;

-- SysConfig Name: de.metas.printing.IncludeSystemTimeMSInFileName
-- SysConfig Value: Y
-- 2023-12-22T12:46:31.657868700Z
UPDATE AD_SysConfig SET ConfigurationLevel='O',Updated=TO_TIMESTAMP('2023-12-22 13:46:31.654','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_SysConfig_ID=541681
;

-- SysConfig Name: de.metas.printing.IncludeSystemTimeMSInFileName
-- SysConfig Value: Y
-- 2023-12-22T12:50:19.942700100Z
UPDATE AD_SysConfig SET ConfigurationLevel='O', Description='If set to Y, and something is printed with an AD_PrinterHW that has OutputType=Store, then metasfresh prepends "<Timestamp>_" to the PDF filename.
<Timestamp> is the timestamp in milliseconds since 01-01-1970.
If *both* this system-config *and* de.metas.printing.IncludePInstanceIdInFileName are set to Y, then the filename contains first the AD_PInstance_ID, followed by the timestamp.',Updated=TO_TIMESTAMP('2023-12-22 13:50:19.938','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_SysConfig_ID=541681
;

-- SysConfig Name: de.metas.printing.IncludePInstanceIdInFileName
-- SysConfig Value: N
-- 2023-12-22T12:51:00.736744500Z
UPDATE AD_SysConfig SET ConfigurationLevel='O', Description='If set to Y and something is printed with an AD_PrinterHW that has OutputType=Store, then metasfresh prepends "<AD_PInstance_ID>_" to the PDF filename.
<AD_PInstance_ID> is the ID of the (jasper-)process-instance which created the respective PDF.
If *both* this system-config *and* de.metas.printing.IncludeSystemTimeMSInFileName are set to Y, then the filename contains first the AD_PInstance_ID, followed by the timestamp.',Updated=TO_TIMESTAMP('2023-12-22 13:51:00.732','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_SysConfig_ID=541680
;

-- SysConfig Name: de.metas.printing.IncludeSystemTimeMSInFileName
-- SysConfig Value: Y
-- 2023-12-22T12:51:09.554292Z
UPDATE AD_SysConfig SET ConfigurationLevel='O', Description='If set to Y and something is printed with an AD_PrinterHW that has OutputType=Store, then metasfresh prepends "<Timestamp>_" to the PDF filename.
<Timestamp> is the timestamp in milliseconds since 01-01-1970.
If *both* this system-config *and* de.metas.printing.IncludePInstanceIdInFileName are set to Y, then the filename contains first the AD_PInstance_ID, followed by the timestamp.',Updated=TO_TIMESTAMP('2023-12-22 13:51:09.55','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_SysConfig_ID=541681
;

-- SysConfig Name: de.metas.printing.IncludePInstanceIdInFileName
-- SysConfig Value: N
-- 2023-12-22T13:08:44.536434800Z
UPDATE AD_SysConfig SET ConfigurationLevel='O', Description='If set to Y and something is printed with an AD_PrinterHW that has OutputType=Store, then metasfresh prepends "<AD_PInstance_ID>_" to the PDF filename.
<AD_PInstance_ID> is the ID of the (jasper-)process-instance which created the respective PDF, as stored in respecting the AD_Archive record.
If the AD_Archive in question has no AD_PInstance_ID for whatever reason, then this system-config is ignored.
If *both* this system-config *and* de.metas.printing.IncludeSystemTimeMSInFileName are set to Y, then the filename contains first the AD_PInstance_ID, followed by the timestamp.',Updated=TO_TIMESTAMP('2023-12-22 14:08:44.532','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_SysConfig_ID=541680
;

