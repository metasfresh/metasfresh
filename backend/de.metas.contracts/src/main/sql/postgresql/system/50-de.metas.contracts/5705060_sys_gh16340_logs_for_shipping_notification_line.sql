-- Run mode: SWING_CLIENT

-- Value: de.metas.contracts.modular.workpackage.impl.AbstractShippingNotificationLogHandler.OnComplete.Description
-- 2023-10-03T17:49:35.461Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545349,0,TO_TIMESTAMP('2023-10-03 20:49:35.241','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','A shipping notification line for product {0} , quantity {1} was completed.','I',TO_TIMESTAMP('2023-10-03 20:49:35.241','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts.modular.workpackage.impl.AbstractShippingNotificationLogHandler.OnComplete.Description')
;

-- 2023-10-03T17:49:35.475Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545349 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.contracts.modular.workpackage.impl.AbstractShippingNotificationLogHandler.OnReverse.Description
-- 2023-10-03T17:50:03.042Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545350,0,TO_TIMESTAMP('2023-10-03 20:50:02.874','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','A shipping notification line for product {0} , quantity {1} was reversed.','I',TO_TIMESTAMP('2023-10-03 20:50:02.874','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts.modular.workpackage.impl.AbstractShippingNotificationLogHandler.OnReverse.Description')
;

-- 2023-10-03T17:50:03.044Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545350 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.contracts.modular.workpackage.impl.AbstractShippingNotificationLogHandler.OnReverse.Description
-- 2023-10-03T17:50:28.146Z
UPDATE AD_Message_Trl SET MsgText='Eine Lieferaviszeile für Produkt {0}, Menge {1} wurde storniert.',Updated=TO_TIMESTAMP('2023-10-03 20:50:28.146','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545350
;

-- Value: de.metas.contracts.modular.workpackage.impl.AbstractShippingNotificationLogHandler.OnReverse.Description
-- 2023-10-03T17:50:36.586Z
UPDATE AD_Message_Trl SET MsgText='Eine Lieferaviszeile für Produkt {0}, Menge {1} wurde storniert.',Updated=TO_TIMESTAMP('2023-10-03 20:50:36.586','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545350
;

-- 2023-10-03T17:50:36.587Z
UPDATE AD_Message SET MsgText='Eine Lieferaviszeile für Produkt {0}, Menge {1} wurde storniert.' WHERE AD_Message_ID=545350
;

-- Value: de.metas.contracts.modular.workpackage.impl.AbstractShippingNotificationLogHandler.OnComplete.Description
-- 2023-10-03T17:51:17.367Z
UPDATE AD_Message_Trl SET MsgText='Eine Lieferaviszeile für Produkt {0}, Menge {1} wurde abgeschlossen.',Updated=TO_TIMESTAMP('2023-10-03 20:51:17.367','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=545349
;

-- Value: de.metas.contracts.modular.workpackage.impl.AbstractShippingNotificationLogHandler.OnComplete.Description
-- 2023-10-03T17:51:19.931Z
UPDATE AD_Message_Trl SET MsgText='Eine Lieferaviszeile für Produkt {0}, Menge {1} wurde abgeschlossen.',Updated=TO_TIMESTAMP('2023-10-03 20:51:19.931','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545349
;

-- 2023-10-03T17:51:19.932Z
UPDATE AD_Message SET MsgText='Eine Lieferaviszeile für Produkt {0}, Menge {1} wurde abgeschlossen.' WHERE AD_Message_ID=545349
;

-- Value: de.metas.contracts.modular.workpackage.impl.AbstractShippingNotificationLogHandler.OnComplete.Description
-- 2023-10-03T17:51:29.947Z
UPDATE AD_Message_Trl SET MsgText='Eine Lieferaviszeile für Produkt {0}, Menge {1} wurde abgeschlossen.',Updated=TO_TIMESTAMP('2023-10-03 20:51:29.947','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545349
;

-- Reference: ModCntr_Log_DocumentType
-- Value: ShippingNotification
-- ValueName: Lieferavis
-- 2023-10-03T17:54:37.450Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543566,541770,TO_TIMESTAMP('2023-10-03 20:54:37.307','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Lieferavis',TO_TIMESTAMP('2023-10-03 20:54:37.307','YYYY-MM-DD HH24:MI:SS.US'),100,'ShippingNotification','Lieferavis')
;

-- 2023-10-03T17:54:37.456Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543566 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: ModCntr_Log_DocumentType -> ShippingNotification_Lieferavis
-- 2023-10-03T17:54:53.410Z
UPDATE AD_Ref_List_Trl SET Name='Shipping Notification',Updated=TO_TIMESTAMP('2023-10-03 20:54:53.409','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543566
;

-- Name: Logs for Shipping Notification
-- 2023-10-03T17:56:06.946Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541836,TO_TIMESTAMP('2023-10-03 20:56:06.803','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','N','Logs for Shipping Notification',TO_TIMESTAMP('2023-10-03 20:56:06.803','YYYY-MM-DD HH24:MI:SS.US'),100,'T')
;

-- 2023-10-03T17:56:06.953Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541836 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: Logs for Shipping Notification
-- Table: ModCntr_Log
-- Key: ModCntr_Log.ModCntr_Log_ID
-- 2023-10-03T17:59:04.662Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,586758,0,541836,542338,TO_TIMESTAMP('2023-10-03 20:59:04.635','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','N','N',TO_TIMESTAMP('2023-10-03 20:59:04.635','YYYY-MM-DD HH24:MI:SS.US'),100,'ModCntr_Log.AD_Table_ID = get_table_id(''M_Shipping_NotificationLine_ID'') AND ModCntr_Log_Status.Record_ID IN  (SELECT M_Shipping_NotificationLine_ID        from M_Shipping_NotificationLine nline        where nline.M_Shipping_Notification_ID = @M_Shipping_Notification_ID / -1@)')
;

-- 2023-10-03T17:59:15.266Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541828,540433,TO_TIMESTAMP('2023-10-03 20:59:15.099','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.swat','Y','N','M_ShippingNotification -> Modular Logs',TO_TIMESTAMP('2023-10-03 20:59:15.099','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-03T17:59:19.787Z
UPDATE AD_RelationType SET AD_Reference_Target_ID=541836,Updated=TO_TIMESTAMP('2023-10-03 20:59:19.786','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_RelationType_ID=540433
;

-- Run mode: SWING_CLIENT

-- Process: RecomputeLogRecordsForDocument(de.metas.contracts.modular.log.process.RecomputeLogRecordsForDocument)
-- Table: M_Shipping_Notification
-- EntityType: de.metas.contracts
-- 2023-10-03T10:03:14.352Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585314,542365,541426,TO_TIMESTAMP('2023-10-03 13:03:14.184','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y',TO_TIMESTAMP('2023-10-03 13:03:14.184','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N')
;

