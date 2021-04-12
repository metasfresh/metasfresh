-- 2021-03-21T12:00:17.968Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,542351,541255,TO_TIMESTAMP('2021-03-21 14:00:17','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Other',TO_TIMESTAMP('2021-03-21 14:00:17','YYYY-MM-DD HH24:MI:SS'),100,'Other','Other')
;

-- 2021-03-21T12:00:17.978Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542351 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-03-21T12:30:15.218Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540576,0,541603,TO_TIMESTAMP('2021-03-21 14:30:15','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N','idx_ExternalSystemExportAudit_RecordId_TableId_System','N',TO_TIMESTAMP('2021-03-21 14:30:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-21T12:30:15.221Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540576 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2021-03-21T12:30:33.074Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,573189,541071,540576,0,TO_TIMESTAMP('2021-03-21 14:30:32','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2021-03-21 14:30:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-21T12:30:41.849Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,573190,541072,540576,0,TO_TIMESTAMP('2021-03-21 14:30:41','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',20,TO_TIMESTAMP('2021-03-21 14:30:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-21T12:30:57.850Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,573194,541073,540576,0,TO_TIMESTAMP('2021-03-21 14:30:57','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',30,TO_TIMESTAMP('2021-03-21 14:30:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-21T12:31:01.783Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET EntityType='D',Updated=TO_TIMESTAMP('2021-03-21 14:31:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540576
;

-- 2021-03-21T12:31:05.296Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE INDEX idx_ExternalSystemExportAudit_RecordId_TableId_System ON ExternalSystem_ExportAudit (Record_ID,AD_Table_ID,ExternalSystemType)
;
