--
-- unrelated to issue 2302, but make sure that AD_Val_Rule.Name is unique
--

-- 2017-09-04T10:04:56.732
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540402,0,108,TO_TIMESTAMP('2017-09-04 10:04:56','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Y','AD_Val_Rule_UC_Name','N',TO_TIMESTAMP('2017-09-04 10:04:56','YYYY-MM-DD HH24:MI:SS'),100,'IsActive=''Y''')
;

-- 2017-09-04T10:04:56.747
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Index_Table_ID=540402 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2017-09-04T10:05:07.645
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,188,540805,540402,0,TO_TIMESTAMP('2017-09-04 10:05:07','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2017-09-04 10:05:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-04T10:05:10.065
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX AD_Val_Rule_UC_Name ON AD_Val_Rule (Name) WHERE IsActive='Y'
;

