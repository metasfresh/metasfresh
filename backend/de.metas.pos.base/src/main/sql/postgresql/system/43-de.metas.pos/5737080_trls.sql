-- Element: C_POS_Order_ID
-- 2024-10-16T14:03:02.197Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='POS Auftrag', PrintName='POS Auftrag',Updated=TO_TIMESTAMP('2024-10-16 17:03:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583266 AND AD_Language='de_CH'
;

-- 2024-10-16T14:03:02.264Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583266,'de_CH') 
;

-- Element: C_POS_Order_ID
-- 2024-10-16T14:03:07.932Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-16 17:03:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583266 AND AD_Language='en_US'
;

-- 2024-10-16T14:03:07.936Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583266,'en_US') 
;

-- Element: C_POS_Order_ID
-- 2024-10-16T14:03:11.908Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='POS Auftrag', PrintName='POS Auftrag',Updated=TO_TIMESTAMP('2024-10-16 17:03:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583266 AND AD_Language='de_DE'
;

-- 2024-10-16T14:03:11.912Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583266,'de_DE') 
;

-- 2024-10-16T14:03:11.918Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583266,'de_DE') 
;

-- Reference Item: POSPaymentMethod -> CARD_Card
-- 2024-10-16T14:03:39.393Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Karte',Updated=TO_TIMESTAMP('2024-10-16 17:03:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543720
;

-- Reference Item: POSPaymentMethod -> CARD_Card
-- 2024-10-16T14:03:41.816Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Karte',Updated=TO_TIMESTAMP('2024-10-16 17:03:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543720
;

-- Reference Item: POSPaymentMethod -> CARD_Card
-- 2024-10-16T14:03:43.292Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-16 17:03:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543720
;

-- Reference Item: POSPaymentMethod -> CASH_Cash
-- 2024-10-16T14:03:58.535Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Bar',Updated=TO_TIMESTAMP('2024-10-16 17:03:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543719
;

-- Reference Item: POSPaymentMethod -> CASH_Cash
-- 2024-10-16T14:04:00.220Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Bar',Updated=TO_TIMESTAMP('2024-10-16 17:04:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543719
;

-- Reference Item: POSPaymentMethod -> CASH_Cash
-- 2024-10-16T14:04:01.602Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-16 17:04:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543719
;

-- Element: C_POS_Payment_ID
-- 2024-10-16T14:05:12.740Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='POS Zahlung', PrintName='POS Zahlung',Updated=TO_TIMESTAMP('2024-10-16 17:05:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583269 AND AD_Language='de_CH'
;

-- 2024-10-16T14:05:12.744Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583269,'de_CH') 
;

-- Element: C_POS_Payment_ID
-- 2024-10-16T14:05:14.727Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-16 17:05:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583269 AND AD_Language='en_US'
;

-- 2024-10-16T14:05:14.731Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583269,'en_US') 
;

-- Element: C_POS_Payment_ID
-- 2024-10-16T14:05:18.652Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='POS Zahlung', PrintName='POS Zahlung',Updated=TO_TIMESTAMP('2024-10-16 17:05:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583269 AND AD_Language='de_DE'
;

-- 2024-10-16T14:05:18.655Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583269,'de_DE') 
;

-- 2024-10-16T14:05:18.668Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583269,'de_DE') 
;

-- Reference Item: C_POS_Order_Status -> WP_WaitingPayment
-- 2024-10-16T14:06:53.642Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Warten auf Zahlung',Updated=TO_TIMESTAMP('2024-10-16 17:06:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543716
;

-- Reference Item: C_POS_Order_Status -> WP_WaitingPayment
-- 2024-10-16T14:06:55.380Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Warten auf Zahlung',Updated=TO_TIMESTAMP('2024-10-16 17:06:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543716
;

-- Reference Item: C_POS_Order_Status -> WP_WaitingPayment
-- 2024-10-16T14:06:56.749Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-16 17:06:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543716
;

-- Reference Item: C_POS_Order_Status -> CL_Closed
-- 2024-10-16T14:07:08.432Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Geschlossen',Updated=TO_TIMESTAMP('2024-10-16 17:07:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543760
;

-- Reference Item: C_POS_Order_Status -> CL_Closed
-- 2024-10-16T14:07:10.123Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-16 17:07:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543760
;

-- Reference Item: C_POS_Order_Status -> CL_Closed
-- 2024-10-16T14:07:14.637Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Geschlossen',Updated=TO_TIMESTAMP('2024-10-16 17:07:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543760
;

-- Reference Item: C_POS_Order_Status -> CO_Completed
-- 2024-10-16T14:07:24.804Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Fertiggestellt',Updated=TO_TIMESTAMP('2024-10-16 17:07:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543717
;

