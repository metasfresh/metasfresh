-- 2023-09-06T08:56:56.725Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582711,0,'Finished_Product_UOM',TO_TIMESTAMP('2023-09-06 11:56:56','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','2_Maßeinheit','2_Maßeinheit',TO_TIMESTAMP('2023-09-06 11:56:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-09-06T08:56:56.732Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582711 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;




-- Element: Finished_Product_Lot
-- 2023-09-06T09:01:03.170Z
UPDATE AD_Element_Trl SET Name='2_Charge', PrintName='2_Charge',Updated=TO_TIMESTAMP('2023-09-06 12:01:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582680 AND AD_Language='de_CH'
;

-- 2023-09-06T09:01:03.196Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582680,'de_CH') 
;

-- Element: Finished_Product_Lot
-- 2023-09-06T09:01:06.827Z
UPDATE AD_Element_Trl SET Name='2_Charge', PrintName='2_Charge',Updated=TO_TIMESTAMP('2023-09-06 12:01:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582680 AND AD_Language='de_DE'
;

-- 2023-09-06T09:01:06.829Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582680,'de_DE') 
;

-- 2023-09-06T09:01:06.831Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582680,'de_DE') 
;

-- Element: Finished_Product_Lot
-- 2023-09-06T09:01:10.905Z
UPDATE AD_Element_Trl SET Name='2_Charge', PrintName='2_Charge',Updated=TO_TIMESTAMP('2023-09-06 12:01:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582680 AND AD_Language='en_US'
;

-- 2023-09-06T09:01:10.907Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582680,'en_US') 
;

-- Element: Finished_Product_Lot
-- 2023-09-06T09:01:14.029Z
UPDATE AD_Element_Trl SET Name='2_Charge', PrintName='2_Charge',Updated=TO_TIMESTAMP('2023-09-06 12:01:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582680 AND AD_Language='fr_CH'
;

-- 2023-09-06T09:01:14.031Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582680,'fr_CH') 
;

-- Element: Finished_Product_Lot
-- 2023-09-06T09:01:20.697Z
UPDATE AD_Element_Trl SET Name='2_Charge', PrintName='2_Charge',Updated=TO_TIMESTAMP('2023-09-06 12:01:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582680 AND AD_Language='nl_NL'
;

-- 2023-09-06T09:01:20.700Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582680,'nl_NL') 
;

-- Element: Finished_Product_Mhd
-- 2023-09-06T09:05:00.116Z
UPDATE AD_Element_Trl SET Name='2_MHD', PrintName='2_MHD',Updated=TO_TIMESTAMP('2023-09-06 12:05:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582682 AND AD_Language='de_CH'
;

-- 2023-09-06T09:05:00.119Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582682,'de_CH') 
;

-- Element: Finished_Product_Mhd
-- 2023-09-06T09:05:02.957Z
UPDATE AD_Element_Trl SET Name='2_MHD', PrintName='2_MHD',Updated=TO_TIMESTAMP('2023-09-06 12:05:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582682 AND AD_Language='de_DE'
;

-- 2023-09-06T09:05:02.959Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582682,'de_DE') 
;

-- 2023-09-06T09:05:02.960Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582682,'de_DE') 
;

-- Element: Finished_Product_Mhd
-- 2023-09-06T09:05:06.881Z
UPDATE AD_Element_Trl SET Name='2_MHD', PrintName='2_MHD',Updated=TO_TIMESTAMP('2023-09-06 12:05:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582682 AND AD_Language='en_US'
;

-- 2023-09-06T09:05:06.883Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582682,'en_US') 
;

-- Element: Finished_Product_Mhd
-- 2023-09-06T09:05:09.387Z
UPDATE AD_Element_Trl SET Name='2_MHD', PrintName='2_MHD',Updated=TO_TIMESTAMP('2023-09-06 12:05:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582682 AND AD_Language='fr_CH'
;

-- 2023-09-06T09:05:09.389Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582682,'fr_CH') 
;

-- Element: Finished_Product_Mhd
-- 2023-09-06T09:05:13.425Z
UPDATE AD_Element_Trl SET Name='2_MHD', PrintName='2_MHD',Updated=TO_TIMESTAMP('2023-09-06 12:05:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582682 AND AD_Language='nl_NL'
;

-- 2023-09-06T09:05:13.428Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582682,'nl_NL') 
;

-- 2023-09-06T09:13:48.745Z
UPDATE AD_Element SET ColumnName='Prod_Stock',Updated=TO_TIMESTAMP('2023-09-06 12:13:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582688
;

-- 2023-09-06T09:13:48.747Z
UPDATE AD_Column SET ColumnName='Prod_Stock' WHERE AD_Element_ID=582688
;

-- 2023-09-06T09:13:48.748Z
UPDATE AD_Process_Para SET ColumnName='Prod_Stock' WHERE AD_Element_ID=582688
;

-- 2023-09-06T09:13:48.750Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582688,'de_DE') 
;

