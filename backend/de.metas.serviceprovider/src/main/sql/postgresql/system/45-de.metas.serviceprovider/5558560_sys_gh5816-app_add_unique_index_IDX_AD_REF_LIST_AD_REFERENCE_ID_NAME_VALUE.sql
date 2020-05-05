-- 2020-05-05T07:10:33.305Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540538,0,104,TO_TIMESTAMP('2020-05-05 10:10:33','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Y','IDX_AD_REF_LIST_AD_REFERENCE_ID_NAME_VALUE','N',TO_TIMESTAMP('2020-05-05 10:10:33','YYYY-MM-DD HH24:MI:SS'),100,'AD_RE')
;

-- 2020-05-05T07:10:33.310Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Index_Table_ID=540538 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2020-05-05T07:10:50.629Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET EntityType='D', WhereClause='AD_Ref_List.isActive=''Y''',Updated=TO_TIMESTAMP('2020-05-05 10:10:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540538
;

-- 2020-05-05T07:11:55.097Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,151,541016,540538,0,TO_TIMESTAMP('2020-05-05 10:11:54','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2020-05-05 10:11:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-05-05T07:12:01.747Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,149,541017,540538,0,TO_TIMESTAMP('2020-05-05 10:12:01','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',20,TO_TIMESTAMP('2020-05-05 10:12:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-05-05T07:12:41.294Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,200,541018,540538,0,TO_TIMESTAMP('2020-05-05 10:12:41','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',30,TO_TIMESTAMP('2020-05-05 10:12:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-05-05T07:12:51.362Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX IDX_AD_REF_LIST_AD_REFERENCE_ID_NAME_VALUE ON AD_Ref_List (AD_Reference_ID,Name,Value) WHERE AD_Ref_List.isActive='Y'
;

