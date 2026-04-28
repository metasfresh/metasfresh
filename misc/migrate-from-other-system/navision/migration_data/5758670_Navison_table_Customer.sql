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
    'INSERT INTO migration_data."Navision$Customer" (' +
    '"timestamp", "No_", "Name", "Search Name", "Name 2", "Address", "Address 2", "City", "Contact", "Phone No_", "Telex No_", "Our Account No_", "Territory Code", "Global Dimension 1 Code", "Global Dimension 2 Code", "Chain Name", "Budgeted Amount", "Credit Limit (LCY)", "Customer Posting Group", "Currency Code", "Customer Price Group", "Language Code", "Statistics Group", "Payment Terms Code", "Fin_ Charge Terms Code", "Salesperson Code", "Shipment Method Code", "Shipping Agent Code", "Place of Export", "Invoice Disc_ Code", "Customer Disc_ Group", "Country_Region Code", "Collection Method", "Amount", "Blocked", "Invoice Copies", "Last Statement No_", "Print Statements", "Bill-to Customer No_", "Priority", "Payment Method Code", "Last Date Modified", "Application Method", "Prices Including VAT", "Location Code", "Fax No_", "Telex Answer Back", "VAT Registration No_", "Combine Shipments", "Gen_ Bus_ Posting Group", "Picture", "Post Code", "County", "E-Mail", "Home Page", "Reminder Terms Code", "No_ Series", "Tax Area Code", "Tax Liable", "VAT Bus_ Posting Group", "Reserve", "Block Payment Tolerance", "IC Partner Code", "Prepayment _", "Partner Type", "Preferred Bank Account", "Cash Flow Payment Terms Code", "Primary Contact No_", "Responsibility Center", "Shipping Advice", "Shipping Time", "Shipping Agent Service Code", "Service Zone Code", "Allow Line Disc_", "Base Calendar Code", "Copy Sell-to Addr_ to Qte From", "Routing Code", "various Customer", "importet", "Dialing Code", "Name 3", "Complete Phone No_", "Datev Account No_", "Don_t Export Bank Account", "Datev Export Date", "Skip Bank Acc_ in Bank Import", "Disable Auto Application", "Association No_", "Association Sort Code", "SearchCity", "Balance Acknowledgement Code", "Last Posting at", "Min_ Pos_ Payment Note", "Default Charges", "Limit Lines per Head", "Trading Type", "Service No_", "Electronic Document Dispatch", "Packing Slip Copies"' +
    ') VALUES (' +

        -- "timestamp" BYTEA NOT NULL
        -- Corrected: Use sys.fn_varbintohexstr for timestamp (rowversion) conversion to hex string
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
    '''' + REPLACE(REPLACE(COALESCE([Address 2], ''), '''', ''''''), '\', '\\') + '''' + ', ' +

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

        -- "Global Dimension 1 Code" VARCHAR(20) NOT NULL (Order corrected)
    '''' + REPLACE(REPLACE([Global Dimension 1 Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Global Dimension 2 Code" VARCHAR(20) NOT NULL (Order corrected)
    '''' + REPLACE(REPLACE([Global Dimension 2 Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Chain Name" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Chain Name], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Budgeted Amount" NUMERIC(38, 20) NOT NULL
    CAST([Budgeted Amount] AS NVARCHAR(MAX)) + ', ' +

        -- "Credit Limit (LCY)" NUMERIC(38, 20) NOT NULL
    CAST([Credit Limit (LCY)] AS NVARCHAR(MAX)) + ', ' +

        -- "Customer Posting Group" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Customer Posting Group], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Currency Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Currency Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Customer Price Group" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Customer Price Group], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Language Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Language Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Statistics Group" INTEGER NOT NULL
    CAST([Statistics Group] AS NVARCHAR(MAX)) + ', ' +

        -- "Payment Terms Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Payment Terms Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Fin_ Charge Terms Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Fin_ Charge Terms Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Salesperson Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Salesperson Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Shipment Method Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Shipment Method Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Shipping Agent Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Shipping Agent Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Place of Export" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Place of Export], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Invoice Disc_ Code" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Invoice Disc_ Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Customer Disc_ Group" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Customer Disc_ Group], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Country_Region Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Country_Region Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Collection Method" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Collection Method], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Amount" NUMERIC(38, 20) NOT NULL
    CAST(Amount AS NVARCHAR(MAX)) + ', ' +

        -- "Blocked" INTEGER NOT NULL
    CAST(Blocked AS NVARCHAR(MAX)) + ', ' +

        -- "Invoice Copies" INTEGER NOT NULL
    CAST([Invoice Copies] AS NVARCHAR(MAX)) + ', ' +

        -- "Last Statement No_" INTEGER NOT NULL
    CAST([Last Statement No_] AS NVARCHAR(MAX)) + ', ' +

        -- "Print Statements" BOOLEAN NOT NULL
    CASE WHEN [Print Statements] = 1 THEN 'TRUE' ELSE 'FALSE' END + ', ' +

        -- "Bill-to Customer No_" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Bill-to Customer No_], '''', ''''''), '\', '\\') + '''' + ', ' +

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

        -- "Location Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Location Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Fax No_" VARCHAR(30) NOT NULL
    '''' + REPLACE(REPLACE([Fax No_], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Telex Answer Back" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Telex Answer Back], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "VAT Registration No_" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([VAT Registration No_], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Combine Shipments" BOOLEAN NOT NULL
    CASE WHEN [Combine Shipments] = 1 THEN 'TRUE' ELSE 'FALSE' END + ', ' +

        -- "Gen_ Bus_ Posting Group" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Gen_ Bus_ Posting Group], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Picture" BYTEA
        -- Corrected: Use sys.fn_varbintohexstr for image conversion to hex string
    CASE WHEN Picture IS NULL THEN 'NULL' ELSE 'E''\\x' + SUBSTRING(sys.fn_varbintohexstr(Picture), 3, DATALENGTH(Picture) * 2) + '''' END + ', ' +

        -- "Post Code" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Post Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "County" VARCHAR(30) NOT NULL
    '''' + REPLACE(REPLACE(County, '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "E-Mail" VARCHAR(80) NOT NULL
    '''' + REPLACE(REPLACE([E-Mail], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Home Page" VARCHAR(80) NOT NULL
    '''' + REPLACE(REPLACE([Home Page], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Reminder Terms Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Reminder Terms Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "No_ Series" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([No_ Series], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Tax Area Code" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Tax Area Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Tax Liable" BOOLEAN NOT NULL
    CASE WHEN [Tax Liable] = 1 THEN 'TRUE' ELSE 'FALSE' END + ', ' +

        -- "VAT Bus_ Posting Group" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([VAT Bus_ Posting Group], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Reserve" INTEGER NOT NULL
    CAST(Reserve AS NVARCHAR(MAX)) + ', ' +

        -- "Block Payment Tolerance" BOOLEAN NOT NULL
    CASE WHEN [Block Payment Tolerance] = 1 THEN 'TRUE' ELSE 'FALSE' END + ', ' +

        -- "IC Partner Code" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([IC Partner Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Prepayment _" NUMERIC(38, 20) NOT NULL
    CAST([Prepayment _] AS NVARCHAR(MAX)) + ', ' +

        -- "Partner Type" INTEGER NOT NULL
    CAST([Partner Type] AS NVARCHAR(MAX)) + ', ' +

        -- "Preferred Bank Account" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Preferred Bank Account], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Cash Flow Payment Terms Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Cash Flow Payment Terms Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Primary Contact No_" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Primary Contact No_], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Responsibility Center" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Responsibility Center], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Shipping Advice" INTEGER NOT NULL
    CAST([Shipping Advice] AS NVARCHAR(MAX)) + ', ' +

        -- "Shipping Time" VARCHAR(32) NOT NULL
    '''' + REPLACE(REPLACE([Shipping Time], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Shipping Agent Service Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Shipping Agent Service Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Service Zone Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Service Zone Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Allow Line Disc_" BOOLEAN NOT NULL
    CASE WHEN [Allow Line Disc_] = 1 THEN 'TRUE' ELSE 'FALSE' END + ', ' +

        -- "Base Calendar Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Base Calendar Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Copy Sell-to Addr_ to Qte From" INTEGER NOT NULL
    CAST([Copy Sell-to Addr_ to Qte From] AS NVARCHAR(MAX)) + ', ' +

        -- "Routing Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Routing Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "various Customer" BOOLEAN NOT NULL
    CASE WHEN [various Customer] = 1 THEN 'TRUE' ELSE 'FALSE' END + ', ' +

        -- "importet" BOOLEAN NOT NULL
    CASE WHEN importet = 1 THEN 'TRUE' ELSE 'FALSE' END + ', ' +

        -- "Dialing Code" VARCHAR(30) NOT NULL
    '''' + REPLACE(REPLACE([Dialing Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Name 3" VARCHAR(50) NOT NULL
    '''' + REPLACE(REPLACE([Name 3], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Complete Phone No_" VARCHAR(90) NOT NULL
    '''' + REPLACE(REPLACE([Complete Phone No_], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Datev Account No_" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Datev Account No_], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Don_t Export Bank Account" BOOLEAN NOT NULL
    CASE WHEN [Don_t Export Bank Account] = 1 THEN 'TRUE' ELSE 'FALSE' END + ', ' +

        -- "Datev Export Date" TIMESTAMP NOT NULL
    '''' + CONVERT(NVARCHAR(MAX), [Datev Export Date], 120) + '''' + ', ' +

        -- "Skip Bank Acc_ in Bank Import" BOOLEAN NOT NULL
    CASE WHEN [Skip Bank Acc_ in Bank Import] = 1 THEN 'TRUE' ELSE 'FALSE' END + ', ' +

    -- "Disable Auto Application" BOOLEAN NOT NULL
    CASE WHEN [Disable Auto Application] = 1 THEN 'TRUE' ELSE 'FALSE' END + ', ' +

    -- "Association No_" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Association No_], '''', ''''''), '\', '\\') + '''' + ', ' +

    -- "Association Sort Code" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Association Sort Code], '''', ''''''), '\', '\\') + '''' + ',  ' +

    -- "SearchCity" VARCHAR(30) NOT NULL
    '''' + REPLACE(REPLACE(SearchCity, '''', ''''''), '\', '\\') + '''' + ', ' +

    -- "Balance Acknowledgement Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Balance Acknowledgement Code], '''', ''''''), '\', '\\') + '''' + ', ' +

    -- "Last Posting at" TIMESTAMP NOT NULL
    '''' + CONVERT(NVARCHAR(MAX), [Last Posting at], 120) + '''' + ', ' +

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

    -- "Electronic Document Dispatch" BOOLEAN NOT NULL
    CASE WHEN [Electronic Document Dispatch] = 1 THEN 'TRUE' ELSE 'FALSE' END + ', ' +

    -- "Packing Slip Copies" INTEGER NOT NULL (LAST COLUMN, no comma after)
    CAST([Packing Slip Copies] AS NVARCHAR(MAX)) +
    ');'
