-- 2021-11-19T09:21:32.650Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540686,0,541943,TO_TIMESTAMP('2021-11-19 11:21:32','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','N','IDX_ExternalSystem_Status_WebService_Instance_ID','N',TO_TIMESTAMP('2021-11-19 11:21:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-19T09:21:32.654Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540686 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2021-11-19T09:21:53.910Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,578509,541227,540686,0,TO_TIMESTAMP('2021-11-19 11:21:53','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y',10,TO_TIMESTAMP('2021-11-19 11:21:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-19T09:23:44.492Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE INDEX IDX_ExternalSystem_Status_WebService_Instance_ID ON ExternalSystem_Status (ExternalSystem_WebService_Instance_ID)
;

