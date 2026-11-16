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

-- ReSend scripted export — add the IsOnlyNotSentSuccessfully parameter
-- to both M_InOut_ReSend_ScriptedExportConversion (585633) and
-- C_Invoice_ReSend_ScriptedExportConversion (585637).
--
-- IDs allocated from idserver.metas.de on 2026-06-16:
--   AD_Element_ID          585004   (IsOnlyNotSentSuccessfully)
--   AD_Process_Para_ID     543252   (M_InOut process, AD_Process_ID=585633)
--   AD_Process_Para_ID     543253   (C_Invoice process, AD_Process_ID=585637)

-- 1) AD_Element ----------------------------------------------------------------------
INSERT INTO AD_Element
    (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     ColumnName, EntityType, Name, PrintName)
VALUES
    (585004 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-16 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-16 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'IsOnlyNotSentSuccessfully',
     'de.metas.externalsystem',
     'Nur noch nicht erfolgreich gesendete',
     'Nur noch nicht erfolgreich gesendete')
;

-- 2) AD_Element_Trl: German translations --------------------------------------------
INSERT INTO AD_Element_Trl
    (AD_Language, AD_Element_ID, Name, PrintName,
     IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       585004,
       CASE l.AD_Language
           WHEN 'de_DE' THEN 'Nur noch nicht erfolgreich gesendete'
           WHEN 'de_CH' THEN 'Nur noch nicht erfolgreich gesendete'
           ELSE e.Name
       END,
       CASE l.AD_Language
           WHEN 'de_DE' THEN 'Nur noch nicht erfolgreich gesendete'
           WHEN 'de_CH' THEN 'Nur noch nicht erfolgreich gesendete'
           ELSE e.PrintName
       END,
       CASE WHEN l.AD_Language IN ('de_DE', 'de_CH') THEN 'Y' ELSE 'N' END,
       0, 0,
       TO_TIMESTAMP('2026-06-16 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-06-16 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
       'Y'
FROM AD_Language l,
     AD_Element e
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND e.AD_Element_ID = 585004
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = 585004)
;

-- AD_Element_Trl: en_US translation
UPDATE AD_Element_Trl
SET Name         = 'Only not yet successfully sent',
    PrintName    = 'Only not yet successfully sent',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-06-16 10:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Language = 'en_US'
  AND AD_Element_ID = 585004
;

-- 3) AD_Process_Para for M_InOut_ReSend_ScriptedExportConversion (AD_Process_ID=585633) --------
INSERT INTO AD_Process_Para
    (AD_Process_Para_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     AD_Process_ID, AD_Element_ID,
     ColumnName, Name,
     AD_Reference_ID, FieldLength, IsMandatory, DefaultValue, SeqNo,
     EntityType)
VALUES
    (543252 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-16 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-16 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
     585633,
     585004,
     'IsOnlyNotSentSuccessfully',
     'Only not yet successfully sent',
     20 /*YesNo*/, 1, 'N', 'Y' /*DefaultValue=Y: default to re-sending ONLY the not-yet-successfully-sent records (safe default; uncheck to force-resend all selected)*/, 10,
     'de.metas.externalsystem')
;

-- AD_Process_Para_Trl skeleton for 543252
INSERT INTO AD_Process_Para_Trl
    (AD_Language, AD_Process_Para_ID, Name, Description, Help,
     IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       543252,
       p.Name,
       p.Description,
       p.Help,
       'N',
       0, 0,
       TO_TIMESTAMP('2026-06-16 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-06-16 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
       'Y'
FROM AD_Language l,
     AD_Process_Para p
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND p.AD_Process_Para_ID = 543252
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_Para_ID = 543252)
;

-- German translation for 543252 (fr_CH adopts German text verbatim with IsTranslated='N')
UPDATE AD_Process_Para_Trl
SET Name         = 'Nur noch nicht erfolgreich gesendete',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-06-16 10:00:02', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Language IN ('de_DE', 'de_CH')
  AND AD_Process_Para_ID = 543252
;
UPDATE AD_Process_Para_Trl
SET Name         = 'Nur noch nicht erfolgreich gesendete',
    IsTranslated = 'N',
    Updated      = TO_TIMESTAMP('2026-06-16 10:00:02', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Language = 'fr_CH'
  AND AD_Process_Para_ID = 543252
;

-- 4) AD_Process_Para for C_Invoice_ReSend_ScriptedExportConversion (AD_Process_ID=585637) -------
INSERT INTO AD_Process_Para
    (AD_Process_Para_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     AD_Process_ID, AD_Element_ID,
     ColumnName, Name,
     AD_Reference_ID, FieldLength, IsMandatory, DefaultValue, SeqNo,
     EntityType)
VALUES
    (543253 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-16 10:00:03', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-16 10:00:03', 'YYYY-MM-DD HH24:MI:SS'), 100,
     585637,
     585004,
     'IsOnlyNotSentSuccessfully',
     'Only not yet successfully sent',
     20 /*YesNo*/, 1, 'N', 'Y' /*DefaultValue=Y: default to re-sending ONLY the not-yet-successfully-sent records (safe default; uncheck to force-resend all selected)*/, 10,
     'de.metas.externalsystem')
;

-- AD_Process_Para_Trl skeleton for 543253
INSERT INTO AD_Process_Para_Trl
    (AD_Language, AD_Process_Para_ID, Name, Description, Help,
     IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       543253,
       p.Name,
       p.Description,
       p.Help,
       'N',
       0, 0,
       TO_TIMESTAMP('2026-06-16 10:00:03', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-06-16 10:00:03', 'YYYY-MM-DD HH24:MI:SS'), 100,
       'Y'
FROM AD_Language l,
     AD_Process_Para p
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND p.AD_Process_Para_ID = 543253
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_Para_ID = 543253)
;

-- German translation for 543253 (fr_CH adopts German text verbatim with IsTranslated='N')
UPDATE AD_Process_Para_Trl
SET Name         = 'Nur noch nicht erfolgreich gesendete',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-06-16 10:00:04', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Language IN ('de_DE', 'de_CH')
  AND AD_Process_Para_ID = 543253
;
UPDATE AD_Process_Para_Trl
SET Name         = 'Nur noch nicht erfolgreich gesendete',
    IsTranslated = 'N',
    Updated      = TO_TIMESTAMP('2026-06-16 10:00:04', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Language = 'fr_CH'
  AND AD_Process_Para_ID = 543253
;

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585004);

