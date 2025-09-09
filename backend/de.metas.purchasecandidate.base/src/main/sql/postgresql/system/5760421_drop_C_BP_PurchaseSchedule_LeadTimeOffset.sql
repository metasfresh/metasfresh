-- Run mode: SWING_CLIENT

SELECT backup_table('C_BP_PurchaseSchedule')
;

-- UI Element: Geschäftspartner Pharma(540409,U) -> Bestell Schema(541103,de.metas.purchasecandidate) -> main -> 10 -> default.Lead time (days)
-- Column: C_BP_PurchaseSchedule.LeadTimeOffset
-- 2025-07-09T08:09:06.065Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=552339
;

-- 2025-07-09T08:09:06.067Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=565044
;

-- Field: Geschäftspartner Pharma(540409,U) -> Bestell Schema(541103,de.metas.purchasecandidate) -> Lead Time Offset
-- Column: C_BP_PurchaseSchedule.LeadTimeOffset
-- 2025-07-09T08:09:06.072Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=565044
;

-- 2025-07-09T08:09:06.078Z
DELETE FROM AD_Field WHERE AD_Field_ID=565044
;

-- 2025-07-09T08:09:06.080Z
/* DDL */ SELECT public.db_alter_table('C_BP_PurchaseSchedule','ALTER TABLE C_BP_PurchaseSchedule DROP COLUMN IF EXISTS LeadTimeOffset')
;

-- Column: C_BP_PurchaseSchedule.LeadTimeOffset
-- 2025-07-09T08:09:06.108Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=560223
;

-- 2025-07-09T08:09:06.113Z
DELETE FROM AD_Column WHERE AD_Column_ID=560223
;

