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
    'INSERT INTO migration_data."Navision$Business Relation" (' +
    '"timestamp", "No_", "Name", "Search Name", "Name 2", "Address", "Address 2", "City", "Contact", "Phone No_", "Telex No_", "Our Account No_", "Territory Code", "Global Dimension 1 Code", "Global Dimension 2 Code", "Budgeted Amount", "Vendor Posting Group", "Currency Code", "Language Code", "Statistics Group", "Payment Terms Code", "Fin_ Charge Terms Code", "Purchaser Code", "Shipment Method Code", "Shipping Agent Code", "Invoice Disc_ Code", "Country_Region Code", "Blocked", "Pay-to Vendor No_", "Priority", "Payment Method Code", "Last Date Modified", "Application Method", "Prices Including VAT", "Fax No_", "Telex Answer Back", "VAT Registration No_", "Gen_ Bus_ Posting Group", "Picture", "Post Code", "County", "E-Mail", "Home Page", "No_ Series", "Tax Area Code", "Tax Liable", "VAT Bus_ Posting Group", "Block Payment Tolerance", "IC Partner Code", "Prepayment _", "Primary Contact No_", "Responsibility Center", "Location Code", "Lead Time Calculation", "Base Calendar Code", "Registration No_", "Dialing Code", "Name 3", "Complete Phone No_", "Delivery Reminder Terms", "Datev Account No_", "Don_t Export Bank Account", "Skip Bank Acc_ in Bank Import", "Disable Auto Application", "Association No_", "Association Sort Code", "SearchCity", "Balance Acknowledgement Code", "Min_ Pos_ Payment Note", "Default Charges", "Limit Lines per Head", "Trading Type", "Service No_", "Partner Type", "Creditor No_", "Preferred Bank Account", "Cash Flow Payment Terms Code", "Datev Export Date", "Last Posting at", "Electronic Document Dispatch"' +
    ') VALUES (' +

        -- "timestamp" BYTEA NOT NULL
    'E''\\x' + SUBSTRING(sys.fn_varbintohexstr(timestamp), 3, DATALENGTH(timestamp) * 2) + '''' + ', ' +

        -- "No_" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE(No_, '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Name" VARCHAR(50) NOT NULL
    '''' + REPLACE(REPLACE(Name, '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Search Name" VARCHAR(50) NOT NULL
    '''' + REPLACE(REPLACE([Search Name], '''', ''''''), '\', '\\') + '''' + ', ' +

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

        -- "Telex No_" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Telex No_], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Our Account No_" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Our Account No_], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Territory Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Territory Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Global Dimension 1 Code" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Global Dimension 1 Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Global Dimension 2 Code" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Global Dimension 2 Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Budgeted Amount" NUMERIC(38, 20) NOT NULL
    CAST([Budgeted Amount] AS NVARCHAR(MAX)) + ', ' +

        -- "Vendor Posting Group" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Vendor Posting Group], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Currency Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Currency Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Language Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Language Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Statistics Group" INTEGER NOT NULL
    CAST([Statistics Group] AS NVARCHAR(MAX)) + ', ' +

        -- "Payment Terms Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Payment Terms Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Fin_ Charge Terms Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Fin_ Charge Terms Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Purchaser Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Purchaser Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Shipment Method Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Shipment Method Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Shipping Agent Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Shipping Agent Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Invoice Disc_ Code" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Invoice Disc_ Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Country_Region Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Country_Region Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Blocked" INTEGER NOT NULL
    CAST(Blocked AS NVARCHAR(MAX)) + ', ' +

        -- "Pay-to Vendor No_" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Pay-to Vendor No_], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Priority" INTEGER NOT NULL
    CAST(Priority AS NVARCHAR(MAX)) + ', ' +

        -- "Payment Method Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Payment Method Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Last Date Modified" TIMESTAMP NOT NULL
    '''' + CONVERT(NVARCHAR(MAX), [Last Date Modified], 120) + '''' + ', ' +

        -- "Application Method" INTEGER NOT NULL
    CAST([Application Method] AS NVARCHAR(MAX)) + ', ' +

        -- "Prices Including VAT" BOOLEAN NOT NULL
    CASE WHEN [Prices Including VAT] = 1 THEN 'TRUE' ELSE 'FALSE' END + ', ' +

        -- "Fax No_" VARCHAR(30) NOT NULL
    '''' + REPLACE(REPLACE([Fax No_], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Telex Answer Back" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Telex Answer Back], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "VAT Registration No_" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([VAT Registration No_], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Gen_ Bus_ Posting Group" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Gen_ Bus_ Posting Group], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Picture" BYTEA
    CASE WHEN Picture IS NULL THEN 'NULL' ELSE 'E''\\x' + SUBSTRING(sys.fn_varbintohexstr(Picture), 3, DATALENGTH(Picture) * 2) + '''' END + ', ' +

        -- "Post Code" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Post Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "County" VARCHAR(30) NOT NULL
    '''' + REPLACE(REPLACE(County, '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "E-Mail" VARCHAR(80) NOT NULL
    '''' + REPLACE(REPLACE([E-Mail], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Home Page" VARCHAR(80) NOT NULL
    '''' + REPLACE(REPLACE([Home Page], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "No_ Series" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([No_ Series], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Tax Area Code" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Tax Area Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Tax Liable" BOOLEAN NOT NULL
    CASE WHEN [Tax Liable] = 1 THEN 'TRUE' ELSE 'FALSE' END + ', ' +

        -- "VAT Bus_ Posting Group" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([VAT Bus_ Posting Group], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Block Payment Tolerance" BOOLEAN NOT NULL
    CASE WHEN [Block Payment Tolerance] = 1 THEN 'TRUE' ELSE 'FALSE' END + ', ' +

        -- "IC Partner Code" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([IC Partner Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Prepayment _" NUMERIC(38, 20) NOT NULL
    CAST([Prepayment _] AS NVARCHAR(MAX)) + ', ' +

        -- "Primary Contact No_" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Primary Contact No_], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Responsibility Center" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Responsibility Center], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Location Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Location Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Lead Time Calculation" VARCHAR(32) NOT NULL
    '''' + REPLACE(REPLACE([Lead Time Calculation], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Base Calendar Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Base Calendar Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Registration No_" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Registration No_], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Dialing Code" VARCHAR(30) NOT NULL
    '''' + REPLACE(REPLACE([Dialing Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Name 3" VARCHAR(50) NOT NULL
    '''' + REPLACE(REPLACE([Name 3], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Complete Phone No_" VARCHAR(90) NOT NULL
    '''' + REPLACE(REPLACE([Complete Phone No_], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Delivery Reminder Terms" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Delivery Reminder Terms], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Datev Account No_" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Datev Account No_], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Don_t Export Bank Account" BOOLEAN NOT NULL
    CASE WHEN [Don_t Export Bank Account] = 1 THEN 'TRUE' ELSE 'FALSE' END + ', ' +

        -- "Skip Bank Acc_ in Bank Import" BOOLEAN NOT NULL
    CASE WHEN [Skip Bank Acc_ in Bank Import] = 1 THEN 'TRUE' ELSE 'FALSE' END + ', ' +

        -- "Disable Auto Application" BOOLEAN NOT NULL
    CASE WHEN [Disable Auto Application] = 1 THEN 'TRUE' ELSE 'FALSE' END + ', ' +

        -- "Association No_" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Association No_], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Association Sort Code" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Association Sort Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "SearchCity" VARCHAR(30) NOT NULL
    '''' + REPLACE(REPLACE(SearchCity, '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Balance Acknowledgement Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Balance Acknowledgement Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Min_ Pos_ Payment Note" INTEGER NOT NULL
    CAST([Min_ Pos_ Payment Note] AS NVARCHAR(MAX)) + ', ' +

        -- "Default Charges" INTEGER NOT NULL
    CAST([Default Charges] AS NVARCHAR(MAX)) + ', ' +

        -- "Limit Lines per Head" INTEGER NOT NULL
    CAST([Limit Lines per Head] AS NVARCHAR(MAX)) + ', ' +

        -- "Trading Type" INTEGER NOT NULL
    CAST([Trading Type] AS NVARCHAR(MAX)) + ', ' +

        -- "Service No_" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Service No_], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Partner Type" INTEGER NOT NULL
    CAST([Partner Type] AS NVARCHAR(MAX)) + ', ' +

        -- "Creditor No_" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Creditor No_], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Preferred Bank Account" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Preferred Bank Account], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Cash Flow Payment Terms Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Cash Flow Payment Terms Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Datev Export Date" TIMESTAMP NOT NULL
    '''' + CONVERT(NVARCHAR(MAX), [Datev Export Date], 120) + '''' + ', ' +

        -- "Last Posting at" TIMESTAMP NOT NULL
    '''' + CONVERT(NVARCHAR(MAX), [Last Posting at], 120) + '''' + ', ' +

        -- "Electronic Document Dispatch" BOOLEAN NOT NULL (LAST COLUMN, no comma after)
    CASE WHEN [Electronic Document Dispatch] = 1 THEN 'TRUE' ELSE 'FALSE' END +
    ');'
