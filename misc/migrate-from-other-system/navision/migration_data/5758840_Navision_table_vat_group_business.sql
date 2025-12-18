/*
 * #%L
 * work-metas
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

/*
SELECT
    'INSERT INTO migration_data."Navision$VAT Business Posting Group" (' +
    '"timestamp", "Code", "Description"' +
    ') VALUES (' +

        -- "timestamp" BYTEA NOT NULL
    'E''\\x' + SUBSTRING(sys.fn_varbintohexstr(timestamp), 3, DATALENGTH(timestamp) * 2) + '''' + ', ' +

        -- "Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE(Code, '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Description" VARCHAR(50) NOT NULL (LAST COLUMN, no comma after)
    '''' + REPLACE(REPLACE(Description, '''', ''''''), '\', '\\') + '''' +
    ');'
FROM
    [Navision$VAT Business Posting Group];
*/

CREATE TABLE migration_data."Navision$VAT Business Posting Group" (
    "timestamp" BYTEA NOT NULL,
    "Code" VARCHAR(10) PRIMARY KEY,
    "Description" VARCHAR(50) NOT NULL
);

