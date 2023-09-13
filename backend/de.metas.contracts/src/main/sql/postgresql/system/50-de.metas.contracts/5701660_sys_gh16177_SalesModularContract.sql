-- Name: C_Flatrate_Conditions_Modular_Settings
-- 2023-09-05T14:06:35.524512500Z
UPDATE AD_Val_Rule SET Code='(C_Flatrate_Conditions_ID IN (SELECT c.C_Flatrate_Conditions_ID FROM C_Flatrate_Conditions c INNER JOIN ModCntr_Settings mc ON c.ModCntr_Settings_ID = mc.ModCntr_Settings_ID AND mc.M_Product_ID = @M_Product_ID@ WHERE c.ModCntr_Settings_ID IS NOT NULL AND c.DocStatus = ''CO'' AND c.type_conditions <> ''InterimInvoice'' AND mc.issotrx = ''@IsSOTrx@''))',Updated=TO_TIMESTAMP('2023-09-05 17:06:35.523','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540640
;

-- Value: de.metas.contracts.modular.impl.ReactivateNotAllowed
-- 2023-09-06T09:31:58.507128100Z
UPDATE AD_Message SET Value='de.metas.contracts.modular.impl.ReactivateNotAllowed',Updated=TO_TIMESTAMP('2023-09-06 12:31:58.506','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Message_ID=545281
;

-- Reference: C_Order_Term_C_Flatrate_Term
-- Table: C_Order
-- Key: C_Order.C_Order_ID
-- 2023-09-06T10:48:00.953791Z
UPDATE AD_Ref_Table SET WhereClause='C_Order.C_Order_ID = @C_Order_Term_ID/-1@ AND C_Order.issotrx = ''N''',Updated=TO_TIMESTAMP('2023-09-06 13:48:00.953','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Reference_ID=541788
;

-- Name: C_Order_Term_C_Flatrate_Term (PO ONLY)
-- 2023-09-06T10:48:45.014473600Z
UPDATE AD_Reference SET Name='C_Order_Term_C_Flatrate_Term (PO ONLY)',Updated=TO_TIMESTAMP('2023-09-06 13:48:45.014','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Reference_ID=541788
;

-- 2023-09-06T10:48:45.028568800Z
UPDATE AD_Reference_Trl trl SET Name='C_Order_Term_C_Flatrate_Term (PO ONLY)' WHERE AD_Reference_ID=541788 AND AD_Language='de_DE'
;

-- 2023-09-06T10:49:16.468793400Z
UPDATE AD_Reference_Trl SET Name='C_Order_Term_C_Flatrate_Term (PO ONLY)',Updated=TO_TIMESTAMP('2023-09-06 13:49:16.467','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Reference_ID=541788
;

-- 2023-09-06T10:49:18.513967400Z
UPDATE AD_Reference_Trl SET Name='C_Order_Term_C_Flatrate_Term (PO ONLY)',Updated=TO_TIMESTAMP('2023-09-06 13:49:18.512','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Reference_ID=541788
;

-- 2023-09-06T10:49:20.739722Z
UPDATE AD_Reference_Trl SET Name='C_Order_Term_C_Flatrate_Term (PO ONLY)',Updated=TO_TIMESTAMP('2023-09-06 13:49:20.737','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Reference_ID=541788
;

-- 2023-09-06T10:49:30.075022Z
UPDATE AD_Reference_Trl SET Name='C_Order_Term_C_Flatrate_Term (PO ONLY)',Updated=TO_TIMESTAMP('2023-09-06 13:49:30.074','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='it_IT' AND AD_Reference_ID=541788
;

-- 2023-09-06T10:50:41.482646900Z
UPDATE AD_RelationType SET Name='C_Flatrate_Term -> C_Order (PO ONLY)',Updated=TO_TIMESTAMP('2023-09-06 13:50:41.481','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_RelationType_ID=540410
;

-- Name: C_Order_Term_C_Flatrate_Term (SO ONLY)
-- 2023-09-06T10:58:15.385043400Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541820,TO_TIMESTAMP('2023-09-06 13:58:15.24','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','C_Order_Term_C_Flatrate_Term (SO ONLY)',TO_TIMESTAMP('2023-09-06 13:58:15.24','YYYY-MM-DD HH24:MI:SS.US'),100,'T')
;

-- 2023-09-06T10:58:15.406048400Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541820 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: C_Order_Term_C_Flatrate_Term (SO ONLY)
-- Table: C_Order
-- Key: C_Order.C_Order_ID
-- 2023-09-06T10:58:57.221373Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,2161,0,541820,259,TO_TIMESTAMP('2023-09-06 13:58:57.218','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','N',TO_TIMESTAMP('2023-09-06 13:58:57.218','YYYY-MM-DD HH24:MI:SS.US'),100,'C_Order.C_Order_ID = @C_Order_Term_ID/-1@ AND C_Order.issotrx = ''Y''')
;
