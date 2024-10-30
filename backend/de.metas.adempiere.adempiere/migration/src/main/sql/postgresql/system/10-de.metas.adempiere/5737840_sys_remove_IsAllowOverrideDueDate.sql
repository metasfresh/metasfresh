-- Run mode: SWING_CLIENT

-- UI Element: Zahlungsbedingung(141,D) -> Zahlungsbedingung(184,D) -> main -> 20 -> flags.Allow override due date
-- Column: C_PaymentTerm.IsAllowOverrideDueDate
-- 2024-10-28T15:46:56.783Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=615725
;

-- 2024-10-28T15:46:56.793Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712376
;

-- Field: Zahlungsbedingung(141,D) -> Zahlungsbedingung(184,D) -> Überschreiben des Fälligkeitsdatums zulassen
-- Column: C_PaymentTerm.IsAllowOverrideDueDate
-- 2024-10-28T15:46:56.797Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=712376
;

-- 2024-10-28T15:46:56.802Z
DELETE FROM AD_Field WHERE AD_Field_ID=712376
;

-- 2024-10-28T15:46:56.834Z
/* DDL */ SELECT public.db_alter_table('C_PaymentTerm','ALTER TABLE C_PaymentTerm DROP COLUMN IF EXISTS IsAllowOverrideDueDate')
;

-- Column: C_PaymentTerm.IsAllowOverrideDueDate
-- 2024-10-28T15:46:57.056Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=586044
;

-- 2024-10-28T15:46:57.061Z
DELETE FROM AD_Column WHERE AD_Column_ID=586044
;

