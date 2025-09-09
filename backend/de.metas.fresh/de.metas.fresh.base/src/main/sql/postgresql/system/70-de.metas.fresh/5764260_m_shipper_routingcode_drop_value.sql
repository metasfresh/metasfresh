-- Run mode: SWING_CLIENT

-- UI Element: Lieferweg(142,D) -> Leitcode(548360,D) -> Routingcode -> 10 -> Routingcode.Suchschlüssel
-- Column: M_Shipper_RoutingCode.Value
-- 2025-08-28T10:43:10.225Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=636144
;

-- 2025-08-28T10:43:26.913Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=751867
;

-- Field: Lieferweg(142,D) -> Leitcode(548360,D) -> Suchschlüssel
-- Column: M_Shipper_RoutingCode.Value
-- 2025-08-28T10:43:26.918Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=751867
;

-- 2025-08-28T10:43:26.923Z
DELETE FROM AD_Field WHERE AD_Field_ID=751867
;

-- Column: M_Shipper_RoutingCode.Value
-- 2025-08-28T10:44:11.260Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=590616
;

-- 2025-08-28T10:44:11.265Z
DELETE FROM AD_Column WHERE AD_Column_ID=590616
;

-- 2025-08-28T10:44:11.265Z
/* DDL */ SELECT public.db_alter_table('M_Shipper_RoutingCode','ALTER TABLE public.M_Shipper_RoutingCode DROP COLUMN Value')
;
