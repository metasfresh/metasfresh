-- 2019-12-09T16:44:32.786Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,Description,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540516,0,541445,TO_TIMESTAMP('2019-12-09 18:44:32','YYYY-MM-DD HH24:MI:SS'),100,'Unique index on (C_BPartner_SalesRep_ID, C_BPartner_Customer_ID).','de.metas.contracts','Y','Y','C_Customer_Trade_Margin_SalesRepId_CustomerId','N',TO_TIMESTAMP('2019-12-09 18:44:32','YYYY-MM-DD HH24:MI:SS'),100,'isActive=''Y'' and COALESCE(C_BPartner_Customer_ID, 0) != 0;')
;

-- 2019-12-09T16:44:32.789Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Index_Table_ID=540516 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2019-12-10T09:16:28.458Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2019-12-10 11:16:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540516
;

-- 2019-12-10T11:44:53.222Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,569731,540985,540516,0,TO_TIMESTAMP('2019-12-10 13:44:53','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y',10,TO_TIMESTAMP('2019-12-10 13:44:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-11T13:03:23.932Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,569715,540986,540516,0,TO_TIMESTAMP('2019-12-11 15:03:23','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y',20,TO_TIMESTAMP('2019-12-11 15:03:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-11T13:04:40.465Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX C_Customer_Trade_Margin_SalesRepId_CustomerId ON C_Customer_Trade_Margin (C_BPartner_Customer_ID,C_BPartner_SalesRep_ID) WHERE isActive='Y' and COALESCE(C_BPartner_Customer_ID, 0) != 0;
;

-- 2019-12-11T14:11:59.326Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET ErrorMsg='You already have one active record for this Sales Partner/Business Partner.',Updated=TO_TIMESTAMP('2019-12-11 16:11:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540516
;
