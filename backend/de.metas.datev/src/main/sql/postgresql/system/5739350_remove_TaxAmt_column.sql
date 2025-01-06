-- Column: RV_DATEV_Export_Fact_Acct_Invoice.TaxAmt
-- Column: RV_DATEV_Export_Fact_Acct_Invoice.TaxAmt
-- 2024-11-18T07:56:43.800Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585324
;

-- 2024-11-18T07:56:44.065Z
DELETE FROM AD_Column WHERE AD_Column_ID=585324
;

-- UI Element: Buchungen Export_OLD -> Lines.Steuerbetrag
-- Column: DATEV_ExportLine.TaxAmt
-- UI Element: Buchungen Export_OLD(540413,de.metas.datev) -> Lines(541037,de.metas.datev) -> main -> 10 -> default.Steuerbetrag
-- Column: DATEV_ExportLine.TaxAmt
-- 2024-11-18T07:57:34.053Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=614517
;

-- 2024-11-18T07:57:34.148Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=709966
;

-- Field: Buchungen Export_OLD -> Lines -> Steuerbetrag
-- Column: DATEV_ExportLine.TaxAmt
-- Field: Buchungen Export_OLD(540413,de.metas.datev) -> Lines(541037,de.metas.datev) -> Steuerbetrag
-- Column: DATEV_ExportLine.TaxAmt
-- 2024-11-18T07:57:34.395Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=709966
;

-- 2024-11-18T07:57:34.654Z
DELETE FROM AD_Field WHERE AD_Field_ID=709966
;

-- UI Element: Buchungen Export -> Lines.Steuerbetrag
-- Column: DATEV_ExportLine.TaxAmt
-- UI Element: Buchungen Export(541616,de.metas.datev) -> Lines(546636,de.metas.datev) -> main -> 10 -> default.Steuerbetrag
-- Column: DATEV_ExportLine.TaxAmt
-- 2024-11-18T07:57:35.484Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=614519
;

-- 2024-11-18T07:57:35.570Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=709968
;

-- Field: Buchungen Export -> Lines -> Steuerbetrag
-- Column: DATEV_ExportLine.TaxAmt
-- Field: Buchungen Export(541616,de.metas.datev) -> Lines(546636,de.metas.datev) -> Steuerbetrag
-- Column: DATEV_ExportLine.TaxAmt
-- 2024-11-18T07:57:35.792Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=709968
;

-- 2024-11-18T07:57:36.046Z
DELETE FROM AD_Field WHERE AD_Field_ID=709968
;

-- 2024-11-18T07:57:36.135Z
/* DDL */ SELECT public.db_alter_table('DATEV_ExportLine','ALTER TABLE DATEV_ExportLine DROP COLUMN IF EXISTS TaxAmt')
;

-- Column: DATEV_ExportLine.TaxAmt
-- Column: DATEV_ExportLine.TaxAmt
-- 2024-11-18T07:57:36.423Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585326
;

-- 2024-11-18T07:57:36.682Z
DELETE FROM AD_Column WHERE AD_Column_ID=585326
;

