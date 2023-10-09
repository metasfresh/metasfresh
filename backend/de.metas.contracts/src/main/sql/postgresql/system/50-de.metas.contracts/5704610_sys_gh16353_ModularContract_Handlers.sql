-- Run mode: SWING_CLIENT

-- Name: Modular Contract Type Handler
-- 2023-10-06T11:16:54.197Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541838,TO_TIMESTAMP('2023-10-06 14:16:53.967','YYYY-MM-DD HH24:MI:SS.US'),100,'Modular Contract Type Handler List','de.metas.contracts','Y','N','Modular Contract Type Handler',TO_TIMESTAMP('2023-10-06 14:16:53.967','YYYY-MM-DD HH24:MI:SS.US'),100,'L')
;

-- 2023-10-06T11:16:54.213Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541838 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: Modular Contract Type Handler
-- Value: Interim_Contract
-- ValueName: Interim_Contract
-- 2023-10-06T11:18:29.446Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543568,541838,TO_TIMESTAMP('2023-10-06 14:18:29.316','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Interim Contract',TO_TIMESTAMP('2023-10-06 14:18:29.316','YYYY-MM-DD HH24:MI:SS.US'),100,'Interim_Contract','Interim_Contract')
;

-- 2023-10-06T11:18:29.448Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543568 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: Modular Contract Type Handler
-- Value: InventoryLine_Modular
-- ValueName: InventoryLine_Modular
-- 2023-10-06T11:20:06.075Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543569,541838,TO_TIMESTAMP('2023-10-06 14:20:05.961','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Inventory Line -> Modular Contract',TO_TIMESTAMP('2023-10-06 14:20:05.961','YYYY-MM-DD HH24:MI:SS.US'),100,'InventoryLine_Modular','InventoryLine_Modular')
;

-- 2023-10-06T11:20:06.079Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543569 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: Modular Contract Type Handler
-- Value: MaterialReceiptLine_Interim
-- ValueName: MaterialReceiptLine_Interim
-- 2023-10-06T11:21:27.801Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543570,541838,TO_TIMESTAMP('2023-10-06 14:21:27.703','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Material Receipt Line -> Interim Contract',TO_TIMESTAMP('2023-10-06 14:21:27.703','YYYY-MM-DD HH24:MI:SS.US'),100,'MaterialReceiptLine_Interim','MaterialReceiptLine_Interim')
;

-- 2023-10-06T11:21:27.802Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543570 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: Modular Contract Type Handler
-- Value: MaterialReceiptLine_Modular
-- ValueName: MaterialReceiptLine_Modular
-- 2023-10-06T11:22:05.521Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543571,541838,TO_TIMESTAMP('2023-10-06 14:22:05.401','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Material Receipt Line -> Modular Contract',TO_TIMESTAMP('2023-10-06 14:22:05.401','YYYY-MM-DD HH24:MI:SS.US'),100,'MaterialReceiptLine_Modular','MaterialReceiptLine_Modular')
;

-- 2023-10-06T11:22:05.523Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543571 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: Modular Contract Type Handler
-- Value: PPCostCollector_Modular
-- ValueName: PPCostCollector_Modular
-- 2023-10-06T11:23:42.948Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543572,541838,TO_TIMESTAMP('2023-10-06 14:23:42.824','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Manufacturing Cost Collector -> Modular Contract',TO_TIMESTAMP('2023-10-06 14:23:42.824','YYYY-MM-DD HH24:MI:SS.US'),100,'PPCostCollector_Modular','PPCostCollector_Modular')
;

-- 2023-10-06T11:23:42.949Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543572 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: Modular Contract Type Handler
-- Value: PurchaseInvoiceLine_Interim
-- ValueName: Purchase Invoice Line -> Interim Contract
-- 2023-10-06T11:24:26.614Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543573,541838,TO_TIMESTAMP('2023-10-06 14:24:26.497','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Purchase Invoice Line -> Interim Contract',TO_TIMESTAMP('2023-10-06 14:24:26.497','YYYY-MM-DD HH24:MI:SS.US'),100,'PurchaseInvoiceLine_Interim','PurchaseInvoiceLine_Interim')
;

