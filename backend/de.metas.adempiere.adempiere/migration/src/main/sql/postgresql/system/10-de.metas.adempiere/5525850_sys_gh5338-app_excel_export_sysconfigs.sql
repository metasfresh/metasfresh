
-- 2019-06-24T16:05:24.073
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541283,'S',TO_TIMESTAMP('2019-06-24 16:05:23','YYYY-MM-DD HH24:MI:SS'),100,'When exporting data to excel, this the result size from which metasfresh won''t auto-size any columns','D','Y','de.metas.excel.MaxRowsToAllowCellWidthAutoSize',TO_TIMESTAMP('2019-06-24 16:05:23','YYYY-MM-DD HH24:MI:SS'),100,'100000')
;

-- 2019-06-24T16:09:16.133
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541284,'S',TO_TIMESTAMP('2019-06-24 16:09:15','YYYY-MM-DD HH24:MI:SS'),100,'When exporting a webui-view to excel and all rows are selected, then this is the number of rows metasfresh loads in one page','D','Y','de.metas.excel.ViewExcelExporter.AllRowsPageSize',TO_TIMESTAMP('2019-06-24 16:09:15','YYYY-MM-DD HH24:MI:SS'),100,'10000')
;

-- 2019-06-24T16:16:26.376
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='When exporting a webui-view to excel and all rows are selected, then this is the number of rows metasfresh loads in one page.',Updated=TO_TIMESTAMP('2019-06-24 16:16:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541284
;

