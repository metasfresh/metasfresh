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

-- Value: C_Proforma_Order_Alloc_DeAllocate
-- Classname: de.metas.invoice.proforma.process.C_Proforma_Order_Alloc_DeAllocate
-- 2026-04-13T15:31:05.124Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,CSVFieldQuote,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsIncludeCSVHeaderRow,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585610,'Y','de.metas.invoice.proforma.process.C_Proforma_Order_Alloc_DeAllocate','N',TO_TIMESTAMP('2026-04-13 15:31:04.932000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'"','de.metas.invoice','Y','N','N','N','Y','Y','N','N','N','N','Y','N','Y',0,'Zuweisung entfernen','json','N','N','xls','Java',TO_TIMESTAMP('2026-04-13 15:31:04.932000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'C_Proforma_Order_Alloc_DeAllocate')
;

-- 2026-04-13T15:31:05.136Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585610 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: C_Proforma_Order_Alloc_DeAllocate(de.metas.invoice.proforma.process.C_Proforma_Order_Alloc_DeAllocate)
-- 2026-04-13T15:31:59.766Z
UPDATE AD_Process_Trl SET Name='Deallocate',Updated=TO_TIMESTAMP('2026-04-13 15:31:59.766000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585610
;

-- 2026-04-13T15:31:59.769Z
UPDATE AD_Process base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Process: C_Proforma_Order_Alloc_DeAllocate(de.metas.invoice.proforma.process.C_Proforma_Order_Alloc_DeAllocate)
-- 2026-04-13T15:32:14.009Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Remove allocation',Updated=TO_TIMESTAMP('2026-04-13 15:32:14.009000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585610
;

-- 2026-04-13T15:32:14.010Z
UPDATE AD_Process base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Process: C_Proforma_Order_Alloc_DeAllocate(de.metas.invoice.proforma.process.C_Proforma_Order_Alloc_DeAllocate)
-- 2026-04-13T15:32:16.185Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-04-13 15:32:16.184000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585610
;

-- Process: C_Proforma_Order_Alloc_DeAllocate(de.metas.invoice.proforma.process.C_Proforma_Order_Alloc_DeAllocate)
-- 2026-04-13T15:32:17.345Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-04-13 15:32:17.345000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585610
;

-- Process: C_Proforma_Order_Alloc_DeAllocate(de.metas.invoice.proforma.process.C_Proforma_Order_Alloc_DeAllocate)
-- Table: C_Proforma_Order_Alloc
-- EntityType: de.metas.invoice
-- 2026-04-13T15:33:09.552Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585610,542590,541638,TO_TIMESTAMP('2026-04-13 15:33:09.418000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.invoice','Y',TO_TIMESTAMP('2026-04-13 15:33:09.418000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','N','N')
;


