-- 2018-02-07T11:19:53.171
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540418,0,101,TO_TIMESTAMP('2018-02-07 11:19:51','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Y','AD_Column_IsKey','N',TO_TIMESTAMP('2018-02-07 11:19:51','YYYY-MM-DD HH24:MI:SS'),100,'IsKey=''Y''')
;

-- 2018-02-07T11:19:53.182
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Index_Table_ID=540418 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2018-02-07T11:20:05.377
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,114,540834,540418,0,TO_TIMESTAMP('2018-02-07 11:20:05','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2018-02-07 11:20:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-07T11:20:17.319
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET WhereClause='IsKey=''Y'' and IsActive = ''Y''',Updated=TO_TIMESTAMP('2018-02-07 11:20:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540418
;

-- 2018-02-07T11:20:34.503
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,119,540835,540418,0,TO_TIMESTAMP('2018-02-07 11:20:34','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',20,TO_TIMESTAMP('2018-02-07 11:20:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-07T11:21:54.427
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsKey='N',Updated=TO_TIMESTAMP('2018-02-07 11:21:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=7827
;

-- 2018-02-07T13:18:57.952
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsKey='N', IsUpdateable='N',Updated=TO_TIMESTAMP('2018-02-07 13:18:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=71277
;

-- 2018-02-07T13:18:59.676
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsKey='N', IsUpdateable='N',Updated=TO_TIMESTAMP('2018-02-07 13:18:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=70647
;

-- 2018-02-07T13:19:18.915
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX AD_Column_IsKey ON AD_Column (AD_Table_ID,IsKey) WHERE IsKey='Y' and IsActive = 'Y'
;

