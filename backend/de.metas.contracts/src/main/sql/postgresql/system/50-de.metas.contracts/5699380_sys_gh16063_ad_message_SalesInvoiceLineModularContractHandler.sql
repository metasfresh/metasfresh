

-- Value: de.metas.contracts.modular.impl.SalesInvoiceLineModularContractHandler
-- 2023-08-17T10:06:29.011970400Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545321,0,TO_TIMESTAMP('2023-08-17 13:06:28.812','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Debitorenrechnung für das Produkt {0} mit der Menge {1} wurde fertiggestellt.','I',TO_TIMESTAMP('2023-08-17 13:06:28.812','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts.modular.impl.SalesInvoiceLineModularContractHandler')
;

-- 2023-08-17T10:06:29.020972300Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545321 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.contracts.modular.impl.SalesInvoiceLineModularContractHandler
-- 2023-08-17T10:06:46.929595200Z
UPDATE AD_Message_Trl SET MsgText='Sales invoice for the product {0} with the quantity {1} was completed.',Updated=TO_TIMESTAMP('2023-08-17 13:06:46.929','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545321
;

-- Value: de.metas.contracts.modular.impl.SalesInvoiceLineModularContractHandler.OnComplete.Description
-- 2023-08-17T10:20:05.590016300Z
UPDATE AD_Message SET Value='de.metas.contracts.modular.impl.SalesInvoiceLineModularContractHandler.OnComplete.Description',Updated=TO_TIMESTAMP('2023-08-17 13:20:05.589','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Message_ID=545321
;

-- Value: de.metas.contracts.modular.impl.SalesInvoiceLineModularContractHandler.OnReverse.Description
-- 2023-08-17T10:20:43.176185700Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545322,0,TO_TIMESTAMP('2023-08-17 13:20:43.055','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Debitorenrechnung für das Produkt {0} mit der Menge {1} wurde storniert.','I',TO_TIMESTAMP('2023-08-17 13:20:43.055','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts.modular.impl.SalesInvoiceLineModularContractHandler.OnReverse.Description')
;

-- 2023-08-17T10:20:43.177721900Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545322 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.contracts.modular.impl.SalesInvoiceLineModularContractHandler.OnReverse.Description
-- 2023-08-17T10:20:57.644142800Z
UPDATE AD_Message_Trl SET MsgText='Sales invoice for the product {0} with the quantity {1} was reversed.',Updated=TO_TIMESTAMP('2023-08-17 13:20:57.644','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545322
;

