-- Field: Zahlungsbedingung(141,D) -> Zahlungsbedingung(184,D) -> Net Day
-- Column: C_PaymentTerm.NetDay
-- 2023-04-18T14:35:31.166Z
UPDATE AD_Field SET DisplayLogic='',Updated=TO_TIMESTAMP('2023-04-18 17:35:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=7365
;

-- Field: Zahlungsbedingung(141,D) -> Zahlungsbedingung(184,D) -> Net Day
-- Column: C_PaymentTerm.NetDay
-- 2023-04-18T14:38:47.988Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-04-18 17:38:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=7365
;

-- UI Element: Zahlungsbedingung(141,D) -> Zahlungsbedingung(184,D) -> advanced edit -> 10 -> advanced edit.Beschreibung
-- Column: C_PaymentTerm.Description
-- 2023-04-18T14:42:01.657Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540546, SeqNo=60,Updated=TO_TIMESTAMP('2023-04-18 17:42:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546529
;

-- UI Element: Zahlungsbedingung(141,D) -> Zahlungsbedingung(184,D) -> advanced edit -> 10 -> advanced edit.Notiz
-- Column: C_PaymentTerm.DocumentNote
-- 2023-04-18T14:42:13.746Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540546, SeqNo=70,Updated=TO_TIMESTAMP('2023-04-18 17:42:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546530
;

-- UI Column: Zahlungsbedingung(141,D) -> Zahlungsbedingung(184,D) -> description -> 10
-- UI Element Group: description
-- 2023-04-18T14:43:18.518Z
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=540826
;

-- UI Section: Zahlungsbedingung(141,D) -> Zahlungsbedingung(184,D) -> description
-- UI Column: 10
-- 2023-04-18T14:43:23.095Z
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=540477
;

-- Tab: Zahlungsbedingung(141,D) -> Zahlungsbedingung(184,D)
-- UI Section: description
-- 2023-04-18T14:43:26.310Z
DELETE FROM  AD_UI_Section_Trl WHERE AD_UI_Section_ID=540351
;

-- 2023-04-18T14:43:26.317Z
DELETE FROM AD_UI_Section WHERE AD_UI_Section_ID=540351
;

-- Field: Zahlungsbedingung(141,D) -> Zahlungsbedingung(184,D) -> Tage Netto
-- Column: C_PaymentTerm.NetDays
-- 2023-04-18T14:52:53.012Z
UPDATE AD_Field SET DisplayLogic='',Updated=TO_TIMESTAMP('2023-04-18 17:52:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=1069
;

-- UI Element: Zahlungsbedingung(141,D) -> Zahlungsbedingung(184,D) -> advanced edit -> 10 -> advanced edit.Beschreibung
-- Column: C_PaymentTerm.Description
-- 2023-04-19T06:46:31.965Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546529
;

-- UI Element: Zahlungsbedingung(141,D) -> Zahlungsbedingung(184,D) -> advanced edit -> 10 -> advanced edit.Notiz
-- Column: C_PaymentTerm.DocumentNote
-- 2023-04-19T06:46:39.423Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546530
;

