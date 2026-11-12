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

-- Prerequisite: 00_parameters.sql
-- Creates the pricing systems, sales price lists and the current price list versions (ValidFrom = pricelist_validfrom):
--  * one pricing system per Navision "Customer Price Group" (key: migration_key = key_prefix || "Code"),
--  * one pricing system per customer that has own sales prices (key: migration_key = key_prefix || customer "No_");
--    its price list version is based on the version of the customer's price group.
-- Existing pricing systems / price lists / versions with the same key are skipped, so the script can be re-run.
-- ADAPT PER PROJECT: Default_TaxCategory_ID of the price lists ('Reduced' below).

DROP TABLE IF EXISTS migration_data.m_pricingsystem;
CREATE TABLE migration_data.m_pricingsystem AS
SELECT 1000000                        AS ad_client_id,
       (SELECT ad_org_id FROM migration_data.nav_import_params) AS ad_org_id,
       NOW()                          AS created,
       99                             AS createdby,
       "Description"                  AS description,
       'Y'                            AS isactive,
       nextval('m_pricingsystem_seq') AS m_pricingsystem_id,
       "Description"                  AS name,
       NOW()                          AS updated,
       99                             AS updatedby,
       (SELECT key_prefix FROM migration_data.nav_import_params) || "Description"      AS value,
       'N'                            AS issubscriptiononly,
       (SELECT key_prefix FROM migration_data.nav_import_params) || "Code"             AS migration_key
FROM migration_data."Navision$Customer Price Group" cpg
WHERE NOT EXISTS (SELECT 1
                  FROM m_pricingsystem ps
                  WHERE ps.migration_key = (SELECT key_prefix FROM migration_data.nav_import_params) || cpg."Code"
                     OR ps.value = (SELECT key_prefix FROM migration_data.nav_import_params) || cpg."Description")
;

INSERT INTO m_pricingsystem (ad_client_id, ad_org_id, created, createdby, description, isactive, m_pricingsystem_id,
                             name, updated, updatedby, value, issubscriptiononly, migration_key)
SELECT ad_client_id,
       ad_org_id,
       created,
       createdby,
       description,
       isactive,
       nextval('m_pricingsystem_seq'),
       name,
       updated,
       updatedby,
       value,
       issubscriptiononly,
       migration_key
FROM migration_data.m_pricingsystem
;


DROP TABLE IF EXISTS migration_data.m_pricingsystem_partners;
CREATE TABLE migration_data.m_pricingsystem_partners AS
SELECT 1000000              AS ad_client_id,
       (SELECT ad_org_id FROM migration_data.nav_import_params) AS ad_org_id,
       NOW()                AS created,
       99                   AS createdby,
       c."Name"             AS description,
       'Y'                  AS isactive,
       c."Name"             AS name,
       NOW()                AS updated,
       99                   AS updatedby,
       (SELECT key_prefix FROM migration_data.nav_import_params) || c."No_" AS value, -- the No_, because several customers may have the same name
       'N'                  AS issubscriptiononly,
       (SELECT key_prefix FROM migration_data.nav_import_params) || c."No_"  AS migration_key
FROM migration_data."Navision$Sales Price" sp
         JOIN migration_data."Navision$Customer" c ON sp."Sales Code" = c."No_"
WHERE sp."Sales Type" = 0 -- 0 = Customer
  AND NOT EXISTS (SELECT 1
                  FROM m_pricingsystem ps
                  WHERE ps.migration_key = (SELECT key_prefix FROM migration_data.nav_import_params) || c."No_"
                     OR ps.value = (SELECT key_prefix FROM migration_data.nav_import_params) || c."No_")
GROUP BY c."Name", c."No_"
;

INSERT INTO m_pricingsystem (ad_client_id, ad_org_id, created, createdby, description, isactive, m_pricingsystem_id,
                             name, updated, updatedby, value, issubscriptiononly, migration_key)
SELECT ad_client_id,
       ad_org_id,
       created,
       createdby,
       description,
       isactive,
       nextval('m_pricingsystem_seq'),
       name,
       updated,
       updatedby,
       value,
       issubscriptiononly,
       migration_key
FROM migration_data.m_pricingsystem_partners
;


