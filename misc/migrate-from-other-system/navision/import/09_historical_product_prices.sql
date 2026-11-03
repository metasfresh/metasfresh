

DROP TABLE IF EXISTS migration_data.m_pricelist_version_5years;
CREATE TABLE migration_data.m_pricelist_version_5years AS
SELECT pl.ad_client_id                                                AS ad_client_id,
       pl.ad_org_id                                                   AS ad_org_id,
       'Y'                                                            AS isactive,
       NOW()                                                          AS created,
       99                                                             AS createdby,
       NOW()                                                          AS updated,
       99                                                             AS updatedby,
       pl.internalname || ' ' || to_char(startdates.date, 'yyyymmdd') AS name,
       NULL                                                           AS description,
       pl.m_pricelist_id                                              AS m_pricelist_id,
       NULL::numeric                                                  AS m_discountschema_id,
       startdates.date::timestamp                                     AS validfrom,
       NULL                                                           AS proccreate,
       NULL::numeric                                                  AS m_pricelist_version_base_id,
       'N'                                                            AS processed,
       NULL::numeric                                                  AS c_region_id
FROM m_pricelist pl
         JOIN m_pricingsystem ps ON pl.m_pricingsystem_id = ps.m_pricingsystem_id
         JOIN "migration_data"."Navision$Customer Price Group" AS t_cpg_source
              ON ps.migration_key = ('001 - ' || t_cpg_source."Code")
         JOIN LATERAL (
    SELECT DISTINCT "Starting Date" AS date
    FROM "migration_data"."Navision$Sales Price" sp
             JOIN "migration_data"."Navision$Item" AS T_Item ON sp."Item No_" = T_Item."No_"
    WHERE t_cpg_source."Code" = "Sales Code"
      AND SP."Sales Type" = 1
      AND "Starting Date" < '2025-09-01'
      AND "Starting Date" > '2020-01-01'
      AND "Ending Date" > '2027-01-01' --exclude campaign prices
    ) startdates ON TRUE
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
FROM migration_data.m_pricelist_version_5years
;

DROP TABLE IF EXISTS migration_data.m_pricelist_version_partners_5years;
CREATE TABLE migration_data.m_pricelist_version_partners_5years AS
SELECT pl_customer.ad_client_id                                                AS ad_client_id,
       pl_customer.ad_org_id                                                   AS ad_org_id,
       'Y'                                                                     AS isactive,
       NOW()                                                                   AS created,
       99                                                                      AS createdby,
       NOW()                                                                   AS updated,
       99                                                                      AS updatedby,
       pl_customer.internalname || ' ' || to_char(startdates.date, 'yyyymmdd') AS name,
       NULL                                                                    AS description,
       pl_customer.m_pricelist_id                                              AS m_pricelist_id,
       NULL::numeric                                                           AS m_discountschema_id,
       startdates.date                                                         AS validfrom,
       NULL                                                                    AS proccreate,
       NULL::numeric                                                           AS m_pricelist_version_base_id,
       'N'                                                                     AS processed,
       NULL::numeric                                                           AS c_region_id
FROM m_pricelist pl_customer
         JOIN m_pricingsystem ps_customer ON pl_customer.m_pricingsystem_id = ps_customer.m_pricingsystem_id
         JOIN "migration_data"."Navision$Customer" AS c
              ON ps_customer.migration_key = ('001 - ' || c."No_")
         JOIN LATERAL (
    SELECT DISTINCT "Starting Date" AS date
    FROM "migration_data"."Navision$Sales Price" sp
             JOIN "migration_data"."Navision$Item" AS T_Item ON sp."Item No_" = T_Item."No_"
    WHERE c."No_" = "Sales Code"
      AND SP."Sales Type" = 0
      AND "Starting Date" < '2025-09-01'
      AND "Starting Date" > '2020-01-01'
      AND "Ending Date" > '2027-01-01' --exclude campaign prices
    ) startdates ON TRUE
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
FROM migration_data.m_pricelist_version_partners_5years
;


