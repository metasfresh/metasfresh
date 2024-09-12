-- Run mode: WEBUI



-- 2024-09-10T08:42:59.321Z
INSERT INTO C_ElementValue (AccountSign,AccountType,AD_Client_ID,AD_Org_ID,C_Element_ID,C_ElementValue_ID,Created,CreatedBy,IsActive,IsAutoTaxAccount,
IsBankAccount,IsDocControlled,IsForeignCurrency,IsMandatoryActivity,IsSummary,Name,PostActual,PostBudget,PostEncumbrance,PostStatistical,SeqNo,ShowIntCurrency,
Updated,UpdatedBy,Value) VALUES ('N','E',1000000,1000000,1000000,543201,TO_TIMESTAMP('2024-09-10 11:42:59.315','YYYY-MM-DD HH24:MI:SS.US'),100,
'Y','N','N','N','N','N','N','CashRounding_ACCT','Y','Y','Y','Y',999,'N',TO_TIMESTAMP('2024-09-10 11:42:59.315','YYYY-MM-DD HH24:MI:SS.US'),100,'90084')
;

-- 2024-09-10T08:42:59.328Z
INSERT INTO C_ElementValue_Trl (AD_Language,C_ElementValue_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_ElementValue_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_ElementValue t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_ElementValue_ID=543201 AND NOT EXISTS (SELECT 1 FROM C_ElementValue_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_ElementValue_ID=t.C_ElementValue_ID)
;

-- 2024-09-10T08:42:59.386Z
INSERT INTO AD_TreeNode (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) 
SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 543201, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=1000000 
AND t.IsActive='Y' AND t.AD_Tree_ID=1000020 AND NOT EXISTS (SELECT * FROM AD_TreeNode e WHERE e.AD_Tree_ID=t.AD_Tree_ID 
AND Node_ID=543201)
;

-- 2024-09-10T08:43:08.392Z
UPDATE C_ElementValue SET Description='Cash Rounding Account',Updated=TO_TIMESTAMP('2024-09-10 11:43:08.392','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_ElementValue_ID=543201
;

-- 2024-09-10T08:43:08.396Z
UPDATE C_ElementValue_Trl trl SET Description='Cash Rounding Account' WHERE C_ElementValue_ID=543201 AND (IsTranslated='N' OR AD_Language='de_DE')
;

-- 2024-09-10T08:43:13.463Z
UPDATE C_ElementValue SET AccountType='A',Updated=TO_TIMESTAMP('2024-09-10 11:43:13.463','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_ElementValue_ID=543201
;

-- 2024-09-10T12:16:25.116Z
UPDATE C_ElementValue SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2024-09-10 15:16:25.116','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_ElementValue_ID=543201
;



-- -- 2024-09-10T13:06:31.535Z
-- UPDATE C_ElementValue SET C_Element_ID=540231,Updated=TO_TIMESTAMP('2024-09-10 16:06:31.535','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_ElementValue_ID=543201
-- ;



-- Run mode: WEBUI

-- 2024-09-12T13:26:02.867Z
UPDATE C_ElementValue SET C_Element_ID=1000000,Updated=TO_TIMESTAMP('2024-09-12 16:26:02.867','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=571128 WHERE C_ElementValue_ID=543201
;

-- 2024-09-10T13:06:31.692Z
INSERT INTO C_ValidCombination (Account_ID,AD_Client_ID,AD_Org_ID,C_AcctSchema_ID,Combination,Created,CreatedBy,C_ValidCombination_ID,Description,IsActive,
IsFullyQualified,Updated,UpdatedBy,UserElementString1,UserElementString2,UserElementString3,UserElementString4,UserElementString5,UserElementString6,UserElementString7)
 VALUES (543201,1000000,0,1000000,'90084',TO_TIMESTAMP('2024-09-10 16:06:31.543','YYYY-MM-DD HH24:MI:SS.US'),100,540610,'CashRounding_ACCT','Y',
 'Y',TO_TIMESTAMP('2024-09-10 16:06:31.543','YYYY-MM-DD HH24:MI:SS.US'),100,'null','null','null','null','null','null','null')
;


-- -- 2024-09-10T13:07:40.252Z
-- INSERT INTO C_ValidCombination (Account_ID,AD_Client_ID,AD_Org_ID,C_AcctSchema_ID,C_Harvesting_Calendar_ID,Combination,Created,CreatedBy,C_ValidCombination_ID,Description,Harvesting_Year_ID,IsActive,IsFullyQualified,Updated,UpdatedBy) VALUES (543201,1000000,0,1000000,1000000,'90084',TO_TIMESTAMP('2024-09-10 16:07:40.246','YYYY-MM-DD HH24:MI:SS.US'),100,540609,'CashRounding_ACCT',540031,'Y','Y',TO_TIMESTAMP('2024-09-10 16:07:40.246','YYYY-MM-DD HH24:MI:SS.US'),100)
-- ;

-- 2024-09-10T13:13:40.395Z
UPDATE C_AcctSchema_GL SET AD_Org_ID=0, CashRounding_Acct=540610,Updated=TO_TIMESTAMP('2024-09-10 16:13:40.395','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_AcctSchema_GL_ID=1000000
;



