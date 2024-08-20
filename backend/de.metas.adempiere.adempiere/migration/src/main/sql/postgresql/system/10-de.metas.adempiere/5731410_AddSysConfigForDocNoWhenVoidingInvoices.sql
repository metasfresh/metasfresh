-- SysConfig Name: org.compiere.model.MInvoice.ANNOTATE_DOCNO_INVOICE
-- SysConfig Value: Y
-- 2024-08-20T14:52:42.284Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541730,'S',TO_TIMESTAMP('2024-08-20 17:52:42.139','YYYY-MM-DD HH24:MI:SS.US'),100,'If set to Y, then metasfresh will  he documentno will be added to description when the invoice gets voided.','D','Y','org.compiere.model.MInvoice.ANNOTATE_DOCNO_INVOICE',TO_TIMESTAMP('2024-08-20 17:52:42.139','YYYY-MM-DD HH24:MI:SS.US'),100,'Y')
;

-- SysConfig Name: org.compiere.model.MInvoice.ANNOTATE_DOCNO_INVOICE
-- SysConfig Value: N
-- SysConfig Value (old): Y
-- 2024-08-20T14:52:45.495Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', Value='N',Updated=TO_TIMESTAMP('2024-08-20 17:52:45.493','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_SysConfig_ID=541730
;

