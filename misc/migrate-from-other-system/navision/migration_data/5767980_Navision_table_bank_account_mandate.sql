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
    'INSERT INTO migration_data."Navision$Bank Account Mandate" (' +
    '"timestamp", "Mandate ID", "Account Type", "Account No_", "Name", "Code", "IBAN", "SWIFT Code", "Mandate Date", "Mandate Type", "Mandate Frequency", "Mandate On Behalf of Owner", "Mandate Is Electronic", "Mandate Last Used", "Mandate Status", "Maximum Amount", "Maximum Usage", "Valid from Date", "Valid to Date", "Usage", "Single Payment", "OrgnlMndtId", "Alt_ Creditor Identifier"' +
    ') VALUES (' +

    -- "timestamp" BYTEA NOT NULL
    'E''\\x' + SUBSTRING(sys.fn_varbintohexstr(timestamp), 3, DATALENGTH(timestamp) * 2) + '''' + ', ' +

    -- "Mandate ID" VARCHAR(35) NOT NULL
    '''' + REPLACE(REPLACE([Mandate ID], '''', ''''''), '\', '\\') + '''' + ', ' +

    -- "Account Type" INTEGER NOT NULL
    CAST([Account Type] AS NVARCHAR(MAX)) + ', ' +

    -- "Account No_" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Account No_], '''', ''''''), '\', '\\') + '''' + ', ' +

    -- "Name" VARCHAR(50) NOT NULL
    '''' + REPLACE(REPLACE(Name, '''', ''''''), '\', '\\') + '''' + ', ' +

    -- "Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE(Code, '''', ''''''), '\', '\\') + '''' + ', ' +

    -- "IBAN" VARCHAR(50) NOT NULL
    '''' + REPLACE(REPLACE(IBAN, '''', ''''''), '\', '\\') + '''' + ', ' +

    -- "SWIFT Code" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([SWIFT Code], '''', ''''''), '\', '\\') + '''' + ', ' +

    -- "Mandate Date" TIMESTAMP NOT NULL
    '''' + CONVERT(NVARCHAR(MAX), [Mandate Date], 126) + '''' + ', ' +

    -- "Mandate Type" INTEGER NOT NULL
    CAST([Mandate Type] AS NVARCHAR(MAX)) + ', ' +

    -- "Mandate Frequency" INTEGER NOT NULL
    CAST([Mandate Frequency] AS NVARCHAR(MAX)) + ', ' +

    -- "Mandate On Behalf of Owner" BOOLEAN NOT NULL
    CASE WHEN [Mandate On Behalf of Owner] = 1 THEN 'TRUE' ELSE 'FALSE' END + ', ' +

    -- "Mandate Is Electronic" BOOLEAN NOT NULL
    CASE WHEN [Mandate Is Electronic] = 1 THEN 'TRUE' ELSE 'FALSE' END + ', ' +

    -- "Mandate Last Used" TIMESTAMP NOT NULL
    '''' + CONVERT(NVARCHAR(MAX), [Mandate Last Used], 126) + '''' + ', ' +

    -- "Mandate Status" INTEGER NOT NULL
    CAST([Mandate Status] AS NVARCHAR(MAX)) + ', ' +

    -- "Maximum Amount" NUMERIC(38, 20) NOT NULL
    CAST([Maximum Amount] AS NVARCHAR(MAX)) + ', ' +

    -- "Maximum Usage" INTEGER NOT NULL
    CAST([Maximum Usage] AS NVARCHAR(MAX)) + ', ' +

    -- "Valid from Date" TIMESTAMP NOT NULL
    '''' + CONVERT(NVARCHAR(MAX), [Valid from Date], 126) + '''' + ', ' +

    -- "Valid to Date" TIMESTAMP NOT NULL
    '''' + CONVERT(NVARCHAR(MAX), [Valid to Date], 126) + '''' + ', ' +

    -- "Usage" INTEGER NOT NULL
    CAST(Usage AS NVARCHAR(MAX)) + ', ' +

    -- "Single Payment" BOOLEAN NOT NULL
    CASE WHEN [Single Payment] = 1 THEN 'TRUE' ELSE 'FALSE' END + ', ' +

    -- "OrgnlMndtId" VARCHAR(35) NOT NULL
    '''' + REPLACE(REPLACE(OrgnlMndtId, '''', ''''''), '\', '\\') + '''' + ', ' +

    -- "Alt_ Creditor Identifier" VARCHAR(38) NOT NULL (LAST COLUMN)
    '''' + REPLACE(REPLACE([Alt_ Creditor Identifier], '''', ''''''), '\', '\\') + '''' +
    ');'
FROM
    [Navision$Bank Account Mandate];
*/

CREATE TABLE migration_data."Navision$Bank Account Mandate" (
    "timestamp" BYTEA NOT NULL,
    "Mandate ID" VARCHAR(35) NOT NULL PRIMARY KEY,
    "Account Type" INTEGER NOT NULL,
    "Account No_" VARCHAR(20) NOT NULL,
    "Name" VARCHAR(50) NOT NULL,
    "Code" VARCHAR(10) NOT NULL,
    "IBAN" VARCHAR(50) NOT NULL,
    "SWIFT Code" VARCHAR(20) NOT NULL,
    "Mandate Date" TIMESTAMP NOT NULL,
    "Mandate Type" INTEGER NOT NULL,
    "Mandate Frequency" INTEGER NOT NULL,
    "Mandate On Behalf of Owner" BOOLEAN NOT NULL,
    "Mandate Is Electronic" BOOLEAN NOT NULL,
    "Mandate Last Used" TIMESTAMP NOT NULL,
    "Mandate Status" INTEGER NOT NULL,
    "Maximum Amount" NUMERIC(38, 20) NOT NULL,
    "Maximum Usage" INTEGER NOT NULL,
    "Valid from Date" TIMESTAMP NOT NULL,
    "Valid to Date" TIMESTAMP NOT NULL,
    "Usage" INTEGER NOT NULL,
    "Single Payment" BOOLEAN NOT NULL,
    "OrgnlMndtId" VARCHAR(35) NOT NULL,
    "Alt_ Creditor Identifier" VARCHAR(38) NOT NULL
);

