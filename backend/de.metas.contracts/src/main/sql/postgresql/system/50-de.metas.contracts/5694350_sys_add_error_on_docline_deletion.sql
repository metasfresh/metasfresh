/*
 * #%L
 * de.metas.contracts
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

-- Value: document_line_deletion_error_because_of_related_module_contract_log
-- 2023-07-04T09:21:44.709275400Z
INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Updated, UpdatedBy, Value)
VALUES (0, 545282, 0, TO_TIMESTAMP('2023-07-04 11:21:44.573', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'D', 'Y', 'Diese Dokumentzeile kann nicht gelöscht werden, da zu dieser Zeile ein Vertragsbaustein Log existiert.', 'E', TO_TIMESTAMP('2023-07-04 11:21:44.573', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'documentLineDeletionErrorBecauseOfRelatedModuleContractLog')
;

-- 2023-07-04T09:21:44.713457500Z
INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       t.AD_Message_ID,
       t.MsgText,
       t.MsgTip,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy,
       'Y'
FROM AD_Language l,
     AD_Message t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Message_ID = 545282
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = t.AD_Message_ID)
;

-- Value: document_line_deletion_error_because_of_related_module_contract_log
-- 2023-07-04T09:21:54.268807Z
UPDATE AD_Message_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-07-04 11:21:54.268', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Language = 'de_CH'
  AND AD_Message_ID = 545282
;

-- Value: document_line_deletion_error_because_of_related_module_contract_log
-- 2023-07-04T09:21:55.034720900Z
UPDATE AD_Message_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-07-04 11:21:55.034', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Language = 'de_DE'
  AND AD_Message_ID = 545282
;

-- Value: document_line_deletion_error_because_of_related_module_contract_log
-- 2023-07-04T09:22:01.524443500Z
UPDATE AD_Message_Trl
SET IsTranslated='Y', MsgText='The document line can not be deleted because a module contract is linked to it.', Updated=TO_TIMESTAMP('2023-07-04 11:22:01.524', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Language = 'en_US'
  AND AD_Message_ID = 545282
;

