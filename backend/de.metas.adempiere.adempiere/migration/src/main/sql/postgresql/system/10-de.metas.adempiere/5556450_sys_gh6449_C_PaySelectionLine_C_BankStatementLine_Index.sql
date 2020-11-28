-- 2020-04-07T08:31:52.354Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (CreatedBy,Processing,Created,AD_Client_ID,IsActive,IsUnique,AD_Table_ID,Updated,UpdatedBy,AD_Index_Table_ID,Name,AD_Org_ID,EntityType) VALUES (100,'N',TO_TIMESTAMP('2020-04-07 11:31:52','YYYY-MM-DD HH24:MI:SS'),0,'Y','N',427,TO_TIMESTAMP('2020-04-07 11:31:52','YYYY-MM-DD HH24:MI:SS'),100,540523,'C_PaySelectionLine_C_BankStatementLine_Index',0,'D')
;

-- 2020-04-07T08:31:52.357Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Index_Table_ID=540523 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2020-04-07T08:32:14.691Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (Created,CreatedBy,Updated,AD_Client_ID,AD_Index_Table_ID,IsActive,AD_Column_ID,SeqNo,UpdatedBy,AD_Index_Column_ID,AD_Org_ID,EntityType) VALUES (TO_TIMESTAMP('2020-04-07 11:32:14','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2020-04-07 11:32:14','YYYY-MM-DD HH24:MI:SS'),0,540523,'Y',551690,10,100,540994,0,'D')
;

-- 2020-04-07T08:32:19.186Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE INDEX C_PaySelectionLine_C_BankStatementLine_Index ON C_PaySelectionLine (C_BankStatementLine_ID)
;
-- 2020-04-07T08:42:06.382Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET WhereClause='C_BankStatementLine_ID is not null',Updated=TO_TIMESTAMP('2020-04-07 11:42:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540523
;

-- 2020-04-07T08:42:07.557Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP INDEX IF EXISTS c_payselectionline_c_bankstatementline_index
;

-- 2020-04-07T08:42:07.558Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE INDEX C_PaySelectionLine_C_BankStatementLine_Index ON C_PaySelectionLine (C_BankStatementLine_ID) WHERE C_BankStatementLine_ID is not null
;

