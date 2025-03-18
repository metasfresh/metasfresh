-- Run mode: SWING_CLIENT

-- UI Element: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> main -> 20 -> additional.Handelsfranken
-- Column: ModCntr_Settings.TradeMargin
-- 2025-03-18T10:48:50.519Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=626192
;

-- 2025-03-18T10:48:50.528Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731908
;

-- Field: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> Handelsfranken
-- Column: ModCntr_Settings.TradeMargin
-- 2025-03-18T10:48:50.535Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=731908
;

-- 2025-03-18T10:48:50.537Z
DELETE FROM AD_Field WHERE AD_Field_ID=731908
;

-- 2025-03-18T10:48:50.566Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Settings','ALTER TABLE ModCntr_Settings DROP COLUMN IF EXISTS TradeMargin')
;

-- Column: ModCntr_Settings.TradeMargin
-- 2025-03-18T10:48:50.738Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=589307
;

-- 2025-03-18T10:48:50.740Z
DELETE FROM AD_Column WHERE AD_Column_ID=589307
;

