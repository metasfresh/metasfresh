-- 2019-10-11T10:38:54.948Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540503,0,533,TO_TIMESTAMP('2019-10-11 13:38:54','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','GlobalId_Index','N',TO_TIMESTAMP('2019-10-11 13:38:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-11T10:38:54.959Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Index_Table_ID=540503 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2019-10-11T10:39:05.851Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,568963,540964,540503,0,TO_TIMESTAMP('2019-10-11 13:39:05','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2019-10-11 13:39:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-11T10:39:08.309Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE INDEX GlobalId_Index ON I_BPartner (GlobalId)
;