-- Reference Item: C_POS_Order_Status -> CO_Completed
-- 2024-10-16T14:07:27.314Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Fertiggestellt',Updated=TO_TIMESTAMP('2024-10-16 17:07:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543717
;

-- Reference Item: C_POS_Order_Status -> CO_Completed
-- 2024-10-16T14:07:29.894Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-16 17:07:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543717
;

-- Reference Item: C_POS_Order_Status -> DR_Drafted
-- 2024-10-16T14:07:42.169Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-16 17:07:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543715
;

-- Reference Item: C_POS_Order_Status -> DR_Drafted
-- 2024-10-16T14:07:46.301Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Entwurf',Updated=TO_TIMESTAMP('2024-10-16 17:07:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543715
;

-- Reference Item: C_POS_Order_Status -> DR_Drafted
-- 2024-10-16T14:07:50.935Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Entwurf',Updated=TO_TIMESTAMP('2024-10-16 17:07:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543715
;

-- Reference Item: C_POS_Order_Status -> VO_Voided
-- 2024-10-16T14:08:27.089Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-16 17:08:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543718
;

-- Reference Item: C_POS_Order_Status -> VO_Voided
-- 2024-10-16T14:08:29.176Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Storniert',Updated=TO_TIMESTAMP('2024-10-16 17:08:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543718
;

-- Reference Item: C_POS_Order_Status -> VO_Voided
-- 2024-10-16T14:08:31.071Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Storniert',Updated=TO_TIMESTAMP('2024-10-16 17:08:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543718
;

-- Element: C_POS_OrderLine_ID
-- 2024-10-16T14:09:54.991Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='POS Auftragsposition', PrintName='POS Auftragsposition',Updated=TO_TIMESTAMP('2024-10-16 17:09:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583268 AND AD_Language='de_CH'
;

-- 2024-10-16T14:09:54.994Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583268,'de_CH') 
;

-- Element: C_POS_OrderLine_ID
-- 2024-10-16T14:09:56.909Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-16 17:09:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583268 AND AD_Language='en_US'
;

-- 2024-10-16T14:09:56.912Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583268,'en_US') 
;

-- Element: C_POS_OrderLine_ID
-- 2024-10-16T14:10:01.380Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='POS Auftragsposition', PrintName='POS Auftragsposition',Updated=TO_TIMESTAMP('2024-10-16 17:10:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583268 AND AD_Language='de_DE'
;

-- 2024-10-16T14:10:01.384Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583268,'de_DE') 
;

-- 2024-10-16T14:10:01.399Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583268,'de_DE') 
;

-- Element: ScannedBarcode
-- 2024-10-16T14:10:53.010Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Barcode gescannt', PrintName='Barcode gescannt',Updated=TO_TIMESTAMP('2024-10-16 17:10:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583296 AND AD_Language='de_CH'
;

-- 2024-10-16T14:10:53.025Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583296,'de_CH') 
;

-- Element: ScannedBarcode
-- 2024-10-16T14:10:54.408Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-16 17:10:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583296 AND AD_Language='en_US'
;

-- 2024-10-16T14:10:54.413Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583296,'en_US') 
;

-- Element: ScannedBarcode
-- 2024-10-16T14:10:56.764Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Barcode gescannt', PrintName='Barcode gescannt',Updated=TO_TIMESTAMP('2024-10-16 17:10:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583296 AND AD_Language='de_DE'
;

-- 2024-10-16T14:10:56.768Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583296,'de_DE') 
;

-- 2024-10-16T14:10:56.782Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583296,'de_DE') 
;

-- Element: AmountTendered
-- 2024-10-16T14:13:12.949Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Bar erhalten', PrintName='Bar erhalten',Updated=TO_TIMESTAMP('2024-10-16 17:13:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=52021 AND AD_Language='de_CH'
;

-- 2024-10-16T14:13:12.952Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(52021,'de_CH') 
;

-- Element: AmountTendered
-- 2024-10-16T14:13:17.336Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Bar erhalten', PrintName='Bar erhalten',Updated=TO_TIMESTAMP('2024-10-16 17:13:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=52021 AND AD_Language='de_DE'
;

-- 2024-10-16T14:13:17.340Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(52021,'de_DE') 
;

-- 2024-10-16T14:13:17.354Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(52021,'de_DE') 
;

-- Element: ChangeBackAmount
-- 2024-10-16T14:13:38.198Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rückgeld', PrintName='Rückgeld',Updated=TO_TIMESTAMP('2024-10-16 17:13:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583323 AND AD_Language='de_CH'
;

-- 2024-10-16T14:13:38.201Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583323,'de_CH') 
;

-- Element: ChangeBackAmount
-- 2024-10-16T14:13:39.407Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-16 17:13:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583323 AND AD_Language='en_US'
;

-- 2024-10-16T14:13:39.412Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583323,'en_US') 
;