DROP TABLE IF EXISTS "migration_data".i_product_inserts_prices_5years;
CREATE TABLE "migration_data".i_product_inserts_prices_5years AS
SELECT 1000000                                                                                 AS ad_client_id,
       1000000                                                                                 AS ad_org_id,
       'Y'                                                                                     AS isactive,
       NOW()                                                                                   AS created,
       99                                                                                      AS createdby,
       NOW()                                                                                   AS updated,
       99                                                                                      AS updatedby,
       'N'                                                                                     AS i_isimported,
       NULL                                                                                    AS i_errormsg,
       NULL::numeric                                                                           AS m_product_id,
       T_Item."No_"                                                                            AS value,
       T_Item."Description"                                                                    AS name,
       T_Item."Description 2"                                                                  AS description,
       NULL                                                                                    AS documentnote,
       NULL                                                                                    AS help,
       NULL                                                                                    AS upc,
       NULL                                                                                    AS sku,
       CASE T_Item."Base Unit of Measure" -- x12de355: Map Navision "Base Unit of Measure"
           WHEN 'KG' THEN 'KGM'
           WHEN 'STCK' THEN 'PCE'
           ELSE T_Item."Base Unit of Measure"
           END                                                                                 AS x12de355,
       NULL::numeric                                                                           AS c_uom_id,
       'Standard'                                                                              AS productcategory_value,
       NULL::numeric                                                                           AS m_product_category_id,
       CASE T_Item."Type" -- producttype: Map Navision Type (0 for Item, 1 for Service)
           WHEN 0 THEN 'I'
           WHEN 1 THEN 'S'
           ELSE 'I'
           END                                                                                 AS producttype,
       NULL                                                                                    AS classification,
       T_Item."Unit Volume"                                                                    AS volume,
       T_Item."Gross Weight"                                                                   AS weight,
       NULL::numeric                                                                           AS shelfwidth,
       NULL::numeric                                                                           AS shelfheight,
       NULL::numeric                                                                           AS shelfdepth,
       T_Item."Units per Parcel"                                                               AS unitsperpallet,
       'N'                                                                                     AS discontinued,
       NULL::timestamp without time zone                                                       AS discontinuedby,
       NULL                                                                                    AS imageurl,
       NULL                                                                                    AS descriptionurl,
       NULL                                                                                    AS bpartner_value,
       NULL::numeric                                                                           AS c_bpartner_id,
       COALESCE(NULLIF(TRIM(LEFT(SP."Currency Code", 3)), ''), 'EUR')                          AS iso_code,
       NULL::numeric                                                                           AS pricelist,
       NULL::numeric                                                                           AS pricepo,
       0                                                                                       AS royaltyamt,
       SP."Starting Date"                                                                      AS priceeffective,
       NULL                                                                                    AS vendorproductno,
       NULL                                                                                    AS vendorcategory,
       T_Item."Minimum Order Quantity"                                                         AS order_min,
       T_Item."Order Multiple"                                                                 AS order_pack,
       0                                                                                       AS costperorder,
       NULL::numeric                                                                           AS deliverytime_promised,
       NULL                                                                                    AS processing,
       'N'                                                                                     AS processed,
       SP."Unit Price"                                                                         AS pricestd,
       NULL::numeric                                                                           AS pricelimit,
       CASE
           WHEN T_Item."VAT Prod_ Posting Group" = '19ST' THEN (SELECT c_taxcategory_id
                                                                FROM c_taxcategory
                                                                WHERE internalname = 'Normal')
           ELSE (SELECT c_taxcategory_id FROM c_taxcategory WHERE internalname = 'Reduced')
           END                                                                                 AS c_taxcategory_id,
       NULL                                                                                    AS c_taxcategory_name,
       plv.name                                                                                AS m_pricelist_version_name,
       NULL::numeric                                                                           AS package_uom_id,
       NULL                                                                                    AS packagesize,
       NULL                                                                                    AS productmanufacturer,
       CASE T_Item."Blocked" WHEN TRUE THEN 'N' ELSE 'Y' END                                   AS issold,
       CASE T_Item."Type" WHEN 0 THEN 'Y' ELSE 'N' END                                         AS isstocked,
       NULL                                                                                    AS a00pgeinh,
       'N'                                                                                     AS isprescription,
       'N'                                                                                     AS isnarcotic,
       'N'                                                                                     AS istfg,
       'N'                                                                                     AS iscoldchain,
       NULL                                                                                    AS fam_zub,
       NULL::numeric                                                                           AS m_indication_id,
       NULL                                                                                    AS m_indication_name,
       NULL::numeric                                                                           AS m_dosageform_id,
       NULL                                                                                    AS a00darfo,
       NULL                                                                                    AS m_productplanningschema_selector,
       NULL::numeric                                                                           AS c_dataimport_id,
       NULL                                                                                    AS pharmaproductcategory_name,
       NULL::numeric                                                                           AS m_pharmaproductcategory_id,
       NULL::numeric                                                                           AS a01aep,
       NULL::numeric                                                                           AS a01apu,
       NULL::numeric                                                                           AS apu_price_list_id,
       NULL::numeric                                                                           AS aep_price_list_id,
       NULL::numeric                                                                           AS manufacturer_id,
       NULL::numeric                                                                           AS ad_issue_id,
       NULL::numeric                                                                           AS i_lineno,
       NULL                                                                                    AS i_linecontent,
       NULL::numeric                                                                           AS c_dataimport_run_id,
       T_Item."No_"                                                                            AS externalid,
       T_Item."Net Weight"                                                                     AS netweight,
       T_Item."Country_Region of Origin Code"                                                  AS rawmaterialorigincountrycode,
       T_Item."Tariff No_"                                                                     AS customstariff,
       NULL::numeric                                                                           AS m_customstariff_id,
       NULL::numeric                                                                           AS rawmaterialorigin_id,
       'N'                                                                                     AS isscaleprice,
       SP."Minimum Quantity"                                                                   AS qty,
       NULL                                                                                    AS trademark,
       NULL                                                                                    AS pzn,
       NULL                                                                                    AS manufacturingmethod,
       'N'                                                                                     AS iscommissioned,
       'N'                                                                                     AS ispurchased,
       NULL                                                                                    AS diettype,
       NULL::numeric                                                                           AS dosageqty,
       NULL::numeric                                                                           AS dosageuom_id,
       CASE
           WHEN T_Item."Net Weight" <> 0
               THEN (SELECT c_uom_id FROM c_uom WHERE x12de355 = 'KGM' AND isactive = 'Y') END AS netweight_uom_id,
       CASE
           WHEN T_Item."Gross Weight" <> 0
               THEN (SELECT c_uom_id FROM c_uom WHERE x12de355 = 'KGM' AND isactive = 'Y') END AS weight_uom_id,
       NULL                                                                                    AS weightuom,
       NULL                                                                                    AS netweightuom,
       NULL                                                                                    AS dosageuom,
       NULL                                                                                    AS m_hu_pi_value,
       NULL::numeric                                                                           AS qtycu_uom_id,
       'N'                                                                                     AS isdefaultpacking,
       NULL                                                                                    AS invoicableqtybasedon,
       T_Item."Price Unit Conversion"                                                          AS uom_multiplierrate,
       NULL::numeric                                                                           AS qtycu,
       NULL                                                                                    AS qtycu_uom_code,
       'Y'                                                                                     AS isupdatename,
       CASE
           WHEN NULLIF(TRIM(T_Item."Expiration Calculation"), '') IS NULL THEN 0
           ELSE regexp_replace(TRIM(T_Item."Expiration Calculation"), '\D', '', 'g')::numeric
           END                                                                                 AS GuaranteeDaysMin
