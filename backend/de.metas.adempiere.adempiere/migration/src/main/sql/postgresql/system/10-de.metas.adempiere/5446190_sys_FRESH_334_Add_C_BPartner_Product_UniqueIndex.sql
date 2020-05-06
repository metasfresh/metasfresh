
-- 25.05.2016 16:53
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540378,0,632,TO_TIMESTAMP('2016-05-25 16:53:47','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Y','C_BPartner_Product_Org_Unique','N',TO_TIMESTAMP('2016-05-25 16:53:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 25.05.2016 16:53
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Index_Table_ID=540378 AND NOT EXISTS (SELECT * FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 25.05.2016 16:54
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET WhereClause='IsActive = ''Y'' ',Updated=TO_TIMESTAMP('2016-05-25 16:54:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540378
;

-- 25.05.2016 16:55
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,10163,540752,540378,0,TO_TIMESTAMP('2016-05-25 16:55:06','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2016-05-25 16:55:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 25.05.2016 16:55
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,10171,540753,540378,0,TO_TIMESTAMP('2016-05-25 16:55:20','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',20,TO_TIMESTAMP('2016-05-25 16:55:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 25.05.2016 16:55
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,10165,540754,540378,0,TO_TIMESTAMP('2016-05-25 16:55:29','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',30,TO_TIMESTAMP('2016-05-25 16:55:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 25.05.2016 17:10
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX C_BPartner_Product_Org_Unique ON C_BPartner_Product (C_BPartner_ID,M_Product_ID,AD_Org_ID) WHERE IsActive = 'Y' 
;

