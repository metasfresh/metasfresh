DROP FUNCTION IF EXISTS backup_table(p_TableName text)
;

CREATE OR REPLACE FUNCTION backup_table(p_TableName text)
    RETURNS text
    LANGUAGE plpgsql
    VOLATILE
AS
$$
DECLARE
    v_backupTableName text;
    v_rowcount        numeric;
BEGIN
    v_backupTableName = 'backup.' || p_TableName || '_bkp_' || TO_CHAR(NOW(), 'YYYYMMDD_HH24MISS_MS');
    RAISE NOTICE 'Backup `%` to `%`...', p_TableName, v_backupTableName;

    EXECUTE 'CREATE TABLE ' || v_backupTableName || ' AS SELECT * FROM ' || p_TableName;
    GET DIAGNOSTICS v_rowcount = ROW_COUNT;
    RAISE NOTICE 'Backup done. % rows copied.', v_rowcount;

    RETURN v_backupTableName;
END
$$
;

/*
 * #%L
 * de.metas.adempiere.adempiere.migration-sql
 * %%
 * Copyright (C) 2022 metas GmbH
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

-- Usage example:
-- SELECT backup_table('C_BP_Group');
-- => select * from backup.C_BP_Group_bkp_20220303_084927_890