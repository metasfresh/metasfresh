-- 2022-11-25T11:26:20.550Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581735,0,TO_TIMESTAMP('2022-11-25 13:26:20','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Inco Term','Inco Term',TO_TIMESTAMP('2022-11-25 13:26:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-25T11:26:20.552Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581735 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Inco Term
-- Column: M_Delivery_Planning.C_Incoterms_ID
-- 2022-11-25T11:26:30.931Z
UPDATE AD_Field SET AD_Name_ID=581735, Description=NULL, Help=NULL, Name='Inco Term',Updated=TO_TIMESTAMP('2022-11-25 13:26:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708079
;

-- 2022-11-25T11:26:30.963Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581735) 
;

-- 2022-11-25T11:26:31.008Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708079
;

-- 2022-11-25T11:26:31.010Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708079)
;

-- 2022-11-25T11:27:26.701Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581736,0,TO_TIMESTAMP('2022-11-25 13:27:26','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Order','Order',TO_TIMESTAMP('2022-11-25 13:27:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-25T11:27:26.702Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581736 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Order
-- Column: M_Delivery_Planning.OrderDocumentNo
-- 2022-11-25T11:28:02.916Z
UPDATE AD_Field SET AD_Name_ID=581736, Description=NULL, Help=NULL, Name='Order',Updated=TO_TIMESTAMP('2022-11-25 13:28:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708080
;

-- 2022-11-25T11:28:02.918Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581736) 
;

-- 2022-11-25T11:28:02.922Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708080
;

-- 2022-11-25T11:28:02.923Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708080)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Order
-- Column: M_Delivery_Planning.C_Order_ID
-- 2022-11-25T11:31:00.775Z
UPDATE AD_Field SET AD_Name_ID=581736, Description=NULL, Help=NULL, Name='Order',Updated=TO_TIMESTAMP('2022-11-25 13:31:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708111
;

-- 2022-11-25T11:31:00.777Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581736) 
;

-- 2022-11-25T11:31:00.780Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708111
;

-- 2022-11-25T11:31:00.781Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708111)
;

-- 2022-11-25T11:33:35.318Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581737,0,TO_TIMESTAMP('2022-11-25 13:33:35','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Order Line','Order Line',TO_TIMESTAMP('2022-11-25 13:33:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-25T11:33:35.320Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581737 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Order Line
-- Column: M_Delivery_Planning.C_OrderLine_ID
-- 2022-11-25T11:36:44.188Z
UPDATE AD_Field SET AD_Name_ID=581737, Description=NULL, Help=NULL, Name='Order Line',Updated=TO_TIMESTAMP('2022-11-25 13:36:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708152
;

-- 2022-11-25T11:36:44.190Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581737) 
;

-- 2022-11-25T11:36:44.193Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708152
;

-- 2022-11-25T11:36:44.194Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708152)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Ship-to location
-- Column: M_Delivery_Planning.C_BPartner_Location_ID
-- 2022-11-25T11:39:50.512Z
UPDATE AD_Field SET AD_Name_ID=581681, Description=NULL, Help=NULL, Name='Ship-to location',Updated=TO_TIMESTAMP('2022-11-25 13:39:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708084
;

-- 2022-11-25T11:39:50.514Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581681) 
;

-- 2022-11-25T11:39:50.517Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708084
;

-- 2022-11-25T11:39:50.518Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708084)
;

-- 2022-11-25T14:10:13.600Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581739,0,TO_TIMESTAMP('2022-11-25 16:10:13','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Material Name','Material Name',TO_TIMESTAMP('2022-11-25 16:10:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-25T14:10:13.602Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581739 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Material Name
-- Column: M_Delivery_Planning.ProductName
-- 2022-11-25T14:10:24.275Z
UPDATE AD_Field SET AD_Name_ID=581739, Description=NULL, Help=NULL, Name='Material Name',Updated=TO_TIMESTAMP('2022-11-25 16:10:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708086
;

-- 2022-11-25T14:10:24.277Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581739) 
;

-- 2022-11-25T14:10:24.281Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708086
;

-- 2022-11-25T14:10:24.282Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708086)
;

-- 2022-11-25T14:11:39.920Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581740,0,TO_TIMESTAMP('2022-11-25 16:11:39','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Ordered Qty','Ordered Qty',TO_TIMESTAMP('2022-11-25 16:11:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-25T14:11:39.921Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581740 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-11-25T14:11:57.062Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581741,0,TO_TIMESTAMP('2022-11-25 16:11:56','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Qty Total Open','Qty Total Open',TO_TIMESTAMP('2022-11-25 16:11:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-25T14:11:57.064Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581741 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Ordered Qty
-- Column: M_Delivery_Planning.QtyOrdered
-- 2022-11-25T14:12:09.760Z
UPDATE AD_Field SET AD_Name_ID=581740, Description=NULL, Help=NULL, Name='Ordered Qty',Updated=TO_TIMESTAMP('2022-11-25 16:12:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708090
;

-- 2022-11-25T14:12:09.762Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581740) 
;

-- 2022-11-25T14:12:09.767Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708090
;

-- 2022-11-25T14:12:09.768Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708090)
;

-- 2022-11-25T14:13:01.031Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581742,0,TO_TIMESTAMP('2022-11-25 16:13:00','YYYY-MM-DD HH24:MI:SS'),100,'D','','Y','Warehouse','Warehouse',TO_TIMESTAMP('2022-11-25 16:13:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-25T14:13:01.033Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581742 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Warehouse
-- Column: M_Delivery_Planning.WarehouseName
-- 2022-11-25T14:13:18.620Z
UPDATE AD_Field SET AD_Name_ID=581742, Description=NULL, Help='', Name='Warehouse',Updated=TO_TIMESTAMP('2022-11-25 16:13:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708092
;

-- 2022-11-25T14:13:18.621Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581742) 
;

-- 2022-11-25T14:13:18.624Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708092
;

-- 2022-11-25T14:13:18.625Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708092)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Warehouse
-- Column: M_Delivery_Planning.M_Warehouse_ID
-- 2022-11-25T14:13:32.565Z
UPDATE AD_Field SET AD_Name_ID=581742, Description=NULL, Help='', Name='Warehouse',Updated=TO_TIMESTAMP('2022-11-25 16:13:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708093
;

-- 2022-11-25T14:13:32.566Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581742) 
;

-- 2022-11-25T14:13:32.569Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708093
;

-- 2022-11-25T14:13:32.570Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708093)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Ursprungsland
-- Column: M_Delivery_Planning.C_OriginCountry_ID
-- 2022-11-25T14:14:49.545Z
UPDATE AD_Field SET AD_Name_ID=579119, Description='Land des Leistungserbringers', Help='Land des Leistungserbringers', Name='Ursprungsland',Updated=TO_TIMESTAMP('2022-11-25 16:14:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708204
;

-- 2022-11-25T14:14:49.547Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579119) 
;

-- 2022-11-25T14:14:49.549Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708204
;

-- 2022-11-25T14:14:49.550Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708204)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Country Of Origin
-- Column: M_Delivery_Planning.C_OriginCountry_ID
-- 2022-11-25T14:15:10.992Z
UPDATE AD_Field SET AD_Name_ID=581694, Description=NULL, Help=NULL, Name='Country Of Origin',Updated=TO_TIMESTAMP('2022-11-25 16:15:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708204
;

-- 2022-11-25T14:15:10.993Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581694) 
;

-- 2022-11-25T14:15:10.996Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708204
;

-- 2022-11-25T14:15:10.997Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708204)
;

