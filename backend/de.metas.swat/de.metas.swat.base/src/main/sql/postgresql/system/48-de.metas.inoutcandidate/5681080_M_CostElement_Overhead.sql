-- Name: M_CostElement Overhead
-- 2023-03-08T17:21:05.270Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540627,'M_CostElement.CostElementType=''O''',TO_TIMESTAMP('2023-03-08 19:21:04','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','M_CostElement Overhead','S',TO_TIMESTAMP('2023-03-08 19:21:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: C_Cost_Type.M_CostElement_ID
-- 2023-03-08T17:21:30.049Z
UPDATE AD_Column SET AD_Val_Rule_ID=540627,Updated=TO_TIMESTAMP('2023-03-08 19:21:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586067
;

