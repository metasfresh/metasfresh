-- Run mode: SWING_CLIENT

-- Name: C_Order_with_lines_with_C_Flatrate_Conditions_ID
-- 2023-10-03T09:04:23.107Z
UPDATE AD_Reference SET Name='C_Order_with_lines_with_C_Flatrate_Conditions_ID',Updated=TO_TIMESTAMP('2023-10-03 12:04:23.106','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Reference_ID=540754
;

-- 2023-10-03T09:04:23.114Z
UPDATE AD_Reference_Trl trl SET Name='C_Order_with_lines_with_C_Flatrate_Conditions_ID' WHERE AD_Reference_ID=540754 AND AD_Language='de_DE'
;

-- 2023-10-03T09:04:26.466Z
UPDATE AD_Reference_Trl SET Name='C_Order_C_Flatrate_Term',Updated=TO_TIMESTAMP('2023-10-03 12:04:26.466','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Reference_ID=540754
;

-- 2023-10-03T09:04:29.706Z
UPDATE AD_Reference_Trl SET Name='C_Order_C_Flatrate_Term',Updated=TO_TIMESTAMP('2023-10-03 12:04:29.706','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Reference_ID=540754
;

-- 2023-10-03T09:04:32.660Z
UPDATE AD_Reference_Trl SET Name='C_ ORDER_WITH_LINES_WITH_C_FLATRATE_CONTItions_id',Updated=TO_TIMESTAMP('2023-10-03 12:04:32.659','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Reference_ID=540754
;

-- 2023-10-03T09:04:36.803Z
UPDATE AD_Reference_Trl SET Name='C_Order_with_lines_with_C_Flatrate_Conditions_ID',Updated=TO_TIMESTAMP('2023-10-03 12:04:36.802','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='it_IT' AND AD_Reference_ID=540754
;

-- Reference: C_Order_with_lines_with_C_Flatrate_Conditions_ID
-- Table: C_Order
-- Key: C_Order.C_Order_ID
-- 2023-10-03T09:04:48.679Z
UPDATE AD_Ref_Table SET WhereClause='EXISTS (select 1 from C_OrderLine ol where ol.C_Order_ID = @C_Order_ID/-1@ AND ol.C_Flatrate_Conditions_ID IS NOT NULL)',Updated=TO_TIMESTAMP('2023-10-03 12:04:48.679','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Reference_ID=540754
;

-- Run mode: SWING_CLIENT

-- Value: MSG_ExtensionNotAllowed_InterimAndModularContracts
-- 2023-10-03T09:26:19.703Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545344,0,TO_TIMESTAMP('2023-10-03 12:26:19.582','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Die Laufzeiten von modularen Verträgen und Vorfinanzierungsverträgen dürfen nicht verlängert werden. Bitte bei Vertragsverlängerung die Option "Verlängerung nicht zulässig" auswählen.','E',TO_TIMESTAMP('2023-10-03 12:26:19.582','YYYY-MM-DD HH24:MI:SS.US'),100,'MSG_ExtensionNotAllowed_InterimAndModularContracts')
;

-- 2023-10-03T09:26:19.713Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545344 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: MSG_ExtensionNotAllowed_InterimAndModularContracts
-- 2023-10-03T09:26:33.463Z
UPDATE AD_Message_Trl SET MsgText='The terms of modular contracts and interim payment contracts may not be extended. Please select the option "Extension not allowed" for when extending contract.',Updated=TO_TIMESTAMP('2023-10-03 12:26:33.462','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545344
;

-- Run mode: SWING_CLIENT

-- Value: de.metas.contracts.ShipmentCompleted
-- 2023-10-03T09:27:54.177Z
UPDATE AD_Message SET MsgText='Eine Lieferung für das Produkt {0} mit der Menge {1} wurde fertiggestellt.',Updated=TO_TIMESTAMP('2023-10-03 12:27:54.176','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Message_ID=545311
;

-- 2023-10-03T09:27:54.179Z
UPDATE AD_Message_Trl trl SET MsgText='Eine Lieferung für das Produkt {0} mit der Menge {1} wurde fertiggestellt.' WHERE AD_Message_ID=545311 AND AD_Language='de_DE'
;

-- Value: de.metas.contracts.ShipmentCompleted
-- 2023-10-03T09:28:12.180Z
UPDATE AD_Message_Trl SET MsgText='A shipment for the product {0} with the quantity {1} was completed.',Updated=TO_TIMESTAMP('2023-10-03 12:28:12.18','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545311
;

-- Value: de.metas.contracts.ShipmentCompleted
-- 2023-10-03T09:28:22.111Z
UPDATE AD_Message_Trl SET MsgText='Eine Lieferung für das Produkt {0} mit der Menge {1} wurde fertiggestellt.',Updated=TO_TIMESTAMP('2023-10-03 12:28:22.111','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='it_IT' AND AD_Message_ID=545311
;

-- Value: de.metas.contracts.ShipmentCompleted
-- 2023-10-03T09:28:23.459Z
UPDATE AD_Message_Trl SET MsgText='Eine Lieferung für das Produkt {0} mit der Menge {1} wurde fertiggestellt.',Updated=TO_TIMESTAMP('2023-10-03 12:28:23.459','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=545311
;

-- Value: de.metas.contracts.ShipmentCompleted
-- 2023-10-03T09:28:25.176Z
UPDATE AD_Message_Trl SET MsgText='Eine Lieferung für das Produkt {0} mit der Menge {1} wurde fertiggestellt.',Updated=TO_TIMESTAMP('2023-10-03 12:28:25.176','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545311
;

-- Run mode: SWING_CLIENT

-- Value: de.metas.contracts.ShipmentReversed
-- 2023-10-03T09:28:55.581Z
UPDATE AD_Message SET MsgText='Eine Lieferung für das Produkt {0} mit der Menge {1} wurde storniert.',Updated=TO_TIMESTAMP('2023-10-03 12:28:55.58','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Message_ID=545312
;

-- 2023-10-03T09:28:55.581Z
UPDATE AD_Message_Trl trl SET MsgText='Eine Lieferung für das Produkt {0} mit der Menge {1} wurde storniert.' WHERE AD_Message_ID=545312 AND AD_Language='de_DE'
;

-- Value: de.metas.contracts.ShipmentReversed
-- 2023-10-03T09:29:03.765Z
UPDATE AD_Message_Trl SET MsgText='A shipment for the product {0} with the quantity {1} was reversed.',Updated=TO_TIMESTAMP('2023-10-03 12:29:03.765','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545312
;

-- Value: de.metas.contracts.ShipmentReversed
-- 2023-10-03T09:29:09.588Z
UPDATE AD_Message_Trl SET MsgText='Eine Lieferung für das Produkt {0} mit der Menge {1} wurde storniert.',Updated=TO_TIMESTAMP('2023-10-03 12:29:09.588','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='it_IT' AND AD_Message_ID=545312
;

-- Value: de.metas.contracts.ShipmentReversed
-- 2023-10-03T09:29:11.326Z
UPDATE AD_Message_Trl SET MsgText='Eine Lieferung für das Produkt {0} mit der Menge {1} wurde storniert.',Updated=TO_TIMESTAMP('2023-10-03 12:29:11.326','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=545312
;

-- Value: de.metas.contracts.ShipmentReversed
-- 2023-10-03T09:29:12.892Z
UPDATE AD_Message_Trl SET MsgText='Eine Lieferung für das Produkt {0} mit der Menge {1} wurde storniert.',Updated=TO_TIMESTAMP('2023-10-03 12:29:12.892','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545312
;

-- Run mode: SWING_CLIENT

-- Value: de.metas.contracts.modular.workpackage.impl.PurchaseOrderLineLogHandler.OnComplete.Description
-- 2023-10-03T09:32:16.496Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545345,0,TO_TIMESTAMP('2023-10-03 12:32:16.356','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Eine Bestellung für das Produkt {0} mit der Menge {1} wurde fertiggestellt.','I',TO_TIMESTAMP('2023-10-03 12:32:16.356','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts.modular.workpackage.impl.PurchaseOrderLineLogHandler.OnComplete.Description')
;

-- 2023-10-03T09:32:16.497Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545345 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.contracts.modular.workpackage.impl.PurchaseOrderLineLogHandler.OnComplete.Description
-- 2023-10-03T09:32:26.435Z
UPDATE AD_Message_Trl SET MsgText='A purchase order for the product {0} with the quantity {1} was completed.',Updated=TO_TIMESTAMP('2023-10-03 12:32:26.435','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545345
;

-- Run mode: SWING_CLIENT

-- Value: MSG_ModularContractSettings_AlreadyUsed
-- 2023-10-03T09:33:48.552Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545346,0,TO_TIMESTAMP('2023-10-03 12:33:48.435','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Diese Einstellungen für modulare Verträge werden bereits für fertiggestellte Vertragsbedingungen verwendet.','E',TO_TIMESTAMP('2023-10-03 12:33:48.435','YYYY-MM-DD HH24:MI:SS.US'),100,'MSG_ModularContractSettings_AlreadyUsed')
;

-- 2023-10-03T09:33:48.554Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545346 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: MSG_ModularContractSettings_AlreadyUsed
-- 2023-10-03T09:33:56.940Z
UPDATE AD_Message_Trl SET MsgText='These modular contract settings are already being used for completed contract terms.',Updated=TO_TIMESTAMP('2023-10-03 12:33:56.94','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545346
;

