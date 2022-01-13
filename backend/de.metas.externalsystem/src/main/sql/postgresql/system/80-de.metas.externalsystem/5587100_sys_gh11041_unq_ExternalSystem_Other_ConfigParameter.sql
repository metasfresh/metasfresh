-- 2021-04-30T09:10:28.079Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540591,0,541633,TO_TIMESTAMP('2021-04-30 12:10:27','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Y','unq_ExternalSystem_Other_ConfigParam','N',TO_TIMESTAMP('2021-04-30 12:10:27','YYYY-MM-DD HH24:MI:SS'),100,'IsActive=''Y''')
;

-- 2021-04-30T09:10:28.081Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540591 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2021-04-30T09:10:39.890Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET EntityType='de.metas.externalsystem',Updated=TO_TIMESTAMP('2021-04-30 12:10:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540591
;

-- 2021-04-30T09:10:57.745Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,573701,541100,540591,0,TO_TIMESTAMP('2021-04-30 12:10:57','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y',10,TO_TIMESTAMP('2021-04-30 12:10:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-30T09:11:12.164Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,573702,541101,540591,0,TO_TIMESTAMP('2021-04-30 12:11:12','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y',20,TO_TIMESTAMP('2021-04-30 12:11:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-30T09:11:15.389Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX unq_ExternalSystem_Other_ConfigParam ON ExternalSystem_Other_ConfigParameter (ExternalSystem_Config_ID,Name) WHERE IsActive='Y'
;