DROP TABLE IF EXISTS migration_data.m_pricelist;
CREATE TABLE migration_data.m_pricelist AS
SELECT 1000000                                                                     AS ad_client_id,
       T_PS.ad_org_id                                                              AS ad_org_id,
       'Y'                                                                         AS isactive,
       NOW()                                                                       AS created,
       99                                                                          AS createdby,
       NOW()                                                                       AS updated,
       99                                                                          AS updatedby,
       T_PS.name                                                                   AS name,
       NULL                                                                        AS description,
       NULL::numeric                                                               AS basepricelist_id,
       CASE
           WHEN T_CPG_SOURCE."Price Includes VAT" THEN 'Y'
           ELSE 'N' END                                                            AS istaxincluded,
       'Y'                                                                         AS issopricelist,
       'N'                                                                         AS isdefault,
       T_CUR.c_currency_id                                                         AS c_currency_id,
       'N'                                                                         AS enforcepricelimit,
       2                                                                           AS priceprecision,
       'N'                                                                         AS ismandatory,
       'N'                                                                         AS ispresentforproduct,
       T_PS.m_pricingsystem_id                                                     AS m_pricingsystem_id,
       NULL::numeric                                                               AS c_country_id,
       T_PS.value                                                                  AS internalname,
       'N'                                                                         AS isroundnetamounttocurrencyprecision,
       (SELECT c_taxcategory_id FROM c_taxcategory WHERE internalname = 'Reduced') AS default_taxcategory_id -- ADAPT
FROM m_pricingsystem AS T_PS
         JOIN "migration_data"."Navision$Customer Price Group" AS T_CPG_SOURCE
              ON T_PS.migration_key = ((SELECT key_prefix FROM migration_data.nav_import_params) || T_CPG_SOURCE."Code")
         JOIN LATERAL (
    SELECT DISTINCT COALESCE(NULLIF(TRIM(LEFT(SP."Currency Code", 3)), ''), (SELECT default_currency FROM migration_data.nav_import_params)) AS "CurrencyCode"
    --COALESCE(NULLIF(TRIM(LEFT(C."Country_Region Code", 2)), ''), 'DE') AS "CountryCode"
    FROM "migration_data"."Navision$Sales Price" AS SP
             JOIN "migration_data"."Navision$Customer Price Group" AS T_CPG_INNER
                  ON SP."Sales Code" = T_CPG_INNER."Code"
             JOIN "migration_data"."Navision$Customer" AS C
                  ON T_CPG_INNER."Code" = C."Customer Price Group"
    WHERE ((SELECT key_prefix FROM migration_data.nav_import_params) || T_CPG_INNER."Code") = T_PS.migration_key
    ) AS SalesAndCountryData ON TRUE
         JOIN
     c_currency AS T_CUR ON SalesAndCountryData."CurrencyCode" = T_CUR.iso_code
--          JOIN
--      c_country AS T_COUNTRY ON SalesAndCountryData."CountryCode" = T_COUNTRY.countrycode
WHERE NOT EXISTS (SELECT 1 FROM m_pricelist pl WHERE pl.m_pricingsystem_id = T_PS.m_pricingsystem_id AND pl.issopricelist = 'Y')
;


INSERT INTO m_pricelist (m_pricelist_id,
                         ad_client_id,
                         ad_org_id,
                         isactive,
                         created,
                         createdby,
                         updated,
                         updatedby,
                         name,
                         description,
                         basepricelist_id,
                         istaxincluded,
                         issopricelist,
                         isdefault,
                         c_currency_id,
                         enforcepricelimit,
                         priceprecision,
                         ismandatory,
                         ispresentforproduct,
                         m_pricingsystem_id,
                         c_country_id,
                         internalname,
                         isroundnetamounttocurrencyprecision,
                         default_taxcategory_id)
SELECT nextval('m_pricelist_seq'),
       ad_client_id,
       ad_org_id,
       isactive,
       created,
       createdby,
       updated,
       updatedby,
       name,
       description,
       basepricelist_id,
       istaxincluded,
       issopricelist,
       isdefault,
       c_currency_id,
       enforcepricelimit,
       priceprecision,
       ismandatory,
       ispresentforproduct,
       m_pricingsystem_id,
       c_country_id,
       internalname,
       isroundnetamounttocurrencyprecision,
       default_taxcategory_id
FROM migration_data.m_pricelist
;

DROP TABLE IF EXISTS migration_data.m_pricelist_version;
CREATE TABLE migration_data.m_pricelist_version AS
SELECT pl.ad_client_id                  AS ad_client_id,
       pl.ad_org_id                     AS ad_org_id,
       'Y'                              AS isactive,
       NOW()                            AS created,
       99                               AS createdby,
       NOW()                            AS updated,
       99                               AS updatedby,
       pl.internalname                  AS name,
       NULL                             AS description,
       pl.m_pricelist_id                AS m_pricelist_id,
       NULL::numeric                    AS m_discountschema_id,
       (SELECT pricelist_validfrom FROM migration_data.nav_import_params) AS validfrom,
       NULL                             AS proccreate,
       NULL::numeric                    AS m_pricelist_version_base_id,
       'N'                              AS processed,
       NULL::numeric                    AS c_region_id
