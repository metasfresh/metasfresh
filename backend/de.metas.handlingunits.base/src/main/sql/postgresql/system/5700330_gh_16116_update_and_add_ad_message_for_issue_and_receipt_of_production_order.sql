-- Value: de.metas.contracts.modular.impl.IssueReceiptModularContractHandler.Description.Issue
-- 2023-08-28T21:56:20.359863200Z
UPDATE AD_Message SET Value='de.metas.contracts.modular.impl.IssueReceiptModularContractHandler.Description.Issue',Updated=TO_TIMESTAMP('2023-08-29 00:56:20.359','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Message_ID=545297
;

-- Value: de.metas.contracts.modular.impl.IssueReceiptModularContractHandler.Description.Issue
-- 2023-08-28T21:56:35.112108900Z
UPDATE AD_Message_Trl SET MsgText='Die Menge {0} des Produkts {1} wurde ausgegeben.',Updated=TO_TIMESTAMP('2023-08-29 00:56:35.111','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='it_IT' AND AD_Message_ID=545297
;

-- Value: de.metas.contracts.modular.impl.IssueReceiptModularContractHandler.Description.Issue
-- 2023-08-28T21:56:46.434831800Z
UPDATE AD_Message_Trl SET MsgText='Die Menge {0} des Produkts {1} wurde ausgegeben.',Updated=TO_TIMESTAMP('2023-08-29 00:56:46.434','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=545297
;

-- Value: de.metas.contracts.modular.impl.IssueReceiptModularContractHandler.Description.Issue
-- 2023-08-28T21:56:51.873651300Z
UPDATE AD_Message_Trl SET MsgText='The quantity {0} of the product {1} was issued.',Updated=TO_TIMESTAMP('2023-08-29 00:56:51.873','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545297
;

-- Value: de.metas.contracts.modular.impl.IssueReceiptModularContractHandler.Description.Issue
-- 2023-08-28T21:56:58.906143800Z
UPDATE AD_Message_Trl SET MsgText='Die Menge {0} des Produkts {1} wurde ausgegeben.',Updated=TO_TIMESTAMP('2023-08-29 00:56:58.906','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545297
;

-- 2023-08-28T21:56:58.908259200Z
UPDATE AD_Message SET MsgText='Die Menge {0} des Produkts {1} wurde ausgegeben.' WHERE AD_Message_ID=545297
;

-- Value: de.metas.contracts.modular.impl.IssueReceiptModularContractHandler.Description.Issue
-- 2023-08-28T21:57:15.754431500Z
UPDATE AD_Message_Trl SET MsgText='Die Menge {0} des Produkts {1} wurde ausgegeben.',Updated=TO_TIMESTAMP('2023-08-29 00:57:15.753','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545297
;

-- Value: de.metas.contracts.modular.impl.IssueReceiptModularContractHandler.Description.Receipt
-- 2023-08-28T21:57:43.449009200Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545329,0,TO_TIMESTAMP('2023-08-29 00:57:43.331','YYYY-MM-DD HH24:MI:SS.US'),100,'U','Y','Die Menge {0} des Produkts {1} wurde ausgegeben.','I',TO_TIMESTAMP('2023-08-29 00:57:43.331','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts.modular.impl.IssueReceiptModularContractHandler.Description.Receipt')
;

-- 2023-08-28T21:57:43.458036300Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545329 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.contracts.modular.impl.IssueReceiptModularContractHandler.Description.Receipt
-- 2023-08-28T21:58:06.087959Z
UPDATE AD_Message_Trl SET MsgText='Die Menge {0} des Produkts {1} wurde erhalten.',Updated=TO_TIMESTAMP('2023-08-29 00:58:06.087','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=545329
;

-- Value: de.metas.contracts.modular.impl.IssueReceiptModularContractHandler.Description.Receipt
-- 2023-08-28T21:58:09.471402500Z
UPDATE AD_Message_Trl SET MsgText='Die Menge {0} des Produkts {1} wurde erhalten.',Updated=TO_TIMESTAMP('2023-08-29 00:58:09.471','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545329
;

-- 2023-08-28T21:58:09.472533800Z
UPDATE AD_Message SET MsgText='Die Menge {0} des Produkts {1} wurde erhalten.' WHERE AD_Message_ID=545329
;

-- Value: de.metas.contracts.modular.impl.IssueReceiptModularContractHandler.Description.Receipt
-- 2023-08-28T21:58:13.725922400Z
UPDATE AD_Message_Trl SET MsgText='Die Menge {0} des Produkts {1} wurde erhalten.',Updated=TO_TIMESTAMP('2023-08-29 00:58:13.725','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='it_IT' AND AD_Message_ID=545329
;

-- Value: de.metas.contracts.modular.impl.IssueReceiptModularContractHandler.Description.Receipt
-- 2023-08-28T21:58:26.528856800Z
UPDATE AD_Message_Trl SET MsgText='Die Menge {0} des Produkts {1} wurde erhalten.',Updated=TO_TIMESTAMP('2023-08-29 00:58:26.528','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545329
;

-- Value: de.metas.contracts.modular.impl.IssueReceiptModularContractHandler.Description.Receipt
-- 2023-08-28T21:58:52.826122600Z
UPDATE AD_Message_Trl SET MsgText='The quantity {0} of the product {1} was received.',Updated=TO_TIMESTAMP('2023-08-29 00:58:52.826','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545329
;