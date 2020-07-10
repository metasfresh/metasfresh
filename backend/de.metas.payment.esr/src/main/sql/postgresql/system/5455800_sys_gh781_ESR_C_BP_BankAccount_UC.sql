
-- 31.01.2017 17:03
-- URL zum Konzept
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540394,0,298,TO_TIMESTAMP('2017-01-31 17:03:25','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.esr','Y','N','C_BP_BankAccount_IsEsrAccount_UC','N',TO_TIMESTAMP('2017-01-31 17:03:25','YYYY-MM-DD HH24:MI:SS'),100,'IsActive=''Y'' AND IsEsrAccount=''Y''')
;

-- 31.01.2017 17:03
-- URL zum Konzept
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Index_Table_ID=540394 AND NOT EXISTS (SELECT * FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 31.01.2017 17:03
-- URL zum Konzept
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,3102,540787,540394,0,TO_TIMESTAMP('2017-01-31 17:03:40','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.esr','Y',10,TO_TIMESTAMP('2017-01-31 17:03:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 31.01.2017 17:04
-- URL zum Konzept
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,548019,540788,540394,0,TO_TIMESTAMP('2017-01-31 17:04:05','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.esr','Y',20,TO_TIMESTAMP('2017-01-31 17:04:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 31.01.2017 17:05
-- URL zum Konzept
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,ColumnSQL,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,3105,540789,540394,0,'COALESCE(AccountNo, ''0000000'')',TO_TIMESTAMP('2017-01-31 17:05:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.esr','Y',30,TO_TIMESTAMP('2017-01-31 17:05:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 31.01.2017 17:06
-- URL zum Konzept
UPDATE AD_Index_Table SET ErrorMsg='Bei ESR-Konten müssen Teilnehmernummer und Kontonummer pro Geschäftspartner eindeutig sein.', IsUnique='Y',Updated=TO_TIMESTAMP('2017-01-31 17:06:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540394
;

-- 31.01.2017 17:06
-- URL zum Konzept
UPDATE AD_Index_Table_Trl SET IsTranslated='N' WHERE AD_Index_Table_ID=540394
;

-- 31.01.2017 17:09
-- URL zum Konzept
CREATE UNIQUE INDEX IF NOT EXISTS C_BP_BankAccount_IsEsrAccount_UC ON C_BP_BankAccount (C_BPartner_ID,ESR_RenderedAccountNo,COALESCE(AccountNo, '0000000')) WHERE IsActive='Y' AND IsEsrAccount='Y'
;