FROM m_pricelist pl
         JOIN m_pricingsystem ps ON pl.m_pricingsystem_id = ps.m_pricingsystem_id
         JOIN "migration_data"."Navision$Customer Price Group" AS t_cpg_source
              ON ps.migration_key = ((SELECT key_prefix FROM migration_data.nav_import_params) || t_cpg_source."Code")
WHERE NOT EXISTS (SELECT 1 FROM m_pricelist_version v WHERE v.m_pricelist_id = pl.m_pricelist_id AND v.validfrom = (SELECT pricelist_validfrom FROM migration_data.nav_import_params))
;


INSERT INTO m_pricelist_version (m_pricelist_version_id,
                                 ad_client_id,
                                 ad_org_id,
                                 isactive,
                                 created,
                                 createdby,
                                 updated,
                                 updatedby,
                                 name,
                                 description,
                                 m_pricelist_id,
                                 m_discountschema_id,
                                 validfrom,
                                 proccreate,
                                 m_pricelist_version_base_id,
                                 processed,
                                 c_region_id)
SELECT nextval('m_pricelist_version_seq'),
       ad_client_id,
       ad_org_id,
       isactive,
       created,
       createdby,
       updated,
       updatedby,
       name,
       description,
       m_pricelist_id,
       m_discountschema_id,
       validfrom,
       proccreate,
       m_pricelist_version_base_id,
       processed,
       c_region_id
FROM migration_data.m_pricelist_version
;

DROP TABLE IF EXISTS migration_data.m_pricelist_partners;
CREATE TABLE migration_data.m_pricelist_partners AS
SELECT 1000000                                                                     AS ad_client_id,
       ps_customer.ad_org_id                                                       AS ad_org_id,
       'Y'                                                                         AS isactive,
       NOW()                                                                       AS created,
       99                                                                          AS createdby,
       NOW()                                                                       AS updated,
       99                                                                          AS updatedby,
       ps_customer.name                                                            AS name,
       NULL                                                                        AS description,
       NULL::numeric                                                               AS basepricelist_id,
       CASE
           WHEN c."Prices Including VAT" THEN 'Y'
           ELSE 'N' END                                                            AS istaxincluded,
       'Y'                                                                         AS issopricelist,
       'N'                                                                         AS isdefault,
       cur.c_currency_id                                                           AS c_currency_id,
       'N'                                                                         AS enforcepricelimit,
       2                                                                           AS priceprecision,
       'N'                                                                         AS ismandatory,
       'N'                                                                         AS ispresentforproduct,
       ps_customer.m_pricingsystem_id                                              AS m_pricingsystem_id,
       NULL::numeric                                                               AS c_country_id,
       ps_customer.value                                                           AS internalname,
       'N'                                                                         AS isroundnetamounttocurrencyprecision,
       (SELECT c_taxcategory_id FROM c_taxcategory WHERE internalname = 'Reduced') AS default_taxcategory_id -- ADAPT
FROM m_pricingsystem AS ps_customer
         JOIN "migration_data"."Navision$Customer" AS c
              ON ps_customer.migration_key = ((SELECT key_prefix FROM migration_data.nav_import_params) || c."No_")
         JOIN LATERAL (
    SELECT DISTINCT COALESCE(NULLIF(TRIM(LEFT(pp."Currency Code", 3)), ''), (SELECT default_currency FROM migration_data.nav_import_params)) AS "CurrencyCode"
--             COALESCE(NULLIF(TRIM(LEFT(C."Country_Region Code", 2)), ''), 'DE') AS "CountryCode"
    FROM "migration_data"."Navision$Sales Price" AS pp
    WHERE pp."Sales Code" = c."No_"
      AND pp."Sales Type" = 0
    ) AS PriceData ON TRUE
         JOIN
     c_currency AS cur ON PriceData."CurrencyCode" = cur.iso_code

--         JOIN
--     c_country AS country ON PriceData."CountryCode" = country.countrycode
WHERE NOT EXISTS (SELECT 1 FROM m_pricelist pl WHERE pl.m_pricingsystem_id = ps_customer.m_pricingsystem_id AND pl.issopricelist = 'Y')
;


INSERT INTO m_pricelist (m_pricelist_id,
                         ad_client_id,
                         ad_org_id,
                         isactive,
                         created,
                         createdby,
                         updated,
                         updatedby,
                         name,
                         description,
                         basepricelist_id,
                         istaxincluded,
                         issopricelist,
                         isdefault,
                         c_currency_id,
                         enforcepricelimit,
                         priceprecision,
                         ismandatory,
                         ispresentforproduct,
                         m_pricingsystem_id,
                         c_country_id,
                         internalname,
                         isroundnetamounttocurrencyprecision,
                         default_taxcategory_id)
