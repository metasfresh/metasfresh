
-- 2019-10-04T08:04:52.696Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540501,0,291,TO_TIMESTAMP('2019-10-04 11:04:52','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Y','C_BPartner_GlobalId_Uniqe','N',TO_TIMESTAMP('2019-10-04 11:04:52','YYYY-MM-DD HH24:MI:SS'),100,'IsActive=''Y'' AND COALESCE(GlobalId, '''') != ''''')
;

-- 2019-10-04T08:04:52.704Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Index_Table_ID=540501 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2019-10-04T08:10:01.528Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,558551,540961,540501,0,TO_TIMESTAMP('2019-10-04 11:10:01','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2019-10-04 11:10:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-04T08:10:08.457Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,2895,540962,540501,0,TO_TIMESTAMP('2019-10-04 11:10:08','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',20,TO_TIMESTAMP('2019-10-04 11:10:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-04T08:10:10.041Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX C_BPartner_GlobalId_Uniqe ON C_BPartner (GlobalId,AD_Org_ID) WHERE IsActive='Y' AND COALESCE(GlobalId, '') != ''
;