-- Element: Prod_Stock
-- 2023-09-06T09:14:18.236Z
UPDATE AD_Element_Trl SET Name='2_Lagerbestand', PrintName='2_Lagerbestand',Updated=TO_TIMESTAMP('2023-09-06 12:14:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582688 AND AD_Language='de_CH'
;

-- 2023-09-06T09:14:18.238Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582688,'de_CH') 
;

-- Element: Prod_Stock
-- 2023-09-06T09:14:21.352Z
UPDATE AD_Element_Trl SET Name='2_Lagerbestand', PrintName='2_Lagerbestand',Updated=TO_TIMESTAMP('2023-09-06 12:14:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582688 AND AD_Language='de_DE'
;

-- 2023-09-06T09:14:21.354Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582688,'de_DE') 
;

-- 2023-09-06T09:14:21.355Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582688,'de_DE') 
;

-- Element: Prod_Stock
-- 2023-09-06T09:14:24.032Z
UPDATE AD_Element_Trl SET Name='2_Lagerbestand', PrintName='2_Lagerbestand',Updated=TO_TIMESTAMP('2023-09-06 12:14:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582688 AND AD_Language='en_US'
;

-- 2023-09-06T09:14:24.034Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582688,'en_US') 
;

-- Element: Prod_Stock
-- 2023-09-06T09:14:26.650Z
UPDATE AD_Element_Trl SET Name='2_Lagerbestand', PrintName='2_Lagerbestand',Updated=TO_TIMESTAMP('2023-09-06 12:14:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582688 AND AD_Language='fr_CH'
;

-- 2023-09-06T09:14:26.652Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582688,'fr_CH') 
;

-- Element: Prod_Stock
-- 2023-09-06T09:14:38.344Z
UPDATE AD_Element_Trl SET Name='2_Lagerbestand', PrintName='2_Lagerbestand',Updated=TO_TIMESTAMP('2023-09-06 12:14:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582688 AND AD_Language='nl_NL'
;

-- 2023-09-06T09:14:38.346Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582688,'nl_NL') 
;

-- 2023-09-06T09:31:25.996Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582712,0,'Shipment_Date',TO_TIMESTAMP('2023-09-06 12:31:25','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','2_Belegdatum','2_Belegdatum',TO_TIMESTAMP('2023-09-06 12:31:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-09-06T09:31:25.998Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582712 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: Vendor_Lot
-- 2023-09-06T09:31:44.063Z
UPDATE AD_Element_Trl SET Name='2_Lieferantencharge', PrintName='2_Lieferantencharge',Updated=TO_TIMESTAMP('2023-09-06 12:31:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582681 AND AD_Language='nl_NL'
;

-- 2023-09-06T09:31:44.065Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582681,'nl_NL') 
;

-- Element: Vendor_Lot
-- 2023-09-06T09:31:46.607Z
UPDATE AD_Element_Trl SET Name='2_Lieferantencharge', PrintName='2_Lieferantencharge',Updated=TO_TIMESTAMP('2023-09-06 12:31:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582681 AND AD_Language='fr_CH'
;

-- 2023-09-06T09:31:46.610Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582681,'fr_CH') 
;

-- Element: Vendor_Lot
-- 2023-09-06T09:31:49.304Z
UPDATE AD_Element_Trl SET Name='2_Lieferantencharge', PrintName='2_Lieferantencharge',Updated=TO_TIMESTAMP('2023-09-06 12:31:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582681 AND AD_Language='en_US'
;

-- 2023-09-06T09:31:49.306Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582681,'en_US') 
;

-- Element: Vendor_Lot
-- 2023-09-06T09:31:53.415Z
UPDATE AD_Element_Trl SET Name='2_Lieferantencharge', PrintName='2_Lieferantencharge',Updated=TO_TIMESTAMP('2023-09-06 12:31:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582681 AND AD_Language='de_DE'
;

-- 2023-09-06T09:31:53.417Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582681,'de_DE') 
;

-- 2023-09-06T09:31:53.419Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582681,'de_DE') 
;

-- Element: Vendor_Lot
-- 2023-09-06T09:31:56.054Z
UPDATE AD_Element_Trl SET Name='2_Lieferantencharge', PrintName='Leer2_Lieferantencharge',Updated=TO_TIMESTAMP('2023-09-06 12:31:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582681 AND AD_Language='de_CH'
;

-- 2023-09-06T09:31:56.056Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582681,'de_CH') 
;

