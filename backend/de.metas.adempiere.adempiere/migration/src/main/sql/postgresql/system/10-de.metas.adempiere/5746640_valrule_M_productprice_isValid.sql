-- Run mode: WEBUI

-- Name: M_ProductPrice with maxSeq is valid
-- 2025-02-14T08:26:37.287Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540700,TO_TIMESTAMP('2025-02-14 08:26:37.282000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','M_ProductPrice with maxSeq is valid','S',TO_TIMESTAMP('2025-02-14 08:26:37.282000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Name: M_ProductPrice with maxSeq is valid
-- 2025-02-14T16:42:29.332Z
UPDATE AD_Val_Rule SET Code='m_hu_pi_item_product_id IS NOT NULL OR isattributedependant = ''Y'' OR isinvalidprice = ''N''',Updated=TO_TIMESTAMP('2025-02-14 16:42:29.332000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Val_Rule_ID=540700
;

-- Name: M_ProductPrice is valid
-- 2025-02-14T16:43:12.996Z
UPDATE AD_Val_Rule SET Name='M_ProductPrice is valid',Updated=TO_TIMESTAMP('2025-02-14 16:43:12.996000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Val_Rule_ID=540700
;

