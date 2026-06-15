-- Run mode: SWING_CLIENT

-- Value: de.metas.contracts.modular.ModularContractService.ContractHasBillableLogs
-- 2025-06-13T12:22:53.464Z
UPDATE AD_Message SET MsgType='E',Updated=TO_TIMESTAMP('2025-06-13 14:22:53.462','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Message_ID=545513
;

-- Value: de.metas.contracts.modular.ModularContractService.ContractHasBillableUnprocessedLogs
-- 2025-06-13T12:24:29.133Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545560,0,TO_TIMESTAMP('2025-06-13 14:24:29.008','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Zum Vertrag existieren noch abrechenbare Logs die nicht verarbeitet sind.','E',TO_TIMESTAMP('2025-06-13 14:24:29.008','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts.modular.ModularContractService.ContractHasBillableUnprocessedLogs')
;

-- 2025-06-13T12:24:29.136Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545560 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.contracts.modular.ModularContractService.ContractHasBillableUnprocessedLogs
-- 2025-06-13T12:27:13.303Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='The contract has still invoicable logs that are not processed',Updated=TO_TIMESTAMP('2025-06-13 14:27:13.303','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545560
;

-- Value: de.metas.contracts.modular.ModularContractService.ContractHasBillableUnprocessedLogs
-- 2025-06-13T12:27:18.475Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-06-13 14:27:18.475','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545560
;

-- Value: de.metas.contracts.modular.ModularContractService.ContractHasBillableUnprocessedLogs
-- 2025-06-13T12:27:20.527Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-06-13 14:27:20.527','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545560
;

-- Value: de.metas.contracts.modular.ModularContractService.ContractHasNotClosedSalesOrders
-- 2025-06-13T12:34:18.230Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545561,0,TO_TIMESTAMP('2025-06-13 14:34:18.112','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Zum Vertrag gehörende Aufträge müssen zuerst geschlossen werden.','E',TO_TIMESTAMP('2025-06-13 14:34:18.112','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts.modular.ModularContractService.ContractHasNotClosedSalesOrders')
;

-- 2025-06-13T12:34:18.232Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545561 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.contracts.modular.ModularContractService.ContractHasNotClosedSalesOrders
-- 2025-06-13T12:42:24.500Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Sales Orders related to the contract need to be closed first',Updated=TO_TIMESTAMP('2025-06-13 14:42:24.5','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545561
;

-- Value: de.metas.contracts.modular.ModularContractService.ContractHasNotClosedSalesOrders
-- 2025-06-13T12:42:25.188Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-06-13 14:42:25.188','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545561
;

-- Value: de.metas.contracts.modular.ModularContractService.ContractHasNotClosedSalesOrders
-- 2025-06-13T12:42:25.894Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-06-13 14:42:25.894','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545561
;

