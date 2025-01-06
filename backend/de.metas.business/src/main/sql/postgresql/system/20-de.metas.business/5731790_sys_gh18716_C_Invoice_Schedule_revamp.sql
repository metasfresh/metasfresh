/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2024 metas GmbH
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

-- Value: C_InvoiceSchedule_CreateOrUpdate
-- Classname: de.metas.ui.web.invoice.process.C_InvoiceSchedule_CreateOrUpdate
-- 2024-08-27T15:51:03.282Z
UPDATE AD_Process SET Description='Erlaubt das Erstellen eines neuen Terminplans oder das Ändern der Werte, die relevant für Rechnungskandidaten sind.
Nach einer Neuerstellung muss das Fenster durch clicken auf den "Terminplan Rechnung"-Breadcrump neu geladen werden, damit der neue Datensatz angezeigt wird.
Nach einer Änderung werden alle betroffenen Rechnungskandidaten als "zu aktualisieren" markiert.',Updated=TO_TIMESTAMP('2024-08-27 15:51:03.275000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585421
;

-- Process: C_InvoiceSchedule_CreateOrUpdate(de.metas.ui.web.invoice.process.C_InvoiceSchedule_CreateOrUpdate)
-- 2024-08-27T15:51:11.916Z
UPDATE AD_Process_Trl SET Description='Erlaubt das Erstellen eines neuen Terminplans oder das Ändern der Werte, die relevant für Rechnungskandidaten sind.
Nach einer Neuerstellung muss das Fenster durch clicken auf den "Terminplan Rechnung"-Breadcrump neu geladen werden, damit der neue Datensatz angezeigt wird.
Nach einer Änderung werden alle betroffenen Rechnungskandidaten als "zu aktualisieren" markiert.',Updated=TO_TIMESTAMP('2024-08-27 15:51:11.916000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585421
;

-- Process: C_InvoiceSchedule_CreateOrUpdate(de.metas.ui.web.invoice.process.C_InvoiceSchedule_CreateOrUpdate)
-- 2024-08-27T15:51:15.577Z
UPDATE AD_Process_Trl SET Description='Erlaubt das Erstellen eines neuen Terminplans oder das Ändern der Werte, die relevant für Rechnungskandidaten sind.
Nach einer Neuerstellung muss das Fenster durch clicken auf den "Terminplan Rechnung"-Breadcrump neu geladen werden, damit der neue Datensatz angezeigt wird.
Nach einer Änderung werden alle betroffenen Rechnungskandidaten als "zu aktualisieren" markiert.',Updated=TO_TIMESTAMP('2024-08-27 15:51:15.577000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585421
;

-- Process: C_InvoiceSchedule_CreateOrUpdate(de.metas.ui.web.invoice.process.C_InvoiceSchedule_CreateOrUpdate)
-- 2024-08-27T15:51:45.296Z
UPDATE AD_Process_Trl SET Description='Allows you to create a new schedule or change the values that are relevant for invoice candidates. 
After creating a new schedule, the window must be reloaded by clicking on the "Invoice schedule" breadcrumb to display the new data record. 
After a change, all affected invoice candidates are marked as "to be updated".',Updated=TO_TIMESTAMP('2024-08-27 15:51:45.295000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585421
;

-- Value: C_InvoiceSchedule_CreateOrUpdate
-- Classname: de.metas.ui.web.invoice.process.C_InvoiceSchedule_CreateOrUpdate
-- 2024-08-27T15:58:23.364Z
UPDATE AD_Process SET AllowProcessReRun='N', IsUseBPartnerLanguage='N',Updated=TO_TIMESTAMP('2024-08-27 15:58:23.360000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585421
;

-- Process: C_InvoiceSchedule_CreateOrUpdate(de.metas.ui.web.invoice.process.C_InvoiceSchedule_CreateOrUpdate)
-- Table: C_InvoiceSchedule
-- EntityType: D
-- 2024-08-27T15:58:42.129Z
UPDATE AD_Table_Process SET WEBUI_ViewQuickAction='N', WEBUI_ViewQuickAction_Default='N',Updated=TO_TIMESTAMP('2024-08-27 15:58:42.129000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Table_Process_ID=541520
;

-- Process: C_InvoiceSchedule_CreateOrUpdate(de.metas.ui.web.invoice.process.C_InvoiceSchedule_CreateOrUpdate)
-- Table: C_InvoiceSchedule
-- EntityType: D
-- 2024-08-27T15:59:02.479Z
UPDATE AD_Table_Process SET WEBUI_DocumentAction='N', WEBUI_ViewQuickAction='Y', WEBUI_ViewQuickAction_Default='Y',Updated=TO_TIMESTAMP('2024-08-27 15:59:02.479000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Table_Process_ID=541520
;

-- Tab: Terminplan Rechnung -> Terminplan Rechnung
-- Table: C_InvoiceSchedule
-- Tab: Terminplan Rechnung(147,D) -> Terminplan Rechnung
-- Table: C_InvoiceSchedule
-- 2024-08-27T16:34:02.122Z
UPDATE AD_Tab SET IsInsertRecord='N',Updated=TO_TIMESTAMP('2024-08-27 16:34:02.121000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Tab_ID=193
;

-- Window: Terminplan Rechnung, InternalName=147 (Todo: Set Internal Name for UI testing)
-- Window: Terminplan Rechnung, InternalName=147 (Todo: Set Internal Name for UI testing)
-- 2024-08-27T16:34:13.263Z
UPDATE AD_Window SET IsBetaFunctionality='N',Updated=TO_TIMESTAMP('2024-08-27 16:34:13.260000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Window_ID=147
;

