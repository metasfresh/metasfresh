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

-- Name: ModCntr_Log
-- 2023-07-03T16:20:31.571232100Z
INSERT INTO AD_Reference (AD_Client_ID, AD_Org_ID, AD_Reference_ID, Created, CreatedBy, EntityType, IsActive, IsOrderByValue, Name, Updated, UpdatedBy, ValidationType)
VALUES (0, 0, 541775, TO_TIMESTAMP('2023-07-03 18:20:31.454', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'de.metas.contracts', 'Y', 'N', 'ModCntr_Log', TO_TIMESTAMP('2023-07-03 18:20:31.454', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'T')
;

-- 2023-07-03T16:20:31.572294900Z
INSERT INTO AD_Reference_Trl (AD_Language, AD_Reference_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       t.AD_Reference_ID,
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
     AD_Reference t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Reference_ID = 541775
  AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Reference_ID = t.AD_Reference_ID)
;

-- Table Validation - SQL not generated (not possible), because of missing ad_ref_table_id column
INSERT INTO ad_ref_table (ad_reference_id, ad_client_id, ad_org_id, createdby, updatedby, ad_table_id, ad_key, ad_window_id)
VALUES (541775, 0, 0, 100, 100, 542338, 586758, 541711)
;

-- Reference: ModCntr_Log
-- Table: ModCntr_Log
-- Key: ModCntr_Log.ModCntr_Log_ID
-- 2023-07-03T17:59:28.710432900Z
UPDATE AD_Ref_Table
SET EntityType='de.metas.contracts', Updated=TO_TIMESTAMP('2023-07-03 19:59:28.709', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Reference_ID = 541775
;

-- 2023-07-03T18:08:21.049132900Z
INSERT INTO AD_RelationType (AD_Client_ID, AD_Org_ID, AD_Reference_Target_ID, AD_RelationType_ID, Created, CreatedBy, EntityType, IsActive, IsTableRecordIdTarget, Name, Updated, UpdatedBy)
VALUES (0, 0, 541775, 540407, TO_TIMESTAMP('2023-07-03 20:08:20.881', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'de.metas.contracts', 'Y', 'Y', 'ModCntr_Log_TableRecordIdTarget', TO_TIMESTAMP('2023-07-03 20:08:20.881', 'YYYY-MM-DD HH24:MI:SS.US'), 100)
;

