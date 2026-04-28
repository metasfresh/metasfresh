-- 19.01.2017 19:00
-- URL zum Konzept
INSERT INTO DIM_Dimension_Spec (AD_Client_ID,AD_Org_ID,Created,CreatedBy,DIM_Dimension_Spec_ID,DIM_Dimension_Type_ID,IsActive,IsIncludeEmpty,Name,Updated,UpdatedBy) 
VALUES (0,0,TO_TIMESTAMP('2017-01-19 19:00:28','YYYY-MM-DD HH24:MI:SS'),100,540007,540000,'Y','N','DIM_Barcode_Attributes',TO_TIMESTAMP('2017-01-19 19:00:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.01.2017 10:18
-- URL zum Konzept
UPDATE DIM_Dimension_Spec SET InternalName='DIM_Barcode_Attributes',Updated=TO_TIMESTAMP('2017-01-26 10:18:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE DIM_Dimension_Spec_ID=540007
;

