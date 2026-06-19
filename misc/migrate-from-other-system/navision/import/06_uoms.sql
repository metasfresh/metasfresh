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




DROP TABLE IF EXISTS migration_data.c_uom;
CREATE TABLE migration_data.c_uom AS
SELECT 1000000                  AS ad_client_id,
       0                        AS ad_org_id,
       'Y'                      AS isactive,
       now()                    AS created,
       now()                    AS updated,
       99                       AS createdby,
       99                       AS updatedby,
       CASE source_uom."Code"
           WHEN 'TOLONG' THEN 'LONG' --shorten to metasfresh max length 4
           ELSE source_uom."Code"
           END AS x12de355,
       source_uom."Code"        AS uomsymbol,
       source_uom."Description" AS name,
       source_uom."Description" AS description,
       0                        AS stdprecision,
       0                        AS costingprecision,
       'N'                      AS isdefault,
       NULL                     AS uomtype
FROM migration_data."Navision$Unit of Measure" source_uom
WHERE NOT EXISTS (SELECT 1 FROM c_uom u WHERE source_uom."Code" = u.x12de355)
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
