
-- 2022-10-10T14:21:54.038Z
INSERT INTO C_Aggregation (AD_Client_ID,AD_Org_ID,AD_Table_ID,C_Aggregation_ID,Created,CreatedBy,EntityType,IsActive,IsDefault,IsDefaultPO,IsDefaultSO,Name,Updated,UpdatedBy) VALUES (1000000,1000000,540270,540009,TO_TIMESTAMP('2022-10-10 17:21:54.025','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.invoicecandidate','Y','N','N','N','invoicing-agg-per-issue',TO_TIMESTAMP('2022-10-10 17:21:54.025','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-10-10T14:22:00.225Z
UPDATE C_Aggregation SET AggregationUsageLevel='H',Updated=TO_TIMESTAMP('2022-10-10 17:22:00.225','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_Aggregation_ID=540009
;

-- 2022-10-10T14:22:05Z
INSERT INTO C_AggregationItem (AD_Client_ID,AD_Column_ID,AD_Org_ID,C_Aggregation_ID,C_AggregationItem_ID,Created,CreatedBy,EntityType,IsActive,Type,Updated,UpdatedBy) VALUES (1000000,551072,1000000,540009,540060,TO_TIMESTAMP('2022-10-10 17:22:04.999','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.invoicecandidate','Y','COL',TO_TIMESTAMP('2022-10-10 17:22:04.999','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-10-10T14:22:16.553Z
INSERT INTO C_AggregationItem (AD_Client_ID,AD_Column_ID,AD_Org_ID,C_Aggregation_ID,C_AggregationItem_ID,Created,CreatedBy,EntityType,IsActive,Type,Updated,UpdatedBy) VALUES (1000000,572537,1000000,540009,540061,TO_TIMESTAMP('2022-10-10 17:22:16.551','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.invoicecandidate','Y','COL',TO_TIMESTAMP('2022-10-10 17:22:16.551','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-10-10T14:22:24.439Z
INSERT INTO C_AggregationItem (AD_Client_ID,AD_Org_ID,C_Aggregation_ID,C_AggregationItem_ID,Created,CreatedBy,EntityType,Included_Aggregation_ID,IsActive,Type,Updated,UpdatedBy) VALUES (1000000,1000000,540009,540062,TO_TIMESTAMP('2022-10-10 17:22:24.437','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.invoicecandidate',1000000,'Y','INC',TO_TIMESTAMP('2022-10-10 17:22:24.437','YYYY-MM-DD HH24:MI:SS.US'),100)
;


-- 2022-10-11T07:16:23.145Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541514,'S',TO_TIMESTAMP('2022-10-11 10:16:22.9','YYYY-MM-DD HH24:MI:SS.US'),100,'C_Aggregation_ID for invoicing-agg-per-issue','D','Y','InvoiceAggregationFactory_IssueAggregationId',TO_TIMESTAMP('2022-10-11 10:16:22.9','YYYY-MM-DD HH24:MI:SS.US'),100,'540009')
;


-- 2022-10-12T08:00:50.287Z
INSERT INTO C_Aggregation (AD_Client_ID,AD_Org_ID,AD_Table_ID,C_Aggregation_ID,Created,CreatedBy,EntityType,IsActive,IsDefault,IsDefaultPO,IsDefaultSO,Name,Updated,UpdatedBy) VALUES (1000000,1000000,540270,540010,TO_TIMESTAMP('2022-10-12 11:00:50.267','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.invoicecandidate','Y','N','N','N','Invoice Line Per Issue',TO_TIMESTAMP('2022-10-12 11:00:50.267','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-10-12T08:00:52.067Z
UPDATE C_Aggregation SET AggregationUsageLevel='L',Updated=TO_TIMESTAMP('2022-10-12 11:00:52.067','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_Aggregation_ID=540010
;

-- 2022-10-12T08:01:05.467Z
INSERT INTO C_AggregationItem (AD_Client_ID,AD_Column_ID,AD_Org_ID,C_Aggregation_ID,C_AggregationItem_ID,Created,CreatedBy,EntityType,IsActive,Type,Updated,UpdatedBy) VALUES (1000000,546178,1000000,540010,540063,TO_TIMESTAMP('2022-10-12 11:01:05.464','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.invoicecandidate','Y','COL',TO_TIMESTAMP('2022-10-12 11:01:05.464','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-10-12T08:01:15.220Z
INSERT INTO C_AggregationItem (AD_Client_ID,AD_Org_ID,C_Aggregation_ID,C_AggregationItem_ID,Created,CreatedBy,EntityType,Included_Aggregation_ID,IsActive,Type,Updated,UpdatedBy) VALUES (1000000,1000000,540010,540064,TO_TIMESTAMP('2022-10-12 11:01:15.218','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.invoicecandidate',540003,'Y','INC',TO_TIMESTAMP('2022-10-12 11:01:15.218','YYYY-MM-DD HH24:MI:SS.US'),100)
;


-- 2022-10-12T08:03:34.753Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541518,'S',TO_TIMESTAMP('2022-10-12 11:03:34.514','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','InvoiceLineAggregationFactory_IssueAggregationId',TO_TIMESTAMP('2022-10-12 11:03:34.514','YYYY-MM-DD HH24:MI:SS.US'),100,'540010')
;
