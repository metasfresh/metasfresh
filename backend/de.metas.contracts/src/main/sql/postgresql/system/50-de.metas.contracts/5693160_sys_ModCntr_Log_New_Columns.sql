-- 2023-06-23T14:50:07.538008300Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582470,0,'ModCntr_Log_DocumentType',TO_TIMESTAMP('2023-06-23 17:50:07.02','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Document Type','Document Type',TO_TIMESTAMP('2023-06-23 17:50:07.02','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-23T14:50:07.578195200Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582470 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Name: ModCntr_Log_DocumentType
-- 2023-06-23T14:50:50.831898200Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541770,TO_TIMESTAMP('2023-06-23 17:50:50.407','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','ModCntr_Log_DocumentType',TO_TIMESTAMP('2023-06-23 17:50:50.407','YYYY-MM-DD HH24:MI:SS.US'),100,'L')
;

-- 2023-06-23T14:50:50.877915100Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541770 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: ModCntr_Log_DocumentType
-- Value: PurchaseOrder
-- ValueName: Purchase Order
-- 2023-06-23T14:52:07.800670100Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541770,543507,TO_TIMESTAMP('2023-06-23 17:52:07.313','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Purchase Order',TO_TIMESTAMP('2023-06-23 17:52:07.313','YYYY-MM-DD HH24:MI:SS.US'),100,'PurchaseOrder','Purchase Order')
;

-- 2023-06-23T14:52:07.837422300Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543507 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: ModCntr_Log_DocumentType -> PurchaseOrder_Purchase Order
-- 2023-06-23T14:52:15.916312700Z
UPDATE AD_Ref_List_Trl SET Name='Bestellung',Updated=TO_TIMESTAMP('2023-06-23 17:52:15.916','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543507
;

-- Reference Item: ModCntr_Log_DocumentType -> PurchaseOrder_Purchase Order
-- 2023-06-23T14:52:19.124016400Z
UPDATE AD_Ref_List_Trl SET Name='Bestellung',Updated=TO_TIMESTAMP('2023-06-23 17:52:19.124','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543507
;

-- 2023-06-23T14:52:19.162291700Z
UPDATE AD_Ref_List SET Name='Bestellung' WHERE AD_Ref_List_ID=543507
;

-- Reference: ModCntr_Log_DocumentType
-- Value: SupplyAgreement
-- ValueName: Supply Agreement
-- 2023-06-23T14:52:37.188310900Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541770,543508,TO_TIMESTAMP('2023-06-23 17:52:36.691','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Supply Agreement',TO_TIMESTAMP('2023-06-23 17:52:36.691','YYYY-MM-DD HH24:MI:SS.US'),100,'SupplyAgreement','Supply Agreement')
;

-- 2023-06-23T14:52:37.224245900Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543508 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: ModCntr_Log_DocumentType -> SupplyAgreement_Supply Agreement
-- 2023-06-23T14:52:41.698698800Z
UPDATE AD_Ref_List_Trl SET Name='Liefervereinbarung',Updated=TO_TIMESTAMP('2023-06-23 17:52:41.698','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543508
;

-- 2023-06-23T14:52:41.734931800Z
UPDATE AD_Ref_List SET Name='Liefervereinbarung' WHERE AD_Ref_List_ID=543508
;

-- Reference Item: ModCntr_Log_DocumentType -> SupplyAgreement_Supply Agreement
-- 2023-06-23T14:52:44.567589600Z
UPDATE AD_Ref_List_Trl SET Name='Liefervereinbarung',Updated=TO_TIMESTAMP('2023-06-23 17:52:44.567','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543508
;

-- Reference: ModCntr_Log_DocumentType
-- Value: MaterialReceipt
-- ValueName: Material Receipt
-- 2023-06-23T14:52:57.288825Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541770,543509,TO_TIMESTAMP('2023-06-23 17:52:56.84','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Material Receipt',TO_TIMESTAMP('2023-06-23 17:52:56.84','YYYY-MM-DD HH24:MI:SS.US'),100,'MaterialReceipt','Material Receipt')
;

-- 2023-06-23T14:52:57.324823300Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543509 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: ModCntr_Log_DocumentType -> MaterialReceipt_Material Receipt
-- 2023-06-23T14:53:33.700573700Z
UPDATE AD_Ref_List_Trl SET Name='Wareneingang',Updated=TO_TIMESTAMP('2023-06-23 17:53:33.7','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543509
;

-- Reference Item: ModCntr_Log_DocumentType -> MaterialReceipt_Material Receipt
-- 2023-06-23T14:53:36.079531400Z
UPDATE AD_Ref_List_Trl SET Name='Wareneingang',Updated=TO_TIMESTAMP('2023-06-23 17:53:36.079','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543509
;

-- 2023-06-23T14:53:36.116051700Z
UPDATE AD_Ref_List SET Name='Wareneingang' WHERE AD_Ref_List_ID=543509
;

-- Reference: ModCntr_Log_DocumentType
-- Value: Production
-- ValueName: Production
-- 2023-06-23T14:53:47.242480300Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541770,543510,TO_TIMESTAMP('2023-06-23 17:53:46.748','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Production',TO_TIMESTAMP('2023-06-23 17:53:46.748','YYYY-MM-DD HH24:MI:SS.US'),100,'Production','Production')
;

-- 2023-06-23T14:53:47.278495400Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543510 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: ModCntr_Log_DocumentType -> Production_Production
-- 2023-06-23T14:53:51.567277400Z
UPDATE AD_Ref_List_Trl SET Name='Produktion',Updated=TO_TIMESTAMP('2023-06-23 17:53:51.566','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543510
;

-- 2023-06-23T14:53:51.662025100Z
UPDATE AD_Ref_List SET Name='Produktion' WHERE AD_Ref_List_ID=543510
;

-- Reference Item: ModCntr_Log_DocumentType -> Production_Production
-- 2023-06-23T14:53:54.033790300Z
UPDATE AD_Ref_List_Trl SET Name='Produktion',Updated=TO_TIMESTAMP('2023-06-23 17:53:54.033','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543510
;

-- Reference: ModCntr_Log_DocumentType
-- Value: ContractPrefinancing
-- ValueName: Contract Prefinancing
-- 2023-06-23T14:54:16.706848500Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541770,543511,TO_TIMESTAMP('2023-06-23 17:54:16.226','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Contract Prefinancing',TO_TIMESTAMP('2023-06-23 17:54:16.226','YYYY-MM-DD HH24:MI:SS.US'),100,'ContractPrefinancing','Contract Prefinancing')
;

-- 2023-06-23T14:54:16.742524700Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543511 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: ModCntr_Log_DocumentType -> ContractPrefinancing_Contract Prefinancing
-- 2023-06-23T14:54:37.583566600Z
UPDATE AD_Ref_List_Trl SET Name='Vertrag Vorfinanzierung',Updated=TO_TIMESTAMP('2023-06-23 17:54:37.583','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543511
;

-- Reference Item: ModCntr_Log_DocumentType -> ContractPrefinancing_Contract Prefinancing
-- 2023-06-23T14:54:39.957865600Z
UPDATE AD_Ref_List_Trl SET Name='Vertrag Vorfinanzierung',Updated=TO_TIMESTAMP('2023-06-23 17:54:39.957','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543511
;

-- 2023-06-23T14:54:40.038983300Z
UPDATE AD_Ref_List SET Name='Vertrag Vorfinanzierung' WHERE AD_Ref_List_ID=543511
;

-- Reference: ModCntr_Log_DocumentType
-- Value: InterimInvoice
-- ValueName: Interim Invoice
-- 2023-06-23T15:29:18.341092300Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541770,543512,TO_TIMESTAMP('2023-06-23 18:29:17.792','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Interim Invoice',TO_TIMESTAMP('2023-06-23 18:29:17.792','YYYY-MM-DD HH24:MI:SS.US'),100,'InterimInvoice','Interim Invoice')
;

-- 2023-06-23T15:29:18.378155100Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543512 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: ModCntr_Log_DocumentType -> InterimInvoice_Interim Invoice
-- 2023-06-23T15:29:24.177847400Z
UPDATE AD_Ref_List_Trl SET Name='Akonto Rechnung',Updated=TO_TIMESTAMP('2023-06-23 18:29:24.177','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543512
;

-- 2023-06-23T15:29:24.213970600Z
UPDATE AD_Ref_List SET Name='Akonto Rechnung' WHERE AD_Ref_List_ID=543512
;

-- Reference Item: ModCntr_Log_DocumentType -> InterimInvoice_Interim Invoice
-- 2023-06-23T15:29:26.750444600Z
UPDATE AD_Ref_List_Trl SET Name='Akonto Rechnung',Updated=TO_TIMESTAMP('2023-06-23 18:29:26.75','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543512
;

-- Reference: ModCntr_Log_DocumentType
-- Value: ContractSetting
-- ValueName: Contract Setting
-- 2023-06-23T15:29:48.328472500Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541770,543513,TO_TIMESTAMP('2023-06-23 18:29:47.825','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Contract Setting',TO_TIMESTAMP('2023-06-23 18:29:47.825','YYYY-MM-DD HH24:MI:SS.US'),100,'ContractSetting','Contract Setting')
;

-- 2023-06-23T15:29:48.364454600Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543513 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: ModCntr_Log_DocumentType -> ContractSetting_Contract Setting
-- 2023-06-23T15:30:19.727374300Z
UPDATE AD_Ref_List_Trl SET Name='Einstellungen Vertrag',Updated=TO_TIMESTAMP('2023-06-23 18:30:19.727','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543513
;

-- Reference Item: ModCntr_Log_DocumentType -> ContractSetting_Contract Setting
-- 2023-06-23T15:30:22.558679200Z
UPDATE AD_Ref_List_Trl SET Name='Einstellungen Vertrag',Updated=TO_TIMESTAMP('2023-06-23 18:30:22.558','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543513
;

-- 2023-06-23T15:30:22.594201600Z
UPDATE AD_Ref_List SET Name='Einstellungen Vertrag' WHERE AD_Ref_List_ID=543513
;

-- Reference: ModCntr_Log_DocumentType
-- Value: SalesOrder
-- ValueName: Sales Order
-- 2023-06-23T15:30:40.738838200Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541770,543514,TO_TIMESTAMP('2023-06-23 18:30:40.214','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Sales Order',TO_TIMESTAMP('2023-06-23 18:30:40.214','YYYY-MM-DD HH24:MI:SS.US'),100,'SalesOrder','Sales Order')
;

-- 2023-06-23T15:30:40.779177800Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543514 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: ModCntr_Log_DocumentType -> SalesOrder_Sales Order
-- 2023-06-23T15:30:51.339575300Z
UPDATE AD_Ref_List_Trl SET Name='Auftrag',Updated=TO_TIMESTAMP('2023-06-23 18:30:51.339','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543514
;

-- 2023-06-23T15:30:51.375236400Z
UPDATE AD_Ref_List SET Name='Auftrag' WHERE AD_Ref_List_ID=543514
;

-- Reference Item: ModCntr_Log_DocumentType -> SalesOrder_Sales Order
-- 2023-06-23T15:30:54.102102500Z
UPDATE AD_Ref_List_Trl SET Name='Auftrag',Updated=TO_TIMESTAMP('2023-06-23 18:30:54.102','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543514
;

-- Reference: ModCntr_Log_DocumentType
-- Value: ShipmentDisposition
-- ValueName: Shipment Disposition
-- 2023-06-23T15:31:18.660878100Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541770,543515,TO_TIMESTAMP('2023-06-23 18:31:18.16','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Shipment Disposition',TO_TIMESTAMP('2023-06-23 18:31:18.16','YYYY-MM-DD HH24:MI:SS.US'),100,'ShipmentDisposition','Shipment Disposition')
;

-- 2023-06-23T15:31:18.698237400Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543515 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: ModCntr_Log_DocumentType -> ShipmentDisposition_Shipment Disposition
-- 2023-06-23T15:31:25.974341100Z
UPDATE AD_Ref_List_Trl SET Name='Lieferavis',Updated=TO_TIMESTAMP('2023-06-23 18:31:25.973','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543515
;

-- 2023-06-23T15:31:26.010320400Z
UPDATE AD_Ref_List SET Name='Lieferavis' WHERE AD_Ref_List_ID=543515
;

-- Reference Item: ModCntr_Log_DocumentType -> ShipmentDisposition_Shipment Disposition
-- 2023-06-23T15:31:28.946960400Z
UPDATE AD_Ref_List_Trl SET Name='Lieferavis',Updated=TO_TIMESTAMP('2023-06-23 18:31:28.946','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543515
;

-- Reference: ModCntr_Log_DocumentType
-- Value: Shipment
-- ValueName: Shipment
-- 2023-06-23T15:31:41.870221400Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541770,543516,TO_TIMESTAMP('2023-06-23 18:31:41.394','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Shipment',TO_TIMESTAMP('2023-06-23 18:31:41.394','YYYY-MM-DD HH24:MI:SS.US'),100,'Shipment','Shipment')
;

-- 2023-06-23T15:31:41.907613Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543516 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: ModCntr_Log_DocumentType -> Shipment_Shipment
-- 2023-06-23T15:31:49.269533300Z
UPDATE AD_Ref_List_Trl SET Name='Lieferung',Updated=TO_TIMESTAMP('2023-06-23 18:31:49.269','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543516
;

-- Reference Item: ModCntr_Log_DocumentType -> Shipment_Shipment
-- 2023-06-23T15:31:51.776246800Z
UPDATE AD_Ref_List_Trl SET Name='Lieferung',Updated=TO_TIMESTAMP('2023-06-23 18:31:51.775','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543516
;

-- 2023-06-23T15:31:51.812084400Z
UPDATE AD_Ref_List SET Name='Lieferung' WHERE AD_Ref_List_ID=543516
;

-- Reference: ModCntr_Log_DocumentType
-- Value: FinalSettlement
-- ValueName: Final Settlement
-- 2023-06-23T15:37:40.684501500Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541770,543517,TO_TIMESTAMP('2023-06-23 18:37:40.216','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Final Settlement',TO_TIMESTAMP('2023-06-23 18:37:40.216','YYYY-MM-DD HH24:MI:SS.US'),100,'FinalSettlement','Final Settlement')
;

-- 2023-06-23T15:37:40.720698100Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543517 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: ModCntr_Log_DocumentType -> FinalSettlement_Final Settlement
-- 2023-06-23T15:37:46.145945700Z
UPDATE AD_Ref_List_Trl SET Name='Schlussabrechnung',Updated=TO_TIMESTAMP('2023-06-23 18:37:46.145','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543517
;

-- 2023-06-23T15:37:46.182208900Z
UPDATE AD_Ref_List SET Name='Schlussabrechnung' WHERE AD_Ref_List_ID=543517
;

-- Reference Item: ModCntr_Log_DocumentType -> FinalSettlement_Final Settlement
-- 2023-06-23T15:37:48.524686400Z
UPDATE AD_Ref_List_Trl SET Name='Schlussabrechnung',Updated=TO_TIMESTAMP('2023-06-23 18:37:48.524','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543517
;

-- Reference: ModCntr_Log_DocumentType
-- Value: DefinitiveFinalSettlement
-- ValueName: Definitive Final Settlement
-- 2023-06-23T15:38:05.405851100Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541770,543518,TO_TIMESTAMP('2023-06-23 18:38:04.903','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Definitive Final Settlement',TO_TIMESTAMP('2023-06-23 18:38:04.903','YYYY-MM-DD HH24:MI:SS.US'),100,'DefinitiveFinalSettlement','Definitive Final Settlement')
;

-- 2023-06-23T15:38:05.444930200Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543518 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: ModCntr_Log_DocumentType -> DefinitiveFinalSettlement_Definitive Final Settlement
-- 2023-06-23T15:38:12.558972700Z
UPDATE AD_Ref_List_Trl SET Name='Definitive Schlussabrechnung ',Updated=TO_TIMESTAMP('2023-06-23 18:38:12.558','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543518
;

-- Reference Item: ModCntr_Log_DocumentType -> DefinitiveFinalSettlement_Definitive Final Settlement
-- 2023-06-23T15:38:16.276240700Z
UPDATE AD_Ref_List_Trl SET Name='Definitive Schlussabrechnung ',Updated=TO_TIMESTAMP('2023-06-23 18:38:16.276','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543518
;

-- 2023-06-23T15:38:16.312595700Z
UPDATE AD_Ref_List SET Name='Definitive Schlussabrechnung ' WHERE AD_Ref_List_ID=543518
;

-- Column: ModCntr_Log.ModCntr_Log_DocumentType
-- 2023-06-23T15:51:01.905886500Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586851,582470,0,17,541770,542338,'ModCntr_Log_DocumentType',TO_TIMESTAMP('2023-06-23 18:51:01.327','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,250,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Document Type',0,0,TO_TIMESTAMP('2023-06-23 18:51:01.327','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-23T15:51:01.943537800Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586851 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-23T15:51:03.741467400Z
/* DDL */  select update_Column_Translation_From_AD_Element(582470) 
;

-- 2023-06-23T15:51:08.854799100Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Log','ALTER TABLE public.ModCntr_Log ADD COLUMN ModCntr_Log_DocumentType VARCHAR(250)')
;

-- Column: ModCntr_Log.Qty
-- 2023-06-23T15:53:05.098995900Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586852,526,0,29,542338,'Qty',TO_TIMESTAMP('2023-06-23 18:53:04.571','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Menge','D',0,10,'Menge bezeichnet die Anzahl eines bestimmten Produktes oder Artikels für dieses Dokument.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Menge',0,0,TO_TIMESTAMP('2023-06-23 18:53:04.571','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-23T15:53:05.137200Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586852 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-23T15:53:06.434874900Z
/* DDL */  select update_Column_Translation_From_AD_Element(526) 
;

-- 2023-06-23T15:53:11.718870300Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Log','ALTER TABLE public.ModCntr_Log ADD COLUMN Qty NUMERIC')
;

-- 2023-06-23T15:54:07.664774Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582471,0,'Harvesting_Year_ID',TO_TIMESTAMP('2023-06-23 18:54:07.176','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Harvesting Year','Harvesting Year',TO_TIMESTAMP('2023-06-23 18:54:07.176','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-23T15:54:07.702249300Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582471 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: Harvesting_Year_ID
-- 2023-06-23T15:56:12.472810400Z
UPDATE AD_Element_Trl SET Name='Erntejahr', PrintName='Erntejahr',Updated=TO_TIMESTAMP('2023-06-23 18:56:12.472','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582471 AND AD_Language='de_CH'
;

-- 2023-06-23T15:56:12.548589500Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582471,'de_CH') 
;

-- Element: Harvesting_Year_ID
-- 2023-06-23T15:56:16.035492800Z
UPDATE AD_Element_Trl SET Name='Erntejahr', PrintName='Erntejahr',Updated=TO_TIMESTAMP('2023-06-23 18:56:16.035','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582471 AND AD_Language='de_DE'
;

-- 2023-06-23T15:56:16.071028700Z
UPDATE AD_Element SET Name='Erntejahr', PrintName='Erntejahr' WHERE AD_Element_ID=582471
;

-- 2023-06-23T15:56:16.912058800Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582471,'de_DE') 
;

-- 2023-06-23T15:56:16.948081200Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582471,'de_DE') 
;

-- Column: ModCntr_Log.Harvesting_Year_ID
-- 2023-06-23T15:56:46.578732Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586853,582471,0,30,540133,542338,'Harvesting_Year_ID',TO_TIMESTAMP('2023-06-23 18:56:45.945','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Erntejahr',0,0,TO_TIMESTAMP('2023-06-23 18:56:45.945','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-23T15:56:46.637820700Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586853 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-23T15:56:47.901169400Z
/* DDL */  select update_Column_Translation_From_AD_Element(582471) 
;

-- 2023-06-23T15:56:52.888403200Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Log','ALTER TABLE public.ModCntr_Log ADD COLUMN Harvesting_Year_ID NUMERIC(10)')
;

-- 2023-06-23T15:56:52.932084400Z
ALTER TABLE ModCntr_Log ADD CONSTRAINT HarvestingYear_ModCntrLog FOREIGN KEY (Harvesting_Year_ID) REFERENCES public.C_Year DEFERRABLE INITIALLY DEFERRED
;

-- Column: ModCntr_Log.C_UOM_ID
-- 2023-06-23T15:58:11.461342300Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586854,215,0,19,542338,'C_UOM_ID',TO_TIMESTAMP('2023-06-23 18:58:10.68','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Maßeinheit','D',0,10,'Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Maßeinheit',0,0,TO_TIMESTAMP('2023-06-23 18:58:10.68','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-23T15:58:11.499155800Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586854 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-23T15:58:13.047089700Z
/* DDL */  select update_Column_Translation_From_AD_Element(215) 
;

-- 2023-06-23T15:58:18.703564900Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Log','ALTER TABLE public.ModCntr_Log ADD COLUMN C_UOM_ID NUMERIC(10)')
;

-- 2023-06-23T15:58:18.749613200Z
ALTER TABLE ModCntr_Log ADD CONSTRAINT CUOM_ModCntrLog FOREIGN KEY (C_UOM_ID) REFERENCES public.C_UOM DEFERRABLE INITIALLY DEFERRED
;

-- Column: ModCntr_Log.Amount
-- 2023-06-23T15:58:37.947117600Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586855,1367,0,12,542338,'Amount',TO_TIMESTAMP('2023-06-23 18:58:37.419','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Betrag in einer definierten Währung','D',0,10,'"Betrag" gibt den Betrag für diese Dokumentenposition an','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Betrag',0,0,TO_TIMESTAMP('2023-06-23 18:58:37.419','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-23T15:58:37.984160800Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586855 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-23T15:58:39.308671300Z
/* DDL */  select update_Column_Translation_From_AD_Element(1367) 
;

-- 2023-06-23T15:58:44.514497800Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Log','ALTER TABLE public.ModCntr_Log ADD COLUMN Amount NUMERIC NOT NULL')
;

-- Column: ModCntr_Log.C_Currency_ID
-- 2023-06-23T15:58:59.861947Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586856,193,0,19,542338,'C_Currency_ID',TO_TIMESTAMP('2023-06-23 18:58:59.135','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Die Währung für diesen Eintrag','D',0,10,'Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Währung',0,0,TO_TIMESTAMP('2023-06-23 18:58:59.135','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-23T15:58:59.897918700Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586856 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-23T15:59:01.075376900Z
/* DDL */  select update_Column_Translation_From_AD_Element(193) 
;

-- 2023-06-23T15:59:06.353249300Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Log','ALTER TABLE public.ModCntr_Log ADD COLUMN C_Currency_ID NUMERIC(10)')
;

-- 2023-06-23T15:59:06.396313900Z
ALTER TABLE ModCntr_Log ADD CONSTRAINT CCurrency_ModCntrLog FOREIGN KEY (C_Currency_ID) REFERENCES public.C_Currency DEFERRABLE INITIALLY DEFERRED
;

-- Column: ModCntr_Log.Bill_BPartner_ID
-- 2023-06-23T15:59:37.269593900Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586857,2039,0,30,541252,542338,'Bill_BPartner_ID',TO_TIMESTAMP('2023-06-23 18:59:36.407','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Geschäftspartner für die Rechnungsstellung','D',0,10,'Wenn leer, wird die Rechnung an den Geschäftspartner der Lieferung gestellt','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Rechnungspartner',0,0,TO_TIMESTAMP('2023-06-23 18:59:36.407','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-23T15:59:37.306933800Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586857 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-23T15:59:38.242826800Z
/* DDL */  select update_Column_Translation_From_AD_Element(2039) 
;

-- 2023-06-23T15:59:43.227331800Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Log','ALTER TABLE public.ModCntr_Log ADD COLUMN Bill_BPartner_ID NUMERIC(10)')
;

-- 2023-06-23T15:59:43.271234400Z
ALTER TABLE ModCntr_Log ADD CONSTRAINT BillBPartner_ModCntrLog FOREIGN KEY (Bill_BPartner_ID) REFERENCES public.C_BPartner DEFERRABLE INITIALLY DEFERRED
;

