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
    'INSERT INTO migration_data."Navision$Item" (' +
    '"timestamp", "No_", "No_ 2", "Description", "Search Description", "Description 2", "Base Unit of Measure", "Price Unit Conversion", "Type", "Inventory Posting Group", "Shelf No_", "Item Disc_ Group", "Allow Invoice Disc_", "Statistics Group", "Commission Group", "Unit Price", "Price_Profit Calculation", "Profit _", "Costing Method", "Unit Cost", "Standard Cost", "Last Direct Cost", "Indirect Cost _", "Cost is Adjusted", "Allow Online Adjustment", "Vendor No_", "Vendor Item No_", "Lead Time Calculation", "Reorder Point", "Maximum Inventory", "Reorder Quantity", "Alternative Item No_", "Unit List Price", "Duty Due _", "Duty Code", "Gross Weight", "Net Weight", "Units per Parcel", "Unit Volume", "Durability", "Freight Type", "Tariff No_", "Duty Unit Conversion", "Country_Region Purchased Code", "Budget Quantity", "Budgeted Amount", "Budget Profit", "Blocked", "Last Date Modified", "Price Includes VAT", "VAT Bus_ Posting Gr_ (Price)", "Gen_ Prod_ Posting Group", "Picture", "Country_Region of Origin Code", "Automatic Ext_ Texts", "No_ Series", "Tax Group Code", "VAT Prod_ Posting Group", "Reserve", "Global Dimension 1 Code", "Global Dimension 2 Code", "Stockout Warning", "Prevent Negative Inventory", "Application Wksh_ User ID", "Assembly Policy", "Low-Level Code", "Lot Size", "Serial Nos_", "Last Unit Cost Calc_ Date", "Rolled-up Material Cost", "Rolled-up Capacity Cost", "Scrap _", "Inventory Value Zero", "Discrete Order Quantity", "Minimum Order Quantity", "Maximum Order Quantity", "Safety Stock Quantity", "Order Multiple", "Safety Lead Time", "Flushing Method", "Replenishment System", "Rounding Precision", "Sales Unit of Measure", "Purch_ Unit of Measure", "Time Bucket", "Reordering Policy", "Include Inventory", "Manufacturing Policy", "Rescheduling Period", "Lot Accumulation Period", "Dampener Period", "Dampener Quantity", "Overflow Level", "Manufacturer Code", "Item Category Code", "Created From Nonstock Item", "Product Group Code", "Service Item Group", "Item Tracking Code", "Lot Nos_", "Expiration Calculation", "Special Equipment Code", "Put-away Template Code", "Put-away Unit of Measure Code", "Phys Invt Counting Period Code", "Last Counting Period Update", "Next Counting Period", "Use Cross-Docking", "Next Counting Start Date", "Next Counting End Date", "Description 3", "No Commission Statement", "Itemtype", "Routing No_", "Production BOM No_", "Single-Level Material Cost", "Single-Level Capacity Cost", "Single-Level Subcontrd_ Cost", "Single-Level Cap_ Ovhd Cost", "Single-Level Mfg_ Ovhd Cost", "Overhead Rate", "Rolled-up Subcontracted Cost", "Rolled-up Mfg_ Ovhd Cost", "Rolled-up Cap_ Overhead Cost", "Order Tracking Policy", "Critical", "Common Item No_"' +
    ') VALUES (' +

        -- "timestamp" BYTEA NOT NULL
    'E''\\x' + SUBSTRING(sys.fn_varbintohexstr(timestamp), 3, DATALENGTH(timestamp) * 2) + '''' + ', ' +

        -- "No_" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE(No_, '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "No_ 2" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([No_ 2], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Description" VARCHAR(50) NOT NULL
    '''' + REPLACE(REPLACE(Description, '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Search Description" VARCHAR(50) NOT NULL
    '''' + REPLACE(REPLACE([Search Description], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Description 2" VARCHAR(100) NOT NULL
    '''' + REPLACE(REPLACE([Description 2], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Base Unit of Measure" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Base Unit of Measure], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Price Unit Conversion" INTEGER NOT NULL
    CAST([Price Unit Conversion] AS NVARCHAR(MAX)) + ', ' +

        -- "Type" INTEGER NOT NULL
    CAST(Type AS NVARCHAR(MAX)) + ', ' +

        -- "Inventory Posting Group" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Inventory Posting Group], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Shelf No_" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Shelf No_], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Item Disc_ Group" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Item Disc_ Group], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Allow Invoice Disc_" BOOLEAN NOT NULL
    CASE WHEN [Allow Invoice Disc_] = 1 THEN 'TRUE' ELSE 'FALSE' END + ', ' +

        -- "Statistics Group" INTEGER NOT NULL
    CAST([Statistics Group] AS NVARCHAR(MAX)) + ', ' +

        -- "Commission Group" INTEGER NOT NULL
    CAST([Commission Group] AS NVARCHAR(MAX)) + ', ' +

        -- "Unit Price" NUMERIC(38, 20) NOT NULL
    CAST([Unit Price] AS NVARCHAR(MAX)) + ', ' +

        -- "Price_Profit Calculation" INTEGER NOT NULL
    CAST([Price_Profit Calculation] AS NVARCHAR(MAX)) + ', ' +

        -- "Profit _" NUMERIC(38, 20) NOT NULL
    CAST([Profit _] AS NVARCHAR(MAX)) + ', ' +

        -- "Costing Method" INTEGER NOT NULL
    CAST([Costing Method] AS NVARCHAR(MAX)) + ', ' +

        -- "Unit Cost" NUMERIC(38, 20) NOT NULL
    CAST([Unit Cost] AS NVARCHAR(MAX)) + ', ' +

        -- "Standard Cost" NUMERIC(38, 20) NOT NULL
    CAST([Standard Cost] AS NVARCHAR(MAX)) + ', ' +

        -- "Last Direct Cost" NUMERIC(38, 20) NOT NULL
    CAST([Last Direct Cost] AS NVARCHAR(MAX)) + ', ' +

        -- "Indirect Cost _" NUMERIC(38, 20) NOT NULL
    CAST([Indirect Cost _] AS NVARCHAR(MAX)) + ', ' +

        -- "Cost is Adjusted" BOOLEAN NOT NULL
    CASE WHEN [Cost is Adjusted] = 1 THEN 'TRUE' ELSE 'FALSE' END + ', ' +

        -- "Allow Online Adjustment" BOOLEAN NOT NULL
    CASE WHEN [Allow Online Adjustment] = 1 THEN 'TRUE' ELSE 'FALSE' END + ', ' +

        -- "Vendor No_" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Vendor No_], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Vendor Item No_" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Vendor Item No_], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Lead Time Calculation" VARCHAR(32) NOT NULL
    '''' + REPLACE(REPLACE([Lead Time Calculation], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Reorder Point" NUMERIC(38, 20) NOT NULL
    CAST([Reorder Point] AS NVARCHAR(MAX)) + ', ' +

        -- "Maximum Inventory" NUMERIC(38, 20) NOT NULL
    CAST([Maximum Inventory] AS NVARCHAR(MAX)) + ', ' +

        -- "Reorder Quantity" NUMERIC(38, 20) NOT NULL
    CAST([Reorder Quantity] AS NVARCHAR(MAX)) + ', ' +

        -- "Alternative Item No_" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Alternative Item No_], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Unit List Price" NUMERIC(38, 20) NOT NULL
    CAST([Unit List Price] AS NVARCHAR(MAX)) + ', ' +

        -- "Duty Due _" NUMERIC(38, 20) NOT NULL
    CAST([Duty Due _] AS NVARCHAR(MAX)) + ', ' +

        -- "Duty Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Duty Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Gross Weight" NUMERIC(38, 20) NOT NULL
    CAST([Gross Weight] AS NVARCHAR(MAX)) + ', ' +

        -- "Net Weight" NUMERIC(38, 20) NOT NULL
    CAST([Net Weight] AS NVARCHAR(MAX)) + ', ' +

        -- "Units per Parcel" NUMERIC(38, 20) NOT NULL
    CAST([Units per Parcel] AS NVARCHAR(MAX)) + ', ' +

        -- "Unit Volume" NUMERIC(38, 20) NOT NULL
    CAST([Unit Volume] AS NVARCHAR(MAX)) + ', ' +

        -- "Durability" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE(Durability, '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Freight Type" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Freight Type], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Tariff No_" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Tariff No_], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Duty Unit Conversion" NUMERIC(38, 20) NOT NULL
    CAST([Duty Unit Conversion] AS NVARCHAR(MAX)) + ', ' +

        -- "Country_Region Purchased Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Country_Region Purchased Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Budget Quantity" NUMERIC(38, 20) NOT NULL
    CAST([Budget Quantity] AS NVARCHAR(MAX)) + ', ' +

        -- "Budgeted Amount" NUMERIC(38, 20) NOT NULL
    CAST([Budgeted Amount] AS NVARCHAR(MAX)) + ', ' +

        -- "Budget Profit" NUMERIC(38, 20) NOT NULL
    CAST([Budget Profit] AS NVARCHAR(MAX)) + ', ' +

        -- "Blocked" BOOLEAN NOT NULL
    CASE WHEN Blocked = 1 THEN 'TRUE' ELSE 'FALSE' END + ', ' +

        -- "Last Date Modified" TIMESTAMP NOT NULL
    '''' + CONVERT(NVARCHAR(MAX), [Last Date Modified], 120) + '''' + ', ' +

        -- "Price Includes VAT" BOOLEAN NOT NULL
    CASE WHEN [Price Includes VAT] = 1 THEN 'TRUE' ELSE 'FALSE' END + ', ' +

        -- "VAT Bus_ Posting Gr_ (Price)" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([VAT Bus_ Posting Gr_ (Price)], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Gen_ Prod_ Posting Group" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Gen_ Prod_ Posting Group], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Picture" BYTEA
    CASE WHEN Picture IS NULL THEN 'NULL' ELSE 'E''\\x' + SUBSTRING(sys.fn_varbintohexstr(CONVERT(VARBINARY(MAX), Picture)), 3, DATALENGTH(CONVERT(VARBINARY(MAX), Picture)) * 2) + '''' END + ', ' +

        -- "Country_Region of Origin Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Country_Region of Origin Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Automatic Ext_ Texts" BOOLEAN NOT NULL
    CASE WHEN [Automatic Ext_ Texts] = 1 THEN 'TRUE' ELSE 'FALSE' END + ', ' +

        -- "No_ Series" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([No_ Series], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Tax Group Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Tax Group Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "VAT Prod_ Posting Group" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([VAT Prod_ Posting Group], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Reserve" INTEGER NOT NULL
    CAST(Reserve AS NVARCHAR(MAX)) + ', ' +

        -- "Global Dimension 1 Code" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Global Dimension 1 Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Global Dimension 2 Code" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Global Dimension 2 Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Stockout Warning" INTEGER NOT NULL
    CAST([Stockout Warning] AS NVARCHAR(MAX)) + ', ' +

        -- "Prevent Negative Inventory" INTEGER NOT NULL
    CAST([Prevent Negative Inventory] AS NVARCHAR(MAX)) + ', ' +

        -- "Application Wksh_ User ID" VARCHAR(128) NOT NULL
    '''' + REPLACE(REPLACE([Application Wksh_ User ID], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Assembly Policy" INTEGER NOT NULL
    CAST([Assembly Policy] AS NVARCHAR(MAX)) + ', ' +

        -- "Low-Level Code" INTEGER NOT NULL
    CAST([Low-Level Code] AS NVARCHAR(MAX)) + ', ' +

        -- "Lot Size" NUMERIC(38, 20) NOT NULL
    CAST([Lot Size] AS NVARCHAR(MAX)) + ', ' +

        -- "Serial Nos_" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Serial Nos_], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Last Unit Cost Calc_ Date" TIMESTAMP NOT NULL
    '''' + CONVERT(NVARCHAR(MAX), [Last Unit Cost Calc_ Date], 120) + '''' + ', ' +

        -- "Rolled-up Material Cost" NUMERIC(38, 20) NOT NULL
    CAST([Rolled-up Material Cost] AS NVARCHAR(MAX)) + ', ' +

        -- "Rolled-up Capacity Cost" NUMERIC(38, 20) NOT NULL
    CAST([Rolled-up Capacity Cost] AS NVARCHAR(MAX)) + ', ' +

        -- "Scrap _" NUMERIC(38, 20) NOT NULL
    CAST([Scrap _] AS NVARCHAR(MAX)) + ', ' +

        -- "Inventory Value Zero" BOOLEAN NOT NULL
    CASE WHEN [Inventory Value Zero] = 1 THEN 'TRUE' ELSE 'FALSE' END + ', ' +

        -- "Discrete Order Quantity" INTEGER NOT NULL
    CAST([Discrete Order Quantity] AS NVARCHAR(MAX)) + ', ' +

        -- "Minimum Order Quantity" NUMERIC(38, 20) NOT NULL
    CAST([Minimum Order Quantity] AS NVARCHAR(MAX)) + ', ' +

        -- "Maximum Order Quantity" NUMERIC(38, 20) NOT NULL
    CAST([Maximum Order Quantity] AS NVARCHAR(MAX)) + ', ' +

        -- "Safety Stock Quantity" NUMERIC(38, 20) NOT NULL
    CAST([Safety Stock Quantity] AS NVARCHAR(MAX)) + ', ' +

        -- "Order Multiple" NUMERIC(38, 20) NOT NULL
    CAST([Order Multiple] AS NVARCHAR(MAX)) + ', ' +

        -- "Safety Lead Time" VARCHAR(32) NOT NULL
    '''' + REPLACE(REPLACE([Safety Lead Time], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Flushing Method" INTEGER NOT NULL
    CAST([Flushing Method] AS NVARCHAR(MAX)) + ', ' +

        -- "Replenishment System" INTEGER NOT NULL
    CAST([Replenishment System] AS NVARCHAR(MAX)) + ', ' +

        -- "Rounding Precision" NUMERIC(38, 20) NOT NULL
    CAST([Rounding Precision] AS NVARCHAR(MAX)) + ', ' +

        -- "Sales Unit of Measure" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Sales Unit of Measure], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Purch_ Unit of Measure" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Purch_ Unit of Measure], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Time Bucket" VARCHAR(32) NOT NULL
    '''' + REPLACE(REPLACE([Time Bucket], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Reordering Policy" INTEGER NOT NULL
    CAST([Reordering Policy] AS NVARCHAR(MAX)) + ', ' +

        -- "Include Inventory" BOOLEAN NOT NULL
    CASE WHEN [Include Inventory] = 1 THEN 'TRUE' ELSE 'FALSE' END + ', ' +

        -- "Manufacturing Policy" INTEGER NOT NULL
    CAST([Manufacturing Policy] AS NVARCHAR(MAX)) + ', ' +

        -- "Rescheduling Period" VARCHAR(32) NOT NULL
    '''' + REPLACE(REPLACE([Rescheduling Period], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Lot Accumulation Period" VARCHAR(32) NOT NULL
    '''' + REPLACE(REPLACE([Lot Accumulation Period], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Dampener Period" VARCHAR(32) NOT NULL
    '''' + REPLACE(REPLACE([Dampener Period], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Dampener Quantity" NUMERIC(38, 20) NOT NULL
    CAST([Dampener Quantity] AS NVARCHAR(MAX)) + ', ' +

        -- "Overflow Level" NUMERIC(38, 20) NOT NULL
    CAST([Overflow Level] AS NVARCHAR(MAX)) + ', ' +

        -- "Manufacturer Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Manufacturer Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Item Category Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Item Category Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Created From Nonstock Item" BOOLEAN NOT NULL
    CASE WHEN [Created From Nonstock Item] = 1 THEN 'TRUE' ELSE 'FALSE' END + ', ' +

        -- "Product Group Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Product Group Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Service Item Group" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Service Item Group], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Item Tracking Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Item Tracking Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Lot Nos_" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Lot Nos_], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Expiration Calculation" VARCHAR(32) NOT NULL
    '''' + REPLACE(REPLACE([Expiration Calculation], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Special Equipment Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Special Equipment Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Put-away Template Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Put-away Template Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Put-away Unit of Measure Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Put-away Unit of Measure Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Phys Invt Counting Period Code" VARCHAR(10) NOT NULL
    '''' + REPLACE(REPLACE([Phys Invt Counting Period Code], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Last Counting Period Update" TIMESTAMP NOT NULL
    '''' + CONVERT(NVARCHAR(MAX), [Last Counting Period Update], 120) + '''' + ', ' +

        -- "Next Counting Period" VARCHAR(250) NOT NULL
    '''' + REPLACE(REPLACE([Next Counting Period], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Use Cross-Docking" BOOLEAN NOT NULL
    CASE WHEN [Use Cross-Docking] = 1 THEN 'TRUE' ELSE 'FALSE' END + ', ' +

        -- "Next Counting Start Date" TIMESTAMP NOT NULL
    '''' + CONVERT(NVARCHAR(MAX), [Next Counting Start Date], 120) + '''' + ', ' +

        -- "Next Counting End Date" TIMESTAMP NOT NULL
    '''' + CONVERT(NVARCHAR(MAX), [Next Counting End Date], 120) + '''' + ', ' +

        -- "Description 3" VARCHAR(100) NOT NULL
    '''' + REPLACE(REPLACE([Description 3], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "No Commission Statement" BOOLEAN NOT NULL
    CASE WHEN [No Commission Statement] = 1 THEN 'TRUE' ELSE 'FALSE' END + ', ' +

        -- "Itemtype" INTEGER NOT NULL
    CAST(Itemtype AS NVARCHAR(MAX)) + ', ' +

        -- "Routing No_" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Routing No_], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Production BOM No_" VARCHAR(20) NOT NULL
    '''' + REPLACE(REPLACE([Production BOM No_], '''', ''''''), '\', '\\') + '''' + ', ' +

        -- "Single-Level Material Cost" NUMERIC(38, 20) NOT NULL
    CAST([Single-Level Material Cost] AS NVARCHAR(MAX)) + ', ' +

        -- "Single-Level Capacity Cost" NUMERIC(38, 20) NOT NULL
    CAST([Single-Level Capacity Cost] AS NVARCHAR(MAX)) + ', ' +

        -- "Single-Level Subcontrd_ Cost" NUMERIC(38, 20) NOT NULL
    CAST([Single-Level Subcontrd_ Cost] AS NVARCHAR(MAX)) + ', ' +

        -- "Single-Level Cap_ Ovhd Cost" NUMERIC(38, 20) NOT NULL
    CAST([Single-Level Cap_ Ovhd Cost] AS NVARCHAR(MAX)) + ', ' +

        -- "Single-Level Mfg_ Ovhd Cost" NUMERIC(38, 20) NOT NULL
    CAST([Single-Level Mfg_ Ovhd Cost] AS NVARCHAR(MAX)) + ', ' +

        -- "Overhead Rate" NUMERIC(38, 20) NOT NULL
    CAST([Overhead Rate] AS NVARCHAR(MAX)) + ', ' +

        -- "Rolled-up Subcontracted Cost" NUMERIC(38, 20) NOT NULL
    CAST([Rolled-up Subcontracted Cost] AS NVARCHAR(MAX)) + ', ' +

        -- "Rolled-up Mfg_ Ovhd Cost" NUMERIC(38, 20) NOT NULL
    CAST([Rolled-up Mfg_ Ovhd Cost] AS NVARCHAR(MAX)) + ', ' +

        -- "Rolled-up Cap_ Overhead Cost" NUMERIC(38, 20) NOT NULL
    CAST([Rolled-up Cap_ Overhead Cost] AS NVARCHAR(MAX)) + ', ' +

        -- "Order Tracking Policy" INTEGER NOT NULL
    CAST([Order Tracking Policy] AS NVARCHAR(MAX)) + ', ' +

        -- "Critical" BOOLEAN NOT NULL
    CASE WHEN Critical = 1 THEN 'TRUE' ELSE 'FALSE' END + ', ' +

        -- "Common Item No_" VARCHAR(20) NOT NULL (LAST COLUMN, no comma after)
    '''' + REPLACE(REPLACE([Common Item No_], '''', ''''''), '\', '\\') + '''' +
    ');'
