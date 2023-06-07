-- Reference: Type_Conditions
-- Value: ModularContract
-- ValueName: ModularContract
-- 2023-06-07T07:07:10.112153900Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543483,540271,TO_TIMESTAMP('2023-06-07 10:07:09.853','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','ModularContract',TO_TIMESTAMP('2023-06-07 10:07:09.853','YYYY-MM-DD HH24:MI:SS.US'),100,'ModularContract','ModularContract')
;

-- 2023-06-07T07:07:10.121582100Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543483 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: Type_Conditions -> ModularContract_ModularContract
-- 2023-06-07T07:07:59.627885300Z
UPDATE AD_Ref_List_Trl SET Name='Vertragsbausteine',Updated=TO_TIMESTAMP('2023-06-07 10:07:59.626','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543483
;

-- Reference Item: Type_Conditions -> ModularContract_ModularContract
-- 2023-06-07T07:08:02.097251400Z
UPDATE AD_Ref_List_Trl SET Name='Vertragsbausteine',Updated=TO_TIMESTAMP('2023-06-07 10:08:02.097','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543483
;

-- 2023-06-07T07:08:02.098349900Z
UPDATE AD_Ref_List SET Name='Vertragsbausteine' WHERE AD_Ref_List_ID=543483
;

-- Reference Item: Type_Conditions -> ModularContract_ModularContract
-- 2023-06-07T07:08:12.492063600Z
UPDATE AD_Ref_List_Trl SET Name='Modular Contract',Updated=TO_TIMESTAMP('2023-06-07 10:08:12.491','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543483
;

-- Reference Item: Type_Conditions -> ModularContract_ModularContract
-- 2023-06-07T07:08:17.449930400Z
UPDATE AD_Ref_List_Trl SET Name='Vertragsbausteine',Updated=TO_TIMESTAMP('2023-06-07 10:08:17.449','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=543483
;



-- Field: Vertragsbedingungen (OLD)(540113,de.metas.contracts) -> Bedingungen(540331,de.metas.contracts) -> Preissystem
-- Column: C_Flatrate_Conditions.M_PricingSystem_ID
-- 2023-06-07T07:12:03.070919300Z
UPDATE AD_Field SET DisplayLogic='@Type_Conditions/''Refund''@!''Refund'' & @Type_Conditions/''Commission''@!''Commission'' & @Type_Conditions/''MediatedCommission''@!''MediatedCommission'' & @Type_Conditions/''MarginCommission''@!''MarginCommission'' & @Type_Conditions/''LicenseFee''@!''LicenseFee'' & @Type_Conditions/''ModularContract''@!''ModularContract''',Updated=TO_TIMESTAMP('2023-06-07 10:12:03.07','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=547848
;

-- Field: Vertragsbedingungen (OLD)(540113,de.metas.contracts) -> Bedingungen(540331,de.metas.contracts) -> Vertragsverlängerung/-übergang
-- Column: C_Flatrate_Conditions.C_Flatrate_Transition_ID
-- 2023-06-07T07:16:16.939243Z
UPDATE AD_Field SET DisplayLogic='@Type_Conditions/''ModularContract''@!''ModularContract''',Updated=TO_TIMESTAMP('2023-06-07 10:16:16.939','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=548457
;

-- Field: Vertragsbedingungen (OLD)(540113,de.metas.contracts) -> Bedingungen(540331,de.metas.contracts) -> Rechnungsstellung
-- Column: C_Flatrate_Conditions.InvoiceRule
-- 2023-06-07T07:17:23.459037800Z
UPDATE AD_Field SET DisplayLogic='@Type_Conditions/''Refund''@!''Refund'' & @Type_Conditions/''Commission''@!''Commission'' & @Type_Conditions/''MediatedCommission''@!''MediatedCommission'' & @Type_Conditions/''MarginCommission''@!''MarginCommission'' & @Type_Conditions/''CallOrder''@!''CallOrder'' & @Type_Conditions/''ModularContract''@!''ModularContract''',Updated=TO_TIMESTAMP('2023-06-07 10:17:23.459','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=548120
;


-- Tab: Vertragsbedingungen (OLD)(540113,de.metas.contracts) -> Rückvergütung
-- Table: C_Flatrate_RefundConfig
-- 2023-06-07T07:41:11.288165600Z
UPDATE AD_Tab SET DisplayLogic='@Type_Conditions/''''@=''Refund''',Updated=TO_TIMESTAMP('2023-06-07 10:41:11.288','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=541106
;

