-- Run mode: SWING_CLIENT

-- Column: RV_DATEV_Export_Fact_Acct_Invoice.DueDate
-- 2024-11-20T15:39:18.467Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=559217
;

-- 2024-11-20T15:39:18.483Z
DELETE FROM AD_Column WHERE AD_Column_ID=559217
;

-- 2024-11-20T15:40:36.193Z
DELETE FROM DATEV_ExportFormatColumn WHERE DATEV_ExportFormatColumn_ID=540003
;

-- UI Element: Buchungen Export(540413,de.metas.datev) -> Lines(541037,de.metas.datev) -> main -> 10 -> default.Datum Fälligkeit
-- Column: DATEV_ExportLine.DueDate
-- 2024-11-20T15:40:55.175Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=551046
;

-- 2024-11-20T15:40:55.180Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=562787
;

-- Field: Buchungen Export(540413,de.metas.datev) -> Lines(541037,de.metas.datev) -> Datum Fälligkeit
-- Column: DATEV_ExportLine.DueDate
-- 2024-11-20T15:40:55.185Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=562787
;

-- 2024-11-20T15:40:55.192Z
DELETE FROM AD_Field WHERE AD_Field_ID=562787
;

-- 2024-11-20T15:40:55.198Z
/* DDL */ SELECT public.db_alter_table('DATEV_ExportLine','ALTER TABLE DATEV_ExportLine DROP COLUMN IF EXISTS DueDate')
;

-- Column: DATEV_ExportLine.DueDate
-- 2024-11-20T15:40:55.223Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=559219
;

-- 2024-11-20T15:40:55.229Z
DELETE FROM AD_Column WHERE AD_Column_ID=559219
;

