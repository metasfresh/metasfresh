-- 2017-07-31T16:18:15.938
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541156,'S',TO_TIMESTAMP('2017-07-31 16:18:15','YYYY-MM-DD HH24:MI:SS'),100,'Decides if all processed picking candidates (table M_Picking_Candidate) are closed when the picking-view (AD_Window_ID=540350) is closed via the "Confirm" button.
Closed picking candidates can''t be accessed anymore from that window.
See https://github.com/metasfresh/metasfresh/issues/2101','de.metas.picking','Y','WEBUI_Picking_Close_PickingCandidatesOnWindowClose',TO_TIMESTAMP('2017-07-31 16:18:15','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

-- 2017-07-31T16:22:16.322
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET ConfigurationLevel='O',Updated=TO_TIMESTAMP('2017-07-31 16:22:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541156
;

