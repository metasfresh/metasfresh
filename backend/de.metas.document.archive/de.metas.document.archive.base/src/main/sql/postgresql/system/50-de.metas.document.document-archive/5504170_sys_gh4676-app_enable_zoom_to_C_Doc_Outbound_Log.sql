
SELECT db_Alter_table ('c_doc_outbound_log_line','ALTER TABLE c_doc_outbound_log_line ALTER COLUMN action TYPE character varying (16)');

-- 2018-10-18T22:50:31.183
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET TechnicalNote='The column is "lazy" because we want the binary data to be loaded on demand only.
Otherwise we might run into performance or memory problems.',Updated=TO_TIMESTAMP('2018-10-18 22:50:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557031
;

-- allow to zoom to C_DocOutbound_Log
-- 2018-10-18T23:05:35.297
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,IsActive,Created,CreatedBy,IsOrderByValue,Updated,UpdatedBy,AD_Reference_ID,ValidationType,Name,AD_Org_ID,EntityType) VALUES (0,'Y',TO_TIMESTAMP('2018-10-18 23:05:35','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2018-10-18 23:05:35','YYYY-MM-DD HH24:MI:SS'),100,540926,'T','C_Doc_Outbound_Log',0,'de.metas.document.archive')
;

-- 2018-10-18T23:05:35.301
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540926 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2018-10-18T23:06:29.857
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Reference_ID,AD_Key,AD_Client_ID,IsActive,Created,CreatedBy,Updated,IsValueDisplayed,AD_Window_ID,UpdatedBy,AD_Table_ID,AD_Org_ID,EntityType) VALUES (540926,548157,0,'Y',TO_TIMESTAMP('2018-10-18 23:06:29','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2018-10-18 23:06:29','YYYY-MM-DD HH24:MI:SS'),'N',540170,100,540453,0,'de.metas.document.archive')
;

-- 2018-10-18T23:06:44.920
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (Created,CreatedBy,IsActive,AD_Client_ID,Updated,UpdatedBy,AD_RelationType_ID,AD_Reference_Target_ID,AD_Org_ID,Name,IsTableRecordIdTarget,IsDirected,EntityType) VALUES (TO_TIMESTAMP('2018-10-18 23:06:44','YYYY-MM-DD HH24:MI:SS'),100,'Y',0,TO_TIMESTAMP('2018-10-18 23:06:44','YYYY-MM-DD HH24:MI:SS'),100,540222,540926,0,'C_Doc_Outbound_Log_TableRecordIdTarget','Y','N','de.metas.document.archive')
;

