/*
 * #%L
 * de.metas.serviceprovider.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

SELECT backup_table('ad_ui_element');
DELETE FROM ad_ui_element
WHERE ad_field_id IN (SELECT ad_field_id
                      FROM ad_field f
                               JOIN ad_column c ON (f.ad_column_id = c.ad_column_id)
                      WHERE c.columnname = 'ExternalSystem'
                        AND c.ad_table_id = get_table_id('S_ExternalReference'))
;

SELECT backup_table('ad_field');
DELETE FROM ad_field
WHERE ad_field_id IN (SELECT ad_field_id
                         FROM ad_field f
                                  JOIN ad_column c ON (f.ad_column_id = c.ad_column_id)
                         WHERE c.columnname = 'ExternalSystem'
                           AND c.ad_table_id = get_table_id('S_ExternalReference'))
;

SELECT backup_table('ad_column');
DELETE FROM ad_column
       WHERE columnname = 'ExternalSystem'
         AND ad_table_id = get_table_id('S_ExternalReference')
;

ALTER TABLE S_ExternalReference DROP COLUMN ExternalSystem
;

