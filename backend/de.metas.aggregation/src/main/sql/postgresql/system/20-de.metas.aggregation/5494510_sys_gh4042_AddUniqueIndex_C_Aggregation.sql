-- 2018-05-25T13:15:33.036
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,BeforeChangeCodeType,Created,CreatedBy,EntityType,ErrorMsg,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540440,0,540649,'SQLS',TO_TIMESTAMP('2018-05-25 13:15:32','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.aggregation','The name shall be unique','Y','Y','C_Aggregation_UniqueName','N',TO_TIMESTAMP('2018-05-25 13:15:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-25T13:15:33.040
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Index_Table_ID=540440 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2018-05-25T13:15:44.091
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,551849,540865,540440,0,TO_TIMESTAMP('2018-05-25 13:15:43','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.aggregation','Y',10,TO_TIMESTAMP('2018-05-25 13:15:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-25T13:15:46.001
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX C_Aggregation_UniqueName ON C_Aggregation (Name)
;

-- 2018-05-25T13:19:30.532
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET WhereClause='IsActive=''Y''',Updated=TO_TIMESTAMP('2018-05-25 13:19:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540440
;

-- 2018-05-25T13:19:35.934
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP INDEX IF EXISTS c_aggregation_uniquename
;

-- 2018-05-25T13:19:35.935
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX C_Aggregation_UniqueName ON C_Aggregation (Name) WHERE IsActive='Y'
;

