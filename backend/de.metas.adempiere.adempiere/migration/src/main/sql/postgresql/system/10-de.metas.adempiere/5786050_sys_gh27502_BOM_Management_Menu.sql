-- Run mode: SWING_CLIENT

-- 2026-01-30T11:08:12.406Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584477,0,TO_TIMESTAMP('2026-01-30 11:08:12.052000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Stücklistenverwaltung','Stücklistenverwaltung',TO_TIMESTAMP('2026-01-30 11:08:12.052000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-30T11:08:12.475Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584477 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2026-01-30T11:09:26.507Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='BOM Management', PrintName='BOM Management',Updated=TO_TIMESTAMP('2026-01-30 11:09:26.507000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584477 AND AD_Language='en_US'
;

-- 2026-01-30T11:09:26.574Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-01-30T11:09:43.452Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584477,'en_US')
;

-- Name: Stücklistenverwaltung
-- Action Type: null
-- 2026-01-30T11:11:51.843Z
INSERT INTO AD_Menu (AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES (0,584477,542294,0,TO_TIMESTAMP('2026-01-30 11:11:51.296000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Stücklistenverwaltung','Y','N','N','N','Y','Stücklistenverwaltung',TO_TIMESTAMP('2026-01-30 11:11:51.296000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-30T11:11:51.908Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542294 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2026-01-30T11:11:51.972Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542294, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542294)
;

-- 2026-01-30T11:11:52.036Z
/* DDL */  select update_menu_translation_from_ad_element(584477)
;

-- Reordering children of `Product Management`
-- Node name: `Replenishment`
-- 2026-01-30T11:12:08.831Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542202 AND AD_Tree_ID=10
;

-- Node name: `Product Data Capture (Jasper)`
-- 2026-01-30T11:12:08.890Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541293 AND AD_Tree_ID=10
;

-- Node name: `Product`
-- 2026-01-30T11:12:08.948Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000034 AND AD_Tree_ID=10
;

-- Node name: `Customs Tariff`
-- 2026-01-30T11:12:09.007Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541391 AND AD_Tree_ID=10
;

-- Node name: `Commodity Number`
-- 2026-01-30T11:12:09.075Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541468 AND AD_Tree_ID=10
;

-- Node name: `Bill of Material Version`
-- 2026-01-30T11:12:09.132Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000092 AND AD_Tree_ID=10
;

-- Node name: `Components of the BOM & Formula`
-- 2026-01-30T11:12:09.191Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541376 AND AD_Tree_ID=10
;

-- Node name: `Discount Schema`
-- 2026-01-30T11:12:09.249Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000093 AND AD_Tree_ID=10
;

-- Node name: `Discount Rows`
-- 2026-01-30T11:12:09.327Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541230 AND AD_Tree_ID=10
;

-- Node name: `Lot control`
-- 2026-01-30T11:12:09.432Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541062 AND AD_Tree_ID=10
;

-- Node name: `BPartner Product statistics`
-- 2026-01-30T11:12:09.512Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541194 AND AD_Tree_ID=10
;

-- Node name: `Hazard Symbol`
-- 2026-01-30T11:12:09.577Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541870 AND AD_Tree_ID=10
;

-- Node name: `Product Certification`
-- 2026-01-30T11:12:09.634Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541904 AND AD_Tree_ID=10
;

-- Node name: `Hazard Symbol Trl`
-- 2026-01-30T11:12:09.692Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541871 AND AD_Tree_ID=10
;

-- Node name: `Download Partner Products`
-- 2026-01-30T11:12:09.764Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541411 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2026-01-30T11:12:09.843Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000044 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2026-01-30T11:12:09.985Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000035 AND AD_Tree_ID=10
;

-- Node name: `Sales`
-- 2026-01-30T11:12:10.385Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000010 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2026-01-30T11:12:10.772Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000037 AND AD_Tree_ID=10
;

-- Node name: `Package-Licensing`
-- 2026-01-30T11:12:10.831Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542248 AND AD_Tree_ID=10
;

-- Node name: `Produkt Marktpläze`
-- 2026-01-30T11:12:10.919Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541864 AND AD_Tree_ID=10
;

-- Node name: `Bill of Material`
-- 2026-01-30T11:12:10.979Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541824 AND AD_Tree_ID=10
;

-- Node name: `Additives translation`
-- 2026-01-30T11:12:11.038Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541873 AND AD_Tree_ID=10
;

-- Node name: `Waste Management`
-- 2026-01-30T11:12:11.098Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542270 AND AD_Tree_ID=10
;

-- Node name: `Food`
-- 2026-01-30T11:12:11.157Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542272 AND AD_Tree_ID=10
;

-- Node name: `Stücklistenverwaltung`
-- 2026-01-30T11:12:11.215Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542294 AND AD_Tree_ID=10
;

-- Reordering children of `webUI`
-- Node name: `CRM`
-- 2026-01-30T11:12:19.620Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000008 AND AD_Tree_ID=10
;

-- Node name: `Marketing`
-- 2026-01-30T11:12:19.678Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541091 AND AD_Tree_ID=10
;

-- Node name: `Product Management`
-- 2026-01-30T11:12:19.740Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000009 AND AD_Tree_ID=10
;

-- Node name: `Stücklistenverwaltung`
-- 2026-01-30T11:12:19.801Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542294 AND AD_Tree_ID=10
;

-- Node name: `Purchase`
-- 2026-01-30T11:12:19.862Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000011 AND AD_Tree_ID=10
;

-- Node name: `Pricing`
-- 2026-01-30T11:12:19.923Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540752 AND AD_Tree_ID=10
;

-- Node name: `Warehouse Management`
-- 2026-01-30T11:12:19.988Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000012 AND AD_Tree_ID=10
;

-- Node name: `Contract Management`
-- 2026-01-30T11:12:20.048Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000013 AND AD_Tree_ID=10
;

-- Node name: `Manufacturing`
-- 2026-01-30T11:12:20.108Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000014 AND AD_Tree_ID=10
;

-- Node name: `Material Receipt`
-- 2026-01-30T11:12:20.167Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000017 AND AD_Tree_ID=10
;

-- Node name: `Billing`
-- 2026-01-30T11:12:20.228Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000018 AND AD_Tree_ID=10
;

-- Node name: `Finance`
-- 2026-01-30T11:12:20.298Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000015 AND AD_Tree_ID=10
;

-- Node name: `Logistics`
-- 2026-01-30T11:12:20.360Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000016 AND AD_Tree_ID=10
;

-- Node name: `Shipment`
-- 2026-01-30T11:12:20.421Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000019 AND AD_Tree_ID=10
;

-- Node name: `Pharma`
-- 2026-01-30T11:12:20.482Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541012 AND AD_Tree_ID=10
;

-- Node name: `Project Management`
-- 2026-01-30T11:12:20.542Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541329 AND AD_Tree_ID=10
;

-- Node name: `Forum Datenaustausch`
-- 2026-01-30T11:12:20.602Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541229 AND AD_Tree_ID=10
;

-- Node name: `Seminar-Verwaltung`
-- 2026-01-30T11:12:20.663Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541340 AND AD_Tree_ID=10
;

-- Node name: `Client/ Organisation`
-- 2026-01-30T11:12:20.723Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540833 AND AD_Tree_ID=10
;

-- Node name: `Service delivery`
-- 2026-01-30T11:12:20.782Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541428 AND AD_Tree_ID=10
;

-- Node name: `System`
-- 2026-01-30T11:12:20.868Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000098 AND AD_Tree_ID=10
;

-- Reordering children of `Stücklistenverwaltung`
-- Node name: `Bill of Material`
-- 2026-01-30T11:12:56.401Z
UPDATE AD_TreeNodeMM SET Parent_ID=542294, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541824 AND AD_Tree_ID=10
;

-- Reordering children of `Stücklistenverwaltung`
-- Node name: `Bill of Material Version`
-- 2026-01-30T11:13:04.575Z
UPDATE AD_TreeNodeMM SET Parent_ID=542294, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000092 AND AD_Tree_ID=10
;

-- Node name: `Bill of Material`
-- 2026-01-30T11:13:04.635Z
UPDATE AD_TreeNodeMM SET Parent_ID=542294, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541824 AND AD_Tree_ID=10
;

-- Reordering children of `Stücklistenverwaltung`
-- Node name: `Components of the BOM & Formula`
-- 2026-01-30T11:13:13.261Z
UPDATE AD_TreeNodeMM SET Parent_ID=542294, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541376 AND AD_Tree_ID=10
;

-- Node name: `Bill of Material Version`
-- 2026-01-30T11:13:13.320Z
UPDATE AD_TreeNodeMM SET Parent_ID=542294, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000092 AND AD_Tree_ID=10
;

-- Node name: `Bill of Material`
-- 2026-01-30T11:13:13.382Z
UPDATE AD_TreeNodeMM SET Parent_ID=542294, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541824 AND AD_Tree_ID=10
;

-- Reordering children of `webUI`
-- Node name: `CRM`
-- 2026-01-30T11:14:16.358Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000008 AND AD_Tree_ID=10
;

-- Node name: `Marketing`
-- 2026-01-30T11:14:16.420Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541091 AND AD_Tree_ID=10
;

-- Node name: `Product Management`
-- 2026-01-30T11:14:16.488Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000009 AND AD_Tree_ID=10
;

-- Node name: `Stücklistenverwaltung`
-- 2026-01-30T11:14:16.546Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542294 AND AD_Tree_ID=10
;

-- Node name: `Bill of Material Translation`
-- 2026-01-30T11:14:16.610Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541096 AND AD_Tree_ID=10
;

-- Node name: `Purchase`
-- 2026-01-30T11:14:16.670Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000011 AND AD_Tree_ID=10
;

-- Node name: `Pricing`
-- 2026-01-30T11:14:16.730Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540752 AND AD_Tree_ID=10
;

-- Node name: `Warehouse Management`
-- 2026-01-30T11:14:16.793Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000012 AND AD_Tree_ID=10
;

-- Node name: `Contract Management`
-- 2026-01-30T11:14:16.853Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000013 AND AD_Tree_ID=10
;

-- Node name: `Manufacturing`
-- 2026-01-30T11:14:16.938Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000014 AND AD_Tree_ID=10
;

-- Node name: `Material Receipt`
-- 2026-01-30T11:14:16.997Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000017 AND AD_Tree_ID=10
;

-- Node name: `Billing`
-- 2026-01-30T11:14:17.055Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000018 AND AD_Tree_ID=10
;

-- Node name: `Finance`
-- 2026-01-30T11:14:17.148Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000015 AND AD_Tree_ID=10
;

-- Node name: `Logistics`
-- 2026-01-30T11:14:17.206Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000016 AND AD_Tree_ID=10
;

-- Node name: `Shipment`
-- 2026-01-30T11:14:17.263Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000019 AND AD_Tree_ID=10
;

-- Node name: `Pharma`
-- 2026-01-30T11:14:17.326Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541012 AND AD_Tree_ID=10
;

-- Node name: `Project Management`
-- 2026-01-30T11:14:17.387Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541329 AND AD_Tree_ID=10
;

-- Node name: `Forum Datenaustausch`
-- 2026-01-30T11:14:17.451Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541229 AND AD_Tree_ID=10
;

-- Node name: `Seminar-Verwaltung`
-- 2026-01-30T11:14:17.509Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541340 AND AD_Tree_ID=10
;

-- Node name: `Client/ Organisation`
-- 2026-01-30T11:14:17.568Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540833 AND AD_Tree_ID=10
;

-- Node name: `Service delivery`
-- 2026-01-30T11:14:17.632Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541428 AND AD_Tree_ID=10
;

-- Node name: `System`
-- 2026-01-30T11:14:17.690Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000098 AND AD_Tree_ID=10
;

-- Reordering children of `Stücklistenverwaltung`
-- Node name: `Components of the BOM & Formula`
-- 2026-01-30T11:14:20.786Z
UPDATE AD_TreeNodeMM SET Parent_ID=542294, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541376 AND AD_Tree_ID=10
;

-- Node name: `Bill of Material Version`
-- 2026-01-30T11:14:20.844Z
UPDATE AD_TreeNodeMM SET Parent_ID=542294, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000092 AND AD_Tree_ID=10
;

-- Node name: `Bill of Material Translation`
-- 2026-01-30T11:14:20.905Z
UPDATE AD_TreeNodeMM SET Parent_ID=542294, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541096 AND AD_Tree_ID=10
;

-- Node name: `Bill of Material`
-- 2026-01-30T11:14:20.965Z
UPDATE AD_TreeNodeMM SET Parent_ID=542294, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541824 AND AD_Tree_ID=10
;

-- Reordering children of `Stücklistenverwaltung`
-- Node name: `Components of the BOM & Formula`
-- 2026-01-30T11:14:23.805Z
UPDATE AD_TreeNodeMM SET Parent_ID=542294, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541376 AND AD_Tree_ID=10
;

-- Node name: `Bill of Material Version`
-- 2026-01-30T11:14:23.870Z
UPDATE AD_TreeNodeMM SET Parent_ID=542294, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000092 AND AD_Tree_ID=10
;

-- Node name: `Bill of Material`
-- 2026-01-30T11:14:23.927Z
UPDATE AD_TreeNodeMM SET Parent_ID=542294, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541824 AND AD_Tree_ID=10
;

-- Node name: `Bill of Material Translation`
-- 2026-01-30T11:14:23.985Z
UPDATE AD_TreeNodeMM SET Parent_ID=542294, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541096 AND AD_Tree_ID=10
;

-- Run mode: SWING_CLIENT

-- Reordering children of `Menu`
-- Node name: `CRM`
-- 2026-01-30T12:33:05.957Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000008 AND AD_Tree_ID=10
;

-- Node name: `Marketing`
-- 2026-01-30T12:33:06.020Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541091 AND AD_Tree_ID=10
;

-- Node name: `Produktverwaltung`
-- 2026-01-30T12:33:06.092Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000009 AND AD_Tree_ID=10
;

-- Node name: `Beschaffung`
-- 2026-01-30T12:33:06.152Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000011 AND AD_Tree_ID=10
;

-- Node name: `Preise`
-- 2026-01-30T12:33:06.214Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540752 AND AD_Tree_ID=10
;

-- Node name: `Stücklistenverwaltung`
-- 2026-01-30T12:33:06.276Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542294 AND AD_Tree_ID=10
;

-- Node name: `Lagerverwaltung`
-- 2026-01-30T12:33:06.336Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000012 AND AD_Tree_ID=10
;

-- Node name: `Vertragsverwaltung`
-- 2026-01-30T12:33:06.396Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000013 AND AD_Tree_ID=10
;

-- Node name: `Produktion`
-- 2026-01-30T12:33:06.458Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000014 AND AD_Tree_ID=10
;

-- Node name: `Wareneingang`
-- 2026-01-30T12:33:06.517Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000017 AND AD_Tree_ID=10
;

-- Node name: `Fakturierung`
-- 2026-01-30T12:33:06.580Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000018 AND AD_Tree_ID=10
;

-- Node name: `Finanzen`
-- 2026-01-30T12:33:06.641Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000015 AND AD_Tree_ID=10
;

-- Node name: `Logistik`
-- 2026-01-30T12:33:06.701Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000016 AND AD_Tree_ID=10
;

-- Node name: `Lieferung`
-- 2026-01-30T12:33:06.768Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000019 AND AD_Tree_ID=10
;

-- Node name: `Pharma`
-- 2026-01-30T12:33:06.828Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541012 AND AD_Tree_ID=10
;

-- Node name: `Projekt-Verwaltung`
-- 2026-01-30T12:33:06.888Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541329 AND AD_Tree_ID=10
;

-- Node name: `Forum Datenaustausch`
-- 2026-01-30T12:33:06.948Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541229 AND AD_Tree_ID=10
;

-- Node name: `Seminar-Verwaltung`
-- 2026-01-30T12:33:07.011Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541340 AND AD_Tree_ID=10
;

-- Node name: `Mandant/ Organisation`
-- 2026-01-30T12:33:07.073Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540833 AND AD_Tree_ID=10
;

-- Node name: `Dienstleistungserbringung`
-- 2026-01-30T12:33:07.134Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541428 AND AD_Tree_ID=10
;

-- Node name: `System`
-- 2026-01-30T12:33:07.195Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000098 AND AD_Tree_ID=10
;

-- Reordering children of `Menu`
-- Node name: `CRM`
-- 2026-01-30T12:33:12.817Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000008 AND AD_Tree_ID=10
;

-- Node name: `Marketing`
-- 2026-01-30T12:33:12.878Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541091 AND AD_Tree_ID=10
;

-- Node name: `Produktverwaltung`
-- 2026-01-30T12:33:12.942Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000009 AND AD_Tree_ID=10
;

-- Node name: `Beschaffung`
-- 2026-01-30T12:33:13.003Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000011 AND AD_Tree_ID=10
;

-- Node name: `Stücklistenverwaltung`
-- 2026-01-30T12:33:13.063Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542294 AND AD_Tree_ID=10
;

-- Node name: `Preise`
-- 2026-01-30T12:33:13.127Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540752 AND AD_Tree_ID=10
;

-- Node name: `Lagerverwaltung`
-- 2026-01-30T12:33:13.187Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000012 AND AD_Tree_ID=10
;

-- Node name: `Vertragsverwaltung`
-- 2026-01-30T12:33:13.283Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000013 AND AD_Tree_ID=10
;

-- Node name: `Produktion`
-- 2026-01-30T12:33:13.344Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000014 AND AD_Tree_ID=10
;

-- Node name: `Wareneingang`
-- 2026-01-30T12:33:13.411Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000017 AND AD_Tree_ID=10
;

-- Node name: `Fakturierung`
-- 2026-01-30T12:33:13.474Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000018 AND AD_Tree_ID=10
;

-- Node name: `Finanzen`
-- 2026-01-30T12:33:13.539Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000015 AND AD_Tree_ID=10
;

-- Node name: `Logistik`
-- 2026-01-30T12:33:13.601Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000016 AND AD_Tree_ID=10
;

-- Node name: `Lieferung`
-- 2026-01-30T12:33:13.663Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000019 AND AD_Tree_ID=10
;

-- Node name: `Pharma`
-- 2026-01-30T12:33:13.732Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541012 AND AD_Tree_ID=10
;

-- Node name: `Projekt-Verwaltung`
-- 2026-01-30T12:33:13.793Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541329 AND AD_Tree_ID=10
;

-- Node name: `Forum Datenaustausch`
-- 2026-01-30T12:33:13.852Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541229 AND AD_Tree_ID=10
;

-- Node name: `Seminar-Verwaltung`
-- 2026-01-30T12:33:13.914Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541340 AND AD_Tree_ID=10
;

-- Node name: `Mandant/ Organisation`
-- 2026-01-30T12:33:13.977Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540833 AND AD_Tree_ID=10
;

-- Node name: `Dienstleistungserbringung`
-- 2026-01-30T12:33:14.038Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541428 AND AD_Tree_ID=10
;

-- Node name: `System`
-- 2026-01-30T12:33:14.098Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000098 AND AD_Tree_ID=10
;

