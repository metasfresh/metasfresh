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

-- One shipper per Navision "Shipment Method"; key: InternalName = '001 - ' || "Code".

DROP TABLE IF EXISTS migration_data.m_shipper;
CREATE TABLE migration_data.m_shipper AS
SELECT 1000000               AS ad_client_id,
       0                     AS ad_org_id,
       'Y'                   AS isactive,
       now()                 AS created,
       99                    AS createdby,
       now()                 AS updated,
       99                    AS updatedby,
       sm."Description"      AS name,
       sm."Description"      AS description,
       NULL::numeric         AS c_bpartner_id,
       NULL                  AS trackingurl,
       'N'                   AS isdefault,
       NULL                  AS shippergateway,
       sm."Description"      AS value,
       '001 - ' || sm."Code" AS internalname, -- adapt per project
       '1970-01-01 08:00:00'::timestamp AS pickuptimefrom, -- mandatory; same default as in the M_Shipper window
       '1970-01-01 17:00:00'::timestamp AS pickuptimeto
FROM migration_data."Navision$Shipment Method" sm
WHERE NOT EXISTS (SELECT 1 FROM m_shipper WHERE name = sm."Description")
;


INSERT INTO public.m_shipper (m_shipper_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby,
                              name, description, c_bpartner_id, trackingurl, isdefault, shippergateway, value,
                              internalname, pickuptimefrom, pickuptimeto)
SELECT nextval('m_shipper_seq'),
       ad_client_id,
       ad_org_id,
       isactive,
       created,
       createdby,
       updated,
       updatedby,
       name,
       description,
       c_bpartner_id,
       trackingurl,
       isdefault,
       shippergateway,
       value,
       internalname,
       pickuptimefrom,
       pickuptimeto
FROM migration_data.m_shipper
;



-- Routing codes. ADAPT PER PROJECT, or drop this part:
-- the table "Route" (column "Beschreibung") is not standard Navision but an add-on / customisation of one source system,
-- and all its routes are attached to ONE shipper, identified by its InternalName ('001 - ' || 'shipperWithRoutes' below).
-- Create that shipper manually before (a Navision shipment method code has max. 10 characters, so it is not one of them),
-- or change the name below; without it no routing code is created.
DROP TABLE IF EXISTS migration_data.m_shipper_routes;
CREATE TABLE migration_data.m_shipper_routes AS
SELECT 1000000              AS ad_client_id,
       0                    AS ad_org_id,
       'Y'                  AS isactive,
       now()                AS created,
       99                   AS createdby,
       now()                AS updated,
       99                   AS updatedby,
       r."Beschreibung"     AS name,
       shipper.m_shipper_id AS m_shipper_id
FROM migration_data."Navision$Route" r
         JOIN m_shipper shipper
              ON shipper.internalname = '001 - ' || 'shipperWithRoutes' -- adapt per project
WHERE NOT EXISTS (SELECT 1
                  FROM m_shipper_routingcode
                  WHERE name = r."Beschreibung"
                    AND m_shipper_id = shipper.m_shipper_id)
;

INSERT INTO M_Shipper_RoutingCode (M_Shipper_RoutingCode_ID, m_shipper_id, ad_client_id, ad_org_id, isactive, created,
                                   createdby, updated, updatedby, name)
SELECT nextval('M_Shipper_RoutingCode_seq'),
       m_shipper_id,
       ad_client_id,
       ad_org_id,
       isactive,
       created,
       createdby,
       updated,
       updatedby,
       name
FROM migration_data.m_shipper_routes
;
