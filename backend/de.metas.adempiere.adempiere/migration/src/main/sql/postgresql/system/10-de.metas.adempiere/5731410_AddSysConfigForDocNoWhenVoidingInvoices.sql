-- SysConfig Name: org.compiere.model.MInvoice.ANNOTATE_DOCNO_INVOICE
-- SysConfig Value: Y
-- 2024-08-20T14:52:42.284Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541730,'S',TO_TIMESTAMP('2024-08-20 17:52:42.139','YYYY-MM-DD HH24:MI:SS.US'),100,'If set to Y, then metasfresh will  he documentno will be added to description when the invoice gets voided.','D','Y','org.compiere.model.MInvoice.ANNOTATE_DOCNO_INVOICE',TO_TIMESTAMP('2024-08-20 17:52:42.139','YYYY-MM-DD HH24:MI:SS.US'),100,'Y')
;

-- SysConfig Name: org.compiere.model.MInvoice.ANNOTATE_DOCNO_INVOICE_TO_DESCRIPTION
-- SysConfig Value: N
-- 2024-08-20T15:32:18.853Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', Name='org.compiere.model.MInvoice.ANNOTATE_DOCNO_INVOICE_TO_DESCRIPTION',Updated=TO_TIMESTAMP('2024-08-20 18:32:18.852','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_SysConfig_ID=541730
;

-- SysConfig Name: org.compiere.model.MInvoice.ANNOTATE_DOCNO_INVOICE_TO_DESCRIPTION
-- SysConfig Value: N
-- 2024-08-20T15:34:17.222Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', Description='If set to Y, then metasfresh will add the documentno to the description when the invoice gets voided.',Updated=TO_TIMESTAMP('2024-08-20 18:34:17.219','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_SysConfig_ID=541730
;

