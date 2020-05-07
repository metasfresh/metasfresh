DO $$
BEGIN
	IF NOT EXISTS(select 1 from c_tax where C_Country_ID=107 and rate = '7.7') THEN
-- 2018-01-30T18:28:49.468
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,DuplicateTax,IsActive,IsDefault,IsDocumentLevel,IsSalesTax,IsSummary,IsTaxExempt,IsToEULocation,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,To_Country_ID,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,107,TO_TIMESTAMP('2018-01-30 18:28:49','YYYY-MM-DD HH24:MI:SS'),100,1000005,540006,'N','Y','N','Y','N','N','N','N','N','Normalsatz Betriebsaufwand 7,7% (Schweiz)',7.700000000000,'N','B',107,TO_TIMESTAMP('2018-01-30 18:28:49','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2018-01-01','YYYY-MM-DD'))
;

-- 2018-01-30T18:28:49.469
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.C_Tax_ID=540006 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2018-01-30T18:28:49.473
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy ,T_Credit_Acct,T_Due_Acct,T_Expense_Acct,T_Liability_Acct,T_PayDiscount_Exp_Acct,T_PayDiscount_Rev_Acct,T_Receivables_Acct,T_Revenue_Acct) SELECT 540006, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100,p.T_Credit_Acct,p.T_Due_Acct,p.T_Expense_Acct,p.T_Liability_Acct,p.T_PayDiscount_Exp_Acct,p.T_PayDiscount_Rev_Acct,p.T_Receivables_Acct,p.T_Revenue_Acct FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000 AND NOT EXISTS (SELECT * FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540006)
;

-- 2018-01-30T18:29:08.046
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,DuplicateTax,IsActive,IsDefault,IsDocumentLevel,IsSalesTax,IsSummary,IsTaxExempt,IsToEULocation,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,To_Country_ID,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,107,TO_TIMESTAMP('2018-01-30 18:29:07','YYYY-MM-DD HH24:MI:SS'),100,1000001,540007,'N','Y','Y','Y','N','N','N','N','N','Normalsatz Waren/ DL 7.7% (Schweiz)',7.700000000000,'N','B',107,TO_TIMESTAMP('2018-01-30 18:29:07','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2018-01-01','YYYY-MM-DD'))
;

-- 2018-01-30T18:29:08.047
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.C_Tax_ID=540007 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2018-01-30T18:29:08.050
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy ,T_Credit_Acct,T_Due_Acct,T_Expense_Acct,T_Liability_Acct,T_PayDiscount_Exp_Acct,T_PayDiscount_Rev_Acct,T_Receivables_Acct,T_Revenue_Acct) SELECT 540007, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100,p.T_Credit_Acct,p.T_Due_Acct,p.T_Expense_Acct,p.T_Liability_Acct,p.T_PayDiscount_Exp_Acct,p.T_PayDiscount_Rev_Acct,p.T_Receivables_Acct,p.T_Revenue_Acct FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000 AND NOT EXISTS (SELECT * FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540007)
;

-- 2018-01-30T18:29:15.363
UPDATE C_Tax SET Name='Normalsatz Betriebsaufwand 7.7% (Schweiz)',Updated=TO_TIMESTAMP('2018-01-30 18:29:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540006
;

	END IF;
END;
$$;