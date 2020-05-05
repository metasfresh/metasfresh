-- 2018-05-09T17:25:09
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540431,0,540954,TO_TIMESTAMP('2018-05-09 17:25:08','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.product','Y','Y','M_Product_LotNumber_Lock_UniqeProductAndLot','N',TO_TIMESTAMP('2018-05-09 17:25:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-09T17:25:09.017
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Index_Table_ID=540431 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2018-05-09T17:25:20.852
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET WhereClause='IsActive = ''Y''',Updated=TO_TIMESTAMP('2018-05-09 17:25:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540431
;

-- 2018-05-09T17:25:37.773
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,559564,540853,540431,0,TO_TIMESTAMP('2018-05-09 17:25:37','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.product','Y',10,TO_TIMESTAMP('2018-05-09 17:25:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-09T17:25:51.206
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,559563,540854,540431,0,TO_TIMESTAMP('2018-05-09 17:25:51','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.product','Y',20,TO_TIMESTAMP('2018-05-09 17:25:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-09T17:26:03.392
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX M_Product_LotNumber_Lock_UniqeProductAndLot ON M_Product_LotNumber_Lock (Lot,M_Product_ID) WHERE IsActive = 'Y'
;

