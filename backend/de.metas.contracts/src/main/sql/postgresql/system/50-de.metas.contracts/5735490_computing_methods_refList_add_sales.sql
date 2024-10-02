-- Reference: Computing Methods
-- Value: SalesInformativeLogs
-- ValueName: SalesInformativeLogs
-- 2024-10-02T08:36:08.139Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541838,543729,TO_TIMESTAMP('2024-10-02 10:36:07.979','YYYY-MM-DD HH24:MI:SS.US'),100,'Dieser Vertragsbausteintyp erzeugt informative Logs für abgeschlossene Auftragszeilen, die erstellten Vertragsbaustein-Verträge, Schlussabrechnung und Lieferavis. Für sie wird kein Rechnungskandidat erstellt.','de.metas.contracts','Y','Verkauf - Informative Logs',TO_TIMESTAMP('2024-10-02 10:36:07.979','YYYY-MM-DD HH24:MI:SS.US'),100,'SalesInformativeLogs','SalesInformativeLogs')
;

-- 2024-10-02T08:36:08.142Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543729 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: Computing Methods -> SalesInformativeLogs_SalesInformativeLogs
-- 2024-10-02T08:36:14.314Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-02 10:36:14.314','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543729
;

-- Reference Item: Computing Methods -> SalesInformativeLogs_SalesInformativeLogs
-- 2024-10-02T08:36:15.688Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-02 10:36:15.688','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543729
;

-- Reference Item: Computing Methods -> SalesInformativeLogs_SalesInformativeLogs
-- 2024-10-02T08:37:17.790Z
UPDATE AD_Ref_List_Trl SET Description='This computing method type generates informative logs about completed sales order lines, the creation of the modular contracts, final invoice lines and shipment notifications. No Invoice Candidate is created for them.', IsTranslated='Y', Name='Sales - Informative Logs',Updated=TO_TIMESTAMP('2024-10-02 10:37:17.79','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543729
;

-- Reference: Computing Methods
-- Value: SalesInformativeLogs
-- ValueName: SalesInformativeLogs
-- 2024-10-02T08:54:33.458Z
UPDATE AD_Ref_List SET IsActive='N',Updated=TO_TIMESTAMP('2024-10-02 10:54:33.458','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543729
;

-- Reference: Computing Methods
-- Value: Sales
-- ValueName: Sales
-- 2024-10-02T08:42:41.638Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541838,543730,TO_TIMESTAMP('2024-10-02 10:42:41.499','YYYY-MM-DD HH24:MI:SS.US'),100,'','de.metas.contracts','Y','Verkauf',TO_TIMESTAMP('2024-10-02 10:42:41.499','YYYY-MM-DD HH24:MI:SS.US'),100,'Sales','Sales')
;

-- 2024-10-02T08:42:41.641Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543730 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: Computing Methods -> Sales_Sales
-- 2024-10-02T08:42:47.115Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-02 10:42:47.115','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543730
;

-- Reference Item: Computing Methods -> Sales_Sales
-- 2024-10-02T08:42:48.091Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-02 10:42:48.091','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543730
;

-- Reference Item: Computing Methods -> Sales_Sales
-- 2024-10-02T08:42:58.094Z
UPDATE AD_Ref_List_Trl SET Name='Sales',Updated=TO_TIMESTAMP('2024-10-02 10:42:58.094','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543730
;

-- Reference: Computing Methods
-- Value: SalesAV
-- ValueName: SalesAV
-- 2024-10-02T08:50:06.336Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541838,543732,TO_TIMESTAMP('2024-10-02 10:50:06.211','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Verkauf - Wert hinzufügen',TO_TIMESTAMP('2024-10-02 10:50:06.211','YYYY-MM-DD HH24:MI:SS.US'),100,'SalesAV','SalesAV')
;

-- 2024-10-02T08:50:06.337Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543732 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: Computing Methods -> SalesAV_SalesAV
-- 2024-10-02T08:50:15.180Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-02 10:50:15.18','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543732
;

-- Reference Item: Computing Methods -> SalesAV_SalesAV
-- 2024-10-02T08:51:18.416Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-02 10:51:18.416','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543732
;

-- Reference Item: Computing Methods -> SalesAV_SalesAV
-- 2024-10-02T08:51:49.378Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Sales - Added Value',Updated=TO_TIMESTAMP('2024-10-02 10:51:49.378','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543732
;

-- Reference: Computing Methods
-- Value: SalesStorageCost
-- ValueName: SalesStorageCost
-- 2024-10-02T08:53:32.250Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541838,543733,TO_TIMESTAMP('2024-10-02 10:53:32.126','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Verkauf - Lagerkosten',TO_TIMESTAMP('2024-10-02 10:53:32.126','YYYY-MM-DD HH24:MI:SS.US'),100,'SalesStorageCost','SalesStorageCost')
;

-- 2024-10-02T08:53:32.251Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543733 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: Computing Methods -> SalesStorageCost_SalesStorageCost
-- 2024-10-02T08:53:37.625Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-02 10:53:37.625','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543733
;

-- Reference Item: Computing Methods -> SalesStorageCost_SalesStorageCost
-- 2024-10-02T08:53:38.942Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-02 10:53:38.942','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543733
;

-- Reference Item: Computing Methods -> SalesStorageCost_SalesStorageCost
-- 2024-10-02T08:54:10.230Z
UPDATE AD_Ref_List_Trl SET Name='Sales - Storage Cost',Updated=TO_TIMESTAMP('2024-10-02 10:54:10.23','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543733
;

-- Reference Item: Computing Methods -> SalesStorageCost_SalesStorageCost
-- 2024-10-02T08:54:15.220Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-02 10:54:15.22','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543733
;

-- Reference: Computing Methods
-- Value: SalesAverageAVOnShippedQty
-- ValueName: SalesAverageAVOnShippedQty
-- 2024-10-02T08:57:40.809Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541838,543734,TO_TIMESTAMP('2024-10-02 10:57:40.687','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Verkauf - Durchschn. Wert zu gelieferter Menge hinzufügen',TO_TIMESTAMP('2024-10-02 10:57:40.687','YYYY-MM-DD HH24:MI:SS.US'),100,'SalesAverageAVOnShippedQty','SalesAverageAVOnShippedQty')
;

-- 2024-10-02T08:57:40.811Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543734 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: Computing Methods -> SalesAverageAVOnShippedQty_SalesAverageAVOnShippedQty
-- 2024-10-02T08:57:47.359Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-02 10:57:47.359','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543734
;

-- Reference Item: Computing Methods -> SalesAverageAVOnShippedQty_SalesAverageAVOnShippedQty
-- 2024-10-02T09:00:30.367Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-02 11:00:30.367','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543734
;

-- Reference Item: Computing Methods -> SalesAverageAVOnShippedQty_SalesAverageAVOnShippedQty
-- 2024-10-02T09:01:09.136Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Sales - Average Added Value On Shipped Quantity',Updated=TO_TIMESTAMP('2024-10-02 11:01:09.136','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543734
;

