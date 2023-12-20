-- Name: AD_PrinterHW_ForHostKey
-- 2023-07-20T06:41:41.178Z
UPDATE AD_Val_Rule SET Code='AD_PrinterHW.HostKey=''@ConfigHostKey/x@'' OR AD_PrinterHW.OutputType IN (''Attach'', ''Store'') OR AD_PrinterHW.ExternalSystem_Config_ID IS NOT NULL',Updated=TO_TIMESTAMP('2023-07-20 08:41:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540166
;