FROM
    [Navision$Item];


*/

CREATE TABLE migration_data."Navision$Item" (
    "timestamp" BYTEA NOT NULL,
    "No_" VARCHAR(20) PRIMARY KEY,
    "No_ 2" VARCHAR(20) NOT NULL,
    "Description" VARCHAR(50) NOT NULL,
    "Search Description" VARCHAR(50) NOT NULL,
    "Description 2" VARCHAR(100) NOT NULL,
    "Base Unit of Measure" VARCHAR(10) NOT NULL,
    "Price Unit Conversion" INTEGER NOT NULL,
    "Type" INTEGER NOT NULL,
    "Inventory Posting Group" VARCHAR(10) NOT NULL,
    "Shelf No_" VARCHAR(10) NOT NULL,
    "Item Disc_ Group" VARCHAR(20) NOT NULL,
    "Allow Invoice Disc_" BOOLEAN NOT NULL,
    "Statistics Group" INTEGER NOT NULL,
    "Commission Group" INTEGER NOT NULL,
    "Unit Price" NUMERIC(38, 20) NOT NULL,
    "Price_Profit Calculation" INTEGER NOT NULL,
    "Profit _" NUMERIC(38, 20) NOT NULL,
    "Costing Method" INTEGER NOT NULL,
    "Unit Cost" NUMERIC(38, 20) NOT NULL,
    "Standard Cost" NUMERIC(38, 20) NOT NULL,
    "Last Direct Cost" NUMERIC(38, 20) NOT NULL,
    "Indirect Cost _" NUMERIC(38, 20) NOT NULL,
    "Cost is Adjusted" BOOLEAN NOT NULL,
    "Allow Online Adjustment" BOOLEAN NOT NULL,
    "Vendor No_" VARCHAR(20) NOT NULL,
    "Vendor Item No_" VARCHAR(20) NOT NULL,
    "Lead Time Calculation" VARCHAR(32) NOT NULL,
    "Reorder Point" NUMERIC(38, 20) NOT NULL,
    "Maximum Inventory" NUMERIC(38, 20) NOT NULL,
    "Reorder Quantity" NUMERIC(38, 20) NOT NULL,
    "Alternative Item No_" VARCHAR(20) NOT NULL,
    "Unit List Price" NUMERIC(38, 20) NOT NULL,
    "Duty Due _" NUMERIC(38, 20) NOT NULL,
    "Duty Code" VARCHAR(10) NOT NULL,
    "Gross Weight" NUMERIC(38, 20) NOT NULL,
    "Net Weight" NUMERIC(38, 20) NOT NULL,
    "Units per Parcel" NUMERIC(38, 20) NOT NULL,
    "Unit Volume" NUMERIC(38, 20) NOT NULL,
    "Durability" VARCHAR(10) NOT NULL,
    "Freight Type" VARCHAR(10) NOT NULL,
    "Tariff No_" VARCHAR(20) NOT NULL,
    "Duty Unit Conversion" NUMERIC(38, 20) NOT NULL,
    "Country_Region Purchased Code" VARCHAR(10) NOT NULL,
    "Budget Quantity" NUMERIC(38, 20) NOT NULL,
    "Budgeted Amount" NUMERIC(38, 20) NOT NULL,
    "Budget Profit" NUMERIC(38, 20) NOT NULL,
    "Blocked" BOOLEAN NOT NULL,
    "Last Date Modified" TIMESTAMP NOT NULL,
    "Price Includes VAT" BOOLEAN NOT NULL,
    "VAT Bus_ Posting Gr_ (Price)" VARCHAR(10) NOT NULL,
    "Gen_ Prod_ Posting Group" VARCHAR(10) NOT NULL,
    "Picture" BYTEA,
    "Country_Region of Origin Code" VARCHAR(10) NOT NULL,
    "Automatic Ext_ Texts" BOOLEAN NOT NULL,
    "No_ Series" VARCHAR(10) NOT NULL,
    "Tax Group Code" VARCHAR(10) NOT NULL,
    "VAT Prod_ Posting Group" VARCHAR(10) NOT NULL,
    "Reserve" INTEGER NOT NULL,
    "Global Dimension 1 Code" VARCHAR(20) NOT NULL,
    "Global Dimension 2 Code" VARCHAR(20) NOT NULL,
    "Stockout Warning" INTEGER NOT NULL,
    "Prevent Negative Inventory" INTEGER NOT NULL,
    "Application Wksh_ User ID" VARCHAR(128) NOT NULL,
    "Assembly Policy" INTEGER NOT NULL,
    "Low-Level Code" INTEGER NOT NULL,
    "Lot Size" NUMERIC(38, 20) NOT NULL,
    "Serial Nos_" VARCHAR(10) NOT NULL,
    "Last Unit Cost Calc_ Date" TIMESTAMP NOT NULL,
    "Rolled-up Material Cost" NUMERIC(38, 20) NOT NULL,
    "Rolled-up Capacity Cost" NUMERIC(38, 20) NOT NULL,
    "Scrap _" NUMERIC(38, 20) NOT NULL,
    "Inventory Value Zero" BOOLEAN NOT NULL,
    "Discrete Order Quantity" INTEGER NOT NULL,
    "Minimum Order Quantity" NUMERIC(38, 20) NOT NULL,
    "Maximum Order Quantity" NUMERIC(38, 20) NOT NULL,
    "Safety Stock Quantity" NUMERIC(38, 20) NOT NULL,
    "Order Multiple" NUMERIC(38, 20) NOT NULL,
    "Safety Lead Time" VARCHAR(32) NOT NULL,
    "Flushing Method" INTEGER NOT NULL,
    "Replenishment System" INTEGER NOT NULL,
    "Rounding Precision" NUMERIC(38, 20) NOT NULL,
    "Sales Unit of Measure" VARCHAR(10) NOT NULL,
    "Purch_ Unit of Measure" VARCHAR(10) NOT NULL,
    "Time Bucket" VARCHAR(32) NOT NULL,
    "Reordering Policy" INTEGER NOT NULL,
    "Include Inventory" BOOLEAN NOT NULL,
    "Manufacturing Policy" INTEGER NOT NULL,
    "Rescheduling Period" VARCHAR(32) NOT NULL,
    "Lot Accumulation Period" VARCHAR(32) NOT NULL,
    "Dampener Period" VARCHAR(32) NOT NULL,
    "Dampener Quantity" NUMERIC(38, 20) NOT NULL,
    "Overflow Level" NUMERIC(38, 20) NOT NULL,
    "Manufacturer Code" VARCHAR(10) NOT NULL,
    "Item Category Code" VARCHAR(10) NOT NULL,
    "Created From Nonstock Item" BOOLEAN NOT NULL,
    "Product Group Code" VARCHAR(10) NOT NULL,
    "Service Item Group" VARCHAR(10) NOT NULL,
    "Item Tracking Code" VARCHAR(10) NOT NULL,
    "Lot Nos_" VARCHAR(10) NOT NULL,
    "Expiration Calculation" VARCHAR(32) NOT NULL,
    "Special Equipment Code" VARCHAR(10) NOT NULL,
    "Put-away Template Code" VARCHAR(10) NOT NULL,
    "Put-away Unit of Measure Code" VARCHAR(10) NOT NULL,
    "Phys Invt Counting Period Code" VARCHAR(10) NOT NULL,
    "Last Counting Period Update" TIMESTAMP NOT NULL,
    "Next Counting Period" VARCHAR(250) NOT NULL,
    "Use Cross-Docking" BOOLEAN NOT NULL,
    "Next Counting Start Date" TIMESTAMP NOT NULL,
    "Next Counting End Date" TIMESTAMP NOT NULL,
    "Description 3" VARCHAR(100) NOT NULL,
    "No Commission Statement" BOOLEAN NOT NULL,
    "Itemtype" INTEGER NOT NULL,
    "Routing No_" VARCHAR(20) NOT NULL,
    "Production BOM No_" VARCHAR(20) NOT NULL,
    "Single-Level Material Cost" NUMERIC(38, 20) NOT NULL,
    "Single-Level Capacity Cost" NUMERIC(38, 20) NOT NULL,
    "Single-Level Subcontrd_ Cost" NUMERIC(38, 20) NOT NULL,
    "Single-Level Cap_ Ovhd Cost" NUMERIC(38, 20) NOT NULL,
    "Single-Level Mfg_ Ovhd Cost" NUMERIC(38, 20) NOT NULL,
    "Overhead Rate" NUMERIC(38, 20) NOT NULL,
    "Rolled-up Subcontracted Cost" NUMERIC(38, 20) NOT NULL,
    "Rolled-up Mfg_ Ovhd Cost" NUMERIC(38, 20) NOT NULL,
    "Rolled-up Cap_ Overhead Cost" NUMERIC(38, 20) NOT NULL,
    "Order Tracking Policy" INTEGER NOT NULL,
    "Critical" BOOLEAN NOT NULL,
    "Common Item No_" VARCHAR(20) NOT NULL
);
