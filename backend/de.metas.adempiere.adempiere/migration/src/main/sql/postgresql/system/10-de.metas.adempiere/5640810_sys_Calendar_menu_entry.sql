-- 2022-05-25T11:21:19.221Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580941,0,'WEBUI_Calendar_ID',TO_TIMESTAMP('2022-05-25 14:21:19','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Kalender','Kalender',TO_TIMESTAMP('2022-05-25 14:21:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-25T11:21:19.225Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580941 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-05-25T11:21:23.160Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-05-25 14:21:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580941 AND AD_Language='de_CH'
;

-- 2022-05-25T11:21:23.166Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580941,'de_CH') 
;

-- 2022-05-25T11:21:24.435Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-05-25 14:21:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580941 AND AD_Language='de_DE'
;

-- 2022-05-25T11:21:24.436Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580941,'de_DE') 
;

-- 2022-05-25T11:21:24.447Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(580941,'de_DE') 
;

-- 2022-05-25T11:21:34.231Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Calendar', PrintName='Calendar',Updated=TO_TIMESTAMP('2022-05-25 14:21:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580941 AND AD_Language='en_US'
;

-- 2022-05-25T11:21:34.232Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580941,'en_US') 
;

-- 2022-05-25T11:22:49.913Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('C',0,572879,541954,0,TO_TIMESTAMP('2022-05-25 14:22:49','YYYY-MM-DD HH24:MI:SS'),100,'Kalender definieren','D','calendar','Y','N','N','N','N','Kalender',TO_TIMESTAMP('2022-05-25 14:22:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-25T11:22:49.916Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=541954 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2022-05-25T11:22:49.918Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541954, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541954)
;

-- 2022-05-25T11:22:49.932Z
/* DDL */  select update_menu_translation_from_ad_element(572879) 
;

-- Reordering children of `CRM`
-- Node name: `Settings`
-- 2022-05-25T11:22:50.633Z
UPDATE AD_TreeNodeMM SET Parent_ID=541619, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541686 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2022-05-25T11:22:50.636Z
UPDATE AD_TreeNodeMM SET Parent_ID=541619, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541685 AND AD_Tree_ID=10
;

-- Node name: `Business Partner (C_BPartner)`
-- 2022-05-25T11:22:50.637Z
UPDATE AD_TreeNodeMM SET Parent_ID=541619, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541628 AND AD_Tree_ID=10
;

-- Node name: `User (AD_User)`
-- 2022-05-25T11:22:50.638Z
UPDATE AD_TreeNodeMM SET Parent_ID=541619, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541676 AND AD_Tree_ID=10
;

-- Node name: `Request (all) (R_Request)`
-- 2022-05-25T11:22:50.639Z
UPDATE AD_TreeNodeMM SET Parent_ID=541619, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541675 AND AD_Tree_ID=10
;

-- Node name: `EMails (C_Mail)`
-- 2022-05-25T11:22:50.639Z
UPDATE AD_TreeNodeMM SET Parent_ID=541619, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541674 AND AD_Tree_ID=10
;

-- Node name: `Calendar Year and Period (C_Calendar)`
-- 2022-05-25T11:22:50.640Z
UPDATE AD_TreeNodeMM SET Parent_ID=541619, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541673 AND AD_Tree_ID=10
;

-- Node name: `Kalender`
-- 2022-05-25T11:22:50.640Z
UPDATE AD_TreeNodeMM SET Parent_ID=541619, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541954 AND AD_Tree_ID=10
;



























-- 2022-05-25T11:58:40.569Z
INSERT INTO AD_Menu (AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES (0,580941,541955,0,TO_TIMESTAMP('2022-05-25 14:58:40','YYYY-MM-DD HH24:MI:SS'),100,'D','calendarMenu','Y','N','N','N','Y','Kalender',TO_TIMESTAMP('2022-05-25 14:58:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-25T11:58:40.572Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=541955 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2022-05-25T11:58:40.574Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541955, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541955)
;

-- 2022-05-25T11:58:40.576Z
/* DDL */  select update_menu_translation_from_ad_element(580941) 
;

-- Reordering children of `webUI`
-- Node name: `CRM`
-- 2022-05-25T11:58:41.258Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000008 AND AD_Tree_ID=10
;

-- Node name: `Marketing`
-- 2022-05-25T11:58:41.259Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541091 AND AD_Tree_ID=10
;

-- Node name: `Product Management`
-- 2022-05-25T11:58:41.260Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000009 AND AD_Tree_ID=10
;

-- Node name: `Sales`
-- 2022-05-25T11:58:41.261Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000010 AND AD_Tree_ID=10
;

-- Node name: `Purchase`
-- 2022-05-25T11:58:41.261Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000011 AND AD_Tree_ID=10
;

-- Node name: `Pricing`
-- 2022-05-25T11:58:41.262Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540752 AND AD_Tree_ID=10
;

-- Node name: `Warehouse Management`
-- 2022-05-25T11:58:41.263Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000012 AND AD_Tree_ID=10
;

-- Node name: `Contract Management`
-- 2022-05-25T11:58:41.264Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000013 AND AD_Tree_ID=10
;

-- Node name: `Manufacturing`
-- 2022-05-25T11:58:41.265Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000014 AND AD_Tree_ID=10
;

-- Node name: `Material Receipt`
-- 2022-05-25T11:58:41.265Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000017 AND AD_Tree_ID=10
;

-- Node name: `Billing`
-- 2022-05-25T11:58:41.266Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000018 AND AD_Tree_ID=10
;

-- Node name: `Finance`
-- 2022-05-25T11:58:41.267Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000015 AND AD_Tree_ID=10
;

-- Node name: `Logistics`
-- 2022-05-25T11:58:41.267Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000016 AND AD_Tree_ID=10
;

-- Node name: `Shipment`
-- 2022-05-25T11:58:41.268Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000019 AND AD_Tree_ID=10
;

-- Node name: `Pharma`
-- 2022-05-25T11:58:41.269Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541012 AND AD_Tree_ID=10
;

-- Node name: `Project Management`
-- 2022-05-25T11:58:41.270Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541329 AND AD_Tree_ID=10
;

-- Node name: `Seminar-Verwaltung`
-- 2022-05-25T11:58:41.270Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541340 AND AD_Tree_ID=10
;

-- Node name: `Forum Datenaustausch`
-- 2022-05-25T11:58:41.271Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541229 AND AD_Tree_ID=10
;

-- Node name: `Client/ Organisation`
-- 2022-05-25T11:58:41.271Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540833 AND AD_Tree_ID=10
;

-- Node name: `System`
-- 2022-05-25T11:58:41.272Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000098 AND AD_Tree_ID=10
;

-- Node name: `Service delivery`
-- 2022-05-25T11:58:41.273Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541428 AND AD_Tree_ID=10
;

-- Node name: `Kalender`
-- 2022-05-25T11:58:41.273Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541955 AND AD_Tree_ID=10
;

-- Reordering children of `Kalender`
-- Node name: `Kalender`
-- 2022-05-25T11:59:50.315Z
UPDATE AD_TreeNodeMM SET Parent_ID=541955, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541954 AND AD_Tree_ID=10
;


















