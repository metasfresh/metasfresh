-- 30.06.2016 17:16
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540379,0,540773,TO_TIMESTAMP('2016-06-30 17:16:02','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_DocBaseType_Counter_UniqueCombination','N',TO_TIMESTAMP('2016-06-30 17:16:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 30.06.2016 17:16
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Index_Table_ID=540379 AND NOT EXISTS (SELECT * FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 30.06.2016 17:16
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET IsUnique='Y',Updated=TO_TIMESTAMP('2016-06-30 17:16:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540379
;

-- 30.06.2016 17:16
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,554787,540755,540379,0,TO_TIMESTAMP('2016-06-30 17:16:16','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2016-06-30 17:16:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 30.06.2016 17:16
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,554788,540756,540379,0,TO_TIMESTAMP('2016-06-30 17:16:26','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',20,TO_TIMESTAMP('2016-06-30 17:16:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 30.06.2016 17:16
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,554783,540757,540379,0,TO_TIMESTAMP('2016-06-30 17:16:33','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',30,TO_TIMESTAMP('2016-06-30 17:16:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 30.06.2016 17:16
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Index_Column WHERE AD_Index_Column_ID=540757
;

-- 30.06.2016 17:16
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET WhereClause='IsActive = ''Y''',Updated=TO_TIMESTAMP('2016-06-30 17:16:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540379
;
-- 30.06.2016 17:26
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET WhereClause=NULL,Updated=TO_TIMESTAMP('2016-06-30 17:26:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540379
;

-- 30.06.2016 17:26
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,554783,540758,540379,0,TO_TIMESTAMP('2016-06-30 17:26:44','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',30,TO_TIMESTAMP('2016-06-30 17:26:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 30.06.2016 17:26
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Index_Column WHERE AD_Index_Column_ID=540756
;

-- 30.06.2016 17:27
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET WhereClause='IsActive = ''Y'' ',Updated=TO_TIMESTAMP('2016-06-30 17:27:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540379
;

-- 30.06.2016 17:27
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Index_Column WHERE AD_Index_Column_ID=540758
;
-- 30.06.2016 17:28
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX C_DocBaseType_Counter_UniqueCombination ON C_DocBaseType_Counter (DocBaseType) WHERE IsActive = 'Y' 
;

