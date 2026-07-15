-- Run mode: SWING_CLIENT

SELECT backup_table('I_BPartner')
;

-- 2025-07-09T08:30:08.503Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=564403
;

-- Field: Import Geschäftspartner(172,D) -> Import - Geschäftspartner(441,D) -> Lead Time Offset
-- Column: I_BPartner.LeadTimeOffset
-- 2025-07-09T08:30:08.507Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=564403
;

-- 2025-07-09T08:30:08.515Z
DELETE FROM AD_Field WHERE AD_Field_ID=564403
;

-- 2025-07-09T08:30:08.517Z
/* DDL */ SELECT public.db_alter_table('I_BPartner','ALTER TABLE I_BPartner DROP COLUMN IF EXISTS LeadTimeOffset')
;

-- Column: I_BPartner.LeadTimeOffset
-- 2025-07-09T08:30:08.535Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=560207
;

-- 2025-07-09T08:30:08.540Z
DELETE FROM AD_Column WHERE AD_Column_ID=560207
;

