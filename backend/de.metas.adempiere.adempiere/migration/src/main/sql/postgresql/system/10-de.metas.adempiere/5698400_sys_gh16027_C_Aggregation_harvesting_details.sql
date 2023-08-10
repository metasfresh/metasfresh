-- 2023-08-09T06:25:53.610652900Z
INSERT INTO C_Aggregation (AD_Client_ID,AD_Org_ID,AD_Table_ID,C_Aggregation_ID,Created,CreatedBy,EntityType,IsActive,IsDefault,IsDefaultPO,IsDefaultSO,Name,Updated,UpdatedBy) VALUES (1000000,1000000,540270,540011,TO_TIMESTAMP('2023-08-09 09:25:53.594','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.invoicecandidate','Y','N','N','N','invoicing-agg-per-hervesting-details',TO_TIMESTAMP('2023-08-09 09:25:53.594','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-09T06:25:56.608342700Z
UPDATE C_Aggregation SET AggregationUsageLevel='H',Updated=TO_TIMESTAMP('2023-08-09 09:25:56.608','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_Aggregation_ID=540011
;

-- 2023-08-09T06:26:05.813103400Z
INSERT INTO C_AggregationItem (AD_Client_ID,AD_Column_ID,AD_Org_ID,C_Aggregation_ID,C_AggregationItem_ID,Created,CreatedBy,EntityType,IsActive,Type,Updated,UpdatedBy) VALUES (1000000,587248,1000000,540011,540077,TO_TIMESTAMP('2023-08-09 09:26:05.799','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.invoicecandidate','Y','COL',TO_TIMESTAMP('2023-08-09 09:26:05.799','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-09T06:26:19.245050100Z
INSERT INTO C_AggregationItem (AD_Client_ID,AD_Column_ID,AD_Org_ID,C_Aggregation_ID,C_AggregationItem_ID,Created,CreatedBy,EntityType,IsActive,Type,Updated,UpdatedBy) VALUES (1000000,587249,1000000,540011,540078,TO_TIMESTAMP('2023-08-09 09:26:19.244','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.invoicecandidate','Y','COL',TO_TIMESTAMP('2023-08-09 09:26:19.244','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-09T06:26:28.206420800Z
INSERT INTO C_AggregationItem (AD_Client_ID,AD_Column_ID,AD_Org_ID,C_Aggregation_ID,C_AggregationItem_ID,Created,CreatedBy,EntityType,IsActive,Type,Updated,UpdatedBy) VALUES (1000000,587247,1000000,540011,540079,TO_TIMESTAMP('2023-08-09 09:26:28.206','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.invoicecandidate','Y','COL',TO_TIMESTAMP('2023-08-09 09:26:28.206','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-09T06:26:43.178668800Z
INSERT INTO C_AggregationItem (AD_Client_ID,AD_Org_ID,C_Aggregation_ID,C_AggregationItem_ID,Created,CreatedBy,EntityType,Included_Aggregation_ID,IsActive,Type,Updated,UpdatedBy) VALUES (1000000,1000000,540011,540080,TO_TIMESTAMP('2023-08-09 09:26:43.174','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.invoicecandidate',1000000,'Y','INC',TO_TIMESTAMP('2023-08-09 09:26:43.174','YYYY-MM-DD HH24:MI:SS.US'),100)
;