FROM "migration_data"."Navision$Item" AS T_Item
         LEFT JOIN "migration_data"."Navision$Product Group" AS T_PG
                   ON T_Item."Product Group Code" = T_PG."Code"
         JOIN "migration_data"."Navision$Sales Price" AS SP
              ON SP."Item No_" = T_Item."No_"
         JOIN m_pricingsystem ps ON ps.migration_key = ('001 - ' || SP."Sales Code")
         JOIN m_pricelist pl ON ps.m_pricingsystem_id = pl.m_pricingsystem_id AND pl.issopricelist = 'Y'
         JOIN m_pricelist_version plv ON pl.m_pricelist_id = plv.m_pricelist_id
    AND plv.validfrom = SP."Starting Date"
    AND plv.createdby = 99
WHERE  "Starting Date" < '2025-09-01'
  AND "Ending Date" > '2027-01-01' /*
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
 */ --exclude campaign prices
;


INSERT INTO i_product (i_product_id,
                       ad_client_id,
                       ad_org_id,
                       isactive,
                       created,
                       createdby,
                       updated,
                       updatedby,
                       i_isimported,
                       i_errormsg,
                       m_product_id,
                       value,
                       name,
                       description,
                       documentnote,
                       help,
                       upc,
                       sku,
                       x12de355,
                       c_uom_id,
                       productcategory_value,
                       m_product_category_id,
                       producttype,
                       classification,
                       volume,
                       weight,
                       shelfwidth,
                       shelfheight,
                       shelfdepth,
                       unitsperpallet,
                       discontinued,
                       discontinuedby,
                       imageurl,
                       descriptionurl,
                       bpartner_value,
                       c_bpartner_id,
                       iso_code,
                       pricelist,
                       pricepo,
                       royaltyamt,
                       priceeffective,
                       vendorproductno,
                       vendorcategory,
                       order_min,
                       order_pack,
                       costperorder,
                       deliverytime_promised,
                       processing,
                       processed,
                       pricestd,
                       pricelimit,
                       c_taxcategory_id,
                       c_taxcategory_name,
                       m_pricelist_version_name,
                       package_uom_id,
                       packagesize,
                       productmanufacturer,
                       issold,
                       isstocked,
                       a00pgeinh,
                       isprescription,
                       isnarcotic,
                       istfg,
                       iscoldchain,
                       fam_zub,
                       m_indication_id,
                       m_indication_name,
                       m_dosageform_id,
                       a00darfo,
                       m_productplanningschema_selector,
                       c_dataimport_id,
                       pharmaproductcategory_name,
                       m_pharmaproductcategory_id,
                       a01aep,
                       a01apu,
                       apu_price_list_id,
                       aep_price_list_id,
                       manufacturer_id,
                       ad_issue_id,
                       i_lineno,
                       i_linecontent,
                       c_dataimport_run_id,
                       externalid,
                       netweight,
                       rawmaterialorigincountrycode,
                       customstariff,
                       m_customstariff_id,
                       rawmaterialorigin_id,
                       isscaleprice,
                       qty,
                       trademark,
                       pzn,
                       manufacturingmethod,
                       iscommissioned,
                       ispurchased,
                       diettype,
                       dosageqty,
                       dosageuom_id,
                       netweight_uom_id,
                       weight_uom_id,
                       weightuom,
                       netweightuom,
                       dosageuom,
                       m_hu_pi_value,
                       qtycu_uom_id,
                       isdefaultpacking,
                       invoicableqtybasedon,
                       uom_multiplierrate,
                       qtycu,
                       qtycu_uom_code,
                       isupdatename,
                       GuaranteeDaysMin)
