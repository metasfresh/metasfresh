-- Name: Position
-- Action Type: W
-- Window: Position(351,D)
-- 2025-03-04T09:52:19.084Z

DELETE FROM AD_Menu WHERE AD_Window_ID IN (351, 352)
;

INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,574214,542208,0,351,TO_TIMESTAMP('2025-03-04 09:52:18.157000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Verwalten von Job-Positionen','D','Position','Y','N','N','N','N','Position',TO_TIMESTAMP('2025-03-04 09:52:18.157000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-03-04T09:52:19.137Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542208 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2025-03-04T09:52:19.190Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542208, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542208)
;

-- 2025-03-04T09:52:19.269Z
/* DDL */  select update_menu_translation_from_ad_element(574214) 
;

-- Reordering children of `Requisition-to-Invoice`
-- Node name: `RfQ Topic`
-- 2025-03-04T09:52:27.926Z
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=452 AND AD_Tree_ID=10
;

-- Node name: `RfQ`
-- 2025-03-04T09:52:27.986Z
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=454 AND AD_Tree_ID=10
;

-- Node name: `RfQ Response`
-- 2025-03-04T09:52:28.037Z
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=466 AND AD_Tree_ID=10
;

-- Node name: `Ausschreibung-Antwort Position`
-- 2025-03-04T09:52:28.087Z
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540716 AND AD_Tree_ID=10
;

-- Node name: `RfQ Unanswered`
-- 2025-03-04T09:52:28.145Z
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=468 AND AD_Tree_ID=10
;

-- Node name: `RfQ Response`
-- 2025-03-04T09:52:28.224Z
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=467 AND AD_Tree_ID=10
;

-- Node name: `Requisition`
-- 2025-03-04T09:52:28.343Z
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=463 AND AD_Tree_ID=10
;

-- Node name: `Create PO from Requisition`
-- 2025-03-04T09:52:28.697Z
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=549 AND AD_Tree_ID=10
;

-- Node name: `Open Requisitions`
-- 2025-03-04T09:52:28.747Z
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=471 AND AD_Tree_ID=10
;

-- Node name: `Purchase Order`
-- 2025-03-04T09:52:28.797Z
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=205 AND AD_Tree_ID=10
;

-- Node name: `Material Receipt`
-- 2025-03-04T09:52:28.847Z
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=204 AND AD_Tree_ID=10
;

-- Node name: `Expense Invoice (Alpha)`
-- 2025-03-04T09:52:28.897Z
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=360 AND AD_Tree_ID=10
;

-- Node name: `Material Receipt Details`
-- 2025-03-04T09:52:28.947Z
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=493 AND AD_Tree_ID=10
;

-- Node name: `Invoice (Vendor)`
-- 2025-03-04T09:52:28.998Z
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=206 AND AD_Tree_ID=10
;

-- Node name: `Invoice Batch`
-- 2025-03-04T09:52:29.049Z
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=516 AND AD_Tree_ID=10
;

-- Node name: `Wareneingangsdisposition`
-- 2025-03-04T09:52:29.101Z
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540495 AND AD_Tree_ID=10
;

-- Node name: `Procurement`
-- 2025-03-04T09:52:29.153Z
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540688 AND AD_Tree_ID=10
;

-- Node name: `DATEV Export Format`
-- 2025-03-04T09:52:29.202Z
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541039 AND AD_Tree_ID=10
;

-- Node name: `Position`
-- 2025-03-04T09:52:29.251Z
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542208 AND AD_Tree_ID=10
;

-- Reordering children of `Settings`
-- Node name: `Position`
-- 2025-03-04T09:52:37.220Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542208 AND AD_Tree_ID=10
;

-- Node name: `Nutzer Rolle`
-- 2025-03-04T09:52:37.272Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541743 AND AD_Tree_ID=10
;

-- Node name: `Greeting`
-- 2025-03-04T09:52:37.323Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000028 AND AD_Tree_ID=10
;

-- Node name: `Greetings Translation`
-- 2025-03-04T09:52:37.375Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541168 AND AD_Tree_ID=10
;

-- Node name: `Outbound Documents Config`
-- 2025-03-04T09:52:37.426Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540816 AND AD_Tree_ID=10
;

-- Node name: `Payment Term`
-- 2025-03-04T09:52:37.478Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000032 AND AD_Tree_ID=10
;

-- Node name: `Paymentterm Translation`
-- 2025-03-04T09:52:37.530Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541102 AND AD_Tree_ID=10
;

-- Node name: `Partner Relation`
-- 2025-03-04T09:52:37.583Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000090 AND AD_Tree_ID=10
;

-- Node name: `Business Partner Group`
-- 2025-03-04T09:52:37.636Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000029 AND AD_Tree_ID=10
;

-- Node name: `Request Type`
-- 2025-03-04T09:52:37.687Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000091 AND AD_Tree_ID=10
;

-- Node name: `Request Type Translation`
-- 2025-03-04T09:52:37.739Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541135 AND AD_Tree_ID=10
;

-- Node name: `Request Status`
-- 2025-03-04T09:52:37.790Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540850 AND AD_Tree_ID=10
;

-- Node name: `Default Response`
-- 2025-03-04T09:52:37.839Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540848 AND AD_Tree_ID=10
;

-- Name: Positions- Kategorie
-- Action Type: W
-- Window: Positions- Kategorie(352,D)
-- 2025-03-04T09:55:04.409Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,574215,542209,0,352,TO_TIMESTAMP('2025-03-04 09:55:03.624000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Verwalten von Job-Positions-Kategorien','D','Positions- Kategorie','Y','N','N','N','N','Positions- Kategorie',TO_TIMESTAMP('2025-03-04 09:55:03.624000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-03-04T09:55:04.461Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542209 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2025-03-04T09:55:04.512Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542209, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542209)
;

-- 2025-03-04T09:55:04.561Z
/* DDL */  select update_menu_translation_from_ad_element(574215) 
;

-- Reordering children of `Settings`
-- Node name: `Position`
-- 2025-03-04T09:55:12.489Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542208 AND AD_Tree_ID=10
;

-- Node name: `Nutzer Rolle`
-- 2025-03-04T09:55:12.539Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541743 AND AD_Tree_ID=10
;

-- Node name: `Greeting`
-- 2025-03-04T09:55:12.589Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000028 AND AD_Tree_ID=10
;

-- Node name: `Greetings Translation`
-- 2025-03-04T09:55:12.645Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541168 AND AD_Tree_ID=10
;

-- Node name: `Outbound Documents Config`
-- 2025-03-04T09:55:12.696Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540816 AND AD_Tree_ID=10
;

-- Node name: `Payment Term`
-- 2025-03-04T09:55:12.747Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000032 AND AD_Tree_ID=10
;

-- Node name: `Paymentterm Translation`
-- 2025-03-04T09:55:12.798Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541102 AND AD_Tree_ID=10
;

-- Node name: `Partner Relation`
-- 2025-03-04T09:55:12.850Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000090 AND AD_Tree_ID=10
;

-- Node name: `Business Partner Group`
-- 2025-03-04T09:55:12.901Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000029 AND AD_Tree_ID=10
;

-- Node name: `Request Type`
-- 2025-03-04T09:55:12.952Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000091 AND AD_Tree_ID=10
;

-- Node name: `Request Type Translation`
-- 2025-03-04T09:55:13.002Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541135 AND AD_Tree_ID=10
;

-- Node name: `Request Status`
-- 2025-03-04T09:55:13.053Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540850 AND AD_Tree_ID=10
;

-- Node name: `Default Response`
-- 2025-03-04T09:55:13.103Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540848 AND AD_Tree_ID=10
;

-- Node name: `Positions- Kategorie`
-- 2025-03-04T09:55:13.153Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542209 AND AD_Tree_ID=10
;

-- Column: C_Job.IsEmployee
-- Column: C_Job.IsEmployee
-- 2025-03-04T10:02:13.175Z
UPDATE AD_Column SET DefaultValue='N',Updated=TO_TIMESTAMP('2025-03-04 10:02:13.175000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=14397
;

-- 2025-03-04T10:02:50.497Z
INSERT INTO t_alter_column values('c_job','IsEmployee','CHAR(1)',null,'N')
;

-- 2025-03-04T10:02:50.556Z
UPDATE C_Job SET IsEmployee='N' WHERE IsEmployee IS NULL
;

-- Reordering children of `Settings`
-- Node name: `Nutzer Rolle`
-- 2025-03-04T10:08:22.364Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541743 AND AD_Tree_ID=10
;

-- Node name: `Greeting`
-- 2025-03-04T10:08:22.417Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000028 AND AD_Tree_ID=10
;

-- Node name: `Greetings Translation`
-- 2025-03-04T10:08:22.470Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541168 AND AD_Tree_ID=10
;

-- Node name: `Outbound Documents Config`
-- 2025-03-04T10:08:22.535Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540816 AND AD_Tree_ID=10
;

-- Node name: `Payment Term`
-- 2025-03-04T10:08:22.584Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000032 AND AD_Tree_ID=10
;

-- Node name: `Paymentterm Translation`
-- 2025-03-04T10:08:22.635Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541102 AND AD_Tree_ID=10
;

-- Node name: `Partner Relation`
-- 2025-03-04T10:08:22.685Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000090 AND AD_Tree_ID=10
;

-- Node name: `Business Partner Group`
-- 2025-03-04T10:08:22.737Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000029 AND AD_Tree_ID=10
;

-- Node name: `Request Type`
-- 2025-03-04T10:08:22.788Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000091 AND AD_Tree_ID=10
;

-- Node name: `Request Type Translation`
-- 2025-03-04T10:08:22.838Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541135 AND AD_Tree_ID=10
;

-- Node name: `Request Status`
-- 2025-03-04T10:08:22.889Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540850 AND AD_Tree_ID=10
;

-- Node name: `Default Response`
-- 2025-03-04T10:08:22.939Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540848 AND AD_Tree_ID=10
;

-- Node name: `Positions- Kategorie`
-- 2025-03-04T10:08:22.988Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542209 AND AD_Tree_ID=10
;

-- Node name: `Position`
-- 2025-03-04T10:08:23.043Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542208 AND AD_Tree_ID=10
;

-- Reordering children of `Settings`
-- Node name: `Nutzer Rolle`
-- 2025-03-04T10:08:49.097Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541743 AND AD_Tree_ID=10
;

-- Node name: `Greeting`
-- 2025-03-04T10:08:49.146Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000028 AND AD_Tree_ID=10
;

-- Node name: `Greetings Translation`
-- 2025-03-04T10:08:49.197Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541168 AND AD_Tree_ID=10
;

-- Node name: `Outbound Documents Config`
-- 2025-03-04T10:08:49.249Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540816 AND AD_Tree_ID=10
;

-- Node name: `Payment Term`
-- 2025-03-04T10:08:49.299Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000032 AND AD_Tree_ID=10
;

-- Node name: `Paymentterm Translation`
-- 2025-03-04T10:08:49.350Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541102 AND AD_Tree_ID=10
;

-- Node name: `Partner Relation`
-- 2025-03-04T10:08:49.402Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000090 AND AD_Tree_ID=10
;

-- Node name: `Business Partner Group`
-- 2025-03-04T10:08:49.453Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000029 AND AD_Tree_ID=10
;

-- Node name: `Request Type`
-- 2025-03-04T10:08:49.502Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000091 AND AD_Tree_ID=10
;

-- Node name: `Request Type Translation`
-- 2025-03-04T10:08:49.552Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541135 AND AD_Tree_ID=10
;

-- Node name: `Request Status`
-- 2025-03-04T10:08:49.600Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540850 AND AD_Tree_ID=10
;

-- Node name: `Default Response`
-- 2025-03-04T10:08:49.650Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540848 AND AD_Tree_ID=10
;

-- Node name: `Position`
-- 2025-03-04T10:08:49.700Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542208 AND AD_Tree_ID=10
;

-- Node name: `Positions- Kategorie`
-- 2025-03-04T10:08:49.748Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542209 AND AD_Tree_ID=10
;

