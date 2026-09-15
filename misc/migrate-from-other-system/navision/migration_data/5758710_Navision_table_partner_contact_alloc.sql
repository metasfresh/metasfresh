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
    'INSERT INTO migration_data."Navision$Contact Business Relation" (' +
    '"timestamp", "Contact No_", "Business Relation Code", "Link to Table", "No_"' +
    ') VALUES (' +

        -- "timestamp" BYTEA NOT NULL
    'E''\\x' + SUBSTRING(sys.fn_varbintohexstr(timestamp), 3, DATALENGTH(timestamp) * 2) + '''' + ', ' +

        -- "Contact No_" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Contact No_], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Business Relation Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Business Relation Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Link to Table" INTEGER NOT NULL
    CAST([Link to Table] AS NVARCHAR(MAX)) + ', ' +

        -- "No_" VARCHAR(20) NOT NULL (LAST COLUMN, no comma after)
    '''' + REPLACE(REPLACE(No_, '''', ''''''), '\', '\\') + '''' +
    ');'
FROM
    [Navision$Contact Business Relation];
*/

CREATE TABLE migration_data."Navision$Contact Business Relation" (
    "timestamp" BYTEA NOT NULL,
    "Contact No_" VARCHAR(20) NOT NULL,
    "Business Relation Code" VARCHAR(10) NOT NULL,
    "Link to Table" INTEGER NOT NULL,
    "No_" VARCHAR(20) NOT NULL,
    PRIMARY KEY ("Contact No_", "Business Relation Code")
);

