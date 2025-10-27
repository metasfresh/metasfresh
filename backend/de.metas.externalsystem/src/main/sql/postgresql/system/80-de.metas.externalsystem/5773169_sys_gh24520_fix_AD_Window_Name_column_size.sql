/*
 * #%L
 * de.metas.externalsystem
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

-- On some DBs the script 5773170_sys_gh24520_ExternalSystem_ScriptedExportConversion.sql fails because AD_Window.Name is much smaller than AD_Window_Trl.Name.
-- We are addressing this problem in a later migration-script, but for thos DBs we need the fix already now
UPDATE ad_column
SET fieldlength = 600,
    updated = '2025-10-27 07:41',
    updatedby = 100
WHERE ad_column_id IN (
    SELECT c.ad_column_id
    FROM ad_column c
             JOIN ad_table t ON c.ad_table_id = t.ad_table_id
    WHERE t.tablename = 'AD_Window'
      AND c.columnname = 'Name'
);

select db_alter_table('AD_Window', 'ALTER TABLE AD_Window ALTER COLUMN Name TYPE VARCHAR(600)');