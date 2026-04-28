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
    'INSERT INTO migration_data."Navision$Payment Terms" (' +
    '"timestamp", "Code", "Due Date Calculation", "Discount Date Calculation", "Discount _", "Description", "Calc_ Pmt_ Disc_ on Cr_ Memos", "Description 2", "Immediate Annuity Credit Disc_"' +
    ') VALUES (' +

        -- "timestamp" BYTEA NOT NULL
    'E''\\x' + SUBSTRING(sys.fn_varbintohexstr(timestamp), 3, DATALENGTH(timestamp) * 2) + '''' + ', ' +

        -- "Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE(Code, '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Due Date Calculation" VARCHAR(32) NOT NULL
    '''' + REPLACE(REPLACE(REPLACE([Due Date Calculation], '''', ''''''), '\', '\\'), CHAR(2), '') + '''' + ', ' +

        -- "Discount Date Calculation" VARCHAR(32) NOT NULL
    '''' + REPLACE(REPLACE(REPLACE([Discount Date Calculation], '''', ''''''), '\', '\\'), CHAR(2), '') + '''' + ', ' +

        -- "Discount _" NUMERIC(38, 20) NOT NULL
    CAST([Discount _] AS NVARCHAR(MAX)) + ', ' +

        -- "Description" VARCHAR(50) NOT NULL
    '''' + REPLACE(REPLACE(Description, '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Calc_ Pmt_ Disc_ on Cr_ Memos" BOOLEAN NOT NULL
    CASE WHEN [Calc_ Pmt_ Disc_ on Cr_ Memos] = 1 THEN 'TRUE' ELSE 'FALSE' END + ', ' +

        -- "Description 2" VARCHAR(100) NOT NULL
    '''' + REPLACE(REPLACE([Description 2], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Immediate Annuity Credit Disc_" BOOLEAN NOT NULL (LAST COLUMN, no comma after)
    CASE WHEN [Immediate Annuity Credit Disc_] = 1 THEN 'TRUE' ELSE 'FALSE' END +
    ');'
FROM
    [Navision$Payment Terms];
*/

CREATE TABLE migration_data."Navision$Payment Terms" (
    "timestamp" BYTEA NOT NULL,
    "Code" VARCHAR(10) PRIMARY KEY,
    "Due Date Calculation" VARCHAR(32) NOT NULL,
    "Discount Date Calculation" VARCHAR(32) NOT NULL,
    "Discount _" NUMERIC(38, 20) NOT NULL,
    "Description" VARCHAR(50) NOT NULL,
    "Calc_ Pmt_ Disc_ on Cr_ Memos" BOOLEAN NOT NULL,
    "Description 2" VARCHAR(100) NOT NULL,
    "Immediate Annuity Credit Disc_" BOOLEAN NOT NULL
);
