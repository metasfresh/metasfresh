
-- Run mode: WEBUI

-- 2025-07-31T13:08:05.371Z
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,SeqNo,SOPOType,Updated,UpdatedBy,ValidFrom,ValidTo) VALUES (1000000,1000000,101,TO_TIMESTAMP('2025-07-31 13:08:05.368000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1000009,540107,'Y','N','Y','N','N','N','N','N','UST Normal 8.1%',0,0,'B',TO_TIMESTAMP('2025-07-31 13:08:05.368000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'2025-07-01'::timestamp without time zone,'9999-12-31'::timestamp without time zone)
;

-- 2025-07-31T13:08:05.381Z
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_Tax_ID=540107 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2025-07-31T13:08:05.385Z
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy 
, T_Credit_Acct
, T_Due_Acct
, T_Expense_Acct
, T_Liability_Acct
, T_PayDiscount_Exp_Acct
, T_PayDiscount_Rev_Acct
, T_Receivables_Acct
, T_Revenue_Acct
) SELECT 540107, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
, p.T_Credit_Acct
, p.T_Due_Acct
, p.T_Expense_Acct
, p.T_Liability_Acct
, NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
, NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
, p.T_Receivables_Acct
, NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
 FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
 AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540107)
;

-- 2025-07-31T13:08:10.570Z
UPDATE C_Tax SET ValidFrom='2025-01-01'::timestamp without time zone,Updated=TO_TIMESTAMP('2025-07-31 13:08:10.570000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE C_Tax_ID=540107
;

-- 2025-07-31T13:08:13.267Z
UPDATE C_Tax SET ValidFrom='2024-01-01'::timestamp without time zone,Updated=TO_TIMESTAMP('2025-07-31 13:08:13.266000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE C_Tax_ID=540107
;

-- 2025-07-31T13:08:28.207Z
UPDATE C_Tax SET Name='Normale MWSt 8.1%',Updated=TO_TIMESTAMP('2025-07-31 13:08:28.207000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE C_Tax_ID=540107
;

-- 2025-07-31T13:08:28.209Z
UPDATE C_Tax_Trl trl SET Name='Normale MWSt 8.1%' WHERE C_Tax_ID=540107 AND (IsTranslated='N' OR AD_Language='de_DE')
;

-- 2025-07-31T13:08:55.884Z
UPDATE C_Tax SET To_Country_ID=107, TypeOfDestCountry='OUTSIDE_COUNTRY_AREA',Updated=TO_TIMESTAMP('2025-07-31 13:08:55.884000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE C_Tax_ID=540107
;

-- 2025-07-31T13:09:15.994Z
UPDATE C_Tax SET SeqNo=1001,Updated=TO_TIMESTAMP('2025-07-31 13:09:15.994000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE C_Tax_ID=540107
;



-- 2025-07-31T13:16:51.636Z
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,SeqNo,SOPOType,Updated,UpdatedBy,ValidFrom,ValidTo) VALUES (1000000,1000000,101,TO_TIMESTAMP('2025-07-31 13:16:51.635000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1000009,540108,'Y','N','Y','N','N','N','N','N','Normale MWSt 7.7%',0,0,'B',TO_TIMESTAMP('2025-07-31 13:16:51.635000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'2021-01-01'::timestamp without time zone,'2023-12-31'::timestamp without time zone)
;

-- 2025-07-31T13:16:51.639Z
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_Tax_ID=540108 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2025-07-31T13:16:51.640Z
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy 
, T_Credit_Acct
, T_Due_Acct
, T_Expense_Acct
, T_Liability_Acct
, T_PayDiscount_Exp_Acct
, T_PayDiscount_Rev_Acct
, T_Receivables_Acct
, T_Revenue_Acct
) SELECT 540108, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
, p.T_Credit_Acct
, p.T_Due_Acct
, p.T_Expense_Acct
, p.T_Liability_Acct
, NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
, NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
, p.T_Receivables_Acct
, NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
 FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
 AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540108)
;

-- 2025-07-31T13:17:01.235Z
UPDATE C_Tax SET To_Country_ID=107, TypeOfDestCountry='OUTSIDE_COUNTRY_AREA',Updated=TO_TIMESTAMP('2025-07-31 13:17:01.235000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE C_Tax_ID=540108
;

-- 2025-07-31T13:17:07.209Z
UPDATE C_Tax SET SeqNo=1001,Updated=TO_TIMESTAMP('2025-07-31 13:17:07.209000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE C_Tax_ID=540108
;

-- 2025-07-31T13:17:11.129Z
UPDATE C_Tax SET Rate=7.7,Updated=TO_TIMESTAMP('2025-07-31 13:17:11.129000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE C_Tax_ID=540108
;

-- 2025-07-31T13:17:19.485Z
UPDATE C_Tax SET Rate=8.1,Updated=TO_TIMESTAMP('2025-07-31 13:17:19.485000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE C_Tax_ID=540107
;

-- 2025-07-31T13:17:27.725Z
UPDATE C_Tax SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2025-07-31 13:17:27.725000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE C_Tax_ID=540107
;

-- 2025-07-31T13:17:31.383Z
UPDATE C_Tax SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2025-07-31 13:17:31.383000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE C_Tax_ID=540108
;

-- 2025-07-31T13:18:53.770Z
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,SeqNo,SOPOType,Updated,UpdatedBy,ValidFrom,ValidTo) VALUES (1000000,1000000,101,TO_TIMESTAMP('2025-07-31 13:18:53.769000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1000010,540109,'Y','N','Y','N','N','N','N','N','Reduzierte MWSt 2.6%',0,0,'B',TO_TIMESTAMP('2025-07-31 13:18:53.769000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'2024-01-01'::timestamp without time zone,'9999-12-31'::timestamp without time zone)
;

-- 2025-07-31T13:18:53.775Z
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_Tax_ID=540109 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2025-07-31T13:18:53.776Z
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy 
, T_Credit_Acct
, T_Due_Acct
, T_Expense_Acct
, T_Liability_Acct
, T_PayDiscount_Exp_Acct
, T_PayDiscount_Rev_Acct
, T_Receivables_Acct
, T_Revenue_Acct
) SELECT 540109, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
, p.T_Credit_Acct
, p.T_Due_Acct
, p.T_Expense_Acct
, p.T_Liability_Acct
, NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
, NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
, p.T_Receivables_Acct
, NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
 FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
 AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540109)
;

-- 2025-07-31T13:19:04.901Z
UPDATE C_Tax SET To_Country_ID=107, TypeOfDestCountry='OUTSIDE_COUNTRY_AREA',Updated=TO_TIMESTAMP('2025-07-31 13:19:04.901000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE C_Tax_ID=540109
;

-- 2025-07-31T13:19:18.593Z
UPDATE C_Tax SET Rate=2.6,Updated=TO_TIMESTAMP('2025-07-31 13:19:18.593000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE C_Tax_ID=540109
;

-- 2025-07-31T13:28:29.826Z
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_Country_ID,Created,CreatedBy,C_TaxCategory_ID,C_Tax_ID,IsActive,IsDefault,IsDocumentLevel,IsSalesTax,IsSmallbusiness,IsSummary,IsTaxExempt,IsWholeTax,Name,Rate,SeqNo,SOPOType,Updated,UpdatedBy,ValidFrom,ValidTo) VALUES (1000000,1000000,101,TO_TIMESTAMP('2025-07-31 13:28:29.824000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1000010,540110,'Y','N','Y','N','N','N','N','N','Reduzierte MWSt 2.5%',0,0,'B',TO_TIMESTAMP('2025-07-31 13:28:29.824000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'2021-01-01'::timestamp without time zone,'2023-12-31'::timestamp without time zone)
;

-- 2025-07-31T13:28:29.828Z
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_Tax t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_Tax_ID=540110 AND NOT EXISTS (SELECT 1 FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- 2025-07-31T13:28:29.829Z
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy 
, T_Credit_Acct
, T_Due_Acct
, T_Expense_Acct
, T_Liability_Acct
, T_PayDiscount_Exp_Acct
, T_PayDiscount_Rev_Acct
, T_Receivables_Acct
, T_Revenue_Acct
) SELECT 540110, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100
, p.T_Credit_Acct
, p.T_Due_Acct
, p.T_Expense_Acct
, p.T_Liability_Acct
, NULL /* missing C_AcctSchema_Default.T_PayDiscount_Exp_Acct */
, NULL /* missing C_AcctSchema_Default.T_PayDiscount_Rev_Acct */
, p.T_Receivables_Acct
, NULL /* missing C_AcctSchema_Default.T_Revenue_Acct */
 FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000
 AND NOT EXISTS (SELECT 1 FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=540110)
;

-- 2025-07-31T13:28:36.951Z
UPDATE C_Tax SET SeqNo=1001,Updated=TO_TIMESTAMP('2025-07-31 13:28:36.951000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE C_Tax_ID=540110
;

-- 2025-07-31T13:28:39.642Z
UPDATE C_Tax SET Rate=2.5,Updated=TO_TIMESTAMP('2025-07-31 13:28:39.642000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE C_Tax_ID=540110
;

-- 2025-07-31T13:28:43.849Z
UPDATE C_Tax SET To_Country_ID=107, TypeOfDestCountry='OUTSIDE_COUNTRY_AREA',Updated=TO_TIMESTAMP('2025-07-31 13:28:43.849000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE C_Tax_ID=540110
;

-- 2025-07-31T13:28:52.352Z
UPDATE C_Tax SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2025-07-31 13:28:52.352000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE C_Tax_ID=540110
;

-- 2025-07-31T13:28:56.035Z
UPDATE C_Tax SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2025-07-31 13:28:56.035000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE C_Tax_ID=540109
;