SELECT NEXTVAL('i_product_seq'),
       ad_client_id,
       ad_org_id,
       isactive,
       created,
       createdby,
       updated,
       updatedby,
       i_isimported,
       i_errormsg,
       m_product_id,
       value,
       name,
       description,
       documentnote,
       help,
       upc,
       sku,
       x12de355,
       c_uom_id,
       productcategory_value,
       m_product_category_id,
       producttype,
       classification,
       volume,
       weight,
       shelfwidth,
       shelfheight,
       shelfdepth,
       unitsperpallet,
       discontinued,
       discontinuedby,
       imageurl,
       descriptionurl,
       bpartner_value,
       c_bpartner_id,
       iso_code,
       pricelist,
       pricepo,
       royaltyamt,
       priceeffective,
       vendorproductno,
       vendorcategory,
       order_min,
       order_pack,
       costperorder,
       deliverytime_promised,
       processing,
       processed,
       pricestd,
       pricelimit,
       c_taxcategory_id,
       c_taxcategory_name,
       m_pricelist_version_name,
       package_uom_id,
       packagesize,
       productmanufacturer,
       issold,
       isstocked,
       a00pgeinh,
       isprescription,
       isnarcotic,
       istfg,
       iscoldchain,
       fam_zub,
       m_indication_id,
       m_indication_name,
       m_dosageform_id,
       a00darfo,
       m_productplanningschema_selector,
       c_dataimport_id,
       pharmaproductcategory_name,
       m_pharmaproductcategory_id,
       a01aep,
       a01apu,
       apu_price_list_id,
       aep_price_list_id,
       manufacturer_id,
       ad_issue_id,
       i_lineno,
       i_linecontent,
       c_dataimport_run_id,
       externalid,
       netweight,
       rawmaterialorigincountrycode,
       customstariff,
       m_customstariff_id,
       rawmaterialorigin_id,
       isscaleprice,
       qty,
       trademark,
       pzn,
       manufacturingmethod,
       iscommissioned,
       ispurchased,
       diettype,
       dosageqty,
       dosageuom_id,
       netweight_uom_id,
       weight_uom_id,
       weightuom,
       netweightuom,
       dosageuom,
       m_hu_pi_value,
       qtycu_uom_id,
       isdefaultpacking,
       invoicableqtybasedon,
       uom_multiplierrate,
       qtycu,
       qtycu_uom_code,
       isupdatename,
       GuaranteeDaysMin
FROM "migration_data".i_product_inserts_prices_5years
;