-- 2023-10-06T11:24:26.616Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543573 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: Modular Contract Type Handler
-- Value: PurchaseModularContract
-- ValueName: PurchaseModularContract
-- 2023-10-06T11:26:59.780Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543574,541838,TO_TIMESTAMP('2023-10-06 14:26:59.648','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Purchase Modular Contract',TO_TIMESTAMP('2023-10-06 14:26:59.648','YYYY-MM-DD HH24:MI:SS.US'),100,'PurchaseModularContract','PurchaseModularContract')
;

-- 2023-10-06T11:26:59.788Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543574 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: Modular Contract Type Handler
-- Value: PurchaseOrderLine_Modular
-- ValueName: PurchaseOrderLine_Modular
-- 2023-10-06T11:27:44.392Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543575,541838,TO_TIMESTAMP('2023-10-06 14:27:44.279','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','PurchaseOrderLine -> Modular Contract',TO_TIMESTAMP('2023-10-06 14:27:44.279','YYYY-MM-DD HH24:MI:SS.US'),100,'PurchaseOrderLine_Modular','PurchaseOrderLine_Modular')
;

-- 2023-10-06T11:27:44.393Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543575 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: Modular Contract Type Handler
-- Value: SOLineForPO_Modular
-- ValueName: SOLineForPO_Modular
-- 2023-10-06T11:28:33.528Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543576,541838,TO_TIMESTAMP('2023-10-06 14:28:33.394','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Sales Order Line For Purchase Order -> Modular Contract',TO_TIMESTAMP('2023-10-06 14:28:33.394','YYYY-MM-DD HH24:MI:SS.US'),100,'SOLineForPO_Modular','SOLineForPO_Modular')
;

-- 2023-10-06T11:28:33.530Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543576 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: Modular Contract Type Handler
-- Value: SalesContractProForma_Modular
-- ValueName: Sales Contract Pro Forma -> Modular Contract
-- 2023-10-06T11:29:20.468Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543577,541838,TO_TIMESTAMP('2023-10-06 14:29:20.329','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Sales Contract Pro Forma -> Modular Contract',TO_TIMESTAMP('2023-10-06 14:29:20.329','YYYY-MM-DD HH24:MI:SS.US'),100,'SalesContractProForma_Modular','SalesContractProForma_Modular')
;

-- 2023-10-06T11:29:20.470Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543577 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: Modular Contract Type Handler
-- Value: SalesInvoiceLine_Modular
-- ValueName: SalesInvoiceLine_Modular
-- 2023-10-06T11:29:50.269Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543578,541838,TO_TIMESTAMP('2023-10-06 14:29:50.146','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Sales Invoice Line -> Modular Contract',TO_TIMESTAMP('2023-10-06 14:29:50.146','YYYY-MM-DD HH24:MI:SS.US'),100,'SalesInvoiceLine_Modular','SalesInvoiceLine_Modular')
;

-- 2023-10-06T11:29:50.270Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543578 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: Modular Contract Type Handler
-- Value: SalesModularContract
-- ValueName: SalesModularContract
-- 2023-10-06T11:30:15.635Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543579,541838,TO_TIMESTAMP('2023-10-06 14:30:15.513','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Sales Modular Contract',TO_TIMESTAMP('2023-10-06 14:30:15.513','YYYY-MM-DD HH24:MI:SS.US'),100,'SalesModularContract','SalesModularContract')
;

-- 2023-10-06T11:30:15.637Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543579 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: Modular Contract Type Handler
-- Value: SalesOrderLine_Modular
-- ValueName: SalesOrderLine_Modular
-- 2023-10-06T11:30:47.527Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543580,541838,TO_TIMESTAMP('2023-10-06 14:30:47.402','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Sales Order Line -> Modular Contract',TO_TIMESTAMP('2023-10-06 14:30:47.402','YYYY-MM-DD HH24:MI:SS.US'),100,'SalesOrderLine_Modular','SalesOrderLine_Modular')
;