-- Element: OriginCountry
-- 2022-11-25T14:15:36.742Z
UPDATE AD_Element_Trl SET Name='Country of Origin',Updated=TO_TIMESTAMP('2022-11-25 16:15:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581694 AND AD_Language='de_CH'
;

-- 2022-11-25T14:15:36.746Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581694,'de_CH') 
;

-- Element: OriginCountry
-- 2022-11-25T14:15:40.950Z
UPDATE AD_Element_Trl SET Name='Country of Origin',Updated=TO_TIMESTAMP('2022-11-25 16:15:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581694 AND AD_Language='de_DE'
;

-- 2022-11-25T14:15:40.952Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581694,'de_DE') 
;

-- 2022-11-25T14:15:40.954Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581694,'de_DE') 
;

-- Element: OriginCountry
-- 2022-11-25T14:15:44.344Z
UPDATE AD_Element_Trl SET Name='Country of Origin',Updated=TO_TIMESTAMP('2022-11-25 16:15:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581694 AND AD_Language='en_US'
;

-- 2022-11-25T14:15:44.346Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581694,'en_US') 
;

-- Element: OriginCountry
-- 2022-11-25T14:15:47.660Z
UPDATE AD_Element_Trl SET Name='Country of Origin',Updated=TO_TIMESTAMP('2022-11-25 16:15:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581694 AND AD_Language='nl_NL'
;

-- 2022-11-25T14:15:47.662Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581694,'nl_NL') 
;

