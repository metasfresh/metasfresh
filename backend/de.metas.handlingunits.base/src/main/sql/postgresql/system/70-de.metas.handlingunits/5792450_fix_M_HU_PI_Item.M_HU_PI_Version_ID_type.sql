/*
 * #%L
 * de.metas.handlingunits.base
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

-- Table: C_Invoice_Line_Alloc
-- 2026-03-06T11:08:28.516Z
UPDATE AD_Table
SET PO_Window_ID=540983, Updated=TO_TIMESTAMP('2026-03-06 11:08:28.405000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Table_ID = 540321
;

-- Name: M_HU_PI_Version
-- 2026-03-06T12:18:02.784Z
INSERT INTO AD_Reference (AD_Client_ID, AD_Org_ID, AD_Reference_ID, Created, CreatedBy, EntityType, IsActive, IsOrderByValue, Name, Updated, UpdatedBy, ValidationType)
VALUES (0, 0, 542071, TO_TIMESTAMP('2026-03-06 12:18:02.378000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 'D', 'Y', 'N', 'M_HU_PI_Version', TO_TIMESTAMP('2026-03-06 12:18:02.378000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 'T')
;

-- 2026-03-06T12:18:02.832Z
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
  AND t.AD_Reference_ID = 542071
  AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Reference_ID = t.AD_Reference_ID)
;

-- Reference: M_HU_PI_Version
-- Table: M_HU_PI_Version
-- Key: M_HU_PI_Version.M_HU_PI_Version_ID
-- 2026-03-06T12:20:48.093Z
INSERT INTO AD_Ref_Table (AD_Client_ID, AD_Key, AD_Org_ID, AD_Reference_ID, AD_Table_ID, AD_Window_ID, Created, CreatedBy, EntityType, IsActive, IsValueDisplayed, ShowInactiveValues, Updated, UpdatedBy)
VALUES (0, 549203, 0, 542071, 540510, 540344, TO_TIMESTAMP('2026-03-06 12:20:47.847000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 'D', 'Y', 'N', 'N', TO_TIMESTAMP('2026-03-06 12:20:47.847000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100)
;

-- Use the M_HU_PI_Version reference to ensure a proper type and window are found by de.metas.document.references.zoom_into.DefaultGenericZoomIntoTableInfoRepository.getParentLink_SingleParentColumn
-- Column: M_HU_PI_Item.M_HU_PI_Version_ID
-- 2026-03-06T12:21:22.146Z
UPDATE AD_Column
SET AD_Reference_ID=18, AD_Reference_Value_ID=542071, IsUpdateable='N', Updated=TO_TIMESTAMP('2026-03-06 12:21:22.146000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Column_ID = 549287
;