FROM
    [Navision$Vendor];

*/

CREATE TABLE migration_data."Navision$Vendor" (
    "timestamp" BYTEA NOT NULL,
    "No_" VARCHAR(20) PRIMARY KEY,
    "Name" VARCHAR(50) NOT NULL,
    "Search Name" VARCHAR(50) NOT NULL,
    "Name 2" VARCHAR(50) NOT NULL,
    "Address" VARCHAR(50) NOT NULL,
    "Address 2" VARCHAR(50) NOT NULL,
    "City" VARCHAR(30) NOT NULL,
    "Contact" VARCHAR(50) NOT NULL,
    "Phone No_" VARCHAR(30) NOT NULL,
    "Telex No_" VARCHAR(20) NOT NULL,
    "Our Account No_" VARCHAR(20) NOT NULL,
    "Territory Code" VARCHAR(10) NOT NULL,
    "Global Dimension 1 Code" VARCHAR(20) NOT NULL,
    "Global Dimension 2 Code" VARCHAR(20) NOT NULL,
    "Budgeted Amount" NUMERIC(38, 20) NOT NULL,
    "Vendor Posting Group" VARCHAR(10) NOT NULL,
    "Currency Code" VARCHAR(10) NOT NULL,
    "Language Code" VARCHAR(10) NOT NULL,
    "Statistics Group" INTEGER NOT NULL,
    "Payment Terms Code" VARCHAR(10) NOT NULL,
    "Fin_ Charge Terms Code" VARCHAR(10) NOT NULL,
    "Purchaser Code" VARCHAR(10) NOT NULL,
    "Shipment Method Code" VARCHAR(10) NOT NULL,
    "Shipping Agent Code" VARCHAR(10) NOT NULL,
    "Invoice Disc_ Code" VARCHAR(20) NOT NULL,
    "Country_Region Code" VARCHAR(10) NOT NULL,
    "Blocked" INTEGER NOT NULL,
    "Pay-to Vendor No_" VARCHAR(20) NOT NULL,
    "Priority" INTEGER NOT NULL,
    "Payment Method Code" VARCHAR(10) NOT NULL,
    "Last Date Modified" TIMESTAMP NOT NULL,
    "Application Method" INTEGER NOT NULL,
    "Prices Including VAT" BOOLEAN NOT NULL,
    "Fax No_" VARCHAR(30) NOT NULL,
    "Telex Answer Back" VARCHAR(20) NOT NULL,
    "VAT Registration No_" VARCHAR(20) NOT NULL,
    "Gen_ Bus_ Posting Group" VARCHAR(10) NOT NULL,
    "Picture" BYTEA,
    "Post Code" VARCHAR(20) NOT NULL,
    "County" VARCHAR(30) NOT NULL,
    "E-Mail" VARCHAR(80) NOT NULL,
    "Home Page" VARCHAR(80) NOT NULL,
    "No_ Series" VARCHAR(10) NOT NULL,
    "Tax Area Code" VARCHAR(20) NOT NULL,
    "Tax Liable" BOOLEAN NOT NULL,
    "VAT Bus_ Posting Group" VARCHAR(10) NOT NULL,
    "Block Payment Tolerance" BOOLEAN NOT NULL,
    "IC Partner Code" VARCHAR(20) NOT NULL,
    "Prepayment _" NUMERIC(38, 20) NOT NULL,
    "Primary Contact No_" VARCHAR(20) NOT NULL,
    "Responsibility Center" VARCHAR(10) NOT NULL,
    "Location Code" VARCHAR(10) NOT NULL,
    "Lead Time Calculation" VARCHAR(32) NOT NULL,
    "Base Calendar Code" VARCHAR(10) NOT NULL,
    "Registration No_" VARCHAR(20) NOT NULL,
    "Dialing Code" VARCHAR(30) NOT NULL,
    "Name 3" VARCHAR(50) NOT NULL,
    "Complete Phone No_" VARCHAR(90) NOT NULL,
    "Delivery Reminder Terms" VARCHAR(10) NOT NULL,
    "Datev Account No_" VARCHAR(20) NOT NULL,
    "Don_t Export Bank Account" BOOLEAN NOT NULL,
    "Skip Bank Acc_ in Bank Import" BOOLEAN NOT NULL,
    "Disable Auto Application" BOOLEAN NOT NULL,
    "Association No_" VARCHAR(20) NOT NULL,
    "Association Sort Code" VARCHAR(20) NOT NULL,
    "SearchCity" VARCHAR(30) NOT NULL,
    "Balance Acknowledgement Code" VARCHAR(10) NOT NULL,
    "Min_ Pos_ Payment Note" INTEGER NOT NULL,
    "Default Charges" INTEGER NOT NULL,
    "Limit Lines per Head" INTEGER NOT NULL,
    "Trading Type" INTEGER NOT NULL,
    "Service No_" VARCHAR(10) NOT NULL,
    "Partner Type" INTEGER NOT NULL,
    "Creditor No_" VARCHAR(20) NOT NULL,
    "Preferred Bank Account" VARCHAR(10) NOT NULL,
    "Cash Flow Payment Terms Code" VARCHAR(10) NOT NULL,
    "Datev Export Date" TIMESTAMP NOT NULL,
    "Last Posting at" TIMESTAMP NOT NULL,
    "Electronic Document Dispatch" BOOLEAN NOT NULL
);
