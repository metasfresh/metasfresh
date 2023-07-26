-- Value: de.metas.contracts.modular.impl.IssueReceiptModularContractHandler.Description
-- 2023-07-24T11:43:17.615746400Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545297,0,TO_TIMESTAMP('2023-07-24 14:43:16.567','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Die Menge {0} des Produkts {1} wurde ausgegeben/empfangen.','I',TO_TIMESTAMP('2023-07-24 14:43:16.567','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts.modular.impl.IssueReceiptModularContractHandler.Description')
;

-- 2023-07-24T11:43:17.622746Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545297 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.contracts.modular.impl.IssueReceiptModularContractHandler.Description
-- 2023-07-24T11:43:30.872516700Z
UPDATE AD_Message_Trl SET MsgText='The quantity {0} of the product {1} was issued/received.',Updated=TO_TIMESTAMP('2023-07-24 14:43:30.872','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545297
;

-- Column: PP_Order.Modular_Flatrate_Term_ID
-- 2023-07-25T10:00:43.620686700Z
UPDATE AD_Column SET MandatoryLogic='@C_DocTypeTarget_ID@=1000037',Updated=TO_TIMESTAMP('2023-07-25 13:00:43.62','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587163
;

-- Name: Modular_Flatrate_Term_For_ManufacturingOrder
-- 2023-07-25T10:05:10.923373700Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540649,'c_flatrate_term.c_flatrate_term_id IN (SELECT DISTINCT contract.c_flatrate_term_id
FROM M_Warehouse warehouse
         INNER JOIN c_flatrate_term contract ON warehouse.c_bpartner_id = contract.bill_bpartner_id
         INNER JOIN C_Flatrate_Conditions conditions ON contract.c_flatrate_conditions_id = conditions.c_flatrate_conditions_id
WHERE conditions.type_conditions = ''ModularContract''
  AND warehouse.M_Warehouse_ID = @M_Warehouse_ID / -1@)',TO_TIMESTAMP('2023-07-25 13:05:10.813','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Modular_Flatrate_Term_For_ManufacturingOrder','S',TO_TIMESTAMP('2023-07-25 13:05:10.813','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Column: PP_Order.Modular_Flatrate_Term_ID
-- 2023-07-25T10:06:10.308962100Z
UPDATE AD_Column SET AD_Val_Rule_ID=540649,Updated=TO_TIMESTAMP('2023-07-25 13:06:10.308','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587163
;
