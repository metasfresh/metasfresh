-- 2023-09-13T11:26:40.034781Z
UPDATE C_Aggregation SET IsDefault='N',Updated=TO_TIMESTAMP('2023-09-13 14:26:40.03','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_Aggregation_ID=540015
;

-- 2023-09-13T11:27:20.095161400Z
INSERT INTO C_Aggregation (AD_Client_ID,AD_Org_ID,AD_Table_ID,C_Aggregation_ID,Created,CreatedBy,EntityType,IsActive,IsDefault,IsDefaultPO,IsDefaultSO,Name,Updated,UpdatedBy) VALUES (1000000,1000000,540270,540016,TO_TIMESTAMP('2023-09-13 14:27:20.093','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.invoicecandidate','Y','Y','N','N','invoicing-agg-per-auction',TO_TIMESTAMP('2023-09-13 14:27:20.093','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-13T11:27:22.328387300Z
UPDATE C_Aggregation SET AggregationUsageLevel='H',Updated=TO_TIMESTAMP('2023-09-13 14:27:22.328','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_Aggregation_ID=540016
;

-- 2023-09-13T11:27:38.045129Z
INSERT INTO C_AggregationItem (AD_Client_ID,AD_Column_ID,AD_Org_ID,C_Aggregation_ID,C_AggregationItem_ID,Created,CreatedBy,EntityType,IsActive,Type,Updated,UpdatedBy) VALUES (1000000,587463,1000000,540016,540095,TO_TIMESTAMP('2023-09-13 14:27:38.044','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.invoicecandidate','Y','COL',TO_TIMESTAMP('2023-09-13 14:27:38.044','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-13T11:27:54.251929700Z
INSERT INTO C_AggregationItem (AD_Client_ID,AD_Org_ID,C_Aggregation_ID,C_AggregationItem_ID,Created,CreatedBy,EntityType,Included_Aggregation_ID,IsActive,Type,Updated,UpdatedBy) VALUES (1000000,1000000,540016,540096,TO_TIMESTAMP('2023-09-13 14:27:54.249','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.invoicecandidate',540015,'Y','INC',TO_TIMESTAMP('2023-09-13 14:27:54.249','YYYY-MM-DD HH24:MI:SS.US'),100)
;

