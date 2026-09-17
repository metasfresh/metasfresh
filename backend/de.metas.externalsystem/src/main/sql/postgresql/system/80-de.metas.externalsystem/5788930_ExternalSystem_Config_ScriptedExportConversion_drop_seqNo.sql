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

SELECT backup_table('ad_ui_element')
;

DELETE FROM ad_ui_element ui
    USING ad_field f
        JOIN ad_column c ON c.ad_column_id = f.ad_column_id
WHERE ui.ad_field_id = f.ad_field_id
  AND c.columnname = 'SeqNo'
  AND c.ad_table_id = get_table_id('ExternalSystem_Config_ScriptedExportConversion')
;

SELECT backup_table('ad_field')
;

DELETE FROM ad_field f
    USING ad_column c
WHERE f.ad_column_id = c.ad_column_id
  AND c.columnname = 'SeqNo'
  AND c.ad_table_id = get_table_id('ExternalSystem_Config_ScriptedExportConversion')
;

SELECT backup_table('ad_column')
;

DELETE FROM ad_column
WHERE columnname = 'SeqNo'
  AND ad_table_id = get_table_id('ExternalSystem_Config_ScriptedExportConversion')
;

ALTER TABLE ExternalSystem_Config_ScriptedExportConversion
    DROP COLUMN IF EXISTS SeqNo
;
