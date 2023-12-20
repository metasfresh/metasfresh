-- 2023-02-15T14:19:57.918Z
INSERT INTO M_CostElement (AD_Client_ID,AD_Org_ID,CostElementType,Created,CreatedBy,IsActive,IsCalculated,M_CostElement_ID,Name,Updated,UpdatedBy,CostingMethod)
VALUES (1000000,0,'M',TO_TIMESTAMP('2023-02-15 15:19:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',540002,'Moving Average Invoice',TO_TIMESTAMP('2023-02-15 15:19:57','YYYY-MM-DD HH24:MI:SS'),100,'M')
;

-- 2023-02-15T14:20:05.868Z
UPDATE M_CostElement SET AD_Org_ID=0, CostingMethod='M',Updated=TO_TIMESTAMP('2023-02-15 15:20:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_CostElement_ID=540002
;