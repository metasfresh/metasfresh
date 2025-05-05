/*
 * #%L
 * metasfresh-material-cockpit
 * %%
 * Copyright (C) 2023 metas GmbH
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

-- Value: de.metas.ui.web.material.cockpit.process.MD_Cockpit_SetProcurementStatus
-- Classname: de.metas.ui.web.material.cockpit.process.MD_Cockpit_SetProcurementStatus
-- 2023-12-07T10:51:06.690Z
INSERT INTO AD_Process (AccessLevel, AD_Client_ID, AD_Org_ID, AD_Process_ID, AllowProcessReRun, Classname, CopyFromProcess, Created, CreatedBy, EntityType, IsActive, IsApplySecuritySettings, IsBetaFunctionality, IsDirectPrint, IsFormatExcelFile, IsLogWarning, IsNotifyUserAfterExecution, IsOneInstanceOnly, IsReport, IsTranslateExcelHeaders, IsUseBPartnerLanguage, LockWaitTimeout, Name,
                        PostgrestResponseFormat, RefreshAllAfterExecution, ShowHelp, SpreadsheetFormat, Type, Updated, UpdatedBy, Value)
VALUES ('3', 0, 0, 585342, 'Y', 'de.metas.ui.web.material.cockpit.process.MD_Cockpit_SetProcurementStatus', 'N', TO_TIMESTAMP('2023-12-07 11:51:06', 'YYYY-MM-DD HH24:MI:SS'), 100, 'de.metas.material.cockpit', 'Y', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'Y', 'Y', 0, 'Beschaffungsstatus setzen', 'json', 'N', 'N', 'xls', 'Java', TO_TIMESTAMP('2023-12-07 11:51:06', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'de.metas.ui.web.material.cockpit.process.MD_Cockpit_SetProcurementStatus')
;

-- 2023-12-07T10:51:06.700Z
INSERT INTO AD_Process_Trl (AD_Language, AD_Process_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       t.AD_Process_ID,
       t.Description,
       t.Help,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy,
       'Y'
FROM AD_Language l,
     AD_Process t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y')
  AND t.AD_Process_ID = 585342
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_ID = t.AD_Process_ID)
;

-- Process: de.metas.ui.web.material.cockpit.process.MD_Cockpit_SetProcurementStatus(de.metas.ui.web.material.cockpit.process.MD_Cockpit_SetProcurementStatus)
-- 2023-12-07T10:51:13.348Z
UPDATE AD_Process_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-12-07 11:51:13', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'de_DE'
  AND AD_Process_ID = 585342
;

-- Process: de.metas.ui.web.material.cockpit.process.MD_Cockpit_SetProcurementStatus(de.metas.ui.web.material.cockpit.process.MD_Cockpit_SetProcurementStatus)
-- 2023-12-07T10:51:14.119Z
UPDATE AD_Process_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-12-07 11:51:14', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'de_CH'
  AND AD_Process_ID = 585342
;

-- Process: de.metas.ui.web.material.cockpit.process.MD_Cockpit_SetProcurementStatus(de.metas.ui.web.material.cockpit.process.MD_Cockpit_SetProcurementStatus)
-- 2023-12-07T10:51:27.081Z
UPDATE AD_Process_Trl
SET IsTranslated='Y', Name='Set Procurement Status', Updated=TO_TIMESTAMP('2023-12-07 11:51:27', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'en_US'
  AND AD_Process_ID = 585342
;

-- Process: de.metas.ui.web.material.cockpit.process.MD_Cockpit_SetProcurementStatus(de.metas.ui.web.material.cockpit.process.MD_Cockpit_SetProcurementStatus)
-- ParameterName: ProcurementStatus
-- 2023-12-07T11:30:32.756Z
INSERT INTO AD_Process_Para (AD_Client_ID, AD_Element_ID, AD_Org_ID, AD_Process_ID, AD_Process_Para_ID, AD_Reference_ID, AD_Reference_Value_ID, ColumnName, Created, CreatedBy, EntityType, FieldLength, IsActive, IsAutocomplete, IsCentrallyMaintained, IsEncrypted, IsMandatory, IsRange, Name, SeqNo, Updated, UpdatedBy)
VALUES (0, 582840, 0, 585342, 542761, 17, 541842, 'ProcurementStatus', TO_TIMESTAMP('2023-12-07 12:30:32', 'YYYY-MM-DD HH24:MI:SS'), 100, 'de.metas.material.cockpit', 0, 'Y', 'N', 'Y', 'N', 'N', 'N', 'Beschaffungsstatus', 10, TO_TIMESTAMP('2023-12-07 12:30:32', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2023-12-07T11:30:32.764Z
INSERT INTO AD_Process_Para_Trl (AD_Language, AD_Process_Para_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       t.AD_Process_Para_ID,
       t.Description,
       t.Help,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy,
       'Y'
FROM AD_Language l,
     AD_Process_Para t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y')
  AND t.AD_Process_Para_ID = 542761
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_Para_ID = t.AD_Process_Para_ID)
;