-- Element: OriginCountry
-- 2022-11-25T14:15:57.678Z
UPDATE AD_Element_Trl SET PrintName='Country of Origin',Updated=TO_TIMESTAMP('2022-11-25 16:15:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581694 AND AD_Language='de_CH'
;

-- 2022-11-25T14:15:57.680Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581694,'de_CH') 
;

-- Element: OriginCountry
-- 2022-11-25T14:15:58.827Z
UPDATE AD_Element_Trl SET PrintName='Country of Origin',Updated=TO_TIMESTAMP('2022-11-25 16:15:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581694 AND AD_Language='de_DE'
;

-- 2022-11-25T14:15:58.831Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581694,'de_DE') 
;

-- 2022-11-25T14:15:58.832Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581694,'de_DE') 
;

-- Element: OriginCountry
-- 2022-11-25T14:15:59.898Z
UPDATE AD_Element_Trl SET PrintName='Country of Origin',Updated=TO_TIMESTAMP('2022-11-25 16:15:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581694 AND AD_Language='en_US'
;

-- 2022-11-25T14:15:59.901Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581694,'en_US') 
;

-- Element: OriginCountry
-- 2022-11-25T14:16:01.906Z
UPDATE AD_Element_Trl SET PrintName='Country of Origin',Updated=TO_TIMESTAMP('2022-11-25 16:16:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581694 AND AD_Language='nl_NL'
;

-- 2022-11-25T14:16:01.908Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581694,'nl_NL') 
;

