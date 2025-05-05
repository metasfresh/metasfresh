-- 2023-09-18T23:08:14.050Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541626,540430,TO_TIMESTAMP('2023-09-19 00:08:13.747','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.swat','Y','N','M_ShipmentSchedule (SOTrx) -> M_Shipping_Notification',TO_TIMESTAMP('2023-09-19 00:08:13.747','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Name: M_Shipping_Notification_Target
-- 2023-09-18T23:12:08.421Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541833,TO_TIMESTAMP('2023-09-19 00:12:08.089','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.swat','Y','N','M_Shipping_Notification_Target',TO_TIMESTAMP('2023-09-19 00:12:08.089','YYYY-MM-DD HH24:MI:SS.US'),100,'T')
;

-- 2023-09-18T23:12:08.433Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541833 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: M_Shipping_Notification_Target
-- Table: M_Shipping_Notification
-- Key: M_Shipping_Notification.M_Shipping_Notification_ID
-- 2023-09-18T23:12:56.643Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,587380,0,541833,542365,TO_TIMESTAMP('2023-09-19 00:12:56.614','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.shippingnotification','Y','N','N',TO_TIMESTAMP('2023-09-19 00:12:56.614','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Reference: M_Shipping_Notification_Target
-- Table: M_Shipping_Notification
-- Key: M_Shipping_Notification.M_Shipping_Notification_ID
-- 2023-09-18T23:13:04.381Z
UPDATE AD_Ref_Table SET WhereClause='EXISTS (      SELECT 1 from m_shipping_notification sn               INNER JOIN m_shipping_notificationline snl on sn.M_Shipping_Notification_ID = snl.M_Shipping_Notification_ID      where snl.M_ShipmentSchedule_ID = @M_ShipmentSchedule_ID@        AND M_Shipping_Notification.M_Shipping_Notification_ID = sn.M_Shipping_Notification_ID  )',Updated=TO_TIMESTAMP('2023-09-19 00:13:04.381','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Reference_ID=541833
;

-- Reference: M_Shipping_Notification_Target
-- Table: M_Shipping_Notification
-- Key: M_Shipping_Notification.M_Shipping_Notification_ID
-- 2023-09-18T23:13:20.770Z
UPDATE AD_Ref_Table SET AD_Window_ID=541734,Updated=TO_TIMESTAMP('2023-09-19 00:13:20.77','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Reference_ID=541833
;

-- Name: M_Shipping_Notification_Target
-- 2023-09-18T23:13:38.304Z
UPDATE AD_Reference SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-19 00:13:38.302','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Reference_ID=541833
;

-- 2023-09-18T23:14:02.649Z
UPDATE AD_RelationType SET AD_Reference_Target_ID=541833,Updated=TO_TIMESTAMP('2023-09-19 00:14:02.648','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_RelationType_ID=540430
;

-- 2023-09-18T23:14:09.318Z
UPDATE AD_RelationType SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-19 00:14:09.318','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_RelationType_ID=540430
;

-- 2023-09-18T23:16:05.871Z
UPDATE AD_RelationType SET Description='Link shipment schedule and shipping notification records',Updated=TO_TIMESTAMP('2023-09-19 00:16:05.869','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_RelationType_ID=540430
;

-- Name: M_ShipmentSchedule (SOTrx) -> M_Shipping_Notification_Source
-- 2023-09-18T23:25:00.769Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541834,TO_TIMESTAMP('2023-09-19 00:25:00.423','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.shippingnotification','Y','N','M_ShipmentSchedule (SOTrx) -> M_Shipping_Notification_Source',TO_TIMESTAMP('2023-09-19 00:25:00.423','YYYY-MM-DD HH24:MI:SS.US'),100,'T')
;

-- 2023-09-18T23:25:00.772Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541834 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: M_ShipmentSchedule (SOTrx) -> M_Shipping_Notification_Source
-- Table: M_ShipmentSchedule
-- Key: M_ShipmentSchedule.M_ShipmentSchedule_ID
-- 2023-09-18T23:26:12.273Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,500232,0,541834,500221,TO_TIMESTAMP('2023-09-19 00:26:12.266','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.shippingnotification','Y','N','N',TO_TIMESTAMP('2023-09-19 00:26:12.266','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-18T23:26:29.208Z
UPDATE AD_RelationType SET AD_Reference_Source_ID=541834,Updated=TO_TIMESTAMP('2023-09-19 00:26:29.206','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_RelationType_ID=540430
;

-- Name: M_ShipmentSchedule (SOTrx) -> M_Shipping_Notification_Target
-- 2023-09-18T23:27:00.620Z
UPDATE AD_Reference SET Name='M_ShipmentSchedule (SOTrx) -> M_Shipping_Notification_Target',Updated=TO_TIMESTAMP('2023-09-19 00:27:00.618','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Reference_ID=541833
;

-- 2023-09-18T23:27:00.623Z
UPDATE AD_Reference_Trl trl SET Name='M_ShipmentSchedule (SOTrx) -> M_Shipping_Notification_Target' WHERE AD_Reference_ID=541833 AND AD_Language='de_DE'
;

