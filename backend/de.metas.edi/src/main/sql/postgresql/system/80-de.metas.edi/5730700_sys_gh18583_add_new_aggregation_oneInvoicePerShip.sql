-- 2024-08-02T06:47:18.727Z
INSERT INTO C_Aggregation (AD_Client_ID,AD_Org_ID,AD_Table_ID,C_Aggregation_ID,Created,CreatedBy,EntityType,IsActive,IsDefault,IsDefaultPO,IsDefaultSO,Name,Updated,UpdatedBy) VALUES (1000000,1000000,540270,540017,TO_TIMESTAMP('2024-08-02 06:47:18.722000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.invoicecandidate','Y','N','N','N','one-invoice-per-shipment',TO_TIMESTAMP('2024-08-02 06:47:18.722000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-08-02T06:47:22.211Z
UPDATE C_Aggregation SET AggregationUsageLevel='H',Updated=TO_TIMESTAMP('2024-08-02 06:47:22.211000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE C_Aggregation_ID=540017
;

-- 2024-08-02T06:47:32.888Z
INSERT INTO C_AggregationItem (AD_Client_ID,AD_Org_ID,C_Aggregation_ID,C_AggregationItem_ID,Created,CreatedBy,EntityType,Included_Aggregation_ID,IsActive,Type,Updated,UpdatedBy) VALUES (1000000,1000000,540017,540098,TO_TIMESTAMP('2024-08-02 06:47:32.885000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.invoicecandidate',1000000,'Y','INC',TO_TIMESTAMP('2024-08-02 06:47:32.885000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-08-02T06:47:41.178Z
INSERT INTO C_AggregationItem (AD_Client_ID,AD_Org_ID,C_Aggregation_ID,C_AggregationItem_ID,Created,CreatedBy,EntityType,IsActive,Type,Updated,UpdatedBy) VALUES (1000000,1000000,540017,540099,TO_TIMESTAMP('2024-08-02 06:47:41.177000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','ATR',TO_TIMESTAMP('2024-08-02 06:47:41.177000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-08-02T06:47:46.011Z
UPDATE C_AggregationItem SET C_Aggregation_Attribute_ID=540001, EntityType='de.metas.invoicecandidate',Updated=TO_TIMESTAMP('2024-08-02 06:47:46.011000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE C_AggregationItem_ID=540099
;


