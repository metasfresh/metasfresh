-- 2021-09-14T11:10:27.075971300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Index_Table_Trl WHERE AD_Index_Table_ID=540631
;

-- 2021-09-14T11:10:27.082018700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Index_Table WHERE AD_Index_Table_ID=540631
;

-- 2021-09-14T11:10:29.403060800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Index_Table_Trl WHERE AD_Index_Table_ID=540632
;

-- 2021-09-14T11:10:29.407067900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Index_Table WHERE AD_Index_Table_ID=540632
;

-- 2021-09-14T11:10:53.673332400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,Description,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540643,0,541803,TO_TIMESTAMP('2021-09-14 14:10:53','YYYY-MM-DD HH24:MI:SS'),100,'Unique constraint on parent config id','D','Y','Y','IDX_S_ExternalSystemRabbitMQ_unique_parent_id','N',TO_TIMESTAMP('2021-09-14 14:10:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-14T11:10:53.673332400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540643 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2021-09-14T11:11:15.511333100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,575936,541164,540643,0,TO_TIMESTAMP('2021-09-14 14:11:15','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2021-09-14 14:11:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-14T11:11:24.106159400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX IDX_S_ExternalSystemRabbitMQ_unique_parent_id ON ExternalSystem_Config_RabbitMQ_HTTP (ExternalSystem_Config_ID)
;

-- 2021-09-14T11:11:47.857933700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,Description,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540644,0,541803,TO_TIMESTAMP('2021-09-14 14:11:47','YYYY-MM-DD HH24:MI:SS'),100,'Unique index external system rabbit value','de.metas.externalsystem','Y','Y','IDX_S_ExternalSystemRabbitMQ_unique_value','N',TO_TIMESTAMP('2021-09-14 14:11:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-14T11:11:47.857933700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540644 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2021-09-14T11:12:08.276015500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,575935,541165,540644,0,TO_TIMESTAMP('2021-09-14 14:12:08','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y',10,TO_TIMESTAMP('2021-09-14 14:12:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-14T11:12:16.048700300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX IDX_S_ExternalSystemRabbitMQ_unique_value ON ExternalSystem_Config_RabbitMQ_HTTP (ExternalSystemValue)
;

-- 2021-09-14T11:14:30.782713200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Index_Table_Trl WHERE AD_Index_Table_ID=540633
;

-- 2021-09-14T11:14:30.787715300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Index_Table WHERE AD_Index_Table_ID=540633
;

-- 2021-09-14T11:14:33.276180600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Index_Table_Trl WHERE AD_Index_Table_ID=540634
;

-- 2021-09-14T11:14:33.281213700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Index_Table WHERE AD_Index_Table_ID=540634
;

-- 2021-09-14T11:14:35.216132100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Index_Table_Trl WHERE AD_Index_Table_ID=540635
;

-- 2021-09-14T11:14:35.218127900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Index_Table WHERE AD_Index_Table_ID=540635
;

-- 2021-09-14T11:14:57.324847200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,Description,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540645,0,541804,TO_TIMESTAMP('2021-09-14 14:14:57','YYYY-MM-DD HH24:MI:SS'),100,'Constraint on parent data export audit id','D','Y','N','IDX_DataExportAudit_parent_id','N',TO_TIMESTAMP('2021-09-14 14:14:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-14T11:14:57.324847200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540645 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2021-09-14T11:15:17.741939300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,575948,541166,540645,0,TO_TIMESTAMP('2021-09-14 14:15:17','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2021-09-14 14:15:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-14T11:15:21.088013200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE INDEX IDX_DataExportAudit_parent_id ON Data_Export_Audit (Data_Export_Audit_Parent_ID)
;

-- 2021-09-14T11:15:46.005977300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,Description,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540646,0,541804,TO_TIMESTAMP('2021-09-14 14:15:45','YYYY-MM-DD HH24:MI:SS'),100,'Unique constraint on record_id and ad_table_id','D','Y','Y','IDX_DataExportAudit_unique_record_id_and_table_id','N',TO_TIMESTAMP('2021-09-14 14:15:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-14T11:15:46.005977300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540646 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2021-09-14T11:16:08.748311700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,575947,541167,540646,0,TO_TIMESTAMP('2021-09-14 14:16:08','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2021-09-14 14:16:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-14T11:16:23.627626100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,575946,541168,540646,0,TO_TIMESTAMP('2021-09-14 14:16:23','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',20,TO_TIMESTAMP('2021-09-14 14:16:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-14T11:16:40.752284100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX IDX_DataExportAudit_unique_record_id_and_table_id ON Data_Export_Audit (Record_ID,AD_Table_ID)
;

-- 2021-09-14T11:17:31.277929500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Index_Table_Trl WHERE AD_Index_Table_ID=540636
;

-- 2021-09-14T11:17:31.280930700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Index_Table WHERE AD_Index_Table_ID=540636
;

-- 2021-09-14T11:17:49.928677500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540647,0,541805,TO_TIMESTAMP('2021-09-14 14:17:49','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','IDX_DataExportAuditId_and_ExternalSystemConfig_ID','N',TO_TIMESTAMP('2021-09-14 14:17:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-14T11:17:49.930680200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540647 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2021-09-14T11:18:11.610970900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,575957,541169,540647,0,TO_TIMESTAMP('2021-09-14 14:18:11','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2021-09-14 14:18:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-14T11:18:24.247594600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,575959,541170,540647,0,TO_TIMESTAMP('2021-09-14 14:18:24','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',20,TO_TIMESTAMP('2021-09-14 14:18:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-14T11:18:27.459131700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE INDEX IDX_DataExportAuditId_and_ExternalSystemConfig_ID ON Data_Export_Audit_Log (Data_Export_Audit_ID,ExternalSystem_Config_ID)
;