/*
 * #%L
 * de.metas.business
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

-- Run mode: SWING_CLIENT

-- Value: C_Invoice_Proforma_RemoveAllAllocations
-- Classname: de.metas.invoice.proforma.process.C_Invoice_Proforma_RemoveAllAllocations
-- 2026-04-29T00:00:00.000Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,CSVFieldQuote,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsIncludeCSVHeaderRow,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585613/*From ID Server*/,'Y','de.metas.invoice.proforma.process.C_Invoice_Proforma_RemoveAllAllocations','N',TO_TIMESTAMP('2026-04-29 00:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'"','de.metas.invoice','Y','N','N','N','Y','Y','N','N','N','N','Y','N','Y',0,'Alle Zuweisungen entfernen','json','N','N','xls','Java',TO_TIMESTAMP('2026-04-29 00:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'C_Invoice_Proforma_RemoveAllAllocations')
;

-- 2026-04-29T00:00:00.001Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585613 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: C_Invoice_Proforma_RemoveAllAllocations(de.metas.invoice.proforma.process.C_Invoice_Proforma_RemoveAllAllocations)
-- 2026-04-29T00:00:00.002Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Remove all allocations',Updated=TO_TIMESTAMP('2026-04-29 00:00:00.002000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585613
;

-- 2026-04-29T00:00:00.003Z
UPDATE AD_Process base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Process: C_Invoice_Proforma_RemoveAllAllocations(de.metas.invoice.proforma.process.C_Invoice_Proforma_RemoveAllAllocations)
-- 2026-04-29T00:00:00.004Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Alle Zuweisungen entfernen',Updated=TO_TIMESTAMP('2026-04-29 00:00:00.004000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585613
;

-- Process: C_Invoice_Proforma_RemoveAllAllocations(de.metas.invoice.proforma.process.C_Invoice_Proforma_RemoveAllAllocations)
-- 2026-04-29T00:00:00.005Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Alle Zuweisungen entfernen',Updated=TO_TIMESTAMP('2026-04-29 00:00:00.005000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585613
;

-- Process: C_Invoice_Proforma_RemoveAllAllocations(de.metas.invoice.proforma.process.C_Invoice_Proforma_RemoveAllAllocations)
-- Table: C_Invoice (AD_Table_ID=318)
-- EntityType: de.metas.invoice
-- 2026-04-29T00:00:00.006Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585613/*From ID Server*/,318,541642/*From ID Server*/,TO_TIMESTAMP('2026-04-29 00:00:00.006000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.invoice','Y',TO_TIMESTAMP('2026-04-29 00:00:00.006000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','N','N')
;