-- Element: ChangeBackAmount
-- 2024-10-16T14:13:43.028Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rückgeld', PrintName='Rückgeld',Updated=TO_TIMESTAMP('2024-10-16 17:13:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583323 AND AD_Language='de_DE'
;

-- 2024-10-16T14:13:43.031Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583323,'de_DE') 
;

-- 2024-10-16T14:13:43.044Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583323,'de_DE') 
;

-- Element: POSPaymentProcessingStatus
-- 2024-10-16T14:14:20.018Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Status', PrintName='Status',Updated=TO_TIMESTAMP('2024-10-16 17:14:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583306 AND AD_Language='de_CH'
;

-- 2024-10-16T14:14:20.021Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583306,'de_CH') 
;

-- Element: POSPaymentProcessingStatus
-- 2024-10-16T14:14:23.299Z
UPDATE AD_Element_Trl SET Name='Status', PrintName='Status',Updated=TO_TIMESTAMP('2024-10-16 17:14:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583306 AND AD_Language='en_US'
;

-- 2024-10-16T14:14:23.302Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583306,'en_US') 
;

-- Element: POSPaymentProcessingStatus
-- 2024-10-16T14:14:25.898Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Status', PrintName='Status',Updated=TO_TIMESTAMP('2024-10-16 17:14:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583306 AND AD_Language='de_DE'
;

-- 2024-10-16T14:14:25.901Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583306,'de_DE') 
;

-- 2024-10-16T14:14:25.914Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583306,'de_DE') 
;

-- Reference Item: POSPaymentProcessingStatus -> SUCCESSFUL_SUCCESSFUL
-- 2024-10-16T14:14:54.221Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Erfolgreich',Updated=TO_TIMESTAMP('2024-10-16 17:14:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543740
;

-- Reference Item: POSPaymentProcessingStatus -> SUCCESSFUL_SUCCESSFUL
-- 2024-10-16T14:14:56.342Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Erfolgreich',Updated=TO_TIMESTAMP('2024-10-16 17:14:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543740
;

-- Reference Item: POSPaymentProcessingStatus -> SUCCESSFUL_SUCCESSFUL
-- 2024-10-16T14:14:57.412Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-16 17:14:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543740
;

-- Reference Item: POSPaymentProcessingStatus -> SUCCESSFUL_SUCCESSFUL
-- 2024-10-16T14:15:04.337Z
UPDATE AD_Ref_List_Trl SET Name='Successful',Updated=TO_TIMESTAMP('2024-10-16 17:15:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543740
;

-- Reference Item: POSPaymentProcessingStatus -> PENDING_PENDING
-- 2024-10-16T14:15:19.239Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Pending',Updated=TO_TIMESTAMP('2024-10-16 17:15:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543743
;

-- Reference Item: POSPaymentProcessingStatus -> PENDING_PENDING
-- 2024-10-16T14:15:21.210Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Ausstehend',Updated=TO_TIMESTAMP('2024-10-16 17:15:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543743
;

-- Reference Item: POSPaymentProcessingStatus -> PENDING_PENDING
-- 2024-10-16T14:15:22.991Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Ausstehend',Updated=TO_TIMESTAMP('2024-10-16 17:15:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543743
;

-- Reference Item: POSPaymentProcessingStatus -> NEW_NEW
-- 2024-10-16T14:15:35.086Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='New',Updated=TO_TIMESTAMP('2024-10-16 17:15:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543744
;

-- Reference Item: POSPaymentProcessingStatus -> NEW_NEW
-- 2024-10-16T14:15:38.600Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Neu',Updated=TO_TIMESTAMP('2024-10-16 17:15:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543744
;

-- Reference Item: POSPaymentProcessingStatus -> NEW_NEW
-- 2024-10-16T14:15:40.850Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Neu',Updated=TO_TIMESTAMP('2024-10-16 17:15:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543744
;

-- Reference Item: POSPaymentProcessingStatus -> FAILED_FAILED
-- 2024-10-16T14:15:56.425Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Fehlgeschlagen',Updated=TO_TIMESTAMP('2024-10-16 17:15:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543742
;

-- Reference Item: POSPaymentProcessingStatus -> FAILED_FAILED
-- 2024-10-16T14:15:59.040Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Fehlgeschlagen',Updated=TO_TIMESTAMP('2024-10-16 17:15:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543742
;

-- Reference Item: POSPaymentProcessingStatus -> FAILED_FAILED
-- 2024-10-16T14:16:02.294Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Failed',Updated=TO_TIMESTAMP('2024-10-16 17:16:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543742
;

-- Element: POSPaymentMethod
-- 2024-10-16T14:17:42.163Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Zahlmittel', PrintName='Zahlmittel',Updated=TO_TIMESTAMP('2024-10-16 17:17:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583270 AND AD_Language='de_CH'
;

-- 2024-10-16T14:17:42.167Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583270,'de_CH') 
;

-- Element: POSPaymentMethod
-- 2024-10-16T14:17:48.108Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Payment Method', PrintName='Payment Method',Updated=TO_TIMESTAMP('2024-10-16 17:17:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583270 AND AD_Language='en_US'
;

-- 2024-10-16T14:17:48.111Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583270,'en_US') 
;