SELECT nextval('m_pricelist_seq'),
       ad_client_id,
       ad_org_id,
       isactive,
       created,
       createdby,
       updated,
       updatedby,
       name,
       description,
       basepricelist_id,
       istaxincluded,
       issopricelist,
       isdefault,
       c_currency_id,
       enforcepricelimit,
       priceprecision,
       ismandatory,
       ispresentforproduct,
       m_pricingsystem_id,
       c_country_id,
       internalname,
       isroundnetamounttocurrencyprecision,
       default_taxcategory_id
FROM migration_data.m_pricelist_partners
;

DROP TABLE IF EXISTS migration_data.m_pricelist_version_partners;
CREATE TABLE migration_data.m_pricelist_version_partners AS
SELECT pl_customer.ad_client_id         AS ad_client_id,
       pl_customer.ad_org_id            AS ad_org_id,
       'Y'                              AS isactive,
       NOW()                            AS created,
       99                               AS createdby,
       NOW()                            AS updated,
       99                               AS updatedby,
       pl_customer.internalname         AS name,
       NULL                             AS description,
       pl_customer.m_pricelist_id       AS m_pricelist_id,
       NULL::numeric                    AS m_discountschema_id,
       (SELECT pricelist_validfrom FROM migration_data.nav_import_params) AS validfrom,
       NULL                             AS proccreate,
       plv_cpg.m_pricelist_version_id   AS m_pricelist_version_base_id,
       'N'                              AS processed,
       NULL::numeric                    AS c_region_id
FROM m_pricelist pl_customer
         JOIN m_pricingsystem ps_customer ON pl_customer.m_pricingsystem_id = ps_customer.m_pricingsystem_id
         JOIN "migration_data"."Navision$Customer" AS c
              ON ps_customer.migration_key = ((SELECT key_prefix FROM migration_data.nav_import_params) || c."No_")
         LEFT JOIN "migration_data"."Navision$Customer Price Group" AS cpg
                   ON c."Customer Price Group" = cpg."Code"
         LEFT JOIN m_pricingsystem ps_cpg ON ((SELECT key_prefix FROM migration_data.nav_import_params) || c."Customer Price Group" = ps_cpg.migration_key)
         LEFT JOIN m_pricelist pl_cpg
                   ON ps_cpg.m_pricingsystem_id = pl_cpg.m_pricingsystem_id --AND pl_cpg.c_country_id = pl_customer.c_country_id
         LEFT JOIN m_pricelist_version plv_cpg
                   ON pl_cpg.m_pricelist_id = plv_cpg.m_pricelist_id AND plv_cpg.validfrom = (SELECT pricelist_validfrom FROM migration_data.nav_import_params)
WHERE NOT EXISTS (SELECT 1 FROM m_pricelist_version v WHERE v.m_pricelist_id = pl_customer.m_pricelist_id AND v.validfrom = (SELECT pricelist_validfrom FROM migration_data.nav_import_params))
;


INSERT INTO m_pricelist_version (m_pricelist_version_id,
                                 ad_client_id,
                                 ad_org_id,
                                 isactive,
                                 created,
                                 createdby,
                                 updated,
                                 updatedby,
                                 name,
                                 description,
                                 m_pricelist_id,
                                 m_discountschema_id,
                                 validfrom,
                                 proccreate,
                                 m_pricelist_version_base_id,
                                 processed,
                                 c_region_id)
SELECT nextval('m_pricelist_version_seq'),
       ad_client_id,
       ad_org_id,
       isactive,
       created,
       createdby,
       updated,
       updatedby,
       name,
       description,
       m_pricelist_id,
       m_discountschema_id,
       validfrom,
       proccreate,
       m_pricelist_version_base_id,
       processed,
       c_region_id
FROM migration_data.m_pricelist_version_partners
;

-- correction of m_pricelist_version_base_id not set above
UPDATE M_PriceList AS p
SET basepricelist_id = (SELECT plvb.m_pricelist_id
                        FROM m_pricelist_version plvb
                        WHERE plvb.m_pricelist_version_id = plv.m_pricelist_version_base_id)
FROM m_pricelist_version AS plv
WHERE p.m_pricelist_id = plv.m_pricelist_id
  AND p.ad_org_id = (SELECT ad_org_id FROM migration_data.nav_import_params)
  AND plv.ValidFrom = (SELECT pricelist_validfrom FROM migration_data.nav_import_params)
  AND plv.m_pricelist_version_base_id > 0
;
