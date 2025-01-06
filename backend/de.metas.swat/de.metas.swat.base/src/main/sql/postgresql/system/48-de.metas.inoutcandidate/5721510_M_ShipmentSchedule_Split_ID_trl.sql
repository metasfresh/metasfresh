-- Run mode: SWING_CLIENT

-- Element: M_ShipmentSchedule_Split_ID
-- 2024-04-11T15:47:04.460Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Lieferungen aufteilen', PrintName='Lieferungen aufteilen',Updated=TO_TIMESTAMP('2024-04-11 18:47:04.459','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583072 AND AD_Language='de_CH'
;

-- 2024-04-11T15:47:04.491Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583072,'de_CH')
;

-- Element: M_ShipmentSchedule_Split_ID
-- 2024-04-11T15:47:09.261Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Lieferungen aufteilen', PrintName='Lieferungen aufteilen',Updated=TO_TIMESTAMP('2024-04-11 18:47:09.261','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583072 AND AD_Language='de_DE'
;

-- 2024-04-11T15:47:09.264Z
UPDATE AD_Element SET Name='Lieferungen aufteilen', PrintName='Lieferungen aufteilen' WHERE AD_Element_ID=583072
;

-- 2024-04-11T15:47:09.518Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583072,'de_DE')
;

-- 2024-04-11T15:47:09.521Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583072,'de_DE')
;

-- Element: M_ShipmentSchedule_Split_ID
-- 2024-04-11T15:47:13.041Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-04-11 18:47:13.041','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583072 AND AD_Language='en_US'
;

-- 2024-04-11T15:47:13.045Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583072,'en_US')
;

-- Process: SplitShipmentView_Launcher(de.metas.ui.web.split_shipment.SplitShipmentView_Launcher)
-- 2024-04-11T15:49:16.274Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Lieferungen aufteilen',Updated=TO_TIMESTAMP('2024-04-11 18:49:16.274','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585374
;

-- Process: SplitShipmentView_Launcher(de.metas.ui.web.split_shipment.SplitShipmentView_Launcher)
-- 2024-04-11T15:49:18.234Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Lieferungen aufteilen',Updated=TO_TIMESTAMP('2024-04-11 18:49:18.234','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585374
;

-- 2024-04-11T15:49:18.236Z
UPDATE AD_Process SET Name='Lieferungen aufteilen' WHERE AD_Process_ID=585374
;

-- Process: SplitShipmentView_Launcher(de.metas.ui.web.split_shipment.SplitShipmentView_Launcher)
-- 2024-04-11T15:49:19.885Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-04-11 18:49:19.885','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585374
;

