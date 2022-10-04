
-- 2022-09-22T09:03:42.741Z
INSERT INTO C_AggregationItem (AD_Client_ID,AD_Column_ID,AD_Org_ID,C_Aggregation_ID,C_AggregationItem_ID,Created,CreatedBy,EntityType,IsActive,Type,Updated,UpdatedBy) VALUES (1000000,584388,1000000,1000000,540056,TO_TIMESTAMP('2022-09-22 12:03:42','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoicecandidate','Y','COL',TO_TIMESTAMP('2022-09-22 12:03:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-23T09:59:54.840Z
UPDATE C_AggregationItem SET Description='If inactive, invoice candidates with difference sections can end up in the same invoice, but the invoice''s section will be null.',Updated=TO_TIMESTAMP('2022-09-23 12:59:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_AggregationItem_ID=540056
;

