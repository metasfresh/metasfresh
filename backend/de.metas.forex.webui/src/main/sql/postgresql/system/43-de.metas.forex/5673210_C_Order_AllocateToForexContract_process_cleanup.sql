-- Process: C_Order_AllocateToForexContract(de.metas.forex.webui.process.C_Order_AllocateToForexContract)
-- ParameterName: C_ForeignExchangeContract_ID
-- 2023-01-24T10:10:36.486Z
UPDATE AD_Process_Para SET AD_Val_Rule_ID=NULL,Updated=TO_TIMESTAMP('2023-01-24 12:10:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542456
;

-- Name: C_ForeignExchangeContract - Eligible for Order allocation
-- 2023-01-24T10:10:44.269Z
DELETE FROM AD_Val_Rule WHERE AD_Val_Rule_ID=540616
;