-- 2022-11-25T14:17:05.478Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581743,0,TO_TIMESTAMP('2022-11-25 16:17:05','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Material Receipt Candidate','Material Receipt Candidate',TO_TIMESTAMP('2022-11-25 16:17:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-25T14:17:05.479Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581743 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Material Receipt Candidate
-- Column: M_Delivery_Planning.M_ReceiptSchedule_ID
-- 2022-11-25T14:17:19.723Z
UPDATE AD_Field SET AD_Name_ID=581743, Description=NULL, Help=NULL, Name='Material Receipt Candidate',Updated=TO_TIMESTAMP('2022-11-25 16:17:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708112
;

-- 2022-11-25T14:17:19.725Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581743) 
;

-- 2022-11-25T14:17:19.728Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708112
;

-- 2022-11-25T14:17:19.729Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708112)
;

-- 2022-11-25T14:18:05.531Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581744,0,TO_TIMESTAMP('2022-11-25 16:18:05','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Shipment Disposition','Shipment Disposition',TO_TIMESTAMP('2022-11-25 16:18:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-25T14:18:05.533Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581744 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Shipment Disposition
-- Column: M_Delivery_Planning.M_ShipmentSchedule_ID
-- 2022-11-25T14:18:44.285Z
UPDATE AD_Field SET AD_Name_ID=581744, Description=NULL, Help=NULL, Name='Shipment Disposition',Updated=TO_TIMESTAMP('2022-11-25 16:18:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708113
;

-- 2022-11-25T14:18:44.287Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581744) 
;

-- 2022-11-25T14:18:44.291Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708113
;

-- 2022-11-25T14:18:44.293Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708113)
;

-- 2022-11-25T14:20:18.102Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581745,0,TO_TIMESTAMP('2022-11-25 16:20:17','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Business Partner','Business Partner',TO_TIMESTAMP('2022-11-25 16:20:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-25T14:20:18.104Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581745 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Business Partner
-- Column: M_Delivery_Planning.BPartnerName
-- 2022-11-25T14:20:37.305Z
UPDATE AD_Field SET AD_Name_ID=581745, Description=NULL, Help=NULL, Name='Business Partner',Updated=TO_TIMESTAMP('2022-11-25 16:20:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708082
;

-- 2022-11-25T14:20:37.306Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581745) 
;

-- 2022-11-25T14:20:37.309Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708082
;

-- 2022-11-25T14:20:37.310Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708082)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Business Partner
-- Column: M_Delivery_Planning.C_BPartner_ID
-- 2022-11-25T14:21:18.877Z
UPDATE AD_Field SET AD_Name_ID=581745, Description=NULL, Help=NULL, Name='Business Partner',Updated=TO_TIMESTAMP('2022-11-25 16:21:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708083
;

-- 2022-11-25T14:21:18.879Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581745) 
;

-- 2022-11-25T14:21:18.882Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708083
;

-- 2022-11-25T14:21:18.882Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708083)
;

-- 2022-11-25T14:21:52.422Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581746,0,TO_TIMESTAMP('2022-11-25 16:21:52','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Reference Number','Reference Number',TO_TIMESTAMP('2022-11-25 16:21:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-25T14:21:52.423Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581746 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Reference Number
-- Column: M_Delivery_Planning.POReference
-- 2022-11-25T14:22:10.657Z
UPDATE AD_Field SET AD_Name_ID=581746, Description=NULL, Help=NULL, Name='Reference Number',Updated=TO_TIMESTAMP('2022-11-25 16:22:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708081
;

-- 2022-11-25T14:22:10.659Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581746) 
;

-- 2022-11-25T14:22:10.662Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708081
;

-- 2022-11-25T14:22:10.662Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708081)
;

-- 2022-11-25T14:22:42.563Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581747,0,TO_TIMESTAMP('2022-11-25 16:22:42','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Material Code','Material Code',TO_TIMESTAMP('2022-11-25 16:22:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-25T14:22:42.564Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581747 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Material Code
-- Column: M_Delivery_Planning.ProductValue
-- 2022-11-25T14:22:50.201Z
UPDATE AD_Field SET AD_Name_ID=581747, Description=NULL, Help=NULL, Name='Material Code',Updated=TO_TIMESTAMP('2022-11-25 16:22:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708087
;

-- 2022-11-25T14:22:50.203Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581747) 
;

-- 2022-11-25T14:22:50.205Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708087
;

-- 2022-11-25T14:22:50.206Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708087)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Transport Order
-- Column: M_Delivery_Planning.M_ShipperTransportation_ID
-- 2022-11-25T14:23:25.506Z
UPDATE AD_Field SET AD_Name_ID=581700, Description=NULL, Help=NULL, Name='Transport Order',Updated=TO_TIMESTAMP('2022-11-25 16:23:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708109
;

-- 2022-11-25T14:23:25.507Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581700) 
;

-- 2022-11-25T14:23:25.510Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708109
;

-- 2022-11-25T14:23:25.511Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708109)
;



-- 2022-11-25T14:26:15.030Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581748,0,TO_TIMESTAMP('2022-11-25 16:26:14','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Material','Material',TO_TIMESTAMP('2022-11-25 16:26:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-25T14:26:15.031Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581748 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Material
-- Column: M_Delivery_Planning.M_Product_ID
-- 2022-11-25T14:26:31.218Z
UPDATE AD_Field SET AD_Name_ID=581748, Description=NULL, Help=NULL, Name='Material',Updated=TO_TIMESTAMP('2022-11-25 16:26:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708110
;

-- 2022-11-25T14:26:31.219Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581748) 
;

-- 2022-11-25T14:26:31.225Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708110
;

-- 2022-11-25T14:26:31.226Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708110)
;





-- 2022-11-25T14:47:32.208Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581749,0,TO_TIMESTAMP('2022-11-25 16:47:32','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Country of destination','Country of destination',TO_TIMESTAMP('2022-11-25 16:47:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-25T14:47:32.214Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581749 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Country of destination
-- Column: M_Delivery_Planning.C_DestinationCountry_ID
-- 2022-11-25T14:47:42.458Z
UPDATE AD_Field SET AD_Name_ID=581749, Description=NULL, Help=NULL, Name='Country of destination',Updated=TO_TIMESTAMP('2022-11-25 16:47:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708202
;

-- 2022-11-25T14:47:42.460Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581749) 
;

-- 2022-11-25T14:47:42.462Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708202
;

-- 2022-11-25T14:47:42.464Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708202)
;

