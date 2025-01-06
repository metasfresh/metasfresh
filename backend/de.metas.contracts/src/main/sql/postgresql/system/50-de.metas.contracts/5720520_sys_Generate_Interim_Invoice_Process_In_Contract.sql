-- Run mode: SWING_CLIENT

-- Value: InterimContract_GenerateInterimInvoice
-- Classname: de.metas.contracts.modular.interim.invoice.process.C_BPartner_InterimContract_GenerateInterimInvoice
-- 2024-04-01T13:02:05.437Z
UPDATE AD_Process SET Value='InterimContract_GenerateInterimInvoice',Updated=TO_TIMESTAMP('2024-04-01 16:02:05.435','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_ID=585312
;

-- Value: InterimContract_GenerateInterimInvoice
-- Classname: de.metas.contracts.modular.interim.invoice.process.InterimContract_GenerateInterimInvoice
-- 2024-04-01T13:02:30.669Z
UPDATE AD_Process SET Classname='de.metas.contracts.modular.interim.invoice.process.InterimContract_GenerateInterimInvoice',Updated=TO_TIMESTAMP('2024-04-01 16:02:30.667','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_ID=585312
;

-- Process: InterimContract_GenerateInterimInvoice(de.metas.contracts.modular.interim.invoice.process.InterimContract_GenerateInterimInvoice)
-- Table: C_BPartner_InterimContract
-- EntityType: de.metas.invoicecandidate
-- 2024-04-01T14:10:37.072Z
DELETE FROM AD_Table_Process WHERE AD_Table_Process_ID=541413
;

-- Process: InterimContract_GenerateInterimInvoice(de.metas.contracts.modular.interim.invoice.process.InterimContract_GenerateInterimInvoice)
-- Table: C_Flatrate_Term
-- EntityType: de.metas.contracts
-- 2024-04-01T14:11:17.298Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585312,540320,541471,TO_TIMESTAMP('2024-04-01 17:11:17.151','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y',TO_TIMESTAMP('2024-04-01 17:11:17.151','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N')
;

