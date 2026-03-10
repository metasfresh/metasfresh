-- Reference: ModCntr_Log_DocumentType
-- Value: ShipmentDisposition
-- ValueName: Shipment Disposition
-- 2024-11-07T07:30:56.161Z
UPDATE AD_Ref_List SET IsActive='N',Updated=TO_TIMESTAMP('2024-11-07 08:30:56.161','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543515
;

-- Reference: ModCntr_Log_DocumentType
-- Value: ProFormaSOModularContract
-- ValueName: ProFormaSOModularContract
-- 2024-11-07T07:31:21.902Z
UPDATE AD_Ref_List SET IsActive='N',Updated=TO_TIMESTAMP('2024-11-07 08:31:21.902','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543565
;

-- Reference: ModCntr_Log_DocumentType
-- Value: SupplyAgreement
-- ValueName: Supply Agreement
-- 2024-11-07T07:32:20.433Z
UPDATE AD_Ref_List SET IsActive='N',Updated=TO_TIMESTAMP('2024-11-07 08:32:20.433','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543508
;

-- Reference: ModCntr_Log_DocumentType
-- Value: ProformaShipment
-- ValueName: ProformaShipment
-- 2024-11-07T07:33:07.728Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541770,543769,TO_TIMESTAMP('2024-11-07 08:33:07.586','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Proforma Lieferung',TO_TIMESTAMP('2024-11-07 08:33:07.586','YYYY-MM-DD HH24:MI:SS.US'),100,'ProformaShipment','ProformaShipment')
;

-- 2024-11-07T07:33:07.731Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543769 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: ModCntr_Log_DocumentType -> ProformaShipment_ProformaShipment
-- 2024-11-07T07:33:22.053Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Proforma Shipment',Updated=TO_TIMESTAMP('2024-11-07 08:33:22.053','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543769
;

-- Reference Item: ModCntr_Log_DocumentType -> ProformaShipment_ProformaShipment
-- 2024-11-07T07:33:22.813Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-11-07 08:33:22.813','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543769
;

-- Reference Item: ModCntr_Log_DocumentType -> ProformaShipment_ProformaShipment
-- 2024-11-07T07:33:25.465Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-11-07 08:33:25.465','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543769
;

-- Reference: ModCntr_Log_DocumentType
-- Value: ProformaShippingNotification
-- ValueName: ProformaShippingNotification
-- 2024-11-07T07:34:14.652Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541770,543770,TO_TIMESTAMP('2024-11-07 08:34:14.527','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Proforma Lieferavis',TO_TIMESTAMP('2024-11-07 08:34:14.527','YYYY-MM-DD HH24:MI:SS.US'),100,'ProformaShippingNotification','ProformaShippingNotification')
;

-- 2024-11-07T07:34:14.653Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543770 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: ModCntr_Log_DocumentType -> ProformaShippingNotification_ProformaShippingNotification
-- 2024-11-07T07:34:32.212Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Proforma Shipping Notification',Updated=TO_TIMESTAMP('2024-11-07 08:34:32.212','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543770
;

-- Reference Item: ModCntr_Log_DocumentType -> ProformaShippingNotification_ProformaShippingNotification
-- 2024-11-07T07:34:35.700Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-11-07 08:34:35.7','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543770
;

-- Reference Item: ModCntr_Log_DocumentType -> ProformaShippingNotification_ProformaShippingNotification
-- 2024-11-07T07:34:37.850Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-11-07 08:34:37.85','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543770
;

