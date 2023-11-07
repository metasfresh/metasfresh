-- Element: Discontinued
-- 2023-10-30T18:41:17.520Z
UPDATE AD_Element_Trl SET Name='Auslaufprodukt', PrintName='Auslaufprodukt',Updated=TO_TIMESTAMP('2023-10-30 20:41:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=278 AND AD_Language='de_DE'
;

-- 2023-10-30T18:41:17.566Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(278,'de_DE') 
;

-- 2023-10-30T18:41:17.572Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(278,'de_DE') 
;

-- Element: Discontinued
-- 2023-10-30T18:41:22.934Z
UPDATE AD_Element_Trl SET Name='Auslaufprodukt', PrintName='Auslaufprodukt',Updated=TO_TIMESTAMP('2023-10-30 20:41:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=278 AND AD_Language='de_CH'
;

-- 2023-10-30T18:41:22.936Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(278,'de_CH') 
;

-- Value: M_ProductPrice_ActivationBasedOnProductDiscontinuedFlag_Process
-- Classname: de.metas.product.process.M_ProductPrice_ActivationBasedOnProductDiscontinuedFlag_Process
-- 2023-11-06T17:37:38.465Z
UPDATE AD_Process SET Description='test_DE',Updated=TO_TIMESTAMP('2023-11-06 19:37:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584763
;

-- Process: M_ProductPrice_ActivationBasedOnProductDiscontinuedFlag_Process(de.metas.product.process.M_ProductPrice_ActivationBasedOnProductDiscontinuedFlag_Process)
-- 2023-11-06T17:38:26.315Z
UPDATE AD_Process_Trl SET Description='test_DE', Help='test_DE',Updated=TO_TIMESTAMP('2023-11-06 19:38:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584763
;

-- Value: M_ProductPrice_ActivationBasedOnProductDiscontinuedFlag_Process
-- Classname: de.metas.product.process.M_ProductPrice_ActivationBasedOnProductDiscontinuedFlag_Process
-- 2023-11-06T17:38:28.483Z
UPDATE AD_Process SET Description='test_DE', Help='test_DE', Name='Produktpreise (de)aktivieren',Updated=TO_TIMESTAMP('2023-11-06 19:38:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584763
;

-- Process: M_ProductPrice_ActivationBasedOnProductDiscontinuedFlag_Process(de.metas.product.process.M_ProductPrice_ActivationBasedOnProductDiscontinuedFlag_Process)
-- 2023-11-06T17:38:28.479Z
UPDATE AD_Process_Trl SET Description='test_DE', Help='test_DE',Updated=TO_TIMESTAMP('2023-11-06 19:38:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=584763
;

-- Process: M_ProductPrice_ActivationBasedOnProductDiscontinuedFlag_Process(de.metas.product.process.M_ProductPrice_ActivationBasedOnProductDiscontinuedFlag_Process)
-- 2023-11-06T17:38:34.208Z
UPDATE AD_Process_Trl SET Description='test_EN', Help='test_EN',Updated=TO_TIMESTAMP('2023-11-06 19:38:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584763
;

-- Process: M_ProductPrice_ActivationBasedOnProductDiscontinuedFlag_Process(de.metas.product.process.M_ProductPrice_ActivationBasedOnProductDiscontinuedFlag_Process)
-- 2023-11-06T17:38:38.692Z
UPDATE AD_Process_Trl SET Description='test_DE', Help='test_DE',Updated=TO_TIMESTAMP('2023-11-06 19:38:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Process_ID=584763
;

-- Process: M_ProductPrice_ActivationBasedOnProductDiscontinuedFlag_Process(de.metas.product.process.M_ProductPrice_ActivationBasedOnProductDiscontinuedFlag_Process)
-- ParameterName: ValidFrom
-- 2023-11-07T08:39:43.166Z
UPDATE AD_Process_Para SET DisplayLogic='1=0',Updated=TO_TIMESTAMP('2023-11-07 10:39:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541883
;

-- Value: ActivationBasedOnProductDiscontinuedFlag_Process.MissingDiscontinuedFrom
-- 2023-11-07T12:34:40.362Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545357,0,TO_TIMESTAMP('2023-11-07 14:34:40','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Eingestellt ab muss angegeben werden, wenn das Produkt als Auslaufprodukt gekennzeichnet ist.','E',TO_TIMESTAMP('2023-11-07 14:34:40','YYYY-MM-DD HH24:MI:SS'),100,'ActivationBasedOnProductDiscontinuedFlag_Process.MissingDiscontinuedFrom')
;

-- 2023-11-07T12:34:40.369Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545357 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: ActivationBasedOnProductDiscontinuedFlag_Process.MissingDiscontinuedFrom
-- 2023-11-07T12:35:00.993Z
UPDATE AD_Message_Trl SET MsgText='Discontinued From must be specified when the Product is marked as Discontinued.',Updated=TO_TIMESTAMP('2023-11-07 14:35:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545357
;

