-- Run mode: SWING_CLIENT

-- Window: Verkaufsstatistik, InternalName=null
-- 2026-03-17T16:06:34.567Z
UPDATE AD_Window SET WindowType='T',Updated=TO_TIMESTAMP('2026-03-17 16:06:34.563000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Window_ID=542070
;

-- Column: C_Order_M_InOut_C_Invoice_Overview_V.IsSOTrx
-- 2026-03-17T16:10:52.337Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2026-03-17 16:10:52.336000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591966
;

-- Column: C_Order_M_InOut_C_Invoice_Overview_V.DocBaseType
-- 2026-03-17T16:12:50.383Z
UPDATE AD_Column SET IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2026-03-17 16:12:50.383000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591968
;

-- Name: C_DocType (IsActive=Y)
-- 2026-03-17T16:14:27.988Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,542079,TO_TIMESTAMP('2026-03-17 16:14:27.846000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','C_DocType (IsActive=Y)',TO_TIMESTAMP('2026-03-17 16:14:27.846000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'T')
;

-- 2026-03-17T16:14:27.992Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=542079 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: C_DocType (IsActive=Y)
-- Table: C_DocType
-- Key: C_DocType.C_DocType_ID
-- 2026-03-17T16:15:59.715Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,OrderByClause,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,1509,1501,0,542079,217,TO_TIMESTAMP('2026-03-17 16:15:59.692000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','C_DocType.Name','N',TO_TIMESTAMP('2026-03-17 16:15:59.692000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'C_DocType.IsActive=''Y''')
;

-- Name: C_DocType (IsActive=Y)
-- 2026-03-17T16:21:51.420Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,Description,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540774,'C_DocType.IsActive=''Y'' AND C_DocType.AD_Org_ID IN (@AD_Org_ID/-1@, 0)',TO_TIMESTAMP('2026-03-17 16:21:51.267000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'','D','Y','C_DocType (IsActive=Y)','S',TO_TIMESTAMP('2026-03-17 16:21:51.267000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Column: C_Order_M_InOut_C_Invoice_Overview_V.C_DocType_ID
-- 2026-03-17T16:22:09.565Z
UPDATE AD_Column SET AD_Reference_Value_ID=170, AD_Val_Rule_ID=540774,Updated=TO_TIMESTAMP('2026-03-17 16:22:09.565000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591967
;

-- Column: C_Order_M_InOut_C_Invoice_Overview_V.C_DocType_ID
-- 2026-03-17T16:35:36.494Z
UPDATE AD_Column SET AD_Val_Rule_ID=NULL, IsFacetFilter='Y',Updated=TO_TIMESTAMP('2026-03-17 16:35:36.494000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591967
;

-- Column: C_Order_M_InOut_C_Invoice_Overview_V.DocStatus
-- 2026-03-17T17:53:23.823Z
UPDATE AD_Column SET IsFacetFilter='Y',Updated=TO_TIMESTAMP('2026-03-17 17:53:23.823000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591971
;

-- Table: C_Order_M_InOut_C_Invoice_Overview_V
-- 2026-03-17T18:32:46.701Z
UPDATE AD_Table SET Name='C_Order_M_InOut_C_Invoice_Overview_V',Updated=TO_TIMESTAMP('2026-03-17 18:32:46.698000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Table_ID=542578
;

-- 2026-03-17T18:32:47.362Z
UPDATE AD_Table_Trl trl SET Name='C_Order_M_InOut_C_Invoice_Overview_V' WHERE AD_Table_ID=542578 AND AD_Language='de_DE'
;

-- Element: null
-- 2026-03-17T18:36:47.889Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Ein- und Verkaufsübersicht', PrintName='Ein- und Verkaufsübersicht',Updated=TO_TIMESTAMP('2026-03-17 18:36:47.889000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584505 AND AD_Language='de_CH'
;

-- 2026-03-17T18:36:47.890Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-03-17T18:36:48.317Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584505,'de_CH')
;

-- Element: null
-- 2026-03-17T18:37:01.325Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Ein- und Verkaufsübersicht', PrintName='Ein- und Verkaufsübersicht',Updated=TO_TIMESTAMP('2026-03-17 18:37:01.325000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584505 AND AD_Language='de_DE'
;

-- 2026-03-17T18:37:01.328Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-03-17T18:37:04.198Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584505,'de_DE')
;

-- 2026-03-17T18:37:04.201Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584505,'de_DE')
;

-- Element: null
-- 2026-03-17T18:38:10.854Z
UPDATE AD_Element_Trl SET Name='Purchase & Sales Overview', PrintName='Purchase & Sales Overview',Updated=TO_TIMESTAMP('2026-03-17 18:38:10.854000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584505 AND AD_Language='en_US'
;

-- 2026-03-17T18:38:10.856Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-03-17T18:38:11.308Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584505,'en_US')
;

-- Element: null
-- 2026-03-17T18:39:02.298Z
UPDATE AD_Element_Trl SET Description='Overview of order, shipment, and invoice lines from purchasing and sales, incl. current stock on hand.', Help='',Updated=TO_TIMESTAMP('2026-03-17 18:39:02.298000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584505 AND AD_Language='en_US'
;

-- 2026-03-17T18:39:02.299Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-03-17T18:39:02.629Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584505,'en_US')
;

-- Element: null
-- 2026-03-17T18:39:16.133Z
UPDATE AD_Element_Trl SET Description='Übersicht über Auftrags-, Liefer- und Rechnungspositionen aus Ein- und Verkauf, inkl. aktuellem Lagerbestand.', Help='',Updated=TO_TIMESTAMP('2026-03-17 18:39:16.133000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584505 AND AD_Language='de_DE'
;

-- 2026-03-17T18:39:16.135Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-03-17T18:39:16.729Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584505,'de_DE')
;

-- 2026-03-17T18:39:16.730Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584505,'de_DE')
;

-- Element: null
-- 2026-03-17T18:39:44.627Z
UPDATE AD_Element_Trl SET Description='Übersicht über Auftrags-, Liefer- und Rechnungspositionen aus Ein- und Verkauf, inkl. aktuellem Lagerbestand.', Help='',Updated=TO_TIMESTAMP('2026-03-17 18:39:44.627000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584505 AND AD_Language='de_CH'
;

-- 2026-03-17T18:39:44.628Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-03-17T18:39:44.949Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584505,'de_CH')
;

-- Table: C_Order_M_InOut_C_Invoice_Overview_V
-- 2026-03-17T19:46:41.483Z
UPDATE AD_Table SET AD_Window_ID=542070,Updated=TO_TIMESTAMP('2026-03-17 19:46:41.480000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Table_ID=542578
;

-- Name: Finanzen
-- Action Type: null
-- 2026-03-17T19:50:26.916Z
UPDATE AD_Menu SET EntityType='D',Updated=TO_TIMESTAMP('2026-03-17 19:50:26.913000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Menu_ID=1000015
;

-- 2026-03-17T19:51:16.911Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584683,0,TO_TIMESTAMP('2026-03-17 19:51:16.740000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Zahlungswesen','Zahlungswesen',TO_TIMESTAMP('2026-03-17 19:51:16.740000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-17T19:51:16.914Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584683 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2026-03-17T19:51:20.141Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-03-17 19:51:20.140000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584683 AND AD_Language='de_CH'
;

-- 2026-03-17T19:51:20.144Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584683,'de_CH')
;

-- Element: null
-- 2026-03-17T19:51:22.194Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-03-17 19:51:22.194000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584683 AND AD_Language='de_DE'
;

-- 2026-03-17T19:51:22.197Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584683,'de_DE')
;

-- 2026-03-17T19:51:22.198Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584683,'de_DE')
;

-- Element: null
-- 2026-03-17T19:51:41.033Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Payments', PrintName='Payments',Updated=TO_TIMESTAMP('2026-03-17 19:51:41.033000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584683 AND AD_Language='en_US'
;

-- 2026-03-17T19:51:41.035Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-03-17T19:51:41.356Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584683,'en_US')
;

-- Name: Zahlungswesen
-- Action Type: null
-- 2026-03-17T19:52:21.043Z
INSERT INTO AD_Menu (AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES (0,584683,542310,0,TO_TIMESTAMP('2026-03-17 19:52:20.916000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Payments','Y','N','N','N','Y','Zahlungswesen',TO_TIMESTAMP('2026-03-17 19:52:20.916000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-17T19:52:21.054Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542310 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2026-03-17T19:52:21.056Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542310, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542310)
;

-- 2026-03-17T19:52:21.086Z
/* DDL */  select update_menu_translation_from_ad_element(584683)
;

-- Reordering children of `CRM`
-- Node name: `Report Texts (C_BPartner_Report_Text)`
-- 2026-03-17T19:52:21.701Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542217 AND AD_Tree_ID=10
;

-- Node name: `Request (R_Request)`
-- 2026-03-17T19:52:21.703Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=237 AND AD_Tree_ID=10
;

-- Node name: `Request (all) (R_Request)`
-- 2026-03-17T19:52:21.704Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=308 AND AD_Tree_ID=10
;

-- Node name: `Company Phone Book (AD_User)`
-- 2026-03-17T19:52:21.705Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541234 AND AD_Tree_ID=10
;

-- Node name: `Business Partner (C_BPartner)`
-- 2026-03-17T19:52:21.707Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000021 AND AD_Tree_ID=10
;

-- Node name: `Partner Export (C_BPartner_Export)`
-- 2026-03-17T19:52:21.708Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541728 AND AD_Tree_ID=10
;

-- Node name: `Outbound Documents (C_Doc_Outbound_Log)`
-- 2026-03-17T19:52:21.709Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540815 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2026-03-17T19:52:21.710Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000042 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2026-03-17T19:52:21.711Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000024 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2026-03-17T19:52:21.712Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000025 AND AD_Tree_ID=10
;

-- Node name: `Businesspartner Global ID (I_BPartner_GlobalID)`
-- 2026-03-17T19:52:21.713Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541200 AND AD_Tree_ID=10
;

-- Node name: `Import User (I_User)`
-- 2026-03-17T19:52:21.714Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541210 AND AD_Tree_ID=10
;

-- Node name: `Phone call (R_Request)`
-- 2026-03-17T19:52:21.715Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541896 AND AD_Tree_ID=10
;

-- Node name: `Phonecall Schema (C_Phonecall_Schema)`
-- 2026-03-17T19:52:21.716Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541217 AND AD_Tree_ID=10
;

-- Node name: `Phonecall Schema Version (C_Phonecall_Schema_Version)`
-- 2026-03-17T19:52:21.717Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541218 AND AD_Tree_ID=10
;

-- Node name: `Phonecall Schedule (C_Phonecall_Schedule)`
-- 2026-03-17T19:52:21.718Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541219 AND AD_Tree_ID=10
;

-- Node name: `Zahlungswesen`
-- 2026-03-17T19:52:21.719Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542310 AND AD_Tree_ID=10
;

-- Reordering children of `Finanzen`
-- Node name: `Zahlungswesen`
-- 2026-03-17T19:52:32.870Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542310 AND AD_Tree_ID=10
;

-- Node name: `Lexware`
-- 2026-03-17T19:52:32.873Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542265 AND AD_Tree_ID=10
;

-- Node name: `Remittance Advice (REMADV) (C_RemittanceAdvice)`
-- 2026-03-17T19:52:32.873Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541584 AND AD_Tree_ID=10
;

-- Node name: `Kontenauszug (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2026-03-17T19:52:32.874Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542131 AND AD_Tree_ID=10
;

-- Node name: `GL Journal (GL_Journal)`
-- 2026-03-17T19:52:32.875Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540905 AND AD_Tree_ID=10
;

-- Node name: `Bank Account (C_BP_BankAccount)`
-- 2026-03-17T19:52:32.877Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540814 AND AD_Tree_ID=10
;

-- Node name: `GL Journal (SAP) (SAP_GLJournal)`
-- 2026-03-17T19:52:32.877Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542031 AND AD_Tree_ID=10
;

-- Node name: `Import Bank Statement (I_BankStatement)`
-- 2026-03-17T19:52:32.878Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541297 AND AD_Tree_ID=10
;

-- Node name: `Bank Statement (C_BankStatement)`
-- 2026-03-17T19:52:32.879Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540803 AND AD_Tree_ID=10
;

-- Node name: `GL Distribution (GL_Distribution)`
-- 2026-03-17T19:52:32.880Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=464 AND AD_Tree_ID=10
;

-- Node name: `Cash Journal (C_BankStatement)`
-- 2026-03-17T19:52:32.882Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541377 AND AD_Tree_ID=10
;

-- Node name: `Bankstatement Reference`
-- 2026-03-17T19:52:32.882Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540904 AND AD_Tree_ID=10
;

-- Node name: `Payment (C_Payment)`
-- 2026-03-17T19:52:32.883Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540749 AND AD_Tree_ID=10
;

-- Node name: `Manual Payment Allocations (C_AllocationHdr)`
-- 2026-03-17T19:52:32.884Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540779 AND AD_Tree_ID=10
;

-- Node name: `Payment Selection (C_PaySelection)`
-- 2026-03-17T19:52:32.885Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540910 AND AD_Tree_ID=10
;

-- Node name: `Payment Reservation (C_Payment_Reservation)`
-- 2026-03-17T19:52:32.886Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541308 AND AD_Tree_ID=10
;

-- Node name: `Payment Reservation Capture (C_Payment_Reservation_Capture)`
-- 2026-03-17T19:52:32.887Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541313 AND AD_Tree_ID=10
;

-- Node name: `Dunning (C_DunningDoc)`
-- 2026-03-17T19:52:32.887Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540758 AND AD_Tree_ID=10
;

-- Node name: `Dunning Candidates (C_Dunning_Candidate)`
-- 2026-03-17T19:52:32.888Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540759 AND AD_Tree_ID=10
;

-- Node name: `Accounting Transactions (Fact_Acct_Transactions_View)`
-- 2026-03-17T19:52:32.889Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540806 AND AD_Tree_ID=10
;

-- Node name: `ESR Payment Import (ESR_Import)`
-- 2026-03-17T19:52:32.890Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540891 AND AD_Tree_ID=10
;

-- Node name: `Account Combination (C_ValidCombination)`
-- 2026-03-17T19:52:32.891Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540896 AND AD_Tree_ID=10
;

-- Node name: `Chart of Accounts (C_Element)`
-- 2026-03-17T19:52:32.892Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540903 AND AD_Tree_ID=10
;

-- Node name: `Element values (C_ElementValue)`
-- 2026-03-17T19:52:32.893Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541405 AND AD_Tree_ID=10
;

-- Node name: `Productcosts (M_Product)`
-- 2026-03-17T19:52:32.894Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540906 AND AD_Tree_ID=10
;

-- Node name: `Cost Element (M_CostElement)`
-- 2026-03-17T19:52:32.895Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542067 AND AD_Tree_ID=10
;

-- Node name: `Current Product Costs (M_Cost)`
-- 2026-03-17T19:52:32.896Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541455 AND AD_Tree_ID=10
;

-- Node name: `Products Without Initial Cost (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2026-03-17T19:52:32.897Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541710 AND AD_Tree_ID=10
;

-- Node name: `Products With Booked Quantity (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2026-03-17T19:52:32.897Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541717 AND AD_Tree_ID=10
;

-- Node name: `Cost Detail (M_CostDetail)`
-- 2026-03-17T19:52:32.898Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541454 AND AD_Tree_ID=10
;

-- Node name: `Costcenter Documents (Fact_Acct_ActivityChangeRequest)`
-- 2026-03-17T19:52:32.899Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540907 AND AD_Tree_ID=10
;

-- Node name: `Cost Type (C_Cost_Type)`
-- 2026-03-17T19:52:32.900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542052 AND AD_Tree_ID=10
;

-- Node name: `Cost Revaluation (M_CostRevaluation)`
-- 2026-03-17T19:52:32.901Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541977 AND AD_Tree_ID=10
;

-- Node name: `Cost Center (C_Activity)`
-- 2026-03-17T19:52:32.902Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540908 AND AD_Tree_ID=10
;

-- Node name: `Referenz No (C_ReferenceNo)`
-- 2026-03-17T19:52:32.903Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541015 AND AD_Tree_ID=10
;

-- Node name: `Referenz No Type (C_ReferenceNo_Type)`
-- 2026-03-17T19:52:32.904Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541016 AND AD_Tree_ID=10
;

-- Node name: `Accounting Export (DATEV_Export)`
-- 2026-03-17T19:52:32.904Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541042 AND AD_Tree_ID=10
;

-- Node name: `Matched Invoices (M_MatchInv)`
-- 2026-03-17T19:52:32.905Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=315 AND AD_Tree_ID=10
;

-- Node name: `UnPosted Documents (RV_UnPosted)`
-- 2026-03-17T19:52:32.906Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541368 AND AD_Tree_ID=10
;

-- Node name: `Import Datev Payment (I_Datev_Payment)`
-- 2026-03-17T19:52:32.907Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541120 AND AD_Tree_ID=10
;

-- Node name: `Enqueue all not posted documents (de.metas.acct.process.Documents_EnqueueNotPosted)`
-- 2026-03-17T19:52:32.908Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541125 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2026-03-17T19:52:32.908Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=41, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000056 AND AD_Tree_ID=10
;

-- Node name: `PayPal`
-- 2026-03-17T19:52:32.909Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=42, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541304 AND AD_Tree_ID=10
;

-- Node name: `SumUp`
-- 2026-03-17T19:52:32.911Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=43, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542175 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2026-03-17T19:52:32.911Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=44, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000064 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2026-03-17T19:52:32.912Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=45, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000072 AND AD_Tree_ID=10
;

-- Node name: `Bank Statement Import-File (C_BankStatement_Import_File)`
-- 2026-03-17T19:52:32.913Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=46, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542014 AND AD_Tree_ID=10
;

-- Node name: `Invoice Accounting Overrides (C_Invoice_Acct)`
-- 2026-03-17T19:52:32.914Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=47, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542034 AND AD_Tree_ID=10
;

-- Node name: `Document Accounting Log (Document_Acct_Log)`
-- 2026-03-17T19:52:32.915Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=48, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542238 AND AD_Tree_ID=10
;

-- Node name: `INTRASTAT RTIC File (AT) (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2026-03-17T19:52:32.915Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=49, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542261 AND AD_Tree_ID=10
;

-- Reordering children of `Zahlungswesen`
-- Node name: `Manual Payment Allocations (C_AllocationHdr)`
-- 2026-03-17T19:52:37.662Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540779 AND AD_Tree_ID=10
;

-- Reordering children of `Zahlungswesen`
-- Node name: `Payment (C_Payment)`
-- 2026-03-17T19:52:40.779Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540749 AND AD_Tree_ID=10
;

-- Node name: `Manual Payment Allocations (C_AllocationHdr)`
-- 2026-03-17T19:52:40.781Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540779 AND AD_Tree_ID=10
;

-- Reordering children of `Finanzen`
-- Node name: `Zahlungswesen`
-- 2026-03-17T19:52:44.192Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542310 AND AD_Tree_ID=10
;

-- Node name: `Payment Selection (C_PaySelection)`
-- 2026-03-17T19:52:44.193Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540910 AND AD_Tree_ID=10
;

-- Node name: `Lexware`
-- 2026-03-17T19:52:44.194Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542265 AND AD_Tree_ID=10
;

-- Node name: `Remittance Advice (REMADV) (C_RemittanceAdvice)`
-- 2026-03-17T19:52:44.194Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541584 AND AD_Tree_ID=10
;

-- Node name: `Kontenauszug (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2026-03-17T19:52:44.195Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542131 AND AD_Tree_ID=10
;

-- Node name: `GL Journal (GL_Journal)`
-- 2026-03-17T19:52:44.196Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540905 AND AD_Tree_ID=10
;

-- Node name: `Bank Account (C_BP_BankAccount)`
-- 2026-03-17T19:52:44.197Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540814 AND AD_Tree_ID=10
;

-- Node name: `GL Journal (SAP) (SAP_GLJournal)`
-- 2026-03-17T19:52:44.198Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542031 AND AD_Tree_ID=10
;

-- Node name: `Import Bank Statement (I_BankStatement)`
-- 2026-03-17T19:52:44.208Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541297 AND AD_Tree_ID=10
;

-- Node name: `Bank Statement (C_BankStatement)`
-- 2026-03-17T19:52:44.209Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540803 AND AD_Tree_ID=10
;

-- Node name: `GL Distribution (GL_Distribution)`
-- 2026-03-17T19:52:44.209Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=464 AND AD_Tree_ID=10
;

-- Node name: `Cash Journal (C_BankStatement)`
-- 2026-03-17T19:52:44.210Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541377 AND AD_Tree_ID=10
;

-- Node name: `Bankstatement Reference`
-- 2026-03-17T19:52:44.211Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540904 AND AD_Tree_ID=10
;

-- Node name: `Payment Reservation (C_Payment_Reservation)`
-- 2026-03-17T19:52:44.212Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541308 AND AD_Tree_ID=10
;

-- Node name: `Payment Reservation Capture (C_Payment_Reservation_Capture)`
-- 2026-03-17T19:52:44.213Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541313 AND AD_Tree_ID=10
;

-- Node name: `Dunning (C_DunningDoc)`
-- 2026-03-17T19:52:44.213Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540758 AND AD_Tree_ID=10
;

-- Node name: `Dunning Candidates (C_Dunning_Candidate)`
-- 2026-03-17T19:52:44.214Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540759 AND AD_Tree_ID=10
;

-- Node name: `Accounting Transactions (Fact_Acct_Transactions_View)`
-- 2026-03-17T19:52:44.215Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540806 AND AD_Tree_ID=10
;

-- Node name: `ESR Payment Import (ESR_Import)`
-- 2026-03-17T19:52:44.216Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540891 AND AD_Tree_ID=10
;

-- Node name: `Account Combination (C_ValidCombination)`
-- 2026-03-17T19:52:44.217Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540896 AND AD_Tree_ID=10
;

-- Node name: `Chart of Accounts (C_Element)`
-- 2026-03-17T19:52:44.218Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540903 AND AD_Tree_ID=10
;

-- Node name: `Element values (C_ElementValue)`
-- 2026-03-17T19:52:44.218Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541405 AND AD_Tree_ID=10
;

-- Node name: `Productcosts (M_Product)`
-- 2026-03-17T19:52:44.219Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540906 AND AD_Tree_ID=10
;

-- Node name: `Cost Element (M_CostElement)`
-- 2026-03-17T19:52:44.220Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542067 AND AD_Tree_ID=10
;

-- Node name: `Current Product Costs (M_Cost)`
-- 2026-03-17T19:52:44.221Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541455 AND AD_Tree_ID=10
;

-- Node name: `Products Without Initial Cost (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2026-03-17T19:52:44.222Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541710 AND AD_Tree_ID=10
;

-- Node name: `Products With Booked Quantity (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2026-03-17T19:52:44.223Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541717 AND AD_Tree_ID=10
;

-- Node name: `Cost Detail (M_CostDetail)`
-- 2026-03-17T19:52:44.223Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541454 AND AD_Tree_ID=10
;

-- Node name: `Costcenter Documents (Fact_Acct_ActivityChangeRequest)`
-- 2026-03-17T19:52:44.224Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540907 AND AD_Tree_ID=10
;

-- Node name: `Cost Type (C_Cost_Type)`
-- 2026-03-17T19:52:44.225Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542052 AND AD_Tree_ID=10
;

-- Node name: `Cost Revaluation (M_CostRevaluation)`
-- 2026-03-17T19:52:44.226Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541977 AND AD_Tree_ID=10
;

-- Node name: `Cost Center (C_Activity)`
-- 2026-03-17T19:52:44.227Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540908 AND AD_Tree_ID=10
;

-- Node name: `Referenz No (C_ReferenceNo)`
-- 2026-03-17T19:52:44.228Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541015 AND AD_Tree_ID=10
;

-- Node name: `Referenz No Type (C_ReferenceNo_Type)`
-- 2026-03-17T19:52:44.228Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541016 AND AD_Tree_ID=10
;

-- Node name: `Accounting Export (DATEV_Export)`
-- 2026-03-17T19:52:44.229Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541042 AND AD_Tree_ID=10
;

-- Node name: `Matched Invoices (M_MatchInv)`
-- 2026-03-17T19:52:44.230Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=315 AND AD_Tree_ID=10
;

-- Node name: `UnPosted Documents (RV_UnPosted)`
-- 2026-03-17T19:52:44.231Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541368 AND AD_Tree_ID=10
;

-- Node name: `Import Datev Payment (I_Datev_Payment)`
-- 2026-03-17T19:52:44.232Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541120 AND AD_Tree_ID=10
;

-- Node name: `Enqueue all not posted documents (de.metas.acct.process.Documents_EnqueueNotPosted)`
-- 2026-03-17T19:52:44.232Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541125 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2026-03-17T19:52:44.233Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000056 AND AD_Tree_ID=10
;

-- Node name: `PayPal`
-- 2026-03-17T19:52:44.234Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541304 AND AD_Tree_ID=10
;

-- Node name: `SumUp`
-- 2026-03-17T19:52:44.235Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=41, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542175 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2026-03-17T19:52:44.236Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=42, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000064 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2026-03-17T19:52:44.237Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=43, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000072 AND AD_Tree_ID=10
;

-- Node name: `Bank Statement Import-File (C_BankStatement_Import_File)`
-- 2026-03-17T19:52:44.237Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=44, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542014 AND AD_Tree_ID=10
;

-- Node name: `Invoice Accounting Overrides (C_Invoice_Acct)`
-- 2026-03-17T19:52:44.238Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=45, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542034 AND AD_Tree_ID=10
;

-- Node name: `Document Accounting Log (Document_Acct_Log)`
-- 2026-03-17T19:52:44.239Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=46, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542238 AND AD_Tree_ID=10
;

-- Node name: `INTRASTAT RTIC File (AT) (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2026-03-17T19:52:44.240Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=47, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542261 AND AD_Tree_ID=10
;

-- Reordering children of `Zahlungswesen`
-- Node name: `Payment (C_Payment)`
-- 2026-03-17T19:52:45.815Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540749 AND AD_Tree_ID=10
;

-- Node name: `Payment Selection (C_PaySelection)`
-- 2026-03-17T19:52:45.817Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540910 AND AD_Tree_ID=10
;

-- Node name: `Manual Payment Allocations (C_AllocationHdr)`
-- 2026-03-17T19:52:45.818Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540779 AND AD_Tree_ID=10
;

-- Reordering children of `Zahlungswesen`
-- Node name: `Payment (C_Payment)`
-- 2026-03-17T19:52:57.721Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540749 AND AD_Tree_ID=10
;

-- Node name: `Payment Selection (C_PaySelection)`
-- 2026-03-17T19:52:57.722Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540910 AND AD_Tree_ID=10
;

-- Node name: `Bank Statement (C_BankStatement)`
-- 2026-03-17T19:52:57.723Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540803 AND AD_Tree_ID=10
;

-- Node name: `Manual Payment Allocations (C_AllocationHdr)`
-- 2026-03-17T19:52:57.724Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540779 AND AD_Tree_ID=10
;

-- Reordering children of `Finanzen`
-- Node name: `Zahlungswesen`
-- 2026-03-17T19:52:59.482Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542310 AND AD_Tree_ID=10
;

-- Node name: `Bankstatement Reference`
-- 2026-03-17T19:52:59.483Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540904 AND AD_Tree_ID=10
;

-- Node name: `Lexware`
-- 2026-03-17T19:52:59.484Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542265 AND AD_Tree_ID=10
;

-- Node name: `Remittance Advice (REMADV) (C_RemittanceAdvice)`
-- 2026-03-17T19:52:59.485Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541584 AND AD_Tree_ID=10
;

-- Node name: `Kontenauszug (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2026-03-17T19:52:59.486Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542131 AND AD_Tree_ID=10
;

-- Node name: `GL Journal (GL_Journal)`
-- 2026-03-17T19:52:59.487Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540905 AND AD_Tree_ID=10
;

-- Node name: `Bank Account (C_BP_BankAccount)`
-- 2026-03-17T19:52:59.488Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540814 AND AD_Tree_ID=10
;

-- Node name: `GL Journal (SAP) (SAP_GLJournal)`
-- 2026-03-17T19:52:59.489Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542031 AND AD_Tree_ID=10
;

-- Node name: `Import Bank Statement (I_BankStatement)`
-- 2026-03-17T19:52:59.489Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541297 AND AD_Tree_ID=10
;

-- Node name: `GL Distribution (GL_Distribution)`
-- 2026-03-17T19:52:59.490Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=464 AND AD_Tree_ID=10
;

-- Node name: `Cash Journal (C_BankStatement)`
-- 2026-03-17T19:52:59.491Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541377 AND AD_Tree_ID=10
;

-- Node name: `Payment Reservation (C_Payment_Reservation)`
-- 2026-03-17T19:52:59.492Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541308 AND AD_Tree_ID=10
;

-- Node name: `Payment Reservation Capture (C_Payment_Reservation_Capture)`
-- 2026-03-17T19:52:59.493Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541313 AND AD_Tree_ID=10
;

-- Node name: `Dunning (C_DunningDoc)`
-- 2026-03-17T19:52:59.493Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540758 AND AD_Tree_ID=10
;

-- Node name: `Dunning Candidates (C_Dunning_Candidate)`
-- 2026-03-17T19:52:59.495Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540759 AND AD_Tree_ID=10
;

-- Node name: `Accounting Transactions (Fact_Acct_Transactions_View)`
-- 2026-03-17T19:52:59.496Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540806 AND AD_Tree_ID=10
;

-- Node name: `ESR Payment Import (ESR_Import)`
-- 2026-03-17T19:52:59.496Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540891 AND AD_Tree_ID=10
;

-- Node name: `Account Combination (C_ValidCombination)`
-- 2026-03-17T19:52:59.497Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540896 AND AD_Tree_ID=10
;

-- Node name: `Chart of Accounts (C_Element)`
-- 2026-03-17T19:52:59.498Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540903 AND AD_Tree_ID=10
;

-- Node name: `Element values (C_ElementValue)`
-- 2026-03-17T19:52:59.499Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541405 AND AD_Tree_ID=10
;

-- Node name: `Productcosts (M_Product)`
-- 2026-03-17T19:52:59.499Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540906 AND AD_Tree_ID=10
;

-- Node name: `Cost Element (M_CostElement)`
-- 2026-03-17T19:52:59.500Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542067 AND AD_Tree_ID=10
;

-- Node name: `Current Product Costs (M_Cost)`
-- 2026-03-17T19:52:59.501Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541455 AND AD_Tree_ID=10
;

-- Node name: `Products Without Initial Cost (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2026-03-17T19:52:59.502Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541710 AND AD_Tree_ID=10
;

-- Node name: `Products With Booked Quantity (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2026-03-17T19:52:59.503Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541717 AND AD_Tree_ID=10
;

-- Node name: `Cost Detail (M_CostDetail)`
-- 2026-03-17T19:52:59.504Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541454 AND AD_Tree_ID=10
;

-- Node name: `Costcenter Documents (Fact_Acct_ActivityChangeRequest)`
-- 2026-03-17T19:52:59.505Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540907 AND AD_Tree_ID=10
;

-- Node name: `Cost Type (C_Cost_Type)`
-- 2026-03-17T19:52:59.506Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542052 AND AD_Tree_ID=10
;

-- Node name: `Cost Revaluation (M_CostRevaluation)`
-- 2026-03-17T19:52:59.506Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541977 AND AD_Tree_ID=10
;

-- Node name: `Cost Center (C_Activity)`
-- 2026-03-17T19:52:59.507Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540908 AND AD_Tree_ID=10
;

-- Node name: `Referenz No (C_ReferenceNo)`
-- 2026-03-17T19:52:59.508Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541015 AND AD_Tree_ID=10
;

-- Node name: `Referenz No Type (C_ReferenceNo_Type)`
-- 2026-03-17T19:52:59.509Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541016 AND AD_Tree_ID=10
;

-- Node name: `Accounting Export (DATEV_Export)`
-- 2026-03-17T19:52:59.510Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541042 AND AD_Tree_ID=10
;

-- Node name: `Matched Invoices (M_MatchInv)`
-- 2026-03-17T19:52:59.510Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=315 AND AD_Tree_ID=10
;

-- Node name: `UnPosted Documents (RV_UnPosted)`
-- 2026-03-17T19:52:59.512Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541368 AND AD_Tree_ID=10
;

-- Node name: `Import Datev Payment (I_Datev_Payment)`
-- 2026-03-17T19:52:59.513Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541120 AND AD_Tree_ID=10
;

-- Node name: `Enqueue all not posted documents (de.metas.acct.process.Documents_EnqueueNotPosted)`
-- 2026-03-17T19:52:59.514Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541125 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2026-03-17T19:52:59.514Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000056 AND AD_Tree_ID=10
;

-- Node name: `PayPal`
-- 2026-03-17T19:52:59.515Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541304 AND AD_Tree_ID=10
;

-- Node name: `SumUp`
-- 2026-03-17T19:52:59.516Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542175 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2026-03-17T19:52:59.517Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000064 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2026-03-17T19:52:59.518Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=41, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000072 AND AD_Tree_ID=10
;

-- Node name: `Bank Statement Import-File (C_BankStatement_Import_File)`
-- 2026-03-17T19:52:59.519Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=42, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542014 AND AD_Tree_ID=10
;

-- Node name: `Invoice Accounting Overrides (C_Invoice_Acct)`
-- 2026-03-17T19:52:59.520Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=43, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542034 AND AD_Tree_ID=10
;

-- Node name: `Document Accounting Log (Document_Acct_Log)`
-- 2026-03-17T19:52:59.521Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=44, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542238 AND AD_Tree_ID=10
;

-- Node name: `INTRASTAT RTIC File (AT) (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2026-03-17T19:52:59.522Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=45, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542261 AND AD_Tree_ID=10
;

-- Reordering children of `Zahlungswesen`
-- Node name: `Payment (C_Payment)`
-- 2026-03-17T19:53:00.759Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540749 AND AD_Tree_ID=10
;

-- Node name: `Payment Selection (C_PaySelection)`
-- 2026-03-17T19:53:00.760Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540910 AND AD_Tree_ID=10
;

-- Node name: `Bank Statement (C_BankStatement)`
-- 2026-03-17T19:53:00.761Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540803 AND AD_Tree_ID=10
;

-- Node name: `Bankstatement Reference`
-- 2026-03-17T19:53:00.762Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540904 AND AD_Tree_ID=10
;

-- Node name: `Manual Payment Allocations (C_AllocationHdr)`
-- 2026-03-17T19:53:00.763Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540779 AND AD_Tree_ID=10
;

-- Reordering children of `Zahlungswesen`
-- Node name: `Payment (C_Payment)`
-- 2026-03-17T19:53:05.316Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540749 AND AD_Tree_ID=10
;

-- Node name: `Payment Selection (C_PaySelection)`
-- 2026-03-17T19:53:05.317Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540910 AND AD_Tree_ID=10
;

-- Node name: `Bank Statement (C_BankStatement)`
-- 2026-03-17T19:53:05.318Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540803 AND AD_Tree_ID=10
;

-- Node name: `Bankstatement Reference`
-- 2026-03-17T19:53:05.319Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540904 AND AD_Tree_ID=10
;

-- Node name: `Cash Journal (C_BankStatement)`
-- 2026-03-17T19:53:05.320Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541377 AND AD_Tree_ID=10
;

-- Node name: `Manual Payment Allocations (C_AllocationHdr)`
-- 2026-03-17T19:53:05.320Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540779 AND AD_Tree_ID=10
;

-- Reordering children of `Zahlungswesen`
-- Node name: `Payment (C_Payment)`
-- 2026-03-17T19:53:08.276Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540749 AND AD_Tree_ID=10
;

-- Node name: `Payment Selection (C_PaySelection)`
-- 2026-03-17T19:53:08.277Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540910 AND AD_Tree_ID=10
;

-- Node name: `Bank Statement (C_BankStatement)`
-- 2026-03-17T19:53:08.278Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540803 AND AD_Tree_ID=10
;

-- Node name: `Bankstatement Reference`
-- 2026-03-17T19:53:08.279Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540904 AND AD_Tree_ID=10
;

-- Node name: `Cash Journal (C_BankStatement)`
-- 2026-03-17T19:53:08.280Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541377 AND AD_Tree_ID=10
;

-- Node name: `Payment Reservation (C_Payment_Reservation)`
-- 2026-03-17T19:53:08.281Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541308 AND AD_Tree_ID=10
;

-- Node name: `Manual Payment Allocations (C_AllocationHdr)`
-- 2026-03-17T19:53:08.281Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540779 AND AD_Tree_ID=10
;

-- Reordering children of `Zahlungswesen`
-- Node name: `Payment (C_Payment)`
-- 2026-03-17T19:53:11.989Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540749 AND AD_Tree_ID=10
;

-- Node name: `Payment Selection (C_PaySelection)`
-- 2026-03-17T19:53:11.990Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540910 AND AD_Tree_ID=10
;

-- Node name: `Bank Statement (C_BankStatement)`
-- 2026-03-17T19:53:11.991Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540803 AND AD_Tree_ID=10
;

-- Node name: `Bankstatement Reference`
-- 2026-03-17T19:53:11.992Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540904 AND AD_Tree_ID=10
;

-- Node name: `Cash Journal (C_BankStatement)`
-- 2026-03-17T19:53:11.993Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541377 AND AD_Tree_ID=10
;

-- Node name: `Payment Reservation (C_Payment_Reservation)`
-- 2026-03-17T19:53:11.994Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541308 AND AD_Tree_ID=10
;

-- Node name: `Payment Reservation Capture (C_Payment_Reservation_Capture)`
-- 2026-03-17T19:53:11.994Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541313 AND AD_Tree_ID=10
;

-- Node name: `Manual Payment Allocations (C_AllocationHdr)`
-- 2026-03-17T19:53:11.995Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540779 AND AD_Tree_ID=10
;

-- Reordering children of `Zahlungswesen`
-- Node name: `Payment (C_Payment)`
-- 2026-03-17T19:53:19.676Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540749 AND AD_Tree_ID=10
;

-- Node name: `Payment Selection (C_PaySelection)`
-- 2026-03-17T19:53:19.677Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540910 AND AD_Tree_ID=10
;

-- Node name: `Bank Statement (C_BankStatement)`
-- 2026-03-17T19:53:19.678Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540803 AND AD_Tree_ID=10
;

-- Node name: `Bankstatement Reference`
-- 2026-03-17T19:53:19.678Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540904 AND AD_Tree_ID=10
;

-- Node name: `Cash Journal (C_BankStatement)`
-- 2026-03-17T19:53:19.680Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541377 AND AD_Tree_ID=10
;

-- Node name: `Payment Reservation (C_Payment_Reservation)`
-- 2026-03-17T19:53:19.681Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541308 AND AD_Tree_ID=10
;

-- Node name: `Payment Reservation Capture (C_Payment_Reservation_Capture)`
-- 2026-03-17T19:53:19.682Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541313 AND AD_Tree_ID=10
;

-- Node name: `Remittance Advice (REMADV) (C_RemittanceAdvice)`
-- 2026-03-17T19:53:19.682Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541584 AND AD_Tree_ID=10
;

-- Node name: `Manual Payment Allocations (C_AllocationHdr)`
-- 2026-03-17T19:53:19.683Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540779 AND AD_Tree_ID=10
;

-- Reordering children of `Zahlungswesen`
-- Node name: `Payment (C_Payment)`
-- 2026-03-17T19:53:23.982Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540749 AND AD_Tree_ID=10
;

-- Node name: `Payment Selection (C_PaySelection)`
-- 2026-03-17T19:53:23.983Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540910 AND AD_Tree_ID=10
;

-- Node name: `Bank Statement (C_BankStatement)`
-- 2026-03-17T19:53:23.984Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540803 AND AD_Tree_ID=10
;

-- Node name: `Bankstatement Reference`
-- 2026-03-17T19:53:23.985Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540904 AND AD_Tree_ID=10
;

-- Node name: `Cash Journal (C_BankStatement)`
-- 2026-03-17T19:53:23.986Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541377 AND AD_Tree_ID=10
;

-- Node name: `Payment Reservation (C_Payment_Reservation)`
-- 2026-03-17T19:53:23.988Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541308 AND AD_Tree_ID=10
;

-- Node name: `Payment Reservation Capture (C_Payment_Reservation_Capture)`
-- 2026-03-17T19:53:23.989Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541313 AND AD_Tree_ID=10
;

-- Node name: `Remittance Advice (REMADV) (C_RemittanceAdvice)`
-- 2026-03-17T19:53:23.990Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541584 AND AD_Tree_ID=10
;

-- Node name: `Import Bank Statement (I_BankStatement)`
-- 2026-03-17T19:53:23.991Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541297 AND AD_Tree_ID=10
;

-- Node name: `Manual Payment Allocations (C_AllocationHdr)`
-- 2026-03-17T19:53:23.993Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540779 AND AD_Tree_ID=10
;

-- Reordering children of `Zahlungswesen`
-- Node name: `PayPal`
-- 2026-03-17T19:54:00.885Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541304 AND AD_Tree_ID=10
;

-- Node name: `Payment (C_Payment)`
-- 2026-03-17T19:54:00.887Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540749 AND AD_Tree_ID=10
;

-- Node name: `Payment Selection (C_PaySelection)`
-- 2026-03-17T19:54:00.888Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540910 AND AD_Tree_ID=10
;

-- Node name: `Bank Statement (C_BankStatement)`
-- 2026-03-17T19:54:00.890Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540803 AND AD_Tree_ID=10
;

-- Node name: `Bankstatement Reference`
-- 2026-03-17T19:54:00.891Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540904 AND AD_Tree_ID=10
;

-- Node name: `Cash Journal (C_BankStatement)`
-- 2026-03-17T19:54:00.893Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541377 AND AD_Tree_ID=10
;

-- Node name: `Payment Reservation (C_Payment_Reservation)`
-- 2026-03-17T19:54:00.895Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541308 AND AD_Tree_ID=10
;

-- Node name: `Payment Reservation Capture (C_Payment_Reservation_Capture)`
-- 2026-03-17T19:54:00.896Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541313 AND AD_Tree_ID=10
;

-- Node name: `Remittance Advice (REMADV) (C_RemittanceAdvice)`
-- 2026-03-17T19:54:00.899Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541584 AND AD_Tree_ID=10
;

-- Node name: `Import Bank Statement (I_BankStatement)`
-- 2026-03-17T19:54:00.901Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541297 AND AD_Tree_ID=10
;

-- Node name: `Manual Payment Allocations (C_AllocationHdr)`
-- 2026-03-17T19:54:00.904Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540779 AND AD_Tree_ID=10
;

-- Reordering children of `Zahlungswesen`
-- Node name: `PayPal`
-- 2026-03-17T19:54:12.682Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541304 AND AD_Tree_ID=10
;

-- Node name: `SumUp`
-- 2026-03-17T19:54:12.684Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542175 AND AD_Tree_ID=10
;

-- Node name: `Payment (C_Payment)`
-- 2026-03-17T19:54:12.684Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540749 AND AD_Tree_ID=10
;

-- Node name: `Payment Selection (C_PaySelection)`
-- 2026-03-17T19:54:12.685Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540910 AND AD_Tree_ID=10
;

-- Node name: `Bank Statement (C_BankStatement)`
-- 2026-03-17T19:54:12.686Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540803 AND AD_Tree_ID=10
;

-- Node name: `Bankstatement Reference`
-- 2026-03-17T19:54:12.687Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540904 AND AD_Tree_ID=10
;

-- Node name: `Cash Journal (C_BankStatement)`
-- 2026-03-17T19:54:12.688Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541377 AND AD_Tree_ID=10
;

-- Node name: `Payment Reservation (C_Payment_Reservation)`
-- 2026-03-17T19:54:12.688Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541308 AND AD_Tree_ID=10
;

-- Node name: `Payment Reservation Capture (C_Payment_Reservation_Capture)`
-- 2026-03-17T19:54:12.690Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541313 AND AD_Tree_ID=10
;

-- Node name: `Remittance Advice (REMADV) (C_RemittanceAdvice)`
-- 2026-03-17T19:54:12.690Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541584 AND AD_Tree_ID=10
;

-- Node name: `Import Bank Statement (I_BankStatement)`
-- 2026-03-17T19:54:12.691Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541297 AND AD_Tree_ID=10
;

-- Node name: `Manual Payment Allocations (C_AllocationHdr)`
-- 2026-03-17T19:54:12.692Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540779 AND AD_Tree_ID=10
;

-- Reordering children of `Zahlungswesen`
-- Node name: `PayPal`
-- 2026-03-17T19:54:22.767Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541304 AND AD_Tree_ID=10
;

-- Node name: `SumUp`
-- 2026-03-17T19:54:22.768Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542175 AND AD_Tree_ID=10
;

-- Node name: `Bank Statement Import-File (C_BankStatement_Import_File)`
-- 2026-03-17T19:54:22.769Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542014 AND AD_Tree_ID=10
;

-- Node name: `Payment (C_Payment)`
-- 2026-03-17T19:54:22.770Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540749 AND AD_Tree_ID=10
;

-- Node name: `Payment Selection (C_PaySelection)`
-- 2026-03-17T19:54:22.771Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540910 AND AD_Tree_ID=10
;

-- Node name: `Bank Statement (C_BankStatement)`
-- 2026-03-17T19:54:22.772Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540803 AND AD_Tree_ID=10
;

-- Node name: `Bankstatement Reference`
-- 2026-03-17T19:54:22.772Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540904 AND AD_Tree_ID=10
;

-- Node name: `Cash Journal (C_BankStatement)`
-- 2026-03-17T19:54:22.773Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541377 AND AD_Tree_ID=10
;

-- Node name: `Payment Reservation (C_Payment_Reservation)`
-- 2026-03-17T19:54:22.774Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541308 AND AD_Tree_ID=10
;

-- Node name: `Payment Reservation Capture (C_Payment_Reservation_Capture)`
-- 2026-03-17T19:54:22.775Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541313 AND AD_Tree_ID=10
;

-- Node name: `Remittance Advice (REMADV) (C_RemittanceAdvice)`
-- 2026-03-17T19:54:22.775Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541584 AND AD_Tree_ID=10
;

-- Node name: `Import Bank Statement (I_BankStatement)`
-- 2026-03-17T19:54:22.776Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541297 AND AD_Tree_ID=10
;

-- Node name: `Manual Payment Allocations (C_AllocationHdr)`
-- 2026-03-17T19:54:22.777Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540779 AND AD_Tree_ID=10
;

-- Reordering children of `Zahlungswesen`
-- Node name: `PayPal`
-- 2026-03-17T19:54:25.715Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541304 AND AD_Tree_ID=10
;

-- Node name: `SumUp`
-- 2026-03-17T19:54:25.717Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542175 AND AD_Tree_ID=10
;

-- Node name: `Payment (C_Payment)`
-- 2026-03-17T19:54:25.718Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540749 AND AD_Tree_ID=10
;

-- Node name: `Payment Selection (C_PaySelection)`
-- 2026-03-17T19:54:25.719Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540910 AND AD_Tree_ID=10
;

-- Node name: `Bank Statement (C_BankStatement)`
-- 2026-03-17T19:54:25.720Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540803 AND AD_Tree_ID=10
;

-- Node name: `Bankstatement Reference`
-- 2026-03-17T19:54:25.721Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540904 AND AD_Tree_ID=10
;

-- Node name: `Bank Statement Import-File (C_BankStatement_Import_File)`
-- 2026-03-17T19:54:25.722Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542014 AND AD_Tree_ID=10
;

-- Node name: `Cash Journal (C_BankStatement)`
-- 2026-03-17T19:54:25.723Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541377 AND AD_Tree_ID=10
;

-- Node name: `Payment Reservation (C_Payment_Reservation)`
-- 2026-03-17T19:54:25.724Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541308 AND AD_Tree_ID=10
;

-- Node name: `Payment Reservation Capture (C_Payment_Reservation_Capture)`
-- 2026-03-17T19:54:25.725Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541313 AND AD_Tree_ID=10
;

-- Node name: `Remittance Advice (REMADV) (C_RemittanceAdvice)`
-- 2026-03-17T19:54:25.726Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541584 AND AD_Tree_ID=10
;

-- Node name: `Import Bank Statement (I_BankStatement)`
-- 2026-03-17T19:54:25.726Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541297 AND AD_Tree_ID=10
;

-- Node name: `Manual Payment Allocations (C_AllocationHdr)`
-- 2026-03-17T19:54:25.728Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540779 AND AD_Tree_ID=10
;

-- Reordering children of `Zahlungswesen`
-- Node name: `PayPal`
-- 2026-03-17T19:54:31.172Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541304 AND AD_Tree_ID=10
;

-- Node name: `SumUp`
-- 2026-03-17T19:54:31.173Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542175 AND AD_Tree_ID=10
;

-- Node name: `Payment (C_Payment)`
-- 2026-03-17T19:54:31.174Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540749 AND AD_Tree_ID=10
;

-- Node name: `Payment Selection (C_PaySelection)`
-- 2026-03-17T19:54:31.175Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540910 AND AD_Tree_ID=10
;

-- Node name: `Bank Statement (C_BankStatement)`
-- 2026-03-17T19:54:31.176Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540803 AND AD_Tree_ID=10
;

-- Node name: `Bankstatement Reference`
-- 2026-03-17T19:54:31.177Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540904 AND AD_Tree_ID=10
;

-- Node name: `Import Bank Statement (I_BankStatement)`
-- 2026-03-17T19:54:31.178Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541297 AND AD_Tree_ID=10
;

-- Node name: `Bank Statement Import-File (C_BankStatement_Import_File)`
-- 2026-03-17T19:54:31.178Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542014 AND AD_Tree_ID=10
;

-- Node name: `Cash Journal (C_BankStatement)`
-- 2026-03-17T19:54:31.179Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541377 AND AD_Tree_ID=10
;

-- Node name: `Payment Reservation (C_Payment_Reservation)`
-- 2026-03-17T19:54:31.180Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541308 AND AD_Tree_ID=10
;

-- Node name: `Payment Reservation Capture (C_Payment_Reservation_Capture)`
-- 2026-03-17T19:54:31.181Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541313 AND AD_Tree_ID=10
;

-- Node name: `Remittance Advice (REMADV) (C_RemittanceAdvice)`
-- 2026-03-17T19:54:31.181Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541584 AND AD_Tree_ID=10
;

-- Node name: `Manual Payment Allocations (C_AllocationHdr)`
-- 2026-03-17T19:54:31.182Z
UPDATE AD_TreeNodeMM SET Parent_ID=542310, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540779 AND AD_Tree_ID=10
;

-- Column: C_Order_M_InOut_C_Invoice_Overview_V.DocBaseType
-- 2026-03-17T19:56:17.152Z
UPDATE AD_Column SET IsSelectionColumn='N',Updated=TO_TIMESTAMP('2026-03-17 19:56:17.152000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591968
;

-- Column: C_Order_M_InOut_C_Invoice_Overview_V.C_DocType_ID
-- 2026-03-17T19:57:27.177Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=10,Updated=TO_TIMESTAMP('2026-03-17 19:57:27.177000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591967
;

-- Column: C_Order_M_InOut_C_Invoice_Overview_V.DocumentNo
-- 2026-03-17T19:57:37.945Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=20,Updated=TO_TIMESTAMP('2026-03-17 19:57:37.945000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591969
;

-- Column: C_Order_M_InOut_C_Invoice_Overview_V.M_Product_ID
-- 2026-03-17T19:57:56.173Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=30,Updated=TO_TIMESTAMP('2026-03-17 19:57:56.173000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591975
;

-- Column: C_Order_M_InOut_C_Invoice_Overview_V.Date
-- 2026-03-17T20:04:47.209Z
UPDATE AD_Column SET AD_Reference_ID=15,Updated=TO_TIMESTAMP('2026-03-17 20:04:47.209000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591972
;

-- UI Element: Ein- und Verkaufsübersicht(542070,D) -> Verkaufsstatistik(548990,D) -> main -> 20 -> Org.Sektion
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.AD_Org_ID
-- 2026-03-17T20:07:54.845Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2026-03-17 20:07:54.844000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646934
;

-- UI Element: Ein- und Verkaufsübersicht(542070,D) -> Verkaufsstatistik(548990,D) -> main -> 10 -> Product.Maßeinheit
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.C_UOM_ID
-- 2026-03-17T20:07:54.852Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2026-03-17 20:07:54.852000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646924
;

-- UI Element: Ein- und Verkaufsübersicht(542070,D) -> Verkaufsstatistik(548990,D) -> main -> 10 -> Product.Zeilennetto
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.LineNetAmt
-- 2026-03-17T20:07:54.860Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2026-03-17 20:07:54.860000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646925
;

-- UI Element: Ein- und Verkaufsübersicht(542070,D) -> Verkaufsstatistik(548990,D) -> main -> 10 -> Product.Aktueller Bestand
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.Current_Qty_Sum
-- 2026-03-17T20:07:54.867Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2026-03-17 20:07:54.867000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646923
;

-- Field: Ein- und Verkaufsübersicht(542070,D) -> Verkaufsstatistik(548990,D) -> Erstellt durch
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.CreatedBy
-- 2026-03-17T20:08:15.034Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591983,774940,0,548990,0,TO_TIMESTAMP('2026-03-17 20:08:14.907000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzer, der diesen Eintrag erstellt hat',0,'D',0,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.',0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Erstellt durch',0,0,30,0,1,1,TO_TIMESTAMP('2026-03-17 20:08:14.907000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-17T20:08:15.049Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=774940 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-03-17T20:08:15.054Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(246)
;

-- 2026-03-17T20:08:15.873Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=774940
;

-- 2026-03-17T20:08:15.883Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(774940)
;

-- Tab: Ein- und Verkaufsübersicht(542070,D) -> Ein- und Verkaufsübersicht
-- Table: C_Order_M_InOut_C_Invoice_Overview_V
-- 2026-03-17T20:09:01.105Z
UPDATE AD_Tab SET AD_Element_ID=584505, CommitWarning=NULL, Description='Übersicht über Auftrags-, Liefer- und Rechnungspositionen aus Ein- und Verkauf, inkl. aktuellem Lagerbestand.', EntityType='D', Help='', IsInsertRecord='N', IsReadOnly='Y', Name='Ein- und Verkaufsübersicht',Updated=TO_TIMESTAMP('2026-03-17 20:09:01.105000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Tab_ID=548990
;

-- 2026-03-17T20:09:01.109Z
UPDATE AD_Tab_Trl trl SET Description='Übersicht über Auftrags-, Liefer- und Rechnungspositionen aus Ein- und Verkauf, inkl. aktuellem Lagerbestand.',Help='',Name='Ein- und Verkaufsübersicht' WHERE AD_Tab_ID=548990 AND AD_Language='de_DE'
;

-- 2026-03-17T20:09:01.113Z
/* DDL */  select update_tab_translation_from_ad_element(584505)
;

-- 2026-03-17T20:09:01.117Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(548990)
;

-- Field: Ein- und Verkaufsübersicht(542070,D) -> Ein- und Verkaufsübersicht(548990,D) -> Datum
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.Date
-- 2026-03-17T20:09:31.273Z
UPDATE AD_Field SET SortNo=10,Updated=TO_TIMESTAMP('2026-03-17 20:09:31.273000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=772017
;

-- Field: Ein- und Verkaufsübersicht(542070,D) -> Ein- und Verkaufsübersicht(548990,D) -> Geschäftspartner
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.C_BPartner_ID
-- 2026-03-17T20:09:57.479Z
UPDATE AD_Field SET SortNo=20,Updated=TO_TIMESTAMP('2026-03-17 20:09:57.478000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=772018
;

-- Field: Ein- und Verkaufsübersicht(542070,D) -> Ein- und Verkaufsübersicht(548990,D) -> Datensatz-ID
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.Record_ID
-- 2026-03-17T20:10:11.598Z
UPDATE AD_Field SET SortNo=NULL,Updated=TO_TIMESTAMP('2026-03-17 20:10:11.598000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=772134
;

-- Field: Ein- und Verkaufsübersicht(542070,D) -> Ein- und Verkaufsübersicht(548990,D) -> DB-Tabelle
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.AD_Table_ID
-- 2026-03-17T20:10:12.590Z
UPDATE AD_Field SET SortNo=NULL,Updated=TO_TIMESTAMP('2026-03-17 20:10:12.590000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=772135
;

-- Field: Ein- und Verkaufsübersicht(542070,D) -> Ein- und Verkaufsübersicht(548990,D) -> Erstellt durch
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.CreatedBy
-- 2026-03-17T20:10:13.694Z
UPDATE AD_Field SET SortNo=NULL,Updated=TO_TIMESTAMP('2026-03-17 20:10:13.694000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=774940
;

-- UI Element: Ein- und Verkaufsübersicht(542070,D) -> Ein- und Verkaufsübersicht(548990,D) -> main -> 10 -> Document.Standort
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.C_BPartner_Location_ID
-- 2026-03-17T20:10:45.951Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2026-03-17 20:10:45.951000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646921
;

-- UI Element: Ein- und Verkaufsübersicht(542070,D) -> Ein- und Verkaufsübersicht(548990,D) -> main -> 10 -> Product.Produkt
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.M_Product_ID
-- 2026-03-17T20:10:45.958Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2026-03-17 20:10:45.958000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646922
;

-- UI Element: Ein- und Verkaufsübersicht(542070,D) -> Ein- und Verkaufsübersicht(548990,D) -> main -> 10 -> Product.Maßeinheit
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.C_UOM_ID
-- 2026-03-17T20:10:45.963Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2026-03-17 20:10:45.963000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646924
;

-- UI Element: Ein- und Verkaufsübersicht(542070,D) -> Ein- und Verkaufsübersicht(548990,D) -> main -> 10 -> Product.Zeilennetto
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.LineNetAmt
-- 2026-03-17T20:10:45.970Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2026-03-17 20:10:45.970000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646925
;

-- UI Element: Ein- und Verkaufsübersicht(542070,D) -> Ein- und Verkaufsübersicht(548990,D) -> main -> 10 -> Product.Aktueller Bestand
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.Current_Qty_Sum
-- 2026-03-17T20:10:45.975Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2026-03-17 20:10:45.975000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646923
;

-- UI Element: Ein- und Verkaufsübersicht(542070,D) -> Ein- und Verkaufsübersicht(548990,D) -> main -> 10 -> Document.Created By
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.CreatedBy
-- 2026-03-17T20:11:51.675Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,774940,0,548990,554807,648585,'F',TO_TIMESTAMP('2026-03-17 20:11:51.529000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzer, der diesen Eintrag erstellt hat','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','Y','N','N','N',0,'Created By',50,0,0,TO_TIMESTAMP('2026-03-17 20:11:51.529000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Ein- und Verkaufsübersicht(542070,D) -> Ein- und Verkaufsübersicht(548990,D) -> main -> 10 -> Document.Belegart
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.C_DocType_ID
-- 2026-03-17T20:12:16.158Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2026-03-17 20:12:16.158000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646919
;

-- UI Element: Ein- und Verkaufsübersicht(542070,D) -> Ein- und Verkaufsübersicht(548990,D) -> main -> 10 -> Document.Nr.
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.DocumentNo
-- 2026-03-17T20:12:16.167Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2026-03-17 20:12:16.167000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646918
;

-- UI Element: Ein- und Verkaufsübersicht(542070,D) -> Ein- und Verkaufsübersicht(548990,D) -> main -> 10 -> Document.Created By
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.CreatedBy
-- 2026-03-17T20:12:16.174Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2026-03-17 20:12:16.174000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=648585
;

-- UI Element: Ein- und Verkaufsübersicht(542070,D) -> Ein- und Verkaufsübersicht(548990,D) -> main -> 10 -> Product.Aktueller Bestand
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.Current_Qty_Sum
-- 2026-03-17T20:12:16.180Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2026-03-17 20:12:16.180000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646923
;

-- Column: C_Order_M_InOut_C_Invoice_Overview_V.CreatedBy
-- 2026-03-17T20:12:47.345Z
UPDATE AD_Column SET IsFacetFilter='Y',Updated=TO_TIMESTAMP('2026-03-17 20:12:47.345000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591983
;

-- 2026-03-17T20:13:48.257Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584684,0,TO_TIMESTAMP('2026-03-17 20:13:48.125000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Erfasst durch','Erfasst durch',TO_TIMESTAMP('2026-03-17 20:13:48.125000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-17T20:13:48.261Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584684 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2026-03-17T20:13:52.030Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-03-17 20:13:52.030000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584684 AND AD_Language='de_CH'
;

-- 2026-03-17T20:13:52.034Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584684,'de_CH')
;

-- Element: null
-- 2026-03-17T20:13:56.344Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-03-17 20:13:56.344000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584684 AND AD_Language='de_DE'
;

-- 2026-03-17T20:13:56.348Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584684,'de_DE')
;

-- 2026-03-17T20:13:56.350Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584684,'de_DE')
;

-- Element: null
-- 2026-03-17T20:14:19.680Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Recorded by', PrintName='Recorded by',Updated=TO_TIMESTAMP('2026-03-17 20:14:19.680000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584684 AND AD_Language='en_US'
;

-- 2026-03-17T20:14:19.682Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-03-17T20:14:20.147Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584684,'en_US')
;

--------------------------

-- UI Element: Ein- und Verkaufsübersicht(542070,D) -> Ein- und Verkaufsübersicht(548990,D) -> main -> 10 -> Product.Menge
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.Qty
-- 2026-03-17T20:21:30.695Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2026-03-17 20:21:30.695000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646929
;

-- UI Element: Ein- und Verkaufsübersicht(542070,D) -> Ein- und Verkaufsübersicht(548990,D) -> main -> 10 -> Product.Zeilennetto
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.LineNetAmt
-- 2026-03-17T20:21:30.703Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2026-03-17 20:21:30.703000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646925
;

-- UI Element: Ein- und Verkaufsübersicht(542070,D) -> Ein- und Verkaufsübersicht(548990,D) -> main -> 10 -> Document.Created By
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.CreatedBy
-- 2026-03-17T20:21:30.709Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2026-03-17 20:21:30.709000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=648585
;

-- UI Element: Ein- und Verkaufsübersicht(542070,D) -> Ein- und Verkaufsübersicht(548990,D) -> main -> 10 -> Product.Aktueller Bestand
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.Current_Qty_Sum
-- 2026-03-17T20:21:30.716Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2026-03-17 20:21:30.715000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646923
;

-- UI Element: Ein- und Verkaufsübersicht(542070,D) -> Ein- und Verkaufsübersicht(548990,D) -> main -> 20 -> Status.Datensatz-ID
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.Record_ID
-- 2026-03-17T20:22:06.710Z
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2026-03-17 20:22:06.710000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647015
;

-- UI Element: Ein- und Verkaufsübersicht(542070,D) -> Ein- und Verkaufsübersicht(548990,D) -> main -> 20 -> Status.Datensatz-ID
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.Record_ID
-- 2026-03-17T20:22:14.541Z
UPDATE AD_UI_Element SET IsActive='Y',Updated=TO_TIMESTAMP('2026-03-17 20:22:14.541000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647015
;

-- UI Element: Ein- und Verkaufsübersicht(542070,D) -> Ein- und Verkaufsübersicht(548990,D) -> main -> 20 -> Status.DocStatus
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.DocStatus
-- 2026-03-17T20:22:38.562Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772016,0,548990,554810,648586,'F',TO_TIMESTAMP('2026-03-17 20:22:38.428000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'The current status of the document','The Document Status indicates the status of a document at this time.  If you want to change the document status, use the Document Action field','Y','N','N','Y','N','N','N',0,'DocStatus',25,0,0,TO_TIMESTAMP('2026-03-17 20:22:38.428000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Ein- und Verkaufsübersicht(542070,D) -> Ein- und Verkaufsübersicht(548990,D) -> main -> 20 -> Status.DocStatus
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.DocStatus
-- 2026-03-17T20:22:55.228Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2026-03-17 20:22:55.228000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=648586
;

-- UI Element: Ein- und Verkaufsübersicht(542070,D) -> Ein- und Verkaufsübersicht(548990,D) -> main -> 20 -> Status.Datum
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.Date
-- 2026-03-17T20:22:55.236Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2026-03-17 20:22:55.236000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646930
;

-- UI Element: Ein- und Verkaufsübersicht(542070,D) -> Ein- und Verkaufsübersicht(548990,D) -> main -> 10 -> Document.Geschäftspartner
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.C_BPartner_ID
-- 2026-03-17T20:22:55.242Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2026-03-17 20:22:55.242000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646920
;

-- UI Element: Ein- und Verkaufsübersicht(542070,D) -> Ein- und Verkaufsübersicht(548990,D) -> main -> 10 -> Document.Standort
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.C_BPartner_Location_ID
-- 2026-03-17T20:22:55.249Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2026-03-17 20:22:55.249000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646921
;

-- UI Element: Ein- und Verkaufsübersicht(542070,D) -> Ein- und Verkaufsübersicht(548990,D) -> main -> 10 -> Product.Produkt
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.M_Product_ID
-- 2026-03-17T20:22:55.255Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2026-03-17 20:22:55.255000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646922
;

-- UI Element: Ein- und Verkaufsübersicht(542070,D) -> Ein- und Verkaufsübersicht(548990,D) -> main -> 10 -> Product.Maßeinheit
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.C_UOM_ID
-- 2026-03-17T20:22:55.260Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2026-03-17 20:22:55.260000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646924
;

-- UI Element: Ein- und Verkaufsübersicht(542070,D) -> Ein- und Verkaufsübersicht(548990,D) -> main -> 10 -> Product.Menge
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.Qty
-- 2026-03-17T20:22:55.266Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2026-03-17 20:22:55.266000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646929
;

-- UI Element: Ein- und Verkaufsübersicht(542070,D) -> Ein- und Verkaufsübersicht(548990,D) -> main -> 10 -> Product.Zeilennetto
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.LineNetAmt
-- 2026-03-17T20:22:55.273Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2026-03-17 20:22:55.273000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646925
;

-- UI Element: Ein- und Verkaufsübersicht(542070,D) -> Ein- und Verkaufsübersicht(548990,D) -> main -> 10 -> Document.Created By
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.CreatedBy
-- 2026-03-17T20:22:55.279Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2026-03-17 20:22:55.278000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=648585
;

-- UI Element: Ein- und Verkaufsübersicht(542070,D) -> Ein- und Verkaufsübersicht(548990,D) -> main -> 10 -> Product.Aktueller Bestand
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.Current_Qty_Sum
-- 2026-03-17T20:22:55.284Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2026-03-17 20:22:55.284000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646923
;

-- Field: Ein- und Verkaufsübersicht(542070,D) -> Ein- und Verkaufsübersicht(548990,D) -> Belegdatum
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.Date
-- 2026-03-17T20:23:16.036Z
UPDATE AD_Field SET AD_Name_ID=541346, Description=NULL, Help=NULL, Name='Belegdatum',Updated=TO_TIMESTAMP('2026-03-17 20:23:16.036000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=772017
;

-- 2026-03-17T20:23:16.038Z
UPDATE AD_Field_Trl trl SET Name='Belegdatum' WHERE AD_Field_ID=772017 AND AD_Language='de_DE'
;

-- 2026-03-17T20:23:16.039Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541346)
;

-- 2026-03-17T20:23:16.052Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772017
;

-- 2026-03-17T20:23:16.053Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772017)
;

-- UI Element: Ein- und Verkaufsübersicht(542070,D) -> Ein- und Verkaufsübersicht(548990,D) -> main -> 20 -> Status.DocStatus
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.DocStatus
-- 2026-03-17T20:24:03.387Z
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2026-03-17 20:24:03.387000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=648586
;

-- UI Element: Ein- und Verkaufsübersicht(542070,D) -> Ein- und Verkaufsübersicht(548990,D) -> main -> 20 -> Org.Mandant
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.AD_Client_ID
-- 2026-03-17T20:24:34.840Z
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2026-03-17 20:24:34.840000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646935
;



-- UI Element: Ein- und Verkaufsübersicht(542070,D) -> Ein- und Verkaufsübersicht(548990,D) -> main -> 10 -> Product.Menge
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.Qty
-- 2026-03-17T20:37:21.359Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2026-03-17 20:37:21.359000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646929
;

-- UI Element: Ein- und Verkaufsübersicht(542070,D) -> Ein- und Verkaufsübersicht(548990,D) -> main -> 10 -> Product.Maßeinheit
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.C_UOM_ID
-- 2026-03-17T20:37:21.371Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2026-03-17 20:37:21.371000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646924
;

-- UI Element: Ein- und Verkaufsübersicht(542070,D) -> Ein- und Verkaufsübersicht(548990,D) -> main -> 10 -> Product.Aktueller Bestand
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.Current_Qty_Sum
-- 2026-03-17T20:37:32.516Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2026-03-17 20:37:32.515000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646923
;

-- UI Element: Ein- und Verkaufsübersicht(542070,D) -> Ein- und Verkaufsübersicht(548990,D) -> main -> 10 -> Document.Created By
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.CreatedBy
-- 2026-03-17T20:37:32.523Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2026-03-17 20:37:32.523000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=648585
;

