-- 2024-08-27T08:18:26.583Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (1000000,1000000,541733,'O',TO_TIMESTAMP('2024-08-27 11:18:26','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','de.metas.product.impl.ProductBL.ShipAndReceiveNonStockItems',TO_TIMESTAMP('2024-08-27 11:18:26','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

-- 2024-08-27T08:19:28.116Z
UPDATE AD_SysConfig SET ConfigurationLevel='O', Description='When the value is on Y, the quantity and date to invoice in an invoice candidate are calculated based on the delivery date of shipments or receipts of any kind of "Item" type products, stocked or not stocked.',Updated=TO_TIMESTAMP('2024-08-27 11:19:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541733
;

-- 2024-08-27T08:20:34.402Z
UPDATE AD_SysConfig SET ConfigurationLevel='O', Description='When ''Y'', the quantity and date to invoice in an invoice candidate with the invoicing rule "After Delivery" are calculated based on the delivery date of shipments or receipts for any kind of "Item" type products, stocked or not stocked.',Updated=TO_TIMESTAMP('2024-08-27 11:20:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541733
;

-- 2024-08-27T08:23:26.907Z
UPDATE AD_SysConfig SET ConfigurationLevel='O', Description='When ''Y'', the quantity and date to invoice in an invoice candidate with the invoicing rule "After Delivery" are calculated based on the delivery date of shipments or receipts for any kind of "Item" type products, stocked or not stocked.
When ''N'', the quantity and date to invoice in an invoice candidate with the invoicing rule "After Delivery" are calculated based on the delivery date of shipments or receipts only for the stocked products of type "Item". If the item product is not stocked, the quantity and date to invoice are taken from the order. This is the default behavior.',Updated=TO_TIMESTAMP('2024-08-27 11:23:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541733
;

-- 2024-08-27T08:23:38.682Z
UPDATE AD_SysConfig SET ConfigurationLevel='O', EntityType='de.metas.invoicecandidate',Updated=TO_TIMESTAMP('2024-08-27 11:23:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541733
;

-- 2024-08-27T08:23:45.325Z
UPDATE AD_SysConfig SET AD_Org_ID=0, ConfigurationLevel='O',Updated=TO_TIMESTAMP('2024-08-27 11:23:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541733
;

