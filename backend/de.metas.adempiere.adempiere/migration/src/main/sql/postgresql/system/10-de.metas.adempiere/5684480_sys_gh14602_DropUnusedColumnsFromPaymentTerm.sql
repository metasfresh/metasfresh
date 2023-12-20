--IsDueFixed
delete from exp_formatline where ad_column_id=3006;

--FixMonthCutoff
delete from exp_formatline where ad_column_id=3007;

--FixMonthDay
delete from exp_formatline where ad_column_id=3008;

--FixMonthOffset
delete from exp_formatline where ad_column_id=3009;

-- 2023-04-06T15:28:27.306Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=2108
;

-- Field: Zahlungsbedingung(141,D) -> Zahlungsbedingung(184,D) -> Fester Fälligkeitstermin
-- Column: C_PaymentTerm.IsDueFixed
-- 2023-04-06T15:28:27.311Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=2108
;

-- 2023-04-06T15:28:27.317Z
DELETE FROM AD_Field WHERE AD_Field_ID=2108
;

-- 2023-04-06T15:28:27.319Z
/* DDL */ SELECT public.db_alter_table('C_PaymentTerm','ALTER TABLE C_PaymentTerm DROP COLUMN IF EXISTS IsDueFixed')
;

-- Column: C_PaymentTerm.IsDueFixed
-- 2023-04-06T15:28:27.412Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=3006
;

-- 2023-04-06T15:28:27.416Z
DELETE FROM AD_Column WHERE AD_Column_ID=3006
;

-- 2023-04-06T15:29:34.205Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=2105
;

-- Field: Zahlungsbedingung(141,D) -> Zahlungsbedingung(184,D) -> Fix month cutoff
-- Column: C_PaymentTerm.FixMonthCutoff
-- 2023-04-06T15:29:34.209Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=2105
;

-- 2023-04-06T15:29:34.215Z
DELETE FROM AD_Field WHERE AD_Field_ID=2105
;

-- 2023-04-06T15:29:34.218Z
/* DDL */ SELECT public.db_alter_table('C_PaymentTerm','ALTER TABLE C_PaymentTerm DROP COLUMN IF EXISTS FixMonthCutoff')
;

-- Column: C_PaymentTerm.FixMonthCutoff
-- 2023-04-06T15:29:34.303Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=3007
;

-- 2023-04-06T15:29:34.307Z
DELETE FROM AD_Column WHERE AD_Column_ID=3007
;

-- 2023-04-06T15:30:06.039Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=2106
;

-- Field: Zahlungsbedingung(141,D) -> Zahlungsbedingung(184,D) -> Fix month day
-- Column: C_PaymentTerm.FixMonthDay
-- 2023-04-06T15:30:06.044Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=2106
;

-- 2023-04-06T15:30:06.050Z
DELETE FROM AD_Field WHERE AD_Field_ID=2106
;

-- 2023-04-06T15:30:06.053Z
/* DDL */ SELECT public.db_alter_table('C_PaymentTerm','ALTER TABLE C_PaymentTerm DROP COLUMN IF EXISTS FixMonthDay')
;

-- Column: C_PaymentTerm.FixMonthDay
-- 2023-04-06T15:30:06.147Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=3008
;

-- 2023-04-06T15:30:06.151Z
DELETE FROM AD_Column WHERE AD_Column_ID=3008
;

-- 2023-04-06T15:30:36.935Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=2107
;

-- Field: Zahlungsbedingung(141,D) -> Zahlungsbedingung(184,D) -> Fix month offset
-- Column: C_PaymentTerm.FixMonthOffset
-- 2023-04-06T15:30:36.940Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=2107
;

-- 2023-04-06T15:30:36.946Z
DELETE FROM AD_Field WHERE AD_Field_ID=2107
;

-- 2023-04-06T15:30:36.948Z
/* DDL */ SELECT public.db_alter_table('C_PaymentTerm','ALTER TABLE C_PaymentTerm DROP COLUMN IF EXISTS FixMonthOffset')
;

-- Column: C_PaymentTerm.FixMonthOffset
-- 2023-04-06T15:30:37.040Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=3009
;

-- 2023-04-06T15:30:37.045Z
DELETE FROM AD_Column WHERE AD_Column_ID=3009
;

