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


DROP TABLE IF EXISTS migration_data.i_campaign_price;
CREATE TABLE migration_data.i_campaign_price AS
SELECT 1000000                                                          AS ad_client_id,
       1000000                                                          AS ad_org_id,
       'Y'                                                              AS isactive,
       NOW()                                                            AS created,
       99                                                               AS createdby,
       NOW()                                                            AS updated,
       99                                                               AS updatedby,
       'N'                                                              AS i_isimported,
       NULL                                                             AS i_errormsg,
       'N'                                                              AS processed,
       T_Item."No_"                                                     AS productvalue,
       COALESCE(NULLIF(TRIM(T_Sales_Price."Currency Code"), ''), 'EUR') AS iso_code,
       NULL                                                             AS c_taxcategory_name,
       T_Sales_Price."Unit Price"                                       AS pricestd,
       T_Sales_Price."Starting Date"                                    AS validfrom,
       T_Sales_Price."Ending Date"                                      AS validto,
       'Nominal'                                                        AS invoicableqtybasedon,
       NULL::numeric                                                    AS c_bpartner_id,
       CASE
           WHEN T_Item."VAT Prod_ Posting Group" = '19ST'
               THEN (SELECT c_taxcategory_id FROM c_taxcategory WHERE internalname = 'Normal')
           ELSE (SELECT c_taxcategory_id FROM c_taxcategory WHERE internalname = 'Reduced')
           END                                                          AS c_taxcategory_id,
       NULL                                                             AS pricingsystem_value,
       (SELECT m_pricingsystem_id
        FROM m_pricingsystem
        WHERE migration_key = ('001 - ' || T_Sales_Price."Sales Code")) AS m_pricingsystem_id,
       NULL::numeric                                                    AS m_product_id,
       'DE'                                                             AS countrycode,
       T_Customer."No_"                                                 AS bpartner_value,
       CASE T_Item."Base Unit of Measure" -- Map Navision "Base Unit of Measure" to UOM
           WHEN 'KG' THEN (SELECT name FROM c_uom WHERE x12de355 = 'KGM' AND isactive = 'Y')
           WHEN 'STCK' THEN (SELECT name FROM c_uom WHERE x12de355 = 'PCE' AND isactive = 'Y')
           ELSE (SELECT name FROM c_uom WHERE x12de355 = T_Item."Base Unit of Measure")
           END                                                          AS uom,
       NULL::numeric                                                    AS c_uom_id,
       NULL::numeric                                                    AS c_currency_id,
       NULL::numeric                                                    AS c_country_id,
       NULL::numeric                                                    AS i_lineno
FROM "migration_data"."Navision$Sales Price" AS T_Sales_Price
         JOIN "migration_data"."Navision$Item" AS T_Item ON T_Sales_Price."Item No_" = T_Item."No_"
         LEFT JOIN "migration_data"."Navision$Customer" AS T_Customer
                   ON T_Sales_Price."Sales Code" = T_Customer."No_" AND T_Sales_Price."Sales Type" = 0
WHERE T_Sales_Price."Starting Date" > '2020-01-01'
  AND T_Sales_Price."Ending Date" < '2027-01-01'
;

INSERT INTO i_campaign_price (ad_client_id, ad_org_id, created, createdby, updated, updatedby,
                              i_isimported, i_errormsg, i_campaign_price_id, isactive, processed,
                              productvalue, iso_code, c_taxcategory_name, pricestd, validfrom, validto,
                              invoicableqtybasedon, c_bpartner_id, c_taxcategory_id, pricingsystem_value,
                              m_pricingsystem_id,
                              m_product_id, countrycode, bpartner_value, uom, c_uom_id, c_currency_id, c_country_id,
                              i_lineno)
SELECT ad_client_id,
       ad_org_id,
       created,
       createdby,
       updated,
       updatedby,
       i_isimported,
       i_errormsg,
       nextval('i_campaign_price_seq'),
       isactive,
       processed,
       productvalue,
       iso_code,
       c_taxcategory_name,
       pricestd,
       validfrom,
       validto,
       invoicableqtybasedon,
       c_bpartner_id,
       c_taxcategory_id,
       pricingsystem_value,
       m_pricingsystem_id,
       m_product_id,
       countrycode,
       bpartner_value,
       uom,
       c_uom_id,
       c_currency_id,
       c_country_id,
       i_lineno
FROM migration_data.i_campaign_price
;
