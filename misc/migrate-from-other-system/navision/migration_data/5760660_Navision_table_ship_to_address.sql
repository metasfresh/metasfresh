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
    'INSERT INTO migration_data."Navision$Ship-to Address" (' +
    '"timestamp", "Customer No_", "Code", "Name", "Name 2", "Address", "Address 2", ' +
    '"City", "Contact", "Phone No_", "Telex No_", "Shipment Method Code", ' +
    '"Shipping Agent Code", "Place of Export", "Country_Region Code", "Last Date Modified", ' +
    '"Location Code", "Fax No_", "Telex Answer Back", "Post Code", "County", "E-Mail", ' +
    '"Home Page", "Tax Area Code", "Tax Liable", "Shipping Agent Service Code", "Service Zone Code"' +
    ') VALUES (' +

        -- "timestamp" BYTEA NOT NULL
    'E''\\x' + SUBSTRING(sys.fn_varbintohexstr(timestamp), 3, DATALENGTH(timestamp) * 2) + '''' + ', ' +

        -- "Customer No_" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Customer No_], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE(Code, '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Name" VARCHAR(50) NOT NULL
    '''' + REPLACE(REPLACE(Name, '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Name 2" VARCHAR(50) NOT NULL
    '''' + REPLACE(REPLACE([Name 2], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Address" VARCHAR(50) NOT NULL
    '''' + REPLACE(REPLACE(Address, '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Address 2" VARCHAR(50) NOT NULL
    '''' + REPLACE(REPLACE([Address 2], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "City" VARCHAR(30) NOT NULL
    '''' + REPLACE(REPLACE(City, '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Contact" VARCHAR(50) NOT NULL
    '''' + REPLACE(REPLACE(Contact, '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Phone No_" VARCHAR(30) NOT NULL
    '''' + REPLACE(REPLACE([Phone No_], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Telex No_" VARCHAR(30) NOT NULL
    '''' + REPLACE(REPLACE([Telex No_], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Shipment Method Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Shipment Method Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Shipping Agent Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Shipping Agent Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Place of Export" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Place of Export], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Country_Region Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Country_Region Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Last Date Modified" TIMESTAMP NOT NULL
    '''' + CONVERT(NVARCHAR(MAX), [Last Date Modified], 126) + '''' + ', ' +

        -- "Location Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Location Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Fax No_" VARCHAR(30) NOT NULL
    '''' + REPLACE(REPLACE([Fax No_], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Telex Answer Back" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Telex Answer Back], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Post Code" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Post Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "County" VARCHAR(30) NOT NULL
    '''' + REPLACE(REPLACE(County, '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "E-Mail" VARCHAR(80) NOT NULL
    '''' + REPLACE(REPLACE([E-Mail], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Home Page" VARCHAR(80) NOT NULL
    '''' + REPLACE(REPLACE([Home Page], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Tax Area Code" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Tax Area Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Tax Liable" SMALLINT NOT NULL
    CAST([Tax Liable] AS NVARCHAR(MAX)) + ', ' +

        -- "Shipping Agent Service Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Shipping Agent Service Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Service Zone Code" VARCHAR(10) NOT NULL (LAST COLUMN, no comma after)
    '''' + REPLACE(REPLACE([Service Zone Code], '''', ''''''), '\', '\\') + '''' +
    ');'
FROM
    [Navision$Ship-to Address];
*/

CREATE TABLE migration_data."Navision$Ship-to Address" (
    "timestamp" BYTEA NOT NULL,
    "Customer No_" VARCHAR(20) NOT NULL,
    "Code" VARCHAR(10) NOT NULL,
    "Name" VARCHAR(50) NOT NULL,
    "Name 2" VARCHAR(50) NOT NULL,
    "Address" VARCHAR(50) NOT NULL,
    "Address 2" VARCHAR(50) NOT NULL,
    "City" VARCHAR(30) NOT NULL,
    "Contact" VARCHAR(50) NOT NULL,
    "Phone No_" VARCHAR(30) NOT NULL,
    "Telex No_" VARCHAR(30) NOT NULL,
    "Shipment Method Code" VARCHAR(10) NOT NULL,
    "Shipping Agent Code" VARCHAR(10) NOT NULL,
    "Place of Export" VARCHAR(20) NOT NULL,
    "Country_Region Code" VARCHAR(10) NOT NULL,
    "Last Date Modified" TIMESTAMP NOT NULL,
    "Location Code" VARCHAR(10) NOT NULL,
    "Fax No_" VARCHAR(30) NOT NULL,
    "Telex Answer Back" VARCHAR(20) NOT NULL,
    "Post Code" VARCHAR(20) NOT NULL,
    "County" VARCHAR(30) NOT NULL,
    "E-Mail" VARCHAR(80) NOT NULL,
    "Home Page" VARCHAR(80) NOT NULL,
    "Tax Area Code" VARCHAR(20) NOT NULL,
    "Tax Liable" SMALLINT NOT NULL,
    "Shipping Agent Service Code" VARCHAR(10) NOT NULL,
    "Service Zone Code" VARCHAR(10) NOT NULL,
    PRIMARY KEY ("Customer No_", "Code")
);
