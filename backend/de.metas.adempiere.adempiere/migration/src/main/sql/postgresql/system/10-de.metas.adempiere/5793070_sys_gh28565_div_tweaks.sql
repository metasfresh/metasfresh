-- Run mode: SWING_CLIENT

-- Column: C_Invoice.C_PromotionCode_ID
-- 2026-03-08T17:43:40.967Z
UPDATE AD_Column SET AD_Reference_ID=30, AD_Reference_Value_ID=542070, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2026-03-08 17:43:40.967000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=592186
;

-- Column: C_Invoice.C_PromotionCode2_ID
-- 2026-03-08T17:43:49.418Z
UPDATE AD_Column SET AD_Reference_ID=30, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2026-03-08 17:43:49.418000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=592187
;


-- Node name: `Artikelstatistik (Excel)`
-- 2026-03-08T17:51:05.673Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541711 AND AD_Tree_ID=10
;

-- Node name: `Aktionskennzeichen Auswertung (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2026-03-08T17:51:05.675Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542303 AND AD_Tree_ID=10
;

-- Node name: `ADR Auswertung (@PREFIX@de/metas/reports/umsatzreport_adr_bpartner/report.jasper)`
-- 2026-03-08T17:51:05.678Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540930 AND AD_Tree_ID=10
;
-- 2026-03-08T17:59:50.122Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584640,0,'Invoiced',TO_TIMESTAMP('2026-03-08 17:59:49.930000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Filtert nach Fakturierungsstatus: Ja = nur fakturierte, Nein = nur nicht-fakturierte. Nicht gesetzt = alle Aufträge.','D','Dropdown mit drei Zuständen: Ja zeigt nur Aufträge die bereits fakturiert sind, Nein zeigt nur nicht-fakturierte Aufträge, und wenn nichts gewählt ist werden alle Aufträge unabhängig vom Fakturierungsstatus angezeigt.','Y','Fakturiert','Fakturiert',TO_TIMESTAMP('2026-03-08 17:59:49.930000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-08T17:59:50.132Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584640 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: Invoiced
-- 2026-03-08T17:59:54.078Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-03-08 17:59:54.078000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584640 AND AD_Language='de_CH'
;

-- 2026-03-08T17:59:54.111Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584640,'de_CH')
;

-- Element: Invoiced
-- 2026-03-08T18:00:06.094Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-03-08 18:00:06.094000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584640 AND AD_Language='de_DE'
;

-- 2026-03-08T18:00:06.097Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584640,'de_DE')
;

-- 2026-03-08T18:00:06.107Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584640,'de_DE')
;

-- Element: Invoiced
-- 2026-03-08T18:00:53.699Z
UPDATE AD_Element_Trl SET Description='Filter by invoicing status: Yes = invoiced only, No = not-invoiced only. Leave empty for all orders.', Help='Dropdown with three states: Yes shows only orders that have been invoiced, No shows only orders not yet invoiced, and when nothing is selected all orders are shown regardless of invoicing status.', IsTranslated='Y', Name='Invoiced', PrintName='Invoiced',Updated=TO_TIMESTAMP('2026-03-08 18:00:53.699000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584640 AND AD_Language='en_GB'
;

-- 2026-03-08T18:00:53.700Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_GB' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-03-08T18:00:54.030Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584640,'en_GB')
;

-- Element: Invoiced
-- 2026-03-08T18:01:14.736Z
UPDATE AD_Element_Trl SET Description='Filter by invoicing status: Yes = invoiced only, No = not-invoiced only. Leave empty for all orders.', Help='Dropdown with three states: Yes shows only orders that have been invoiced, No shows only orders not yet invoiced, and when nothing is selected all orders are shown regardless of invoicing status.', IsTranslated='Y', Name='Invoiced', PrintName='Invoiced',Updated=TO_TIMESTAMP('2026-03-08 18:01:14.735000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584640 AND AD_Language='en_US'
;

-- 2026-03-08T18:01:14.737Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-03-08T18:01:15.063Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584640,'en_US')
;

-- Process: PromotionCode_Report(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: Invoiced
-- 2026-03-08T18:01:53.183Z
UPDATE AD_Process_Para SET AD_Element_ID=584640,Updated=TO_TIMESTAMP('2026-03-08 18:01:53.183000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543142
;

-- Process: PromotionCode_Report(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: Invoiced
-- 2026-03-08T18:02:06.044Z
UPDATE AD_Process_Para SET IsCentrallyMaintained='Y',Updated=TO_TIMESTAMP('2026-03-08 18:02:06.044000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543142
;


/*
 * #%L
 * de.metas.adempiere.adempiere.migration-sql
 * %%
 * Copyright (C) 2026 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


-- 2026-03-08T18:01:15.063Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584640,'en_US')
;
select update_TRL_Tables_On_AD_Element_TRL_Update(584640,'en_GB')
;
select update_TRL_Tables_On_AD_Element_TRL_Update(584640,'de_DE')
;
select update_TRL_Tables_On_AD_Element_TRL_Update(584640,'de_CH')
;