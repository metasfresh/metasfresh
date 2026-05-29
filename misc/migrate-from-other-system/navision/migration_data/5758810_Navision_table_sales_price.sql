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
    'INSERT INTO migration_data."Navision$Sales Price" (' +
    '"timestamp", "Item No_", "Sales Type", "Sales Code", "Starting Date", "Currency Code", "Variant Code", "Unit of Measure Code", "Minimum Quantity", "Unit Price", "Price Includes VAT", "Allow Invoice Disc_", "VAT Bus_ Posting Gr_ (Price)", "Ending Date", "Allow Line Disc_"' +
    ') VALUES (' +

        -- "timestamp" BYTEA NOT NULL
    'E''\\x' + SUBSTRING(sys.fn_varbintohexstr(timestamp), 3, DATALENGTH(timestamp) * 2) + '''' + ', ' +

        -- "Item No_" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Item No_], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Sales Type" INTEGER NOT NULL
    CAST([Sales Type] AS NVARCHAR(MAX)) + ', ' +

        -- "Sales Code" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Sales Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Starting Date" TIMESTAMP NOT NULL
    '''' + CONVERT(NVARCHAR(MAX), [Starting Date], 120) + '''' + ', ' +

        -- "Currency Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Currency Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Variant Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Variant Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Unit of Measure Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Unit of Measure Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Minimum Quantity" NUMERIC(38, 20) NOT NULL
    CAST([Minimum Quantity] AS NVARCHAR(MAX)) + ', ' +

        -- "Unit Price" NUMERIC(38, 20) NOT NULL
    CAST([Unit Price] AS NVARCHAR(MAX)) + ', ' +

        -- "Price Includes VAT" BOOLEAN NOT NULL
    CASE WHEN [Price Includes VAT] = 1 THEN 'TRUE' ELSE 'FALSE' END + ', ' +

        -- "Allow Invoice Disc_" BOOLEAN NOT NULL
    CASE WHEN [Allow Invoice Disc_] = 1 THEN 'TRUE' ELSE 'FALSE' END + ', ' +

        -- "VAT Bus_ Posting Gr_ (Price)" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([VAT Bus_ Posting Gr_ (Price)], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Ending Date" TIMESTAMP NOT NULL
    '''' + CONVERT(NVARCHAR(MAX), [Ending Date], 120) + '''' + ', ' +

        -- "Allow Line Disc_" BOOLEAN NOT NULL (LAST COLUMN, no comma after)
    CASE WHEN [Allow Line Disc_] = 1 THEN 'TRUE' ELSE 'FALSE' END +
    ');'
FROM
    [Navision$Sales Price];
*/

CREATE TABLE migration_data."Navision$Sales Price" (
    "timestamp" BYTEA NOT NULL,
    "Item No_" VARCHAR(20) NOT NULL,
    "Sales Type" INTEGER NOT NULL,
    "Sales Code" VARCHAR(20) NOT NULL,
    "Starting Date" TIMESTAMP NOT NULL,
    "Currency Code" VARCHAR(10) NOT NULL,
    "Variant Code" VARCHAR(10) NOT NULL,
    "Unit of Measure Code" VARCHAR(10) NOT NULL,
    "Minimum Quantity" NUMERIC(38, 20) NOT NULL,
    "Unit Price" NUMERIC(38, 20) NOT NULL,
    "Price Includes VAT" BOOLEAN NOT NULL,
    "Allow Invoice Disc_" BOOLEAN NOT NULL,
    "VAT Bus_ Posting Gr_ (Price)" VARCHAR(10) NOT NULL,
    "Ending Date" TIMESTAMP NOT NULL,
    "Allow Line Disc_" BOOLEAN NOT NULL,
    PRIMARY KEY ("Item No_", "Sales Type", "Sales Code", "Starting Date", "Currency Code", "Variant Code", "Unit of Measure Code", "Minimum Quantity")
);
