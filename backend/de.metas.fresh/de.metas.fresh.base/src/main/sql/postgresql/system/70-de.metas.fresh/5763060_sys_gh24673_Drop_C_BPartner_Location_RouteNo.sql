-- Run mode: SWING_CLIENT

-- Column: C_BPartner_Location.RouteNo
-- 2025-08-18T17:24:12.703Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=590572
;

-- 2025-08-18T17:24:13.038Z
DELETE FROM AD_Column WHERE AD_Column_ID=590572
;

ALTER TABLE public.c_bpartner_location
    DROP COLUMN RouteNo
;
