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
    'INSERT INTO migration_data."Navision$Vendor Bank Account" (' +
    '"timestamp", "Vendor No_", "Code", "Name", "Name 2", "Address", "Address 2", "City", "Post Code", "Contact", "Phone No_", "Telex No_", "Bank Branch No_", "Bank Account No_", "Transit No_", "Currency Code", "Country_Region Code", "County", "Fax No_", "Telex Answer Back", "Language Code", "E-Mail", "Home Page", "IBAN", "SWIFT Code", "Inserted by Bank Import", "Payment Type Code", "Owner", "Mandate ID", "Mandate Date", "Routing No_", "Payment Method Code", "Mandate Type", "Mandate Frequency", "Mandate Is Electronic", "Mandate On Behalf of Owner", "Mandate Last Used", "Mandate Status", "Alt_ Reason Row 1", "Alt_ Reason Row 2", "Owner Contact", "Alt_ Creditor Identifier", "Bank Clearing Code", "Bank Clearing Standard", "Hash Value Branch _ Acc_ No_", "Hash Value IBAN", "Hash Value SWIFT _ Acc_ No_"' +
    ') VALUES (' +

        -- "timestamp" BYTEA NOT NULL
    'E''\\x' + SUBSTRING(sys.fn_varbintohexstr(timestamp), 3, DATALENGTH(timestamp) * 2) + '''' + ', ' +

        -- "Vendor No_" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Vendor No_], '''', ''''''), '\', '\\') + '''' + ', ' +

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

        -- "Post Code" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Post Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Contact" VARCHAR(50) NOT NULL
    '''' + REPLACE(REPLACE(Contact, '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Phone No_" VARCHAR(30) NOT NULL
    '''' + REPLACE(REPLACE([Phone No_], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Telex No_" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Telex No_], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Bank Branch No_" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Bank Branch No_], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Bank Account No_" VARCHAR(34) NOT NULL
    '''' + REPLACE(REPLACE([Bank Account No_], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Transit No_" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Transit No_], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Currency Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Currency Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Country_Region Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Country_Region Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "County" VARCHAR(30) NOT NULL
    '''' + REPLACE(REPLACE(County, '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Fax No_" VARCHAR(30) NOT NULL
    '''' + REPLACE(REPLACE([Fax No_], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Telex Answer Back" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Telex Answer Back], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Language Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Language Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "E-Mail" VARCHAR(80) NOT NULL
    '''' + REPLACE(REPLACE([E-Mail], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Home Page" VARCHAR(80) NOT NULL
    '''' + REPLACE(REPLACE([Home Page], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "IBAN" VARCHAR(50) NOT NULL
    '''' + REPLACE(REPLACE(IBAN, '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "SWIFT Code" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([SWIFT Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Inserted by Bank Import" BOOLEAN NOT NULL
    CASE WHEN [Inserted by Bank Import] = 1 THEN 'TRUE' ELSE 'FALSE' END + ', ' +

        -- "Payment Type Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Payment Type Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Owner" VARCHAR(50) NOT NULL
    '''' + REPLACE(REPLACE(Owner, '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Mandate ID" VARCHAR(35) NOT NULL
    '''' + REPLACE(REPLACE([Mandate ID], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Mandate Date" TIMESTAMP NOT NULL
    '''' + CONVERT(NVARCHAR(MAX), [Mandate Date], 120) + '''' + ', ' +

        -- "Routing No_" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Routing No_], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Payment Method Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Payment Method Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Mandate Type" INTEGER NOT NULL
    CAST([Mandate Type] AS NVARCHAR(MAX)) + ', ' +

        -- "Mandate Frequency" INTEGER NOT NULL
    CAST([Mandate Frequency] AS NVARCHAR(MAX)) + ', ' +

        -- "Mandate Is Electronic" BOOLEAN NOT NULL
    CASE WHEN [Mandate Is Electronic] = 1 THEN 'TRUE' ELSE 'FALSE' END + ', ' +

        -- "Mandate On Behalf of Owner" BOOLEAN NOT NULL
    CASE WHEN [Mandate On Behalf of Owner] = 1 THEN 'TRUE' ELSE 'FALSE' END + ', ' +

        -- "Mandate Last Used" TIMESTAMP NOT NULL
    '''' + CONVERT(NVARCHAR(MAX), [Mandate Last Used], 120) + '''' + ', ' +

        -- "Mandate Status" INTEGER NOT NULL
    CAST([Mandate Status] AS NVARCHAR(MAX)) + ', ' +

        -- "Alt_ Reason Row 1" VARCHAR(35) NOT NULL
    '''' + REPLACE(REPLACE([Alt_ Reason Row 1], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Alt_ Reason Row 2" VARCHAR(35) NOT NULL
    '''' + REPLACE(REPLACE([Alt_ Reason Row 2], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Owner Contact" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Owner Contact], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Alt_ Creditor Identifier" VARCHAR(38) NOT NULL
    '''' + REPLACE(REPLACE([Alt_ Creditor Identifier], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Bank Clearing Code" VARCHAR(50) NOT NULL
    '''' + REPLACE(REPLACE([Bank Clearing Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Bank Clearing Standard" VARCHAR(50) NOT NULL
    '''' + REPLACE(REPLACE([Bank Clearing Standard], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Hash Value Branch _ Acc_ No_" VARCHAR(50) NOT NULL
    '''' + REPLACE(REPLACE([Hash Value Branch _ Acc_ No_], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Hash Value IBAN" VARCHAR(50) NOT NULL
    '''' + REPLACE(REPLACE([Hash Value IBAN], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Hash Value SWIFT _ Acc_ No_" VARCHAR(50) NOT NULL (LAST COLUMN, no comma after)
    '''' + REPLACE(REPLACE([Hash Value SWIFT _ Acc_ No_], '''', ''''''), '\', '\\') + '''' +
    ');'
FROM
    [Navision$Vendor Bank Account];

*/

CREATE TABLE migration_data."Navision$Vendor Bank Account" (
    "timestamp" BYTEA NOT NULL,
    "Vendor No_" VARCHAR(20) NOT NULL,
    "Code" VARCHAR(10) NOT NULL,
    "Name" VARCHAR(50) NOT NULL,
    "Name 2" VARCHAR(50) NOT NULL,
    "Address" VARCHAR(50) NOT NULL,
    "Address 2" VARCHAR(50) NOT NULL,
    "City" VARCHAR(30) NOT NULL,
    "Post Code" VARCHAR(20) NOT NULL,
    "Contact" VARCHAR(50) NOT NULL,
    "Phone No_" VARCHAR(30) NOT NULL,
    "Telex No_" VARCHAR(20) NOT NULL,
    "Bank Branch No_" VARCHAR(20) NOT NULL,
    "Bank Account No_" VARCHAR(34) NOT NULL,
    "Transit No_" VARCHAR(20) NOT NULL,
    "Currency Code" VARCHAR(10) NOT NULL,
    "Country_Region Code" VARCHAR(10) NOT NULL,
    "County" VARCHAR(30) NOT NULL,
    "Fax No_" VARCHAR(30) NOT NULL,
    "Telex Answer Back" VARCHAR(20) NOT NULL,
    "Language Code" VARCHAR(10) NOT NULL,
    "E-Mail" VARCHAR(80) NOT NULL,
    "Home Page" VARCHAR(80) NOT NULL,
    "IBAN" VARCHAR(50) NOT NULL,
    "SWIFT Code" VARCHAR(20) NOT NULL,
    "Inserted by Bank Import" BOOLEAN NOT NULL,
    "Payment Type Code" VARCHAR(10) NOT NULL,
    "Owner" VARCHAR(50) NOT NULL,
    "Mandate ID" VARCHAR(35) NOT NULL,
    "Mandate Date" TIMESTAMP NOT NULL,
    "Routing No_" VARCHAR(20) NOT NULL,
    "Payment Method Code" VARCHAR(10) NOT NULL,
    "Mandate Type" INTEGER NOT NULL,
    "Mandate Frequency" INTEGER NOT NULL,
    "Mandate Is Electronic" BOOLEAN NOT NULL,
    "Mandate On Behalf of Owner" BOOLEAN NOT NULL,
    "Mandate Last Used" TIMESTAMP NOT NULL,
    "Mandate Status" INTEGER NOT NULL,
    "Alt_ Reason Row 1" VARCHAR(35) NOT NULL,
    "Alt_ Reason Row 2" VARCHAR(35) NOT NULL,
    "Owner Contact" VARCHAR(20) NOT NULL,
    "Alt_ Creditor Identifier" VARCHAR(38) NOT NULL,
    "Bank Clearing Code" VARCHAR(50) NOT NULL,
    "Bank Clearing Standard" VARCHAR(50) NOT NULL,
    "Hash Value Branch _ Acc_ No_" VARCHAR(50) NOT NULL,
    "Hash Value IBAN" VARCHAR(50) NOT NULL,
    "Hash Value SWIFT _ Acc_ No_" VARCHAR(50) NOT NULL,
    PRIMARY KEY ("Vendor No_", "Code")
);
