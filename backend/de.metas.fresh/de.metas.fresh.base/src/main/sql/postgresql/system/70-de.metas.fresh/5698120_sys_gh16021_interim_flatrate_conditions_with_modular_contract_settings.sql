-- Column: C_Flatrate_Conditions.C_Interim_Invoice_Settings_ID
-- 2023-08-04T14:06:18.550405700Z
UPDATE AD_Column SET MandatoryLogic='',Updated=TO_TIMESTAMP('2023-08-04 17:06:18.55','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=583805
;

-- Column: C_Flatrate_Conditions.ModCntr_Settings_ID
-- 2023-08-04T14:08:24.676863500Z
UPDATE AD_Column SET MandatoryLogic='@Type_Conditions/''''@=''ModularContract'' | @Type_Conditions/''''@=''InterimInvoice''',Updated=TO_TIMESTAMP('2023-08-04 17:08:24.676','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586810
;

-- Field: Vertragsbedingungen (OLD)(540113,de.metas.contracts) -> Bedingungen(540331,de.metas.contracts) -> Einstellungen für Vorfinanzierungen
-- Column: C_Flatrate_Conditions.C_Interim_Invoice_Settings_ID
-- 2023-08-04T14:11:53.444413700Z
UPDATE AD_Field SET DisplayLogic='0=1',Updated=TO_TIMESTAMP('2023-08-04 17:11:53.444','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=702171
;

-- Field: Vertragsbedingungen (OLD)(540113,de.metas.contracts) -> Bedingungen(540331,de.metas.contracts) -> Einstellungen für Vorfinanzierungen
-- Column: C_Flatrate_Conditions.C_Interim_Invoice_Settings_ID
-- 2023-08-04T14:11:58.593173400Z
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2023-08-04 17:11:58.593','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=702171
;

-- Column: C_Flatrate_Conditions.M_PricingSystem_ID
-- 2023-08-04T14:12:22.544669900Z
UPDATE AD_Column SET MandatoryLogic='',Updated=TO_TIMESTAMP('2023-08-04 17:12:22.544','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=545656
;

-- Field: Vertragsbedingungen (OLD)(540113,de.metas.contracts) -> Bedingungen(540331,de.metas.contracts) -> Preissystem
-- Column: C_Flatrate_Conditions.M_PricingSystem_ID
-- 2023-08-04T14:13:26.492675300Z
UPDATE AD_Field SET DisplayLogic='@Type_Conditions/''Refund''@!''Refund'' & @Type_Conditions/''Commission''@!''Commission'' & @Type_Conditions/''MediatedCommission''@!''MediatedCommission'' & @Type_Conditions/''MarginCommission''@!''MarginCommission'' & @Type_Conditions/''LicenseFee''@!''LicenseFee'' & @Type_Conditions/''ModularContract''@!''ModularContract'' & @Type_Conditions/''InterimInvoice''@!''InterimInvoice''',Updated=TO_TIMESTAMP('2023-08-04 17:13:26.492','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=547848
;

-- Field: Vertragsbedingungen (OLD)(540113,de.metas.contracts) -> Bedingungen(540331,de.metas.contracts) -> Rechnungsstellung
-- Column: C_Flatrate_Conditions.InvoiceRule
-- 2023-08-04T14:14:27.022579300Z
UPDATE AD_Field SET DisplayLogic='@Type_Conditions/''Refund''@!''Refund'' & @Type_Conditions/''Commission''@!''Commission'' & @Type_Conditions/''MediatedCommission''@!''MediatedCommission'' & @Type_Conditions/''MarginCommission''@!''MarginCommission'' & @Type_Conditions/''CallOrder''@!''CallOrder'' & @Type_Conditions/''ModularContract''@!''ModularContract'' & @Type_Conditions/''InterimInvoice''@!''InterimInvoice''',Updated=TO_TIMESTAMP('2023-08-04 17:14:27.022','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=548120
;

-- Field: Vertragsbedingungen (OLD)(540113,de.metas.contracts) -> Bedingungen(540331,de.metas.contracts) -> Vertragsverlängerung/-übergang
-- Column: C_Flatrate_Conditions.C_Flatrate_Transition_ID
-- 2023-08-04T14:14:59.125208900Z
UPDATE AD_Field SET DisplayLogic='@Type_Conditions/''ModularContract''@!''ModularContract'' & @Type_Conditions/''InterimInvoice''@!''InterimInvoice''',Updated=TO_TIMESTAMP('2023-08-04 17:14:59.11','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=548457
;

-- Field: Vertragsbedingungen (OLD)(540113,de.metas.contracts) -> Bedingungen(540331,de.metas.contracts) -> Einstellungen für modulare Verträge
-- Column: C_Flatrate_Conditions.ModCntr_Settings_ID
-- 2023-08-04T14:15:41.474021900Z
UPDATE AD_Field SET DisplayLogic='@Type_Conditions/''''@=''ModularContract'' | @Type_Conditions/''''@=''InterimInvoice''',Updated=TO_TIMESTAMP('2023-08-04 17:15:41.474','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=716357
;

create unique index c_flatrate_conditions_modcntr_settings_id_uindex
    on public.c_flatrate_conditions (modcntr_settings_id)
    where isactive = 'Y' and type_conditions = 'InterimInvoice';