-- SysConfig Name: de.metas.order.PurchaseOrder.UseDatePromisedForPricing
-- SysConfig Value: N (system default; override per client to Y to enable DatePromised)
-- 2026-06-17T14:24:46.414Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541822 /*From ID Server*/,'C',TO_TIMESTAMP('2026-06-17 14:24:46.222000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','de.metas.order.PurchaseOrder.UseDatePromisedForPricing',TO_TIMESTAMP('2026-06-17 14:24:46.222000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y')
;

-- SysConfig Name: de.metas.order.PurchaseOrder.UseDatePromisedForPricing
-- 2026-06-17T14:26:39.346Z
UPDATE AD_SysConfig SET ConfigurationLevel='C', Description='Controls which date is used to select the price list version on purchase orders. Y = DatePromised (Zugesagter Termin); N = DateOrdered (Auftragsdatum, default).', Value='N',Updated=TO_TIMESTAMP('2026-06-17 14:26:39.261000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_SysConfig_ID=541822
;
