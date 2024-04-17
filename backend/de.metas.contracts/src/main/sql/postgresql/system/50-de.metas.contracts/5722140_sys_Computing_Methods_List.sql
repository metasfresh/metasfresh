-- Run mode: SWING_CLIENT

-- Reference: Modular Contract Type Handler
-- Value: ShipmentLineForSO_Modular
-- ValueName: ShipmentLineForSO_Modular
-- 2024-04-15T14:08:49.460Z
UPDATE AD_Ref_List SET IsActive='N',Updated=TO_TIMESTAMP('2024-04-15 17:08:49.46','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543584
;



-- Run mode: SWING_CLIENT

-- Name: Computing Methods
-- 2024-04-16T07:58:20.185Z
UPDATE AD_Reference SET Description='Computing Methods List', Name='Computing Methods',Updated=TO_TIMESTAMP('2024-04-16 10:58:20.175','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Reference_ID=541838
;

-- 2024-04-16T07:58:20.241Z
UPDATE AD_Reference_Trl trl SET Description='Computing Methods List',Name='Computing Methods' WHERE AD_Reference_ID=541838 AND AD_Language='de_DE'
;

-- Reference: Computing Methods
-- Value: ImportLog
-- ValueName: ImportLog
-- 2024-04-16T07:59:07Z
UPDATE AD_Ref_List SET Description='Not used for now. Will be used in a future increment.',Updated=TO_TIMESTAMP('2024-04-16 10:59:06.999','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543592
;

-- 2024-04-16T07:59:07.006Z
UPDATE AD_Ref_List_Trl trl SET Description='Not used for now. Will be used in a future increment.' WHERE AD_Ref_List_ID=543592 AND AD_Language='de_DE'
;

-- Reference: Computing Methods
-- Value: ImportLog
-- ValueName: ImportLog
-- 2024-04-16T08:17:00.748Z
UPDATE AD_Ref_List SET IsActive='N',Updated=TO_TIMESTAMP('2024-04-16 11:17:00.748','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543592
;

-- Reference: Computing Methods
-- Value: ImportLog
-- ValueName: ImportLog
-- 2024-04-16T08:17:14.972Z
UPDATE AD_Ref_List SET Description='Will be used in a future increment.',Updated=TO_TIMESTAMP('2024-04-16 11:17:14.972','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543592
;

-- 2024-04-16T08:17:14.980Z
UPDATE AD_Ref_List_Trl trl SET Description='Will be used in a future increment.' WHERE AD_Ref_List_ID=543592 AND AD_Language='de_DE'
;

-- Reference: Computing Methods
-- Value: Interim
-- ValueName: Interim_Contract
-- 2024-04-16T08:20:40.103Z
UPDATE AD_Ref_List SET Value='Interim',Updated=TO_TIMESTAMP('2024-04-16 11:20:40.103','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543568
;




-- Run mode: SWING_CLIENT

-- Reference: Computing Methods
-- Value: MaterialReceiptLine_Interim
-- ValueName: MaterialReceiptLine_Interim
-- 2024-04-17T12:31:58.009Z
UPDATE AD_Ref_List SET Description='Covered by Interim', IsActive='N',Updated=TO_TIMESTAMP('2024-04-17 15:31:58.009','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543570
;

-- 2024-04-17T12:31:58.015Z
UPDATE AD_Ref_List_Trl trl SET Description='Covered by Interim' WHERE AD_Ref_List_ID=543570 AND AD_Language='de_DE'
;

-- Reference: Computing Methods
-- Value: PurchaseInvoiceLine_Interim
-- ValueName: PurchaseInvoiceLine_Interim
-- 2024-04-17T12:32:21.342Z
UPDATE AD_Ref_List SET Description='Covered by Interim', IsActive='N',Updated=TO_TIMESTAMP('2024-04-17 15:32:21.342','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543573
;

-- 2024-04-17T12:32:21.343Z
UPDATE AD_Ref_List_Trl trl SET Description='Covered by Interim' WHERE AD_Ref_List_ID=543573 AND AD_Language='de_DE'
;

-- Reference: Computing Methods
-- Value: ShipmentLineForSO_Modular
-- ValueName: ShipmentLineForSO_Modular
-- 2024-04-17T12:33:05.519Z
UPDATE AD_Ref_List SET Description='Will be used in a future increment.',Updated=TO_TIMESTAMP('2024-04-17 15:33:05.519','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543584
;

-- 2024-04-17T12:33:05.520Z
UPDATE AD_Ref_List_Trl trl SET Description='Will be used in a future increment.' WHERE AD_Ref_List_ID=543584 AND AD_Language='de_DE'
;

-- Reference: Computing Methods
-- Value: Receipt
-- ValueName: Receipt
-- 2024-04-17T12:34:31.924Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541838,543663,TO_TIMESTAMP('2024-04-17 15:34:31.8','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Receipt',TO_TIMESTAMP('2024-04-17 15:34:31.8','YYYY-MM-DD HH24:MI:SS.US'),100,'Receipt','Receipt')
;

-- 2024-04-17T12:34:31.928Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543663 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: Computing Methods
-- Value: MaterialReceiptLine_Modular
-- ValueName: MaterialReceiptLine_Modular
-- 2024-04-17T12:34:48.853Z
UPDATE AD_Ref_List SET Description='Cover by Receipt', IsActive='N',Updated=TO_TIMESTAMP('2024-04-17 15:34:48.853','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543571
;

-- 2024-04-17T12:34:48.854Z
UPDATE AD_Ref_List_Trl trl SET Description='Cover by Receipt' WHERE AD_Ref_List_ID=543571 AND AD_Language='de_DE'
;

-- Reference: Computing Methods
-- Value: PurchaseModularContract
-- ValueName: PurchaseModularContract
-- 2024-04-17T12:35:02.134Z
UPDATE AD_Ref_List SET Description='Covered by Receipt', IsActive='N',Updated=TO_TIMESTAMP('2024-04-17 15:35:02.134','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543574
;

-- 2024-04-17T12:35:02.135Z
UPDATE AD_Ref_List_Trl trl SET Description='Covered by Receipt' WHERE AD_Ref_List_ID=543574 AND AD_Language='de_DE'
;

-- Reference: Computing Methods
-- Value: PurchaseOrderLine_Modular
-- ValueName: PurchaseOrderLine_Modular
-- 2024-04-17T12:35:18.449Z
UPDATE AD_Ref_List SET Description='Covered by Receipt', IsActive='N',Updated=TO_TIMESTAMP('2024-04-17 15:35:18.449','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543575
;

-- 2024-04-17T12:35:18.449Z
UPDATE AD_Ref_List_Trl trl SET Description='Covered by Receipt' WHERE AD_Ref_List_ID=543575 AND AD_Language='de_DE'
;

-- Reference: Computing Methods
-- Value: SalesOrderLine_Modular
-- ValueName: SalesOrderLine_Modular
-- 2024-04-17T12:35:52.168Z
UPDATE AD_Ref_List SET Description='Will be used in a future increment.', IsActive='N',Updated=TO_TIMESTAMP('2024-04-17 15:35:52.168','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543580
;

-- 2024-04-17T12:35:52.169Z
UPDATE AD_Ref_List_Trl trl SET Description='Will be used in a future increment.' WHERE AD_Ref_List_ID=543580 AND AD_Language='de_DE'
;

-- Reference: Computing Methods
-- Value: SalesOrderLineProForma_Modular
-- ValueName: SalesOrderLineProForma_Modular
-- 2024-04-17T12:36:04.303Z
UPDATE AD_Ref_List SET Description='Will be used in a future increment.', IsActive='N',Updated=TO_TIMESTAMP('2024-04-17 15:36:04.303','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543581
;

-- 2024-04-17T12:36:04.303Z
UPDATE AD_Ref_List_Trl trl SET Description='Will be used in a future increment.' WHERE AD_Ref_List_ID=543581 AND AD_Language='de_DE'
;

-- Reference: Computing Methods
-- Value: ShippingNotificationForSales_Modular
-- ValueName: ShippingNotificationForSales_Modular
-- 2024-04-17T12:36:13.673Z
UPDATE AD_Ref_List SET Description='Will be used in a future increment.', IsActive='N',Updated=TO_TIMESTAMP('2024-04-17 15:36:13.673','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543586
;

-- 2024-04-17T12:36:13.674Z
UPDATE AD_Ref_List_Trl trl SET Description='Will be used in a future increment.' WHERE AD_Ref_List_ID=543586 AND AD_Language='de_DE'
;

-- Reference: Computing Methods
-- Value: ProForma
-- ValueName: ProForma
-- 2024-04-17T12:52:26.637Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541838,543664,TO_TIMESTAMP('2024-04-17 15:52:26.514','YYYY-MM-DD HH24:MI:SS.US'),100,'U','Y','ProForma',TO_TIMESTAMP('2024-04-17 15:52:26.514','YYYY-MM-DD HH24:MI:SS.US'),100,'ProForma','ProForma')
;

-- 2024-04-17T12:52:26.639Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543664 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: Computing Methods
-- Value: ProForma
-- ValueName: ProForma
-- 2024-04-17T12:53:00.104Z
UPDATE AD_Ref_List SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2024-04-17 15:53:00.103','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543664
;

-- Reference: Computing Methods
-- Value: SalesOrderLineProFormaPO_Modular
-- ValueName: SalesOrderLineProFormaPO_Modular
-- 2024-04-17T12:53:15.260Z
UPDATE AD_Ref_List SET Description='Covered by ProForma', IsActive='N',Updated=TO_TIMESTAMP('2024-04-17 15:53:15.26','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543582
;

-- 2024-04-17T12:53:15.260Z
UPDATE AD_Ref_List_Trl trl SET Description='Covered by ProForma' WHERE AD_Ref_List_ID=543582 AND AD_Language='de_DE'
;

-- Reference: Computing Methods
-- Value: DefinitiveInvoice
-- ValueName: DefinitiveInvoice
-- 2024-04-17T12:53:56.535Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541838,543665,TO_TIMESTAMP('2024-04-17 15:53:56.425','YYYY-MM-DD HH24:MI:SS.US'),100,'U','Y','DefinitiveInvoice',TO_TIMESTAMP('2024-04-17 15:53:56.425','YYYY-MM-DD HH24:MI:SS.US'),100,'DefinitiveInvoice','DefinitiveInvoice')
;

-- 2024-04-17T12:53:56.536Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543665 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: Computing Methods
-- Value: InventoryLine_Modular
-- ValueName: InventoryLine_Modular
-- 2024-04-17T12:54:14.491Z
UPDATE AD_Ref_List SET Description='Will be covered by DefinitiveInvoice', IsActive='N',Updated=TO_TIMESTAMP('2024-04-17 15:54:14.491','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543569
;

-- 2024-04-17T12:54:14.492Z
UPDATE AD_Ref_List_Trl trl SET Description='Will be covered by DefinitiveInvoice' WHERE AD_Ref_List_ID=543569 AND AD_Language='de_DE'
;

-- Reference: Computing Methods
-- Value: SalesContractProForma_Modular
-- ValueName: SalesContractProForma_Modular
-- 2024-04-17T12:54:46.454Z
UPDATE AD_Ref_List SET Description='Will be coveerd by ProForma', IsActive='N',Updated=TO_TIMESTAMP('2024-04-17 15:54:46.454','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543577
;

-- 2024-04-17T12:54:46.455Z
UPDATE AD_Ref_List_Trl trl SET Description='Will be coveerd by ProForma' WHERE AD_Ref_List_ID=543577 AND AD_Language='de_DE'
;

-- Reference: Computing Methods
-- Value: SalesInvoiceLine_Modular
-- ValueName: SalesInvoiceLine_Modular
-- 2024-04-17T12:57:20.623Z
UPDATE AD_Ref_List SET Description='Will be used in a future increment.', IsActive='N',Updated=TO_TIMESTAMP('2024-04-17 15:57:20.623','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543578
;

-- 2024-04-17T12:57:20.624Z
UPDATE AD_Ref_List_Trl trl SET Description='Will be used in a future increment.' WHERE AD_Ref_List_ID=543578 AND AD_Language='de_DE'
;

-- Reference: Computing Methods
-- Value: SOLineForPO_Modular
-- ValueName: SOLineForPO_Modular
-- 2024-04-17T12:58:01.089Z
UPDATE AD_Ref_List SET Description='Will be used in a future increment.', IsActive='N',Updated=TO_TIMESTAMP('2024-04-17 15:58:01.089','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543576
;

-- 2024-04-17T12:58:01.090Z
UPDATE AD_Ref_List_Trl trl SET Description='Will be used in a future increment.' WHERE AD_Ref_List_ID=543576 AND AD_Language='de_DE'
;

-- Reference: Computing Methods
-- Value: SalesModularContract
-- ValueName: SalesModularContract
-- 2024-04-17T12:58:21.379Z
UPDATE AD_Ref_List SET Description='Will be used in a future increment.', IsActive='N',Updated=TO_TIMESTAMP('2024-04-17 15:58:21.379','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543579
;

-- 2024-04-17T12:58:21.380Z
UPDATE AD_Ref_List_Trl trl SET Description='Will be used in a future increment.' WHERE AD_Ref_List_ID=543579 AND AD_Language='de_DE'
;

-- Reference: Computing Methods
-- Value: RawProductSales
-- ValueName: RawProductSales
-- 2024-04-17T12:59:55.937Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541838,543666,TO_TIMESTAMP('2024-04-17 15:59:55.819','YYYY-MM-DD HH24:MI:SS.US'),100,'U','Y','Raw Product Sales',TO_TIMESTAMP('2024-04-17 15:59:55.819','YYYY-MM-DD HH24:MI:SS.US'),100,'RawProductSales','RawProductSales')
;

-- 2024-04-17T12:59:55.938Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543666 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: Computing Methods
-- Value: DefinitiveInvoice
-- ValueName: DefinitiveInvoice
-- 2024-04-17T12:59:59.545Z
UPDATE AD_Ref_List SET Name='Definitive Invoice',Updated=TO_TIMESTAMP('2024-04-17 15:59:59.545','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543665
;

-- 2024-04-17T12:59:59.546Z
UPDATE AD_Ref_List_Trl trl SET Name='Definitive Invoice' WHERE AD_Ref_List_ID=543665 AND AD_Language='de_DE'
;

-- Reference: Computing Methods
-- Value: SalesOnRawProduct
-- ValueName: RawProductSales
-- 2024-04-17T13:00:52.818Z
UPDATE AD_Ref_List SET Name='Sales On Raw Product', Value='SalesOnRawProduct',Updated=TO_TIMESTAMP('2024-04-17 16:00:52.818','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543666
;

-- 2024-04-17T13:00:52.819Z
UPDATE AD_Ref_List_Trl trl SET Name='Sales On Raw Product' WHERE AD_Ref_List_ID=543666 AND AD_Language='de_DE'
;

-- Reference: Computing Methods
-- Value: SalesOnRawProduct
-- ValueName: SalesOnRawProduct
-- 2024-04-17T13:00:57.684Z
UPDATE AD_Ref_List SET ValueName='SalesOnRawProduct',Updated=TO_TIMESTAMP('2024-04-17 16:00:57.684','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543666
;

-- Reference: Computing Methods
-- Value: SalesOnProcessedProduct
-- ValueName: SalesOnProcessedProduct
-- 2024-04-17T13:02:15.167Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541838,543667,TO_TIMESTAMP('2024-04-17 16:02:15.051','YYYY-MM-DD HH24:MI:SS.US'),100,'U','Y','Sales On Processed Product',TO_TIMESTAMP('2024-04-17 16:02:15.051','YYYY-MM-DD HH24:MI:SS.US'),100,'SalesOnProcessedProduct','SalesOnProcessedProduct')
;

-- 2024-04-17T13:02:15.168Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543667 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: Computing Methods
-- Value: CoProduct
-- ValueName: CoProduct
-- 2024-04-17T13:02:40.317Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541838,543668,TO_TIMESTAMP('2024-04-17 16:02:40.192','YYYY-MM-DD HH24:MI:SS.US'),100,'U','Y','Co-Product',TO_TIMESTAMP('2024-04-17 16:02:40.192','YYYY-MM-DD HH24:MI:SS.US'),100,'CoProduct','CoProduct')
;

-- 2024-04-17T13:02:40.318Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543668 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: Computing Methods
-- Value: AddValueOnRawProduct
-- ValueName: AddValueOnRawProduct
-- 2024-04-17T13:03:18.948Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541838,543669,TO_TIMESTAMP('2024-04-17 16:03:18.798','YYYY-MM-DD HH24:MI:SS.US'),100,'U','Y','Add Value On Raw Product',TO_TIMESTAMP('2024-04-17 16:03:18.798','YYYY-MM-DD HH24:MI:SS.US'),100,'AddValueOnRawProduct','AddValueOnRawProduct')
;

-- 2024-04-17T13:03:18.949Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543669 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: Computing Methods
-- Value: AddValueOnProcessedProduct
-- ValueName: AddValueOnProcessedProduct
-- 2024-04-17T13:03:49.271Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541838,543670,TO_TIMESTAMP('2024-04-17 16:03:49.142','YYYY-MM-DD HH24:MI:SS.US'),100,'U','Y',' Add Value On Processed Product',TO_TIMESTAMP('2024-04-17 16:03:49.142','YYYY-MM-DD HH24:MI:SS.US'),100,'AddValueOnProcessedProduct','AddValueOnProcessedProduct')
;

-- 2024-04-17T13:03:49.273Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543670 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: Computing Methods
-- Value: SubtractValueOnRawProduct
-- ValueName: SubtractValueOnRawProduct
-- 2024-04-17T13:04:19.959Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541838,543671,TO_TIMESTAMP('2024-04-17 16:04:19.767','YYYY-MM-DD HH24:MI:SS.US'),100,'U','Y','Subtract Value On Raw Product',TO_TIMESTAMP('2024-04-17 16:04:19.767','YYYY-MM-DD HH24:MI:SS.US'),100,'SubtractValueOnRawProduct','SubtractValueOnRawProduct')
;

-- 2024-04-17T13:04:19.960Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543671 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: Computing Methods
-- Value: AddValueOnProcessedProduct
-- ValueName: AddValueOnProcessedProduct
-- 2024-04-17T13:04:22.471Z
UPDATE AD_Ref_List SET Name='Add Value On Processed Product',Updated=TO_TIMESTAMP('2024-04-17 16:04:22.471','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543670
;

-- 2024-04-17T13:04:22.472Z
UPDATE AD_Ref_List_Trl trl SET Name='Add Value On Processed Product' WHERE AD_Ref_List_ID=543670 AND AD_Language='de_DE'
;

-- Reference: Computing Methods
-- Value: ReductionCalibration
-- ValueName: ReductionCalibration
-- 2024-04-17T13:05:14.241Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541838,543672,TO_TIMESTAMP('2024-04-17 16:05:14.121','YYYY-MM-DD HH24:MI:SS.US'),100,'U','Y','Reduction/ Calibration',TO_TIMESTAMP('2024-04-17 16:05:14.121','YYYY-MM-DD HH24:MI:SS.US'),100,'ReductionCalibration','ReductionCalibration')
;

-- 2024-04-17T13:05:14.242Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543672 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: Computing Methods
-- Value: StorageCost
-- ValueName: StorageCost
-- 2024-04-17T13:05:51.374Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541838,543673,TO_TIMESTAMP('2024-04-17 16:05:51.242','YYYY-MM-DD HH24:MI:SS.US'),100,'U','Y','Storage Cost',TO_TIMESTAMP('2024-04-17 16:05:51.242','YYYY-MM-DD HH24:MI:SS.US'),100,'StorageCost','StorageCost')
;

-- 2024-04-17T13:05:51.375Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543673 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: Computing Methods
-- Value: AverageBonusOnQuantity
-- ValueName: Average Bonus On Quantity
-- 2024-04-17T13:12:16.847Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541838,543674,TO_TIMESTAMP('2024-04-17 16:12:16.674','YYYY-MM-DD HH24:MI:SS.US'),100,'U','Y','Average Bonus On Quantity',TO_TIMESTAMP('2024-04-17 16:12:16.674','YYYY-MM-DD HH24:MI:SS.US'),100,'AverageBonusOnQuantity','Average Bonus On Quantity')
;

-- 2024-04-17T13:12:16.848Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543674 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: Computing Methods
-- Value: AverageBonusOnQuantity
-- ValueName: AverageBonusOnQuantity
-- 2024-04-17T13:12:31.939Z
UPDATE AD_Ref_List SET EntityType='de.metas.contracts', ValueName='AverageBonusOnQuantity',Updated=TO_TIMESTAMP('2024-04-17 16:12:31.939','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543674
;

-- Reference: Computing Methods
-- Value: SubtractValueOnRawProduct
-- ValueName: SubtractValueOnRawProduct
-- 2024-04-17T13:12:36.463Z
UPDATE AD_Ref_List SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2024-04-17 16:12:36.463','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543671
;

-- Reference: Computing Methods
-- Value: StorageCost
-- ValueName: StorageCost
-- 2024-04-17T13:12:37.134Z
UPDATE AD_Ref_List SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2024-04-17 16:12:37.134','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543673
;

-- Reference: Computing Methods
-- Value: ReductionCalibration
-- ValueName: ReductionCalibration
-- 2024-04-17T13:12:37.699Z
UPDATE AD_Ref_List SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2024-04-17 16:12:37.699','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543672
;

-- Reference: Computing Methods
-- Value: AddValueOnProcessedProduct
-- ValueName: AddValueOnProcessedProduct
-- 2024-04-17T13:12:38.159Z
UPDATE AD_Ref_List SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2024-04-17 16:12:38.159','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543670
;

-- Reference: Computing Methods
-- Value: AddValueOnRawProduct
-- ValueName: AddValueOnRawProduct
-- 2024-04-17T13:12:38.567Z
UPDATE AD_Ref_List SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2024-04-17 16:12:38.567','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543669
;

-- Reference: Computing Methods
-- Value: CoProduct
-- ValueName: CoProduct
-- 2024-04-17T13:12:39.536Z
UPDATE AD_Ref_List SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2024-04-17 16:12:39.536','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543668
;

-- Reference: Computing Methods
-- Value: SalesOnProcessedProduct
-- ValueName: SalesOnProcessedProduct
-- 2024-04-17T13:12:40.639Z
UPDATE AD_Ref_List SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2024-04-17 16:12:40.639','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543667
;

-- Reference: Computing Methods
-- Value: SalesOnRawProduct
-- ValueName: SalesOnRawProduct
-- 2024-04-17T13:12:41.425Z
UPDATE AD_Ref_List SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2024-04-17 16:12:41.425','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543666
;

-- Reference: Computing Methods
-- Value: DefinitiveInvoice
-- ValueName: DefinitiveInvoice
-- 2024-04-17T13:12:45.551Z
UPDATE AD_Ref_List SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2024-04-17 16:12:45.551','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543665
;

-- Reference: Computing Methods
-- Value: PPCostCollector_Modular
-- ValueName: PPCostCollector_Modular
-- 2024-04-17T13:13:27.664Z
UPDATE AD_Ref_List SET Description='Will be covered by Co-Product and SalesOnProcessedProduct', IsActive='N',Updated=TO_TIMESTAMP('2024-04-17 16:13:27.664','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543572
;

-- 2024-04-17T13:13:27.665Z
UPDATE AD_Ref_List_Trl trl SET Description='Will be covered by Co-Product and SalesOnProcessedProduct' WHERE AD_Ref_List_ID=543572 AND AD_Language='de_DE'
;

-- Reference: Computing Methods
-- Value: SalesModularContract
-- ValueName: SalesModularContract_NotUsed
-- 2024-04-17T13:15:37.838Z
UPDATE AD_Ref_List SET ValueName='SalesModularContract_NotUsed',Updated=TO_TIMESTAMP('2024-04-17 16:15:37.838','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543579
;

-- Reference: Computing Methods
-- Value: AverageAddedValueOnShippedQuantity
-- ValueName: AverageAddedValueOnShippedQuantity
-- 2024-04-17T13:20:52.170Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541838,543675,TO_TIMESTAMP('2024-04-17 16:20:52.042','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Average Added Value On Shipped Quantity',TO_TIMESTAMP('2024-04-17 16:20:52.042','YYYY-MM-DD HH24:MI:SS.US'),100,'AverageAddedValueOnShippedQuantity','AverageAddedValueOnShippedQuantity')
;

-- 2024-04-17T13:20:52.172Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543675 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: Computing Methods
-- Value: SalesInvoiceLine_Modular_NotUsed
-- ValueName: SalesInvoiceLine_Modular
-- 2024-04-17T13:21:35.796Z
UPDATE AD_Ref_List SET Value='SalesInvoiceLine_Modular_NotUsed',Updated=TO_TIMESTAMP('2024-04-17 16:21:35.796','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543578
;

-- Reference: Computing Methods
-- Value: SalesContractProForma_Modular
-- ValueName: SalesContractProForma_Modular_NotUsed
-- 2024-04-17T13:21:45.141Z
UPDATE AD_Ref_List SET ValueName='SalesContractProForma_Modular_NotUsed',Updated=TO_TIMESTAMP('2024-04-17 16:21:45.141','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543577
;

-- Reference: Computing Methods
-- Value: ImportLog
-- ValueName: ImportLog_NotUsed
-- 2024-04-17T13:21:50.822Z
UPDATE AD_Ref_List SET ValueName='ImportLog_NotUsed',Updated=TO_TIMESTAMP('2024-04-17 16:21:50.822','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543592
;

-- Reference: Computing Methods
-- Value: InventoryLine_Modular
-- ValueName: InventoryLine_Modular_NotUsed
-- 2024-04-17T13:21:54.860Z
UPDATE AD_Ref_List SET ValueName='InventoryLine_Modular_NotUsed',Updated=TO_TIMESTAMP('2024-04-17 16:21:54.86','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543569
;

-- Reference: Computing Methods
-- Value: MaterialReceiptLine_Interim
-- ValueName: MaterialReceiptLine_Interim_NotUsed
-- 2024-04-17T13:22:00.137Z
UPDATE AD_Ref_List SET ValueName='MaterialReceiptLine_Interim_NotUsed',Updated=TO_TIMESTAMP('2024-04-17 16:22:00.136','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543570
;

-- Reference: Computing Methods
-- Value: MaterialReceiptLine_Modular
-- ValueName: MaterialReceiptLine_Modular_NotUsed
-- 2024-04-17T13:22:03.871Z
UPDATE AD_Ref_List SET ValueName='MaterialReceiptLine_Modular_NotUsed',Updated=TO_TIMESTAMP('2024-04-17 16:22:03.871','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543571
;

-- Reference: Computing Methods
-- Value: PurchaseInvoiceLine_Interim
-- ValueName: PurchaseInvoiceLine_Interim_NotUsed
-- 2024-04-17T13:22:07.876Z
UPDATE AD_Ref_List SET ValueName='PurchaseInvoiceLine_Interim_NotUsed',Updated=TO_TIMESTAMP('2024-04-17 16:22:07.876','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543573
;

-- Reference: Computing Methods
-- Value: PurchaseModularContract
-- ValueName: PurchaseModularContract_NotUsed
-- 2024-04-17T13:22:11.597Z
UPDATE AD_Ref_List SET ValueName='PurchaseModularContract_NotUsed',Updated=TO_TIMESTAMP('2024-04-17 16:22:11.597','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543574
;

-- Reference: Computing Methods
-- Value: PPCostCollector_Modular
-- ValueName: PPCostCollector_Modular_NotUsed
-- 2024-04-17T13:22:45.503Z
UPDATE AD_Ref_List SET ValueName='PPCostCollector_Modular_NotUsed',Updated=TO_TIMESTAMP('2024-04-17 16:22:45.503','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543572
;

-- Reference: Computing Methods
-- Value: PurchaseOrderLine_Modular
-- ValueName: PurchaseOrderLine_Modular_NotUsed
-- 2024-04-17T13:22:54.195Z
UPDATE AD_Ref_List SET ValueName='PurchaseOrderLine_Modular_NotUsed',Updated=TO_TIMESTAMP('2024-04-17 16:22:54.195','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543575
;

-- Reference: Computing Methods
-- Value: SalesInvoiceLine_Modular_NotUsed
-- ValueName: SalesInvoiceLine_Modular_NotUsed
-- 2024-04-17T13:23:03.610Z
UPDATE AD_Ref_List SET ValueName='SalesInvoiceLine_Modular_NotUsed',Updated=TO_TIMESTAMP('2024-04-17 16:23:03.61','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543578
;

-- Reference: Computing Methods
-- Value: SalesOrderLine_Modular
-- ValueName: SalesOrderLine_Modular_NotUsed
-- 2024-04-17T13:23:10.375Z
UPDATE AD_Ref_List SET ValueName='SalesOrderLine_Modular_NotUsed',Updated=TO_TIMESTAMP('2024-04-17 16:23:10.375','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543580
;

-- Reference: Computing Methods
-- Value: SalesOrderLineProForma_Modular
-- ValueName: SalesOrderLineProForma_Modular_NotUsed
-- 2024-04-17T13:23:14.628Z
UPDATE AD_Ref_List SET ValueName='SalesOrderLineProForma_Modular_NotUsed',Updated=TO_TIMESTAMP('2024-04-17 16:23:14.628','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543581
;

-- Reference: Computing Methods
-- Value: SalesOrderLineProFormaPO_Modular
-- ValueName: SalesOrderLineProFormaPO_Modular_NotUsed
-- 2024-04-17T13:23:20.194Z
UPDATE AD_Ref_List SET ValueName='SalesOrderLineProFormaPO_Modular_NotUsed',Updated=TO_TIMESTAMP('2024-04-17 16:23:20.194','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543582
;

-- Reference: Computing Methods
-- Value: ShipmentLineForSO_Modular
-- ValueName: ShipmentLineForSO_Modular_NotUsed
-- 2024-04-17T13:23:24.130Z
UPDATE AD_Ref_List SET ValueName='ShipmentLineForSO_Modular_NotUsed',Updated=TO_TIMESTAMP('2024-04-17 16:23:24.13','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543584
;

-- Reference: Computing Methods
-- Value: ShippingNotificationForSales_Modular
-- ValueName: ShippingNotificationForSales_Modular_NotUsed
-- 2024-04-17T13:23:28.061Z
UPDATE AD_Ref_List SET ValueName='ShippingNotificationForSales_Modular_NotUsed',Updated=TO_TIMESTAMP('2024-04-17 16:23:28.061','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543586
;

-- Reference: Computing Methods
-- Value: SOLineForPO_Modular
-- ValueName: SOLineForPO_Modular_NotUsed
-- 2024-04-17T13:23:32.071Z
UPDATE AD_Ref_List SET ValueName='SOLineForPO_Modular_NotUsed',Updated=TO_TIMESTAMP('2024-04-17 16:23:32.071','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543576
;

-- Reference: Computing Methods
-- Value: AddValueOnInterim
-- ValueName: AddValueOnInterim
-- 2024-04-17T13:30:41.327Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541838,543676,TO_TIMESTAMP('2024-04-17 16:30:41.199','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Add Value On Interim',TO_TIMESTAMP('2024-04-17 16:30:41.199','YYYY-MM-DD HH24:MI:SS.US'),100,'AddValueOnInterim','AddValueOnInterim')
;

-- 2024-04-17T13:30:41.328Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543676 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: Computing Methods
-- Value: SubtractValueOnInterim
-- ValueName: SubtractValueOnInterim
-- 2024-04-17T13:31:07.533Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541838,543677,TO_TIMESTAMP('2024-04-17 16:31:07.399','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Subtract Value On Interim',TO_TIMESTAMP('2024-04-17 16:31:07.399','YYYY-MM-DD HH24:MI:SS.US'),100,'SubtractValueOnInterim','SubtractValueOnInterim')
;

-- 2024-04-17T13:31:07.534Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543677 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: Computing Methods
-- Value: ShippingNotificationForPurchase_Modular
-- ValueName: ShippingNotificationForPurchase_Modular
-- 2024-04-17T13:38:07.490Z
UPDATE AD_Ref_List SET Description='Will be covered by AddValueOnInterim and SubtractValueOnInterim', IsActive='N',Updated=TO_TIMESTAMP('2024-04-17 16:38:07.49','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543585
;

-- 2024-04-17T13:38:07.491Z
UPDATE AD_Ref_List_Trl trl SET Description='Will be covered by AddValueOnInterim and SubtractValueOnInterim' WHERE AD_Ref_List_ID=543585 AND AD_Language='de_DE'
;

-- Reference: Computing Methods
-- Value: ShipmentLineForPO_Modular
-- ValueName: ShipmentLineForPO_Modular
-- 2024-04-17T13:38:35.021Z
UPDATE AD_Ref_List SET Description='Will be used in a future increment', IsActive='N',Updated=TO_TIMESTAMP('2024-04-17 16:38:35.021','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543583
;

-- 2024-04-17T13:38:35.022Z
UPDATE AD_Ref_List_Trl trl SET Description='Will be used in a future increment' WHERE AD_Ref_List_ID=543583 AND AD_Language='de_DE'
;

-- Reference: Computing Methods
-- Value: ShipmentLineForPO_Modular
-- ValueName: ShipmentLineForPO_Modular_NotUsed
-- 2024-04-17T13:38:49.440Z
UPDATE AD_Ref_List SET IsActive='Y', ValueName='ShipmentLineForPO_Modular_NotUsed',Updated=TO_TIMESTAMP('2024-04-17 16:38:49.44','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543583
;

-- Reference: Computing Methods
-- Value: ShippingNotificationForPurchase_Modular
-- ValueName: ShippingNotificationForPurchase_Modular_NotUsed
-- 2024-04-17T13:38:54.343Z
UPDATE AD_Ref_List SET IsActive='Y', ValueName='ShippingNotificationForPurchase_Modular_NotUsed',Updated=TO_TIMESTAMP('2024-04-17 16:38:54.342','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543585
;

-- Reference: Computing Methods
-- Value: ShipmentLineForPO_Modular
-- ValueName: ShipmentLineForPO_Modular_NotUsed
-- 2024-04-17T13:39:14.131Z
UPDATE AD_Ref_List SET IsActive='N',Updated=TO_TIMESTAMP('2024-04-17 16:39:14.13','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543583
;

-- Reference: Computing Methods
-- Value: ShippingNotificationForPurchase_Modular
-- ValueName: ShippingNotificationForPurchase_Modular_NotUsed
-- 2024-04-17T13:39:17.273Z
UPDATE AD_Ref_List SET IsActive='N',Updated=TO_TIMESTAMP('2024-04-17 16:39:17.273','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543585
;



-- Reference: Computing Methods
-- Value: AverageBonusOnQuantity
-- ValueName: AverageBonusOnQuantity
-- 2024-04-17T13:49:36.776Z
DELETE FROM  AD_Ref_List_Trl WHERE AD_Ref_List_ID=543674
;

-- 2024-04-17T13:49:36.780Z
DELETE FROM AD_Ref_List WHERE AD_Ref_List_ID=543674
;




