
-- Value: Material_Cockpit2_BOM_Demand_Report
-- Classname: de.metas.process.ExecuteUpdateSQL
-- 2026-01-21T16:35:27.194Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585559,'Y','de.metas.process.ExecuteUpdateSQL',TO_TIMESTAMP('2026-01-21 16:35:27.190000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N','N','Y','N','N','N','N','Y','N','Y','Bestandsprüfung Rohware ','json','N','N','xls','SQL',TO_TIMESTAMP('2026-01-21 16:35:27.190000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Material_Cockpit2_BOM_Demand_Report')
;

-- 2026-01-21T16:35:27.197Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585559 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Value: Material_Cockpit2_BOM_Demand_Report
-- Classname: de.metas.process.ExecuteUpdateSQL
-- 2026-01-21T16:35:55.168Z
UPDATE AD_Process SET SQLStatement='select de_metas_os198.create_manual_invoice_candidates_from_shipment_schedules(''@$WEBUI_ViewSelectedIds/0@'', ''@$WEBUI_ViewId@'');',Updated=TO_TIMESTAMP('2026-01-21 16:35:55.168000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585559
;

-- Value: Material_Cockpit2_BOM_Demand_Report
-- Classname: de.metas.process.ExecuteUpdateSQL
-- 2026-01-21T16:36:09.151Z
UPDATE AD_Process SET SQLStatement='select material_cockpit_bom_demand_report(''@$WEBUI_ViewSelectedIds/0@'', ''@$WEBUI_ViewId@'');',Updated=TO_TIMESTAMP('2026-01-21 16:36:09.151000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585559
;

-- Process: Material_Cockpit2_BOM_Demand_Report(de.metas.process.ExecuteUpdateSQL)
-- Table: QtyDemand_QtySupply_V
-- Window: Material Cockpit v2(541963,D)
-- EntityType: D
-- 2026-01-21T16:38:28.242Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585559,542218,541600,541963,TO_TIMESTAMP('2026-01-21 16:38:28.232000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y',TO_TIMESTAMP('2026-01-21 16:38:28.232000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N')
;

-- Process: Material_Cockpit2_BOM_Demand_Report(de.metas.process.ExecuteUpdateSQL)
-- Table: QtyDemand_QtySupply_V
-- Window: Material Cockpit v2(541963,D)
-- EntityType: D
-- 2026-01-21T16:38:34.665Z
UPDATE AD_Table_Process SET WEBUI_ViewQuickAction='Y',Updated=TO_TIMESTAMP('2026-01-21 16:38:34.665000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Table_Process_ID=541600
;



-- Value: Material_Cockpit2_BOM_Demand_Report
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2026-01-21T16:43:51.877Z
UPDATE AD_Process SET Classname='de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess', Type='Excel',Updated=TO_TIMESTAMP('2026-01-21 16:43:51.877000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585559
;


-- Value: Material_Cockpit2_BOM_Demand_Report
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2026-01-21T16:46:21.139Z
UPDATE AD_Process SET SQLStatement='select * from material_cockpit_bom_demand_report(''@$WEBUI_ViewSelectedIds/0@'', ''@$WEBUI_ViewId@'');',Updated=TO_TIMESTAMP('2026-01-21 16:46:21.139000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585559
;






-- 2026-01-21T17:46:29.415Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584434,0,'Parent_product',TO_TIMESTAMP('2026-01-21 17:46:29.413000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','Fertigprodukt','Fertigprodukt',TO_TIMESTAMP('2026-01-21 17:46:29.413000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-21T17:46:29.415Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584434 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2026-01-21T17:46:31.251Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2026-01-21 17:46:31.251000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584434
;

-- 2026-01-21T17:46:31.252Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584434,'de_DE')
;

-- Element: Parent_product
-- 2026-01-21T17:46:41.210Z
UPDATE AD_Element_Trl SET Name='Finished Product',Updated=TO_TIMESTAMP('2026-01-21 17:46:41.210000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584434 AND AD_Language='en_US'
;

-- 2026-01-21T17:46:41.211Z
UPDATE AD_Element base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-01-21T17:46:41.428Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584434,'en_US')
;

-- 2026-01-21T17:46:59.637Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584435,0,'Parent_qty_reserved',TO_TIMESTAMP('2026-01-21 17:46:59.636000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','Reservierte Menge','Reservierte Menge',TO_TIMESTAMP('2026-01-21 17:46:59.636000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-21T17:46:59.638Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584435 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2026-01-21T17:47:01.353Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2026-01-21 17:47:01.353000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584435
;

-- 2026-01-21T17:47:01.354Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584435,'de_DE')
;

-- Element: Parent_qty_reserved
-- 2026-01-21T17:47:12.219Z
UPDATE AD_Element_Trl SET Name='Reserved Quantity',Updated=TO_TIMESTAMP('2026-01-21 17:47:12.219000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584435 AND AD_Language='en_US'
;

-- 2026-01-21T17:47:12.219Z
UPDATE AD_Element base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-01-21T17:47:12.457Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584435,'en_US')
;

-- 2026-01-21T17:48:11.243Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584436,0,'Bom_line',TO_TIMESTAMP('2026-01-21 17:48:11.241000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','Stücklistenbestandteile','Stücklistenbestandteile',TO_TIMESTAMP('2026-01-21 17:48:11.241000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-21T17:48:11.243Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584436 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2026-01-21T17:48:15.447Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2026-01-21 17:48:15.447000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584436
;

-- 2026-01-21T17:48:15.448Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584436,'de_DE')
;

-- Element: Bom_line
-- 2026-01-21T17:48:30.383Z
UPDATE AD_Element_Trl SET Name='BOM Position',Updated=TO_TIMESTAMP('2026-01-21 17:48:30.383000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584436 AND AD_Language='en_US'
;

-- 2026-01-21T17:48:30.384Z
UPDATE AD_Element base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-01-21T17:48:30.621Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584436,'en_US')
;

-- 2026-01-21T17:48:47.516Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584437,0,'Component_product',TO_TIMESTAMP('2026-01-21 17:48:47.516000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','Komponente','Komponente',TO_TIMESTAMP('2026-01-21 17:48:47.516000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-21T17:48:47.517Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584437 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2026-01-21T17:48:49.078Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2026-01-21 17:48:49.078000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584437
;

-- 2026-01-21T17:48:49.079Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584437,'de_DE')
;

-- Element: Component_product
-- 2026-01-21T17:48:58.704Z
UPDATE AD_Element_Trl SET Name='Component',Updated=TO_TIMESTAMP('2026-01-21 17:48:58.704000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584437 AND AD_Language='en_US'
;

-- 2026-01-21T17:48:58.704Z
UPDATE AD_Element base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-01-21T17:48:58.934Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584437,'en_US')
;

-- 2026-01-21T17:49:18.105Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584438,0,'Bom_qty_per_unit',TO_TIMESTAMP('2026-01-21 17:49:18.104000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','Menge pro Einheit','Menge pro Einheit',TO_TIMESTAMP('2026-01-21 17:49:18.104000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-21T17:49:18.105Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584438 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2026-01-21T17:49:23.845Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2026-01-21 17:49:23.845000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584438
;

-- 2026-01-21T17:49:23.846Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584438,'de_DE')
;

-- Element: Bom_qty_per_unit
-- 2026-01-21T17:49:29.927Z
UPDATE AD_Element_Trl SET Name='Qty per Unit',Updated=TO_TIMESTAMP('2026-01-21 17:49:29.927000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584438 AND AD_Language='en_US'
;

-- 2026-01-21T17:49:29.928Z
UPDATE AD_Element base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-01-21T17:49:30.148Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584438,'en_US')
;

-- Element: Bom_qty_per_unit
-- 2026-01-21T17:49:30.818Z
UPDATE AD_Element_Trl SET PrintName='Qty per Unit',Updated=TO_TIMESTAMP('2026-01-21 17:49:30.818000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584438 AND AD_Language='en_US'
;

-- 2026-01-21T17:49:30.819Z
UPDATE AD_Element base SET PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-01-21T17:49:31.017Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584438,'en_US')
;

-- 2026-01-21T17:49:41.629Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584439,0,'Total_component_qty_needed',TO_TIMESTAMP('2026-01-21 17:49:41.628000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','Bedarf Komponente','Bedarf Komponente',TO_TIMESTAMP('2026-01-21 17:49:41.628000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-21T17:49:41.639Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584439 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2026-01-21T17:49:43.230Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2026-01-21 17:49:43.230000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584439
;

-- 2026-01-21T17:49:43.230Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584439,'de_DE')
;

-- Element: Total_component_qty_needed
-- 2026-01-21T17:49:52.460Z
UPDATE AD_Element_Trl SET Name='Component Demand',Updated=TO_TIMESTAMP('2026-01-21 17:49:52.460000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584439 AND AD_Language='en_US'
;

-- 2026-01-21T17:49:52.460Z
UPDATE AD_Element base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-01-21T17:49:52.686Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584439,'en_US')
;

-- Element: Total_component_qty_needed
-- 2026-01-21T17:49:53.225Z
UPDATE AD_Element_Trl SET PrintName='Component Demand',Updated=TO_TIMESTAMP('2026-01-21 17:49:53.225000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584439 AND AD_Language='en_US'
;

-- 2026-01-21T17:49:53.226Z
UPDATE AD_Element base SET PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-01-21T17:49:53.428Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584439,'en_US')
;

-- 2026-01-21T17:50:05.657Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584440,0,'Total_on_hand_qty',TO_TIMESTAMP('2026-01-21 17:50:05.655000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','Lagerbestand Gesamt','Lagerbestand Gesamt',TO_TIMESTAMP('2026-01-21 17:50:05.655000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-21T17:50:05.658Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584440 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2026-01-21T17:50:08.178Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2026-01-21 17:50:08.178000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584440
;

-- 2026-01-21T17:50:08.179Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584440,'de_DE')
;

-- Element: Total_on_hand_qty
-- 2026-01-21T17:50:16.894Z
UPDATE AD_Element_Trl SET Name='Total Stock',Updated=TO_TIMESTAMP('2026-01-21 17:50:16.894000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584440 AND AD_Language='en_US'
;

-- 2026-01-21T17:50:16.895Z
UPDATE AD_Element base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-01-21T17:50:17.100Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584440,'en_US')
;

-- Element: Total_on_hand_qty
-- 2026-01-21T17:50:17.981Z
UPDATE AD_Element_Trl SET PrintName='Total Stock',Updated=TO_TIMESTAMP('2026-01-21 17:50:17.981000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584440 AND AD_Language='en_US'
;

-- 2026-01-21T17:50:17.981Z
UPDATE AD_Element base SET PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-01-21T17:50:18.187Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584440,'en_US')
;

-- 2026-01-21T17:50:37.918Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584441,0,'Shortage',TO_TIMESTAMP('2026-01-21 17:50:37.917000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','Fehlmenge','Fehlmenge',TO_TIMESTAMP('2026-01-21 17:50:37.917000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-21T17:50:37.919Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584441 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2026-01-21T17:50:40.250Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2026-01-21 17:50:40.250000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584441
;

-- 2026-01-21T17:50:40.251Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584441,'de_DE')
;

-- Element: Shortage
-- 2026-01-21T17:50:47.618Z
UPDATE AD_Element_Trl SET Name='Shortage',Updated=TO_TIMESTAMP('2026-01-21 17:50:47.618000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584441 AND AD_Language='en_US'
;

-- 2026-01-21T17:50:47.619Z
UPDATE AD_Element base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-01-21T17:50:47.845Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584441,'en_US')
;

-- Element: Shortage
-- 2026-01-21T17:50:50.200Z
UPDATE AD_Element_Trl SET PrintName='Shortage',Updated=TO_TIMESTAMP('2026-01-21 17:50:50.200000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584441 AND AD_Language='en_US'
;

-- 2026-01-21T17:50:50.200Z
UPDATE AD_Element base SET PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-01-21T17:50:50.429Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584441,'en_US')
;