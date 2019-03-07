-- 2019-02-22T09:22:03.416
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540488,0,541169,TO_TIMESTAMP('2019-02-22 09:22:03','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dataentry','Y','Y','DataEntry_Record_UC','N',TO_TIMESTAMP('2019-02-22 09:22:03','YYYY-MM-DD HH24:MI:SS'),100,'IsActive=''Y''')
;

-- 2019-02-22T09:22:03.430
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Index_Table_ID=540488 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2019-02-22T09:22:37.323
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,563969,540935,540488,0,TO_TIMESTAMP('2019-02-22 09:22:37','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dataentry','Y',10,TO_TIMESTAMP('2019-02-22 09:22:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-02-22T09:23:30.861
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,564181,540936,540488,0,TO_TIMESTAMP('2019-02-22 09:23:30','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dataentry','Y',20,TO_TIMESTAMP('2019-02-22 09:23:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-02-22T09:24:02.325
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,564141,540937,540488,0,TO_TIMESTAMP('2019-02-22 09:24:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dataentry','Y',30,TO_TIMESTAMP('2019-02-22 09:24:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-02-22T09:24:09.273
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX DataEntry_Record_UC ON DataEntry_Record (DataEntry_Record_ID,DataEntry_SubGroup_ID,AD_Table_ID) WHERE IsActive='Y'
;

-- 2019-02-22T09:24:31.745
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Column SET AD_Column_ID=564142,Updated=TO_TIMESTAMP('2019-02-22 09:24:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Column_ID=540935
;

-- 2019-02-22T15:07:59.914
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP INDEX IF EXISTS dataentry_record_uc
;

-- 2019-02-22T15:07:59.919
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX DataEntry_Record_UC ON DataEntry_Record (Record_ID,DataEntry_SubGroup_ID,AD_Table_ID) WHERE IsActive='Y'
;

