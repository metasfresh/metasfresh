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

-- One UOM per Navision "Unit of Measure"; key: C_UOM.X12DE355 = the Navision code (max. 4 characters in metasfresh).
-- A unit whose X12DE355 or name already exists is skipped, so the script can be re-run and standard UOMs are reused.
--
-- ADAPT PER PROJECT: the mapping below (twice: report and import), for
--  * Navision codes that mean an existing metasfresh UOM under another code ('KG' -> 'KGM', 'STCK' -> 'PCE');
--    keep it in sync with the "Base Unit of Measure" mapping in 07, 08, 09 and 11,
--  * Navision codes longer than 4 characters (they do not fit into X12DE355 and are reported + skipped if unmapped);
--    the mapping 'TOLONG' -> 'LONG' only shows the syntax.
-- Within the source, only the first unit per X12DE355 and per name is taken (both are unique in metasfresh).

-- Report: units that are NOT imported because their code does not fit into X12DE355
SELECT source_uom."Code", source_uom."Description"
FROM migration_data."Navision$Unit of Measure" source_uom
WHERE LENGTH(CASE source_uom."Code" -- ADAPT: same mapping as below
                 WHEN 'KG' THEN 'KGM'
                 WHEN 'STCK' THEN 'PCE'
                 WHEN 'TOLONG' THEN 'LONG'
                 ELSE source_uom."Code"
             END) > 4
;

DROP TABLE IF EXISTS migration_data.c_uom;
CREATE TABLE migration_data.c_uom AS
WITH source AS (SELECT source_uom.*,
                       CASE source_uom."Code" -- ADAPT: per-project mapping of Navision codes to X12DE355
                           WHEN 'KG' THEN 'KGM'
                           WHEN 'STCK' THEN 'PCE'
                           WHEN 'TOLONG' THEN 'LONG'
                           ELSE source_uom."Code"
                       END AS x12de355
                FROM migration_data."Navision$Unit of Measure" source_uom),
     candidates AS (SELECT s.*,
                           ROW_NUMBER() OVER (PARTITION BY s.x12de355 ORDER BY s."Code")     AS rn_code,
                           ROW_NUMBER() OVER (PARTITION BY s."Description" ORDER BY s."Code") AS rn_name
                    FROM source s
                    WHERE LENGTH(s.x12de355) <= 4)
SELECT 1000000                  AS ad_client_id,
       0                        AS ad_org_id,
       'Y'                      AS isactive,
       now()                    AS created,
       now()                    AS updated,
       99                       AS createdby,
       99                       AS updatedby,
       source_uom.x12de355      AS x12de355,
       source_uom."Code"        AS uomsymbol,
       source_uom."Description" AS name,
       source_uom."Description" AS description,
       0                        AS stdprecision,
       0                        AS costingprecision,
       'N'                      AS isdefault,
       NULL                     AS uomtype
FROM candidates source_uom
WHERE source_uom.rn_code = 1
  AND source_uom.rn_name = 1
  AND NOT EXISTS (SELECT 1
                  FROM c_uom u
                  WHERE u.x12de355 = source_uom.x12de355
                     OR u.name = source_uom."Description")
;

INSERT INTO public.c_uom (c_uom_id, ad_client_id, ad_org_id, isactive, created, updated, createdby, updatedby, x12de355,
                          uomsymbol, name, description, stdprecision, costingprecision, isdefault, uomtype)
SELECT nextval('c_uom_seq'),
       ad_client_id,
       ad_org_id,
       isactive,
       created,
       updated,
       createdby,
       updatedby,
       x12de355,
       uomsymbol,
       name,
       description,
       stdprecision,
       costingprecision,
       isdefault,
       uomtype
FROM migration_data.c_uom
;