FROM
    [Navision$Customer];
*/


CREATE TABLE migration_data."Navision$Customer" (
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
                                                                      "Chain Name" VARCHAR(10) NOT NULL,
                                                                      "Budgeted Amount" NUMERIC(38, 20) NOT NULL,
                                                                      "Credit Limit (LCY)" NUMERIC(38, 20) NOT NULL,
                                                                      "Customer Posting Group" VARCHAR(10) NOT NULL,
                                                                      "Currency Code" VARCHAR(10) NOT NULL,
                                                                      "Customer Price Group" VARCHAR(10) NOT NULL,
                                                                      "Language Code" VARCHAR(10) NOT NULL,
                                                                      "Statistics Group" INTEGER NOT NULL,
                                                                      "Payment Terms Code" VARCHAR(10) NOT NULL,
                                                                      "Fin_ Charge Terms Code" VARCHAR(10) NOT NULL,
                                                                      "Salesperson Code" VARCHAR(10) NOT NULL,
                                                                      "Shipment Method Code" VARCHAR(10) NOT NULL,
                                                                      "Shipping Agent Code" VARCHAR(10) NOT NULL,
                                                                      "Place of Export" VARCHAR(20) NOT NULL,
                                                                      "Invoice Disc_ Code" VARCHAR(20) NOT NULL,
                                                                      "Customer Disc_ Group" VARCHAR(20) NOT NULL,
                                                                      "Country_Region Code" VARCHAR(10) NOT NULL,
                                                                      "Collection Method" VARCHAR(20) NOT NULL,
                                                                      "Amount" NUMERIC(38, 20) NOT NULL,
                                                                      "Blocked" INTEGER NOT NULL,
                                                                      "Invoice Copies" INTEGER NOT NULL,
                                                                      "Last Statement No_" INTEGER NOT NULL,
                                                                      "Print Statements" BOOLEAN NOT NULL,
                                                                      "Bill-to Customer No_" VARCHAR(20) NOT NULL,
                                                                      "Priority" INTEGER NOT NULL,
                                                                      "Payment Method Code" VARCHAR(10) NOT NULL,
                                                                      "Last Date Modified" TIMESTAMP NOT NULL,
                                                                      "Application Method" INTEGER NOT NULL,
                                                                      "Prices Including VAT" BOOLEAN NOT NULL,
                                                                      "Location Code" VARCHAR(10) NOT NULL,
                                                                      "Fax No_" VARCHAR(30) NOT NULL,
                                                                      "Telex Answer Back" VARCHAR(20) NOT NULL,
                                                                      "VAT Registration No_" VARCHAR(20) NOT NULL,
                                                                      "Combine Shipments" BOOLEAN NOT NULL,
                                                                      "Gen_ Bus_ Posting Group" VARCHAR(10) NOT NULL,
                                                                      "Picture" BYTEA,
                                                                      "Post Code" VARCHAR(20) NOT NULL,
                                                                      "County" VARCHAR(30) NOT NULL,
                                                                      "E-Mail" VARCHAR(80) NOT NULL,
                                                                      "Home Page" VARCHAR(80) NOT NULL,
                                                                      "Reminder Terms Code" VARCHAR(10) NOT NULL,
                                                                      "No_ Series" VARCHAR(10) NOT NULL,
                                                                      "Tax Area Code" VARCHAR(20) NOT NULL,
                                                                      "Tax Liable" BOOLEAN NOT NULL,
                                                                      "VAT Bus_ Posting Group" VARCHAR(10) NOT NULL,
                                                                      "Reserve" INTEGER NOT NULL,
                                                                      "Block Payment Tolerance" BOOLEAN NOT NULL,
                                                                      "IC Partner Code" VARCHAR(20) NOT NULL,
                                                                      "Prepayment _" NUMERIC(38, 20) NOT NULL,
                                                                      "Partner Type" INTEGER NOT NULL,
                                                                      "Preferred Bank Account" VARCHAR(10) NOT NULL,
                                                                      "Cash Flow Payment Terms Code" VARCHAR(10) NOT NULL,
                                                                      "Primary Contact No_" VARCHAR(20) NOT NULL,
                                                                      "Responsibility Center" VARCHAR(10) NOT NULL,
                                                                      "Shipping Advice" INTEGER NOT NULL,
                                                                      "Shipping Time" VARCHAR(32) NOT NULL,
                                                                      "Shipping Agent Service Code" VARCHAR(10) NOT NULL,
                                                                      "Service Zone Code" VARCHAR(10) NOT NULL,
                                                                      "Allow Line Disc_" BOOLEAN NOT NULL,
                                                                      "Base Calendar Code" VARCHAR(10) NOT NULL,
                                                                      "Copy Sell-to Addr_ to Qte From" INTEGER NOT NULL,
                                                                      "Routing Code" VARCHAR(10) NOT NULL,
                                                                      "various Customer" BOOLEAN NOT NULL,
                                                                      "importet" BOOLEAN NOT NULL,
                                                                      "Dialing Code" VARCHAR(30) NOT NULL,
                                                                      "Name 3" VARCHAR(50) NOT NULL,
                                                                      "Complete Phone No_" VARCHAR(90) NOT NULL,
                                                                      "Datev Account No_" VARCHAR(20) NOT NULL,
                                                                      "Don_t Export Bank Account" BOOLEAN NOT NULL,
                                                                      "Datev Export Date" TIMESTAMP NOT NULL,
                                                                      "Skip Bank Acc_ in Bank Import" BOOLEAN NOT NULL,
                                                                      "Disable Auto Application" BOOLEAN NOT NULL,
                                                                      "Association No_" VARCHAR(20) NOT NULL,
                                                                      "Association Sort Code" VARCHAR(20) NOT NULL,
                                                                      "SearchCity" VARCHAR(30) NOT NULL,
                                                                      "Balance Acknowledgement Code" VARCHAR(10) NOT NULL,
                                                                      "Last Posting at" TIMESTAMP NOT NULL,
                                                                      "Min_ Pos_ Payment Note" INTEGER NOT NULL,
                                                                      "Default Charges" INTEGER NOT NULL,
                                                                      "Limit Lines per Head" INTEGER NOT NULL,
                                                                      "Trading Type" INTEGER NOT NULL,
                                                                      "Service No_" VARCHAR(10) NOT NULL,
                                                                      "Electronic Document Dispatch" BOOLEAN NOT NULL,
                                                                      "Packing Slip Copies" INTEGER NOT NULL
);
