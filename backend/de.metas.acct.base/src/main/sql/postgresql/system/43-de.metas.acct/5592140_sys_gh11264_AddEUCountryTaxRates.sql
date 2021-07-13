-- 2021-06-10T13:11:55.556Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_User SET AD_Language='en_US',Updated=TO_TIMESTAMP('2021-06-10 16:11:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_User_ID=100
;

-- 2021-06-10T13:12:24.964Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,Updated,UpdatedBy,ValidFrom) VALUES (1000000,1000000,101,TO_TIMESTAMP('2021-06-10 16:12:24','YYYY-MM-DD HH24:MI:SS'),100,1000010,540010,'Y','N','Y','N','N','N','N','N','N','Normale MWSt 5% (CY)',0,'N','B',TO_TIMESTAMP('2021-06-10 16:12:24','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-10T13:12:24.966Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540010 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-10T13:12:24.990Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540010, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540010)
;

-- 2021-06-10T13:12:34.928Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-10 16:12:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540010
;

-- 2021-06-10T13:12:42.743Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=165, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-10 16:12:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540010
;

-- 2021-06-10T13:13:05.700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2021-06-10 16:13:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540010
;

-- 2021-06-10T13:15:11.834Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,Updated,UpdatedBy,ValidFrom) VALUES (1000000,1000000,101,TO_TIMESTAMP('2021-06-10 16:15:11','YYYY-MM-DD HH24:MI:SS'),100,1000010,540011,'Y','N','Y','N','N','N','N','N','N','Reduzierte MWSt 5% (CY)',0,'N','B',TO_TIMESTAMP('2021-06-10 16:15:11','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-10T13:15:11.837Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540011 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-10T13:15:11.839Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540011, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540011)
;

-- 2021-06-10T13:15:15.628Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-10 16:15:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540011
;

-- 2021-06-10T13:15:19.317Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=165, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-10 16:15:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540011
;

-- 2021-06-10T13:15:27.161Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET Rate=5,Updated=TO_TIMESTAMP('2021-06-10 16:15:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540011
;

-- 2021-06-10T13:15:29.955Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2021-06-10 16:15:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540011
;

-- 2021-06-10T13:16:08.927Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET Name='Normale MWSt 19% (CY)',Updated=TO_TIMESTAMP('2021-06-10 16:16:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540010
;

-- 2021-06-10T13:16:08.930Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl trl SET Description=NULL, Name='Normale MWSt 19% (CY)', TaxIndicator=NULL  WHERE C_Tax_ID=540010 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-10T13:18:01.051Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_TaxCategory_Trl SET Name='Normale MWSt',Updated=TO_TIMESTAMP('2021-06-10 16:18:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_TaxCategory_ID=1000009
;

-- 2021-06-10T13:20:12.090Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_TaxCategory_ID=1000009,Updated=TO_TIMESTAMP('2021-06-10 16:20:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540010
;

-- 2021-06-10T13:20:41.979Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET Rate=19,Updated=TO_TIMESTAMP('2021-06-10 16:20:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540010
;

-- 2021-06-10T13:21:40.912Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,Updated,UpdatedBy,ValidFrom) VALUES (1000000,1000000,101,TO_TIMESTAMP('2021-06-10 16:21:40','YYYY-MM-DD HH24:MI:SS'),100,1000009,540012,'Y','N','Y','N','N','N','N','N','N','Normale MWSt 27% (HU)',27,'N','B',TO_TIMESTAMP('2021-06-10 16:21:40','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-10T13:21:40.914Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540012 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-10T13:21:40.919Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540012, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540012)
;

-- 2021-06-10T13:21:43.320Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-10 16:21:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540012
;

-- 2021-06-10T13:21:48.026Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=206, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-10 16:21:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540012
;

-- 2021-06-10T13:22:36.110Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,Updated,UpdatedBy,ValidFrom) VALUES (1000000,1000000,101,TO_TIMESTAMP('2021-06-10 16:22:36','YYYY-MM-DD HH24:MI:SS'),100,1000010,540013,'Y','N','Y','N','N','N','N','N','N','Reduzierte MWSt 5% (HU)',5,'N','B',TO_TIMESTAMP('2021-06-10 16:22:36','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-10T13:22:36.113Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540013 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-10T13:22:36.117Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540013, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540013)
;

-- 2021-06-10T13:22:38.043Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-10 16:22:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540013
;

-- 2021-06-10T13:22:45.204Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=206, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-10 16:22:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540013
;

-- 2021-06-10T13:22:49.656Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2021-06-10 16:22:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540013
;

-- 2021-06-10T13:23:04.225Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2021-06-10 16:23:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540012
;

-- 2021-06-10T13:23:51.552Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,Updated,UpdatedBy,ValidFrom) VALUES (1000000,1000000,101,TO_TIMESTAMP('2021-06-10 16:23:51','YYYY-MM-DD HH24:MI:SS'),100,1000009,540014,'Y','N','Y','N','N','N','N','N','N','Normale MWSt 21% (CZ)',0,'N','B',TO_TIMESTAMP('2021-06-10 16:23:51','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-10T13:23:51.554Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540014 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-10T13:23:51.556Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540014, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540014)
;

-- 2021-06-10T13:23:53.451Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-10 16:23:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540014
;

-- 2021-06-10T13:23:57.934Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=166, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-10 16:23:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540014
;

-- 2021-06-10T13:24:05.065Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2021-06-10 16:24:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540014
;

-- 2021-06-10T13:24:52.071Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,Updated,UpdatedBy,ValidFrom) VALUES (1000000,1000000,101,TO_TIMESTAMP('2021-06-10 16:24:52','YYYY-MM-DD HH24:MI:SS'),100,1000010,540015,'Y','N','Y','N','N','N','N','N','N','Reduzierte MWSt 10% (CZ)',0,'N','B',TO_TIMESTAMP('2021-06-10 16:24:52','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-10T13:24:52.073Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540015 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-10T13:24:52.076Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540015, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540015)
;

-- 2021-06-10T13:24:54.078Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-10 16:24:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540015
;

-- 2021-06-10T13:24:56.942Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=166, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-10 16:24:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540015
;

-- 2021-06-10T13:25:05.389Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2021-06-10 16:25:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540015
;

-- 2021-06-10T13:25:12.907Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET Rate=10,Updated=TO_TIMESTAMP('2021-06-10 16:25:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540015
;

-- 2021-06-10T13:25:24.630Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET Rate=21,Updated=TO_TIMESTAMP('2021-06-10 16:25:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540014
;

-- 2021-06-10T13:27:04.738Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,Updated,UpdatedBy,ValidFrom) VALUES (1000000,1000000,101,TO_TIMESTAMP('2021-06-10 16:27:04','YYYY-MM-DD HH24:MI:SS'),100,1000009,540016,'Y','N','Y','N','N','N','N','N','N','Normale MWSt 21% (ES)',21,'N','B',TO_TIMESTAMP('2021-06-10 16:27:04','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-10T13:27:04.740Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540016 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-10T13:27:04.742Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540016, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540016)
;

-- 2021-06-10T13:27:06.682Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-10 16:27:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540016
;

-- 2021-06-10T13:27:22.691Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=106, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-10 16:27:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540016
;

-- 2021-06-10T13:28:25.557Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,Updated,UpdatedBy,ValidFrom) VALUES (1000000,1000000,101,TO_TIMESTAMP('2021-06-10 16:28:25','YYYY-MM-DD HH24:MI:SS'),100,1000009,540017,'Y','N','Y','N','N','N','N','N','N','Normale MWSt 22% (SI)',22,'N','B',TO_TIMESTAMP('2021-06-10 16:28:25','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-10T13:28:25.559Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540017 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-10T13:28:25.561Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540017, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540017)
;

-- 2021-06-10T13:28:27.246Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-10 16:28:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540017
;

-- 2021-06-10T13:28:32.055Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=302, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-10 16:28:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540017
;

-- 2021-06-10T13:29:00.182Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2021-06-10 16:29:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540017
;

-- 2021-06-10T13:29:32.255Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,Updated,UpdatedBy,ValidFrom) VALUES (1000000,1000000,101,TO_TIMESTAMP('2021-06-10 16:29:32','YYYY-MM-DD HH24:MI:SS'),100,1000009,540018,'Y','N','Y','N','N','N','N','N','N','Normale MWSt 20% (SK)',20,'N','B',TO_TIMESTAMP('2021-06-10 16:29:32','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-10T13:29:32.257Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540018 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-10T13:29:32.259Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540018, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540018)
;

-- 2021-06-10T13:29:34.056Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-10 16:29:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540018
;

-- 2021-06-10T13:29:37.743Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=301, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-10 16:29:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540018
;

-- 2021-06-10T13:29:42.887Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2021-06-10 16:29:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540018
;

-- 2021-06-10T13:30:37.414Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,TO_TIMESTAMP('2021-06-10 16:30:37','YYYY-MM-DD HH24:MI:SS'),100,1000009,540019,'Y','N','Y','N','N','N','N','N','N','Normale MWSt 25% (SE)',25,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-10 16:30:37','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-10T13:30:37.416Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540019 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-10T13:30:37.418Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540019, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540019)
;

-- 2021-06-10T13:30:42.723Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=313, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-10 16:30:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540019
;

-- 2021-06-10T13:32:48.627Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,Updated,UpdatedBy,ValidFrom) VALUES (1000000,1000000,101,TO_TIMESTAMP('2021-06-10 16:32:48','YYYY-MM-DD HH24:MI:SS'),100,1000009,540020,'Y','N','Y','N','N','N','N','N','N','Normale MWSt 19% (RO)',0,'N','B',TO_TIMESTAMP('2021-06-10 16:32:48','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-10T13:32:48.630Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540020 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-10T13:32:48.632Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540020, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540020)
;

-- 2021-06-10T13:32:50.662Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-10 16:32:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540020
;

-- 2021-06-10T13:32:57.289Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=285, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-10 16:32:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540020
;

-- 2021-06-10T13:33:03.386Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET Rate=19,Updated=TO_TIMESTAMP('2021-06-10 16:33:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540020
;

-- 2021-06-10T13:33:09.521Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2021-06-10 16:33:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540020
;

-- 2021-06-10T13:33:55.923Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-10 16:33:55','YYYY-MM-DD HH24:MI:SS'),100,1000009,540021,'Y','N','Y','N','N','N','N','N','N','Normale MWSt 23% (PT)',23,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-10 16:33:55','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-10T13:33:55.926Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540021 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-10T13:33:55.928Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540021, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540021)
;

-- 2021-06-10T13:33:58.351Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-10 16:33:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540021
;

-- 2021-06-10T13:34:05.105Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=281, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-10 16:34:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540021
;

-- 2021-06-10T13:34:43.506Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-10 16:34:43','YYYY-MM-DD HH24:MI:SS'),100,1000009,540022,'Y','N','Y','N','N','N','N','N','N','Normale MWSt 23% (PL)',23,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-10 16:34:43','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-10T13:34:43.509Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540022 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-10T13:34:43.511Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540022, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540022)
;

-- 2021-06-10T13:34:45.182Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-10 16:34:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540022
;

-- 2021-06-10T13:34:48.481Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=280, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-10 16:34:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540022
;

-- 2021-06-10T13:35:52.363Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-10 16:35:52','YYYY-MM-DD HH24:MI:SS'),100,1000009,540023,'Y','N','Y','N','N','N','N','N','N','Normale MWSt 20% (AT)',20,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-10 16:35:52','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-10T13:35:52.365Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540023 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-10T13:35:52.367Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540023, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540023)
;

-- 2021-06-10T13:35:54.118Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-10 16:35:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540023
;

-- 2021-06-10T13:35:59.779Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=108, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-10 16:35:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540023
;

-- 2021-06-10T13:36:58.111Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-10 16:36:58','YYYY-MM-DD HH24:MI:SS'),100,1000009,540024,'Y','N','Y','N','N','N','N','N','N','Normale MWSt 21% (NL)',21,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-10 16:36:58','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-10T13:36:58.114Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540024 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-10T13:36:58.116Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540024, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540024)
;

-- 2021-06-10T13:36:59.761Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-10 16:36:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540024
;

-- 2021-06-10T13:37:07.285Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=105, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-10 16:37:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540024
;

-- Malta NORMAL
-- 2021-06-10T13:39:48.634Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-10 16:39:48','YYYY-MM-DD HH24:MI:SS'),100,1000009,540025,'Y','N','Y','N','N','N','N','N','N','Normale MWSt 18% (MT)',18,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-10 16:39:48','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-10T13:39:48.636Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540025 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-10T13:39:48.639Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540025, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540025)
;

-- 2021-06-10T13:39:50.211Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-10 16:39:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540025
;

-- 2021-06-10T13:39:55.268Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=241, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-10 16:39:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540025
;

-- Luxemburg Normale

-- 2021-06-10T13:41:29.533Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,Updated,UpdatedBy,ValidFrom) VALUES (1000000,1000000,101,TO_TIMESTAMP('2021-06-10 16:41:29','YYYY-MM-DD HH24:MI:SS'),100,1000009,540026,'Y','N','Y','N','N','N','N','N','N','Normale MWSt 17% (LU)',17,'N','B',TO_TIMESTAMP('2021-06-10 16:41:29','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-10T13:41:29.535Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540026 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-10T13:41:29.538Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540026, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540026)
;

-- 2021-06-10T13:41:31.723Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-10 16:41:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540026
;

-- 2021-06-10T13:41:36.441Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=233, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-10 16:41:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540026
;

-- 2021-06-10T13:41:42.465Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2021-06-10 16:41:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540026
;

--
-- 2021-06-10T13:43:22.950Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-10 16:43:22','YYYY-MM-DD HH24:MI:SS'),100,1000004,540027,'Y','N','Y','N','N','N','N','N','N','Normale MWSt 21% (LT)',21,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-10 16:43:22','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-10T13:43:22.952Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540027 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-10T13:43:22.954Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540027, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540027)
;

-- 2021-06-10T13:43:24.654Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_TaxCategory_ID=1000009,Updated=TO_TIMESTAMP('2021-06-10 16:43:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540027
;

-- 2021-06-10T13:43:26.754Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-10 16:43:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540027
;

-- 2021-06-10T13:43:32.514Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=232, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-10 16:43:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540027
;

-- 2021-06-10T13:44:12.562Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-10 16:44:12','YYYY-MM-DD HH24:MI:SS'),100,1000009,540028,'Y','N','Y','N','N','N','N','N','N','Normale MWSt 21% (LV)',21,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-10 16:44:12','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-10T13:44:12.564Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540028 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-10T13:44:12.566Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540028, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540028)
;

-- 2021-06-10T13:44:14.784Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-10 16:44:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540028
;

-- 2021-06-10T13:44:19.639Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=226, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-10 16:44:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540028
;

-- 2021-06-10T13:45:21.457Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,Updated,UpdatedBy,ValidFrom) VALUES (1000000,1000000,101,TO_TIMESTAMP('2021-06-10 16:45:21','YYYY-MM-DD HH24:MI:SS'),100,1000009,540029,'Y','N','Y','N','N','N','N','N','N','Normale MWSt 25% (HR)',25,'N','B',TO_TIMESTAMP('2021-06-10 16:45:21','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-10T13:45:21.459Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540029 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-10T13:45:21.460Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540029, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540029)
;

-- 2021-06-10T13:45:26.444Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2021-06-10 16:45:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540029
;

-- 2021-06-10T13:45:27.838Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-10 16:45:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540029
;

-- 2021-06-10T13:45:33.185Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=163, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-10 16:45:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540029
;

-- 2021-06-10T13:46:08.229Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-10 16:46:08','YYYY-MM-DD HH24:MI:SS'),100,1000009,540030,'Y','N','Y','N','N','N','N','N','N','Normale MWSt 22% (IT)',22,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-10 16:46:08','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-10T13:46:08.231Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540030 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-10T13:46:08.233Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540030, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540030)
;

-- 2021-06-10T13:46:09.480Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-10 16:46:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540030
;

-- 2021-06-10T13:46:13.607Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=214, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-10 16:46:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540030
;

-- 2021-06-10T13:46:53.852Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-10 16:46:53','YYYY-MM-DD HH24:MI:SS'),100,1000009,540031,'Y','N','Y','N','N','N','N','N','N','Normale MWSt 23% (IE)',23,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-10 16:46:53','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-10T13:46:53.855Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540031 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-10T13:46:53.858Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540031, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540031)
;

-- 2021-06-10T13:46:55.109Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-10 16:46:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540031
;

-- 2021-06-10T13:47:04.259Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=212, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-10 16:47:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540031
;

-- 2021-06-10T13:48:00.948Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-10 16:48:00','YYYY-MM-DD HH24:MI:SS'),100,1000009,540032,'Y','N','Y','N','N','N','N','N','N','Normale MWSt 20% (GB)',20,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-10 16:48:00','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-10T13:48:00.951Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540032 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-10T13:48:00.953Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540032, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540032)
;

-- 2021-06-10T13:48:02.451Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-10 16:48:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540032
;

-- 2021-06-10T13:50:14.055Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET Name='Normale MWSt 24% (GG)',Updated=TO_TIMESTAMP('2021-06-10 16:50:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540032
;

-- 2021-06-10T13:50:14.057Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl trl SET Description=NULL, Name='Normale MWSt 24% (GG)', TaxIndicator=NULL  WHERE C_Tax_ID=540032 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-10T13:50:20.128Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET Rate=24,Updated=TO_TIMESTAMP('2021-06-10 16:50:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540032
;

-- 2021-06-10T13:50:24.346Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=192, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-10 16:50:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540032
;

-- 2021-06-10T13:50:56.315Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET Name='Normale MWSt 24% (GR)',Updated=TO_TIMESTAMP('2021-06-10 16:50:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540032
;

-- 2021-06-10T13:50:56.317Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl trl SET Description=NULL, Name='Normale MWSt 24% (GR)', TaxIndicator=NULL  WHERE C_Tax_ID=540032 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-10T13:51:39.170Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-10 16:51:39','YYYY-MM-DD HH24:MI:SS'),100,1000009,540033,'Y','N','Y','N','N','N','N','N','N','Normale MWSt 20% (MC)',20,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-10 16:51:39','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-10T13:51:39.172Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540033 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-10T13:51:39.173Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540033, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540033)
;

-- 2021-06-10T13:51:41.327Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-10 16:51:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540033
;

-- 2021-06-10T13:51:46.639Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=250, TypeOfDestCountry='OUTSIDE_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-10 16:51:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540033
;

-- 2021-06-10T13:51:52.274Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-10 16:51:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540033
;

-- 2021-06-10T13:52:48.506Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-10 16:52:48','YYYY-MM-DD HH24:MI:SS'),100,1000009,540034,'Y','N','Y','N','N','N','N','N','N','Normale MWSt 24% (FI)',24,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-10 16:52:48','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-10T13:52:48.508Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540034 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-10T13:52:48.510Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540034, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540034)
;

-- 2021-06-10T13:52:50.839Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-10 16:52:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540034
;

-- 2021-06-10T13:52:54.628Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=181, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-10 16:52:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540034
;

-- 2021-06-10T13:53:54.367Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-10 16:53:54','YYYY-MM-DD HH24:MI:SS'),100,1000009,540035,'Y','N','Y','N','N','N','N','N','N','Normale MWSt 20% (EE)',20,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-10 16:53:54','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-10T13:53:54.370Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540035 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-10T13:53:54.372Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540035, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540035)
;

-- 2021-06-10T13:53:55.604Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-10 16:53:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540035
;

-- 2021-06-10T13:54:00.740Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=176, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-10 16:54:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540035
;


-- 2021-06-10T13:55:52.205Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-10 16:55:52','YYYY-MM-DD HH24:MI:SS'),100,1000009,540036,'Y','N','Y','N','N','N','N','N','N','Normale MWSt 25% (DK)',25,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-10 16:55:52','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-10T13:55:52.208Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540036 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-10T13:55:52.210Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540036, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540036)
;

-- 2021-06-10T13:55:54.487Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-10 16:55:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540036
;

-- 2021-06-10T13:56:00.543Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=167, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-10 16:56:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540036
;

-- 2021-06-10T13:57:05.289Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-10 16:57:05','YYYY-MM-DD HH24:MI:SS'),100,1000009,540037,'Y','N','Y','N','N','N','N','N','N','Normale MWSt 20% (BG)',20,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-10 16:57:05','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-10T13:57:05.291Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540037 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-10T13:57:05.293Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540037, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540037)
;

-- 2021-06-10T13:57:06.549Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-10 16:57:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540037
;

-- 2021-06-10T13:57:10.127Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=142, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-10 16:57:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540037
;

-- 2021-06-10T13:57:51.556Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-10 16:57:51','YYYY-MM-DD HH24:MI:SS'),100,1000009,540038,'Y','N','Y','N','N','N','N','N','N','Normale MWSt 21% (BE)',21,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-10 16:57:51','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-10T13:57:51.558Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540038 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-10T13:57:51.560Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540038, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540038)
;

-- 2021-06-10T13:57:52.910Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-10 16:57:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540038
;

-- 2021-06-10T13:57:57.766Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=103, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-10 16:57:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540038
;

-- 2021-06-10T14:03:36.224Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-10 17:03:36','YYYY-MM-DD HH24:MI:SS'),100,1000009,540039,'Y','N','Y','N','N','N','N','N','N','Normale MWSt 20% (FR)',20,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-10 17:03:36','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-10T14:03:36.226Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540039 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-10T14:03:36.229Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540039, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540039)
;

-- 2021-06-10T14:03:37.567Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-10 17:03:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540039
;

-- 2021-06-10T14:03:40.952Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=102, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-10 17:03:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540039
;

-- 2021-06-10T14:16:39.550Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET Name='Normale MWSt 19% (DE)',Updated=TO_TIMESTAMP('2021-06-10 17:16:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=1000022
;

-- 2021-06-10T14:16:39.552Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl trl SET Description=NULL, Name='Normale MWSt 19% (DE)', TaxIndicator=NULL  WHERE C_Tax_ID=1000022 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-10T14:20:27.748Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-10 17:20:27','YYYY-MM-DD HH24:MI:SS'),100,1000009,540041,'Y','N','Y','N','N','N','N','N','N','Normale MWSt 20% (GB)',20,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-10 17:20:27','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-10T14:20:27.750Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540041 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-10T14:20:27.753Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540041, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540041)
;

-- 2021-06-10T14:20:29.594Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-10 17:20:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540041
;

-- 2021-06-10T14:26:49.613Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-10 17:26:49','YYYY-MM-DD HH24:MI:SS'),100,1000010,540042,'Y','N','Y','N','N','N','N','N','N','Reduzierte MWSt 4% (ES)',4,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-10 17:26:49','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-10T14:26:49.616Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540042 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-10T14:26:49.619Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540042, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540042)
;

-- 2021-06-10T14:26:52.018Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-10 17:26:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540042
;

-- 2021-06-10T14:26:55.652Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=106, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-10 17:26:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540042
;

-- 2021-06-10T14:27:58.989Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-10 17:27:58','YYYY-MM-DD HH24:MI:SS'),100,1000010,540043,'Y','N','Y','N','N','N','N','N','N','Reduzierte MWSt 9.5% (SI)',9.5,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-10 17:27:58','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-10T14:27:58.991Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540043 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-10T14:27:58.994Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540043, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540043)
;

-- 2021-06-10T14:28:00.795Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-10 17:28:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540043
;

-- 2021-06-10T14:28:07.631Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=302,Updated=TO_TIMESTAMP('2021-06-10 17:28:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540043
;

-- 2021-06-10T14:28:10.204Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-10 17:28:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540043
;

-- 2021-06-10T14:28:13.902Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=302, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-10 17:28:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540043
;

-- 2021-06-10T14:28:53.983Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-10 17:28:53','YYYY-MM-DD HH24:MI:SS'),100,1000010,540044,'Y','N','Y','N','N','N','N','N','N','Reduzierte MWSt 10% (SK)',10,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-10 17:28:53','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-10T14:28:53.986Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540044 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-10T14:28:53.989Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540044, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540044)
;

-- 2021-06-10T14:28:55.586Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-10 17:28:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540044
;

-- 2021-06-10T14:29:01.324Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=301, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-10 17:29:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540044
;

-- 2021-06-10T14:29:46.939Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-10 17:29:46','YYYY-MM-DD HH24:MI:SS'),100,1000010,540045,'Y','N','Y','N','N','N','N','N','N','Reduzierte MWSt 6% (SE)',6,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-10 17:29:46','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-10T14:29:46.943Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540045 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-10T14:29:46.947Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540045, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540045)
;

-- 2021-06-10T14:29:48.249Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-10 17:29:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540045
;

-- 2021-06-10T14:29:51.715Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=313, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-10 17:29:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540045
;

-- 2021-06-10T14:31:07.294Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-10 17:31:07','YYYY-MM-DD HH24:MI:SS'),100,1000010,540046,'Y','N','Y','N','N','N','N','N','N','Reduzierte MWSt 5% (RO)',5,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-10 17:31:07','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-10T14:31:07.297Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540046 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-10T14:31:07.298Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540046, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540046)
;

-- 2021-06-10T14:31:09.479Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-10 17:31:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540046
;

-- 2021-06-10T14:31:15.305Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=285, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-10 17:31:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540046
;

-- 2021-06-10T14:32:21.661Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-10 17:32:21','YYYY-MM-DD HH24:MI:SS'),100,1000010,540047,'Y','N','Y','N','N','N','N','N','N','Reduzierte MWSt 6% (PT)',6,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-10 17:32:21','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-10T14:32:21.663Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540047 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-10T14:32:21.665Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540047, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540047)
;

-- 2021-06-10T14:32:24.310Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-10 17:32:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540047
;

-- 2021-06-10T14:32:28.717Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=281, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-10 17:32:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540047
;

-- 2021-06-10T14:33:09.977Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-10 17:33:09','YYYY-MM-DD HH24:MI:SS'),100,1000010,540048,'Y','N','Y','N','N','N','N','N','N','Reduzierte MWSt 5% (PL)',5,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-10 17:33:09','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-10T14:33:09.979Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540048 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-10T14:33:09.981Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540048, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540048)
;

-- 2021-06-10T14:33:11.972Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-10 17:33:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540048
;

-- 2021-06-10T14:33:15.859Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=280, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-10 17:33:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540048
;

-- 2021-06-10T14:33:57.621Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-10 17:33:57','YYYY-MM-DD HH24:MI:SS'),100,1000010,540049,'Y','N','Y','N','N','N','N','N','N','Reduzierte MWSt 5% (AT)',5,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-10 17:33:57','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-10T14:33:57.623Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540049 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-10T14:33:57.626Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540049, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540049)
;

-- 2021-06-10T14:33:58.825Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-10 17:33:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540049
;

-- 2021-06-10T14:34:03.748Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=108, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-10 17:34:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540049
;

-- 2021-06-10T14:34:56.816Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-10 17:34:56','YYYY-MM-DD HH24:MI:SS'),100,1000010,540050,'Y','N','Y','N','N','N','N','N','N','Reduzierte MWSt 9% (NL)',9,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-10 17:34:56','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-10T14:34:56.820Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540050 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-10T14:34:56.823Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540050, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540050)
;

-- 2021-06-10T14:34:58.667Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-10 17:34:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540050
;

-- 2021-06-10T14:35:08.965Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=105, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-10 17:35:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540050
;

-- 2021-06-10T14:36:51.166Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-10 17:36:51','YYYY-MM-DD HH24:MI:SS'),100,1000010,540051,'Y','N','Y','N','N','N','N','N','N','Reduzierte MWSt 5% (MT)',5,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-10 17:36:51','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-10T14:36:51.175Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540051 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-10T14:36:51.178Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540051, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540051)
;

-- 2021-06-10T14:36:52.936Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-10 17:36:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540051
;

-- 2021-06-10T14:36:59.364Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=241, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-10 17:36:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540051
;

-- 2021-06-10T14:37:57.163Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-10 17:37:57','YYYY-MM-DD HH24:MI:SS'),100,1000010,540052,'Y','N','Y','N','N','N','N','N','N','Reduzierte MWSt 3% (LU)',3,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-10 17:37:57','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-10T14:37:57.165Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540052 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-10T14:37:57.167Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540052, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540052)
;

-- 2021-06-10T14:37:59.719Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-10 17:37:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540052
;

-- 2021-06-10T14:38:05.930Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=233, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-10 17:38:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540052
;

-- 2021-06-10T14:38:49.897Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-10 17:38:49','YYYY-MM-DD HH24:MI:SS'),100,1000010,540053,'Y','N','Y','N','N','N','N','N','N','Reduzierte MWSt 21% (LT)',21,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-10 17:38:49','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-10T14:38:49.900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540053 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-10T14:38:49.902Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540053, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540053)
;

-- 2021-06-10T14:38:51.216Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-10 17:38:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540053
;

-- 2021-06-10T14:38:58.798Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=232, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-10 17:38:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540053
;

-- 2021-06-10T14:39:37.561Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-10 17:39:37','YYYY-MM-DD HH24:MI:SS'),100,1000010,540054,'Y','N','Y','N','N','N','N','N','N','Reduzierte MWSt 21% (LV)',21,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-10 17:39:37','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-10T14:39:37.564Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540054 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-10T14:39:37.566Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540054, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540054)
;

-- 2021-06-10T14:39:39.177Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-10 17:39:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540054
;

-- 2021-06-10T14:39:43.880Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=226, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-10 17:39:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540054
;

-- 2021-06-10T14:41:07.563Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-10 17:41:07','YYYY-MM-DD HH24:MI:SS'),100,1000010,540055,'Y','N','Y','N','N','N','N','N','N','Reduzierte MWSt 5% (HR)',5,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-10 17:41:07','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-10T14:41:07.566Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540055 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-10T14:41:07.569Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540055, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540055)
;

-- 2021-06-10T14:41:08.761Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-10 17:41:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540055
;

-- 2021-06-10T14:41:14.234Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=163, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-10 17:41:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540055
;

-- 2021-06-10T14:42:23.460Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-10 17:42:23','YYYY-MM-DD HH24:MI:SS'),100,1000010,540056,'Y','N','Y','N','N','N','N','N','N','Reduzierte MWSt 4% (IT)',4,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-10 17:42:23','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-10T14:42:23.462Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540056 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-10T14:42:23.465Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540056, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540056)
;

-- 2021-06-10T14:42:25.405Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-10 17:42:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540056
;

-- 2021-06-10T14:42:28.822Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=214, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-10 17:42:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540056
;

-- 2021-06-10T14:43:24.861Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-10 17:43:24','YYYY-MM-DD HH24:MI:SS'),100,1000010,540057,'Y','N','Y','N','N','N','N','N','N','Reduzierte MWSt 0% (IE)',0,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-10 17:43:24','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-10T14:43:24.863Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540057 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-10T14:43:24.866Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540057, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540057)
;

-- 2021-06-10T14:43:26.127Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-10 17:43:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540057
;

-- 2021-06-10T14:43:36.951Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=212, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-10 17:43:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540057
;

-- 2021-06-10T14:45:09.914Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,TO_TIMESTAMP('2021-06-10 17:45:09','YYYY-MM-DD HH24:MI:SS'),100,1000010,540058,'Y','N','Y','N','N','N','N','N','N','Reduzierte MWSt 0% (GB)',0,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-10 17:45:09','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-10T14:45:09.917Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540058 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-10T14:45:09.920Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540058, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540058)
;

-- 2021-06-10T14:48:33.460Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,Updated,UpdatedBy,ValidFrom) VALUES (1000000,1000000,TO_TIMESTAMP('2021-06-10 17:48:33','YYYY-MM-DD HH24:MI:SS'),100,1000010,540059,'Y','N','Y','N','N','N','N','N','N','Reduzierte MWSt 6% (GR)',0,'N','B',TO_TIMESTAMP('2021-06-10 17:48:33','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-10T14:48:33.463Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540059 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-10T14:48:33.465Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540059, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540059)
;

-- 2021-06-10T14:48:41.329Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET Rate=6,Updated=TO_TIMESTAMP('2021-06-10 17:48:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540059
;

-- 2021-06-10T14:48:44.461Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2021-06-10 17:48:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540059
;

-- 2021-06-10T14:48:49.761Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=192, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-10 17:48:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540059
;

-- 2021-06-10T14:50:27.478Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=333, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-10 17:50:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540058
;

-- 2021-06-10T14:50:52.451Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=333, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-10 17:50:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540041
;

-- 2021-06-10T14:51:53.347Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-10 17:51:53','YYYY-MM-DD HH24:MI:SS'),100,1000010,540060,'Y','N','Y','N','N','N','N','N','N','Reduzierte MWSt 5.5% (FR)',5.5,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-10 17:51:53','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-10T14:51:53.349Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540060 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-10T14:51:53.352Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540060, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540060)
;

-- 2021-06-10T14:51:56.556Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-10 17:51:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540060
;

-- 2021-06-10T14:51:59.639Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=102, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-10 17:51:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540060
;

-- 2021-06-10T14:53:31.478Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-10 17:53:31','YYYY-MM-DD HH24:MI:SS'),100,1000010,540061,'Y','N','Y','N','N','N','N','N','N','Reduzierte MWSt 5.5% (MC)',5.5,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-10 17:53:31','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-10T14:53:31.480Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540061 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-10T14:53:31.484Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540061, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540061)
;

-- 2021-06-10T14:53:33.692Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-10 17:53:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540061
;

-- 2021-06-10T14:53:37.165Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=250, TypeOfDestCountry='OUTSIDE_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-10 17:53:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540061
;

-- 2021-06-11T06:16:00.570Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-11 09:16:00','YYYY-MM-DD HH24:MI:SS'),100,1000010,540062,'Y','N','Y','N','N','N','N','N','N','Reduzierte MWSt 10% (FI)',10,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-11 09:16:00','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-11T06:16:00.594Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540062 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-11T06:16:00.624Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540062, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540062)
;

-- 2021-06-11T06:16:02.226Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-11 09:16:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540062
;

-- 2021-06-11T06:16:06.738Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=181, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-11 09:16:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540062
;

-- 2021-06-11T06:17:12.222Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-11 09:17:12','YYYY-MM-DD HH24:MI:SS'),100,1000010,540063,'Y','N','Y','N','N','N','N','N','N','Reduzierte MWSt 9% (EE)',9,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-11 09:17:12','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-11T06:17:12.225Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540063 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-11T06:17:12.228Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540063, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540063)
;

-- 2021-06-11T06:17:15.709Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-11 09:17:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540063
;

-- 2021-06-11T06:17:18.802Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=176, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-11 09:17:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540063
;

-- 2021-06-11T06:18:34.285Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-11 09:18:34','YYYY-MM-DD HH24:MI:SS'),100,1000010,540064,'Y','N','Y','N','N','N','N','N','N','Reduzierte MWSt 25% (DK)',25,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-11 09:18:34','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-11T06:18:34.288Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540064 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-11T06:18:34.290Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540064, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540064)
;

-- 2021-06-11T06:18:36.119Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-11 09:18:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540064
;

-- 2021-06-11T06:18:49.169Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=167, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-11 09:18:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540064
;

-- 2021-06-11T06:19:53.087Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-11 09:19:53','YYYY-MM-DD HH24:MI:SS'),100,1000010,540065,'Y','N','Y','N','N','N','N','N','N','Reduzierte MWSt 20% (BG)',20,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-11 09:19:53','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-11T06:19:53.089Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540065 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-11T06:19:53.092Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540065, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540065)
;

-- 2021-06-11T06:19:54.800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-11 09:19:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540065
;

-- 2021-06-11T06:19:59.995Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=142, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-11 09:19:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540065
;

-- 2021-06-11T06:20:40.185Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-11 09:20:40','YYYY-MM-DD HH24:MI:SS'),100,1000010,540066,'Y','N','Y','N','N','N','N','N','N','Reduzierte MWSt 6% (BE)',6,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-11 09:20:40','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-11T06:20:40.188Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540066 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-11T06:20:40.191Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540066, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540066)
;

-- 2021-06-11T06:20:42.059Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-11 09:20:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540066
;

-- 2021-06-11T06:20:45.968Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=103, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-11 09:20:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540066
;

-- 2021-06-11T09:12:11.917Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET Name='Reduzierte MWSt 10% (AT)',Updated=TO_TIMESTAMP('2021-06-11 12:12:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540049
;

-- 2021-06-11T09:12:11.925Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl trl SET Description=NULL, Name='Reduzierte MWSt 10% (AT)', TaxIndicator=NULL  WHERE C_Tax_ID=540049 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-11T09:12:15.826Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET Rate=10,Updated=TO_TIMESTAMP('2021-06-11 12:12:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540049
;

-- 2021-06-11T09:13:05.365Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-11 12:13:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND C_Tax_ID=540049
;

-- 2021-06-11T09:13:07.925Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-11 12:13:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND C_Tax_ID=540049
;

-- 2021-06-11T09:13:10.345Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-11 12:13:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND C_Tax_ID=540049
;

-- 2021-06-11T09:13:36.311Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl SET Name='Reduced VAT 10% (AT)',Updated=TO_TIMESTAMP('2021-06-11 12:13:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_Tax_ID=540049
;

-- 2021-06-11T09:13:38.125Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-11 12:13:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_Tax_ID=540049
;

-- 2021-06-11T09:15:21.860Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-11 12:15:21','YYYY-MM-DD HH24:MI:SS'),100,540007,540067,'Y','N','Y','N','N','N','N','N','N','Stark ermäßigte MwSt 13% (AT)',13,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-11 12:15:21','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-11T09:15:21.862Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540067 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-11T09:15:21.889Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540067, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540067)
;

-- 2021-06-11T09:15:28.339Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-11 12:15:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540067
;

-- 2021-06-11T09:15:38.047Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=108, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-11 12:15:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540067
;

-- 2021-06-11T09:20:13.410Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-11 12:20:13','YYYY-MM-DD HH24:MI:SS'),100,540007,540068,'Y','N','Y','N','N','N','N','N','N','Stark ermäßigte MwSt 12% (BE)',12,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-11 12:20:13','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-11T09:20:13.422Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540068 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-11T09:20:13.428Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540068, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540068)
;

-- 2021-06-11T09:20:15.712Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-11 12:20:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540068
;

-- 2021-06-11T09:20:21.979Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=103, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-11 12:20:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540068
;

-- 2021-06-11T09:22:00.626Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET Name='Reduzierte MWSt 9% (BG)',Updated=TO_TIMESTAMP('2021-06-11 12:22:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540065
;

-- 2021-06-11T09:22:00.628Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl trl SET Description=NULL, Name='Reduzierte MWSt 9% (BG)', TaxIndicator=NULL  WHERE C_Tax_ID=540065 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-11T09:22:08.162Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET Rate=9,Updated=TO_TIMESTAMP('2021-06-11 12:22:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540065
;

-- 2021-06-11T09:23:59.395Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-11 12:23:59','YYYY-MM-DD HH24:MI:SS'),100,540007,540069,'Y','N','Y','N','N','N','N','N','N','Stark ermäßigte MwSt 13% (HR)',13,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-11 12:23:59','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-11T09:23:59.398Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540069 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-11T09:23:59.401Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540069, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540069)
;

-- 2021-06-11T09:24:01.225Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-11 12:24:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540069
;

-- 2021-06-11T09:24:05.185Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=163, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-11 12:24:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540069
;

-- 2021-06-11T09:26:22.955Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-11 12:26:22','YYYY-MM-DD HH24:MI:SS'),100,540007,540070,'Y','N','Y','N','N','N','N','N','N','Stark ermäßigte MwSt 9% (CY)',9,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-11 12:26:22','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-11T09:26:22.970Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540070 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-11T09:26:22.978Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540070, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540070)
;

-- 2021-06-11T09:26:24.632Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-11 12:26:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540070
;

-- 2021-06-11T09:26:29.111Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=165, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-11 12:26:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540070
;

-- 2021-06-11T09:27:47.192Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-11 12:27:47','YYYY-MM-DD HH24:MI:SS'),100,540007,540071,'Y','N','Y','N','N','N','N','N','N','Stark ermäßigte MwSt 15% (CZ)',15,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-11 12:27:47','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-11T09:27:47.195Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540071 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-11T09:27:47.199Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540071, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540071)
;

-- 2021-06-11T09:27:48.715Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-11 12:27:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540071
;

-- 2021-06-11T09:28:03.216Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=166, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-11 12:28:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540071
;

-- 2021-06-11T09:29:53.974Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-11 12:29:53','YYYY-MM-DD HH24:MI:SS'),100,540007,540072,'Y','N','Y','N','N','N','N','N','N','Stark ermäßigte MwSt 14% (FI)',14,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-11 12:29:53','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-11T09:29:53.978Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540072 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-11T09:29:53.984Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540072, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540072)
;

-- 2021-06-11T09:29:56.006Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-11 12:29:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540072
;

-- 2021-06-11T09:29:59.648Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=181, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-11 12:29:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540072
;

-- 2021-06-11T09:32:34.600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-11 12:32:34','YYYY-MM-DD HH24:MI:SS'),100,540007,540073,'Y','N','Y','N','N','N','N','N','N','Stark ermäßigte MwSt 10% (FR)',10,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-11 12:32:34','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-11T09:32:34.602Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540073 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-11T09:32:34.605Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540073, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540073)
;

-- 2021-06-11T09:32:36.883Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-11 12:32:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540073
;

-- 2021-06-11T09:32:40.695Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=102, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-11 12:32:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540073
;

-- 2021-06-11T09:33:20.447Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET Name='Stark ermäßigte MwSt 2.1% (FR)',Updated=TO_TIMESTAMP('2021-06-11 12:33:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540073
;

-- 2021-06-11T09:33:20.450Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl trl SET Description=NULL, Name='Stark ermäßigte MwSt 2.1% (FR)', TaxIndicator=NULL  WHERE C_Tax_ID=540073 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-11T09:33:24.843Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET Rate=2.1,Updated=TO_TIMESTAMP('2021-06-11 12:33:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540073
;

-- 2021-06-11T09:33:38.502Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_TaxCategory_ID=540006,Updated=TO_TIMESTAMP('2021-06-11 12:33:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540073
;

-- 2021-06-11T09:35:12.183Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-11 12:35:12','YYYY-MM-DD HH24:MI:SS'),100,540007,540074,'Y','N','Y','N','N','N','N','N','N','Parken MwSt 10% (FR)',10,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-11 12:35:12','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-11T09:35:12.185Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540074 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-11T09:35:12.188Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540074, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540074)
;

-- 2021-06-11T09:35:13.966Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-11 12:35:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540074
;

-- 2021-06-11T09:35:17.537Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=102, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-11 12:35:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540074
;

-- 2021-06-11T09:35:52.005Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET Name='Parken MwSt 13% (AT)',Updated=TO_TIMESTAMP('2021-06-11 12:35:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540067
;

-- 2021-06-11T09:35:52.008Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl trl SET Description=NULL, Name='Parken MwSt 13% (AT)', TaxIndicator=NULL  WHERE C_Tax_ID=540067 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-11T09:36:24.163Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl SET Name='Parking VAT 13% (AT)',Updated=TO_TIMESTAMP('2021-06-11 12:36:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_Tax_ID=540067
;

-- 2021-06-11T09:36:52.324Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET Name='Parken MwSt 12% (BE)',Updated=TO_TIMESTAMP('2021-06-11 12:36:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540068
;

-- 2021-06-11T09:36:52.328Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl trl SET Description=NULL, Name='Parken MwSt 12% (BE)', TaxIndicator=NULL  WHERE C_Tax_ID=540068 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-11T09:37:14.916Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl SET Name='Parking VAT 12% (BE)',Updated=TO_TIMESTAMP('2021-06-11 12:37:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_Tax_ID=540068
;

-- 2021-06-11T09:37:34.792Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET Name='Parken MwSt 13% (HR)',Updated=TO_TIMESTAMP('2021-06-11 12:37:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540069
;

-- 2021-06-11T09:37:34.795Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl trl SET Description=NULL, Name='Parken MwSt 13% (HR)', TaxIndicator=NULL  WHERE C_Tax_ID=540069 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-11T09:37:51.810Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl SET Name='Parking VAT 13% (HR)',Updated=TO_TIMESTAMP('2021-06-11 12:37:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_Tax_ID=540069
;

-- 2021-06-11T09:38:06.744Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET Name='Parken MwSt 9% (CY)',Updated=TO_TIMESTAMP('2021-06-11 12:38:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540070
;

-- 2021-06-11T09:38:06.747Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl trl SET Description=NULL, Name='Parken MwSt 9% (CY)', TaxIndicator=NULL  WHERE C_Tax_ID=540070 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-11T09:38:21.963Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl SET Name='Parken MwSt9% (CY)',Updated=TO_TIMESTAMP('2021-06-11 12:38:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND C_Tax_ID=540070
;

-- 2021-06-11T09:38:34.644Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl SET Name='Parking VAT 9% (CY)',Updated=TO_TIMESTAMP('2021-06-11 12:38:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_Tax_ID=540070
;

-- 2021-06-11T09:39:05.409Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET Name='Parken MwSt 15% (CZ)',Updated=TO_TIMESTAMP('2021-06-11 12:39:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540071
;

-- 2021-06-11T09:39:05.412Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl trl SET Description=NULL, Name='Parken MwSt 15% (CZ)', TaxIndicator=NULL  WHERE C_Tax_ID=540071 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-11T09:39:16.326Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl SET Name='Parken MwSt15% (CZ)',Updated=TO_TIMESTAMP('2021-06-11 12:39:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND C_Tax_ID=540071
;

-- 2021-06-11T09:39:28.942Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl SET Name='Parking VAT 15% (CZ)',Updated=TO_TIMESTAMP('2021-06-11 12:39:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_Tax_ID=540071
;

-- 2021-06-11T09:39:54.521Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET Name='Parken MwSt 14% (FI)',Updated=TO_TIMESTAMP('2021-06-11 12:39:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540072
;

-- 2021-06-11T09:39:54.525Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl trl SET Description=NULL, Name='Parken MwSt 14% (FI)', TaxIndicator=NULL  WHERE C_Tax_ID=540072 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-11T09:40:18.452Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl SET Name='Parking VAT 14% (FI)',Updated=TO_TIMESTAMP('2021-06-11 12:40:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_Tax_ID=540072
;

-- 2021-06-11T09:47:29.675Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-11 12:47:29','YYYY-MM-DD HH24:MI:SS'),100,540007,540075,'Y','N','Y','N','N','N','N','N','N','Parken MwSt 10% (GR)',10,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-11 12:47:29','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-11T09:47:29.677Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540075 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-11T09:47:29.681Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540075, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540075)
;

-- 2021-06-11T09:47:32.361Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-11 12:47:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540075
;

-- 2021-06-11T09:47:35.953Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=192, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-11 12:47:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540075
;

-- 2021-06-11T09:48:24.632Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-11 12:48:24','YYYY-MM-DD HH24:MI:SS'),100,540007,540076,'Y','N','Y','N','N','N','N','N','N','Parken MwSt 18% (HU)',18,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-11 12:48:24','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-11T09:48:24.634Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540076 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-11T09:48:24.637Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540076, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540076)
;

-- 2021-06-11T09:48:26.596Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-11 12:48:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540076
;

-- 2021-06-11T09:48:29.893Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=206, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-11 12:48:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540076
;

-- 2021-06-11T09:51:31.062Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET Name='Reduzierte MWSt 9% (IE)',Updated=TO_TIMESTAMP('2021-06-11 12:51:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540057
;

-- 2021-06-11T09:51:31.075Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl trl SET Description=NULL, Name='Reduzierte MWSt 9% (IE)', TaxIndicator=NULL  WHERE C_Tax_ID=540057 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-11T09:51:35.500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET Rate=9,Updated=TO_TIMESTAMP('2021-06-11 12:51:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540057
;

-- 2021-06-11T09:52:11.320Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl SET Name='Reduced VAT 9% (IE)',Updated=TO_TIMESTAMP('2021-06-11 12:52:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_Tax_ID=540057
;

-- 2021-06-11T09:52:45.702Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_TaxCategory SET Name='.',Updated=TO_TIMESTAMP('2021-06-11 12:52:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_TaxCategory_ID=540007
;

-- 2021-06-11T09:52:45.706Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_TaxCategory_Trl trl SET Description=NULL, Name='.'  WHERE C_TaxCategory_ID=540007 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-11T09:53:22.494Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-11 12:53:22','YYYY-MM-DD HH24:MI:SS'),100,540007,540077,'Y','N','Y','N','N','N','N','N','N','Parken MwSt 13.5% (IE)',13.5,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-11 12:53:22','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-11T09:53:22.496Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540077 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-11T09:53:22.505Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540077, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540077)
;

-- 2021-06-11T09:53:24.512Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-11 12:53:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540077
;

-- 2021-06-11T09:53:42.118Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=212, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-11 12:53:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540077
;

-- 2021-06-11T09:54:31.093Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-11 12:54:31','YYYY-MM-DD HH24:MI:SS'),100,540006,540078,'Y','N','Y','N','N','N','N','N','N','Stark ermäßigte MwSt 4.8% (IE)',4.8,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-11 12:54:31','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-11T09:54:31.095Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540078 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-11T09:54:31.098Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540078, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540078)
;

-- 2021-06-11T09:54:34.004Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-11 12:54:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540078
;

-- 2021-06-11T09:54:38.041Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=212, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-11 12:54:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540078
;

-- 2021-06-11T09:55:16.824Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET Name='Stark ermäßigte MwSt 4% (IT)',Updated=TO_TIMESTAMP('2021-06-11 12:55:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540056
;

-- 2021-06-11T09:55:16.826Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl trl SET Description=NULL, Name='Stark ermäßigte MwSt 4% (IT)', TaxIndicator=NULL  WHERE C_Tax_ID=540056 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-11T09:55:19.331Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_TaxCategory_ID=100,Updated=TO_TIMESTAMP('2021-06-11 12:55:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540056
;

-- 2021-06-11T09:55:22.344Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_TaxCategory_ID=540006,Updated=TO_TIMESTAMP('2021-06-11 12:55:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540056
;

-- 2021-06-11T09:56:03.941Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl SET Name='Super-reduced VAT 4% (IT)',Updated=TO_TIMESTAMP('2021-06-11 12:56:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_Tax_ID=540056
;

-- 2021-06-11T09:58:50.907Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_TaxCategory SET Name='Parken MwSt',Updated=TO_TIMESTAMP('2021-06-11 12:58:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_TaxCategory_ID=540007
;

-- 2021-06-11T09:58:50.911Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_TaxCategory_Trl trl SET Description=NULL, Name='Parken MwSt'  WHERE C_TaxCategory_ID=540007 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-11T09:59:05.788Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_TaxCategory_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-11 12:59:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND C_TaxCategory_ID=540007
;

-- 2021-06-11T09:59:07.300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_TaxCategory_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-11 12:59:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND C_TaxCategory_ID=540007
;

-- 2021-06-11T09:59:08.981Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_TaxCategory_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-11 12:59:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND C_TaxCategory_ID=540007
;

-- 2021-06-11T10:02:03.217Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-11 13:02:03','YYYY-MM-DD HH24:MI:SS'),100,1000010,540079,'Y','N','Y','N','N','N','N','N','N','Reduzierte MWSt 5% (IT)',5,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-11 13:02:03','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-11T10:02:03.232Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540079 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-11T10:02:03.241Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540079, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540079)
;

-- 2021-06-11T10:02:04.867Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-11 13:02:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540079
;

-- 2021-06-11T10:02:13.062Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=214, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-11 13:02:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540079
;

-- 2021-06-11T10:03:17.766Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-11 13:03:17','YYYY-MM-DD HH24:MI:SS'),100,540007,540080,'Y','N','Y','N','N','N','N','N','N','Parken MwSt 10% (IT)',10,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-11 13:03:17','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-11T10:03:17.769Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540080 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-11T10:03:17.772Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540080, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540080)
;

-- 2021-06-11T10:03:19.619Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-11 13:03:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540080
;

-- 2021-06-11T10:03:23.793Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=214, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-11 13:03:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540080
;

-- 2021-06-11T10:04:30.148Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET Name='Reduzierte MWSt 5% (LV)',Updated=TO_TIMESTAMP('2021-06-11 13:04:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540054
;

-- 2021-06-11T10:04:30.151Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl trl SET Description=NULL, Name='Reduzierte MWSt 5% (LV)', TaxIndicator=NULL  WHERE C_Tax_ID=540054 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-11T10:04:33.352Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET Rate=5,Updated=TO_TIMESTAMP('2021-06-11 13:04:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540054
;

-- 2021-06-11T10:05:42.572Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-11 13:05:42','YYYY-MM-DD HH24:MI:SS'),100,540007,540081,'Y','N','Y','N','N','N','N','N','N','Parken MwSt 12% (LV)',12,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-11 13:05:42','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-11T10:05:42.574Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540081 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-11T10:05:42.577Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540081, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540081)
;

-- 2021-06-11T10:05:44.278Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-11 13:05:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540081
;

-- 2021-06-11T10:05:50.549Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=226, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-11 13:05:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540081
;

-- 2021-06-11T10:06:51.506Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET Name='Reduzierte MWSt 5% (LT)',Updated=TO_TIMESTAMP('2021-06-11 13:06:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540053
;

-- 2021-06-11T10:06:51.510Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl trl SET Description=NULL, Name='Reduzierte MWSt 5% (LT)', TaxIndicator=NULL  WHERE C_Tax_ID=540053 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-11T10:06:56.255Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET Rate=5,Updated=TO_TIMESTAMP('2021-06-11 13:06:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540053
;

-- 2021-06-11T10:07:57.351Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-11 13:07:57','YYYY-MM-DD HH24:MI:SS'),100,540007,540082,'Y','N','Y','N','N','N','N','N','N','Parken MwSt 9% (LT)',9,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-11 13:07:57','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-11T10:07:57.354Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540082 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-11T10:07:57.357Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540082, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540082)
;

-- 2021-06-11T10:07:58.737Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-11 13:07:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540082
;

-- 2021-06-11T10:08:03.383Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=232, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-11 13:08:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540082
;

-- 2021-06-11T10:09:19.341Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET Name='Reduzierte MWSt 8% (LU)',Updated=TO_TIMESTAMP('2021-06-11 13:09:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540052
;

-- 2021-06-11T10:09:19.344Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl trl SET Description=NULL, Name='Reduzierte MWSt 8% (LU)', TaxIndicator=NULL  WHERE C_Tax_ID=540052 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-11T10:09:21.988Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET Rate=8,Updated=TO_TIMESTAMP('2021-06-11 13:09:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540052
;

-- 2021-06-11T10:10:17.252Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,Updated,UpdatedBy,ValidFrom) VALUES (1000000,1000000,101,TO_TIMESTAMP('2021-06-11 13:10:17','YYYY-MM-DD HH24:MI:SS'),100,540007,540083,'Y','N','Y','N','N','N','N','N','N','Parken MwSt 14% (LU)',0,'N','B',TO_TIMESTAMP('2021-06-11 13:10:17','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-11T10:10:17.257Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540083 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-11T10:10:17.261Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540083, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540083)
;

-- 2021-06-11T10:10:19.002Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-11 13:10:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540083
;

-- 2021-06-11T10:10:23.463Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=233, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-11 13:10:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540083
;

-- 2021-06-11T10:10:29.661Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2021-06-11 13:10:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540083
;

-- 2021-06-11T10:10:37.667Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET Rate=14,Updated=TO_TIMESTAMP('2021-06-11 13:10:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540083
;

-- 2021-06-11T10:11:21.179Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-11 13:11:21','YYYY-MM-DD HH24:MI:SS'),100,540006,540084,'Y','N','Y','N','N','N','N','N','N','Stark ermäßigte MwSt 3% (LU)',3,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-11 13:11:21','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-11T10:11:21.181Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540084 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-11T10:11:21.183Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540084, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540084)
;

-- 2021-06-11T10:11:23.306Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-11 13:11:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540084
;

-- 2021-06-11T10:11:26.906Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=233, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-11 13:11:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540084
;

-- 2021-06-11T10:13:20.477Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-11 13:13:20','YYYY-MM-DD HH24:MI:SS'),100,540007,540085,'Y','N','Y','N','N','N','N','N','N','Parken MwSt 7% (MT)',7,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-11 13:13:20','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-11T10:13:20.479Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540085 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-11T10:13:20.481Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540085, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540085)
;

-- 2021-06-11T10:13:22.249Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-11 13:13:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540085
;

-- 2021-06-11T10:13:27.978Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=241, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-11 13:13:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540085
;

-- 2021-06-11T10:15:02.317Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-11 13:15:02','YYYY-MM-DD HH24:MI:SS'),100,540007,540086,'Y','N','Y','N','N','N','N','N','N','Parken MwSt 8% (PL)',8,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-11 13:15:02','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-11T10:15:02.320Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540086 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-11T10:15:02.324Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540086, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540086)
;

-- 2021-06-11T10:15:03.752Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-11 13:15:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540086
;

-- 2021-06-11T10:15:06.806Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=280, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-11 13:15:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540086
;

-- 2021-06-11T10:16:26.155Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-11 13:16:26','YYYY-MM-DD HH24:MI:SS'),100,540007,540087,'Y','N','Y','N','N','N','N','N','N','Parken MwSt 13% (PT)',13,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-11 13:16:26','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-11T10:16:26.158Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540087 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-11T10:16:26.162Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540087, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540087)
;

-- 2021-06-11T10:16:28.446Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-11 13:16:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540087
;

-- 2021-06-11T10:16:32.438Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=281, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-11 13:16:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540087
;

-- 2021-06-11T10:17:39.083Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-11 13:17:39','YYYY-MM-DD HH24:MI:SS'),100,540007,540088,'Y','N','Y','N','N','N','N','N','N','Parken MwSt 9% (RO)',9,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-11 13:17:39','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-11T10:17:39.085Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540088 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-11T10:17:39.088Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540088, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540088)
;

-- 2021-06-11T10:17:40.816Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-11 13:17:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540088
;

-- 2021-06-11T10:17:49.771Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=285, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-11 13:17:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540088
;

-- 2021-06-11T10:20:12.029Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET Name='Parken MwSt 9.5% (SI)',Updated=TO_TIMESTAMP('2021-06-11 13:20:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540043
;

-- 2021-06-11T10:20:12.031Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl trl SET Description=NULL, Name='Parken MwSt 9.5% (SI)', TaxIndicator=NULL  WHERE C_Tax_ID=540043 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-11T10:20:13.760Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_TaxCategory_ID=540007,Updated=TO_TIMESTAMP('2021-06-11 13:20:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540043
;

-- 2021-06-11T10:21:38.296Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,TO_TIMESTAMP('2021-06-11 13:21:38','YYYY-MM-DD HH24:MI:SS'),100,1000010,540089,'Y','N','Y','N','N','N','N','N','N','Reduzierte MWSt 5% (SI)',5,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-11 13:21:38','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-11T10:21:38.298Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540089 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-11T10:21:38.301Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540089, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540089)
;

-- 2021-06-11T10:21:43.535Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=302, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-11 13:21:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540089
;

-- 2021-06-11T10:22:50.303Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET Name='Reduzierte MWSt 10% (ES)',Updated=TO_TIMESTAMP('2021-06-11 13:22:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540042
;

-- 2021-06-11T10:22:50.306Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl trl SET Description=NULL, Name='Reduzierte MWSt 10% (ES)', TaxIndicator=NULL  WHERE C_Tax_ID=540042 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-11T10:22:54.375Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET Rate=10,Updated=TO_TIMESTAMP('2021-06-11 13:22:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540042
;

-- 2021-06-11T10:23:44.392Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,Updated,UpdatedBy,ValidFrom) VALUES (1000000,1000000,101,TO_TIMESTAMP('2021-06-11 13:23:44','YYYY-MM-DD HH24:MI:SS'),100,540006,540090,'Y','N','Y','N','N','N','N','N','N','Stark ermäßigte MwSt 4% (ES)',0,'N','B',TO_TIMESTAMP('2021-06-11 13:23:44','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-11T10:23:44.394Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540090 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-11T10:23:44.397Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540090, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540090)
;

-- 2021-06-11T10:23:46.900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-11 13:23:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540090
;

-- 2021-06-11T10:23:51.702Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=106, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-11 13:23:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540090
;

-- 2021-06-11T10:23:56.408Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET Rate=4,Updated=TO_TIMESTAMP('2021-06-11 13:23:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540090
;

-- 2021-06-11T10:24:03.363Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2021-06-11 13:24:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540090
;

-- 2021-06-11T10:25:28.043Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsFiscalRepresentation,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,RequiresTaxCertificate,SOPOType,TypeOfDestCountry,Updated,UpdatedBy,ValidFrom) VALUES (1000000,0,101,TO_TIMESTAMP('2021-06-11 13:25:28','YYYY-MM-DD HH24:MI:SS'),100,540007,540091,'Y','N','Y','N','N','N','N','N','N','Parken MwSt 12% (SE)',12,'N','B','WITHIN_COUNTRY_AREA',TO_TIMESTAMP('2021-06-11 13:25:28','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-01','YYYY-MM-DD'))
;

-- 2021-06-11T10:25:28.046Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Tax_ID=540091 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2021-06-11T10:25:28.048Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy
                       , T_Credit_Acct
                       , T_Due_Acct
                       , T_Expense_Acct
                       , T_Liability_Acct
                       , T_PayDiscount_Exp_Acct
                       , T_PayDiscount_Rev_Acct
                       , T_Receivables_Acct
                       , T_Revenue_Acct
) SELECT 540091, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
       , p.T_Credit_Acct
       , p.T_Due_Acct
       , p.T_Expense_Acct
       , p.T_Liability_Acct
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
       , NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
       , p.T_Receivables_Acct
       , NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
                              AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540091)
;

-- 2021-06-11T10:25:29.621Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET C_Country_ID=NULL,Updated=TO_TIMESTAMP('2021-06-11 13:25:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540091
;

-- 2021-06-11T10:25:34.229Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET To_Country_ID=313, TypeOfDestCountry='WITHIN_COUNTRY_AREA',Updated=TO_TIMESTAMP('2021-06-11 13:25:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540091
;

-- 2021-06-11T10:26:03.919Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET Name='Reduzierte MWSt 5% (GB)',Updated=TO_TIMESTAMP('2021-06-11 13:26:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540058
;

-- 2021-06-11T10:26:03.921Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl trl SET Description=NULL, Name='Reduzierte MWSt 5% (GB)', TaxIndicator=NULL  WHERE C_Tax_ID=540058 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-11T10:26:07.357Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET Rate=5,Updated=TO_TIMESTAMP('2021-06-11 13:26:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540058
;

-- 2021-06-11T11:02:13.811Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_TaxCategory SET Name='Vorläufige MwSt',Updated=TO_TIMESTAMP('2021-06-11 14:02:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_TaxCategory_ID=540007
;

-- 2021-06-11T11:02:13.814Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_TaxCategory_Trl trl SET Description=NULL, Name='Vorläufige MwSt'  WHERE C_TaxCategory_ID=540007 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-11T11:02:39.754Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_TaxCategory_Trl SET Name='Vorläufige MwSt',Updated=TO_TIMESTAMP('2021-06-11 14:02:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND C_TaxCategory_ID=540007
;

-- 2021-06-11T11:02:45.414Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_TaxCategory_Trl SET Name='Vorläufige MwSt',Updated=TO_TIMESTAMP('2021-06-11 14:02:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND C_TaxCategory_ID=540007
;

-- 2021-06-11T11:04:42.923Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET Name='Vorläufige MwSt 10% (IT)',Updated=TO_TIMESTAMP('2021-06-11 14:04:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540080
;

-- 2021-06-11T11:04:42.926Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl trl SET Description=NULL, Name='Vorläufige MwSt 10% (IT)', TaxIndicator=NULL  WHERE C_Tax_ID=540080 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-11T11:05:21.057Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl SET Name='Parking VAT 10% (IT)',Updated=TO_TIMESTAMP('2021-06-11 14:05:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_Tax_ID=540080
;

-- 2021-06-11T11:05:43.727Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET Name='Vorläufige MwSt 15% (CZ)',Updated=TO_TIMESTAMP('2021-06-11 14:05:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540071
;

-- 2021-06-11T11:05:43.729Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl trl SET Description=NULL, Name='Vorläufige MwSt 15% (CZ)', TaxIndicator=NULL  WHERE C_Tax_ID=540071 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-11T11:06:22.903Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET Name='Vorläufige MwSt 9% (LT)',Updated=TO_TIMESTAMP('2021-06-11 14:06:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540082
;

-- 2021-06-11T11:06:22.905Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl trl SET Description=NULL, Name='Vorläufige MwSt 9% (LT)', TaxIndicator=NULL  WHERE C_Tax_ID=540082 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-11T11:06:38.761Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl SET Name='Parking VAT 9% (LT)',Updated=TO_TIMESTAMP('2021-06-11 14:06:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_Tax_ID=540082
;

-- 2021-06-11T11:06:57.917Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET Name='Vorläufige MwSt 7% (MT)',Updated=TO_TIMESTAMP('2021-06-11 14:06:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540085
;

-- 2021-06-11T11:06:57.919Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl trl SET Description=NULL, Name='Vorläufige MwSt 7% (MT)', TaxIndicator=NULL  WHERE C_Tax_ID=540085 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-11T11:07:13.523Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl SET Name='Parking VAT 7% (MT)',Updated=TO_TIMESTAMP('2021-06-11 14:07:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_Tax_ID=540085
;

-- 2021-06-11T11:07:38.384Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET Name='Vorläufige MwSt 10% (GR)',Updated=TO_TIMESTAMP('2021-06-11 14:07:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540075
;

-- 2021-06-11T11:07:38.387Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl trl SET Description=NULL, Name='Vorläufige MwSt 10% (GR)', TaxIndicator=NULL  WHERE C_Tax_ID=540075 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-11T11:07:53.163Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl SET Name='Parking VAT 10% (GR)',Updated=TO_TIMESTAMP('2021-06-11 14:07:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_Tax_ID=540075
;

-- 2021-06-11T11:08:17.223Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET Name='Vorläufige MwSt 8% (PL)',Updated=TO_TIMESTAMP('2021-06-11 14:08:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540086
;

-- 2021-06-11T11:08:17.226Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl trl SET Description=NULL, Name='Vorläufige MwSt 8% (PL)', TaxIndicator=NULL  WHERE C_Tax_ID=540086 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-11T11:08:31.954Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl SET Name='Parking VAT 8% (PL)',Updated=TO_TIMESTAMP('2021-06-11 14:08:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_Tax_ID=540086
;

-- 2021-06-11T11:09:27.746Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET Name='Vorläufige MwSt 13.5% (IE)',Updated=TO_TIMESTAMP('2021-06-11 14:09:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540077
;

-- 2021-06-11T11:09:27.748Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl trl SET Description=NULL, Name='Vorläufige MwSt 13.5% (IE)', TaxIndicator=NULL  WHERE C_Tax_ID=540077 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-11T11:09:47.779Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl SET Name='Parking VAT 13.5% (IE)',Updated=TO_TIMESTAMP('2021-06-11 14:09:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_Tax_ID=540077
;

-- 2021-06-11T11:10:15.880Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET Name='Vorläufige MwSt 13% (AT)',Updated=TO_TIMESTAMP('2021-06-11 14:10:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540067
;

-- 2021-06-11T11:10:15.883Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl trl SET Description=NULL, Name='Vorläufige MwSt 13% (AT)', TaxIndicator=NULL  WHERE C_Tax_ID=540067 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-11T11:10:50.841Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET Name='Vorläufige MwSt 12% (LV)',Updated=TO_TIMESTAMP('2021-06-11 14:10:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540081
;

-- 2021-06-11T11:10:50.842Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl trl SET Description=NULL, Name='Vorläufige MwSt 12% (LV)', TaxIndicator=NULL  WHERE C_Tax_ID=540081 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-11T11:11:07.095Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl SET Name='Parking VAT 12% (LV)',Updated=TO_TIMESTAMP('2021-06-11 14:11:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_Tax_ID=540081
;

-- 2021-06-11T11:11:28.908Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET Name='Vorläufige MwSt 13% (HR)',Updated=TO_TIMESTAMP('2021-06-11 14:11:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540069
;

-- 2021-06-11T11:11:28.911Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl trl SET Description=NULL, Name='Vorläufige MwSt 13% (HR)', TaxIndicator=NULL  WHERE C_Tax_ID=540069 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-11T11:11:51.734Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET Name='Vorläufige MwSt 18% (HU)',Updated=TO_TIMESTAMP('2021-06-11 14:11:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540076
;

-- 2021-06-11T11:11:51.737Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl trl SET Description=NULL, Name='Vorläufige MwSt 18% (HU)', TaxIndicator=NULL  WHERE C_Tax_ID=540076 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-11T11:12:11.897Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl SET Name='Parking VAT18% (HU)',Updated=TO_TIMESTAMP('2021-06-11 14:12:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_Tax_ID=540076
;

-- 2021-06-11T11:12:29.926Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET Name='Vorläufige MwSt 13% (PT)',Updated=TO_TIMESTAMP('2021-06-11 14:12:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540087
;

-- 2021-06-11T11:12:29.929Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl trl SET Description=NULL, Name='Vorläufige MwSt 13% (PT)', TaxIndicator=NULL  WHERE C_Tax_ID=540087 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-11T11:12:44.651Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl SET Name='Parking VAT 13% (PT)',Updated=TO_TIMESTAMP('2021-06-11 14:12:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_Tax_ID=540087
;

-- 2021-06-11T11:13:04.967Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET Name='Vorläufige MwSt  9% (RO)',Updated=TO_TIMESTAMP('2021-06-11 14:13:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540088
;

-- 2021-06-11T11:13:04.970Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl trl SET Description=NULL, Name='Vorläufige MwSt  9% (RO)', TaxIndicator=NULL  WHERE C_Tax_ID=540088 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-11T11:13:21.649Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl SET Name='Parking VAT 9% (RO)',Updated=TO_TIMESTAMP('2021-06-11 14:13:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_Tax_ID=540088
;

-- 2021-06-11T11:13:40.216Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET Name='Vorläufige MwSt 12% (BE)',Updated=TO_TIMESTAMP('2021-06-11 14:13:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540068
;

-- 2021-06-11T11:13:40.219Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl trl SET Description=NULL, Name='Vorläufige MwSt 12% (BE)', TaxIndicator=NULL  WHERE C_Tax_ID=540068 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-11T11:13:49.498Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-11 14:13:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_Tax_ID=540068
;

-- 2021-06-11T11:14:07.012Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET Name='Vorläufige MwSt 10% (FR)',Updated=TO_TIMESTAMP('2021-06-11 14:14:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540074
;

-- 2021-06-11T11:14:07.014Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl trl SET Description=NULL, Name='Vorläufige MwSt 10% (FR)', TaxIndicator=NULL  WHERE C_Tax_ID=540074 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-11T11:14:23.067Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl SET Name='Parking VAT 10% (FR)',Updated=TO_TIMESTAMP('2021-06-11 14:14:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_Tax_ID=540074
;

-- 2021-06-11T11:14:58.697Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET Name='Vorläufige MwStt 14% (LU)',Updated=TO_TIMESTAMP('2021-06-11 14:14:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540083
;

-- 2021-06-11T11:14:58.700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl trl SET Description=NULL, Name='Vorläufige MwStt 14% (LU)', TaxIndicator=NULL  WHERE C_Tax_ID=540083 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-11T11:15:14.090Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl SET Name='Parking VAT 14% (LU)',Updated=TO_TIMESTAMP('2021-06-11 14:15:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_Tax_ID=540083
;

-- 2021-06-11T11:15:28.015Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET Name='Vorläufige MwSt 14% (LU)',Updated=TO_TIMESTAMP('2021-06-11 14:15:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540083
;

-- 2021-06-11T11:15:28.019Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl trl SET Description=NULL, Name='Vorläufige MwSt 14% (LU)', TaxIndicator=NULL  WHERE C_Tax_ID=540083 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-11T11:15:51.692Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET Name='Vorläufige MwSt 9.5% (SI)',Updated=TO_TIMESTAMP('2021-06-11 14:15:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540043
;

-- 2021-06-11T11:15:51.694Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl trl SET Description=NULL, Name='Vorläufige MwSt 9.5% (SI)', TaxIndicator=NULL  WHERE C_Tax_ID=540043 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-11T11:16:05.473Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl SET Name='Parking VAT 9.5% (SI)',Updated=TO_TIMESTAMP('2021-06-11 14:16:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_Tax_ID=540043
;

-- 2021-06-11T11:16:21.260Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET Name='Vorläufige MwSt 14% (FI)',Updated=TO_TIMESTAMP('2021-06-11 14:16:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540072
;

-- 2021-06-11T11:16:21.263Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl trl SET Description=NULL, Name='Vorläufige MwSt 14% (FI)', TaxIndicator=NULL  WHERE C_Tax_ID=540072 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-11T11:17:08.555Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET Name='Vorläufige MwSt 9% (CY)',Updated=TO_TIMESTAMP('2021-06-11 14:17:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540070
;

-- 2021-06-11T11:17:08.556Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl trl SET Description=NULL, Name='Vorläufige MwSt 9% (CY)', TaxIndicator=NULL  WHERE C_Tax_ID=540070 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-11T11:17:42.532Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax SET Name='Vorläufige MwSt 12% (SE)',Updated=TO_TIMESTAMP('2021-06-11 14:17:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=540091
;

-- 2021-06-11T11:17:42.534Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl trl SET Description=NULL, Name='Vorläufige MwSt 12% (SE)', TaxIndicator=NULL  WHERE C_Tax_ID=540091 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-11T11:18:00.038Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Trl SET Name='Parking VAT 12% (SE)',Updated=TO_TIMESTAMP('2021-06-11 14:18:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_Tax_ID=540091
;
