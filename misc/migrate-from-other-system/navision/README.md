# Navision import examples

Example SQL for migrating master data from Microsoft Dynamics NAV (Navision) into metasfresh.
These scripts are references to copy and adapt in a migration project. They are not migration scripts and are never applied automatically.

## Folders

- `migration_data/` stages the Navision tables 1:1 in the metasfresh database. Each file has two parts:
  1. a commented T-SQL `SELECT` that you run on the Navision SQL Server; it prints one Postgres `INSERT` per row,
  2. the `CREATE TABLE` of the staging table.

  Apply the `CREATE TABLE`, then the printed `INSERT`s, to the metasfresh database.
  Text is escaped by doubling single quotes only. Backslashes stay as they are, because metasfresh's Postgres runs with `standard_conforming_strings = on`.
- `import/` maps the staging tables into metasfresh, either directly or through the import tables (`I_BPartner`, `I_Product`, `I_Campaign_Price`).

## Staging convention

Every Navision table is staged as `migration_data."<Company>$<Table>"`, with the Navision column names kept exactly (e.g. `migration_data."Navision$Customer"."No_"`).
The examples use the company name `Navision`. If your tables are staged under another company name, search and replace `Navision$` with `<YourCompany>$` in all scripts.

## Order

| Step | Script | Then |
|---|---|---|
| 0 | `00_parameters.sql`: project parameters, date-formula helper | |
| 1 | `06_uoms.sql` | |
| 2 | `01_payment_terms.sql` | create any reported payment terms manually |
| 3 | `02_shipper.sql` | |
| 4 | `10_bp_groups.sql` (optional) | |
| 5 | `03_pricing_systems_lists_and_versions.sql` | |
| 6 | `04_partners.sql` | run the Business Partner import (`I_BPartner`) |
| 7 | `05_direct_debit_mandate.sql` | |
| 8 | `07_products_and_prices.sql`, or `11_productOnly.sql` for products without prices | run the Product import (`I_Product`) |
| 9 | `08_campaign_prices.sql` | run the Campaign Price import (`I_Campaign_Price`) |
| 10 | `09_historical_product_prices.sql` (optional) | run the Product import (`I_Product`) again |

Every script states its prerequisites at the top. The scripts that write metasfresh tables directly skip records whose key already exists, so they can be re-run. The scripts that fill import tables add new import rows each time they run.

## What to adapt per project

- **`00_parameters.sql`**: the org (`ad_org_id`), the key prefix, the fallback country and currency, the `ValidFrom` of the current price list versions, the earliest historical price, and the "no end date" convention.
- **Key prefix** (default `'001 - '`): put in front of every Value, migration key and ExternalId that comes from a Navision code. Use one prefix per source company, so that several Navision companies can be imported into one metasfresh.
- **Mappings marked `ADAPT`** in the scripts:
  - Navision units of measure to `C_UOM.X12DE355` (at most 4 characters),
  - VAT product posting groups to tax categories,
  - payment methods to payment rules,
  - business relation codes,
  - product category,
  - private persons vs. companies.
- **Non-standard tables**: `CMM Recipient`, `Route` (column `Beschreibung`) and `Bank Account Mandate` come from add-ons in one source system. Remove or adapt them if your source has no such tables.
- **Missing staging scripts**: `Customer Group` and `Vendor Group`, used by `10_bp_groups.sql`, have no file in `migration_data/`. Stage them in the same way as the other tables.
- **Number series**: customers and vendors both use the Navision `No_` as Value. If the two number series overlap, prefix one of them, so that a customer and a vendor with the same number do not become one partner.

## Conventions used by the scripts

- **Date formulas**: Navision stores a formula such as `"Due Date Calculation"` as a number followed by a unit control character. `CHAR(2)` means days, and a leading `+` may appear (e.g. `'+28' || CHR(2)`). Only day formulas are converted, by `migration_data.nav_dateformula_days`. Other units are never approximated: payment terms that use them are reported and skipped, and product expiration formulas that use them are reported and set to 0.
- **End dates of prices**: in Navision an empty date is `1753-01-01`. A sales price has no end date when its `"Ending Date"` is `1753-01-01`, or on or after `no_end_date_from` (the project's far-future convention, e.g. `2099-12-31`). Prices without an end date are regular prices (scripts 07 and 09). Prices with a real end date are campaign prices (script 08).
- **Locations**: Navision has no default ship-to address. The main customer or vendor address becomes the default bill-to and ship-to location. Each Navision `Ship-to Address` becomes an additional ship-to location.
- **Payment terms**: the key is the Navision `Code` (Value = key prefix + `Code`), and the name is the description. If a description is not unique, the name gets the code appended.
