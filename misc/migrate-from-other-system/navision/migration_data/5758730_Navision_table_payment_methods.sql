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
    'INSERT INTO migration_data."Navision$Payment Method" (' +
    '"timestamp", "Code", "Description", "Bal_ Account Type", "Bal_ Account No_", "Payment Processor", "Direct Debit", "Direct Debit Pmt_ Terms Code", "Pmt_ Export Line Definition", "Bank Data Conversion Pmt_ Type", "Default Payment Type Code", "Single Payment", "Default Pmt_ Bank Account No_", "Separate Pmt_ Proposal Head", "Vendor Purpose Text", "Customer Purpose Text", "Payment Note Purpose Text", "Vendor Purpose Text Header", "Purpose Text Footer", "Limit Payment Amount (LCY)", "Customer Purpose Text Header", "Limit Lines per Head", "Min_ Pos_ Payment Note"' +
    ') VALUES (' +

        -- "timestamp" BYTEA NOT NULL
    'E''\\x' + SUBSTRING(sys.fn_varbintohexstr(timestamp), 3, DATALENGTH(timestamp) * 2) + '''' + ', ' +

        -- "Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE(Code, '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Description" VARCHAR(50) NOT NULL
    '''' + REPLACE(REPLACE(Description, '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Bal_ Account Type" INTEGER NOT NULL
    CAST([Bal_ Account Type] AS NVARCHAR(MAX)) + ', ' +

        -- "Bal_ Account No_" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Bal_ Account No_], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Payment Processor" INTEGER NOT NULL
    CAST([Payment Processor] AS NVARCHAR(MAX)) + ', ' +

        -- "Direct Debit" BOOLEAN NOT NULL
    CASE WHEN [Direct Debit] = 1 THEN 'TRUE' ELSE 'FALSE' END + ', ' +

        -- "Direct Debit Pmt_ Terms Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Direct Debit Pmt_ Terms Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Pmt_ Export Line Definition" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Pmt_ Export Line Definition], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Bank Data Conversion Pmt_ Type" VARCHAR(50) NOT NULL
    '''' + REPLACE(REPLACE([Bank Data Conversion Pmt_ Type], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Default Payment Type Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Default Payment Type Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Single Payment" BOOLEAN NOT NULL
    CASE WHEN [Single Payment] = 1 THEN 'TRUE' ELSE 'FALSE' END + ', ' +

        -- "Default Pmt_ Bank Account No_" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Default Pmt_ Bank Account No_], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Separate Pmt_ Proposal Head" BOOLEAN NOT NULL
    CASE WHEN [Separate Pmt_ Proposal Head] = 1 THEN 'TRUE' ELSE 'FALSE' END + ', ' +

        -- "Vendor Purpose Text" VARCHAR(30) NOT NULL
    '''' + REPLACE(REPLACE([Vendor Purpose Text], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Customer Purpose Text" VARCHAR(30) NOT NULL
    '''' + REPLACE(REPLACE([Customer Purpose Text], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Payment Note Purpose Text" VARCHAR(30) NOT NULL
    '''' + REPLACE(REPLACE([Payment Note Purpose Text], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Vendor Purpose Text Header" VARCHAR(30) NOT NULL
    '''' + REPLACE(REPLACE([Vendor Purpose Text Header], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Purpose Text Footer" VARCHAR(30) NOT NULL
    '''' + REPLACE(REPLACE([Purpose Text Footer], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Limit Payment Amount (LCY)" NUMERIC(38, 20) NOT NULL
    CAST([Limit Payment Amount (LCY)] AS NVARCHAR(MAX)) + ', ' +

        -- "Customer Purpose Text Header" VARCHAR(30) NOT NULL
    '''' + REPLACE(REPLACE([Customer Purpose Text Header], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Limit Lines per Head" INTEGER NOT NULL
    CAST([Limit Lines per Head] AS NVARCHAR(MAX)) + ', ' +

        -- "Min_ Pos_ Payment Note" INTEGER NOT NULL (LAST COLUMN, no comma after)
    CAST([Min_ Pos_ Payment Note] AS NVARCHAR(MAX)) +
    ');'
FROM
    [Navision$Payment Method];

*/

CREATE TABLE migration_data."Navision$Payment Method" (
    "timestamp" BYTEA NOT NULL,
    "Code" VARCHAR(10) PRIMARY KEY,
    "Description" VARCHAR(50) NOT NULL,
    "Bal_ Account Type" INTEGER NOT NULL,
    "Bal_ Account No_" VARCHAR(20) NOT NULL,
    "Payment Processor" INTEGER NOT NULL,
    "Direct Debit" BOOLEAN NOT NULL,
    "Direct Debit Pmt_ Terms Code" VARCHAR(10) NOT NULL,
    "Pmt_ Export Line Definition" VARCHAR(20) NOT NULL,
    "Bank Data Conversion Pmt_ Type" VARCHAR(50) NOT NULL,
    "Default Payment Type Code" VARCHAR(10) NOT NULL,
    "Single Payment" BOOLEAN NOT NULL,
    "Default Pmt_ Bank Account No_" VARCHAR(20) NOT NULL,
    "Separate Pmt_ Proposal Head" BOOLEAN NOT NULL,
    "Vendor Purpose Text" VARCHAR(30) NOT NULL,
    "Customer Purpose Text" VARCHAR(30) NOT NULL,
    "Payment Note Purpose Text" VARCHAR(30) NOT NULL,
    "Vendor Purpose Text Header" VARCHAR(30) NOT NULL,
    "Purpose Text Footer" VARCHAR(30) NOT NULL,
    "Limit Payment Amount (LCY)" NUMERIC(38, 20) NOT NULL,
    "Customer Purpose Text Header" VARCHAR(30) NOT NULL,
    "Limit Lines per Head" INTEGER NOT NULL,
    "Min_ Pos_ Payment Note" INTEGER NOT NULL
);