-- Element: POSPaymentMethod
-- 2024-10-16T14:17:52.565Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Zahlmittel', PrintName='Zahlmittel',Updated=TO_TIMESTAMP('2024-10-16 17:17:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583270 AND AD_Language='de_DE'
;

-- 2024-10-16T14:17:52.572Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583270,'de_DE') 
;

-- 2024-10-16T14:17:52.585Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583270,'de_DE') 
;

-- Element: POS_CardReader_Name
-- 2024-10-16T14:19:34.365Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Card Reader', PrintName='Card Reader',Updated=TO_TIMESTAMP('2024-10-16 17:19:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583327 AND AD_Language='en_US'
;

-- 2024-10-16T14:19:34.368Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583327,'en_US') 
;

-- Element: POS_CardReader_Name
-- 2024-10-16T14:19:40.864Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Kartenleser', PrintName='Kartenleser',Updated=TO_TIMESTAMP('2024-10-16 17:19:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583327 AND AD_Language='de_CH'
;

-- 2024-10-16T14:19:40.868Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583327,'de_CH') 
;

-- Element: POS_CardReader_Name
-- 2024-10-16T14:19:43.977Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Kartenleser', PrintName='Kartenleser',Updated=TO_TIMESTAMP('2024-10-16 17:19:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583327 AND AD_Language='de_DE'
;

-- 2024-10-16T14:19:43.982Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583327,'de_DE') 
;

-- 2024-10-16T14:19:43.998Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583327,'de_DE') 
;

-- Element: SUMUP_CardReader_ExternalId
-- 2024-10-16T14:20:35.336Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Kartenleser (Externe ID)', PrintName='Kartenleser (Externe ID)',Updated=TO_TIMESTAMP('2024-10-16 17:20:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583324 AND AD_Language='de_CH'
;

-- 2024-10-16T14:20:35.339Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583324,'de_CH') 
;

-- Element: SUMUP_CardReader_ExternalId
-- 2024-10-16T14:20:37.822Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-16 17:20:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583324 AND AD_Language='en_US'
;

-- 2024-10-16T14:20:37.825Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583324,'en_US') 
;

-- Element: SUMUP_CardReader_ExternalId
-- 2024-10-16T14:20:41.804Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Kartenleser (Externe ID)', PrintName='Kartenleser (Externe ID)',Updated=TO_TIMESTAMP('2024-10-16 17:20:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583324 AND AD_Language='de_DE'
;

-- 2024-10-16T14:20:41.807Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583324,'de_DE') 
;

-- 2024-10-16T14:20:41.820Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583324,'de_DE') 
;

-- Element: POS_CardReader_ExternalId
-- 2024-10-16T14:22:55.747Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Kartenleser (Externe ID)', PrintName='Kartenleser (Externe ID)',Updated=TO_TIMESTAMP('2024-10-16 17:22:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583326 AND AD_Language='de_CH'
;

-- 2024-10-16T14:22:55.750Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583326,'de_CH') 
;

-- Element: POS_CardReader_ExternalId
-- 2024-10-16T14:22:57.579Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-16 17:22:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583326 AND AD_Language='en_US'
;

-- 2024-10-16T14:22:57.582Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583326,'en_US') 
;

-- Element: POS_CardReader_ExternalId
-- 2024-10-16T14:23:00.043Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Kartenleser (Externe ID)', PrintName='Kartenleser (Externe ID)',Updated=TO_TIMESTAMP('2024-10-16 17:23:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583326 AND AD_Language='de_DE'
;

-- 2024-10-16T14:23:00.046Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583326,'de_DE') 
;

-- 2024-10-16T14:23:00.048Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583326,'de_DE') 
;




























with to_update as (SELECT r.ad_reference_id, r.name as ref_name, r.entitytype, rl.ad_ref_list_id, rl.value, rl.name, rl_trl.name AS name_trl
                   FROM ad_reference r
                            LEFT OUTER JOIN ad_ref_list rl ON rl.ad_reference_id = r.ad_reference_id
                            LEFT OUTER JOIN ad_ref_list_trl rl_trl ON rl.ad_ref_list_id = rl_trl.ad_ref_list_id AND rl_trl.ad_language = 'de_DE'
                   WHERE r.entitytype = 'de.metas.pos')
update ad_ref_list rl set name=t.name_trl
from to_update t
where rl.ad_ref_list_id=t.ad_ref_list_id
and t.name != t.name_trl;





















