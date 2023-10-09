UPDATE modcntr_type type
SET modularcontracthandlertype = (CASE
                                      WHEN type_temp.classname = 'de.metas.contracts.modular.interim.logImpl.InterimContractHandler' THEN 'Interim_Contract'
                                      WHEN type_temp.classname = 'de.metas.contracts.modular.impl.InventoryLineModularContractHandler' THEN 'InventoryLine_Modular'
                                      WHEN type_temp.classname = 'de.metas.contracts.modular.interim.logImpl.MaterialReceiptLineInterimContractHandler' THEN 'MaterialReceiptLine_Interim'
                                      WHEN type_temp.classname = 'de.metas.contracts.modular.impl.MaterialReceiptLineModularContractHandler' THEN 'MaterialReceiptLine_Modular'
                                      WHEN type_temp.classname = 'de.metas.handlingunits.modular.impl.PPCostCollectorModularContractHandler' THEN 'PPCostCollector_Modular'
                                      WHEN type_temp.classname = 'de.metas.contracts.modular.interim.logImpl.PurchaseInvoiceLineInterimHandler' THEN 'PurchaseInvoiceLine_Interim'
                                      WHEN type_temp.classname = 'de.metas.contracts.modular.impl.PurchaseModularContractHandler' THEN 'PurchaseModularContract'
                                      WHEN type_temp.classname = 'de.metas.contracts.modular.impl.PurchaseOrderLineModularContractHandler' THEN 'PurchaseOrderLine_Modular'
                                      WHEN type_temp.classname = 'de.metas.contracts.modular.impl.SOLineForPOModularContractHandler' THEN 'SOLineForPO_Modular'
                                      WHEN type_temp.classname = 'de.metas.contracts.modular.impl.SalesContractProFormaModularContractHandler' THEN 'SalesContractProForma_Modular'
                                      WHEN type_temp.classname = 'de.metas.contracts.modular.impl.SalesInvoiceLineModularContractHandler' THEN 'SalesInvoiceLine_Modular'
                                      WHEN type_temp.classname = 'de.metas.contracts.modular.impl.SalesModularContractHandler' THEN 'SalesModularContract'
                                      WHEN type_temp.classname = 'de.metas.contracts.modular.impl.SalesOrderLineModularContractHandler' THEN 'SalesOrderLine_Modular'
                                      WHEN type_temp.classname = 'de.metas.contracts.modular.impl.SalesOrderLineProFormaModularContractHandler' THEN 'SalesOrderLineProForma_Modular'
                                      WHEN type_temp.classname = 'de.metas.contracts.modular.impl.SalesOrderLineProFormaPOModularContractHandler' THEN 'SalesOrderLineProFormaPO_Modular'
                                      WHEN type_temp.classname = 'de.metas.contracts.modular.impl.ShipmentLineForPOModularContractHandler' THEN 'ShipmentLineForPO_Modular'
                                      WHEN type_temp.classname = 'de.metas.contracts.modular.impl.ShipmentLineForSOModularContractHandler' THEN 'ShipmentLineForSO_Modular'
                                      WHEN type_temp.classname = 'de.metas.contracts.modular.impl.ShippingNotificationForPurchaseModularContractHandler' THEN 'ShippingNotificationForPurchase_Modular'
                                      WHEN type_temp.classname = 'de.metas.contracts.modular.impl.ShippingNotificationForSalesModularContractHandler' THEN 'ShippingNotificationForSales_Modular'
                                  END)
FROM modcntr_type_temp type_temp
WHERE type.modcntr_type_id = type_temp.modcntr_type_id
;

DROP TABLE public.ModCntr_Type_Temp
;