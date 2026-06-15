-- Reference: Computing Methods
-- Value: SalesOnRawProductShippedQty
-- ValueName: SalesOnRawProductShippedQty
-- 2024-10-14T14:14:45.772Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541838,543761,TO_TIMESTAMP('2024-10-14 16:14:45.629','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Verkauf Rohprodukt gelieferte Menge',TO_TIMESTAMP('2024-10-14 16:14:45.629','YYYY-MM-DD HH24:MI:SS.US'),100,'SalesOnRawProductShippedQty','SalesOnRawProductShippedQty')
;

-- 2024-10-14T14:14:45.774Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543761 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: Computing Methods -> SalesOnRawProductShippedQty_SalesOnRawProductShippedQty
-- 2024-10-14T14:15:23.421Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Raw Product Sales shipped Quantity',Updated=TO_TIMESTAMP('2024-10-14 16:15:23.421','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543761
;

-- Reference Item: Computing Methods -> SalesOnRawProductShippedQty_SalesOnRawProductShippedQty
-- 2024-10-14T14:15:24.769Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-14 16:15:24.769','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543761
;

-- Reference Item: Computing Methods -> SalesOnRawProductShippedQty_SalesOnRawProductShippedQty
-- 2024-10-14T14:15:25.686Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-14 16:15:25.685','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543761
;

-- Reference: Computing Methods
-- Value: SalesOnProcessedProductShippedQty
-- ValueName: SalesOnProcessedProductShippedQty
-- 2024-10-14T14:16:24.982Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541838,543762,TO_TIMESTAMP('2024-10-14 16:16:24.822','YYYY-MM-DD HH24:MI:SS.US'),100,'U','Y','Verkauf verarbeitetes Erzeugnis gelieferte Menge',TO_TIMESTAMP('2024-10-14 16:16:24.822','YYYY-MM-DD HH24:MI:SS.US'),100,'SalesOnProcessedProductShippedQty','SalesOnProcessedProductShippedQty')
;

-- 2024-10-14T14:16:24.983Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543762 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: Computing Methods -> SalesOnProcessedProductShippedQty_SalesOnProcessedProductShippedQty
-- 2024-10-14T14:16:54.472Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Processed Product Sales shipped Quantity',Updated=TO_TIMESTAMP('2024-10-14 16:16:54.472','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543762
;

-- Reference Item: Computing Methods -> SalesOnProcessedProductShippedQty_SalesOnProcessedProductShippedQty
-- 2024-10-14T14:16:55.573Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-14 16:16:55.573','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543762
;

-- Reference Item: Computing Methods -> SalesOnProcessedProductShippedQty_SalesOnProcessedProductShippedQty
-- 2024-10-14T14:16:56.349Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-14 16:16:56.348','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543762
;

-- Run mode: WEBUI

-- 2024-10-14T14:19:41.265Z
INSERT INTO ModCntr_Type (AD_Client_ID,AD_Org_ID,Created,CreatedBy,Description,IsActive,IsSOTrx,ModCntr_Type_ID,ModularContractHandlerType,Name,Updated,UpdatedBy,Value) VALUES (1000000,1000000,TO_TIMESTAMP('2024-10-14 16:19:41.231','YYYY-MM-DD HH24:MI:SS.US'),100,'Erstellt Lieferlogs, die für Verkaufsmenge und Verkaufspreis in der Schlusszahlung genutzt werden. Als Preis wird in der Schlusszahlung der Durchschnittspreis minus Handelsfranken genutzt.','Y','N',540032,'SalesOnRawProductShippedQty','Verkauf Rohprodukt gelieferte Menge',TO_TIMESTAMP('2024-10-14 16:19:41.231','YYYY-MM-DD HH24:MI:SS.US'),100,'Raw Products Sales shipped Quantity')
;

-- 2024-10-14T14:21:15.084Z
INSERT INTO ModCntr_Type (AD_Client_ID,AD_Org_ID,Created,CreatedBy,Description,IsActive,IsSOTrx,ModCntr_Type_ID,ModularContractHandlerType,Name,Updated,UpdatedBy,Value) VALUES (1000000,1000000,TO_TIMESTAMP('2024-10-14 16:21:15.08','YYYY-MM-DD HH24:MI:SS.US'),100,'Erstellt Lieferlogs, die für Verkaufsmenge und Verkaufspreis in der Schlusszahlung genutzt werden. Als Preis wird in der Schlusszahlung der Durchschnittspreis minus Handelsfranken genutzt.','Y','N',540033,'SalesOnProcessedProductShippedQty','Verkauf verarbeitetes Erzeugnis gelieferte Menge',TO_TIMESTAMP('2024-10-14 16:21:15.08','YYYY-MM-DD HH24:MI:SS.US'),100,'Processed Products Sales shipped Quantity')
;
