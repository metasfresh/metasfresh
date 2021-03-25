
-- 2021-03-25T09:01:49.944057900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540578,0,541601,TO_TIMESTAMP('2021-03-25 11:01:49','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','AD_OrgChange_History_C_BPartner_From','N',TO_TIMESTAMP('2021-03-25 11:01:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-25T09:01:49.963098Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540578 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2021-03-25T09:02:04.236571500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,573232,541075,540578,0,TO_TIMESTAMP('2021-03-25 11:02:04','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2021-03-25 11:02:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-25T09:02:05.735049600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE INDEX AD_OrgChange_History_C_BPartner_From ON AD_OrgChange_History (C_BPartner_From_ID)
;

-- 2021-03-25T09:02:22.207294400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540579,0,541601,TO_TIMESTAMP('2021-03-25 11:02:22','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','AD_OrgChange_History_C_BPartner_To','N',TO_TIMESTAMP('2021-03-25 11:02:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-25T09:02:22.211848400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540579 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2021-03-25T09:02:31.233765700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,573234,541076,540579,0,TO_TIMESTAMP('2021-03-25 11:02:31','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2021-03-25 11:02:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-25T09:02:32.092339600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE INDEX AD_OrgChange_History_C_BPartner_To ON AD_OrgChange_History (C_BPartner_To_ID)
;

-- 2021-03-25T09:09:41.635188700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540580,0,541601,TO_TIMESTAMP('2021-03-25 11:09:41','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','AD_OrgChange_History_OrgChangeDate','N',TO_TIMESTAMP('2021-03-25 11:09:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-25T09:09:41.640366800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540580 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2021-03-25T09:09:57.833750100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,573233,541077,540580,0,TO_TIMESTAMP('2021-03-25 11:09:57','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2021-03-25 11:09:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-25T09:09:58.160945700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE INDEX AD_OrgChange_History_OrgChangeDate ON AD_OrgChange_History (Date_OrgChange)
;

