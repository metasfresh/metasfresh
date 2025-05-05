-- Run mode: WEBUI

-- 2024-05-20T13:10:38.942Z
INSERT INTO ModCntr_Type (AD_Client_ID,AD_Org_ID,Created,CreatedBy,Description,IsActive,ModCntr_Type_ID,ModularContractHandlerType,Name,Updated,UpdatedBy,Value) VALUES (1000000,1000000,TO_TIMESTAMP('2024-05-20 16:10:38.926','YYYY-MM-DD HH24:MI:SS.US'),100,'Erstellt Wareneingangslogs, Lieferlogs und Inventurlogs für die endgültige Schlussrechnung.','Y',540009,'DefinitiveInvoiceRawProduct','Endgültige Schlussrechnung für Rohprodukt',TO_TIMESTAMP('2024-05-20 16:10:38.926','YYYY-MM-DD HH24:MI:SS.US'),100,'Definitive Invoice for Raw Product')
;

-- 2024-05-20T13:11:15.100Z
INSERT INTO ModCntr_Type (AD_Client_ID,AD_Org_ID,Created,CreatedBy,Description,IsActive,ModCntr_Type_ID,ModularContractHandlerType,Name,Updated,UpdatedBy,Value) VALUES (1000000,1000000,TO_TIMESTAMP('2024-05-20 16:11:15.095','YYYY-MM-DD HH24:MI:SS.US'),100,'Erstellt Logs für erhaltenes verarbeitetes Erzeugnis in der Produktion, Lieferlogs und Inventurlogs für die endgültige Schlussrechnung.','Y',540010,'DefinitiveInvoiceProcessedProduct','Endgültige Schlussrechnung für verarbeitetes Produkt',TO_TIMESTAMP('2024-05-20 16:11:15.095','YYYY-MM-DD HH24:MI:SS.US'),100,'Definitive Invoice for Processed Product')
;


-- 2024-05-20T13:16:26.720Z
UPDATE ModCntr_Type SET Value='DefinitiveInvoiceProcessed Product',Updated=TO_TIMESTAMP('2024-05-20 16:16:26.72','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE ModCntr_Type_ID=540010
;

-- 2024-05-20T13:16:29.699Z
UPDATE ModCntr_Type SET Value='DefinitiveInvoiceProcessedProduct',Updated=TO_TIMESTAMP('2024-05-20 16:16:29.699','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE ModCntr_Type_ID=540010
;

-- 2024-05-20T13:16:40.687Z
UPDATE ModCntr_Type SET Value='DefinitiveInvoiceRawProduct',Updated=TO_TIMESTAMP('2024-05-20 16:16:40.686','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE ModCntr_Type_ID=540009
;



-- 2024-05-20T13:37:16.556Z
UPDATE ModCntr_Type SET IsActive='N',Updated=TO_TIMESTAMP('2024-05-20 16:37:16.556','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE ModCntr_Type_ID=540009
;

-- 2024-05-20T13:37:21.071Z
UPDATE ModCntr_Type SET IsActive='N',Updated=TO_TIMESTAMP('2024-05-20 16:37:21.071','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE ModCntr_Type_ID=540010
;