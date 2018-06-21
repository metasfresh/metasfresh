-- 2018-06-11T15:26:39.332
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540441,0,540930,TO_TIMESTAMP('2018-06-11 15:26:39','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.purchasecandidate','Y','Y','UC_C_PurchaseCandidate_Alloc_C_OrderLinePO_ID','N',TO_TIMESTAMP('2018-06-11 15:26:39','YYYY-MM-DD HH24:MI:SS'),100,'IsActive=''Y''')
;

-- 2018-06-11T15:26:39.336
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Index_Table_ID=540441 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2018-06-11T15:26:59.387
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,559005,540866,540441,0,TO_TIMESTAMP('2018-06-11 15:26:59','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.purchasecandidate','Y',10,TO_TIMESTAMP('2018-06-11 15:26:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-11T15:28:56.677
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET Help='Each purchase order line can''t have mor than one purchase candidate. Still wee want to keep this table C_PurchaseCandidate_Alloc, 
because it also holds a reference to the the "remote" pruchase order line (from the vendor gateway''s side). And we don''t want to add all those columns to C_OrderLine..',Updated=TO_TIMESTAMP('2018-06-11 15:28:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540441
;

-- 2018-06-11T15:29:00.588
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX UC_C_PurchaseCandidate_Alloc_C_OrderLinePO_ID ON C_PurchaseCandidate_Alloc (C_OrderLinePO_ID) WHERE IsActive='Y'
;

