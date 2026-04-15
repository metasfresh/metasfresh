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

-- ExternalSystem_Outbound_Endpoint: add ContentType as mandatory RefList column
-- Allows configuring the HTTP Content-Type header per endpoint (e.g. application/xml for EPCIS)

-- 1. Create AD_Reference (List) for ContentType values
INSERT INTO AD_Reference (AD_Reference_ID, AD_Client_ID, AD_Org_ID, IsActive,
                          Created, CreatedBy, Updated, UpdatedBy,
                          Name, Description, ValidationType, EntityType, IsOrderByValue)
VALUES (542065, 0, 0, 'Y',
        TO_TIMESTAMP('2026-03-04 10:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-04 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        'ExternalSystemOutboundEndpointContentType', 'HTTP Content-Type header for outbound endpoint', 'L', 'de.metas.externalsystem', 'N');

-- 2. Create AD_Ref_List entries
-- application/json (default)
INSERT INTO AD_Ref_List (AD_Ref_List_ID, AD_Client_ID, AD_Org_ID, IsActive,
                         Created, CreatedBy, Updated, UpdatedBy,
                         AD_Reference_ID, Value, ValueName, Name, EntityType)
VALUES (544153, 0, 0, 'Y',
        TO_TIMESTAMP('2026-03-04 10:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-04 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        542065, 'application/json', 'application_json', 'application/json', 'de.metas.externalsystem');

-- application/xml
INSERT INTO AD_Ref_List (AD_Ref_List_ID, AD_Client_ID, AD_Org_ID, IsActive,
                         Created, CreatedBy, Updated, UpdatedBy,
                         AD_Reference_ID, Value, ValueName, Name, EntityType)
VALUES (544154, 0, 0, 'Y',
        TO_TIMESTAMP('2026-03-04 10:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-04 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        542065, 'application/xml', 'application_xml', 'application/xml', 'de.metas.externalsystem');

-- text/xml
INSERT INTO AD_Ref_List (AD_Ref_List_ID, AD_Client_ID, AD_Org_ID, IsActive,
                         Created, CreatedBy, Updated, UpdatedBy,
                         AD_Reference_ID, Value, ValueName, Name, EntityType)
VALUES (544155, 0, 0, 'Y',
        TO_TIMESTAMP('2026-03-04 10:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-04 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        542065, 'text/xml', 'text_xml', 'text/xml', 'de.metas.externalsystem');

-- 3. AD_Column: change from String(10) to List(17) with reference, make mandatory
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Element_ID, AD_Table_ID, ColumnName, Name, Description,
                       Version, EntityType, AD_Reference_ID, AD_Reference_Value_ID,
                       IsMandatory, DefaultValue, IsUpdateable, IsIdentifier, IsKey, IsParent,
                       FieldLength, IsTranslated, IsSelectionColumn, IsAlwaysUpdateable,
                       PersonalDataCategory)
VALUES (592116, 0, 0, 'Y', TO_TIMESTAMP('2026-03-04 10:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-04 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        543391, 542551, 'ContentType', 'Content type', 'HTTP Content-Type header value (e.g. application/json, application/xml)',
        0, 'de.metas.externalsystem', 17, 542065,
        'Y', 'application/json', 'Y', 'N', 'N', 'N',
        40, 'N', 'N', 'N',
        'NP');

-- 4. Physical column + populate existing rows + make NOT NULL
SELECT db_alter_table('ExternalSystem_Outbound_Endpoint', 'ALTER TABLE public.ExternalSystem_Outbound_Endpoint ADD COLUMN IF NOT EXISTS ContentType VARCHAR(40)');

UPDATE ExternalSystem_Outbound_Endpoint SET ContentType = 'application/json' WHERE ContentType IS NULL;

INSERT INTO t_alter_column VALUES ('ExternalSystem_Outbound_Endpoint', 'ContentType', 'VARCHAR(40)', 'NOT NULL', 'application/json');

-- 5. AD_Field on the Outbound Endpoint window/tab
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Column_ID, AD_Tab_ID, Name, Description, EntityType,
                      IsDisplayed, IsReadOnly, IsSameLine, IsFieldOnly)
VALUES (774765, 0, 0, 'Y', TO_TIMESTAMP('2026-03-04 10:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-04 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        592116, 548506, 'Content type', 'HTTP Content-Type header value (e.g. application/json, application/xml)', 'de.metas.externalsystem',
        'Y', 'N', 'N', 'N');

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=774765 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Run mode: SWING_CLIENT

-- UI Element: Externer System-Ausgangsendpunkt(541967,de.metas.externalsystem) -> Externer System-Ausgangsendpunkt(548506,de.metas.externalsystem) -> main -> 10 -> main.Content type
-- Column: ExternalSystem_Outbound_Endpoint.ContentType
-- 2026-03-06T06:30:57.324Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,774765,0,548506,553738,648503,'F',TO_TIMESTAMP('2026-03-06 06:30:56.956000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Content type',45,0,0,TO_TIMESTAMP('2026-03-06 06:30:56.956000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
