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
    'INSERT INTO migration_data."Navision$Contact" (' +
    '"timestamp", "No_", "Name", "Search Name", "Name 2", "Address", "Address 2", "City", "Phone No_", "Telex No_", "Territory Code", "Currency Code", "Language Code", "Salesperson Code", "Country_Region Code", "Last Date Modified", "Fax No_", "Telex Answer Back", "VAT Registration No_", "Picture", "Post Code", "County", "E-Mail", "Home Page", "No_ Series", "Type", "Company No_", "Company Name", "Lookup Contact No_", "First Name", "Middle Name", "Surname", "Job Title", "Initials", "Extension No_", "Mobile Phone No_", "Pager", "Organizational Level Code", "Exclude from Segment", "External ID", "Correspondence Type", "Salutation Code", "Search E-Mail", "Last Time Modified", "E-Mail 2", "Importiert", "Duplicated", "Person Information", "Display Level", "Dialing Code", "Travel Time _ h", "Single Distance", "Distance Zone", "Date of First Contact", "Name 3", "Contact Information 1", "Contact Information 2", "Contact Information 3", "Contact Information 4", "Modified from", "Business Condition", "Nominal 1", "Nominal 2", "Nominal 3", "Nominal 4", "Nominal 5", "Actual 1", "Actual 2", "Actual 3", "Actual 4", "Actual 5", "Actual 6", "Nominal 6", "Sales Mio_", "Employee", "Employee Group", "Budget Last Year Group", "Budget Year Group", "Foundation Year", "Source", "Agreement Type", "Partner", "Conclusion Criterion 1", "Conclusion Criterion 2", "Conclusion Criterion 3", "Conclusion Criterion 4", "Conclusion Criterion 5", "Conclusion Criterion 6", "Conclusion Criterion 7", "Conclusion Criterion Code 1", "Conclusion Criterion Code 2", "Conclusion Criterion Code 3", "Conclusion Criterion Code 4", "Conclusion Criterion Code 5", "Conclusion Criterion Code 6", "Conclusion Criterion Code 7", "amount of quotes", "amount of orders", "Competitor 1", "Competitor 2", "Competitor 3", "Order Date as per Customer", "Date of Birth", "Privat Phone No_", "Privat E-Mail", "Privat Fax No_", "Old Sales No_", "Old Kontaktperson No_", "Canceled", "Complete Phone No_"' +
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

        -- "Phone No_" VARCHAR(30) NOT NULL
    '''' + REPLACE(REPLACE([Phone No_], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Telex No_" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Telex No_], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Territory Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Territory Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Currency Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Currency Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Language Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Language Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Salesperson Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Salesperson Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Country_Region Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Country_Region Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Last Date Modified" TIMESTAMP NOT NULL
    '''' + CONVERT(NVARCHAR(MAX), [Last Date Modified], 120) + '''' + ', ' +

        -- "Fax No_" VARCHAR(30) NOT NULL
    '''' + REPLACE(REPLACE([Fax No_], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Telex Answer Back" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Telex Answer Back], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "VAT Registration No_" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([VAT Registration No_], '''', ''''''), '\', '\\') + '''' + ', ' +

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

        -- "Type" INTEGER NOT NULL
    CAST(Type AS NVARCHAR(MAX)) + ', ' +

        -- "Company No_" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Company No_], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Company Name" VARCHAR(50) NOT NULL
    '''' + REPLACE(REPLACE([Company Name], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Lookup Contact No_" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Lookup Contact No_], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "First Name" VARCHAR(30) NOT NULL
    '''' + REPLACE(REPLACE([First Name], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Middle Name" VARCHAR(30) NOT NULL
    '''' + REPLACE(REPLACE([Middle Name], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Surname" VARCHAR(30) NOT NULL
    '''' + REPLACE(REPLACE(Surname, '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Job Title" VARCHAR(30) NOT NULL
    '''' + REPLACE(REPLACE([Job Title], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Initials" VARCHAR(30) NOT NULL
    '''' + REPLACE(REPLACE(Initials, '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Extension No_" VARCHAR(30) NOT NULL
    '''' + REPLACE(REPLACE([Extension No_], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Mobile Phone No_" VARCHAR(30) NOT NULL
    '''' + REPLACE(REPLACE([Mobile Phone No_], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Pager" VARCHAR(30) NOT NULL
    '''' + REPLACE(REPLACE(Pager, '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Organizational Level Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Organizational Level Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Exclude from Segment" BOOLEAN NOT NULL
    CASE WHEN [Exclude from Segment] = 1 THEN 'TRUE' ELSE 'FALSE' END + ', ' +

        -- "External ID" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([External ID], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Correspondence Type" INTEGER NOT NULL
    CAST([Correspondence Type] AS NVARCHAR(MAX)) + ', ' +

        -- "Salutation Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Salutation Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Search E-Mail" VARCHAR(80) NOT NULL
    '''' + REPLACE(REPLACE([Search E-Mail], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Last Time Modified" TIMESTAMP NOT NULL
    '''' + CONVERT(NVARCHAR(MAX), [Last Time Modified], 120) + '''' + ', ' +

        -- "E-Mail 2" VARCHAR(80) NOT NULL
    '''' + REPLACE(REPLACE([E-Mail 2], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Importiert" BOOLEAN NOT NULL
    CASE WHEN Importiert = 1 THEN 'TRUE' ELSE 'FALSE' END + ', ' +

        -- "Duplicated" BOOLEAN NOT NULL
    CASE WHEN Duplicated = 1 THEN 'TRUE' ELSE 'FALSE' END + ', ' +

        -- "Person Information" VARCHAR(70) NOT NULL
    '''' + REPLACE(REPLACE([Person Information], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Display Level" INTEGER NOT NULL
    CAST([Display Level] AS NVARCHAR(MAX)) + ', ' +

        -- "Dialing Code" VARCHAR(30) NOT NULL
    '''' + REPLACE(REPLACE([Dialing Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Travel Time _ h" NUMERIC(38, 20) NOT NULL
    CAST([Travel Time _ h] AS NVARCHAR(MAX)) + ', ' +

        -- "Single Distance" INTEGER NOT NULL
    CAST([Single Distance] AS NVARCHAR(MAX)) + ', ' +

        -- "Distance Zone" INTEGER NOT NULL
    CAST([Distance Zone] AS NVARCHAR(MAX)) + ', ' +

        -- "Date of First Contact" TIMESTAMP NOT NULL
    '''' + CONVERT(NVARCHAR(MAX), [Date of First Contact], 120) + '''' + ', ' +

        -- "Name 3" VARCHAR(50) NOT NULL
    '''' + REPLACE(REPLACE([Name 3], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Contact Information 1" VARCHAR(70) NOT NULL
    '''' + REPLACE(REPLACE([Contact Information 1], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Contact Information 2" VARCHAR(70) NOT NULL
    '''' + REPLACE(REPLACE([Contact Information 2], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Contact Information 3" VARCHAR(70) NOT NULL
    '''' + REPLACE(REPLACE([Contact Information 3], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Contact Information 4" VARCHAR(70) NOT NULL
    '''' + REPLACE(REPLACE([Contact Information 4], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Modified from" VARCHAR(50) NOT NULL
    '''' + REPLACE(REPLACE([Modified from], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Business Condition" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Business Condition], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Nominal 1" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Nominal 1], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Nominal 2" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Nominal 2], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Nominal 3" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Nominal 3], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Nominal 4" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Nominal 4], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Nominal 5" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Nominal 5], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Actual 1" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Actual 1], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Actual 2" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Actual 2], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Actual 3" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Actual 3], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Actual 4" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Actual 4], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Actual 5" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Actual 5], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Actual 6" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Actual 6], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Nominal 6" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Nominal 6], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Sales Mio_" NUMERIC(38, 20) NOT NULL
    CAST([Sales Mio_] AS NVARCHAR(MAX)) + ', ' +

        -- "Employee" INTEGER NOT NULL
    CAST(Employee AS NVARCHAR(MAX)) + ', ' +

        -- "Employee Group" INTEGER NOT NULL
    CAST([Employee Group] AS NVARCHAR(MAX)) + ', ' +

        -- "Budget Last Year Group" INTEGER NOT NULL
    CAST([Budget Last Year Group] AS NVARCHAR(MAX)) + ', ' +

        -- "Budget Year Group" INTEGER NOT NULL
    CAST([Budget Year Group] AS NVARCHAR(MAX)) + ', ' +

        -- "Foundation Year" INTEGER NOT NULL
    CAST([Foundation Year] AS NVARCHAR(MAX)) + ', ' +

        -- "Source" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE(Source, '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Agreement Type" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Agreement Type], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Partner" VARCHAR(50) NOT NULL
    '''' + REPLACE(REPLACE(Partner, '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Conclusion Criterion 1" NUMERIC(38, 20) NOT NULL
    CAST([Conclusion Criterion 1] AS NVARCHAR(MAX)) + ', ' +

        -- "Conclusion Criterion 2" NUMERIC(38, 20) NOT NULL
    CAST([Conclusion Criterion 2] AS NVARCHAR(MAX)) + ', ' +

        -- "Conclusion Criterion 3" NUMERIC(38, 20) NOT NULL
    CAST([Conclusion Criterion 3] AS NVARCHAR(MAX)) + ', ' +

        -- "Conclusion Criterion 4" NUMERIC(38, 20) NOT NULL
    CAST([Conclusion Criterion 4] AS NVARCHAR(MAX)) + ', ' +

        -- "Conclusion Criterion 5" NUMERIC(38, 20) NOT NULL
    CAST([Conclusion Criterion 5] AS NVARCHAR(MAX)) + ', ' +

        -- "Conclusion Criterion 6" NUMERIC(38, 20) NOT NULL
    CAST([Conclusion Criterion 6] AS NVARCHAR(MAX)) + ', ' +

        -- "Conclusion Criterion 7" NUMERIC(38, 20) NOT NULL
    CAST([Conclusion Criterion 7] AS NVARCHAR(MAX)) + ', ' +

        -- "Conclusion Criterion Code 1" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Conclusion Criterion Code 1], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Conclusion Criterion Code 2" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Conclusion Criterion Code 2], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Conclusion Criterion Code 3" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Conclusion Criterion Code 3], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Conclusion Criterion Code 4" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Conclusion Criterion Code 4], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Conclusion Criterion Code 5" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Conclusion Criterion Code 5], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Conclusion Criterion Code 6" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Conclusion Criterion Code 6], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Conclusion Criterion Code 7" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Conclusion Criterion Code 7], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "amount of quotes" INTEGER NOT NULL
    CAST([amount of quotes] AS NVARCHAR(MAX)) + ', ' +

        -- "amount of orders" INTEGER NOT NULL
    CAST([amount of orders] AS NVARCHAR(MAX)) + ', ' +

        -- "Competitor 1" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Competitor 1], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Competitor 2" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Competitor 2], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Competitor 3" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Competitor 3], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Order Date as per Customer" VARCHAR(5) NOT NULL
    '''' + REPLACE(REPLACE([Order Date as per  Customer], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Date of Birth" TIMESTAMP NOT NULL
    '''' + CONVERT(NVARCHAR(MAX), [Date of Birth], 120) + '''' + ', ' +

        -- "Privat Phone No_" VARCHAR(30) NOT NULL
    '''' + REPLACE(REPLACE([Privat Phone No_], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Privat E-Mail" VARCHAR(100) NOT NULL
    '''' + REPLACE(REPLACE([Privat E-Mail], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Privat Fax No_" VARCHAR(30) NOT NULL
    '''' + REPLACE(REPLACE([Privat Fax No_], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Old Sales No_" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Old Sales No_], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Old Kontaktperson No_" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Old Kontaktperson No_], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Canceled" BOOLEAN NOT NULL
    CASE WHEN Canceled = 1 THEN 'TRUE' ELSE 'FALSE' END + ', ' +

        -- "Complete Phone No_" VARCHAR(90) NOT NULL (LAST COLUMN, no comma after)
    '''' + REPLACE(REPLACE([Complete Phone No_], '''', ''''''), '\', '\\') + '''' +
    ');'
