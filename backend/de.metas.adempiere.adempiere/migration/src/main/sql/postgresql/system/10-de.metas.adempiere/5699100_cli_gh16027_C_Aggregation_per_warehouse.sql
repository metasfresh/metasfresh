-- 2023-08-11T07:28:35.761772900Z
INSERT INTO C_Aggregation (AD_Client_ID,AD_Org_ID,AD_Table_ID,C_Aggregation_ID,Created,CreatedBy,EntityType,IsActive,IsDefault,IsDefaultPO,IsDefaultSO,Name,Updated,UpdatedBy) VALUES (1000000,1000000,540270,540014,TO_TIMESTAMP('2023-08-11 10:28:35.759','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.invoicecandidate','Y','N','N','N','invoicing-agg-per-warehouse',TO_TIMESTAMP('2023-08-11 10:28:35.759','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-11T07:28:37.583798200Z
UPDATE C_Aggregation SET AggregationUsageLevel='H',Updated=TO_TIMESTAMP('2023-08-11 10:28:37.583','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_Aggregation_ID=540014
;

-- 2023-08-11T07:28:44.437367800Z
INSERT INTO C_AggregationItem (AD_Client_ID,AD_Column_ID,AD_Org_ID,C_Aggregation_ID,C_AggregationItem_ID,Created,CreatedBy,EntityType,IsActive,Type,Updated,UpdatedBy) VALUES (1000000,587247,1000000,540014,540090,TO_TIMESTAMP('2023-08-11 10:28:44.436','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.invoicecandidate','Y','COL',TO_TIMESTAMP('2023-08-11 10:28:44.436','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-11T07:28:54.255315200Z
INSERT INTO C_AggregationItem (AD_Client_ID,AD_Org_ID,C_Aggregation_ID,C_AggregationItem_ID,Created,CreatedBy,EntityType,Included_Aggregation_ID,IsActive,Type,Updated,UpdatedBy) VALUES (1000000,1000000,540014,540091,TO_TIMESTAMP('2023-08-11 10:28:54.252','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.invoicecandidate',1000000,'Y','INC',TO_TIMESTAMP('2023-08-11 10:28:54.252','YYYY-MM-DD HH24:MI:SS.US'),100)
;

