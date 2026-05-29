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
    'INSERT INTO migration_data."Navision$Product Group" (' +
    '"timestamp", "Item Category Code", "Code", "Description", "Warehouse Class Code"' +
    ') VALUES (' +

        -- "timestamp" BYTEA NOT NULL
    'E''\\x' + SUBSTRING(sys.fn_varbintohexstr(timestamp), 3, DATALENGTH(timestamp) * 2) + '''' + ', ' +

        -- "Item Category Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Item Category Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE(Code, '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Description" VARCHAR(50) NOT NULL
    '''' + REPLACE(REPLACE(Description, '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Warehouse Class Code" VARCHAR(10) NOT NULL (LAST COLUMN, no comma after)
    '''' + REPLACE(REPLACE([Warehouse Class Code], '''', ''''''), '\', '\\') + '''' +
    ');'
FROM
    [Navision$Product Group];
*/

CREATE TABLE migration_data."Navision$Product Group" (
    "timestamp" BYTEA NOT NULL,
    "Item Category Code" VARCHAR(10) NOT NULL,
    "Code" VARCHAR(10) NOT NULL,
    "Description" VARCHAR(50) NOT NULL,
    "Warehouse Class Code" VARCHAR(10) NOT NULL,
    PRIMARY KEY ("Item Category Code", "Code")
);
