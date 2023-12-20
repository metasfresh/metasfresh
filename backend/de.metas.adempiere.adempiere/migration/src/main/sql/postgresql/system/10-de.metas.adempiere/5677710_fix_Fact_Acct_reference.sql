-- Name: Fact_Acct_Transactions_View
-- 2023-02-16T17:18:21.847Z
UPDATE AD_Reference SET Name='Fact_Acct_Transactions_View',Updated=TO_TIMESTAMP('2023-02-16 19:18:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540773
;

-- 2023-02-16T17:18:21.849Z
UPDATE AD_Reference_Trl trl SET Name='Fact_Acct_Transactions_View' WHERE AD_Reference_ID=540773 AND AD_Language='en_US'
;

-- Name: Fact_Acct
-- 2023-02-16T17:18:46.542Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541718,TO_TIMESTAMP('2023-02-16 19:18:46','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','Fact_Acct',TO_TIMESTAMP('2023-02-16 19:18:46','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2023-02-16T17:18:46.543Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541718 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: Fact_Acct
-- Table: Fact_Acct
-- Key: Fact_Acct.Fact_Acct_ID
-- 2023-02-16T17:19:04.008Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,3001,0,541718,270,162,TO_TIMESTAMP('2023-02-16 19:19:03','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2023-02-16 19:19:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Reference: Fact_Acct
-- Table: Fact_Acct
-- Key: Fact_Acct.Fact_Acct_ID
-- 2023-02-16T17:19:46.818Z
UPDATE AD_Ref_Table SET AD_Window_ID=NULL,Updated=TO_TIMESTAMP('2023-02-16 19:19:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541718
;

-- Column: Fact_Acct.Counterpart_Fact_Acct_ID
-- 2023-02-16T17:20:06.019Z
UPDATE AD_Column SET AD_Reference_Value_ID=541718,Updated=TO_TIMESTAMP('2023-02-16 19:20:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559124
;

-- Column: Fact_Acct_Transactions_View.Counterpart_Fact_Acct_ID
-- 2023-02-16T17:20:30.893Z
UPDATE AD_Column SET AD_Reference_Value_ID=540773, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2023-02-16 19:20:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=571111
;

