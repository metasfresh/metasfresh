-- Run mode: WEBUI

-- 2024-10-02T09:43:26.999Z
INSERT INTO ModCntr_Type (AD_Client_ID,AD_Org_ID,Created,CreatedBy,Description,IsActive,IsSOTrx,ModCntr_Type_ID,ModularContractHandlerType,Name,Updated,UpdatedBy,Value) VALUES (1000000,1000000,TO_TIMESTAMP('2024-10-02 11:43:26.98','YYYY-MM-DD HH24:MI:SS.US'),100,'Dieser Vertragsbausteintyp erzeugt informative Logs für abgeschlossene Auftragszeilen, die erstellten Vertragsbaustein-Verträge, Schlussrechnungen und Lieferavis. Für sie wird kein Rechnungskandidat erstellt.','Y','Y',540026,'SalesInformativeLogs','Verkauf - Informative Logs',TO_TIMESTAMP('2024-10-02 11:43:26.98','YYYY-MM-DD HH24:MI:SS.US'),100,'SalesInformativeLogs')
;

-- 2024-10-02T09:46:10.222Z
UPDATE ModCntr_Type SET IsActive='N',Updated=TO_TIMESTAMP('2024-10-02 11:46:10.222','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE ModCntr_Type_ID=540026
;

-- 2024-10-02T09:46:02.004Z
INSERT INTO ModCntr_Type (AD_Client_ID,AD_Org_ID,Created,CreatedBy,Description,IsActive,IsSOTrx,ModCntr_Type_ID,ModularContractHandlerType,Name,Updated,UpdatedBy,Value) VALUES (1000000,1000000,TO_TIMESTAMP('2024-10-02 11:46:02.0','YYYY-MM-DD HH24:MI:SS.US'),100,'Erstellt Logs für geliefertes Produkt, die für Verkaufsmenge und Verkaufspreis in der Schlusszahlung genutzt werden.','Y','Y',540027,'Sales','Verkauf',TO_TIMESTAMP('2024-10-02 11:46:02.0','YYYY-MM-DD HH24:MI:SS.US'),100,'Sales')
;

-- 2024-10-02T09:50:07.309Z
INSERT INTO ModCntr_Type (AD_Client_ID,AD_Org_ID,Created,CreatedBy,Description,IsActive,IsSOTrx,ModCntr_Type_ID,ModularContractHandlerType,Name,Updated,UpdatedBy,Value) VALUES (1000000,1000000,TO_TIMESTAMP('2024-10-02 11:50:07.305','YYYY-MM-DD HH24:MI:SS.US'),100,'Erstellt Lieferlogs für hinzugefügte Werte in der Schlusszahlung','Y','Y',540028,'SalesAV','Verkauf - Wert hinzufügen',TO_TIMESTAMP('2024-10-02 11:50:07.305','YYYY-MM-DD HH24:MI:SS.US'),100,'Sales - Added Value')
;

-- 2024-10-02T09:50:58.452Z
INSERT INTO ModCntr_Type (AD_Client_ID,AD_Org_ID,Created,CreatedBy,Description,IsActive,IsSOTrx,ModCntr_Type_ID,ModularContractHandlerType,Name,Updated,UpdatedBy,Value) VALUES (1000000,1000000,TO_TIMESTAMP('2024-10-02 11:50:58.449','YYYY-MM-DD HH24:MI:SS.US'),100,'Erstellt die Lieferlogs für die Lagerkosten in der Schlusszahlung','Y','Y',540029,'SalesStorageCost','Verkauf - Lagerkosten',TO_TIMESTAMP('2024-10-02 11:50:58.449','YYYY-MM-DD HH24:MI:SS.US'),100,'Sales - Storage Cost')
;

-- 2024-10-02T09:53:10.497Z
INSERT INTO ModCntr_Type (AD_Client_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,IsActive,IsSOTrx,ModCntr_Type_ID,ModularContractHandlerType,Name,Updated,UpdatedBy,Value) VALUES (1000000,1000000,'UserElementNumber1',TO_TIMESTAMP('2024-10-02 11:53:10.486','YYYY-MM-DD HH24:MI:SS.US'),100,'Erstellt die Lieferlogs für HL','Y','Y',540030,'SalesAverageAVOnShippedQty','Verkauf - HL',TO_TIMESTAMP('2024-10-02 11:53:10.486','YYYY-MM-DD HH24:MI:SS.US'),100,'Sales - HL - AverageAVOnShippedQtyComputingMethod')
;

-- 2024-10-02T09:55:16.289Z
INSERT INTO ModCntr_Type (AD_Client_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,IsActive,IsSOTrx,ModCntr_Type_ID,ModularContractHandlerType,Name,Updated,UpdatedBy,Value) VALUES (1000000,1000000,'UserElementNumber2',TO_TIMESTAMP('2024-10-02 11:55:16.285','YYYY-MM-DD HH24:MI:SS.US'),100,'Erstellt die Lieferlogs für Protein','Y','Y',540031,'SalesAverageAVOnShippedQty','Verkauf - Protein',TO_TIMESTAMP('2024-10-02 11:55:16.285','YYYY-MM-DD HH24:MI:SS.US'),100,'Sales - Protein - AverageAVOnShippedQtyComputingMethod')
;