-- 2023-10-06T11:30:47.529Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543580 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: Modular Contract Type Handler
-- Value: SalesOrderLineProForma_Modular
-- ValueName: SalesOrderLineProForma_Modular
-- 2023-10-06T11:31:35.401Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543581,541838,TO_TIMESTAMP('2023-10-06 14:31:35.285','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Sales Order Line Pro Forma -> Modular Contract',TO_TIMESTAMP('2023-10-06 14:31:35.285','YYYY-MM-DD HH24:MI:SS.US'),100,'SalesOrderLineProForma_Modular','SalesOrderLineProForma_Modular')
;

-- 2023-10-06T11:31:35.403Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543581 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: Modular Contract Type Handler
-- Value: SalesOrderLineProFormaPO_Modular
-- ValueName: SalesOrderLineProFormaPO_Modular
-- 2023-10-06T11:32:15.736Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543582,541838,TO_TIMESTAMP('2023-10-06 14:32:15.612','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Sales Order Line Pro Forma PO -> Modular Contract',TO_TIMESTAMP('2023-10-06 14:32:15.612','YYYY-MM-DD HH24:MI:SS.US'),100,'SalesOrderLineProFormaPO_Modular','SalesOrderLineProFormaPO_Modular')
;

-- 2023-10-06T11:32:15.736Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543582 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: Modular Contract Type Handler
-- Value: ShipmentLineForPO_Modular
-- ValueName: ShipmentLineForPO_Modular
-- 2023-10-06T11:33:04.040Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543583,541838,TO_TIMESTAMP('2023-10-06 14:33:03.921','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Shipment Line For PO -> Modular Contract',TO_TIMESTAMP('2023-10-06 14:33:03.921','YYYY-MM-DD HH24:MI:SS.US'),100,'ShipmentLineForPO_Modular','ShipmentLineForPO_Modular')
;

-- 2023-10-06T11:33:04.041Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543583 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: Modular Contract Type Handler
-- Value: ShipmentLineForSO_Modular
-- ValueName: ShipmentLineForSO_Modular
-- 2023-10-06T11:33:31.503Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543584,541838,TO_TIMESTAMP('2023-10-06 14:33:31.394','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Shipment Line For SO -> Modular Contract',TO_TIMESTAMP('2023-10-06 14:33:31.394','YYYY-MM-DD HH24:MI:SS.US'),100,'ShipmentLineForSO_Modular','ShipmentLineForSO_Modular')
;

-- 2023-10-06T11:33:31.504Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543584 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: Modular Contract Type Handler
-- Value: ShippingNotificationForPurchase_Modular
-- ValueName: ShippingNotificationForPurchase_Modular
-- 2023-10-06T11:34:11.024Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543585,541838,TO_TIMESTAMP('2023-10-06 14:34:10.864','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Shipping Notification For Purchase -> Modular Contract',TO_TIMESTAMP('2023-10-06 14:34:10.864','YYYY-MM-DD HH24:MI:SS.US'),100,'ShippingNotificationForPurchase_Modular','ShippingNotificationForPurchase_Modular')
;

-- 2023-10-06T11:34:11.026Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543585 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: Modular Contract Type Handler
-- Value: ShippingNotificationForSales_Modular
-- ValueName: ShippingNotificationForSales_Modular
-- 2023-10-06T11:34:38.391Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543586,541838,TO_TIMESTAMP('2023-10-06 14:34:38.256','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Shipping Notification For Sales -> Modular Contract',TO_TIMESTAMP('2023-10-06 14:34:38.256','YYYY-MM-DD HH24:MI:SS.US'),100,'ShippingNotificationForSales_Modular','ShippingNotificationForSales_Modular')
;

-- 2023-10-06T11:34:38.392Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543586 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

