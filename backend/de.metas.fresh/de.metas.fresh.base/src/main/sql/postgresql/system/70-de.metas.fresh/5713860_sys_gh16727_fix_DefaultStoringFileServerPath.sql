
-- The previously set value "/opt/metasfresh-webui-api/process_created_files" does not cut it, 
-- because this needs to work for both webapi **and app**
UPDATE AD_SysConfig SET Value='/opt/metasfresh_process_created_files', Updated=TO_TIMESTAMP('2023-12-18 11:08:45.997', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100 WHERE AD_SysConfig_ID=541655;
