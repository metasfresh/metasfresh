/*
 * #%L
 * work-metas
 * %%
 * Copyright (C) 2026 metas GmbH
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

-- Per-project parameters of the Navision import examples. Run this script FIRST.
--
-- ADAPT PER PROJECT: every value below. The other scripts read them from migration_data.nav_import_params,
-- so they are set in this one place.
--
-- Not a parameter (an SQL identifier cannot be one): the company table prefix of the staging tables,
-- migration_data."<Company>$<Table>". The examples use the company name "Navision"; search/replace
-- "Navision$" with "<YourCompany>$" in all scripts if the staging tables were dumped under another name.

DROP TABLE IF EXISTS migration_data.nav_import_params;
CREATE TABLE migration_data.nav_import_params AS
SELECT 1000000::numeric                 AS ad_org_id,              -- AD_Org_ID of the imported partners, prices, products, groups and mandates
       '001 - '::text                   AS key_prefix,             -- prefix of every Value / migration_key / ExternalId derived from a Navision code; one per source company, so several companies can be imported into one metasfresh
       'DE'::text                       AS default_countrycode,    -- used when a Navision address has no "Country_Region Code" (a country is mandatory for a metasfresh location)
       'EUR'::text                      AS default_currency,       -- used when a Navision price has no "Currency Code" (= local currency of the source company)
       '2025-09-01 00:00:00'::timestamp AS pricelist_validfrom,    -- ValidFrom of the price list versions that hold the current prices (usually the go-live date)
       '2020-01-01 00:00:00'::timestamp AS historical_prices_from, -- prices starting before this date are not imported (historical and campaign prices)
       '2099-12-31 00:00:00'::timestamp AS no_end_date_from        -- convention of the source data: an "Ending Date" on or after this date means "no end date" (e.g. 2099-12-31).
                                                                   -- Navision's own empty date 1753-01-01 is always treated as "no end date" as well.
;

-- Navision stores a date formula (e.g. "Due Date Calculation") as a number followed by a unit control character:
-- CHAR(2) = days, other control characters = other units (week, month, quarter, year, current period, ...),
-- optionally with a leading '+'. Example: '+28' || CHR(2) = 28 days.
-- Some staging generators already strip CHAR(2); a plain number is therefore also read as days.
--
-- Returns the number of days, 0 for an empty formula, and NULL for any other formula (other units, combined formulas).
-- NULL is deliberate: such a formula cannot be expressed as a fixed number of days and must not be approximated.
-- The import scripts report and skip (or leave at the default) the records with a NULL result.
CREATE OR REPLACE FUNCTION migration_data.nav_dateformula_days(p_formula text)
    RETURNS numeric
    LANGUAGE sql
    IMMUTABLE
AS
$$
SELECT CASE
           WHEN p_formula IS NULL OR BTRIM(p_formula) = ''                     THEN 0
           WHEN BTRIM(p_formula) ~ ('^\+?[0-9]+' || CHR(2) || '?$')            THEN REGEXP_REPLACE(BTRIM(p_formula), '[^0-9]', '', 'g')::numeric
                                                                               ELSE NULL
       END
$$
;
