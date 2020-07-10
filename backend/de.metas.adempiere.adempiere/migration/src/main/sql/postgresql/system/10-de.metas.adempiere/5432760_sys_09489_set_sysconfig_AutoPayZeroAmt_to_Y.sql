--
-- generally setting it to yes, because afaik there is no user-friendly way to deal with grandtotal=0-invoices. 
-- org.compiere.model.MInvoice.AutoPayZeroAmt
-- 09.11.2015 14:23
-- URL zum Konzept
UPDATE AD_SysConfig SET Value='Y',Updated=TO_TIMESTAMP('2015-11-09 14:23:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=50057
;

