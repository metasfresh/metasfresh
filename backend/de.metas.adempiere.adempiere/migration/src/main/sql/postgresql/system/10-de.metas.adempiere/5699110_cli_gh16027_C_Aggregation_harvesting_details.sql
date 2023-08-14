-- 2023-08-11T07:30:10.849348800Z
INSERT INTO C_Aggregation (AD_Client_ID,AD_Org_ID,AD_Table_ID,C_Aggregation_ID,Created,CreatedBy,EntityType,IsActive,IsDefault,IsDefaultPO,IsDefaultSO,Name,Updated,UpdatedBy) VALUES (1000000,1000000,540270,540015,TO_TIMESTAMP('2023-08-11 10:30:10.847','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.invoicecandidate','Y','N','N','N','invoicing-agg-per-hervesting-details',TO_TIMESTAMP('2023-08-11 10:30:10.847','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-11T07:30:12.555367400Z
UPDATE C_Aggregation SET AggregationUsageLevel='H',Updated=TO_TIMESTAMP('2023-08-11 10:30:12.555','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_Aggregation_ID=540015
;

-- 2023-08-11T07:30:25.083280500Z
INSERT INTO C_AggregationItem (AD_Client_ID,AD_Column_ID,AD_Org_ID,C_Aggregation_ID,C_AggregationItem_ID,Created,CreatedBy,EntityType,IsActive,Type,Updated,UpdatedBy) VALUES (1000000,587248,1000000,540015,540092,TO_TIMESTAMP('2023-08-11 10:30:25.082','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.invoicecandidate','Y','COL',TO_TIMESTAMP('2023-08-11 10:30:25.082','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-11T07:30:35.787718600Z
INSERT INTO C_AggregationItem (AD_Client_ID,AD_Column_ID,AD_Org_ID,C_Aggregation_ID,C_AggregationItem_ID,Created,CreatedBy,EntityType,IsActive,Type,Updated,UpdatedBy) VALUES (1000000,587249,1000000,540015,540093,TO_TIMESTAMP('2023-08-11 10:30:35.787','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.invoicecandidate','Y','COL',TO_TIMESTAMP('2023-08-11 10:30:35.787','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-11T07:30:42.782262400Z
INSERT INTO C_AggregationItem (AD_Client_ID,AD_Org_ID,C_Aggregation_ID,C_AggregationItem_ID,Created,CreatedBy,EntityType,Included_Aggregation_ID,IsActive,Type,Updated,UpdatedBy) VALUES (1000000,1000000,540015,540094,TO_TIMESTAMP('2023-08-11 10:30:42.779','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.invoicecandidate',540014,'Y','INC',TO_TIMESTAMP('2023-08-11 10:30:42.779','YYYY-MM-DD HH24:MI:SS.US'),100)
;

