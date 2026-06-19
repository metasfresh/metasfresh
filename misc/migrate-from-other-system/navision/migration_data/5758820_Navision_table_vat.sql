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
    'INSERT INTO migration_data."Navision$VAT Posting Setup" (' +
    '"timestamp", "VAT Bus_ Posting Group", "VAT Prod_ Posting Group", "VAT Calculation Type", "VAT _", "Unrealized VAT Type", "Adjust for Payment Discount", "Sales VAT Account", "Sales VAT Unreal_ Account", "Purchase VAT Account", "Purch_ VAT Unreal_ Account", "Reverse Chrg_ VAT Acc_", "Reverse Chrg_ VAT Unreal_ Acc_", "VAT Identifier", "EU Service", "Income Date VAT Post_ Grp_", "VAT Clause Code", "Certificate of Supply Required", "Datev VAT Key", "Datev VAT Case Fact", "Datev Acc_ receiv_ Prepayments", "Datev Acc_ payed Prepayments"' +
    ') VALUES (' +

        -- "timestamp" BYTEA NOT NULL
    'E''\\x' + SUBSTRING(sys.fn_varbintohexstr(timestamp), 3, DATALENGTH(timestamp) * 2) + '''' + ', ' +

        -- "VAT Bus_ Posting Group" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([VAT Bus_ Posting Group], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "VAT Prod_ Posting Group" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([VAT Prod_ Posting Group], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "VAT Calculation Type" INTEGER NOT NULL
    CAST([VAT Calculation Type] AS NVARCHAR(MAX)) + ', ' +

        -- "VAT _" NUMERIC(38, 20) NOT NULL
    CAST([VAT _] AS NVARCHAR(MAX)) + ', ' +

        -- "Unrealized VAT Type" INTEGER NOT NULL
    CAST([Unrealized VAT Type] AS NVARCHAR(MAX)) + ', ' +

        -- "Adjust for Payment Discount" BOOLEAN NOT NULL
    CASE WHEN [Adjust for Payment Discount] = 1 THEN 'TRUE' ELSE 'FALSE' END + ', ' +

        -- "Sales VAT Account" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Sales VAT Account], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Sales VAT Unreal_ Account" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Sales VAT Unreal_ Account], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Purchase VAT Account" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Purchase VAT Account], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Purch_ VAT Unreal_ Account" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Purch_ VAT Unreal_ Account], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Reverse Chrg_ VAT Acc_" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Reverse Chrg_ VAT Acc_], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Reverse Chrg_ VAT Unreal_ Acc_" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Reverse Chrg_ VAT Unreal_ Acc_], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "VAT Identifier" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([VAT Identifier], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "EU Service" BOOLEAN NOT NULL
    CASE WHEN [EU Service] = 1 THEN 'TRUE' ELSE 'FALSE' END + ', ' +

        -- "Income Date VAT Post_ Grp_" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Income Date VAT Post_ Grp_], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "VAT Clause Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([VAT Clause Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Certificate of Supply Required" BOOLEAN NOT NULL
    CASE WHEN [Certificate of Supply Required] = 1 THEN 'TRUE' ELSE 'FALSE' END + ', ' +

        -- "Datev VAT Key" VARCHAR(2) NOT NULL
    '''' + REPLACE(REPLACE([Datev VAT Key], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Datev VAT Case Fact" VARCHAR(2) NOT NULL
    '''' + REPLACE(REPLACE([Datev VAT Case Fact], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Datev Acc_ receiv_ Prepayments" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Datev Acc_ receiv_ Prepayments], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Datev Acc_ payed Prepayments" VARCHAR(20) NOT NULL (LAST COLUMN, no comma after)
    '''' + REPLACE(REPLACE([Datev Acc_ payed Prepayments], '''', ''''''), '\', '\\') + '''' +
    ');'
FROM
    [Navision$VAT Posting Setup];

*/

CREATE TABLE  migration_data."Navision$VAT Posting Setup" (
    "timestamp" BYTEA NOT NULL,
    "VAT Bus_ Posting Group" VARCHAR(10) NOT NULL,
    "VAT Prod_ Posting Group" VARCHAR(10) NOT NULL,
    "VAT Calculation Type" INTEGER NOT NULL,
    "VAT _" NUMERIC(38, 20) NOT NULL,
    "Unrealized VAT Type" INTEGER NOT NULL,
    "Adjust for Payment Discount" BOOLEAN NOT NULL,
    "Sales VAT Account" VARCHAR(20) NOT NULL,
    "Sales VAT Unreal_ Account" VARCHAR(20) NOT NULL,
    "Purchase VAT Account" VARCHAR(20) NOT NULL,
    "Purch_ VAT Unreal_ Account" VARCHAR(20) NOT NULL,
    "Reverse Chrg_ VAT Acc_" VARCHAR(20) NOT NULL,
    "Reverse Chrg_ VAT Unreal_ Acc_" VARCHAR(20) NOT NULL,
    "VAT Identifier" VARCHAR(10) NOT NULL,
    "EU Service" BOOLEAN NOT NULL,
    "Income Date VAT Post_ Grp_" VARCHAR(10) NOT NULL,
    "VAT Clause Code" VARCHAR(10) NOT NULL,
    "Certificate of Supply Required" BOOLEAN NOT NULL,
    "Datev VAT Key" VARCHAR(2) NOT NULL,
    "Datev VAT Case Fact" VARCHAR(2) NOT NULL,
    "Datev Acc_ receiv_ Prepayments" VARCHAR(20) NOT NULL,
    "Datev Acc_ payed Prepayments" VARCHAR(20) NOT NULL,
    PRIMARY KEY ("VAT Bus_ Posting Group", "VAT Prod_ Posting Group")
);
