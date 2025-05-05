

-- Column: M_Shipping_Notification.Harvesting_Year_ID
-- 2023-12-11T16:37:29.486Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2023-12-11 18:37:29.486','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587393
;

-- 2023-12-11T16:37:30.711Z
INSERT INTO t_alter_column values('m_shipping_notification','Harvesting_Year_ID','NUMERIC(10)',null,null)
;

-- 2023-12-11T16:37:30.717Z
INSERT INTO t_alter_column values('m_shipping_notification','Harvesting_Year_ID',null,'NULL',null)
;

-- Column: M_Shipping_Notification.C_Harvesting_Calendar_ID
-- 2023-12-11T16:38:00.028Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2023-12-11 18:38:00.028','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587392
;

-- 2023-12-11T16:38:00.857Z
INSERT INTO t_alter_column values('m_shipping_notification','C_Harvesting_Calendar_ID','NUMERIC(10)',null,null)
;

-- 2023-12-11T16:38:00.860Z
INSERT INTO t_alter_column values('m_shipping_notification','C_Harvesting_Calendar_ID',null,'NULL',null)
;



-- UI Element: Lieferavis(541734,de.metas.shippingnotification) -> Shipping Notification(547218,de.metas.shippingnotification) -> main -> 10 -> default.Erntejahr
-- Column: M_Shipping_Notification.Harvesting_Year_ID
-- 2023-12-11T16:49:45.470Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2023-12-11 18:49:45.47','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620885
;
