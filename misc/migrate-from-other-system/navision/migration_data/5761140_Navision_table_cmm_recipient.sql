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
    'INSERT INTO migration_data."Navision$CMM Recipient" (' +
    '"timestamp", "Source Type", "Source Code", "Document Type", "Recipient Type", ' +
    '"Address Source Type", "Custom Address", "Contact No_", "Dispatch Type", "Valid", ' +
    '"Created", "Created By", "Modified", "Modified By"' +
    ') VALUES (' +

    -- "timestamp" BYTEA NOT NULL
    'E''\\x' + SUBSTRING(sys.fn_varbintohexstr(timestamp), 3, DATALENGTH(timestamp) * 2) + '''' + ', ' +

    -- "Source Type" INTEGER NOT NULL
    CAST([Source Type] AS NVARCHAR(MAX)) + ', ' +

    -- "Source Code" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Source Code], '''', ''''''), '\', '\\') + '''' + ', ' +

    -- "Document Type" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Document Type], '''', ''''''), '\', '\\') + '''' + ', ' +

    -- "Recipient Type" INTEGER NOT NULL
    CAST([Recipient Type] AS NVARCHAR(MAX)) + ', ' +

    -- "Address Source Type" INTEGER NOT NULL
    CAST([Address Source Type] AS NVARCHAR(MAX)) + ', ' +

    -- "Custom Address" VARCHAR(80) NOT NULL
    '''' + REPLACE(REPLACE([Custom Address], '''', ''''''), '\', '\\') + '''' + ', ' +

    -- "Contact No_" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Contact No_], '''', ''''''), '\', '\\') + '''' + ', ' +

    -- "Dispatch Type" INTEGER NOT NULL
    CAST([Dispatch Type] AS NVARCHAR(MAX)) + ', ' +

    -- "Valid" INTEGER NOT NULL
    CAST(Valid AS NVARCHAR(MAX)) + ', ' +

    -- "Created" TIMESTAMP NOT NULL
    '''' + CONVERT(NVARCHAR(MAX), Created, 126) + '''' + ', ' +

    -- "Created By" VARCHAR(50) NOT NULL
    '''' + REPLACE(REPLACE([Created By], '''', ''''''), '\', '\\') + '''' + ', ' +

    -- "Modified" TIMESTAMP NOT NULL
    '''' + CONVERT(NVARCHAR(MAX), Modified, 126) + '''' + ', ' +

    -- "Modified By" VARCHAR(50) NOT NULL (LAST COLUMN, no comma after)
    '''' + REPLACE(REPLACE([Modified By], '''', ''''''), '\', '\\') + '''' +
    ');'
FROM
    [Navision$CMM Recipient];
*/

CREATE TABLE migration_data."Navision$CMM Recipient" (
    "timestamp" BYTEA NOT NULL,
    "Source Type" INTEGER NOT NULL,
    "Source Code" VARCHAR(20) NOT NULL,
    "Document Type" VARCHAR(20) NOT NULL,
    "Recipient Type" INTEGER NOT NULL,
    "Address Source Type" INTEGER NOT NULL,
    "Custom Address" VARCHAR(80) NOT NULL,
    "Contact No_" VARCHAR(20) NOT NULL,
    "Dispatch Type" INTEGER NOT NULL,
    "Valid" INTEGER NOT NULL,
    "Created" TIMESTAMP NOT NULL,
    "Created By" VARCHAR(50) NOT NULL,
    "Modified" TIMESTAMP NOT NULL,
    "Modified By" VARCHAR(50) NOT NULL,
    PRIMARY KEY ("Source Type", "Source Code", "Document Type", "Recipient Type", "Address Source Type", "Custom Address", "Contact No_")
);
