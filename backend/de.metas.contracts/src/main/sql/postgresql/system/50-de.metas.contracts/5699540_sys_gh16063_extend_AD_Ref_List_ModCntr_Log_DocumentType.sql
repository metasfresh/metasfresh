-- Reference: ModCntr_Log_DocumentType
-- Value: SalesInvoice
-- ValueName: Sales invoice
-- 2023-08-18T10:36:41.118763500Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543537,541770,TO_TIMESTAMP('2023-08-18 13:36:40.979','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Sales invoice',TO_TIMESTAMP('2023-08-18 13:36:40.979','YYYY-MM-DD HH24:MI:SS.US'),100,'SalesInvoice','Sales invoice')
;

-- 2023-08-18T10:36:41.121763100Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543537 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: ModCntr_Log_DocumentType -> SalesInvoice_Sales invoice
-- 2023-08-18T10:36:56.686311200Z
UPDATE AD_Ref_List_Trl SET Name='Debitorenrechnung',Updated=TO_TIMESTAMP('2023-08-18 13:36:56.685','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543537
;

-- Reference Item: ModCntr_Log_DocumentType -> SalesInvoice_Sales invoice
-- 2023-08-18T10:37:01.239038100Z
UPDATE AD_Ref_List_Trl SET Name='Debitorenrechnung',Updated=TO_TIMESTAMP('2023-08-18 13:37:01.239','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543537
;

-- 2023-08-18T10:37:01.241952100Z
UPDATE AD_Ref_List SET Name='Debitorenrechnung' WHERE AD_Ref_List_ID=543537
;

-- Reference Item: ModCntr_Log_DocumentType -> SalesInvoice_Sales invoice
-- 2023-08-18T10:37:03.436563800Z
UPDATE AD_Ref_List_Trl SET Name='Debitorenrechnung',Updated=TO_TIMESTAMP('2023-08-18 13:37:03.435','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=543537
;

-- Reference Item: ModCntr_Log_DocumentType -> SalesInvoice_Sales invoice
-- 2023-08-18T10:37:08.834720600Z
UPDATE AD_Ref_List_Trl SET Name='Debitorenrechnung',Updated=TO_TIMESTAMP('2023-08-18 13:37:08.834','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='it_IT' AND AD_Ref_List_ID=543537
;

