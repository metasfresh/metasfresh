-- Run mode: WEBUI

-- Change the aggregation item to use StorageAttributesKey instead of M_AttributeSetInstance_ID
-- Why? because storage attributes may be identical for different ASIs

-- 2026-03-25T17:29:26.830Z
UPDATE C_AggregationItem
SET AD_Column_ID=592312, Updated=TO_TIMESTAMP('2026-03-25 17:29:26.827000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE C_AggregationItem_ID = 540124
;

