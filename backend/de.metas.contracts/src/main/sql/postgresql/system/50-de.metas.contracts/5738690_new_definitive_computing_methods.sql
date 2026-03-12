
-- Reference: Computing Methods
-- Value: DefinitiveInvoiceProcessedProduct
-- ValueName: DefinitiveInvoiceProcessedProduct
-- 2024-11-08T07:50:24.444Z
UPDATE AD_Ref_List SET IsActive='Y',Updated=TO_TIMESTAMP('2024-11-08 08:50:24.444','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543683
;

-- Reference: Computing Methods
-- Value: DefinitiveInvoiceRawProduct
-- ValueName: DefinitiveInvoiceRawProduct
-- 2024-11-08T07:50:26.056Z
UPDATE AD_Ref_List SET IsActive='Y',Updated=TO_TIMESTAMP('2024-11-08 08:50:26.056','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543682
;

-- Reference: Computing Methods
-- Value: InformativeLogs
-- ValueName: InformativeLogs
-- 2024-11-08T07:50:26.979Z
UPDATE AD_Ref_List SET IsActive='Y',Updated=TO_TIMESTAMP('2024-11-08 08:50:26.979','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543679
;

-- Reference: Computing Methods
-- Value: SalesInformativeLogs
-- ValueName: SalesInformativeLogs
-- 2024-11-08T07:50:55.881Z
UPDATE AD_Ref_List SET IsActive='Y',Updated=TO_TIMESTAMP('2024-11-08 08:50:55.881','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543729
;

-- Reference Item: Computing Methods -> DefinitiveInvoiceProcessedProduct_DefinitiveInvoiceProcessedProduct
-- 2024-11-08T07:51:36.309Z
UPDATE AD_Ref_List_Trl SET Name='Endgültige Schlussabrechnung verarbeitetes Erzeugnis',Updated=TO_TIMESTAMP('2024-11-08 08:51:36.309','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543683
;

-- Reference Item: Computing Methods -> DefinitiveInvoiceProcessedProduct_DefinitiveInvoiceProcessedProduct
-- 2024-11-08T07:51:37.898Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-11-08 08:51:37.898','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543683
;

-- Reference Item: Computing Methods -> DefinitiveInvoiceProcessedProduct_DefinitiveInvoiceProcessedProduct
-- 2024-11-08T07:51:39.543Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-11-08 08:51:39.543','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543683
;

-- Reference Item: Computing Methods -> DefinitiveInvoiceProcessedProduct_DefinitiveInvoiceProcessedProduct
-- 2024-11-08T07:51:45.508Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Endgültige Schlussabrechnung verarbeitetes Erzeugnis',Updated=TO_TIMESTAMP('2024-11-08 08:51:45.508','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543683
;

-- 2024-11-08T07:51:45.511Z
UPDATE AD_Ref_List SET Name='Endgültige Schlussabrechnung verarbeitetes Erzeugnis' WHERE AD_Ref_List_ID=543683
;

-- Reference Item: Computing Methods -> DefinitiveInvoiceRawProduct_DefinitiveInvoiceRawProduct
-- 2024-11-08T07:58:39.909Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-11-08 08:58:39.909','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543682
;

-- Reference Item: Computing Methods -> DefinitiveInvoiceRawProduct_DefinitiveInvoiceRawProduct
-- 2024-11-08T07:58:57.585Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Endgültige Schlussabrechnung Rohprodukt',Updated=TO_TIMESTAMP('2024-11-08 08:58:57.585','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543682
;

-- Reference Item: Computing Methods -> DefinitiveInvoiceRawProduct_DefinitiveInvoiceRawProduct
-- 2024-11-08T07:59:03.069Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Endgültige Schlussabrechnung Rohprodukt',Updated=TO_TIMESTAMP('2024-11-08 08:59:03.069','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543682
;

-- 2024-11-08T07:59:03.070Z
UPDATE AD_Ref_List SET Name='Endgültige Schlussabrechnung Rohprodukt' WHERE AD_Ref_List_ID=543682
;

-- Reference: Computing Methods
-- Value: DefinitiveInvoiceAverageAVOnShippedQty
-- ValueName: DefinitiveInvoiceAverageAVOnShippedQty
-- 2024-11-08T08:04:08.936Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541838,543771,TO_TIMESTAMP('2024-11-08 09:04:08.618','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Endg. Schlussabr. Durchschn. Wert zu gel. Menge hinzufügen',TO_TIMESTAMP('2024-11-08 09:04:08.618','YYYY-MM-DD HH24:MI:SS.US'),100,'DefinitiveInvoiceAverageAVOnShippedQty','DefinitiveInvoiceAverageAVOnShippedQty')
;

-- 2024-11-08T08:04:08.938Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543771 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: Computing Methods -> DefinitiveInvoiceAverageAVOnShippedQty_DefinitiveInvoiceAverageAVOnShippedQty
-- 2024-11-08T08:04:26.712Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='DefinitiveInvoiceAverageAVOnShippedQty',Updated=TO_TIMESTAMP('2024-11-08 09:04:26.712','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543771
;

-- Reference Item: Computing Methods -> DefinitiveInvoiceAverageAVOnShippedQty_DefinitiveInvoiceAverageAVOnShippedQty
-- 2024-11-08T08:04:27.816Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-11-08 09:04:27.816','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543771
;

-- Reference Item: Computing Methods -> DefinitiveInvoiceAverageAVOnShippedQty_DefinitiveInvoiceAverageAVOnShippedQty
-- 2024-11-08T08:04:41.024Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-11-08 09:04:41.024','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543771
;

-- Reference: Computing Methods
-- Value: DefinitiveInvoiceStorageCost
-- ValueName: DefinitiveInvoiceStorageCost
-- 2024-11-08T08:05:23.156Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541838,543772,TO_TIMESTAMP('2024-11-08 09:05:22.927','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Endgültige Schlussabrechnung Lagerkosten',TO_TIMESTAMP('2024-11-08 09:05:22.927','YYYY-MM-DD HH24:MI:SS.US'),100,'DefinitiveInvoiceStorageCost','DefinitiveInvoiceStorageCost')
;

-- 2024-11-08T08:05:23.159Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543772 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: Computing Methods -> DefinitiveInvoiceStorageCost_DefinitiveInvoiceStorageCost
-- 2024-11-08T08:05:51.502Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Definitive Invoice Storage Costs',Updated=TO_TIMESTAMP('2024-11-08 09:05:51.502','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543772
;

-- Reference Item: Computing Methods -> DefinitiveInvoiceStorageCost_DefinitiveInvoiceStorageCost
-- 2024-11-08T08:05:52.318Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-11-08 09:05:52.318','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543772
;

-- Reference Item: Computing Methods -> DefinitiveInvoiceStorageCost_DefinitiveInvoiceStorageCost
-- 2024-11-08T08:05:54.350Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-11-08 09:05:54.35','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543772
;

-- Run mode: WEBUI

-- 2024-11-08T08:21:34.384Z
INSERT INTO ModCntr_Type (AD_Client_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,IsActive,IsSOTrx,ModCntr_Type_ID,ModularContractHandlerType,Name,Updated,UpdatedBy,Value) VALUES (1000000,1000000,'UserElementNumber1',TO_TIMESTAMP('2024-11-08 09:21:34.355','YYYY-MM-DD HH24:MI:SS.US'),100,'Erstellt die Lieferlogs inklusive HL für HL Rechnungspositionen und Details in der Endgültigen Schlusszahlung','Y','N',540034,'DefinitiveInvoiceAverageAVOnShippedQty','HL - Endgültige Schlussabrechnung durchschnittliche gelieferte Menge',TO_TIMESTAMP('2024-11-08 09:21:34.355','YYYY-MM-DD HH24:MI:SS.US'),100,'HL - Definitive Invoice Average AV On Shipped Qty')
;

-- 2024-11-08T08:25:36.642Z
INSERT INTO ModCntr_Type (AD_Client_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,IsActive,IsSOTrx,ModCntr_Type_ID,ModularContractHandlerType,Name,Updated,UpdatedBy,Value) VALUES (1000000,1000000,'UserElementNumber2',TO_TIMESTAMP('2024-11-08 09:25:36.637','YYYY-MM-DD HH24:MI:SS.US'),100,'Erstellt die Lieferlogs inklusive Protein für Protein Rechnungspositionen und Details in der Endgültigen Schlusszahlung','Y','N',540035,'DefinitiveInvoiceAverageAVOnShippedQty','Protein - Endgültige Schlussabrechnung durchschnittliche gelieferte Menge',TO_TIMESTAMP('2024-11-08 09:25:36.637','YYYY-MM-DD HH24:MI:SS.US'),100,'Protein - Definitive Invoice Average AV On Shipped Qty')
;

-- 2024-11-08T08:38:47.060Z
INSERT INTO ModCntr_Type (AD_Client_ID,AD_Org_ID,Created,CreatedBy,Description,IsActive,IsSOTrx,ModCntr_Type_ID,ModularContractHandlerType,Name,Updated,UpdatedBy,Value) VALUES (1000000,1000000,TO_TIMESTAMP('2024-11-08 09:38:47.053','YYYY-MM-DD HH24:MI:SS.US'),100,'Erstellt die Lieferlogs für die Lagerkosten in der Endgültigen Schlusszahlung','Y','N',540036,'DefinitiveInvoiceStorageCost','Endgültige Schlussabrechnung Lagerkosten',TO_TIMESTAMP('2024-11-08 09:38:47.053','YYYY-MM-DD HH24:MI:SS.US'),100,'Definitive Invoice Storage Cost')
;
