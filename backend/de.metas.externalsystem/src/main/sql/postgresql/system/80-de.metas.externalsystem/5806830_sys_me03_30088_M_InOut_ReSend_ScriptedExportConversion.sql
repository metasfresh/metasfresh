/*
 * #%L
 * de.metas.externalsystem
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

-- me03 30088: EPCIS Error-Handling & Retry — Phase 5.1
-- Per-record "Re-send EPCIS export" AD process on M_InOut.
-- Re-triggers the scripted-export-conversion for any config with a non-Sent latest attempt.
--
-- IDs allocated from idserver.metas.de on 2026-06-09:
--   AD_Process_ID     585633   (M_InOut_ReSend_ScriptedExportConversion)
--   AD_Table_Process  541648   (M_InOut table binding)

-- 1) AD_Process -----------------------------------------------------------------------
INSERT INTO AD_Process
    (AD_Process_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Value, Name,
     Classname,
     IsReport, IsFormatExcelFile, EntityType,
     AccessLevel, Type, ShowHelp, IsBetaFunctionality)
VALUES
    (585633 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-09 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-09 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'M_InOut_ReSend_ScriptedExportConversion',
     'EPCIS-Export erneut senden (Scripted Export Conversion)',
     'de.metas.externalsystem.scriptedexportconversion.process.M_InOut_ReSend_ScriptedExportConversion',
     'N', 'Y', 'de.metas.externalsystem',
     '3', 'Java', 'Y', 'N')
;

-- 2) AD_Process_Trl skeleton -----------------------------------------------------------
INSERT INTO AD_Process_Trl
    (AD_Language, AD_Process_ID, Description, Help, Name,
     IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       t.AD_Process_ID,
       t.Description,
       t.Help,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.CreatedBy,
       t.Updated,
       t.UpdatedBy,
       'Y'
FROM AD_Language l,
     AD_Process t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Process_ID = 585633
  AND NOT EXISTS (SELECT 1
                  FROM AD_Process_Trl tt
                  WHERE tt.AD_Language = l.AD_Language
                    AND tt.AD_Process_ID = t.AD_Process_ID)
;

-- 3) AD_Process_Trl: set en_US and German translations --------------------------------
UPDATE AD_Process_Trl
SET Name         = 'Re-send EPCIS export (Scripted Export Conversion)',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-06-09 10:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Language = 'en_US'
  AND AD_Process_ID = 585633
;

UPDATE AD_Process_Trl
SET Name         = 'EPCIS-Export erneut senden (Scripted Export Conversion)',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-06-09 10:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Language IN ('de_CH', 'de_DE')
  AND AD_Process_ID = 585633
;

-- 4) AD_Table_Process: bind to M_InOut (AD_Table_ID=319) as a document action --------
INSERT INTO AD_Table_Process
    (AD_Client_ID, AD_Org_ID,
     AD_Process_ID, AD_Table_ID, AD_Table_Process_ID,
     Created, CreatedBy,
     EntityType, IsActive,
     Updated, UpdatedBy,
     WEBUI_DocumentAction, WEBUI_IncludedTabTopAction, WEBUI_ViewAction,
     WEBUI_ViewQuickAction, WEBUI_ViewQuickAction_Default)
VALUES
    (0, 0,
     585633 /*From ID Server*/, 319 /*M_InOut*/, 541648 /*From ID Server*/,
     TO_TIMESTAMP('2026-06-09 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'de.metas.externalsystem', 'Y',
     TO_TIMESTAMP('2026-06-09 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Y', 'N', 'Y',
     'N', 'N')
;
