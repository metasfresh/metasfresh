-- 2021-04-26T13:04:38.423Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540590,0,541627,TO_TIMESTAMP('2021-04-26 16:04:38','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N','unq_ExternalSystem_RuntimeParameter','N',TO_TIMESTAMP('2021-04-26 16:04:38','YYYY-MM-DD HH24:MI:SS'),100,'IsActive=''Y''')
;

-- 2021-04-26T13:04:38.428Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540590 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2021-04-26T13:04:51.379Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,573624,541097,540590,0,TO_TIMESTAMP('2021-04-26 16:04:51','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y',10,TO_TIMESTAMP('2021-04-26 16:04:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T13:05:03.272Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,573623,541098,540590,0,TO_TIMESTAMP('2021-04-26 16:05:03','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y',20,TO_TIMESTAMP('2021-04-26 16:05:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T13:05:14.636Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,573621,541099,540590,0,TO_TIMESTAMP('2021-04-26 16:05:14','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y',30,TO_TIMESTAMP('2021-04-26 16:05:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T13:05:27.199Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET EntityType='de.metas.externalsystem',Updated=TO_TIMESTAMP('2021-04-26 16:05:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540590
;

-- 2021-04-26T13:05:31.766Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE INDEX unq_ExternalSystem_RuntimeParameter ON ExternalSystem_RuntimeParameter (ExternalSystem_Config_ID,External_Request,Name) WHERE IsActive='Y'
;

