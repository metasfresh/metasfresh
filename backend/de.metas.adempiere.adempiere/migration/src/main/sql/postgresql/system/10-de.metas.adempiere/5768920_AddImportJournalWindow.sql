-- Run mode: SWING_CLIENT

-- Name: Import - Hauptbuchjournal
-- Action Type: W
-- Window: Import - Hauptbuchjournal(278,D)
-- 2025-09-18T09:55:00.233Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,574197,542247,0,278,TO_TIMESTAMP('2025-09-18 09:54:59.925000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Import von Hauptbuch-Journalen','D','Import GL Journal','Y','N','N','N','N','Import - Hauptbuchjournal',TO_TIMESTAMP('2025-09-18 09:54:59.925000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T09:55:00.246Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542247 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2025-09-18T09:55:00.252Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542247, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542247)
;

-- 2025-09-18T09:55:00.256Z
/* DDL */  select update_menu_translation_from_ad_element(574197)
;

-- Reordering children of `System`
-- Node name: `Change Log Config (AD_ChangeLog_Config)`
-- 2025-09-18T09:55:00.863Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542199 AND AD_Tree_ID=10
;

-- Node name: `Warnings (AD_Record_Warning)`
-- 2025-09-18T09:55:00.867Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542223 AND AD_Tree_ID=10
;

-- Node name: `Externe Systeme authentifizieren (de.metas.externalsystem.externalservice.authorization.SendAuthTokenProcess)`
-- 2025-09-18T09:55:00.868Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541993 AND AD_Tree_ID=10
;

-- Node name: `External system config PCM (ExternalSystem_Config_ProCareManagement)`
-- 2025-09-18T09:55:00.871Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542142 AND AD_Tree_ID=10
;

-- Node name: `Costing (Freight etc)`
-- 2025-09-18T09:55:00.872Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542051 AND AD_Tree_ID=10
;

-- Node name: `External system config Leich + Mehl (ExternalSystem_Config_LeichMehl)`
-- 2025-09-18T09:55:00.874Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541966 AND AD_Tree_ID=10
;

-- Node name: `API Audit`
-- 2025-09-18T09:55:00.875Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541725 AND AD_Tree_ID=10
;

-- Node name: `External system config Shopware 6 (ExternalSystem_Config_Shopware6)`
-- 2025-09-18T09:55:00.877Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541702 AND AD_Tree_ID=10
;

-- Node name: `External System (ExternalSystem_Config)`
-- 2025-09-18T09:55:00.878Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541585 AND AD_Tree_ID=10
;

-- Node name: `External system log (ExternalSystem_Config_PInstance_Log_v)`
-- 2025-09-18T09:55:00.879Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541600 AND AD_Tree_ID=10
;

-- Node name: `PostgREST Configs (S_PostgREST_Config)`
-- 2025-09-18T09:55:00.880Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541481 AND AD_Tree_ID=10
;

-- Node name: `External reference (S_ExternalReference)`
-- 2025-09-18T09:55:00.882Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541456 AND AD_Tree_ID=10
;

-- Node name: `EMail`
-- 2025-09-18T09:55:00.883Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541134 AND AD_Tree_ID=10
;

-- Node name: `Test`
-- 2025-09-18T09:55:00.884Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541474 AND AD_Tree_ID=10
;

-- Node name: `Full Text Search`
-- 2025-09-18T09:55:00.886Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541111 AND AD_Tree_ID=10
;

-- Node name: `Asynchronous processing`
-- 2025-09-18T09:55:00.887Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541416 AND AD_Tree_ID=10
;

-- Node name: `Scan Barcode (de.metas.ui.web.globalaction.process.GlobalActionReadProcess)`
-- 2025-09-18T09:55:00.889Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541142 AND AD_Tree_ID=10
;

-- Node name: `Async workpackage queue (C_Queue_WorkPackage)`
-- 2025-09-18T09:55:00.890Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540892 AND AD_Tree_ID=10
;

-- Node name: `Scheduler (AD_Scheduler)`
-- 2025-09-18T09:55:00.891Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540969 AND AD_Tree_ID=10
;

-- Node name: `Batch (C_Async_Batch)`
-- 2025-09-18T09:55:00.893Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540914 AND AD_Tree_ID=10
;

-- Node name: `Role (AD_Role)`
-- 2025-09-18T09:55:00.894Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=150 AND AD_Tree_ID=10
;

-- Node name: `Batch Type (C_Async_Batch_Type)`
-- 2025-09-18T09:55:00.896Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540915 AND AD_Tree_ID=10
;

-- Node name: `User (AD_User)`
-- 2025-09-18T09:55:00.897Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=147 AND AD_Tree_ID=10
;

-- Node name: `Counter Document (C_DocTypeCounter)`
-- 2025-09-18T09:55:00.898Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541539 AND AD_Tree_ID=10
;

-- Node name: `Users Group (AD_UserGroup)`
-- 2025-09-18T09:55:00.900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541216 AND AD_Tree_ID=10
;

-- Node name: `User Record Access (AD_User_Record_Access)`
-- 2025-09-18T09:55:00.901Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541263 AND AD_Tree_ID=10
;

-- Node name: `Language (AD_Language)`
-- 2025-09-18T09:55:00.902Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=145 AND AD_Tree_ID=10
;

-- Node name: `Menu (AD_Menu)`
-- 2025-09-18T09:55:00.904Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=144 AND AD_Tree_ID=10
;

-- Node name: `User Dashboard (WEBUI_Dashboard)`
-- 2025-09-18T09:55:00.905Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540743 AND AD_Tree_ID=10
;

-- Node name: `KPI (WEBUI_KPI)`
-- 2025-09-18T09:55:00.907Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540784 AND AD_Tree_ID=10
;

-- Node name: `Document Type (C_DocType)`
-- 2025-09-18T09:55:00.907Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540826 AND AD_Tree_ID=10
;

-- Node name: `Boiler Plate (AD_BoilerPlate)`
-- 2025-09-18T09:55:00.909Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540898 AND AD_Tree_ID=10
;

-- Node name: `Boilerplate Translation (AD_BoilerPlate_Trl)`
-- 2025-09-18T09:55:00.911Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541476 AND AD_Tree_ID=10
;

-- Node name: `Document Type Printing Options (C_DocType_PrintOptions)`
-- 2025-09-18T09:55:00.911Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541563 AND AD_Tree_ID=10
;

-- Node name: `Text Variable (AD_BoilerPlate_Var)`
-- 2025-09-18T09:55:00.913Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540895 AND AD_Tree_ID=10
;

-- Node name: `Print Format (AD_PrintFormat)`
-- 2025-09-18T09:55:00.914Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540827 AND AD_Tree_ID=10
;

-- Node name: `Zebra Configuration (AD_Zebra_Config)`
-- 2025-09-18T09:55:00.916Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541599 AND AD_Tree_ID=10
;

-- Node name: `Document Sequence (AD_Sequence)`
-- 2025-09-18T09:55:00.917Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540828 AND AD_Tree_ID=10
;

-- Node name: `My Profile (AD_User)`
-- 2025-09-18T09:55:00.919Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540849 AND AD_Tree_ID=10
;

-- Node name: `Printing Queue (C_Printing_Queue)`
-- 2025-09-18T09:55:00.921Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540911 AND AD_Tree_ID=10
;

-- Node name: `Druck-Jobs (C_Print_Job)`
-- 2025-09-18T09:55:00.923Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540427 AND AD_Tree_ID=10
;

-- Node name: `Druckpaket (C_Print_Package)`
-- 2025-09-18T09:55:00.925Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=41, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540438 AND AD_Tree_ID=10
;

-- Node name: `Printer (AD_PrinterHW)`
-- 2025-09-18T09:55:00.927Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=42, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540912 AND AD_Tree_ID=10
;

-- Node name: `Printer Mapping (AD_Printer_Config)`
-- 2025-09-18T09:55:00.928Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=43, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540913 AND AD_Tree_ID=10
;

-- Node name: `Printer-Tray-Mapping (AD_Printer_Matching)`
-- 2025-09-18T09:55:00.929Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=44, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541478 AND AD_Tree_ID=10
;

-- Node name: `RV Missing Counter Documents (RV_Missing_Counter_Documents)`
-- 2025-09-18T09:55:00.931Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=45, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541540 AND AD_Tree_ID=10
;

-- Node name: `System Configuration (AD_SysConfig)`
-- 2025-09-18T09:55:00.932Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=46, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540894 AND AD_Tree_ID=10
;

-- Node name: `Prozess Revision (AD_PInstance)`
-- 2025-09-18T09:55:00.934Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=47, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540917 AND AD_Tree_ID=10
;

-- Node name: `Session Audit (AD_Session)`
-- 2025-09-18T09:55:00.936Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=48, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540982 AND AD_Tree_ID=10
;

-- Node name: `Logischer Drucker (AD_Printer)`
-- 2025-09-18T09:55:00.938Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=49, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541414 AND AD_Tree_ID=10
;

-- Node name: `Change Log (AD_ChangeLog)`
-- 2025-09-18T09:55:00.939Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=50, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540919 AND AD_Tree_ID=10
;

-- Node name: `Import Business Partner (I_BPartner)`
-- 2025-09-18T09:55:00.941Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=51, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540983 AND AD_Tree_ID=10
;

-- Node name: `Export Processor (EXP_Processor)`
-- 2025-09-18T09:55:00.943Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=52, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53101 AND AD_Tree_ID=10
;

-- Node name: `Import Product (I_Product)`
-- 2025-09-18T09:55:00.944Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=53, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541080 AND AD_Tree_ID=10
;

-- Node name: `Import Replenishment (I_Replenish)`
-- 2025-09-18T09:55:00.945Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=54, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541273 AND AD_Tree_ID=10
;

-- Node name: `Import Inventory (I_Inventory)`
-- 2025-09-18T09:55:00.947Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=55, Updated=now(), UpdatedBy=100 WHERE  Node_ID=363 AND AD_Tree_ID=10
;

-- Node name: `Import Discount Schema (I_DiscountSchema)`
-- 2025-09-18T09:55:00.949Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=56, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541079 AND AD_Tree_ID=10
;

-- Node name: `Import Account (I_ElementValue)`
-- 2025-09-18T09:55:00.950Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=57, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541108 AND AD_Tree_ID=10
;

-- Node name: `Import Format (AD_ImpFormat)`
-- 2025-09-18T09:55:00.952Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=58, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541053 AND AD_Tree_ID=10
;

-- Node name: `Data import (C_DataImport)`
-- 2025-09-18T09:55:00.954Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=59, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541052 AND AD_Tree_ID=10
;

-- Node name: `Data Import Run (C_DataImport_Run)`
-- 2025-09-18T09:55:00.955Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=60, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541513 AND AD_Tree_ID=10
;

-- Node name: `Import Postal Data (I_Postal)`
-- 2025-09-18T09:55:00.956Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=61, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541233 AND AD_Tree_ID=10
;

-- Node name: `Import Processor (IMP_Processor)`
-- 2025-09-18T09:55:00.958Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=62, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53103 AND AD_Tree_ID=10
;

-- Node name: `Import Processor Log (IMP_ProcessorLog)`
-- 2025-09-18T09:55:00.960Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=63, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541389 AND AD_Tree_ID=10
;

-- Node name: `Eingabequelle (AD_InputDataSource)`
-- 2025-09-18T09:55:00.961Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=64, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540243 AND AD_Tree_ID=10
;

-- Node name: `Accounting Export Format (DATEV_ExportFormat)`
-- 2025-09-18T09:55:00.963Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=65, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541041 AND AD_Tree_ID=10
;

-- Node name: `Message (AD_Message)`
-- 2025-09-18T09:55:00.965Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=66, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541104 AND AD_Tree_ID=10
;

-- Node name: `Event Log (AD_EventLog)`
-- 2025-09-18T09:55:00.966Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=67, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541109 AND AD_Tree_ID=10
;

-- Node name: `Anhang (AD_AttachmentEntry)`
-- 2025-09-18T09:55:00.968Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=68, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541162 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2025-09-18T09:55:00.969Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=69, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000099 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2025-09-18T09:55:00.971Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=70, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000100 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2025-09-18T09:55:00.972Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=71, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000101 AND AD_Tree_ID=10
;

-- Node name: `Extended Windows`
-- 2025-09-18T09:55:00.973Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=72, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540901 AND AD_Tree_ID=10
;

-- Node name: `Attachment changelog (AD_Attachment_Log)`
-- 2025-09-18T09:55:00.974Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=73, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541282 AND AD_Tree_ID=10
;

-- Node name: `Fix Native Sequences (de.metas.process.ExecuteUpdateSQL)`
-- 2025-09-18T09:55:00.976Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=74, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541339 AND AD_Tree_ID=10
;

-- Node name: `Role Access Update (org.compiere.process.RoleAccessUpdate)`
-- 2025-09-18T09:55:00.977Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=75, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541326 AND AD_Tree_ID=10
;

-- Node name: `User Record Access Update from BPartner Hierachy (de.metas.security.permissions.bpartner_hierarchy.process.AD_User_Record_Access_UpdateFrom_BPartnerHierarchy)`
-- 2025-09-18T09:55:00.979Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=76, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541417 AND AD_Tree_ID=10
;

-- Node name: `Belege verarbeiten (org.adempiere.ad.process.ProcessDocuments)`
-- 2025-09-18T09:55:00.980Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=77, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540631 AND AD_Tree_ID=10
;

-- Node name: `Geocoding Configuration (GeocodingConfig)`
-- 2025-09-18T09:55:00.981Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=78, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541374 AND AD_Tree_ID=10
;

-- Node name: `Exported Data Audit (Data_Export_Audit)`
-- 2025-09-18T09:55:00.983Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=79, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541752 AND AD_Tree_ID=10
;

-- Node name: `Analysis Report (QM_Analysis_Report)`
-- 2025-09-18T09:55:00.985Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=80, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542215 AND AD_Tree_ID=10
;

-- Node name: `Rebuild FactAcct Summary (de.metas.acct.process.Rebuild_FactAcctSummary)`
-- 2025-09-18T09:55:00.986Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=81, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542089 AND AD_Tree_ID=10
;

-- Node name: `External System Service (ExternalSystem_Service)`
-- 2025-09-18T09:55:00.987Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=82, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541861 AND AD_Tree_ID=10
;

-- Node name: `External System Service Instance (ExternalSystem_Service_Instance)`
-- 2025-09-18T09:55:00.988Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=83, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541862 AND AD_Tree_ID=10
;

-- Node name: `Update purchase order highest price cache (de.metas.process.ExecuteUpdateSQL)`
-- 2025-09-18T09:55:00.989Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=84, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542158 AND AD_Tree_ID=10
;

-- Node name: `Print Scale Devices QR Codes (de.metas.devices.webui.process.PrintDeviceQRCodes)`
-- 2025-09-18T09:55:00.990Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=85, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541906 AND AD_Tree_ID=10
;

-- Node name: `RabbitMQ Message Audit (RabbitMQ_Message_Audit)`
-- 2025-09-18T09:55:00.992Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=86, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541910 AND AD_Tree_ID=10
;

-- Node name: `Letters (C_Letter)`
-- 2025-09-18T09:55:00.993Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=87, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540403 AND AD_Tree_ID=10
;

-- Node name: `Issues (AD_Issue)`
-- 2025-09-18T09:55:00.996Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=88, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542151 AND AD_Tree_ID=10
;

-- Node name: `Mobile`
-- 2025-09-18T09:55:00.997Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=89, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542179 AND AD_Tree_ID=10
;

-- Node name: `Business Rules`
-- 2025-09-18T09:55:00.999Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=90, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542187 AND AD_Tree_ID=10
;

-- Node name: `UI Tracing`
-- 2025-09-18T09:55:01Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=91, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542190 AND AD_Tree_ID=10
;

-- Node name: `Notification`
-- 2025-09-18T09:55:01.002Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=92, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542210 AND AD_Tree_ID=10
;

-- Node name: `Import - Hauptbuchjournal`
-- 2025-09-18T09:55:01.003Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=93, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542247 AND AD_Tree_ID=10
;

-- Reordering children of `System`
-- Node name: `Change Log Config (AD_ChangeLog_Config)`
-- 2025-09-18T09:55:16.096Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542199 AND AD_Tree_ID=10
;

-- Node name: `Warnings (AD_Record_Warning)`
-- 2025-09-18T09:55:16.097Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542223 AND AD_Tree_ID=10
;

-- Node name: `Externe Systeme authentifizieren (de.metas.externalsystem.externalservice.authorization.SendAuthTokenProcess)`
-- 2025-09-18T09:55:16.099Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541993 AND AD_Tree_ID=10
;

-- Node name: `External system config PCM (ExternalSystem_Config_ProCareManagement)`
-- 2025-09-18T09:55:16.100Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542142 AND AD_Tree_ID=10
;

-- Node name: `Costing (Freight etc)`
-- 2025-09-18T09:55:16.102Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542051 AND AD_Tree_ID=10
;

-- Node name: `External system config Leich + Mehl (ExternalSystem_Config_LeichMehl)`
-- 2025-09-18T09:55:16.103Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541966 AND AD_Tree_ID=10
;

-- Node name: `API Audit`
-- 2025-09-18T09:55:16.104Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541725 AND AD_Tree_ID=10
;

-- Node name: `External system config Shopware 6 (ExternalSystem_Config_Shopware6)`
-- 2025-09-18T09:55:16.106Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541702 AND AD_Tree_ID=10
;

-- Node name: `External System (ExternalSystem_Config)`
-- 2025-09-18T09:55:16.107Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541585 AND AD_Tree_ID=10
;

-- Node name: `External system log (ExternalSystem_Config_PInstance_Log_v)`
-- 2025-09-18T09:55:16.108Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541600 AND AD_Tree_ID=10
;

-- Node name: `PostgREST Configs (S_PostgREST_Config)`
-- 2025-09-18T09:55:16.109Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541481 AND AD_Tree_ID=10
;

-- Node name: `External reference (S_ExternalReference)`
-- 2025-09-18T09:55:16.111Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541456 AND AD_Tree_ID=10
;

-- Node name: `EMail`
-- 2025-09-18T09:55:16.112Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541134 AND AD_Tree_ID=10
;

-- Node name: `Test`
-- 2025-09-18T09:55:16.113Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541474 AND AD_Tree_ID=10
;

-- Node name: `Full Text Search`
-- 2025-09-18T09:55:16.115Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541111 AND AD_Tree_ID=10
;

-- Node name: `Asynchronous processing`
-- 2025-09-18T09:55:16.116Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541416 AND AD_Tree_ID=10
;

-- Node name: `Scan Barcode (de.metas.ui.web.globalaction.process.GlobalActionReadProcess)`
-- 2025-09-18T09:55:16.117Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541142 AND AD_Tree_ID=10
;

-- Node name: `Async workpackage queue (C_Queue_WorkPackage)`
-- 2025-09-18T09:55:16.118Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540892 AND AD_Tree_ID=10
;

-- Node name: `Scheduler (AD_Scheduler)`
-- 2025-09-18T09:55:16.120Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540969 AND AD_Tree_ID=10
;

-- Node name: `Batch (C_Async_Batch)`
-- 2025-09-18T09:55:16.121Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540914 AND AD_Tree_ID=10
;

-- Node name: `Role (AD_Role)`
-- 2025-09-18T09:55:16.122Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=150 AND AD_Tree_ID=10
;

-- Node name: `Batch Type (C_Async_Batch_Type)`
-- 2025-09-18T09:55:16.123Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540915 AND AD_Tree_ID=10
;

-- Node name: `User (AD_User)`
-- 2025-09-18T09:55:16.123Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=147 AND AD_Tree_ID=10
;

-- Node name: `Counter Document (C_DocTypeCounter)`
-- 2025-09-18T09:55:16.125Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541539 AND AD_Tree_ID=10
;

-- Node name: `Users Group (AD_UserGroup)`
-- 2025-09-18T09:55:16.125Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541216 AND AD_Tree_ID=10
;

-- Node name: `User Record Access (AD_User_Record_Access)`
-- 2025-09-18T09:55:16.128Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541263 AND AD_Tree_ID=10
;

-- Node name: `Language (AD_Language)`
-- 2025-09-18T09:55:16.129Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=145 AND AD_Tree_ID=10
;

-- Node name: `Menu (AD_Menu)`
-- 2025-09-18T09:55:16.130Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=144 AND AD_Tree_ID=10
;

-- Node name: `User Dashboard (WEBUI_Dashboard)`
-- 2025-09-18T09:55:16.132Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540743 AND AD_Tree_ID=10
;

-- Node name: `KPI (WEBUI_KPI)`
-- 2025-09-18T09:55:16.133Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540784 AND AD_Tree_ID=10
;

-- Node name: `Document Type (C_DocType)`
-- 2025-09-18T09:55:16.134Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540826 AND AD_Tree_ID=10
;

-- Node name: `Boiler Plate (AD_BoilerPlate)`
-- 2025-09-18T09:55:16.135Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540898 AND AD_Tree_ID=10
;

-- Node name: `Boilerplate Translation (AD_BoilerPlate_Trl)`
-- 2025-09-18T09:55:16.136Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541476 AND AD_Tree_ID=10
;

-- Node name: `Document Type Printing Options (C_DocType_PrintOptions)`
-- 2025-09-18T09:55:16.137Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541563 AND AD_Tree_ID=10
;

-- Node name: `Text Variable (AD_BoilerPlate_Var)`
-- 2025-09-18T09:55:16.139Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540895 AND AD_Tree_ID=10
;

-- Node name: `Print Format (AD_PrintFormat)`
-- 2025-09-18T09:55:16.140Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540827 AND AD_Tree_ID=10
;

-- Node name: `Zebra Configuration (AD_Zebra_Config)`
-- 2025-09-18T09:55:16.141Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541599 AND AD_Tree_ID=10
;

-- Node name: `Document Sequence (AD_Sequence)`
-- 2025-09-18T09:55:16.142Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540828 AND AD_Tree_ID=10
;

-- Node name: `My Profile (AD_User)`
-- 2025-09-18T09:55:16.143Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540849 AND AD_Tree_ID=10
;

-- Node name: `Printing Queue (C_Printing_Queue)`
-- 2025-09-18T09:55:16.145Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540911 AND AD_Tree_ID=10
;

-- Node name: `Druck-Jobs (C_Print_Job)`
-- 2025-09-18T09:55:16.146Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540427 AND AD_Tree_ID=10
;

-- Node name: `Druckpaket (C_Print_Package)`
-- 2025-09-18T09:55:16.148Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=41, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540438 AND AD_Tree_ID=10
;

-- Node name: `Printer (AD_PrinterHW)`
-- 2025-09-18T09:55:16.149Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=42, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540912 AND AD_Tree_ID=10
;

-- Node name: `Printer Mapping (AD_Printer_Config)`
-- 2025-09-18T09:55:16.151Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=43, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540913 AND AD_Tree_ID=10
;

-- Node name: `Printer-Tray-Mapping (AD_Printer_Matching)`
-- 2025-09-18T09:55:16.152Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=44, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541478 AND AD_Tree_ID=10
;

-- Node name: `RV Missing Counter Documents (RV_Missing_Counter_Documents)`
-- 2025-09-18T09:55:16.153Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=45, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541540 AND AD_Tree_ID=10
;

-- Node name: `System Configuration (AD_SysConfig)`
-- 2025-09-18T09:55:16.155Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=46, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540894 AND AD_Tree_ID=10
;

-- Node name: `Prozess Revision (AD_PInstance)`
-- 2025-09-18T09:55:16.156Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=47, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540917 AND AD_Tree_ID=10
;

-- Node name: `Session Audit (AD_Session)`
-- 2025-09-18T09:55:16.157Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=48, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540982 AND AD_Tree_ID=10
;

-- Node name: `Logischer Drucker (AD_Printer)`
-- 2025-09-18T09:55:16.158Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=49, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541414 AND AD_Tree_ID=10
;

-- Node name: `Change Log (AD_ChangeLog)`
-- 2025-09-18T09:55:16.160Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=50, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540919 AND AD_Tree_ID=10
;

-- Node name: `Import Business Partner (I_BPartner)`
-- 2025-09-18T09:55:16.161Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=51, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540983 AND AD_Tree_ID=10
;

-- Node name: `Export Processor (EXP_Processor)`
-- 2025-09-18T09:55:16.162Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=52, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53101 AND AD_Tree_ID=10
;

-- Node name: `Import Product (I_Product)`
-- 2025-09-18T09:55:16.163Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=53, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541080 AND AD_Tree_ID=10
;

-- Node name: `Import Replenishment (I_Replenish)`
-- 2025-09-18T09:55:16.164Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=54, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541273 AND AD_Tree_ID=10
;

-- Node name: `Import Inventory (I_Inventory)`
-- 2025-09-18T09:55:16.165Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=55, Updated=now(), UpdatedBy=100 WHERE  Node_ID=363 AND AD_Tree_ID=10
;

-- Node name: `Import Discount Schema (I_DiscountSchema)`
-- 2025-09-18T09:55:16.166Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=56, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541079 AND AD_Tree_ID=10
;

-- Node name: `Import - Hauptbuchjournal`
-- 2025-09-18T09:55:16.167Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=57, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542247 AND AD_Tree_ID=10
;

-- Node name: `Import Account (I_ElementValue)`
-- 2025-09-18T09:55:16.169Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=58, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541108 AND AD_Tree_ID=10
;

-- Node name: `Import Format (AD_ImpFormat)`
-- 2025-09-18T09:55:16.170Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=59, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541053 AND AD_Tree_ID=10
;

-- Node name: `Data import (C_DataImport)`
-- 2025-09-18T09:55:16.171Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=60, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541052 AND AD_Tree_ID=10
;

-- Node name: `Data Import Run (C_DataImport_Run)`
-- 2025-09-18T09:55:16.172Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=61, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541513 AND AD_Tree_ID=10
;

-- Node name: `Import Postal Data (I_Postal)`
-- 2025-09-18T09:55:16.173Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=62, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541233 AND AD_Tree_ID=10
;

-- Node name: `Import Processor (IMP_Processor)`
-- 2025-09-18T09:55:16.174Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=63, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53103 AND AD_Tree_ID=10
;

-- Node name: `Import Processor Log (IMP_ProcessorLog)`
-- 2025-09-18T09:55:16.176Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=64, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541389 AND AD_Tree_ID=10
;

-- Node name: `Eingabequelle (AD_InputDataSource)`
-- 2025-09-18T09:55:16.177Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=65, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540243 AND AD_Tree_ID=10
;

-- Node name: `Accounting Export Format (DATEV_ExportFormat)`
-- 2025-09-18T09:55:16.178Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=66, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541041 AND AD_Tree_ID=10
;

-- Node name: `Message (AD_Message)`
-- 2025-09-18T09:55:16.180Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=67, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541104 AND AD_Tree_ID=10
;

-- Node name: `Event Log (AD_EventLog)`
-- 2025-09-18T09:55:16.181Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=68, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541109 AND AD_Tree_ID=10
;

-- Node name: `Anhang (AD_AttachmentEntry)`
-- 2025-09-18T09:55:16.182Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=69, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541162 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2025-09-18T09:55:16.183Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=70, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000099 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2025-09-18T09:55:16.185Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=71, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000100 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2025-09-18T09:55:16.186Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=72, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000101 AND AD_Tree_ID=10
;

-- Node name: `Extended Windows`
-- 2025-09-18T09:55:16.187Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=73, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540901 AND AD_Tree_ID=10
;

-- Node name: `Attachment changelog (AD_Attachment_Log)`
-- 2025-09-18T09:55:16.188Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=74, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541282 AND AD_Tree_ID=10
;

-- Node name: `Fix Native Sequences (de.metas.process.ExecuteUpdateSQL)`
-- 2025-09-18T09:55:16.190Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=75, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541339 AND AD_Tree_ID=10
;

-- Node name: `Role Access Update (org.compiere.process.RoleAccessUpdate)`
-- 2025-09-18T09:55:16.191Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=76, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541326 AND AD_Tree_ID=10
;

-- Node name: `User Record Access Update from BPartner Hierachy (de.metas.security.permissions.bpartner_hierarchy.process.AD_User_Record_Access_UpdateFrom_BPartnerHierarchy)`
-- 2025-09-18T09:55:16.192Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=77, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541417 AND AD_Tree_ID=10
;

-- Node name: `Belege verarbeiten (org.adempiere.ad.process.ProcessDocuments)`
-- 2025-09-18T09:55:16.193Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=78, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540631 AND AD_Tree_ID=10
;

-- Node name: `Geocoding Configuration (GeocodingConfig)`
-- 2025-09-18T09:55:16.194Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=79, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541374 AND AD_Tree_ID=10
;

-- Node name: `Exported Data Audit (Data_Export_Audit)`
-- 2025-09-18T09:55:16.196Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=80, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541752 AND AD_Tree_ID=10
;

-- Node name: `Analysis Report (QM_Analysis_Report)`
-- 2025-09-18T09:55:16.197Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=81, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542215 AND AD_Tree_ID=10
;

-- Node name: `Rebuild FactAcct Summary (de.metas.acct.process.Rebuild_FactAcctSummary)`
-- 2025-09-18T09:55:16.198Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=82, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542089 AND AD_Tree_ID=10
;

-- Node name: `External System Service (ExternalSystem_Service)`
-- 2025-09-18T09:55:16.200Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=83, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541861 AND AD_Tree_ID=10
;

-- Node name: `External System Service Instance (ExternalSystem_Service_Instance)`
-- 2025-09-18T09:55:16.201Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=84, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541862 AND AD_Tree_ID=10
;

-- Node name: `Update purchase order highest price cache (de.metas.process.ExecuteUpdateSQL)`
-- 2025-09-18T09:55:16.202Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=85, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542158 AND AD_Tree_ID=10
;

-- Node name: `Print Scale Devices QR Codes (de.metas.devices.webui.process.PrintDeviceQRCodes)`
-- 2025-09-18T09:55:16.203Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=86, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541906 AND AD_Tree_ID=10
;

-- Node name: `RabbitMQ Message Audit (RabbitMQ_Message_Audit)`
-- 2025-09-18T09:55:16.204Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=87, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541910 AND AD_Tree_ID=10
;

-- Node name: `Letters (C_Letter)`
-- 2025-09-18T09:55:16.206Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=88, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540403 AND AD_Tree_ID=10
;

-- Node name: `Issues (AD_Issue)`
-- 2025-09-18T09:55:16.207Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=89, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542151 AND AD_Tree_ID=10
;

-- Node name: `Mobile`
-- 2025-09-18T09:55:16.208Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=90, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542179 AND AD_Tree_ID=10
;

-- Node name: `Business Rules`
-- 2025-09-18T09:55:16.209Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=91, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542187 AND AD_Tree_ID=10
;

-- Node name: `UI Tracing`
-- 2025-09-18T09:55:16.210Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=92, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542190 AND AD_Tree_ID=10
;

-- Node name: `Notification`
-- 2025-09-18T09:55:16.212Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=93, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542210 AND AD_Tree_ID=10
;

