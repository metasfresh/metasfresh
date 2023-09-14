-- Column: C_Order.PhysicalClearanceDate
-- 2023-09-14T10:17:16.401698500Z
UPDATE AD_Column SET IsUpdateable='Y',Updated=TO_TIMESTAMP('2023-09-14 13:17:16.401','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587455
;


-- Column: M_Shipping_Notification.Processed
-- 2023-09-14T10:41:10.809716300Z
UPDATE AD_Column SET IsUpdateable='Y',Updated=TO_TIMESTAMP('2023-09-14 13:41:10.809','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587384
;

-- Column: M_Shipping_Notification.Updated
-- 2023-09-14T10:41:28.900728100Z
UPDATE AD_Column SET IsUpdateable='Y',Updated=TO_TIMESTAMP('2023-09-14 13:41:28.9','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587378
;

-- Column: M_Shipping_Notification.UpdatedBy
-- 2023-09-14T10:41:32.684494800Z
UPDATE AD_Column SET IsUpdateable='Y',Updated=TO_TIMESTAMP('2023-09-14 13:41:32.683','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587379
;

-- Column: M_Shipping_Notification.Description
-- 2023-09-14T10:42:48.706914300Z
UPDATE AD_Column SET IsAlwaysUpdateable='Y',Updated=TO_TIMESTAMP('2023-09-14 13:42:48.706','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587395
;

-- Tab: Lieferavis(541734,de.metas.shippingnotification) -> Shipping Notification
-- Table: M_Shipping_Notification
-- 2023-09-14T10:44:31.953298500Z
UPDATE AD_Tab SET IsReadOnly='N', IsRefreshViewOnChangeEvents='Y',Updated=TO_TIMESTAMP('2023-09-14 13:44:31.952','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=547218
;

-- Tab: Lieferavis(541734,de.metas.shippingnotification) -> Shipping Notification Line
-- Table: M_Shipping_NotificationLine
-- 2023-09-14T10:44:37.613191Z
UPDATE AD_Tab SET IsRefreshViewOnChangeEvents='Y',Updated=TO_TIMESTAMP('2023-09-14 13:44:37.612','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=547219
;





-- Window: Lieferavis, InternalName=null
-- 2023-09-14T10:47:19.719106300Z
UPDATE AD_Window SET WindowType='T',Updated=TO_TIMESTAMP('2023-09-14 13:47:19.717','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Window_ID=541734
;

-- Window: Lieferavis, InternalName=null
-- 2023-09-14T10:48:10.173169600Z
UPDATE AD_Window SET IsEnableRemoteCacheInvalidation='Y',Updated=TO_TIMESTAMP('2023-09-14 13:48:10.171','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Window_ID=541734
;

-- Tab: Lieferavis(541734,de.metas.shippingnotification) -> Shipping Notification
-- Table: M_Shipping_Notification
-- 2023-09-14T10:48:28.529846500Z
UPDATE AD_Tab SET IsSingleRow='Y',Updated=TO_TIMESTAMP('2023-09-14 13:48:28.529','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=547218
;

-- Tab: Lieferavis(541734,de.metas.shippingnotification) -> Shipping Notification
-- Table: M_Shipping_Notification
-- 2023-09-14T10:48:30.294598800Z
UPDATE AD_Tab SET IsRefreshViewOnChangeEvents='N',Updated=TO_TIMESTAMP('2023-09-14 13:48:30.294','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=547218
;

-- Field: Lieferavis(541734,de.metas.shippingnotification) -> Shipping Notification(547218,de.metas.shippingnotification) -> Geschäftspartner
-- Column: M_Shipping_Notification.C_BPartner_ID
-- 2023-09-14T10:48:52.362471500Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-09-14 13:48:52.361','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720414
;

-- Field: Lieferavis(541734,de.metas.shippingnotification) -> Shipping Notification(547218,de.metas.shippingnotification) -> Standort
-- Column: M_Shipping_Notification.C_BPartner_Location_ID
-- 2023-09-14T10:49:22.811210700Z
UPDATE AD_Field SET IsDisplayed='Y', IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2023-09-14 13:49:22.81','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720415
;

-- Field: Lieferavis(541734,de.metas.shippingnotification) -> Shipping Notification(547218,de.metas.shippingnotification) -> Standort
-- Column: M_Shipping_Notification.C_BPartner_Location_ID
-- 2023-09-14T10:49:42.857540800Z
UPDATE AD_Field SET SeqNo=25,Updated=TO_TIMESTAMP('2023-09-14 13:49:42.857','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720415
;

-- UI Element: Lieferavis(541734,de.metas.shippingnotification) -> Shipping Notification(547218,de.metas.shippingnotification) -> main -> 20 -> primary.Nr.
-- Column: M_Shipping_Notification.DocumentNo
-- 2023-09-14T10:51:07.028725400Z
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=10,Updated=TO_TIMESTAMP('2023-09-14 13:51:07.028','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620442
;

-- UI Element: Lieferavis(541734,de.metas.shippingnotification) -> Shipping Notification(547218,de.metas.shippingnotification) -> main -> 10 -> default.Geschäftspartner
-- Column: M_Shipping_Notification.C_BPartner_ID
-- 2023-09-14T10:51:07.035985600Z
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=20,Updated=TO_TIMESTAMP('2023-09-14 13:51:07.035','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620436
;

-- UI Element: Lieferavis(541734,de.metas.shippingnotification) -> Shipping Notification(547218,de.metas.shippingnotification) -> main -> 20 -> dates.Physical Clearance Date
-- Column: M_Shipping_Notification.PhysicalClearanceDate
-- 2023-09-14T10:51:07.042728Z
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=30,Updated=TO_TIMESTAMP('2023-09-14 13:51:07.042','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620443
;

-- UI Element: Lieferavis(541734,de.metas.shippingnotification) -> Shipping Notification(547218,de.metas.shippingnotification) -> main -> 10 -> default.Lager
-- Column: M_Shipping_Notification.M_Warehouse_ID
-- 2023-09-14T10:51:07.050498300Z
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=40,Updated=TO_TIMESTAMP('2023-09-14 13:51:07.049','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620439
;

-- UI Element: Lieferavis(541734,de.metas.shippingnotification) -> Shipping Notification(547218,de.metas.shippingnotification) -> main -> 10 -> default.Auktion
-- Column: M_Shipping_Notification.C_Auction_ID
-- 2023-09-14T10:51:07.057806100Z
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=50,Updated=TO_TIMESTAMP('2023-09-14 13:51:07.057','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620440
;

-- Column: M_Shipping_Notification.C_BPartner_ID
-- 2023-09-14T10:54:41.961565700Z
UPDATE AD_Column SET AD_Reference_ID=30, AD_Val_Rule_ID=540244,Updated=TO_TIMESTAMP('2023-09-14 13:54:41.961','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587386
;

-- Column: M_Shipping_Notification.C_BPartner_ID
-- 2023-09-14T10:55:20.689797500Z
UPDATE AD_Column SET IsIdentifier='Y',Updated=TO_TIMESTAMP('2023-09-14 13:55:20.689','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587386
;

-- Column: M_Shipping_Notification.M_Warehouse_ID
-- 2023-09-14T11:02:35.933614200Z
UPDATE AD_Column SET AD_Reference_ID=30, AD_Val_Rule_ID=189,Updated=TO_TIMESTAMP('2023-09-14 14:02:35.933','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587390
;

-- Column: M_Shipping_Notification.C_Harvesting_Calendar_ID
-- 2023-09-14T11:04:45.826515600Z
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2023-09-14 14:04:45.826','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587392
;

-- UI Element: Lieferavis(541734,de.metas.shippingnotification) -> Shipping Notification(547218,de.metas.shippingnotification) -> main -> 10 -> default.Erntejahr
-- Column: M_Shipping_Notification.Harvesting_Year_ID
-- 2023-09-14T11:08:07.921753900Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=620459
;

-- 2023-09-14T11:10:09.700907400Z
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,720421,0,541732,620437,TO_TIMESTAMP('2023-09-14 14:10:09.54','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,'widget',TO_TIMESTAMP('2023-09-14 14:10:09.54','YYYY-MM-DD HH24:MI:SS.US'),100)
;

