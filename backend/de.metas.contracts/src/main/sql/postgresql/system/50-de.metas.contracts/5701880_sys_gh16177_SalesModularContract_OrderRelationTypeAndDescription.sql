-- 2023-09-06T11:01:51.849000100Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541787,541820,540421,TO_TIMESTAMP('2023-09-06 14:01:51.734','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','C_Flatrate_Term -> C_Order (SO ONLY)',TO_TIMESTAMP('2023-09-06 14:01:51.734','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Reference: ModCntr_Log_DocumentType
-- Value: Zuschlag Vertrag
-- ValueName: Zuschlag Vertrag
-- 2023-09-07T13:44:35.620722Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543549,541770,TO_TIMESTAMP('2023-09-07 16:44:35.352','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Zuschlag Vertrag',TO_TIMESTAMP('2023-09-07 16:44:35.352','YYYY-MM-DD HH24:MI:SS.US'),100,'SalesModularContract','SalesModularContract')
;

-- 2023-09-07T13:44:35.656708Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543549 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: ModCntr_Log_DocumentType -> Zuschlag Vertrag_Zuschlag Vertrag
-- 2023-09-07T13:44:44.306804200Z
UPDATE AD_Ref_List_Trl SET Name='Sales Modular Contract',Updated=TO_TIMESTAMP('2023-09-07 16:44:44.305','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543549
;

-- Reference: ModCntr_Log_DocumentType
-- Value: Liefervereinbarung
-- ValueName: Liefervereinbarung
-- 2023-09-07T13:45:00.790382800Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543550,541770,TO_TIMESTAMP('2023-09-07 16:45:00.649','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Liefervereinbarung',TO_TIMESTAMP('2023-09-07 16:45:00.649','YYYY-MM-DD HH24:MI:SS.US'),100,'PurchaseModularContract','PurchaseModularContract')
;

-- 2023-09-07T13:45:00.791382800Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543550 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: ModCntr_Log_DocumentType -> Liefervereinbarung_Liefervereinbarung
-- 2023-09-07T13:45:17.377997800Z
UPDATE AD_Ref_List_Trl SET Name='Purchase Modular Contract',Updated=TO_TIMESTAMP('2023-09-07 16:45:17.377','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543550
;

-- Value: de.metas.contracts.modular.modularContractCompleteLogDescription
-- 2023-09-07T14:21:36.776059200Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545331,0,TO_TIMESTAMP('2023-09-07 17:21:36.559','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Modularer Vertrag für Produkt {0} mit der Menge {1} wurde fertiggestellt.','I',TO_TIMESTAMP('2023-09-07 17:21:36.559','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts.modular.modularContractCompleteLogDescription')
;

-- 2023-09-07T14:21:36.817059Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545331 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.contracts.modular.modularContractCompleteLogDescription
-- 2023-09-07T14:21:46.774785700Z
UPDATE AD_Message_Trl SET MsgText='Modular Contract for product {0} with the quantity {1} was completed.',Updated=TO_TIMESTAMP('2023-09-07 17:21:46.774','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545331
;

-- Column: ModCntr_Log.C_Flatrate_Term_ID
-- 2023-09-07T15:21:49.115740100Z
UPDATE AD_Column SET IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2023-09-07 18:21:49.114','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586778
;