FROM
    [Navision$Contact];
*/

CREATE TABLE migration_data."Navision$Contact" (
    "timestamp" BYTEA NOT NULL,
    "No_" VARCHAR(20) PRIMARY KEY,
    "Name" VARCHAR(50) NOT NULL,
    "Search Name" VARCHAR(50) NOT NULL,
    "Name 2" VARCHAR(50) NOT NULL,
    "Address" VARCHAR(50) NOT NULL,
    "Address 2" VARCHAR(50) NOT NULL,
    "City" VARCHAR(30) NOT NULL,
    "Phone No_" VARCHAR(30) NOT NULL,
    "Telex No_" VARCHAR(20) NOT NULL,
    "Territory Code" VARCHAR(10) NOT NULL,
    "Currency Code" VARCHAR(10) NOT NULL,
    "Language Code" VARCHAR(10) NOT NULL,
    "Salesperson Code" VARCHAR(10) NOT NULL,
    "Country_Region Code" VARCHAR(10) NOT NULL,
    "Last Date Modified" TIMESTAMP NOT NULL,
    "Fax No_" VARCHAR(30) NOT NULL,
    "Telex Answer Back" VARCHAR(20) NOT NULL,
    "VAT Registration No_" VARCHAR(20) NOT NULL,
    "Picture" BYTEA,
    "Post Code" VARCHAR(20) NOT NULL,
    "County" VARCHAR(30) NOT NULL,
    "E-Mail" VARCHAR(80) NOT NULL,
    "Home Page" VARCHAR(80) NOT NULL,
    "No_ Series" VARCHAR(10) NOT NULL,
    "Type" INTEGER NOT NULL,
    "Company No_" VARCHAR(20) NOT NULL,
    "Company Name" VARCHAR(50) NOT NULL,
    "Lookup Contact No_" VARCHAR(20) NOT NULL,
    "First Name" VARCHAR(30) NOT NULL,
    "Middle Name" VARCHAR(30) NOT NULL,
    "Surname" VARCHAR(30) NOT NULL,
    "Job Title" VARCHAR(30) NOT NULL,
    "Initials" VARCHAR(30) NOT NULL,
    "Extension No_" VARCHAR(30) NOT NULL,
    "Mobile Phone No_" VARCHAR(30) NOT NULL,
    "Pager" VARCHAR(30) NOT NULL,
    "Organizational Level Code" VARCHAR(10) NOT NULL,
    "Exclude from Segment" BOOLEAN NOT NULL,
    "External ID" VARCHAR(20) NOT NULL,
    "Correspondence Type" INTEGER NOT NULL,
    "Salutation Code" VARCHAR(10) NOT NULL,
    "Search E-Mail" VARCHAR(80) NOT NULL,
    "Last Time Modified" TIMESTAMP NOT NULL,
    "E-Mail 2" VARCHAR(80) NOT NULL,
    "Importiert" BOOLEAN NOT NULL,
    "Duplicated" BOOLEAN NOT NULL,
    "Person Information" VARCHAR(70) NOT NULL,
    "Display Level" INTEGER NOT NULL,
    "Dialing Code" VARCHAR(30) NOT NULL,
    "Travel Time _ h" NUMERIC(38, 20) NOT NULL,
    "Single Distance" INTEGER NOT NULL,
    "Distance Zone" INTEGER NOT NULL,
    "Date of First Contact" TIMESTAMP NOT NULL,
    "Name 3" VARCHAR(50) NOT NULL,
    "Contact Information 1" VARCHAR(70) NOT NULL,
    "Contact Information 2" VARCHAR(70) NOT NULL,
    "Contact Information 3" VARCHAR(70) NOT NULL,
    "Contact Information 4" VARCHAR(70) NOT NULL,
    "Modified from" VARCHAR(50) NOT NULL,
    "Business Condition" VARCHAR(10) NOT NULL,
    "Nominal 1" VARCHAR(10) NOT NULL,
    "Nominal 2" VARCHAR(10) NOT NULL,
    "Nominal 3" VARCHAR(10) NOT NULL,
    "Nominal 4" VARCHAR(10) NOT NULL,
    "Nominal 5" VARCHAR(10) NOT NULL,
    "Actual 1" VARCHAR(10) NOT NULL,
    "Actual 2" VARCHAR(10) NOT NULL,
    "Actual 3" VARCHAR(10) NOT NULL,
    "Actual 4" VARCHAR(10) NOT NULL,
    "Actual 5" VARCHAR(10) NOT NULL,
    "Actual 6" VARCHAR(10) NOT NULL,
    "Nominal 6" VARCHAR(10) NOT NULL,
    "Sales Mio_" NUMERIC(38, 20) NOT NULL,
    "Employee" INTEGER NOT NULL,
    "Employee Group" INTEGER NOT NULL,
    "Budget Last Year Group" INTEGER NOT NULL,
    "Budget Year Group" INTEGER NOT NULL,
    "Foundation Year" INTEGER NOT NULL,
    "Source" VARCHAR(10) NOT NULL,
    "Agreement Type" VARCHAR(10) NOT NULL,
    "Partner" VARCHAR(50) NOT NULL,
    "Conclusion Criterion 1" NUMERIC(38, 20) NOT NULL,
    "Conclusion Criterion 2" NUMERIC(38, 20) NOT NULL,
    "Conclusion Criterion 3" NUMERIC(38, 20) NOT NULL,
    "Conclusion Criterion 4" NUMERIC(38, 20) NOT NULL,
    "Conclusion Criterion 5" NUMERIC(38, 20) NOT NULL,
    "Conclusion Criterion 6" NUMERIC(38, 20) NOT NULL,
    "Conclusion Criterion 7" NUMERIC(38, 20) NOT NULL,
    "Conclusion Criterion Code 1" VARCHAR(10) NOT NULL,
    "Conclusion Criterion Code 2" VARCHAR(10) NOT NULL,
    "Conclusion Criterion Code 3" VARCHAR(10) NOT NULL,
    "Conclusion Criterion Code 4" VARCHAR(10) NOT NULL,
    "Conclusion Criterion Code 5" VARCHAR(10) NOT NULL,
    "Conclusion Criterion Code 6" VARCHAR(10) NOT NULL,
    "Conclusion Criterion Code 7" VARCHAR(10) NOT NULL,
    "amount of quotes" INTEGER NOT NULL,
    "amount of orders" INTEGER NOT NULL,
    "Competitor 1" VARCHAR(10) NOT NULL,
    "Competitor 2" VARCHAR(10) NOT NULL,
    "Competitor 3" VARCHAR(10) NOT NULL,
    "Order Date as per Customer" VARCHAR(5) NOT NULL,
    "Date of Birth" TIMESTAMP NOT NULL,
    "Privat Phone No_" VARCHAR(30) NOT NULL,
    "Privat E-Mail" VARCHAR(100) NOT NULL,
    "Privat Fax No_" VARCHAR(30) NOT NULL,
    "Old Sales No_" VARCHAR(20) NOT NULL,
    "Old Kontaktperson No_" VARCHAR(20) NOT NULL,
    "Canceled" BOOLEAN NOT NULL,
    "Complete Phone No_" VARCHAR(90) NOT NULL
);
